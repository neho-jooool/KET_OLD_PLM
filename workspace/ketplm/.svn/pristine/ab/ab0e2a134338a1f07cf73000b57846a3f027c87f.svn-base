/**
 * @(#)	ProjectUserData.java
 * Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.project.beans;

import java.util.Locale;

import wt.project.Role;
import e3ps.common.util.CommonUtil;
import e3ps.groupware.company.PeopleData;
import e3ps.project.ProjectMemberLink;
import e3ps.project.TaskMemberLink;
import ext.ket.shared.log.Kogger;

public class ProjectUserData
{
    public ProjectMemberLink link;
    public int memberType;
    public String projectDuty = "";

    public String wtuserOID = "";
    public String peopleOID = "";

    public String id = "";
    public String name = "";
    public String email = "";
    public String duty = "";
    public String departmentName = "";
    public String role_en = "";
    public String role_ko = "";
    public PeopleData data;

    public ProjectUserData(ProjectMemberLink _link)
    {
        try
        {
            this.link = _link;
            memberType = _link.getPjtMemberType();
//            projectDuty = ProjectUserHelper.DUTYNAME[memberType];

            data = new PeopleData(_link.getMember());
            wtuserOID = CommonUtil.getOIDString(data.wtuser);
            peopleOID = CommonUtil.getOIDString(data.people);

            id = data.id;
            name = data.name;
            email = data.email;
            duty = data.duty;
            role_en = _link.getPjtRole();

            if (role_en != null && role_en.length() > 0)
                role_ko = Role.toRole(role_en).getDisplay(Locale.KOREA);
            if(this.memberType == 2) { //Role Member 아닐 경우
            	projectDuty = this.role_ko;
            	Kogger.debug(getClass(), "AAA>> "+projectDuty);
            } else if ( memberType < ProjectUserHelper.DUTYNAME.length ) {  // [PLM System 1차개선] 조건 추가(오류 수정), 2013-08-06, BoLee
            	projectDuty = ProjectUserHelper.DUTYNAME[memberType];
            }
            departmentName = data.departmentName;
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    public ProjectUserData(TaskMemberLink _link)
    {
        try
        {
            this.link = _link.getMember();
            memberType = _link.getTaskMemberType();
//            projectDuty = TaskUserHelper.DUTYNAME[memberType];

            PeopleData data = new PeopleData(this.link.getMember());
            wtuserOID = CommonUtil.getOIDString(data.wtuser);
            peopleOID = CommonUtil.getOIDString(data.people);

            id = data.id;
            name = data.name;
            email = data.email;
            duty = data.duty;
            departmentName = data.departmentName;
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }






}
