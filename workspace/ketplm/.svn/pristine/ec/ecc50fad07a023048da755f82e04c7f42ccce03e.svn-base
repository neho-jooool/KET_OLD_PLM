package ext.ket.shared.calendar.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import wt.calendar.CalendarComponent;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.StatementSpec;
import wt.projmgmt.util.CalendarHelper;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import ext.ket.shared.log.Kogger;

public class StandardCalendarService extends StandardManager implements CalendarService {
    private static final long serialVersionUID = 1L;

    public static StandardCalendarService newStandardCalendarService() throws WTException {
	StandardCalendarService instance = new StandardCalendarService();
	instance.initialize();
	return instance;
    }

    @Override
    public boolean isNonWorkingDay(String dateStr) throws Exception {
	DateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
	return CalendarHelper.isNonWorking(CalendarHelper.getSystemCalendarOwner(), sdFormat.parse(dateStr), TimeZone.getDefault());
    }

    @Override
    public List<String> getNonWorkingDays(String yyyymm) throws Exception {
	QuerySpec query = new QuerySpec();
	SearchCondition sc = null;
	int idx = query.appendClassList(CalendarComponent.class, false);

	SQLFunction toChar = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR,
	        new ColumnExpression[] { new ClassAttribute(CalendarComponent.class, CalendarComponent.START_DATE), ConstantExpression.newExpression("YYYY-MM-DD") });
	query.appendSelect(toChar, false);

	SQLFunction sqlFunction = SQLFunction.newSQLFunction(SQLFunction.TO_CHAR, new ColumnExpression[] { new ClassAttribute(CalendarComponent.class, CalendarComponent.START_DATE),
	        ConstantExpression.newExpression("YYYY-MM") });
	sc = new SearchCondition(sqlFunction, SearchCondition.EQUAL, new KeywordExpression("'" + yyyymm + "'"));
	query.appendWhere(sc, new int[] { idx });
	query.appendAnd();
	sc = new SearchCondition(CalendarComponent.class, CalendarComponent.WORKING_DAY, SearchCondition.IS_FALSE);
	query.appendWhere(sc, new int[] { idx });
	query.appendOrderBy(new OrderBy(toChar, false), new int[] { idx });
	Kogger.debug(getClass(), query);
	QueryResult qr = PersistenceHelper.manager.find((StatementSpec) query);
	List<String> list = new ArrayList<String>();
	while (qr.hasMoreElements()) {
	    Object[] objArr = (Object[]) qr.nextElement();
	    list.add((String) objArr[0]);
	}
	return list;
    }

}
