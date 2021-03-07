package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.CommonDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class CommonDAOImpl extends BaseDAO implements CommonDAO {
    @Override
    public Map checkKey(String key, String valuesFile, int fileNum) throws CloneNotSupportedException, SQLException {
        Map inP = new HashMap();
        inP.put("pi_key", key);
        inP.put("pi_val", valuesFile);
        inP.put("pi_num_file", fileNum);
        return this.getClone().callProcedure("PK_UPLOAD_HANDLE.handle_key", inP);
    }

    @Override
    public Map getAreaChild(Map in) throws CloneNotSupportedException, SQLException {
        in.put("pi_s_parent_id", in.get("areaID"));
        return this.getClone().callProcedure("PK_AREA.cbbGetProvincesChild", in);
    }

    @Override
    public Map getUnitCbb() throws CloneNotSupportedException, SQLException {
        String sql = "select N_ID, S_NAME from tb_unit order by n_num desc";
        Map out = Utils.returnMap("200");
        out.put("rs", this.getClone().executeQuery(sql));
        return out;
    }

    @Override
    public Map getUnitAreaCbb() throws CloneNotSupportedException, SQLException {
        String sql = "select N_ID, S_NAME from tb_unit_area order by n_num desc";
        Map out = Utils.returnMap("200");
        out.put("rs", this.getClone().executeQuery(sql));
        return out;
    }

    @Override
    public Map productInfo(Map mapParams) throws CloneNotSupportedException, SQLException {
        return this.getClone().callProcedure(PK_INFO_CODE + "product_info", mapParams);
    }

    @Override
    public Map memberInfo(Map mapParams) throws CloneNotSupportedException, SQLException {
        return this.getClone().callFunction(PK_INFO_CODE + "member_code", mapParams);
    }

    @Override
    public Map processInfo(Map mapParams) throws CloneNotSupportedException, SQLException {
        return this.getClone().callFunction(PK_INFO_CODE + "process_info", mapParams);
    }
}
