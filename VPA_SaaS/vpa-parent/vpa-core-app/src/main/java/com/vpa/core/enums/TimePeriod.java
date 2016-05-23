package com.vpa.core.enums;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public enum TimePeriod {
	TO_DATE, LAST_MONTH, LAST_3_MONTHS, LAST_6_MONTHS, YEAR_TO_DATE;

	public static Map<Integer, Integer> getTimePeriod(String timePeriod) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		end.add(Calendar.MONTH, -1);
		SimpleDateFormat timeFormat = new SimpleDateFormat("yyyyMMdd");
		Integer startDate = 0;
		Integer endDate = 0;
		Map<Integer, Integer> timeDuration = new HashMap<>();
		if (timePeriod.isEmpty()) {
			throw new IllegalArgumentException("time period  is Null in JSON ");
		}
		String timeStr = timePeriod.replace(' ', '_').toUpperCase();
		if (timeStr.equalsIgnoreCase(TimePeriod.LAST_MONTH.toString())) {
			start.set(Calendar.DATE, 1);
			startDate = Integer.parseInt(timeFormat.format(start.getTime()));
			endDate= Integer.parseInt(timeFormat.format(end.getTime()));
			timeDuration.put(startDate, endDate);
			return timeDuration;
		} else if (timeStr
				.equalsIgnoreCase(TimePeriod.LAST_3_MONTHS.toString())) {
			start.add(Calendar.MONTH, -4);
			startDate = Integer.parseInt(timeFormat.format(start.getTime()));
			endDate= Integer.parseInt(timeFormat.format(end.getTime()));
			timeDuration.put(startDate, endDate);
			return timeDuration;
		} else if (timeStr
				.equalsIgnoreCase(TimePeriod.LAST_6_MONTHS.toString())) {
			start.add(Calendar.MONTH, -7);
			startDate = Integer.parseInt(timeFormat.format(start.getTime()));
			endDate= Integer.parseInt(timeFormat.format(end.getTime()));
			timeDuration.put(startDate, endDate);
			return timeDuration;
		} else if (timeStr.equalsIgnoreCase(TimePeriod.YEAR_TO_DATE.toString())) {
			start.set(Calendar.MONTH, 0);
			startDate = Integer.parseInt(timeFormat.format(start.getTime()));
			endDate= Integer.parseInt(timeFormat.format(end.getTime()));
			timeDuration.put(startDate, endDate);
			return timeDuration;
		}else{
			timeDuration.put(0, 0);
			return timeDuration;
		}
	}

}
