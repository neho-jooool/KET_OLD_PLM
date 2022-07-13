package e3ps.bom.common.clipboard;

import java.util.LinkedHashMap;
import java.util.Vector;

import e3ps.bom.common.component.BOMAssyComponent;

public class BOMBasicInfoPool
{
    private static String authHeader = "";
    private static String userId = "wcadmin";
    private static String userName = "windchill admin";
    private static String userDept = "dept";
    private static String userGroup = "";
    private static String userEMail = "slgmoney@ket.com";
	private static String userTel = "02-111-1111";
    private static String userRole = "BOMMaster";
    private static String savePath = "NA";
    private static boolean isAdminFlag = false;
    private static String workItemOid = "";
	private static String orgId = "";
    private static String orgCode = "1";
    private static String orgName = "";
    private static Vector coWorkerVec = new Vector();
    private static LinkedHashMap statusHash = null;
	private static LinkedHashMap bomEcoStatusHash = null;
    private static String serverCodebase = "";
    private static String publicBOMStatus = "";
    private static String publicBOMStatusKr = "";
    private static String publicMyStatus = "";
    private static String publicBomOid = "";
    private static String isLastApprover = "";
    private static int publicTotalDataCount = 0;
    private static String publicModelName = "Empty";
	private static String publicModelDesc = "";
	private static String publicModelUom = "";
	private static String publicModelUserItemType = "";
	private static String publicModelStatus = "";
	private static String publicModelStatusKr = "";
    private static String publicTransFlag = "";
    private static String publicBasicModelName = "";
	private static String publicBasicModelDesc = "";
    private static String publicBomNumber = "";
    private static String publicOwnerDate = "";
    private static String publicOwnerDept = "";
    private static String publicOwnerID = "wcadmin";
    private static String publicOwnerName = "";
    private static String publicOwnerEmail = "";
    private static String publicCheckOutDate = "";
    private static String publicCheckOutStatus = "";
    private static String publicCheckOuter = "";
    private static String publicCheckOuterGroup = "";
    private static String publicCheckOuterID = "";
    private static String publicApproveDate = "";
    private static String publicApproveDept = "";
    private static String publicApproveID = "";
    private static String publicApproveName = "";
	private static String bomEcoNumber = "";
	private static String bomEcoType = "";		// Standard / Multiple ECO 구분
	private static String bomEcoReason = "";
	private static String bomEcoReasonDetails = "";
	private static String bomEcoRelatedEcrNo = "";
	private static String bomEcoTitle = "";
	private static String bomEcoDescription = "";
	private static String bomHeaderPartType = "";
	private static String bomBoxQuantity = "";
	private static boolean errorFrame = false;

	// BOM Validation 결과를 담고 있는 정보
    private static boolean bomValidationResult = false;
    private static boolean hasError = false;

	// BOM Validation 시 End Working 임을 알려주기 위한 flag
	private static String validationForEnd = "";

	// MyBom 에서 선택된 Standard type의 ECO No
	private static String publicECONo = "";

	private static String bomSearchGubun = "";
	private static BOMAssyComponent bomComponent = null;

    public BOMBasicInfoPool()
    {
    }

    public static final String getAuthHeader()
    {
        return authHeader;
    }

    public static final String getUserId()
    {
        return userId;
    }

    public static final String getUserName()
    {
        return userName;
    }

    public static final String getUserDept()
    {
        return userDept;
    }

    public static final String getUserEMail()
    {
        return userEMail;
    }

	public static final String getUserTel()
	{
		return userTel;
	}

    public static final String getUserGroup()
    {
        return userGroup;
    }

    public static final String getUserRole()
    {
        return userRole;
    }

    public static final String getSavePath()
    {
        return savePath;
    }

    public static final boolean isAdmin()
    {
        return isAdminFlag;
    }

    public static final String getWorkItemOid()
    {
        return workItemOid;
    }

	public static final String getOrgID()
	{
		return orgId;
	}

    public static final String getOrgCode()
    {
        return orgCode;
    }

    public static final String getOrgName()
    {
        return orgName;
    }

    public static final Vector getCoWorkerVec()
    {
        return coWorkerVec;
    }

    public static final LinkedHashMap getStatusHash()
    {
        return statusHash;
    }

    public static final LinkedHashMap getBomEcoStatusHash()
    {
        return bomEcoStatusHash;
    }

    public static final String getServerCodebase()
    {
        return serverCodebase;
    }

    public static final String getPublicBOMStatus()
    {
        return publicBOMStatus;
    }
    
    public static final String getPublicBOMStatusKr()
    {
        return publicBOMStatusKr;
    }

    public static final String getPublicMyStatus()
    {
        return publicMyStatus;
    }

    public static final String getPublicBOMOid()
    {
        return publicBomOid;
    }

    public static final int getPublicTotalDataCount()
    {
        return publicTotalDataCount;
    }

    public static final String getPublicModelName()
    {
        return publicModelName;
    }

