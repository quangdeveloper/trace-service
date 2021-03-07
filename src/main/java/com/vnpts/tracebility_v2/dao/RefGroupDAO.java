package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface RefGroupDAO {
  public Map getList(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map createRefGroup(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map editRefGroup(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map deleteRefGroup(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map getCBB(Map inParam) throws CloneNotSupportedException, SQLException;
}
