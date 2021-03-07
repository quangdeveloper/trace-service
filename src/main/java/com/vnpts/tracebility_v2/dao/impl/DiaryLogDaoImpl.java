package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.DiaryLogDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DiaryLogDaoImpl extends BaseDAO implements DiaryLogDAO {
  @Autowired
  private CheckService checkService;
  @Override
  public ResponseMap logDiary(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_content", "pi_note");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_actionDate");
    inParams = Utils.changeClob(inParams, "pi_note");
    Map out = this.getClone().callProcedure(PK_DIARY + "log_diary", inParams);
    ResponseMap re = new ResponseMap(out);
    if (!re.isFail()) {
      checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    }
    return re;
  }

  @Override
  public ResponseMap editLogDiary(Map inParams) throws CloneNotSupportedException, SQLException {
    inParams = Utils.trimMap(inParams, "pi_content", "pi_note");
    inParams = Utils.toDateMap(inParams, "dd/MM/yyyy", "pi_actionDate");
    inParams = Utils.changeClob(inParams, "pi_note");
    inParams = Utils.changeArr(inParams, true, true, "|", "pi_imgDel");
    Map out =  this.getClone().callProcedure(PK_DIARY + "edit_log_diary", inParams);
    ResponseMap re = new ResponseMap(out);
    if (!re.isFail()) {
      checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    }
    return re;
  }

  @Override
  public Map deleteLogDiary(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_DIARY + "delete_log_diary", inParams);
  }

  @Override
  public Map getDiaryStage(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_DIARY + "get_diary_stage", inParams);
  }

  @Override
  public Map getImg(long id, long diaryID) throws CloneNotSupportedException, SQLException {
    Map inParams = new HashMap();
    inParams.put("pi_id", id);
    inParams.put("pi_diary_id", diaryID);
    return this.getClone().callProcedure(PK_DIARY + "get_img_diary", inParams);
  }

  @Override
  public Map getByID(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_DIARY + "get_by_id", inParams);
  }

  @Override
  public Map getCompanyForAdmin(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_USER + "get_cbb_company", inParams);
  }
}
