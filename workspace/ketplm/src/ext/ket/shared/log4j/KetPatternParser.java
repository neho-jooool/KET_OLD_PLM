package ext.ket.shared.log4j;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.log4j.helpers.AbsoluteTimeDateFormat;
import org.apache.log4j.helpers.DateTimeDateFormat;
import org.apache.log4j.helpers.FormattingInfo;
import org.apache.log4j.helpers.ISO8601DateFormat;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

class KetPatternParser extends PatternParser {

    private TimeZone oTimezone;

    public KetPatternParser(String sPattern) {

	super(sPattern);

    }

    public void setTimeZone(TimeZone oTimezone) {

	this.oTimezone = oTimezone;

    }

    public TimeZone getTimeZone() {

	return this.oTimezone;

    }

    protected void finalizeConverter(char c) {

	PatternConverter oPatternConverter = null;

	if (c == 100) {

	    DateFormat oDateFormat;

	    String sDateFormat = "ISO8601";
	    String sOpt = extractOption();

	    if (sOpt != null)
		sDateFormat = sOpt;

	    if (sDateFormat.equalsIgnoreCase("ISO8601"))
		oDateFormat = new ISO8601DateFormat();
	    else if (sDateFormat.equalsIgnoreCase("ABSOLUTE"))
		oDateFormat = new AbsoluteTimeDateFormat();
	    else if (sDateFormat.equalsIgnoreCase("DATE"))
		oDateFormat = new DateTimeDateFormat();
	    else {

		try {

		    oDateFormat = new SimpleDateFormat(sDateFormat);

		} catch (IllegalArgumentException e) {

		    LogLog.error("Could not instantiate SimpleDateFormat with " + sDateFormat, e);
		    oDateFormat = (DateFormat) OptionConverter.instantiateByClassName("org.apache.log4j.helpers.ISO8601DateFormat", java.text.DateFormat.class, null);

		}

	    }

	    oDateFormat.setTimeZone(getTimeZone());

	    oPatternConverter = new DatePatternConverter(formattingInfo, oDateFormat);
	    currentLiteral.setLength(0);
	    addConverter(oPatternConverter);

	}

	else
	    super.finalizeConverter(c);

    }

    private static class DatePatternConverter extends PatternConverter {

	private DateFormat oDateFormat;
	private Date oDate;

	public String convert(LoggingEvent oLoggingEvent) {

	    oDate.setTime(oLoggingEvent.timeStamp);

	    String sConverted = null;

	    try {

		sConverted = oDateFormat.format(oDate);

	    } catch (Exception ex) {

		LogLog.error("Error occured while converting oDate.", ex);

	    }

	    return sConverted;

	}

	DatePatternConverter(FormattingInfo formattingInfo, DateFormat oDateFormat) {

	    super(formattingInfo);

	    oDate = new Date();
	    this.oDateFormat = oDateFormat;

	}

    }

}