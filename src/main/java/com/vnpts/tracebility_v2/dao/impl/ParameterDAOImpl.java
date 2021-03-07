package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParameterDAOImpl extends BaseDAO implements ParameterDAO {

    @Override
    public String getParameter(String paramName) throws SQLException, CloneNotSupportedException {
        String sql = "select * from TB_PARAMETER where S_PARAM_NAME='" + paramName + "'";
        List<Map> map = getClone().executeQuery(sql);
        if (map == null || map.size() == 0) return null;
        return (String) map.get(0).get("S_PARAM_VALUE");
    }

    @Override
    public String getFileUploadId() throws SQLException, CloneNotSupportedException {
        String sql = "select seq_upload_file.nextval from dual";
        int seq = getClone().queryForInt(sql);
        return String.valueOf(seq);
    }

    @Override
    public Map searchParameter(Map map) throws SQLException, CloneNotSupportedException {
        Map<String, Object> inParams = new HashMap<>();
        inParams.put("pi_keyword", map.get("keyword").toString().toLowerCase());
        inParams.put("pi_pageNum", map.get("currentPage"));
        inParams.put("pi_pageSize", map.get("recordPerPage"));
        return getClone().callFunction(PK_PARAMETER + "searchParameter", inParams);
    }

    @Override
    public Map updateParameter(Map inParams) throws SQLException, CloneNotSupportedException {
        return getClone().callProcedure(this.PK_PARAMETER + "updateParameter", inParams);
    }
}
