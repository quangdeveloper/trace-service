package com.vnpts.tracebility_v2.service;

import com.vnpts.tracebility_v2.config.GsonResponse;
import com.vnpts.tracebility_v2.dao.ParameterDAO;
import com.vnpts.tracebility_v2.dao.ReportDAO;
import com.vnpts.tracebility_v2.model.ProductByTimeReportModel;
import com.vnpts.tracebility_v2.model.ProductionHarvestEstimatedReportModel;
import com.vnpts.tracebility_v2.model.UsedTempReportModel;
import com.vnpts.tracebility_v2.model.excelConfig.ExcelConfig;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.ResponseSession;
import com.vnpts.tracebility_v2.util.Constants;
import com.vnpts.tracebility_v2.util.ExcelUtil;
import com.vnpts.tracebility_v2.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@SessionAttributes("resSession")
@RequestMapping(value = "/report", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class ReportService {
    @Autowired
    private GsonResponse gsonResponse;

    @Autowired
    private ReportDAO reportDAO;

    @Autowired
    private ParameterDAO parameterDAO;

    @RequestMapping(value = "/report01")
    @ResponseBody
    public String report01(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id_number", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.report01(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/cbbOwnerPlan")
    @ResponseBody
    public String cbbOwnerPlan(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id_number", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.cbbOwnerPlan(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/cbbOwnerCompany")
    @ResponseBody
    public String cbbOwnerCompany(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id_number", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.cbbOwnerCompany(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/report02")
    @ResponseBody
    public String report02(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id_number", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.report02(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/exportReport01")
    @ResponseBody
    public ResponseEntity<Resource> exportReport01(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                                   HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id_number", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap res = new ResponseMap(reportDAO.report01(mapParams));
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
                ExcelConfig config = FileUtils.loadYamlFile(Constants.CONFIG_REPORT_01, ExcelConfig.class);
                String fileExcel = ExcelUtil.createExcelFile(res.getMap(), tempDir, config, saveDir);
                return FileUtils.returnFile(request, saveDir, fileExcel, "baocao1.xlsx");
            }
        } catch (IOException ex) {
            log.info("Could not determine file type.");
            ex.printStackTrace();
        } catch (CloneNotSupportedException | SQLException | InvalidFormatException ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/exportReport02")
    @ResponseBody
    public ResponseEntity<Resource> exportReport02(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                                   HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_user_id_number", responseSession.getId());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap res = new ResponseMap(reportDAO.report02(mapParams));
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
                ExcelConfig config = FileUtils.loadYamlFile(Constants.CONFIG_REPORT_02, ExcelConfig.class);
                String fileExcel = ExcelUtil.createExcelFile(res.getMap(), tempDir, config, saveDir);
                return FileUtils.returnFile(request, saveDir, fileExcel, "baocao2.xlsx");
            }
        } catch (IOException ex) {
            log.info("Could not determine file type.");
            ex.printStackTrace();
        } catch (CloneNotSupportedException | SQLException | InvalidFormatException ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/report03")
    @ResponseBody
    public String report03(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.report03(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/exportReport03")
    @ResponseBody
    public ResponseEntity<Resource> exportReport03(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                                   HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            String dateSearch = getSearchDateContent(mapParams);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap res = new ResponseMap(reportDAO.report03(mapParams));
            String resVolTonTotal = res.getMap().get("PO_EXP_VOL_TON_TOTAL") != null ? res.getMap().get("PO_EXP_VOL_TON_TOTAL").toString() : "0";
            Double volTonTotal = Double.parseDouble(resVolTonTotal);
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
                saveDir = "D:\\traceability\\report\\";
                tempDir = "D:\\traceability\\report\\template\\";
                FileUtils.createDir(tempDir);
                FileUtils.createDir(saveDir);
                List<Object> resultList = (List<Object>) res.getMap().get("rs");
                //nhom du lieu
                Map<String, Map<String, List>> resultMap = new HashMap<>();
                for (int i = 0; i < resultList.size(); i++) {
                    Map<String, Object> entity = (Map<String, Object>) resultList.get(i);
                    ProductionHarvestEstimatedReportModel model = new ProductionHarvestEstimatedReportModel();
                    model.convertResultToModel(entity);
                    Map<String, List> companyMap = resultMap.get(model.getCompanyId() + "-" + model.getCompanyName());
                    if (companyMap == null) {
                        companyMap = new HashMap<>();
                        resultMap.put(model.getCompanyId() + "-" + model.getCompanyName(), companyMap);
                    }
                    if (companyMap.get(model.getProductionPlanId() + "-" + model.getProductionPlanName()) == null) {
                        companyMap.put(model.getProductionPlanId() + "-" + model.getProductionPlanName(), new ArrayList<>());
                    }
                    companyMap.get(model.getProductionPlanId() + "-" + model.getProductionPlanName()).add(model);
                }
                //xuat du lieu vao excel sau khi da lay du lieu
                XSSFWorkbook workbook = new XSSFWorkbook(new File(tempDir + "report03.xlsx"));
                XSSFSheet sheet = workbook.getSheetAt(0);
                ExcelUtil.setContent(1, 0, sheet, dateSearch);
                int startRow = 5;
                for (String companyInfo : resultMap.keySet()) {
                    ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 4);
                    ExcelUtil.setHeader(workbook, sheet, startRow, 0, companyInfo.split("-")[1]);
                    startRow++;
                    for (String planInfo : resultMap.get(companyInfo).keySet()) {
                        List<ProductionHarvestEstimatedReportModel> boList = resultMap.get(companyInfo).get(planInfo);
                        ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 4);
                        ExcelUtil.setHeader(workbook, sheet, startRow, 0, planInfo.split("-")[1]);
                        startRow++;
                        for (int i = 0; i < boList.size(); i++) {
                            int column = 0;
                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, (i + 1));
                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getProductName());
                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getProductionSectorName());
                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getVolumeTon());
                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getPlannedHarvestDate());
                            startRow++;
                        }
                    }
                }
                ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 3);
                Cell totalCell = ExcelUtil.setHeader(workbook, sheet, startRow, 0, "TỔNG");
                totalCell.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
                ExcelUtil.setHeader(workbook, sheet, startRow, 4, volTonTotal);
                String nameSave = FileUtils.generateName() + ".xlsx";
                FileOutputStream out = new FileOutputStream(new File(saveDir + nameSave));
                workbook.write(out);
                return FileUtils.returnFile(request, saveDir, nameSave, "report04.xlsx");
            }
        } catch (IOException ex) {
            log.info("Could not determine file type.");
            ex.printStackTrace();
        } catch (CloneNotSupportedException | SQLException | InvalidFormatException ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/cbbProductByCompany")
    @ResponseBody
    public String cbbProductByCompany(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.cbbProductByCompany(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/report04")
    @ResponseBody
    public String report04(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.report04(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/exportReport04")
    @ResponseBody
    public ResponseEntity<Resource> exportReport04(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                                   HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            String dateSearch = getSearchDateContent(mapParams);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap res = new ResponseMap(reportDAO.report04(mapParams));
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
//                saveDir = "D:\\traceability\\report\\";
//                tempDir = "D:\\traceability\\report\\template\\";
                FileUtils.createDir(tempDir);
                FileUtils.createDir(saveDir);
                List<Object> resultList = (List<Object>) res.getMap().get("rs");
                String resTempTotal = res.getMap().get("PO_TEMP_TOTAL") != null ? res.getMap().get("PO_TEMP_TOTAL").toString() : "0";
                Integer tempTotal = Integer.parseInt(resTempTotal);
                //xuat du lieu vao excel sau khi da lay du lieu
                XSSFWorkbook workbook = new XSSFWorkbook(new File(tempDir + "report04.xlsx"));
                XSSFSheet sheet = workbook.getSheetAt(0);
                ExcelUtil.setContent(1, 0, sheet, dateSearch);
                int startRow = 4;
                for (int i = 0; i < resultList.size(); i++) {
                    Map<String, Object> entity = (Map<String, Object>) resultList.get(i);
                    UsedTempReportModel model = new UsedTempReportModel();
                    model.convertResultToModel(entity);
                    int column = 0;
                    ExcelUtil.setCellContent(workbook, sheet, startRow, column++, (i + 1));
                    ExcelUtil.setCellContent(workbook, sheet, startRow, column++, model.getCompanyName());
                    ExcelUtil.setCellContent(workbook, sheet, startRow, column++, model.getProductionPlanName());
                    ExcelUtil.setCellContent(workbook, sheet, startRow, column++, model.getHarvestName());
                    ExcelUtil.setCellContent(workbook, sheet, startRow, column++, model.getPrintCommandCode());
                    ExcelUtil.setCellContent(workbook, sheet, startRow, column++, model.getTempNum());
                    startRow++;
                }
                ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 4);
                Cell totalCell = ExcelUtil.setHeader(workbook, sheet, startRow, 0, "TỔNG");
                totalCell.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
                ExcelUtil.setHeader(workbook, sheet, startRow, 5, tempTotal);
                String nameSave = FileUtils.generateName() + ".xlsx";
                FileOutputStream out = new FileOutputStream(new File(saveDir + nameSave));
                workbook.write(out);
                return FileUtils.returnFile(request, saveDir, nameSave, "report04.xlsx");
            }
        } catch (IOException ex) {
            log.info("Could not determine file type.");
            ex.printStackTrace();
        } catch (CloneNotSupportedException | SQLException | InvalidFormatException ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    @RequestMapping(value = "/cbbPlanByCompany")
    @ResponseBody
    public String cbbPlanByCompany(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.cbbPlanByCompany(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/searchProductByTime")
    @ResponseBody
    public String searchProductByTime(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap responseMap = new ResponseMap(reportDAO.searchProductByTime(mapParams));
            return gsonResponse.toJson(responseMap);
        } catch (SQLException | CloneNotSupportedException e) {
            return gsonResponse.errorSystem(e, log);
        }
    }

    @RequestMapping(value = "/exportReportProductByTime")
    @ResponseBody
    public ResponseEntity<Resource> exportReportProductByTime(@ModelAttribute("resSession") ResponseSession responseSession, @RequestBody String json,
                                                              HttpServletRequest request) {
        try {
            Map<String, Object> mapParams = gsonResponse.fromJsonPi(json);
            String dateSearch = getSearchDateContent(mapParams);
            mapParams.put("pi_user", responseSession.getUserID());
            mapParams.put("pi_typeUser", responseSession.getUserType());
            ResponseMap res = new ResponseMap(reportDAO.reportProductByTime(mapParams));
            if (!res.isFail()) {
                String saveDir = parameterDAO.getParameter(Constants.GEN_SAVE);
                String tempDir = parameterDAO.getParameter(Constants.TEMPLATE_SAVE);
                saveDir = "D:\\traceability\\report\\";
                tempDir = "D:\\traceability\\report\\template\\";
                FileUtils.createDir(tempDir);
                FileUtils.createDir(saveDir);
                List<Object> resultList = (List<Object>) res.getMap().get("rs");
                //nhom du lieu
                Map<String, Map> searchMap = new HashMap<>();
                List<ProductByTimeReportModel> reportList = new ArrayList<>();
                Map<Long, Double> amountMap = new HashMap<>();
                Map<Long, Long> planOrderMap = new HashMap<>();
                Map<String, String> productOrderMap = new HashMap<>();
                long countType1 = 0;
                long countRow = 0;
                for (int i = 0; i < resultList.size(); i++) {
                    Map<String, Object> entity = (Map<String, Object>) resultList.get(i);
                    ProductByTimeReportModel model = new ProductByTimeReportModel();
                    model.convertResultToModel(entity);
                    Long rootProductId = model.getRootProductId();
                    String rootProductName = model.getRootProductName();
                    Long productionPlanId = model.getProductionPlanId();
                    String productionPlanName = model.getProductionPlanName();
                    Double amount = model.getAmount();
                    Map childMap = searchMap.get(rootProductId + '|' + rootProductName);
                    if (childMap == null) {
                        childMap = new HashMap();
                        searchMap.put(rootProductId + "|" + rootProductName, childMap);
                        amountMap.put(rootProductId, 0D);
                        countType1++;
                        ProductByTimeReportModel productRootModel = new ProductByTimeReportModel();
                        productRootModel.setType(1L);
                        productRootModel.setRootProductId(rootProductId);
                        productRootModel.setRootProductName(rootProductName);
                        productRootModel.setOrder("" + countType1);
                        reportList.add(productRootModel);
                        planOrderMap.put(rootProductId, 0L);
                    }
                    amountMap.put(rootProductId, amountMap.get(rootProductId) + amount);
                    List childList = (List) childMap.get(productionPlanId + "|" + productionPlanName);
                    if (childList == null) {
                        childList = new ArrayList();
                        childMap.put(productionPlanId + "|" + productionPlanName, childList);
                        ProductByTimeReportModel planModel = new ProductByTimeReportModel();
                        planModel.setType(2L);
                        planModel.setProductionPlanName(productionPlanName);
                        planModel.setOrder(countType1 + "." + (planOrderMap.get(rootProductId) + 1));
                        reportList.add(planModel);
                        productOrderMap.put(rootProductId + "|" + productionPlanId, countType1 + "|" + (planOrderMap.get(rootProductId) + 1));
                        planOrderMap.put(rootProductId, planOrderMap.get(rootProductId) + 1);
                        countRow = 0;
                    }
                    countRow++;
                    model.setOrder(productOrderMap.get(rootProductId + "|" + productionPlanId) + '.' + countRow);
                    childList.add(model);
                    reportList.add(model);

                }
                //xuat du lieu vao excel sau khi da lay du lieu
//                XSSFWorkbook workbook = new XSSFWorkbook(new File(tempDir + "reportProductByTime.xlsx"));
//                XSSFSheet sheet = workbook.getSheetAt(0);
//                ExcelUtil.setContent(1, 0, sheet, dateSearch);
//                int startRow = 5;
//                for (String companyInfo : resultMap.keySet()) {
//                    ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 4);
//                    ExcelUtil.setHeader(workbook, sheet, startRow, 0, companyInfo.split("-")[1]);
//                    startRow++;
//                    for (String planInfo : resultMap.get(companyInfo).keySet()) {
//                        List<ProductionHarvestEstimatedReportModel> boList = resultMap.get(companyInfo).get(planInfo);
//                        ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 4);
//                        ExcelUtil.setHeader(workbook, sheet, startRow, 0, planInfo.split("-")[1]);
//                        startRow++;
//                        for (int i = 0; i < boList.size(); i++) {
//                            int column = 0;
//                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, (i + 1));
//                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getProductName());
//                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getProductionSectorName());
//                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getVolumeTon());
//                            ExcelUtil.setCellContent(workbook, sheet, startRow, column++, boList.get(i).getPlannedHarvestDate());
//                            startRow++;
//                        }
//                    }
//                }
//                ExcelUtil.mergeAndBorder(sheet, startRow, startRow, 0, 3);
//                Cell totalCell = ExcelUtil.setHeader(workbook, sheet, startRow, 0, "TỔNG");
//                totalCell.getCellStyle().setAlignment(HorizontalAlignment.RIGHT);
//                ExcelUtil.setHeader(workbook, sheet, startRow, 4, volTonTotal);
//                String nameSave = FileUtils.generateName() + ".xlsx";
//                FileOutputStream out = new FileOutputStream(new File(saveDir + nameSave));
//                workbook.write(out);
//                return FileUtils.returnFile(request, saveDir, nameSave, "reportProductByTime.xlsx");
                return null;
            }
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return ResponseEntity.notFound().build();
    }

    public String getSearchDateContent(Map mapParams) {
        String fromDate = mapParams.get("pi_fromDate") != null ? mapParams.get("pi_fromDate").toString() : "";
        String toDate = mapParams.get("pi_toDate") != null ? mapParams.get("pi_toDate").toString() : "";
        String dateSearch = "";
        if (fromDate == null || fromDate.equals("")) {
            dateSearch = "từ ... đến ";
        } else {
            dateSearch = "từ " + fromDate + " đến ";
        }
        if (toDate == null || toDate.equals("")) {
            dateSearch = dateSearch + "...";
        } else {
            dateSearch = dateSearch + toDate;
        }
        return dateSearch;
    }
}
