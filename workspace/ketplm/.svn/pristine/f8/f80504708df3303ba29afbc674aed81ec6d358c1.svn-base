package e3ps.project.material.servlet;

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
import e3ps.project.material.MoldMaterial;
import ext.ket.shared.log.Kogger;

public class MaterialListServlet extends CommonServlet {

	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setAttribute("control", this.getPageControl(req,res));
		gotoResult(req, res,"/jsp/project/material/ListMaterial.jsp");
		// TODO Auto-generated method stub
		
	}
	
	
	protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
		
		QuerySpec qs = null;
        //String initType = req.getParameter("initType");
        
        try {
        	
        	 
        	String material  = ParamUtil.getStrParameter(req.getParameter("material"));
            String maker = ParamUtil.getStrParameter(req.getParameter("maker"));
            String type  = ParamUtil.getStrParameter(req.getParameter("type"));
            String grade = ParamUtil.getStrParameter(req.getParameter("grade"));
            
            
            qs = new QuerySpec();
    		
    		int index1 = qs.addClassList(MoldMaterial.class, true);
            
    		if(material != null && material.length() > 0){
    		
            	qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL, "=", material) , new int[]{index1});
    		
    		}
    		
    		if(maker != null && maker.length() > 0){
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			long id = CommonUtil.getOIDLongValue(maker);
    			
            	qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_MAKER_REFERENCE + ".key.id", "=", id) , new int[]{index1});
    		
    		}
    		
    		if(type != null && type.length() > 0){
    			
    			long id = CommonUtil.getOIDLongValue(type);
    			
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			
            	qs.appendWhere(new SearchCondition(MoldMaterial.class, MoldMaterial.MATERIAL_TYPE_REFERENCE + ".key.id", "=", id) , new int[]{index1});
    		
    		}
    		
    		if(grade != null && grade.length() > 0){
    			if(qs.getConditionCount() > 0){
    				qs.appendAnd();
    			}
    			
    			ClassAttribute ca0 = new ClassAttribute(MoldMaterial.class, MoldMaterial.GRADE);
            	SQLFunction upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca0);
            	grade = StringUtil.changeString(grade, "*", "%");
            	grade = grade.toUpperCase();
            	ColumnExpression ce = ConstantExpression.newExpression(grade);
            	qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce) , new int[]{index1});
    		
    		}
    		
    		//Kogger.debug(getClass(), "qs == " + qs);
    		
    		    		
            SearchUtil.setOrderBy(qs, MoldMaterial.class, index1, "thePersistInfo.createStamp" , "createStamp", false);
            
            
            
            //Kogger.debug(getClass(), qs);
            //SearchUtil.setOrderBy(qs, MoldSubPart.class, index2, MoldSubPart.MOLD_NO , "moldNo", false);
            
            
        
        }catch(Exception ex){
        	Kogger.error(getClass(), ex);
        }
        return qs;
	}
	
	
	
}
