package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface PrintCommandDAO {

  Map findAll(Map map) throws SQLException, CloneNotSupportedException;

  Map findById(Map map) throws SQLException, CloneNotSupportedException;

  Map create(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbProdPlanPrint(Map map) throws SQLException, CloneNotSupportedException;

  Map getProfileCommandMembers(Map map) throws SQLException, CloneNotSupportedException;

  Map getDiariesDetail(Map map) throws SQLException, CloneNotSupportedException;

  Map printTemp(Map map) throws SQLException, CloneNotSupportedException;

  Map sendCommand(Map map) throws SQLException, CloneNotSupportedException;

  Map cancelCommand(Map map) throws SQLException, CloneNotSupportedException;

  Map getDiariesDetailFromQrCode(Map map) throws SQLException, CloneNotSupportedException;

  Map getAllByProductionPlan(Map map) throws SQLException, CloneNotSupportedException;
}
