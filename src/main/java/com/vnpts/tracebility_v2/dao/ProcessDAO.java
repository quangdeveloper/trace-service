package com.vnpts.tracebility_v2.dao;

import com.vnpts.tracebility_v2.response.ResponseMap;

import java.sql.SQLException;
import java.util.Map;

public interface ProcessDAO {
  public Map cbbType() throws CloneNotSupportedException, SQLException;

  public Map cbbProductForEdit(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map cbbProductForEditCustom(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getCBB(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map publicOrNot(Map inParams) throws CloneNotSupportedException, SQLException;

  public ResponseMap createProcess(Map inParams) throws CloneNotSupportedException, SQLException;

  public ResponseMap editProcess(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getChildStageByParent(Map inParams) throws CloneNotSupportedException, SQLException;
}
