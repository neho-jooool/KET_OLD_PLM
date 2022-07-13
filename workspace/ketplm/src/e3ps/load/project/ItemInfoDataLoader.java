package e3ps.load.project;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import wt.fc.PersistenceHelper;
import wt.pom.Transaction;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.util.JExcelUtil;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.project.beans.ProductHelper;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.material.MoldMaterial;
import e3ps.project.material.load.MaterialLoad;
import ext.ket.shared.log.Kogger;

public class ItemInfoDataLoader implements wt.method.RemoteAccess, java.io.Serializable {

    static final boolean  SERVER    = wt.method.RemoteMethodServer.ServerFlag;

    //private static String FILESERVER = "\\\\192.168.17.13";
    private static String EXCELFILE = "";

    // 192.168.16.103
    //데이터정리양식_프로젝트.xls

    private static String SEPARATOR = File.separator;

    static {
	try {
	    String wt_home = WTProperties.getLocalProperties().getProperty("wt.home");
	    EXCELFILE = wt_home + SEPARATOR + "loadFiles\\ket\\migration\\project";
	} catch (Exception e) {
	    Kogger.error(ItemInfoDataLoader.class, e);
	}
    }

    private static String logFile   = "productprojectlogfile.log";

    public static boolean loadFile(String fileName) throws WTException {
	try {
	    String filePath = EXCELFILE + SEPARATOR + fileName;
	    File dataFile = new File(filePath);
	    if (!dataFile.exists()) {
		Kogger.debug(ItemInfoDataLoader.class, "File not found!!!");
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

	    if (sheets.length > 0) {
		Sheet sheet = null;
		for (int i = 0; i < 1; i++) {
		    sheet = sheets[i];

		    int rows = sheet.getRows();

		    Cell hdrs[] = sheet.getRow(1);
		    //System.out.print("#################################################################################################Loader Attr Start");
		    //Kogger.debug(ItemInfoDataLoader.class, );
		    for (int k = 0; k < hdrs.length; k++) {
			Cell hdr = hdrs[k];
			System.out.print("[" + hdr.getContents() + "]");
		    }
		    Kogger.debug(ItemInfoDataLoader.class, "");
		    for (int k = 3; k < rows; k++) {
			Cell cell[] = sheet.getRow(k);
			for (int a = 0; a < cell.length; a++) {
			    System.out.print("<" + cell[a].getContents() + ">");
			}
			ItemInfoData data = (ItemInfoData) getRowData(sheet.getRow(k), k + 1, sheet.getName());

			if (data != null) {
			    if (!data.isValidate) {
				//System.out.print("[["+ rdata.msg +"]]");
				//log(rdata.msg);
			    }
			    else {
				Vector v = new Vector();
				v.add(data);

				Vector v0 = (Vector) save(v);
				for (int m = 0; m < v0.size(); m++) {
				    ItemInfoData rdata0 = (ItemInfoData) v0.get(m);
				    if (!(rdata0.isLoadCompleted)) {
					System.out.print("[[" + rdata0.msg + "]]");
					//log(rdata0.msg);
				    }
				}
			    }
			}

		    }
		    //Kogger.debug(ItemInfoDataLoader.class, );
		    //System.out.print("#################################################################################################Loader Attr END ");
		}
	    }
	    workbook.close();
	    workbook = null;
	} catch (Exception e) {
	    Kogger.error(ItemInfoDataLoader.class, e);
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
    			Kogger.error(ItemInfoDataLoader.class, e);
    		}
    	}	
    */
    public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
	ItemInfoData data = null;

	MoldItemInfo miInfo = null;

	StringBuffer sb = new StringBuffer();
	try {
	    if (JExcelUtil.getContent(cells, 0).length() == 0) {
		return null;
	    }

	    sb.append("\n############## >>> [ row:" + rownum + " ]-[ sheet:" + sheetName + " ] <<< ##############");
	    data = new ItemInfoData(cells, rownum);

	    E3PSProject project = null;

	    if (data.projectNo.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Project No : 입력값이 없습니다.");
	    }
	    else {
		project = ProjectHelper.getProject(data.projectNo.toUpperCase());
		if (project == null) {
		    data.isValidate = true;
		    sb.append("\n").append("- Project No : 이 번호로 등록된 프로젝트가 없습니다.[" + data.projectNo + "]");
		}
	    }

	    if (data.dieNo.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Die No : 입력값이 없습니다.");
	    }
	    else {
		data.isValidate = true;
		miInfo = MoldProjectHelper.getMoldItem(data.dieNo, data.partNo);
	    }

	    if (miInfo != null) {
		if ("시작Fa".equals(data.moldType) || "양산Fa".equals(data.moldType)) {
		    //					Kogger.debug(getClass(), "");
		    //					Kogger.debug(getClass(), "moldType == " + data.moldType);
		    //					Kogger.debug(getClass(), "partNo == " + data.partNo + " / " + miInfo.getPartNo());
		    if ((data.partNo).equals(miInfo.getPartNo())) {
			data.isExist = true;
		    }
		    else {
			data.isExist = false;
		    }
		}
		else {
		    data.isExist = true;
		}
	    }
	    else {
		data.isExist = false;
	    }




	} catch (Exception e) {
	    Kogger.error(ItemInfoDataLoader.class, e);
	    throw new WTException(e);
	} finally {
	    if (data != null) {
		data.msg = sb.toString();
	    }
	}
	return data;
    }


