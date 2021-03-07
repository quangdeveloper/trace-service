package com.vnpts.tracebility_v2.util;

import com.vnpts.tracebility_v2.model.ImportError;
import com.vnpts.tracebility_v2.model.excelConfig.Column;
import com.vnpts.tracebility_v2.model.excelConfig.ExcelConfig;
import com.vnpts.tracebility_v2.model.excelConfig.Header;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static Cell setCellContent(Workbook wb, Sheet sheet, int rowIndex, int colIndex, Object value) {
        Cell c = setContent(rowIndex, colIndex, sheet, value);
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        style.setFont(font);
        addBorderStyle(style);
        c.setCellStyle(style);
        return c;
    }
    /** CREATED BY QUANG*/
    public static Cell setCellContentCustomForDateSearch(Workbook wb, Sheet sheet, int rowIndex, int colIndex, Object value) {
        Cell c = setContent(rowIndex, colIndex, sheet, value);
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        style.setFont(font);
        c.setCellStyle(style);
        return c;
    }
    /** CREATED BY QUANG*/
    public static Cell setCellContentBold(Workbook wb, Sheet sheet, int rowIndex, int colIndex, Object value) {
        Cell c = setContent(rowIndex, colIndex, sheet, value);
        CellStyle style = wb.createCellStyle();
        style.setWrapText(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        style.setFont(font);
        font.setBold(true);
        addBorderStyle(style);
        c.setCellStyle(style);
        return c;
    }

    public static void mergeAndBorder(Sheet sheet, int startRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(cellRangeAddress);
        RegionUtil.setBorderBottom(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, cellRangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, cellRangeAddress, sheet);
    }

    /**
     * Create by Quang
     */
    public static void merge(Sheet sheet, int startRow, int lastRow, int firstCol, int lastCol) {
        CellRangeAddress cellRangeAddress = new CellRangeAddress(startRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * Create by Quang
     */
    public static Cell setHeaderCentre(Workbook wb, Sheet sheet, int row, int col, Object value) {
        Cell c = setCellContent(wb, sheet, row, col, value);
        addBoldStyleToHeader(wb, c.getCellStyle());
        return c;
    }
    /**
     * Create by Quang
     */
    public static Cell setHeaderCentreForDateSearch(Workbook wb, Sheet sheet, int row, int col, Object value) {
        Cell c = setCellContentCustomForDateSearch(wb, sheet, row, col, value);
        addBoldStyleToHeader(wb, c.getCellStyle());
        return c;
    }

    public static Cell setHeader(Workbook wb, Sheet sheet, int row, int col, Object value) {
        Cell c = setCellContent(wb, sheet, row, col, value);
        addBoldStyleToCell(wb, c.getCellStyle());
        return c;
    }

    public static void addBorderStyle(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

    }

    public static void addBoldStyleToCell(Workbook wb, CellStyle style) {
        Font boldFont = wb.createFont();
        boldFont.setFontName("Times New Roman");
        boldFont.setBold(true);
        style.setFont(boldFont);

    }
    /** Create by Quang*/
    public static void addBoldStyleToHeader(Workbook wb, CellStyle style) {
        Font boldFont = wb.createFont();
        boldFont.setFontName("Times New Roman");
        boldFont.setBold(true);
        boldFont.setFontHeightInPoints((short)14);
        style.setFont(boldFont);
        style.setAlignment(HorizontalAlignment.CENTER);
    }

    public static Cell setContent(int rowIndex, int colIndex, Sheet sheet, Object value) {

        CellReference ref = new CellReference(rowIndex, colIndex);
        Row r = sheet.getRow(ref.getRow());
        if (r == null) {
            r = sheet.createRow(rowIndex);
        }
        Cell c = r.getCell(ref.getCol());
        if (c == null) {
            c = r.createCell(colIndex);
        }
        if (value instanceof String) {
            c.setCellValue(Utils.NVL(value));
        } else if (value instanceof Integer || value instanceof Long || value instanceof Short) {
            if (value != null) {
                long longValue = ((Number) value).longValue();
                c.setCellValue(longValue);
            } else {
                c.setCellValue("");
            }
        } else if (value instanceof Double || value instanceof Float) {
            if (value != null) {
                float floatValue = ((Number) value).longValue();
                c.setCellValue(floatValue);
            } else {
                c.setCellValue("");
            }
        } else {
            c.setCellValue(1);
        }
        return c;
    }

    public static boolean checkRowData(Sheet sheet, int rowIndex) {
        Row r = sheet.getRow(rowIndex);
        return r != null;
    }

    public static void setCellVal(Sheet sheet, int row, int col, Object value, CellStyle style) {
        Row r = sheet.getRow(row);
        if (r == null) r = sheet.createRow(row);
        Cell c = r.getCell(col);
        if (c == null) c = r.createCell(col);
        if (value instanceof String) {
            c.setCellValue(Utils.NVL(value));
        } else if (value instanceof Number) {
            double val2 = ((Number) value).doubleValue();
            c.setCellValue(new BigDecimal(val2).doubleValue());
            c.setCellValue(val2);
        }
        if (style != null) {
            c.setCellStyle(style);
        }
    }

    public static String convertCellContentToString(Sheet sheet, int rowIndex, int colIndex) {
        CellReference ref = new CellReference(rowIndex, colIndex);
        Row r = sheet.getRow(ref.getRow());
        if (r == null) {
            return null;
        }
        Cell c = r.getCell(ref.getCol());
        if (c == null) {
            return null;
        }
        if (c.getCellTypeEnum() == CellType.NUMERIC) {
            return "" + c.getNumericCellValue();
        } else if (c.getCellTypeEnum() == CellType.STRING) {
            return "" + c.getStringCellValue().trim();
        } else if (c.getCellTypeEnum() == CellType.FORMULA) {
            return "" + c.getCellFormula().trim();
        } else {
            return "";
        }
    }

    public static void validateCellContent(Sheet sheet, int rowIndex, int colIndex, List<ImportError> importErrorList,
                                           String typeErrorCheck, Object... paramObject) {
        String cellContent = convertCellContentToString(sheet, rowIndex, colIndex);
        switch (typeErrorCheck) {
            case "required":
                if (cellContent == null || cellContent.trim().isEmpty()) {
                    importErrorList.add(createImportError(rowIndex, colIndex, ImportError.REQUIRED));
                }
                break;
            case "minLength":
                Long minLength = (Long) paramObject[0];
                if (cellContent == null || cellContent.trim().length() < minLength) {
                    importErrorList.add(createImportError(rowIndex, colIndex, ImportError.MINLENGTH, minLength));
                }
                break;
            case "maxLength":
                Long maxLength = (Long) paramObject[0];
                if (cellContent == null || cellContent.trim().length() > maxLength) {
                    importErrorList.add(createImportError(rowIndex, colIndex, ImportError.MAXLENGTH, maxLength));
                }
                break;
            default:
                break;
        }
    }

    public static void validateNotEqual(Sheet sheet, int rowIndex, int colIndex1, int colIndex2, List<ImportError> importErrorList) {
        String value1 = convertCellContentToString(sheet, rowIndex, colIndex1);
        String value2 = convertCellContentToString(sheet, rowIndex, colIndex2);
        if (value1 != null && value2 != null && value1.equalsIgnoreCase(value2)) {
            importErrorList.add(createImportError(rowIndex, colIndex2, ImportError.CAN_NOT_EQUAL, CellReference.convertNumToColString(colIndex1)));
        }
    }

    public static ImportError createImportError(int rowIndex, int colIndex, String content, Object... paramObject) {
        ImportError error = new ImportError();
        error.setRow((rowIndex + 1) + "");
        error.setColumn(CellReference.convertNumToColString(colIndex));
        error.setCode(content);
        if (paramObject.length > 0) {
            error.setAdditionalParam(paramObject[0].toString());
        }
        return error;
    }

    public static String createExcelFile(Map in, String dirTemp, ExcelConfig excelConfig, String dirSave) throws IOException, InvalidFormatException {
        FileUtils.createDir(dirTemp);
        FileUtils.createDir(dirSave);
        XSSFWorkbook workbook = new XSSFWorkbook(new File(dirTemp + excelConfig.getTemplateFile()));
        CellStyle allBorderStyle = workbook.createCellStyle();
        allBorderStyle.setBorderBottom(BorderStyle.THIN);
        allBorderStyle.setBorderTop(BorderStyle.THIN);
        allBorderStyle.setBorderRight(BorderStyle.THIN);
        allBorderStyle.setBorderLeft(BorderStyle.THIN);
        allBorderStyle.setWrapText(true);
        Font font = workbook.createFont();
        font.setFontName("Times New Roman");
        allBorderStyle.setFont(font);
        XSSFSheet sheet = workbook.getSheetAt(0);
        if (excelConfig.getListHeader() != null) {
            for (Header header : excelConfig.getListHeader()) {
                Object data = in.get(header.getColumnsName());
                if (data != null || header.getDefaultText() != null) {
                    setCellVal(sheet, header.getRow(), header.getCol(), header.getHeaderText() + ": " + (data == null ? header.getDefaultText() : data), null);
                }
            }
        }
        List<Map> tableData = (List<Map>) in.get("rs");
        int row = excelConfig.getData().getRow();
        Map<Integer, Integer> mergeMap = new HashMap();
        Map lastMap = new HashMap();
        for (Map map : tableData) {
            int col = excelConfig.getData().getCol();
            int minLv = 10000;
            for (Column cl : excelConfig.getData().getColumns()) {
                Object dataCell = map.get(cl.getName());
                Object dc = dataCell;
                if (cl.getFormat() != null) dc = TotalConverter.toString((Date) dataCell, cl.getFormat());
                setCellVal(sheet, row, col, dc, allBorderStyle);
                Object lastCell = lastMap.get(cl.getName());
                if (cl.getGroupLevel() > 0) {
                    if (minLv < cl.getGroupLevel() || !equals(dataCell, lastCell)) {
                        if (mergeMap.get(col) != null && mergeMap.get(col) != row - 1)
                            sheet.addMergedRegion(new CellRangeAddress(mergeMap.get(col), row - 1, col, col));
                        mergeMap.put(col, row);
                        if (minLv > cl.getGroupLevel()) minLv = cl.getGroupLevel();
                    }
                }
                col++;
            }
            row++;
            lastMap = map;
        }
        int col = excelConfig.getData().getCol();
        for (Column cl : excelConfig.getData().getColumns()) {
            if (cl.getGroupLevel() > 0 && mergeMap.get(col) != null && mergeMap.get(col) != row - 1)
                sheet.addMergedRegion(new CellRangeAddress(mergeMap.get(col), row - 1, col, col));
            col++;
        }
        String nameSave = FileUtils.generateName() + ".xlsx";
        FileOutputStream out = new FileOutputStream(new File(dirSave + nameSave));
        workbook.write(out);
        out.close();
        return nameSave;
    }

    static boolean equals(Object o1, Object o2) {
        if (o1 == null || o2 == null) return false;
        if (o1 instanceof String) return o1.equals(o2);
        else if (o1 instanceof Number)
            return (o2 instanceof Number) && ((Number) o1).floatValue() == ((Number) o2).floatValue();
        else if (o1 instanceof Date)
            return o2 instanceof Date && ((Date) o1).compareTo((Date) o2) == 0;
        else return o1 == o2;
    }
}
