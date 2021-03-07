package com.vnpts.tracebility_v2.util;

import org.apache.commons.io.IOUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TotalConverter {
  private static final String UNICODE = "áàảãạăắằẳẵặâấầẩẫậđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ";
  private static final String NOSIGN_CONST = "aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY";
  private static char[] NOSIGN = new char["aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY".length()];
  public static final byte[] EBCDIC2ASCII;
  public static final byte[] ASCII2EBCDIC;
  public static final byte STX = 2;
  public static final byte FS = 28;
  public static final byte US = 31;
  public static final byte RS = 29;
  public static final byte GS = 30;
  public static final byte ETX = 3;

  public TotalConverter() {
  }

  public static String toString(Date date, String pattern) {
    if (date == null) {
      return null;
    } else {
      SimpleDateFormat formater = new SimpleDateFormat(pattern);
      return formater.format(date);
    }
  }

  public static String toString(Clob clob) {
    try {
      Reader reader;
      reader = clob.getCharacterStream();
      StringWriter writer = new StringWriter();
      IOUtils.copy(reader, writer);
      return writer.toString();
    } catch (SQLException | IOException e) {
      return null;
    }

  }

  public static String toString(double num, String pattern) {
    DecimalFormat fmt = new DecimalFormat(pattern);
    return fmt.format(num);
  }

  public static String toString(float num, String pattern) {
    DecimalFormat fmt = new DecimalFormat(pattern);
    return fmt.format((double) num);
  }

  public static String toString(long num, String pattern) {
    DecimalFormat fmt = new DecimalFormat(pattern);
    return fmt.format(num);
  }

  public static String toString(int num, String pattern) {
    DecimalFormat fmt = new DecimalFormat(pattern);
    return fmt.format((long) num);
  }

  public static String toString(BigDecimal num, String pattern) {
    DecimalFormat fmt = new DecimalFormat(pattern);
    return fmt.format(num);
  }

  public static String numberToCurrencyFormat(double number, String currencyCode) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    currency.setCurrency(Currency.getInstance(currencyCode));
    return currency.format(number).replace(currency.getCurrency().getSymbol(), "") + " " + currency.getCurrency().getCurrencyCode();
  }

  public static String numberToCurrencyFormat(double number) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    return currency.format(number).replace(currency.getCurrency().getSymbol(), "");
  }

  public static String numberToCurrencyFormat(float number, String currencyCode) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    currency.setCurrency(Currency.getInstance(currencyCode));
    return currency.format((double) number).replace(currency.getCurrency().getSymbol(), "") + " " + currency.getCurrency().getCurrencyCode();
  }

  public static String numberToCurrencyFormat(float number) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    return currency.format((double) number).replace(currency.getCurrency().getSymbol(), "");
  }

  public static String numberToCurrencyFormat(long number, String currencyCode) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    currency.setCurrency(Currency.getInstance(currencyCode));
    return currency.format(number).replace(currency.getCurrency().getSymbol(), "") + " " + currency.getCurrency().getCurrencyCode();
  }

  public static String numberToCurrencyFormat(long number) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    return currency.format(number).replace(currency.getCurrency().getSymbol(), "");
  }

  public static String numberToCurrencyFormat(int number, String currencyCode) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    currency.setCurrency(Currency.getInstance(currencyCode));
    return currency.format((long) number).replace(currency.getCurrency().getSymbol(), "") + " " + currency.getCurrency().getCurrencyCode();
  }

  public static String numberToCurrencyFormat(int number) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    return currency.format((long) number).replace(currency.getCurrency().getSymbol(), "");
  }

  public static String numberToCurrencyFormat(BigDecimal number, String currencyCode) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    currency.setCurrency(Currency.getInstance(currencyCode));
    return currency.format(number).replace(currency.getCurrency().getSymbol(), "") + " " + currency.getCurrency().getCurrencyCode();
  }

  public static String numberToCurrencyFormat(BigDecimal number) {
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    return currency.format(number).replace(currency.getCurrency().getSymbol(), "");
  }

  public static Date stringToDate(String date, String pattern) throws ParseException {
    if (date == null || date.equals("")) return null;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    return simpleDateFormat.parse(date);
  }

  public static Number toNumber(String number) throws ParseException {
    if (number == null || number.equals("")) return null;
    DecimalFormat decimalFormat = new DecimalFormat();
    return decimalFormat.parse(number);
  }

  public static double toDouble(String number) throws ParseException {
    return toNumber(number).doubleValue();
  }

  public static float toFloat(String number) throws ParseException {
    return toNumber(number).floatValue();
  }

  public static long toLong(String number) throws ParseException {
    return toNumber(number).longValue();
  }

  public static int toInt(String number) throws ParseException {
    return toNumber(number).intValue();
  }

  public static String padright(String s, int len, char c) throws Exception {
    s = s.trim();
    if (s.length() > len) {
      throw new Exception("invalid len " + s.length() + "/" + len);
    } else {
      StringBuffer d = new StringBuffer(len);
      int fill = len - s.length();
      d.append(s);

      while (fill-- > 0) {
        d.append(c);
      }

      return d.toString();
    }
  }

  public static String padleft(String s, int len, char c) throws Exception {
    s = s.trim();
    if (s.length() > len) {
      throw new Exception("invalid len " + s.length() + "/" + len);
    } else {
      StringBuffer d = new StringBuffer(len);
      int fill = len - s.length();

      while (fill-- > 0) {
        d.append(c);
      }

      d.append(s);
      return d.toString();
    }
  }

  public static String toNoSign(String s) {
    String result = "";

    for (int i = 0; s != null && i < s.length(); ++i) {
      char c = s.charAt(i);
      int pos;
      if ((pos = "áàảãạăắằẳẵặâấầẩẫậđéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬĐÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ".indexOf(c)) != -1) {
        result = result + NOSIGN[pos];
      } else {
        result = result + c;
      }
    }

    result = result.replaceAll("[^-a-zA-Z0-9~!@#$%^&*()_+={}\\[\\]|\\\\/?<>,\"\':;. ]+", "");
    return result;
  }

  public static String toStringBase64(byte[] byteArray) {
    return (new BASE64Encoder()).encodeBuffer(byteArray).replaceAll(String.valueOf('\r'), "");
  }

  public static byte[] toByteArrayBase64(String string) throws IOException {
    return (new BASE64Decoder()).decodeBuffer(string);
  }

  public static String ebcdicToAscii(byte[] e) {
    try {
      return new String(ebcdicToAsciiBytes(e, 0, e.length), "ISO8859_1");
    } catch (UnsupportedEncodingException var2) {
      return var2.toString();
    }
  }

  public static String ebcdicToAscii(byte[] e, int offset, int len) {
    try {
      return new String(ebcdicToAsciiBytes(e, offset, len), "ISO8859_1");
    } catch (UnsupportedEncodingException var4) {
      return var4.toString();
    }
  }

  public static byte[] ebcdicToAsciiBytes(byte[] e) {
    return ebcdicToAsciiBytes(e, 0, e.length);
  }

  public static byte[] ebcdicToAsciiBytes(byte[] e, int offset, int len) {
    byte[] a = new byte[len];

    for (int i = 0; i < len; ++i) {
      a[i] = EBCDIC2ASCII[e[offset + i] & 255];
    }

    return a;
  }

  public static byte[] asciiToEbcdic(String s) {
    return asciiToEbcdic(s.getBytes());
  }

  public static byte[] asciiToEbcdic(byte[] a) {
    byte[] e = new byte[a.length];

    for (int i = 0; i < a.length; ++i) {
      e[i] = ASCII2EBCDIC[a[i] & 255];
    }

    return e;
  }

  public static void asciiToEbcdic(String s, byte[] e, int offset) {
    int len = s.length();

    for (int i = 0; i < len; ++i) {
      e[offset + i] = ASCII2EBCDIC[s.charAt(i) & 255];
    }

  }

  public static String trim(String s) {
    return s != null ? s.trim() : null;
  }

  public static String zeropad(String s, int len) throws Exception {
    return padleft(s, len, '0');
  }

  public static String zeropad(long l, int len) {
    try {
      return padleft(Long.toString((long) ((double) l % Math.pow(10.0D, (double) len))), len, '0');
    } catch (Exception var4) {
      return null;
    }
  }

  public static String strpad(String s, int len) {
    StringBuffer d = new StringBuffer(s);

    while (d.length() < len) {
      d.append(' ');
    }

    return d.toString();
  }

  public static String zeropadRight(String s, int len) {
    StringBuffer d = new StringBuffer(s);

    while (d.length() < len) {
      d.append('0');
    }

    return d.toString();
  }

  public static byte[] str2bcd(String s, boolean padLeft, byte[] d, int offset) {
    int len = s.length();
    int start = (len & 1) == 1 && padLeft ? 1 : 0;

    for (int i = start; i < len + start; ++i) {
      d[offset + (i >> 1)] = (byte) (d[offset + (i >> 1)] | s.charAt(i - start) - 48 << ((i & 1) == 1 ? 0 : 4));
    }

    return d;
  }

  public static byte[] str2bcd(String s, boolean padLeft) {
    int len = s.length();
    byte[] d = new byte[len + 1 >> 1];
    return str2bcd(s, padLeft, d, 0);
  }

  public static byte[] str2bcd(String s, boolean padLeft, byte fill) {
    int len = s.length();
    byte[] d = new byte[len + 1 >> 1];
    Arrays.fill(d, fill);
    int start = (len & 1) == 1 && padLeft ? 1 : 0;

    for (int i = start; i < len + start; ++i) {
      d[i >> 1] = (byte) (d[i >> 1] | s.charAt(i - start) - 48 << ((i & 1) == 1 ? 0 : 4));
    }

    return d;
  }

  public static String bcd2str(byte[] b, int offset, int len, boolean padLeft) {
    StringBuffer d = new StringBuffer(len);
    int start = (len & 1) == 1 && padLeft ? 1 : 0;

    for (int i = start; i < len + start; ++i) {
      int shift = (i & 1) == 1 ? 0 : 4;
      char c = Character.forDigit(b[offset + (i >> 1)] >> shift & 15, 16);
      if (c == 100) {
        c = 61;
      }

      d.append(Character.toUpperCase(c));
    }

    return d.toString();
  }

  public static String hexString(byte[] b) {
    StringBuffer d = new StringBuffer(b.length * 2);

    for (int i = 0; i < b.length; ++i) {
      char hi = Character.forDigit(b[i] >> 4 & 15, 16);
      char lo = Character.forDigit(b[i] & 15, 16);
      d.append(Character.toUpperCase(hi));
      d.append(Character.toUpperCase(lo));
    }

    return d.toString();
  }

  public static String dumpString(byte[] b) {
    StringBuffer d = new StringBuffer(b.length * 2);

    for (int i = 0; i < b.length; ++i) {
      char c = (char) b[i];
      if (Character.isISOControl(c)) {
        switch (c) {
          case '\u0000':
            d.append("{NULL}");
            break;
          case '\u0001':
            d.append("{SOH}");
            break;
          case '\u0002':
            d.append("{STX}");
            break;
          case '\u0003':
            d.append("{ETX}");
            break;
          case '\u0004':
            d.append("{EOT}");
            break;
          case '\u0005':
            d.append("{ENQ}");
            break;
          case '\u0006':
            d.append("{ACK}");
            break;
          case '\u0007':
            d.append("{BEL}");
            break;
          case '\b':
          case '\t':
          case '\u000b':
          case '\f':
          case '\u000e':
          case '\u000f':
          case '\u0011':
          case '\u0012':
          case '\u0013':
          case '\u0014':
          case '\u0017':
          case '\u0018':
          case '\u0019':
          case '\u001a':
          case '\u001b':
          case '\u001d':
          default:
            char hi = Character.forDigit(b[i] >> 4 & 15, 16);
            char lo = Character.forDigit(b[i] & 15, 16);
            d.append('[');
            d.append(Character.toUpperCase(hi));
            d.append(Character.toUpperCase(lo));
            d.append(']');
            break;
          case '\n':
            d.append("{LF}");
            break;
          case '\r':
            d.append("{CR}");
            break;
          case '\u0010':
            d.append("{DLE}");
            break;
          case '\u0015':
            d.append("{NAK}");
            break;
          case '\u0016':
            d.append("{SYN}");
            break;
          case '\u001c':
            d.append("{FS}");
            break;
          case '\u001e':
            d.append("{RS}");
        }
      } else {
        d.append(c);
      }
    }

    return d.toString();
  }

  public static String hexString(byte[] b, int offset, int len) {
    StringBuffer d = new StringBuffer(len * 2);
    len += offset;

    for (int i = offset; i < len; ++i) {
      char hi = Character.forDigit(b[i] >> 4 & 15, 16);
      char lo = Character.forDigit(b[i] & 15, 16);
      d.append(Character.toUpperCase(hi));
      d.append(Character.toUpperCase(lo));
    }

    return d.toString();
  }

  public static String bitSet2String(BitSet b) {
    int len = b.size();
    len = len > 128 ? 128 : len;
    StringBuffer d = new StringBuffer(len);

    for (int i = 0; i < len; ++i) {
      d.append((char) (b.get(i) ? '1' : '0'));
    }

    return d.toString();
  }

  public static byte[] bitSet2byte(BitSet b) {
    int len = b.length() + 62 >> 6 << 6;
    byte[] d = new byte[len >> 3];

    for (int i = 0; i < len; ++i) {
      if (b.get(i + 1)) {
        d[i >> 3] = (byte) (d[i >> 3] | 128 >> i % 8);
      }
    }

    if (len > 64) {
      d[0] = (byte) (d[0] | 128);
    }

    if (len > 128) {
      d[8] = (byte) (d[8] | 128);
    }

    return d;
  }

  public static byte[] bitSet2byte(BitSet b, int bytes) {
    int len = bytes * 8;
    byte[] d = new byte[bytes];

    for (int i = 0; i < len; ++i) {
      if (b.get(i + 1)) {
        d[i >> 3] = (byte) (d[i >> 3] | 128 >> i % 8);
      }
    }

    if (len > 64) {
      d[0] = (byte) (d[0] | 128);
    }

    if (len > 128) {
      d[8] = (byte) (d[8] | 128);
    }

    return d;
  }

  public static int bitSet2Int(BitSet bs) {
    int total = 0;
    int b = bs.length() - 1;
    if (b > 0) {
      int value = (int) Math.pow(2.0D, (double) b);

      for (int i = 0; i <= b; ++i) {
        if (bs.get(i)) {
          total += value;
        }

        value >>= 1;
      }
    }

    return total;
  }

  public static BitSet int2BitSet(int value) {
    return int2BitSet(value, 0);
  }

  public static BitSet int2BitSet(int value, int offset) {
    BitSet bs = new BitSet();
    String hex = Integer.toHexString(value);
    hex2BitSet(bs, hex.getBytes(), offset);
    return bs;
  }

  public static BitSet byte2BitSet(byte[] b, int offset, boolean bitZeroMeansExtended) {
    int len = bitZeroMeansExtended ? ((b[offset] & 128) == 128 ? 128 : 64) : 64;
    BitSet bmap = new BitSet(len);

    for (int i = 0; i < len; ++i) {
      if ((b[offset + (i >> 3)] & 128 >> i % 8) > 0) {
        bmap.set(i + 1);
      }
    }

    return bmap;
  }

  public static BitSet byte2BitSet(byte[] b, int offset, int maxBits) {
    int len = maxBits > 64 ? ((b[offset] & 128) == 128 ? 128 : 64) : maxBits;
    if (maxBits > 128 && b.length > offset + 8 && (b[offset + 8] & 128) == 128) {
      len = 192;
    }

    BitSet bmap = new BitSet(len);

    for (int i = 0; i < len; ++i) {
      if ((b[offset + (i >> 3)] & 128 >> i % 8) > 0) {
        bmap.set(i + 1);
      }
    }

    return bmap;
  }

  public static BitSet byte2BitSet(BitSet bmap, byte[] b, int bitOffset) {
    int len = b.length << 3;

    for (int i = 0; i < len; ++i) {
      if ((b[i >> 3] & 128 >> i % 8) > 0) {
        bmap.set(bitOffset + i + 1);
      }
    }

    return bmap;
  }

  public static BitSet hex2BitSet(byte[] b, int offset, boolean bitZeroMeansExtended) {
    int len = bitZeroMeansExtended ? ((Character.digit((char) b[offset], 16) & 8) == 8 ? 128 : 64) : 64;
    BitSet bmap = new BitSet(len);

    for (int i = 0; i < len; ++i) {
      int digit = Character.digit((char) b[offset + (i >> 2)], 16);
      if ((digit & 8 >> i % 4) > 0) {
        bmap.set(i + 1);
      }
    }

    return bmap;
  }

  public static BitSet hex2BitSet(byte[] b, int offset, int maxBits) {
    int len = maxBits > 64 ? ((Character.digit((char) b[offset], 16) & 8) == 8 ? 128 : 64) : maxBits;
    BitSet bmap = new BitSet(len);

    for (int i = 0; i < len; ++i) {
      int digit = Character.digit((char) b[offset + (i >> 2)], 16);
      if ((digit & 8 >> i % 4) > 0) {
        bmap.set(i + 1);
        if (i == 65 && maxBits > 128) {
          len = 192;
        }
      }
    }

    return bmap;
  }

  public static BitSet hex2BitSet(BitSet bmap, byte[] b, int bitOffset) {
    int len = b.length << 2;

    for (int i = 0; i < len; ++i) {
      int digit = Character.digit((char) b[i >> 2], 16);
      if ((digit & 8 >> i % 4) > 0) {
        bmap.set(bitOffset + i + 1);
      }
    }

    return bmap;
  }

  public static byte[] hex2byte(byte[] b, int offset, int len) {
    byte[] d = new byte[len];

    for (int i = 0; i < len * 2; ++i) {
      int shift = i % 2 == 1 ? 0 : 4;
      d[i >> 1] = (byte) (d[i >> 1] | Character.digit((char) b[offset + i], 16) << shift);
    }

    return d;
  }

  public static byte[] hex2byte(String s) {
    return s.length() % 2 == 0 ? hex2byte(s.getBytes(), 0, s.length() >> 1) : hex2byte("0" + s);
  }

  public static String formatDouble(double d, int len) {
    String prefix = Long.toString((long) d);
    String suffix = Integer.toString((int) (Math.round(d * 100.0D) % 100L));

    try {
      if (len > 3) {
        prefix = padleft(prefix, len - 3, ' ');
      }

      suffix = zeropad(suffix, 2);
    } catch (Exception var6) {
      ;
    }

    return prefix + "." + suffix;
  }

  public static String formatAmount(long l, int len) throws Exception {
    String buf = Long.toString(l);
    if (l < 100L) {
      buf = zeropad(buf, 3);
    }

    StringBuffer s = new StringBuffer(padleft(buf, len - 1, ' '));
    s.insert(len - 3, '.');
    return s.toString();
  }

  public static String normalize(String s, boolean canonical) {
    StringBuffer str = new StringBuffer();
    int len = s != null ? s.length() : 0;

    for (int i = 0; i < len; ++i) {
      char ch = s.charAt(i);
      switch (ch) {
        case '\n':
        case '\r':
          if (canonical) {
            str.append("&#");
            str.append(Integer.toString(ch & 255));
            str.append(';');
            break;
          }
        default:
          if (ch < 32) {
            str.append("&#");
            str.append(Integer.toString(ch & 255));
            str.append(';');
          } else if (ch > '\uff00') {
            str.append((char) (ch & 255));
          } else {
            str.append(ch);
          }
          break;
        case '\"':
          str.append("&quot;");
          break;
        case '&':
          str.append("&amp;");
          break;
        case '<':
          str.append("&lt;");
          break;
        case '>':
          str.append("&gt;");
      }
    }

    return str.toString();
  }

  public static String normalize(String s) {
    return normalize(s, true);
  }

  public static String protect(String s) {
    StringBuffer sb = new StringBuffer();
    int len = s.length();
    int clear = len > 6 ? 6 : 0;
    int lastFourIndex = -1;
    if (clear > 0) {
      lastFourIndex = s.indexOf(61) - 4;
      if (lastFourIndex < 0) {
        lastFourIndex = s.indexOf(94) - 4;
        if (lastFourIndex < 0) {
          lastFourIndex = len - 4;
        }
      }
    }

    int e;
    for (e = 0; e < len; ++e) {
      if (s.charAt(e) == 61) {
        clear = 1;
      } else if (s.charAt(e) == 94) {
        lastFourIndex = 0;
        clear = len - e;
      } else if (e == lastFourIndex) {
        clear = 4;
      }

      sb.append(clear-- > 0 ? s.charAt(e) : '_');
    }

    s = sb.toString();

    try {
      e = s.replaceAll("[^\\^]", "").length();
      if (e == 2) {
        s = s.substring(0, s.lastIndexOf("^") + 1);
        s = padright(s, len, '_');
      }
    } catch (Exception var6) {
      ;
    }

    return s;
  }

  public static int[] toIntArray(String s) {
    StringTokenizer st = new StringTokenizer(s);
    int[] array = new int[st.countTokens()];

    for (int i = 0; st.hasMoreTokens(); ++i) {
      array[i] = Integer.parseInt(st.nextToken());
    }

    return array;
  }

  public static String[] toStringArray(String s) {
    StringTokenizer st = new StringTokenizer(s);
    String[] array = new String[st.countTokens()];

    for (int i = 0; st.hasMoreTokens(); ++i) {
      array[i] = st.nextToken();
    }

    return array;
  }

  public static byte[] xor(byte[] op1, byte[] op2) {
    Object result = null;
    byte[] var4;
    if (op2.length > op1.length) {
      var4 = new byte[op1.length];
    } else {
      var4 = new byte[op2.length];
    }

    for (int i = 0; i < var4.length; ++i) {
      var4[i] = (byte) (op1[i] ^ op2[i]);
    }

    return var4;
  }

  public static String hexor(String op1, String op2) {
    byte[] xor = xor(hex2byte(op1), hex2byte(op2));
    return hexString(xor);
  }

  public static byte[] trim(byte[] array, int length) {
    byte[] trimmedArray = new byte[length];
    System.arraycopy(array, 0, trimmedArray, 0, length);
    return trimmedArray;
  }

  public static byte[] concat(byte[] array1, byte[] array2) {
    byte[] concatArray = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, concatArray, 0, array1.length);
    System.arraycopy(array2, 0, concatArray, array1.length, array2.length);
    return concatArray;
  }

  public static byte[] concat(byte[] array1, int beginIndex1, int length1, byte[] array2, int beginIndex2, int length2) {
    byte[] concatArray = new byte[length1 + length2];
    System.arraycopy(array1, beginIndex1, concatArray, 0, length1);
    System.arraycopy(array2, beginIndex2, concatArray, length1, length2);
    return concatArray;
  }

  public static void sleep(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException var3) {
      ;
    }

  }

  public static String zeroUnPad(String s) {
    return unPadLeft(s, '0');
  }

  public static String blankUnPad(String s) {
    return unPadRight(s, ' ');
  }

  public static String unPadRight(String s, char c) {
    int end = s.length();
    if (end == 0) {
      return s;
    } else {
      while (0 < end && s.charAt(end - 1) == c) {
        --end;
      }

      return 0 < end ? s.substring(0, end) : s.substring(0, 1);
    }
  }

  public static String unPadLeft(String s, char c) {
    int fill = 0;
    int end = s.length();
    if (end == 0) {
      return s;
    } else {
      while (fill < end && s.charAt(fill) == c) {
        ++fill;
      }

      return fill < end ? s.substring(fill, end) : s.substring(fill - 1, end);
    }
  }

  public static boolean isZero(String s) {
    int i = 0;

    int len;
    for (len = s.length(); i < len && s.charAt(i) == 48; ++i) {
      ;
    }

    return i >= len;
  }

  public static boolean isBlank(String s) {
    return s.trim().length() == 0;
  }

  public static boolean isAlphaNumeric(String s) {
    int i = 0;

    int len;
    for (len = s.length(); i < len && (Character.isLetterOrDigit(s.charAt(i)) || s.charAt(i) == 32 || s.charAt(i) == 46 || s.charAt(i) == 45 || s.charAt(i) == 95) || s.charAt(i) == 63; ++i) {
      ;
    }

    return i >= len;
  }

  public static boolean isNumeric(String s, int radix) {
    int i = 0;

    int len;
    for (len = s.length(); i < len && Character.digit(s.charAt(i), radix) > -1; ++i) {
      ;
    }

    return i >= len;
  }

  public static byte[] bitSet2extendedByte(BitSet b) {
    short len = 128;
    byte[] d = new byte[len >> 3];

    for (int i = 0; i < len; ++i) {
      if (b.get(i + 1)) {
        d[i >> 3] = (byte) (d[i >> 3] | 128 >> i % 8);
      }
    }

    d[0] = (byte) (d[0] | 128);
    return d;
  }

  private static String hexOffset(int i) {
    i = i >> 4 << 4;
    int w = i > '\uffff' ? 8 : 4;

    try {
      return zeropad(Integer.toString(i, 16), w);
    } catch (Exception var3) {
      return var3.getMessage();
    }
  }

  public static String hexdump(byte[] b) {
    return hexdump(b, 0, b.length);
  }

  public static String hexdump(byte[] b, int offset, int len) {
    StringBuffer sb = new StringBuffer();
    StringBuffer hex = new StringBuffer();
    StringBuffer ascii = new StringBuffer();
    String sep = "  ";
    String lineSep = System.getProperty("line.separator");

    for (int i = offset; i < len; ++i) {
      char hi = Character.forDigit(b[i] >> 4 & 15, 16);
      char lo = Character.forDigit(b[i] & 15, 16);
      hex.append(Character.toUpperCase(hi));
      hex.append(Character.toUpperCase(lo));
      hex.append(' ');
      char c = (char) b[i];
      ascii.append(c >= 32 && c < 127 ? c : '.');
      int j = i % 16;
      switch (j) {
        case 7:
          hex.append(' ');
          break;
        case 15:
          sb.append(hexOffset(i));
          sb.append(sep);
          sb.append(hex.toString());
          sb.append(' ');
          sb.append(ascii.toString());
          sb.append(lineSep);
          hex = new StringBuffer();
          ascii = new StringBuffer();
      }
    }

    if (hex.length() > 0) {
      while (hex.length() < 49) {
        hex.append(' ');
      }

      sb.append(hexOffset(len));
      sb.append(sep);
      sb.append(hex.toString());
      sb.append(' ');
      sb.append(ascii.toString());
      sb.append(lineSep);
    }

    return sb.toString();
  }

  public static String strpadf(String s, int len) {
    StringBuffer d = new StringBuffer(s);

    while (d.length() < len) {
      d.append('F');
    }

    return d.toString();
  }

  public static String trimf(String s) {
    if (s != null) {
      int l = s.length();
      if (l > 0) {
        do {
          --l;
        } while (l >= 0 && s.charAt(l) == 70);

        s = l == 0 ? "" : s.substring(0, l + 1);
      }
    }

    return s;
  }

  public static String takeLastN(String s, int n) throws Exception {
    return s.length() > n ? s.substring(s.length() - n) : (s.length() < n ? zeropad(s, n) : s);
  }

  public static String takeFirstN(String s, int n) throws Exception {
    return s.length() > n ? s.substring(0, n) : (s.length() < n ? zeropad(s, n) : s);
  }

  public static String millisToString(long millis) {
    StringBuilder sb = new StringBuilder();
    int ms = (int) (millis % 1000L);
    millis /= 1000L;
    int dd = (int) (millis / 86400L);
    millis -= (long) (dd * 86400);
    int hh = (int) (millis / 3600L);
    millis -= (long) (hh * 3600);
    int mm = (int) (millis / 60L);
    millis -= (long) (mm * 60);
    int ss = (int) millis;
    if (dd > 0) {
      sb.append(Long.toString((long) dd));
      sb.append("d ");
    }

    sb.append(zeropad((long) hh, 2));
    sb.append(':');
    sb.append(zeropad((long) mm, 2));
    sb.append(':');
    sb.append(zeropad((long) ss, 2));
    sb.append('.');
    sb.append(zeropad((long) ms, 3));
    return sb.toString();
  }

  static {
    for (int i = 0; i < "aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY".length(); ++i) {
      NOSIGN[i] = "aaaaaaaaaaaaaaaaadeeeeeeeeeeeiiiiiooooooooooooooooouuuuuuuuuuuyyyyyAAAAAAAAAAAAAAAAADEEEEEEEEEEEIIIIIOOOOOOOOOOOOOOOOOUUUUUUUUUUUYYYYY".charAt(i);
    }

    EBCDIC2ASCII = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) -100, (byte) 9, (byte) -122, (byte) 127, (byte) -105, (byte) -115, (byte) -114, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) -99, (byte) 10, (byte) 8, (byte) -121, (byte) 24, (byte) 25, (byte) -110, (byte) -113, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) -128, (byte) -127, (byte) -126, (byte) -125, (byte) -124, (byte) -123, (byte) 23, (byte) 27, (byte) -120, (byte) -119, (byte) -118, (byte) -117, (byte) -116, (byte) 5, (byte) 6, (byte) 7, (byte) -112, (byte) -111, (byte) 22, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) 4, (byte) -104, (byte) -103, (byte) -102, (byte) -101, (byte) 20, (byte) 21, (byte) -98, (byte) 26, (byte) 32, (byte) -96, (byte) -30, (byte) -28, (byte) -32, (byte) -31, (byte) -29, (byte) -27, (byte) -25, (byte) -15, (byte) -94, (byte) 46, (byte) 60, (byte) 40, (byte) 43, (byte) 124, (byte) 38, (byte) -23, (byte) -22, (byte) -21, (byte) -24, (byte) -19, (byte) -18, (byte) -17, (byte) -20, (byte) -33, (byte) 33, (byte) 36, (byte) 42, (byte) 41, (byte) 59, (byte) 94, (byte) 45, (byte) 47, (byte) -62, (byte) -60, (byte) -64, (byte) -63, (byte) -61, (byte) -59, (byte) -57, (byte) -47, (byte) -90, (byte) 44, (byte) 37, (byte) 95, (byte) 62, (byte) 63, (byte) -8, (byte) -55, (byte) -54, (byte) -53, (byte) -56, (byte) -51, (byte) -50, (byte) -49, (byte) -52, (byte) 96, (byte) 58, (byte) 35, (byte) 64, (byte) 39, (byte) 61, (byte) 34, (byte) -40, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) -85, (byte) -69, (byte) -16, (byte) -3, (byte) -2, (byte) -79, (byte) -80, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) -86, (byte) -70, (byte) -26, (byte) -72, (byte) -58, (byte) -92, (byte) -75, (byte) 126, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) -95, (byte) -65, (byte) -48, (byte) 91, (byte) -34, (byte) -82, (byte) -84, (byte) -93, (byte) -91, (byte) -73, (byte) -87, (byte) -89, (byte) -74, (byte) -68, (byte) -67, (byte) -66, (byte) -35, (byte) -88, (byte) -81, (byte) 93, (byte) -76, (byte) -41, (byte) 123, (byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) -83, (byte) -12, (byte) -10, (byte) -14, (byte) -13, (byte) -11, (byte) 125, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) -71, (byte) -5, (byte) -4, (byte) -7, (byte) -6, (byte) -1, (byte) 92, (byte) -9, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) -78, (byte) -44, (byte) -42, (byte) -46, (byte) -45, (byte) -43, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) -77, (byte) -37, (byte) -36, (byte) -39, (byte) -38, (byte) -97};
    ASCII2EBCDIC = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 55, (byte) 45, (byte) 46, (byte) 47, (byte) 22, (byte) 5, (byte) 21, (byte) 11, (byte) 12, (byte) 13, (byte) 14, (byte) 15, (byte) 16, (byte) 17, (byte) 18, (byte) 19, (byte) 60, (byte) 61, (byte) 50, (byte) 38, (byte) 24, (byte) 25, (byte) 63, (byte) 39, (byte) 28, (byte) 29, (byte) 30, (byte) 31, (byte) 64, (byte) 90, (byte) 127, (byte) 123, (byte) 91, (byte) 108, (byte) 80, (byte) 125, (byte) 77, (byte) 93, (byte) 92, (byte) 78, (byte) 107, (byte) 96, (byte) 75, (byte) 97, (byte) -16, (byte) -15, (byte) -14, (byte) -13, (byte) -12, (byte) -11, (byte) -10, (byte) -9, (byte) -8, (byte) -7, (byte) 122, (byte) 94, (byte) 76, (byte) 126, (byte) 110, (byte) 111, (byte) 124, (byte) -63, (byte) -62, (byte) -61, (byte) -60, (byte) -59, (byte) -58, (byte) -57, (byte) -56, (byte) -55, (byte) -47, (byte) -46, (byte) -45, (byte) -44, (byte) -43, (byte) -42, (byte) -41, (byte) -40, (byte) -39, (byte) -30, (byte) -29, (byte) -28, (byte) -27, (byte) -26, (byte) -25, (byte) -24, (byte) -23, (byte) -83, (byte) -32, (byte) -67, (byte) 95, (byte) 109, (byte) 121, (byte) -127, (byte) -126, (byte) -125, (byte) -124, (byte) -123, (byte) -122, (byte) -121, (byte) -120, (byte) -119, (byte) -111, (byte) -110, (byte) -109, (byte) -108, (byte) -107, (byte) -106, (byte) -105, (byte) -104, (byte) -103, (byte) -94, (byte) -93, (byte) -92, (byte) -91, (byte) -90, (byte) -89, (byte) -88, (byte) -87, (byte) -64, (byte) 79, (byte) -48, (byte) -95, (byte) 7, (byte) 32, (byte) 33, (byte) 34, (byte) 35, (byte) 36, (byte) 37, (byte) 6, (byte) 23, (byte) 40, (byte) 41, (byte) 42, (byte) 43, (byte) 44, (byte) 9, (byte) 10, (byte) 27, (byte) 48, (byte) 49, (byte) 26, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 8, (byte) 56, (byte) 57, (byte) 58, (byte) 59, (byte) 4, (byte) 20, (byte) 62, (byte) -1, (byte) 65, (byte) -86, (byte) 74, (byte) -79, (byte) -97, (byte) -78, (byte) 106, (byte) -75, (byte) -69, (byte) -76, (byte) -102, (byte) -118, (byte) -80, (byte) -54, (byte) -81, (byte) -68, (byte) -112, (byte) -113, (byte) -22, (byte) -6, (byte) -66, (byte) -96, (byte) -74, (byte) -77, (byte) -99, (byte) -38, (byte) -101, (byte) -117, (byte) -73, (byte) -72, (byte) -71, (byte) -85, (byte) 100, (byte) 101, (byte) 98, (byte) 102, (byte) 99, (byte) 103, (byte) -98, (byte) 104, (byte) 116, (byte) 113, (byte) 114, (byte) 115, (byte) 120, (byte) 117, (byte) 118, (byte) 119, (byte) -84, (byte) 105, (byte) -19, (byte) -18, (byte) -21, (byte) -17, (byte) -20, (byte) -65, (byte) -128, (byte) -3, (byte) -2, (byte) -5, (byte) -4, (byte) -70, (byte) -82, (byte) 89, (byte) 68, (byte) 69, (byte) 66, (byte) 70, (byte) 67, (byte) 71, (byte) -100, (byte) 72, (byte) 84, (byte) 81, (byte) 82, (byte) 83, (byte) 88, (byte) 85, (byte) 86, (byte) 87, (byte) -116, (byte) 73, (byte) -51, (byte) -50, (byte) -53, (byte) -49, (byte) -52, (byte) -31, (byte) 112, (byte) -35, (byte) -34, (byte) -37, (byte) -36, (byte) -115, (byte) -114, (byte) -33};
  }

  public static String toMd5(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(text.getBytes("UTF-8"));
    byte byteData[] = md.digest();

    //convert the byte to hex format method 1
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < byteData.length; i++) {
      sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }
    return sb.toString();
  }

  public static List<Map> resultSetToMap(ResultSet resultSet) throws SQLException {
    List<Map> list = new ArrayList<>();
    while (resultSet.next()) {
      Map mapRS = new HashMap<String, Object>();
      ResultSetMetaData md = resultSet.getMetaData();
      int columns = md.getColumnCount();
      for (int index = 1; index <= columns; ++index) {
        mapRS.put(md.getColumnName(index), resultSet.getObject(index));
      }
      list.add(mapRS);
    }
    return list;
  }

  public static String imgToBase64(String fileName, String type) throws IOException {
    String imageString = null;
    BufferedImage img = ImageIO.read(new File(fileName));
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ImageIO.write(img, type, bos);
    byte[] imageBytes = bos.toByteArray();
    BASE64Encoder encoder = new BASE64Encoder();
    imageString = encoder.encode(imageBytes);
    bos.close();
    return /*"data:image/png;base64," + */imageString;
  }

  public static BigDecimal getNumber(Map map, String key) {
    return (BigDecimal) map.get(key);
  }

  public static int getNumberInt(Map map, String key) {
    BigDecimal bigDecimal = getNumber(map, key);
    return bigDecimal == null ? 0 : bigDecimal.intValue();
  }

  public static String toCharFM(long number, int size) {
    String nm = String.valueOf(number);
    while (nm.length() < size) nm = "0" + nm;
    return nm;
  }

  public static long getNumberLong(Map map, String key) {
    BigDecimal bigDecimal = getNumber(map, key);
    return bigDecimal == null ? 0 : bigDecimal.longValue();
  }

  public static String toStringNum(Long num){
    if (num == null || num == 0) return "";
    NumberFormat ins = NumberFormat.getIntegerInstance();
    return ins.format(num);
  }
}
