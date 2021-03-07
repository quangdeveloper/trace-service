package com.vnpts.tracebility_v2.dao.impl;

import com.google.gson.Gson;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.EmailDAO;
import com.vnpts.tracebility_v2.model.EmailModel;
import com.vnpts.tracebility_v2.util.Constants;
import com.vnpts.tracebility_v2.util.TotalConverter;
import com.vnpts.tracebility_v2.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class EmailDaoImpl extends BaseDAO implements EmailDAO {
  @Autowired
  private GsonResponse gsonResponse;
  @Override
  public Map getListEmailSending() throws CloneNotSupportedException, SQLException {
    Map inParam = new HashMap();
    inParam.put("pi_size", Constants.SIZE_EMAIL);
    inParam.put("pi_max_err", Constants.MAX_ERR);
    return this.getClone().callFunction(PK_EMAIL_HANDLE + "get_email_sending", inParam, (resultSet, i) -> {
      EmailModel model = new EmailModel();
      model.setId(resultSet.getInt("N_ID"));
      model.setTemplateName(resultSet.getString("S_TEMPLATE"));
      model.setSubject(resultSet.getString("S_TITLE"));
      model.setReceiver(resultSet.getString("S_RECEIVER"));
      model.setCc(resultSet.getString("S_CC"));
      model.setBcc(resultSet.getString("S_BCC"));
      model.setFrom(resultSet.getString("S_FROM"));
      model.setParams(gsonResponse.fromJson(TotalConverter.toString(resultSet.getClob("C_PARAMS"))));
      return model;
    });
  }

  @Override
  public void updateSuccess(long id) throws CloneNotSupportedException, SQLException {
    this.getClone().executeUpdate("update tb_email set n_status = 0 where n_id = " + id);
  }

  @Override
  public void updateFail(long id) {
    try {
      this.getClone().executeUpdate("update tb_email set n_status = 1, n_count = n_count + 1 where n_id = " + id);
    } catch (SQLException | CloneNotSupportedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void createMail(String title, String template, String from, String rec, String cc, String bcc, int priority, Map params) throws CloneNotSupportedException, SQLException {
    Gson gson = new Gson();
    Map inParams = new HashMap();
    inParams.put("pi_title", title);
    inParams.put("pi_template", template);
    inParams.put("pi_from", from);
    inParams.put("pi_rec", rec);
    inParams.put("pi_cc", cc);
    inParams.put("pi_bcc", bcc);
    inParams.put("pi_prioty", priority);
    inParams.put("pi_params", gson.toJson(params));
    inParams = Utils.changeClob(inParams, "pi_params");
    this.getClone().callProcedure(PK_EMAIL_HANDLE + "create_email", inParams);
  }

  @Override
  public void createActiveMail(String rec, String activeKey, String userID, String userName) {
    try {
      Map param = new HashMap();
      param.put("token", activeKey);
      param.put("userId", userID);
      param.put("username", userName);
      param.put("serverAddress", Constants.SERVER_ADDRESS);
      createMail("Xác nhận đăng ký tài khoản", "activateAccount.ftl", "no-reply.truyxuatcb@gmail.com", rec, null, null, 10, param);
    } catch (CloneNotSupportedException | SQLException e) {
      log.error("Can not create email: " + e.getMessage());
    }
  }
}
