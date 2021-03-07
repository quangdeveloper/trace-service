package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ChangeRoleChainRequestDAO;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
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
@RequestMapping(value = "/changeRoleChainRequest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ChangeRoleChainRequestService {
  @Autowired
  private ChangeRoleChainRequestDAO changeRoleChainRequestDAO;
  @Autowired
  private GsonResponse gsonResponse;
  @Autowired
  ParameterDAO parameterDAO;

  @RequestMapping(value = "/getCurrentChainRoleByUser")
  @ResponseBody
  public String getCurrentChainRoleByUser(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_n_user_id", responseSession.getId());
    try {
      ResponseMap responseMap = new ResponseMap(changeRoleChainRequestDAO.getCurrentChainRoleByUser(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/sendRequest")
  @ResponseBody
  public String sendRequest(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_n_user_id", responseSession.getId());
    try {
      ResponseMap responseMap = new ResponseMap(changeRoleChainRequestDAO.sendRequest(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getList")
  @ResponseBody
  public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(changeRoleChainRequestDAO.getList(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/approveRequest")
  @ResponseBody
  public String approveRequest(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_n_user_id", responseSession.getId());
    try {
      ResponseMap responseMap = new ResponseMap(changeRoleChainRequestDAO.approveRequest(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getListByUser")
  @ResponseBody
  public String getListByUser(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_n_user_id", responseSession.getId());
    try {
      ResponseMap responseMap = new ResponseMap(changeRoleChainRequestDAO.getListByUser(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getDetail")
  @ResponseBody
  public String getDetail(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(changeRoleChainRequestDAO.getDetail(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
