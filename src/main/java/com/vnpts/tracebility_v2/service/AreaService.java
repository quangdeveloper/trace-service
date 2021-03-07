package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.AreaDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/area", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class AreaService {
  @Autowired
  private GsonResponse gsonResponse;
  @Autowired
  private AreaDAO areaDAO;

  @RequestMapping(value = "/nonAuthen/cbbGetByParent")
  @ResponseBody
  public String cbbGetByParent(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      return gsonResponse.toJson(new ResponseMap(areaDAO.cbbGetAllChild(mapParams)));
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
