package com.vnpts.tracebility_v2.model;

import com.vnpts.tracebility_v2.util.TotalConverter;

import java.util.Date;
import java.util.Map;

public class ProductionHarvestEstimatedReportModel {

    private Integer stt;
    private Integer companyId;
    private String companyName;
    private Integer productionPlanId;
    private String productionPlanName;
    private String productName;
    private String productionSectorName;
    private Double volumeTon;
    private String plannedHarvestDate;


    public ProductionHarvestEstimatedReportModel() {
    }

    public void convertResultToModel(Map map) {
        setStt(Integer.parseInt(map.get("STT").toString()));
        setCompanyId(Integer.parseInt(map.get("N_COMPANY_ID").toString()));
        setCompanyName(map.get("S_COMPANY_NAME").toString());
        setProductionPlanId(Integer.parseInt(map.get("N_PRODUCTION_PLAN_ID").toString()));
        setProductionPlanName(map.get("S_PRODUCTION_PLAN_NAME").toString());
        setProductName(map.get("S_PRODUCT_NAME").toString());
        setProductionSectorName(map.get("S_PRODUCTION_SECTOR_NAME").toString());
        setVolumeTon(Double.parseDouble(map.get("N_VOLUME_TON").toString()));
        setPlannedHarvestDate(TotalConverter.toString((Date) map.get("D_PLANNED_HARVEST"), "dd/MM/yyyy"));
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(Integer productionPlanId) {
        this.productionPlanId = productionPlanId;
    }

    public String getProductionPlanName() {
        return productionPlanName;
    }

    public void setProductionPlanName(String productionPlanName) {
        this.productionPlanName = productionPlanName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductionSectorName() {
        return productionSectorName;
    }

    public void setProductionSectorName(String productionSectorName) {
        this.productionSectorName = productionSectorName;
    }

    public Double getVolumeTon() {
        return volumeTon;
    }

    public void setVolumeTon(Double volumeTon) {
        this.volumeTon = volumeTon;
    }

    public String getPlannedHarvestDate() {
        return plannedHarvestDate;
    }

    public void setPlannedHarvestDate(String plannedHarvestDate) {
        this.plannedHarvestDate = plannedHarvestDate;
    }
}
