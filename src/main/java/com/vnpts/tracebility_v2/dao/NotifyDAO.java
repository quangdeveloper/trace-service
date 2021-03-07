package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface NotifyDAO {
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map markRead(Map inParams) throws CloneNotSupportedException, SQLException;

  public Map getListNotifSend() throws CloneNotSupportedException, SQLException;
  public void updateSending(String ids, String dates) throws CloneNotSupportedException, SQLException;

  public void createNotify(Integer user, String userID, String userType, String title, String body, Map data, int type);
}
