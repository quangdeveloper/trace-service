package com.vnpts.tracebility_v2.dao;

import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.response.ResponseMap;

import java.sql.SQLException;
import java.util.Map;

public interface RegisterCompanyDAO {
    public Map getInfoCompany(Map map) throws SQLException, CloneNotSupportedException;
    public Map check1(Map map) throws SQLException, CloneNotSupportedException;
    public Map check2(Map producer, Map packager, Map transporter, Map distributor) throws SQLException, CloneNotSupportedException;
    public ResponseMap saveRegister(Map product, Map process, Map producer, Map packager, Map transporter, Map distributor, Map stage, CheckService checkService) throws SQLException, CloneNotSupportedException;
}
