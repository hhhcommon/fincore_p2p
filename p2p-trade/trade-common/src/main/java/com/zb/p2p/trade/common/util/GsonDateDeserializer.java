package com.zb.p2p.trade.common.util;


import com.google.gson.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author yuxingsong
 * 
 */
@Slf4j
public class GsonDateDeserializer implements JsonDeserializer<Date> {

	private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT,
			Locale.US);
	private final DateFormat localFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT);
	private final DateFormat iso8601Format = buildIso8601Format();
	private final DateFormat format1 = buildFormat1();
	private final DateFormat format2 = buildFormat2();

	private static DateFormat buildIso8601Format() {
		DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return iso8601Format;
	}

	private static DateFormat buildFormat1() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	private static DateFormat buildFormat2() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		if (!(json instanceof JsonPrimitive)) {
			throw new JsonParseException("The date should be a string value");
		}
		Date date = deserializeToDate(json.getAsString());
		if (typeOfT == Date.class) {
			return date;
		} else if (typeOfT == Timestamp.class) {
			return new Timestamp(date.getTime());
		} else if (typeOfT == java.sql.Date.class) {
			return new java.sql.Date(date.getTime());
		} else {
			throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
		}
	}

	private Date deserializeToDate(String json) {
		try {
			return localFormat.parse(json);
		} catch (ParseException ignored) {
			log.warn(ignored.getMessage());
		}
		try {
			return enUsFormat.parse(json);
		} catch (ParseException ignored) {
			log.warn(ignored.getMessage());
		}
		try {
			return format1.parse(json);
		} catch (ParseException ignored) {
			log.warn(ignored.getMessage());
		}
		try {
			return format2.parse(json);
		} catch (ParseException ignored) {
			log.warn(ignored.getMessage());
		}
		try {
			return iso8601Format.parse(json);
		} catch (ParseException e) {
			throw new JsonSyntaxException(json, e);
		}
	}

}
