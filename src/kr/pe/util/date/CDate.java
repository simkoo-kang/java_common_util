/**
 * 
 */
package kr.pe.util.date;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 */
public class CDate {

	private Calendar cal = Calendar.getInstance();

	/**
	 * 생성자
	 */
	public CDate() {
		set(new Date());
	}

	/**
	 * 생성자
	 * 
	 * @param date
	 */
	public CDate(Date date) {
		set(date);
	}

	/**
	 * 생성자
	 * 
	 * @param date
	 */
	public CDate(long date) {
		set(date);
	}

	/**
	 * 생성자
	 * 
	 * @param year
	 * @param month
	 * @param day
	 */
	public CDate(int year, int month, int day) {
		set(year, month, day);
	}

	/**
	 * 생성자
	 * 
	 * @param date
	 */
	public CDate(String date) {
		set(date);
	}

	public CDate firstDate() {
		return new CDate(getYear(), getMonth(), 1);
	}

	public CDate lastDate() {
		CDate cd = new CDate(DateUtil.addMonth(getDate(), 1));
		return cd.firstDate().addDay(-1);
	}

	public CDate addDay(int d) {
		setDay(getDay() + d);
		return this;
	}

	public CDate addMonth(int m) {
		setMonth(getMonth() + m);
		return this;
	}

	public CDate addYear(int y) {
		setYear(getYear() + y);
		return this;
	}

	public CDate addMillis(long millis) {
		set(getTimemillis() + millis);
		return this;
	}

	/**
	 * @param date
	 */
	public CDate set(Date date) {
		this.cal.setTime(date);
		return this;
	}

	/**
	 * @param date
	 */
	public CDate set(long date) {
		this.cal.setTime(new Date(date));
		return this;
	}

	/**
	 * @param date
	 */
	public CDate set(String date) {
		this.cal.setTime(DateFmt.convertStringToDate(date));
		return this;
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 */
	public void set(int year, int month, int day) {
		set(year, month, day, 0, 0, 0);
	}

	/**
	 * @param yy
	 * @param mm
	 * @param dd
	 * @param h
	 * @param m
	 * @param s
	 */
	public CDate set(int yy, int mm, int dd, int h, int m, int s) {
		this.cal.set(yy, mm - 1, dd, h, m, s);
		this.cal.set(Calendar.MILLISECOND, 0);
		return this;
	}

	/**
	 * date 보다 이전이면 true
	 * 
	 * @param date
	 * @return the boolean
	 */
	public boolean before(CDate date) {
		return this.cal.before(date.getCalendar());
	}

	/**
	 * date 보다 이후이면 true
	 * 
	 * @param date
	 * @return the boolean
	 */
	public boolean after(CDate date) {
		return this.cal.after(date.getCalendar());
	}

	/**
	 * @return the Calendar
	 */
	public Calendar getCalendar() {
		return this.cal;
	}

	/**
	 * @return the Date
	 */
	public Date getDate() {
		return this.cal.getTime();
	}

	/**
	 * @return timemillis
	 */
	public long getTimemillis() {
		return this.cal.getTimeInMillis();
	}

	/**
	 * @return Only time except date in milliseconds
	 */
	public long getTime() {
		CDate cdate = new CDate(getYear(), getMonth(), getDay());
		return getTimemillis() - cdate.getTimemillis();
	}

	/**
	 * @return the Date to String (DateFmt.yyyyMMddHHmmss_DASH)
	 */
	public String getDateString() {
		return getDateString(DateFmt.yyyyMMddHHmmss_DASH);
	}

	/**
	 * @return the Date to String (DateFmt.yyyyMMdd_DASH)
	 */
	public String getDateShortString() {
		return getDateString(DateFmt.yyyyMMdd_DASH);
	}

	/**
	 * @param pattern
	 * @return the string
	 */
	public String getDateString(String pattern) {
		return DateFmt.convertDateToString(getDate(), pattern);
	}

	@Override
	public String toString() {
		return getDateString();
	}

	/**
	 * @param year
	 */
	public CDate setYear(int year) {
		this.cal.set(Calendar.YEAR, year);
		return this;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return this.cal.get(Calendar.YEAR);
	}

	/**
	 * @param month
	 */
	public CDate setMonth(int month) {
		this.cal.set(Calendar.MONTH, month - 1);
		return this;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return this.cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * @param day
	 */
	public CDate setDay(int day) {
		this.cal.set(Calendar.DAY_OF_MONTH, day);
		return this;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return this.cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @param hour
	 */
	public CDate setHour(int hour) {
		this.cal.set(Calendar.HOUR_OF_DAY, hour);
		return this;
	}

	/**
	 * @return the hour
	 */
	public int getHour() {
		return this.cal.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * @param minute
	 */
	public CDate setMinute(int minute) {
		this.cal.set(Calendar.MINUTE, minute);
		return this;
	}

	/**
	 * @return the minute
	 */
	public int getMinute() {
		return this.cal.get(Calendar.MINUTE);
	}

	/**
	 * @param second
	 */
	public CDate setSecond(int second) {
		this.cal.set(Calendar.SECOND, second);
		return this;
	}

	/**
	 * @return the second
	 */
	public int getSecond() {
		return this.cal.get(Calendar.SECOND);
	}

	/**
	 * 요일 (1:일, 2:월, 3:화, ...)
	 * 
	 * @return the week
	 */
	public int getWeek() {
		return this.cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CDate date = new CDate();
		date.addDay(7);
		date.addMonth(-5);
		date.addYear(1);
		System.out.println(date.getDateShortString());

		CDate cdate = new CDate();
		CDate tdate = new CDate("2025-04-15");
		System.out.println(cdate.getDateString());
		System.out.println(tdate.getDateString());
		long millis = cdate.getTimemillis() - tdate.getTimemillis();// - 9 * 60 * 60 * 1000;
		System.out.println(millis);
		CDate ndate = new CDate(millis);
		System.out.println(ndate.getDateString());
		tdate.addMillis(millis);
		System.out.println(tdate.getDateString());
		tdate = new CDate(0, 0, 0);
		System.out.println(tdate.getDateString());
	}
}
