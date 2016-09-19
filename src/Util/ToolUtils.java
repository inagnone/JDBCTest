package Util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
public class ToolUtils {
  
  /**
   * 去空格
   * @param str 字符串
   * @return 首尾去空格后的字符串
   */
  public static String trim(String str) {
    if (str == null || "null".equals(str)) {
      str = "";
    }
    return str.trim();
  }
  
  /**
   * HTTP协议传参时,2次转码
   * 
   * @param val 字符串
   * @return 转码后的字符串
   */
  public static String encode8(String val) {
    val = ToolUtils.trim(val);
    if (val.equals(""))
      return "";
    try {
      val = java.net.URLEncoder.encode(java.net.URLEncoder.encode(val, "UTF-8"), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return val;
  }
  
  /**
   * HTTP协议传参时,UTF-8解密
   * 
   * @param val 待解码字符串
   * @return 解码后的字符串
   */
  public static String decode8(String val) {
    val = ToolUtils.trim(val);
    if (val.equals(""))
      return "";
    try {
      val = java.net.URLDecoder.decode(val, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return val;
  }
  
  
  /**
   * URL转码
   * 
   * @param str 字符串
   * @return 转码后的字符串
   */
  public static String encodeURI(String str) {
    str = ToolUtils.trim(str);
    try {
      return URLEncoder.encode(str, "utf-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
      return "";
    }
  }
  
  /**
   * URL解码
   * 
   * @param str 字符串
   * @return 解码后的字符串
   * @throws UnsupportedEncodingException 异常
   */
  public static String decodeURI(String str) throws UnsupportedEncodingException {
    str = ToolUtils.trim(str);
    return URLDecoder.decode(str, "utf-8");
  }
  
  
  

  /**
   * base64转码
   * @param s
   * @return
   */
  public static String encode64(String s) {
    if (s != null) {
      try {
        return new sun.misc.BASE64Encoder().encode(s.getBytes("utf-8"));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      }
    }
    return "";
  }

  /**
   * base64解码
   * @param s
   * @return
   */
  public static String decode64(String s) {
    try {
      byte[] temp = new sun.misc.BASE64Decoder().decodeBuffer(s);
      return new String(temp, "utf-8");
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  } 
  
  
  
  
  /**
   * 
   * @param rq  日期
   * @param patten 日期格式
   * @return 日期字串
   */
  public static String convertRq(Date rq, String patten) {
    SimpleDateFormat sdf = new SimpleDateFormat(patten);
    if (rq == null)
      return "";
    else
      return sdf.format(rq);
  }
  
  /**
   * 将yyyyMMdd日期转换为yyyy-MM-dd.
   * @param str 日期串
   * @return 日期串
   */
  public static String formatRq(String str) {
    String st = str;
    if (str != null && str.length() == 8) {
      st = str.substring(0, 4) + "-" + str.substring(4, 6) + "-" + str.substring(6, 8);
    }
    if (st == null) {
      return "";
    }
    return st;
  }
  
  
  /**
   * 将字符串转换为日期.
   * 
   * @param date "yyyy-MM-dd HH:mm:ss" 日期
   * @return 日期
   * @throws Exception
   */
  public static Date getDate(String date) {
    DateFormat sdf = null;
    Date rdate = null;
    try {
      if (date == null || date.length() < 1)
        return null;
  
      if (date.length() != 19)
        return null;
      
      sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      rdate = sdf.parse(date);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return rdate;
  }
  
  
  /**
   * 获取中文日期字符串
   * 
   * @param date yyyy-MM-dd
   * @return 二〇一一年五月十九日 字符串
   */
  public static String dateToCnDate(String date) {
    String result = "";
    String[] cnDate = new String[] { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
    String ten = "十";
    String[] dateStr = date.split("-");
    for (int i = 0; i < dateStr.length; i++) {
      for (int j = 0; j < dateStr[i].length(); j++) {
        String charStr = dateStr[i];
        String str = String.valueOf(charStr.charAt(j));
        if (charStr.length() == 2) {
          if (charStr.equals("10")) {
            result += ten;
            break;
          } else {
            if (j == 0) {
              if (charStr.charAt(j) == '1')
                result += ten;
              else if (charStr.charAt(j) == '0')
                result += "";
              else
                result += cnDate[Integer.parseInt(str)] + ten;
            }
            if (j == 1) {
              if (charStr.charAt(j) == '0')
                result += "";
              else
                result += cnDate[Integer.parseInt(str)];
            }
          }
        } else {
          result += cnDate[Integer.parseInt(str)];
        }
      }
      if (i == 0) {
        result += "年";
        continue;
      }
      if (i == 1) {
        result += "月";
        continue;
      }
      if (i == 2) {
        result += "日";
        continue;
      }
    }
    return result;
  }
  
  /**
   * 得到几月几天后的时间.
   * 
   * @param d 输入日期
   * @param day 天数
   * @return 时间
   */
  public static Date getDateAfter(Date d, int ys, int day) {
    Calendar now = Calendar.getInstance();
    now.setTime(d);
    now.set(Calendar.MONTH, now.get(Calendar.MONTH) + ys);
    now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
    return now.getTime();
  }
  
  /**
   * 日期相差天数
   * 
   * @param before 开始日期
   * @param after 结束日期
   * @return 相差天数
   */
  public static long getCDays(Date before, Date after) {
    GregorianCalendar gc = new GregorianCalendar();
    gc.setTime(after);
    gc.set(Calendar.HOUR_OF_DAY, 0);
    gc.set(Calendar.MINUTE, 0);
    gc.set(Calendar.SECOND, 0);
    gc.set(Calendar.MILLISECOND, 0);
    after = gc.getTime();

    gc = new GregorianCalendar();
    gc.setTime(before);
    gc.set(Calendar.HOUR_OF_DAY, 0);
    gc.set(Calendar.MINUTE, 0);
    gc.set(Calendar.SECOND, 0);
    gc.set(Calendar.MILLISECOND, 0);
    before = gc.getTime();

    long lb = after.getTime() - before.getTime();

    // 日期相减得到相差的日期
    long day = lb / (24 * 60 * 60 * 1000);
    return day;
  }
  
  
  
  public static void main(String[] args) {
    /*
    String str1 = "你好, Hello!";
    String str_encode = encode8(str1);
    String str_decode = decode8(decode8(str_encode));
    
    System.out.println(str_encode);
    System.out.println(str_decode);
    */

    String str = dateToCnDate("2016-03-03");
    System.out.println(str);
    
  }
  
}
