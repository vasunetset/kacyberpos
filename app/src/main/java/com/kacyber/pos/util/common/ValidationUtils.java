package com.kacyber.pos.util.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 字符串格式验证工具：
 * <em>
 *     <li>验证自然数</li>
 *     <li>银行卡账号</li>
 *     <li>验证手机号码</li>
 *     <li>验证邮箱</li>
 *     <li>验证验证码</li>
 *     <li>验证密码</li>
 *     <li>验证店铺名</li>
 *     <li>验证身份证号码</li>
 *     <li>验证价格、运费</li>
 *     <li>验证路径</li>
 * </em>
 * Created by caojing on 2016/2/24.
 */
public class ValidationUtils {

    /**
     * 验证自然数
     *
     * @param text
     * @return
     */
    public static boolean isNaturalNumber(String text) {
        Pattern pattern = Pattern.compile("(0|^[1-9]\\d*$)");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * 验证银行卡账号
     * @param text
     * @return
     */
    public static boolean isBankCardNo(String text) {
        Pattern pattern = Pattern.compile("(^\\d{16}$)|(^\\d{19}$)");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * 验证手机号码
     * @param text
     * @return
     */
    public static boolean isPhoneNo(String text) {
        Pattern pattern = Pattern.compile("^(1[3-9][0-9])[0-9]{8}$");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * 验证邮箱
     * @param text
     * @return
     */
    public static boolean isEmail(String text) {
        Pattern pattern = Pattern.compile("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * 验证验证码
     * @param text
     * @return
     */
    public static boolean isCaptcha(String text) {
        Pattern pattern = Pattern.compile("^\\d{4}$");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * 验证密码（长度和格式）
     * @param text
     * @return
     */
    public static boolean isPassword(String text) {
        Pattern pattern = Pattern.compile("^.{6,20}$");
        Matcher matcher = pattern.matcher(text);
        return matcher.matches();
    }

    /**
     * 判断名字只有中文和字母
     * @param text
     * @return
     */
    public static boolean isName(String text) {
        Pattern pattern = Pattern.compile("^[a-z0-9_-]{3,15}$");
       // Pattern pattern = Pattern.compile("^[A-Za-z\\u4e00-\\u9fa5\\s]+$");
        return pattern.matcher(text).matches();
    }

    /**
     * 验证身份证号码
     * @param text
     * @return
     */
    public static boolean isIDNumber(String text) {
        Pattern pattern = Pattern.compile("(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)");
        return pattern.matcher(text).matches();
    }

    /**
     * 验证价格
     * @param text
     * @return
     */
    public static boolean isPrice(String text) {
        Pattern pattern = Pattern.compile("^(-?\\d+)(.\\d+)?$");
        return pattern.matcher(text).matches();
    }

    /**
     * 判断是否为网页路径
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {
        Pattern pattern = Pattern.compile("[a-zA-z]+://[^\\s]*");
        return pattern.matcher(url).matches();
    }
}
