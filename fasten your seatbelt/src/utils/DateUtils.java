package utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import utils.Utils;

/**
 * Class for date handling.
 *
  */
public final class DateUtils {

	private static final String DEFAULT_YEAR = "1900";
	
	public static final int MILLIS_PER_SECOND = 1000;
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int HOURS_PER_DAY = 24;
	public static final int DAYS_PER_WEEK = 7;
	public static final int DAYS_PER_YEAR = 365;
	public static final int WEEKS_PER_YEAR = 52;
	public static final int MONTHS_PER_YEAR = 12;

	public static final int MILLIS_PER_MINUTE =
								MILLIS_PER_SECOND * SECONDS_PER_MINUTE;
	public static final int MILLIS_PER_HOUR =
								MILLIS_PER_MINUTE * MINUTES_PER_HOUR;
	public static final int MILLIS_PER_DAY =
								MILLIS_PER_HOUR * HOURS_PER_DAY;

	public static final int SECONDS_PER_HOUR =
								SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
	public static final int SECONDS_PER_DAY =
								SECONDS_PER_HOUR * HOURS_PER_DAY;
	public static final int SECONDS_PER_WEEK =
	    						SECONDS_PER_DAY * DAYS_PER_WEEK;
	public static final int SECONDS_PER_YEAR =
	    						SECONDS_PER_DAY * DAYS_PER_YEAR;

	public static final int MINUTES_PER_DAY =
								MINUTES_PER_HOUR * HOURS_PER_DAY;

	/**
	 * "HH:mm:ss"
	 */
	public static final String HMS = "HH:mm:ss";

	/**
	 * "HH:mm"
	 */
	public static final String HM = "HH:mm";

	/**
	 * "yyyy-MM-dd"
	 */
	public static final String YMD = "yyyy-MM-dd";

	/** Haal de 0 ervoor weg */
	public static final String YMD_NO_ZEROS = "yyyy-M-d";

	/**
	 * "MM-dd-yyyy"
	 */
	public static final String MDY = "MM-dd-yyyy";

	/**
	 * "dd-MM-yy", vb.: "MM-dd-yyyy" maakt 4-1-0074 van 4-1-74 en
	 * deze maakt 4-1-1974.
	 */
	public static final String DMYY = "dd-MM-yy";

	/**
	 * "dd-MM-yy", vb.: "MM-dd-yyyy" maakt 4-1-0074 van 4-1-74 en
	 * deze maakt 4-1-1974.
	 */
	public static final String DMYY_YEAR_SEPARATE = "dd-MM yy";

	/**
	 * "ddMMyy", maakt 040174 van 4-1-1974.
	 */
	public static final String DMYY_NO_SEPARATORS = "ddMMyy";

	/**
	 * "MM/dd/yy"
	 */
	public static final String MDYY_WITH_SLASHES = "MM/dd/yy";
	
	/**
	 * "dd-MM-yyyy"
	 */
	public static final String DMY = "dd-MM-yyyy";
	
	/** Haal de 0 ervoor weg */
	public static final String DMY_NO_ZEROS = "d-M-yyyy";

	/**
	 * "yyyy-MM-dd HH:mm"
	 */
	public static final String YMDHM = "yyyy-MM-dd HH:mm";

	/**
	 * "MM-dd-yyyy HH:mm"
	 */
	public static final String MDYHM = "MM-dd-yyyy HH:mm";

	/**
	 * "dd-MM-yyyy HH:mm"
	 */
	public static final String DMYHM = "dd-MM-yyyy HH:mm";

	/**
	 * "yyyy-MM-dd HH:mm:ss"
	 */
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * "MM-dd-yyyy HH:mm:ss"
	 */
	public static final String MDYHMS = "MM-dd-yyyy HH:mm:ss";

	/**
	 * "dd-MM-yyyy HH:mm:ss"
	 */
	public static final String DMYHMS = "dd-MM-yyyy HH:mm:ss";

	public static final String ISO_8601_DATE = "yyyy-MM-dd'T'HH:mm:ss";

