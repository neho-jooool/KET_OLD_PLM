package e3ps.ecm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.PageControl;
import e3ps.ecm.entity.KETSearchEcoDetailVO;
import e3ps.ecm.entity.KETSearchEcoVO;

public class SearchMoldChangeRequestDao {
	private static final long serialVersionUID = -1432672658288760111L;

	/**
	 * 
	 * 함수명 : searchPartPopupList
	 * 설명 :  부품 팝업 목록을 검색한다.
	 * @param ketSearchEcoVO : 금형 ECO 검색 VO
	 * @return ketSearchEcoVO : 금형 ECO 검색 VO
	 * @throws Exception
	 * 작성자 : 안수학
	 * 작성일자 : 2010. 12. 31.
	 */
	public KETSearchEcoVO searchPartPopupList(KETSearchEcoVO ketSearchEcoVO) throws Exception {
		ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList = new ArrayList<KETSearchEcoDetailVO>();//ECO 검색상세 List
		KETSearchEcoDetailVO ketSearchEcoDetailVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		
        try {
			con = PlmDBUtil.getConnection();
			if(ketSearchEcoVO.getTotalCount() == 0 && ketSearchEcoVO.getPagingYn() == 1) {//신규조회시 전체건수 조회
				sql = getSearchPartPopupListSQL(ketSearchEcoVO, 0);
	        	pstmt = con.prepareStatement(sql);
	        	rs = pstmt.executeQuery();
	        	rs.next();
	        	ketSearchEcoVO.setTotalCount(rs.getInt("totalCount"));
	    		if(rs != null) rs.close();
	    		if(pstmt != null) pstmt.close();
			}

			sql = getSearchPartPopupListSQL(ketSearchEcoVO, 1);
        	pstmt = con.prepareStatement(sql);
        	int resultRows = 0;
        	if(ketSearchEcoVO.getPagingYn() == 1) {
	        	int startRow = (ketSearchEcoVO.getPage() - 1) * ketSearchEcoVO.getPerPage() + 1;
				int endRow = ketSearchEcoVO.getPage() * ketSearchEcoVO.getPerPage();
	    		pstmt.setInt(1, startRow);
	    		pstmt.setInt(2, endRow);
        	}
        	rs = pstmt.executeQuery();
        	while(rs.next()) {
        		resultRows++;
        		ketSearchEcoDetailVO = new KETSearchEcoDetailVO();
        		ketSearchEcoDetailVO.setPartNumber(StringUtil.escapeHtml(rs.getString("partNo")));
        		ketSearchEcoDetailVO.setPartName(StringUtil.escapeHtml(rs.getString("partName")));
        		ketSearchEcoDetailVO.setPartVersion(rs.getString("partVersion"));
        		ketSearchEcoDetailVO.setOid(rs.getString("oid"));
        		ketSearchEcoDetailVOList.add(ketSearchEcoDetailVO);
        	}
        	ketSearchEcoVO.setResultRows(resultRows);
        } catch( Exception e ) {
        	throw e;
        } finally {
    		if(rs != null) rs.close();
    		if(pstmt != null) pstmt.close();
			PlmDBUtil.close(con);
        }
        ketSearchEcoVO.setKetSearchEcoDetailVOList(ketSearchEcoDetailVOList);
		PageControl pageControl = new PageControl(ketSearchEcoVO.getPage(), 10, ketSearchEcoVO.getPerPage(), ketSearchEcoVO.getTotalCount());
        ketSearchEcoVO.setPageControl(pageControl);
        return ketSearchEcoVO;
	}

	/**
	 * 
	 * 함수명 : getSearchPartPopupListSQL
	 * 설명 : 부품 팝업 목록을 검색하는 SQL을 작성한다.
	 * @param ketSearchEcoVO : 금형 ECO 검색 VO
	 * @param ingPage : 페이지 처리여부(0:미처리 1:페이지처리)
	 * @return String : SQL
	 * @throws Exception
	 * 작성자 : 안수학
	 * 작성일자 : 2010. 12. 31.
	 */
	private String getSearchPartPopupListSQL(KETSearchEcoVO ketSearchEcoVO, int ingPage) throws Exception {
		StringBuffer sql = new StringBuffer();
		if(ingPage == 0) {//건수조회
			sql.append( "SELECT COUNT(*) totalCount \n" );
		} else {
			sql.append( "SELECT * \n" );
		}
		sql.append( "  FROM ( \n" );
		sql.append( "            SELECT ROWNUM row_id \n" );
		sql.append( "                    , t.* \n" );
		sql.append( "             FROM ( \n" ); 
		
		sql.append("SELECT T3.WTPartNumber partNo \n");
		sql.append("	, T3.name partName \n");
		sql.append("	, T2.versionIdA2versionInfo partVersion \n");
		sql.append("	, T2.classnameA2A2||':'||T2.ida2a2 oid \n");
		sql.append("FROM WTPart T2 \n");
		sql.append("	, WTPartMaster T3 \n");
		sql.append("WHERE 1 = 1 \n");
		sql.append("AND T2.idA3masterReference = T3.ida2a2 \n");
		sql.append("AND T2.ida2a2 IN ( \n" );
		sql.append("	SELECT MAX(ST2.ida2a2) ida2a2 \n" );
		sql.append("		FROM WTPart ST2 \n" );
		sql.append("			, WTPartMaster ST3 \n" );
		sql.append("		WHERE 1 = 1 \n" );
		sql.append("	AND ST2.idA3masterReference = ST3.ida2a2 \n" );
		sql.append("	AND ST2.latestiterationinfo = 1 \n" );
		sql.append("	GROUP BY ST3.WTPartNumber \n" );
		sql.append(") \n" );
		sql.append("ORDER BY " + ketSearchEcoVO.getSortColumn() + " \n");

		sql.append( ") t  )tb	 \n" );
		sql.append( "WHERE 1 = 1 \n" );
		if(ingPage == 1 && ketSearchEcoVO.getPagingYn() == 1) {//자료조회
			sql.append( "AND tb.row_id BETWEEN ? AND ? \n" );
		}
        return sql.toString();
	}
}
