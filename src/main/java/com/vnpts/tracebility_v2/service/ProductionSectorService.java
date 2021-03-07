package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ProductionSectorDAO;
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
@RequestMapping(value = "/productionSector", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductionSectorService {
  @Autowired
  private ProductionSectorDAO productionSectorDAO;

  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/createSector")
  @ResponseBody
  public String createSector(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionSectorDAO.createSector(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/editSector")
  @ResponseBody
  public String editSector(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionSectorDAO.editSector(mapParams));
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
      ResponseMap responseMap = new ResponseMap(productionSectorDAO.getList(mapParams));
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
      ResponseMap responseMap = new ResponseMap(productionSectorDAO.getByID(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/cbbNotDone")
  @ResponseBody
  public String cbbNotDone(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionSectorDAO.cbbNotDone(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getCbb")
  @ResponseBody
  public String getCbb(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productionSectorDAO.getCbb(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
