/**
 * 
 */
package kr.pe.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kr.pe.util.Str;
import kr.pe.util.date.fmt.DateFormat;
import kr.pe.util.date.fmt.Format04;
import kr.pe.util.date.fmt.Format05;
import kr.pe.util.date.fmt.Format06;
import kr.pe.util.date.fmt.Format08;
import kr.pe.util.date.fmt.Format09;
import kr.pe.util.date.fmt.Format10;
import kr.pe.util.date.fmt.Format11;
import kr.pe.util.date.fmt.Format12;
import kr.pe.util.date.fmt.Format14;
import kr.pe.util.date.fmt.Format16;
import kr.pe.util.date.fmt.Format17;
import kr.pe.util.date.fmt.Format19;
import kr.pe.util.date.fmt.Format23;

/**
 * G Era designator Text AD y Year Year 1996; 96 M Month in year Month July;
 * Jul; 07 w Week in year Number 27 W Week in month Number 2 D Day in year
 * Number 189 d Day in month Number 10 F Day of week in month Number 2 E Day in
 * week Text Tuesday; Tue a Am/pm marker Text PM H Hour in day (0-23) Number 0 k
 * Hour in day (1-24) Number 24 K Hour in am/pm (0-11) Number 0 h Hour in am/pm
 * (1-12) Number 12 m Minute in hour Number 30 s Second in minute Number 55 S
 * Millisecond Number 978 z Time zone General time zone Pacific Standard Time;
 * PST; GMT-08:00 Z Time zone RFC 822 time zone -0800
 * 
 * @author bbrain
 */
public final class DateFmt {

	/** 하루: 24 * 60 * 60; */
	public final static long ONE_DAY = 86400;
	/** 하루: 24 * 60 * 60 * 1000; */
	public final static long ONE_DAY_MILLI = ONE_DAY * 1000;

	/** TIME_NONE = "HHmm"; 4 */
	public final static String HHmm_NONE = "HHmm";

	/** TIME_NONE = "HH:mm"; 5 */
	public final static String HHmm_NORM = "HH:mm";

	/** TIME_NONE = "HHmmss"; 6 */
	public final static String HHmmss_NONE = "HHmmss";

	/** YEAR-MON = "yyyy-MM"; 7 */
	public final static String yyyyMM_NORM = "yyyy-MM";
	/** YEAR.MON = "yyyy.MM"; 7 */
	public final static String yyyyMM_DOT = "yyyy.MM";
	/** YEAR/MON = "yyyy/MM"; 7 */
	public final static String yyyyMM_SLASH = "yyyy/MM";

	/** SHORT_NONE = "yyyyMMdd"; 8 */
	public final static String yyyyMMdd_NONE = "yyyyMMdd";
	/** TIME_NORMAL = "HH:mm:ss"; 8 */
	public final static String HHmmss_NORM = "HH:mm:ss";

	/** TIME_NONE = "HHmmssSSS"; 9 */
	public final static String HHmmssSSS_NONE = "HHmmssSSS";

	/** SHORT_DASH = "yyyy-MM-dd"; 10 */
	public final static String yyyyMMdd_DASH = "yyyy-MM-dd";
	/** SHORT_DOT = "yyyy.MM.dd"; 10 */
	public final static String yyyyMMdd_DOT = "yyyy.MM.dd";
	/** SHORT_SLASH = "yyyy/MM/dd"; 10 */
	public final static String yyyyMMdd_SLASH = "yyyy/MM/dd";

	/** TIME_NORMAL = "dd HH:mm:ss"; 11 */
	public final static String ddHHmmss_NORM = "dd HH:mm:ss";

	/** TIME_NORMAL = "HH:mm:ss.SSS"; 12 */
	public final static String HHmmssSSS_NORM = "HH:mm:ss.SSS";
	/** LONG_NONE = "yyyyMMddHHmm"; 12 */
	public final static String yyyyMMddHHmm_NONE = "yyyyMMddHHmm";

	/** LONG_NONE = "yyyyMMddHHmmss"; 14 */
	public final static String yyyyMMddHHmmss_NONE = "yyyyMMddHHmmss";

	/** LONG_SLASH = "yyyy/MM/dd HH:mm"; 16 */
	public final static String yyyyMMddHHmm_SLASH = "yyyy/MM/dd HH:mm";
	/** LONG_DASH = "yyyy-MM-dd HH:mm"; 16 */
	public final static String yyyyMMddHHmm_DASH = "yyyy-MM-dd HH:mm";
	/** LONG_DOT = "yyyy.MM.dd HH:mm"; 16 */
	public final static String yyyyMMddHHmm_DOT = "yyyy.MM.dd HH:mm";

	/** LONG_NONE_TIME = "yyyyMMddHHmmssSSS"; 17 */
	public final static String yyyyMMddHHmmssSSS_NONE = "yyyyMMddHHmmssSSS";

	/** LONG_SLASH = "yyyy/MM/dd HH:mm:ss"; 19 */
	public final static String yyyyMMddHHmmss_SLASH = "yyyy/MM/dd HH:mm:ss";
	/** LONG_DASH = "yyyy-MM-dd HH:mm:ss"; 19 */
	public final static String yyyyMMddHHmmss_DASH = "yyyy-MM-dd HH:mm:ss";
	/** LONG_DOT = "yyyy.MM.dd HH:mm:ss"; 19 */
	public final static String yyyyMMddHHmmss_DOT = "yyyy.MM.dd HH:mm:ss";

