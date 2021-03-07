package com.vnpts.tracebility_v2.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.TrainingDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class TrainingDAOImpl extends BaseDAO implements TrainingDAO {
  @Autowired
  private CheckService checkService;

  @Override
  public Map findAccounts(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_s_keyword", map.get("keyword"));
    inParams.put("pi_d_count", map.get("dayNo"));
    inParams.put("pi_n_type", map.get("type"));
//    inParams.put("pi_n_page_size", map.get("pageSize"));
//    inParams.put("pi_n_page_num", map.get("pageNum"));
    inParams.put("pi_s_user_act", map.get(USER_ACT));
    return this.getClone().callFunction(this.PK_TRAINING + "find_accounts", inParams);
  }

  @Override
  public Map insertTrainingEmail(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_s_title", map.get("title"));
    inParams.put("pi_s_content", map.get("content"));
    inParams.put("pi_n_type", map.get("type"));
    inParams.put("pi_s_user_act", map.get(USER_ACT));
    Map out = this.getClone().callProcedure(this.PK_TRAINING + "insert_training_email", inParams);
    String uploadKey = (String) out.get("PO_UPLOAD_KEY");
    if (uploadKey != null) {
      checkService.setKeyUpload(uploadKey);
    }
    return out;
  }

  @Override
  public Map insertTrainEmailHis(Map map) throws SQLException, CloneNotSupportedException {
    String empIds = (String) map.get("empIds");
    System.out.println(empIds);
    if (empIds != null && empIds.length() > 0 && empIds.endsWith("|")) {
      empIds = empIds.substring(0, empIds.length() - 1);
    }
    System.out.println(empIds);
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_n_training_email", map.get("trainEmailId"));
    inParams.put("pi_n_emp_ids", empIds);
    inParams.put("pi_n_owner_ids", map.get("ownerIds"));
    inParams.put("pi_n_type", map.get("type"));
    inParams.put("pi_s_user_act", map.get(USER_ACT));
    return this.getClone().callProcedure(this.PK_TRAINING + "insert_train_email_his", inParams);
  }

  @Override
  public Map findSentTrainEmailHis(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_s_keyword", map.get("keyword"));
    inParams.put("pi_n_status", map.get("status"));
    inParams.put("pi_n_page_size", map.get("pageSize"));
    inParams.put("pi_n_page_num", map.get("pageNum"));
    inParams.put("pi_s_user_act", map.get(USER_ACT));
    return this.getClone().callFunction(this.PK_TRAINING + "find_sent_train_email_his", inParams);
  }

  @Override
  public Map findSentTrainEmailById(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_n_id", map.get("id"));
    inParams.put("p_n_type", map.get("type"));
    inParams.put("pi_s_user_act", map.get(USER_ACT));
    return this.getClone().callProcedure(this.PK_TRAINING + "find_sent_email_by_id", inParams);
  }

  @Override
  public Map findValidEmails() throws SQLException, CloneNotSupportedException {
    return this.getClone().callFunction(this.PK_TRAINING + "find_valid_emails", new HashMap<>());
  }

  @Override
  public Map updateSentStatus(int id, int status) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_n_id", id);
    inParams.put("pi_n_status", status);
//    inParams.put("pi_s_user_act", map.get(USER_ACT));
    return this.getClone().callProcedure(this.PK_TRAINING + "update_sent_status", inParams);
  }

  @Override
  public Map downloadAttachFile(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_n_file_id", map.get("fileId"));
    inParams.put("pi_n_train_id", map.get("trainId"));
    return this.getClone().callProcedure(this.PK_TRAINING + "download_attach_file", inParams);
  }
}
