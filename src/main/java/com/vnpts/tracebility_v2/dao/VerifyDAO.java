package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface VerifyDAO {

  Map verifySectorStage(Map map) throws SQLException, CloneNotSupportedException;

  Map getListVerifySector(Map map) throws SQLException, CloneNotSupportedException;

  Map getVerifyStageInfo(Map map) throws SQLException, CloneNotSupportedException;

  Map moreInfoForVerifyStage(Map map) throws SQLException, CloneNotSupportedException;

  Map verifyPrintCommand(Map map) throws SQLException, CloneNotSupportedException;

  Map getFileVerify(long id, long verifyID) throws SQLException, CloneNotSupportedException;
}
