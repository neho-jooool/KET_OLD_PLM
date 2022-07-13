package e3ps.project.customerPlan.beans;

import java.util.HashMap;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.method.RemoteAccess;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.code.NumberCode;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectSubcontractorLink;
import e3ps.project.customerPlan.CustomerPlan;
import ext.ket.shared.log.Kogger;

public class CustomerPlanHelper implements RemoteAccess{

	public static void createCustomerPlan(HashMap map) throws Exception{
		String oid = (String)map.get("oid");
		String plan_gubun = (String)map.get("plan_gubun");//구분
		String plan_date = (String)map.get("plan_date");//날짜
		String projectOid = (String)map.get("projectOid");
		String customerOid = (String)map.get("customerOid");
		E3PSProject project = (E3PSProject)CommonUtil.getObject(projectOid);
		NumberCode numbercode = (NumberCode)CommonUtil.getObject(customerOid);

		CustomerPlan customerPlan = null;
		if(oid != null && oid.length() > 0){
			customerPlan = (CustomerPlan)CommonUtil.getObject(oid);
		}else{
			customerPlan = customerPlan.newCustomerPlan();
		}

		customerPlan.setIni_Sample(plan_gubun);
		customerPlan.setIni_Date(plan_date);
		customerPlan.setProject(project);
		customerPlan.setCustomer(numbercode);

		customerPlan = (CustomerPlan)PersistenceHelper.manager.save(customerPlan);

	}

	public static void deleteCustomerPlan(HashMap map) throws Exception{
		String oid = (String)map.get("oid");
		CustomerPlan customerPlan = null;
		customerPlan = (CustomerPlan)CommonUtil.getObject(oid);
		PersistenceHelper.manager.delete(customerPlan);
	}

	public static CustomerPlan getCustomerPlan(String projectOid, String customerOid)throws Exception{
		CustomerPlan customerPlan = null;
		QuerySpec qs = new QuerySpec(CustomerPlan.class);

		long customerId = CommonUtil.getOIDLongValue(CommonUtil.getObject(customerOid));
		long projectId = CommonUtil.getOIDLongValue(CommonUtil.getObject(projectOid));

		qs.appendWhere(new SearchCondition(CustomerPlan.class, CustomerPlan.CUSTOMER_REFERENCE + ".key.id", "=", customerId) , new int[]{0});
		qs.appendAnd();
		qs.appendWhere(new SearchCondition(CustomerPlan.class, CustomerPlan.PROJECT_REFERENCE + ".key.id", "=", projectId) , new int[]{0});

		QueryResult rs = PersistenceHelper.manager.find(qs);

		if(rs.hasMoreElements()){
			customerPlan = (CustomerPlan)rs.nextElement();
		}
		return customerPlan;
	}

	public static CustomerPlan getCustomerPlanGannt(String projectOid)throws Exception{
		CustomerPlan customerPlan = null;
		QuerySpec qs = new QuerySpec(CustomerPlan.class);

		long projectId = CommonUtil.getOIDLongValue(CommonUtil.getObject(projectOid));
		qs.appendWhere(new SearchCondition(CustomerPlan.class, CustomerPlan.PROJECT_REFERENCE + ".key.id", "=", projectId) , new int[]{0});
		Kogger.debug(CustomerPlanHelper.class, qs);
		QueryResult rs = PersistenceHelper.manager.find(qs);

		if(rs.hasMoreElements()){
			customerPlan = (CustomerPlan)rs.nextElement();
		}
		return customerPlan;
	}

	public static void copyLinkProject(String orgPjtOid, Vector subcontractorVec, Vector projectoidVec) throws Exception{

		String subcontractoroid = null;
		String projectOid = null;
		String linkProjectOid = null;
		CustomerPlan customerPlan = null;
		CustomerPlan Link_customerPlan = null;

        for (int i = 0; i < subcontractorVec.size(); i++) {
            subcontractoroid = (String)subcontractorVec.get(i);

            for (int k = 0; k < projectoidVec.size(); k++) {
            	projectOid = (String)projectoidVec.get(k);

            	if(checkLinkProject(orgPjtOid,subcontractoroid,projectOid)){
            		customerPlan = getCustomerPlan(orgPjtOid,subcontractoroid);

            		Link_customerPlan = getCustomerPlan(projectOid,subcontractoroid);
            		if(Link_customerPlan != null){

            			linkProjectOid = CommonUtil.getOIDString(Link_customerPlan);
            		}
            		HashMap< String, String> map = new HashMap<String, String>();
            		map.put("oid", linkProjectOid);
            		map.put("plan_gubun", customerPlan.getIni_Sample());
            		map.put("plan_date", customerPlan.getIni_Date());
            		map.put("projectOid", projectOid);
            		map.put("customerOid", subcontractoroid);

            		createCustomerPlan(map);
            	}
            }
        }
	}

