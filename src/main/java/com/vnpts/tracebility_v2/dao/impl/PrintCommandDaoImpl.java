package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.PrintCommandDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class PrintCommandDaoImpl extends BaseDAO implements PrintCommandDAO {
  @Override
  public Map findAll(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.toDateMap(map, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
    return this.getClone().callFunction(PK_PRINT_COMMAND + "get_list", map);
  }

  @Override
  public Map findById(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "get_by_id", map);
  }

  @Override
  public Map create(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_PRINT_COMMAND + "create_command", map);
  }

  @Override
  public Map cbbProdPlanPrint(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "cbb_pp_print", map);
  }

  @Override
  public Map getProfileCommandMembers(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "profile_command_members", map);
  }

  @Override
  public Map getDiariesDetail(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "get_list_diary_details", map);
  }

  @Override
  public Map printTemp(Map map) throws SQLException, CloneNotSupportedException {
    map.putIfAbsent("pi_fromSerial", -1);
    map.putIfAbsent("pi_toSerial", -1);
    map = Utils.changeClob(map, "pi_printConfig");
    return this.getClone().callFunction(PK_PRINT_COMMAND + "print_temp", map);
  }

  @Override
  public Map sendCommand(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_PRINT_COMMAND + "send_command", map);
  }

  @Override
  public Map cancelCommand(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_PRINT_COMMAND + "cancel_command", map);
  }

  @Override
  public Map getDiariesDetailFromQrCode(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "get_list_diary_details_from_qrCode", map);
  }

  @Override
  public Map getAllByProductionPlan(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "get_all_by_production_plan", map);
  }
}
