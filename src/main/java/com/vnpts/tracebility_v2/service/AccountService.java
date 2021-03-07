package com.vnpts.tracebility_v2.service;

import com.github.jhonnymertz.wkhtmltopdf.wrapper.Pdf;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.configurations.WrapperConfig;
import com.github.jhonnymertz.wkhtmltopdf.wrapper.params.Param;
import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.AccountDAO;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.model.excelConfig.ExcelConfig;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.response.user.ResponseObject;
import com.vnpts.tracebility_v2.util.Constants;
import com.vnpts.tracebility_v2.util.ExcelUtil;
import com.vnpts.tracebility_v2.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/account", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Slf4j
public class AccountService {

    @Autowired
    AccountDAO accountDAO;
    @Autowired
    private GsonResponse gsonResponse;
    @Autowired
    ParameterDAO parameterDAO;

    @RequestMapping(value = "/getAdminList")
    @ResponseBody
    public String getList(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            ResponseMap response = new ResponseMap(accountDAO.getAdminList(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/getAllRole")
    @ResponseBody
    public String getAllRole() {
        try {
            return gsonResponse.toJson(new ResponseObject(accountDAO.getAllRole()));
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/createUser")
    @ResponseBody
    public String createUser(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            ResponseMap response = new ResponseMap(accountDAO.createUser(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/getByID")
    @ResponseBody
    public String getByID(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            ResponseMap response = new ResponseMap(accountDAO.getByID(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/editUser")
    @ResponseBody
    public String editUser(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id", responseSession.getId());
            ResponseMap response = new ResponseMap(accountDAO.editUser(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/q_deleteUser")
    @ResponseBody
    public String deleteUserById(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_userId", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            mapParams.put("pi_userIdDelete",mapParams.get("pi_id"));
            ResponseMap response = new ResponseMap(accountDAO.deleteUserById(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/resetAdminPass")
    @ResponseBody
    public String resetAdminPass(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            ResponseMap response = new ResponseMap(accountDAO.resetPassByAdmin(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/getCustomerListInPlan")
    @ResponseBody
    public String getCustomerListInPlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_userId", responseSession.getUserID());
            ResponseMap response = new ResponseMap(accountDAO.getCustomerListInPlan(mapParams));
            return gsonResponse.toJson(response);
        } catch (CloneNotSupportedException | SQLException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }


    @RequestMapping(value = "/downloadAccountProfile")
    public ResponseEntity<Object> downloadAccountProfile(
            @ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json
    ) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJson(json);
            String html = (String) mapParams.get("html");
            String fileName = parameterDAO.getParameter("exportLocation") + "account_profile/" +
                    FileUtils.generateImageName("pdf.pdf");
            createFile(html, fileName, "style_account_profile_print.css");
            File expFile = new File(fileName);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/octet-stream"));
            headers.set("Content-Transfer-Encoding", "binary");
            headers.setContentLength(expFile.length());
            headers.setContentDispositionFormData("attachment", "HoSoNguoiDung.pdf");
            FileSystemResource fileSystemResource = new FileSystemResource(expFile);
            return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
        } catch (CloneNotSupportedException | SQLException | InterruptedException | IOException e) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .body(gsonResponse.errorSystem(e, log));
        }
    }

    @RequestMapping(value = "/downloadCustomerInPlanProfile")
    public ResponseEntity<Object> downloadCustomerInPlanProfile(
            @ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json
    ) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJson(json);
            String html = (String) mapParams.get("html");
            String fileName = parameterDAO.getParameter("exportLocation") + "customer_in_plan_profile/" +
                    FileUtils.generateImageName("pdf.pdf");
            createFile(html, fileName, "style_customer_in_plan_profile_print.css");
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

    void createFile(String template, String loc, String cssFileName) throws IOException, InterruptedException, SQLException, CloneNotSupportedException {
        StringBuffer html = new StringBuffer("<html>" +
                "<head>" +
                "<title>HTML to PDF</title>" +
                "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.7.0/css/all.css\" integrity=\"sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ\" crossorigin=\"anonymous\">" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
                "<link rel=\"stylesheet\" href=\"" + parameterDAO.getParameter("cssLocation") + "/" + cssFileName + "\">" +
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

    @RequestMapping(value = "/listReport")
    @ResponseBody
    public ResponseEntity<Resource> listReport(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                               HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            ResponseMap res = new ResponseMap(accountDAO.getAdminList(mapParams));
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
//                saveDir = "D:\\traceability\\report\\";
//                tempDir = "D:\\traceability\\report\\template\\";
                ExcelConfig config = FileUtils.loadYamlFile(Constants.REPORT_ACCOUNT_LIST, ExcelConfig.class);
                String fileExcel = ExcelUtil.createExcelFile(res.getMap(), tempDir, config, saveDir);
                return FileUtils.returnFile(request, saveDir, fileExcel, "report_account_list.xlsx");
            }
        } catch (IOException ex) {
            log.info("Could not determine file type.");
            ex.printStackTrace();
        } catch (CloneNotSupportedException | SQLException | InvalidFormatException ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }
}
