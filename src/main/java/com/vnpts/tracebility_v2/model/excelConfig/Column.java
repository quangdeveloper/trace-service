package com.vnpts.tracebility_v2.model.excelConfig;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Column {
  String name;
  String format;
  int groupLevel = 0;

  @Override
  public String toString() {
    return "Column{" +
        "name='" + name + '\'' +
        ", format='" + format + '\'' +
        ", groupLevel=" + groupLevel +
        '}';
  }
}
