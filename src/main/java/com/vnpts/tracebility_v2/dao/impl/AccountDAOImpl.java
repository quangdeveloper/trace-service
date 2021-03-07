package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.AccountDAO;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.model.RoleModel;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AccountDAOImpl extends BaseDAO implements AccountDAO {

  @Override
  public Map getAdminList(Map inParams) throws SQLException, CloneNotSupportedException {
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    inParams = Utils.changeArr(inParams, false, false, "|", "pi_businessType");
    return this.getClone().callFunction(PK_ACCOUNT + "get_admin_list", inParams);
  }

  @Override
  public Map resetPassByAdmin(Map inParams) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(this.PK_ACCOUNT + "reset_admin_pass", inParams);
  }

  @Override
  public Map getByID(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_ACCOUNT + "get_by_id", inParam);
  }

  @Override
  public Map editUser(Map inParams) throws SQLException, CloneNotSupportedException {
    inParams = Utils.trimMap(inParams, "pi_fullName", "pi_email", "pi_address");
    inParams = Utils.changeArr(inParams, false, false, "|","pi_roles");
    return this.getClone().callProcedure(this.PK_ACCOUNT + "edit_user", inParams);
  }

  @Override
  public Map deleteUserById(Map inParams) throws SQLException, CloneNotSupportedException {

    return this.getClone().callProcedure(this.PK_ACCOUNT + "delete_user_by_id", inParams);
  }

  @Override
  public RoleModel getAllRole() throws SQLException, CloneNotSupportedException {
    Map inParam = new HashMap();
    Map out = this.getClone().callFunction(PK_ROLE + "get_all_role", inParam);
    if ("200".equals(out.get("PO_CODE"))) {
      List<Map> roleList = (List<Map>) out.get("rs");
      return new RoleModel(roleList);
    }
    return null;
  }

  @Override
  public Map createUser(Map inParams) throws SQLException, CloneNotSupportedException {
    inParams = Utils.trimMap(inParams, "pi_fullName", "pi_email", "pi_address");
    inParams = Utils.lowerMap(inParams, "pi_userId");
    inParams = Utils.changeArr(inParams, false, false, "|","pi_roles");
    return this.getClone().callProcedure(this.PK_ACCOUNT + "create_user", inParams);
  }

  @Override
  public Map getCustomerListInPlan(Map inParams) throws SQLException, CloneNotSupportedException {
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    return this.getClone().callFunction(PK_ACCOUNT + "get_customer_list_in_plan", inParams);
  }
}
