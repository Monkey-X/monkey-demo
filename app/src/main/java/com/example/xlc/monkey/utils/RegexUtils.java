package com.example.xlc.monkey.utils;

import android.support.v4.util.SimpleArrayMap;

import com.example.xlc.monkey.constant.RegexConstant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author:xlc
 * @date:2018/9/3
 * @descirbe: 正则工具类
 */
public class RegexUtils {

    private final static SimpleArrayMap<String, String> CITY_MAP = new SimpleArrayMap<>();

    private RegexUtils() {
    }

    /**
     * 简单手机的正则
     *
     * @param input
     * @return
     */
    public static boolean isMobileSimple(CharSequence input) {
        return isMatch(RegexConstant.REGEX_MOBILE_SIMPLE, input);
    }

    /**
     * 精确手机正则
     *
     * @param input
     * @return
     */
    public static boolean isMobileExact(CharSequence input) {
        return isMatch(RegexConstant.REGEX_MOBILE_EXACT, input);
    }

    /**
     * 电话
     *
     * @param input
     * @return
     */
    public static boolean isTel(CharSequence input) {
        return isMatch(RegexConstant.REGEX_TEL, input);
    }

    public static boolean isIDCard15(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_ID_CARD15, input);
    }

    //    public static boolean

    public static boolean isEmail(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_EMAIL, input);
    }

    public static boolean isURL(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_URL, input);
    }

    public static boolean isZh(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_ZH, input);
    }

    public static boolean isUsername(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_USERNAME, input);
    }

    public static boolean isDate(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_DATE, input);
    }

    public static boolean isIP(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_IP, input);
    }

    public static boolean isIDCard18(final CharSequence input) {
        return isMatch(RegexConstant.REGEX_ID_CARD18, input);
    }

    /**
     * Return whether input matches regex of exact id card number which length is 18.
     *
     * @param input The input.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isIDCard18Exact(final CharSequence input) {
        if (isIDCard18(input)) {
            int[] factor = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            char[] suffix = new char[]{'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};
            if (CITY_MAP.isEmpty()) {
                CITY_MAP.put("11", "北京");
                CITY_MAP.put("12", "天津");
                CITY_MAP.put("13", "河北");
                CITY_MAP.put("14", "山西");
                CITY_MAP.put("15", "内蒙古");

                CITY_MAP.put("21", "辽宁");
                CITY_MAP.put("22", "吉林");
                CITY_MAP.put("23", "黑龙江");

                CITY_MAP.put("31", "上海");
                CITY_MAP.put("32", "江苏");
                CITY_MAP.put("33", "浙江");
                CITY_MAP.put("34", "安徽");
                CITY_MAP.put("35", "福建");
                CITY_MAP.put("36", "江西");
                CITY_MAP.put("37", "山东");

                CITY_MAP.put("41", "河南");
                CITY_MAP.put("42", "湖北");
                CITY_MAP.put("43", "湖南");
                CITY_MAP.put("44", "广东");
                CITY_MAP.put("45", "广西");
                CITY_MAP.put("46", "海南");

                CITY_MAP.put("50", "重庆");
                CITY_MAP.put("51", "四川");
                CITY_MAP.put("52", "贵州");
                CITY_MAP.put("53", "云南");
                CITY_MAP.put("54", "西藏");

                CITY_MAP.put("61", "陕西");
                CITY_MAP.put("62", "甘肃");
                CITY_MAP.put("63", "青海");
                CITY_MAP.put("64", "宁夏");
                CITY_MAP.put("65", "新疆");

                CITY_MAP.put("71", "台湾");
                CITY_MAP.put("81", "香港");
                CITY_MAP.put("82", "澳门");
                CITY_MAP.put("91", "国外");
            }
            if (CITY_MAP.get(input.subSequence(0, 2).toString()) != null) {
                int weightSum = 0;
                for (int i = 0; i < 17; ++i) {
                    weightSum += (input.charAt(i) - '0') * factor[i];
                }
                int idCardMod = weightSum % 11;
                char idCardLast = input.charAt(17);
                return idCardLast == suffix[idCardMod];
            }
        }
        return false;
    }

    /**
     * Return the list of input matches the regex.
     *
     * @param regex The regex.
     * @param input The input.
     * @return the list of input matches the regex
     */
    public static List<String> getMatches(final String regex, final CharSequence input) {
        if (input == null)
            return Collections.emptyList();
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * Splits input around matches of the regex.
     *
     * @param input The input.
     * @param regex The regex.
     * @return the array of strings computed by splitting input around matches of regex
     */
    public static String[] getSplits(final String input, final String regex) {
        if (input == null)
            return new String[0];
        return input.split(regex);
    }

    /**
     * Replace the first subsequence of the input sequence that matches the
     * regex with the given replacement string.
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing the first matching
     * subsequence by the replacement string, substituting captured
     * subsequences as needed
     */
    public static String getReplaceFirst(final String input,
                                         final String regex,
                                         final String replacement) {
        if (input == null)
            return "";
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }


    /**
     * Replace every subsequence of the input sequence that matches the
     * pattern with the given replacement string.
     *
     * @param input       The input.
     * @param regex       The regex.
     * @param replacement The replacement string.
     * @return the string constructed by replacing each matching subsequence
     * by the replacement string, substituting captured subsequences
     * as needed
     */
    public static String getReplaceAll(final String input,
                                       final String regex,
                                       final String replacement) {
        if (input == null)
            return "";
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }


    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }
}
