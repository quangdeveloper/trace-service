package com.vnpts.tracebility_v2.dao.impl;

import com.vnpts.tracebility_v2.dao.BaseDAO;
import com.vnpts.tracebility_v2.dao.ReportDAO;
import com.vnpts.tracebility_v2.util.Utils;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Map;

@Component
public class ReportDAOImpl extends BaseDAO implements ReportDAO {
    @Override
    public Map report01(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.lowerMap(in, "pi_keyword");
        in = Utils.noSignMap(in, "pi_keyword");
        in = Utils.changeArr(in, false, false, "|", "pi_businessType");
        return this.getClone().callFunction(PK_REPORT + "report_01", in);
    }

    @Override
    public Map cbbOwnerPlan(Map in) throws CloneNotSupportedException, SQLException {
        return this.getClone().callFunction(PK_REPORT + "cbb_owner_plan", in);
    }

    @Override
    public Map cbbOwnerCompany(Map in) throws CloneNotSupportedException, SQLException {
        return this.getClone().callFunction(PK_REPORT + "cbb_owner_company", in);
    }

    @Override
    public Map report02(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_productList", "pi_companyList");
        return this.getClone().callFunction(PK_REPORT + "report_02", in);
    }

    @Override
    public Map report03(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_productList", "pi_companyList");
        in = Utils.toDateMap(in, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
        return this.getClone().callFunction(PK_REPORT + "report_03", in);
    }

    @Override
    public Map cbbProductByCompany(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_companyList");
        return this.getClone().callFunction(PK_REPORT + "cbb_product_by_company", in);
    }

    @Override
    public Map report04(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_planList", "pi_companyList");
        in = Utils.toDateMap(in, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
        return this.getClone().callFunction(PK_REPORT + "report_04", in);
    }

    @Override
    public Map cbbPlanByCompany(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_companyList");
        return this.getClone().callFunction(PK_REPORT + "cbb_plan_by_company", in);
    }

    @Override
    public Map reportProductByTime(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_rootProductList", "pi_productionPlanList", "pi_companyList");
        in = Utils.toDateMap(in, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
        return this.getClone().callFunction(PK_REPORT + "cbb_report_product_by_time", in);
    }

    @Override
    public Map searchProductByTime(Map in) throws CloneNotSupportedException, SQLException {
        in = Utils.changeArr(in, false, false, "|", "pi_rootProductList", "pi_productionPlanList", "pi_companyList");
        in = Utils.toDateMap(in, "dd/MM/yyyy", "pi_fromDate", "pi_toDate");
        return this.getClone().callFunction(PK_REPORT + "search_product_by_time", in);
    }
}
