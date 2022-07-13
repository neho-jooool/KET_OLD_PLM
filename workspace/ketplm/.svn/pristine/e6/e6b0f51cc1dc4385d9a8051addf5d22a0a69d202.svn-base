package e3ps.load.remote.edm;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.KeywordExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.query.SubSelectExpression;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.common.code.NumberCode;
import e3ps.common.code.NumberCodeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ExtendScheduleData;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectCustomereventLink;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.ProjectViewDepartmentLink;
import e3ps.project.ReviewProject;
import e3ps.project.beans.E3PSProjectData;
import e3ps.project.outputtype.OEMProjectType;
import ext.ket.shared.log.Kogger;

public class SearchProjectHelper  implements wt.method.RemoteAccess, java.io.Serializable {

	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public static Vector<Hashtable<String, String>> find(HashMap map) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"find",
						"e3ps.load.remote.edm.SearchProjectHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(SearchProjectHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(SearchProjectHelper.class, e);
				throw new WTException(e);
			}
			return (Vector<Hashtable<String, String>>)obj;
		}

		QueryResult result = null;
		Vector<Hashtable<String, String>> vec = new Vector<Hashtable<String, String>>();

		try
		{
Kogger.debug(SearchProjectHelper.class,  "======> SearchProjectHelper : find() Start!!!!" );

			result = PersistenceHelper.manager.find(getE3PSProjectQuerySpec(map));

Kogger.debug(SearchProjectHelper.class,  "======> SearchProjectHelper : find() Result Size : " + result.size() );

            Object[] obj = null;
            E3PSProjectData data = null;
            E3PSProject project = null;

           	 while ( result.hasMoreElements() )
           	 {
           		 Hashtable<String, String> hash = new Hashtable<String, String>();

           		 obj = (Object[]) result.nextElement();
           		 project = (E3PSProject) obj[0];
				 data = new E3PSProjectData((E3PSProject) obj[0]);

				 hash.put( "projectNumber", data.pjtNo );
				 hash.put( "projectName", data.pjtName );
				 hash.put( "planStartDate", DateUtil.getDateString(data.pjtPlanStartDate, "d") );
				 hash.put( "planEndDate", DateUtil.getDateString(data.pjtPlanEndDate, "d") );
				 hash.put( "status", data.state );
				 hash.put( "oid", PersistenceHelper.getObjectIdentifier(project).getStringValue() );

				 vec.addElement( hash );
           	 }
		}
		catch(Exception e){
			Kogger.error(SearchProjectHelper.class, e);
		}
		return vec;
	}


	//////////////////////////////////////////////////////////////////////////////
	// getE3PSProjectQuerySpec
	// Sang Min, Kim
	// smkim@e3ps.com
	// 수정
	//////////////////////////////////////////////////////////////////////////////
	public static QuerySpec getE3PSProjectQuerySpec(HashMap map) throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{HashMap.class};
			Object args[] = new Object[]{map};
			Object obj = null;
			try {
				obj = wt.method.RemoteMethodServer.getDefault().invoke(
						"getE3PSProjectQuerySpec",
						"e3ps.load.remote.edm.SearchProjectHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(SearchProjectHelper.class, e);
				throw new WTException(e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(SearchProjectHelper.class, e);
				throw new WTException(e);
			}
			return (QuerySpec)obj;
		}

		QuerySpec mainSpec = null;
		try {
			//Attribute Setting
			String cmd = (String)map.get("command");
			String initType =(String)map.get("initType");
			String pjtType = (String)map.get("pjtType");
			if(cmd == null)
				cmd = "";
			mainSpec = new QuerySpec();
			Class main_target = E3PSProject.class;
			if(pjtType.equals("3")){
				main_target = MoldProject.class;
			}
			int main_idx = mainSpec.addClassList(main_target, true);

			/////////////////////////////////////////////////////////////////////
			// Sub Query Start mainSpec
			/////////////////////////////////////////////////////////////////////

			if(!mainSpec.isAdvancedQueryEnabled()) {
				mainSpec.setAdvancedQueryEnabled(true);
			}

			QuerySpec spec = null;
			spec = new QuerySpec();

			if(!spec.isAdvancedQueryEnabled()) {
				spec.setAdvancedQueryEnabled(true);
			}

			Class target = E3PSProject.class;
											//pjtType(프로젝트종류)
			if(pjtType.equals("0") || pjtType.equals("1")){
				target = ReviewProject.class;
			}

			if(pjtType.equals("2") || pjtType.equals("4")){
				target = ProductProject.class;
			}

			if(pjtType.equals("3")){
				target = MoldProject.class;
			}


			int idx_target = spec.addClassList(target, false);

			spec.setDistinct(true);

    		ClassAttribute ca = new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
        	ca.setColumnAlias("projectId");
        	spec.appendSelect(ca, true);


        	String pjtNo = (String)map.get("pjtNo");										//pjtNo(프로젝트 NO)
			String pjtName = (String)map.get("pjtName");									//pjtName(프로젝트 명)

			String pjtState = (String)map.get("pjtState");
        	String setPm = (String)map.get("setPm");								// setPm (PM);

        	long pmUser = 0;
        	if(setPm != null && setPm.length()>0){
        		pmUser = CommonUtil.getOIDLongValue(setPm);
        	}


			WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
	        long lperistable=CommonUtil.getOIDLongValue(wtuser);

	        /*
	        Department dept = e3ps.groupware.company.DepartmentHelper.manager.getDepartment(wtuser);
	        ArrayList childList = null;
	        if(dept != null) {
	        	childList = e3ps.groupware.company.DepartmentHelper.manager.getChildDeptTree(dept);
	        }

	        if(childList == null) {
	        	childList = new ArrayList();
	        }
	        childList.add(0, dept);
	        */
	        String pdevDeptOid = (String)map.get("pdevDeptOid");
	        String rdevDeptOid = (String)map.get("rdevDeptOid");

			if(StringUtil.checkString(cmd)) {
				if(cmd.equalsIgnoreCase("search") || cmd.equalsIgnoreCase("select")) {
					//프로젝트 NO
					if(StringUtil.checkString(pjtNo)) {
						pjtNo = StringUtil.changeString(pjtNo, "*", "%");
						setUpperNoneLikeCondition(spec, target, idx_target, E3PSProject.PJT_NO, pjtNo.trim() );
					}
					//프로젝트 명
					if(StringUtil.checkString(pjtName)) {
						pjtName = StringUtil.changeString(pjtName, "*", "%");
						setUpperNoneLikeCondition(spec, target, idx_target, E3PSProject.PJT_NAME, pjtName.trim() );
					}
					//프로젝트 상태
					if(StringUtil.checkString(pjtState)) {
						if(spec.getConditionCount() > 0){
							spec.appendAnd();
						}

						if(pjtState.equals("UNDERREVIEW")){
							Hashtable h = new Hashtable();
							h.put("INWORK", "INWORK");
							h.put("UNDERREVIEW", "UNDERREVIEW");
							h.put("APPROVED", "APPROVED");
							h.put("REJECTED", "REJECTED");
							h.put("REWORK", "REWORK");

							Enumeration e = h.keys();
							spec.appendOpenParen();
							boolean start = true;
							while(e.hasMoreElements()){
								if(!start){
									spec.appendOr();
								}else{
									start = false;
								}
								String key = (String)e.nextElement();
								SearchCondition where = new SearchCondition(target, "state.state" , SearchCondition.EQUAL, key);
								spec.appendWhere(where, new int[]{idx_target});
							}
							spec.appendCloseParen();
						}else{

							SearchCondition where = new SearchCondition(target, "state.state" , SearchCondition.EQUAL, pjtState);
							spec.appendWhere(where, new int[]{idx_target});
						}

					}
					// PM
//					if(StringUtil.checkString(setPm)){
//						if(spec.getConditionCount() > 0)
//							spec.appendAnd();
//
//						Class prjectMember = ProjectMemberLink.class;
//						int idx_Member = spec.appendClassList(prjectMember, false);
//
//
//						ClassAttribute ca1 = null;
//			            ClassAttribute ca2 = null;
//
//			            ca1 = new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME);
//						ca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
//						SearchCondition sc = new SearchCondition(ca1, "=", ca2);
//						sc.setFromIndicies(new int[]{idx_target, idx_Member}, 0);
//						sc.setOuterJoin(0);
//						spec.appendWhere(sc, new int[]{idx_target, idx_Member});
//
//						if(spec.getConditionCount() > 0)
//							spec.appendAnd();
//
//						sc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.manager.PM);
//						spec.appendWhere(sc , new int[]{idx_Member});
//
//						if(spec.getConditionCount() > 0)
//							spec.appendAnd();
//
//						sc = new SearchCondition(prjectMember,"roleBObjectRef.key.id" , SearchCondition.EQUAL, pmUser);
//						spec.appendWhere(sc , new int[]{idx_Member});
//					}
					// 개발담당부서 검토
					if(rdevDeptOid != null && rdevDeptOid.length() > 0){
						if(spec.getConditionCount() > 0)
						spec.appendAnd();
						long deptid = CommonUtil.getOIDLongValue(rdevDeptOid);
						spec.appendWhere(new SearchCondition(target, "devDeptReference.key.id","=", deptid), new int[] {idx_target} );
					}


					// 개발담당부서 제품
				    if(pdevDeptOid != null && pdevDeptOid.length() > 0){
				    	if(spec.getConditionCount() > 0)
				    	spec.appendAnd();
				    	long deptid = CommonUtil.getOIDLongValue(pdevDeptOid);

				    	int linkIndex = spec.addClassList(ProjectViewDepartmentLink.class, false);

				    	ClassAttribute mca1 = null;
			            ClassAttribute mca2 = null;

			            mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
			            mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
						SearchCondition msc = new SearchCondition(mca1, "=", mca2);
						msc.setFromIndicies(new int[]{idx_target, linkIndex}, 0);
						msc.setOuterJoin(0);
						spec.appendWhere(msc, new int[]{idx_target, linkIndex});

				    	spec.appendAnd();
				    	spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=", deptid), new int[] {linkIndex});
				    }


					String planStartStartDate = (String)map.get("planStartStartDate");				//planStartStartDate(계획 시작일자 - 시작)
					String planStartEndDate = (String)map.get("planStartEndDate");					//planStartEndDate(계획 시작일자 - 끝)
					String planEndStartDate = (String)map.get("planEndStartDate");					//planEndStartDate(계획 종료일자 - 시작)
					String planEndEndDate = (String)map.get("planEndEndDate");						//planEndEndDate(계획 종료일자 - 끝)

					//계획 시작일자(시작 ~ 끝), 계획 종료일자(시작 ~ 끝)
					if( (planStartStartDate != null && planStartStartDate.length() > 0)
							|| (planStartEndDate != null && planStartEndDate.length() > 0)
							|| (planEndStartDate != null && planEndStartDate.length() > 0)
							|| (planEndEndDate != null && planEndEndDate.length() > 0)
							) {

						if(spec.getConditionCount() > 0)
							spec.appendAnd();

						Class schedule = ExtendScheduleData.class;
						int idx_schedule = spec.appendClassList(schedule, false);

						ClassAttribute ca1 = null;
			            ClassAttribute ca2 = null;

			            ca1 = new ClassAttribute(target, "pjtSchedule.key.id");
						ca2 = new ClassAttribute(schedule, wt.util.WTAttributeNameIfc.ID_NAME);
						SearchCondition sc = new SearchCondition(ca1, "=", ca2);
						sc.setFromIndicies(new int[]{idx_target, idx_schedule}, 0);
						sc.setOuterJoin(0);
						spec.appendWhere(sc, new int[]{idx_target, idx_schedule});

						if(planStartStartDate != null && planStartStartDate.length() > 0) {
							Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd" ).parse ( planStartStartDate ,
									new ParsePosition ( 0 ) ).getTime () ) );

							if(spec.getConditionCount() > 0)
								spec.appendAnd();

							sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
							spec.appendWhere(sc , new int[]{idx_schedule});
						}
						if(planStartEndDate != null && planStartEndDate.length() > 0) {
							Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd:HH-mm-ss" ).parse (planStartEndDate + ":23-59-59" ,
									new ParsePosition ( 0 ) ).getTime () ) );

							if(spec.getConditionCount() > 0)
								spec.appendAnd();

							sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_START_DATE, SearchCondition.LESS_THAN, convertDate);
							spec.appendWhere(sc , new int[]{idx_schedule});
						}
						if(planEndStartDate != null && planEndStartDate.length() > 0) {
							Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd" ).parse ( planEndStartDate ,
									new ParsePosition ( 0 ) ).getTime () ) );

							if(spec.getConditionCount() > 0)
								spec.appendAnd();

							sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.GREATER_THAN_OR_EQUAL, convertDate);
							spec.appendWhere(sc , new int[]{idx_schedule});
						}
						if(planEndEndDate != null && planEndEndDate.length() > 0) {
							Timestamp convertDate = ( new Timestamp ( new SimpleDateFormat ("yyyy-MM-dd:HH-mm-ss" ).parse (planEndEndDate + ":23-59-59" ,
									new ParsePosition ( 0 ) ).getTime () ) );

							if(spec.getConditionCount() > 0)
								spec.appendAnd();

							sc = new SearchCondition(schedule, ExtendScheduleData.PLAN_END_DATE, SearchCondition.LESS_THAN, convertDate);
							spec.appendWhere(sc , new int[]{idx_schedule});
						}
					}


					/*
					 	기준정보 검색 항목
					 	v 제품구분
					 	v 개발구분
					 	v 조립구분
					 	v Rank
					 	대표차종
					 	v 납입처
					 	v 고객사
					 	제작구분
					 	금형구분
					 	금형분류
					*/

					String RANK = (String)map.get("RANK");
					if(RANK != null && RANK.length() > 0){
						setNumberCodeQuery(spec, target, idx_target, "rankReference.key.id", "RANK", RANK );
					}

					String PRODUCTTYPE = (String)map.get("PRODUCTTYPE");
					if(PRODUCTTYPE != null && PRODUCTTYPE.length() > 0){
						setNumberCodeQuery(spec, target, idx_target, "productTypeReference.key.id", "PRODUCTTYPE", PRODUCTTYPE );
					}

					String ASSEMBLEDTYPE = (String)map.get("ASSEMBLEDTYPE");
					if(ASSEMBLEDTYPE != null && ASSEMBLEDTYPE.length() > 0){
						setNumberCodeQuery(spec, target, idx_target, "assembledTypeReference.key.id", "ASSEMBLEDTYPE", ASSEMBLEDTYPE );
					}

					String DEVELOPENTTYPE = (String)map.get("DEVELOPENTTYPE");
					if(DEVELOPENTTYPE != null && DEVELOPENTTYPE.length() > 0){
						setNumberCodeQuery(spec, target, idx_target, "developentTypeReference.key.id", "DEVELOPENTTYPE", DEVELOPENTTYPE );
					}
					/*
					 대표 차종
					 */
					String carTypeInfo = (String)map.get("carTypeInfo");
					if(carTypeInfo != null && carTypeInfo.length() > 0){
						carTypeInfo = StringUtil.changeString(carTypeInfo, "*", "%");
						if(spec.getConditionCount() > 0 ) {
							spec.appendAnd();
						}
						Class oemLink = OEMProjectType.class;
						int idx_oem = spec.appendClassList(oemLink, false);
						SearchCondition where = null;
						where = new SearchCondition(
										new ClassAttribute(target, "oemTypeReference.key.id"),
										"=",
										new ClassAttribute(oemLink, wt.util.WTAttributeNameIfc.ID_NAME));
						where.setFromIndicies(new int[]{idx_target, idx_oem}, 0);
						where.setOuterJoin(0);
				        spec.appendWhere(where, new int[]{idx_target, idx_oem});

				        spec.appendAnd();

						spec.appendWhere(new SearchCondition(oemLink, "name", SearchCondition.LIKE, carTypeInfo),
								new int[] {idx_oem} );
					}

					//멀티 기준 정보(최종 사용처)
					String CUSTOMEREVENT1 = (String)map.get("CUSTOMEREVENT1");
					if(CUSTOMEREVENT1 != null && CUSTOMEREVENT1.length() > 0) {
						NumberCode customer = NumberCodeHelper.manager.getNumberCode("CUSTOMEREVENT", CUSTOMEREVENT1);
						if(customer != null) {
							if(spec.getConditionCount() > 0)
								spec.appendAnd();

							Class linkClass = ProjectCustomereventLink.class;
							int idx_dcLink = spec.appendClassList(linkClass, false);
							SearchCondition where = null;
							where = new SearchCondition(
											new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME),
											"=",
											new ClassAttribute(linkClass, "roleBObjectRef.key.id"));
							where.setFromIndicies(new int[]{idx_target, idx_dcLink}, 0);
							where.setOuterJoin(0);
					        spec.appendWhere(where, new int[]{idx_target, idx_dcLink});

					        spec.appendAnd();

					        where = new SearchCondition(linkClass, "roleAObjectRef.key.id",
					        				SearchCondition.EQUAL, customer.getPersistInfo().getObjectIdentifier().getId());
					        spec.appendWhere(where, new int[]{idx_dcLink});
						}
					}
					//멀티 기준 정보(고객처) -Text 변경
					String SUBCONTRACTOR1 = (String)map.get("SUBCONTRACTOR1");
					if(SUBCONTRACTOR1 != null && SUBCONTRACTOR1.length() > 0) {
						if(spec.getConditionCount() > 0)
							spec.appendAnd();

						SUBCONTRACTOR1 = StringUtil.changeString(SUBCONTRACTOR1, "*", "%");
						Class scLink = ProjectSubcontractorLink.class;
						int idx_sc = spec.appendClassList(scLink, false);
						int idx_nc = spec.appendClassList(NumberCode.class, false);


						SearchCondition where = null;
						where = new SearchCondition(
										new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME),
										"=",
										new ClassAttribute(scLink, "roleBObjectRef.key.id"));
						where.setFromIndicies(new int[]{idx_target, idx_sc}, 0);
						where.setOuterJoin(0);
				        spec.appendWhere(where, new int[]{idx_target, idx_sc});

				        spec.appendAnd();

						SearchCondition where2 = null;
						where2 = new SearchCondition(
										new ClassAttribute(NumberCode.class, wt.util.WTAttributeNameIfc.ID_NAME),
										"=",
										new ClassAttribute(scLink, "roleAObjectRef.key.id"));
						where2.setFromIndicies(new int[]{idx_nc, idx_sc}, 0);
						where2.setOuterJoin(2);
				        spec.appendWhere(where2, new int[]{idx_nc, idx_sc});

				        spec.appendAnd();

				        where = new SearchCondition(NumberCode.class, "name",
				        				SearchCondition.LIKE, SUBCONTRACTOR1 );
				        spec.appendWhere(where, new int[]{idx_nc});
					}
					//pName, pNumber
					String pName = StringUtil.checkNull( (String)map.get("pName") );
					String pNum = StringUtil.checkNull( (String)map.get("pNum") );

					if(pName.length() > 0 || pNum.length() > 0) {
						if(spec.getConditionCount() > 0)
							spec.appendAnd();

						Class ppClass = ProjectProductInfoLink.class;
						int idx_pp = spec.appendClassList(ppClass, false);
						SearchCondition where = null;
						where = new SearchCondition(
										new ClassAttribute(target, wt.util.WTAttributeNameIfc.ID_NAME),
										"=",
										new ClassAttribute(ppClass, "projectReference.key.id"));
						where.setFromIndicies(new int[]{idx_target, idx_pp}, 0);
						where.setOuterJoin(2);
				        spec.appendWhere(where, new int[]{idx_target, idx_pp});

				        if(pName.length() > 0){
				        	if(spec.getConditionCount() > 0)
				        	spec.appendAnd();

					        pName = StringUtil.changeString(pName, "*", "%");
					        where = new SearchCondition(ppClass, "pName",
					        				SearchCondition.LIKE, pName);
					        spec.appendWhere(where, new int[]{idx_pp});
				        }

				        if(pNum.length() > 0){
				        	if(spec.getConditionCount() > 0)
					        spec.appendAnd();

					        pNum = StringUtil.changeString(pNum, "*", "%");
					        where = new SearchCondition(ppClass, "pNum",
					        				SearchCondition.LIKE, pNum);
					        spec.appendWhere(where, new int[]{idx_pp});
					    }
					}


					/*
				     	1,0 : 개발 검토 (자동차/전자)
				     	2,4 : 제품 (자동차/전자)
				     	3 : 금형
				     	사업부(dtype)	1 : 자동차	2 : 전자
					*/

					String partNo = StringUtil.checkNull( (String)map.get("partNo") );
					String partName = StringUtil.checkNull( (String)map.get("partName") );
					if((partNo != null && partNo.length() > 0) || (partName != null && partName.length() > 0)){


						if(spec.getConditionCount() > 0) {
					    	spec.appendAnd();
					    }
						spec.appendOpenParen();
						spec.appendOpenParen();

						int idx1 = spec.addClassList(MoldItemInfo.class, false);
						ClassAttribute ca0 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
					    ClassAttribute ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
					    SearchCondition sc = new SearchCondition(ca0, "=", ca1);
					    sc.setFromIndicies(new int[]{idx1, idx_target}, 0);
					    sc.setOuterJoin(0);
					    spec.appendWhere(sc, new int[]{idx1, idx_target});

					    if(partNo != null && partNo.length() > 0){
					    	spec.appendAnd();
					    	ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NO);
					    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
					    	partNo = StringUtil.changeString(partNo, "*", "%");
							ColumnExpression ce = ConstantExpression.newExpression(partNo);
							SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
							spec.appendWhere(dsc, new int[] {idx1 });
					    }

					    if(partName != null && partName.length() > 0){
					    	spec.appendAnd();
					    	ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PART_NAME);
					    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
					    	partName = StringUtil.changeString(partName, "*", "%");
							ColumnExpression ce = ConstantExpression.newExpression(partName);
							SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
							spec.appendWhere(dsc, new int[] {idx1 });
					    }

					    spec.appendCloseParen();
					    spec.appendOr();
					    spec.appendOpenParen();

					    idx1 = spec.addClassList(ProductInfo.class, false);
						ca0 = new ClassAttribute(ProductInfo.class, ProductInfo.PROJECT_REFERENCE + ".key.id");
					    ca1 = new ClassAttribute(target, "thePersistInfo.theObjectIdentifier.id");
					    sc = new SearchCondition(ca0, "=", ca1);
					    sc.setFromIndicies(new int[]{idx1, idx_target}, 0);
					    sc.setOuterJoin(0);
					    spec.appendWhere(sc, new int[]{idx1, idx_target});

					    if(partNo != null && partNo.length() > 0){
					    	spec.appendAnd();
					    	ClassAttribute mca = new ClassAttribute(ProductInfo.class, ProductInfo.P_NUM);
					    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
					    	partNo = StringUtil.changeString(partNo, "*", "%");
							ColumnExpression ce = ConstantExpression.newExpression(partNo);
							SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
							spec.appendWhere(dsc, new int[] {idx1 });
					    }

					    if(partName != null && partName.length() > 0){
					    	spec.appendAnd();
					    	ClassAttribute mca = new ClassAttribute(ProductInfo.class, ProductInfo.P_NAME);
					    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
					    	partName = StringUtil.changeString(partName, "*", "%");
							ColumnExpression ce = ConstantExpression.newExpression(partName);
							SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
							spec.appendWhere(dsc, new int[] {idx1 });
					    }

					    spec.appendCloseParen();
					    spec.appendCloseParen();
					}

					String dType = (String)map.get("dType");
				    if(StringUtil.checkString(pjtType)) {
						if(spec.getConditionCount() > 0) spec.appendAnd();
						if(pjtType.equals("0") || pjtType.equals("1")){
							if(dType != null && dType.length() > 0) {
								if(dType.equals("1")){
									spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")), new int[]{idx_target});
								}else{
									spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("0")), new int[]{idx_target});
								}
							}else{
								spec.appendOpenParen();
								spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("0")), new int[]{idx_target});
								spec.appendOr();
								spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("1")), new int[]{idx_target});
								spec.appendCloseParen();
							}
						}else if(pjtType.equals("2") || pjtType.equals("4")){
							if(dType != null && dType.length() > 0) {
								if(dType.equals("1")){
									spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")), new int[]{idx_target});
								}else{
									spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("4")), new int[]{idx_target});
								}
							}else{
								spec.appendOpenParen();
								spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("2")), new int[]{idx_target});
								spec.appendOr();
								spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("4")), new int[]{idx_target});
								spec.appendCloseParen();
							}
						}else if(pjtType.equals("3")){

							spec.appendWhere(new SearchCondition(target, E3PSProject.PJT_TYPE, SearchCondition.LIKE, Integer.parseInt("3")), new int[]{idx_target});


							int index_item = spec.addClassList(MoldItemInfo.class, false);
							int index_productProject = spec.addClassList(ProductProject.class, false);
							ClassAttribute ca0 = new ClassAttribute(MoldProject.class, "moldInfoReference.key.id");
							ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);
							ClassAttribute ca2 = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.PROJECT_REFERENCE + ".key.id");
							ClassAttribute ca3 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);

							SearchCondition sc = new SearchCondition(ca0, "=", ca1);
						    sc.setFromIndicies(new int[]{idx_target, index_item}, 0);
						    sc.setOuterJoin(0);
						    spec.appendAnd();
						    spec.appendWhere(sc, new int[]{idx_target, index_item});


						    sc = new SearchCondition(ca2, "=", ca3);
						    sc.setFromIndicies(new int[]{index_item, index_productProject}, 0);
						    sc.setOuterJoin(0);
						    spec.appendAnd();
						    spec.appendWhere(sc, new int[]{index_item, index_productProject});


						    /*map.put("dieNo", dieNo);
							map.put("devDeptOid", devDeptOid);
							map.put("productpjtNo", productpjtNo);
							map.put("itemType", itemType);
							map.put("moldProductType", moldProductType);
							map.put("productPartNo", productPartNo);
							map.put("productName", productName);
							map.put("making", making);
							map.put("moldType", moldType);
							map.put("outsourcing", outsourcing);
							map.put("tempsetProductPm", tempsetProductPm);
							map.put("setProductPm", setProductPm);
							map.put("tempsetMoldCharger", setProductPm);
							map.put("setMoldCharger", setMoldCharger);*/

						    String dieNo = (String)map.get("dieNo");
						    String devDeptOid = (String)map.get("devDeptOid");
						    String productpjtNo = (String)map.get("productpjtNo");
						    String itemType = (String)map.get("itemType");
						    String moldProductType = (String)map.get("moldProductType");
						    String productPartNo = (String)map.get("productPartNo");
						    String productName = (String)map.get("productName");
						    String making = (String)map.get("making");
						    String moldType = (String)map.get("moldType");
						    String outsourcing = (String)map.get("outsourcing");
						    String setProductPm = (String)map.get("setProductPm");
						    String setMoldCharger = (String)map.get("setMoldCharger");
						    String carTypeInfo2 = (String)map.get("carTypeInfo2");
						    String DEVELOPENTTYPE2 = (String)map.get("DEVELOPENTTYPE2");

						   // String devDeptOid = (String)map.get("devDeptOid");

						    if(dieNo != null && dieNo.length() > 0){
						    	spec.appendAnd();
						    	ClassAttribute mca = new ClassAttribute(MoldItemInfo.class, MoldItemInfo.DIE_NO);
						    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
						    	dieNo = StringUtil.changeString(dieNo, "*", "%");
								ColumnExpression ce = ConstantExpression.newExpression(dieNo);
								SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
								spec.appendWhere(dsc, new int[] {index_item });
						    }

						    if(devDeptOid != null && devDeptOid.length() > 0){
						    	spec.appendAnd();
						    	long deptid = CommonUtil.getOIDLongValue(devDeptOid);

						    	int linkIndex = spec.addClassList(ProjectViewDepartmentLink.class, false);

						    	ClassAttribute mca1 = null;
					            ClassAttribute mca2 = null;

					            mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
					            mca2 = new ClassAttribute(ProjectViewDepartmentLink.class, "roleAObjectRef.key.id");
								SearchCondition msc = new SearchCondition(mca1, "=", mca2);
								msc.setFromIndicies(new int[]{index_productProject, linkIndex}, 0);
								msc.setOuterJoin(0);
								spec.appendWhere(msc, new int[]{index_productProject, linkIndex});

						    	spec.appendAnd();
						    	spec.appendWhere(new SearchCondition(ProjectViewDepartmentLink.class, "roleBObjectRef.key.id", "=", deptid), new int[] {linkIndex});
						    }

						    if(productpjtNo != null && productpjtNo.length() > 0){
						    	spec.appendAnd();
						    	ClassAttribute mca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NO);
						    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
						    	productpjtNo = StringUtil.changeString(productpjtNo, "*", "%");
								ColumnExpression ce = ConstantExpression.newExpression(dieNo);
								SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
								spec.appendWhere(dsc, new int[] {index_productProject});
						    }

						    if(itemType != null && itemType.length() > 0){
						    	spec.appendAnd();
						    	spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.ITEM_TYPE, "=", itemType), new int[]{index_item});
						    }

						    if(moldProductType != null && moldProductType.length() > 0){
						    	setNumberCodeQuery(spec, MoldItemInfo.class, index_item, MoldItemInfo.PRODUCT_TYPE_REFERENCE + ".key.id", "MOLDPRODUCTSTYPE", moldProductType );
						    }

						    if(productPartNo != null && productPartNo.length() > 0){
						    	spec.appendAnd();
						    	ClassAttribute mca = new ClassAttribute(ProductProject.class, ProductProject.PART_NO);
						    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
						    	productPartNo = StringUtil.changeString(productPartNo, "*", "%");
								ColumnExpression ce = ConstantExpression.newExpression(productPartNo);
								SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
								spec.appendWhere(dsc, new int[] {index_productProject});
						    }

						    if(productName != null && productName.length() > 0){
						    	ClassAttribute mca = new ClassAttribute(ProductProject.class, ProductProject.PJT_NAME);
						    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
						    	productName = StringUtil.changeString(productName, "*", "%");
								ColumnExpression ce = ConstantExpression.newExpression(productName);
								SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
								spec.appendWhere(dsc, new int[] {index_productProject});
						    }

						    if(making != null && making.length() > 0){
						    	spec.appendAnd();
						    	spec.appendWhere(new SearchCondition(MoldItemInfo.class, MoldItemInfo.MAKING, "=", making), new int[]{index_item});
						    }

						    if(moldType != null && moldType.length() > 0){
						    	setNumberCodeQuery(spec, MoldItemInfo.class, index_item, MoldItemInfo.MOLD_TYPE_REFERENCE + ".key.id", "MOLDTYPE", moldType );
						    }

						    if(outsourcing != null && outsourcing.length() > 0){
						    	spec.appendAnd();
						    	ClassAttribute mca = new ClassAttribute(MoldProject.class, MoldProject.OUT_SOURCING);
						    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
						    	outsourcing = StringUtil.changeString(outsourcing, "*", "%");
								ColumnExpression ce = ConstantExpression.newExpression(outsourcing);
								SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
								spec.appendWhere(dsc, new int[] {idx_target});
						    }

						    //Kogger.debug(getClass(), "carTypeInfo2 = " + carTypeInfo2);
						    if(carTypeInfo2 != null && carTypeInfo2.length() > 0){

						    	ClassAttribute mca = new ClassAttribute(OEMProjectType.class, OEMProjectType.NAME);
						    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, mca);
						    	carTypeInfo2 = StringUtil.changeString(carTypeInfo2, "*", "%");
						    	carTypeInfo2 = carTypeInfo2.toUpperCase();
						    	Class oemLink = OEMProjectType.class;
								int idx_oem = spec.appendClassList(oemLink, false);
								SearchCondition where = null;
								where = new SearchCondition(
												new ClassAttribute(ProductProject.class, "oemTypeReference.key.id"),
												"=",
												new ClassAttribute(oemLink, wt.util.WTAttributeNameIfc.ID_NAME));
								where.setFromIndicies(new int[]{index_productProject, idx_oem}, 0);
								where.setOuterJoin(0);
								spec.appendAnd();
						        spec.appendWhere(where, new int[]{index_productProject, idx_oem});

						        spec.appendAnd();
						        ColumnExpression ce = ConstantExpression.newExpression(carTypeInfo2);
						        SearchCondition dsc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
								spec.appendWhere(dsc, new int[] {idx_oem});

						    }

