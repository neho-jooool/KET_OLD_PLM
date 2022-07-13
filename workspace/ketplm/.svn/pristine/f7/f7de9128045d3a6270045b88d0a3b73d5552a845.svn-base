package e3ps.load.code;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeDao;
import e3ps.common.code.NumberCodeType;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class KETStdCodeLoad implements wt.method.RemoteAccess, java.io.Serializable {
    private static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
    private static Registry registry = Registry.getRegistry("e3ps.bom.bom");

    public static boolean loadData(String fileName) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { String.class };
	    Object args[] = new Object[] { fileName };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("loadData", "e3ps.load.code.KETStdCodeLoad", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(KETStdCodeLoad.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(KETStdCodeLoad.class, e);
		throw new WTException(e);
	    }

	    return ((Boolean) obj).booleanValue();
	}

	Transaction trx = new Transaction();
	Connection conn = null;
	try {
	    if (fileName == null || fileName.length() == 0) {
		fileName = "KETStdCodeLoad.xls";
	    }

	    String wt_home = WTProperties.getServerProperties().getProperty("wt.home");
	    String loadBase_home = wt_home + File.separator + "loadFiles" + File.separator + "ket" + File.separator + "code";

	    File file = new File(loadBase_home + File.separator + fileName);
	    if (file == null) {
		Kogger.debug(KETStdCodeLoad.class, "File not found!!!");
		return false;
	    }

	    trx.start();
	    DBConnectionManager res = DBConnectionManager.getInstance();
	    conn = res.getConnection(registry.getString("plm"));

	    DecimalFormatSymbols symbol = null;
	    DecimalFormat df = null;

	    NumberCodeType ptype = null;
	    NumberCodeType type = null;
	    NumberCode numberCode = null;
	    NumberCode parentCode = null;

	    String pcodeType = "";// 모코드 Type
	    String pcode = "";// 모코드
	    String codeType = "";// 자코드 Type
	    String format = "";// Format
	    String code = "";// 자코드
	    String name = "";// Name
	    String description = "";// Description
	    String division = ""; // 사업부
	    String sorting = ""; // sorting
	    String disable = ""; // disable
	    String dscode = ""; // dscode

	    Workbook workbook = Workbook.getWorkbook(file);
	    Sheet sheets[] = workbook.getSheets();

	    if (sheets.length > 0) {
		Sheet sheet = null;

		for (int i = 0; i < sheets.length; i++) {
		    sheet = sheets[i];
		    Kogger.debug(KETStdCodeLoad.class, "######### Sheet [" + sheet.getName() + "] Loading start ########");

		    codeType = "";
		    pcodeType = "";
		    type = null;
		    ptype = null;
		    format = "";
		    symbol = null;
		    df = null;
		    dscode = "";

		    int rows = sheet.getRows();

		    for (int j = 0; j < rows; j++) {
			System.out.print("@>>>>>> Row[" + j + "]" + " begin >>>>> ");

			/*
		         * Column Number 1:코드타입 2:Format 3:모코드타입
		         */
			if (j == 0) {
			    Cell typeCell = sheet.getCell(1, j);
			    codeType = typeCell.getContents();

			    if (codeType == null) {
				codeType = "";
			    }

			    if (codeType.trim().length() == 0) {
				throw new WTException("'" + sheet.getName() + "' Sheet의 코드타입 정보가 없습니다.");
			    }

			    type = NumberCodeType.toNumberCodeType(codeType.trim());
			    if (type == null) {
				throw new WTException("코드타입[" + codeType + "]이 존재하지 않습니다.");
			    }

			    Cell formatCell = sheet.getCell(2, j);
			    format = formatCell.getContents();
			    if (format == null) {
				format = "";
			    }

			    if (format.trim().length() > 0) {
				symbol = new DecimalFormatSymbols();
				df = new DecimalFormat(format, symbol);
			    }

			    Cell ptypeCell = sheet.getCell(3, j);
			    pcodeType = ptypeCell.getContents();

			    if (pcodeType == null) {
				pcodeType = "";
			    }

			    if (pcodeType.trim().length() > 0) {
				ptype = NumberCodeType.toNumberCodeType(pcodeType);

				if (ptype == null) {
				    throw new WTException(sheet.getName() + "의 모코드타입[" + pcodeType + "]이 없습니다.");
				}
			    }

			    continue;
			}

			/*
		         * 0:코드 1:Name 2:Description 3:모코드 4:사업부 5:Sorting 6:Disable
		         */

			Cell a1 = sheet.getCell(0, j);
			String a1Str = a1.getContents();
			if (a1Str == null)
			    a1Str = "";

			if (a1Str.startsWith("#")) {
			    continue;
			}

			Cell b1 = sheet.getCell(1, j);
			Cell c1 = sheet.getCell(2, j);
			Cell d1 = sheet.getCell(3, j);
			Cell e1 = sheet.getCell(4, j);
			Cell f1 = sheet.getCell(5, j);
//			Cell g1 = sheet.getCell(6, j);
//			Cell h1 = sheet.getCell(7, j);

			String b1Str = b1.getContents();
			String c1Str = c1.getContents();
			String d1Str = d1.getContents();
			String e1Str = e1.getContents();
			String f1Str = f1.getContents();
			String g1Str = "";//g1.getContents();
			String h1Str = "";//h1.getContents();

			if (b1Str == null)
			    b1Str = "";
			if (c1Str == null)
			    c1Str = "";
			if (d1Str == null)
			    d1Str = "";
			if (e1Str == null)
			    e1Str = "";
			if (f1Str == null)
			    f1Str = "";
			if (g1Str == null)
			    g1Str = "";
			if (h1Str == null)
			    h1Str = "";

			code = a1Str.trim();

			if (!StringUtil.checkString(code))
			    continue;

			name = b1Str.trim();
			description = c1Str.trim();
			pcode = d1Str.trim();
			division = e1Str.trim();
			sorting = f1Str.trim();
			disable = g1Str.trim();
			dscode = h1Str.trim();

			// 모코드 가져오기
			parentCode = null;
			if (pcode.length() > 0) {
			    parentCode = checkCodeData(null, ptype, pcode);

			    if (parentCode == null) {
				throw new WTException("모코드 중 코드타입[" + pcodeType + "]이 '" + pcode + "'인 코드가 존재하지 않습니다.");
			    }
			}
			// 모코드 가져오기 끝

			// code = df.format(Integer.parseInt(code));
			Kogger.debug(KETStdCodeLoad.class, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			Kogger.debug(KETStdCodeLoad.class, "numberCode=>" + numberCode);
			Kogger.debug(KETStdCodeLoad.class, "code=>" + code);
			Kogger.debug(KETStdCodeLoad.class, "name=>" + name);
			Kogger.debug(KETStdCodeLoad.class, "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

			numberCode = checkCodeData(null, type, code);

			if (numberCode == null && StringUtil.checkString(code)) {
			    numberCode = NumberCode.newNumberCode();
			    numberCode.setCodeType(type);
			}

			numberCode.setCode(code);
			numberCode.setName(name);
			numberCode.setDescription(description);
			numberCode.setVenderCode(division);
			numberCode.setSorting(sorting);
			numberCode.setDisabled(("1".equals(disable)) ? true : false);
			numberCode.setDsCode(dscode);

			if (parentCode != null) {
			    numberCode.setParent(parentCode);
			}

			numberCode = (NumberCode) PersistenceHelper.manager.save(numberCode);
			NumberCodeDao dao = new NumberCodeDao(conn);

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("codeType", codeType);
			param.put("code", code);
			param.put("value", name);
			param.put("desc", description);
			param.put("abbr", "");
			param.put("lang", "ko");
			dao.createNumberCodeValue(param);
			param.put("lang", "en");
			dao.createNumberCodeValue(param);
			param.put("lang", "zh_CN");
			dao.createNumberCodeValue(param);

			System.out.print("@>>>>>> Row[" + j + "]" + " Loading end >>>>> ");
		    }

		    Kogger.debug(KETStdCodeLoad.class, "######### Sheet [" + sheet.getName() + "] end ########");
		}
	    }

	    workbook.close();
	    trx.commit();
	    conn.close();
	    trx = null;
	    conn = null;
	} catch (Exception e) {
	    Kogger.error(KETStdCodeLoad.class, e);
	    trx.rollback();

	    throw new WTException(e.getMessage());
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	    if (conn != null) {
		try {
		    conn.close();
		} catch (SQLException e) {
		    Kogger.error(KETStdCodeLoad.class, e);
		}
		conn = null;
	    }
	}

	return true;
    }

    public static NumberCode checkCodeData(NumberCode pcode, NumberCodeType type, String code) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { NumberCode.class, NumberCodeType.class, String.class };
	    Object args[] = new Object[] { pcode, type, code };
	    Object obj = null;

	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("checkCodeData", "e3ps.load.code.KETStdCodeLoad", null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(KETStdCodeLoad.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(KETStdCodeLoad.class, e);
		throw new WTException(e);
	    }

	    return (NumberCode) obj;
	}

	try {
	    Class codeClass = NumberCode.class;

	    QuerySpec qs = new QuerySpec();
	    int index = qs.addClassList(codeClass, true);

	    SearchCondition sc = null;

	    sc = new SearchCondition(codeClass, "codeType", SearchCondition.EQUAL, type);
	    qs.appendWhere(sc, new int[] { index });

	    qs.appendAnd();
	    sc = new SearchCondition(codeClass, "code", SearchCondition.EQUAL, code);
	    qs.appendWhere(sc, new int[] { index });

	    if (pcode != null) {
		qs.appendAnd();

		sc = new SearchCondition(NumberCode.class, "parentReference.key.id", SearchCondition.EQUAL, pcode.getPersistInfo().getObjectIdentifier().getId());
		qs.appendWhere(sc, new int[] { index });
	    }

	    QueryResult qr = PersistenceHelper.manager.find(qs);

	    if (qr.hasMoreElements()) {
		Object obj[] = (Object[]) qr.nextElement();
		return (NumberCode) obj[0];
	    }
	} catch (Exception e) {
	    Kogger.error(KETStdCodeLoad.class, e);
	    throw new WTException(e);
	}

	return null;
    }

    public static void main(String args[]) throws Exception {
	// wt.method.RemoteMethodServer remotemethod = wt.method.RemoteMethodServer.getDefault();
	// if(remotemethod.getUserName() == null) {
	// remotemethod.setUserName("wcadmin");
	// remotemethod.setPassword("wcadmin");
	// }
	wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
	wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

	String fileName = args[2];
	boolean flag = loadData(fileName);
	Kogger.debug(KETStdCodeLoad.class, "Load : " + flag);
	System.exit(0);
    }
}
