package com.vnpts.tracebility_v2.dao.impl;

import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.RoleDAO;
import com.vnpts.tracebility_v2.model.RoleModel;
import com.vnpts.tracebility_v2.util.Utils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleDaoImpl extends BaseDAO implements RoleDAO {
  @Override
  public RoleModel getAllRole() throws CloneNotSupportedException, SQLException {
    Map inParam = new HashMap();
    inParam.put("pi_area_id", null);
    inParam.put("pi_user_type", 0);
    Map out = this.getClone().callFunction(PK_ROLE + "get_all_role", inParam);
    if ("200".equals(out.get("PO_CODE"))) {
      List<Map> roleList = (List<Map>) out.get("rs");
      return new RoleModel(roleList);
    }
    return null;
  }

  @Override
  public Map getTypeAdd() throws CloneNotSupportedException, SQLException {
    List<Map> rs = this.getClone().executeQuery("select N_ID, S_NAME, S_CODE from tb_user_type where n_status = 1 and n_is_add_type = 1");
    Map out = new HashMap();
    out.put("rs", rs);
    out.put("PO_CODE", "200");
    return out;
  }

  @Override
  public Map getListUserType(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam.put("pi_keyword2", inParam.get("pi_keyword"));
    inParam = Utils.trimMap(inParam, "pi_keyword", "pi_keyword2");
    inParam = Utils.lowerMap(inParam, "pi_keyword");
    inParam = Utils.upperMap(inParam, "pi_keyword2");
    return this.getClone().callFunction(PK_USER_TYPE + "get_list", inParam);
  }

  @Override
  public Map getListGroupRole(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam.put("pi_keyword2", inParam.get("pi_keyword"));
    inParam = Utils.trimMap(inParam, "pi_keyword", "pi_keyword2");
    inParam = Utils.lowerMap(inParam, "pi_keyword");
    inParam = Utils.upperMap(inParam, "pi_keyword2");
    return this.getClone().callFunction(PK_ROLE + "get_list_group", inParam);
  }

  @Override
  public Map getCbbGroup(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_ROLE + "get_list_gr_cbb", inParam);
  }

  @Override
  public Map createType(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    inParam = Utils.changeArr(inParam, false, false, "|","pi_roles");
    return this.getClone().callProcedure(PK_USER_TYPE + "create_type", inParam);
  }

  @Override
  public Map createGroupRole(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    inParam = Utils.changeArr(inParam, false, false, "|","pi_roles");
    return this.getClone().callProcedure( PK_ROLE + "create_group_role", inParam);
  }

  @Override
  public Map editGroupRole(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    inParam = Utils.changeArr(inParam, false, false, "|","pi_roles");
    return this.getClone().callProcedure( PK_ROLE + "edit_group_role", inParam);
  }

  @Override
  public Map editType(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    inParam = Utils.changeArr(inParam, false, false, "|","pi_roles");
    return this.getClone().callProcedure(PK_USER_TYPE + "edit_type", inParam);
  }

  @Override
  public Map getTypeAreaInfoByID(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_USER_TYPE + "get_by_id", inParam);
  }

  @Override
  public RoleModel getRoleByArea(Map inParam) throws CloneNotSupportedException, SQLException {
    Map out =  this.getClone().callFunction(PK_ROLE + "get_role_area_gr", inParam);
    if ("200".equals(out.get("PO_CODE"))) {
      List<Map> roleList = (List<Map>) out.get("rs");
      return new RoleModel(roleList);
    }
    return null;
  }
}
