package e3ps.edm.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

import wt.util.WTProperties;
import e3ps.common.util.CharUtil;
import e3ps.edm.CADAppType;
import e3ps.edm.CADCategory;
import e3ps.edm.CADManageType;
import e3ps.edm.DevStage;
import ext.ket.shared.log.Kogger;

public class EDMProperties {

	private static EDMProperties INSTANCE = null;
	private static Properties properties = null;

	public static String SEPARATOR = File.separator;

	/*
	static
	{
		InputStream is = null;
	    try {
	    	WTContext context = WTContext.getContext();
	    	is = context.getResourceAsStream("e3ps"+SEPARATOR+"edm.properties");
	    	if (is != null) {
	    		properties.load(is);
	    	}
	    }
	    catch (Exception e) {
	    	Kogger.debug(getClass(), "EDMProperties: Error in reading edm.properties");
	    	Kogger.error(getClass(), e);
	    }

	    if (is != null) {
	    	try {
	    		is.close();
	    	}
	    	catch (Exception e) {
	    	} finally {
	    		is = null;
	    	}
	    }
	}
	*/


	private EDMProperties()
    {
        this.initialize();
    }

    private void initialize()
    {
        if (properties == null)
        {
            try
            {
            	WTProperties wtproperties = WTProperties.getLocalProperties();
    			String location = wtproperties.getProperty("wt.codebase.location");
                properties = PropertiesUtil.load(new Properties(), location+SEPARATOR+"e3ps"+SEPARATOR+"edm.properties");
            }
            catch (Exception e)
            {
                Kogger.debug(getClass(), this.getClass().getName() + ":initialize() EPMProperties - Can't initialize : "
                        + e.getMessage());
            }
        }
    }

    public synchronized static EDMProperties getInstance()
    {
        if (INSTANCE == null)
            INSTANCE = new EDMProperties();
        return INSTANCE;
    }

