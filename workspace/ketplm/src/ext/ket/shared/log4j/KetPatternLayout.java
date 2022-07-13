package ext.ket.shared.log4j;

import java.util.TimeZone;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternParser;

public class KetPatternLayout extends PatternLayout {

    private String sTimezone;
    private KetPatternParser oPatternParser = null;

    public KetPatternLayout() {

	super();

    }

    public KetPatternLayout(String sPattern) {

	super(sPattern);

    }

    public String getTimeZone() {

	return sTimezone;

    }

    public void setTimeZone(String sTimezone) {

	if (oPatternParser != null)
	    oPatternParser.setTimeZone(TimeZone.getTimeZone(sTimezone));
	this.sTimezone = sTimezone;

    }

    protected PatternParser createPatternParser(String sPattern) {

	if (sTimezone == null)
	    sTimezone = TimeZone.getDefault().getID();

	oPatternParser = new KetPatternParser(sPattern);
	oPatternParser.setTimeZone(TimeZone.getTimeZone(sTimezone));

	return oPatternParser;

    }

}