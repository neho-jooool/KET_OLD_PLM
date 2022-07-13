/**
 * @(#)	ManageDepartmentServlet.java
 * Copyright (c) e3ps. All rights reserverd
 * 
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.groupware.company.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.DepartmentHelper;
import ext.ket.shared.log.Kogger;

public class ManageDepartmentServlet extends CommonServlet
{	
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        String command = ParamUtil.getStrParameter( req.getParameter("command") );		
        String msg = "";
        
		if( command.equals("create") )
		{ 
		    create(req);
		    msg = "부서가 생성되었습니다.";
		}
		else if( command.equals("update") )
		{
		    update(req);
		    msg = "부서가 수정되었습니다.";
		}
		else if( command.equals("delete") )
		{
		    delete(req);
		    msg = "부서가 삭제되었습니다.";
		}
		
		alertNgo(res,msg,"/plm/jsp/groupware/company/adminDepartmentTree.jsp");
    }
    
	private void create(HttpServletRequest req)
	{
	    String cname = ParamUtil.getStrParameter( req.getParameter("cname") );
	    String ccode = ParamUtil.getStrParameter( req.getParameter("ccode") );
	    String soid = ParamUtil.getStrParameter( req.getParameter("soid") );
	    String sort = ParamUtil.getStrParameter( req.getParameter("csort") );
	    
	    try
	    {
            Department department = Department.newDepartment();
            department.setName(cname);
            department.setCode(ccode);
            department.setSort(Integer.parseInt(sort));
            department.setEnabled(true);
            
            if( !soid.equals("root") )
            {
            	department.setParent( (Department)CommonUtil.getObject(soid) );
            }
            
            PersistenceHelper.manager.save( department );
        }
	    catch( WTPropertyVetoException e )
	    {
            Kogger.error(getClass(), e);
        }
	    catch( WTException e )
	    {
            Kogger.error(getClass(), e);
        }
	}
	
	private void update(HttpServletRequest req)
	{
	    String sname = ParamUtil.getStrParameter( req.getParameter("sname") );
	    String scode = ParamUtil.getStrParameter( req.getParameter("scode") );
	    String soid = ParamUtil.getStrParameter( req.getParameter("soid") );
	    String sort = ParamUtil.getStrParameter( req.getParameter("ssort") );
	    String sdept = ParamUtil.getStrParameter( req.getParameter("sdept") );
	    	    
        try
        {
            Department department = (Department)CommonUtil.getObject(soid);
            department.setName(sname);
            department.setCode(scode);
            department.setSort(Integer.parseInt(sort));
            
            if( !sdept.equals("root") )
            {
            	department.setParent((Department)DepartmentHelper.manager.getDepartment(sdept));
            }
            else
            {
            	department.setParent(Department.newDepartment());
            }
            
            PersistenceHelper.manager.modify(department);
        }
        catch( WTPropertyVetoException e )
        {
            Kogger.error(getClass(), e);
        }
        catch( WTException e )
        {
            Kogger.error(getClass(), e);
        }
	}
	
	private void delete(HttpServletRequest req)
	{
	    String soid = ParamUtil.getStrParameter( req.getParameter("soid") );
	    
	    try
	    {
		    Department department = (Department)CommonUtil.getObject( soid );
            PersistenceHelper.manager.delete(department);
        }
	    catch( WTException e )
	    {
            Kogger.error(getClass(), e);
        }
	}    
}
