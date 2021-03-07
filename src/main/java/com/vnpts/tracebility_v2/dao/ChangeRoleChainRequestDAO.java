package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ChangeRoleChainRequestDAO {

  Map getCurrentChainRoleByUser(Map map) throws SQLException, CloneNotSupportedException;

  Map sendRequest(Map map) throws SQLException, CloneNotSupportedException;

  Map getList(Map map) throws SQLException, CloneNotSupportedException;

  Map getListByUser(Map map) throws SQLException, CloneNotSupportedException;

  Map getDetail(Map map) throws SQLException, CloneNotSupportedException;

  Map approveRequest(Map map) throws SQLException, CloneNotSupportedException;
}
