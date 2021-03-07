package com.vnpts.tracebility_v2.dao.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ProductionPlanDAO;
import com.vnpts.tracebility_v2.model.PPMembersModel;
import com.vnpts.tracebility_v2.model.PPUserModel;
import com.vnpts.tracebility_v2.model.ProcessStageModel;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

@Component
public class ProductionPlanDaoImpl extends BaseDAO implements ProductionPlanDAO {
  @Override
  public Object createMember(Map inParams) throws CloneNotSupportedException, SQLException {
    String members = (String) inParams.get("pi_members");
    Gson gson = new Gson();
    List<PPUserModel> users = gson.fromJson(members, new TypeToken<List<PPUserModel>>() {
    }.getType());
    BaseDAO cl = this.getClone();
    List<ResponseMap> listResponse = new ArrayList<>();
    for (PPUserModel userModel : users) {
      Map newMap = new HashMap();
      newMap.put("pi_user", inParams.get("pi_user"));
      newMap.put("pi_user_id_number", inParams.get("pi_user_id_number"));
      newMap.put("pi_typeUser", inParams.get("pi_typeUser"));
      newMap.put("pi_userID", userModel.getUserID().trim().toLowerCase());
      newMap.put("pi_userName", userModel.getUserName().trim());
      newMap.put("pi_address", userModel.getAddress().trim());
      newMap.put("pi_phone", userModel.getPhone().trim());
      newMap.put("pi_email", userModel.getEmail().trim().toLowerCase());
      newMap.put("pi_provinceID", userModel.getProvince());
      newMap.put("pi_districtID", userModel.getDistrict());
      newMap.put("pi_wardID", userModel.getWard());
      StringBuffer buffer = new StringBuffer();
      for (int x = 0; x < userModel.getBusinessType().length - 1; x++) {
        buffer.append(userModel.getBusinessType()[x]).append("|");
      }
      if (userModel.getBusinessType().length > 0)
        buffer.append(userModel.getBusinessType()[userModel.getBusinessType().length - 1]);
      newMap.put("pi_businessType", buffer.toString());
      Map out = cl.callProcedure(PK_PRODUCTION_PLAN + "create_member", newMap);
      out.put("STT", userModel.getStt());
      listResponse.add(new ResponseMap(out));
    }
    return listResponse;
  }

  @Override
  public Map findMembersInvite(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.changeArr(inParams, false, false, "|", "pi_businessType");
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "find_members_to_invite", inParams);
  }

  @Override
  public Map insertPlan(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = makeInputToDB(inParams);
    if (!"200".equals(inParams.get("PO_CODE"))) {
      return Utils.returnMap((String) inParams.get("PO_CODE"));
    }
    System.out.println(inParams);
    return this.getClone().callProcedure(PK_PRODUCTION_PLAN + "insert_plan", inParams);
  }

  @Override
  public Map editPlan(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = makeInputToDB(inParams);
    if (!"200".equals(inParams.get("PO_CODE"))) {
      return Utils.returnMap((String) inParams.get("PO_CODE"));
    }
    System.out.println(inParams);
    return this.getClone().callProcedure(PK_PRODUCTION_PLAN + "edit_plan", inParams);
  }

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    inParams = Utils.noSignMap(inParams, "pi_keyword");
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "get_list", inParams);
  }

  @Override
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "get_by_id", inParams);
  }

  @Override
  public Map deletePlan(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_PRODUCTION_PLAN + "delete_plan", inParams);
  }

  @Override
  public Map cbbPlanJoined(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams.putIfAbsent("pi_owner", "");
    inParams.putIfAbsent("pi_productID", -1);
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "list_plan_active", inParams);
  }

  @Override
  public Map listPlanJoined(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams.putIfAbsent("pi_owner", "");
    inParams.putIfAbsent("pi_keyword", "");
    inParams.putIfAbsent("pi_productID", -1);
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    inParams = Utils.trimMap(inParams, "pi_keyword");
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "list_plan_active_page", inParams);
  }

  @Override
  public Map listPlanRequesting(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    inParams = Utils.noSignMap(inParams, "pi_keyword");
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "get_list_invited", inParams);
  }

  @Override
  public Map infoRequesting(Map inParams) throws CloneNotSupportedException, SQLException {
    Map out = this.getClone().callProcedure(PK_PRODUCTION_PLAN + "get_info_invite", inParams);
    List<Map> stages = (List<Map>) out.get("PO_PROCESS_STAGE");
    if (stages.size() == 0) return out;
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
    out.remove("PO_PROCESS_STAGE");
    out.put("PO_STAGES", root);
    return out;
  }

  @Override
  public Map acceptInvite(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_PRODUCTION_PLAN + "accept_invite", inParams);
  }

  Map makeInputToDB(Map inParams) {
    inParams = Utils.trimMap(inParams, "pi_owner", "pi_name");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_startDate", "pi_expectedEndDate");
    String members = (String) inParams.get("pi_members");
    Gson gson = new Gson();
    List<PPMembersModel> users = gson.fromJson(members, new TypeToken<List<PPMembersModel>>() {
    }.getType());
    int size = users.size();
    HashSet<Object> seen = new HashSet<>();
    users.removeIf(e -> !seen.add(e.getIdUser()));
    if (size != users.size()) {
      return Utils.returnMap("311");
    }
    StringBuffer s = new StringBuffer();
    for (int i = 0; i < size - 1; i++)
      s.append(users.get(i).toDataIn()).append("O");
    if (size > 0) {
      s.append(users.get(size - 1).toDataIn());
    }
    inParams.put("pi_members", s.toString());
    inParams.put("PO_CODE", "200");
    return inParams;
  }
}
