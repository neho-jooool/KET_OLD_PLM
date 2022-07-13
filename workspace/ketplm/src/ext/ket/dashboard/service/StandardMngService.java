package ext.ket.dashboard.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import ext.ket.dashboard.entity.StandardDTO;
import ext.ket.dashboard.entity.StandardDataDTO;
import ext.ket.shared.dao.CommonDao;

@Service
public class StandardMngService {

    @Inject
    private CommonDao dao;

    @SuppressWarnings({ "unchecked" })
    public List<StandardDTO> findStandardByDashboard(StandardDTO paramObject) throws Exception {

	String type = paramObject.getSelectTab();

	String QueryName = "";

	if ("0".equals(type)) {
	    QueryName = "standardMng.StandardList";
	} else {

	    if (StringUtils.isNotEmpty(paramObject.getDepartmentOid())) {
		String departOid = CommonUtil.getOIDLongValue2Str(paramObject.getDepartmentOid());
		paramObject.setDepartmentOid(departOid);
	    } else {
		paramObject.setDepartmentOid("null");
	    }
	    QueryName = "standardMng.StandardList" + type;
	}

	List<StandardDTO> standardData = dao.find(QueryName, paramObject);

	standardData = this.tempDataSet(standardData);

	return standardData;
    }

    public List<StandardDTO> tempDataSet(List<StandardDTO> standardData) {
	if (standardData.size() < 1) {
	    String year = DateUtil.getToDay("yyyy");
	    StandardDTO temp = new StandardDTO();
	    temp.setYear(year);
	    temp.setOid("");
	    standardData.add(temp);
	}
	return standardData;
    }

    public String save(StandardDataDTO paramObject) throws Exception {

	String returnMsg = "";
	List<StandardDTO> standardData = paramObject.getStandard();

	String delOidsStr = paramObject.getDelOids();

	String Del_QueryName = "";
	String Up_QueryName = "";
	String In_QueryName = "";

	String type = (String) (paramObject.getSelectTab());

	if ("0".equals(type)) {
	    Del_QueryName = "standardMng.StandardDelete";
	    Up_QueryName = "standardMng.StandardUpdate";
	    In_QueryName = "standardMng.StandardInsert";
	} else {
	    Del_QueryName = "standardMng.StandardDelete" + type;
	    Up_QueryName = "standardMng.StandardUpdate" + type;
	    In_QueryName = "standardMng.StandardInsert" + type;

	}

	if (StringUtils.isNotEmpty(delOidsStr)) {

	    String delOidsAr[] = null;
	    if (delOidsStr != null && delOidsStr.length() > 0) {
		delOidsAr = delOidsStr.split(",");
	    }
	    paramObject.setVdelOids(delOidsAr);

	    dao.delete(Del_QueryName, paramObject);
	}
	
	WTUser user = (WTUser) SessionHelper.manager.getPrincipal();
	String userId = user.getName();
	
	if (standardData != null && standardData.size() > 0) {
	    for (StandardDTO data : standardData) {
		if (StringUtils.isNotEmpty(data.getDepartmentOid())) {
		    String departOid = CommonUtil.getOIDLongValue2Str(data.getDepartmentOid());
		    data.setDepartmentOid(departOid);
		    returnMsg = departOid;
		}
		data.setCreateUser(userId);
		data.setUpdateUser(userId);
		if (StringUtils.isEmpty(data.getOid())) {
		    dao.insert(In_QueryName, data);
		} else {
		    dao.update(Up_QueryName, data);
		}
	    }
	}
	return returnMsg;
    }
}
