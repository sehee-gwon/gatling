package com.example.gatling.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class XmlMessageController {
    //private final SimpMessageSendingOperations simpMessageSendingOperations;

    @MessageMapping("/loading")
    public void loading() {
        log.info("Hi, I am XmlMessageController!! XML Loading...");
    }

    @MessageMapping("/insert")
    public void insert() {
        log.info("Hi, I am XmlMessageController!! XML Insert...");
    }

    @MessageMapping("/update")
    public void update() {
        log.info("Hi, I am XmlMessageController!! XML Update...");
    }

    @MessageMapping("/delete")
    public void delete() {
        log.info("Hi, I am XmlMessageController!! XML Delete...");
    }
}
