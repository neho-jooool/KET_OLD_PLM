package e3ps.project.servlet;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.org.WTUser;
import wt.query.ClassAttribute;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.E3PSTask;
import e3ps.project.ExtendScheduleData;
import e3ps.project.ProjectOutput;
import e3ps.project.beans.ProjectStateFlag;
import ext.ket.shared.log.Kogger;

public class SearchProjectTaskServlet extends CommonServlet{	
    public void doService(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException
    {
//        req.setAttribute("control",this.getPageControl(req,res));
        String category = req.getParameter("category");
        gotoResult(req, res, "/jsp/project/ListJELTask.jsp?category="+category);
    }
    
    protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res)
    {
        try {
          String category = ParamUtil.getStrParameter(req.getParameter("category"),"all");
          WTUser wtuser = (WTUser)SessionHelper.manager.getPrincipal();
          
          QuerySpec query = new QuerySpec();
          Class taskClass = E3PSTask.class; 
          Class outputClass = ProjectOutput.class;
          int idx_task = query.addClassList(taskClass,true);
          int idx_output = query.addClassList(outputClass,false);
          query.appendSelectAttribute("outputName",idx_output,false);

          long userID = CommonUtil.getOIDLongValue(wtuser);
          
          ClassAttribute taskAttr = new ClassAttribute(taskClass, "thePersistInfo.theObjectIdentifier.id");
          ClassAttribute outputAttr = new ClassAttribute(outputClass,"taskReference.key.id");
          query.appendWhere(new SearchCondition(taskAttr,"=",outputAttr),new int[]{idx_task,idx_output});
          query.appendAnd();
          query.appendWhere(new SearchCondition(outputClass, "owner.key.id","=",userID),new int[]{idx_output});
          if(category!=null && !"all".equals(category)) {
              if("ready".equals(category)) {
                  int idx_schedule = query.addClassList(ExtendScheduleData.class,false);
                  query.appendAnd();
                  query.appendWhere(new SearchCondition(new ClassAttribute(taskClass,"taskSchedule.key.id"),"=",new ClassAttribute(ExtendScheduleData.class,"thePersistInfo.theObjectIdentifier.id")),new int[]{idx_task,idx_schedule});
                  query.appendAnd();
                  SearchCondition where = new SearchCondition(ExtendScheduleData.class,ExtendScheduleData.PLAN_START_DATE,">",new Timestamp(new java.util.Date().getTime()));
                  query.appendWhere(where,new int[]{idx_schedule});
              } else if("continue".equals(category)) {
                  query.appendAnd();
                  query.appendWhere(new SearchCondition(taskClass,"taskState","=",ProjectStateFlag.TASK_STATE_PROGRESS),new int[]{idx_task});
              } else if("complete".equals(category)) {
                  query.appendAnd();
                  query.appendWhere(new SearchCondition(taskClass,"taskState","=",ProjectStateFlag.TASK_STATE_COMPLETE),new int[]{idx_task});
              }
          }
          SearchUtil.setOrderBy(query,taskClass,idx_task,"taskState","state",false);
          return query;
      } catch(Exception e) {
          Kogger.error(getClass(), e);
          return null;
      }  
    }
}
