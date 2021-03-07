package com.vnpts.tracebility_v2.dao.impl;

import com.google.gson.Gson;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ProcessDAO;
import com.vnpts.tracebility_v2.model.ProcessStageModel;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProcessDaoImpl extends BaseDAO implements ProcessDAO {
  @Override
  public Map cbbType() throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PROCESS + "cbb_type", null);
  }

  @Override
  public Map cbbProductForEdit(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PROCESS + "cbb_product_edit", inParams);
  }

  @Override
  public Map cbbProductForEditCustom(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction("PK_QUANG_REPORT." + "cbb_get_product_by_user_role", inParams);
  }

  @Override
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException {
    Map out = this.getClone().callFunction(PK_PROCESS + "get_by_id", inParams);
    List<Map> stages = (List<Map>) out.get("PO_PROCESS_STAGE");
    ProcessStageModel root = new ProcessStageModel();
    root.setRoot((String) stages.get(0).get("S_PARENT_UID"), Utils.getLong(stages.get(0), "N_PROCESS_ID"));
    ProcessStageModel[] save = new ProcessStageModel[10];
    save[0] = root;
    for (Map m : stages) {
      ProcessStageModel model = new ProcessStageModel(m);
      int level = Utils.getInt(m, "LEVEL");
      save[level - 1].pushStep(model);
      save[level] = model;
    }
    out.put("PO_STAGES", root);
    return out;
  }

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PROCESS + "get_list", inParams);
  }

  @Override
  public Map getCBB(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PROCESS + "get_cbb", inParams);
  }

  @Override
  public Map publicOrNot(Map inParams) throws CloneNotSupportedException, SQLException {
    if (!inParams.get("pi_typeUser").equals("ADMIN")) {
      return Utils.returnMap("013");
    }
    return this.getClone().callProcedure(PK_PROCESS + "public_or_not", inParams);
  }

  @Override
  public ResponseMap createProcess(Map inParams) throws CloneNotSupportedException, SQLException {
    BaseDAO clone = this.getClone();
    String stageStr = (String) inParams.get("pi_stage");
    Gson gson = new Gson();
    ProcessStageModel model;
    try {
      model = gson.fromJson(stageStr, ProcessStageModel.class);
      if (model.getChildren() == null || model.getChildren().size() == 0) {
        return new ResponseMap(Utils.returnMap("308"));
      }
    } catch (Exception e) {
      return new ResponseMap(Utils.returnMap("307"));
    }
    inParams = Utils.trimMap(inParams, "pi_name", "pi_details");
    inParams = Utils.changeClob(inParams, "pi_details");
    ResponseMap res = new ResponseMap(clone.callProcedure(PK_PROCESS + "create_process", inParams));
    if (!res.isFail()) {
      // create stage here
      InsertProcessStageThread thread = new InsertProcessStageThread(clone, model, Utils.getLong(res.getMap(), "PO_ID"));
      new Thread(thread).start();
    }
    return res;
  }

  @Override
  public ResponseMap editProcess(Map inParams) throws CloneNotSupportedException, SQLException {
    BaseDAO clone = this.getClone();
    String stageStr = (String) inParams.get("pi_stage");
    Gson gson = new Gson();
    ProcessStageModel model;
    try {
      model = gson.fromJson(stageStr, ProcessStageModel.class);
      if (model.getChildren() == null || model.getChildren().size() == 0) {
        return new ResponseMap(Utils.returnMap("308"));
      }
    } catch (Exception e) {
      return new ResponseMap(Utils.returnMap("307"));
    }
    inParams = Utils.trimMap(inParams, "pi_name", "pi_details");
    inParams = Utils.changeClob(inParams, "pi_details");
    ResponseMap res = new ResponseMap(clone.callProcedure(PK_PROCESS + "edit_process", inParams));
    if (!res.isFail()) {
      // create stage here
      InsertProcessStageThread thread = new InsertProcessStageThread(clone, model, Utils.getLong(inParams, "pi_id"));
      new Thread(thread).start();
    }
    return res;
  }

  @Override
  public Map getChildStageByParent(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PROCESS + "get_child_stage_by_parent", inParams);
  }
}

@Slf4j
class InsertProcessStageThread implements Runnable {
  BaseDAO dao;
  ProcessStageModel model;
  long processID;

  public InsertProcessStageThread(BaseDAO dao, ProcessStageModel model, long processID) {
    try {
      this.dao = dao.getClone();
      this.processID = processID;
      this.model = model;
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {
    List listAdd = new ArrayList<>();
    createRawData(listAdd, model, null, 0);
    String sql = "insert into tb_process_stage (N_ID, S_NAME, S_DESC, N_PROCESS_ID, N_PARENT_ID, S_STEP, S_UID, S_PARENT_UID) " +
        "values(sq_process_stage.nextval, ?, ?, ?, null, ?, ?, ?)";
    try {
      dao.executeUpdateBatch(sql, listAdd);
      Map in = new HashMap();
      in.put("pi_process_id", this.processID);
      dao.callProcedure("PK_PROCESS.refix_stage", in);
    } catch (SQLException e) {
      log.error("Error when adding stage for id: " + this.processID);
      log.error("Caused by: " + e.getMessage());
    }
  }

  void createRawData(List list, ProcessStageModel child, ProcessStageModel parent, int index) {
    child.setUniqueID(Utils.genUUID());
    child.setProcessID(this.processID);
    if (parent == null) {
      child.setStep("");
    } else {
      child.setStep(parent.getStep() + index + ".");
      child.setParentUniqueID(parent.getUniqueID());
      Object[] objectAdd = new Object[6];
      objectAdd[0] = child.getName();
      objectAdd[1] = child.getDesc();
      objectAdd[2] = this.processID;
      objectAdd[3] = child.getStep();
      objectAdd[4] = child.getUniqueID();
      objectAdd[5] = child.getParentUniqueID();
      list.add(objectAdd);
    }
    if (child.getChildren() != null)
      for (int i = 0; i < child.getChildren().size(); i++) {
        ProcessStageModel m = child.getChildren().get(i);
        createRawData(list, m, child, i + 1);
      }
  }
}
