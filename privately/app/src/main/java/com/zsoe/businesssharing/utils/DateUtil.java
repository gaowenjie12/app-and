package com.zsoe.businesssharing.utils;

import android.text.TextUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

public class DateUtil {

    // 相关日期格式
    public static SimpleDateFormat formatModel1 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat formatModel2 = new SimpleDateFormat(
            "yyyyMMddHHmmss");

    public static SimpleDateFormat formatModel3 = new SimpleDateFormat(
            "yyyy-MM-dd");

    private static SimpleDateFormat formatModel4 = new SimpleDateFormat(
            "yyyyMMdd");
    public static SimpleDateFormat formatModel5 = new SimpleDateFormat(
            "yyyy年MM月dd日");
    public static SimpleDateFormat formatModel6 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static SimpleDateFormat formatModel7 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    private static SimpleDateFormat formatModel8 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss:SSS");

    public static SimpleDateFormat formatModel9 = new SimpleDateFormat("M月d日");
    private static SimpleDateFormat formatModel10 = new SimpleDateFormat(
            "yyyy年MM月dd日 HH:mm");

    public static SimpleDateFormat formatModel11 = new SimpleDateFormat(
            "yyyy.MM.dd");

    public static SimpleDateFormat formatModel12 = new SimpleDateFormat(
            "yyyy");

    public static SimpleDateFormat formatModel13 = new SimpleDateFormat(
            "MM月dd日 HH:mm");


    public static SimpleDateFormat formatModel14 = new SimpleDateFormat(
            "MM-dd HH:mm");

    public static SimpleDateFormat formatModel15 = new SimpleDateFormat(
            "MM.dd HH:mm");


    public static SimpleDateFormat formatModel16 = new SimpleDateFormat(
            "yyyy.MM.dd HH:mm");


    public static SimpleDateFormat formatModel17 = new SimpleDateFormat(
            "HH:mm");


    public static SimpleDateFormat formatModel18 = new SimpleDateFormat(
            "MM-dd");

