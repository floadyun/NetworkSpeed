/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.base.lib.util;

import android.text.Editable;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/*
 *
 *
 * @author :liuyuxing
 *
 * @version :1.0
 *
 * @creation date: 2017/4/18 0018
 *
 * @description:
 *
 * @update date :
 */
public class AbStrUtil {

    /**
     * 去掉字符串最后一个字符
     * @param str
     * @return
     */
    public static String removelastStr(String str){
        return str.substring(0,str.length()-1);
    }
    /**
     * 描述：将null转化为“”.
     *
     * @param str 指定的字符串
     * @return 字符串的String类型
     */
    public static String parseEmpty(String str) {
        if (str == null || "null".equals(str.trim())) {
            str = "";
        }
        return str.trim();
    }

    /**
     * 描述：判断一个字符串是否为null或空值.
     *
     * @param string 指定的字符串
     * @return true or false
     */
    public static boolean isEmpty(String string) {
//        return str == null || str.trim().length() == 0;
        if (null == string || "".equals(string) || " ".equals(string)
                || "null".equals(string)) {
            return true;
        }
        return false;
    }

    /**
     * 获取字符串中文字符的长度（每个中文算2个字符）.
     *
     * @param str 指定的字符串
     * @return 中文字符的长度
     */
    public static int chineseLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        if (!isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                /* 获取一个字符 */
                String temp = str.substring(i, i + 1);
	            /* 判断是否为中文字符 */
                if (temp.matches(chinese)) {
                    valueLength += 2;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取字符串的长度.
     *
     * @param str 指定的字符串
     * @return 字符串的长度（中文字符计2个）
     */
    public static int strLength(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    //中文字符长度为2
                    valueLength += 2;
                } else {
                    //其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    /**
     * 描述：获取指定长度的字符所在位置.
     *
     * @param str  指定的字符串
     * @param maxL 要取到的长度（字符长度，中文字符计2个）
     * @return 字符的所在位置
     */
    public static int subStringLength(String str, int maxL) {
        int currentIndex = 0;
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for (int i = 0; i < str.length(); i++) {
            //获取一个字符
            String temp = str.substring(i, i + 1);
            //判断是否为中文字符
            if (temp.matches(chinese)) {
                //中文字符长度为2
                valueLength += 2;
            } else {
                //其他字符长度为1
                valueLength += 1;
            }
            if (valueLength >= maxL) {
                currentIndex = i;
                break;
            }
        }
        return currentIndex;
    }

//    /**
//     * 描述：手机号格式验证.
//     *
//     * @param str 指定的手机号码字符串
//     * @return 是否为手机号码格式:是为true，否则false
//     */
// 	public static Boolean isMobileNo(String str) {
// 		Boolean isMobileNo = false;
// 		try {
//			Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//			Matcher m = p.matcher(str);
//			isMobileNo = m.matches();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
// 		return isMobileNo;
// 	}

    /**
     * 判断手机格式是否正确
     *
     * @param mobiles
     * @return 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     */
    public static boolean isMobilePhone(String mobiles) {
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1][34578]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
    /**
     * 金额输入框中的内容限制（最大：小数点前frontInteger位，小数点后backDecimal位）
     *
     * @param frontInteger 前整数
     * @param backDecimal  后小数
     * @param edt
     * @return
     */
    public static int judgeNumber(int frontInteger, int backDecimal, Editable edt) {
        String temp = edt.toString();
        //判断小数点开头
        if(isNumberDecimalPointStart(temp)){
            if(temp.length()==1){
                edt.delete(0, 1);
                return 3;
            }
            if(temp.length()>3){
                edt.delete(2, 3);//删除小数点后的第三位
                return 3;
            }
        }
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        if (posDot <= 0) {//不包含小数点
            int length = temp.length();
            if (length <= frontInteger) {
                //如果不包含小数点，第一位字符为0，第二位字符还是为0，直接把第二位字符删除
                if (length == 2) {
                    String str = temp.substring(0, 1);
                    if (str.equals("0")) {
                        edt.delete(0, 1);
                        return 1;
                    }
                }
                return 0;//小于frontInteger位数直接返回
            } else {
                edt.delete(frontInteger, frontInteger + 1);//大于frontInteger位数就删掉第frontInteger+1位（只会保留frontInteger位）
                return 2;
            }
        }
        if (temp.length() - posDot - 1 > backDecimal)//如果包含小数点
        {
            edt.delete(posDot + 3, posDot + 4);//删除小数点后的第三位
            return 3;
        }
        return 0;
    }

    /**
     * 金额输入框中的内容限制（最大：整数frontInteger位）
     *
     * @param frontInteger 整数
     * @param edt
     */
    public static int judgeNumber(int frontInteger, Editable edt) {
        String temp = edt.toString();
        int length = temp.length();
        if (length <= frontInteger) {
            if (length == 1 && temp.equals("0")) {
                edt.clear();
                return 1;
            }
            return 0;//小于frontInteger位数直接返回
        } else {
            edt.delete(frontInteger, frontInteger + 1);//大于frontInteger位数就删掉第frontInteger+1位（只会保留frontInteger位）
            return 2;
        }
    }
    /**
     * 判断小数点开头
     * @param temp
     * @return
     */
    public static boolean isNumberDecimalPointStart(String temp) {
        if(temp.indexOf(".")==0){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 描述：是否只是字母或者数字或者汉字.
     * @param str
     * @return
     */
    public static Boolean isNumericOrChineseCharactersOrletters(String str){
        Boolean isNoLetter = false;
        String expr = "^[a-zA-Z0-9\\u4e00-\\u9fa5]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 验证微信号
     * @param str
     * @return
     */
    public static Boolean isWeiXin(String str){
        Boolean isNoLetter = false;
        String expr = "^[A-Z0-9a-z_-]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }
    /**
     * 描述：是否只是字母和数字.
     *
     * @param str 指定的字符串
     * @return 是否只是字母和数字:是为true，否则false
     */
    public static Boolean isNumberLetter(String str) {
        Boolean isNoLetter = false;
        String expr = "^[A-Za-z0-9]+$";
        if (str.matches(expr)) {
            isNoLetter = true;
        }
        return isNoLetter;
    }

    /**
     * 描述：是否只是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为true，否则false
     */
    public static Boolean isNumber(String str) {
// 		Boolean isNumber = false;
// 		String expr = "^[0-9]+$";
// 		if (str.matches(expr)) {
// 			isNumber = true;
// 		}
// 		return isNumber;
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param str  13，14，15，17，18 开头
     * @return
     */
    public static boolean isCellphone(String str) {
        Boolean isPhone = false;
        String regex = "^((14[0-9])|(17[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,0-9]))\\d{8}$";
        if (str.matches(regex)) {
            isPhone = true;
        }
        return isPhone;
    }
    /**
     * 描述：是否是邮箱.
     *
     * @param str 指定的字符串
     * @return 是否是邮箱:是为true，否则false
     */
    public static Boolean isEmail(String str) {
        Boolean isEmail = false;
        String expr = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        if (str.matches(expr)) {
            isEmail = true;
        }
        return isEmail;
    }

    /**
     * 描述：是否是中文.
     *
     * @param str 指定的字符串
     * @return 是否是中文:是为true，否则false
     */
    public static Boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：是否包含中文.
     *
     * @param str 指定的字符串
     * @return 是否包含中文:是为true，否则false
     */
    public static Boolean isContainChinese(String str) {
        Boolean isChinese = false;
        String chinese = "[\u0391-\uFFE5]";
        if (!isEmpty(str)) {
            //获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                //获取一个字符
                String temp = str.substring(i, i + 1);
                //判断是否为中文字符
                if (temp.matches(chinese)) {
                    isChinese = true;
                } else {

                }
            }
        }
        return isChinese;
    }

    /**
     * 描述：从输入流中获得String.
     *
     * @param is 输入流
     * @return 获得的String
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }

            //最后一个\n删除
            if (sb.indexOf("\n") != -1 && sb.lastIndexOf("\n") == sb.length() - 1) {
                sb.delete(sb.lastIndexOf("\n"), sb.lastIndexOf("\n") + 1);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
     * 描述：标准化日期时间类型的数据，不足两位的补0.
     *
     * @param dateTime 预格式的时间字符串，如:2012-3-2 12:2:20
     * @return String 格式化好的时间字符串，如:2012-03-20 12:02:20
     */
    public static String dateTimeFormat(String dateTime) {
        StringBuilder sb = new StringBuilder();
        try {
            if (isEmpty(dateTime)) {
                return null;
            }
            String[] dateAndTime = dateTime.split(" ");
            if (dateAndTime.length > 0) {
                for (String str : dateAndTime) {
                    if (str.indexOf("-") != -1) {
                        String[] date = str.split("-");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append("-");
                            }
                        }
                    } else if (str.indexOf(":") != -1) {
                        sb.append(" ");
                        String[] date = str.split(":");
                        for (int i = 0; i < date.length; i++) {
                            String str1 = date[i];
                            sb.append(strFormat2(str1));
                            if (i < date.length - 1) {
                                sb.append(":");
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sb.toString();
    }

    /**
     * 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    the str
     * @param length 指定字节长度
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length) {
        return cutString(str, length, "");
    }

    /**
     * 描述：截取字符串到指定字节长度.
     *
     * @param str    文本
     * @param length 字节长度
     * @param dot    省略符号
     * @return 截取后的字符串
     */
    public static String cutString(String str, int length, String dot) {
        int strBLen = strlen(str, "GBK");
        if (strBLen <= length) {
            return str;
        }
        int temp = 0;
        StringBuffer sb = new StringBuffer(length);
        char[] ch = str.toCharArray();
        for (char c : ch) {
            sb.append(c);
            if (c > 256) {
                temp += 2;
            } else {
                temp += 1;
            }
            if (temp >= length) {
                if (dot != null) {
                    sb.append(dot);
                }
                break;
            }
        }
        return sb.toString();
    }

    /**
     * 描述：截取字符串从第一个指定字符.
     *
     * @param str1   原文本
     * @param str2   指定字符
     * @param offset 偏移的索引
     * @return 截取后的字符串
     */
    public static String cutStringFromChar(String str1, String str2, int offset) {
        if (isEmpty(str1)) {
            return "";
        }
        int start = str1.indexOf(str2);
        if (start != -1) {
            if (str1.length() > start + offset) {
                return str1.substring(start + offset);
            }
        }
        return "";
    }

    /**
     * 描述：获取字节长度.
     *
     * @param str     文本
     * @param charset 字符集（GBK）
     * @return the int
     */
    public static int strlen(String str, String charset) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int length = 0;
        try {
            length = str.getBytes(charset).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }

    /**
     * 获取大小的描述.
     *
     * @param size 字节个数
     * @return 大小的描述
     */
    public static String getSizeDesc(long size) {
        String suffix = "B";
        if (size >= 1024) {
            suffix = "K";
            size = size >> 10;
            if (size >= 1024) {
                suffix = "M";
                //size /= 1024;
                size = size >> 10;
                if (size >= 1024) {
                    suffix = "G";
                    size = size >> 10;
                    //size /= 1024;
                }
            }
        }
        return size + suffix;
    }

    /**
     * 描述：ip地址转换为10进制数.
     *
     * @param ip the ip
     * @return the long
     */
    public static long ip2int(String ip) {
        ip = ip.replace(".", ",");
        String[] items = ip.split(",");
        return Long.valueOf(items[0]) << 24 | Long.valueOf(items[1]) << 16 | Long.valueOf(items[2]) << 8 | Long.valueOf(items[3]);
    }
    /**
     * 计算小数时保留两位小数,不采用四舍五入
     * @param price
     * @return
     */
    public static String getDecimalFormat(double price) {
        try {
            DecimalFormat formater = new DecimalFormat("0.00");
            formater.setMaximumFractionDigits(2);
            formater.setGroupingSize(0);
            formater.setRoundingMode(RoundingMode.DOWN);
            return formater.format(price);
        }catch (Exception e){
            return price+"";
        }
    }
    /**
     * 如果小数不足2位,会以0补足
     * @param number
     * @return
     */
    public static String getDecimalFormat(String number) {
        try {
            DecimalFormat decimalFormat = new DecimalFormat("0.00");
            return decimalFormat.format(number);
        }catch (Exception e){

        }
        return number;
    }

    /**
     * 校验密码，大小写8-24位
     * @param password
     * @return
     */
    public static boolean checkPassword(String password){
        String regex = "^(?![0-9]+$+.)(?![a-zA-Z]+$)[0-9A-Za-z]{8,24}$";
        return password.matches(regex);
    }
    /**
     * 只允许字母、数字和汉字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isChineseAndIntAndEng(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String s = m.replaceAll("").trim();
        if (s.equals(str)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 只允汉字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isChinese1(String str) throws PatternSyntaxException {
        // 汉字
        String regEx = "[^\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String s = m.replaceAll("").trim();
        if (s.equals(str)) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * 是否是数字和字母
	 * @param str
	 * @return
	 */
	public static Boolean isNumberLetter1(String str) {
		String   regEx  =  "[^a-zA-Z0-9]";
		Pattern   p   =   Pattern.compile(regEx);
		Matcher   m   =   p.matcher(str);
		String s=   m.replaceAll("").trim();
		if(s.equals(str)){
			return true;
		}else {
			return false;
		}
	}
    /**
      * 验证身份证
      */
    public static boolean isShenFenZheng(String str) {
        String strs = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
        Pattern p = Pattern.compile(strs);
        Matcher m = p.matcher(str);
        return m.matches();
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
        //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    /**
     * 只允许字母、数字和汉字
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isChineseAndInt(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^0-9\u4E00-\u9FA5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        String s = m.replaceAll("").trim();
        if (s.equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 描述：是否不是数字.
     *
     * @param str 指定的字符串
     * @return 是否只是数字:是为false，否则true
     */
    public static Boolean isunNumber(String str) {

        String reg = "^[0-9]+(.[0-9]+)?$";
        if (str.matches(reg)){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 判断网址是否合法
     * @param str
     * @return
     */
    public static Boolean isURL(String str){
        String reg="([hH][tT][tT][pP][sS]{0,1})://[a-zA-Z0-9\\.\\-]+\\.([a-zA-Z]{2,4})[^\u4e00-\u9fa5\\s]*";
        if(str.matches(reg)){
            return true;
        }else{
            return  false;
        }
    }

    /**
     * NumberFormat is the abstract base class for all number formats.
     * This class provides the interface for formatting and parsing numbers.
     * @param保留小数，采用四舍五入
     * @return
     */
    public static String formatDouble(double number,int bits) {
        try {
            DecimalFormat formater = new DecimalFormat("0.00");
            formater.setMaximumFractionDigits(bits);
            formater.setGroupingSize(0);
            formater.setRoundingMode(RoundingMode.UP);
            return formater.format(number);
        }catch (Exception e){
            return number+"";
        }
    }
    /**
     * 三位数分割，格式化金额
     * @param amount
     * @return
     */
    public static String formatAmount(double amount){
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,###");
        System.out.println(myformat.format(amount));
        return myformat.format(amount);
    }
}
