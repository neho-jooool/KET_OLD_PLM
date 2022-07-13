package e3ps.edm.clients.batch;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.method.RemoteMethodServer;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.session.SessionHelper;
import wt.util.WTException;

public class EPMLoadContext implements wt.method.RemoteAccess {

    public static boolean DEBUG = true;
    public static boolean isAdmin;

    public static WTUser  currentUser;

    public static String  userId;

    public EPMLoadContext(String id) {
	this.userId = id;
	init();
    }

    /*
    public void init() {
    	isAdmin = isAdmin(userId);
    }
    */

    public static void init() {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    try {
		currentUser = (WTUser) SessionHelper.getPrincipal();
		Enumeration en = currentUser.parentGroupNames();
		while (en.hasMoreElements()) {
		    String st = (String) en.nextElement();
		    if (st.equals("Administrators")) {
			isAdmin = true;
		    }
		}

	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}

    }

    static {
	if (!wt.method.RemoteMethodServer.ServerFlag) {
	    try {

		//URL url = WTProperties.getServerCodebase();
		// host = url.toString();
		//System.out.println("host =" + host);
	    } catch (Exception e) {
		e.printStackTrace();
	    }
	}
    }


    public static boolean isAdmin(String userId) {

	Class argTypes[] = { String.class };
	Object argValues[] = { userId };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("isAdmin", "e3ps.load.remote.edm.EPMLoadHelper", null, argTypes, argValues);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return ((Boolean) obj).booleanValue();
    }

    public static String load(String userId, Vector datas, String dirString) {

	Class argTypes[] = { String.class, Vector.class, String.class };
	Object argValues[] = { userId, datas, dirString };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("load", "e3ps.load.remote.edm.EPMLoadHelper", null, argTypes, argValues);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return (String) obj;
    }

    public static Object validate(Object data) {

	Class argTypes[] = { Object.class };
	Object argValues[] = { data };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("validate", "e3ps.load.remote.edm.EPMLoadHelper", null, argTypes, argValues);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return obj;
    }


    public static Vector validate(Vector datas) {

	Class argTypes[] = { Vector.class };
	Object argValues[] = { datas };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("validate", "e3ps.load.remote.edm.EPMLoadHelper", null, argTypes, argValues);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return (Vector) obj;
    }

    public static boolean deleteLoadFile(String filePath, String userId) {
	Class argTypes[] = { String.class, String.class };
	Object argValues[] = { filePath, userId };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("deleteLoadFile", "e3ps.load.remote.edm.EPMLoadHelper", null, argTypes, argValues);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return ((Boolean) obj).booleanValue();
    }

    public static Vector getBizCodes(String userId) throws RemoteException, InvocationTargetException {
	Class argTypes[] = { String.class };
	Object argValues[] = { userId };
	Object obj = RemoteMethodServer.getDefault().invoke("getBizCodes", "e3ps.load.remote.edm.EPMCodeHelper", null, argTypes, argValues);
	return (Vector) obj;
    }

    public static Vector getNumberCode(String type) throws RemoteException, InvocationTargetException {
	Class argTypes[] = { String.class };
	Object argValues[] = { type };
	Object obj = RemoteMethodServer.getDefault().invoke("getNumberCode", "e3ps.load.remote.edm.EDMCodeHelper", null, argTypes, argValues);
	return (Vector) obj;
    }

    public static Vector getDevStageSet(String type) throws RemoteException, InvocationTargetException {
	Class argTypes[] = { String.class };
	Object argValues[] = { type };
	Object obj = RemoteMethodServer.getDefault().invoke("getDevStageSet", "e3ps.load.remote.edm.EPMCodeHelper", null, argTypes, argValues);
	return (Vector) obj;
    }

    public static Vector getCADCatsSet(String type, String devStage) throws RemoteException, InvocationTargetException {
	Class argTypes[] = { String.class, String.class };
	Object argValues[] = { type, devStage };
	Object obj = RemoteMethodServer.getDefault().invoke("getCADCatsSet", "e3ps.load.remote.edm.EPMCodeHelper", null, argTypes, argValues);
	return (Vector) obj;
    }

    public static Vector getCADAppTypeSet(String category) throws RemoteException, InvocationTargetException {
	Class argTypes[] = { String.class };
	Object argValues[] = { category };
	Object obj = RemoteMethodServer.getDefault().invoke("getCADAppTypeSet", "e3ps.load.remote.edm.EPMCodeHelper", null, argTypes, argValues);
	return (Vector) obj;
    }

    public static String convNumber(String partNumber, String category, String cadAppType) throws RemoteException, InvocationTargetException {
	Class argTypes[] = { String.class, String.class, String.class };
	Object argValues[] = { partNumber, category, cadAppType };
	Object obj = RemoteMethodServer.getDefault().invoke("convNumber", "e3ps.load.remote.edm.EPMLoadHelper", null, argTypes, argValues);
	return (String) obj;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Added by MJOH, 2011-03-17
    // 프로젝트 링크 추가 처리
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static Vector<Hashtable<String, String>> find(HashMap map) throws WTException {
	Class argTypes[] = new Class[] { HashMap.class };
	Object args[] = new Object[] { map };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("find", "e3ps.load.remote.edm.SearchProjectHelper", null, argTypes, args);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return (Vector<Hashtable<String, String>>) obj;
    }

    public static QuerySpec getE3PSProjectQuerySpec(HashMap map) throws WTException {
	Class argTypes[] = new Class[] { HashMap.class };
	Object args[] = new Object[] { map };
	Object obj = null;
	try {
	    obj = RemoteMethodServer.getDefault().invoke("getE3PSProjectQuerySpec", "e3ps.load.remote.edm.SearchProjectHelper", null, argTypes, args);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return (QuerySpec) obj;
    }

    public static void setUpperNoneLikeCondition(QuerySpec spec, Class target, int index, String column, String value) throws WTException {
	Class argTypes[] = new Class[] { QuerySpec.class, Class.class, int.class, String.class, String.class };
	Object args[] = new Object[] { spec, target, new Integer(index), column, value };
	try {
	    RemoteMethodServer.getDefault().invoke("setUpperNoneLikeCondition", "e3ps.load.remote.edm.SearchProjectHelper", null, argTypes, args);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    e.printStackTrace();
	}
	return;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
