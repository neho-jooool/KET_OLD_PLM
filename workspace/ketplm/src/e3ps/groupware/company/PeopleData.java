/**
 * @(#) PeopleData.java
 *      Copyright (c) e3ps. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.groupware.company;

import java.net.URL;
import java.util.Vector;

import org.codehaus.jackson.annotate.JsonIgnore;

import wt.content.ApplicationData;
import wt.content.ContentHelper;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.session.SessionHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public class PeopleData {
    @JsonIgnore
    public WTUser wtuser;
    public String wtuserOID;
    @JsonIgnore
    public People people;
    public String peopleOID;
    @JsonIgnore
    public Department department;
    @JsonIgnore
    public Department Parentdepartment;

    public String id = "";
    public String name = "";
    public String email = "";
    public String duty = "";
    public String dutycode = "";
    public String gradeName = "";
    public String gradeCode = "";
    public String cccCode = "";
    public String cellTel = "";
    public String homeTel = "";
    public String officeTel = "";
    public String address = "";
    public String departmentName = "";
    public String departmentEnName = "";
    public String ParentdepartmentName = "";
    public String ParentdepartmentEnName = "";
    public String password = "";
    public String chief = "";

    public boolean isDiable = false;
    public String imgUrl = CompanyState.defautlURL.toString();

    public PeopleData() throws Exception {
	this(SessionHelper.manager.getPrincipal());
    }

    public PeopleData(Object obj) throws Exception {
	if (obj instanceof WTUser) {
	    this.wtuser = (WTUser) obj;

	    QueryResult qr = PersistenceHelper.manager.navigate(this.wtuser, "people", WTUserPeopleLink.class);
	    if (qr.hasMoreElements()) {
		this.people = (People) qr.nextElement();
	    } else {
		try {
		    this.people = People.newPeople();
		    this.people.setUser(this.wtuser);
		    this.people.setId(this.wtuser.getName());
		    this.people.setName(this.wtuser.getFullName());
		    this.people.setEmail((this.wtuser.getEMail() == null) ? "" : this.wtuser.getEMail());
		    this.people = (People) PersistenceHelper.manager.save(this.people);
		    this.people.setGradeCode((this.people.getGradeCode() == null) ? "" : this.people.getGradeCode());
		    this.people.setGradeName((this.people.getGradeName() == null) ? "" : this.people.getGradeName());
		    this.people.setCcCode((this.people.getCcCode() == null) ? "" : this.people.getCcCode());
		    this.people.setIsDisable((this.people.isIsDisable() == false) ? false : this.people.isIsDisable());

		} catch (WTPropertyVetoException e) {
		    Kogger.error(getClass(), e);
		} catch (WTException e) {
		    Kogger.error(getClass(), e);
		}
	    }
	} else if (obj instanceof People) {
	    this.people = (People) obj;
	    this.wtuser = this.people.getUser();
	}
	this.wtuserOID = CommonUtil.getOIDString(this.wtuser);
	this.peopleOID = CommonUtil.getOIDString(this.people);
	setWTUserData();
	setPeopleData();
	setDepartmentData();
    }

    public PeopleData(String oid) throws Exception {
	this(CommonUtil.getObject(oid));
    }

    private void setWTUserData() throws Exception {
	if (this.wtuser == null)
	    return;

	this.id = this.wtuser.getName();
	this.name = this.wtuser.getFullName();
	this.email = this.wtuser.getEMail();
	if (this.email == null)
	    this.email = "";
    }

    private void setPeopleData() throws Exception {
	if (this.people == null)
	    return;

	this.duty = this.people.getDuty();
	if (this.duty == null)
	    this.duty = "지정안됨";
	this.dutycode = this.people.getDutyCode();
	if (this.dutycode == null)
	    this.dutycode = "";
	this.cellTel = this.people.getCellTel();
	if (this.cellTel == null)
	    this.cellTel = "";
	this.homeTel = this.people.getHomeTel();
	if (this.homeTel == null)
	    this.homeTel = "";
	this.officeTel = this.people.getOfficeTel();
	if (this.officeTel == null)
	    this.officeTel = "";
	this.address = this.people.getAddress();
	if (this.address == null)
	    this.address = "";
	this.isDiable = this.people.isIsDisable();
	this.password = this.people.getPassword();
	this.imgUrl = getImageURL();
	this.gradeName = this.people.getGradeName();
	this.gradeCode = this.people.getGradeCode();
	this.cccCode = this.people.getCcCode();
	this.chief = this.people.getChief();
	if (this.chief == null)
	    this.chief = "";
    }

    private void setDepartmentData() throws Exception {
	if (people != null) {

	    QueryResult qr = PersistenceHelper.manager.navigate(this.people, "department", DepartmentPeopleLink.class);
	    if (qr.hasMoreElements()) {
		this.department = (Department) qr.nextElement();
		this.departmentName = this.department.getName();
		this.departmentEnName = this.department.getEnName();

		try {
		    this.Parentdepartment = (Department) department.getParent();
		    this.ParentdepartmentName = this.Parentdepartment.getName();
		    this.ParentdepartmentEnName = this.Parentdepartment.getEnName();
		} catch (Exception e) {
		    // Kogger.error(getClass(), e);
		    this.ParentdepartmentName = "지정안됨";
		    this.ParentdepartmentEnName = "지정안됨";
		}
	    } else {
		this.departmentName = "지정안됨";
		this.departmentEnName = "지정안됨";
		this.ParentdepartmentName = "지정안됨";
		this.ParentdepartmentEnName = "지정안됨";

	    }
	} else {
	    this.departmentName = "지정안됨";
	    this.ParentdepartmentName = "지정안됨";
	    this.departmentEnName = "지정안됨";
	    this.ParentdepartmentEnName = "지정안됨";
	}
    }

    public String getImageURL() throws Exception {
	this.people = (People) ContentHelper.service.getContents(this.people);
	Vector appList = ContentHelper.getApplicationData(this.people);
	URL picURL = CompanyState.defautlURL;
	if (appList.size() > 0) {
	    ApplicationData pic = (ApplicationData) appList.get(0);
	    picURL = ContentHelper.getDownloadURL(this.people, pic);
	}
	return picURL.toString();
    }

    /**
     * 전결 권한이 있는지 여부
     * 
     * @return
     */
    public boolean isArbitrary() {
	String[] arbi = e3ps.common.jdf.config.ConfigImpl.getInstance().getArray("arbitrary.dutycode");
	if (arbi == null)
	    return false;

	for (int i = arbi.length - 1; i > -1; i--) {
	    if (arbi[i].equals(dutycode))
		return true;
	}
	return false;
    }
}
