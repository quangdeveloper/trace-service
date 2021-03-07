package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ParameterDAO {
    public String getParameter(String paramName) throws SQLException, CloneNotSupportedException;
    public String getFileUploadId() throws SQLException, CloneNotSupportedException;
    public Map searchParameter(Map inParams) throws SQLException, CloneNotSupportedException;
    public Map updateParameter(Map inParams) throws SQLException, CloneNotSupportedException;
}
