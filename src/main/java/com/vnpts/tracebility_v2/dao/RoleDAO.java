package com.vnpts.tracebility_v2.dao;

import com.vnpts.tracebility_v2.model.RoleModel;

import java.sql.SQLException;
import java.util.Map;

public interface RoleDAO {
  public RoleModel getAllRole() throws CloneNotSupportedException, SQLException;

  public Map getTypeAdd() throws CloneNotSupportedException, SQLException;

  public Map getListUserType(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map getListGroupRole(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map getCbbGroup(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map createType(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map createGroupRole(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map editGroupRole(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map editType(Map inParam) throws CloneNotSupportedException, SQLException;

  public Map getTypeAreaInfoByID(Map inParam) throws CloneNotSupportedException, SQLException;

  public RoleModel getRoleByArea(Map inParam) throws CloneNotSupportedException, SQLException;
}
