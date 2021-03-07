package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.CommonDAO;
import com.vnpts.tracebility_v2.response.ResponseCode;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@SessionAttributes({"resSession", "keyUpload"})

@RequestMapping(value = "/common", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CommonService {
    @Autowired
    private CommonDAO commonDAO;

    @Autowired
    private GsonResponse gsonResponse;
    public static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    @RequestMapping(value = "/nonAuthen/upload")
    @ResponseBody
    public String upload(@ModelAttribute("keyUpload") String key,
                         @RequestParam("file") MultipartFile[] uploadFile) {
        ResponseCode res = new ResponseCode();
        if (uploadFile.length == 0) {
            res.setCode("530");
            return gsonResponse.toJson(res);
        }
        StringBuffer val = new StringBuffer();
        String[] names = new String[uploadFile.length];
        for (int i = 0; i < uploadFile.length; i++) {
            Map mapFile = new HashMap();
            names[i] = FileUtils.generateName();
            mapFile.put("name", names[i]);
            mapFile.put("full", names[i] + FileUtils.getExtensionName(uploadFile[i].getOriginalFilename()));
            mapFile.put("size", uploadFile[i].getSize());
            mapFile.put("originName", uploadFile[i].getOriginalFilename());
            val.append(gsonResponse.toJson(mapFile)).append("|");
        }
        try {
            res = new ResponseMap(commonDAO.checkKey(key, val.substring(0, val.length() - 1), uploadFile.length));
            if (((ResponseMap) res).getMap().get("PO_LOC") == null) {
                res.setCode("531");
            } else {
                String dir = (String) ((ResponseMap) res).getMap().get("PO_LOC");
                ((ResponseMap) res).getMap().remove("PO_LOC");
                FileUtils.createDir(dir);
                for (int i = 0; i < uploadFile.length; i++) {
                    File file = new File(dir + names[i] + FileUtils.getExtensionName(uploadFile[i].getOriginalFilename()));
                    uploadFile[i].transferTo(file);
                }
                res.setSuccess();
            }
            return gsonResponse.toJson(res);
        } catch (SQLException | CloneNotSupportedException | IOException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }

    @RequestMapping(value = "/nonAuthen/getAreaChild")
    @ResponseBody
    public String cbbGetProvincesChild(@RequestBody String json) {
        Map<String, Object> mapParams = gsonResponse.fromJson(json);
        try {
            return gsonResponse.toJson(new ResponseMap(commonDAO.getAreaChild(mapParams)));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }

    @RequestMapping(value = "/nonAuthen/getUnitCbb")
    @ResponseBody
    public String getUnitCbb() {
        try {
            return gsonResponse.toJson(new ResponseMap(commonDAO.getUnitCbb()));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }

    @RequestMapping(value = "/nonAuthen/getUnitAreaCbb")
    @ResponseBody
    public String getUnitAreaCbb() {
        try {
            return gsonResponse.toJson(new ResponseMap(commonDAO.getUnitAreaCbb()));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }

    @RequestMapping(value = "/nonAuthen/scan/productInfo")
    @ResponseBody
    public String scanProductInfo(@RequestBody String json) {
        Map<String, Object> mapParams = gsonResponse.fromJson(json);
        try {
            return gsonResponse.toJson(new ResponseMap(commonDAO.productInfo(mapParams)));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }

    @RequestMapping(value = "/nonAuthen/scan/memberInfo")
    @ResponseBody
    public String scanMemberInfo(@RequestBody String json) {
        Map<String, Object> mapParams = gsonResponse.fromJson(json);
        try {
            return gsonResponse.toJson(new ResponseMap(commonDAO.memberInfo(mapParams)));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }

    @RequestMapping(value = "/nonAuthen/scan/processInfo")
    @ResponseBody
    public String scanProcessInfo(@RequestBody String json) {
        Map<String, Object> mapParams = gsonResponse.fromJson(json);
        try {
            return gsonResponse.toJson(new ResponseMap(commonDAO.processInfo(mapParams)));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, logger);
        }
    }
}
