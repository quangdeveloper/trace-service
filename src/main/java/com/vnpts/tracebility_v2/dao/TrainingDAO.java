package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface TrainingDAO {
  Map findAccounts(Map map) throws SQLException, CloneNotSupportedException;

  Map insertTrainingEmail(Map map) throws SQLException, CloneNotSupportedException;

  Map insertTrainEmailHis(Map map) throws SQLException, CloneNotSupportedException;

  Map findSentTrainEmailHis(Map map) throws SQLException, CloneNotSupportedException;

  Map findSentTrainEmailById(Map map) throws SQLException, CloneNotSupportedException;

  Map findValidEmails() throws SQLException, CloneNotSupportedException;

  Map updateSentStatus(int id, int status) throws SQLException, CloneNotSupportedException;

  Map downloadAttachFile(Map map) throws SQLException, CloneNotSupportedException;
}