//						    if(setProductPm != null && setProductPm.length() > 0){
//						    	if(spec.getConditionCount() > 0)
//									spec.appendAnd();
//
//								Class prjectMember = ProjectMemberLink.class;
//								int idx_Member = spec.appendClassList(prjectMember, false);
//
//
//								ClassAttribute mca1 = null;
//					            ClassAttribute mca2 = null;
//
//					            mca1 = new ClassAttribute(ProductProject.class, wt.util.WTAttributeNameIfc.ID_NAME);
//					            mca2 = new ClassAttribute(prjectMember, "roleAObjectRef.key.id");
//								SearchCondition msc = new SearchCondition(mca1, "=", mca2);
//								msc.setFromIndicies(new int[]{index_productProject, idx_Member}, 0);
//								msc.setOuterJoin(0);
//								spec.appendWhere(msc, new int[]{index_productProject, idx_Member});
//
//								if(spec.getConditionCount() > 0)
//									spec.appendAnd();
//
//								msc = new SearchCondition(prjectMember, ProjectMemberLink.PJT_MEMBER_TYPE, SearchCondition.EQUAL, ProjectUserHelper.PM);
//								spec.appendWhere(msc , new int[]{idx_Member});
//
//								if(spec.getConditionCount() > 0)
//									spec.appendAnd();
//
//								long userId = CommonUtil.getOIDLongValue(setProductPm);
//								msc = new SearchCondition(prjectMember,"roleBObjectRef.key.id" , SearchCondition.EQUAL, userId);
//								spec.appendWhere(msc , new int[]{idx_Member});
//						    }

						    if(DEVELOPENTTYPE2 != null && DEVELOPENTTYPE2.length() > 0){

						    	setNumberCodeQuery(spec, ProductProject.class, index_productProject, "developentTypeReference.key.id", "DEVELOPENTTYPE", DEVELOPENTTYPE2);
						    }
						}
					}
				}
			}

			/*
			 	최신 프로젝트
			 */
			if(spec.getConditionCount() > 0) {
				spec.appendAnd();
			}
			spec.appendWhere(new SearchCondition(target, "lastest", SearchCondition.IS_TRUE, Boolean.getBoolean("true")),new int[] { idx_target});
			if(spec.getConditionCount() > 0){
				spec.appendAnd();
			}
			spec.appendWhere(new SearchCondition(target, "checkOutState", SearchCondition.NOT_EQUAL, "c/o"),new int[] {idx_target});

			/////////////////////////////////////////////////////////////////////
			// Sub Query End mainSpec
			/////////////////////////////////////////////////////////////////////

			SubSelectExpression subfrom = new SubSelectExpression(spec);
	        subfrom.setFromAlias(new String[]{"B0"}, 0);
            int sub_pjt_index = mainSpec.appendFrom(subfrom);

            if(mainSpec.getConditionCount() > 0) mainSpec.appendAnd();

            SearchCondition sc = new SearchCondition(new ClassAttribute(E3PSProject.class, wt.util.WTAttributeNameIfc.ID_NAME),
            						"=",
            						new KeywordExpression(mainSpec.getFromClause().getAliasAt(sub_pjt_index) + ".projectId"));
            sc.setFromIndicies(new int[]{main_idx, sub_pjt_index},0);
            sc.setOuterJoin(0);
            mainSpec.appendWhere(sc, new int[]{main_idx, sub_pjt_index});

            int sortIdx = 0;
			String sort =(String)map.get("sortKey");
			String isDesc =(String)map.get("dsc");
			//Kogger.debug(getClass(), "Search SortKey :" + sort +"  :::  "+isDesc);


			if((sort != null) && (sort.trim().length() > 0)) {
				if((isDesc == null) || (isDesc.trim().length() == 0)) {
					isDesc = "FALSE";
				}

				if(sort.equals(E3PSProject.PJT_NAME) || sort.equals("state.state") || sort.equals(E3PSProject.PJT_NO)){
					SearchUtil.setOrderBy( mainSpec, target, main_idx, sort, "m_Sort"+ sortIdx++ , "TRUE".equals(isDesc.toUpperCase()));
				}

				if(sort.equals("planEndDate") || sort.equals("planStartDate")){
					if(mainSpec.getConditionCount() > 0) mainSpec.appendAnd();

					Class schedule2 = ExtendScheduleData.class;
					int idx_schedule2 = mainSpec.appendClassList(schedule2, false);

					ClassAttribute ca1 = null;
		            ClassAttribute ca2 = null;

		            ca1 = new ClassAttribute(E3PSProject.class, "pjtSchedule.key.id");
					ca2 = new ClassAttribute(schedule2, wt.util.WTAttributeNameIfc.ID_NAME);
					SearchCondition sc2 = new SearchCondition(ca1, "=", ca2);
					sc2.setFromIndicies(new int[]{main_idx, idx_schedule2}, 0);
					sc2.setOuterJoin(0);
					mainSpec.appendWhere(sc2, new int[]{main_idx, idx_schedule2});

					SearchUtil.setOrderBy(mainSpec, schedule2, idx_schedule2, sort, "m_Sort"+ sortIdx++ , "TRUE".equals(isDesc.toUpperCase()));
				}

				if(sort.equals("moldPartName") || sort.equals("moldPartNo") || sort.equals("making")){
					int index_item = mainSpec.addClassList(MoldItemInfo.class, false);
					//int index_productProject = mainSpec.addClassList(ProductProject.class, false);

					ClassAttribute ca0 = new ClassAttribute(MoldProject.class, MoldProject.MOLD_INFO_REFERENCE + ".key.id");
					ClassAttribute ca1 = new ClassAttribute(MoldItemInfo.class, wt.util.WTAttributeNameIfc.ID_NAME);

					SearchCondition msc = new SearchCondition(ca0, "=", ca1);
				    msc.setFromIndicies(new int[]{main_idx, index_item}, 0);
				    msc.setOuterJoin(0);

				    //Kogger.debug(getClass(), "index_item === " + index_item);

				    if(mainSpec.getConditionCount() > 0) {
				    	mainSpec.appendAnd();
				    }

				    mainSpec.appendWhere(msc, new int[]{main_idx, index_item});

				    String sortKey = "";
				    if(sort.equals("moldPartNo")){
				    	sortKey =  MoldItemInfo.PART_NO;
				    }else if(sort.equals("moldPartName")){
				    	sortKey = MoldItemInfo.PART_NAME;
				    }else if( sort.equals("making")){
				    	sortKey = MoldItemInfo.MAKING;
				    }

				    SearchUtil.setOrderBy(mainSpec, MoldItemInfo.class, index_item, sortKey, "m_Sort"+ sortIdx++ , "TRUE".equals(isDesc.toUpperCase()));
				}
			}

			//디폴트 생성 일자
			SearchUtil.setOrderBy(mainSpec, target, main_idx, wt.util.WTAttributeNameIfc.CREATE_STAMP_NAME, "modifyStamp", true);
            //Kogger.debug(getClass(), mainSpec);
		}
		catch (QueryException e)
        {
            Kogger.error(SearchProjectHelper.class, e);
        }
        catch (WTException e)
        {
            Kogger.error(SearchProjectHelper.class, e);
        }
        catch (Exception e)
        {
            Kogger.error(SearchProjectHelper.class, e);
        }

        //Kogger.debug(getClass(), "project Search spec ===>"+mainSpec);
        return mainSpec;
	}


	public static void setUpperNoneLikeCondition(QuerySpec spec, Class target, int index, String column, String value )throws WTException {
		if(!SERVER) {
			Class argTypes[] = new Class[]{QuerySpec.class, Class.class, int.class, String.class, String.class};
			Object args[] = new Object[]{spec, target, new Integer(index), column, value};
			try {
				wt.method.RemoteMethodServer.getDefault().invoke(
						"setUpperNoneLikeCondition",
						"e3ps.load.remote.edm.SearchProjectHelper",
						null,
						argTypes,
						args);
			}
			catch(RemoteException e) {
				Kogger.error(SearchProjectHelper.class, e);
			}
			catch(InvocationTargetException e) {
				Kogger.error(SearchProjectHelper.class, e);
			}
			return;
		}

		try {
			if(value != null && (value.trim()).length() > 0) {
				if(!spec.isAdvancedQueryEnabled()) {
					spec.setAdvancedQueryEnabled(true);
				}

				if(spec.getConditionCount() > 0)
		    		spec.appendAnd();

				ClassAttribute ca = new ClassAttribute(target, column);
		    	SQLFunction sqlfunction = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				ColumnExpression ce = ConstantExpression.newExpression(value.toUpperCase());
				SearchCondition sc = new SearchCondition(sqlfunction, SearchCondition.LIKE, ce );
				spec.appendWhere(sc, new int[] {index });
			}
		}
		catch(Exception e) {
			Kogger.error(SearchProjectHelper.class, e);
		}
	}


	private static void setNumberCodeQuery(QuerySpec spec, Class target, int idx, String ref, String key, String value) throws WTException
	{
		if( (value == null) || (value.trim().length() == 0) )
		{
			return;
		}

		SearchUtil.appendEQUAL(spec, target, ref, CommonUtil.getOIDLongValue(NumberCodeHelper.manager.getNumberCode(key, value)), idx);
	}
}
