package com.vnpts.tracebility_v2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PPUserModel {
  String userID, userName, address, phone, email;
  String province, district, ward;
  int[] businessType;

  int stt;
}
