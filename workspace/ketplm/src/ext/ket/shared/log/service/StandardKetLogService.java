package ext.ket.shared.log.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.common.dao.DaoFactory;
import e3ps.common.dao.DaoManager;
import e3ps.common.dao.RowSetStrategy;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import ext.ket.part.migration.dao.StampDaoFactory;
import ext.ket.part.migration.dao.StampDaoManager;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.log.entity.dto.EcmLogDTO;
import ext.ket.shared.log.entity.dto.EcmLogSearchDTO;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKetLogService extends StandardManager implements KetLogService {

    private static final long serialVersionUID = 1L;

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public static StandardKetLogService newStandardKetLogService() throws WTException {
	StandardKetLogService instance = new StandardKetLogService();
	instance.initialize();
	return instance;
    }

    // 로그 검색
    @Override
    public PageControl searchEcoLogList(EcmLogSearchDTO ecmLogSearchDTO, String pageTotalSize) throws Exception {
	

	DaoManager oDaoManager = DaoFactory.getInstance().getDaoManager();
	List alBindSql = new ArrayList();

	int totalCount = Integer.parseInt(pageTotalSize);
	if (totalCount < 0) {
	    boolean withOrderBy = false;
	    //
	    String sSql = getQueryByCondition(ecmLogSearchDTO, withOrderBy);
	    totalCount = oDaoManager.queryForCount(sSql);

	    // 총 개수가 0이면 굳이 paging 쿼리 실행할 필요가 없음.
	    if (totalCount == 0) {
		List<EcmLogDTO> searchResultList = new ArrayList<EcmLogDTO>();
		PageControl pageControl = new PageControl(ecmLogSearchDTO.getPage() + 1, searchResultList,
			ecmLogSearchDTO.getFormPage(), ecmLogSearchDTO.getFormPage(), totalCount);
		return pageControl;
	    }
	}

	String sSql = getQueryByCondition(ecmLogSearchDTO, true);
	
	sSql = oDaoManager.getPagingQuery(sSql, ecmLogSearchDTO.getPage(), ecmLogSearchDTO.getFormPage(), alBindSql);

	List<EcmLogDTO> searchResultList = oDaoManager.queryForList(sSql, alBindSql, new RowSetStrategy<EcmLogDTO>() {

	    @Override
	    public EcmLogDTO mapRow(ResultSet rs) throws SQLException {

		EcmLogDTO ecmLogDTO = new EcmLogDTO();

		ecmLogDTO.setEcoNo(rs.getString("ECONO"));
		ecmLogDTO.setEcoOid(rs.getString("ECOOID"));
		ecmLogDTO.setEcoIda2a2(rs.getString("ECOIDA2A2"));
		ecmLogDTO.setEcoState(rs.getString("ECOSTATE"));
		ecmLogDTO.setEcaName(rs.getString("ECANAME"));
		ecmLogDTO.setEcaOid(rs.getString("ECAOID"));
		ecmLogDTO.setEcaIda2a2(rs.getString("ECAIDA2A2"));
		ecmLogDTO.setEcaState(rs.getString("ECASTATE"));
		ecmLogDTO.setEventName(rs.getString("EVENTNAME"));
		ecmLogDTO.setEventResult(rs.getString("EVENTRESULT"));
		ecmLogDTO.setLogLog(rs.getString("LOGLOG"));
		ecmLogDTO.setErrMessage(rs.getString("ERRMESSAGE"));
		ecmLogDTO.setReEventResult(rs.getString("REEVENTRESULT"));
		
		ecmLogDTO.setChangeUserId(rs.getString("CHANGEUSERID"));
		ecmLogDTO.setChangeUserName(rs.getString("CHANGEUSERNAME"));
		
		ecmLogDTO.setLogDate(rs.getString("LOGDATE"));
		
		return ecmLogDTO;
	    }
	});

	PageControl pageControl = new PageControl(ecmLogSearchDTO.getPage() + 1, searchResultList, ecmLogSearchDTO.getFormPage(), ecmLogSearchDTO.getFormPage(), totalCount);
	return pageControl;
	
    }
    
    private String getQueryByCondition(EcmLogSearchDTO ecmLogSearchDTO, boolean withOrderBy) throws Exception {

	StringBuffer buffer = new StringBuffer();

	// /////////////////////////////////////
	// 실제 SQL문 시작
	// /////////////////////////////////////
	buffer.append(" SELECT  L.CHANGEUSERID, L.CHANGEUSERNAME, TO_CHAR(L.LOGDATE, 'yyyy-MM-dd HH24:MI:ss') LOGDATE, REPLACE(L.ECONO, 'ECO-', '') ECONO, L.EVENTNAME \n");
	buffer.append("         , L.EVENTRESULT, L.ERRMESSAGE, L.LOGLOG, L.ECOSTATE, L.ECOOID \n");
	buffer.append("         , L.ECOIDA2A2, L.REEVENTRESULT, L.ECAIDA2A2, L.ECANAME, L.ECAOID \n");
	buffer.append("         , L.ECASTATE \n");
	buffer.append(" FROM KETECMLOG L \n");
	buffer.append(" WHERE 1 =1 \n");

	// where 처리
	// ECO NO 검색
	if (StringUtils.isNotEmpty(ecmLogSearchDTO.getEcoNo())) {
	    buffer.append(" AND ")
		    .append(KETQueryUtil.getSqlQueryForMultiSearch("L.ECONO", "ECO-" + ecmLogSearchDTO.getEcoNo().toUpperCase(), false)).append(" \n");
	}
	
	// Event 결과 검색
	if (StringUtils.isNotEmpty(ecmLogSearchDTO.getEventResult())) {
	    buffer.append(" AND ")
	    .append(KETQueryUtil.getSqlQueryForMultiSearch("L.EVENTRESULT", ecmLogSearchDTO.getEventResult(), false)).append(" \n");
	}

	// 작성일자(from)가 있는 경우
	if (StringUtils.isNotEmpty(ecmLogSearchDTO.getCreateStartDate())) {
	    String create_start = ecmLogSearchDTO.getCreateStartDate();
	    create_start = create_start.substring(0, 4) + create_start.substring(5, 7) + create_start.substring(8, 10);
	    buffer.append(" AND L.LOGDATE >= TO_DATE('" + create_start + "' || '000000','YYYYMMDDHH24MISS')         \n");
	}

	// 작성일자(to)가 있는 경우
	if (StringUtils.isNotEmpty(ecmLogSearchDTO.getCreateEndDate())) {
	    String create_end = ecmLogSearchDTO.getCreateEndDate();
	    create_end = create_end.substring(0, 4) + create_end.substring(5, 7) + create_end.substring(8, 10);
	    buffer.append(" AND L.LOGDATE <= TO_DATE('" + create_end + "' || '235959','YYYYMMDDHH24MISS')           \n");
	}
	
	// 작성자
	if (StringUtils.isNotEmpty(ecmLogSearchDTO.getChangeUserName())) {
	    buffer.append(" AND ")
	    .append(KETQueryUtil.getSqlQueryForMultiSearch("L.CHANGEUSERNAME", ecmLogSearchDTO.getChangeUserName(), true)).append(" \n");
	}

	// Order by 처리
	if(withOrderBy){
	    String sortName = null;
	    String sortDirection = null;
	    if (!StringUtil.isTrimToEmpty(ecmLogSearchDTO.getSortName())) {
		if (ecmLogSearchDTO.getSortName().startsWith("-")) {
		    sortName = ecmLogSearchDTO.getSortName().substring(1);
		    sortDirection = " DESC";
		} else {
		    sortName = ecmLogSearchDTO.getSortName();
		    sortDirection = " ASC";
		}
	    } else {
		sortName = "LOGDATE";
		sortDirection = " DESC";
	    }

	    if (!StringUtil.isTrimToEmpty(ecmLogSearchDTO.getSortName())) {
		if (ecmLogSearchDTO.getSortName().startsWith("-")) {
		    buffer.append(" ORDER BY ").append(sortName).append(" " + sortDirection);
		} else {
		    buffer.append(" ORDER BY ").append(sortName).append(" " + sortDirection);
		}
	    } else {
		buffer.append(" ORDER BY ").append(sortName).append(" " + sortDirection);
	    }
	}

	Kogger.debug(getClass(), buffer.toString());
	return buffer.toString();
    }
    
    // 로그 상세 조회
    @Override
    public List<EcmLogDTO> viewDetailEcoLog(EcmLogDTO dto, Locale locale) throws Exception {
	return null;
    }

    // 에러 복구 실행
    public boolean recoverEcoError(EcmLogDTO dto) throws Exception {

	boolean ecmLog = false;

	try {

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return ecmLog;
    }

    // 로그 삭제
    @Override
    public void deleteLog(EcmLogDTO dto, Locale locale) throws Exception {

    }
    
    // 로그 저장
    public void insertLog(EcmLogDTO dto) throws Exception {
	
	StampDaoManager oDaoManager = StampDaoFactory.getInstance().getDaoManager();
	
	try {
	    
	    List aBind = new ArrayList();
	    
	    StringBuffer buffer = new StringBuffer();
	    buffer.append(" INSERT INTO KETECMLOG(CHANGEUSERID, CHANGEUSERNAME, LOGDATE, ECONO, EVENTNAME \n");
	    buffer.append(" , EVENTRESULT, ERRMESSAGE, LOGLOG, ECOSTATE, ECOOID \n");
	    buffer.append(" , ECOIDA2A2, REEVENTRESULT, ECAIDA2A2, ECANAME, ECAOID \n");
	    buffer.append(" , ECASTATE ) \n");
	    buffer.append(" VALUES ( ?,?,SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,? ) \n");
	    
	    aBind.add(dto.getChangeUserId());
	    aBind.add(dto.getChangeUserName());
//	    aBind.add(dto.getLogDate());
	    aBind.add(dto.getEcoNo());
	    aBind.add(dto.getEventName());
	    
	    aBind.add(dto.getEventResult());
	    aBind.add(dto.getErrMessage());
	    aBind.add(dto.getLogLog());
	    aBind.add(dto.getEcoState());
	    aBind.add(dto.getEcoOid());
	    
	    aBind.add(dto.getEcoIda2a2());
	    aBind.add(dto.getReEventResult());
	    aBind.add(dto.getEcaIda2a2());
	    aBind.add(dto.getEcaName());
	    aBind.add(dto.getEcaOid());
	    
	    aBind.add(dto.getEcaState());
	    
	    String sSql = buffer.toString();
	    oDaoManager.update(sSql, aBind);
	    
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}
    }

    /**
CREATE TABLE KETECMLOG
(
  CHANGEUSERID     VARCHAR2(600 BYTE),
  CHANGEUSERNAME   VARCHAR2(600 BYTE),
  LOGDATE          DATE,
  ECONO            VARCHAR2(600 BYTE),
  EVENTNAME        VARCHAR2(600 BYTE),
  EVENTRESULT      VARCHAR2(600 BYTE),
  ERRMESSAGE       VARCHAR2(4000 BYTE),
  LOGLOG           VARCHAR2(4000 BYTE),  
  ECOSTATE         VARCHAR2(600 BYTE),
  ECOOID           VARCHAR2(600 BYTE),
  ECOIDA2A2        NUMBER,  
  REEVENTRESULT    VARCHAR2(600 BYTE),
  ECAIDA2A2        NUMBER,
  ECANAME          VARCHAR2(600 BYTE),
  ECAOID           VARCHAR2(600 BYTE),
  ECASTATE         VARCHAR2(600 BYTE)
)
TABLESPACE USERS
RESULT_CACHE (MODE DEFAULT)
PCTUSED    0
PCTFREE    10
INITRANS   1
MAXTRANS   255
STORAGE    (
            INITIAL          24K
            NEXT             24K
            MINEXTENTS       1
            MAXEXTENTS       UNLIMITED
            PCTINCREASE      0
            BUFFER_POOL      DEFAULT
            FLASH_CACHE      DEFAULT
            CELL_FLASH_CACHE DEFAULT
           )
LOGGING 
NOCOMPRESS 
NOCACHE
NOPARALLEL
MONITORING;
     */
    @Override
    public void testLog() throws Exception {

//	KETEcmLog log = KETEcmLog.newKETEcmLog();
//	log.setEcoNo("1412-002");
//	
//	PersistenceHelper.manager.save(log);
	
    }

}
