package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ReflectionDAO {
  public Map getStatusFilter(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map createReflection(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getHistory(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getFileHis(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getFileHisPublic(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map doAction(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map findById(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map myRef(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map publicRef(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map refInfo(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map subOrUnsubRef(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map subscribeRefList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map reportHandlingList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map reportDoneList(Map inParams) throws CloneNotSupportedException, SQLException;
}
