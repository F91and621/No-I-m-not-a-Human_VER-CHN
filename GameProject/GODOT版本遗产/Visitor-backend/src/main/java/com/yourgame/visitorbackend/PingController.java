package com.yourgame.visitorbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "pong! Godot 与 Java 后台通信正常！🎉 当前时间: " + java.time.LocalDateTime.now();
    }
}
