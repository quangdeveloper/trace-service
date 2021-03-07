package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface EmailDAO {
  Map getListEmailSending() throws CloneNotSupportedException, SQLException;

  void updateSuccess(long id) throws CloneNotSupportedException, SQLException;

  void updateFail(long id);

  void createMail(String title, String template, String from, String rec, String cc, String bcc, int priority, Map params) throws CloneNotSupportedException, SQLException;

  void createActiveMail(String rec, String activeKey, String userID, String userName);
}
