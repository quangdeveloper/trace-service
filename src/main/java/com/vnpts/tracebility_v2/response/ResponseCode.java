package com.vnpts.tracebility_v2.response;

/**
 * Created by Thucvn on 6/14/2017.
 */
public class ResponseCode {
    protected String code;
    protected String message;

    public ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseCode() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        setMessByConfig();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private void setMessByConfig() {
        String mess = ResponseConfig.getInstance().getMess(this.code);
        if (mess != null) this.message = mess;
    }

    public void setFailSystem(){
        this.setCode("999");
    }

    public void setForbidden(){
        this.setCode("007");
    }

    public void setWrongPartner(){
        this.setCode("100");
    }

    public void invalidSignature(){
        this.setCode("800");
    }

    public void expiredToken(){
        this.setCode("005");
    }

    public void invalidToken(){
        this.setCode("006");
    }

    public void setFailJson(){
        this.setCode("011");
    }

    public void setFailMaxDFileSize(){
        this.setCode("012");
    }
    @Override
    public String toString() {
        return "ResponseCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public boolean isFail(){
        return !"200".equals(code);
    }

    public void setSuccess(){
        this.setCode("200");
    }
}
