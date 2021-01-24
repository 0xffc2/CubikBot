package top.cubik65536.cubikbot.logic.impl;

import top.cubik65536.cubikbot.logic.TimeLogic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeLogicImpl implements TimeLogic {
    //判断是否在规定的时间内签到 nowTime 当前时间 beginTime规定开始时间 endTime规定结束时间
    private boolean timeCalendar(Date nowTime, Date beginTime, Date endTime) {
        //设置当前时间
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        //设置开始时间
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);//开始时间
        //设置结束时间
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);//结束时间
        //处于开始时间之后，和结束时间之前的判断
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isInTime(String beginTimeString, String endTimeString) {
        if (beginTimeString.equals(endTimeString)) return true;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");//设定时区为中国时区
        df.setTimeZone(timeZone);
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(beginTimeString);
            endTime = df.parse(endTimeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeCalendar(now, beginTime, endTime);
//        return true;
    }

    @Override
    public boolean isInTimeSec(String beginTimeString, String endTimeString) {
        if (beginTimeString.equals(endTimeString)) return true;
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//设置日期格式
        TimeZone timeZone = TimeZone.getTimeZone("GMT+8:00");//设定时区为中国时区
        df.setTimeZone(timeZone);
        Date now = null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(beginTimeString);
            endTime = df.parse(endTimeString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeCalendar(now, beginTime, endTime);
//        return true;
    }

}
