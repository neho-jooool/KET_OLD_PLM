package ext.ket.common.dashboard.service.internal;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import e3ps.common.util.CommonUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import ext.ket.common.dashboard.entity.KETDashBoardMailLink;
import ext.ket.common.dashboard.entity.KETDashBoardMailSetting;
import ext.ket.common.dashboard.entity.dto.KETMailReceiveDTO;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class KETMailReceiveBuilder {

    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();

    public KETMailReceiveDTO buildPers2Dto(KETDashBoardMailSetting mailConfig, KETMailReceiveDTO paramObject) throws Exception {

	People peo = null;
	Department dept = null;
	String receiveOid = null;
	String targetDeptName = "";
	String targetDeptOid = "";

	receiveOid = CommonUtil.getFullOIDString(mailConfig);

	paramObject.setReceiveOid(receiveOid);

	List<Department> DepartmentList = query.queryForListBByRoleA(KETDashBoardMailSetting.class, KETDashBoardMailLink.class,
	        Department.class, mailConfig);
	for (Department depart : DepartmentList) {
	    targetDeptName += depart.getName() + ", ";
	    targetDeptOid += CommonUtil.getFullOIDString(depart) + ", ";
	}

	targetDeptName = StringUtils.removeEnd(targetDeptName, ", ");
	targetDeptOid = StringUtils.removeEnd(targetDeptOid, ", ");

	paramObject.setTargetDeptName(targetDeptName);
	paramObject.setTargetDeptOid(targetDeptOid);

	peo = (People) mailConfig.getMan();

	paramObject.setTargetPeopleName(peo.getName());
	paramObject.setTargetPeopleOid(CommonUtil.getFullOIDString(peo));

	paramObject.setDuty(peo.getDuty());

	dept = (Department) peo.getDepartment();

	paramObject.setDept(dept.getName());

	return paramObject;
    }
}
