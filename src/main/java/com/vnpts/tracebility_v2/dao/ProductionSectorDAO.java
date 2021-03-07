package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ProductionSectorDAO {
  public Map createSector(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map editSector(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map cbbNotDone(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getCbb(Map inParams) throws CloneNotSupportedException, SQLException;
}
