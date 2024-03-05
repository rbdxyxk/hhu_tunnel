package com.suidao.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期正则匹配工具类
 *
 * @author 许畅
 * @since JDK1.7
 * @version 2019年5月15日 许畅 新建
 */
public class DatePatternUtil {

    /**
     * 构造方法
     */
    private DatePatternUtil() {

    }

    /** 格式化 */
    private static final List<Pattern> patternList = new ArrayList<Pattern>(5);

    /** 格式匹配模式 */
    private static final Pattern PATTERN1 = Pattern.compile("\\d{4}");

    /** 格式匹配模式 */
    private static final Pattern PATTERN2 = Pattern.compile("\\d{4}-\\d{1,2}");

    /** 格式匹配模式 */
    private static final Pattern PATTERN3 = Pattern.compile("(\\d{4}\\-\\d{1,2}\\-\\d{1,2})");

    /** 格式匹配模式 */
    private static final Pattern PATTERN4 = Pattern.compile("(\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2})");

    /** 格式匹配模式 */
    private static final Pattern PATTERN5 = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}");

    /** 格式匹配模式 */
    private static final Pattern PATTERN6 = Pattern
            .compile("\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\.\\d+");

    /** 格式匹配模式 */
    private static final Pattern PATTERN7 = Pattern.compile("\\d{4}/\\d{1,2}/\\d{1,2}");

    /** 格式匹配模式 */
    private static final Pattern PATTERN8 = Pattern
            .compile("\\w{3}\\s\\w{3}\\s\\d{1,2}\\s\\d{4}\\s\\d{1,2}:\\d{1,2}:\\d{1,2}\\sGMT\\+0800");

    /** 格式匹配模式 */
    private static final Pattern PATTERN9 = Pattern.compile("\\d{4}\\d{1,2}");

    /** 格式匹配模式 */
    private static final Pattern PATTERN10 = Pattern.compile("(\\d{4}\\d{1,2}\\d{1,2})");

    /** 格式化 */
    private static final Map<Pattern, String> patternMap = new HashMap<Pattern, String>();

    static {
        patternMap.put(PATTERN1, "yyyy");
        patternMap.put(PATTERN2, "yyyy-MM");
        patternMap.put(PATTERN3, "yyyy-MM-dd");
        patternMap.put(PATTERN4, "yyyy-MM-dd HH:mm");
        patternMap.put(PATTERN5, "yyyy-MM-dd HH:mm:ss");
        patternMap.put(PATTERN6, "yyyy-MM-dd HH:mm:ss.SSS");
        patternMap.put(PATTERN7, "yyyy/MM/dd");
        patternMap.put(PATTERN8, "EEE MMM dd yyyy HH:mm:ss 'GMT+0800'");
        patternMap.put(PATTERN9, "yyyyMM");
        patternMap.put(PATTERN10, "yyyyMMdd");

        // 添加pattern
        patternList.add(PATTERN1);
        patternList.add(PATTERN2);
        patternList.add(PATTERN3);
        patternList.add(PATTERN4);
        patternList.add(PATTERN5);
        patternList.add(PATTERN6);
        patternList.add(PATTERN7);
        patternList.add(PATTERN8);
        patternList.add(PATTERN9);
        patternList.add(PATTERN10);
    }

    /**
     * 获取需要反序列化为正确格式的日期
     *
     * @param strDateValue 字符串类型的日期值
     * @return Date
     */
    public static Date getPatternDate(String strDateValue) {
        String value = strDateValue;
        if (value == null || "".equals(value.trim()) || "null".equalsIgnoreCase(value.trim())) {
            return null;
        }
        // 解决字符串被自动转码导致的问题，在此将转码后的字符串还原。
        if (value.indexOf('%') >= 0) {
            try {
                value = URLDecoder.decode(value, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                //
            }
        }

        String format = getMatchFormat(value);
        if (format == null) {
            // 如果以上8种时间格式都无法匹配，校验是否是时间戳格式，如果是就直接转换为Date，否则直接抛出异常
            Matcher matcher = Pattern.compile("[-]?\\d+").matcher(value);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                return new Date(Long.valueOf(value));
            }
            throw new IllegalArgumentException("不支持的时间格式:" + value);
        }

        if (format.indexOf("GMT") > 0) {
            SimpleDateFormat objSimpleFormat = new SimpleDateFormat(format, Locale.US);
            try {
                return objSimpleFormat.parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException("不支持的时间格式:" + value);
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("不支持的时间格式:" + value);
        }
    }

    /**
     * 根据值获取合适的格式
     *
     *
     * @param value 数据
     * @return 格式
     */
    private static String getMatchFormat(final String value) {
        Pattern pattern = null;
        for (Iterator<Pattern> iterator = patternList.iterator(); iterator.hasNext();) {
            pattern = iterator.next();
            Matcher matcher = pattern.matcher(value);
            boolean isMatch = matcher.matches();
            if (isMatch) {
                return patternMap.get(pattern);
            }
        }
        return null;
    }

}