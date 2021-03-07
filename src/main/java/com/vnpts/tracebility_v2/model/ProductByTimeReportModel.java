package com.vnpts.tracebility_v2.model;

import com.vnpts.tracebility_v2.util.TotalConverter;

import java.util.Date;
import java.util.Map;

public class ProductByTimeReportModel {

    private String stt;
    private Long rootProductId;
    private String rootProductName;
    private Long productId;
    private String productName;
    private Long productionPlanId;
    private String productionPlanName;
    private String ownerName;
    private Double amount;
    private String unitName;
    private String exptedEndDate;
    private Long type;
    private String order;


    public ProductByTimeReportModel() {
    }

    public void convertResultToModel(Map map) {
        setRootProductId(Long.parseLong(map.get("N_ROOT_PRODUCT_ID").toString()));
        setRootProductName(map.get("S_ROOT_PRODUCT_NAME").toString());
        setProductionPlanId(Long.parseLong(map.get("N_PRODUCTION_PLAN_ID").toString()));
        setAmount(Double.parseDouble(map.get("N_AMOUNT").toString()));
        setUnitName(map.get("S_UNIT_NAME").toString());
        setProductionPlanName(map.get("S_PRODUCTION_PLAN_NAME").toString());
        setProductId(Long.parseLong(map.get("N_PRODUCT_ID").toString()));
        setProductName(map.get("S_PRODUCT_NAME").toString());
        setOwnerName(map.get("S_OWNER").toString());
        setExptedEndDate(TotalConverter.toString((Date) map.get("D_EXPECTED_END_DATE"), "dd/MM/yyyy"));
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public Long getRootProductId() {
        return rootProductId;
    }

    public void setRootProductId(Long rootProductId) {
        this.rootProductId = rootProductId;
    }

    public String getRootProductName() {
        return rootProductName;
    }

    public void setRootProductName(String rootProductName) {
        this.rootProductName = rootProductName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getExptedEndDate() {
        return exptedEndDate;
    }

    public void setExptedEndDate(String exptedEndDate) {
        this.exptedEndDate = exptedEndDate;
    }

    public Long getProductionPlanId() {
        return productionPlanId;
    }

    public void setProductionPlanId(Long productionPlanId) {
        this.productionPlanId = productionPlanId;
    }

    public String getProductionPlanName() {
        return productionPlanName;
    }

    public void setProductionPlanName(String productionPlanName) {
        this.productionPlanName = productionPlanName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
