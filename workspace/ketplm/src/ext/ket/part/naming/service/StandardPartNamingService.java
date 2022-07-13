package ext.ket.part.naming.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;
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
import ext.ket.part.entity.KETPartClassNamingLink;
import ext.ket.part.entity.KETPartNaming;
import ext.ket.part.entity.dto.PartNamingDTO;
import ext.ket.part.naming.service.internal.PartNamingBuilder;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.QueryStrategy;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardPartNamingService extends StandardManager implements PartNamingService {

    private static final long serialVersionUID = 1L;
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public static StandardPartNamingService newStandardPartNamingService() throws WTException {
	StandardPartNamingService instance = new StandardPartNamingService();
	instance.initialize();
	return instance;
    }

    @Override
    public KETPartNaming insert(KETPartNaming partNaming, boolean existNotInsert) throws Exception {
	
	final String code = partNaming.getNamingCode();

	KETPartNaming oldPartNaming = query.queryForFirstByOneClass(KETPartNaming.class, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {

		ClassAttribute classAttribute = new ClassAttribute(KETPartNaming.class, KETPartNaming.NAMING_CODE);
		SQLFunction uppeNamingCode = SQLFunction.newSQLFunction(SQLFunction.UPPER, classAttribute);
		ColumnExpression columnExpression = ConstantExpression.newExpression(code.toUpperCase());
		query.appendWhere(new SearchCondition(uppeNamingCode, SearchCondition.EQUAL, columnExpression), new int[] { index });
	    }
	});

	if (oldPartNaming == null) {
	    partNaming = (KETPartNaming)PersistenceHelper.manager.save(partNaming);
	} else {
	    if (!existNotInsert)
		throw new Exception("# already exists KETPartNaming !!");
	}
	return partNaming;
    }

    @Override
    public List<PartNamingDTO> searchFullList(Locale locale) throws Exception {
	
	List<KETPartNaming> partAttributeList = query.queryForListByOneClass(KETPartNaming.class, new QueryStrategy() {

	    @Override
	    public void handleQuery(QuerySpec query, Class class1, int index) throws Exception {
		query.appendOrderBy(new OrderBy(new ClassAttribute(class1, KETPartNaming.NAMING_CODE), false), new int[] { index });
	    }
	});
	
	PartNamingBuilder builder = new PartNamingBuilder();
	List<PartNamingDTO> list = new ArrayList<PartNamingDTO>();
	for(KETPartNaming partNaming : partAttributeList){
	    PartNamingDTO dto = new PartNamingDTO();
	    list.add(builder.buildPers2Dto(partNaming, dto, null, locale));
	}
	
	return list;
    }
    
    @Override
    public PartNamingDTO searchSelectedPartNaming(String partNamingOid, Locale locale)throws Exception{
	
	KETPartNaming partNaming = (KETPartNaming)CommonUtil.getObject(partNamingOid);
	KETPartClassNamingLink oldNamingLink = query.queryForFirstLinkByRoleA(KETPartNaming.class, KETPartClassNamingLink.class, partNaming);
	
	PartNamingBuilder builder = new PartNamingBuilder();
	PartNamingDTO partNamingDto = new PartNamingDTO();
	builder.buildPers2Dto(partNaming, partNamingDto, oldNamingLink, locale);
	
	return partNamingDto;
    }
    
    @Override
    public String savePartNaming(PartNamingDTO partNamingDTO)throws Exception{
	
	PartNamingBuilder builder = new PartNamingBuilder();
	KETPartNaming partNaming = null;
	if(StringUtils.isEmpty(partNamingDTO.getPartNamingOid())){
	    partNaming = builder.buildDto2Pers(partNamingDTO, null);
	}else{
	    partNaming = builder.buildDto2Pers(partNamingDTO, (KETPartNaming)CommonUtil.getObject(partNamingDTO.getPartNamingOid()));
	}
	
	partNaming = (KETPartNaming)PersistenceHelper.manager.save(partNaming);
	
	return CommonUtil.getOIDString(partNaming);
    }

    @Override
    public void deletePartNaming(String partNamingOid)throws Exception{
	Persistable pers = CommonUtil.getObject(partNamingOid);
	if(pers == null) throw new Exception("@ Deleting KETPartNaming Object is null ");
	
	KETPartClassNamingLink oldNamingLink = query.queryForFirstLinkByRoleA(KETPartClassNamingLink.class, KETPartNaming.class, (KETPartNaming)pers);
	if(oldNamingLink != null) throw new Exception("Part Naming에 연결된 Classification이 존재합니다. 삭제할 수 없습니다.");
	
	ObjectUtil.deletePersistableWithAdmin(pers);
    }
    
}