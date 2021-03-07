package com.vnpts.tracebility_v2.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vnpts.tracebility_v2.response.ResponseCode;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GsonResponse {

  public String toJson(Object ob){
//    GsonBuilder builder = new GsonBuilder();
//    builder.registerTypeAdapter(ResponseCode.class, new ResponseAdapter());
//    Gson gsonExt = builder.create();
    Gson gsonExt = new GsonBuilder().serializeNulls().create();
//    Gson gsonExt = new Gson();
    return gsonExt.toJson(ob);
  }

  public String errorSystem(Exception e, Logger logger){
    e.printStackTrace();
    logger.error(e.getMessage());
    ResponseCode responseCode = new ResponseCode();
    responseCode.setFailSystem();
    return toJson(responseCode);
  }


  public String failInput(){
    ResponseCode responseCode = new ResponseCode();
    responseCode.setCode("011");
    return toJson(responseCode);
  }

  public Map<String, Object> fromJson(String json) {
    Gson gsonExt = new GsonBuilder().serializeNulls().create();
    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();
    return gsonExt.fromJson(json, type);
  }

  public Map<String, Object> fromJsonPi(String json) {
    Gson gsonExt = new GsonBuilder().serializeNulls().create();
    Type type = new TypeToken<Map<String, Object>>() {
    }.getType();
    Map map = gsonExt.fromJson(json, type);
    Map map1 = new HashMap();
    for (Object key : map.keySet()) {
      map1.put("pi_" + key, map.get(key));
    }
    return map1;
  }

  public List<Map<String, Object>> fromJsonArray(String json) {
    Gson gsonExt = new GsonBuilder().serializeNulls().create();
    Type type = new TypeToken<List<Map<String, Object>>>() {
    }.getType();
    return gsonExt.fromJson(json, type);
  }

  public <T> T castJson(String json, Class<T> classOfT) {
    Gson gson = new Gson();
    return gson.fromJson(json, classOfT);
  }
}
