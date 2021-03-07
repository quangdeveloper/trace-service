package com.vnpts.tracebility_v2.dao;

import com.vnpts.tracebility_v2.model.RoleModel;

import java.sql.SQLException;
import java.util.Map;

public interface AccountDAO {

  Map getAdminList(Map inParams) throws SQLException, CloneNotSupportedException;

  Map resetPassByAdmin(Map inParams) throws SQLException, CloneNotSupportedException;

  Map getByID(Map inParam) throws CloneNotSupportedException, SQLException;

  Map editUser(Map inParams) throws SQLException, CloneNotSupportedException;

// created by Quang
  Map deleteUserById(Map inParams) throws SQLException, CloneNotSupportedException;

  RoleModel getAllRole() throws SQLException, CloneNotSupportedException;

  Map createUser(Map inParams) throws SQLException, CloneNotSupportedException;

  Map getCustomerListInPlan(Map map) throws SQLException, CloneNotSupportedException;
}
