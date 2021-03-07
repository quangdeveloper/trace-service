package com.vnpts.tracebility_v2.model;

import com.vnpts.tracebility_v2.util.Utils;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class ProcessStageModel {
  String step;
  String name;
  String desc;
  long id;
  long parentID;
  long processID;
  String uniqueID;
  String parentUniqueID;
  List<ProcessStageModel> children;

  public ProcessStageModel() {
    if (children == null) children = new ArrayList<>();
  }

  @Override
  public String toString() {
    return "ProcessStageModel{" +
        "step='" + step + '\'' +
        ", name='" + name + '\'' +
        ", desc='" + desc + '\'' +
        ", id=" + id +
        ", parentID=" + parentID +
        ", processID=" + processID +
        ", uniqueID='" + uniqueID + '\'' +
        ", parentUniqueID='" + parentUniqueID + '\'' +
        ", children=" + children +
        '}';
  }

  public void setRoot(String uniqueID, long processID) {
    this.step = "";
    this.name = "root";
    this.id = 0;
    this.uniqueID = uniqueID;
    this.processID = processID;
  }

  public void pushStep(ProcessStageModel step) {
    children.add(step);
  }

  public ProcessStageModel(Map map) {
    if (children == null) children = new ArrayList<>();
    this.step = (String) map.get("S_STEP");
    this.name = (String) map.get("S_NAME");
    this.desc = (String) map.get("S_DESC");
    this.uniqueID = (String) map.get("S_UID");
    this.parentUniqueID = (String) map.get("S_PARENT_UID");
    this.id = Utils.getLong(map, "N_ID");
    this.parentID = Utils.getLong(map, "N_PARENT_ID");
    this.processID = Utils.getLong(map, "N_PROCESS_ID");
  }
}
