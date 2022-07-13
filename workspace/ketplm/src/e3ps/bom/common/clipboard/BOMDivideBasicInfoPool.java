package e3ps.bom.common.clipboard;

import java.util.LinkedHashMap;
import java.util.Vector;

public class BOMDivideBasicInfoPool 
{
	// 로그인 사용자 정보
	private static String userId = "wcadmin";
	private static String userName = "windchill Administrator";
	private static String userDept = "PLM TFT";
	private static String userEMail = "aaa@bbb.com";

	// 작업중인 Organization 정보
	private static String orgId = "";
	private static String orgCode = "";
	
	// Co-Worker 정보
	private static Vector coWorkerVec = new Vector();

	// Work Item Oid 정보
	private static String srcWorkItemOid = "";
	private static String tgtWorkItemOid = "";
	
	// Source BOM 관련 정보
	private static String srcHeaderType = "";
	private static String srcHeaderNo = "";
	private static String srcHeaderState = "";
	private static String srcCodeNo = "";
	private static String srcCodeDesc = "";
	private static String srcCodeUom = "";
	private static String srcCodeVer = "";
	private static String srcCodeState = "";
	
	// Target BOM 관련 정보
	private static String tgtHeaderType = "";
	private static String tgtHeaderNo = "";
	private static String tgtHeaderState = "";
	private static String tgtCodeNo = "";
	private static String tgtCodeDesc = "";
	private static String tgtCodeUom = "";
	private static String tgtCodeVer = "";
	private static String tgtCodeState = "";
	
	// BOM Validation 시 End Working 임을 알려주기 위한 flag
	private static String validationForEnd = "";

	public BOMDivideBasicInfoPool()
	{
	}

	/**
	 * 로그인 한 사용자의 ID Setter & Getter
	 **/
	public static final void setUserId(String s)
	{
		userId = s;
	}

	public static final String getUserId()
	{
		return userId;
	}

	/**
	 * 로그인 한 사용자의 이름 Setter & Getter
	 **/
	public static final void setUserName(String s)
	{
		userName = s;
	}

	public static final String getUserName()
	{
		return userName;
	}

	/**
	 * 로그인 한 사용자의 부서(팀)명칭 Setter & Getter
	 **/
	public static final void setUserDept(String s)
	{
		userDept = s;
	}
	
	public static final String getUserDept()
	{
		return userDept;
	}

	/**
	 * 로그인 한 사용자의 이메일 Setter & Getter
	 **/
	public static final void setUserEMail(String s)
	{
		userEMail = s;
	}

	public static final String getUserEMail()
	{
		return userEMail;
	}

	/**
	 * 작업중인 ORG ID Setter & Getter
	 **/	
	public static final void setOrgId(String s)
	{
		orgId = s;
	}

	public static final String getOrgId()
	{
		return orgId;
	}

	/**
	 * 작업중인 ORG Code Setter & Getter
	 **/
	public static final void setOrgCode(String s)
	{
		orgCode = s;
	}

	public static final String getOrgCode()
	{
		return orgCode;
	}

	/**
	 * BOM Co-Worker 정보 Setter & Getter
	 **/
	public static final void setCoWorkerVec(Vector v)
	{
		if (v != null)
			coWorkerVec = v;
	}

	public static final Vector getCoWorkerVec()
	{
		return coWorkerVec;
	}
	
	/**
	 * Source BOM 관련 Work Item OID Setter & Getter
	 **/
	public static final void setSourceWorkItemOid(String s)
	{
		srcWorkItemOid = s;
	}

	public static final String getSourceWorkItemOid()
	{
		return srcWorkItemOid;
	}
	
	/**
	 * Target BOM 관련 Work Item OID Setter & Getter
	 **/
	public static final void setTgtWorkItemOid(String s)
	{
		tgtWorkItemOid = s;
	}

	public static final String getTgtWorkItemOid()
	{
		return tgtWorkItemOid;
	}

	/**
	 * Source BOM 관련 Header Type Setter & Getter
	 **/
	public static final void setSourceHeaderType(String s)
	{
		srcHeaderType = s;
	}

	public static final String getSourceHeaderType()
	{
		return srcHeaderType;
	}	

	/**
	 * Source BOM 관련 Header Number Setter & Getter
	 **/
	public static final void setSourceHeaderNo(String s)
	{
		srcHeaderNo = s;
	}

