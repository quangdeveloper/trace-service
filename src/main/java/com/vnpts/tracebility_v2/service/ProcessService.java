package com.vnpts.tracebility_v2.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.dao.ProcessDAO;
import com.vnpts.tracebility_v2.model.ProcessReportModel;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.Constants;
import com.vnpts.tracebility_v2.util.ExcelUtil;
import com.vnpts.tracebility_v2.util.FileUtils;
import com.vnpts.tracebility_v2.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.rmi.CORBA.Util;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/process", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ProcessService {
    @Autowired
    private ProcessDAO processDAO;
    @Autowired
    private GsonResponse gsonResponse;
    @Autowired
    ParameterDAO parameterDAO;

    @RequestMapping(value = "/cbbType")
    @ResponseBody
    public String cbbType(@ModelAttribute("resSession") ResponseSession responseSession) {
        try {
            ResponseMap responseMap = new ResponseMap(processDAO.cbbType());
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/cbbProductToEdit")
    @ResponseBody
    public String cbbProductToEdit(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(processDAO.cbbProductForEdit(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    /** created by Quang*/
    @RequestMapping(value = "/cbbProductToEditCustom")
    @ResponseBody
    public String cbbProductToEditCustom(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            mapParams.put("pi_user_id", responseSession.getId());
            ResponseMap responseMap = new ResponseMap(processDAO.cbbProductForEditCustom(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/createProcess")
    @ResponseBody
    public String createProcess(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = processDAO.createProcess(mapParams);
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
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(processDAO.getByID(mapParams));
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
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(processDAO.getList(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/editProcess")
    @ResponseBody
    public String editProcess(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = processDAO.editProcess(mapParams);
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/publicOrNot")
    @ResponseBody
    public String publicOrNot(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(processDAO.publicOrNot(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/getCBB")
    @ResponseBody
    public String getCBB(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(processDAO.getCBB(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/getChildStageByParent")
    @ResponseBody
    public String getChildStageByParent(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            ResponseMap responseMap = new ResponseMap(processDAO.getChildStageByParent(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/downloadProcessProfile")
    public ResponseEntity<Object> downloadProcessProfile(
            @ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json
    ) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJson(json);
            String html = (String) mapParams.get("html");
            String fileName = parameterDAO.getParameter("exportLocation") + "process_profile/" +
                    FileUtils.generateImageName("pdf.pdf");
            createFile(html, fileName);
            File expFile = new File(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
            headers.set("Content-Transfer-Encoding", "binary");
            headers.setContentLength(expFile.length());
            headers.setContentDispositionFormData("attachment", "HoSoQuyTrinh.pdf");
            FileSystemResource fileSystemResource = new FileSystemResource(expFile);
            return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
        } catch (CloneNotSupportedException | SQLException | InterruptedException | IOException e) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(gsonResponse.errorSystem(e, log));
        }
    }

    @RequestMapping(value = "/listReport")
    @ResponseBody
    public ResponseEntity<Resource> listReport(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                               HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap res = new ResponseMap(processDAO.getList(mapParams));
            Map<String, List> resultMap = new LinkedHashMap<>();
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
//                saveDir = "D:\\traceability\\report\\";
//                tempDir = "D:\\traceability\\report\\template\\";
                FileUtils.createDir(tempDir);
                FileUtils.createDir(saveDir);
                List<Object> resultList = (List<Object>) res.getMap().get("rs");
                //nhom quy trinh thanh cac nhom bat buoc, co so, tu nhien
                for (int i = 0; i < resultList.size(); i++) {
                    Map<String, Object> entity = (Map<String, Object>) resultList.get(i);
                    ProcessReportModel model = new ProcessReportModel();
                    model.convertResultToModel(entity);
                    if (resultMap.get(model.getProcessType() + "-" + model.getProcessTypeName()) == null) {
                        resultMap.put(model.getProcessType() + "-" + model.getProcessTypeName(), new ArrayList());
                    }
                    resultMap.get(model.getProcessType() + "-" + model.getProcessTypeName()).add(model);
                }
                //xuat du lieu vao excel sau khi da nhom
                XSSFWorkbook workbook = new XSSFWorkbook(new File(tempDir + "report_process_list.xlsx"));
                XSSFSheet sheet = workbook.getSheetAt(0);
                int startRow = 3;
                int count = 1;
                for (String processType : resultMap.keySet()) {
                    ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 4);
                    ExcelUtil.setHeader(workbook, sheet, startRow, 0, Utils.toRoman(count) +
                            ". " + processType.split("-")[1]);
                    count++;
                    startRow++;
                    List<ProcessReportModel> reportList = resultMap.get(processType);
                    for (int i = 0; i < reportList.size(); i++) {
                        int column = 0;
                        ExcelUtil.setCellContent(workbook, sheet, startRow, column++, (i + 1));
                        ExcelUtil.setCellContent(workbook, sheet, startRow, column++, reportList.get(i).getName());
                        ExcelUtil.setCellContent(workbook, sheet, startRow, column++, reportList.get(i).getProductName());
                        ExcelUtil.setCellContent(workbook, sheet, startRow, column++, reportList.get(i).getDetail());
                        ExcelUtil.setCellContent(workbook, sheet, startRow, column++, reportList.get(i).getCreatedDate());
                        startRow++;
                    }
                }
                String nameSave = FileUtils.generateName() + ".xlsx";
                FileOutputStream out = new FileOutputStream(new File(saveDir + nameSave));
                workbook.write(out);
                return FileUtils.returnFile(request, saveDir, nameSave, "report_process_list.xlsx");
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    void createFile(String template, String loc) throws IOException, InterruptedException, SQLException, CloneNotSupportedException {
        StringBuffer html = new StringBuffer("<html>" +
                "<head>" +
                "<title>HTML to PDF</title>" +
                "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
                "<link rel=\"stylesheet\" href=\"" + parameterDAO.getParameter("cssLocation") + "/style_process_profile_print.css" + "\">" +
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
