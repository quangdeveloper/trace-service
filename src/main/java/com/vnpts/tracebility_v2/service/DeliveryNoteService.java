package com.vnpts.tracebility_v2.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.DeliveryNoteDAO;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/deliveryNote", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class DeliveryNoteService {
  @Autowired
  private DeliveryNoteDAO deliveryNoteDAO;
  @Autowired
  private ParameterDAO parameterDAO;
  @Autowired
  private GsonResponse gsonResponse;

  @RequestMapping(value = "/getList")
  @ResponseBody
  public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.getList(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getListTempPackage")
  @ResponseBody
  public String getListTempPackage(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.getListTempPackage(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getNextSendToList")
  @ResponseBody
  public String getNextSendToList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.getNextSendToList(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/createNote")
  @ResponseBody
  public String createNote(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.createNote(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/editNote")
  @ResponseBody
  public String editNote(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.editNote(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/deleteNote")
  @ResponseBody
  public String deleteNote(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.deleteNote(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getDeliveryProfile")
  @ResponseBody
  public String getDeliveryProfile(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      ResponseMap responseMap = new ResponseMap(deliveryNoteDAO.getDeliveryProfile(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/downloadDeliveryNoteProfile")
  public ResponseEntity<Object> downloadDeliveryNoteProfile(
      @ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json
  ) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJson(json);
      String html = (String) mapParams.get("html");
      String fileName = parameterDAO.getParameter("exportLocation") + "delivery_note_profile/" +
          FileUtils.generateImageName("pdf.pdf");
      createFile(html, fileName);
      File expFile = new File(fileName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
      headers.set("Content-Transfer-Encoding", "binary");
      headers.setContentLength(expFile.length());
      headers.setContentDispositionFormData("attachment", "PhieuGiaoHang.pdf");
      FileSystemResource fileSystemResource = new FileSystemResource(expFile);
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    } catch (CloneNotSupportedException | SQLException | InterruptedException | IOException e) {
      return ResponseEntity.ok()
          .header("Content-Type", "application/json;charset=UTF-8")
          .body(gsonResponse.errorSystem(e, log));
    }
  }

  void createFile(String template, String loc) throws IOException, InterruptedException, SQLException, CloneNotSupportedException {
    StringBuffer html = new StringBuffer("<html>" +
        "<head>" +
        "<title>HTML to PDF</title>" +
        "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">" +
        "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
        "<link rel=\"stylesheet\" href=\"" + parameterDAO.getParameter("cssLocation") + "/style_delivery_note_print.css" + "\">" +
        "<meta charset=\"UTF-8\"></head>" +
        "<body>");
    html.append(template);
    html.append("</body></html>");
    WrapperConfig wc = new WrapperConfig(parameterDAO.getParameter("wkhtmlToPdfBin"));
//    wc = new WrapperConfig("C:\\Users\\Admin 88\\Downloads\\wkhtmltox\\bin\\wkhtmltopdf.exe");
    Pdf pdf = new Pdf(wc);
    pdf.addParam(new Param("--viewport-size", "1024x768"));
    pdf.addParam(new Param("--margin-left", "20"));
    pdf.addParam(new Param("--margin-top", "20"));
    pdf.addPageFromString(html.toString());
    pdf.saveAs(loc);
  }
}
