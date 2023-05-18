package com.example.gatling.configuration.simulations;

import com.example.gatling.stomp.domain.SendFrame;
import com.example.gatling.stomp.domain.StompFrame;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.http.MediaType;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;

public class DesignActionSimulation extends Simulation {
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/xhtml+xml;q=0.8,application/xml,*/*;q=0.6")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:16.0) Gecko/20100101 Firefox/16.0")
            .wsBaseUrl("ws://localhost:8080");

    StompFrame stompFrame = SendFrame.builder()
            .destination("/app/designId")
            .body("")
            .contentType(MediaType.APPLICATION_XML)
            .build();

    ChainBuilder insert =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .exec(ws("Send").sendText(stompFrame.make()))
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(insert);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}