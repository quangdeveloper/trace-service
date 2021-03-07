package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface HrmUnitDAO {

  Map findAll(Map map) throws SQLException, CloneNotSupportedException;

  Map findById(Map map) throws SQLException, CloneNotSupportedException;

  Map edit(Map map) throws SQLException, CloneNotSupportedException;

  Map remove(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbGetAllUnits(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbGetUnitByArea(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbGetUnitByUser(Map map) throws SQLException, CloneNotSupportedException;

  Map findUnitAnnounce(Map map) throws SQLException, CloneNotSupportedException;

  Map editUnitAnnounce(Map map) throws SQLException, CloneNotSupportedException;
}
