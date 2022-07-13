package e3ps.load.part;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.part.service.KETNewPartListHelper;
import e3ps.project.E3PSProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.shared.log.Kogger;

public class NewPartListDataLoader implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean  SERVER    = wt.method.RemoteMethodServer.ServerFlag;

    //private static String FILESERVER = "\\\\192.168.17.13";
    private static String EXCELFILE = "";

    // 192.168.16.103
    //데이터정리양식_프로젝트.xls

    private static String SEPARATOR = File.separator;

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\part";
	} catch (Exception e) {
	    Kogger.error(NewPartListDataLoader.class, e);
	}
    }

    private static String logFile   = "partlogfile.log";

    public static boolean loadFile(String fileName) throws WTException {
	try {
	    String filePath = EXCELFILE + SEPARATOR + fileName;
	    Kogger.debug(NewPartListDataLoader.class, filePath);
	    File dataFile = new File(filePath);
	    if (!dataFile.exists()) {
		Kogger.debug(NewPartListDataLoader.class, "File not found!!!");
		return false;
	    }

	    logFile = EXCELFILE + SEPARATOR;
	    int pidx = fileName.lastIndexOf(".");
	    if (pidx > 0) {
		logFile += fileName.substring(0, pidx);
	    }
	    else {
		logFile = fileName;
	    }
	    logFile += ".txt";

	    Workbook workbook = Workbook.getWorkbook(dataFile);
	    Sheet sheets[] = workbook.getSheets();

	    Vector lds = new Vector();
	    Kogger.debug(NewPartListDataLoader.class, "sheets.length = " + sheets.length);
	    if (sheets.length > 0) {
		Sheet sheet = null;
		for (int i = 0; i < sheets.length; i++) {
		    sheet = sheets[i];

		    int rows = sheet.getRows();
		    Kogger.debug(NewPartListDataLoader.class, rows);
		    Cell hdrs[] = sheet.getRow(1);

		    for (int k = 0; k < hdrs.length; k++) {
			Cell hdr = hdrs[k];
		    }

		    for (int k = 3; k < rows; k++) {
			Cell cell[] = sheet.getRow(k);
			for (int a = 0; a < cell.length; a++) {
			    //System.out.print("<" + cell[a].getContents()+">");
			}
			ProductProjectData data = (ProductProjectData) getRowData(sheet.getRow(k), k + 1, sheet.getName());

			if (data != null) {
			    if (!data.isValidate) {
				//log(rdata.msg);
			    }
			    else {
				Vector v = new Vector();
				v.add(data);

				Vector v0 = (Vector) save(v);
				for (int m = 0; m < v0.size(); m++) {
				    ProductProjectData rdata0 = (ProductProjectData) v0.get(m);
				    if (!(rdata0.isLoadCompleted)) {
					//System.out.print("[["+ rdata0.msg +"]]");
					//log(rdata0.msg);
				    }
				}
			    }
			}

		    }
		}
	    }
	    workbook.close();
	    workbook = null;
	} catch (Exception e) {
	    Kogger.error(NewPartListDataLoader.class, e);
	    throw new WTException("file name = " + fileName);
	}
	return true;
    }

    /*	
    	public static void log(String msg) {
    		try {
    			LogToFile logger = new LogToFile(logFile,true);
    			logger.log(msg);
    		} catch (IOException e) {
    			Kogger.error(getClass(), e);
    		}
    	}	
    */
    public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
	ProductProjectData data = null;

	StringBuffer sb = new StringBuffer();
	try {
	    if (JExcelUtil.getContent(cells, 0).length() == 0) {
		return null;
	    }

	    sb.append("\n############## >>> [ row:" + rownum + " ]-[ sheet:" + sheetName + " ] <<< ##############");
	    data = new ProductProjectData(cells, rownum);

	    E3PSProject project = null;

	    if (data.partNumber.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- partNumber : 입력값이 없습니다.");
	    }
	    else {
		project = ProjectHelper.getProject(data.partNumber.toUpperCase());
		if (project != null) {
		    data.isValidate = true;
		    sb.append("\n").append("- Project No : 이미 등록된 번호 입니다.[" + data.partNumber + "]");
		}
	    }
	    data.isExist = (project == null) ? false : true;

	    if (data.partName.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- partName : 입력값이 없습니다.");
	    }

	} catch (Exception e) {
	    Kogger.error(NewPartListDataLoader.class, e);
	    throw new WTException(e);
	} finally {
	    if (data != null) {
		data.msg = sb.toString();
	    }
	}
	return data;
    }


    public static ProductProjectData save(ProductProjectData data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }

	    Hashtable<String, String> hash = new Hashtable<String, String>();

	    hash.put("partNumber", data.partNumber);
	    hash.put("partName", data.partName);
	    hash.put("rawMaterial", KETStringUtil.nvl(data.rawMaterial, "-"));
	    hash.put("customer", KETStringUtil.nvl(data.customer, "-"));
	    hash.put("description", KETStringUtil.nvl(data.description, "-"));
	    hash.put("register", KETStringUtil.nvl(data.register.trim(), "-"));
	    hash.put("newparttypeReference", data.newparttypeReference); // 신규부품유형
	    hash.put("newchetypeReference", data.newchetypeReference); // 채번구분

	    KETNewPartListHelper.service.createNewPartList(hash);

	} catch (Exception e) {
	    Kogger.error(NewPartListDataLoader.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static ProductProjectData update(ProductProjectData data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }


	} catch (Exception e) {
	    Kogger.error(NewPartListDataLoader.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static Object save(Vector data) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Vector.class };
	    Object args[] = new Object[] { data };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", NewPartListDataLoader.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(NewPartListDataLoader.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(NewPartListDataLoader.class, e);
		throw new WTException(e);
	    }
	    return obj;
	}

	Vector r = new Vector();
	try {
	    //trx.start();

	    for (int i = 0; i < data.size(); i++) {
		ProductProjectData ppData = (ProductProjectData) data.get(i);

		try {

		    ppData = save((ProductProjectData) data.get(i));

		    ppData.isLoadCompleted = true;
		} catch (Exception e) {
		    ppData.isLoadCompleted = false;
		    ppData.msg += "\n" + e.getLocalizedMessage();
		    throw new Exception(e);
		}

		r.add(ppData);
	    }
	} catch (Exception e) {
	    Kogger.error(NewPartListDataLoader.class, e);
	} finally {

	}
	return r;
    }


    public static void main(String[] args) throws Exception {
	NewPartListDataLoader loader = new NewPartListDataLoader();
	//		String type = "전자";
	//		loader.loadFile("제품프로젝트_V1.0_전자_101120.xls", type);
	wt.method.RemoteMethodServer.getDefault().setUserName(args[1]);
	wt.method.RemoteMethodServer.getDefault().setPassword(args[2]);
	loader.loadFile(args[0]);

    }

}

