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
import wt.fc.QueryResult;
import wt.pom.Transaction;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTProperties;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.JExcelUtil;
import e3ps.project.CostInfo;
import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.ProductProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.shared.log.Kogger;

public class CostInfoDataLoader implements wt.method.RemoteAccess, java.io.Serializable {

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
	    Kogger.error(CostInfoDataLoader.class, e);
	}
    }

    private static String logFile   = "productprojectlogfile.log";

    public static boolean loadFile(String fileName) throws WTException {
	try {
	    String filePath = EXCELFILE + SEPARATOR + fileName;
	    File dataFile = new File(filePath);
	    if (!dataFile.exists()) {
		Kogger.debug(CostInfoDataLoader.class, "File not found!!!");
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
		    //Kogger.debug(CostInfoDataLoader.class, );
		    for (int k = 0; k < hdrs.length; k++) {
			Cell hdr = hdrs[k];
			System.out.print("[" + hdr.getContents() + "]");
		    }
		    Kogger.debug(CostInfoDataLoader.class, "");
		    for (int k = 3; k < rows; k++) {
			Cell cell[] = sheet.getRow(k);
			for (int a = 0; a < cell.length; a++) {
			    System.out.print("<" + cell[a].getContents() + ">");
			}
			CostInfoData data = (CostInfoData) getRowData(sheet.getRow(k), k + 1, sheet.getName());

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
				    CostInfoData rdata0 = (CostInfoData) v0.get(m);
				    if (!(rdata0.isLoadCompleted)) {
					System.out.print("[[" + rdata0.msg + "]]");
					//log(rdata0.msg);
				    }
				}
			    }
			}

		    }
		    //Kogger.debug(CostInfoDataLoader.class, );
		    //System.out.print("#################################################################################################Loader Attr END ");
		}
	    }
	    workbook.close();
	    workbook = null;
	} catch (Exception e) {
	    Kogger.error(CostInfoDataLoader.class, e);
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
    			Kogger.error(CostInfoDataLoader.class, e);
    		}
    	}	
    */
    public static Object getRowData(Cell[] cells, int rownum, String sheetName) throws WTException {
	CostInfoData data = null;

	StringBuffer sb = new StringBuffer();
	try {
	    if (JExcelUtil.getContent(cells, 0).length() == 0) {
		return null;
	    }

	    sb.append("\n############## >>> [ row:" + rownum + " ]-[ sheet:" + sheetName + " ] <<< ##############");
	    data = new CostInfoData(cells, rownum);

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
	    //			data.isExist = (project!=null)? false:true;

	    if (data.dieNo.length() == 0) {
		data.isValidate = false;
		sb.append("\n").append("- Die No : 입력값이 없습니다.");
	    }

	} catch (Exception e) {
	    Kogger.error(CostInfoDataLoader.class, e);
	    throw new WTException(e);
	} finally {
	    if (data != null) {
		data.msg = sb.toString();
	    }
	}
	return data;
    }


    public static CostInfoData save(CostInfoData data) throws WTException {
	try {
	    if (!data.isValidate) {
		return data;
	    }
	    Kogger.debug(CostInfoDataLoader.class, "############################ Save Start");
	    Kogger.debug(CostInfoDataLoader.class, data.projectNo);
	    Kogger.debug(CostInfoDataLoader.class, data.costType);
	    Kogger.debug(CostInfoDataLoader.class, data.partNo);
	    Kogger.debug(CostInfoDataLoader.class, data.dieNo);
	    Kogger.debug(CostInfoDataLoader.class, data.costName);
	    Kogger.debug(CostInfoDataLoader.class, data.orderInvest);
	    Kogger.debug(CostInfoDataLoader.class, data.targetCost);
	    Kogger.debug(CostInfoDataLoader.class, data.executionCost);
	    Kogger.debug(CostInfoDataLoader.class, data.editCost);
	    Kogger.debug(CostInfoDataLoader.class, "############################ Save End");


	    E3PSProject project = ProjectHelper.getProject(data.projectNo.toUpperCase());
	    if (project != null && project instanceof ProductProject) {
		CostInfo costInfo = CostInfo.newCostInfo();
		costInfo.setProject((ProductProject) project);

		if (data.costType != null) costInfo.setCostType(data.costType);
		if (data.partNo != null) costInfo.setPartNo(data.partNo);
		if (data.dieNo != null) costInfo.setDieNo(data.dieNo);
		if (data.costName != null) costInfo.setCostName(data.costName);
		if (data.orderInvest != null) costInfo.setOrderInvest(data.orderInvest);
		if (data.targetCost != null) costInfo.setTargetCost(data.targetCost);
		//costInfo.setDecideCost((String)decideCost.get(i));
		if (data.executionCost != null) costInfo.setExecutionCost(data.executionCost);
		if (data.editCost != null) costInfo.setEditCost(data.editCost);

		costInfo = (CostInfo) PersistenceHelper.manager.save(costInfo);

		if (data.costType != null) {
		    if ("금형".equals(data.costType)) {
			QuerySpec specItem = new QuerySpec();
			int idx_Item = specItem.addClassList(MoldItemInfo.class, true);
			SearchCondition scItem = new SearchCondition(MoldItemInfo.class, "projectReference.key.id", "=", CommonUtil.getOIDLongValue(project));
			specItem.appendWhere(scItem, new int[] { idx_Item });

			if (data.dieNo != null) {
			    specItem.appendAnd();
			    scItem = new SearchCondition(MoldItemInfo.class, "dieNo", "=", data.dieNo);
			    specItem.appendWhere(scItem, new int[] { idx_Item });
			}

			QueryResult rtItem = PersistenceHelper.manager.find(specItem);
			while (rtItem.hasMoreElements()) {
			    Object obj[] = (Object[]) rtItem.nextElement();
			    MoldItemInfo moldItemInfo = (MoldItemInfo) obj[0];
			    moldItemInfo.setCostInfo(costInfo);
			    moldItemInfo = (MoldItemInfo) PersistenceHelper.manager.save(moldItemInfo);
			}
		    }
		}

	    }

	} catch (Exception e) {
	    Kogger.error(CostInfoDataLoader.class, e);
	    throw new WTException(e);
	}
	return data;
    }

    public static ItemInfoData update(ItemInfoData data) throws WTException {
	/*		try {
				if(!data.isValidate) {
					return data;
				}
				Kogger.debug(getClass(), "############################ update Start");
				Kogger.debug(getClass(), data.projectNo);
				Kogger.debug(getClass(), data.projectName);
				Kogger.debug(getClass(), data.salesID);
				Kogger.debug(getClass(), data.teamType);
				Kogger.debug(getClass(), data.devDeptName);
				Kogger.debug(getClass(), data.pmID);
				Kogger.debug(getClass(), data.partNo);
				Kogger.debug(getClass(), data.producttype);
				Kogger.debug(getClass(), data.rank);
				Kogger.debug(getClass(), data.model);
				Kogger.debug(getClass(), data.assembledtype);
				Kogger.debug(getClass(), data.process);
				Kogger.debug(getClass(), data.developenttype);
				Kogger.debug(getClass(), data.designType);
				Kogger.debug(getClass(), data.planStartDate);
				Kogger.debug(getClass(), data.planEndDate);
				Kogger.debug(getClass(), data.customereventName);
				Kogger.debug(getClass(), data.subcontractorName);
				Kogger.debug(getClass(), data.costsDate);
				Kogger.debug(getClass(), data.state);
				Kogger.debug(getClass(), "############################ update End");
			   
				   String stateCheck = "";
				   if(data.state.equals("진행")){
					   stateCheck = "PROGRESS";
				   }else{
					   stateCheck = "COMPLETED";
				   }
				   
				   ProductProject project = null;
				   Calendar tempCal = Calendar.getInstance();
				   
				   E3PSProject e3ps = ProjectHelper.getProject(data.projectNo.toUpperCase()); 
				   Object obj = e3ps;
				   project = (ProductProject)obj;
				   
				   ExtendScheduleData schedule = ExtendScheduleData.newExtendScheduleData();
				   if(data.planStartDate != null && data.planEndDate != null) {
					   int tempDuration = DateUtil.getDaysFromTo(DateUtil.getDateString(data.planEndDate, "d"), DateUtil.getDateString(data.planStartDate, "d"));
					   schedule.setDuration(tempDuration);
				   }
				   schedule.setScheduleHistory(0);
				   
				   if(data.planStartDate != null) {
					   tempCal.setTime(DateUtil.parseDateStr( DateUtil.getDateString(data.planStartDate, "d") ));
					   schedule.setPlanStartDate(new java.sql.Timestamp(tempCal.getTime().getTime()));
				   }
				   schedule.setExecWork(0);
				   schedule = (ExtendScheduleData) PersistenceHelper.manager.save(schedule);
				   
				   String lifecycle = "KET_PRODUCT_PMS_LC";
			       
				   project.setPjtNo(data.projectNo.trim());
				   project.setPjtSeq(0);
				   project.setPjtHistory(0);
				   project.setPjtCompletion(0);
				   project.setLastest(true);
				   project.setPjtSchedule(ObjectReference.newObjectReference(schedule));
				   
				   if(StringUtil.checkString(data.projectName.trim())){
					   project.setPjtName(StringUtil.checkNull(data.projectName.trim()));
				   }
				   project.setPjtState(1);
				   if(data.teamType.equals("자동차 사업부")){
					   project.setPjtType(2);
				   }else{
					   project.setPjtType(4);
				   }
				   
		   		   project.setTeamType(data.teamType);
		   		   project.setPartNo(data.partNo);
				   project.setCostsDate( data.costsDate );

				   //Number Code
				   NumberCode producttype2Level = null;
				   NumberCode rank2Level = null;
		   				   
				   if(data.teamType.equals("자동차 사업부")){
					   producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "자동차", data.producttype);
					   rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "자동차", data.rank);
				   }else{
					   producttype2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("PRODUCTTYPE", "전자", data.producttype);
					   rank2Level = NumberCodeHelper.manager.getNumberCodeOnlyOneType("RANK", "전자", data.rank);
				   }

				   if(producttype2Level != null) {
		   			   project.setProductType(producttype2Level);
		   		   }
		   		   if(rank2Level != null) {
		  			   project.setRank(rank2Level);
		   		   }
		   		   NumberCode assembledtype1Level = NumberCodeHelper.manager.getNumberCodeName("ASSEMBLEDTYPE", data.assembledtype);
		   		   if(assembledtype1Level != null) {
		   			   project.setAssembledType(assembledtype1Level);
		   		   }
		   		   NumberCode process = NumberCodeHelper.manager.getNumberCodeName("PROCESS", data.process);
		   		   if(process != null) {
		   			   project.setProcess(process);
		   		   }
		   		   NumberCode developentType = NumberCodeHelper.manager.getNumberCodeName("DEVELOPENTTYPE", data.developenttype);
		   		   if(developentType != null) {
		   			   project.setDevelopentType(developentType);
		   		   }
		   		   NumberCode designType = NumberCodeHelper.manager.getNumberCodeName("DESIGNTYPE", data.designType);
		   		   if(designType != null) {
		   			   project.setDesignType(designType);
		   		   }
		   		   
		/*   		   if(data.model != null && data.model.length() > 0) {
						   OEMProjectType modelType = (OEMProjectType) CommonUtil.getObject((String)model);
						   if(modelType != null) {
							   project.setOemType(modelType);
						   }
		   		   }

		   		   

		   		   
		   		   
		   		   //2.16 ???? [PLM Attribute]
		   		   String proteamNo = (String) hash.get("proteamNo");
		   		   if(proteamNo != null && proteamNo.length() > 0) {
		   			   if(proteamNo.indexOf("NumberCode") > -1) {
		   				   NumberCode code = (NumberCode)CommonUtil.getObject(proteamNo);
		   				   project.setAssemblyPlace(code);
		   			   }else {
		   				   project.setPartnerNo(proteamNo);
		   			   }
		   		   }
		*/

	//Development Department
	/*			   People devPeople =PeopleHelper.manager.getPeople(data.pmID);
				   WTUser wtdevUser = null;
				   if(devPeople != null ){
					   PeopleData pData = new PeopleData(devPeople);
					   wtdevUser = (WTUser) pData.wtuser;
				   }
				   if(wtdevUser != null) ProjectUserHelper.manager.setPM(project, wtdevUser, 0);
				  
				   // salesUser
				   People salesPeople =PeopleHelper.manager.getPeople(data.salesID);
				   if(salesPeople  != null){
					   PeopleData pData = new PeopleData(salesPeople);
					   WTUser wtsalesUser =  (WTUser) pData.wtuser;
					   if(wtsalesUser != null) { project.setBusiness(wtsalesUser); }
				   }
				   
					QueryResult departmentQr = ProjectUserHelper.manager.getViewDepartmentLink(project, null);
					if(departmentQr.hasMoreElements()) {
						Object[] objDept = (Object[])departmentQr.nextElement();
						ProjectViewDepartmentLink link = (ProjectViewDepartmentLink)objDept[0];
						PersistenceHelper.manager.delete(link);
					}
				   Department depart = DepartmentHelper.manager.getDepartmentObj(data.devDeptName);
		   		   if(depart != null) {
			   		   ArrayList list = new ArrayList();
			   		   list.add(depart);
			   		   boolean flag = ProjectHelper.addRefDepartment(project, list);
		   		   }
				   
				   //save
				   project = (ProductProject) PersistenceHelper.manager.save(project);
				   
				   String ce = data.customereventName;
				   String sc = data.subcontractorName;
				   StringTokenizer cetoken = new StringTokenizer(ce, ",");
				   int token1 = 0;
				   String ceValue[] = new String[cetoken.countTokens()];
				   NumberCode nc = null;
				   QueryResult result = null;
					QuerySpec qs = new QuerySpec();
					int index = qs.appendClassList(ProjectCustomereventLink.class, true);
					qs.appendWhere(new SearchCondition(ProjectCustomereventLink.class,
															"roleBObjectRef.key.id","=",
																CommonUtil.getOIDLongValue(project)),new int[] {index} );
					result = PersistenceHelper.manager.find(qs);
					
					while(result.hasMoreElements())
					{
						Object[] object = (Object[])result.nextElement();
						ProjectCustomereventLink link = (ProjectCustomereventLink)object[0];
						if(link != null){
						PersistenceHelper.manager.delete(link);
						}
					}		   
				   
				   while(cetoken.hasMoreElements()){
					   ceValue[token1] = cetoken.nextToken();
					   nc = NumberCodeHelper.manager.getNumberCodeName("CUSTOMEREVENT", ceValue[token1]);
		        	   if(nc != null){
		        		   	ProjectCustomereventLink plink = ProjectCustomereventLink.newProjectCustomereventLink(nc, project);
		        	   		PersistenceHelper.manager.save(plink);
		        	   }else{
		        		   throw new Exception("최종 사용처 "+ ceValue[token1]+" 정보가 없습니다.");
		        	   }	
					   token1++;
				   }
				   
				   StringTokenizer sctoken = new StringTokenizer(sc, ",");
				   int token2 = 0;
				   String scValue[] = new String[sctoken.countTokens()];
				    QueryResult result2 = null;
					QuerySpec qs2 = new QuerySpec();
					int index2 = qs2.appendClassList(ProjectSubcontractorLink.class, true);
					qs2.appendWhere(new SearchCondition(ProjectSubcontractorLink.class,
															"roleBObjectRef.key.id","=",
																CommonUtil.getOIDLongValue(project)),new int[] {index2} );
					result2 = PersistenceHelper.manager.find(qs2);
					
					while(result2.hasMoreElements())
					{
						Object[] object = (Object[])result2.nextElement();
						ProjectSubcontractorLink link = (ProjectSubcontractorLink)object[0];
						if(link != null){
						PersistenceHelper.manager.delete(link);
						}
					}
				   
				   while(sctoken.hasMoreElements()){
					   scValue[token2] = sctoken.nextToken();
					   nc = NumberCodeHelper.manager.getNumberCodeName("SUBCONTRACTOR", scValue[token2]);
		        	   if(nc != null){
		        		   ProjectSubcontractorLink plink = ProjectSubcontractorLink.newProjectSubcontractorLink(nc, project);
		        		   PersistenceHelper.manager.save(plink);
		        	   }else{
		        		   throw new Exception("개발검토 의뢰처  "+ scValue[token2]+" 정보가 없습니다.");
		        	   }
		        	   token2++;
				   }
				   
				   project = (ProductProject)LifeCycleHelper.service.setLifeCycleState(project, State.toState(stateCheck), false);

			}
			catch(Exception e) {
				Kogger.error(getClass(), e);
				throw new WTException(e);
			}
	*/return data;
    }

    public static Object save(Vector data) throws WTException {
	if (!SERVER) {
	    Class argTypes[] = new Class[] { Vector.class };
	    Object args[] = new Object[] { data };
	    Object obj = null;
	    try {
		obj = wt.method.RemoteMethodServer.getDefault().invoke("save", CostInfoDataLoader.class.getName(), null, argTypes, args);
	    } catch (RemoteException e) {
		Kogger.error(CostInfoDataLoader.class, e);
		throw new WTException(e);
	    } catch (InvocationTargetException e) {
		Kogger.error(CostInfoDataLoader.class, e);
		throw new WTException(e);
	    }
	    return obj;
	}

	Transaction trx = new Transaction();

	Vector r = new Vector();
	try {
	    trx.start();

	    for (int i = 0; i < data.size(); i++) {
		CostInfoData ppData = (CostInfoData) data.get(i);

		try {
		    if (ppData.isExist) {
			//						ppData = update(ppData);
		    }
		    else {
			ppData = save((CostInfoData) data.get(i));
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
	    Kogger.error(CostInfoDataLoader.class, e);
	} finally {
	    if (trx != null) {
		trx = null;
	    }
	}
	return r;
    }


    public static void main(String[] args) throws Exception {
	CostInfoDataLoader loader = new CostInfoDataLoader();
	//		String type = "전자";
	//		loader.loadFile("제품프로젝트_V1.0_전자_101120.xls", type);
	wt.method.RemoteMethodServer.getDefault().setUserName(args[1]);
	wt.method.RemoteMethodServer.getDefault().setPassword(args[2]);
	loader.loadFile(args[0]);

    }

}

class CostInfoData implements Serializable {

    /**
	 * 
	 */
    /*	private static final long serialVersionUID = 1L;
    */
    public int     rownum;

    public String  projectNo;
    public String  costType;
    public String  partNo;
    public String  dieNo;
    public String  costName;
    public String  orderInvest;
    public String  targetCost;
    public String  executionCost;
    public String  editCost;

    public String  msg;

    public boolean isExist         = false;
    /*public boolean isDocNumberSizeValid = true; */
    public boolean isValidate      = true;
    /*public boolean isRepPartExist = true;
    */
    public boolean isLoadCompleted = false;

    public CostInfoData(Cell[] cells, int rownum) {
	this.rownum = rownum;

	int colIdex = -1;
	projectNo = JExcelUtil.getContent(cells, ++colIdex);
	costType = JExcelUtil.getContent(cells, ++colIdex);
	partNo = JExcelUtil.getContent(cells, ++colIdex);
	dieNo = JExcelUtil.getContent(cells, ++colIdex);
	costName = JExcelUtil.getContent(cells, ++colIdex);
	orderInvest = JExcelUtil.getContent(cells, ++colIdex);
	targetCost = JExcelUtil.getContent(cells, ++colIdex);
	targetCost = targetCost.replaceAll(",", "");
	executionCost = JExcelUtil.getContent(cells, ++colIdex);
	executionCost = executionCost.replaceAll(",", "");
	editCost = JExcelUtil.getContent(cells, ++colIdex);
	editCost = editCost.replaceAll(",", "");
    }

}
