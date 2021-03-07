package com.vnpts.tracebility_v2.dao.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ReflectionDAO;
import com.vnpts.tracebility_v2.util.Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ReflectionDaoImpl extends BaseDAO implements ReflectionDAO {
  @Autowired
  private CheckService checkService;

  @Override
  public Map getStatusFilter(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_REFLECTION + "get_status_filter", inParams);
  }

  @Override
  public Map createReflection(Map inParams) throws CloneNotSupportedException, SQLException {
    Map addInfo = new HashMap();
    addInfo.put("lat", inParams.get("pi_lat"));
    addInfo.put("long", inParams.get("pi_long"));
    addInfo.put("title", inParams.get("pi_title"));
    addInfo.put("address", inParams.get("pi_address"));
    addInfo.put("refType", inParams.get("pi_refType"));
    Gson gson = new Gson();
    inParams.put("pi_add_info", gson.toJson(addInfo));
    inParams = Utils.changeClob(inParams, "pi_add_info");
    Map out = this.getClone().callProcedure(PK_REFLECTION + "create_reflection", inParams);
    if (out.get("PO_UPLOAD_KEY") != null) checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    return out;
  }

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
    return this.getClone().callFunction(PK_REFLECTION + "get_list", inParams);
  }

  @Override
  public Map getHistory(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_REFLECTION + "get_history_info", inParams);
  }

  @Override
  public Map getFileHis(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_REFLECTION + "get_file_his", inParams);
  }

  @Override
  public Map getFileHisPublic(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_REFLECTION + "get_file_his_public", inParams);
  }

  @Override
  public Map doAction(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.fillDefault(inParams, null, "pi_content", "pi_dueDate");
    inParams = Utils.fillDefault(inParams, 0, "pi_isEmail",
        "pi_isSMS", "pi_isEmailCreator", "pi_isSMSCreator", "pi_unitID", "pi_hasFile");
    Map addInfo = new HashMap();
    addInfo.put("emailCreator", inParams.get("pi_isEmailCreator"));
    addInfo.put("smsCreator", inParams.get("pi_isSMSCreator"));
    Gson gson = new Gson();
    inParams.put("pi_add_info", gson.toJson(addInfo));
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_dueDate");
    inParams = Utils.changeClob(inParams, "pi_add_info");
    Map out = this.getClone().callProcedure(PK_REFLECTION + "do_action", inParams);
    if (out.get("PO_UPLOAD_KEY") != null) checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    return out;
  }

  @Override
  public Map findById(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_REFLECTION + "get_by_id", inParams);
  }

  @Override
  public Map myRef(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_REFLECTION + "my_ref", inParams);
  }

  @Override
  public Map publicRef(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_REFLECTION + "public_ref", inParams);
  }

  @Override
  public Map refInfo(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_REFLECTION + "details_for_app", inParams);
  }

  @Override
  public Map subOrUnsubRef(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_REFLECTION + "sub_or_unsub", inParams);
  }

  @Override
  public Map subscribeRefList(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_REFLECTION + "sub_ref_list", inParams);
  }

  @Override
  public Map reportHandlingList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
    return this.getClone().callFunction(PK_REFLECTION_REPORT + "get_report_handling", inParams);
  }

  @Override
  public Map reportDoneList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
    return this.getClone().callFunction(PK_REFLECTION_REPORT + "get_report_done", inParams);
  }

}
