package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.AnnounceTypeDAO;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import org.springframework.stereotype.Component;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AnnounceTypeDAOImpl extends BaseDAO implements AnnounceTypeDAO {

    @Override
    public Map findAll(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_s_keyword", map.get("keyword"));
        inParams.put("pi_n_status", map.get("status"));
        inParams.put("pi_is_adminVnpt", map.get("isAdminVnpt"));
        inParams.put("pi_province_id", map.get("provinceId"));
        inParams.put("pi_n_page_size", map.get("pageSize"));
        inParams.put("pi_n_page_num", map.get("pageNum"));
        inParams.put("pi_s_user_act", map.get(USER_ACT));
        return getClone().callFunction(this.PK_ANNOUNCE_TYPE + "find_announce_types", inParams);
    }

    @Override
    public Map edit(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_n_id", map.get("id"));
        inParams.put("pi_s_name", map.get("name"));
        inParams.put("pi_s_code", map.get("code"));
        inParams.put("pi_s_desc", map.get("desc"));
        inParams.put("pi_s_area_id", map.get("areaId"));
        inParams.put("pi_n_status", map.get("status"));
        inParams.put("pi_s_user_act", map.get(USER_ACT));
        return getClone().callProcedure(this.PK_ANNOUNCE_TYPE + "edit_announce_type", inParams);
    }

    @Override
    public Map remove(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_n_id", map.get("id"));
        inParams.put("pi_s_user_act", map.get(USER_ACT));
        return getClone().callProcedure(this.PK_ANNOUNCE_TYPE + "remove_announce_type", inParams);
    }

    @Override
    public Map getAll(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_is_adminVnpt", map.get("isAdminVnpt"));
        inParams.put("pi_province_id", map.get("provinceId"));
        return getClone().callFunction(this.PK_ANNOUNCE_TYPE + "get_all_announce_types", inParams);
    }

    @Override
    public Map findById(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_n_id", map.get("id"));
        inParams.put("pi_s_user_act", map.get(USER_ACT));
        return getClone().callProcedure(this.PK_ANNOUNCE_TYPE + "find_by_id", inParams);
    }
}
