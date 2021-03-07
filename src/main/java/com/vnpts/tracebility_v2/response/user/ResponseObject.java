/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vnpts.tracebility_v2.response.user;

import com.vnpts.tracebility_v2.response.ResponseCode;

/**
 *
 * @author Truong
 */
public class ResponseObject extends ResponseCode {
    protected Object object;

    public ResponseObject(Object object) {
        super();
        this.object = object;
        if (object != null) setSuccess();
        else setFailSystem();
    }

    @Override
    public String toString() {
        return "ResponseMap{" +
        "object=" + object +
        ", code='" + code + '\'' +
        ", message='" + message + '\'' +
        '}';
    }
    
    
}