    public static final String getPublicModelDesc()
    {
        return publicModelDesc;
    }

    public static final String getPublicModelUom()
    {
        return publicModelUom;
    }

    public static final String getPublicModelUserItemType()
    {
        return publicModelUserItemType;
    }

    public static final String getPublicModelStatus()
    {
        return publicModelStatus;
    }
    
    public static final String getPublicModelStatusKr()
    {
        return publicModelStatusKr;
    }

    public static final String getPublicTransFlag()
    {
        return publicTransFlag;
    }

    public static final String getPublicBasicModelName()
    {
        return publicBasicModelName;
    }

    public static final String getPublicBasicModelDesc()
    {
        return publicBasicModelDesc;
    }

    public static final String getPublicBomNumber()
    {
        return publicBomNumber;
    }

    public static final String getPublicOwnerDate()
    {
        return publicOwnerDate;
    }

    public static final String getPublicOwnerDept()
    {
        return publicOwnerDept;
    }

    public static final String getPublicOwnerId()
    {
        return publicOwnerID;
    }

    public static final String getPublicOwnerName()
    {
        return publicOwnerName;
    }

    public static final String getPublicOwnerEmail()
    {
        return publicOwnerEmail;
    }

    public static final String getPublicCheckOutDate()
    {
        return publicCheckOutDate;
    }

    public static final String getPublicCheckOutStatus()
    {
        return publicCheckOutStatus;
    }

    public static final String getPublicCheckOuter()
    {
        return publicCheckOuter;
    }

    public static final String getPublicCheckOuterGroup()
    {
        return publicCheckOuterGroup;
    }

    public static final String getPublicCheckOuterId()
    {
        return publicCheckOuterID;
    }

    public static final String getPublicApproveDate()
    {
        return publicApproveDate;
    }

    public static final String getPublicApproveDept()
    {
        return publicApproveDept;
    }

    public static final String getPublicApproveId()
    {
        return publicApproveID;
    }

    public static final String getPublicApproveName()
    {
        return publicApproveName;
    }

    public static final String getLastApprover()
    {
        return isLastApprover;
    }

	public static final boolean getBomValidationResult()
	{
		return bomValidationResult;
	}

	public static final boolean getHasErrorInValidation()
	{
		return hasError;
	}

    public static final String getValidationForEnd()
    {
        return validationForEnd;
    }

	public static final String getBomEcoNumber()
	{
		return bomEcoNumber;
	}

	public static final String getBomEcoType()
	{
		return bomEcoType;
	}

    public static final String getECONo()
    {
    	return publicECONo;
    }

	public static final String getBomEcoReason()
	{
		return bomEcoReason;
	}

    public static final String getBomEcoReasonDetails()
    {
    	return bomEcoReasonDetails;
    }

	public static final String getBomEcoRelatedEcrNo()
	{
		return bomEcoRelatedEcrNo;
	}

    public static final String getBomEcoTitle()
    {
    	return bomEcoTitle;
    }

    public static final String getBomEcoDescription()
    {
    	return bomEcoDescription;
    }
    
    public static final String getBomHeaderPartType()
    {
    	return bomHeaderPartType;
    }
    
    public static final String getBomBoxQuantity()
    {
    	return bomBoxQuantity;
    }

	public static final boolean getErrorFrame()
	{
		return errorFrame;
	}

	public static final BOMAssyComponent getBomComponent()
	{
		return bomComponent;
	}
	
	public static final String getBomSearchGubun()
	{
		return bomSearchGubun;
	}

    public static final void setAuthHeader(String s)
    {
        authHeader = s;
    }

    public static final void setUserId(String s)
    {
        userId = s;
    }

    public static final void setUserName(String s)
    {
        userName = s;
    }

    public static final void setUserDept(String s)
    {
        userDept = s;
    }

    public static final void setUserEMail(String s)
    {
        userEMail = s;
    }

	public static final void setUserTel(String s)
	{
		userTel = s;
	}

    public static final void setUserGroup(String s)
    {
        userGroup = s;
    }

    public static final void setUserRole(String s)
    {
        userRole = s;
    }

    public static final void setSavePath(String s)
    {
        savePath = s;
    }

    public static final void setIsAdminFlag(boolean b)
    {
        isAdminFlag = b;
    }

    public static final void setWorkItemOid(String s)
    {
        workItemOid = s;
    }

	public static final void setOrgId(String s)
	{
		orgId = s;
	}

    public static final void setOrgCode(String s)
    {
        orgCode = s;
    }

    public static final void setOrgName(String s)
    {
        orgName = s;
    }

    public static final void setCoWorkerVec(Vector v)
    {
        if(v != null)
            coWorkerVec = v;
    }

    public static final void setStatusHash(LinkedHashMap s)
    {
        statusHash = s;
    }

    public static final void setBomEcoStatusHash(LinkedHashMap s)
    {
        bomEcoStatusHash = s;
    }

    public static final void setServerCodebase(String s)
    {
        serverCodebase = s;
    }

