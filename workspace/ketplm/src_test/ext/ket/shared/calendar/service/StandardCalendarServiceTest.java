package ext.ket.shared.calendar.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import ext.ket.shared.log.Kogger;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardCalendarServiceTest extends AbstractUnitTest {

    @Test
    public void testIsNonWorkingDay() throws Exception {
	assertThat(CalendarHelper.service.isNonWorkingDay("2014-07-08"), is(true));
	assertThat(CalendarHelper.service.isNonWorkingDay("2014-07-09"), is(false));
    }

    @Test
    public void testGetNonWorkingDays() throws Exception {
	Kogger.debug(getClass(), CalendarHelper.service.getNonWorkingDays("2014-11").size());
	assertThat(CalendarHelper.service.getNonWorkingDays("2014-11").size(), not(0));
    }

}
