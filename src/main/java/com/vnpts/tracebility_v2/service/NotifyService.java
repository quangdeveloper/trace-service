package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.NotifyDAO;
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
@RequestMapping(value = "/notify", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class NotifyService {
  @Autowired
  private NotifyDAO notifyDAO;
  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/getList")
  @ResponseBody
  public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(notifyDAO.getList(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/markRead")
  @ResponseBody
  public String markRead(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    try {
      ResponseMap responseMap = new ResponseMap(notifyDAO.markRead(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
