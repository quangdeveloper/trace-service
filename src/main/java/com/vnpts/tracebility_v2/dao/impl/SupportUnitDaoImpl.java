package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.BusinessTypeDAO;
import com.vnpts.tracebility_v2.dao.SupportUnitDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class SupportUnitDaoImpl extends BaseDAO implements SupportUnitDAO {
  @Override
  public Map findAll(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_SUPPORT_UNIT + "get_list", map);
  }

  @Override
  public Map findById(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_SUPPORT_UNIT + "find_by_id", map);
  }

  @Override
  public Map edit(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.trimMap(map, "pi_description");
    map = Utils.changeClob(map, "pi_description");
    return this.getClone().callProcedure(PK_SUPPORT_UNIT + "edit_unit", map);
  }

  @Override
  public Map create(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.trimMap(map, "pi_description");
    map = Utils.changeClob(map, "pi_description");
    return this.getClone().callProcedure(PK_SUPPORT_UNIT + "create_unit", map);
  }

  @Override
  public Map remove(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_SUPPORT_UNIT + "delete_unit", map);
  }

  @Override
  public Map cbbGetAll(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_SUPPORT_UNIT + "get_cbb", map);
  }
}
