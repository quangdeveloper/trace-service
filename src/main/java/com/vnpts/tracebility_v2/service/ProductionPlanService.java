package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ProductionPlanDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.response.user.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/productionPlan", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductionPlanService {
  @Autowired
  private ProductionPlanDAO productionPlanDAO;

  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/createMember")
  @ResponseBody
  public String createMember(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseObject responseMap = new ResponseObject(productionPlanDAO.createMember(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/findMembersInvite")
  @ResponseBody
  public String findMembersInvite(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.findMembersInvite(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }


  @RequestMapping(value = "/insertPlan")
  @ResponseBody
  public String insertPlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.insertPlan(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getList")
  @ResponseBody
  public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.getList(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getByID")
  @ResponseBody
  public String getByID(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.getByID(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/editPlan")
  @ResponseBody
  public String editPlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.editPlan(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/deletePlan")
  @ResponseBody
  public String deletePlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.deletePlan(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/cbbPlanJoined")
  @ResponseBody
  public String cbbPlanJoined(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.cbbPlanJoined(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/listPlanJoined")
  @ResponseBody
  public String listPlanJoined(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.listPlanJoined(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/listPlanRequesting")
  @ResponseBody
  public String listPlanRequesting(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.listPlanRequesting(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/infoRequesting")
  @ResponseBody
  public String infoRequesting(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.infoRequesting(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/acceptInvite")
  @ResponseBody
  public String acceptInvite(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionPlanDAO.acceptInvite(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
