package com.vnpts.tracebility_v2.model.excelConfig;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Data {
  int row, col;
  List<Column> columns;

  @Override
  public String toString() {
    return "Data{" +
        "row=" + row +
        ", col=" + col +
        ", columns=" + columns +
        '}';
  }
}
