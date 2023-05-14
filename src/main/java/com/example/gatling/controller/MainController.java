package com.example.gatling.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/loading")
    public void loading() {
        log.info("loading action");
    }

    @GetMapping("/insert")
    public void insert() {
        log.info("insert action");
    }

    @GetMapping("/update")
    public void update() {
        log.info("update action");
    }

    @GetMapping("/delete")
    public void delete() {
        log.info("delete action");
    }
}
