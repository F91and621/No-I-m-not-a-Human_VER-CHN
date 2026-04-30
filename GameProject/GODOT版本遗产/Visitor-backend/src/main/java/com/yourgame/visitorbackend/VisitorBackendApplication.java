package com.yourgame.visitorbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VisitorBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(VisitorBackendApplication.class, args);
        System.out.println("✅ 伪人后台启动成功！访问 http://localhost:8080");
    }
}
