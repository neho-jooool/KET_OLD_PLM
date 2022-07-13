package ext.ket.bom.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import ext.ket.shared.log.Kogger;

public class KETIctMig {

    Registry registry = Registry.getRegistry("e3ps.bom.bom");

    public KETIctMig() {

    }

    public String getStringValue(Cell cell) {
	String value = "";

	if (cell == null)
	    return value;

	switch (cell.getCellType()) {
	case Cell.CELL_TYPE_FORMULA:
	    value = cell.getCellFormula();
	    break;
	case Cell.CELL_TYPE_NUMERIC:
	    value = Integer.toString(new Double(cell.getNumericCellValue()).intValue());
	    break;
	case Cell.CELL_TYPE_STRING:
	    value = cell.getStringCellValue();
	    break;
	case Cell.CELL_TYPE_BLANK:
	    break;
	case Cell.CELL_TYPE_BOOLEAN:
	    value = Boolean.toString(cell.getBooleanCellValue());
	    break;
	case Cell.CELL_TYPE_ERROR:
	    value = "ERROR";
	    break;
	default:
	}

	return value.trim();
    }

    public void updateIct(String parent, String child) {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql;

	try {
	    sql = new StringBuffer();

	    // sql.append(" SELECT ICT FROM KETPARTUSAGELINK WHERE PARENTITEMCODE='" + parent + "' AND CHILDITEMCODE='" + child + "' ");

	    // Kogger.debug(getClass(), "SQL==>" + sql.toString());
	    // pstmt = conn.prepareStatement(sql.toString());
	    // rs = pstmt.executeQuery();
	    // while (rs.next()) {
	    // String ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
	    // Kogger.debug(getClass(), "ict==>" + ict);
	    // }

	    sql.append("UPDATE KETPARTUSAGELINK SET ICT='N' WHERE PARENTITEMCODE='" + parent + "' AND CHILDITEMCODE='" + child + "' ");
	    Kogger.debug(getClass(), sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.executeUpdate();

	    conn.commit();
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);

	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }

	    // throw e;
	} finally {

	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		// throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);

		}
	    }

	}
    }

    public void checkIct(int cnt, String parent, String child) {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql;

	try {
	    sql = new StringBuffer();

	    sql.append(" SELECT ICT FROM KETPARTUSAGELINK WHERE PARENTITEMCODE='" + parent + "' AND CHILDITEMCODE='" + child + "' ");

	    // Kogger.debug(getClass(), sql.toString());
	    pstmt = conn.prepareStatement(sql.toString());
	    rs = pstmt.executeQuery();

	    int k = 0;
	    if (rs != null) {
		while (rs.next()) {
		    String ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
		    if (!ict.equals("N"))
			Kogger.debug(getClass(), "FAIL[" + cnt + "]-----------------parent==>" + parent + "	child==>" + child + "	ict==>" + ict);
		    // else
		    // Kogger.debug(getClass(), "SUCCESS[" + cnt + "]----------------- parent==>" + parent + "	child==>" + child + "	ict==>"
		    // + ict);
		    k++;
		}
	    }
	    if (k == 0) {
		Kogger.debug(getClass(), "No Data[" + cnt + "]-----------------parent==>" + parent + "	child==>" + child);
	    }

	    conn.commit();
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);

	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }

	    // throw e;
	} finally {

	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		// throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);

		}
	    }

	}
    }

    public boolean updateIctL() {
	DBConnectionManager res = DBConnectionManager.getInstance();
	Connection conn = res.getConnection(registry.getString("plm"));
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	StringBuffer sql;

	boolean isComplete = false;

	try {
	    sql = new StringBuffer();

	    // sql.append(" SELECT ICT FROM KETPARTUSAGELINK WHERE ICT IS NULL");

	    // Kogger.debug(getClass(), "SQL==>" + sql.toString());
	    // pstmt = conn.prepareStatement(sql.toString());
	    // rs = pstmt.executeQuery();
	    // while (rs.next()) {
	    // String ict = rs.getString("ICT") == null ? "" : rs.getString("ICT").trim();
	    // Kogger.debug(getClass(), "ict==>" + ict);
	    // }

	    sql.append(" UPDATE KETPARTUSAGELINK SET ICT='L' WHERE ICT IS NULL");
	    pstmt = conn.prepareStatement(sql.toString());
	    pstmt.executeUpdate();

	    conn.commit();

	    isComplete = true;
	} catch (Exception e) {
	    try {

		conn.rollback();
		Kogger.error(getClass(), e);

	    } catch (SQLException se) {
		Kogger.error(getClass(), se);
	    }

	    // throw e;
	} finally {

	    try {
		if (rs != null) {
		    rs.close();
		}

		if (pstmt != null) {
		    pstmt.close();
		}
	    } catch (Exception e) {
		Kogger.error(getClass(), e);
		// throw e;
	    } finally {
		if (res != null) {
		    res.freeConnection(registry.getString("plm"), conn);

		}
	    }

	}

	return isComplete;
    }

    public void getExelBomIct() throws FileNotFoundException, IOException {

	HSSFSheet sheet = null;
	HSSFRow row = null;
	HSSFCell cell1 = null;
	HSSFCell cell2 = null;

	File template = new File("D:/ket/workspace/ketplm/src/ext/ket/bom/util/ICT_MIG.xls");

	if (template.isFile()) {

	    POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(template));
	    HSSFWorkbook wb = new HSSFWorkbook(filein);

	    sheet = wb.getSheetAt(0);

	    int endRow = sheet.getLastRowNum();

	    // Kogger.debug(getClass(), "endRow===>" + endRow);

	    row = sheet.getRow(1);
	    cell1 = row.getCell(0);
	    cell2 = row.getCell(1);

	    // Kogger.debug(getClass(), "1열==>" + cell1.getStringCellValue() + "    2열==>" + cell2.getStringCellValue());
	    for (int i = 1; i <= endRow; i++) {
		row = sheet.getRow(i);
		cell1 = row.getCell(0);
		cell2 = row.getCell(1);

		String parent = "";
		String child = "";

		parent = getStringValue(cell1);
		child = getStringValue(cell2);

		// String parent = cell1.getStringCellValue();
		// String child = cell2.getStringCellValue();

		Kogger.debug(getClass(), "NO==>" + i + "    1열==>" + parent + "    2열==>" + child);

		updateIct(parent, child);

	    }

	}

    }

    public void checkExelBomIct() throws FileNotFoundException, IOException {

	HSSFSheet sheet = null;
	HSSFRow row = null;
	HSSFCell cell1 = null;
	HSSFCell cell2 = null;

	File template = new File("D:/ket/workspace/ketplm/src/ext/ket/bom/util/ICT_MIG.xls");

	if (template.isFile()) {

	    POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(template));
	    HSSFWorkbook wb = new HSSFWorkbook(filein);

	    sheet = wb.getSheetAt(0);

	    int endRow = sheet.getLastRowNum();

	    // Kogger.debug(getClass(), "endRow===>" + endRow);

	    // row = sheet.getRow(1);
	    // cell1 = row.getCell(0);
	    // cell2 = row.getCell(1);

	    // Kogger.debug(getClass(), "1열==>" + cell1.getStringCellValue() + "    2열==>" + cell2.getStringCellValue());
	    for (int i = 1; i <= endRow; i++) {
		row = sheet.getRow(i);
		cell1 = row.getCell(0);
		cell2 = row.getCell(1);

		String parent = "";
		String child = "";

		parent = getStringValue(cell1);
		child = getStringValue(cell2);

		// String parent = cell1.getStringCellValue();
		// String child = cell2.getStringCellValue();

		// Kogger.debug(getClass(), "NO==>" + i + "    1열==>" + parent + "    2열==>" + child);

		checkIct(i, parent, child);

	    }

	}

    }

    public static void main(String[] args) {
	// TODO Auto-generated method stub
	// windchill ext.ket.bom.util.KETIctMig > KETIctMig_log.txt

	KETIctMig mig = new KETIctMig();
	try {

	    if (mig.updateIctL()) {
		mig.getExelBomIct();
	    }

	    mig.checkExelBomIct();
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(KETIctMig.class, e);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    Kogger.error(KETIctMig.class, e);
	}
    }

}
