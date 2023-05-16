package com.example.gatling.simulations;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.ws;
import static io.gatling.recorder.internal.bouncycastle.oer.its.ieee1609dot2.basetypes.Duration.seconds;

public class XmlSimulation extends Simulation {
    FeederBuilder<String> feeder = csv("search.csv").random();

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .doNotTrackHeader("1")
            .acceptLanguageHeader("en-US,en;q=0.5")
            .acceptEncodingHeader("gzip, deflate")
            .userAgentHeader("Mozilla/5.0(Windows NT 6.3; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0")
            .wsBaseUrl("ws://localhost:8080");

    ChainBuilder loading =
            exec(ws("Connect WS").connect("/connect"))
            .pause(1)
            .exec(
                ws("Connect via STOMP")
                .sendText("[\"CONNECT\\naccept-version:1.1,1.0\\nheart-beat:10000,10000\\n\\n\\u0000\"]")
            )
            .pause(1)
            .exec(
                ws("Send Message")
                .sendText("[\"SEND\\ndestination:/xml/loading\\ncontent-length:0\\n\\n\\u0000\"]"))
            .pause(1)
            .exec(ws("Close WS").close());

    ScenarioBuilder users = scenario("Users").exec(loading);

    {
        setUp(users.injectOpen(atOnceUsers(1))).protocols(httpProtocol);
    }
}
