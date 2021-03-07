package com.vnpts.tracebility_v2.dao;

import java.sql.SQLException;
import java.util.Map;

public interface ReportDAO {
    Map report01(Map in) throws CloneNotSupportedException, SQLException;

    Map cbbOwnerPlan(Map in) throws CloneNotSupportedException, SQLException;

    Map cbbOwnerCompany(Map in) throws CloneNotSupportedException, SQLException;

    Map report02(Map in) throws CloneNotSupportedException, SQLException;

    Map report03(Map in) throws CloneNotSupportedException, SQLException;

    Map cbbProductByCompany(Map in) throws CloneNotSupportedException, SQLException;

    Map report04(Map in) throws CloneNotSupportedException, SQLException;

    Map cbbPlanByCompany(Map in) throws CloneNotSupportedException, SQLException;

    Map reportProductByTime(Map in) throws CloneNotSupportedException, SQLException;

    Map searchProductByTime(Map in) throws CloneNotSupportedException, SQLException;
}