	public static boolean checkLinkProject(String orgPjtOid, String subcontractorOid, String projectOid) throws Exception{
		CustomerPlan Org_customerPlan = null;
		CustomerPlan Link_customerPlan = null;

		Org_customerPlan = getCustomerPlan(orgPjtOid,subcontractorOid);
		if(Org_customerPlan == null){//Org Project에 고객사 객체가 있는지 체크
			return false;
		}

		if("".equals(Org_customerPlan.getIni_Date())){//Org Project에 고객사 객체에 일정이 있는지 체크
			return false;
		}

		E3PSProject project = (E3PSProject)CommonUtil.getObject(projectOid);

		QuerySpec pcspec = new QuerySpec(ProjectSubcontractorLink.class);
		SearchUtil.appendEQUAL(pcspec, ProjectSubcontractorLink.class, "roleBObjectRef.key.id", CommonUtil.getOIDLongValue(project), 0);
		SearchUtil.appendEQUAL(pcspec, ProjectSubcontractorLink.class, "roleAObjectRef.key.id", CommonUtil.getOIDLongValue(CommonUtil.getObject(subcontractorOid)), 0);
		QueryResult pcresult = PersistenceHelper.manager.find(pcspec);


		if(!pcresult.hasMoreElements()){//Link Project에 고객처가 있는지 체크
			return false;
		}

		Link_customerPlan = getCustomerPlan(projectOid,subcontractorOid);

		if(Link_customerPlan != null && !"".equals(Link_customerPlan.getIni_Date())){//Link Project에 고객사 일정이 있는지 체크
			return false;
		}

		return true;
	}


	public static void deleteFromProject(String oid) throws Exception{//일정변경 취소시 삭제

		CustomerPlan customerPlan = null;

		QuerySpec qs = new QuerySpec(CustomerPlan.class);

		long projectId = CommonUtil.getOIDLongValue(CommonUtil.getObject(oid));
		qs.appendWhere(new SearchCondition(CustomerPlan.class, CustomerPlan.PROJECT_REFERENCE + ".key.id", "=", projectId) , new int[]{0});

		QueryResult rs = PersistenceHelper.manager.find(qs);

		while(rs.hasMoreElements()){
			customerPlan = (CustomerPlan)rs.nextElement();
			PersistenceHelper.manager.delete(customerPlan);
		}


	}

	public static void main(String args[]) throws Exception{
		/*HashMap map = new HashMap(); //등록 test
		map.put("Ini_Sample", "test2");
		map.put("projectOid", "e3ps.project.ProductProject:157515493");
		map.put("customerOid", "e3ps.common.code.NumberCode:157211482");
		createCustomerPlan(map);*/

		/*HashMap map = new HashMap(); //수정 test
		map.put("Ini_Sample", "test2");
		map.put("oid", "e3ps.project.customerPlan.CustomerPlan:158041422");
		map.put("projectOid", "e3ps.project.ProductProject:157515493");
		map.put("customerOid", "e3ps.common.code.NumberCode:157211482");
		createCustomerPlan(map);*/

		/*CustomerPlan customerPlan = null; 조회 test
		customerPlan = getCustomerPlan("e3ps.project.ProductProject:157515493","e3ps.common.code.NumberCode:157211482");
		if(customerPlan != null){
			Kogger.debug(getClass(), customerPlan.getIni_Sample());
		}*/

		/*HashMap map = new HashMap(); 삭제 test
		map.put("oid", "e3ps.project.customerPlan.CustomerPlan:158041421");
		deleteCustomerPlan(map);*/

	}
}