	/** LONG_DASH_TIME = "yyyy-MM-dd HH:mm:ss.SSS"; 23 */
	public final static String yyyyMMddHHmmssSSS_DASH = "yyyy-MM-dd HH:mm:ss.SSS";
	/** LONG_DASH_TIME = "yyyy-MM-dd HH:mm:ss.SSS"; 23 */
	public final static String yyyyMMddHHmmssSSS_DOT = "yyyy.MM.dd HH:mm:ss.SSS";
	/** LONG_DASH_TIME = "yyyy-MM-dd HH:mm:ss.SSS"; 23 */
	public final static String yyyyMMddHHmmssSSS_SLASH = "yyyy/MM/dd HH:mm:ss.SSS";

	/**
	 * 패턴을 탐색하여 일치하는 패턴 형식의 날짜로 변환한다.
	 * 
	 * @param source
	 * @return the string
	 */
	public static String getPattern(String source) {
		if (!Str.isEmpty(source)) {
			String dateString = Str.trim(source);
			int length = dateString.length();
			DateFormat fmt;
			switch (length) {
			case 23:
				fmt = new Format23();
				break;
			case 19:
				fmt = new Format19();
				break;
			case 17:
				fmt = new Format17();
				break;
			case 16:
				fmt = new Format16();
				break;
			case 14:
				fmt = new Format14();
				break;
			case 12:
				fmt = new Format12();
				break;
			case 11:
				fmt = new Format11();
				break;
			case 10:
				fmt = new Format10();
				break;
			case 9:
				fmt = new Format09();
				break;
			case 8:
				fmt = new Format08();
				break;
			case 6:
				fmt = new Format06();
				break;
			case 5:
				fmt = new Format05();
				break;
			case 4:
				fmt = new Format04();
				break;
			default:
				return null;
			}
			return fmt.format(dateString);
		}
		return null;
	}

	/**
	 * 패턴을 탐색하여 일치하는 패턴 형식의 날짜로 변환한다.
	 * 
	 * @param source
	 * @return the string
	 */
	public static String getPatternOld(String source) {
		if (!Str.isEmpty(source)) {
			String dateString = Str.trim(source);
			int length = dateString.length();
			switch (length) {
			case 23:
				if (convertStringToDate(dateString, yyyyMMddHHmmssSSS_DASH) != null) {
					return yyyyMMddHHmmssSSS_DASH;
				}
				break;
			case 19:
				if (convertStringToDate(dateString, yyyyMMddHHmmss_DASH) != null) {
					return yyyyMMddHHmmss_DASH;
				}
				if (convertStringToDate(dateString, yyyyMMddHHmmss_SLASH) != null) {
					return yyyyMMddHHmmss_SLASH;
				}
				if (convertStringToDate(dateString, yyyyMMddHHmmss_DOT) != null) {
					return yyyyMMddHHmmss_DOT;
				}
				break;
			case 14:
				if (convertStringToDate(dateString, yyyyMMddHHmmss_NONE) != null) {
					return yyyyMMddHHmmss_NONE;
				}
				break;
			case 12:
				if (convertStringToDate(dateString, HHmmssSSS_NORM) != null) {
					return HHmmssSSS_NORM;
				}
				break;
			case 10:
				if (convertStringToDate(dateString, yyyyMMdd_DASH) != null) {
					return yyyyMMdd_DASH;
				}
				if (convertStringToDate(dateString, yyyyMMdd_SLASH) != null) {
					return yyyyMMdd_SLASH;
				}
				if (convertStringToDate(dateString, yyyyMMdd_DOT) != null) {
					return yyyyMMdd_DOT;
				}
				break;
			case 9:
				if (convertStringToDate(dateString, HHmmssSSS_NONE) != null) {
					return HHmmssSSS_NONE;
				}
				break;
			case 8:
				if (convertStringToDate(dateString, yyyyMMdd_NONE) != null) {
					return yyyyMMdd_NONE;
				}
				if (convertStringToDate(dateString, HHmmss_NORM) != null) {
					return HHmmss_NORM;
				}
				break;
			case 6:
				if (convertStringToDate(dateString, HHmmss_NONE) != null) {
					return HHmmss_NONE;
				}
				break;
			}
		}
		return null;
	}

	/**
	 * @return the current date string - yyyyMMddHHmmss_DASH
	 */
	public static String currentDateToString() {
		return convertDateToString(new Date(), yyyyMMddHHmmss_DASH);
	}

	/**
	 * @param pattern
	 * @return the current date string
	 */
	public static String currentDateToString(String pattern) {
		return convertDateToString(new Date(), pattern);
	}

	/**
	 * 날짜를 패턴 형식의 문자열로 변환한다.
	 * 
	 * @param date
	 * @param pattern
	 * @return the string
	 */
	public static String convertDateToString(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		return simpleDateFormat.format(date);
	}

	/**
	 * 패턴을 탐색하여 일치하는 형식의 날짜로 변환.
	 * 
	 * @param date
	 * @return the string
	 */
	public static Date convertStringToDate(String date) {
		return convertStringToDate(date, getPattern(date));
	}

	/**
	 * 패턴 형식의 문자열을 날짜로 변환한다.
	 * 
	 * @param date
	 * @param pattern
	 * @return the string
	 */
	public static Date convertStringToDate(String date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.KOREA);
		try {
			return simpleDateFormat.parse(date);
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String date = "2025-04-17 19:02:53.549";
		System.out.println(getPattern(date));
		date = "2025.04.17 19:02:53.549";
		System.out.println(getPattern(date));
		date = "2025/04/17 19:02:53.549";
		System.out.println(getPattern(date));
	}

}
