package com.vnpts.tracebility_v2.model;

import com.vnpts.tracebility_v2.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoleModel {
  int id, level;
  String label;
  List<RoleModel> children;

  public RoleModel() {
  }

  public RoleModel(List<Map> roleList) {
    RoleModel[] save = new RoleModel[10];
    save[0] = this;
    for (Map m : roleList) {
      RoleModel model = new RoleModel(m);
      int level = Utils.getInt(m, "LEVEL");
      save[level - 1].addItem(model);
      save[level] = model;
    }
  }

  public RoleModel(Map map) {
    id = Utils.getInt(map, "N_ID");
    level = Utils.getInt(map, "LEVEL");
    label = (String) map.get("S_ROLE_NAME");
    children = new ArrayList<>();
  }

  public void addItem(RoleModel model) {
    if (children == null) children = new ArrayList<>();
    children.add(model);
  }

}
