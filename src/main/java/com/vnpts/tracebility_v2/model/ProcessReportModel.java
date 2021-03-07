package com.vnpts.tracebility_v2.model;

import com.vnpts.tracebility_v2.util.TotalConverter;

import java.util.Date;
import java.util.Map;

public class ProcessReportModel {

    private Integer stt;
    private Integer processType;
    private String name;
    private String productName;
    private String detail;
    private String createdDate;
    private String processTypeName;

    public ProcessReportModel() {
    }

    public void convertResultToModel(Map map) {
        setStt(Integer.parseInt(map.get("STT").toString()));
        setProcessType(Integer.parseInt(map.get("N_PROCESS_TYPE").toString()));
        setProcessTypeName(map.get("S_PROCESS_TYPE_NAME").toString());
        setName(map.get("S_NAME").toString());
        setProductName(map.get("S_PRODUCT_NAME").toString());
        setDetail(map.get("C_DETAILS") != null ? map.get("C_DETAILS").toString() : "");
        setCreatedDate(TotalConverter.toString((Date) map.get("D_CREATED_DATE"), "dd/MM/yyyy"));
    }

    public Integer getStt() {
        return stt;
    }

    public void setStt(Integer stt) {
        this.stt = stt;
    }

    public Integer getProcessType() {
        return processType;
    }

    public void setProcessType(Integer processType) {
        this.processType = processType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getProcessTypeName() {
        return processTypeName;
    }

    public void setProcessTypeName(String processTypeName) {
        this.processTypeName = processTypeName;
    }
}
