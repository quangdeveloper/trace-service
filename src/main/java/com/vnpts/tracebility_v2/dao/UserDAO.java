package com.vnpts.tracebility_v2.dao;


import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;

import java.sql.SQLException;
import java.util.Map;

public interface UserDAO {

    public Map login(Map inParams) throws SQLException, CloneNotSupportedException;

    public Map resetPassword(Map inParams) throws SQLException, CloneNotSupportedException;

    public Map getSelfProfile(String userID) throws SQLException, CloneNotSupportedException;

    public Map changeInfo(Map map) throws SQLException, CloneNotSupportedException;

    public Map changePass(Map inParams) throws SQLException, CloneNotSupportedException;

    public Map changeAvatar(String userID, String location) throws SQLException, CloneNotSupportedException;

    public ResponseMap register(Map inParams) throws SQLException, CloneNotSupportedException;

    public Map activateAccount(Map inParams) throws SQLException, CloneNotSupportedException;

    public void setRole(ResponseSession session) throws SQLException, CloneNotSupportedException;

    public Map firebaseToken(Map inParams) throws SQLException, CloneNotSupportedException;

    public Map expToken(Map inParams) throws SQLException, CloneNotSupportedException;
}
