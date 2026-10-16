/**
 * 
 */
package kr.pe.util.date;

import java.util.Calendar;
import java.util.Date;

import com.ibm.icu.util.ChineseCalendar;

import kr.pe.util.Mat;
import kr.pe.util.Str;

/**
 * 
 */
public final class DateUtil {

	private static final int LUNAR_VALUE = 2637;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "1944-06-02";
		String ldate = toLunar(date);
		System.out.println(date);
		System.out.println(ldate);

		System.out.println();
		date = "1963-03-24";
		CDate cdate = new CDate(toSolar(date));
		System.out.println(date);
		System.out.println(cdate.getDateShortString());

		System.out.println();
		date = "1968-07-06 윤";
		CDate cd = toCSolar(date);
		System.out.println(date);
		System.out.println(cd.getDateShortString());

		sumYearMonthDay("2026.05.05");
	}

	/**
	 * @param dateStr
	 * @return default 0
	 */
	public static int sumYearMonthDay(String dateStr) {
		return sumYearMonthDay(dateStr, 0);
	}

	/**
	 * @param dateStr
	 * @param defaultValue
	 * @return default 0
	 */
	public static int sumYearMonthDay(String dateStr, int defaultValue) {
		String date = dateStr.replaceAll("[-/.]", "");
		if (date.length() < 8) {
			return -1;
		}
		String ys = date.substring(0, 4);
		String ms = date.substring(4, 6);
		String ds = date.substring(6, 8);
		int total = 0;
		int iv = Mat.parseInt(ys);
		if (iv < 0) {
			return defaultValue;
		}
		total += iv;
		iv = Mat.parseInt(ms);
		if (iv < 0) {
			return defaultValue;
		}
		total += iv;
		iv = Mat.parseInt(ds);
		if (iv < 0) {
			return defaultValue;
		}
		total += iv;
		return total;
	}

	/**
	 * 음력을 양력으로
	 * 
	 * @param ldate 음력 날짜 - [1944-05-06] or [1944-06-02 윤] or [19440506] or
	 *              [19440602 윤]
	 * @return
	 */
	public static Date toSolar(String ldate) {
		String date;
		int yun;
		if (ldate.contains(" ")) {
			String[] dd = ldate.split(" ");
			date = dd[0].replace("-", "");
			yun = 1;
		} else {
			date = ldate.replace("-", "");
			yun = 0;
		}
		String ys = date.substring(0, 4);
		String ms = date.substring(4, 6);
		String ds = date.substring(6, 8);
		ChineseCalendar cal = new ChineseCalendar();
		cal.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(ys) + LUNAR_VALUE);
		cal.set(ChineseCalendar.MONTH, Integer.parseInt(ms) - 1);
		cal.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(ds));
		cal.set(ChineseCalendar.IS_LEAP_MONTH, yun);
		return new Date(cal.getTimeInMillis());
	}

	/**
	 * 음력을 양력으로
	 * 
	 * @param ldate 음력 날짜 - [1944-05-06] or [1944-06-02 윤] or [19440506] or
	 *              [19440602 윤]
	 * @return
	 */
	public static CDate toCSolar(String ldate) {
		return new CDate(toSolar(ldate));
	}

	/**
	 * 양력을 음력으로 변환
	 * 
	 * @param date string
	 * @return
	 */
	public static String toLunar(String date) {
		CDate cdate = new CDate(date);
		return toLunar(cdate);
	}

	/**
	 * 양력을 음력으로 변환
	 * 
	 * @param date java.util.Date type
	 * @return
	 */
	public static String toLunar(Date date) {
		ChineseCalendar cal = new ChineseCalendar();
		cal.setTimeInMillis(date.getTime());
		int y = cal.get(ChineseCalendar.EXTENDED_YEAR) - LUNAR_VALUE;
		int m = cal.get(ChineseCalendar.MONTH) + 1;
		int d = cal.get(ChineseCalendar.DAY_OF_MONTH);
		return Str.fill0(y, 4) + "-" + Str.fill0(m, 2) + "-" + Str.fill0(d, 2)
				+ (cal.get(ChineseCalendar.IS_LEAP_MONTH) == 0 ? "" : " 윤");
	}

	/**
	 * 양력을 음력으로 변환
	 * 
	 * @param date CDate type
	 * @return
	 */
	public static String toLunar(CDate date) {
		return toLunar(date.getDate());
	}

	/**
	 * 양력을 음력으로 변환
	 * 
	 * @param time long type
	 * @return
	 */
	public static String toLunar(long time) {
		return toLunar(new CDate(time));
	}

	/**
	 * @param year
	 * @param month
	 * @param day
	 * @return the Date
	 */
	public static Date getDate(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, month - 1, day);
		return cal.getTime();
	}

	/**
	 * 현재일을 yyyy-MM-dd HH:mm:ss 형식으로 반환
	 * 
	 * @return the now
	 */
	public static String now() {
		return DateFmt.currentDateToString();
	}

	/**
	 * 현재일을 yyyy-MM-dd 형식으로 반환.
	 * 
	 * @return the today
	 */
	public static String today() {
		return DateFmt.currentDateToString(DateFmt.yyyyMMdd_DASH);
	}

	/**
	 * @return the day
	 */
	public static int day() {
		return day(new Date());
	}

	/**
	 * @param date
	 * @return the day
	 */
	public static int day(String date) {
		return day(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the day
	 */
	public static int day(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * @return the month
	 */
	public static int month() {
		return month(new Date());
	}

	/**
	 * @param date
	 * @return the month
	 */
	public static int month(String date) {
		return month(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the month
	 */
	public static int month(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH) + 1;
	}

	/**
	 * @return the year
	 */
	public static int year() {
		return year(new Date());
	}

	/**
	 * @param date
	 * @return the year
	 */
	public static int year(String date) {
		return year(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the year
	 */
	public static int year(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}

	/**
	 * @return the week of today 1,2,3,4,5,6,7 [일, 월, 화, 수, 목, 금, 토]
	 */
	public static int week() {
		return week(new Date());
	}

	/**
	 * @param date
	 * @return the week 1,2,3,4,5,6,7 [일, 월, 화, 수, 목, 금, 토]
	 */
	public static int week(String date) {
		return week(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the week 1,2,3,4,5,6,7 [일, 월, 화, 수, 목, 금, 토]
	 */
	public static int week(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Week [W] in month Number (2)
	 * 
	 * @param date
	 * @return the week in month
	 */
	public static int weekInMonth(Date date) {
		String weekNumber = DateFmt.convertDateToString(date, "W");
		return (weekNumber == null) ? -1 : Integer.valueOf(weekNumber);
	}

	/**
	 * @param amount
	 * @return the date
	 */
	public static Date addDay(int amount) {
		return addDate(new Date(), amount, "d");
	}

	/**
	 * @param date
	 * @param amount
	 * @return the date
	 */
	public static Date addDay(Date date, int amount) {
		return addDate(date, amount, "d");
	}

	/**
	 * @param date
	 * @param amount
	 * @return the date
	 */
	public static Date addDay(String date, int amount) {
		return addDate(DateFmt.convertStringToDate(date), amount, "d");
	}

	/**
	 * @param amount
	 * @return the date
	 */
	public static Date addMonth(int amount) {
		return addDate(new Date(), amount, "m");
	}

	/**
	 * @param date
	 * @param amount
	 * @return the date
	 */
	public static Date addMonth(Date date, int amount) {
		return addDate(date, amount, "m");
	}

	/**
	 * @param date
	 * @param amount
	 * @return the date
	 */
	public static Date addMonth(String date, int amount) {
		return addDate(DateFmt.convertStringToDate(date), amount, "m");
	}

	/**
	 * @param amount
	 * @return the date
	 */
	public static Date addYear(int amount) {
		return addDate(new Date(), amount, "y");
	}

	/**
	 * @param date
	 * @param amount
	 * @return the date
	 */
	public static Date addYear(Date date, int amount) {
		return addDate(date, amount, "y");
	}

	/**
	 * @param date
	 * @param amount
	 * @return the date
	 */
	public static Date addYear(String date, int amount) {
		return addDate(DateFmt.convertStringToDate(date), amount, "y");
	}

	/**
	 * @param amount
	 * @param type
	 * @return the date
	 */
	public static Date addDate(int amount, String type) {
		return addDate(new Date(), amount, type);
	}

	/**
	 * @param date
	 * @param amount
	 * @param type   [y/m/d]
	 * @return the date
	 */
	public static Date addDate(String date, int amount, String type) {
		return addDate(DateFmt.convertStringToDate(date), amount, type);
	}

	/**
	 * @param date
	 * @param amount
	 * @param type   [y/m/d]
	 * @return the date
	 */
	public static Date addDate(Date date, int amount, String type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if ("d".equalsIgnoreCase(type)) {
			cal.add(Calendar.DAY_OF_MONTH, amount);
		} else if ("m".equalsIgnoreCase(type)) {
			cal.add(Calendar.MONTH, amount);
		} else if ("y".equalsIgnoreCase(type)) {
			cal.add(Calendar.YEAR, amount);
		}
		return cal.getTime();
	}

	/**
	 * @param year
	 * @return the leap of the year
	 */
	public static int leap(int year) {
		return ((year % 4) > 0) ? 0 : ((year % 100) > 0) ? 1 : ((year % 400) > 0) ? 0 : 1;
//		if ((yy %   4) > 0) return 0;
//		if ((yy % 100) > 0) return 1;
//		if ((yy % 400) > 0) return 0;
//		return 1;
	}

	public static boolean isLeap(int year) {
		return (leap(year) == 0) ? false : true;
	}

	/**
	 * @return the last date of the previous month
	 */
	public static Date lastdateOfPrevious() {
		return lastdateOfPrevious(new Date());
	}

	/**
	 * @param date
	 * @return the last date of the previous month
	 */
	public static Date lastdateOfPrevious(String date) {
		return lastdateOfPrevious(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the last date of the previous month
	 */
	public static Date lastdateOfPrevious(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		return cal.getTime();
	}

	/**
	 * @return the last date of the month
	 */
	public static Date lastdate() {
		return lastdate(new Date());
	}

	/**
	 * @param date
	 * @return the last date of the month
	 */
	public static Date lastdate(String date) {
		return lastdate(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the last date of the month
	 */
	public static Date lastdate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int lastday = lastday(year, month);
		if (day == lastday) {
			return date;
		}
		cal.set(Calendar.DAY_OF_MONTH, lastday);
		return cal.getTime();
	}

	/**
	 * @return the week begin
	 */
	public static Date weekbegin() {
		return weekbegin(new Date());
	}

	/**
	 * @param date
	 * @return the week begin (일요일)
	 */
	public static Date weekbegin(String date) {
		return weekbegin(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the week begin (일요일)
	 */
	public static Date weekbegin(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if (week == 1) {
			return date;
		}
		int another = week - 1;
		return addDay(date, -another);
	}

	/**
	 * @return the weekend
	 */
	public static Date weekend() {
		return weekend(new Date());
	}

	/**
	 * @param date
	 * @return the date
	 */
	public static Date weekend(String date) {
		return weekend(DateFmt.convertStringToDate(date));
	}

	/**
	 * 해당 주의 마지막 날, 즉 주말(토요일)
	 * 
	 * @param date
	 * @return the date
	 */
	public static Date weekend(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		if (week == 7) {
			return date;
		}
		int another = 7 - week;
		return addDay(date, another);
	}

	/**
	 * @return the last day of this month
	 */
	public static int lastday() {
		return lastday(new Date());
	}

	/**
	 * @param date
	 * @return the last day of the month
	 */
	public static int lastday(String date) {
		return lastday(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the last day of the month
	 */
	public static int lastday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		return lastday(year, month);
	}

	/**
	 * @param year
	 * @param month
	 * @return the last day of the month
	 */
	public static int lastday(int year, int month) {
		int day;
		switch (month) {
//		case 1: case 3: case 5: case 7: case 8: case 10: case 12:
//			day = 31;
//			break;
		case 4:
		case 6:
		case 9:
		case 11:
			day = 30;
			break;
		case 2:
			day = 28 + leap(year);
			break;
		default:
			day = 31;
			break;
		}
		return day;
	}

	/**
	 * @param date
	 * @return the sql date
	 */
	public static java.sql.Date convertDateToSqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * @param sqlDate
	 * @return the Date
	 */
	public static Date convertSqlDateToDate(java.sql.Date sqlDate) {
		return new Date(sqlDate.getTime());
	}

	/**
	 * @param datea
	 * @param dateb
	 * @return the integer [-1, 0, 1]
	 */
	public static int compareDate(String datea, String dateb) {
		return compareDate(DateFmt.convertStringToDate(datea), DateFmt.convertStringToDate(dateb));
	}

	/**
	 * @param datea
	 * @param dateb
	 * @return the integer [-1, 0, 1]
	 */
	public static int compareDate(Date datea, Date dateb) {
		return (datea == null) ? ((dateb == null) ? 0 : -1) : ((dateb == null) ? 1 : datea.compareTo(dateb));
//		int resultInt;
//		if(datea==null){
//			resultInt = (dateb==null)? 0: -1;
//		}else{
//			resultInt = (dateb==null)? 1: datea.compareTo(dateb);
//		}
//		return resultInt;
	}

	/**
	 * 두 날짜 사이의 날 수
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return the days - floor
	 */
	public static int betweenDays(String fromDate, String toDate) {
		return betweenDays(DateFmt.convertStringToDate(fromDate), DateFmt.convertStringToDate(toDate), true);
	}

	/**
	 * 두 날짜 사이의 차이.(날 수)
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param upper    [ceil or floor]
	 * @return the days
	 */
	public static int betweenDays(String fromDate, String toDate, boolean upper) {
		return betweenDays(DateFmt.convertStringToDate(fromDate), DateFmt.convertStringToDate(toDate), upper);
	}

	/**
	 * 두 날짜 사이의 날 수
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return the between days
	 */
	public static int betweenDays(Date fromDate, Date toDate) {
		return betweenDays(fromDate, toDate, true);
	}

	/**
	 * 두 날짜 사이의 날 수
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param upper
	 * @return the between days
	 */
	public static int betweenDays(Date fromDate, Date toDate, boolean upper) {
		int resultInt;
		if (fromDate == null) {
			resultInt = -1;
		} else {
			Date tilDate = (toDate == null) ? new Date() : toDate;
			long diff = tilDate.getTime() - fromDate.getTime();
			double doublediff = diff / DateFmt.ONE_DAY_MILLI;
			resultInt = (int) ((upper) ? Math.ceil(doublediff) : Math.floor(doublediff));
		}
		return resultInt;
	}

	/**
	 * 1년 1월 1일부터 오늘(date)까지 날 수
	 * 
	 * @return the total days from ....
	 */
	public static long days() {
		return days(new Date());
	}

	/**
	 * 1년 1월 1일부터 오늘(date)까지 날 수
	 * 
	 * @param date
	 * @return the total days from ....
	 */
	public static long days(String date) {
		return days(DateFmt.convertStringToDate(date));
	}

	/**
	 * 1년 1월 1일부터 오늘(date)까지 날 수
	 * 
	 * @param date
	 * @return the total days from ....
	 */
	public static long days(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year = cal.get(Calendar.YEAR) - 1;
		long days = year * 365L + (year / 4) - (year / 100) + (year / 400);
		return days + cal.get(Calendar.DAY_OF_YEAR);
	}

	/**
	 * @return the day of year
	 */
	public static int dayOfYear() {
		return dayOfYear(new Date());
	}

	/**
	 * @param date
	 * @return the day of year
	 */
	public static int dayOfYear(String date) {
		return dayOfYear(DateFmt.convertStringToDate(date));
	}

	/**
	 * @param date
	 * @return the day of year
	 */
	public static int dayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}
}
