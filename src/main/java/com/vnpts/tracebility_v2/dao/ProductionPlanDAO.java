package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ProductionPlanDAO {
  public Object createMember(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map findMembersInvite(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map insertPlan(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map editPlan(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map deletePlan(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map cbbPlanJoined(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map listPlanJoined(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map listPlanRequesting(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map infoRequesting(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map acceptInvite(Map inParams) throws CloneNotSupportedException, SQLException;
}
