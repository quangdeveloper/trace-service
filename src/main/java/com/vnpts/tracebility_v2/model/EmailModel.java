package com.vnpts.tracebility_v2.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class EmailModel {
  private int id;
  private String subject;
  private String templateName;
  private String from;
  private String receiver;
  private String cc;
  private String bcc;
  private Map params;
}