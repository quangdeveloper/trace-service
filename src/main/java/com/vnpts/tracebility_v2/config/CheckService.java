package com.vnpts.tracebility_v2.config;


import com.vnpts.tracebility_v2.dao.InitSVDAO;
import com.vnpts.tracebility_v2.util.TotalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CheckService {

    @Autowired
    private RedisInstance redisInstance;
    @Value("${checksv.skip_session}")
    private String URL_SKIP_CHECK_SESSION;
    @Value("${checksv.skip_function}")
    private String URL_SKIP_CHECK_FUNCTION;
    @Value("${checksv.is_check_role}")
    private boolean isCheckRole;

    @Autowired
    private InitSVDAO checkSv;

    public String getURL_SKIP_CHECK_SESSION() {
        return URL_SKIP_CHECK_SESSION;
    }

    public String getURL_SKIP_CHECK_FUNCTION() {
        return URL_SKIP_CHECK_FUNCTION;
    }

    @PostConstruct
    public void init() throws SQLException {
        List<Map> listPartner = checkSv.getAllPartner();
        listPartner.forEach(p -> {
            redisInstance.set("partnerKey_" + p.get("s_short_name"), (String) p.get("s_public_key"));
        });

    }

    public boolean checkPartner(String partner) {
        return redisInstance.has("partnerKey_" + partner);
    }

    public String getKeyPartner(String partner) {
        return redisInstance.get("partnerKey_" + partner);
    }

    public boolean isCheckRole() {
        return isCheckRole;
    }

    public void setCheckRole(boolean checkRole) {
        isCheckRole = checkRole;
    }

    public void setKeyUpload(String key) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 15);
        redisInstance.set("UploadKey_" + key, TotalConverter.toString(cal.getTime(), "yyyy/MM/dd HH:mm"));
    }

    public boolean checkUploadKey(String key) {
        String date = redisInstance.get("UploadKey_" + key);
        if (date == null) return false;
        try {
            Date date1 = TotalConverter.stringToDate(date, "yyyy/MM/dd HH:mm");
            if (date1.compareTo(new Date()) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }
    }
}
