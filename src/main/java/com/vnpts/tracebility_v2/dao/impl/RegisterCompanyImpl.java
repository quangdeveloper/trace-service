package com.vnpts.tracebility_v2.dao.impl;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.config.CheckService;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.RegisterCompanyDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.util.Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RegisterCompanyImpl extends BaseDAO implements RegisterCompanyDAO {
    @Override
    public Map getInfoCompany(Map map) throws SQLException, CloneNotSupportedException {
        Map inParams = new HashMap();
        inParams.put("pi_business_code", map.get("businessCode"));
        inParams.put("pi_type", map.get("type"));
        return this.getClone().callProcedure(PK_REGISTER_COMPANY + "get_info", inParams);
    }

    @Override
    public Map check1(Map map) throws SQLException, CloneNotSupportedException {
        return this.getClone().callProcedure(PK_REGISTER_COMPANY + "check1", map);
    }

    @Override
    public Map check2(Map producer, Map packager, Map transporter, Map distributor) throws SQLException, CloneNotSupportedException {
        Gson gson = new Gson();
        Map inParam = new HashMap();
        inParam.put("pi_producer", gson.toJson(producer));
        inParam.put("pi_packager", gson.toJson(packager));
        inParam.put("pi_transporter", gson.toJson(transporter));
        inParam.put("pi_distributor", gson.toJson(distributor));
        return this.getClone().callProcedure(PK_REGISTER_COMPANY + "check2", inParam);
    }

    @Override
    public ResponseMap saveRegister(Map product, Map process, Map producer, Map packager, Map transporter, Map distributor, Map mapStage, CheckService checkService) throws SQLException, CloneNotSupportedException {
        Gson gson = new Gson();
        Map inParam = new HashMap();
        inParam.put("pi_product", gson.toJson(product));
        inParam.put("pi_process", gson.toJson(process));
        inParam.put("pi_producer", gson.toJson(producer));
        inParam.put("pi_packager", gson.toJson(packager));
        inParam.put("pi_transporter", gson.toJson(transporter));
        inParam.put("pi_distributor", gson.toJson(distributor));
        BaseDAO dao = this.getClone();
        ResponseMap res = new ResponseMap(dao.callProcedure(PK_REGISTER_COMPANY + "save_register", inParam));
        if (!res.isFail()){
            if (res.getMap().get("PO_PROCESS_ID") != null) {
                long processID = Utils.getLong(res.getMap(), "PO_PROCESS_ID");
                List addList = new ArrayList();
                String sql = "insert into tb_process_stage (N_ID, S_NAME, S_DESCRIPTION, N_PROCESS_ID, D_CREATED_DATE, " +
                        "S_ACTIVE, N_ORDER, S_FAKE_ID, S_FAKE_PARENT_ID) " +
                        "values (SEQ_TB_PROCESS_STAGE.nextval, ?, ?, ?, sysdate, 'S', ?, ?, ?)";
                for (int i = 1; i <= (int)mapStage.get("num"); i++) {
                    Map map = (Map) mapStage.get("step_" + i);
                    Object[] ob = new Object[6];
                    ob[0] = map.get("name");
                    ob[1] = map.get("desc");
                    ob[2] = processID;
                    ob[3] = map.get("order");
                    ob[4] = map.get("id");
                    ob[5] = map.get("idParent");
                    addList.add(ob);
                }
                dao.executeUpdateBatch(sql, addList);
                sql = "update tb_process_stage set N_PARENT_ID = 0 WHERE N_PROCESS_ID = ? AND S_FAKE_PARENT_ID = 'rootID'";
                dao.executeUpdate(sql, processID);
                //update cac ong stage cap > 1 truong n_parent_id
                sql = "update tb_process_stage s1 set s1.N_PARENT_ID = (" +
                        " SELECT s2.N_ID FROM tb_process_stage s2 WHERE s1.S_FAKE_PARENT_ID = s2.S_FAKE_ID AND s2.N_PROCESS_ID = ?) " +
                        " WHERE s1.N_PROCESS_ID = ? AND s1.S_FAKE_PARENT_ID <> 'rootID'";
                executeUpdate(sql, processID, processID);
            }
            if (res.getMap().get("PO_KEY_PRODUCT") != null) {
                checkService.setKeyUpload((String) res.getMap().get("PO_KEY_PRODUCT"));
            }
            if (res.getMap().get("PO_KEY_PRODUCER") != null) {
                checkService.setKeyUpload((String) res.getMap().get("PO_KEY_PRODUCER"));
            }
            if (res.getMap().get("PO_KEY_PACKAGER") != null) {
                checkService.setKeyUpload((String) res.getMap().get("PO_KEY_PACKAGER"));
            }
            if (res.getMap().get("PO_KEY_TRANSPORTER") != null) {
                checkService.setKeyUpload((String) res.getMap().get("PO_KEY_TRANSPORTER"));
            }
            if (res.getMap().get("PO_KEY_DISTRIBUTOR") != null) {
                checkService.setKeyUpload((String) res.getMap().get("PO_KEY_DISTRIBUTOR"));
            }
        }
        return res;
    }

}
