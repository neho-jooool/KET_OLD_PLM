package e3ps.lesson.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import wt.content.ContentHelper;
import wt.content.ContentHolder;
import wt.content.ContentItem;
import wt.content.ContentRoleType;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.code.NumberCode;
import e3ps.common.content.ContentUtil;
import e3ps.common.content.E3PSContentHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.StringUtil;
import e3ps.groupware.company.Department;
import e3ps.lesson.LessonLearn;
import e3ps.project.ProductProject;
import ext.ket.shared.log.Kogger;

public class LessonHelper implements RemoteAccess{

	public static QueryResult getList(KETParamMapUtil hash) {

		QueryResult result = null;

		try{
			QuerySpec qs = new QuerySpec(LessonLearn.class);

			String partOid = (String)hash.get("partOid");
			String gubunOid = hash.getString("gubun");
			String factoryOid = hash.getString("factory");
			String departOid = (String)hash.get("devDeptOid");
			String causeOid = hash.getString("cause");
			String improveOid = hash.getString("improve");
			String creator = (String)hash.get("creator");
			String createStartdate = (String)hash.get("createStartdate");
			String createEnddate = (String)hash.get("createEnddate");


			if(StringUtil.checkString(partOid)){
				long partId = CommonUtil.getOIDLongValue(CommonUtil.getObject(partOid));

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}

				qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.PART_REFERENCE + ".key.id", "=", partId) , new int[]{0});

			}

			if(StringUtil.checkString(gubunOid)){
				String[] gubunList = hash.getStringArray("gubun");

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}

				qs.appendOpenParen();

				for(int i = 0; i < gubunList.length; i++){
					long gubunId = CommonUtil.getOIDLongValue(CommonUtil.getObject(gubunList[i]));
					if(i > 0){
						qs.appendOr();
					}
					qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.GUBUN_REFERENCE + ".key.id", "=", gubunId) , new int[]{0});
				}

				qs.appendCloseParen();

			}

			if(StringUtil.checkString(factoryOid)){
				String[] factoryList = hash.getStringArray("factory");

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}

				qs.appendOpenParen();

				for(int i = 0; i < factoryList.length; i++){
					long factoryId = CommonUtil.getOIDLongValue(CommonUtil.getObject(factoryList[i]));
					if(i > 0){
						qs.appendOr();
					}
					qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.FACTORY_REFERENCE + ".key.id", "=", factoryId) , new int[]{0});
				}

				qs.appendCloseParen();

			}

			if(StringUtil.checkString(departOid)){
				long departId = CommonUtil.getOIDLongValue(CommonUtil.getObject(departOid));

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}
				qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.DEPARTMENT_REFERENCE + ".key.id", "=", departId) , new int[]{0});
			}

			if(StringUtil.checkString(causeOid)){

				String[] causeList = hash.getStringArray("cause");

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}
				qs.appendOpenParen();
				String causeId = "";

				for(int i = 0; i < causeList.length; i++){
					causeId = "%"+causeList[i]+"%";
					if(i > 0){
						qs.appendOr();
					}
					qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.CAUSE_GB, "LIKE", causeId) , new int[]{0});
				}
				qs.appendCloseParen();
			}

			if(StringUtil.checkString(improveOid)){

				String[] improveList = hash.getStringArray("improve");

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}
				qs.appendOpenParen();
				String improveId = "";

				for(int i = 0; i < improveList.length; i++){
					improveId = "%"+improveList[i]+"%";
					if(i > 0){
						qs.appendOr();
					}
					qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.IMPROVE_GB, "LIKE", improveId) , new int[]{0});
				}
				qs.appendCloseParen();
			}

			if(StringUtil.checkString(creator)){
				long creatorId = CommonUtil.getOIDLongValue(CommonUtil.getObject(creator));

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}
				qs.appendWhere(new SearchCondition(LessonLearn.class, LessonLearn.USER_REFERENCE + ".key.id", "=", creatorId) , new int[]{0});
			}

			if(StringUtil.checkString(createStartdate)){
				Timestamp startDate = DateUtil.convertStartDate2(createStartdate);

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}
				qs.appendWhere(new SearchCondition(LessonLearn.class, "thePersistInfo.createStamp", ">=", startDate) , new int[]{0});
			}

			if(StringUtil.checkString(createEnddate)){
				Timestamp endDate = DateUtil.convertEndDate2(createEnddate);

				if(qs.getConditionCount() > 0 ){
            		qs.appendAnd();
            	}
				qs.appendWhere(new SearchCondition(LessonLearn.class, "thePersistInfo.createStamp", "<=", endDate) , new int[]{0});
			}

			Kogger.debug(LessonHelper.class, "query == " + qs);

			result = PersistenceHelper.manager.find(qs);


		}catch(Exception e){
			Kogger.error(LessonHelper.class, e);
		}

		return result;
	}

	public static List LessonData(LessonLearn lesson){
		String part_no   = "";
		String part_name = "";
		String gubun   	 = "";
		String gubun_oid   	 = "";
		String factory   = "";
		String factory_oid   = "";
		String problem   = "";
		String cause   = "";
		String improve   = "";
		String department   = "";
		String isAttache = "no";
		String partOid = "";
		String departmentOid = "";

		String DieOid = "";
		String DieName = "";
		String DieNo = "";
		String equipNo = "";
		String equipName = "";
		String creatorOid = "";
		String creator = "";
		String create_date = "";
		String occur_date = "";

		String projectOid = "";
		String projectNo = "";
		String projectName = "";
		String prodcut_gubun = "";
		String prodcut_gubunOid = "";
		String cause_gubun = "";
		String cause_gubunOid = "";
		String improve_gubun = "";
		String improve_gubunOid = "";
		String cause_gubun_check	= "";
		String improve_gubun_check	= "";

		Vector attacheList = new Vector();
		attacheList = ContentUtil.getSecondaryContents(lesson);
        if (attacheList.size() != 0){
            isAttache = "yes";
        }

		WTPart part = (WTPart)lesson.getPart();
		if(part != null){
			part_no   	= part.getNumber();
			part_name 	= part.getName();
			partOid 	= CommonUtil.getOIDString(part);
			part 		= null;
		}else{
			if(lesson.getPartNo() != null){
				part_no = lesson.getPartNo();
			}
			if(lesson.getPart_nm() != null){
				part_name = lesson.getPart_nm();
			}

		}



		NumberCode number = (NumberCode)lesson.getGubun();
		if(!"".equals(number) && number != null){
			gubun = number.getName();
			gubun_oid = CommonUtil.getOIDString(number);
		}
		number = (NumberCode)lesson.getFactory();
		if(!"".equals(number) && number != null){
			factory = number.getName();
			factory_oid = CommonUtil.getOIDString(number);
		}

		number = (NumberCode)lesson.getProduct_gubun();
		if(!"".equals(number) && number != null){
			prodcut_gubun = number.getName();
			prodcut_gubunOid = CommonUtil.getOIDString(number);
		}

		/*number = (NumberCode)lesson.getCause_gubun();
		if(!"".equals(number) && number != null){
			cause_gubun = number.getName();
			cause_gubunOid = CommonUtil.getOIDString(number);
		}

		number = (NumberCode)lesson.getImprove_gubun();
		if(!"".equals(number) && number != null){
			improve_gubun = number.getName();
			improve_gubunOid = CommonUtil.getOIDString(number);
		}*/
		cause_gubun_check =  lesson.getCause_gb();
		Kogger.debug(LessonHelper.class, cause_gubun_check);
		improve_gubun_check =  lesson.getImprove_gb();

		Department depart = (Department)lesson.getDepartment();
		departmentOid = CommonUtil.getOIDString(depart);
		department = depart.getName();
		problem = StringUtil.checkNull(lesson.getProblem());
		cause 	= StringUtil.checkNull(lesson.getCause());
		improve = StringUtil.checkNull(lesson.getImprove());

		part 		= (WTPart)lesson.getDie();
		if(part != null){
			DieOid   	= CommonUtil.getOIDString(part);
			DieName 	= part.getName();
			DieNo 		= part.getNumber();
		}else{
			if(lesson.getDieNo() != null){
				DieNo = lesson.getDieNo();
			}
			if(lesson.getDie_nm() != null){
				DieName = lesson.getDie_nm();
			}

		}
		equipNo		= StringUtil.checkNull(lesson.getEquip_no());
		equipName	= StringUtil.checkNull(lesson.getEquip_nm());

		creatorOid = CommonUtil.getOIDString(lesson.getUser());

		WTUser User = lesson.getUser();
		creator = User.getFullName();

		create_date = DateUtil.getDateString(lesson.getInsert_date(), "d");
		occur_date = DateUtil.getDateString(lesson.getOccur_date(), "d");

		ProductProject pjt = null;
		projectOid = lesson.getProjectOid();
		if(projectOid != null){
			pjt = (ProductProject)CommonUtil.getObject(projectOid);
			projectNo = pjt.getPjtNo();
			projectName = pjt.getPjtName();
		}

		Map<String, Object> map = new HashMap();
        List<Map<String, Object>> List = new ArrayList<Map<String, Object>>();
        map.put("part_no", part_no);
        map.put("part_name", part_name);
        map.put("gubun", gubun);
        map.put("gubun_oid", gubun_oid);
        map.put("factory", factory);
        map.put("factory_oid", factory_oid);
        map.put("department", department);
        map.put("problem", problem);
        map.put("cause", cause);
        map.put("improve", improve);
        map.put("isAttache", isAttache);
        map.put("attacheList", attacheList);
        map.put("departmentOid", departmentOid);
        map.put("partOid", partOid);
        map.put("DieOid", DieOid);
        map.put("DieName", DieName);
        map.put("DieNo", DieNo);
        map.put("equipNo", equipNo);
        map.put("equipName", equipName);
        map.put("creatorOid", creatorOid);
        map.put("creator", creator);
        map.put("create_date", create_date);
        map.put("occur_date", occur_date);
        map.put("projectNo", projectNo);
        map.put("projectName", projectName);
        map.put("projectOid", projectOid);
        map.put("prodcut_gubunOid", prodcut_gubunOid);
        map.put("prodcut_gubun", prodcut_gubun);
        map.put("cause_gubun_check", cause_gubun_check);
        map.put("improve_gubun_check", improve_gubun_check);

        /*map.put("cause_gubun", cause_gubun);
        map.put("cause_gubunOid", cause_gubunOid);
        map.put("improve_gubun", improve_gubun);
        map.put("improve_gubunOid", improve_gubunOid);*/
        List.add(map);

        return List;

	}

	public static ContentHolder updateAttachFiles(ContentHolder holder, Map params, Vector files) throws Exception {
        KETParamMapUtil map = KETParamMapUtil.getMap(params);

        Vector secondaryDelFileVec = new Vector();
        String delTemp = map.getString("deleteFiles");
        StringTokenizer tokens = new StringTokenizer(KETStringUtil.nullFilter(delTemp), "*");
        while (tokens.hasMoreElements()) {
            secondaryDelFileVec.addElement(tokens.nextElement());
        }

        QueryResult rs = ContentHelper.service.getContentsByRole(holder, ContentRoleType.SECONDARY);
        while (rs.hasMoreElements()) {
            ContentItem allContentItem = (ContentItem) rs.nextElement();
            Object allObj = allContentItem;
            String ItemString = allObj.toString();
            for (int i = 0; i < secondaryDelFileVec.size(); i++) {
                if (secondaryDelFileVec.get(i).equals(ItemString)) {
                    holder = E3PSContentHelper.service.delete(holder, allContentItem);
                }
            }
        }

        if (files != null && files.size() > 0) {
            holder = E3PSContentHelper.service.attach(holder, files);
        }

        return holder;
    }

	public static void main(String[] args) throws Exception {
		LessonLearn learn = new LessonLearn();

		WTPart part = (WTPart)CommonUtil.getObject("wt.part.WTPart:42589");
		learn.setPart(part);

		Department depart = (Department)CommonUtil.getObject("e3ps.groupware.company.Department:12430");
		learn.setDepartment(depart);

		NumberCode nCode = (NumberCode)CommonUtil.getObject("e3ps.common.code.NumberCode:158861423");
		learn.setGubun(nCode);

		PersistenceHelper.manager.save(learn);
	}

}
