package com.vnpts.tracebility_v2.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vnpts.tracebility_v2.response.ResponseMap;
import com.vnpts.tracebility_v2.response.user.ResponseObject;
import oracle.jdbc.pool.OracleDataSource;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.jdbc.JdbcTemplate;
import org.jdbc.lob.PassThroughClob;
import org.json.JSONArray;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * Created by VNT on 3/18/2017.
 */
public class Utils {
    static Logger logger = Logger.getLogger(Utils.class);
    private final static TreeMap<Integer, String> romanNumeralMap = new TreeMap<Integer, String>();

    static {
        romanNumeralMap.put(1000, "M");
        romanNumeralMap.put(900, "CM");
        romanNumeralMap.put(500, "D");
        romanNumeralMap.put(400, "CD");
        romanNumeralMap.put(100, "C");
        romanNumeralMap.put(90, "XC");
        romanNumeralMap.put(50, "L");
        romanNumeralMap.put(40, "XL");
        romanNumeralMap.put(10, "X");
        romanNumeralMap.put(9, "IX");
        romanNumeralMap.put(5, "V");
        romanNumeralMap.put(4, "IV");
        romanNumeralMap.put(1, "I");
    }

    public static DataSource getDataSource() throws IOException, SQLException {
        File configFile = new ClassPathResource("application.properties").getFile();
        InputStream stream = new FileInputStream(configFile);
        Properties prop = new Properties();
        prop.load(stream);
        OracleDataSource oracleDS = new OracleDataSource();
        oracleDS.setURL(prop.getProperty("db.url"));
        oracleDS.setUser(prop.getProperty("db.username"));
        oracleDS.setPassword(prop.getProperty("db.password"));
        return oracleDS;
    }

    public static Integer getInteger(Map map, String key) {
        Number inputNumber = null;
        try {
            inputNumber = NumberFormat.getInstance().parse(map.get(key).toString());
        } catch (ParseException e) {
            return null;
        }
        return inputNumber == null ? null : inputNumber.intValue();
    }

    public static Long getLong(Map map, String key) {
        Object num = map.get(key);
        return !(num instanceof Number) ? null : ((Number) num).longValue();
    }

    public static int getInt(Map map, String key) {
        BigDecimal bigDecimal = (BigDecimal) map.get(key);
        return bigDecimal == null ? 0 : bigDecimal.intValue();
    }

    public static JdbcTemplate getDB() throws IOException, SQLException {
        return new JdbcTemplate(getDataSource());
    }

    public static String getIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static Map<String, Object> fromJson(String json) {
        Gson gsonExt = new GsonBuilder().serializeNulls().create();
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gsonExt.fromJson(json, type);
        Map<String, Object> map1 = new HashMap<>();
        for (String key : map.keySet()) {
            map1.put("pi_" + key, map.get(key));
        }
        return map1;
    }

    public static String toJson(Object object) {
        ResponseObject response = new ResponseObject(object);
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public static String toJson(Map map) {
        ResponseMap response = new ResponseMap(map);
        Gson gson = new Gson();
        return gson.toJson(response);
    }

    public static String genUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public static String NVL(Object value) {
        return value == null ? "" : value.toString().trim();
    }

    public static Map imageToBase64(String filePath) throws IOException {
//        String pathLocation = mParameterDAO.getParameter(Constants.PARAMETER_IMAGE_LOCATION);
//        String filePath = pathLocation + mFileStorageConfig.getProductDir() + request.getAvatar();
        File file = new File(filePath);
        byte[] fileContent = FileUtils.readFileToByteArray(file);
        String encodedString = "data:image/jpg;base64," + Base64.getEncoder().encodeToString(fileContent);
        Map<String, Object> mapRsp = new HashMap<>();
        mapRsp.put("avatar", encodedString);
        mapRsp.put("po_code", "200");
        return mapRsp;
    }

    public static boolean isNvl(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName) {
        // application/pdf
        // application/xml
        // image/gif, ...
        String mineType = servletContext.getMimeType(fileName);
        try {
            return MediaType.parseMediaType(mineType);
        } catch (Exception e) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    //for file upload
    public static void saveUploadedFiles(String pathLocation, String fileName, List<MultipartFile> files) throws IOException, SQLException {
        File filePath = new File(pathLocation);
        if (!filePath.exists()) {
            FileUtils.forceMkdir(filePath);
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathLocation + File.separator + fileName);
            Files.write(path, bytes);
        }
    }

    public static String generatePassword() {
        String upperCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerrCharacters = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String specialCharacter = "$@!%#*?&";
        String allCharacter = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789$@!%#*?&";
        String pwd = RandomStringUtils.random(1, upperCharacters) + RandomStringUtils.random(1, lowerrCharacters) +
                RandomStringUtils.random(1, digits) + RandomStringUtils.random(1, specialCharacter)
                + RandomStringUtils.random(7, allCharacter);
        return pwd;
    }

    public static Map trimMap(Map data, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            data.put(columns[i], data.get(columns[i]) == null ? null : data.get(columns[i]).toString().trim());
        }
        return data;
    }

    public static Map noSignMap(Map data, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            data.put(columns[i], data.get(columns[i]) == null ? null : TotalConverter.toNoSign(data.get(columns[i]).toString()));
        }
        return data;
    }

    public static Map lowerMap(Map data, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            data.put(columns[i], data.get(columns[i]) == null ? null : data.get(columns[i]).toString().toLowerCase());
        }
        return data;
    }

    public static Map upperMap(Map data, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            data.put(columns[i], data.get(columns[i]) == null ? null : data.get(columns[i]).toString().toUpperCase());
        }
        return data;
    }

    public static Map changeClob(Map data, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            data.put(columns[i], new PassThroughClob(data.get(columns[i]) == null ? null : data.get(columns[i]).toString()));
        }
        return data;
    }

    public static Map toDateMap(Map data, String pattern, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            try {
                data.put(columns[i], TotalConverter.stringToDate((String) data.get(columns[i]), pattern));
            } catch (ParseException e) {
                data.put(columns[i], null);
            }
        }
        return data;
    }

    public static Map fillDefault(Map data, Object defaultVal, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            if (data.get(columns[i]) == null)
                data.put(columns[i], defaultVal);
        }
        return data;
    }

    public static Map changeArr(Map data, boolean isFirstSpr, boolean isLastSpr, String spr, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            String js = (String) data.get(columns[i]);
            JSONArray arr = new JSONArray(js);
            StringBuffer buffer = new StringBuffer();
            if (isFirstSpr) buffer.append(spr);
            for (int x = 0; x < arr.length() - 1; x++) {
                buffer.append(arr.get(x)).append(spr);
            }
            if (arr.length() > 0)
                buffer.append(arr.get(arr.length() - 1));
            if (isLastSpr) buffer.append(spr);
            data.put(columns[i], buffer.toString());
        }
        return data;
    }

    public static Map returnMap(String code) {
        Map map = new HashMap();
        map.put("PO_CODE", code);
        return map;
    }

    public static String toStrArr(int[] array) {
        if (array == null || array.length == 0) return "";
        StringBuffer s = new StringBuffer();
        for (int i = 0; i < array.length - 1; i++) s.append(array[i]).append(",");
        s.append(array[array.length - 1]);
        return s.toString();
    }

    public static String toRoman(int number) {
        int l = romanNumeralMap.floorKey(number);
        if (number == l) {
            return romanNumeralMap.get(number);
        }
        return romanNumeralMap.get(l) + toRoman(number - l);
    }
}
