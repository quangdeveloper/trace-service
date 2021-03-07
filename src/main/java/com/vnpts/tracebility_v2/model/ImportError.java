package com.vnpts.tracebility_v2.model;

public class ImportError {

    public static String REQUIRED = "COMMON.VALIDATION.REQUIRED";
    public static String MINLENGTH = "COMMON.VALIDATION.MINLENGTH";
    public static String MAXLENGTH = "COMMON.VALIDATION.MAXLENGTH";
    public static String CAN_NOT_EQUAL = "COMMON.VALIDATION.CAN_NOT_EQUAL";
    public static String EXISTED_ORDER = "COMMON.VALIDATION.EXISTED_ORDER";
    public static String NOT_EXISTED_PRODUCT = "COMMON.VALIDATION.NOT_EXISTED_PRODUCT";
    public static String NOT_EXISTED_PROCESS_TYPE = "COMMON.VALIDATION.NOT_EXISTED_PROCESS_TYPE";
    public static String NOT_EXISTED_ORDER_PARENT = "COMMON.VALIDATION.NOT_EXISTED_ORDER_PARENT";
    public static String NOT_EXISTED_STAGE = "COMMON.VALIDATION.NOT_EXISTED_STAGE";

    private String row;
    private String column;
    private String code;
    private String additionalParam;

    public ImportError() {
    }

    public ImportError(String row, String column, String code, String additionalParam) {
        this.row = row;
        this.column = column;
        this.code = code;
        this.additionalParam = additionalParam;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAdditionalParam() {
        return additionalParam;
    }

    public void setAdditionalParam(String additionalParam) {
        this.additionalParam = additionalParam;
    }
}
