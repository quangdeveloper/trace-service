package com.vnpts.tracebility_v2.dao;

import com.vnpts.tracebility_v2.response.ResponseMap;

import java.sql.SQLException;
import java.util.Map;

public interface DiaryLogDAO {
  public ResponseMap logDiary(Map inParams) throws CloneNotSupportedException, SQLException;
  public ResponseMap editLogDiary(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map deleteLogDiary(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getDiaryStage(Map inParams) throws CloneNotSupportedException, SQLException;
  public Map getImg(long id, long diaryID) throws CloneNotSupportedException, SQLException;
  Map getByID(Map inParams) throws CloneNotSupportedException, SQLException;
  Map getCompanyForAdmin(Map inParams) throws CloneNotSupportedException, SQLException;
}