class ProductProjectData implements Serializable {

    /**
	 * 
	 */
    /*	private static final long serialVersionUID = 1L;
    */
    public int     rownum;
    public String  partNumber;
    public String  partName;
    public String  rawMaterial;
    public String  customer;
    public String  register;
    public String  regDate;
    public String  modDate;
    public String  description;
    public String  newparttypeReference;
    public String  newchetypeReference;
    public String  msg;

    public boolean isExist         = false;
    /*public boolean isDocNumberSizeValid = true; */
    public boolean isValidate      = true;
    /*public boolean isRepPartExist = true;
    */
    public boolean isLoadCompleted = false;

    public ProductProjectData(Cell[] cells, int rownum) {
	this.rownum = rownum;

	int colIdex = -1;
	partNumber = JExcelUtil.getContent(cells, ++colIdex);
	partName = JExcelUtil.getContent(cells, ++colIdex);
	rawMaterial = JExcelUtil.getContent(cells, ++colIdex);
	customer = JExcelUtil.getContent(cells, ++colIdex);
	description = JExcelUtil.getContent(cells, ++colIdex);
	regDate = JExcelUtil.getContent(cells, ++colIdex);
	register = JExcelUtil.getContent(cells, ++colIdex);
	newparttypeReference = JExcelUtil.getContent(cells, ++colIdex);
	newchetypeReference = JExcelUtil.getContent(cells, ++colIdex);
    }

}
