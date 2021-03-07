package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface CommonDAO {
    public Map checkKey(String key, String valuesFile, int fileNum) throws CloneNotSupportedException, SQLException;
    public Map getAreaChild(Map in) throws CloneNotSupportedException, SQLException;
    public Map getUnitCbb() throws CloneNotSupportedException, SQLException;
    public Map getUnitAreaCbb() throws CloneNotSupportedException, SQLException;
    public Map productInfo(Map mapParams) throws CloneNotSupportedException, SQLException;
    public Map memberInfo(Map mapParams) throws CloneNotSupportedException, SQLException;
    public Map processInfo(Map mapParams) throws CloneNotSupportedException, SQLException;
}
