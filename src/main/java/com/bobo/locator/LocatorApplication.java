package com.bobo.locator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@SpringBootApplication
public class LocatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocatorApplication.class, args);
    }

}
