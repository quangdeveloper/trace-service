package com.vnpts.tracebility_v2;

import com.google.gson.Gson;
import com.vnpts.tracebility_v2.model.ProcessStageModel;
import com.vnpts.tracebility_v2.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
  public static void main(String[] args) {
    ProcessStageModel model0 = new ProcessStageModel();
    ProcessStageModel model1 = new ProcessStageModel();
    ProcessStageModel model11 = new ProcessStageModel();
    ProcessStageModel model111 = new ProcessStageModel();
    ProcessStageModel model12 = new ProcessStageModel();
    ProcessStageModel model2 = new ProcessStageModel();
    model0.setName("buoc 0");
    model0.setDesc("mo ta buoc 0");

    model1.setName("buoc 1");
    model1.setDesc("mo ta buoc 1");

    model11.setName("buoc 1.1");
    model11.setDesc("mo ta buoc 1.1");

    model111.setName("buoc 1.1.1");
    model111.setDesc("mo ta buoc 1.1.1");

    model12.setName("buoc 1.2");
    model12.setDesc("mo ta buoc 1.2");

    model2.setName("buoc 2");
    model2.setDesc("mo ta buoc 2");

    List<ProcessStageModel> child0 = new ArrayList<>();
    model0.setChildren(child0);
    List<ProcessStageModel> child1 = new ArrayList<>();
    model1.setChildren(child1);
    List<ProcessStageModel> child11 = new ArrayList<>();
    model11.setChildren(child11);
//    List<ProcessStageModel> child0 = new ArrayList<>();
//    List<ProcessStageModel> child0 = new ArrayList<>();
    child0.add(model1);
    child0.add(model2);
    child1.add(model11);
    child1.add(model12);
    child11.add(model111);
    Gson gson = new Gson();
    createRawData(model0, null, 0);
    System.out.println(gson.toJson(model0));
  }

  static void createRawData(ProcessStageModel child, ProcessStageModel parent, int index) {
    child.setUniqueID(Utils.genUUID());
    child.setProcessID(1624);
    if (parent == null) {
      child.setStep("");
    } else {
      child.setStep(parent.getStep() + index + ".");
      child.setParentUniqueID(parent.getUniqueID());
    }
    if (child.getChildren() != null)
      for (int i = 0; i < child.getChildren().size(); i++)
        createRawData(child.getChildren().get(i), child, i + 1);
  }
}
