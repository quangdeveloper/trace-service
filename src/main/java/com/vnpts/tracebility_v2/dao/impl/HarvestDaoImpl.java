package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.HarvestDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class HarvestDaoImpl extends BaseDAO implements HarvestDAO {
  @Override
  public Map newHarvest(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_name");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_harvestDate");
    return this.getClone().callProcedure(PK_HARVEST + "new_harvest", inParams);
  }

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
    inParams = Utils.trimMap(inParams, "pi_keyword");
    inParams = Utils.lowerMap(inParams, "pi_keyword");
    inParams = Utils.noSignMap(inParams, "pi_keyword");
    return this.getClone().callFunction(PK_HARVEST + "get_list", inParams);
  }

  @Override
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_HARVEST + "get_by_id", inParams);
  }

  @Override
  public Map cbbHarvestMaterial(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_HARVEST + "get_harvest_material_cbb", inParams);
  }

  @Override
  public Map cbbHarvestEndProduct(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_HARVEST + "get_harvest_end_product_cbb", inParams);
  }

  @Override
  public Map editHarvest(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_name");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_harvestDate");
    return this.getClone().callProcedure(PK_HARVEST + "edit_harvest", inParams);
  }

  @Override
  public Map deleteHarvest(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_HARVEST + "delete_harvest", inParams);
  }
}
