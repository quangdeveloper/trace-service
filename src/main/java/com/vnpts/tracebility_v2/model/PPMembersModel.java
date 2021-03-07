package com.vnpts.tracebility_v2.model;

import com.vnpts.tracebility_v2.util.Utils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PPMembersModel {
  long idUser;
  int[] businessType;
  int status;
  int isLock;

  public String toDataIn(){
    return idUser + "|" + Utils.toStrArr(businessType) + "|" + status + "|" + isLock;
  }
}
