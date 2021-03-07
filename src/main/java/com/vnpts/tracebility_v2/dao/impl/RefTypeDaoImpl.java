package com.vnpts.tracebility_v2.dao.impl;

import org.springframework.stereotype.Component;
import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.RefTypeDAO;
import com.vnpts.tracebility_v2.util.Utils;

import java.sql.SQLException;
import java.util.Map;

@Component
public class RefTypeDaoImpl extends BaseDAO implements RefTypeDAO {
  @Override
  public Map getList(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam.put("pi_keyword2", inParam.get("pi_keyword"));
    inParam = Utils.trimMap(inParam, "pi_keyword", "pi_keyword2");
    inParam = Utils.lowerMap(inParam, "pi_keyword");
    inParam = Utils.upperMap(inParam, "pi_keyword2");
    return this.getClone().callFunction(PK_REF_TYPE + "get_list", inParam);
  }

  @Override
  public Map createRefType(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    return this.getClone().callProcedure( PK_REF_TYPE + "create_ref_type", inParam);
  }

  @Override
  public Map editRefType(Map inParam) throws CloneNotSupportedException, SQLException {
    inParam = Utils.trimMap(inParam, "pi_code");
    inParam = Utils.upperMap(inParam, "pi_code");
    return this.getClone().callProcedure( PK_REF_TYPE + "edit_ref_type", inParam);
  }

  @Override
  public Map deleteRefType(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callProcedure( PK_REF_TYPE + "delete_ref_type", inParam);
  }

  @Override
  public Map getCBB(Map inParam) throws CloneNotSupportedException, SQLException {
    return this.getClone().callFunction( PK_REF_TYPE + "cbb_ref_type", inParam);
  }
}
