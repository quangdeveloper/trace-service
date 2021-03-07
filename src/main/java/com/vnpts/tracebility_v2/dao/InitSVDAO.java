package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface InitSVDAO {
  public List<Map> getAllPartner() throws SQLException;
  public List<Map> getRoleToCache(int userID) throws SQLException;
  public List<Map> getFunctionRegex(int userID) throws SQLException;
}
