package com.thumann.server.helper.date;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thumann.server.helper.string.StringUtil;

public class DateUtil {

	public final static String DEFAULT_DATE_FORMAT = "dd.MM.yyyy";

	public static Date getDate(String dateString)
	{
		if (StringUtil.isEmpty(dateString)) {
			return null;
		}
		try {
			return new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(dateString);
		}
		catch (Exception e) {
		}
		return null;
	}

	public static String getDateString(Date date)
	{
		if (date == null) {
			return null;
		}
		return new SimpleDateFormat(DEFAULT_DATE_FORMAT).format(date);
	}

}
