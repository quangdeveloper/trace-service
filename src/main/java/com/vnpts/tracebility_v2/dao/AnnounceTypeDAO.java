package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface AnnounceTypeDAO {

  Map findAll(Map map) throws SQLException, CloneNotSupportedException;

  Map findById(Map map) throws SQLException, CloneNotSupportedException;

  Map edit(Map map) throws SQLException, CloneNotSupportedException;

  Map remove(Map map) throws SQLException, CloneNotSupportedException;

  Map getAll(Map map) throws SQLException, CloneNotSupportedException;
}
