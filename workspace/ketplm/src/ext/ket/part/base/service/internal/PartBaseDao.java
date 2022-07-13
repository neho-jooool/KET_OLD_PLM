package ext.ket.part.base.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.jasypt.commons.CommonUtils;

import wt.lifecycle.State;
import wt.method.MethodContext;
import wt.org.WTUser;
import wt.pom.DBProperties;
import wt.pom.WTConnection;
import wt.session.SessionHelper;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.common.util.ObjectUtil;
import ext.ket.part.base.service.PartBaseHelper;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.part.entity.dto.CommonCodeDTO;
import ext.ket.part.entity.dto.PartSearchMainDTO;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.code.service.CodeHelper;
import ext.ket.shared.log.Kogger;

public class PartBaseDao {

    // get part search result
    public PageControl searchMainPartList(PartSearchMainDTO partSearchMainDTO, String pageTotalSize) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List alBindSql = new ArrayList();

	int totalCount = Integer.parseInt(pageTotalSize);
	if (totalCount < 0) {
	    boolean withOrderBy = false;
	    // 속도개선 - 빠른 sql문 실행을 위해서 수정 : 즉 불필요하게 조인되지 않게 하기
	    String sSql = getPartQueryBySortNCondition(partSearchMainDTO, withOrderBy);
	    totalCount = oDaoManager.queryForCount(sSql);

	    // WorkSpaceControler에서 사용할때만 쓴다 listTotalWorkData
	    if (partSearchMainDTO.isOnlyResult()) {
		List<PartSearchMainDTO> searchResultList = new ArrayList<PartSearchMainDTO>();
		PageControl pageControl = new PageControl(partSearchMainDTO.getPage() + 1, searchResultList, 1, 1, totalCount);
		return pageControl;
	    } else {
		// 총 개수가 0이면 굳이 paging 쿼리 실행할 필요가 없음.
		if (totalCount == 0) {
		    List<PartSearchMainDTO> searchResultList = new ArrayList<PartSearchMainDTO>();
		    PageControl pageControl = new PageControl(partSearchMainDTO.getPage() + 1, searchResultList,
			    partSearchMainDTO.getFormPage(), partSearchMainDTO.getFormPage(), totalCount);
		    return pageControl;
		}
	    }
	}

	// 속도개선 - 빠른 sql문 실행을 위해서 수정 : 즉 불필요하게 조인되지 않게 하기
	String sSql = getPartQueryBySortNCondition(partSearchMainDTO, true);
	// 페이징된 partOid 20개를 먼저 가져오는 쿼리
	sSql = oDaoManager.getPagingQuery(sSql, partSearchMainDTO.getPage(), partSearchMainDTO.getFormPage(), alBindSql);
	// 페이징 결과를 한 번 더 감쌈
	sSql = getSearchPartQueryByPartOid(sSql);
	// 페이징 결과 ida2a2를 in에 넣고 select 해 오기
	sSql = getSearchPartQueryBySelectIn(partSearchMainDTO, sSql);

	List<PartSearchMainDTO> searchResultList = oDaoManager.queryForList(sSql, alBindSql, new RowSetStrategy<PartSearchMainDTO>() {

	    @Override
	    public PartSearchMainDTO mapRow(ResultSet rs) throws SQLException {

		PartSearchMainDTO partMainDTO = new PartSearchMainDTO();

		partMainDTO.setPartOid(rs.getString("partOid"));
		partMainDTO.setPartRevOid(rs.getString("partRevOid"));
		partMainDTO.setPartMastOid(rs.getString("partMastOid"));
		partMainDTO.setPartNumber(rs.getString("partNumber"));
		partMainDTO.setSpPartType(rs.getString(PartSpecEnum.SpPartType.getAttrCode()));
		partMainDTO.setPartName(rs.getString("partName"));
		partMainDTO.setPartDefaultUnit(rs.getString("partDefaultUnit"));
		partMainDTO.setPartRevision(rs.getString("partRevision"));
		partMainDTO.setPartIteration(rs.getString("partIteration"));
		partMainDTO.setPartState(State.toState(rs.getString("partState")).getDisplay());
		partMainDTO.setPartClazOid(rs.getString("partClazOid"));
		partMainDTO.setPartClazNameKr(rs.getString("partClazNameKr"));
		partMainDTO.setProjectNo(rs.getString("projectNo"));
		partMainDTO.setEcoNo(rs.getString("ecoNo"));

		partMainDTO.setPrimaryEpmNo(getALink(rs.getString("primaryEpmNo"), "openViewEpm"));
		partMainDTO.setDieNo(getALink(rs.getString("dieNo"), "openViewPart"));

		partMainDTO.setCreateDate(DateUtil.getDateString(rs.getTimestamp("createDate"), "d"));
		partMainDTO.setModifyDate(DateUtil.getDateString(rs.getTimestamp("modifyDate"), "d"));
		partMainDTO.setSpPartRevision(rs.getString(PartSpecEnum.SpPartRevision.getAttrCode()));
		partMainDTO.setSpPartTypeName(rs.getString("spPartTypeName"));

		partMainDTO.setSpPartDevLevel(PartUtil.getSpDevLevelText(rs.getString(PartSpecEnum.SpPartDevLevel.getAttrCode())));

		KETPartClassification classific = (KETPartClassification) CommonUtil.getObject(rs.getString("partClazOid"));
		if (classific != null && !"".equals(classific)) {
		    partMainDTO.setPartErpProdCode(classific.getErpProdCode());
		}

		// 재질(금형) 코드를 팝업에서 내려주기 위해서 사용
		partMainDTO.setSpMaterDieCode(rs.getString(PartSpecEnum.SpMaterDie.getAttrCode()));

		try {

		    PartSpecEnum[] specEnums = PartUtil.getPartSpecForSearch();
		    for (PartSpecEnum partSpecEnum : specEnums) {

			String value = rs.getString(partSpecEnum.getAttrCode());
			if (StringUtils.isNotEmpty(value)) {
			    if (StringUtils.isNotEmpty(partSpecEnum.getAttrCodeType())) {
				try {
				    // Kogger.debug(getClass(), " set:" + partSpecEnum.getAttrCode() + " => " + value);
				    String codeOfNumberCodeValue = StringUtils.EMPTY;
				    if (value.indexOf(",") != -1) {
					String codeArr[] = value.split(",");
					for (int i = 0; i < codeArr.length; i++) {
					    if (codeOfNumberCodeValue != "") {
						codeOfNumberCodeValue += ", ";
					    }
					    codeOfNumberCodeValue += CodeHelper.manager.getCodeValue(partSpecEnum.getAttrCodeType(),
						    codeArr[i]);
					}
				    } else {
					codeOfNumberCodeValue = CodeHelper.manager.getCodeValue(partSpecEnum.getAttrCodeType(), value);
				    }
				    // Kogger.debug(getClass(), " set:" + partSpecEnum.getAttrCode() + " => " + codeOfNumberCodeValue);
				    BeanUtils.setProperty(partMainDTO, partSpecEnum.getAttrCode(), codeOfNumberCodeValue);
				} catch (Exception e) {
				    Kogger.debug(getClass(), "# " + partSpecEnum.getAttrCode() + " Translation Error!! ");
				}
			    } else {

				if (PartSpecEnum.SpMaterialInfo == partSpecEnum || PartSpecEnum.SpMaterNotFe == partSpecEnum) {

				    BeanUtils.setProperty(partMainDTO, partSpecEnum.getAttrCode(),
					    PartBaseHelper.service.getMaterialInfo(value));

				} else if (partSpecEnum == PartSpecEnum.SpManufacPlace || partSpecEnum == PartSpecEnum.SpDieWhere) {
				    // 생산처 사내, 외주에 따라 값이 다르다.
				    BeanUtils.setProperty(partMainDTO, partSpecEnum.getAttrCode(),
					    PartBaseHelper.service.getManufacPlaceName(value));

				} else if (partSpecEnum == PartSpecEnum.SpMTerminal || partSpecEnum == PartSpecEnum.SpMConnector
				        || partSpecEnum == PartSpecEnum.SpMClip || partSpecEnum == PartSpecEnum.SpMCover
				        || partSpecEnum == PartSpecEnum.SpMRH || partSpecEnum == PartSpecEnum.SpMWireSeal
				        || partSpecEnum == PartSpecEnum.SpMatchBulb) {

				    BeanUtils.setProperty(partMainDTO, partSpecEnum.getAttrCode(), getALink(value, "openViewPart"));

				} else {
				    BeanUtils.setProperty(partMainDTO, partSpecEnum.getAttrCode(), value);
				}
			    }
			}
		    }

		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    throw new SQLException(" 부품사양정보 set 하다가 에러 남! ");
		}

		return partMainDTO;
	    }
	});