    public static ItemInfoData save(ItemInfoData data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }
	    Kogger.debug(ItemInfoDataLoader.class, "############################ Save Start");
	    Kogger.debug(ItemInfoDataLoader.class, data.projectNo);
	    Kogger.debug(ItemInfoDataLoader.class, data.itemType);
	    Kogger.debug(ItemInfoDataLoader.class, data.productType);
	    Kogger.debug(ItemInfoDataLoader.class, data.partNo);
	    Kogger.debug(ItemInfoDataLoader.class, data.partName);
	    Kogger.debug(ItemInfoDataLoader.class, data.dieNo);
	    Kogger.debug(ItemInfoDataLoader.class, data.moldType);
	    Kogger.debug(ItemInfoDataLoader.class, data.cVPitch);
	    Kogger.debug(ItemInfoDataLoader.class, data.cTSPM);
	    Kogger.debug(ItemInfoDataLoader.class, data.productionPlace1);
	    Kogger.debug(ItemInfoDataLoader.class, data.productionPlace2);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialGubun);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialMaker);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialType);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialGrade);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialCharacter);
	    Kogger.debug(ItemInfoDataLoader.class, "############################ Save End");


	    E3PSProject project = ProjectHelper.getProject(data.projectNo.toUpperCase());
	    if (project != null && project instanceof ProductProject) {
		MoldItemInfo moldItemInfo = MoldItemInfo.newMoldItemInfo();
		moldItemInfo.setProject((ProductProject) project);

		if (data.itemType != null) moldItemInfo.setItemType(data.itemType);

		NumberCode code = null;

		if (data.productType != null) {
		    code = NumberCodeHelper.manager.getNumberCodeName("MOLDPRODUCTSTYPE", data.productType);
		    if (code != null) moldItemInfo.setProductType(code);
		}

		if (data.partNo != null) moldItemInfo.setPartNo(data.partNo);
		if (data.partName != null) moldItemInfo.setPartName(data.partName);
		if (data.dieNo != null) moldItemInfo.setDieNo(data.dieNo);

		if (data.moldType != null) {
		    code = NumberCodeHelper.manager.getNumberCodeName("MOLDTYPE", data.moldType);
		    if (code != null) moldItemInfo.setMoldType(code);
		}

		if (data.cVPitch != null) moldItemInfo.setCVPitch(data.cVPitch);
		if (data.cTSPM != null) moldItemInfo.setCTSPM(data.cTSPM);
		//moldItemInfo.setMaking((String)making.get(i));
		if (data.productionPlace1 != null) {
		    moldItemInfo.setProductionPlace(data.productionPlace1);

		    if (data.productionPlace2 != null) {
			if ("사내".equals(data.productionPlace1)) {
			    code = NumberCodeHelper.manager.getNumberCodeName("PRODUCTIONDEPT", data.productionPlace2);
			    if (code != null) moldItemInfo.setPurchasePlace(code);
			}
			else {
			    //							PartnerDao pDao = new PartnerDao();
			    //							String proteamNo = pDao.ViewPartnerNo(data.productionPlace2);
			    String proteamNo = ProductHelper.ViewPartnerNo(data.productionPlace2);
			    if (proteamNo != null && proteamNo.length() > 0) moldItemInfo.setPartnerNo(proteamNo);
			}
		    }
		}

		if (data.materialGubun != null && data.materialMaker != null && data.materialType != null && data.materialGrade != null) {
		    NumberCode maker = NumberCodeHelper.manager.getNumberCodeName("MATERIALMAKER", data.materialMaker);
		    if (maker != null) {
			NumberCode type = NumberCodeHelper.manager.getNumberCodeName("MATERIALTYPE", data.materialType);
			if (type != null) {
			    MoldMaterial material = MaterialLoad.checkCodeData(data.materialGubun, maker, type, data.materialGrade);
			    if (material != null) moldItemInfo.setMaterial(material);
			}
		    }
		}

		if (data.materialCharacter != null) {
		    code = NumberCodeHelper.manager.getNumberCodeName("MATERIALPROPERTIES", data.materialCharacter);
		    if (code != null) moldItemInfo.setProperty(code);
		}

		moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
	    }

	} catch (Exception e) {
	    Kogger.error(ItemInfoDataLoader.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static ItemInfoData update(ItemInfoData data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }
	    Kogger.debug(ItemInfoDataLoader.class, "############################ Update Start");
	    Kogger.debug(ItemInfoDataLoader.class, data.projectNo);
	    Kogger.debug(ItemInfoDataLoader.class, data.itemType);
	    Kogger.debug(ItemInfoDataLoader.class, data.productType);
	    Kogger.debug(ItemInfoDataLoader.class, data.partNo);
	    Kogger.debug(ItemInfoDataLoader.class, data.partName);
	    Kogger.debug(ItemInfoDataLoader.class, data.dieNo);
	    Kogger.debug(ItemInfoDataLoader.class, data.moldType);
	    Kogger.debug(ItemInfoDataLoader.class, data.cVPitch);
	    Kogger.debug(ItemInfoDataLoader.class, data.cTSPM);
	    Kogger.debug(ItemInfoDataLoader.class, data.productionPlace1);
	    Kogger.debug(ItemInfoDataLoader.class, data.productionPlace2);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialGubun);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialMaker);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialType);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialGrade);
	    Kogger.debug(ItemInfoDataLoader.class, data.materialCharacter);
	    Kogger.debug(ItemInfoDataLoader.class, "############################ Update End");


	    E3PSProject project = ProjectHelper.getProject(data.projectNo.toUpperCase());
	    if (project != null && project instanceof ProductProject) {


		MoldItemInfo moldItemInfo = MoldProjectHelper.getMoldItem(data.dieNo, data.partNo);
		moldItemInfo.setProject((ProductProject) project);

		if (data.itemType != null) moldItemInfo.setItemType(data.itemType);

		NumberCode code = null;

		if (data.productType != null) {
		    code = NumberCodeHelper.manager.getNumberCodeName("MOLDPRODUCTSTYPE", data.productType);
		    if (code != null) moldItemInfo.setProductType(code);
		}

		if (data.partNo != null) moldItemInfo.setPartNo(data.partNo);
		if (data.partName != null) moldItemInfo.setPartName(data.partName);
		if (data.dieNo != null) moldItemInfo.setDieNo(data.dieNo);

		if (data.moldType != null) {
		    code = NumberCodeHelper.manager.getNumberCodeName("MOLDTYPE", data.moldType);
		    if (code != null) moldItemInfo.setMoldType(code);
		}

		if (data.cVPitch != null) moldItemInfo.setCVPitch(data.cVPitch);
		if (data.cTSPM != null) moldItemInfo.setCTSPM(data.cTSPM);
		//moldItemInfo.setMaking((String)making.get(i));
		if (data.productionPlace1 != null) {
		    moldItemInfo.setProductionPlace(data.productionPlace1);

		    if (data.productionPlace2 != null) {
			if ("사내".equals(data.productionPlace1)) {
			    code = NumberCodeHelper.manager.getNumberCodeName("PRODUCTIONDEPT", data.productionPlace2);
			    if (code != null) moldItemInfo.setPurchasePlace(code);
			}
			else {
			    //							PartnerDao pDao = new PartnerDao();
			    //							String proteamNo = pDao.ViewPartnerNo(data.productionPlace2);
			    String proteamNo = ProductHelper.ViewPartnerNo(data.productionPlace2);
			    if (proteamNo != null && proteamNo.length() > 0) moldItemInfo.setPartnerNo(proteamNo);
			}
		    }
		}

		if (data.materialGubun != null && data.materialMaker != null && data.materialType != null && data.materialGrade != null) {
		    NumberCode maker = NumberCodeHelper.manager.getNumberCodeName("MATERIALMAKER", data.materialMaker);
		    if (maker != null) {
			NumberCode type = NumberCodeHelper.manager.getNumberCodeName("MATERIALTYPE", data.materialType);
			if (type != null) {
			    MoldMaterial material = MaterialLoad.checkCodeData(data.materialGubun, maker, type, data.materialGrade);
			    if (material != null) moldItemInfo.setMaterial(material);
			}
		    }
		}

		if (data.materialCharacter != null) {
		    code = NumberCodeHelper.manager.getNumberCodeName("MATERIALPROPERTIES", data.materialCharacter);
		    if (code != null) moldItemInfo.setProperty(code);
		}

		moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
	    }

	} catch (Exception e) {
	    Kogger.error(ItemInfoDataLoader.class, e);
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
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", ItemInfoDataLoader.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(ItemInfoDataLoader.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(ItemInfoDataLoader.class, e);
		throw new WTException(e);
	    }
	    return obj;
	}

	Transaction trx = new Transaction();

	Vector r = new Vector();
	try {
	    trx.start();

	    for (int i = 0; i < data.size(); i++) {
		ItemInfoData ppData = (ItemInfoData) data.get(i);

		try {
		    if (ppData.isExist) {
			ppData = update(ppData);
		    }
		    else {
			ppData = save((ItemInfoData) data.get(i));
		    }
		    ppData.isLoadCompleted = true;
		} catch (Exception e) {
		    ppData.isLoadCompleted = false;
		    ppData.msg += "\n" + e.getLocalizedMessage();
		    throw new Exception(e);
		}

		r.add(ppData);
	    }

	    trx.commit();
	    trx = null;
	} catch (Exception e) {
	    trx.rollback();
	    Kogger.error(ItemInfoDataLoader.class, e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return r;
    }


    public static void main(String[] args) throws Exception {
	ItemInfoDataLoader loader = new ItemInfoDataLoader();
	//		String type = "전자";
	//		loader.loadFile("제품프로젝트_V1.0_전자_101120.xls", type)
	wt.method.RemoteMethodServer.getDefault().setUserName(args[1]);
	wt.method.RemoteMethodServer.getDefault().setPassword(args[2]);

	loader.loadFile(args[0]);

    }

}