	public static final String getSourceHeaderNo()
	{
		return srcHeaderNo;
	}

	/**
	 * Source BOM 관련 Header Lifecycle State Setter & Getter
	 **/
	public static final void setSourceHeaderState(String s)
	{
		srcHeaderState = s;
	}

	public static final String getSourceHeaderState()
	{
		return srcHeaderState;
	}

	/**
	 * Source BOM 0 Level Item Code Setter & Getter
	 **/
	public static final void setSourceCodeNo(String s)
	{
		srcCodeNo = s;
	}

	public static final String getSourceCodeNo()
	{
		return srcCodeNo;
	}	

	/**
	 * Source BOM 0 Level Item Description Setter & Getter
	 **/
	public static final void setSourceCodeDesc(String s)
	{
		srcCodeDesc = s;
	}

	public static final String getSourceCodeDesc()
	{
		return srcCodeDesc;
	}

	/**
	 * Source BOM 0 Level Item UOM Setter & Getter
	 **/
	public static final void setSourceCodeUom(String s)
	{
		srcCodeUom = s;
	}

	public static final String getSourceCodeUom()
	{
		return srcCodeUom;
	}
	
	/**
	 * Source BOM 0 Level Item Version Setter & Getter
	 **/
	public static final void setSourceCodeVer(String s)
	{
		srcCodeVer = s;
	}

	public static final String getSourceCodeVer()
	{
		return srcCodeVer;
	}
	
	/**
	 * Source BOM 0 Level Item Version Setter & Getter
	 **/
	public static final void setSourceCodeState(String s)
	{
		srcCodeState = s;
	}

	public static final String getSourceCodeState()
	{
		return srcCodeState;
	}	
	
	/**
	 * Target BOM 관련 Header Type Setter & Getter
	 **/
	public static final void setTargetHeaderType(String s)
	{
		tgtHeaderType = s;
	}

	public static final String getTargetHeaderType()
	{
		return tgtHeaderType;
	}	

	/**
	 * Target BOM 관련 Header Number Setter & Getter
	 **/
	public static final void setTargetHeaderNo(String s)
	{
		tgtHeaderNo = s;
	}

	public static final String getTargetHeaderNo()
	{
		return tgtHeaderNo;
	}

	/**
	 * Target BOM 관련 Header Lifecycle State Setter & Getter
	 **/
	public static final void setTargetHeaderState(String s)
	{
		tgtHeaderState = s;
	}

	public static final String getTargetHeaderState()
	{
		return tgtHeaderState;
	}

	/**
	 * Target BOM 0 Level Item Code Setter & Getter
	 **/
	public static final void setTargetCodeNo(String s)
	{
		tgtCodeNo = s;
	}

	public static final String getTargetCodeNo()
	{
		return tgtCodeNo;
	}	

	/**
	 * Target BOM 0 Level Item Description Setter & Getter
	 **/
	public static final void setTargetCodeDesc(String s)
	{
		tgtCodeDesc = s;
	}

	public static final String getTargetCodeDesc()
	{
		return tgtCodeDesc;
	}	

	/**
	 * Target BOM 0 Level Item UOM Setter & Getter
	 **/
	public static final void setTargetCodeUom(String s)
	{
		tgtCodeUom = s;
	}

	public static final String getTargetCodeUom()
	{
		return tgtCodeUom;
	}

	/**
	 * Target BOM 0 Level Item Version Setter & Getter
	 **/
	public static final void setTargetCodeVersion(String s)
	{
		tgtCodeVer = s;
	}

	public static final String getTargetCodeVersion()
	{
		return tgtCodeVer;
	}		
	
	/**
	 * Target BOM 0 Level Item Version Setter & Getter
	 **/
	public static final void setTargetCodeLifecycleState(String s)
	{
		tgtCodeState = s;
	}

	public static final String getTargetCodeLifecycleState()
	{
		return tgtCodeState;
	}	
	
	/**
	 * Target BOM 0 Level Item Version Setter & Getter
	 **/
	public static final void setValidationForEnd(String s)
	{
		validationForEnd = s;
	}

	public static final String getValidationForEnd()
	{
		return validationForEnd;
	}

	
	public static final void setInitialize()
	{
		coWorkerVec = new Vector();
		validationForEnd = "";
	}
}
