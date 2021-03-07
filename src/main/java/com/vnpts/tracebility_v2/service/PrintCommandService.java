package com.vnpts.tracebility_v2.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.dao.PrintCommandDAO;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.FileUtils;
import com.vnpts.tracebility_v2.util.StringUtils;
import com.vnpts.tracebility_v2.util.TotalConverter;
import com.vnpts.tracebility_v2.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/printCommand", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PrintCommandService {
  @Autowired
  private PrintCommandDAO printCommandDAO;

  @Autowired
  private GsonResponse gsonResponse;

  @Autowired
  private ParameterDAO mParameterDAO;

  @RequestMapping(value = "/createCommand")
  @ResponseBody
  public String create(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.create(mapParams));
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
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.findAll(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getByID")
  @ResponseBody
  public String findByID(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.findById(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/cbbProdPlanPrint")
  @ResponseBody
  public String cbbProdPlanPrint(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.cbbProdPlanPrint(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getProfileCommandMembers")
  @ResponseBody
  public String getProfileCommandMembers(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.getProfileCommandMembers(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getDiariesDetail")
  @ResponseBody
  public String getDiariesDetail(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.getDiariesDetail(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/printTemp")
  public ResponseEntity<Object> printTemp(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.printTemp(mapParams));
      if (!responseMap.isFail()) {
        String fileName = mParameterDAO.getParameter("saveFileTemp") + FileUtils.generateImageName("pdf.pdf");
        String bar = getBarcode((String)responseMap.getMap().get("PO_PRODUCTBARCODE"));
        createFile2((List<Map>) responseMap.getMap().get("rs"), (String) mapParams.get("pi_html"), Utils.getLong(mapParams, "pi_type"), fileName, bar);
        //download file here
        File expFile = new File(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
        headers.set("Content-Transfer-Encoding", "binary");
        headers.setContentLength(expFile.length());
        headers.setContentDispositionFormData("attachment", "fileInTem.pdf");
        FileSystemResource fileSystemResource = new FileSystemResource(expFile);
        return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
      }
      return ResponseEntity.ok()
          .header("Content-Type", "application/json;charset=UTF-8")
          .body(gsonResponse.toJson(responseMap));
    } catch (SQLException | CloneNotSupportedException | InterruptedException | IOException e) {
      return ResponseEntity.ok()
          .header("Content-Type", "application/json;charset=UTF-8")
          .body(gsonResponse.errorSystem(e, log));
    }
  }

  void createFile2(List<Map> data, String template, Long type, String loc, String bar) throws IOException, InterruptedException, SQLException, CloneNotSupportedException {
    String width, height, appendDiv;
    if (type == 1) {
      width = "100.19mm";
      height = "44.1mm";
      appendDiv = "<div class=\"total\">";
    } else {
      width = "26.11mm";
      height = "33.16mm";
      appendDiv = "<div class=\"total2\">";
    }
    int size = data.size();
    StringBuffer html = new StringBuffer("<html>" +
        "<head>" +
        "<title>HTML to PDF</title>" +
        "<link rel=\"stylesheet\" href=\""+ mParameterDAO.getParameter("stylePrint") +"\">" +
        "<meta charset=\"UTF-8\"></head>" +
        "<body>");
    for (int i = 0; i < size; i++) {
      html.append(appendDiv).append(createCell(data.get(i), template, bar)).append("</div>");
    }
    html.append("</body></html>");
    WrapperConfig wc = new WrapperConfig(mParameterDAO.getParameter("wkhtmlToPdfBin"));
    Pdf pdf = new Pdf(wc);
//    Pdf pdf = new Pdf();
    pdf.addPageFromString(html.toString());
    pdf.addParam(new Param("--page-width", width));
    pdf.addParam(new Param("--page-height", height));
    pdf.addParam(new Param("--disable-smart-shrinking"));
    pdf.addParam(new Param("--margin-top", "0"));
    pdf.addParam(new Param("--margin-bottom", "0"));
    pdf.addParam(new Param("--margin-left", "0"));
    pdf.addParam(new Param("--margin-right", "0"));
    pdf.saveAs(loc);
  }

  String createCell(Map data, String html, String bar) {
    HashMap hintMap = new HashMap();
    hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
    hintMap.put(EncodeHintType.MARGIN, 0);
    return html.replaceAll("\\{\\{qrImage\\}\\}", getQR(StringUtils.toQRLink((String) data.get("S_CODE")), hintMap))
        .replaceAll("\\{\\{serial\\}\\}", (String) data.get("S_SERIAL"))
        .replaceAll("\\{\\{barcodeImage\\}\\}", "data:image/png;base64," + bar)
        .replaceAll("\\{\\{indexHarvest\\}\\}", Utils.getLong(data, "N_INDEX").toString());
  }

  String getQR(String text, Map hintMap) {
    try {
      QRCodeWriter qrCodeWriter = new QRCodeWriter();
      BitMatrix bitMatrix;
      bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 80, 80, hintMap);
      ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
      MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
      byte[] pngData = pngOutputStream.toByteArray();
      return "data:image/png;base64," + TotalConverter.toStringBase64(pngData);
    } catch (WriterException | IOException e) {
      return "";
    }
  }

  String getBarcode(String barcode) {
    try {
      EAN13Bean bean = new EAN13Bean();
      bean.setHeight(5f);
      bean.setModuleWidth(0.08f);
      bean.setBarHeight(3.3f);
      bean.setFontSize(1f);
      BitmapCanvasProvider canvas = new BitmapCanvasProvider(
          null, "image/x-png", 600, BufferedImage.TYPE_INT_ARGB, false, 0);
      bean.generateBarcode(canvas, barcode);
      BufferedImage sub = canvas.getBufferedImage().getSubimage(58, 52, 200, 50);
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      ImageIO.write(sub, "png", bos);
      byte[] imageBytes = bos.toByteArray();
      BASE64Encoder encoder = new BASE64Encoder();
      String imageString = encoder.encode(imageBytes);
      bos.close();
      canvas.finish();
      sub.flush();
      return imageString;
    } catch (Exception e) {
      return "";
    }
  }

  @RequestMapping(value = "/sendCommand")
  @ResponseBody
  public String sendCommand(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.sendCommand(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/cancelCommand")
  @ResponseBody
  public String cancelCommand(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    mapParams.put("pi_user", responseSession.getUserID());
    mapParams.put("pi_user_id_number", responseSession.getId());
    mapParams.put("pi_typeUser", responseSession.getUserType());
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.cancelCommand(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/nonAuthen/getDiariesDetailFromQrCode")
  @ResponseBody
  public String getDiariesDetailFromQrCode(@RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.getDiariesDetailFromQrCode(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }

  @RequestMapping(value = "/getAllByProductionPlan")
  @ResponseBody
  public String getAllByProductionPlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
    Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
    try {
      ResponseMap responseMap = new ResponseMap(printCommandDAO.getAllByProductionPlan(mapParams));
      return gsonResponse.toJson(responseMap);
    } catch (SQLException | CloneNotSupportedException e) {
      return gsonResponse.errorSystem(e, log);
    }
  }
}
