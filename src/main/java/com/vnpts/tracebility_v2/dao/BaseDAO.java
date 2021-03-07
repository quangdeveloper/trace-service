package com.vnpts.tracebility_v2.dao;

import org.jdbc.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import static com.vnpts.tracebility_v2.util.Constants.USER_ACTION;


public class BaseDAO extends JdbcTemplate {
  protected final String PK_PLOT_CATEGORY = "PK_PLOT_CATEGORY.";
  protected final String PK_USER = "PK_USER.";
  protected final String PK_MENU = "PK_MENU.";
  protected final String PK_ROLE = "PK_ROLE.";
  protected final String PK_FUNCTION = "PK_FUNCTION.";
  protected final String PK_PRODUCT = "PK_PRODUCT.";
  protected final String PK_PRODUCER = "PK_PRODUCER.";
  protected final String PK_ADMIN = "PK_ADMIN.";
  protected final String PK_REGISTER_COMPANY = "PK_REGISTER_COMPANY.";
  protected final String PK_ADMIN_ACC = "PK_ADMIN_ACC.";
  protected final String PK_PARAMETER = "PK_PARAMETER.";
  protected final String PK_HRM_UNIT = "PK_HRM_UNIT.";
  protected final String PK_ACCOUNT = "PK_ACCOUNT.";
  protected final String PK_AREA = "PK_AREA.";
  protected final String USER_ACT = USER_ACTION;
  protected final String PK_USER_TYPE = "PK_USER_TYPE.";
  protected final String PK_BUSINESS_TYPE = "PK_BUSINESS_TYPE.";
  protected final String PK_ANNOUNCE_TYPE = "PK_ANNOUNCE_TYPE.";
  protected final String PK_REF_GROUP = "PK_REF_GROUP.";
  protected final String PK_REF_TYPE = "PK_REF_TYPE.";
  protected final String PK_ACC_PUBLIC = "PK_ACC_PUBLIC.";
  protected final String PK_ACC_BUSINESS = "PK_ACC_BUSINESS.";
  protected final String PK_REFLECTION = "PK_REFLECTION.";
  protected final String PK_REFLECTION_REPORT = "PK_REFLECTION_REPORT.";
  protected final String PK_EMAIL_HANDLE = "PK_EMAIL_HANDLE.";
  protected final String PK_TRAINING = "PK_TRAINING.";
  protected final String PK_PROCESS = "PK_PROCESS.";
  protected final String PK_PRODUCTION_PLAN = "PK_PRODUCTION_PLAN.";
  protected final String PK_PRODUCTION_SECTOR = "PK_PRODUCTION_SECTOR.";
  protected final String PK_DIARY = "PK_DIARY.";
  protected final String PK_HARVEST = "PK_HARVEST.";
  protected final String PK_DELIVERY_NOTE = "PK_DELIVERY_NOTE.";
  protected final String PK_PRINT_COMMAND = "PK_PRINT_COMMAND.";
  protected final String PK_SUPPORT_UNIT = "PK_SP_UNIT.";
  protected final String PK_VERIFY = "PK_VERIFY.";
  protected final String PK_INFO_CODE = "PK_INFO_CODE.";
  protected final String PK_REPORT = "PK_REPORT.";
  protected final String PK_NOF = "PK_NOF.";
  protected final String PK_CHANGE_ROLE_CHAIN_REQUEST = "PK_CHANGE_ROLE_CHAIN_REQUEST.";

  public BaseDAO getClone() throws CloneNotSupportedException {
    return (BaseDAO) this.clone();
  }

  @Autowired
  private DataSource dataSource;

  @PostConstruct
  public void setDataSource(){
    this.setDataSource(dataSource);
  }
}
