package com.example.gatling.infrastructure.simulations;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.presentation.DesignActionRequest;
import com.example.gatling.infrastructure.stomp.SendFrame;
import com.example.gatling.infrastructure.stomp.StompFrame;
import com.example.gatling.infrastructure.util.PayloadUtil;
import com.example.gatling.infrastructure.util.SheetXmlUtil;
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
    List<Integer> insertElementIds = Arrays.asList(2, 3, 4);

    List<Integer> updateDesignIds = Arrays.asList(1, 2);
    List<Integer> updateSheetKeys = Arrays.asList(1, 2);
    List<Integer> updateElementIds = Arrays.asList(3, 4);

    List<Integer> deleteDesignIds = Arrays.asList(1, 2);
    List<Integer> deleteSheetKeys = Arrays.asList(1, 2, 3);
    List<Integer> deleteElementIds = Arrays.asList(3, 4);

    ChainBuilder insert =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .foreach(insertDesignIds, "designId").on(
                foreach(insertSheetKeys, "sheetKey").on(
                    exec(ws("INSERT SEND").sendText(session -> {
                        List<Sheet> sheets = SheetXmlUtil.getSaveElement(session.getInt("sheetKey"), insertElementIds);

                        StompFrame insert = SendFrame.builder()
                                .body(PayloadUtil.payload(new DesignActionRequest(session.getInt("designId"), ActionType.INSERT, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return insert.make();
                    })).pause(1)
                )
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ChainBuilder update =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .foreach(updateDesignIds, "designId").on(
                foreach(updateSheetKeys, "sheetKey").on(
                    exec(ws("UPDATE SEND").sendText(session -> {
                        List<Sheet> sheets = SheetXmlUtil.getSaveElement(session.getInt("sheetKey"), updateElementIds);

                        StompFrame update = SendFrame.builder()
                                .body(PayloadUtil.payload(new DesignActionRequest(session.getInt("designId"), ActionType.UPDATE, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return update.make();
                    })).pause(1)
                )
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ChainBuilder delete =
            exec(ws("Connect WS").connect("/connect"))
            .foreach(deleteDesignIds, "designId").on(
                foreach(deleteSheetKeys, "sheetKey").on(
                    exec(ws("UPDATE SEND").sendText(session -> {
                        List<Sheet> sheets = SheetXmlUtil.getDeleteElement(session.getInt("sheetKey"), deleteElementIds);

                        StompFrame delete = SendFrame.builder()
                                .body(PayloadUtil.payload(new DesignActionRequest(session.getInt("designId"), ActionType.DELETE, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return delete.make();
                    })).pause(1)
                )
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(insert, update, delete);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}