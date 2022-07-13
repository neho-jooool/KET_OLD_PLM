package e3ps.cost.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import wt.part.WTPart;
import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.cost.entity.PartBomInfoDTO;
import e3ps.cost.service.internal.PartBomInfoBuilder;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKetCostInfoService extends StandardManager implements KetCostInfoService {
    private static final long serialVersionUID = 1L;

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public static StandardKetCostInfoService newStandardKetCostInfoService() throws WTException {
	StandardKetCostInfoService instance = new StandardKetCostInfoService();
	instance.initialize();
	return instance;
    }


    
    
    public List<PartBomInfoDTO> getChildList(List<PartBomInfoDTO> FullLevleList, String partOid, String groupNo) throws Exception {
	
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	try {

	    StringBuffer sb = new StringBuffer();
	    
	    sb.append(" SELECT '' AS GROUP_NO,                                                                              \n");        
            sb.append("        CHILDITEMCODE,                                                                               \n");
            sb.append("        TRIM(TO_CHAR(ROWNUM,'00')) AS LVL,                                                           \n");
            sb.append("        TRIM(TO_CHAR(QUANTITY,'0')) AS QTY,                                                          \n");
            sb.append("       (SELECT COUNT(*)  												\n");
            sb.append("          FROM KETPARTUSAGELINK A0, WTPART B0                                                               		\n");
            sb.append("         WHERE A0.IDA3A5 = B0.IDA2A2                                                   					\n");                     
            sb.append("           AND SUBSTR(CHILDITEMCODE,1,1) NOT IN ('S','P')                                                   		\n");
            sb.append("           AND (SUBSTR(CHILDITEMCODE,0,1) != 'R' OR (SUBSTR(CHILDITEMCODE,0,1) = 'R' AND 'C' =              		\n");
            sb.append("          (SELECT B.PARTCLASSIFICTYPE FROM KETPARTCLASSLINK A, KETPARTCLASSIFICATION B, WTPARTMASTER C   		\n");
            sb.append("            WHERE A.IDA3A5 = B.IDA2A2                                                                    		\n");
            sb.append("              AND A.IDA3B5 = C.IDA2A2                                                                    		\n");
            sb.append("              AND C.WTPARTNUMBER = A0.CHILDITEMCODE                                                      		\n");
            sb.append("          )))                                                                                           			\n");
            sb.append("  START WITH A0.IDA3A5 = '" + partOid + "'"+                                               "\n");
            sb.append("        CONNECT BY PRIOR (SELECT MAX (B.IDA3A5)              								\n");                                    
            sb.append("                            FROM KETPARTUSAGELINK B                                              			\n");
            sb.append("                           WHERE B.PARENTITEMCODE = A0.CHILDITEMCODE) = A0.IDA3A5               )     as CASE_COUNT_2   	\n"); 
                            
            sb.append("   FROM KETPARTUSAGELINK A0, WTPART B0                                                               \n");
            sb.append("  WHERE A0.IDA3A5 = B0.IDA2A2                                                                        \n");
            sb.append("    AND SUBSTR(CHILDITEMCODE,1,1) NOT IN ('S','P')                                                   \n");
            //R로 시작하는 부품은 [구매품] 일때만 원가 BOM에 포함한다
            sb.append("    AND (SUBSTR(CHILDITEMCODE,0,1) != 'R' OR (SUBSTR(CHILDITEMCODE,0,1) = 'R' AND 'C' =              \n");
            sb.append("      (SELECT B.PARTCLASSIFICTYPE FROM KETPARTCLASSLINK A, KETPARTCLASSIFICATION B, WTPARTMASTER C   \n");
            sb.append("        WHERE A.IDA3A5 = B.IDA2A2                                                                    \n");
            sb.append("          AND A.IDA3B5 = C.IDA2A2                                                                    \n");
            sb.append("          AND C.WTPARTNUMBER = A0.CHILDITEMCODE                                                      \n");
            sb.append("       )))                                                                                           \n");
            sb.append("  START WITH A0.IDA3A5 = '" + partOid + "'"+                                               "\n");
            sb.append("  CONNECT BY PRIOR (SELECT MAX (B.IDA3A5)                                                  \n");
            sb.append("                      FROM KETPARTUSAGELINK B                                              \n");
            sb.append("                     WHERE B.PARENTITEMCODE = A0.CHILDITEMCODE) = A0.IDA3A5                \n");                        
            sb.append("  ORDER SIBLINGS BY A0.ITEMSEQ                                                             \n");

	    String query = sb.toString();
	    List<PartBomInfoDTO> tempList = new ArrayList<PartBomInfoDTO>();
	    tempList = oDaoManager.queryForList(query, new RowSetStrategy<PartBomInfoDTO>() {

		@Override
		public PartBomInfoDTO mapRow(ResultSet rs) throws SQLException {

		    return new PartBomInfoBuilder().buildResultSet2DtoLevle2(rs);
		}
	    });
	    
	    if(tempList.size() < 1){
		
	    }
	    
	    for(PartBomInfoDTO dto: tempList){
		dto.setGroupNo(groupNo+"-"+dto.getLVL());
		FullLevleList.add(dto);
	    }
	    
	} catch (Exception e) {
	    throw e;
	} finally {

	}
	return FullLevleList;
    }
    
    public List<PartBomInfoDTO> MakeBomTree(List<PartBomInfoDTO> FirstLevleList) throws Exception {
	
	WTPart part = null;
	String partOid = null;
	String groupNo = null;
	List<PartBomInfoDTO> FullLevleList = new ArrayList<PartBomInfoDTO>();
	int case_count_1 = 0;
	
	for(PartBomInfoDTO dto: FirstLevleList){
	    PartBomInfoDTO fullDto = new PartBomInfoDTO();
	    fullDto.setChildItemCode(dto.getChildItemCode());
	    fullDto.setGroupNo(dto.getGroupNo());
	    fullDto.setQty(dto.getQty());
	    FullLevleList.add(fullDto);
	    
	    if( Integer.parseInt(dto.getLVL()) > 1 ){
		part = PartBaseHelper.service.getLatestPart(dto.getChildItemCode());
		partOid = CommonUtil.getOIDLongValue2Str(part);
		groupNo = dto.getGroupNo();
		FullLevleList = getChildList(FullLevleList,partOid,groupNo);
		case_count_1++;
	    }
	}
	
	for(PartBomInfoDTO dto: FullLevleList){
	    dto.setCase_count_1(Integer.toString(case_count_1));
	}
	
	
	return FullLevleList;
    }
    
    
    @Override
    public List<PartBomInfoDTO> searchPlmFullList(String partOid,String GroupNo) throws Exception {
	List<PartBomInfoDTO> FirstLevleList = getPlmFullList(partOid,GroupNo); //제품단위로 1레벨 bom 뽑기
	
	List<PartBomInfoDTO> FullLevleList  = MakeBomTree(FirstLevleList);
	for(PartBomInfoDTO dto: FullLevleList){
	    System.out.println(dto.toString()); 
	}
	return FullLevleList;
    }
    
    private List<PartBomInfoDTO> getPlmFullList(String partOid,String GroupNo) throws Exception {

	List<PartBomInfoDTO> PartBomList = new ArrayList<PartBomInfoDTO>();
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    StringBuffer sb = new StringBuffer();
	    
	    sb.append(" SELECT DECODE(LVL,0,'" + GroupNo + "'"+",'"+GroupNo+"-'||TRIM(TO_CHAR(ROWNUM-1,'00'))) AS GROUP_NO,                     \n");        
            sb.append("        CHILDITEMCODE,                                                                              			\n");
            sb.append("        LVL,                                                                                   			\n");
            sb.append("        QTY                                                                                   			\n");
            sb.append("   FROM (SELECT NUM,LVL,CHILDITEMCODE,QTY																								\n");                    
            sb.append("        FROM (																											\n");
            sb.append("              SELECT                                                   0 AS NUM,                                                                                                                                                 \n");
            sb.append("                                                                       0 AS LVL,                        																\n");                                                                                                                                       
            sb.append("                                                         A0.WTPARTNUMBER AS CHILDITEMCODE,                                                                                                                                       \n");
            sb.append("                                                                     '1' AS QTY,  																		\n");
            sb.append("                    (A0.WTPARTNUMBER || '_' ||B0.VERSIONIDA2VERSIONINFO) AS ITEMINFO,																	        \n");
            sb.append("                                                           B0.STATESTATE AS STATUS,																		\n");
            sb.append("                                                           B0.IDA3A2STATE                                                                                                                                      			\n");
            sb.append("                FROM WTPARTMASTER A0, WTPART B0                                                                                        												\n");
            sb.append("               WHERE A0.IDA2A2 = B0.IDA3MASTERREFERENCE AND B0.LATESTITERATIONINFO=1 AND B0.IDA2A2 ='" + partOid + "'"+                                                                                           	       "\n");
            sb.append("             ) BOM, PHASETEMPLATE PH  ,PHASELINK PL, (SELECT KEB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KEB.ECOITEMCODE||'_'||KEB.BOMVERSION) AS HITEMKEY, KEC.COWORKERID, KEC.COWORKERNAME, KEB.STATESTATE AS STATUS              \n");  
            sb.append("                                                        FROM KETBOMECOHEADER KEB, KETBOMECOCOWORKER KEC 																\n");
            sb.append("                                                       WHERE KEB.ECOHEADERNUMBER=KEC.ECOHEADERNUMBER AND KEB.ECOITEMCODE=KEC.ECOITEMCODE AND KEB.STATESTATE='APPROVED'                                    			\n");
            sb.append("                                                       UNION                                                                                                    									\n");
            sb.append("                                                      SELECT KB.ECOHEADERNUMBER AS ECOHEADERNUMBER, (KB.NEWBOMCODE||'_'|| KB.BOMVERSION) AS HITEMKEY, KC.COWORKERID, KC.COWORKERNAME, KB.STATESTATE AS STATUS                    \n");             
            sb.append("                                                        FROM KETBOMHEADER KB, KETBOMECOCOWORKER KC                                                                                 						\n");
            sb.append("                                                       WHERE  KB.NEWBOMCODE=KC.ECOITEMCODE  AND KB.ECOHEADERNUMBER=KC.ECOHEADERNUMBER  AND KB.STATESTATE='APPROVED') HD                                                          \n");                     
            sb.append("       WHERE PL.IDA3A5     = BOM.IDA3A2STATE                                                                                                       									        \n");
            sb.append("         AND PL.IDA3B5     = PH.IDA2A2                                                                                                         										        \n");
            sb.append("         AND PH.PHASESTATE = BOM.STATUS                                                                                    													\n");
            sb.append("         AND BOM.ITEMINFO  = HD.HITEMKEY(+) 																							\n");
            sb.append("     UNION																											\n");
            sb.append("    SELECT NUM,LVL,CHILDITEMCODE,QTY 																								\n");
            sb.append("      FROM (                                                                                             															\n");
            sb.append("            SELECT ROWNUM                                                       AS NUM,               																\n");
            sb.append("                   LEVEL                                                        AS LVL,               																\n");
            sb.append("                   A0.CHILDITEMCODE ,																								\n");
            sb.append("                   TRIM(TO_CHAR(QUANTITY,'0')) AS QTY     																					\n");
            sb.append("              FROM KETPARTUSAGELINK A0, WTPART B0                                                     																\n");
            sb.append("             WHERE A0.IDA3A5 = B0.IDA2A2                                                              																\n");
            sb.append("               AND SUBSTR(CHILDITEMCODE,1,1) NOT IN ('S','P') 																					\n");
            sb.append("               AND LEVEL <3																									\n");
            //R로 시작하는 부품은 [구매품] 일때만 원가 BOM에 포함한다                                           																			
            sb.append("               AND (SUBSTR(CHILDITEMCODE,0,1) != 'R' OR (SUBSTR(CHILDITEMCODE,0,1) = 'R' 																	\n");
            sb.append("               AND 'C' = (SELECT B.PARTCLASSIFICTYPE FROM KETPARTCLASSLINK A, KETPARTCLASSIFICATION B, WTPARTMASTER C   														\n");
            sb.append("                           WHERE A.IDA3A5 = B.IDA2A2                                                                    														\n");
            sb.append("                             AND A.IDA3B5 = C.IDA2A2                                                                    														\n");
            sb.append("                             AND C.WTPARTNUMBER = A0.CHILDITEMCODE                                                      														\n");
            sb.append("                         )																									\n");
            sb.append("            )																											\n");
            sb.append("     )                                                                                           																\n");
            sb.append("     START WITH A0.IDA3A5 = '" + partOid + "'"+																							"\n");
            sb.append("     CONNECT BY PRIOR (SELECT MAX (B.IDA3A5) FROM KETPARTUSAGELINK B WHERE B.PARENTITEMCODE = A0.CHILDITEMCODE) = A0.IDA3A5                											\n");
            sb.append("     ORDER SIBLINGS BY A0.ITEMSEQ) BOM)																								\n");

	    String query = sb.toString();

	    PartBomList = oDaoManager.queryForList(query, new RowSetStrategy<PartBomInfoDTO>() {

		@Override
		public PartBomInfoDTO mapRow(ResultSet rs) throws SQLException {

		    return new PartBomInfoBuilder().buildResultSet2Dto(rs);
		}
	    });

	} catch (Exception e) {
	    throw e;
	} finally {

	}

	return PartBomList;
    }
    
    public static boolean isCostStandard(String gubun, String delimiter) throws Exception{

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	if(!StringUtils.isNotEmpty(gubun)){
	    return false;
	}
	try {
	    
	    StringBuffer sb = new StringBuffer();
	    if("make_1".equals(gubun) ){ //비철
		sb.append(" SELECT MET_TYPE FROM PRESS_MAKER WHERE MAKER_NAME||MET_TYPE = '"+delimiter+"'\n");
	    }
	    
	    if("make_2".equals(gubun) ){//수지
		sb.append(" SELECT MATERIAL_NAME FROM MOLD_MAKER WHERE MAKER_NAME||MATERIAL_NAME||GRADE_NAME  = '"+delimiter+"'\n");
	    }
	    
	    String sSql = sb.toString();
	    int count = oDaoManager.queryForCount(sSql);
	    
	    return (count != 0);

	} catch (Exception e) {
	    throw e;
	}
    }
}