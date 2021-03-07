package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.EmailDAO;
import com.vnpts.tracebility_v2.dao.UserDAO;
import com.vnpts.tracebility_v2.model.MenuItem;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDAOImpl extends BaseDAO implements UserDAO {
  @Autowired
  private CheckService checkService;
  @Autowired
  private EmailDAO emailDAO;

  @Override
  public Map login(Map inParams) throws SQLException, CloneNotSupportedException {
    return getClone().callProcedure(PK_USER + "login", inParams);
  }

  @Override
  public Map resetPassword(Map map) throws SQLException, CloneNotSupportedException {
    return getClone().callProcedure(PK_USER + "resetPassword", map);
  }

  @Override
  public Map getSelfProfile(String userID) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_user_id", userID);
    Map map = getClone().callProcedure(PK_USER + "get_user_profile", inParams);
    return map;
  }

  @Override
  public Map changeInfo(Map inParams) throws SQLException, CloneNotSupportedException {
    inParams = Utils.trimMap(inParams, "pi_name", "pi_info", "pi_address", "pi_phone", "pi_email");
    inParams = Utils.changeClob(inParams, "pi_info");
    return getClone().callProcedure(PK_USER + "change_info", inParams);
  }

  @Override
  public Map changePass(Map inParams) throws SQLException, CloneNotSupportedException {
    return getClone().callProcedure(this.PK_USER + "change_pass", inParams);
  }

  @Override
  public Map changeAvatar(String userID, String location) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_user_id", userID);
    inParams.put("pi_location", location);
    return getClone().callProcedure(PK_USER + "change_avatar", inParams);
  }

  @Override
  public ResponseMap register(Map map) throws SQLException, CloneNotSupportedException {
    map = Utils.trimMap(map, "pi_userID", "pi_name", "pi_address", "pi_phone", "pi_email", "pi_moreInfo");
    map = Utils.lowerMap(map, "pi_userID", "pi_address");
    map = Utils.changeArr(map, false, false, "|", "pi_types");
    map = Utils.changeClob(map, "pi_moreInfo");
    ResponseMap res = new ResponseMap(getClone().callProcedure(PK_USER + "register_user", map));
    if (!res.isFail()) {
      if (res.getMap().get("PO_UPLOAD_KEY") != null) {
        checkService.setKeyUpload((String) res.getMap().get("PO_UPLOAD_KEY"));
      }
      String activeKey = (String) res.getMap().get("PO_ACTIVE_KEY");
      res.getMap().remove("PO_ACTIVE_KEY");
      //create verify email
      emailDAO.createActiveMail((String) map.get("pi_email"), activeKey, (String) map.get("pi_userID"), (String) map.get("pi_name"));
    }
    return res;
  }

  @Override
  public Map activateAccount(Map map) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_token", map.get("token"));
    inParams.put("pi_user_id", map.get("userId"));
    return getClone().callProcedure(PK_USER + "activateAccount", inParams);
  }

  @Override
  public void setRole(ResponseSession session) throws SQLException, CloneNotSupportedException {
    Map<String, Object> inParams = new HashMap<>();
    inParams.put("pi_user_id", session.getId());
    Map out = this.getClone().callProcedure(PK_ROLE + "get_user_role", inParams);
    if (out.get("po_code").equals("200")) {
      session.setUserRole((String) out.get("po_role"));
//      session.setListServices((List<Map>) out.get("po_services"));
      List<Map> menu = (List<Map>) out.get("po_menu");
      MenuItem root = new MenuItem();
      MenuItem[] save = new MenuItem[5];
      save[0] = root;
      for (Map m : menu) {
        MenuItem item = new MenuItem(m);
        int level = Utils.getInt(m, "LEVEL");
        save[level - 1].addItem(item);
        save[level] = item;
      }
      session.setMenuItems(root.getChildren());
    }
  }

  @Override
  public Map firebaseToken(Map inParams) throws SQLException, CloneNotSupportedException {
    return getClone().callProcedure(PK_NOF + "create_token", inParams);
  }

  @Override
  public Map expToken(Map inParams) throws SQLException, CloneNotSupportedException {
    return getClone().callProcedure(PK_NOF + "exp_token", inParams);
  }
}
