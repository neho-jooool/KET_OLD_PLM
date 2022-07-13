package ext.ket.shared.calendar.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ext.ket.shared.calendar.service.CalendarHelper;

@Controller
@RequestMapping("/calendar/*")
public class CalendarController {
    @RequestMapping("/isNonWorkingDay")
    @ResponseBody
    public boolean isNonWorkingDay(String checkDate) throws Exception {
	return CalendarHelper.service.isNonWorkingDay(checkDate);
    }

    @RequestMapping("/getNonWorkingDaysByMonth")
    @ResponseBody
    public List<String> getNonWorkingDaysByMonth(String yyyymm) throws Exception {
	return CalendarHelper.service.getNonWorkingDays(yyyymm);
    }
}
