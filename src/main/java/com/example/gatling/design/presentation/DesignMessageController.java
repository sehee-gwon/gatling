package com.example.gatling.design.presentation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DesignMessageController {
    @MessageMapping("/app/designId")
    public void action(DesignActionRequest request) {
        log.info("Hi, I am DesignMessageController!!");
        log.info("Xml {}...", request.getAction());

        log.debug("request: {}", request);
    }
}
