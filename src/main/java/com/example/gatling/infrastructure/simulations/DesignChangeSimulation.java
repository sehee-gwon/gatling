package com.example.gatling.infrastructure.simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class DesignChangeSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/xhtml+xml;q=0.8,application/xml,*/*;q=0.6")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .wsBaseUrl("ws://localhost:8080");

    ChainBuilder insert =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .repeat(10, "i").on(
                randomSwitch().on(
                    Choice.withWeight(50.0,
                        exec(ws("UPDATE SEND").sendText(session -> {
                                /*int i = session.getInt("i");
                                String designIdx = String.format("DESIGN_%03d", i+1);
                                List<Sheet> sheets = RandomSheetMaker.createSheets(i+1, 1)

                                StompFrame update = SendFrame.builder()
                                    .destination("/app/designId")
                                    .body(payload("2", ActionType.UPDATE))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .build();*/
                                return "";
                            })
                        ).pause(1)
                    ),
                    Choice.withWeight(50.0,
                        exec(ws("DELETE SEND").sendText(session -> {
                                /*int i = session.getInt("i");
                                StompFrame delete = SendFrame.builder()
                                    .destination("/app/designId")
                                    .body(payload("3", ActionType.DELETE))
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .build();*/
                                return "";
                            })
                        ).pause(1)
                    )
                )
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(insert);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}