	/**
	 * "d MMM yy"
	 */
	private static final String DUTCH_3_CHAR_MONTH_TEXT = "d MMM yy";
	public static final String ZULU_TIME =
			"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	/**
	 * "d MMMMMMMMM yyyy"
	 */
	public static final String DUTCH_DATE_TEXT = "d MMMMMMMMM yyyy";
	public static final String DUTCH_DATETIME_TEXT = "d MMMMMMMMM yyyy HH:mm";
	public static final String LEGAL_DATE_TEXT = "dd MMMMMMMMM yyyy";
	public static final String DUTCH_FULL_DATE_AND_DAY_TEXT = "EEEE, d MMMMMMMMM yyyy";
	public static final String DUTCH_FULL_DATETIME_TEXT = 
			"EEEE, d MMMMMMMMM yyyy HH:mm";
	public static final String DUTCH_DATETIME_TEXT_PLUS_2CHAR_OF_DAY = 
		"EE, d MMMMMMMMM yyyy HH:mm";

	/**
	 * "yyyy-MM-dd HH:mm"
	 */
	public static final String SQL = YMDHM;

	public static final String DEFAULT_FORMAT = DMYHMS;
	//TODO: Dit naar 1 logische plek refactoren, dr staan nu overal zulke
	//		dingen die hier niets te zoeken hebben
	public static final int MAX_WEEKS_IN_YEAR = 54;
	public static final int MAX_DAYS_IN_MONTH = 31;
	public static final int MONTHS_IN_QUARTER = 3;
	public static final int MONTHS_IN_HALFYEAR = 6;
	public static final String DUTCH_SMALL_DATE_TEXT = "EEE, d MMM yyyy HH:mm";

	/**
	 * ISO 8601 date format for use with SimpleDateFormat
	 */
	public static final String DATEFORMAT_ISO8601 = "yyyy-MM-dd 'T' hh:mm:ss Z";
	
	/**
	 * RFC 2822 date format for use with SimpleDateFormat
	 * When printed, a folding whitepace is RECOMMENDED in front and after the result
	 */
	public static final String DATEFORMAT_RFC2822 = "EEE, dd MMM yyyy HH:mm:ss Z";
	
	private DateUtils() {
	}

