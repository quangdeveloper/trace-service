package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.DeliveryNoteDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class DeliveryNoteDaoImpl extends BaseDAO implements DeliveryNoteDAO {

  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_DELIVERY_NOTE + "get_list", inParams);
  }

  @Override
  public Map createNote(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_info");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_deliDate");
    inParams = Utils.changeClob(inParams, "pi_info");
    return this.getClone().callProcedure(PK_DELIVERY_NOTE + "create_note", inParams);
  }

  @Override
  public Map editNote(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_info");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_deliDate");
    inParams = Utils.changeClob(inParams, "pi_info");
    return this.getClone().callProcedure(PK_DELIVERY_NOTE + "edit_note", inParams);
  }

  @Override
  public Map deleteNote(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_DELIVERY_NOTE + "delete_note", inParams);
  }

  @Override
  public Map getListTempPackage(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRINT_COMMAND + "get_command_approved_cbb", inParams);
  }

  @Override
  public Map getNextSendToList(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_PRODUCTION_PLAN + "get_members_cbb", inParams);
  }

  @Override
  public Map getDeliveryProfile(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_DELIVERY_NOTE + "get_delivery_profile", inParams);
  }
}
