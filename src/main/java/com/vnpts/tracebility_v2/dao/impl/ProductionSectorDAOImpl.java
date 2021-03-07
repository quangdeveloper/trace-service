package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ProductionSectorDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class ProductionSectorDAOImpl extends BaseDAO implements ProductionSectorDAO {
  @Override
  public Map createSector(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_name", "pi_performName");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy","pi_plannedStartDate", "pi_plannedHarvestDate");
    return this.getClone().callProcedure(PK_PRODUCTION_SECTOR + "create_sector", inParams);
  }

  @Override
  public Map editSector(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_name", "pi_performName");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy","pi_plannedStartDate", "pi_plannedHarvestDate");
    return this.getClone().callProcedure(PK_PRODUCTION_SECTOR + "edit_sector", inParams);
  }

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    inParams = Utils.noSignMap(inParams, "pi_keyword");
    inParams.putIfAbsent("pi_owner", "");
    inParams.putIfAbsent("pi_productID", -1);
    return this.getClone().callFunction(PK_PRODUCTION_SECTOR + "get_list", inParams);
  }

  @Override
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCTION_SECTOR + "get_by_id", inParams);
  }

  @Override
  public Map cbbNotDone(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams.putIfAbsent("pi_pp_id", 0);
    return this.getClone().callFunction(PK_PRODUCTION_SECTOR + "cbb_not_done", inParams);
  }

  @Override
  public Map getCbb(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCTION_SECTOR + "cbb_sector", inParams);
  }
}
