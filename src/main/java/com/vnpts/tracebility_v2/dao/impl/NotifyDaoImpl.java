package com.vnpts.tracebility_v2.dao.impl;

import com.google.gson.Gson;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.NotifyDAO;
import com.vnpts.tracebility_v2.util.Constants;
import org.jdbc.lob.PassThroughClob;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class NotifyDaoImpl extends BaseDAO implements NotifyDAO {
  @Override
  public Map getList(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction(PK_NOF + "list_notify_user", inParams);
  }

  @Override
  public Map markRead(Map inParams) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure(PK_NOF + "mark_read", inParams);
  }

  @Override
  public Map getListNotifSend() throws CloneNotSupportedException, SQLException {
    Map inParam = new HashMap();
    inParam.put("pi_size", Constants.SIZE_NOTIFY);
    return this.getClone().callFunction(PK_NOF + "get_notif_sending", inParam);
  }

  @Override
  public void updateSending(String ids, String dates) throws CloneNotSupportedException, SQLException {
    Map inParam = new HashMap();
    inParam.put("pi_ids", ids);
    inParam.put("pi_dates", dates);
    this.getClone().callProcedure(PK_NOF + "update_sending", inParam);
  }

  @Override
  public void createNotify(Integer user, String userID, String userType, String title, String body, Map data, int type) {
    Gson gson = new Gson();
    Map in = new HashMap();
    in.put("pi_user_id", user);
    in.put("pi_user", userID);
    in.put("pi_type_user", userType);
    in.put("pi_title", title);
    in.put("pi_body", body);
    in.put("pi_data", new PassThroughClob(gson.toJson(data)));
    in.put("pi_type_nof", type);
    CreateNofThread t = new CreateNofThread(in, this);
    new Thread(t).start();
  }

  class CreateNofThread implements Runnable {
    Map inParams;
    BaseDAO dao;

    public CreateNofThread(Map inParams, BaseDAO dao) {
      this.inParams = inParams;
      this.dao = dao;
    }

    @Override
    public void run() {
      try {
        this.dao.getClone().callProcedure(PK_NOF + "create_nof", inParams);
      } catch (SQLException | CloneNotSupportedException e) {
        e.printStackTrace();
      }
    }
  }
}
