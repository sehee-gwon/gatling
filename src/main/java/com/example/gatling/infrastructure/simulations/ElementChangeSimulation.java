package com.example.gatling.infrastructure.simulations;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.presentation.DesignRequest;
import com.example.gatling.infrastructure.stomp.SendFrame;
import com.example.gatling.infrastructure.stomp.StompFrame;
import com.example.gatling.infrastructure.utils.ParserUtils;
import com.example.gatling.infrastructure.utils.SheetXmlUtils;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class ElementChangeSimulation extends Simulation {

    static final String CONNECTION_WS_NAME = "Connect WS";
    static final String CLOSE_WS_NAME = "Close WS";
    static final String CONNECTION_WS_URL = "/connect";

    static final String DESIGN_ID_NAME = "designId";
    static final String SHEET_KEY_NAME = "sheetKey";

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
    List<Integer> insertSheetKeys = Arrays.asList(1, 2, 3);
    List<Integer> insertElementIds = Arrays.asList(2, 3, 4, 5, 6, 7, 8, 9, 10);

    List<Integer> updateDesignIds = Arrays.asList(1, 2);
    List<Integer> updateSheetKeys = Arrays.asList(1, 2);
    List<Integer> updateElementIds = Arrays.asList(3, 4);

    List<Integer> deleteDesignIds = Arrays.asList(1, 2);
    List<Integer> deleteSheetKeys = Arrays.asList(1, 2, 3);
    List<Integer> deleteElementIds = Arrays.asList(3, 4);

    ChainBuilder insert =
            exec(ws(CONNECTION_WS_NAME).connect(CONNECTION_WS_URL))
            .pause(1)
            .foreach(insertDesignIds, DESIGN_ID_NAME).on(
                foreach(insertSheetKeys, SHEET_KEY_NAME).on(
                    exec(ws("INSERT SEND").sendText(session -> {
                        List<Sheet> sheets = SheetXmlUtils.getSaveElement(session.getInt(SHEET_KEY_NAME), insertElementIds);

                        StompFrame request = SendFrame.builder()
                                .body(ParserUtils.toJsonString(new DesignRequest(session.getInt(DESIGN_ID_NAME), ActionType.INSERT, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return request.make();
                    })).pause(1)
                )
            )
            .pause(1)
            .exec(ws(CLOSE_WS_NAME).close());

    ChainBuilder update =
            exec(ws(CONNECTION_WS_NAME).connect(CONNECTION_WS_URL))
            .pause(1)
            .foreach(updateDesignIds, DESIGN_ID_NAME).on(
                foreach(updateSheetKeys, SHEET_KEY_NAME).on(
                    exec(ws("UPDATE SEND").sendText(session -> {
                        List<Sheet> sheets = SheetXmlUtils.getSaveElement(session.getInt(SHEET_KEY_NAME), updateElementIds);

                        StompFrame request = SendFrame.builder()
                                .body(ParserUtils.toJsonString(new DesignRequest(session.getInt(DESIGN_ID_NAME), ActionType.UPDATE, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return request.make();
                    })).pause(1)
                )
            )
            .pause(1)
            .exec(ws(CLOSE_WS_NAME).close());

    ChainBuilder delete =
            exec(ws(CONNECTION_WS_NAME).connect(CONNECTION_WS_URL))
            .foreach(deleteDesignIds, DESIGN_ID_NAME).on(
                foreach(deleteSheetKeys, SHEET_KEY_NAME).on(
                    exec(ws("DELETE SEND").sendText(session -> {
                        List<Sheet> sheets = SheetXmlUtils.getDeleteElement(session.getInt(SHEET_KEY_NAME), deleteElementIds);

                        StompFrame request = SendFrame.builder()
                                .body(ParserUtils.toJsonString(new DesignRequest(session.getInt(DESIGN_ID_NAME), ActionType.DELETE, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return request.make();
                    })).pause(1)
                )
            )
            .pause(1)
            .exec(ws(CLOSE_WS_NAME).close());

    ScenarioBuilder users = scenario("Users").exec(insert, update, delete);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}