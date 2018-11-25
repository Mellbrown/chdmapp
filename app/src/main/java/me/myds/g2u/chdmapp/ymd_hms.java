package me.myds.g2u.chdmapp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ymd_hms{
    static SimpleDateFormat ptnDate = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat ptnTime = new SimpleDateFormat("HH:mm");
    static SimpleDateFormat ptnDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public int year;
    public int month;
    public int day;
    public int hour;
    public int min;

    public ymd_hms(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public ymd_hms(int hour, int min){
        this.hour = hour;
        this.min = min;
    }

    public static ymd_hms str2ymd(String strYMD){
        Date date = new Date();
        try {date = ptnDate.parse(strYMD); }
        catch (ParseException e) { e.printStackTrace(); }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return new ymd_hms(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1, cal.get(Calendar.DAY_OF_MONTH));
    }

    public static ymd_hms str2hms(String strHMS){
        Date date = new Date();
        try{date=ptnTime.parse(strHMS);}
        catch (ParseException e) { e.printStackTrace(); }
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        return new ymd_hms(cal.get(Calendar.HOUR_OF_DAY),cal.get(Calendar.MINUTE));
    }

    public static ymd_hms str2ymd_hms(String strYMD, String strHMS){
        ymd_hms ymd = str2ymd(strYMD);
        ymd_hms hms = str2hms(strHMS);
        ymd.hour = hms.hour;
        ymd.min = hms.min;
        return  ymd;
    }

    public Date toDate(){
        Calendar cal = new GregorianCalendar();
        cal.set(year,month-1,day,hour,min,0);
        return cal.getTime();
    }
}