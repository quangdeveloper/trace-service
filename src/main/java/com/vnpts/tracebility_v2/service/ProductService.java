package com.vnpts.tracebility_v2.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.dao.ProductDAO;
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
@RequestMapping(value = "/product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProductService {

  @Autowired
  private GsonResponse gsonResponse;
  @Autowired
  private ProductDAO productDAO;
  @Autowired
  ParameterDAO parameterDAO;

  @RequestMapping(value = "/createProduct")
  @ResponseBody
  public String createProduct(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    mapParams.putIfAbsent("pi_originID", 0);
    try {
      ResponseMap responseMap = new ResponseMap(productDAO.createProduct(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/cbbType")
  @ResponseBody
  public String cbbType(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(productDAO.cbbType(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/cbbOrigin")
  @ResponseBody
  public String cbbOrigin(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(productDAO.cbbOrigin(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getList")
  @ResponseBody
  public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(productDAO.getList(mapParams));
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
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(productDAO.getByID(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/nonAuthen/img", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getProductImg(@RequestParam("id") long id, @RequestParam("productID") long productID) {
    try {
      Map out = productDAO.getImg(id, productID);
      return FileUtils.returnFile((String) out.get("PO_LOC"), (String) out.get("PO_FILE"));
    } catch (Exception e) {
      return null;
    }
  }

  @RequestMapping(value = "/editProduct")
  @ResponseBody
  public String editProduct(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    mapParams.putIfAbsent("pi_originID", 0);
    try {
      ResponseMap responseMap = new ResponseMap(productDAO.editProduct(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/downloadProductProfile")
  public ResponseEntity<Object> downloadProductProfile(
      @ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json
  ) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJson(json);

      String fileName;
      //type <> null la phan ho so san pham cho duyet lenh in tem, = null la phan ho so cho quan ly san pham, ho so san pham
      if (!mapParams.containsKey("type")) {
        fileName = parameterDAO.getParameter("exportLocation") + "product_profile/" +
            FileUtils.generateImageName("pdf.pdf");
        createFile(mapParams, fileName, null);
      } else {
        fileName = parameterDAO.getParameter("exportLocation") + "product_profile_dynamic/" +
            FileUtils.generateImageName("pdf.pdf");
        createFile(mapParams, fileName, "style-print-command-product-profile.css");
      }
      File expFile = new File(fileName);
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
      headers.set("Content-Transfer-Encoding", "binary");
      headers.setContentLength(expFile.length());
      headers.setContentDispositionFormData("attachment", "HoSoSanPham.pdf");
      FileSystemResource fileSystemResource = new FileSystemResource(expFile);
      return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    } catch (CloneNotSupportedException | SQLException | InterruptedException | IOException e) {
      return ResponseEntity.ok()
          .header("Content-Type", "application/json;charset=UTF-8")
          .body(gsonResponse.errorSystem(e, log));
    }
  }

  void createFile(Map mapParams, String loc, String cssFile) throws IOException, InterruptedException, SQLException, CloneNotSupportedException {
    String pathCssFile = "";
    if (cssFile == null) {
      pathCssFile = parameterDAO.getParameter("styleProfilePrint");
    } else {
      pathCssFile = parameterDAO.getParameter("cssLocation") + cssFile;
    }
    String template = (String) mapParams.get("html");
    String height = (String) mapParams.get("height");
    template = template.replaceAll("/tracebility/", parameterDAO.getParameter("serverAddress") + ":8080/tracebility/");
    StringBuffer html = new StringBuffer("<html>" +
        "<head>" +
        "<title>HTML to PDF</title>" +
        "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">" +
        "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
        "<link rel=\"stylesheet\" href=\"" + pathCssFile + "\">" +
        "<meta charset=\"UTF-8\"></head>" +
        "<body>");
    html.append(template);
    html.append("</body></html>");
    WrapperConfig wc = new WrapperConfig(parameterDAO.getParameter("wkhtmlToPdfBin"));
//    wc = new WrapperConfig("C:\\Users\\Admin 88\\Downloads\\wkhtmltox\\bin\\wkhtmltopdf.exe");
    Pdf pdf = new Pdf(wc);
//    if (height != null && height != "") {
//      pdf.addParam(new Param("--page-width", "210mm"));
//      pdf.addParam(new Param("--page-height", height));
//    }
    pdf.addParam(new Param("--viewport-size", "1024x768"));
    pdf.addParam(new Param("--margin-left", "20"));
    pdf.addParam(new Param("--margin-top", "20"));
    pdf.addPageFromString(html.toString());
    pdf.saveAs(loc);
  }

  @RequestMapping(value = "/getProductProfileByPlan")
  @ResponseBody
  public String getProductProfileByPlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      ResponseMap responseMap = new ResponseMap(productDAO.getProductProfileByPlan(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getListByCompany")
  @ResponseBody
  public String getListByCompany(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      ResponseMap responseMap = new ResponseMap(productDAO.getListByCompany(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getProductCbb")
  @ResponseBody
  public String getProductCbb(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    try {
      Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
      mapParams.put("pi_user", responseSession.getUserID());
      mapParams.put("pi_typeUser", responseSession.getUserType());
      ResponseMap responseMap = new ResponseMap(productDAO.getProductCbb(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

}
