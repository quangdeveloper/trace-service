package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.AreaDAO;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AreaDAOImpl extends BaseDAO implements AreaDAO {

    @Override
    public Map cbbGetAllProvinces() throws SQLException, CloneNotSupportedException {
        return getClone().callProcedure(this.PK_AREA + "cbbGetAllProvinces", new HashMap<>());
    }

    @Override
    public Map cbbGetProvincesChild(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_s_parent_id", map.get("parentId"));
        return getClone().callProcedure(this.PK_AREA + "cbbGetProvincesChild", inParams);
    }

    @Override
    public Map cbbGetProvincesByUserType(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_s_user_type", map.get("userType"));
        if (map.get("prefixType") != null) {
            inParams.put("pi_prefix_type", map.get("prefixType"));
        } else {
            inParams.put("pi_prefix_type", map.get(""));
        }
        inParams.put("pi_is_adminVnpt", map.get("isAdminVnpt"));
        inParams.put("pi_province_id", map.get("provinceId"));
        return getClone().callProcedure(this.PK_AREA + "cbb_get_provinces_by_user_type", inParams);
    }

    @Override
    public Map cbbGetAllChild(Map in) throws CloneNotSupportedException, SQLException {
        return this.getClone().callFunction(this.PK_AREA + "cbb_get_all_child", in);
    }
}
