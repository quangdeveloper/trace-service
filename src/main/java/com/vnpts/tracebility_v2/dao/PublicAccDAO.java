package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface PublicAccDAO {

  Map edit(Map map) throws SQLException, CloneNotSupportedException;

  Map activateAccount(Map map) throws SQLException, CloneNotSupportedException;

  Map login(Map map) throws SQLException, CloneNotSupportedException;
}