class ItemInfoData implements Serializable {

    /**
	 * 
	 */
    /*	private static final long serialVersionUID = 1L;
    */
    public int     rownum;

    public String  projectNo;
    public String  itemType;
    public String  productType;
    public String  partNo;
    public String  partName;
    public String  dieNo;
    public String  moldType;
    public String  cVPitch;
    public String  cTSPM;
    public String  productionPlace1;
    public String  productionPlace2;
    public String  materialGubun;
    public String  materialMaker;
    public String  materialType;
    public String  materialGrade;
    public String  materialCharacter;

    public String  msg;

    public boolean isExist         = false;
    /*public boolean isDocNumberSizeValid = true; */
    public boolean isValidate      = true;
    /*public boolean isRepPartExist = true;
    */
    public boolean isLoadCompleted = false;

    public ItemInfoData(Cell[] cells, int rownum) {
	this.rownum = rownum;

	int colIdex = -1;
	projectNo = JExcelUtil.getContent(cells, ++colIdex);
	itemType = JExcelUtil.getContent(cells, ++colIdex);
	productType = JExcelUtil.getContent(cells, ++colIdex);
	partNo = JExcelUtil.getContent(cells, ++colIdex);
	partName = JExcelUtil.getContent(cells, ++colIdex);
	dieNo = JExcelUtil.getContent(cells, ++colIdex);
	moldType = JExcelUtil.getContent(cells, ++colIdex);
	cVPitch = JExcelUtil.getContent(cells, ++colIdex);
	cTSPM = JExcelUtil.getContent(cells, ++colIdex);
	productionPlace1 = JExcelUtil.getContent(cells, ++colIdex);
	productionPlace2 = JExcelUtil.getContent(cells, ++colIdex);
	materialGubun = JExcelUtil.getContent(cells, ++colIdex);
	materialMaker = JExcelUtil.getContent(cells, ++colIdex);
	materialType = JExcelUtil.getContent(cells, ++colIdex);
	materialGrade = JExcelUtil.getContent(cells, ++colIdex);
	materialCharacter = JExcelUtil.getContent(cells, ++colIdex);
    }

}
