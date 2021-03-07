package com.vnpts.tracebility_v2.config;

import com.vnpts.tracebility_v2.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Value("${firebase.server_key}")
  private String FIREBASE_SERVER_KEY;
  @Autowired
  private GsonResponse gson;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/**").permitAll();
    http.exceptionHandling()
        .authenticationEntryPoint(new CustomEntryPoint());
    http.csrf().disable();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

  }

  class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
      ServletOutputStream out = httpServletResponse.getOutputStream();
      ResponseCode responseCode = new ResponseCode();
      responseCode.setForbidden();
      out.write(gson.toJson(responseCode).getBytes("UTF-8"));
      out.flush();
      out.close();
    }
  }

  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder builder) {
    ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
    interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
    return builder
        .interceptors(interceptors)
        .build();
  }
}

