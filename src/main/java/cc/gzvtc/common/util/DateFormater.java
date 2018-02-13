package cc.gzvtc.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormater {
	/**
	 * 时间格式 yyyy-MM-dd
	 */
	public static final String FORMART1 = "yyyy-MM-dd";
	public static final String FORMART2 = "yyyyMMdd";
	public static final String FORMART3 = "yyyyMMddHHmmss";
	public static final String FORMART4 = "yyyy-MM-dd HH:mm:ss";
	public static final String FORMART5 = "yyyyMMddHHmmssSSS";
	public static final String FORMART6 = "yyyy年MM月dd日";
	public static final String FORMART7 = "MM月dd日 HH:mm";
	public static final String FORMART8 = "yyyy.MM.dd HH:mm:ss";
	public static final long MILLISECONDS_PER_DAY = 24L * 3600 * 1000;

	/**
	 * 获得开始年
	 *
	 * @return int
	 */
	public static int getStartYear() {
		int startyear = DateFormater.getEndYear() - 15;
		return startyear;
	}

	/**
	 * 获得结束年
	 *
	 * @return int
	 */
	public static int getEndYear() {
		Date today = new Date();
		String year = DateFormater.dateToString(today);
		year = year.substring(0, 4);
		int endyear = Integer.parseInt(year);
		return endyear;
	}

	/**
	 * 将字符串转换成为日期类型
	 *
	 * @param dateFormat
	 *            指定的格式
	 * @param date
	 *            需要转换成日期的字符串
	 * @return 转换后的日期
	 * @throws java.lang.IllegalArgumentException
	 *             参数不合法异常
	 * @throws java.text.ParseException
	 *             解析日期异常
	 */
	public static java.util.Date stringToDate(String dateFormat, String date)
			throws java.lang.IllegalArgumentException, java.text.ParseException {
		if (dateFormat == null || dateFormat.equals("")) {
			throw new java.lang.IllegalArgumentException("parameter dateFormat is not valid");
		}
		if (date == null || date.equalsIgnoreCase("")) {
			throw new java.lang.IllegalArgumentException("parameter date is not valid");
		}
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		try {
			return formater.parse(date);
		} catch (java.text.ParseException exception) {
			throw exception;
		}
	}

	/**
	 * 将日期类型转换为字符串的形式
	 *
	 * @param dateFormat
	 *            转换后的格式
	 * @param date
	 *            需要转换的日期
	 * @return 转换后的字符串
	 * @throws java.lang.IllegalArgumentException
	 */
	public static String dateToString(String dateFormat, java.util.Date date)
			throws java.lang.IllegalArgumentException {
		if (dateFormat == null || dateFormat.equals("")) {
			throw new java.lang.IllegalArgumentException("parameter dateFormat is not valid");
		}
		if (date == null) {
			throw new java.lang.IllegalArgumentException("parameter date is not valid");
		}
		SimpleDateFormat formater = new SimpleDateFormat(dateFormat);
		return formater.format(date);
	}

	/**
	 * 将日期类型转换为字符串的形式(默认类型yyyy-MM-dd)
	 *
	 * @param date
	 *            需要转换的日期
	 * @return 转换后的字符串
	 * @throws java.lang.IllegalArgumentException
	 */
	public static String dateToString(java.util.Date date) throws java.lang.IllegalArgumentException {
		String dateFormat = DateFormater.FORMART1;
		return dateToString(dateFormat, date);
	}

	/**
	 * 将字符串转换成为日期类型(默认类型yyyy-MM-dd)
	 *
	 * @param date
	 *            需要转换成日期的字符串
	 * @return 转换后的日期
	 * @throws java.lang.IllegalArgumentException
	 *             参数不合法异常
	 * @throws java.text.ParseException
	 *             解析日期异常
	 */
	public static java.util.Date stringToDate(String date)
			throws java.lang.IllegalArgumentException, java.text.ParseException {
		String dateFormat = DateFormater.FORMART1;
		return stringToDate(dateFormat, date);
	}

	/**
	 * 根据type调用相应方法
	 *
	 * @param before
	 * @param type
	 * @param date
	 * @return
	 */
	public static Date addDate(Date before, int type, int date) {
		Date da = new Date();
		switch (type) {
		case 1:// 年
			da = DateFormater.addYear(before, date);
			break;
		case 2:// 月
			da = DateFormater.addMonth(date, before);
			break;
		case 3:// 天
			da = DateFormater.getNextUpdateDate(before, date);
			break;
		}
		return da;
	}

	/**
	 * 在date基础上添加num年
	 *
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date addYear(Date date, int num) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year + num);
		return calendar.getTime();
	}

	/**
	 * 将结束时间设置为 yyyy-MM-dd 23：59：59
	 *
	 * @param endtime
	 * @return Date
	 */
	public static Date formatEndTime(String endtime) {
		Date date = null;
		if (endtime == null || "".equals(endtime.trim())) {
			return null;
		}
		endtime = endtime.trim() + " 23:59:59";
		try {
			date = DateFormater.stringToDate(DateFormater.FORMART4, endtime);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 时间增加年
	 *
	 * @param int
	 *            增加的年数
	 * @return Date 增加后的时间
	 */
	public static Date addYear(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, year);
		return calendar.getTime();
	}

	/**
	 * 当前时间增加月
	 *
	 * @param int
	 *            增加的月数
	 * @return Date 增加后的时间
	 */
	public static Date addMonth(int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 时间增加月
	 *
	 * @param int
	 *            增加的月数
	 * @return Date 增加后的时间
	 */
	public static Date addMonth(int month, Date date) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 得到传进来要增加年数后的日期
	 *
	 * @param add
	 *            单位是年
	 * @return
	 */
	public static Date getNextDate(String adds) {
		if (adds == null || "".equals(adds)) {
			return null;
		}
		int add = Integer.parseInt(adds);
		Date da = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(da);
		cal.add(Calendar.YEAR, add);
		da = cal.getTime();
		return da;
	}

	/**
	 * 得到传进来要增加天数后的日期
	 *
	 * @param add
	 *            单位是年
	 * @return
	 */
	public static Date getNextUpdateDate(Date start, int add) {
		Date da = start;
		Calendar cal = Calendar.getInstance();
		cal.setTime(da);
		cal.add(Calendar.DAY_OF_MONTH, add);
		da = cal.getTime();
		return da;
	}

	public static long convertDaysToMilliseconds(int days) {
		return days * MILLISECONDS_PER_DAY;
	}

	// 计算两个日期相差天数
	public static int dValueDate(Date beforeDate, Date endDate) {
		int dvalue = 0;
		Calendar c_before = Calendar.getInstance();
		Calendar c_end = Calendar.getInstance();
		c_before.setTime(beforeDate);
		c_end.setTime(endDate);
		while (c_end.after(c_before)) {
			dvalue++;
			beforeDate = DateFormater.getNextUpdateDate(beforeDate, 1);
			c_before.setTime(beforeDate);
		}
		return dvalue;
	}

}