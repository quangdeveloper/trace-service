package com.vnpts.tracebility_v2.model;

import java.util.Map;

public class UsedTempReportModel {

    private Integer stt;
    private String companyName;
    private String productionPlanName;
    private String harvestName;
    private String printCommandCode;
    private Integer tempNum;

    public UsedTempReportModel() {
    }

    public void convertResultToModel(Map map) {
        setStt(Integer.parseInt(map.get("STT").toString()));
        setCompanyName(map.get("S_COMPANY_NAME").toString());
        setProductionPlanName(map.get("S_PRODUCTION_PLAN_NAME").toString());
        setHarvestName(map.get("S_HARVEST_NAME").toString());
        setPrintCommandCode(map.get("S_PRINT_COMMAND_CODE").toString());
        setTempNum(Integer.parseInt(map.get("N_TEMP_NUM").toString()));
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductionPlanName() {
        return productionPlanName;
    }

    public void setProductionPlanName(String productionPlanName) {
        this.productionPlanName = productionPlanName;
    }

    public String getHarvestName() {
        return harvestName;
    }

    public void setHarvestName(String harvestName) {
        this.harvestName = harvestName;
    }

    public String getPrintCommandCode() {
        return printCommandCode;
    }

    public void setPrintCommandCode(String printCommandCode) {
        this.printCommandCode = printCommandCode;
    }

    public Integer getTempNum() {
        return tempNum;
    }

    public void setTempNum(Integer tempNum) {
        this.tempNum = tempNum;
    }
}
