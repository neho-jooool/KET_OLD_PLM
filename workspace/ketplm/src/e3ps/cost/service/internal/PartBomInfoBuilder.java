package e3ps.cost.service.internal;

import java.sql.ResultSet;
import java.sql.SQLException;

import e3ps.cost.entity.PartBomInfoDTO;

/**
 * 데이터 구성및 변환 관련 역할
 * 
 * @클래스명 : PartBomInfoBuilder
 * @작성자 : 황정태
 * @작성일 : 2015. 5. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartBomInfoBuilder {

    public PartBomInfoBuilder() {

    }
    // ResultSet >> PartBomInfoDTO
    public PartBomInfoDTO buildResultSet2Dto(ResultSet rs) throws SQLException {
	
	PartBomInfoDTO dto = new PartBomInfoDTO();
	
	dto.setChildItemCode(rs.getString("CHILDITEMCODE"));
	dto.setGroupNo(rs.getString("GROUP_NO"));
	dto.setLVL(rs.getString("LVL"));
	dto.setQty(rs.getString("QTY"));
	return dto;
    }
    
    public PartBomInfoDTO buildResultSet2DtoLevle2(ResultSet rs) throws SQLException {
	
	PartBomInfoDTO dto = new PartBomInfoDTO();
	
	dto.setChildItemCode(rs.getString("CHILDITEMCODE"));
	dto.setGroupNo(rs.getString("GROUP_NO"));
	dto.setLVL(rs.getString("LVL"));
	dto.setQty(rs.getString("QTY"));
	dto.setCase_count_2(rs.getString("CASE_COUNT_2"));
	return dto;
    }
}