    /**
     * 获取当前时间格式为(yyyy)的日期字符串
     *
     * @return
     */
    public static String getYyyy() {
        String dateStr = "";
        try {
            Date date = new Date();
            dateStr = formatModel12.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取当前时间格式为(yyyymmdd)的日期字符串
     *
     * @return
     */
    public static String getYyyyMmDd() {
        String dateStr = "";
        try {
            Date date = new Date();
            dateStr = formatModel4.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取当前时间格式为(yyyyMMddHHmmss)的日期字符串
     *
     * @return
     */
    public static String getYyyyMmDdHms() {
        String dateStr = "";
        try {
            Date date = new Date();
            dateStr = formatModel2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取当前时间格式为(yyyy.MM.dd HH:mm)的日期字符串
     *
     * @return
     */
    public static String getYyyyMmDdHm(Date date) {
        String dateStr = "";
        try {
            dateStr = formatModel6.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取当前时间格式为(yyyy-MM-dd)的日期字符串
     *
     * @return
     */
    public static String getYyyyMmDd(Date date) {
        String dateStr = "";
        try {
            dateStr = formatModel3.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取当前时间格式为(MM-dd)的日期字符串
     *
     * @return
     */
    public static String getMmDd(Date date) {
        String dateStr = "";
        try {
            dateStr = formatModel18.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 获取时间格式为（yyyy-MM-dd HH:mm:ss:SSS）
     *
     * @param date
     * @return
     */
    public static String getMMDDHHMM(Date date) {
        String dateStr = "";
        try {
            dateStr = formatModel10.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static long getLongTime(String time) {
        try {
            return formatModel6.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取时间格式为（yyyy-MM-dd HH:mm:ss:SSS）
     *
     * @param date
     * @return
     */
    public static String getDetailStr(Date date) {
        String dateStr = "";
        try {
            dateStr = formatModel8.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * 字符串转换时间
     *
     * @param dateStr 时间字符串
     * @param bool    （true:时间加时分秒，false:时间不加时分秒）
     * @return 时间
     */
    public static Date strFormatDate(String dateStr, boolean bool) {
        Date date = null;
        try {
            if (bool) {
                date = formatModel1.parse(dateStr);
            } else {
                date = formatModel3.parse(dateStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date strFormatDate(String dateStr) {
        Date date = null;
        try {
            date = formatModel10.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将时间戳转换为日期格式
     *
     * @param dateStr 时间戳
     * @return
     */
    public static String strFormatTimeStrYyyyMmDd(String dateStr) {
        String timeStr = null;
        try {
            timeStr = formatModel3.format(new Date(Long.parseLong(dateStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    /**
     * 将时间戳转换为日期格式
     *
     * @param dateStr 时间戳
     * @return
     */
    public static String strFormatTimeStr(String dateStr) {
        String timeStr = null;
        try {
            timeStr = formatModel7.format(new Date(Long.parseLong(dateStr)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    /**
     * 两个时间差
     *
     * @return
     */
    public static Long dateDiffOfDay(Date firstDate, Date secondDate) {
        Long lastTime = secondDate.getTime() - firstDate.getTime();
        Long lastDays = lastTime / (24 * 60 * 60 * 1000);
        return lastDays;
    }

    /**
     * 相差的小时数
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static Long dateDiffOfHour(Date firstDate, Date secondDate) {
        Long lastTime = secondDate.getTime() - firstDate.getTime();
        Long lastHours = lastTime / (60 * 60 * 1000);
        return lastHours;
    }

    public static Date dateAddDay(Date date, Integer days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    /**
     * @param currentTimeMillis 起始时间戳
     * @param days              累加天数
     * @return 返回 1月2号 去掉两位数 前面的“0”
     */
    public static String dateAddDay(String currentTimeMillis, int days,
                                    SimpleDateFormat simpFormat) {
        Date dateAddDay = DateUtil.dateAddDay(
                new Date(Long.parseLong(currentTimeMillis)), days);
        return simpFormat.format(dateAddDay);

    }

    /**
     * @param currentTimeMillis 起始时间戳
     * @param days              累加天数
     * @return 返回 1月2号 去掉两位数 前面的“0”
     */
    public static String dateAddDay(String currentTimeMillis, int days) {
        Date dateAddDay = DateUtil.dateAddDay(
                new Date(Long.parseLong(currentTimeMillis)), days);
        return formatModel9.format(dateAddDay);

    }

    /**
     * 输出日期格式：yyyy-MM-dd 的字符串格式。
     *
     * @param date Date
     * @return String
     */
    public static String formatDate(Date date) {
        return formatModel3.format(date);
    }

    public static String formatDate(Date date,
                                    SimpleDateFormat formatModel) {
        return formatModel.format(date);
    }

    /**
     * 计算剩余天数
     *
     * @param days    天数
     * @param created 创建日期
     * @return
     */
    public static String getRemainingTime(Integer days, Date created) {
        Calendar c = Calendar.getInstance();
        c.setTime(created);
        c.add(Calendar.DATE, days);
        Date end = new Date();
        long between = (c.getTimeInMillis() - end.getTime()) / 1000;
        long day1 = between / (24 * 3600);
        long hour1 = between % (24 * 3600) / 3600;
        // long minute1=between%3600/60;
        // long second1=between%60/60;
        return "" + day1 + "天" + hour1 + "小时";
    }

    /**
     * 根据类型格式化日期时间
     *
     * @param type 类型
     * @return 格式化完成后的日期时间
     */
    public static String getFormatDateType(String type) {

        SimpleDateFormat df = new SimpleDateFormat(type);

        return df.format(new Date());

    }

    public static String getFormatTimestampByType(Timestamp date, String type) {

        SimpleDateFormat df = new SimpleDateFormat(type);

        return df.format(date);

    }

    /**
     * 下面的两个成员变量分别是日期分隔符字符串和字符串分隔器，专门用来解析字符串格式的日期
     * 程序中主要的日期分隔符为"-"和"/"，且日期序列为“年/月/日”型，其内容缺一不可 例如:09/02/02或2009-02-02
     */
    public static final String DATE_SEPARATOR = "-/: ";
    public static StringTokenizer sToken;

    // 1.1格式转换

    /**
     * 将日期变为字符串格式 yyyy-MM-dd
     **/
    public static String format(GregorianCalendar pCal) {
        return formatModel3.format(pCal.getTime());
    }

    /**
     * 将日期变为字符串格式 yyyy-MM-dd
     **/
    public static String format(Date pDate) {
        if (EmptyUtil.isEmpty(pDate)) {
            return "";
        }
        return formatModel3.format(pDate);
    }

    /**
     * 将日期变为字符串格式 yyyy-MM-dd HH:mm:ss
     **/
    public static String fullFormat(Date pDate) {
        return formatModel1.format(pDate);
    }

    /**
     * 将字符串格式的日期转换为Calender
     **/
    public static GregorianCalendar parse2Cal(String pDateStr) {
        sToken = new StringTokenizer(pDateStr, DATE_SEPARATOR);
        int vYear = Integer.parseInt(sToken.nextToken());
        // GregorianCalendar的月份是从0开始算起的，变态！！
        int vMonth = Integer.parseInt(sToken.nextToken()) - 1;
        int vDayOfMonth = Integer.parseInt(sToken.nextToken());
        return new GregorianCalendar(vYear, vMonth, vDayOfMonth);
    }

    /**
     * 将字符串类型的日期转换成Date(yyyy-MM-dd)
     **/
    public static Date parse2Date(String pDate) {
        try {
            if (EmptyUtil.isEmpty(pDate)) {
                return null;
            }
            return formatModel3.parse(pDate);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 将字符串类型的日期转换成Date (yyyy-MM-dd HH:mm:ss)
     **/
    public static Date parse2FullDate(String pDate) {
        try {
            return formatModel1.parse(pDate);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static Date parse(String pDate, String formatType) {
        try {
            if (EmptyUtil.isEmpty(formatType)) {
                return null;
            }
            SimpleDateFormat fmt = new SimpleDateFormat(formatType);
            return fmt.parse(pDate);
        } catch (ParseException ex) {
            return null;
        }
    }

    /**
     * @throws
     * @Title:toChineseDate
     * @Description:获取中文日期格式
     * @param:@param pDate
     * @param:@return
     * @return:String yyyy年MM月dd日
     * @Create: 2013-7-3 下午5:01:14
     * @Author : Andy Tang
     */
    public static String toChineseDate(Date date) {
        try {
            return formatModel5.format(date);
        } catch (Exception ex) {
            return null;
        }
    }

    // 1.2.取得两个日期之间所差天数的方法

    /**
     * 返回未来的某一天和今天所差的日期数 注意，这里要clone一个新的日期以免对原始日期类造成的修改。
     * 而在daysBetween(GregorianCalendarpFormer,GregorianCalendarpLatter)就
     * 直接处理而不进行clone动作，因为这里已经做了:)
     **/
    public static int daysBetween(GregorianCalendar pFurtherDay) {
        GregorianCalendar vToday = new GregorianCalendar();
        GregorianCalendar vFurtherDay = (GregorianCalendar) pFurtherDay.clone();
        return daysBetween(vToday, vFurtherDay);
    }

    /**
     * 上面函数的String版本
     **/
    public static int daysBetween(String pFurtherDayStr) {
        GregorianCalendar vFurtherDay = DateUtil.parse2Cal(pFurtherDayStr);
        GregorianCalendar vToday = new GregorianCalendar();
        return daysBetween(vToday, vFurtherDay);
    }

    /**
     * 返回较晚的时间(latter)与较早的时间(former)所差的天数
     **/
    public static int daysBetween(String pFormerStr, String pLatterStr) {
        GregorianCalendar pFormer = DateUtil.parse2Cal(pFormerStr);
        GregorianCalendar pLatter = DateUtil.parse2Cal(pLatterStr);
        return daysBetween(pFormer, pLatter);
    }

    /**
     * 返回较晚的时间(latter)与较早的时间(former)所差的天数
     **/
    public static int daysBetween(GregorianCalendar pFormer,
                                  GregorianCalendar pLatter) {
        GregorianCalendar vFormer = pFormer, vLatter = pLatter;
        boolean vPositive = true;
        if (pFormer.before(pLatter)) {
            vFormer = pFormer;
            vLatter = pLatter;
        } else {
            vFormer = pLatter;
            vLatter = pFormer;
            vPositive = false;
        }

        vFormer.set(Calendar.MILLISECOND, 0);
        vFormer.set(Calendar.SECOND, 0);
        vFormer.set(Calendar.MINUTE, 0);
        vFormer.set(Calendar.HOUR_OF_DAY, 0);
        vLatter.set(Calendar.MILLISECOND, 0);
        vLatter.set(Calendar.SECOND, 0);
        vLatter.set(Calendar.MINUTE, 0);
        vLatter.set(Calendar.HOUR_OF_DAY, 0);

        int vCounter = 0;
        while (vFormer.before(vLatter)) {
            vFormer.add(Calendar.DATE, 1);
            vCounter++;
        }
        if (vPositive)
            return vCounter;
        else
            return -vCounter;
    }

    // 1.3.两个日期的月份差

    /**
     * 本月和未来一个月的月份差
     **/
    public static int monthsBetween(GregorianCalendar pFurtherMonth) {
        GregorianCalendar vToday = new GregorianCalendar();
        GregorianCalendar vFurtherMonth = (GregorianCalendar) pFurtherMonth
                .clone();
        return monthsBetween(vToday, vFurtherMonth);
    }

    /**
     * 给定月分和本月的月份差
     **/
    public static int monthsBetween(String pFurtherMonth) {
        GregorianCalendar vToday = new GregorianCalendar();
        GregorianCalendar vFurtherMonth = DateUtil.parse2Cal(pFurtherMonth);
        return monthsBetween(vToday, vFurtherMonth);
    }

    /**
     * 给定两个时间相差的月份,String版
     **/
    public static int monthsBetween(String pFormerStr, String pLatterStr) {
        GregorianCalendar vFormer = DateUtil.parse2Cal(pFormerStr);
        GregorianCalendar vLatter = DateUtil.parse2Cal(pLatterStr);
        return monthsBetween(vFormer, vLatter);
    }

    public static int monthsBetween(GregorianCalendar pFormer,
                                    GregorianCalendar pLatter) {
        GregorianCalendar vFormer = pFormer, vLatter = pLatter;
        boolean vPositive = true;
        if (pFormer.before(pLatter)) {
            vFormer = pFormer;
            vLatter = pLatter;
        } else {
            vFormer = pLatter;
            vLatter = pFormer;
            vPositive = false;
        }
        int vCounter = 0;
        while (vFormer.get(vFormer.YEAR) != vLatter.get(vLatter.YEAR)
                || vFormer.get(vFormer.MONTH) != vLatter.get(vLatter.MONTH)) {
            vFormer.add(Calendar.MONTH, 1);
            vCounter++;
        }
        if (vPositive)
            return vCounter;
        else
            return -vCounter;
    }

    /**
     * 返回今天是本月的第几天
     **/
    public static int dayOfMonthOfToday() {
        GregorianCalendar vTodayCal = new GregorianCalendar();
        return vTodayCal.get(vTodayCal.DAY_OF_MONTH);
    }

    /**
     * 返回本月是本年的第几个月
     **/
    public static int monthOfYear() {
        GregorianCalendar vTodayCal = new GregorianCalendar();
        return vTodayCal.get(vTodayCal.MONTH) + 1;
    }

    // 返回给定日期的月份
    public static String getMonth(String pFormattedDate) {
        StringTokenizer vSt = new StringTokenizer(pFormattedDate, "-");
        vSt.nextToken();// 跳过年份
        int val = Integer.parseInt(vSt.nextToken());
        return val + "";
    }

    /**
     * 获得从本月开始到本月+pZoneSize区间内的月数
     **/
    public static String[] monthList(int pZoneSize) {
        String[] vMonthList = new String[pZoneSize];
        GregorianCalendar vTodayCal = new GregorianCalendar();
        for (int i = 0; i < pZoneSize; i++) {
            vMonthList[i] = String.valueOf(vTodayCal.get(vTodayCal.MONTH) + 1);
            vTodayCal.roll(vTodayCal.MONTH, true);
        }
        return vMonthList;
    }

    /**
     * 仅显示小数点后两位的Formater
     **/
    public static DecimalFormat formatter = new DecimalFormat("####.##");

    /**
     * 将给定的数字变成小数点后两位的字符串
     **/
    public static String format(double pSrcVal) {
        return formatter.format(pSrcVal);
    }

    public static String parseTimeMini(String dateStr) {
        Date dd = null;
        try {
            dd = formatModel1.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dd == null) {
            return null;
        }
        return String.valueOf(dd.getTime());
    }

    /**
     * 将指定日期增量后得到一个新的日期
     *
     * @param type 增量类型，Calendar.DAY_OF_MONTH、Calendar.MONTH等
     * @param num  增量的数量值
     */
    public static Date add(Date date, int type, int num) {
        Calendar cla = Calendar.getInstance();
        cla.setTime(date);
        cla.add(type, num);
        return cla.getTime();
    }

    /**
     * 将指定日期增量后得到一个新的日期
     *
     * @param dataStr 当前时间yy-MM-dd
     *                增量的数量值
     */
    public static String getAddDeta(String dataStr, int day) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int d = c.get(Calendar.DATE);
        c.set(Calendar.DATE, d + day);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    /**
     * 日期比较大小 返回 0 表示时间日期相同; 1 表示日期1>日期2; -1 表示日期1<日期2; -2 错误
     */
    public static int compareDate(String str1, String str2) {
        try {
            Date date1 = formatModel6.parse(str1);
            Date date2 = new Date();
            if (!EmptyUtil.isEmpty(str2)) {
                String timeStr = getStrTime(str2);
                if (!TextUtils.isEmpty(timeStr)) {
                    date2 = formatModel6.parse(timeStr);
                } else {
                    return -2;
                }
            }
            int number = date1.compareTo(date2);
            return number;
        } catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }


    /**
     * 日期比较大小 返回 0 表示时间日期相同; 1 表示日期1>日期2; -1 表示日期1<日期2; -2 错误
     */
    public static int compareDate2(String str1, String str2) {
        try {
            Date date1 = formatModel6.parse(str1);
            Date date2 = formatModel6.parse(str2);
            int number = date1.compareTo(date2);
            return number;
        } catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }

    /**
     * 日期比较大小 返回 0 表示时间日期相同; 1 表示日期1>日期2; -1 表示日期1<日期2; -2 错误
     */
    public static int compareTaskDate(String str1, String str2) {
        try {
            Date date1 = formatModel3.parse(str1);
            Date date2 = new Date();
            if (!EmptyUtil.isEmpty(str2)) {
                String timeStr = getStrTime(str2, formatModel3);
                if (!TextUtils.isEmpty(timeStr)) {
                    date2 = formatModel3.parse(timeStr);
                } else {
                    return -2;
                }
            }
            int number = date1.compareTo(date2);
            return number;
        } catch (ParseException e) {
            e.printStackTrace();
            return -2;
        }
    }

    /**
     * 日期比较大小 返回 0 表示时间日期相同 1 表示日期1>日期2 -1 表示日期1<日期2
     */
    public static int CompareDate(Date date1, Date date2) {
        int number = date1.compareTo(date2);
        return number;
    }

    /**
     * @param current 当前的时间戳
     * @param value   要比较的时间戳
     * @return 两个时间戳相差的天数
     */
    public static long differValue(String current, String value) {
        Date date;
        Date mydate;
        long day = 0;
        try {
            date = formatModel3.parse(strFormatTimeStrYyyyMmDd(current));
            mydate = formatModel3.parse(strFormatTimeStrYyyyMmDd(value));
            day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;

    }

    public static long differValueday(String startTime, String endTime) {

        long differValue = 0;
        try {
            Date startTimeDate = formatModel3.parse(startTime);
            Date endTimeDate = formatModel3.parse(endTime);

            differValue = differValue(startTimeDate.getTime() + "",
                    endTimeDate.getTime() + "");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return differValue;

    }


    public static boolean isInDay(long time) {
        Date currentTime = new Date(time * 1000);
        Date today = new Date();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTime(today);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTime(today);
        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
        calendarEnd.set(Calendar.MINUTE, 59);
        return currentTime.after(calendarStart.getTime()) && currentTime.before(calendarEnd.getTime());
    }

    //获取星期
    public static String getWeek4Millions(long millions) {

        Date date = new Date(millions);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        String[] weekOfDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    //获取星期
    public static String getDate24Millions(long millions) {

        Date date = new Date(millions);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return 1 + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }


    //获取天数
    public static int isAbigerB(String dateA, String dateB)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long date = sdf.parse(dateA).getTime() - sdf.parse(dateB).getTime();
        return (int) (date / 1000);
    }

    //获取天数
    public static String getDay4Date(String begin, String end)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long date = sdf.parse(end).getTime() - sdf.parse(begin).getTime();
        return date / (1000 * 60 * 60 * 24) + "";
    }

    //获取时长
    public static String getTime(int millions) {
        int hour = millions / (60 * 60);
        int minute = (millions / 60) % 60;
        int second = millions % 60;
//        int day=millions/360/24;
        return hour + "小时" + minute + "分" + second + "秒";
    }

    public static boolean isInServiceTime(String startTime, String endTime) {
        SimpleDateFormat formatters = new SimpleDateFormat("HH:mm");
        Date curDates = new Date(System.currentTimeMillis());// 获取当前时间
        String strs = formatters.format(curDates);
        int cTime = Integer.parseInt(strs.replace(":", ""));
        //三位变两位
        String[] starts = startTime.split(":");
        int sTime = Integer.parseInt(starts[0]) * 100 + Integer.parseInt(starts[1]);
        String[] ends = endTime.split(":");
        int eTime = Integer.parseInt(ends[0]) * 100 + Integer.parseInt(ends[1]);
        if (eTime < sTime) {//跨越时间周期
            return (sTime < cTime && cTime <= 2400) || (0 <= cTime && cTime < eTime);
        } else {//一个时间周期内
            return sTime < cTime && cTime < eTime;
        }
    }

    //获取日期
    public static String getDate4Second(int millions) {

        Date date = new Date(millions * 1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    //获取日期
    public static String getTime4Millions() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    //获取日期
    public static String getTime4Millions(long millions) {

        Date date = new Date(millions);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(date);
    }

    //获取日期时间
    public static String getTime6Millions(long millions) {

        Date date = new Date(millions);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return sdf.format(date);
    }

    //获取日期
    public static String getTime42Millions(long millions) {
        Date date = new Date(millions);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    //获取星期
    public static String getDate4Millions(long millions) {

        Date date = new Date(millions);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return 1 + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "\t" + weekOfDays[w];
    }

    /**
     * 计算时间差
     *
     * @param starTime 开始时间
     * @param endTime  结束时间
     * @return 返回时间差
     */
    public String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 计算相差的小时
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static int getTimeDifferenceHour(String starTime, String endTime) {
        int timeString = 0;
        if (TextUtils.isEmpty(starTime) || TextUtils.isEmpty(endTime)) {
            return timeString;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

            timeString = (int) hour1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return yyyy-MM-dd HH:mm
     */
    public static String getStrTime(String timeStamp) {
        if (!TextUtils.isEmpty(timeStamp)) {
            String timeString;
            long l = Long.valueOf(timeStamp);
            timeString = formatModel6.format(new Date(l));
            return timeString;
        }
        return null;
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return yyyy-MM-dd HH:mm
     */
    public static String getStrTime(String timeStamp, SimpleDateFormat formatModel) {
        if (!TextUtils.isEmpty(timeStamp)) {
            String timeString;
            long l = Long.valueOf(timeStamp);
            timeString = formatModel.format(new Date(l));
            return timeString;
        }
        return null;
    }

    /**
     * 转换帖子显示时间
     *
     * @param timeStamp
     * @return "MM.dd HH:mm
     * 2017-11-29 11:26:20
     */
    public static String getSpeakTime(String timeStamp) {
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                String timeString;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(timeStamp);
                timeString = formatModel14.format(date);
                return timeString;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return "MM-dd HH:mm
     * 2017-11-29 11:26:20
     */
    public static String getLiveStrTime(String timeStamp) {
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                String timeString;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(timeStamp);
                timeString = formatModel14.format(date);
                return timeString;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return "HH:mm
     * 2017-11-29 11:26:20
     */
    public static String getHHMM(String timeStamp) {
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                String timeString;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = format.parse(timeStamp);
                timeString = formatModel17.format(date);
                return timeString;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转字符串
     *
     * @param Date date
     * @return "HH:mm
     * 2017-11-29 11:26:20
     */
    public static String getHHMM(Date date) {
        return formatModel17.format(date);
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return "MM.dd HH:mm
     * 2017-11-29 11:26:20
     */
    public static String getHHPMM(String timeStamp) {
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                String timeString;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = format.parse(timeStamp);
                timeString = formatModel15.format(date);
                return timeString;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转字符串
     *
     * @param timeStamp
     * @return "YYYY.MM.dd HH:mm
     * 2017-11-29 11:26:20
     */
    public static String getYYPHHPMM(String timeStamp) {
        try {
            if (!TextUtils.isEmpty(timeStamp)) {
                String timeString;
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = format.parse(timeStamp);
                timeString = formatModel16.format(date);
                return timeString;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 秒转化天时分秒
     */
    public static String msToDHMS(Long s) {

        if (s == 0) return "0秒";

        Integer mi = 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = s / dd;
        Long hour = (s - day * dd) / hh;
        Long minute = (s - day * dd - hour * hh) / mi;
        Long second = (s - day * dd - hour * hh - minute * mi);

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        return sb.toString();
    }

    /**
     * 秒转化 时分秒
     */
    public static String msToHMS(Long s) {

        if (s == 0) return "0秒";

        Integer mi = 60;
        Integer hh = mi * 60;

        Long hour = s / hh;
        Long minute = (s - hour * hh) / mi;
        Long second = (s - hour * hh - minute * mi);

        StringBuffer sb = new StringBuffer();

        if (hour > 0) {
            sb.append(hour + "时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
        return sb.toString();
    }

    /**
     * 毫秒 转化 分秒 用于动态生成二维码界面右上角的倒计时
     */
    public static String msToMS(Long ms) {

        if (ms == 0) return "00:00";

        Integer ss = 1000;
        Integer mi = ss * 60;

        Long minute = ms / mi;
        Long second = (ms - minute * mi) / ss;

        StringBuffer sb = new StringBuffer();

        if (minute < 10) {
            if (second < 10) {
                sb.append("0" + minute + ":" + "0" + second);
            } else {
                sb.append("0" + minute + ":" + second);
            }
        } else {
            if (second < 10) {
                sb.append(minute + ":" + "0" + second);
            } else {
                sb.append(minute + ":" + second);
            }
        }

        return sb.toString();
    }


    /**
     * 毫秒转化天时分秒毫秒
     */
    public static String[] msToDHMSMS(Long ms, String[] strings) {
        Integer ss = 1000;
        Integer mi = ss * 60;
        Integer hh = mi * 60;
        Integer dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if (day > 0) {
            sb.append(day + "天");
        }
        if (hour > 0) {
            sb.append(hour + "时");
        }
        if (minute > 0) {
            sb.append(minute + "分");
        }
        if (second > 0) {
            sb.append(second + "秒");
        }
//        if (milliSecond > 0) {
//            sb.append(milliSecond + "毫秒");
//        }
        strings[0] = sb.toString();
        strings[1] = String.valueOf(second);
        return strings;
    }


    /**
     * 结束时间是否大于开始时间,假设为相同你月日
     *
     * @param startTime 开始时间
     * @param hour      minute 结束时间
     * @return
     */
    public static boolean isafterTime(String startTime, int hour, int minute) {
        if (TextUtils.isEmpty(startTime)) {
            return false;
        }
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();

        String[] startTimeStrList = startTime.split(":");
        if (startTimeStrList != null && startTimeStrList.length > 1) {
            String hourStr = startTimeStrList[0];
            String minuteStr = startTimeStrList[1];
            startCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hourStr));
            startCalendar.set(Calendar.MINUTE, Integer.parseInt(minuteStr));
        }
        endCalendar.set(Calendar.HOUR_OF_DAY, hour);
        endCalendar.set(Calendar.MINUTE, minute);
        return startCalendar.after(endCalendar);
    }


    /**
     * 时间就近取整:<=30分向前取整,>30分向后取整
     * Author:zr
     *
     * @param time inTime 07:56
     * @return outTime 08:00
     */

    public static void initialTime(Calendar calendar) {

        String hour = "00";//小时
        String minutes = "00";//分钟


        String hhmm = getHHMM(calendar.getTime());
        StringTokenizer st = new StringTokenizer(hhmm, ":");

        List<String> inTime = new ArrayList<String>();

        while (st.hasMoreElements()) {
            inTime.add(st.nextToken());
        }

        hour = inTime.get(0).toString();
        minutes = inTime.get(1).toString();

        if (Integer.parseInt(minutes) > 30) {
            hour = (Integer.parseInt(hour) + 1) + "";
            minutes = "00";
        } else {
            minutes = "30";
        }

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Integer.valueOf(hour), Integer.valueOf(minutes));

    }


    /**
     * 获取分钟数
     */

    public static String getMinutes(Calendar calendar) {

        String minutes = "00";//分钟

        String hhmm = getHHMM(calendar.getTime());

        StringTokenizer st = new StringTokenizer(hhmm, ":");

        List<String> inTime = new ArrayList<String>();

        while (st.hasMoreElements()) {
            inTime.add(st.nextToken());
        }
        minutes = inTime.get(1);
        return minutes;

    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 != null && cal2 != null) {
            return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }
}
