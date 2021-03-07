package com.vnpts.tracebility_v2.response.user;


import com.vnpts.tracebility_v2.response.ResponseCode;

/**
 * Created by Thucvn on 6/14/2017.
 */
public class ResponseLogin extends ResponseCode {
    protected String session;

    @Override
    public String toString() {
        return "ResponseLogin{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", session='" + session + '\'' +
                '}';
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
