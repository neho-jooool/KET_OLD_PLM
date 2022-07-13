package ext.ket.shared.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.fc.Persistable;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.wfm.entity.KETWfmApprovalHistory;
import ext.ket.sales.entity.KETSalesProject;
import ext.ket.shared.code.entity.NumberCodeDTO;

public class ComparatorUtil {

    public static final Comparator<Object> NUMBERCODESORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    NumberCodeDTO code1 = (NumberCodeDTO) obj1;
	    NumberCodeDTO code2 = (NumberCodeDTO) obj2;
	    int x = Integer.parseInt((StringUtil.checkString(code1.getSorting())) ? code1.getSorting() : "0");
	    int y = Integer.parseInt((StringUtil.checkString(code2.getSorting())) ? code2.getSorting() : "0");

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };

    public static final Comparator<Object> APPROVALLINESORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    KETWfmApprovalHistory history1 = (KETWfmApprovalHistory) obj1;
	    KETWfmApprovalHistory history2 = (KETWfmApprovalHistory) obj2;
	    int x = history1.getSeqNum();
	    int y = history2.getSeqNum();

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };

    public static final Comparator<Object> APPROVALHISTORYSORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    KETWfmApprovalHistory history1 = (KETWfmApprovalHistory) obj1;
	    KETWfmApprovalHistory history2 = (KETWfmApprovalHistory) obj2;
	    int x = history1.getSeqNum();
	    int y = history2.getSeqNum();

	    if (x < y)
		return 1;
	    else if (x > y)
		return -1;
	    else
		return 0;
	}
    };
    public static final Comparator<Object> PJTMEMBERSORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map1 = (HashMap<String, String>) obj1;
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map2 = (HashMap<String, String>) obj2;
	    int x = Integer.parseInt(map1.get("memberType"));
	    int y = Integer.parseInt(map2.get("memberType"));

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };
    public static final Comparator<Persistable> OIDSORT = new Comparator<Persistable>() {
	public int compare(Persistable obj1, Persistable obj2) {

	    int x = (int) CommonUtil.getOIDLongValue(obj1);
	    int y = (int) CommonUtil.getOIDLongValue(obj2);

	    if (x < y)
		return 1;
	    else if (x > y)
		return -1;
	    else
		return 0;
	}
    };
    public static final Comparator<Object> MYPROJECTNAMESORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map1 = (HashMap<String, String>) obj1;
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map2 = (HashMap<String, String>) obj2;
	    int ret = (map1.get("projectname")).compareTo(map2.get("projectname"));
	    return ret;
	}
    };
    
    public static final Comparator<Object> SALESPROJECTDATESORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map1 = (HashMap<String, String>) obj1;
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map2 = (HashMap<String, String>) obj2;
	    int x = Integer.parseInt(map1.get("tempplanDate"));
	    int y = Integer.parseInt(map2.get("tempplanDate"));

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };
    
    public static final Comparator<Object> OIDSTRINGSORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map1 = (HashMap<String, String>) obj1;
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map2 = (HashMap<String, String>) obj2;
	    int x = (int)Long.parseLong(map1.get("oid"));
	    int y = (int)Long.parseLong(map2.get("oid"));

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };
    
    public static final Comparator<Object> SALESMANGESORT = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map1 = (HashMap<String, String>) obj1;
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map2 = (HashMap<String, String>) obj2;
	    int x = (int)Long.parseLong(map1.get("sortNo"));
	    int y = (int)Long.parseLong(map2.get("sortNo"));

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };
    
    public static final Comparator<Object> SALESPROJECTSTEP = new Comparator<Object>() {
	public int compare(Object obj1, Object obj2) {

	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map1 = (HashMap<String, String>) obj1;
	    @SuppressWarnings("unchecked")
	    HashMap<String, String> map2 = (HashMap<String, String>) obj2;
	    int x = (int)Integer.parseInt(map1.get("stepNo"));
	    int y = (int)Integer.parseInt(map2.get("stepNo"));

	    if (x > y)
		return 1;
	    else if (x < y)
		return -1;
	    else
		return 0;
	}
    };
    
    
    public static void sortListVO(List<?> list, final String getterMethodText, final String sortInfo) {
	 
        Collections.sort(list, new Comparator<Object>() {
            @Override
            public int compare(Object firstObject, Object secondObject) {
                int rtn = 0;
                int compareIndex = 0; // 비교 인덱스 (작은 문자열 수)
                String firstData = "";
                String secondData = "";
                int firstValue = 0;
                int secondValue = 0;
 
                // 비교할 대상
                try {
                    Method firstDeclaredMethod = firstObject.getClass().getDeclaredMethod(getterMethodText);
                    firstData = (String) firstDeclaredMethod.invoke(firstObject, new Object[] {});
                    Method secondDeclaredMethod = secondObject.getClass().getDeclaredMethod(getterMethodText);
                    secondData = (String) secondDeclaredMethod.invoke(secondObject, new Object[] {});
                } catch (NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
 
                if (firstData == null || firstData == "" || secondData == null || secondData == "") { return rtn; }
 
                // 기본 정렬값 설정
                if ("DESC".equals(sortInfo)) {
                    if (firstData.length() > secondData.length()) {
                        rtn = -1;
                        compareIndex = secondData.length();
                    } else if (firstData.length() < secondData.length()) {
                        rtn = 1;
                        compareIndex = firstData.length();
                    } else {
                        compareIndex = firstData.length();
                    }
                } else {
                    if (firstData.length() < secondData.length()) {
                        rtn = -1;
                        compareIndex = firstData.length();
                    } else if (firstData.length() > secondData.length()) {
                        rtn = 1;
                        compareIndex = secondData.length();
                    } else {
                        compareIndex = firstData.length();
                    }
                }
 
                for (int i = 0; i < compareIndex; i++) {
                    firstValue = Integer.valueOf(firstData.charAt(i));
                    secondValue = Integer.valueOf(secondData.charAt(i));
 
                    if ("DESC".equals(sortInfo)) {
                        if (firstValue > secondValue) {
                            rtn = -1;
                            break;
                        } else if (firstValue < secondValue) {
                            rtn = 1;
                            break;
                        }
                    } else {
                        if (firstValue < secondValue) {
                            rtn = -1;
                            break;
                        } else if (firstValue > secondValue) {
                            rtn = 1;
                            break;
                        }
                    }
                }
 
                return rtn;
            }
        });
    }
}
