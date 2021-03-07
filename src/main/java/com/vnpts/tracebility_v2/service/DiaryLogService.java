package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.DiaryLogDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/diaryLog", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DiaryLogService {
  @Autowired
  private DiaryLogDAO diaryLogDAO;

  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/createLogDiary")
  @ResponseBody
  public String createLogDiary(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = diaryLogDAO.logDiary(mapParams);
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getDiaryStage")
  @ResponseBody
  public String getDiaryStage(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
//      ResponseObject responseMap = new ResponseObject(diaryLogDAO.getDiaryStage(mapParams));
      ResponseMap responseMap = new ResponseMap(diaryLogDAO.getDiaryStage(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/nonAuthen/img", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getDiaryImg(@RequestParam("id") long id, @RequestParam("diaryID") long diaryID) {
    try {
      Map out = diaryLogDAO.getImg(id, diaryID);
      return FileUtils.returnFile((String)out.get("PO_LOC"), (String)out.get("PO_FILE"));
    } catch (Exception e) {
      return null;
    }
  }

  @RequestMapping(value = "/editLogDiary")
  @ResponseBody
  public String editLogDiary(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = diaryLogDAO.editLogDiary(mapParams);
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/deleteLogDiary")
  @ResponseBody
  public String deleteLogDiary(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(diaryLogDAO.deleteLogDiary(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getByID")
  @ResponseBody
  public String getByID(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(diaryLogDAO.getByID(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getCompanyForAdmin")
  @ResponseBody
  public String getCompanyForAdmin(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(diaryLogDAO.getCompanyForAdmin(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
