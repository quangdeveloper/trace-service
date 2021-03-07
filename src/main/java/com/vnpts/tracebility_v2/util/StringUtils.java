package com.vnpts.tracebility_v2.util;


import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by thucv on 4/2/2017.
 */
public class StringUtils {
    public static boolean isEmpty(String inp) {
        return (inp == null || (inp.equals("")));
    }

    public static boolean checkSignature(String input, String signature) throws NoSuchAlgorithmException, UnsupportedEncodingException {
//        String date = TotalConverter.toString(new Date(), "dd/MM/yyyy");
//        return TotalConverter.toMd5(input + date).equals(signature);
        return true;
    }

    public static String toQRLink(String code) {
        code = code.trim();
        return Constants.BASE_URL + "/scan/#/" + code;
    }
}
