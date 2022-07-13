package ext.ket.part.spec.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.OrderBy;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.obj.ObjectUtil;
import e3ps.common.util.CommonUtil;
import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.entity.KETPartAttribute;
import ext.ket.part.entity.KETPartClassAttrLink;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.PartAttributeDTO;
import ext.ket.part.entity.dto.PartClassAttrLinkDTO;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.part.spec.service.internal.PartSpecBuilder;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryBStrategy;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;
import ext.ket.shared.util.PersistableCompareUtil;

public class StandardPartSpecService extends StandardManager implements PartSpecService {

    private static final long serialVersionUID = 1L;
    private PartSpecBuilder partSpecBuilder = new PartSpecBuilder();
    private PartClazBuilder partClazBuilder = new PartClazBuilder();
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public static StandardPartSpecService newStandardPartSpecService() throws WTException {
	StandardPartSpecService instance = new StandardPartSpecService();
	instance.initialize();
	return instance;
    }

    // Migration
    @Override
    public KETPartAttribute insert(KETPartAttribute attr, boolean existNotInsert) throws Exception {

	final String attrCode = attr.getAttrCode();
	final String columnName = attr.getColumnName();

	KETPartAttribute oldPartAttribute = query.queryForFirstByOneClass(KETPartAttribute.class, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		ClassAttribute classAttribute = new ClassAttribute(KETPartAttribute.class, KETPartAttribute.ATTR_CODE);
		SQLFunction upperAttrCode = SQLFunction.newSQLFunction(SQLFunction.UPPER, classAttribute);

		ColumnExpression columnExpression = ConstantExpression.newExpression(attrCode.toUpperCase());

		query.appendWhere(new SearchCondition(upperAttrCode, SearchCondition.EQUAL, columnExpression), new int[] { index });

		if (query.getConditionCount() > 0)
		    query.appendOr();

		query.appendWhere(new SearchCondition(class1, KETPartAttribute.COLUMN_NAME, SearchCondition.EQUAL, columnName),
		        new int[] { index });
	    }

	});

	if (oldPartAttribute == null) {
	    attr = (KETPartAttribute) PersistenceHelper.manager.save(attr);
	} else {
	    if (!existNotInsert) {
		throw new Exception("# already exists KETPartAttribute !!");
	    } else {
		partSpecBuilder.buildPers2Pers(attr, oldPartAttribute);
		PersistenceServerHelper.manager.update(oldPartAttribute);
		attr = (KETPartAttribute) PersistenceHelper.manager.refresh(oldPartAttribute);
	    }
	}
	return attr;
    }

    // 전체 쿼리 ( migration 테스트 확인 )
    @Override
    public List<KETPartAttribute> searchFullList() throws Exception {

	List<KETPartAttribute> partAttributeList = query.queryForListByOneClass(KETPartAttribute.class, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendOrderBy(new OrderBy(new ClassAttribute(class1, KETPartAttribute.COLUMN_NAME), false), new int[] { index });
	    }

	});

	return partAttributeList;
    }

    // 분류체계 부품 속성 리스트 정보가져오기 - 기등록된 정보 : 선택된 분류의 속성
    @Override
    public List<PartClassAttrLinkDTO> searchSelectedList(String clazOid) throws Exception {

	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(clazOid);
	if (claz == null)
	    throw new Exception("KETPartClassification oid can't be null!");

	List<KETPartClassAttrLink> partClassAttrLinkList = getPartClassAttrLinkList(claz);

	List<PartClassAttrLinkDTO> list = new ArrayList<PartClassAttrLinkDTO>();
	for (KETPartClassAttrLink link : partClassAttrLinkList) {
	    PartClassAttrLinkDTO dto = new PartClassAttrLinkDTO();
	    dto.setEssential(link.isEssential());
	    dto.setChecked(link.isChecked());
	    dto.setIndexRow(link.getIndexRow());
	    dto.setIndexCol(link.getIndexCol());

	    dto.setHpYn(link.isHpYn());
	    dto.setIndexSort(link.getIndexSort());

	    KETPartAttribute attr = link.getAttr();

	    PartAttributeDTO attrDTO = dto.getPartAttributeDTO();
	    partSpecBuilder.buildPers2Dto(attr, attrDTO);

	    PartClassificationDTO clazDTO = dto.getPartClassificationDTO();
	    partClazBuilder.buildPers2Dto(clazDTO, claz);

	    dto.setPartAttrOid(CommonUtil.getOIDString(attr));
	    dto.setPartClazOid(CommonUtil.getOIDString(claz));
	    dto.setPartClazAttrLinkOid(CommonUtil.getOIDString(link));
	    dto.setPartAttributeDTO(attrDTO);
	    list.add(dto);
	}

	return list;
    }

    @Override
    // @분류체계 프로젝트, 중량 필수 체크 - 등록 시점만
    public boolean[] checkProjectWeightEss(String clazOid) throws Exception {

	boolean[] projectWeightCheck = new boolean[4];

	boolean isEsseProject = false;
	boolean isCheckedProject = false;

	boolean isEsseWeight = false;
	boolean isCheckedWeight = false;

	KETPartClassification claz = (KETPartClassification) CommonUtil.getObject(clazOid);
	if (claz != null) {
	    List<KETPartClassAttrLink> partClassAttrLinkList = getPartClassAttrLinkList(claz);

	    for (KETPartClassAttrLink link : partClassAttrLinkList) {

		KETPartAttribute attr = link.getAttr();
		if (PartSpecEnum.SpProjectNo.getAttrCode().equals(attr.getAttrCode())) {
		    isEsseProject = link.isEssential();
		    isCheckedProject = link.isChecked();
		} else if (PartSpecEnum.SpNetWeight.getAttrCode().equals(attr.getAttrCode())) {
		    isEsseWeight = link.isEssential();
		    isCheckedWeight = link.isChecked();
		}
	    }
	}

	projectWeightCheck[0] = isEsseProject;
	projectWeightCheck[1] = isCheckedProject;
	projectWeightCheck[2] = isEsseWeight;
	projectWeightCheck[3] = isCheckedWeight;

	return projectWeightCheck;
    }

    // 부품 전체 속성리스트 정보가져오기 - 기존 것은 체크 함
    @Override
    public List<PartAttributeDTO> searchPartAttrList(String attrOidArray) throws Exception {

	List<KETPartAttribute> partAttributeList = query.queryForListByOneClass(KETPartAttribute.class, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", "spPartGroup"), new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpPartNameHis.getAttrCode()),
		        new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpPartRevision.getAttrCode()),
		        new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpPartType.getAttrCode()),
		        new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpIsDeleted.getAttrCode()),
		        new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpBOMFlag.getAttrCode()),
		        new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpWeightUnit.getAttrCode()),
		        new int[] { index });
		query.appendAnd();
		query.appendWhere(new SearchCondition(class1, KETPartAttribute.ATTR_CODE, "<>", PartSpecEnum.SpEoNo.getAttrCode()),
		        new int[] { index });

		query.appendOrderBy(new OrderBy(new ClassAttribute(class1, KETPartAttribute.ATTR_NAME), false), new int[] { index });
	    }

	});

	Map checkMap = new HashMap();
	if (!StringUtils.isEmpty(attrOidArray)) {
	    String[] attrOidArrayList = attrOidArray.split(",");
	    for (int k = 0; k < attrOidArrayList.length; k++) {
		checkMap.put(attrOidArrayList[k], null);
	    }
	}

	List<PartAttributeDTO> resultList = new ArrayList<PartAttributeDTO>();
	for (KETPartAttribute attr : partAttributeList) {
	    PartAttributeDTO dto = new PartAttributeDTO();
	    partSpecBuilder.buildPers2Dto(attr, dto);

	    if (checkMap.containsKey(dto.getPartAttrOid())) {
		dto.setSelectedPartAttr(true);
	    }

	    resultList.add(dto);
	}

	return resultList;
    }

    // 분류의 속성 저장( migration 테스트 )
    @Override
    public boolean insertPartAttrLinkList(PartClassAttrLinkDTO partClassAttrLinkDTO) throws Exception {

	String partClazAttrLinkOid = partClassAttrLinkDTO.getPartClazAttrLinkOid();

	KETPartClassAttrLink partClazAttrLink = null;
	if (StringUtils.isEmpty(partClazAttrLinkOid)) {

	    String partClazOid = partClassAttrLinkDTO.getPartClazOid();
	    String partAttrOid = partClassAttrLinkDTO.getPartAttrOid();

	    KETPartClassification partClaz = (KETPartClassification) CommonUtil.getObject(partClazOid);
	    KETPartAttribute partAttr = (KETPartAttribute) CommonUtil.getObject(partAttrOid);

	    partClazAttrLink = KETPartClassAttrLink.newKETPartClassAttrLink(partAttr, partClaz);

	} else {

	    partClazAttrLink = (KETPartClassAttrLink) CommonUtil.getObject(partClazAttrLinkOid);
	}

	partClazAttrLink.setEssential(partClassAttrLinkDTO.getEssential());
	partClazAttrLink.setChecked(partClassAttrLinkDTO.getChecked());
	partClazAttrLink.setIndexRow(partClassAttrLinkDTO.getIndexRow());
	partClazAttrLink.setIndexCol(partClassAttrLinkDTO.getIndexCol());

	partClazAttrLink.setHpYn(partClassAttrLinkDTO.getHpYn());
	partClazAttrLink.setIndexSort(partClassAttrLinkDTO.getIndexSort());

	partClazAttrLink = (KETPartClassAttrLink) PersistenceHelper.manager.save(partClazAttrLink);

	return true;
    }

    @Override
    public void savePartClazAttrLinkList(List<PartClassAttrLinkDTO> partClazAttrLinkList, String clazOid) throws Exception {

	KETPartClassification partClassification = (KETPartClassification) CommonUtil.getObject(clazOid);
	List<KETPartClassAttrLink> oldClazAttrLinkList = getPartClassAttrLinkList(partClassification);

	List<KETPartClassAttrLink> newClazAttrLinkList = new ArrayList<KETPartClassAttrLink>();
	for (PartClassAttrLinkDTO dto : partClazAttrLinkList) {
	    if (StringUtils.isEmpty(dto.getPartClazAttrLinkOid())) {
		// new 추가
		KETPartAttribute attr = (KETPartAttribute) CommonUtil.getObject(dto.getPartAttrOid());
		KETPartClassAttrLink link = KETPartClassAttrLink.newKETPartClassAttrLink(attr, partClassification);
		link.setEssential(dto.getEssential());
		link.setChecked(dto.getChecked());
		link.setIndexCol(dto.getIndexCol());
		link.setIndexRow(dto.getIndexRow());

		link.setHpYn(dto.getHpYn());
		link.setIndexSort(dto.getIndexSort());

		link = (KETPartClassAttrLink) PersistenceHelper.manager.save(link);
		newClazAttrLinkList.add(link);

		Kogger.debug(getClass(), "add KETPartClassAttrLink :" + link);

	    } else {
		// exist modify ( same 변경 )
		KETPartClassAttrLink link = (KETPartClassAttrLink) CommonUtil.getObject(dto.getPartClazAttrLinkOid());
		link.setEssential(dto.getEssential());
		link.setChecked(dto.getChecked());
		link.setIndexCol(dto.getIndexCol());
		link.setIndexRow(dto.getIndexRow());

		link.setHpYn(dto.getHpYn());
		link.setIndexSort(dto.getIndexSort());

		link = (KETPartClassAttrLink) PersistenceHelper.manager.save(link);
		newClazAttrLinkList.add(link);

		Kogger.debug(getClass(), "modify KETPartClassAttrLink :" + link);

	    }
	}

	if (oldClazAttrLinkList.size() != 0) {

	    PersistableCompareUtil<KETPartClassAttrLink> comparator = new PersistableCompareUtil<KETPartClassAttrLink>();
	    comparator.compare(oldClazAttrLinkList, newClazAttrLinkList);
	    List<KETPartClassAttrLink> deleteList = comparator.getDeleteList();

	    // 삭제
	    for (KETPartClassAttrLink delLink : deleteList) {
		Kogger.debug(getClass(), "delete KETPartClassAttrLink :" + delLink);
	    }

	    ObjectUtil.deletePersistableList(deleteList);
	}
    }

    private List<KETPartClassAttrLink> getPartClassAttrLinkList(KETPartClassification claz) throws Exception {

	List<KETPartClassAttrLink> partClassAttrLinkList = query.queryForListLinkByRoleB(KETPartClassAttrLink.class,
	        KETPartClassification.class, claz, new QueryBStrategy() {

		    @Override
		    public void handleQuery(QuerySpec query, Class classLink, int indexLink, Class classB, int indexB) throws Exception {

		        query.appendOrderBy(new OrderBy(new ClassAttribute(classLink, KETPartClassAttrLink.INDEX_ROW), false),
		                new int[] { indexLink });
		        query.appendOrderBy(new OrderBy(new ClassAttribute(classLink, KETPartClassAttrLink.INDEX_COL), false),
		                new int[] { indexLink });
		        query.appendOrderBy(new OrderBy(new ClassAttribute(classLink, KETPartClassAttrLink.INDEX_SORT), false),
		                new int[] { indexLink });
		    }
	        });

	return partClassAttrLinkList;
    }

}