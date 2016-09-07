package com.lsh.lib.android.utils.format;
/**
 * 字母 	日期或时间元素 	表示 	示例
 * G 	Era 标志符 	Text 	AD
 * y 	年 	Year 	1996; 96
 * M 	年中的月份 	Month 	July; Jul; 07
 * w 	年中的周数 	Number 	27
 * W 	月份中的周数 	Number 	2
 * D 	年中的天数 	Number 	189
 * d 	月份中的天数 	Number 	10
 * F 	月份中的星期 	Number 	2
 * E 	星期中的天数 	Text 	Tuesday; Tue
 * a 	Am/pm 标记 	Text 	PM
 * H 	一天中的小时数（0-23） 	Number 	0
 * k 	一天中的小时数（1-24） 	Number 	24
 * K 	am/pm 中的小时数（0-11） 	Number 	0
 * h 	am/pm 中的小时数（1-12） 	Number 	12
 * m 	小时中的分钟数 	Number 	30
 * s 	分钟中的秒数 	Number 	55
 * S 	毫秒数 	Number 	978
 * z 	时区 	General time zone 	Pacific Standard Time; PST; GMT-08:00
 * Z 	时区 	RFC 822 time zone 	-0800
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 * <p/>
 * 日期和时间模式 	结果
 * "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 * "h:mm a" 	12:08 PM
 * "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z" 	0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ" 	010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 */
/**
 * 日期和时间模式 	结果
 "yyyy.MM.dd G 'at' HH:mm:ss z" 	2001.07.04 AD at 12:08:56 PDT
 "EEE, MMM d, ''yy" 	Wed, Jul 4, '01
 "h:mm a" 	12:08 PM
 "hh 'o''clock' a, zzzz" 	12 o'clock PM, Pacific Daylight Time
 "K:mm a, z" 	0:08 PM, PDT
 "yyyyy.MMMMM.dd GGG hh:mm aaa" 	02001.July.04 AD 12:08 PM
 "EEE, d MMM yyyy HH:mm:ss Z" 	Wed, 4 Jul 2001 12:08:56 -0700
 "yyMMddHHmmssZ" 	010704120856-0700
 "yyyy-MM-dd'T'HH:mm:ss.SSSZ" 	2001-07-04T12:08:56.235-0700
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日起工具类
 * Author:liush
 * Date:2016/8/24
 */
public class DateUtils {
    static String pattern = "yyyy-MM-dd HH:mm:ss";
    static String pattern1 = "yyyy-MM-dd";
    public static String getCurrentTime() {
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        return new SimpleDateFormat(pattern).format(c.getTime());
    }

    public static String format(Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }
    public static String formatYMD(Date date) {
        return new SimpleDateFormat(pattern1).format(date);
    }
    // 使用系统当前日期加以调整作为照片的名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }
}
