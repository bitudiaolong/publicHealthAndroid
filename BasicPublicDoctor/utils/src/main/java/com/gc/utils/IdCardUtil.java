package com.gc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/15/015.
 */

public class IdCardUtil {

    /*********************************** 身份证验证开始 ****************************************/
    /**
     * 身份证号码验证 1、号码的结构 公民身份号码是特征组合码，由十七位数字本体码和一位校验码组成。排列顺序从左至右依次为：六位数字地址码，
     * 八位数字出生日期码，三位数字顺序码和一位数字校验码。 2、地址码(前六位数）
     * 表示编码对象常住户口所在县(市、旗、区)的行政区划代码，按GB/T2260的规定执行。 3、出生日期码（第七位至十四位）
     * 表示编码对象出生的年、月、日，按GB/T7408的规定执行，年、月、日代码之间不用分隔符。 4、顺序码（第十五位至十七位）
     * 表示在同一地址码所标识的区域范围内，对同年、同月、同日出生的人编定的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。 5、校验码（第十八位数）
     * （1）十七位数字本体码加权求和公式 S = Sum(Ai * Wi), i = 0, ... , 16 ，先对前17位数字的权求和
     * Ai:表示第i位置上的身份证号码数字值 Wi:表示第i位置上的加权因子 Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
     * （2）计算模 Y = mod(S, 11) （3）通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5 4 3 2
     */

    /**
     * 功能：身份证的有效验证
     *
     * @param IDStr 身份证号
     * @return 有效：返回"" 无效：返回String信息
     * @throws
     */

