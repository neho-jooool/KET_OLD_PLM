package ext.ket.yesone.service.internal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.PlmDBUtil;
import ext.ket.yesone.entity.KETYesoneBaseDTO;
import ext.ket.yesone.util.FormTypeEnum;

public class QueryUtil {

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	ArrayList<TreeMap<String, String>> CommonCheck = null;

	public QueryUtil(Connection conn, PreparedStatement pstmt, ResultSet rs, KETYesoneBaseDTO baseDto) throws Exception {
		this.conn = conn;
		this.pstmt = pstmt;
		this.rs = rs;
		this.setCommonSelectQry(baseDto);
	}

	public void ExceuteUpdate(KETYesoneBaseDTO baseDto, String form_cd, String maxChasu) throws Exception {

		String ExcuteQry = QueryPool.getInstance().getUpdateQuery(baseDto, form_cd, maxChasu);
		pstmt = conn.prepareStatement(ExcuteQry);
		pstmt.executeUpdate();

		PlmDBUtil.close(pstmt);

	}

	public void ExceuteInsert(KETYesoneBaseDTO baseDto, String form_cd, String maxChasu) throws Exception {

		String selectQry = QueryPool.getInstance().getSelectQueryI(baseDto, form_cd, maxChasu);

		FormTypeEnum Ftype = FormTypeEnum.getFormTypeEnum(form_cd);

		if (Ftype == null) {
			throw new Exception("서식코드가 존재하지 않습니다!");
		}

		if (StringUtils.isNotEmpty(selectQry)) {

			pstmt = conn.prepareStatement(selectQry);

			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			TreeMap<String, String> colMap = null;
			ArrayList<TreeMap<String, String>> DataSet = new ArrayList<TreeMap<String, String>>();

			String colName = null;

			String TableName = null;

			TableName = Ftype.getLegacyTBInfo();

			while (rs.next()) {

				// 여기서부터 체크로직.. 막상 걸만한건 기부금 뿐이고 나머지는 쿼리에서 걸어주었음
				if (Ftype == FormTypeEnum.기부금) {
					String GI_CODE = rs.getString("GI_CODE"); // 기부금 코드
					String GI_RELAT = rs.getString("GI_RELAT"); // 관계코드
					String BAS_DED = this.getCommonInfoENC(rs.getString("GI_RES_NO"), "BAS_DED"); // 기본공제여부

					if (GI_CODE.equals("20") || GI_CODE.equals("42")) {// 정치자금, 우리사주기부금의 경우 본인외 등록불가
						if (!GI_RELAT.equals("0")) {
							continue;
						}
					}

					// 아래는 로직변경으로 인해 주석처리함 (2017.01.25)

					/*
					 * if (GI_CODE.equals("10") || GI_CODE.equals("40") || GI_CODE.equals("41")) {// 법정,지정기부금,종교는 본인또는 기본공제대상자만 if (GI_RELAT.equals("0")) {// 본인이면 ok
					 * 
					 * } else { if (!BAS_DED.equals("1")) {// 기본공제대상자 아니면 제외. continue; } } }
					 */

				}

				colMap = new TreeMap<String, String>();
				for (int i = 1; i <= colCount; i++) {
					colName = rsmd.getColumnLabel(i);

					int type = rsmd.getColumnType(i);

					if (Types.TIMESTAMP == type) {
						colMap.put(colName, "sysdate");
					} else {
						colMap.put(colName, StringUtils.defaultString(rs.getString(colName)));
					}
				}
				DataSet.add(colMap);

			}

			for (TreeMap<String, String> ds : DataSet) {
				Iterator<String> iteratorKey = ds.keySet().iterator();

				StringBuffer sb = new StringBuffer();
				StringBuffer colsb = new StringBuffer();
				StringBuffer valsb = new StringBuffer();

				sb.append("INSERT INTO " + TableName + "@legacy(\n");

				while (iteratorKey.hasNext()) {

					String key = iteratorKey.next();

					colsb.append(key + ",\n");
				}

				sb.append(StringUtils.removeEnd(colsb.toString(), ",\n"));

				sb.append(")values(");

				iteratorKey = ds.keySet().iterator();

				while (iteratorKey.hasNext()) {

					String key = iteratorKey.next();

					if ("sysdate".equals(ds.get(key))) {
						valsb.append(ds.get(key) + ",\n");
					} else {
						valsb.append("'" + ds.get(key) + "',\n");
					}

				}

				sb.append(StringUtils.removeEnd(valsb.toString(), ",\n"));

				sb.append(")");
				pstmt = conn.prepareStatement(sb.toString());
				pstmt.executeUpdate();

			}

			PlmDBUtil.close(pstmt);
			PlmDBUtil.close(rs);
		} else {
			System.out.println("*** QueryPool Not Found [ " + Ftype.name() + " ]");
		}
	}

	public void setCommonSelectQry(KETYesoneBaseDTO baseDto) throws Exception {

		String qry = QueryPool.getInstance().getCommonSelectQry(baseDto);

		CommonCheck = this.ExceuteSelect(qry);
	}

	public ArrayList<TreeMap<String, String>> ExceuteSelect(String selectQry) throws Exception {

		ArrayList<TreeMap<String, String>> DataSet = null;

		if (StringUtils.isNotEmpty(selectQry)) {

			pstmt = conn.prepareStatement(selectQry);

			rs = pstmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();

			TreeMap<String, String> colMap = null;
			DataSet = new ArrayList<TreeMap<String, String>>();

			String colName = null;

			while (rs.next()) {
				colMap = new TreeMap<String, String>();

				for (int i = 1; i <= colCount; i++) {
					colName = rsmd.getColumnLabel(i);
					colMap.put(colName, StringUtils.defaultString(rs.getString(colName)));
				}

				DataSet.add(colMap);

			}

			PlmDBUtil.close(pstmt);
			PlmDBUtil.close(rs);
		}

		return DataSet;
	}

	public String getCommonInfoENC(String FAM_RES_NO, String Key) {// 암호화된 RESNO를 가지고 찾아온다

		String rtn = null;
		for (TreeMap<String, String> ds : CommonCheck) {

			if (FAM_RES_NO.equals(ds.get("FAM_RES_NO"))) {
				rtn = ds.get(Key);
				break;
			}

		}
		return rtn;
	}

	public String getCommonInfoDEC(String RES_NO, String Key) {// 복호화된 RESNO를 가지고 찾아온다

		String rtn = null;
		for (TreeMap<String, String> ds : CommonCheck) {

			if (RES_NO.equals(ds.get("MY_RES_NO"))) {
				rtn = ds.get(Key);
				break;
			}

		}
		return rtn;
	}
}
