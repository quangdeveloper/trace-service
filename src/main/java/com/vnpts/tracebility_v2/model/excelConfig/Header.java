package com.vnpts.tracebility_v2.model.excelConfig;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Header {
  String headerText;
  int row, col;
  String defaultText;
  String columnsName;

  @Override
  public String toString() {
    return "Header{" +
        "headerText='" + headerText + '\'' +
        ", row=" + row +
        ", col=" + col +
        ", defaultText='" + defaultText + '\'' +
        ", columnsName='" + columnsName + '\'' +
        '}';
  }
}
