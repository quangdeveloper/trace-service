package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.InitSVDAO;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
public class InitSVDAOImpl extends BaseDAO implements InitSVDAO {
  public List<Map> getAllPartner() throws SQLException {
    String sql = "select n_id, s_short_name, s_public_key from tb_partner where s_status = 'A'";
    List<Map> map = executeQuery(sql);
    return map;
  }

  @Override
  public List<Map> getRoleToCache(int userID) throws SQLException {
    String sql = "select t1.N_ROLE_ID from tb_user_role t1 " +
        "inner join tb_role t2 on t1.n_role_id = t2.n_id " +
        "where t2.S_status = 'A' " +
        "and N_USER_ID = " + userID;
    List<Map> map = executeQuery(sql);
    return map;
  }

  @Override
  public List<Map> getFunctionRegex(int userID) throws SQLException {
    String sql = "select S_SERVICES_REGEX from tb_function t1 " +
        "inner join tb_function_role t2 on t1.n_id = t2.n_function_id " +
        "inner join tb_role t3 on t2.n_role_id = t3.n_id " +
        "inner join tb_user_role t4 on t3.n_id = t4.n_role_id " +
        "where t3.S_STATUS = 'A' " +
        "and t1.S_STATUS = 'A' " +
        "and t1.S_SERVICES_REGEX is not null " +
        "and t4.n_user_id = " + userID;
    List<Map> map = executeQuery(sql);
    return map;
  }
}
