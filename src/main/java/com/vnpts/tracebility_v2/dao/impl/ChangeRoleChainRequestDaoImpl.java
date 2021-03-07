package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ChangeRoleChainRequestDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class ChangeRoleChainRequestDaoImpl extends BaseDAO implements ChangeRoleChainRequestDAO {
  @Override
  public Map getCurrentChainRoleByUser(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_CHANGE_ROLE_CHAIN_REQUEST + "get_current_role_chain_by_user", map);
  }

  @Override
  public Map sendRequest(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.changeArr(map, false, false, "|", "pi_s_business_type");
    return this.getClone().callProcedure(PK_CHANGE_ROLE_CHAIN_REQUEST + "send_request", map);
  }

  @Override
  public Map getList(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_CHANGE_ROLE_CHAIN_REQUEST + "get_list", map);
  }

  @Override
  public Map getListByUser(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_CHANGE_ROLE_CHAIN_REQUEST + "get_list_by_user", map);
  }

  @Override
  public Map getDetail(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_CHANGE_ROLE_CHAIN_REQUEST + "get_detail", map);
  }

  @Override
  public Map approveRequest(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_CHANGE_ROLE_CHAIN_REQUEST + "approve_request", map);
  }
}
