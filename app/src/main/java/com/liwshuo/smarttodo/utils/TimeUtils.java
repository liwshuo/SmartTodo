package com.liwshuo.smarttodo.utils;

import java.util.Calendar;

/**
 * Created by shuo on 2015/4/10.
 */
public class TimeUtils {

    /**
     * 获取当前时间，格式为 year-month-day hour:minute:second
     * @return current time String format: year-month-day hour:minute:second
     */
    public String getCurrentDateAndTime() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        StringBuilder dateTime = new StringBuilder();
        dateTime.append(year);
        dateTime.append("-");
        dateTime.append(month > 0 && month < 10 ? "0" + month : month);
        dateTime.append("-");
        dateTime.append(day > 0 && day < 10 ? "0" + day : day);
        dateTime.append(" ");
        dateTime.append(hour > 0 && hour < 10 ? "0"+hour : hour);
        dateTime.append(":");
        dateTime.append(minute > 0 && minute < 10 ? "0"+minute : minute);
        dateTime.append(":");
        dateTime.append(second > 0 && second < 10 ? "0"+second : second);
        return dateTime.toString();
    }

    public String getCurrentDateAndTimeWithoutSecond() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        StringBuilder dateTime = new StringBuilder();
        dateTime.append(year);
        dateTime.append("-");
        dateTime.append(month > 0 && month < 10 ? "0" + month : month);
        dateTime.append("-");
        dateTime.append(day > 0 && day < 10 ? "0" + day : day);
        dateTime.append(" ");
        dateTime.append(hour > 0 && hour < 10 ? "0"+hour : hour);
        dateTime.append(":");
        dateTime.append(minute > 0 && minute < 10 ? "0"+minute : minute);
        return dateTime.toString();
    }

    public String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        StringBuilder date = new StringBuilder();
        date.append(year);
        date.append("-");
        date.append(month > 0 && month < 10 ? "0" + month : month);
        date.append("-");
        date.append(day > 0 && day < 10 ? "0" + day : day);
        return date.toString();
    }

    public String getCurrentTimeWithSecond() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        StringBuilder time = new StringBuilder();
        time.append(hour > 0 && hour < 10 ? "0"+hour : hour);
        time.append(":");
        time.append(minute > 0 && minute < 10 ? "0"+minute : minute);
        time.append(":");
        time.append(second > 0 && second < 10 ? "0"+second : second);
        return time.toString();
    }
    public String getCurrentTimeWithoutSecond() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        StringBuilder time = new StringBuilder();
        time.append(hour > 0 && hour < 10 ? "0"+hour : hour);
        time.append(":");
        time.append(minute > 0 && minute < 10 ? "0"+minute : minute);
        return time.toString();
    }



    public int getTimeStamp() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return second + minute * 10 + hour * 100 + day * 1000 + month * 10000 + year * 100000;
    }
}
