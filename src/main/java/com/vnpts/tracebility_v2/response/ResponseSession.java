package com.vnpts.tracebility_v2.response;


import com.vnpts.tracebility_v2.model.MenuItem;
import com.vnpts.tracebility_v2.response.user.ResponseLogin;

import java.util.List;

/**
 * Created by VNT on 6/15/2017.
 */
public class ResponseSession extends ResponseLogin {
  private String userID;

  private Integer id;

  private String userType;

  private String userRole;

  private String avatar;

  List<MenuItem> menuItems; // cannot get menu from model attribute

  public List<MenuItem> getMenuItems() {
    return menuItems;
  }

  public void setMenuItems(List<MenuItem> menuItems) {
    this.menuItems = menuItems;
  }

  public String getUserRole() {
    return userRole;
  }

  public void setUserRole(String userRole) {
    this.userRole = userRole;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Override
  public String toString() {
    return "ResponseSession{" +
        "userID='" + userID + '\'' +
        ", id=" + id +
        ", session='" + session + '\'' +
        ", code='" + code + '\'' +
        ", message='" + message + '\'' +
        '}';
  }

  public boolean hasRole(String path) {
    return true;
  }
}
