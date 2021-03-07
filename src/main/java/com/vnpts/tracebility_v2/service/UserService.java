package com.vnpts.tracebility_v2.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.config.JWTConfig;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.dao.UserDAO;
import com.vnpts.tracebility_v2.model.EmailModel;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.Constants;
import com.vnpts.tracebility_v2.util.FileUtils;
import com.vnpts.tracebility_v2.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class UserService {
  @Autowired
  UserDAO userDAO;
  @Autowired
  ParameterDAO parameterDAO;
  @Autowired
  private GsonResponse gsonResponse;
  @Autowired
  private EmailService emailService;

  @RequestMapping(value = "/authen")
  @ResponseBody
  public String authen(@ModelAttribute("resSession") ResponseSession responseSession) {
    Map<String, Object> authenRes = new HashMap<>();
    authenRes.put("po_code", "200");
    authenRes.put("po_msg", "Ham thiet lap thong tin");
    ResponseMap authenResMap = new ResponseMap(authenRes);
    return gsonResponse.toJson(authenResMap);
  }

  @RequestMapping(value = "/login")
  @ResponseBody
  public String login(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJson(json);
    Map<String, Object> inParams = new HashMap<>();
    String password = mapParams.get("password").toString().trim();
    inParams.put("pi_user_id", mapParams.get("username"));
    inParams.put("pi_pass", password);
    if (password.length() > 100) {
      return gsonResponse.toJson(new ResponseMap("LONG_PASSWORD", "Password quá dài"));
    }
    try {
      Map out = userDAO.login(inParams);
      ResponseMap responseMap = new ResponseMap(out);
      if (!responseMap.isFail()) {
        String typeUser = (String) out.get("PO_TYPE_USER");
        if (!mapParams.get("partner").equals("web") && !typeUser.equals("ADMIN")) {
          String businessType = (String) out.get("PO_BUSINESS_TYPE");
          Integer isLogDiary = ((BigDecimal) out.get("PO_IS_LOG_DIARY")).intValue();
          List<String> businessTypeList = Arrays.asList(businessType.split(","));
          if (businessTypeList.isEmpty() || (!businessTypeList.contains("1") && !businessTypeList.contains("2") && isLogDiary <= 0)) {
            responseMap.setCode("APP_LOGIN_WRONG_BUSINESS_TYPE");
            return gsonResponse.toJson(responseMap);
          }
        }
        ResponseSession responseSession = new ResponseSession();
        responseSession.setUserID(((String) mapParams.get("username")).toLowerCase());
        responseSession.setId(out.get("PO_ID") == null ? null : ((BigDecimal) out.get("PO_ID")).intValue());
        responseSession.setUserType((String) out.get("PO_TYPE_USER"));
        responseSession.setAvatar((String) out.get("PO_AVATAR"));
        userDAO.setRole(responseSession);
        responseMap.getMap().put("PO_MENU", gsonResponse.toJson(responseSession.getMenuItems()));
        responseMap.getMap().put("PO_TOKEN", JWTConfig.genToken(responseSession));
        responseMap.getMap().put("PO_ROLE", responseSession.getUserRole());
        log.info(responseSession.toString());
      }
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/register")
  @ResponseBody
  public String register(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = userDAO.register(mapParams);
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/nonAuthen/activeUser")
  @ResponseBody
  public String activeUser(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJson(json);
    try {
      ResponseMap responseMap = new ResponseMap(userDAO.activateAccount(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getSelfProfile")
  @ResponseBody
  public String getSelfProfile(@ModelAttribute("resSession") ResponseSession responseSession) {
    try {
      ResponseMap responseMap = new ResponseMap(userDAO.getSelfProfile(responseSession.getUserID()));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/firebaseToken")
  @ResponseBody
  public String firebaseToken(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_user_id_number", responseSession.getId());
      mapParams.put("pi_type", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(userDAO.firebaseToken(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/changeInfo")
  @ResponseBody
  public String changeInfo(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user_id", responseSession.getUserID());
      return gsonResponse.toJson(new ResponseMap(userDAO.changeInfo(mapParams)));
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/changePassword")
  @ResponseBody
  public String changePass(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user_id", responseSession.getUserID());
      ResponseMap response = new ResponseMap(userDAO.changePass(mapParams));
      return gsonResponse.toJson(response);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/uploadAvatar")
  @ResponseBody
  public String uploadAvatar(@ModelAttribute("resSession") ResponseSession responseSession,
                             @RequestParam("file") MultipartFile uploadFile) {
    Map response = new HashMap();
    String userId = responseSession.getUserID();
    if (uploadFile.isEmpty()) {
      response.put("po_code", "UPLOAD_AVATAR_01");
      return gsonResponse.toJson(new ResponseMap(response));
    }
    String extension = FilenameUtils.getExtension(uploadFile.getOriginalFilename());
    if (extension == null || (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg")
        && !extension.equalsIgnoreCase("png"))) {
      response.put("po_code", "UPLOAD_AVATAR_02");
      return gsonResponse.toJson(new ResponseMap(response));
    }
    if (uploadFile.getSize() > 1024 * 1024) {
      response.put("po_code", "UPLOAD_AVATAR_03");
      return gsonResponse.toJson(new ResponseMap(response));
    }
    try {
      String pathLocation = parameterDAO.getParameter(Constants.PARAMETER_IMAGE_LOCATION);
      String dir = pathLocation + "avatar/" + responseSession.getId() + File.separator;
      File fileDir = new File(dir);
      if (!fileDir.exists()) {
        FileUtils.createDir(dir);
      }
      String encodeFileName = FileUtils.generateName();
      String fileName = encodeFileName + FileUtils.getExtensionName(uploadFile.getOriginalFilename());
      File file = new File(dir + fileName);
      uploadFile.transferTo(file);
      return gsonResponse.toJson(new ResponseMap(userDAO.changeAvatar(userId, fileName)));
    } catch (IOException | SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/resetPassword")
  @ResponseBody
  public String resetPassword(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      String password = Utils.generatePassword();
      mapParams.put("pi_password", password);
      Map resetPassword = userDAO.resetPassword(mapParams);
      ResponseMap responseMap = new ResponseMap(resetPassword);
      if (responseMap.getCode().equals("200")) {
        EmailModel model = new EmailModel();
        model.setTemplateName("forgotPassword.ftl");
        model.setSubject("Thay đổi mật khẩu hệ thống theo dõi và quản lý thông tin sản xuất tỉnh Cao Bằng");
        model.setReceiver(responseMap.getMap().get("PO_EMAIL").toString());
        model.setFrom("truyxuatcb@gmail.com");
        Map<String, String> params = new HashMap<>();
        params.put("username", responseMap.getMap().get("PO_USER_NAME").toString());
        params.put("userId", responseMap.getMap().get("PO_USER_ID").toString());
        params.put("password", password);
        model.setParams(params);
        this.emailService.sendEmail(model);
      }
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(
      value = "/getAvatar",
      produces = MediaType.IMAGE_PNG_VALUE
  )
  @ResponseBody
  public byte[] getAvatar(@RequestParam("userId") String userId) {
    try {
      String pathLocation = parameterDAO.getParameter(Constants.PARAMETER_IMAGE_LOCATION);
      Map profileRes = userDAO.getSelfProfile(userId);
      if (profileRes != null) {
        ArrayList profileList = (ArrayList) profileRes.get("PO_RS");
        Map profileMap = (Map) profileList.get(0);
        if (profileMap.containsKey("S_AVATAR")) {
          Path path = Paths.get(pathLocation + "avatar/" + profileMap.get("N_ID") + "/" + profileMap.get("S_AVATAR"));
          return Files.readAllBytes(path);
        }
      }
    } catch (Exception e) {
      return null;
    }
    return null;
  }

  @RequestMapping(value = "/logout")
  @ResponseBody
  public String logout(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      ResponseMap responseMap = new ResponseMap(userDAO.expToken(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/downloadProfile")
  public ResponseEntity<Object> downloadProfile(
      @ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json
  ) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJson(json);
      String html = (String) mapParams.get("html");
      String fileName = parameterDAO.getParameter("exportLocation") + "user_profile/" +
          FileUtils.generateImageName("pdf.pdf");
      createFile(html, fileName);
      File expFile = new File(fileName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
      headers.set("Content-Transfer-Encoding", "binary");
      headers.setContentLength(expFile.length());
      headers.setContentDispositionFormData("attachment", "HoSoDonVi.pdf");
      FileSystemResource fileSystemResource = new FileSystemResource(expFile);
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    } catch (CloneNotSupportedException | SQLException | InterruptedException | IOException e) {
      return ResponseEntity.ok()
          .header("Content-Type", "application/json;charset=UTF-8")
          .body(gsonResponse.errorSystem(e, log));
    }
  }

  void createFile(String template, String loc) throws IOException, InterruptedException, SQLException, CloneNotSupportedException {
    template = template.replaceAll("/tracebility/", parameterDAO.getParameter("serverAddress") + ":8080/tracebility/");
    StringBuffer html = new StringBuffer("<html>" +
        "<head>" +
        "<title>HTML to PDF</title>" +
        "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">" +
        "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
        "<link rel=\"stylesheet\" href=\"" + parameterDAO.getParameter("cssLocation") + "/style_user_profile_print.css" + "\">" +
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
