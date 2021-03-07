package com.vnpts.tracebility_v2.response;

import java.util.Map;

public class ResponseMap extends ResponseCode {
    protected Map map;

    public ResponseMap(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public ResponseMap(Map map) {
        super();
        if (map.containsKey("po_code") || map.containsKey("PO_CODE")) {
            this.setCode((String) (map.containsKey("po_code") ? map.get("po_code") : map.get("PO_CODE")));
            map.remove("po_code");
            map.remove("PO_CODE");
        }
        if (map.containsKey("po_msg") || map.containsKey("PO_MSG")) {
            this.setMessage((String) map.get("po_msg"));
            map.remove("po_msg");
            map.remove("PO_MSG");
        }
        this.map = map;
    }

    public Map getResult() {
        return this.map;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "ResponseMap{" +
                "map=" + map +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
