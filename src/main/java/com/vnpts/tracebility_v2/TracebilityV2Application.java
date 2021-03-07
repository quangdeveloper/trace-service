package com.vnpts.tracebility_v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TracebilityV2Application {

  public static void main(String[] args) {
    System.setProperty("server.servlet.context-path", "/tracebility");
    SpringApplication.run(TracebilityV2Application.class, args);
  }

}
