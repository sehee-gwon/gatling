package com.example.gatling.infrastructure.simulations;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.presentation.DesignActionRequest;
import com.example.gatling.infrastructure.stomp.SendFrame;
import com.example.gatling.infrastructure.stomp.StompFrame;
import com.example.gatling.infrastructure.util.PayloadUtil;
import com.example.gatling.infrastructure.util.RandomSheetUtil;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.MediaType;

import java.util.List;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class DesignInsertSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/xhtml+xml;q=0.8,application/xml,*/*;q=0.6")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .wsBaseUrl("ws://localhost:8080");

    final int designCnt = 5;

    ChainBuilder insert =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .repeat(designCnt, "i").on(
                exec(ws("INSERT SEND").sendText(session -> {
                        int i = session.getInt("i");
                        String designIdx = String.format("DESIGN_%03d", i+1);
                        List<Sheet> sheets = RandomSheetUtil.createSheets(ActionType.INSERT, i+1, 1);

                        StompFrame insert = SendFrame.builder()
                                .destination("/app/designId")
                                .body(PayloadUtil.payload(new DesignActionRequest(designIdx, ActionType.INSERT, sheets)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .build();

                        return insert.make();
                    })
                ).pause(1)
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(insert);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}