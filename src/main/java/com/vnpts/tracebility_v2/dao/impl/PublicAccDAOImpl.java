package com.vnpts.tracebility_v2.dao.impl;

import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.PublicAccDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class PublicAccDAOImpl extends BaseDAO implements PublicAccDAO {

  @Override
  public Map edit(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_n_id", 0);
    inParams.put("pi_s_user_id", map.get("userId"));
    inParams.put("pi_n_sex", map.get("sex"));
    inParams.put("pi_s_password", map.get("password"));
    inParams.put("pi_s_re_pass", map.get("confirmPassword"));
    inParams.put("pi_s_full_name", map.get("fullName"));
    inParams.put("pi_s_email", map.get("email"));
    inParams.put("pi_s_phone", map.get("phone"));
    inParams.put("pi_s_area_id", map.get("areaId"));
    inParams.put("pi_s_status", map.get("status"));
    inParams.put("pi_s_address", map.get("address"));
    inParams.put("pi_s_user_act", map.get(USER_ACT));
    return this.getClone().callProcedure(this.PK_ACC_PUBLIC + "edit_user_public", inParams);
  }

  @Override
  public Map activateAccount(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_token", map.get("token"));
    inParams.put("pi_user_id", map.get("userId"));
    return getClone().callProcedure(PK_ACC_PUBLIC + "activate_user_public", inParams);
  }

  @Override
  public Map login(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_user_id", map.get("username"));
    inParams.put("pi_pass", map.get("password"));
    return getClone().callProcedure(PK_ACC_PUBLIC + "login", inParams);
  }

}
