package com.vnpts.tracebility_v2.model.excelConfig;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExcelConfig {
  String templateFile;
  List<Header> listHeader;
  Data data;

  @Override
  public String toString() {
    return "ExcelConfig{" +
        "templateFile='" + templateFile + '\'' +
        ", listHeader=" + listHeader +
        ", data=" + data +
        '}';
  }
}