    public Properties getProperties()
    {
      return properties;
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

    public boolean isJavaWebStart() {
    	String value = getString("epm.loader.isJavaWebStart");
    	if(value == null) {
    		return false;
    	}
    	return value.equals(String.valueOf(true).toLowerCase());
    }

    public String[] getDefinedListCounts() {
    	String[] values = getArray("epm.searchpage.definedListCounts");
    	if(values == null) {
    		return new String[]{"10"};
    	}
    	return values;
    }

    public boolean isAppTypeByPLM(String appType) {
    	String value = getString("epm.applicationtype.plm");
    	if(value == null) {
    		return false;
    	}
    	return value.equals(appType);
    }

    /**
     * PLM에서 도면 등록 시 CAD종류별 파일 확장자 체크
     * 정의되지 않은 경우 true 값 리턴.
     * @param appType
     * @param ext
     * @return
     */
    public boolean isFileExt(String appType, String ext) {
    	String[] values = getArray("epm.cad.plm.access.ext."+appType);
    	if(values == null) {
    		return true;
    	}

		for(int i = 0; i < values.length; i++) {
			if((ext.toLowerCase()).equals(values[i])) {
				return true;
			}
		}

    	return false;
    }

    public boolean isPrefixNrByPart(String category) {
    	String[] values = getArray("epm.numbering.prefix.partnumber.category");
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
     * 금형도면인 경우.
     * CAD 종류에  따라 suffix 정의된 값 가져오기.
     * @param cadAppType
     * @return
     */
    public String getDefedNumberSuffix(String cadAppType) {
    	String value = getString("epm.numbering.suffix.cadapptype."+cadAppType);
    	if(value != null) {
    		return value;
    	}

    	return "";
    }

    /**
     * Part Number를 도면 번호로 변환 ...
     * @param number : 부품 번호!
     * @param category
     * @param cadAppType
     * @return
     */
    public String getConvertedNumber(String number, String category, String cadAppType) {

    	String delimiter = getString("epm.numbering.delimiter");

    	if(isPrefixNrByPart(category)) {
    		if(cadAppType == null) {
    			cadAppType = "";
    		}

    		String subfix = getDefedNumberSuffix(cadAppType);
    		if(subfix.trim().length() > 0) {
    			subfix = ((delimiter==null)? "":delimiter) + subfix;
    		}
    		return number + subfix;
    	}

    	if(isNumberSyncToNr(category)) {
    		return number;
    	}

    	if(isKeyInCatsToNrField(category)) {
    		return number;
    	}

    	String cnvNumber = convertPrefixNumber(number, category);
    	if(cnvNumber == null) {
    		return null;
    	}

    	String prefix = "";
    	String value = getString("edm.numbering.prefix."+category);
    	if(value != null) {
    		prefix = value;
    		if(prefix.trim().length() > 0) {
    			prefix += ((delimiter==null)? "":delimiter);
    		}
    	}
    	return prefix + cnvNumber;
    }

    public String convertPrefixNumber(String number, String before, String after) {
    	if(!before.equals(number.substring(0, before.length()))) {
    		return null;
    	}
    	return after + number.substring(before.length());
    }

    public String convertPrefixNumber(String number, String category) {
    	String[] values = null;
    	if(isCADCatsByEcad(category)) {//if("ECAD_DRAWING".equals(category)) {
    		values = getArray("edm.numbering.prefix.rule.ecad");
    	} else {
    		values = getArray("edm.numbering.prefix.rule.default");
    	}

    	if(values == null) {
    		return number;
    	}

    	if(values != null) {
    		StringTokenizer st = null;
			for(int i = 0; i < values.length; i++) {
				st = new StringTokenizer(values[i],",");
				String before = st.nextToken();
				String after = st.nextToken();

				if(before.equals(number.substring(0, before.length()))) {
					return after + number.substring(before.length());
				}
			}
		}
    	return null;
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

    public ArrayList getCADCatsSet(String type, String stage, boolean isBatch) {
    	ArrayList result = new ArrayList();

    	HashMap hm0 = new HashMap();

    	String[] values0 = getArray("epm.cad.category."+type);
    	if(values0 != null) {
    		for(int i = 0; i < values0.length; i++) {
    			hm0.put(values0[i],"");
    		}
    	}

    	String[] values = null;
    	if(isBatch) {
    		values = getArray("epm.cad.devstage.batch."+stage);
    	} else {
    		values = getArray("epm.cad.devstage."+stage);
    	}

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
     * 도면 유형 중 부품이 필수 입력 항목인 유형's 정의.
     * @param category
     * @return
     */
    public CADCategory[] getEssentialPartCats() {
    	CADCategory[] ccs = null;
    	String[] values = getArray("epm.category.part.essential");
    	if(values != null) {
    		ccs = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			ccs[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}

    	return ccs;
    }

    public boolean isEssentialPartCat(String category) {
    	CADCategory[] ccs = getEssentialPartCats();

    	if(ccs != null) {
    		for(int i = 0; i < ccs.length; i++) {
    			if( category.equals(ccs[i].toString()) ) {
    				return true;
    			}
    		}
    	}
    	return false;
    }


    /**
     * Dummy 파일을 허용하는 도면유형 return
     * @return
     */
    public CADCategory[] getCADCatsByDummyFile() {
    	CADCategory[] ccs = null;
    	String[] values = getArray("epm.cad.category.dummyfile");
    	if(values != null) {
    		ccs = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			ccs[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	return ccs;
    }

    public boolean isCADCatsByDummyFile(String category) {
    	String[] values = getArray("epm.cad.category.dummyfile");
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
     * 도면유형 중 ECAD 유형 정의.
     * @return
     */
    public CADCategory[] getCADCatsByEcad() {
    	CADCategory[] ccs = null;
    	String[] values = getArray("epm.cad.category.ecad");
    	if(values != null) {
    		ccs = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			ccs[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}
    	return ccs;
    }

    public CADManageType getManageType(String category) {
    	CADManageType cmt[] = CADManageType.getCADManageTypeSet();
    	for(int i = 0; i < cmt.length; i++) {
    		String[] values = getArray("epm.cad.category."+cmt[i].toString());
    		if(values != null) {
    			for(int k = 0; k < values.length; k++) {
    				if(category.equals(values[k])) {
    					return cmt[i];
    				}
    			}
    		}
    	}
    	return null;
    }

    public boolean isCADCatsByEcad(String category) {
    	String[] values = getArray("epm.cad.category.ecad");
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
     * 부품을 기준으로 일품일도인지 일품다도인지 여부 정의
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

    public boolean isOnlyRefedByCat(String category) {
    	String value = getReferencedByCat(category);
    	if(value != null) {
    		return "1".equals(value);
    	}
    	return false;
    }

    /**
     * 도면을 기준으로 일도일품인지 일도다품인지 여부 정의
     * @param category
     * @return
     */
    public String getReferencesCat(String category) {
    	String value = getString("epm.category.part.references."+category);
    	if(value != null) {
    		return value;
    	}
    	return null;
    }

    public boolean isOnlyRefesCat(String category) {
    	String value = getReferencesCat(category);
    	if(value != null) {
    		return "1".equals(value);
    	}
    	return false;
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
     * 부품채번관리에서 도면번호를 관리하는 Category.
     * 개발검토도면.
     * @return
     */
    public CADCategory[] getCatByNumberingSystem() {
    	CADCategory catArr[] = null;
    	String[] values = getArray("epm.category.number.numberingsystem");
    	if(values != null) {
    		catArr = new CADCategory[values.length];
    		for(int i = 0; i < values.length; i++) {
    			catArr[i] = CADCategory.toCADCategory(values[i]);
    		}
    	}

    	return catArr;
    }

    public boolean isCatByNumberingSystem(String category) {
    	CADCategory catArr[] = getCatByNumberingSystem();
    	if(catArr != null) {
    		for(int i = 0; i < catArr.length; i++) {
    			if(category.equals(catArr[i].toString())) {
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
    	String[] values = getArray("epm.category.name.partname");
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

    public String getBizCodeType() {
    	String value = getString("epm.biz.codetype");
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


    public String getFullPath(String bizType, String manageType, String category, boolean iswgm) {
    	String path = getEPMDefaultFolderPath();
    	path += "/" + bizType;
    	path += getSubPath(manageType,iswgm);
    	path += "/"+ getEndLeafFolderName(category,iswgm);//path += "/"+ category;
    	return path;
    }

    /**
     * 도면의 end leaf 폴더 명 return
     * @param category
     * @param iswgm
     * @return
     */
    public String getEndLeafFolderName(String category, boolean iswgm) {
    	String value = getString("epm.folder.category"+((iswgm==true)? ".wgm":".2d")+"."+category);
    	if( (value != null) && (value.trim().length() > 0) ) {
    		return CADCategory.toCADCategory(value).getDisplay(Locale.KOREAN);
    	}
    	return CADCategory.toCADCategory(category).getDisplay(Locale.KOREAN);
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
        String value = properties.getProperty(key);
        if (value == null)
            return null;
        else
            value = CharUtil.E2K(value);
        return value;
    }

    public static synchronized String toKor(String english) {
		if (english == null) { return ""; }
		String korean = null;
		try {
			korean = new String ( english.getBytes ( "ISO-8859-1" ) , "UTF-8" );
			//korean = new String ( korean.getBytes ( "ISO-8859-1" ) , "KSC5601" );
		} catch (UnsupportedEncodingException e) {
			korean = new String ( english );
		}
		return korean;
	}

    public String getString(String _key, String _default) {
        String value = properties.getProperty(_key);
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

    public static void main(String args[]) throws Exception {
    	/*
    	Kogger.debug(getClass(), "#######################");

    	EDMProperties edmProp = EDMProperties.getInstance();
    	if(edmProp == null) {
    		Kogger.debug(getClass(), "# is null");
    	}
    	Kogger.debug(getClass(), edmProp.getContainer());
    	Kogger.debug(getClass(), edmProp.getSubPath("PRODUCT_DRAWING", false));

    	Kogger.debug(getClass(), "container = " + edmProp.getString("edm.container"));
    	Kogger.debug(getClass(), "container1 = " + edmProp.getString("edm.container1"));
    	*/
    	/*
    	Iterator itr = edmProp.getProperties().keySet().iterator();

        while(itr.hasNext()) {
        	Kogger.debug(getClass(), "key : " + (String)itr.next());
        }
        */
        /*
        String fileName = "D:"+separator+
        				"ptc"+separator+
        				"Windchill_9.1"+separator+
        				"Windchill"+separator+
        				"codebase"+separator+
        				"e3ps"+separator+
        				"edm.properties";
        Properties prop = new Properties();
        prop.load(new FileInputStream(new File(fileName)));
        Iterator itr0 = prop.keySet().iterator();

        while(itr0.hasNext()) {
        	Kogger.debug(getClass(), "key : " + (String)itr0.next());
        }

        Kogger.debug(getClass(), "value : " + prop.getProperty("epm.folder.subpath.product_drawing.2d"));
        Kogger.debug(getClass(), "value to kor : " + toKor(prop.getProperty("epm.folder.subpath.product_drawing.2d")));
        */
    }
}
