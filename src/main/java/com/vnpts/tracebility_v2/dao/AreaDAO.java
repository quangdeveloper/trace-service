package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface AreaDAO {

  Map cbbGetAllProvinces() throws SQLException, CloneNotSupportedException;

  Map cbbGetProvincesChild(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbGetProvincesByUserType(Map map) throws SQLException, CloneNotSupportedException;

  Map cbbGetAllChild(Map in) throws CloneNotSupportedException, SQLException;

}
