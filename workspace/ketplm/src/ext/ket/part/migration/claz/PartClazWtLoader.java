package ext.ket.part.migration.claz;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.method.RemoteMethodServer;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionContext;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.classify.util.PartClassificType;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassTreeLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.spec.service.PartSpecHelper;
import ext.ket.shared.log.Kogger;

public class PartClazWtLoader implements RemoteAccess, Serializable {

    static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    /**
     * 주요 변수
     */
    static final String EMPTY = "";
    public static PartClazWtLoader manager = new PartClazWtLoader();

    public PartClazWtLoader() {

    }
    // windchill ext.ket.part.migration.claz.PartClazWtLoader Z:\\90.개인폴더\\이응진\\부품사양관리\\업로드포멧\\업로드작업\\제품분류_V1.1_MigrationData.xlsx
    // windchill ext.ket.part.migration.claz.PartClazWtLoader D:\MigrationData.xlsx
    public static void main(String[] args) {

	try {

	    String filePath = null;

	    if (args == null || args.length == 0)
		throw new Exception("@@ args need !");
	    else
		filePath = args[0];

	    Kogger.debug(PartClazWtLoader.class, "@start");
	    PartClazWtLoader.manager.saveFromExcel(filePath);
	    Kogger.debug(PartClazWtLoader.class, "@end");

	} catch (Exception e) {
	    Kogger.debug(PartClazWtLoader.class, e);
	}
    }

    public void saveFromExcel(String filePath) throws Exception {

	if (!SERVER) {
	    try {

		Class aclass[] = { String.class };
		Object aobj[] = { filePath };

		Kogger.debug(getClass(), "@		start");
		RemoteMethodServer.getDefault().invoke("saveFromExcel", null, this, aclass, aobj);
		Kogger.debug(getClass(), "@		end");

		return;

	    } catch (RemoteException e) {
		Kogger.debug(getClass(), e);
	    } catch (InvocationTargetException e) {
		Kogger.debug(getClass(), e);
	    } catch (Exception e) {
		Kogger.debug(getClass(), e);
	    }
	} else {
	    executeMigration(filePath);
	}
    }

