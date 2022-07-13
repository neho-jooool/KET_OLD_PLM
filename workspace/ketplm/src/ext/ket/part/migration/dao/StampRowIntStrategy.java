package ext.ket.part.migration.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @클래스명 : RowIntStrategy
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public interface StampRowIntStrategy {

    int mapRow(ResultSet oResultSet) throws SQLException;

}
