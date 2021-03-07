package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface MenuDAO {

    public Map getPermissionAndMenuPath(Integer userId) throws SQLException;

    public Map getMenu(String paths) throws SQLException;

    public Map searchMenu(Map inParams) throws SQLException;

    public Map getAllMenu(Map inParams) throws SQLException;

    public Map insertMenu(Map mapParams) throws SQLException;

    public Map getMenuById(Map inParams) throws SQLException;

    public Map updateMenu(Map inParams) throws SQLException;
}
