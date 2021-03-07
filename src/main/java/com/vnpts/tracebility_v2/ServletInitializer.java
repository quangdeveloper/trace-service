package com.vnpts.tracebility_v2;

/**
 * @author LONGPD
 * @since 08/17/2019
 */
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(TracebilityV2Application.class);
  }

}