    public void executeMigration(String filePath) throws WTException {

	SessionContext sessioncontext = SessionContext.newContext();

	Transaction trx = null;
	try {

	    trx = new Transaction();
	    trx.start();

	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract Start **************************");

	    SessionHelper.manager.setAdministrator();
	    
	    PartClazLoader loader = new PartClazLoader();
	    loader.load(filePath);
	    
	    List<MigPartClazDTO> list = loader.getList();
	    Map<String, MigPartClazDTO> map = loader.getMap();
	    List<MigPartClazPropLinkDTO> plist = loader.getPList();
	    
	    ////////////////////////////////////////////////////
	    // 분류체계 등록
	    ////////////////////////////////////////////////////
	    // 모두 삭제
//	    deleteKetPartAttrLink();
//	    deleteKetPartClazInnerLink();
//	    deleteKetPartClaz();
	    
	    Map<String, String> clazMap = new HashMap<String, String>();
	    Map<String, String> attrMap = new HashMap<String, String>();
	    
	    // root 등록
	    KETPartClassification root = new PartClazBuilder.Builder("부품분류체계").setClassEnName("Part Classification").setClassZhName("部品分类体系").setUseClaz(true).setSortNo(10)
		        .setPartClassificType(PartClassificType.NONE.getCode()).setClassCode("0").build();
	    //root = PartClazHelper.service.insertMigration(root, null);
	    root = (KETPartClassification) PersistenceHelper.manager.save(root);
	    
	    // 분류등록 excel 등록
	    for( MigPartClazDTO paramObject : list ){
		
		int level = paramObject.getLevel();
		KETPartClassification partClaz = KETPartClassification.newKETPartClassification();
		partClaz.setClassCode(paramObject.getClassNo());
		partClaz.setClassKrName(paramObject.getClassKrName());
		partClaz.setClassEnName(paramObject.getClassEnName());
		partClaz.setClassZhName(paramObject.getClassZhName());
		partClaz.setNumberingCode(paramObject.getNumberingCode());
		
		// 채번 특성 
		String numberingCharics = paramObject.getNumberingCharics();
		if(numberingCharics == null || numberingCharics.equals("")){
		    partClaz.setNumberingCharics("");
		}else if( numberingCharics.indexOf("칼라") != -1 || numberingCharics.indexOf("컬러") != -1 ){
		    partClaz.setNumberingCharics("C");
		}else if( numberingCharics.indexOf("도금") != -1 ){
		    partClaz.setNumberingCharics("P");
		}else if( numberingCharics.indexOf("핀수") != -1 ){
		    partClaz.setNumberingCharics("S");
		}else if( numberingCharics.indexOf("N/A") != -1 ){
		    partClaz.setNumberingCharics("");
		}   
		 
		partClaz.setSortNo(Integer.parseInt(paramObject.getSortNo()));
		partClaz.setUseClaz("1".equals(paramObject.getUseClaz()));
		partClaz.setErpProdCode(paramObject.getErpProdCode());
		partClaz.setErpClassNo(paramObject.getErpClassCode());
		partClaz.setClassPartType(paramObject.getClassPartType());
		partClaz.setEpmCode(paramObject.getEpmCode());
		partClaz.setErpProdGroupCode(paramObject.getErpProdGroupCode());
		String partClassificType = StringUtil.checkNull(paramObject.getPartClassificType());
		if(partClassificType.indexOf("Assy") != -1){
		    partClaz.setPartClassificType("A");
		}else if(partClassificType.indexOf("단품") != -1){
		    partClaz.setPartClassificType("C");
		}else if(partClassificType.indexOf("구매품") != -1){
		    partClaz.setPartClassificType("P");
		}else if(partClassificType.indexOf("Mold") != -1){
		    partClaz.setPartClassificType("M");
		}else if(partClassificType.indexOf("Press") != -1){
		    partClaz.setPartClassificType("S");
		}else{
		    partClaz.setPartClassificType(partClassificType);
		}
		partClaz.setCatalogueCode(paramObject.getCatalogueCode());
		if("전자".equals(paramObject.getDivisionName())){
		    partClaz.setDivisionFlag("E");
		}else if("자동차".equals(paramObject.getDivisionName())){
		    partClaz.setDivisionFlag("C");
		}else{
		    partClaz.setDivisionFlag("");
		}
		
		if(level == 1){
		    //partClaz = PartClazHelper.service.insertMigration(partClaz, root);
		    partClaz = (KETPartClassification) PersistenceHelper.manager.save(partClaz);
		    KETPartClassTreeLink partClassTreeLink = KETPartClassTreeLink.newKETPartClassTreeLink(root, partClaz);
		    partClassTreeLink = (KETPartClassTreeLink) PersistenceHelper.manager.save(partClassTreeLink);
		    partClaz = (KETPartClassification) PersistenceHelper.manager.refresh(partClaz);
		    
		    Kogger.debug(getClass(),  partClaz.getClassKrName() + " clazOid:" + PersistenceHelper.getObjectIdentifier(partClaz).getStringValue() );
		    
		    clazMap.put(paramObject.getKeyCode(), CommonUtil.getOIDString(partClaz));
		    
		}else{
		    
		    KETPartClassification parentClaz = (KETPartClassification)CommonUtil.getObject(clazMap.get(paramObject.getParentKeyCode()));
		    //partClaz = PartClazHelper.service.insertMigration(partClaz, parentClaz);
		    partClaz = (KETPartClassification) PersistenceHelper.manager.save(partClaz);
		    KETPartClassTreeLink partClassTreeLink = KETPartClassTreeLink.newKETPartClassTreeLink(parentClaz, partClaz);
		    partClassTreeLink = (KETPartClassTreeLink) PersistenceHelper.manager.save(partClassTreeLink);
		    
		    Kogger.debug(getClass(),  partClaz.getClassKrName() + " clazOid:" + PersistenceHelper.getObjectIdentifier(partClaz).getStringValue() );
		    
		    clazMap.put(paramObject.getKeyCode(), CommonUtil.getOIDString(partClaz));
		}
	    }
	    
	    ////////////////////////////////////////////////////
	    // 분류체계 속성 등록
	    ////////////////////////////////////////////////////
	    
	    
	    for( MigPartClazPropLinkDTO paramObject : plist ){
		
		PartClassAttrLinkDTO partClassAttrLinkDTO = new PartClassAttrLinkDTO();
		String clazOid = clazMap.get(paramObject.getKeyCode());
		partClassAttrLinkDTO.setPartClazOid(clazOid);
		
		if(attrMap.containsKey(paramObject.getAttrCode())){
		    String attrOid = attrMap.get(paramObject.getAttrCode());
		    partClassAttrLinkDTO.setPartAttrOid(attrOid);
		}else{
		    String attrOid = getAttrProps(paramObject.getAttrCode());
		    attrMap.put(paramObject.getAttrCode(), attrOid);
		    partClassAttrLinkDTO.setPartAttrOid(attrOid);
		}
		
		if("spYazakiNo".equals(paramObject.getAttrCode())){
		    partClassAttrLinkDTO.setEssential(false);
		    partClassAttrLinkDTO.setChecked(false);
		}else{
		    partClassAttrLinkDTO.setEssential("O".equals(paramObject.getEsse()));
		    partClassAttrLinkDTO.setChecked("O".equals(paramObject.getCheck()));
		}
		partClassAttrLinkDTO.setIndexRow(Integer.parseInt(paramObject.getRow()));
		partClassAttrLinkDTO.setIndexCol(Integer.parseInt(paramObject.getCol()));
		
		PartSpecHelper.service.insertPartAttrLinkList(partClassAttrLinkDTO);
	    }
	    
	    Kogger.debug(getClass(), "**************** DRAWING  Excel Extract End **************************");
	    
	    trx.commit();

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    trx.rollback();
	} finally {
	    SessionContext.setContext(sessioncontext);
	}
    }
   