    public static String IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "X", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }


    public static String getBirthday(String card) {
        if (card.length() == 18) {
            return card.substring(6, 14);
        }
        if (card.length() == 15) {
            String birthdaytemp = card.substring(6, 12);
            return "19" + birthdaytemp;
        }
        return "";
    }

    /**
     * 得到性别。
     *
     * @return 性别：1－男  2－女
     */
    public static String getSex(String card) {
        int p = Integer.parseInt(getOrder(card));
        if (p % 2 == 1) {
            return "1";
        } else {
            return "2";
        }
    }

    /**
     * 功能：设置地区编码
     *
     * @return Hashtable 对象
     */
    @SuppressWarnings("unchecked")
    private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 功能：判断字符串是否为数字
     *
     * @param str
     * @return
     */
    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @param
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到身份证的顺序号。
     *
     * @return 顺序号。
     */
    public static String getOrder(String card) {


        if (card.length() == 15) {
            return card.substring(12, 15);
        } else {
            return card.substring(14, 17);
        }
    }

    public static int getAge(String dateOfBirth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        int age = 0;
        try {
            Date dbDate = dateFormat.parse(dateOfBirth);
            Calendar born = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            if (dateOfBirth != null) {
                now.setTime(new Date());
                born.setTime(dbDate);
                if (born.after(now)) {
                    age = -1;
                }
                age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
                    age -= 1;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return age;
    }

//    private String idCardNum = null;
//
//    private static int IS_EMPTY = 1;
//    private static int LEN_ERROR = 2;
//    private static int CHAR_ERROR = 3;
//    private static int DATE_ERROR = 4;
//    private static int CHECK_BIT_ERROR = 5;
//
//    private String[] errMsg = new String[]{"身份证完全正确！",
//            "身份证为空！",
//            "身份证长度不正确！",
//            "身份证有非法字符！",
//            "身份证中出生日期不合法！",
//            "身份证校验位错误！"};
//
//    private int error = 0;
//
//    /**
//     * 构造方法。
//     *
//     * @param idCardNum
//     */
//    public IdCardUtil(String idCardNum) {
//        // super();
//        this.idCardNum = idCardNum.trim();
//        if (!TextUtils.isEmpty(this.idCardNum)) {
//            this.idCardNum = this.idCardNum.replace("x", "X");
//        }
//    }
//
//    public String getIdCardNum() {
//        return idCardNum;
//    }
//
//    public void setIdCardNum(String idCardNum) {
//        this.idCardNum = idCardNum;
//        if (!TextUtils.isEmpty(this.idCardNum)) {
//            this.idCardNum = this.idCardNum.replace("x", "X");
//        }
//    }
//
//    /**
//     * 得到身份证详细错误信息。
//     *
//     * @return 错误信息。
//     */
//    public String getErrMsg() {
//        return this.errMsg[this.error];
//    }
//
//    /**
//     * 是否为空。
//     *
//     * @return true: null  false: not null;
//     */
//    public boolean isEmpty() {
//        if (this.idCardNum == null)
//            return true;
//        else
//            return this.idCardNum.trim().length() > 0 ? false : true;
//    }
//
//    /**
//     * 身份证长度。
//     *
//     * @return
//     */
//    public int getLength() {
//        return this.isEmpty() ? 0 : this.idCardNum.length();
//    }
//
//    /**
//     * 身份证长度。
//     *
//     * @return
//     */
//    public int getLength(String str) {
//        return this.isEmpty() ? 0 : str.length();
//    }
//
//    /**
//     * 是否是15位身份证。
//     *
//     * @return true: 15位  false：其他。
//     */
//    public boolean is15() {
//        return this.getLength() == 15;
//    }
//
//    /**
//     * 是否是18位身份证。
//     *
//     * @return true: 18位  false：其他。
//     */
//    public boolean is18() {
//        return this.getLength() == 18;
//    }
//
//    /**
//     * 得到身份证的省份代码。
//     *
//     * @return 省份代码。
//     */
//    public String getProvince() {
//        return this.isCorrect() == 0 ? this.idCardNum.substring(0, 2) : "";
//    }
//
//    /**
//     * 得到身份证的城市代码。
//     *
//     * @return 城市代码。
//     */
//    public String getCity() {
//        return this.isCorrect() == 0 ? this.idCardNum.substring(2, 4) : "";
//    }
//
//    /**
//     * 得到身份证的区县代码。
//     *
//     * @return 区县代码。
//     */
//    public String getCountry() {
//        return this.isCorrect() == 0 ? this.idCardNum.substring(4, 6) : "";
//    }
//
//    /**
//     * 得到身份证的出生年份。
//     *
//     * @return 出生年份。
//     */
//    public String getYear() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.getLength() == 15) {
//            return "19" + this.idCardNum.substring(6, 8);
//        } else {
//            return this.idCardNum.substring(6, 10);
//        }
//    }
//
//    /**
//     * 得到身份证的出生月份。
//     *
//     * @return 出生月份。
//     */
//    public String getMonth() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.getLength() == 15) {
//            return this.idCardNum.substring(8, 10);
//        } else {
//            return this.idCardNum.substring(10, 12);
//        }
//    }
//
//    /**
//     * 得到身份证的出生日子。
//     *
//     * @return 出生日期。
//     */
//    public String getDay() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.getLength() == 15) {
//            return this.idCardNum.substring(10, 12);
//        } else {
//            return this.idCardNum.substring(12, 14);
//        }
//    }
//
//    /**
//     * 得到身份证的出生日期。
//     *
//     * @return 出生日期。
//     */
//    public String getBirthday() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.getLength() == 15) {
//            return "19" + this.idCardNum.substring(6, 12);
//        } else {
//            return this.idCardNum.substring(6, 14);
//        }
//    }
//
//    /**
//     * 得到身份证的出生年月。
//     *
//     * @return 出生年月。
//     */
//    public String getBirthMonth() {
//        return getBirthday().substring(0, 6);
//    }
//
//    /**
//     * 得到身份证的顺序号。
//     *
//     * @return 顺序号。
//     */
//    public String getOrder() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.getLength() == 15) {
//            return this.idCardNum.substring(12, 15);
//        } else {
//            return this.idCardNum.substring(14, 17);
//        }
//    }
//
//    /**
//     * 得到性别。
//     *
//     * @return 性别：1－男  2－女
//     */
//    public String getSex() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        int p = Integer.parseInt(getOrder());
//        if (p % 2 == 1) {
//            return "男";
//        } else {
//            return "女";
//        }
//    }
//
//    /**
//     * 得到性别值。
//     *
//     * @return 性别：1－男  2－女
//     */
//    public String getSexValue() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        int p = Integer.parseInt(getOrder());
//        if (p % 2 == 1) {
//            return "1";
//        } else {
//            return "2";
//        }
//    }
//
//    /**
//     * 得到校验位。
//     *
//     * @return 校验位。
//     */
//    public String getCheck() {
//        if (!this.isLenCorrect())
//            return "";
//
//        String lastStr = this.idCardNum.substring(this.idCardNum.length() - 1);
//        if ("x".equals(lastStr)) {
//            lastStr = "X";
//        }
//        return lastStr;
//    }
//
//    /**
//     * 得到15位身份证。
//     *
//     * @return 15位身份证。
//     */
//    public String to15() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.is15())
//            return this.idCardNum;
//        else
//            return this.idCardNum.substring(0, 6) + this.idCardNum.substring(8, 17);
//    }
//
//    /**
//     * 得到18位身份证。
//     *
//     * @return 18位身份证。
//     */
//    public String to18() {
//        if (this.isCorrect() != 0)
//            return "";
//
//        if (this.is18())
//            return this.idCardNum;
//        else
//            return this.idCardNum.substring(0, 6) + "19" + this.idCardNum.substring(6) + this.getCheckBit();
//    }
//
//    /**
//     * 得到18位身份证。
//     *
//     * @return 18位身份证。
//     */
//    public static String toNewIdCard(String tempStr) {
//        if (tempStr.length() == 18)
//            return tempStr.substring(0, 6) + tempStr.substring(8, 17);
//        else
//            return tempStr.substring(0, 6) + "19" + tempStr.substring(6) + getCheckBit(tempStr);
//    }
//
//    /**
//     * 校验身份证是否正确
//     *
//     * @return 0：正确
//     */
//    public int isCorrect() {
//        if (this.isEmpty()) {
//            this.error = IdCardUtil.IS_EMPTY;
//            return this.error;
//        }
//
//        if (!this.isLenCorrect()) {
//            this.error = IdCardUtil.LEN_ERROR;
//            return this.error;
//        }
//
//        if (!this.isCharCorrect()) {
//            this.error = IdCardUtil.CHAR_ERROR;
//            return this.error;
//        }
//
//        if (!this.isDateCorrect()) {
//            this.error = IdCardUtil.DATE_ERROR;
//            return this.error;
//        }
//
//        if (this.is18()) {
//            if (!this.getCheck().equals(this.getCheckBit())) {
//                this.error = IdCardUtil.CHECK_BIT_ERROR;
//                return this.error;
//            }
//        }
//
//        return 0;
//    }
//
//
//    private boolean isLenCorrect() {
//        return this.is15() || this.is18();
//    }
//
//    /**
//     * 判断身份证中出生日期是否正确。
//     *
//     * @return
//     */
//    private boolean isDateCorrect() {
//
//        /*非闰年天数*/
//        int[] monthDayN = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//        /*闰年天数*/
//        int[] monthDayL = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
//
//        int month;
//        if (this.is15()) {
//            month = Integer.parseInt(this.idCardNum.substring(8, 10));
//        } else {
//            month = Integer.parseInt(this.idCardNum.substring(10, 12));
//        }
//
//        int day;
//        if (this.is15()) {
//            day = Integer.parseInt(this.idCardNum.substring(10, 12));
//        } else {
//            day = Integer.parseInt(this.idCardNum.substring(12, 14));
//        }
//
//        if (month > 12 || month <= 0) {
//            return false;
//        }
//
//        if (this.isLeapyear()) {
//            if (day > monthDayL[month - 1] || day <= 0)
//                return false;
//        } else {
//            if (day > monthDayN[month - 1] || day <= 0)
//                return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * 得到校验位。
//     *
//     * @return
//     */
//    private String getCheckBit() {
//        if (!this.isLenCorrect())
//            return "";
//
//        String temp = null;
//        if (this.is18())
//            temp = this.idCardNum;
//        else
//            temp = this.idCardNum.substring(0, 6) + "19" + this.idCardNum.substring(6);
//
//
//        String checkTable[] = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
//        int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
//        int sum = 0;
//
//        for (int i = 0; i < 17; i++) {
//            String ch = temp.substring(i, i + 1);
//            sum = sum + Integer.parseInt(ch) * wi[i];
//        }
//
//        int y = sum % 11;
//
//        return checkTable[y];
//    }
//
//
//    /**
//     * 得到校验位。
//     *
//     * @return
//     */
//    private static String getCheckBit(String str) {
//
//        String temp = null;
//        if (str.length() == 18)
//            temp = str;
//        else
//            temp = str.substring(0, 6) + "19" + str.substring(6);
//
//
//        String checkTable[] = new String[]{"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
//        int[] wi = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
//        int sum = 0;
//
//        for (int i = 0; i < 17; i++) {
//            String ch = temp.substring(i, i + 1);
//            sum = sum + Integer.parseInt(ch) * wi[i];
//        }
//
//        int y = sum % 11;
//
//        return checkTable[y];
//    }
//
//
//    /**
//     * 身份证号码中是否存在非法字符。
//     *
//     * @return true: 正确  false：存在非法字符。
//     */
//    private boolean isCharCorrect() {
//        boolean iRet = true;
//
//        if (this.isLenCorrect()) {
//            byte[] temp = this.idCardNum.getBytes();
//
//            if (this.is15()) {
//                for (int i = 0; i < temp.length; i++) {
//                    if (temp[i] < 48 || temp[i] > 57) {
//                        iRet = false;
//                        break;
//                    }
//                }
//            }
//
//            if (this.is18()) {
//                for (int i = 0; i < temp.length; i++) {
//                    if (temp[i] < 48 || temp[i] > 57) {
//                        if (i == 17 && temp[i] != 88) {
//                            iRet = false;
//                            break;
//                        }
//                    }
//                }
//            }
//        } else {
//            iRet = false;
//        }
//        return iRet;
//    }
//
//    /**
//     * 判断身份证的出生年份是否未闰年。
//     *
//     * @return true ：闰年  false 平年
//     */
//    private boolean isLeapyear() {
//        String temp;
//
//        if (this.is15()) {
//            temp = "19" + this.idCardNum.substring(6, 8);
//        } else {
//            temp = this.idCardNum.substring(6, 10);
//        }
//
//        int year = Integer.parseInt(temp);
//
//        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
//            return true;
//        else
//            return false;
//    }


}
