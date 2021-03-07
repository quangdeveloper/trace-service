package com.vnpts.tracebility_v2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuItem {
  String title, icon, link;
  List<MenuItem> children;

  public MenuItem() {
    title = "";
    icon = "";
    link = "";
  }

  public MenuItem(Map m) {
    title = (String) m.get("S_NAME");
    icon = (String) m.get("S_STYLE_CLASS");
    link = (String) m.get("S_PATH");
  }

  @Override
  public String toString() {
    return "MenuItem{" +
            "title='" + title + '\'' +
            ", icon='" + icon + '\'' +
            ", link='" + link + '\'' +
            ", children=" + children +
            '}';
  }

  public void addItem(MenuItem m) {
    if (children == null) children = new ArrayList<>();
    children.add(m);
  }

  public List<MenuItem> getChildren() {
    return children;
  }
}