    public String getAttrProps(String partAttrCode) throws Exception {
	
	QuerySpec query = new QuerySpec(KETPartAttribute.class);
	
	query.appendWhere(new SearchCondition(KETPartAttribute.class, KETPartAttribute.ATTR_CODE, SearchCondition.EQUAL, partAttrCode), new int[] { 0 });
	
	QueryResult queryResult = PersistenceHelper.manager.find(query);
	if(queryResult.hasMoreElements()){
	    KETPartAttribute obj = (KETPartAttribute)queryResult.nextElement();
	    return CommonUtil.getOIDString(obj);
	}else
	    throw new Exception("Attribute find error");
	
    }
    
    public void deleteKetPartAttrLink() throws Exception {
	
	QuerySpec query = new QuerySpec(KETPartClassAttrLink.class);
	
	QueryResult queryResult = PersistenceHelper.manager.find(query);
	while(queryResult.hasMoreElements()){
	    KETPartClassAttrLink obj = (KETPartClassAttrLink)queryResult.nextElement();
	    PersistenceHelper.manager.delete(obj);
	}
    }
    
//    public void deleteKetPartClazInnerLink() throws Exception {
//	
//	QuerySpec query = new QuerySpec(KETPartClassTreeLink.class);
//	
//	QueryResult queryResult = PersistenceHelper.manager.find(query);
//	while(queryResult.hasMoreElements()){
//	    KETPartClassTreeLink obj = (KETPartClassTreeLink)queryResult.nextElement();
//	    PersistenceHelper.manager.delete(obj);
//	}
//    }
    
    public void deleteKetPartClaz() throws Exception {
	
	QuerySpec query = new QuerySpec(KETPartClassification.class);
	
	QueryResult queryResult = PersistenceHelper.manager.find(query);
	while(queryResult.hasMoreElements()){
	    KETPartClassification obj = (KETPartClassification)queryResult.nextElement();
	    PersistenceHelper.manager.delete(obj);
	}
    }


}
