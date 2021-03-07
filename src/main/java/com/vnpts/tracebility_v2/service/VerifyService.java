package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.VerifyDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class VerifyService {
  @Autowired
  private VerifyDAO verifyDAO;
  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/sectorStage")
  @ResponseBody
  public String sectorStage(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(verifyDAO.verifySectorStage(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getListVerifySector")
  @ResponseBody
  public String getListVerifySector(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(verifyDAO.getListVerifySector(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getVerifyStageInfo")
  @ResponseBody
  public String getVerifyStageInfo(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(verifyDAO.getVerifyStageInfo(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/moreInfoForVerifyStage")
  @ResponseBody
  public String moreInfoForVerifyStage(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(verifyDAO.moreInfoForVerifyStage(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/printCommand")
  @ResponseBody
  public String printCommand(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(verifyDAO.verifyPrintCommand(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/nonAuthen/downloadVerifyFile")
  public ResponseEntity<Resource> downloadVerifyFile(@RequestParam("id") long id, @RequestParam("verifyID") long verifyID, HttpServletRequest request) {
    try {
      Map map = verifyDAO.getFileVerify(id, verifyID);
      return FileUtils.returnFile(request, map.get("PO_LOC") + "/", map.get("PO_FILE") + "", map.get("PO_ORIGINAL") + "");
    } catch (CloneNotSupportedException | SQLException | IOException e) {
      return null;
    }
  }
}