	public static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat sdf;
		if (format == DUTCH_DATE_TEXT) {
			sdf = new SimpleDateFormat(format, new Locale("NL"));
		} else {
			sdf = new SimpleDateFormat(format);
		}
		sdf.setTimeZone(TimeZone.getDefault());
		return sdf;
	}

	/**
	 * Transform any given date to string
	 *
	 * If you want the minus sign use the constants preventing typos.
	 * Example (1): DateUtils.toString(rs.getTimestamp("field"), DateUtils.YMD);
	 *					returns : yyyy-MM-dd
	 * Example (2): DateUtils.toString(rs.getTimestamp("field"), "yyyy/MM/dd");
	 *					returns : yyyy/MM/dd
	 *
	 * @param date The given date.
	 * @param format A SimpleDateFormat compatible format.
	 * @return result The formatted date-string.
	 */
	public static String toString(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formatter = getSimpleDateFormat(format);
		String result = formatter.format(date);
		return result;
	}

	/**
	 * Transform a Calendar to a String with format DMYHMS
	 *
	 * @param cal An instance of Calendar.
	 * @return A formatted date.
	 * @see #toString(java.util.Date, String)
	 */
	public static String toString(Calendar cal) {
		Utils.assertNotNull("cal == null", cal);
		return toString(cal.getTime(), DateUtils.DMYHMS);
	}
	
	/**
	 * Transform a Calendar to a String.
	 *
	 * @param cal An instance of Calendar.
	 * @param format The format like
	 *               {@link java.text.SimpleDateFormat SimpleDateFormat}.
	 * @return A formatted date.
	 * @see #toString(java.util.Date, String)
	 */
	public static String toString(Calendar cal, String format) {
		Utils.assertNotNull("cal == null", cal);
		return toString(cal.getTime(), format);
	}

	/**
	 * Convert a date to a Timestamp.
	 *
	 * @param dateString A date as a String. (Example: "01-12-2003")
	 * @param datePattern The pattern to parse the date with.
	 *                    (Example: "dd-MM-yyyy").
	 * @return The timestamp.
	 */
	public static Timestamp toTimestamp(String dateString, String datePattern) {
		Timestamp result = null;
		SimpleDateFormat formatter = getSimpleDateFormat(datePattern);
		ParsePosition pos = new ParsePosition(0);
		Date tmpDate = formatter.parse(dateString, pos);
		if (tmpDate != null) {
			result = new Timestamp(tmpDate.getTime());
		}
		return result;
	}

	/**
	 * Convert a date to a Calendar.
	 *
	 * @param dateString A date as a String. (Example: "01-12-2003")
	 * @param datePattern The pattern to parse the date with.
	 *                    (Example: "dd-MM-yyyy").
	 * @return The 33.
	 */
	public static Calendar toCalendar(String dateString, String datePattern) {
		SimpleDateFormat formatter = getSimpleDateFormat(datePattern);
		return toCalendar(dateString, formatter);
	}

	public static Calendar toCalendar(final String dateString,
			final SimpleDateFormat formatter) {
		Calendar result = null;
		ParsePosition pos = new ParsePosition(0);
		Date tmpDate = null;
		try {
			tmpDate = formatter.parse(dateString, pos);
		} catch (RuntimeException paex) { // java.text/ParseException
			return null;
		}
		if (tmpDate != null) {
			result = Calendar.getInstance();
			result.setTime(tmpDate);
		}
		return result;
	}

	/**
	 * Rounds the Calendar to the previous midnight.
	 * Sets hour, minute, second and millisecond to 0.
	 * This does not clone the Calendar.
	 *
	 * @param cal The Calendar to round.
	 * @return A rounded Calendar.
	 */
	public static Calendar floor(Calendar cal) {
		if (cal == null) {
			return cal;
		}
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	/**
	 * Rounds the Calendar to the next midnight.
	 * Sets hour, minute, second and millisecond to 23:59:59.999.
	 * This does not clone the Calendar.
	 *
	 * @param cal Calendar to round.
	 * @return A rounded Calendar.
	 */
	public static Calendar ceil(Calendar cal) {
		if (cal == null) {
			return cal;
		}
		cal.set(Calendar.HOUR_OF_DAY, (HOURS_PER_DAY - 1));
		cal.set(Calendar.MINUTE, (MINUTES_PER_HOUR - 1));
		cal.set(Calendar.SECOND, (SECONDS_PER_MINUTE - 1));
		cal.set(Calendar.MILLISECOND, (MILLIS_PER_SECOND - 1));
		return cal;
	}
	
	/**
	 * Rounds the Date to the next midnight.
	 * Sets hour, minute, second and millisecond to 23:59:59.999.
	 * This does not clone the Date.
	 *
	 * @param date The Date to round.
	 * @return A rounded Date.
	 */
	public static Date ceil(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal = ceil(cal);
		return cal.getTime();
	}

	/**
	 *
	 * @param date the date in the old format
	 * @param usedFormat the format that the old date is in
	 * @param intoFormat the new format you want for the date
	 * @return date in the new format
	 */
	public static String toString(String date, String usedFormat,
								  String intoFormat) {

		Calendar cal = toCalendar(date, usedFormat);
		return toString(cal, intoFormat);
	}

	/**
	 * Returns the maximum of two dates (i.e., the most recent one)
	 * @param date1		The first date
	 * @param date2		The second date
	 * @return The latest of the two given time stamps.
	 */
	public static Timestamp maxDate(Timestamp date1, Timestamp date2) {
		if (date1 == null) {
			return date2;
		}
		if (date2 == null) {
			return date1;
		}
		if (date1.before(date2)) {
			return date2;
		}
		return date1;
	}

	/**
	 * Get the current time as a formatted String.
	 *
	 * @param format The format.
	 * @return The current time using format.
	 */
	public static String now(String format) {
		return toString(Calendar.getInstance(), format);
	}

	/**
	 * Get the current time increased with nrDays, nrMonths and nrYears as a 
	 * formatted String.
	 * @param nrDays # of days you want to add to the current time
	 * @param nrMonths # of months you want to add to the current time
	 * @param nrYears # of years you want to add to the current time
	 * @param format The format.
	 * @return The current time using format.
	 */
	public static String getTimeFromNow(String format, int nrDays, int nrMonths, 
																int nrYears) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, nrDays);
		c.add(Calendar.MONTH, nrMonths);
		c.add(Calendar.YEAR, nrYears);
		return toString(c, format);
	}

	/**
	 * @param starttime The time to start counting from.
	 * @param amountMinutes The amount of minutes to go back in time. 
	 * @return The requested date/time.
	 */
	public static Calendar getPastTimeMinutes(Calendar starttime, 
											  int amountMinutes) {
		starttime.add(Calendar.MINUTE, -amountMinutes);
		return starttime;
	}

	
	/**
	 * @param starttime The time to start counting from.
	 * @param amountHours The amount of hours to go back in time 
	 * 		  (with a standard 24 Hour day).
	 * @return The requested date/time.
	 */
	public static Calendar getPastTimeHours(Calendar starttime, 
															int amountHours) {
		starttime.add(Calendar.HOUR_OF_DAY, -amountHours);
		return starttime;
	}


	/**
	 * @param amountDays The amount of days to go back in time
	 *                   (with a standard 365 day year).
	 * @return The requested date.
	 */
	public static Calendar getPastDateDays(int amountDays) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -amountDays);
		return c;
	}


	/**
	 * @param months The amount of months to go back in time
	 *               (with a standard 12 month year), negative values
	 *               result in future dates.
	 * @return The requested date.
	 */
	public static Calendar getPastDateMonths(int months) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -months);
		return c;
	}

	/**
	 * Calculate the difference in seconds between 2 calendars.
	 *
	 * @param c1 Calendar 1.
	 * @param c2 Calendar 2.
	 * @return The difference in seconds.
	 */
	public static int diffInSeconds(Calendar c1, Calendar c2) {
		return diffIn(c1, c2, MILLIS_PER_SECOND);
	}
	
	/**
	 * Calculate the difference in hours of 2 calendars.
	 *
	 * @param c1 Calendar 1.
	 * @param c2 Calendar 2.
	 * @return The difference in hours.
	 */
	public static int diffInHours(Calendar c1, Calendar c2) {
		return diffIn(c1, c2, MILLIS_PER_HOUR);
	}
	
	private static int diffIn(Calendar c1, Calendar c2, int what) {
		Utils.assertNotNull("c1 is null !",  c1);
		Utils.assertNotNull("c2 is null !",  c2);
		long a = c1.getTimeInMillis();
		long b = c2.getTimeInMillis();
		long diff = Math.abs(a-b);
		return (int) (diff/what);	
	}
	
	/**
	 * Calculate the difference in days of 2 calendars.
	 *
	 * @param c1 Calendar 1.
	 * @param c2 Calendar 2.
	 * @return The difference in days.
	 */
	public static int diffInDays(Calendar c1, Calendar c2) {
		Utils.assertNotNull("c1 is null !",  c1);
		Utils.assertNotNull("c2 is null !",  c2);
		c1 = (Calendar) c1.clone();
		c2 = (Calendar) c2.clone();
		
		floor(c1);
		floor(c2);
		
		long day1 = c1.getTimeInMillis() / MILLIS_PER_DAY;
		long day2 = c2.getTimeInMillis() / MILLIS_PER_DAY;
		
		return (int) (day2 - day1);
	}

	/**
	 * Validate time 
	 * @param time
	 * @return string time String -1 if wrong
	 */
	public static String formatTime(String time) {
		if (time == null) {
			return null;
		}
		if (time.length() == 4) {
			time = "0" + time;			
		}
		if (time.length() != 5) {
			return null;
		}
		if (!time.substring(2,3).equals(":")) {
			return null;
		}
		String hours = time.substring(0,2);
		String minutes = time.substring(3,5);
		int intHours = Integer.parseInt(hours);
		int intMinutes = Integer.parseInt(minutes);
		if (intHours < 0 && intHours > 23){
			return null;
		}
		if (intMinutes<0 && intMinutes>59){
			return null;
		}
		
		return intHours+":"+minutes;
	}

	/**
	 * @return The date and time (formatted).
	 */
	public static String getDatetimeForFilename() {
		DateFormat df = getSimpleDateFormat("yyyy-MM-dd_HH_mm");
		return df.format(new Date());
	}
	
	/**
	 * @param c
	 * @param minutes
	 */
	public static void setMinuteOfDay(Calendar c, long minutes) {
		long hours = minutes / 60;
		long minute = minutes % 60;
		c.set(Calendar.HOUR_OF_DAY, (int)hours);
		c.set(Calendar.MINUTE, (int)minute);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
	}
	
	public static Calendar getCalendar(int year, int month0To11, int day) {
		Calendar c = Calendar.getInstance();
		floor(c);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month0To11);
		c.set(Calendar.DAY_OF_MONTH, day);
		return c;
	}
	
	/**
	 * @param c1
	 * @param c2
	 * @return true als c1 dezelfde dag is als c2
	 */
	public static boolean sameDay(Calendar c1, Calendar c2) {
		Utils.assertNotNull("c1 is null !",  c1);
		Utils.assertNotNull("c2 is null !",  c2);
		int dagVanHetJaar1 = c1.get(Calendar.DAY_OF_YEAR);
		int dagVanHetJaar2 = c2.get(Calendar.DAY_OF_YEAR);
		
		int jaar1 = c1.get(Calendar.YEAR);
		int jaar2 = c2.get(Calendar.YEAR);
		
		return dagVanHetJaar1 == dagVanHetJaar2 && jaar1 == jaar2;
	}
	
	/**
	 * @param startTimeInMilliSeconds
	 * @return seconden sinds startTimeInMilliSeconds
	 */
	public static double getElapsedTimeInSeconds(long startTimeInMilliSeconds) {
		return (Calendar.getInstance().getTimeInMillis() - 
				startTimeInMilliSeconds) / (double)MILLIS_PER_SECOND;
	}
	
	/**
	 * @param input
	 * @param formats
	 * @return eerste formatting in formats die van input een calendar maakt.
	 */
	public static Calendar parseList(String input, String[] formats) {
		for (String format : formats) {
			Calendar c = toCalendar(input, format);
			if (c != null) {
				return c;
			}
		}
		return null;
	}

	/**
	 * Gives the difference in days in natural language (dutch). A diff of 1
	 * will return "morgen".
	 *
	 * @param  diff The difference in days.
	 * @return <code>null</code> if their is no known String for
	 *		   <code>diff</code> days.
	 */
	public static String diffInDaysAsString(long diff) {
		final int middle = 2;
		final String[] tmp = {
			"eergisteren",
			"gisteren",
			"vandaag",
			"morgen",
			"overmorgen"
		};
	
		if (diff <= -(tmp.length - middle) || diff >= (tmp.length - middle)) {
			return null;
		}
		return tmp[(int) diff + middle];
	}

	/**
	 * Gives the difference in days in natural language (dutch). A diff of 1
	 * will return "morgen".
	 *
	 * @param c1 The first Calendar
	 * @param c2 The second Calendar
	 * @return <code>null</code> if there is no known String for
	 *		   <code>diff</code> days.
	 */
	public static String diffInDaysAsString(Calendar c1, Calendar c2) {
		return diffInDaysAsString(diffInDays(c1, c2));
	}

	/**
	 * Get a timestamp String from the current time with the format
	 * 'yyyyMMddHHmmss'.
	 *
	 * @return DateUtils.getSimpleDateFormat("yyyyMMddHHmmss")
	 *		   .format(Calendar.getInstance().getTime()).
	 */
	public static String generateTimeStamp() {
		Calendar now = Calendar.getInstance();
		SimpleDateFormat format = getSimpleDateFormat(
				"yyyyMMddHHmmss");
		return format.format(now.getTime());
	}
	
	public static boolean isSameDay(Calendar cal1, Calendar cal2) {
		Utils.assertNotNull("cal1 is null !",  cal1);
		Utils.assertNotNull("cal2 is null !",  cal2);
		if ((cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) &&
			(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))) {
			return true;
		}
		return false;
	}
	
	public static String getMonthMask() {
		List<String> list = new ArrayList<String>();
		list.add(Calendar.JANUARY + ";Januari");
		list.add(Calendar.FEBRUARY + ";Februari");
		list.add(Calendar.MARCH + ";Maart");
		list.add(Calendar.APRIL + ";April");
		list.add(Calendar.MAY + ";Mei");
		list.add(Calendar.JUNE + ";Juni");
		list.add(Calendar.JULY + ";Juli");
		list.add(Calendar.AUGUST + ";Augustus");
		list.add(Calendar.SEPTEMBER + ";September");
		list.add(Calendar.OCTOBER + ";Oktober");
		list.add(Calendar.NOVEMBER + ";November");
		list.add(Calendar.DECEMBER + ";December");
		return Utils.glue(list, "|");
	}
}
