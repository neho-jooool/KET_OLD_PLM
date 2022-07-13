package e3ps.bom.command.multiplebomeco;

public class ParentItemData {
	public String strAppliedProductCode;
	public String strParentItem;
	public String strDescription;
	public String strVersion;
	public String strState;
	public String strQuantity;
	public String strUnit;
	public String strOid;

	public ParentItemData(String strAppliedProductCode,
								String strParentItem, 
								String strDescription, 
								String strVersion, 
								String strState, 
								String strQuantity, 
								String strUnit, 
								String strOid) {
		this.strAppliedProductCode = strAppliedProductCode;
		this.strParentItem = strParentItem;
		this.strDescription = strDescription;
		this.strVersion = strVersion;
		this.strState = strState;
		this.strQuantity = strQuantity;
		this.strUnit = strUnit;
		this.strOid = strOid;
	}	
}