    public static final void setPublicBOMStatus(String s)
    {
        publicBOMStatus = s;
    }
    
    public static final void setPublicBOMStatusKr(String s)
    {
        publicBOMStatusKr = s;
    }

    public static final void setPublicMyStatus(String s)
    {
        publicMyStatus = s;
    }

    public static final void setPublicBOMOid(String s)
    {
        publicBomOid = s;
    }

    public static final void setPublicTotalDataCount(int c)
    {
        publicTotalDataCount = c;
    }

    public static final void setPublicModelName(String s)
    {
        publicModelName = s;
    }

    public static final void setPublicModelDesc(String s)
    {
        publicModelDesc = s;
    }

    public static final void setPublicModelUom(String s)
    {
        publicModelUom = s;
    }

    public static final void setPublicModelUserItemType(String s)
    {
        publicModelUserItemType = s;
    }

    public static final void setPublicModelStatus(String s)
    {
        publicModelStatus = s;
    }
    
    public static final void setPublicModelStatusKr(String s)
    {
        publicModelStatusKr = s;
    }

    public static final void setPublicTransFlag(String s)
    {
        publicTransFlag = s;
    }

    public static final void setPublicBasicModelName(String s)
    {
        publicBasicModelName = s;
    }

    public static final void setPublicBasicModelDesc(String s)
    {
        publicBasicModelDesc = s;
    }

    public static final void setPublicBomNumber(String s)
    {
        publicBomNumber = s;
    }

    public static final void setPublicOwnerDate(String s)
    {
        publicOwnerDate = s;
    }

    public static final void setPublicOwnerDept(String s)
    {
        publicOwnerDept = s;
    }

    public static final void setPublicOwnerId(String s)
    {
        publicOwnerID = s;
    }

    public static final void setPublicOwnerName(String s)
    {
        publicOwnerName = s;
    }

    public static final void setPublicOwnerEmail(String s)
    {
        publicOwnerEmail = s;
    }

    public static final void setPublicCheckOutDate(String s)
    {
        publicCheckOutDate = s;
    }

    public static final void setPublicCheckOutStatus(String s)
    {
        publicCheckOutStatus = s;
    }

    public static final void setPublicCheckOuter(String s)
    {
        publicCheckOuter = s;
    }

    public static final void setPublicCheckOuterGroup(String s)
    {
        publicCheckOuterGroup = s;
    }

    public static final void setPublicCheckOuterId(String s)
    {
        publicCheckOuterID = s;
    }

    public static final void setPublicApproveDate(String s)
    {
        publicApproveDate = s;
    }

    public static final void setPublicApproveDept(String s)
    {
        publicApproveDept = s;
    }

    public static final void setPublicApproveId(String s)
    {
        publicApproveID = s;
    }

    public static final void setPublicApproveName(String s)
    {
        publicApproveName = s;
    }

    public static final void setLastApprover(String str)
    {
        isLastApprover = str;
    }

	public static final void setBomValidationResult(boolean b) 
	{
		bomValidationResult = b;
	}

	public static final void setHasErrorInValidation(boolean b)
	{
		hasError = b;
	}

    public static final void setValidationForEnd(String s)
    {
    	validationForEnd = s;
    }

	public static final void setBomEcoNumber(String s)
	{
		bomEcoNumber = s;
	}

	public static final void setBomEcoType(String s)
	{
		bomEcoType = s;
	}

    public static final void setECONo(String s)
    {
    	publicECONo = s;
    }

    public static final void setBomEcoReason(String s)
    {
    	bomEcoReason = s;
    }

	public static final void setBomEcoReasonDetails(String s)
	{
		bomEcoReasonDetails = s;
	}

	public static final void setBomEcoRelatedEcrNo(String s)
	{
		bomEcoRelatedEcrNo = s;
	}

    public static final void setBomEcoTitle(String s)
    {
    	bomEcoTitle = s;
    }

    public static final void setBomEcoDescription(String s)
    {
    	bomEcoDescription = s;
    }
    
    public static final void setBomHeaderPartType(String s)
    {
    	bomHeaderPartType = s;
    }
    
    public static final void setBomBoxQuantity(String s)
    {
    	bomBoxQuantity = s;
    }

	public static final void setBomSearchGubun(String s)
	{
		bomSearchGubun = s;
	}

	public static final void setBomComponent(BOMAssyComponent c)
	{
		bomComponent = c;
	}
	
	public static final void setErrorFrame(boolean s)
	{
		errorFrame = s;
	}
	

    public static final void setInitialize()
    {
		publicTotalDataCount = 0;
		publicModelName = "Empty";
		publicBOMStatus = "";
		publicBOMStatusKr = "";
		publicBomOid = null;
		workItemOid = null;
		publicMyStatus = "";
		coWorkerVec = new Vector();

		bomValidationResult = false;
		hasError = false;
		validationForEnd = "";

		bomEcoNumber = "";
		bomEcoType = "";
		bomHeaderPartType = "";
		bomBoxQuantity = "1";
    }

}
