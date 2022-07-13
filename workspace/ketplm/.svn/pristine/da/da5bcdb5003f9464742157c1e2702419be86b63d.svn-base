package e3ps.edm.util;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.iba.definition.StringDefinition;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public final class IBAStringDefinitionCache {

    // CAD IBA
    private String cadAppType;
    private String isDummyFile;
    private String devStage;
    private String cadCategory;
    private String cadManageType;
    private String manufacturingVersion;

    private volatile static IBAStringDefinitionCache instance;

    private IBAStringDefinitionCache() {
	initialize();
    }

    public static IBAStringDefinitionCache getInstance() {
	if (instance == null) {
	    synchronized (IBAStringDefinitionCache.class) {
		if (instance == null) {
		    instance = new IBAStringDefinitionCache();
		}
	    }
	}
	return instance;
    }

    private void initialize() {

	try {

	    QuerySpec qs = new QuerySpec(StringDefinition.class);
	    qs.appendWhere(new SearchCondition(StringDefinition.class, StringDefinition.NAME, SearchCondition.NOT_LIKE, "PTC%"),
		    new int[] { 0 });
	    qs.appendAnd();
	    qs.appendWhere(new SearchCondition(StringDefinition.class, StringDefinition.NAME, SearchCondition.NOT_LIKE, "com%"),
		    new int[] { 0 });

	    QueryResult qr = PersistenceHelper.manager.find(qs);
	    while (qr != null && qr.hasMoreElements()) {
		StringDefinition stringDef = (StringDefinition) qr.nextElement();
		String name = stringDef.getName();
		// ('CADAppType','IsDummyFile','DevStage','CADCategory','CADManageType','ManufacturingVersion')
		if ("CADAppType".equalsIgnoreCase(name)) {
		    cadAppType = CommonUtil.getOIDLongValue2Str(stringDef);
		} else if ("DevStage".equalsIgnoreCase(name)) {
		    devStage = CommonUtil.getOIDLongValue2Str(stringDef);
		} else if ("CADCategory".equalsIgnoreCase(name)) {
		    cadCategory = CommonUtil.getOIDLongValue2Str(stringDef);
		} else if ("IsDummyFile".equalsIgnoreCase(name)) {
		    isDummyFile = CommonUtil.getOIDLongValue2Str(stringDef);
		} else if ("CADManageType".equalsIgnoreCase(name)) {
		    cadManageType = CommonUtil.getOIDLongValue2Str(stringDef);
		} else if ("ManufacturingVersion".equalsIgnoreCase(name)) {
		    manufacturingVersion = CommonUtil.getOIDLongValue2Str(stringDef);
		} 
	    }

	} catch (WTException e) {
	    Kogger.error(getClass(), e);
	}
    }

    public String getCadAppType() {
	return cadAppType;
    }

    public void setCadAppType(String cadAppType) {
	this.cadAppType = cadAppType;
    }

    public String getIsDummyFile() {
	return isDummyFile;
    }

    public void setIsDummyFile(String isDummyFile) {
	this.isDummyFile = isDummyFile;
    }

    public String getDevStage() {
	return devStage;
    }

    public void setDevStage(String devStage) {
	this.devStage = devStage;
    }

    public String getCadCategory() {
	return cadCategory;
    }

    public void setCadCategory(String cadCategory) {
	this.cadCategory = cadCategory;
    }

    public String getCadManageType() {
	return cadManageType;
    }

    public void setCadManageType(String cadManageType) {
	this.cadManageType = cadManageType;
    }

    public String getManufacturingVersion() {
	return manufacturingVersion;
    }

    public void setManufacturingVersion(String manufacturingVersion) {
	this.manufacturingVersion = manufacturingVersion;
    }
    
    public static void setInstance(IBAStringDefinitionCache instance) {
	IBAStringDefinitionCache.instance = instance;
    }

}
