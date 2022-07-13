package e3ps.project.machine.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.machine.MoldMachine;
import ext.ket.shared.log.Kogger;

public class MachineListServlet extends CommonServlet{

	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setAttribute("control", this.getPageControl(req,res));
		gotoResult(req, res,"/jsp/project/machine/ListMachine.jsp");
		// TODO Auto-generated method stub
		
	}
	
	protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
		
		QuerySpec qs = null;
        //String initType = req.getParameter("initType");
        
        try {
        	
        	 
        	String moldType  = ParamUtil.getStrParameter(req.getParameter("moldType"));
            String maker = ParamUtil.getStrParameter(req.getParameter("maker"));
            String type  = ParamUtil.getStrParameter(req.getParameter("type"));
            String ton  = ParamUtil.getStrParameter(req.getParameter("ton"));
            String model = ParamUtil.getStrParameter(req.getParameter("model"));
            
            
            qs = new QuerySpec();
    		
    		int index1 = qs.addClassList(MoldMachine.class, true);
            
    		if(moldType != null && moldType.length() > 0){
    		
            	qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MOLD_TYPE, "=", moldType) , new int[]{index1});
    		
    		}
    		
    		if(maker != null && maker.length() > 0){
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			long id = CommonUtil.getOIDLongValue(maker);
    			
            	qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_MAKER_REFERENCE + ".key.id", "=", id) , new int[]{index1});
    		
    		}
    		
    		if(type != null && type.length() > 0){
    			
    			long id = CommonUtil.getOIDLongValue(type);
    			
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			
            	qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.MACHINE_TYPE_REFERENCE + ".key.id", "=", id) , new int[]{index1});
    		
    		}
    		
    		if(ton != null && ton.length() > 0){
    			
    			long id = CommonUtil.getOIDLongValue(ton);
    			
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			
            	qs.appendWhere(new SearchCondition(MoldMachine.class, MoldMachine.TON_REFERENCE + ".key.id", "=", id) , new int[]{index1});
    		
    		}
    		
    		if(model != null && model.length() > 0){
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			
    			ClassAttribute ca0 = new ClassAttribute(MoldMachine.class, MoldMachine.MODEL);
            	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
            	model = StringUtil.changeString(model, "*", "%");
            	model = model.toUpperCase();
            	ColumnExpression ce = ConstantExpression.newExpression(model);
            	qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce) , new int[]{index1});
    		
    		}
    		
    		//Kogger.debug(getClass(), "qs == " + qs);
    		
    		    		
            SearchUtil.setOrderBy(qs, MoldMachine.class, index1, "thePersistInfo.createStamp" , "createStamp", false);
            
            
            
            //Kogger.debug(getClass(), qs);
            //SearchUtil.setOrderBy(qs, MoldSubPart.class, index2, MoldSubPart.MOLD_NO , "moldNo", false);
            
            
        
        }catch(Exception ex){
        	Kogger.error(getClass(), ex);
        }
        return qs;
	}
	

}
