/**
 * @(#) ManagePeopleServlet.java Copyright (c) e3ps. All rights reserverd
 *
 * @version 1.00
 * @since jdk 1.4.2
 * @author Cho Sung Ok, jerred@e3ps.com
 */

package e3ps.groupware.company.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.httpgw.WTContextBean;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.impl.Tree;
import e3ps.common.impl.TreeHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.Department;
import e3ps.groupware.company.People;
import ext.ket.shared.log.Kogger;
//import e3ps.load.groupware.LoadDepartment;
//import e3ps.load.groupware.LoadPeople;
//import e3ps.part.AttributeListValue;

public class ManagePeopleServlet extends CommonServlet
{
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
    {
        WTContextBean wtcontextbean = new WTContextBean();
        wtcontextbean.setRequest(req);

        String command = req.getParameter("cmd");
        if(command == null){
        	command = "";
        }

        if (command.equals(""))
            command = "search";

        Kogger.debug(getClass(), "cmd ==> " + command);

        String oid = ParamUtil.getStrParameter(req.getParameter("oid"));
        String sortType = req.getParameter("sortType");
        if(command.equals("search")){
	        if(sortType == null ) {
	        	sortType ="false";
	        }

	        if ("false".equals(sortType.toLowerCase())) {
	            sortType = "true";
	        }
	        else {
	            sortType = "false";
	        }

	        req.setAttribute("control", getPageControl(req, res));
	        this.gotoResult(req, res, "/jsp/groupware/company/departmentPeopleList.jsp?oid=" + oid + "&sortType="+ sortType);
        }

    }

    protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res)
    {
        QuerySpec spec = null;
        try
        {
            String oid = ParamUtil.getStrParameter(req.getParameter("oid"));

            String key = ParamUtil.getStrParameter( req.getParameter("key") );
            String keyvalue = ParamUtil.getStrParameter( req.getParameter("keyvalue") );

Kogger.debug(getClass(),  "=====> ManagePeopleServlet getQuerySpec() keyvalue : " + keyvalue );

           String sortKey = req.getParameter("sortKey");
           String sortType = req.getParameter("sortType");

           if(sortKey == null){
        	   sortKey = "";
           }

           if(sortType == null){
        	   sortType = "false";
           }

            spec = new QuerySpec();
            Class target = People.class;
            int idx = spec.addClassList(target, true);

            if (!keyvalue.equals(""))
            {
                if (spec.getConditionCount() > 0)
                    spec.appendAnd();

                spec.appendWhere( new SearchCondition(target, key, SearchCondition.LIKE, "%" + keyvalue + "%"), new int[] {idx} );
            }
            else
            {
                if (!oid.equals("") && !oid.equals("root"))
                {
                    if (spec.getConditionCount() > 0)
                        spec.appendAnd();
                    // 하위 부서도 모두 출력되게 수정 ( 2006.04.04 Choi Seunghwan )
                    ArrayList list = TreeHelper.manager.getAllChildList(Department.class, (Tree) CommonUtil.getObject(oid), new ArrayList());

                    spec.appendOpenParen();
                    for (int i = list.size() - 1; i > -1; i--)
                    {
                        Department temp = (Department) list.get(i);
                        spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil
                                .getOIDLongValue(temp)), new int[] { idx });
                        spec.appendOr();
                    }
                    spec.appendWhere(new SearchCondition(target, "departmentReference.key.id", "=", CommonUtil
                            .getOIDLongValue(oid)), new int[] { idx });
                    spec.appendCloseParen();
                }
            }

            if (!CommonUtil.isAdmin())
            {
                if (spec.getConditionCount() > 0)
                    spec.appendAnd();
                	spec.appendWhere(new SearchCondition(target, People.IS_DISABLE, SearchCondition.IS_FALSE),
                                 new int[] { idx });
            }


            if (!sortKey.equals(""))
            {
            	if("true".equals(sortType)){
                	SearchUtil.setOrderBy(spec, target, idx, sortKey, "sort", true );
                }else if("false".equals(sortType)){
                	SearchUtil.setOrderBy(spec, target, idx, sortKey, "sort", false);
                }
            }else
            {
            	SearchUtil.setOrderBy(spec, target, idx, People.DUTY_CODE, "sort", false);
            	SearchUtil.setOrderBy(spec, target, idx, People.NAME, "name", false);

            	/*
                SearchUtil.setOrderBy(spec, target, idx, People.GRADE_CODE, "sort", false);
                SearchUtil.setOrderBy(spec, target, idx, People.SORT_NUM, "sortNum", false);
            	*/
            }

           // SearchUtil.setOrderBy(spec, target, idx, People.PRIORITY, "priority", true);

        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
        Kogger.debug(getClass(), "People spec : " +spec);
        return spec;
    }
}
