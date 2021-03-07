package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.MenuDAO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MenuDAOImpl extends BaseDAO implements MenuDAO {


    @Override
    public Map getPermissionAndMenuPath(Integer userId) throws SQLException {
        userId = 1;
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_user_id", userId);
        return callProcedure(PK_MENU + "getPermissionAndMenuPath", inParams);
    }

    @Override
    public Map getMenu(String paths) throws SQLException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_path_list", paths);
        return callFunction(PK_MENU + "getMenuByUser", inParams);
    }

    @Override
    public Map searchMenu(Map inParams) throws SQLException {
        return callFunction(PK_MENU + "searchMenu", inParams);
    }

    @Override
    public Map getAllMenu(Map inParams) throws SQLException {
        return callFunction(PK_MENU + "getAllMenu", inParams);
    }

    @Override
    public Map insertMenu(Map inParams) throws SQLException {
        return callProcedure(PK_MENU + "insertMenu", inParams);
    }

    @Override
    public Map getMenuById(Map inParams) throws SQLException {
        return callFunction(PK_MENU + "getMenuById", inParams);
    }

    @Override
    public Map updateMenu(Map inParams) throws SQLException {
        return callProcedure(PK_MENU + "updateMenu", inParams);
    }
}
