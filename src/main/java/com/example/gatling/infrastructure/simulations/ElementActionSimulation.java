package com.example.gatling.infrastructure.simulations;

import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.domain.enumeration.ActionType;
import com.example.gatling.design.domain.enumeration.Target;
import com.example.gatling.design.presentation.DesignRequest;
import com.example.gatling.infrastructure.stomp.SendFrame;
import com.example.gatling.infrastructure.stomp.StompFrame;
import com.example.gatling.infrastructure.utils.ParserUtils;
import com.example.gatling.infrastructure.utils.SheetXmlMaker;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Session;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class ElementActionSimulation extends Simulation {
    static final String CONNECTION_WS_NAME = "Connect WS";
    static final String CLOSE_WS_NAME = "Close WS";
    static final String CONNECTION_WS_URL = "/connect";

    static final String DESIGN_ID_NAME = "designId";
    static final String SHEET_ID_NAME = "sheetId";

    public String createPayload(Session session, ActionType actionType, List<Integer> elementIds) {
        String designIdx = SheetXmlMaker.DESIGN_ID_FORMAT + session.getInt(DESIGN_ID_NAME);
        List<Sheet> sheets = SheetXmlMaker.createElements(actionType, session.getInt(SHEET_ID_NAME), elementIds);

        DesignRequest request = DesignRequest.builder()
                .designIdx(designIdx)
                .target(Target.ELEMENT)
                .actionType(actionType)
                .sheets(sheets)
                .build();

        StompFrame frame = SendFrame.builder()
                .body(ParserUtils.toJsonString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .build();

        return frame.createPayload();
    }

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/xhtml+xml;q=0.8,application/xml,*/*;q=0.6")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .wsBaseUrl("ws://localhost:8080");

    // design: 1 ~ 3
    // sheet: 1 ~ 3
    // element: 1

    List<Integer> insertDesignIds = Arrays.asList(1, 2);
    List<Integer> insertSheetIds = Arrays.asList(1, 2, 3);
    List<Integer> insertElementIds = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10);

    List<Integer> updateDesignIds = Arrays.asList(1, 2);
    List<Integer> updateSheetIds = Arrays.asList(1, 2);
    List<Integer> updateElementIds = Arrays.asList(3, 4);

    List<Integer> deleteDesignIds = Arrays.asList(1, 2);
    List<Integer> deleteSheetIds = Arrays.asList(1, 2, 3);
    List<Integer> deleteElementIds = Arrays.asList(3, 4);

    ChainBuilder insert =
            exec(ws(CONNECTION_WS_NAME).connect(CONNECTION_WS_URL))
                .pause(1)
                .foreach(insertDesignIds, DESIGN_ID_NAME).on(
                        foreach(insertSheetIds, SHEET_ID_NAME).on(
                                exec(ws("Insert Elements")
                                        .sendText(session -> createPayload(session, ActionType.INSERT, insertElementIds)))
                                        .pause(1)
                        )
                )
                .pause(1)
                .exec(ws(CLOSE_WS_NAME).close());

    ChainBuilder update =
            exec(ws(CONNECTION_WS_NAME).connect(CONNECTION_WS_URL))
                    .pause(1)
                    .foreach(updateDesignIds, DESIGN_ID_NAME).on(
                            foreach(updateSheetIds, SHEET_ID_NAME).on(
                                    exec(ws("Update Elements")
                                            .sendText(session -> createPayload(session, ActionType.UPDATE, updateElementIds)))
                                            .pause(1)
                            )
                    )
                    .pause(1)
                    .exec(ws(CLOSE_WS_NAME).close());

    ChainBuilder delete =
            exec(ws(CONNECTION_WS_NAME).connect(CONNECTION_WS_URL))
                .foreach(deleteDesignIds, DESIGN_ID_NAME).on(
                        foreach(deleteSheetIds, SHEET_ID_NAME).on(
                                exec(ws("Delete Elements")
                                        .sendText(session -> createPayload(session, ActionType.DELETE, deleteElementIds)))
                                        .pause(1)
                        )
                )
                .pause(1)
                .exec(ws(CLOSE_WS_NAME).close());

    ScenarioBuilder users = scenario("Users").exec(insert, update, delete);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}