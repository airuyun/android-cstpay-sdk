package com.cst.cstpaysdk.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author TJS
 * @date 2019/08/27 10:41
 * @cst_do 时间日期格式化相关类
 * 影响范围：时间日期格式化
 * 备注：
 * @cst_end
 */
@SuppressLint("SimpleDateFormat")
object DateUtils {
    private const val CALENDAR_DATE_TIME_PATTERN = "yyyyMMdd"
    private const val EXACT_DATE_TIME_PATTERN = "yyyyMMddHHmmssSSS"
    private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    private const val DATE_TIME_PATTERN2 = "yyyyMMddHHmmss"
    private const val MINUTE_PATTERN = "yyyy-MM-dd HH:mm"
    private const val HOUR_PATTERN = "yyyy-MM-dd HH"
    private const val DATE_PATTERN = "yyyy-MM-dd"
    private const val MONTH_PATTERN = "yyyy-MM"
    private const val YEAR_PATTERN = "yyyy"
    private const val TIME_PATTERN = "HH:mm:ss"
    private const val MINUTE_TIME_PATTERN = "HH:mm"
    private const val TIME_ZONE_DEF = "GMT+8:00"

    /**
     * 获取当前日期的格式化字符串
     */
    fun getCurrentFormatTime(pattern: String): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat(pattern, Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE_DEF)
        return dateFormat.format(date)
    }

    /**
     * 获取当前日期 yyyy-MM-dd
     */
    fun getCurrentDate(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat(DATE_PATTERN)
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE_DEF)
        return dateFormat.format(date)
    }

    fun getCurrentDateAndWeek(): String {
        val currentTimeFormat = SimpleDateFormat("yyyy - MM - dd \tHH:mm")
        val sb = StringBuilder()
        sb.append(currentTimeFormat.format(Date()))
            .append("\t\t")
            .append("星期").append(getCurrentWeek())
        return sb.toString()
    }

    /**
     * 获取当前星期，例如：星期一
     */
    fun getCurrentWeek(): String {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZONE_DEF))
        var mWay = calendar.get(Calendar.DAY_OF_WEEK).toString()
        when (mWay) {
            "1" -> mWay = "天"
            "2" -> mWay = "一"
            "3" -> mWay = "二"
            "4" -> mWay = "三"
            "5" -> mWay = "四"
            "6" -> mWay = "五"
            "7" -> mWay = "六"
        }
        return "星期$mWay"
    }

    /**
     * 获取当前精确到秒钟的时间 HH:mm:ss
     */
    fun getCurrentTime(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat(TIME_PATTERN)
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE_DEF)
        return dateFormat.format(date)
    }

    /**
     * 获取当前精确到分钟的时间 HH:mm
     */
    fun getCurrentMinuteTime(): String {
        val currentTimeFormat = SimpleDateFormat(MINUTE_TIME_PATTERN)
        return currentTimeFormat.format(Date())
    }

    /**
     * 获取14位时间 yyyyMMddHHmmss
     */
    fun getCurrentTime8(time: Long): String {
        val date = Date(time)
        val dateFormat = SimpleDateFormat(CALENDAR_DATE_TIME_PATTERN)
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE_DEF)
        return dateFormat.format(date)
    }

    /**
     * 获取14位时间 yyyyMMddHHmmss
     */
    fun getCurrentTime14(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat(DATE_TIME_PATTERN2)
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE_DEF)
        return dateFormat.format(date)
    }

    /**
     * 获取当前精确到毫秒的日期 yyyyMMddHHmmssSSS
     */
    fun getCurrentTime17(): String {
        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat(EXACT_DATE_TIME_PATTERN)
        dateFormat.timeZone = TimeZone.getTimeZone(TIME_ZONE_DEF)
        return dateFormat.format(date)
    }
}