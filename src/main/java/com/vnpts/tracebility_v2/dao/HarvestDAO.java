package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface HarvestDAO {
  public Map newHarvest(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map cbbHarvestMaterial(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map cbbHarvestEndProduct(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map editHarvest(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map deleteHarvest(Map inParams) throws CloneNotSupportedException, SQLException;
}
