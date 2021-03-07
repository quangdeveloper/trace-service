package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface RefTypeDAO {
  public Map getList(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map createRefType(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map editRefType(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map deleteRefType(Map inParam) throws CloneNotSupportedException, SQLException;
  public Map getCBB(Map inParam) throws CloneNotSupportedException, SQLException;
}
