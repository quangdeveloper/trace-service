package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface DeliveryNoteDAO {
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map createNote(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map editNote(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map deleteNote(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getListTempPackage(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getNextSendToList(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getDeliveryProfile(Map inParams) throws CloneNotSupportedException, SQLException;
}
