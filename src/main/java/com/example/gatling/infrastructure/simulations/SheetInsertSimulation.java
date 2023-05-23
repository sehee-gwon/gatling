package com.example.gatling.infrastructure.simulations;

import com.example.gatling.design.domain.ActionType;
import com.example.gatling.design.domain.Sheet;
import com.example.gatling.design.presentation.DesignActionRequest;
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

public class SheetInsertSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/xhtml+xml;q=0.8,application/xml,*/*;q=0.6")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .wsBaseUrl("ws://localhost:8080");

    List<Integer> designIds = Arrays.asList(1, 2, 3);
    List<Integer> sheetKeys = Arrays.asList(1, 2, 3, 4, 5);

    int elementSize = 1;

    ChainBuilder insert =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .foreach(designIds, "designId").on(
                exec(ws("INSERT SEND").sendText(session -> {
                    List<Sheet> sheets = SheetXmlUtils.getInsertSheet(sheetKeys, elementSize);

                    StompFrame request = SendFrame.builder()
                            .body(ParserUtils.toJsonString(new DesignActionRequest(session.getInt("designId"), ActionType.INSERT, sheets)))
                            .contentType(MediaType.APPLICATION_JSON)
                            .build();

                    return request.make();
                })).pause(1)
            )
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(insert);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}