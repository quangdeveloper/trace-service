package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.VerifyDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class VerifyDaoImpl extends BaseDAO implements VerifyDAO {
  @Autowired
  private CheckService checkService;

  @Override
  public Map verifySectorStage(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.toDateMap(map, "dd/MM/yyyy", "pi_actionDate");
    map = Utils.changeClob(map, "pi_desc");
    Map out = this.getClone().callProcedure(PK_VERIFY + "verify_sector_stage", map);
    if (out.get("PO_UPLOAD_KEY") != null) {
      checkService.setKeyUpload((String) out.get("PO_UPLOAD_KEY"));
    }
    return out;
  }

  @Override
  public Map getListVerifySector(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_VERIFY + "list_verify_sector", map);
  }

  @Override
  public Map getVerifyStageInfo(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(PK_VERIFY + "get_verify_stage_info", map);
  }

  @Override
  public Map moreInfoForVerifyStage(Map map) throws SQLException, CloneNotSupportedException {
    return this.getClone().callProcedure(PK_VERIFY + "more_info_1", map);
  }

  @Override
  public Map verifyPrintCommand(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.trimMap(map, "pi_note");
    map = Utils.changeClob(map, "pi_note");
    return this.getClone().callProcedure(PK_VERIFY + "verify_print_command", map);
  }

  @Override
  public Map getFileVerify(long id, long verifyID) throws SQLException, CloneNotSupportedException {
    Map map = new HashMap();
    map.put("pi_id", id);
    map.put("pi_verifyID", verifyID);
    return this.getClone().callProcedure(PK_VERIFY + "get_img_verify_sector_stage", map);
  }
}