	PageControl pageControl = new PageControl(partSearchMainDTO.getPage() + 1, searchResultList, partSearchMainDTO.getFormPage(),
	        partSearchMainDTO.getFormPage(), totalCount);
	return pageControl;
    }

    public String getALink(String dbValue, String javascriptName) {
	String ret = "";
	if (StringUtils.isEmpty(dbValue)) {
	    ret = dbValue;
	} else if (dbValue.indexOf(",") != -1) {
	    String[] elements = dbValue.split(",");
	    StringBuffer buffer = new StringBuffer();
	    int idx = 0;
	    for (String element : elements) {
		if (idx > 0) {
		    buffer.append(",");
		}
		buffer.append("<a href=\"javascript:" + javascriptName + "('" + element + "');\"><font color='#4398BC'>");
		buffer.append(element);
		buffer.append("</font></a>");
		idx++;
	    }
	    ret = buffer.toString();
	} else {
	    String prefix = "<a href=\"javascript:" + javascriptName + "('" + dbValue + "');\"><font color='#4398BC'>";
	    String suffix = "</font></a>";
	    ret = prefix + dbValue + suffix;
	}

	return ret;
    }

    private String getSearchPartQueryBySelectIn(PartSearchMainDTO partSearchMainDTO, String sql) throws Exception {

	StringBuffer buffer = new StringBuffer();

	// ///////////////////////////////////////////////////////
	// Sorting 체크
	// //////////////////////////////////////////////////////
	String sortName = null;
	String sortDirection = null;
	if (!StringUtil.isTrimToEmpty(partSearchMainDTO.getSortName())) {
	    if (partSearchMainDTO.getSortName().startsWith("-")) {
		sortName = partSearchMainDTO.getSortName().substring(1);
		sortDirection = " DESC";
	    } else {
		sortName = partSearchMainDTO.getSortName();
		sortDirection = " ASC";
	    }
	} else {
	    sortName = "createDate";
	    sortDirection = " DESC";
	}

	// Select 형태의 속성 및 원재료 Sort 처리
	String sortTarget = null;
	PartSpecEnum[] specEnums = PartUtil.getPartSpecForSearch();
	boolean needMater = false;
	PartSpecEnum materPartSpecEnum = null;
	for (PartSpecEnum partSpecEnum : specEnums) {
	    if (partSpecEnum.getAttrCode().equalsIgnoreCase(sortName)) {

		if ("SELECT".equals(partSpecEnum.getAttrInputType())) {
		    // 원재료 처리
		    if (PartSpecEnum.SpMaterialInfo == partSpecEnum || PartSpecEnum.SpMaterNotFe == partSpecEnum) {
			sortTarget = " MM.GRADE ";
			needMater = true;
			materPartSpecEnum = partSpecEnum;
		    }
		    // multi 선택하는 값일 경우
		    else if (partSpecEnum.getAttrMultiSelect()) {
			// multi select는 sorting를 최초 값으로 함
			sortTarget = " ( DECODE( P." + partSpecEnum.getColumnName() + ", NULL, NULL, (SELECT NV.VALUE \n"
			        + " FROM NUMBERCODEVALUE NV WHERE NV.CODETYPE = '" + partSpecEnum.getAttrCodeType() + "' \n"
			        + " AND NV.LANG = 'ko' AND NV.CODE = \n" + "  CASE WHEN INSTR(P." + partSpecEnum.getColumnName()
			        + ", ',', 1, 1) = 0 THEN P." + partSpecEnum.getColumnName() + " \n" + "  ELSE SUBSTR(P."
			        + partSpecEnum.getColumnName() + ", 0, INSTR(P." + partSpecEnum.getColumnName() + ", ',', 1, 1)-1) \n"
			        + "  END ))) ";
		    } else {
			// single NumberCode
			sortTarget = " ( DECODE( P." + partSpecEnum.getColumnName() + ", NULL, NULL, ( SELECT NV.VALUE \n"
			        + " FROM NUMBERCODEVALUE NV WHERE NV.CODETYPE = '" + partSpecEnum.getAttrCodeType() + "' \n"
			        + " AND NV.LANG = 'ko' AND NV.CODE = P." + partSpecEnum.getColumnName() + "))) ";
		    }
		}
	    }
	}
	if (sortTarget == null) {
	    sortTarget = sortName;
	}

	StringBuffer orderBuffer = new StringBuffer();
	if (!StringUtil.isTrimToEmpty(partSearchMainDTO.getSortName())) {
	    if (partSearchMainDTO.getSortName().startsWith("-")) {
		orderBuffer.append(" ORDER BY ").append(sortTarget).append(" " + sortDirection);
	    } else {
		orderBuffer.append(" ORDER BY ").append(sortTarget).append(" " + sortDirection);
	    }
	} else {
	    orderBuffer.append(" ORDER BY ").append(sortTarget).append(" " + sortDirection);
	}

	// /////////////////////////////////////
	// 실제 SQL문 시작
	// /////////////////////////////////////

	buffer.append(" SELECT  P.CLASSNAMEA2A2||':'||P.IDA2A2 as partOid \n");
	buffer.append("         , 'VR'||P.CLASSNAMEA2A2||':'||P.BRANCHIDITERATIONINFO as partRevOid \n");
	buffer.append("         , M.CLASSNAMEA2A2||':'||M.IDA2A2 as partMastOid \n");
	buffer.append("         , M.WTPARTNUMBER AS partNumber \n");
	buffer.append("         , M.NAME AS partName \n");
	buffer.append("         , REPLACE(M.DEFAULTUNIT, 'KET_', '') AS partDefaultUnit \n");
	buffer.append("         , (SELECT VALUE FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECPARTTYPE'  \n");
	buffer.append("            AND CODE = P." + PartSpecEnum.SpPartType.getColumnName() + " AND LANG = 'ko') AS spPartTypeName \n");
	buffer.append("         , P.VERSIONIDA2VERSIONINFO AS partRevision \n");
	buffer.append("         , P.ITERATIONIDA2ITERATIONINFO AS partIteration \n");
	buffer.append("         , P.STATESTATE AS partState \n");
	buffer.append("         , C.CLASSNAMEA2A2||':'||C.IDA2A2 as partClazOid \n");
	buffer.append("         , C.CLASSKRNAME AS partClazNameKr \n");
	// 프로젝트 번호
	buffer.append("         , PJ.PJTNO AS projectNo \n");

	// ECO 번호
	/*
	 * buffer.append("         , REPLACE(BEH.ECOHEADERNUMBER, 'ECO-', '') AS ecoNo \n");
	 */
	buffer.append("     , ( \n");
	buffer.append("	        SELECT REPLACE(MAX(BEH.ECOHEADERNUMBER), 'ECO-', '') \n");
	buffer.append("	          FROM ( \n");
	buffer.append("	                 SELECT eco.ida2a2 AS ECO_IDA2A2, eco.ecoid \n");
	buffer.append("	                      , bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	buffer.append("	                   FROM KETProdChangeOrder eco \n");
	buffer.append("	                      , KETProdChangeActivity eca \n");
	buffer.append("	                      , ( \n");
	buffer.append("	                         SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	buffer.append("	                           FROM KETProdECABomLink \n");
	buffer.append("	                        ) bomlink \n");
	buffer.append("	                      , ( \n");
	buffer.append("	                         SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	buffer.append("	                           FROM KETBomEcoHeader a \n");
	buffer.append("	                        UNION ALL \n");
	buffer.append("	                         SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	buffer.append("	                           FROM KETBomHeader b \n");
	buffer.append("	                        )bomheader \n");
	buffer.append("	                  WHERE eco.ida2a2 = eca.ida3a8 \n");
	buffer.append("	                    AND eca.ida2a2 = bomlink.ida3b5 \n");
	buffer.append("	                    AND bomlink.ida3a5 = bomheader.ida2a2  \n");
	buffer.append("	                 UNION ALL \n");
	buffer.append("	                 SELECT eco.ida2a2 AS ECO_IDA2A2, eco.ecoid \n");
	buffer.append("	                      , bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	buffer.append("	                   FROM KETMoldChangeOrder eco \n");
	buffer.append("	                      , KETMoldChangeActivity eca \n");
	buffer.append("	                      , ( \n");
	buffer.append("	                         SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	buffer.append("	                           FROM KETMoldECABomLink \n");
	buffer.append("	                        ) bomlink \n");
	buffer.append("	                      , ( \n");
	buffer.append("	                         SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	buffer.append("	                           FROM KETBomEcoHeader a \n");
	buffer.append("	                        UNION ALL \n");
	buffer.append("	                         SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	buffer.append("	                           FROM KETBomHeader b \n");
	buffer.append("	                        )bomheader \n");
	buffer.append("	                  WHERE eco.ida2a2 = eca.ida3a8 \n");
	buffer.append("	                    AND eca.ida2a2 = bomlink.ida3b5 \n");
	buffer.append("	                    AND bomlink.ida3a5 = bomheader.ida2a2 \n");
	buffer.append("	               ) BEH \n");
	buffer.append("	         WHERE 1=1 \n");
	buffer.append("	           AND BEH.ECOITEMCODE = M.WTPARTNUMBER \n");
	buffer.append("	           AND BEH.BOMVERSION = P.VERSIONIDA2VERSIONINFO \n");
	buffer.append("	      ) AS ecoNo \n");

	// 도면 번호
	buffer.append("         , (SELECT LISTAGG(EM.DOCUMENTNUMBER, ',') WITHIN GROUP(ORDER BY EM.DOCUMENTNUMBER ) AS EPM_NO \n");
	buffer.append("            FROM EPMLINK L, EPMDOCUMENTMASTER EM WHERE L.IDA3A5 = EM.IDA2A2 AND L.IDA3B5 = M.IDA2A2 \n");
	buffer.append("            AND L.REFERENCETYPE = 'RELATED_DRAWING' AND L.REQUIRED = '1' ) AS primaryEpmNo \n");
	// 금형 Die No
	buffer.append("         , (SELECT LISTAGG(PM.WTPARTNUMBER, ',') WITHIN GROUP(ORDER BY PM.WTPARTNUMBER ) \n");
	buffer.append("            AS PART_NO FROM KETHalbPartDieSetPartLink L, \n");
	buffer.append("            WTPARTMASTER PM WHERE L.IDA3B5 = PM.IDA2A2 AND L.IDA3A5 = M.IDA2A2 ) AS dieNo \n");
	buffer.append("         , P.CREATESTAMPA2 AS createDate \n");
	buffer.append("         , P.MODIFYSTAMPA2 AS modifyDate \n");
	buffer.append("         , P." + PartSpecEnum.SpPartRevision.getColumnName() + " AS " + PartSpecEnum.SpPartRevision.getAttrCode()
	        + " \n");
	buffer.append("         , P." + PartSpecEnum.SpPartType.getColumnName() + " AS " + PartSpecEnum.SpPartType.getAttrCode() + " \n");
	buffer.append("         , P." + PartSpecEnum.SpPartDevLevel.getColumnName() + " AS " + PartSpecEnum.SpPartDevLevel.getAttrCode()
	        + " \n");

	for (PartSpecEnum partSpecEnum : specEnums) {
	    buffer.append("         , " + "P" + "." + partSpecEnum.getColumnName() + " AS " + partSpecEnum.getAttrCode() + " \n");
	}

	buffer.append(" FROM WTPARTMASTER M \n");
	buffer.append("    , WTPART P \n");
	// 분류체계
	buffer.append("    , KETPartClassLink CL \n");
	buffer.append("    , KETPartClassification C \n");
	// 프로젝트
	buffer.append("    , KETPartProjectLink PJL \n");
	buffer.append("    , E3PSProjectMaster PJ \n");

	/*
	 * // ECO 검색 buffer.append("     , ( \n");
	 * buffer.append("	        SELECT P.BRANCHIDITERATIONINFO, BEH.ECO_IDA2A2, BEH.ECOHEADERNUMBER, BEH.ECOITEMCODE, BEH.BOMVERSION \n"
	 * ); buffer.append("	          FROM WTPART P, WTPARTMASTER M, \n"); buffer.append("	               ( \n");
	 * buffer.append("	                 SELECT eco.ida2a2 AS ECO_IDA2A2, eco.ecoid \n");
	 * buffer.append("	                      , bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	 * buffer.append("	                   FROM KETProdChangeOrder eco \n");
	 * buffer.append("	                      , KETProdChangeActivity eca \n"); buffer.append("	                      , ( \n");
	 * buffer.append
	 * ("	                         SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n"
	 * ); buffer.append("	                           FROM KETProdECABomLink \n");
	 * buffer.append("	                        ) bomlink \n"); buffer.append("	                      , ( \n");
	 * buffer.append("	                         SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	 * buffer.append("	                           FROM KETBomEcoHeader a \n"); buffer.append("	                        UNION ALL \n");
	 * buffer.append("	                         SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	 * buffer.append("	                           FROM KETBomHeader b \n"); buffer.append("	                        )bomheader \n");
	 * buffer.append("	                  WHERE eco.ida2a2 = eca.ida3a8 \n");
	 * buffer.append("	                    AND eca.ida2a2 = bomlink.ida3b5 \n");
	 * buffer.append("	                    AND bomlink.ida3a5 = bomheader.ida2a2  \n"); buffer.append("	                 UNION ALL \n");
	 * buffer.append("	                 SELECT eco.ida2a2 AS ECO_IDA2A2, eco.ecoid \n");
	 * buffer.append("	                      , bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	 * buffer.append("	                   FROM KETMoldChangeOrder eco \n");
	 * buffer.append("	                      , KETMoldChangeActivity eca \n"); buffer.append("	                      , ( \n");
	 * buffer.append
	 * ("	                         SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n"
	 * ); buffer.append("	                           FROM KETMoldECABomLink \n");
	 * buffer.append("	                        ) bomlink \n"); buffer.append("	                      , ( \n");
	 * buffer.append("	                         SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	 * buffer.append("	                           FROM KETBomEcoHeader a \n"); buffer.append("	                        UNION ALL \n");
	 * buffer.append("	                         SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	 * buffer.append("	                           FROM KETBomHeader b \n"); buffer.append("	                        )bomheader \n");
	 * buffer.append("	                  WHERE eco.ida2a2 = eca.ida3a8 \n");
	 * buffer.append("	                    AND eca.ida2a2 = bomlink.ida3b5 \n");
	 * buffer.append("	                    AND bomlink.ida3a5 = bomheader.ida2a2 \n"); buffer.append("	               ) BEH \n");
	 * buffer.append("	         WHERE 1=1 \n"); buffer.append("	           AND M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	 * buffer.append("	           AND P.LATESTITERATIONINFO = 1 \n");
	 * buffer.append("	           AND M.WTPARTNUMBER = BEH.ECOITEMCODE \n");
	 * buffer.append("	           AND P.VERSIONIDA2VERSIONINFO = BEH.BOMVERSION \n"); buffer.append("	      ) BEH \n");
	 */

	// 원재료
	if (needMater) {
	    buffer.append("    , MOLDMATERIAL MM \n");
	}
	// 부품
	buffer.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	buffer.append(" AND P.LATESTITERATIONINFO = 1 \n");
	// buffer.append(" AND P.STATECHECKOUTINFO IN ('c/i','c/o') \n");

	// 마이그레이션이 다 안됨 : OLD 제품
	// 분류체계
	buffer.append(" AND M.IDA2A2 = CL.IDA3B5(+) \n");
	buffer.append(" AND CL.IDA3A5 = C.IDA2A2(+) \n");
	// 프로젝트
	buffer.append(" AND P.BRANCHIDITERATIONINFO = PJL.BRANCHIDA3B5(+) \n");
	buffer.append(" AND PJL.IDA3A5 = PJ.IDA2A2(+) \n");

	/*
	 * // EO buffer.append(" AND P.BRANCHIDITERATIONINFO = BEH.BRANCHIDITERATIONINFO \n");
	 */

	// 원재료
	if (needMater) {
	    buffer.append(" AND TO_NUMBER(P." + materPartSpecEnum.getColumnName() + ") = MM.IDA2A2(+) \n");
	}
	// Query In 문
	buffer.append(" AND P.IDA2A2 IN ( \n");
	buffer.append(sql);
	buffer.append(" ) \n");

	// Order by 처리
	buffer.append(orderBuffer);

	Kogger.debug(getClass(), buffer.toString());
	return buffer.toString();
    }

    // 속도 개선으로 복잡해짐...
    private String getPartQueryBySortNCondition(PartSearchMainDTO partSearchMainDTO, boolean withOrderBy) throws Exception {

	// ///////////////////////////////////////////////////////
	// Sorting 체크
	// //////////////////////////////////////////////////////
	String sortName = null;
	String sortDirection = null;
	if (!StringUtil.isTrimToEmpty(partSearchMainDTO.getSortName())) {
	    if (partSearchMainDTO.getSortName().startsWith("-")) {
		sortName = partSearchMainDTO.getSortName().substring(1);
		sortDirection = " DESC";
	    } else {
		sortName = partSearchMainDTO.getSortName();
		sortDirection = " ASC";
	    }
	} else {
	    sortName = "createDate";
	    sortDirection = " DESC";
	}

	// 최신 버전 : Latest
	boolean latest = partSearchMainDTO.isPartLatestReVision();

	// /////////////////////////////////////////////////////
	// 연계 검색 처리 속도개선을 위해 먼저 체크 시작
	// ////////////////////////////////////////////////////
	// 분류체계
	boolean needClaz = false;
	String sqlClaz = null;
	// 분류체계
	if (StringUtils.isNotEmpty(partSearchMainDTO.getPartClazOid())) {
	    // 하위 분류체계를 포함해서 가져온다.
	    String clazTempOids = partSearchMainDTO.getPartClazOid().replaceAll("ext.ket.part.entity.KETPartClassification:", "");
	    List<String> oidList = searchUniqueSubClazOidList(clazTempOids);
	    String oidClazArry = "";
	    for (int k = 0; k < oidList.size(); k++) {
		if (k == 0)
		    oidClazArry = oidClazArry + CommonUtil.getOIDLongValue2Str(oidList.get(k));
		else
		    oidClazArry = oidClazArry + "," + CommonUtil.getOIDLongValue2Str(oidList.get(k));
	    }

	    needClaz = true;
	    sqlClaz = " AND C.IDA2A2 IN ( " + oidClazArry + " ) \n";
	}
	if (StringUtils.isNotEmpty(partSearchMainDTO.getDivision())) {
	    needClaz = true;
	}
	// 도면
	boolean needEPM = false;
	String sqlEPM = null;
	// 도면 검색
	if (StringUtils.isNotEmpty(partSearchMainDTO.getRelateEpmNo())) {
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" AND M.IDA2A2 IN ( SELECT L.IDA3B5 \n");
	    buffer.append("      FROM EPMLINK L, EPMDOCUMENTMASTER EM \n");
	    buffer.append("      WHERE 1=1 \n");
	    buffer.append("      AND L.IDA3A5 = EM.IDA2A2 \n");
	    buffer.append("      AND L.REFERENCETYPE = 'RELATED_DRAWING' \n");
	    buffer.append("      AND L.REQUIRED = '1' \n");
	    buffer.append("      AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("EM.DOCUMENTNUMBER", partSearchMainDTO.getRelateEpmNo().toUpperCase(),
		            false)).append(" \n");
	    buffer.append("      ) \n");

	    needEPM = true;
	    sqlEPM = buffer.toString();
	}

	// 프로젝트
	boolean needProject = false;
	String sqlProject = null;
	// 프로젝트 번호
	if (StringUtils.isNotEmpty(partSearchMainDTO.getProjectNo())) {

	    needProject = true;
	    sqlProject = " AND "
		    + (KETQueryUtil.getSqlQueryForMultiSearch("PJ.PJTNO", partSearchMainDTO.getProjectNo().toUpperCase(), false)) + " \n";
	}

	// EO
	boolean needEO = false;
	String sqlEO = null;
	// ECO NO 검색
	if (StringUtils.isNotEmpty(partSearchMainDTO.getEcoNo())) {

	    needEO = true;
	    sqlEO = " AND "
		    + KETQueryUtil.getSqlQueryForMultiSearch("REPLACE(BEH.ECOHEADERNUMBER, 'ECO-', '')", partSearchMainDTO.getEcoNo()
		            .toUpperCase(), false) + " \n";
	}

	// 4M
	boolean need4m = false;
	// ECO NO 검색
	if (StringUtils.isNotEmpty(partSearchMainDTO.getCompleted4m())) {

	    need4m = true;
	}

	// Die에 연결된 제품 검색
	boolean needPartProd = false;
	String sqlPartProd = null;
	if (StringUtils.isNotEmpty(partSearchMainDTO.getPartProdNumber())) {

	    needPartProd = true;
	    sqlPartProd = " AND "
		    + (KETQueryUtil.getSqlQueryForMultiSearch("PROD.WTPARTNUMBER", partSearchMainDTO.getPartProdNumber().toUpperCase(),
		            false)) + " \n";
	}

	if (StringUtils.isNotEmpty(partSearchMainDTO.getPartProdName())) {

	    needPartProd = true;
	    sqlPartProd = (sqlPartProd == null ? "" : sqlPartProd) + " AND "
		    + (KETQueryUtil.getSqlQueryForMultiSearch("PROD.NAME", partSearchMainDTO.getPartProdName(), true)) + " \n";
	}

	// /////////////////////////////////////
	// 실제 SQL문 시작
	// /////////////////////////////////////

	StringBuffer buffer = new StringBuffer();

	buffer.append(" SELECT  P.IDA2A2 as partIda2a2 \n");
	if ("partOid".equalsIgnoreCase(sortName))
	    buffer.append("         , P.CLASSNAMEA2A2||':'||P.IDA2A2 as partOid \n");
	if ("partRevOid".equalsIgnoreCase(sortName))
	    buffer.append("         , 'VR'||P.CLASSNAMEA2A2||':'||P.BRANCHIDITERATIONINFO as partRevOid \n");
	if ("partMastOid".equalsIgnoreCase(sortName))
	    buffer.append("         , M.CLASSNAMEA2A2||':'||M.IDA2A2 as partMastOid \n");
	if ("partNumber".equalsIgnoreCase(sortName))
	    buffer.append("         , M.WTPARTNUMBER AS partNumber \n");
	if ("partName".equalsIgnoreCase(sortName))
	    buffer.append("         , M.NAME AS partName \n");
	if ("partDefaultUnit".equalsIgnoreCase(sortName))
	    buffer.append("         , REPLACE(M.DEFAULTUNIT, 'KET_', '') AS partDefaultUnit \n");
	if ("partRevision".equalsIgnoreCase(sortName))
	    buffer.append("         , P.VERSIONIDA2VERSIONINFO AS partRevision \n");
	if ("partIteration".equalsIgnoreCase(sortName))
	    buffer.append("         , P.ITERATIONIDA2ITERATIONINFO AS partIteration \n");
	if ("partState".equalsIgnoreCase(sortName))
	    buffer.append("         , P.STATESTATE AS partState \n");
	if ("spPartTypeName".equalsIgnoreCase(sortName))
	    buffer.append("         , (SELECT VALUE FROM NUMBERCODEVALUE WHERE CODETYPE = 'SPECPARTTYPE' AND CODE = P."
		    + PartSpecEnum.SpPartType.getColumnName() + " AND LANG = 'ko') AS spPartTypeName \n");
	if ("partClazOid".equalsIgnoreCase(sortName)) {
	    if (withOrderBy) {
		needClaz = true;
		buffer.append("         , C.CLASSNAMEA2A2||':'||C.IDA2A2 as partClazOid \n");
	    }
	}
	if ("partClazNameKr".equalsIgnoreCase(sortName)) {
	    if (withOrderBy) {
		needClaz = true;
		buffer.append("         , C.CLASSKRNAME AS partClazNameKr \n");
	    }
	}
	// 프로젝트 번호
	if ("projectNo".equalsIgnoreCase(sortName)) {
	    if (withOrderBy) {
		needProject = true;
		buffer.append("         , PJ.PJTNO AS projectNo \n");
	    }
	}
	// ECO 번호
	if ("ecoNo".equalsIgnoreCase(sortName)) {
	    if (withOrderBy) {
		needEO = true;
		buffer.append("         , BEH.ECOHEADERNUMBER AS ecoNo \n");
	    }
	}
	// 도면 번호
	if ("primaryEpmNo".equalsIgnoreCase(sortName)) {
	    if (withOrderBy) {
		needEPM = true;
		buffer.append("         , (SELECT LISTAGG(EM.DOCUMENTNUMBER, ',') WITHIN GROUP(ORDER BY EM.DOCUMENTNUMBER ) AS EPM_NO FROM EPMLINK L, \n");
		buffer.append("            EPMDOCUMENTMASTER EM WHERE L.IDA3A5 = EM.IDA2A2 AND L.IDA3B5 = M.IDA2A2 AND L.REFERENCETYPE = 'RELATED_DRAWING' AND L.REQUIRED = '1' ) AS primaryEpmNo \n");
	    }
	}
	// 금형 Die No
	if ("dieNo".equalsIgnoreCase(sortName)) {
	    buffer.append("         , (SELECT LISTAGG(PM.WTPARTNUMBER, ',') WITHIN GROUP(ORDER BY PM.WTPARTNUMBER ) AS PART_NO FROM KETHalbPartDieSetPartLink L, \n");
	    buffer.append("            WTPARTMASTER PM WHERE L.IDA3B5 = PM.IDA2A2 AND L.IDA3A5 = M.IDA2A2 ) AS dieNo \n");
	}
	if ("createDate".equalsIgnoreCase(sortName))
	    buffer.append("         , P.CREATESTAMPA2 AS createDate \n");
	if ("modifyDate".equalsIgnoreCase(sortName))
	    buffer.append("         , P.MODIFYSTAMPA2 AS modifyDate \n");
	if (PartSpecEnum.SpPartRevision.getAttrCode().equalsIgnoreCase(sortName))
	    buffer.append("         , P." + PartSpecEnum.SpPartRevision.getColumnName() + " AS "
		    + PartSpecEnum.SpPartRevision.getAttrCode() + " \n");
	if (PartSpecEnum.SpPartType.getAttrCode().equalsIgnoreCase(sortName))
	    buffer.append("         , P." + PartSpecEnum.SpPartType.getColumnName() + " AS " + PartSpecEnum.SpPartType.getAttrCode()
		    + " \n");
	if (PartSpecEnum.SpPartDevLevel.getAttrCode().equalsIgnoreCase(sortName))
	    buffer.append("         , P." + PartSpecEnum.SpPartDevLevel.getColumnName() + " AS "
		    + PartSpecEnum.SpPartDevLevel.getAttrCode() + " \n");

	PartSpecEnum[] specEnums = PartUtil.getPartSpecForSearch();
	boolean needMater = false;
	PartSpecEnum materPartSpecEnum = null;
	for (PartSpecEnum partSpecEnum : specEnums) {
	    if (partSpecEnum.getAttrCode().equalsIgnoreCase(sortName)) {

		if ("SELECT".equals(partSpecEnum.getAttrInputType())) {
		    // 원재료 처리
		    if (PartSpecEnum.SpMaterialInfo == partSpecEnum || PartSpecEnum.SpMaterNotFe == partSpecEnum) {
			buffer.append("         , MM.GRADE" + " AS " + partSpecEnum.getAttrCode() + " \n");
			needMater = true;
			materPartSpecEnum = partSpecEnum;
		    }
		    // multi 선택하는 값일 경우
		    else if (partSpecEnum.getAttrMultiSelect()) {
			// multi select는 sorting를 최초 값으로 함
			buffer.append("         , DECODE( P." + partSpecEnum.getColumnName() + ", NULL, NULL, (SELECT NV.VALUE \n");
			buffer.append("            FROM NUMBERCODEVALUE NV WHERE NV.CODETYPE = '" + partSpecEnum.getAttrCodeType() + "' \n");
			buffer.append("            AND NV.LANG = 'ko' AND NV.CODE = \n");
			buffer.append("             CASE WHEN INSTR(P." + partSpecEnum.getColumnName() + ", ',', 1, 1) = 0 THEN P."
			        + partSpecEnum.getColumnName() + " \n");
			buffer.append("             ELSE SUBSTR(P." + partSpecEnum.getColumnName() + ", 0, INSTR(P."
			        + partSpecEnum.getColumnName() + ", ',', 1, 1)-1) \n");
			buffer.append("             END )) AS " + partSpecEnum.getAttrCode() + " \n");
		    } else {
			buffer.append("         , DECODE( P." + partSpecEnum.getColumnName() + ", NULL, NULL, (SELECT NV.VALUE \n");
			buffer.append("            FROM NUMBERCODEVALUE NV WHERE NV.CODETYPE = '" + partSpecEnum.getAttrCodeType() + "' \n");
			buffer.append("            AND NV.LANG = 'ko' AND NV.CODE = P." + partSpecEnum.getColumnName() + ")) AS "
			        + partSpecEnum.getAttrCode() + " \n");
		    }
		} else {
		    buffer.append("         , " + "P" + "." + partSpecEnum.getColumnName() + " AS " + partSpecEnum.getAttrCode() + " \n");
		}
	    }
	}

	// 원재료 처리
	String sqlMater = null;
	if (StringUtils.isNotEmpty(partSearchMainDTO.getSpMaterialInfo())) {
	    needMater = true;
	    materPartSpecEnum = PartSpecEnum.SpMaterialInfo;
	    sqlMater = " AND " + (KETQueryUtil.getSqlQueryForMultiSearch("MM.GRADE", partSearchMainDTO.getSpMaterialInfo(), true)) + " \n";
	} else if (StringUtils.isNotEmpty(partSearchMainDTO.getSpMaterNotFe())) {
	    needMater = true;
	    materPartSpecEnum = PartSpecEnum.SpMaterNotFe;
	    sqlMater = " AND " + (KETQueryUtil.getSqlQueryForMultiSearch("MM.GRADE", partSearchMainDTO.getSpMaterNotFe(), true)) + " \n";
	}

	// 부품
	buffer.append(" FROM WTPARTMASTER M \n");
	buffer.append("    , WTPART P \n");

	// 최신버전 사용
	if (latest) {
	    buffer.append(" ,( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO  \n");
	    buffer.append("    FROM WTPART I, WTPARTMASTER J \n");
	    buffer.append("    WHERE I.IDA3MASTERREFERENCE = J.IDA2A2 \n");
	    buffer.append("    	 AND I.LATESTITERATIONINFO = 1  \n");
	    buffer.append("      AND I.STATECHECKOUTINFO != 'wrk' \n");
	    buffer.append("    GROUP BY J.IDA2A2 \n");
	    buffer.append(" ) MAXVERPART \n");
	}
	// 분류체계
	if (needClaz) {
	    buffer.append("    , KETPartClassLink CL \n");
	    buffer.append("    , KETPartClassification C \n");
	}
	// 프로젝트
	if (needProject) {
	    buffer.append("    , KETPartProjectLink PJL \n");
	    buffer.append("    , E3PSProjectMaster PJ \n");
	}
	// ECO 검색
	if (needEO || need4m) {
	    buffer.append("     , ( \n");
	    buffer.append("	                 SELECT bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	    buffer.append("	                   FROM KETProdChangeOrder eco \n");
	    buffer.append("	                      , KETProdChangeActivity eca \n");
	    buffer.append("	                      , ( \n");
	    buffer.append("	                         SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	    buffer.append("	                           FROM KETProdECABomLink \n");
	    buffer.append("	                        ) bomlink \n");
	    buffer.append("	                      , ( \n");
	    buffer.append("	                         SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	    buffer.append("	                           FROM KETBomEcoHeader a \n");
	    buffer.append("	                        UNION ALL \n");
	    buffer.append("	                         SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	    buffer.append("	                           FROM KETBomHeader b \n");
	    buffer.append("	                        )bomheader \n");
	    buffer.append("	                  WHERE eco.ida2a2 = eca.ida3a8 \n");
	    buffer.append("	                    AND eca.ida2a2 = bomlink.ida3b5 \n");
	    buffer.append("	                    AND bomlink.ida3a5 = bomheader.ida2a2 \n");
	    buffer.append("	                 UNION ALL \n");
	    buffer.append("	                 SELECT bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	    buffer.append("	                   FROM KETMoldChangeOrder eco \n");
	    buffer.append("	                      , KETMoldChangeActivity eca \n");
	    buffer.append("	                      , ( \n");
	    buffer.append("	                         SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	    buffer.append("	                           FROM KETMoldECABomLink \n");
	    buffer.append("	                        ) bomlink \n");
	    buffer.append("	                      , ( \n");
	    buffer.append("	                         SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	    buffer.append("	                           FROM KETBomEcoHeader a \n");
	    buffer.append("	                        UNION ALL \n");
	    buffer.append("	                         SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	    buffer.append("	                           FROM KETBomHeader b \n");
	    buffer.append("	                        )bomheader \n");
	    buffer.append("	                  WHERE eco.ida2a2 = eca.ida3a8 \n");
	    buffer.append("	                    AND eca.ida2a2 = bomlink.ida3b5 \n");
	    buffer.append("	                    AND bomlink.ida3a5 = bomheader.ida2a2 \n");

	    if (need4m) {
		buffer.append(" 	            AND bomheader.ECOITEMCODE||'::'||bomheader.BOMVERSION IN ( \n");
		buffer.append("	                                        SELECT ECOITEMCODE||'::'||BOMVERSION \n");
		buffer.append("	                                          FROM ( \n");
		buffer.append("	                                                SELECT bomheader.ECOITEMCODE, MAX(bomheader.BOMVERSION) AS BOMVERSION \n");
		buffer.append("	                                                  FROM KETMoldChangeOrder eco \n");
		buffer.append("	                                                     , KETMoldChangeActivity eca \n");
		buffer.append("	                                                     , ( \n");
		buffer.append("	                                                        SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
		buffer.append("	                                                          FROM KETMoldECABomLink \n");
		buffer.append("	                                                       ) bomlink \n");
		buffer.append("	                                                     , ( \n");
		buffer.append("	                                                        SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
		buffer.append("	                                                          FROM KETBomEcoHeader a \n");
		buffer.append("	                                                         UNION ALL \n");
		buffer.append("	                                                        SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
		buffer.append("	                                                          FROM KETBomHeader b \n");
		buffer.append("	                                                       )bomheader \n");
		buffer.append("	                                                 WHERE eco.ida2a2 = eca.ida3a8 \n");
		buffer.append("	                                                   AND eca.ida2a2 = bomlink.ida3b5 \n");
		buffer.append("	                                                   AND bomlink.ida3a5 = bomheader.ida2a2 \n");
		buffer.append("	                                                   AND eco.ida2a2 IN (\n");
		buffer.append("	                                                                      SELECT ida3a8 \n");
		buffer.append("	                                                                        FROM KETMoldChangeActivity eca \n");
		buffer.append("	                                                                       WHERE ACTIVITYTYPE = '4' \n");
		buffer.append("                                                                          AND COMPLETEDATE IS NOT NULL \n");
		buffer.append("                                                                      ) \n");
		buffer.append("                                                  GROUP BY bomheader.ECOITEMCODE \n");
		buffer.append("                                                 ) \n");

		buffer.append("                         ) \n");

	    }

	    buffer.append("	      ) BEH \n");
	}
	// 원재료
	if (needMater) {
	    buffer.append("    , MOLDMATERIAL MM \n");
	}
	// Die에 연결된 제품 검색
	if (needPartProd) {
	    buffer.append("    , KETHalbPartDieSetPartLink HD \n");
	    buffer.append("    , WTPARTMASTER PROD \n");
	}
	// 부품
	buffer.append(" WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	buffer.append(" AND P.LATESTITERATIONINFO = 1 \n");
	// buffer.append(" AND P.STATECHECKOUTINFO IN ('c/i','c/o') \n");
	buffer.append(" AND P.STATECHECKOUTINFO NOT IN ('wrk') \n");

	// 최신버전 사용
	if (latest) {
	    buffer.append(" AND M.IDA2A2 = MAXVERPART.IDA2A2 \n");
	    buffer.append(" AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO \n");
	}

	// 분류체계
	if (needClaz) {
	    if (sqlClaz != null) {
		buffer.append(" AND M.IDA2A2 = CL.IDA3B5 \n");
		buffer.append(" AND CL.IDA3A5 = C.IDA2A2 \n");
	    } else {
		buffer.append(" AND M.IDA2A2 = CL.IDA3B5(+) \n");
		buffer.append(" AND CL.IDA3A5 = C.IDA2A2(+) \n");
	    }
	}
	// 프로젝트
	if (needProject) {
	    if (sqlProject != null) {
		buffer.append(" AND P.BRANCHIDITERATIONINFO = PJL.BRANCHIDA3B5 \n");
		buffer.append(" AND PJL.IDA3A5 = PJ.IDA2A2 \n");
	    } else {
		buffer.append(" AND P.BRANCHIDITERATIONINFO = PJL.BRANCHIDA3B5(+) \n");
		buffer.append(" AND PJL.IDA3A5 = PJ.IDA2A2(+) \n");
	    }
	}
	// EO
	if (needEO) {
	    if (sqlEO != null) {
		/*
	         * buffer.append(" AND P.BRANCHIDITERATIONINFO = BEH.BRANCHIDITERATIONINFO \n");
	         */
		buffer.append(" AND M.WTPARTNUMBER = BEH.ECOITEMCODE \n");
		buffer.append(" AND P.VERSIONIDA2VERSIONINFO = BEH.BOMVERSION \n");
	    }
	    /*
	     * else { buffer.append(" AND P.BRANCHIDITERATIONINFO = BEH.BRANCHIDITERATIONINFO(+) \n"); }
	     */
	}
	// 4M
	else if (need4m) {
	    buffer.append(" AND M.WTPARTNUMBER = BEH.ECOITEMCODE \n");
	    buffer.append(" AND P.VERSIONIDA2VERSIONINFO = BEH.BOMVERSION \n");
	}

	// 원재료
	if (needMater) {
	    buffer.append(" AND TO_NUMBER(P." + materPartSpecEnum.getColumnName() + ") = MM.IDA2A2(+) \n");
	}

	// Die에 연결된 제품 검색
	if (needPartProd) {
	    buffer.append(" AND M.IDA2A2 = HD.IDA3B5 \n");
	    buffer.append(" AND PROD.IDA2A2 = HD.IDA3A5 \n");
	}

	// ////////////////////////////////////////////////////
	// 연계 검색 조건절 시작
	// ////////////////////////////////////////////////////

	// 분류체계
	if (needClaz && sqlClaz != null) {
	    buffer.append(sqlClaz);
	}
	// 도면 검색
	if (needEPM && sqlEPM != null) {
	    buffer.append(sqlEPM);
	}
	// 프로젝트
	if (needProject && sqlProject != null) {
	    buffer.append(sqlProject);
	}
	// EO
	if (needEO && sqlEO != null) {
	    buffer.append(sqlEO);
	}

	// 원재료 처리
	if (needMater && sqlMater != null) {
	    buffer.append(sqlMater);
	}
	// Die에 연결된 제품 검색
	if (needPartProd && sqlPartProd != null) {
	    buffer.append(sqlPartProd);
	}

	// //////////////
	// My Part 관련 쿼리
	// //////////////
	WTUser loginUser = (WTUser) SessionHelper.manager.getPrincipal();
	String workerOid = KETObjectUtil.getOidString(loginUser);

	if ("Y".equals(partSearchMainDTO.getIsCreateByme())) {
	    buffer.append(" AND P.IDA3D2ITERATIONINFO = '" + KETObjectUtil.getIda2a2(loginUser) + "' \n");
	} else {
	    if ("Y".equals(partSearchMainDTO.getFromMyPart())) {
		// buffer.append("");
		buffer.append(" AND M.WTPARTNUMBER IN ( \n");

		// buffer.append("               SELECT SUBSTR(bomlink.beforepartoid, INSTR(bomlink.beforepartoid, ':')+1, LENGTH(bomlink.beforepartoid)) AS PART_IDA2A2 \n");
		buffer.append("               SELECT bomheader.ECOITEMCODE  \n");
		buffer.append("                 FROM KETProdChangeActivity eca \n");
		buffer.append("                    , KETProdECABomLink bomlink \n");
		buffer.append("                    , ( \n");
		buffer.append("                       SELECT a.ida2a2, a.ecoitemcode \n");
		buffer.append("                         FROM KETBomEcoHeader a \n");
		buffer.append("                       UNION ALL \n");
		buffer.append("                       SELECT b.ida2a2, b.ecoitemcode \n");
		buffer.append("                         FROM KETBomHeader b \n");
		buffer.append("                      )bomheader \n");
		buffer.append("                WHERE eca.ida2a2 = bomlink.ida3b5 \n");
		buffer.append("                  AND bomlink.ida3a5 = bomheader.ida2a2 \n");
		buffer.append("                  AND ECA.WORKERID = '" + workerOid + "' \n");

		buffer.append("               UNION ALL \n");

		// buffer.append("               SELECT SUBSTR(bomlink.beforepartoid, INSTR(bomlink.beforepartoid, ':')+1, LENGTH(bomlink.beforepartoid)) AS PART_IDA2A2 \n");
		buffer.append("               SELECT bomheader.ECOITEMCODE  \n");
		buffer.append("                 FROM KETMoldChangeActivity eca \n");
		buffer.append("                    , KETMoldECABomLink bomlink \n");
		buffer.append("                    , ( \n");
		buffer.append("                       SELECT a.ida2a2, a.ecoitemcode \n");
		buffer.append("                         FROM KETBomEcoHeader a \n");
		buffer.append("                       UNION ALL \n");
		buffer.append("                       SELECT b.ida2a2, b.ecoitemcode \n");
		buffer.append("                         FROM KETBomHeader b \n");
		buffer.append("                      )bomheader \n");
		buffer.append("                WHERE eca.ida2a2 = bomlink.ida3b5 \n");
		buffer.append("                  AND bomlink.ida3a5 = bomheader.ida2a2 \n");
		buffer.append("                  AND ECA.WORKERID = '" + workerOid + "' \n");
		buffer.append("              ) \n");
	    }
	}

	// ////////////////////////////////////////////////////
	// 연계 검색 조건절 끝
	// ////////////////////////////////////////////////////

	// 사양정보
	if (true) {
	    for (PartSpecEnum partSpecEnum : specEnums) {
		if (PartSpecEnum.SpMaterialInfo == partSpecEnum || PartSpecEnum.SpMaterNotFe == partSpecEnum) {
		    // 원재료는 별도로 처리함.
		    continue;
		}
		String value = BeanUtils.getProperty(partSearchMainDTO, partSpecEnum.getAttrCode());
		if (StringUtils.isNotEmpty(value)) {
		    if (partSpecEnum.getAttrMultiSelect()) {
			String[] keywordAry = value.split(",");
			if (keywordAry != null && keywordAry.length > 0) {
			    buffer.append(" AND ( ");
			    for (int k = 0; k < keywordAry.length; k++) {
				String keyword = keywordAry[k].trim();
				if (k == 0)
				    buffer.append("    P." + partSpecEnum.getColumnName() + " LIKE  '%" + keyword + "%'").append(" \n");
				else
				    buffer.append(" OR P." + partSpecEnum.getColumnName() + " LIKE  '%" + keyword + "%'").append(" \n");
			    }
			    buffer.append(" ) ");
			}
		    } else {
			buffer.append(" AND ")
			        .append(KETQueryUtil.getSqlQueryForMultiSearch("P." + partSpecEnum.getColumnName(), value, false))
			        .append(" \n");
		    }
		}
	    }
	}

	// 사업부
	String division = partSearchMainDTO.getDivision();
	System.out.println(division);
	if (StringUtils.isNotEmpty(partSearchMainDTO.getDivision())) {
	    buffer.append(" AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("C.DIVISIONFLAG", partSearchMainDTO.getDivision(), false))
		    .append(" \n");
	}

	// 부품 유형
	if (StringUtils.isNotEmpty(partSearchMainDTO.getSpPartType()) && partSearchMainDTO.getSpPartType().indexOf("A") == -1) {
	    buffer.append(" AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("P." + PartSpecEnum.SpPartType.getColumnName(),
		            partSearchMainDTO.getSpPartType(), false)).append(" \n");
	}
	// 기타도 검색되도록 수정함.
	// else {
	// buffer.append(" AND P." + PartSpecEnum.SpPartType.getColumnName() + " != 'O' \n"); // 기타 부품 검색에서 제외 // PTC_STR_3TYPEINFOWTPART
	// }

	// YAZAKI 여부
	if (StringUtils.isNotEmpty(partSearchMainDTO.getSpIsYazaki()) && partSearchMainDTO.getSpIsYazaki().indexOf("YES") != -1) {
	    buffer.append(" AND P." + PartSpecEnum.SpIsYazaki.getColumnName() + " = 'YES' \n");
	}

	// 개발단계
	if (StringUtils.isNotEmpty(partSearchMainDTO.getSpPartDevLevel())) {
	    buffer.append(" AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("P." + PartSpecEnum.SpPartDevLevel.getColumnName(),
		            partSearchMainDTO.getSpPartDevLevel(), false)).append(" \n");
	}

	// 부품 번호
	if (StringUtils.isNotEmpty(partSearchMainDTO.getPartNumber())) {
	    buffer.append(" AND ")
		    .append(KETQueryUtil
		            .getSqlQueryForMultiSearch("M.WTPARTNUMBER", partSearchMainDTO.getPartNumber().toUpperCase(), false))
		    .append(" \n");
	}

	// 부품 명
	if (StringUtils.isNotEmpty(partSearchMainDTO.getPartName())) {
	    buffer.append(" AND ").append(KETQueryUtil.getSqlQueryForMultiSearch("M.NAME", partSearchMainDTO.getPartName(), true))
		    .append(" \n");
	}

	// 부품 리비전
	if (StringUtils.isNotEmpty(partSearchMainDTO.getSpPartRevision())) {
	    buffer.append(" AND P." + PartSpecEnum.SpPartRevision.getColumnName() + " = '"
		    + partSearchMainDTO.getSpPartRevision().toUpperCase() + "' \n");
	}

	// 부품 상태
	if (StringUtils.isNotEmpty(partSearchMainDTO.getPartStateCode())) {
	    buffer.append(" AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("P.STATESTATE", partSearchMainDTO.getPartStateCode(), false))
		    .append(" \n");
	}

	// 작성자
	if (StringUtils.isNotEmpty(partSearchMainDTO.getCreatorOid())) {
	    buffer.append(" AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("P.ida3d2iterationinfo",
		            KETParamMapUtil.OidToString(partSearchMainDTO.getCreatorOid()), false)).append(" \n");
	}

	buffer.append(CommonUtil.ketsUserListWhereStr("P.ida3d2iterationinfo"));

	// 작성일자(from)가 있는 경우
	if (StringUtils.isNotEmpty(partSearchMainDTO.getCreateStartDate())) {
	    String create_start = partSearchMainDTO.getCreateStartDate();
	    create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
	    buffer.append(" AND P.createstampa2 >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
	}

	// 작성일자(to)가 있는 경우
	if (StringUtils.isNotEmpty(partSearchMainDTO.getCreateEndDate())) {
	    String create_end = partSearchMainDTO.getCreateEndDate();
	    create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
	    buffer.append(" AND P.createstampa2 <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
	}

	if (withOrderBy) {
	    buffer.append(" ORDER BY " + sortName + " " + sortDirection + " \n");
	}

	return buffer.toString();
    }

    private String getSearchPartQueryByPartOid(String query) {

	StringBuffer sb = new StringBuffer();

	sb.append(" SELECT partIda2a2 FROM ( \n");
	sb.append(query);
	sb.append(" ) \n");

	return sb.toString();
    }

    // get part claz attribute info search result
    public List<String> searchUniqueClazAttrOidList(List<KETPartClassification> clazList) throws Exception {

	StringBuffer buffer = new StringBuffer();

	for (int k = 0; k < clazList.size(); k++) {

	    KETPartClassification claz = clazList.get(k);

	    if (k == 0)
		buffer.append(sigleQueto(CommonUtil.getOIDLongValue2Str(claz)));
	    else
		buffer.append("," + sigleQueto(CommonUtil.getOIDLongValue2Str(claz)));
	}

	return searchUniqueClazAttrOidList(buffer.toString());
    }

    private List<String> searchUniqueClazAttrOidList(String oidStrArray) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	StringBuffer buffer = new StringBuffer();
	buffer.append("SELECT DISTINCT A.CLASSNAMEA2A2 || ':' || A.IDA2A2 OID \n");
	buffer.append("	FROM KETPartClassAttrLink L, KETPartClassification C, KETPartAttribute A \n");
	buffer.append("	WHERE L.IDA3B5 = C.IDA2A2 \n");
	buffer.append("	AND L.IDA3A5 = A.IDA2A2 \n");
	buffer.append("	AND A.ATTRCODE != '" + PartSpecEnum.SpProjectNo.getAttrCode() + "' \n");
	buffer.append("	AND ( C.IDA2A2 IN  \n");
	buffer.append("	( \n");
	buffer.append("	  SELECT IDA2A2 \n");
	buffer.append("	  FROM \n");
	buffer.append("	  ( SELECT C.IDA2A2 \n");
	buffer.append("	   , CONNECT_BY_ISLEAF AS LEAF \n");
	buffer.append("	    FROM KETPartClassification C \n");
	buffer.append("	    WHERE 1 = 1     \n");
	buffer.append("	    START WITH IDA3A3 IN ( ");

	buffer.append(oidStrArray);

	buffer.append("	    ) \n");
	buffer.append("	    CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	buffer.append("	  )WHERE LEAF = 1 \n");
	buffer.append("	) OR C.IDA2A2 IN ( ");

	buffer.append(oidStrArray);

	buffer.append("   ) \n");
	buffer.append(" ) \n");

	List<String> oidList = oDaoManager.queryForList(buffer.toString(), new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("OID");
	    }
	});

	return oidList;
    }

    private List<String> searchUniqueClazOidList(String oidStrArray) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	StringBuffer buffer = new StringBuffer();
	buffer.append("SELECT DISTINCT C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID \n");
	buffer.append("	FROM KETPartClassAttrLink L, KETPartClassification C, KETPartAttribute A \n");
	buffer.append("	WHERE L.IDA3B5 = C.IDA2A2 \n");
	buffer.append("	AND L.IDA3A5 = A.IDA2A2 \n");
	buffer.append("	AND A.ATTRCODE != '" + PartSpecEnum.SpProjectNo.getAttrCode() + "' \n");
	buffer.append("	AND ( C.IDA2A2 IN  \n");
	buffer.append("	( \n");
	buffer.append("	  SELECT IDA2A2 \n");
	buffer.append("	  FROM \n");
	buffer.append("	  ( SELECT C.IDA2A2 \n");
	buffer.append("	   , CONNECT_BY_ISLEAF AS LEAF \n");
	buffer.append("	    FROM KETPartClassification C \n");
	buffer.append("	    WHERE 1 = 1     \n");
	buffer.append("	    START WITH IDA3A3 IN ( ");

	buffer.append(oidStrArray);

	buffer.append("	    ) \n");
	buffer.append("	    CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	buffer.append("	  )WHERE LEAF = 1 \n");
	buffer.append("	) OR C.IDA2A2 IN ( ");

	buffer.append(oidStrArray);

	buffer.append("   ) \n");
	buffer.append(" ) \n");

	List<String> oidList = oDaoManager.queryForList(buffer.toString(), new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("OID");
	    }
	});

	return oidList;
    }

    private List<String> searchUniqueSubClazOidList(String oidStrArray) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	StringBuffer buffer = new StringBuffer();

	buffer.append("  SELECT DISTINCT C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID  \n");
	buffer.append("  FROM KETPartClassification C \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND C.IDA2A2 IN \n");
	buffer.append("  ( \n");
	buffer.append("      SELECT IDA2A2 \n");
	buffer.append("      FROM \n");
	buffer.append("      ( SELECT C.IDA2A2 \n");
	buffer.append("       , CONNECT_BY_ISLEAF AS LEAF \n");
	buffer.append("        FROM KETPartClassification C \n");
	buffer.append("        WHERE 1 = 1 \n");
	buffer.append("        START WITH IDA3A3 IN ( " + oidStrArray + " ) \n");
	buffer.append("        CONNECT BY PRIOR IDA2A2 = IDA3A3 \n");
	buffer.append("     )WHERE LEAF = 1 \n");
	buffer.append("  ) \n");
	buffer.append("  UNION \n");
	buffer.append("  SELECT DISTINCT C.CLASSNAMEA2A2 || ':' || C.IDA2A2 OID \n");
	buffer.append("  FROM KETPartClassification C \n");
	buffer.append("  WHERE 1 = 1 \n");
	buffer.append("  AND C.IDA2A2 IN  ( " + oidStrArray + " ) \n");
	buffer.append("  AND C.IDA3A3 NOT IN  ( " + oidStrArray + " ) \n");

	List<String> oidList = oDaoManager.queryForList(buffer.toString(), new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("OID");
	    }
	});

	return oidList;
    }

    private String sigleQueto(String str) {
	return "'" + str + "'";
    }

    /**
     * EO NO
     * 
     * @param branchId
     * @return
     * @throws Exception
     * @메소드명 : searchEONo
     * @작성자 : yjlee
     * @작성일 : 2014. 10. 5.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String searchEONo(long branchId) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();

	buffer.append(" SELECT DISTINCT BEH.ECOHEADERNUMBER AS ecoNo \n");
	buffer.append(" FROM WTPART P \n");
	buffer.append("    , WTPARTMASTER M \n");
	buffer.append("    , ( \n");
	buffer.append("     SELECT bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	buffer.append("       FROM KETProdChangeOrder eco \n");
	buffer.append("          , KETProdChangeActivity eca \n");
	buffer.append("          , ( \n");
	buffer.append("             SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	buffer.append("               FROM KETProdECABomLink \n");
	buffer.append("            ) bomlink \n");
	buffer.append("          , ( \n");
	buffer.append("             SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	buffer.append("               FROM KETBomEcoHeader a \n");
	buffer.append("            UNION ALL \n");
	buffer.append("             SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	buffer.append("               FROM KETBomHeader b \n");
	buffer.append("            )bomheader \n");
	buffer.append("      WHERE eco.ida2a2 = eca.ida3a8 \n");
	buffer.append("        AND eca.ida2a2 = bomlink.ida3b5 \n");
	buffer.append("        AND bomlink.ida3a5 = bomheader.ida2a2 \n");
	buffer.append("     UNION ALL \n");
	buffer.append("     SELECT bomheader.ECOHEADERNUMBER, bomheader.ECOITEMCODE, bomheader.BOMVERSION \n");
	buffer.append("       FROM KETMoldChangeOrder eco \n");
	buffer.append("          , KETMoldChangeActivity eca \n");
	buffer.append("          , ( \n");
	buffer.append("             SELECT ida3b5, ida3a5, DECODE(NVL(afterversion, ''), '', preversion, afterversion) AS afterversion \n");
	buffer.append("               FROM KETMoldECABomLink \n");
	buffer.append("            ) bomlink \n");
	buffer.append("          , ( \n");
	buffer.append("             SELECT a.ida2a2, a.ECOHEADERNUMBER, a.ECOITEMCODE, a.BOMVERSION \n");
	buffer.append("               FROM KETBomEcoHeader a \n");
	buffer.append("            UNION ALL \n");
	buffer.append("             SELECT b.ida2a2, b.ECOHEADERNUMBER, b.NEWBOMCODE AS ECOITEMCODE, b.BOMVERSION \n");
	buffer.append("               FROM KETBomHeader b \n");
	buffer.append("            )bomheader \n");
	buffer.append("      WHERE eco.ida2a2 = eca.ida3a8 \n");
	buffer.append("        AND eca.ida2a2 = bomlink.ida3b5 \n");
	buffer.append("        AND bomlink.ida3a5 = bomheader.ida2a2 \n");
	buffer.append("      ) BEH \n");
	buffer.append(" WHERE 1=1 \n");
	buffer.append(" AND M.IDA2A2 = P.IDA3MASTERREFERENCE \n");
	buffer.append(" AND P.LATESTITERATIONINFO = 1 \n");
	buffer.append(" AND M.WTPARTNUMBER = BEH.ECOITEMCODE \n");
	buffer.append(" AND P.VERSIONIDA2VERSIONINFO = BEH.BOMVERSION \n");
	buffer.append(" AND P.BRANCHIDITERATIONINFO = ? \n");
	buffer.append(" ORDER BY ecoNo DESC \n"); // 가장 최신의 eco를 우선순위로 가져오기 위함(part상세 조회에서 수정버튼 권한관련 필요)

	aBindList.add(branchId);

	List<String> eoNoList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("ecoNo");
	    }
	});

	if (eoNoList.size() == 0) {
	    return null;
	} else {
	    return eoNoList.get(0);
	}
    }

    public String getPartnerName(String partnerNo) throws Exception {

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List aBindList = new ArrayList();

	StringBuffer buffer = new StringBuffer();
	buffer.append("SELECT partnerName \n");
	buffer.append("FROM KETPartner \n");
	buffer.append("WHERE partnerNo = ? \n");

	aBindList.add(partnerNo);

	List<String> partnerNameList = oDaoManager.queryForList(buffer.toString(), aBindList, new RowSetStrategy<String>() {

	    @Override
	    public String mapRow(ResultSet rs) throws SQLException {

		return rs.getString("partnerName");
	    }
	});

	if (partnerNameList.size() == 0) {
	    return null;
	} else {
	    return partnerNameList.get(0);
	}
    }

    // Material Type by Material Maker
    public List<CommonCodeDTO> getMaterialMaker(String materialGubun, String materalMakerCode) throws Exception {

	List<CommonCodeDTO> list = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();

	    buffer.append(" SELECT CODE VALUE, NAME TEXT \n");
	    buffer.append(" FROM NUMBERCODE \n");
	    buffer.append(" WHERE CODETYPE = 'MATERIALTYPE' \n");
	    buffer.append(" AND IDA2A2 IN \n");
	    buffer.append(" ( \n");
	    buffer.append("    SELECT IDA3B3 \n");
	    buffer.append("    FROM MOLDMATERIAL \n");
	    buffer.append("    WHERE MATERIAL = ? \n");
	    if (StringUtils.isNotEmpty(materalMakerCode)) {
		buffer.append("    AND  IDA3A3 IN  \n");
		buffer.append("    ( \n");
		buffer.append("        SELECT IDA2A2  \n");
		buffer.append("        FROM NUMBERCODE  \n");
		buffer.append("        WHERE CODETYPE = 'MATERIALMAKER'  \n");
		buffer.append("        AND CODE = ? \n");
		buffer.append("    ) \n");
	    }
	    buffer.append(" ) \n");
	    buffer.append(" ORDER BY NAME \n");

	    aBind.add(materialGubun);
	    if (StringUtils.isNotEmpty(materalMakerCode)) {
		aBind.add(materalMakerCode);
	    }

	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new RowSetStrategy<CommonCodeDTO>() {

		@Override
		public CommonCodeDTO mapRow(ResultSet rs) throws SQLException {

		    CommonCodeDTO dto = new CommonCodeDTO();

		    dto.setValue(rs.getString("VALUE"));
		    dto.setText(rs.getString("TEXT"));

		    return dto;
		}

	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return list;
    }

    // MOLDMATERIAL by Maker, Type
    public List<CommonCodeDTO> getMaterial(String materialGubun, String materalMakerCode, String materalTypeCode) throws Exception {

	List<CommonCodeDTO> list = null;
	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();

	try {

	    List aBind = new ArrayList();

	    StringBuffer buffer = new StringBuffer();

	    buffer.append(" SELECT IDA2A2 VALUE, GRADE TEXT \n");
	    buffer.append(" FROM MOLDMATERIAL \n");
	    buffer.append(" WHERE MATERIAL = ? \n");
	    if (CommonUtils.isNotEmpty(materalMakerCode)) {
		buffer.append(" AND  IDA3A3 IN \n");
		buffer.append(" ( \n");
		buffer.append("     SELECT IDA2A2 \n");
		buffer.append("     FROM NUMBERCODE \n");
		buffer.append("     WHERE CODETYPE = 'MATERIALMAKER' \n");
		buffer.append("     AND CODE = ? \n");
		buffer.append(" ) \n");
	    }
	    if (CommonUtils.isNotEmpty(materalTypeCode)) {
		buffer.append(" AND  IDA3B3 IN \n");
		buffer.append(" ( \n");
		buffer.append("     SELECT IDA2A2 \n");
		buffer.append("     FROM NUMBERCODE \n");
		buffer.append("     WHERE CODETYPE = 'MATERIALTYPE' \n");
		buffer.append("     AND CODE = ? \n");
		buffer.append(" ) \n");
		buffer.append(" ORDER BY GRADE \n");
	    }

	    aBind.add(materialGubun);

	    if (CommonUtils.isNotEmpty(materalMakerCode)) {
		aBind.add(materalMakerCode);
	    }
	    if (CommonUtils.isNotEmpty(materalTypeCode)) {
		aBind.add(materalTypeCode);
	    }

	    String sSql = buffer.toString();
	    list = oDaoManager.queryForList(sSql, aBind, new RowSetStrategy<CommonCodeDTO>() {

		@Override
		public CommonCodeDTO mapRow(ResultSet rs) throws SQLException {

		    CommonCodeDTO dto = new CommonCodeDTO();

		    dto.setValue(rs.getString("VALUE"));
		    dto.setText(rs.getString("TEXT"));

		    return dto;
		}

	    });

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return list;
    }
    
    public List<Map<String, Object>> getMassPlanByProjectInfo(String partNo) throws Exception {
	return getResultList(getMassPlanByProjectQuery(partNo));
    }
    
    public List<Map<String, Object>> getMassPlanByEcoInfo(String partNo) throws Exception {
	return getResultList(getMassPlanByEcoQuery(partNo));
    }
    
    public List<Map<String, Object>> getUpdateTargetMassPartList() throws Exception {
	return getResultList(getUpdateTargetMassPartListQuery());
    }
    
    public List<Map<String, Object>> getMigMassDataList() throws Exception {
	return getResultList(getMigMassDataListQuery());
    }
    
    public List<Map<String, Object>> getExtractBomInfo(String partOid) throws Exception {
	return getResultList(getExtractBomQuery(partOid));
    }
    
    public List<Map<String, Object>> getProductList() throws Exception {
	return getResultList(getProductListQuery());
    }
    
    public List<Map<String, Object>> getMassPartDataList() throws Exception {
	return getResultList(getMassPartDataListQuery());
    }
    
    private String getMassPartDataListQuery(){
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT PARTNO FROM KETPARTMASSPLAN WHERE MASSTARTDATE IS NOT NULL");
	return sql.toString();
    }
    
    private String getProductListQuery(){
	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT PJTNO, PJTNAME,PLANENDDATE42 AS DR6ENDDATE, (CASE WHEN EXECENDDATE > PLANENDDATE THEN PLANENDDATE ELSE EXECENDDATE END) as EXECENDDATE,PI.PNUM AS PARTNO,DECODE(IDA3F9,1077705899,'양산','Pilot') AS PROCESS  \n");
	sql.append(" 	  FROM PRODUCTPROJECT      PJT                                                                                                                                                                                  \n");
	sql.append(" 		        ,E3PSPROJECTMASTER   E3PSPJTMST                                                                                                                                                                     \n");
	sql.append(" 		        ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                                                                                       \n");
	sql.append(" 		        ,PRODUCTINFO PI                                                                                                                                                                                     \n");
	sql.append(" 		   WHERE 1=1                                                                                                                                                                                                \n");
	sql.append(" 		     AND PJT.LASTEST       = 1                                                                                                                                                                              \n");
	sql.append(" 		     AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                                                                                         \n");
	sql.append(" 		     AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                                                                                                                                              \n");
	sql.append(" 		     AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                                                                                                \n");
	sql.append(" 		     AND PJT.IDA2A2 = PI.IDA3A3                                                                                                                                                                             \n");                    
	sql.append(" 		     AND IDA3F9 IN (19875,1077705899)                                                                                                                                                                       \n");
	sql.append(" 		     AND (CASE WHEN EXECENDDATE > PLANENDDATE THEN PLANENDDATE ELSE EXECENDDATE END ,PNUM) IN (                                                                                                             \n");
	sql.append(" 		   SELECT min(CASE WHEN EXECENDDATE > PLANENDDATE THEN PLANENDDATE ELSE EXECENDDATE END ), PNUM                                                                                                             \n");
	sql.append(" 		   FROM PRODUCTPROJECT      PJT                                                                                                                                                                             \n");
	sql.append(" 		        ,E3PSPROJECTMASTER   E3PSPJTMST                                                                                                                                                                     \n");
	sql.append(" 		        ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                                                                                       \n");
	sql.append(" 		        ,PRODUCTINFO PI                                                                                                                                                                                     \n");
	sql.append(" 		   WHERE 1=1                                                                                                                                                                                                \n");
	sql.append(" 		     AND PJT.LASTEST       = 1                                                                                                                                                                              \n");
	sql.append(" 		     AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                                                                                         \n");
	sql.append(" 		     AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                                                                                                                                              \n");
	sql.append(" 		     AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                                                                                                \n");
	sql.append(" 		     AND PJT.IDA2A2 = PI.IDA3A3                                                                                                                                                                             \n");                    
	sql.append(" 		     AND IDA3F9 IN (19875,1077705899)                                                                                                                                                                       \n");
	sql.append(" 		     AND EXECENDDATE IS NOT NULL                                                                                                                                                                            \n");
	sql.append(" 		     GROUP BY PNUM                      )                                                                                                                                                                   \n");
	return sql.toString();
    }
    
    private String getExtractBomQuery(String partOid){
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT LEV,BOM.PARTNO,LAST_WTPART_OID FROM (                                                                                                                                                      \n");
	sql.append("         SELECT LEVEL AS LEV,                                                                                                                                                                     \n");
	sql.append("	                    BOM.PARTNO,                                                                                                                                                               \n");
	sql.append("	                    BOM.PARENTITEMCODE,                                                                                                                                                       \n");
	sql.append("	                    BOM.PARENT_WTPART_OID,                                                                                                                                                    \n");
	sql.append("	                    BOM.WTMASER_OID,                                                                                                                                                          \n");
	sql.append("	                    BOM.VERSION_WTPART_OID,                                                                                                                                                   \n");
	sql.append("	                    LAST_WTPART_OID                                                                                                                                                           \n");
	sql.append("	               FROM (                                                                                                                                                                         \n");
	sql.append("	                      SELECT WTPARTNUMBER AS PARTNO, '' AS PARENTITEMCODE,                                                                                                                    \n");
	sql.append("	                             0 AS ITEMSEQ, 0 AS PARENT_WTPART_OID,0 AS WTMASER_OID, '0' AS VERSION_WTPART_OID,                                                                                \n");
	sql.append("	                             P.IDA2A2 AS LAST_WTPART_OID FROM WTPART P, WTPARTMASTER M                                                                                                        \n");
	sql.append("	                       WHERE P.IDA3MASTERREFERENCE = M.IDA2A2                                                                                                                                 \n");
	sql.append("	    	                AND P.IDA2A2 = ('"+partOid+"')                                                  					 	                                                                      \n");
	sql.append("	                   UNION ALL                                                                                                                                                                  \n");
	sql.append("	                      SELECT B.CHILDITEMCODE AS PARTNO ,B.PARENTITEMCODE ,B.ITEMSEQ ,B.IDA3A5  AS PARENT_WTPART_OID, B.IDA3B5  AS WTMASER_OID ,B.VERSIONITEMCODE AS VERSION_WTPART_OID        \n");
	sql.append("	                            ,(SELECT MAX(P.IDA2A2) FROM WTPART P WHERE P.LATESTITERATIONINFO = 1  AND P.STATECHECKOUTINFO != 'WRK'                                                            \n");
	sql.append("	                                 AND P.STATESTATE = 'APPROVED' AND P.IDA3MASTERREFERENCE = B.IDA3B5 ) AS LAST_WTPART_OID                                                                      \n");
	sql.append("	                        FROM KETPARTUSAGELINK B ) BOM                                                                                                                                         \n");
	sql.append("	                  START WITH BOM.PARENT_WTPART_OID  = 0                                                                                                                                       \n");
	sql.append("	            CONNECT BY PRIOR BOM.LAST_WTPART_OID =  BOM.PARENT_WTPART_OID                                                                                                                     \n");
	sql.append("	           ORDER SIBLINGS BY ITEMSEQ                                                                                                                                                          \n");
	sql.append("	           ) BOM, WTPART A, WTPARTMASTER B                                                                                                                                                    \n");
	sql.append("	     WHERE BOM.LAST_WTPART_OID = A.IDA2A2                                                                                                                                                     \n");
	sql.append("	       AND A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                                                                                   \n");
	return sql.toString();
    }
    
    private String getMigMassDataListQuery(){
	StringBuffer sql = new StringBuffer();
	sql.append(" SELECT WTPARTNUMBER,TO_CHAR(MPDATE,'YYYY-MM-DD') AS MPDATE FROM SSP_PM_TMP");
	return sql.toString();
    }
    
    private String getUpdateTargetMassPartListQuery(){
	StringBuffer sql = new StringBuffer();
	sql.append("SELECT WTPARTNUMBER AS PARTNO,VERSIONIDA2VERSIONINFO AS VERSION ,P.CLASSNAMEA2A2||':'||P.IDA2A2 AS PARTOID, STATESTATE                           ");
	sql.append(" FROM (SELECT * FROM WTPARTMASTER WHERE WTPARTNUMBER NOT IN (SELECT PARTNO FROM KETPARTMASSPLAN WHERE MASSTARTDATE IS NOT NULL)) M                                                                                                        ");
	sql.append("	        , WTPART P                                                                                                     ");
	sql.append("	     ,( SELECT J.IDA2A2, MAX(BRANCHIDITERATIONINFO) BRANCHIDITERATIONINFO                                              ");
	sql.append("	        FROM WTPART I,(SELECT * FROM WTPARTMASTER WHERE WTPARTNUMBER NOT IN (SELECT PARTNO FROM KETPARTMASSPLAN WHERE MASSTARTDATE IS NOT NULL)) J                                                                         ");
	sql.append("	        WHERE I.IDA3MASTERREFERENCE = J.IDA2A2                                                                         ");
	sql.append("	             AND I.LATESTITERATIONINFO = 1                                                                             ");
	sql.append("	          AND I.STATECHECKOUTINFO != 'wrk'                                                                             ");
	sql.append("	          AND PTC_STR_3TYPEINFOWTPART NOT IN ('D','M')                                                                 ");
	sql.append("	        GROUP BY J.IDA2A2                                                                                              ");
	sql.append("	     ) MAXVERPART                                                                                                      ");
	sql.append("	     WHERE M.IDA2A2 = P.IDA3MASTERREFERENCE                                                                            ");
	sql.append("	     AND P.LATESTITERATIONINFO = 1                                                                                     ");
	sql.append("	     AND P.STATECHECKOUTINFO NOT IN ('wrk')                                                                            ");
	sql.append("	     AND M.IDA2A2 = MAXVERPART.IDA2A2                                                                                  ");
	sql.append("	     AND P.BRANCHIDITERATIONINFO = MAXVERPART.BRANCHIDITERATIONINFO                                                    ");
	sql.append("	     AND PTC_STR_3TYPEINFOWTPART NOT IN ('D','M')                                                                  ");
	
	return sql.toString();
    }
    
    private String getMassPlanByEcoQuery(String partNo){
	StringBuffer sql = new StringBuffer();
	
	sql.append(" SELECT TO_CHAR(BEH.COMPLETEDDATE,'YYYY-MM-DD') AS ECO_APPROVED_DATE,                                                                            \n  ");
	sql.append("        ECOID, VERSIONIDA2VERSIONINFO AS VERSION                                                                                                              \n  ");
	sql.append("   FROM WTPART A, WTPARTMASTER B,                                                                                          \n  ");
	sql.append("       (  SELECT WFM.COMPLETEDDATE, ECOITEMCODE, BOMVERSION,ECOID                                                          \n  ");
	sql.append("           FROM KETPRODCHANGEORDER ECO                                                                                     \n  ");
	sql.append("               ,KETPRODCHANGEACTIVITY ECA                                                                                  \n  ");
	sql.append("               , ( SELECT IDA3B5, IDA3A5, DECODE(NVL(AFTERVERSION, ''), '', PREVERSION, AFTERVERSION) AS AFTERVERSION      \n  ");
	sql.append("                     FROM KETPRODECABOMLINK                                                                                \n  ");
	sql.append("                 ) BOMLINK                                                                                                 \n  ");
	sql.append("               , ( SELECT A.IDA2A2, A.ECOHEADERNUMBER, A.ECOITEMCODE, A.BOMVERSION                                         \n  ");
	sql.append("                     FROM KETBOMECOHEADER A WHERE ECOITEMCODE = '" + partNo + "'                                                  \n  ");
	sql.append("                UNION ALL                                                                                                  \n  ");
	sql.append("                   SELECT B.IDA2A2, B.ECOHEADERNUMBER, B.NEWBOMCODE AS ECOITEMCODE, B.BOMVERSION                           \n  ");
	sql.append("                     FROM KETBOMHEADER B WHERE NEWBOMCODE = '" + partNo + "'                                                     \n  ");
	sql.append("                 )BOMHEADER                                                                                                \n  ");
	sql.append("               , KETWFMAPPROVALMASTER WFM                                                                                  \n  ");
	sql.append("         WHERE ECO.IDA2A2 = ECA.IDA3A8                                                                                     \n  ");
	sql.append("           AND ECA.IDA2A2 = BOMLINK.IDA3B5                                                                                 \n  ");
	sql.append("           AND BOMLINK.IDA3A5 = BOMHEADER.IDA2A2                                                                           \n  ");
	sql.append("           AND ECO.STATESTATE = 'APPROVED'                                                                                 \n  ");
	sql.append("           AND ECO.IDA2A2 = WFM.IDA3B4                                                                                     \n  ");
	sql.append("       ) BEH                                                                                                               \n  ");
	sql.append("  WHERE A.IDA3MASTERREFERENCE = B.IDA2A2                                                                                   \n  ");
	sql.append("    AND LATESTITERATIONINFO = '1'                                                                                          \n  ");
	sql.append("    AND VERSIONIDA2VERSIONINFO||WTPARTNUMBER IN ( SELECT MAX(VERSIONIDA2VERSIONINFO)||WTPARTNUMBER                         \n  ");
	sql.append("                                                    FROM WTPART A, WTPARTMASTER B                                          \n  ");
	sql.append("                                                   WHERE A.IDA3MASTERREFERENCE = B.IDA2A2                                  \n  ");
	sql.append("                                                     AND LATESTITERATIONINFO = '1'                                         \n  ");
	sql.append("                                                     AND A.PTC_STR_2TYPEINFOWTPART = '0'                                   \n  ");
	sql.append("                                                     AND A.PTC_STR_4TYPEINFOWTPART = 'PC004'                               \n  ");
	sql.append("                                                     AND A.PTC_STR_3TYPEINFOWTPART NOT IN ('D','M')                  \n  ");
	sql.append("                                                     AND WTPARTNUMBER =  '" + partNo + "'                                        \n  ");
	sql.append("                                                GROUP BY WTPARTNUMBER )                                                    \n  ");
	sql.append("   AND BEH.ECOITEMCODE = B.WTPARTNUMBER                                                                                    \n  ");
	sql.append("   AND BEH.BOMVERSION = A.VERSIONIDA2VERSIONINFO                                                                           \n  ");
	sql.append("   ORDER BY COMPLETEDDATE ASC  \n  ");
	
	return sql.toString();
    }
    
    private String getMassPlanByProjectQuery(String partNo) {
	StringBuffer sql = new StringBuffer();

	sql.append(" SELECT PJTNO, PJTNAME, TO_CHAR(DR6ENDDATE,'YYYY-MM-DD')  AS DR6ENDDATE, TO_CHAR(EXECENDDATE,'YYYY-MM-DD') AS  EXECENDDATE,PROCESS,STATESTATE           \n");
	sql.append("   FROM (                                                                                                                                \n");
	sql.append(" SELECT PJTNO, PJTNAME,PLANENDDATE42 AS DR6ENDDATE,(CASE WHEN EXECENDDATE > PLANENDDATE THEN PLANENDDATE ELSE EXECENDDATE END) as EXECENDDATE,PI.PNUM AS PARTNO,DECODE(IDA3F9,1077705899,'양산','Pilot') AS PROCESS, 'PRODUCT' AS GUBUN,STATESTATE      \n");
	sql.append("   FROM PRODUCTPROJECT      PJT                                                                                                          \n");
	sql.append("        ,E3PSPROJECTMASTER   E3PSPJTMST                                                                                                  \n");
	sql.append("        ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                    \n");
	sql.append("        ,PRODUCTINFO PI                                                                                                                  \n");
	sql.append("   WHERE 1=1                                                                                                                             \n");
	sql.append("     AND PJT.LASTEST       = 1                                                                                                           \n");
	sql.append("     AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                      \n");
	sql.append("     AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                                                                           \n");
	sql.append("     AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                             \n");
	sql.append("     AND PJT.IDA2A2 = PI.IDA3A3                                                                                                          \n");
	sql.append("     AND PI.PNUM = '" + partNo + "'                                                                                                            \n");
	sql.append("     AND IDA3F9 IN (19875,1077705899)                                                                                                    \n");
//	sql.append("     AND EXECENDDATE IS NOT NULL                                                                                                         \n");
	sql.append("     UNION                                                                                                                               \n");
	sql.append(" SELECT PJTNO, PJTNAME, PLANENDDATE42 AS DR6ENDDATE, CASE WHEN EXECENDDATE > PLANENDDATE THEN PLANENDDATE ELSE EXECENDDATE END as EXECENDDATE,MIINFO.PARTNO,DECODE(IDA3F9,1077705899,'양산','Pilot') AS PROCESS, '' AS GUBUN,STATESTATE         \n");
	sql.append("   FROM PRODUCTPROJECT      PJT                                                                                                          \n");
	sql.append("        ,E3PSPROJECTMASTER   E3PSPJTMST                                                                                                  \n");
	sql.append("        ,EXTENDSCHEDULEDATA  SCHEDULE                                                                                                    \n");
	sql.append("        ,MOLDITEMINFO MIINFO                                                                                                             \n");
	sql.append("   WHERE 1=1                                                                                                                             \n");
	sql.append("     AND PJT.LASTEST       = 1                                                                                                           \n");
	sql.append("     AND PJT.CHECKOUTSTATE <> 'c/o'                                                                                                      \n");
	sql.append("     AND PJT.IDA3B8        = E3PSPJTMST.IDA2A2                                                                                           \n");
	sql.append("     AND PJT.IDA3A8        = SCHEDULE.IDA2A2                                                                                             \n");
	sql.append("     AND PJT.IDA2A2 = MIINFO.IDA3A3                                                                                                      \n");
	sql.append("     AND SHRINKAGE = '신규'                                                                                                                \n");
	sql.append("     AND IDA3F9 IN (19875,1077705899)                                                                                                    \n");
	sql.append("     AND MIINFO.PARTNO = '" + partNo + "'                                                                                                    \n");
//	sql.append("     AND EXECENDDATE IS NOT NULL ) ORDER BY EXECENDDATE                                                                                \n");
	sql.append("     ) ORDER BY EXECENDDATE                                                                                \n");
	return sql.toString();
    }
    
    
    private List<Map<String, Object>> getResultList(String sql) throws Exception {
	List<Map<String, Object>> list = null;

	Statement stat = null;
	ResultSet rs = null;
	MethodContext mContext = MethodContext.getContext();
	WTConnection conn = null;

	try {
	    conn = (WTConnection) mContext.getConnection();
	    stat = conn.getConnection().createStatement();
	    rs = stat.executeQuery(sql);

	    list = ObjectUtil.manager.rsToList(rs);

	} catch (Exception e) {
	    e.printStackTrace();
	    throw new Exception(e);
	} finally {
	    if (rs != null) {
		rs.close();
	    }
	    if (stat != null) {
		stat.close();
	    }
	    if (DBProperties.FREE_CONNECTION_IMMEDIATE && !conn.isTransactionActive()) {
		MethodContext.getContext().freeConnection();
	    }
	}

	return list;
    }

    public static void main(String[] args) {
	long l = 63634573L;
	String a = String.valueOf(l);
	Kogger.debug(PartBaseDao.class, a);
    }

}
