package e3ps.ecm.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;

import wt.util.WTProperties;
import e3ps.common.util.CharUtil;
import e3ps.edm.CADAppType;
import e3ps.edm.CADCategory;
import e3ps.edm.DevStage;
import e3ps.edm.util.PropertiesUtil;
import ext.ket.shared.log.Kogger;

public class ECMProperties {
	
	private static ECMProperties INSTANCE = null;
	private static Properties DEFAULT = null;
	
	
	private ECMProperties()
    {
        this.initialize();
    }
	
    private void initialize()
    {
        if (ECMProperties.DEFAULT == null)
        {
            try
            {   
            	WTProperties wtproperties = WTProperties.getLocalProperties();
    			String location = wtproperties.getProperty("wt.codebase.location");
    			
                ECMProperties.DEFAULT = PropertiesUtil.load(new Properties(), location+File.separator+"e3ps"+File.separator+"ecm.properties");
                
            }
            catch (Exception e)
            {
                //Kogger.error(getClass(), this.getClass().getName() + ":initialize() EPMProperties - Can't initialize : " + e.getMessage());
        	Kogger.error(getClass(), "initialize()", null, null, ":initialize() EPMProperties - Can't initialize : " + e.getMessage(), e);
            }
        }
    }
    
    public synchronized static ECMProperties getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new ECMProperties();
        return INSTANCE;
    }
    
    public int getMaxLength(String attribute) {
    	String value = getString("epm."+attribute.toLowerCase()+".maxlength.default");
    	if(value != null) {
    		return Integer.parseInt(value);
    	}
    	return 0;
    }
    
    public int getMaxLenForNumber() {
    	return getMaxLength("number");
    }
    
    public int getMaxLenForName() {
    	return getMaxLength("name");
    }
    
    public int getMaxLenForCADName() {
    	return getMaxLength("cadname");
    }
    
    public int getMaxLenForDescription() {
    	return getMaxLength("description");
    }
    
    public String getAppTypeByPLM() {
    	String value = getString("epm.applicationtype.plm");
    	if(value == null) {
    		return "";
    	}
    	return value;
    }
    
    public boolean isAppTypeByPLM(String appType) {
    	String value = getString("epm.applicationtype.plm");
    	if(value == null) {
    		return false;
    	}
    	return value.equals(appType);
    }
    
    public String getConvertedNumber(String number, String category, String cadAppType) {
    	
    	if(isKeyInCatsToNrField(category)) {
    		return number;
    	}
    	
    	if(isNumberSyncToNr(category)) {
    		return number;
    	}
    	
    	String[] values = null;
    	if("ECAD_DRAWING".equals(category)) {
    		values = getArray("edm.numbering.prefix.rule.ecad");
    	} else {
    		values = getArray("edm.numbering.prefix.rule.default");
    	}
    	
    	String prefix = "";
    	String value = getString("edm.numbering.prefix."+category);
    	if(value != null) {
    		prefix = value;
    	}
    	
    	if(values != null) {
    		StringTokenizer st = null;
			for(int i = 0; i < values.length; i++) {
				st = new StringTokenizer(values[i],",");
				String before = st.nextToken();
				String after = st.nextToken();
				
				if(before.equals(number.substring(0, before.length()))) {
					return prefix + after + number.substring(before.length());
				}
			}
			
			return null;
		}
    	
    	return number;
    }
    
    /**
     * 제품/금형 도면에 따른 도면유형 return
     * @param type
     * @return
     */
    public CADCategory[] getCADCategorySet(String type) {
    	CADCategory[] cats = null;
    	
    	String[] values = getArray("epm.cad.category."+type);
    	if(values != null) {
    		cats = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			cats[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	
    	return cats;
    }
    
    /**
     * 제품/금형 도면에 따른 도면구분 return
     * @param type
     * @return
     */
    public DevStage[] getDevStageSet(String type) {
    	DevStage dss[] = null;
    	
    	String[] values = getArray("epm.cad.stage."+type);
    	if(values != null) {
    		dss = new DevStage[values.length];
    		for(int i = 0; i < values.length; i++) {
    			dss[i] = DevStage.toDevStage(values[i]);
    		}
    	}
    	if(dss == null) {
    		dss = new DevStage[0];
    	}
    	return dss;
    }
    
    /**
     * 도면구분에 따른 도면유형's 정의
     * @param type
     * @return
     */
    public CADCategory[] getCADCatsSetByStage(String stage) {
    	CADCategory cats[] = null;
    	
    	String[] values = getArray("epm.cad.devstage."+stage);
    	if(values != null) {
    		cats = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			cats[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	
    	return cats;
    }
    
    /**
     * 도면종류,도면구분에 따른 도면유형's 정의
     * @param type, stage
     * @return
     */
    public ArrayList getCADCatsSet(String type, String stage) {
    	ArrayList result = new ArrayList();
    	
    	HashMap hm0 = new HashMap();
    	
    	String[] values0 = getArray("epm.cad.category."+type);
    	if(values0 != null) {
    		for(int i = 0; i < values0.length; i++) {
    			hm0.put(values0[i],"");
    		}
    	}
    	
    	String[] values = getArray("epm.cad.devstage."+stage);
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(!hm0.containsKey(values[i])) {
    				continue;
    			}
    			result.add(CADCategory.toCADCategory(values[i]));
    		}
    	}
    	
    	return result;
    }
    
    
    
    
    /**
     * 도면유형에 따른 CAD 종류 return
     * @param category
     * @return
     */
    public CADAppType[] getCADAppTypeSet(String category) {
    	CADAppType[] cats = null;
    	String[] values = getArray("epm.category.cads."+category);
    	if(values != null) {
    		cats = new CADAppType[values.length];
    		for(int i = 0; i < values.length; i++) {
    			cats[i] =  CADAppType.toCADAppType(values[i]);
    		}
    	}
    	
    	if(cats == null) {
    		cats = new CADAppType[0];
    	}
    	
    	return cats;
    }
    
    /**
     * 부품과 연관 관계를 갖지 않는 도면유형 return 
     * @return
     */
    public CADCategory[] getCADCatsByNonPart() {
    	CADCategory[] ccs = null;
    	String[] values = getArray("epm.category.nonpart");
    	if(values != null) {
    		ccs = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			ccs[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	
    	return ccs;
    }
    
    /**
     * 부품과 연관 관계를 갖지 않는 도면유형 return 
     * @return
     */
    public boolean isNonPart(String category) {
    	CADCategory[] ccs = null;
    	String[] values = getArray("epm.category.nonpart");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    /**
     * 도면과 부품의 연관관계 속성값 정의
     */
    public String getReferenceType(String category) {
    	String value = getString("epm.cad.category.part.referencetype."+category);
    	if(value == null) {
    		return getString("epm.cad.category.part.referencetype.default");
    	}
    	return value;
    }
    
    public String getReferenceTypeForModel(String p) {
    	String value = getString("epm.cad.category.part.referencetype.partmodel");
    	if(value == null) {
    		return null;
    	}
    	return value;
    }
    
    /**
     * 도면유형에 따른 부품참조유형 return
     * 'NONE' 인 경우 부품과 연관 관계가 없음.
     * @param category
     * @return	
     */
    public String getReferencedByCat(String category) {
    	String value = getString("epm.category.cad.referencedBy."+category);
    	if(value != null) {
    		return value;
    	}
    	return null;
    }
    
    public String getReferencesCat(String category) {
    	String value = getString("epm.category.part.references."+category);
    	if(value != null) {
    		return value;
    	}
    	return null;
    }
    
    /**
     * 도면유형 중 부품 참조 유형이 '다품일도'인 유형인지 여부 ...  
     * @param category
     * @return
     */
    public boolean isVariousPartRefs(String category) {
    	HashMap m = new HashMap();
    	
    	CADCategory catArr[] = CADCategory.getCADCategorySet();
    	for(int i = 0; i < catArr.length; i++) {
    		String value = getReferencesCat(catArr[i].toString());
    		if(value != null) {
    			m.put(catArr[i].toString(), value);
    		}
    	}
    	
    	String v = (String)m.get(category);
    	if(v == null) {
    		return false;
    	}
    	
    	return "N".equals(v);
    }
    
    /**
     * 도면유형 중 모델 참조가 가능한 유형 인지 여부 ...
     * @param category
     * @return
     */
    public boolean isRefModel(String category) {
    	if( (category == null) || (category.trim().length() == 0) ) {
    		return false;
    	}
    	
    	String[] values = getArray("epm.cad.model.reference.enable");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i].toString())) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    /**
     * 도면유형 중 모델 참조가 가능한 유형 return
     * @param category
     * @return
     */
    public CADCategory[] getCategoryRefModel() {
    	CADCategory catArr[] = null;
    	String[] values = getArray("epm.cad.model.reference.enable");
    	if(values != null) {
    		catArr = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			catArr[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	
    	return catArr;
    }
    
    public String[] getAttributeDefSync() {
    	String[] values = getArray("epm.model.sync.attributes");
    	if(values == null) {
    		values = new String[0];
    	}
    	return values;
    }
    
    
    public boolean isAuthoringAppTypeByExtension(String cadAppType) {
    	String[] values = getArray("epm.cad.EPMAuthoringAppType.byExtension");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(cadAppType.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    public String getAuthoringAppTypeByEcadPcb() {
    	String value = getString("epm.cad.EPMAuthoringAppType.ecad.pcb");
    	if(value == null) {
    		return "";
    	}
    	return value;
    }
    
    public String getAuthoringAppTypeByEcadSch() {
    	String value = getString("epm.cad.EPMAuthoringAppType.ecad.sch");
    	if(value == null) {
    		return "";
    	}
    	return value;
    }
    
    public String getAuthoringAppTypeByEcadAcad() {
    	String value = getString("epm.cad.EPMAuthoringAppType.ecad.autocad");
    	if(value == null) {
    		return "";
    	}
    	return value;
    }
    
    /**
     * 제품도면 등록화면에서 도면번호를 사용자 입력 도면유형
     * @param category
     * @return
     */
    public CADCategory[] getKeyInCatsToNrField() {
    	CADCategory catArr[] = null;
    	String[] values = getArray("epm.category.number.keyin");
    	if(values != null) {
    		catArr = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			catArr[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	
    	return catArr;
    } 
    
    public boolean isKeyInCatsToNrField(String category) {
    	String[] values = getArray("epm.category.number.keyin");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    } 
    
    
    /**
     * 제품도면 등록화면에서 도면명을 사용자 입력 도면유형
     * @param category
     * @return
     */
    public CADCategory[] getKeyInCatsToNmField() {
    	CADCategory catArr[] = null;
    	String[] values = getArray("epm.category.name.keyin");
    	if(values != null) {
    		catArr = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			catArr[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	
    	return catArr;
    } 
    
    public boolean isKeyInCatsToNmField(String category) {
    	String[] values = getArray("epm.category.name.keyin");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    } 
    
    public boolean isNumberSyncToNr(String category) {
    	String[] values = getArray("epm.category.number.partnumber");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    public boolean isNameSyncToNm(String category) {
    	String[] values = getArray("epm.category.number.partname");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }

    public String getContainer() {
    	String value = getString("edm.container");
    	if(value != null) {
    		return value;
    	}
    	return "";
    }
    
    public String getEPMDefaultLC() {
    	String value = getString("epm.lifecycle.default");
    	if(value != null) {
    		return value;
    	}
    	return "";
    }
    
    public String getEPMDefaultFolderPath() {
    	String value = getString("epm.folder.default");
    	if(value != null) {
    		return value;
    	}
    	return "";
    }
    
    public String getSubPath(String manageType, boolean isWGM) {
    	String value = getString("epm.folder.subpath."+manageType.toLowerCase()+((isWGM==true)? ".wgm":".2d"));
    	if(value != null) {
    		return value;
    	}
    	return "";
    }
    
    public String getFullPath(String bizType, String manageType, String category, boolean isWGM) {
    	String path = getEPMDefaultFolderPath();
    	path += "/" + bizType;
    	path += getSubPath(manageType,isWGM);
    	path += "/"+ category;
    	return path;
    }
    
    public boolean isStateSyncByPart(String category) {
    	String[] values = getArray("epm.state.sync.part");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
	
    public boolean isStateSyncByModel(String category) {
    	String[] values = getArray("epm.state.sync.model");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(category.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public boolean isReleasedState(String state) {
    	String[] values = getArray("epm.state.released");
    	if(values != null) {
    		for(int i = 0; i < values.length; i++) {
    			if(state.equals(values[i])) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    public String getString(String key)
    {
        String value = DEFAULT.getProperty(key);
        if (value == null)
            return null;
        else
            value = CharUtil.E2K(value);
        return value;
    }
    
    public String getString(String _key, String _default) {
        String value = DEFAULT.getProperty(_key);
        return value == null ? _default : value;
    }
    
    public String[] getArray(String key)
    {
        String value = getString(key);
        if (value == null)
            return null;
        StringTokenizer st = new StringTokenizer(value, ";");
        String[] result = new String[st.countTokens()];

        int idx = 0;
        while (st.hasMoreElements())
        {
            result[idx++] = st.nextToken();
        }

        return result;
    }
}
