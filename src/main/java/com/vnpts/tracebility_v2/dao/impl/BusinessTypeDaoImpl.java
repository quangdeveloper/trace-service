package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.BusinessTypeDAO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;
@Component
public class BusinessTypeDaoImpl extends BaseDAO implements BusinessTypeDAO {
  @Override
  public Map findAll(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_BUSINESS_TYPE + "get_list", map);
  }

  @Override
  public Map findById(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_BUSINESS_TYPE + "find_by_id", map);
  }

  @Override
  public Map edit(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_BUSINESS_TYPE + "edit_type", map);
  }

  @Override
  public Map create(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_BUSINESS_TYPE + "create_type", map);
  }

  @Override
  public Map remove(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_BUSINESS_TYPE + "delete_type", map);
  }

  @Override
  public Map cbbGetAll(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_BUSINESS_TYPE + "get_cbb", map);
  }
  @Override
  public Map cbbGetAllCustom(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_BUSINESS_TYPE + "get_cbb_custom", map);
  }
}
