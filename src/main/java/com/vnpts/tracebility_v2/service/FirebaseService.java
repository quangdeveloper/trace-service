package com.vnpts.tracebility_v2.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.TopicManagementResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FirebaseService {
  @Value("${firebase.create_topic_url}")
  private String FIREBASE_CREATE_TOPIC_URL;

  public FirebaseService() throws IOException {
    FileInputStream fis = new FileInputStream(new ClassPathResource("/traceabilityv2-firebase-adminsdk-lrf1x-bd06ab75df.json").getFile());
    FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(fis))
        .setDatabaseUrl("https://traceabilityv2.firebaseio.com")
        .build();
    FirebaseApp.initializeApp(options);
  }

  int subscribeTopic(String deviceToken, String topicName) throws FirebaseMessagingException {
    List<String> registrationTokens = Collections.singletonList(deviceToken);
    TopicManagementResponse response = FirebaseMessaging.getInstance().subscribeToTopic(registrationTokens, topicName);
    log.info(response.getSuccessCount() + " tokens were subscribed successfully");
    return response.getSuccessCount();
  }

  int unsubscribeTopic(String deviceToken, String topicName) throws FirebaseMessagingException {
    List<String> registrationTokens = Collections.singletonList(deviceToken);
    TopicManagementResponse response = FirebaseMessaging.getInstance().unsubscribeFromTopic(registrationTokens, topicName);
    log.info(response.getSuccessCount() + " tokens were unsubscribed  successfully");
    return response.getSuccessCount();
  }

  void sendNotifyMessageToTopic(String topicName, Map map) throws FirebaseMessagingException {
    Message message = Message.builder()
        .putData("id", ((BigDecimal) map.get("N_ID")).intValue() + "")
        .putData("title", (String) map.get("S_TITLE"))
        .putData("content", (String) map.get("S_CONTENT"))
        .setTopic(topicName)
        .build();
    String response = FirebaseMessaging.getInstance().send(message);
    log.info("Successfully sent message: " + response);
  }

  public void sendNotifyMessageToTopic(Map map) throws FirebaseMessagingException {
    Message message = Message.builder()
        .putData("id", ((BigDecimal) map.get("N_ID")).intValue() + "")
        .putData("title", (String) map.get("S_TITLE"))
        .putData("content", (String) map.get("S_CONTENT"))
        .setTopic((String) map.get("S_RECEIVER"))
        .build();
    String response = FirebaseMessaging.getInstance().send(message);
    log.info("Successfully sent message: " + response);
  }
}
