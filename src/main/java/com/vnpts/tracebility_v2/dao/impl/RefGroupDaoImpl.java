package com.vnpts.tracebility_v2.dao.impl;

import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.RefGroupDAO;
import com.vnpts.tracebility_v2.util.Utils;

import java.sql.SQLException;
import java.util.Map;

@Component
public class RefGroupDaoImpl extends BaseDAO implements RefGroupDAO {
  @Override
  public Map getList(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam.put("pi_keyword2", inParam.get("pi_keyword"));
    inParam = Utils.trimMap(inParam, "pi_keyword", "pi_keyword2");
    inParam = Utils.lowerMap(inParam, "pi_keyword");
    inParam = Utils.upperMap(inParam, "pi_keyword2");
    return this.getClone().callFunction(PK_REF_GROUP + "get_list", inParam);
  }

  @Override
  public Map createRefGroup(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    return this.getClone().callProcedure( PK_REF_GROUP + "create_ref_group", inParam);
  }

  @Override
  public Map editRefGroup(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    return this.getClone().callProcedure( PK_REF_GROUP + "edit_ref_group", inParam);
  }

  @Override
  public Map deleteRefGroup(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure( PK_REF_GROUP + "delete_ref_group", inParam);
  }

  @Override
  public Map getCBB(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction( PK_REF_GROUP + "cbb_ref_gr", inParam);
  }
}
