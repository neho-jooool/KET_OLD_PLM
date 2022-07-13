package e3ps.bom.command.mybom;

import java.util.*;

public class BOMECOData
{
	public String strItemCode;
	public String strItemDesc;
    public String strEcoNo;
    public String strTitle;	
    public String strEcoType;
    public String strReason;
	public String strCreatedBy;
	public String strCreatedDate;
	public String strState;
    public Vector vecCoworker;
	public String strOid;

    public BOMECOData(String itemCode, String itemDesc, String ecoNo, String title, String ecoType, String reason, String createdBy, String createdDate, String state, Vector coworker, String oid)
    {
    	strItemCode = itemCode;
    	strItemDesc = itemDesc;
		strEcoNo = ecoNo;
		strTitle = title;
		strEcoType = ecoType;
		strReason = reason;
		strCreatedBy = createdBy;
		strCreatedDate = createdDate;
		strState = state;
		vecCoworker = coworker;
		strOid = oid;
    }
}
