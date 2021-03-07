package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.BusinessTypeDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/businessType", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class BusinessTypeService {
  @Autowired
  private BusinessTypeDAO businessTypeDAO;

  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/create")
  @ResponseBody
  public String create(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.create(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getList")
  @ResponseBody
  public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.findAll(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/editType")
  @ResponseBody
  public String editType(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.edit(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/deleteType")
  @ResponseBody
  public String deleteType(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.remove(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/findByID")
  @ResponseBody
  public String findByID(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.findById(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/nonAuthen/getCbb")
  @ResponseBody
  public String getCbb(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.cbbGetAll(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getCbb")
  @ResponseBody
  public String getCbb(@ModelAttribute("resSession") ResponseSession responseSession,@RequestBody String json) {

    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_userId", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(businessTypeDAO.cbbGetAllCustom(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
