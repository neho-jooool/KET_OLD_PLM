package e3ps.common.web;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.method.RemoteAccess;
import wt.pds.StatementSpec;
import ext.ket.shared.log.Kogger;

public class CommonSearchHelper implements RemoteAccess, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    static final boolean      SERVER	   = wt.method.RemoteMethodServer.ServerFlag;

    // static CommonSearchHelper manager = new CommonSearchHelper();

    /**
     * 최초 페이지를 리스트할 때 사용한다.
     * 
     * @param req
     * @param res
     * @return
     */
    public static PageControl find(Integer perPage, Integer formPage, StatementSpec spec) {

	if (!SERVER) {
	    Class argTypes[] = new Class[] { Integer.class, Integer.class, StatementSpec.class };
	    Object args[] = new Object[] { perPage, formPage, spec };
	    PageControl obj = null;
	    try {
		obj = (PageControl) wt.method.RemoteMethodServer.getDefault().invoke("find", "e3ps.common.web.CommonSearchHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(CommonSearchHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(CommonSearchHelper.class, e);
	    }
	    return obj;
	}
	try {
	    PagingQueryResult paging = PagingSessionHelper.openPagingSession(0, perPage.intValue(), spec);
	    return new PageControl(paging, 0, formPage.intValue(), perPage.intValue());
	} catch (Exception e) {
	    Kogger.error(CommonSearchHelper.class, e);
	    return null;
	}
    }

    /**
     * 최초 페이지를 리스트할 때 사용한다.
     * 
     * @param req
     * @param res
     * @return
     */
    public static PageControl find(Integer perPage, Integer formPage, StatementSpec spec, Integer page) {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Integer.class, Integer.class, StatementSpec.class, Integer.class };
	    Object args[] = new Object[] { perPage, formPage, spec, page };
	    PageControl obj = null;
	    try {
		obj = (PageControl) wt.method.RemoteMethodServer.getDefault().invoke("find", "e3ps.common.web.CommonSearchHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(CommonSearchHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(CommonSearchHelper.class, e);
	    }
	    return obj;
	}
	try {
	    PagingQueryResult paging = PagingSessionHelper.openPagingSession(page.intValue(), perPage.intValue(), spec);
	    return new PageControl(paging, page.intValue(), formPage.intValue(), perPage.intValue());
	} catch (Exception e) {
	    Kogger.error(CommonSearchHelper.class, e);
	    return null;
	}
    }

    /**
     * PageSessionId가 있는 경우 사용된다.
     * 
     * @param req
     * @param sessionid
     * @return
     */
    public static PageControl find(Integer perPage, Integer formPage, Integer page, String sessionid) {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Integer.class, Integer.class, Integer.class, String.class };
	    Object args[] = new Object[] { perPage, page, formPage, sessionid };
	    PageControl obj = null;
	    try {
		obj = (PageControl) wt.method.RemoteMethodServer.getDefault().invoke("find", "e3ps.common.web.CommonSearchHelper", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(CommonSearchHelper.class, e);
	    } catch (InvocationTargetException e) {
		Kogger.error(CommonSearchHelper.class, e);
	    }
	    return obj;
	}
	try {
	    PagingQueryResult paging = PagingSessionHelper.fetchPagingSession((page.intValue() - 1) * perPage.intValue(), perPage.intValue(), Long.parseLong(sessionid));
	    return new PageControl(paging, page.intValue(), formPage.intValue(), perPage.intValue());
	} catch (Exception e) {
	    Kogger.error(CommonSearchHelper.class, e);
	    return null;
	}
    }
}
