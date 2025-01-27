package com.nqlo.ch.mkt.service.dto;


@Data
public class TimeResponseDTO {

    private int year;
    private int month;
    private int day;
    
    private int hour;
    private int minute;
    private int second;
    private int milisecond;
    private String dateTime;
    private String date;
    private String time;
    private String timeZone;
    private String dayOfWeek;;
    private boolean dstAtive;
    public int getYear() {
        return year;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public int getMonth() {
        return month;
    }
    public void setMonth(int month) {
        this.month = month;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
    public int getHour() {
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }
    public int getMinute() {
        return minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }
    public int getSecond() {
        return second;
    }
    public void setSecond(int second) {
        this.second = second;
    }
    public int getMilisecond() {
        return milisecond;
    }
    public void setMilisecond(int milisecond) {
        this.milisecond = milisecond;
    }
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public boolean isDstAtive() {
        return dstAtive;
    }
    public void setDstAtive(boolean dstAtive) {
        this.dstAtive = dstAtive;
    }
}
