package e3ps.project.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.code.NumberCode;
import e3ps.common.impl.ParentChildLink;
import e3ps.common.jdf.message.Message;
import e3ps.common.jdf.message.MessageBox;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.ManageSequence;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.WebUtil;
import e3ps.dms.entity.KETCarYearlyAmount;
import e3ps.project.outputtype.ModelPlan;
import e3ps.project.outputtype.OEMPlan;
import e3ps.project.outputtype.OEMProjectType;
import e3ps.project.outputtype.OEMType;
import e3ps.project.outputtype.ProjectOutPutType;
import e3ps.project.outputtype.load.CarLoad;
import ext.ket.shared.log.Kogger;

public class ProjectOutPutTypeServlet extends CommonServlet{
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        String cmd = req.getParameter("cmd");
        String oid = req.getParameter("oid");
        String msg = null;
        String param="";
        String oemtype = req.getParameter("oemtype");
        String parentNc = req.getParameter("parentNc");
        
        
        if (cmd.equals("create"))
        {
        	msg = create(req,res);
            param="?"+msg.substring(msg.indexOf("oid"));
            msg = msg.substring(0,msg.indexOf(".")+1);
            alertNgo(res, msg, "/plm/jsp/project/projectType/ViewProjectType.jsp"+param);
        }
        else if (cmd.equals("modify"))
        {
        	msg = modify(req,res);
            param= "?oid="+oid;
            alertNgo(res, msg, "/plm/jsp/project/projectType/ViewProjectType.jsp"+param);
        } 
        else if (cmd.equals("delete"))
        {
            msg = delete(req, res);
            alertNgo(res, msg, "/plm/jsp/project/projectType/ViewProjectType.jsp"+param);
        }else if (cmd.equals("createOEM")) 
        {
        	msg = createOEM(req,res);
            //param="?"+msg.substring(msg.indexOf("oid")) + "&oemtype="+oemtype;
            //msg = msg.substring(0,msg.indexOf(".")+1);
        	param= "?oid="+oid+"&oemtype="+oemtype;
            alertNgo(res, msg, "/plm/jsp/project/projectType/ViewOEMType.jsp"+param);
            
        }else if (cmd.equals("modifyOEM"))
        {
        	msg = modifyOEM(req,res);
            param= "?oid="+oid+"&oemtype="+oemtype;
            alertNgo(res, msg, "/plm/jsp/project/projectType/ViewOEMType.jsp"+param);
        }else if (cmd.equals("deleteOEM"))
        {
            msg = deleteOEM(req, res);
            alertNgo(res, msg, "/plm/jsp/project/projectType/ViewOEMType.jsp"+ "?oemtype="+oemtype+"&oid="+parentNc);
        }else if (cmd.equals("updateViewType")){
        	msg = modifyOLevel(req, res);
        	OEMProjectType opt = (OEMProjectType)CommonUtil.getObject(oid);
        	param= "?oid="+opt.getMaker().getPersistInfo().getObjectIdentifier().getStringValue()+"&oemtype="+oemtype;
        	alertNgo(res, msg, "/plm/jsp/project/projectType/ViewOEMType.jsp"+param);
        }
        
        
        
    }

    /**
     * Delete() 
     * 
     * @param req
     * @param res
     */
    private String delete(HttpServletRequest req, HttpServletResponse res)
    {
        String parentOid = req.getParameter("oid");
        String temp;
        ProjectOutPutType partCode = null;
        

        if (parentOid != null && parentOid.length() > 0)
        	partCode = (ProjectOutPutType) CommonUtil.getObject(parentOid);

        try
        {	
            QueryResult r = PersistenceHelper.manager.navigate(partCode, "child", ParentChildLink.class);
            if (r.hasMoreElements())
            {
                temp = "해당 산출물정의 분류에에 분류코드가 존재합니다. 삭제 하실 수 없습니다.";
            }
            else
            {
                PersistenceHelper.manager.delete(partCode);
                temp = "삭제되었습니다.";
            }
            return temp;
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }

    private String deleteOEM(HttpServletRequest req, HttpServletResponse res)
    {
        String parentOid = req.getParameter("oid");
        String temp;
        OEMProjectType partCode = null;
        

        if (parentOid != null && parentOid.length() > 0)
        	partCode = (OEMProjectType) CommonUtil.getObject(parentOid);

        try
        {	
        	//CARTYPE			차종
        	//CUSTOMEREVENT		이벤트
            long idLong = CommonUtil.getOIDLongValue(parentOid);
        	boolean isDelete = true;
        	try{
        		isDelete = checkKETCarYearlyAmount(parentOid);
        	}catch(Exception e){
        		Kogger.error(getClass(), e);
        	}
        	if(!isDelete){
        		
        		temp = "해당 차종이 개발 검토 의뢰서에 사용 중입니다. 삭제 하실 수 없습니다.";
        		return temp;
        	}
        	
        	QuerySpec qs = null;
            QueryResult r = null;
            
            
            if(partCode.getOemType().equals("CUSTOMEREVENT")){
            	qs = new QuerySpec(OEMPlan.class);
            	qs.appendWhere(new SearchCondition(OEMPlan.class, "oemTypeReference.key.id", "=", idLong), new int[] {0});
            	r = PersistenceHelper.manager.find(qs);
            }else{
            	qs = new QuerySpec(ModelPlan.class);
            	qs.appendWhere(new SearchCondition(ModelPlan.class, "carTypeReference.key.id", "=", idLong), new int[] {0});
            	r = PersistenceHelper.manager.find(qs);
            }
            
            
        	if (r.hasMoreElements())
            {	if(partCode.getOemType().equals("CUSTOMEREVENT")){
                	temp = "해당 고객이벤트가  자동차 일정 관리에 사용 중입니다. 삭제 하실 수 없습니다.";
            	}else{
            		temp = "해당 차종이  자동차 일정 관리에 사용 중입니다. 삭제 하실 수 없습니다.";
            	}
            }
            else
            {
                PersistenceHelper.manager.delete(partCode);
                temp = "삭제되었습니다.";
            }
            return temp;
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }    
    private boolean checkKETCarYearlyAmount(String parentOid) throws Exception {
    	QuerySpec qs = null;
        QueryResult r = null;
    	
        qs = new QuerySpec(KETCarYearlyAmount.class);
    	qs.appendWhere(new SearchCondition(KETCarYearlyAmount.class, "carType", "=", parentOid), new int[] {0});
    	r = PersistenceHelper.manager.find(qs);
    	if(r.hasMoreElements()){
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * Modify()
     * 
     * @param req
     * @param res
     */
    private String modify(HttpServletRequest req, HttpServletResponse res)
    {
        Message message = MessageBox.getInstance("message");
        ProjectOutPutType partCode = null;
       

        Hashtable tempHash = WebUtil.getHttpParams(req);

        Enumeration en = tempHash.keys();
        while(en.hasMoreElements()){
            Object obj = en.nextElement();
        }        
        
        
        String parentOid = (String) tempHash.get("oid");
        String name = (String) tempHash.get("NAME");
        String code = (String) tempHash.get("CODE");
        
        
        String isDisabled = (String) tempHash.get("isDisabled");
        boolean disable = false;
        if(isDisabled.equals("true")){
        	disable = true;
        }
        
        if (parentOid != null && parentOid.length() > 0)
        	partCode = (ProjectOutPutType) CommonUtil.getObject(parentOid);

       

        try
        {
            // DocCodeType Modify
        	partCode.setName(name);
            partCode.setIsDisabled(disable);
            
            if (code != null)
            	partCode.setCode(code);

            // 모자 Path 자동 수정 ( Seunghwan Choi , 2006.05.03 )
            partCode.setPath(getAllPath(partCode));
            String desc = "";
            if(tempHash.get("ATTR") instanceof String)
                desc = (String)tempHash.get("ATTR");
            else if(tempHash.get("ATTR") instanceof String[])
            {
                String[] attr = (String[]) tempHash.get("ATTR");
                for(int i=0;i<attr.length;i++)
                {
                    desc +=attr[i]+";";
                }
            }
            partCode.setDescription(desc);
           
            

            partCode = (ProjectOutPutType) PersistenceHelper.manager.modify(partCode);
         

            return partCode == null?message.getMessage("update.falil"):message.getMessage("update.success");
    
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }

    private String modifyOEM(HttpServletRequest req, HttpServletResponse res)
    {
        Message message = MessageBox.getInstance("message");
        OEMProjectType opt = null;
       

        Hashtable tempHash = WebUtil.getHttpParams(req);

        Enumeration en = tempHash.keys();
        while(en.hasMoreElements()){
            Object obj = en.nextElement();
        }        
        
        
        String parentOid = (String) tempHash.get("oid");
        String name = (String) tempHash.get("NAME");
        String code = (String) tempHash.get("CODE");
        
        boolean isDisabled = Boolean.parseBoolean((String) tempHash.get("isDisabled"));
        boolean isEcoCar = Boolean.parseBoolean((String) tempHash.get("isEcoCar"));
        boolean isRunningChange = Boolean.parseBoolean((String) tempHash.get("isRunningChange"));
        
        if (parentOid != null && parentOid.length() > 0)
        	opt = (OEMProjectType) CommonUtil.getObject(parentOid);

       

        try
        {
            // DocCodeType Modify
        	opt.setName(name);
            opt.setIsDisabled(isDisabled);
            
            //if (code != null)
            	//opt.setCode(code);

            // 모자 Path 자동 수정 ( Seunghwan Choi , 2006.05.03 )
            opt.setPath(getAllPath2(opt));
            String desc = "";
            if(tempHash.get("ATTR") instanceof String)
                desc = (String)tempHash.get("ATTR");
            else if(tempHash.get("ATTR") instanceof String[])
            {
                String[] attr = (String[]) tempHash.get("ATTR");
                for(int i=0;i<attr.length;i++)
                {
                    desc +=attr[i]+";";
                }
            }
            opt.setDescription(desc);
            opt.setIsEcoCar(isEcoCar);
            opt.setIsRunningChange(isRunningChange);

            opt = (OEMProjectType) PersistenceHelper.manager.modify(opt);
         

            return opt == null?message.getMessage("update.falil"):message.getMessage("update.success");
    
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }   
    /**
     * Create()
     * 
     * @param req
     * @param res
     */
    private String create(HttpServletRequest req, HttpServletResponse res)
    {
        ProjectOutPutType parent = null;
        String returnTemp = null;
        

        Hashtable tempHash = WebUtil.getHttpParams(req);
        
       

        Enumeration en = tempHash.keys();
        while(en.hasMoreElements()){
            Object obj = en.nextElement();
        }
        
        String parentOid = (String) tempHash.get("oid");
        String name = (String) tempHash.get("NAME");
        String attribute = null;
        
        
        ArrayList attr = new ArrayList();
        if(tempHash.get("ATTR") instanceof String)
            attribute = (String)tempHash.get("ATTR");
        else if(tempHash.get("ATTR") instanceof String[])
        {
            String[] tmp = (String[])tempHash.get("ATTR");
            for(int i=0;i<tmp.length;i++)
            {
                attr.add(tmp[i]);
            }
        }
        String code = (String) tempHash.get("CODE");
        
        
        if (parentOid != null && parentOid.length() > 0)
            parent = (ProjectOutPutType) CommonUtil.getObject(parentOid);

       

        try
        {
        	ProjectOutPutType child = ProjectOutPutType.newProjectOutPutType();
            child.setName(name);
            String desc = "";
            if(attribute!=null && attribute.length()>0)
            {
                desc=attribute+";";
            }
            else if(attr!=null && attr.size()>0)
            {
                for(int i=0;i<attr.size();i++)
                {
                    desc +=attr.get(i)+";";
                }
            }
            
            child.setDescription(desc);
          
            if (code != null)
                child.setCode(code);

            // Parent Child Link
            if (parent != null)
            {
                String parentCode = parent.getPath();
                if (parentCode.equalsIgnoreCase("root"))
                    parentCode = "";
                child.setPath(parent.getPath() + "/" + name);
                child.setParent(parent);
                
            }
            else
                child.setPath(name);

            child = (ProjectOutPutType) PersistenceHelper.manager.save(child);

                  
            if (child != null)
            {
                returnTemp = "생성되었습니다.oid="+CommonUtil.getOIDString(child);
            }
            else
            {
                returnTemp = "생성되지 않았습니다.";
            }
            return returnTemp;
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }

    private String createOEM(HttpServletRequest req, HttpServletResponse res)
    {
        OEMProjectType parent = null;
        String returnTemp = null;
        

        Hashtable tempHash = WebUtil.getHttpParams(req);
        
        
        Enumeration en = tempHash.keys();
        while(en.hasMoreElements()){
            Object obj = en.nextElement();
          
        }
        
        String name = (String) tempHash.get("NAME");
        String oemtype = (String) tempHash.get("oemtype");
        String code = (String) tempHash.get("CODE");
        
        boolean isEcoCar = Boolean.parseBoolean((String) tempHash.get("isEcoCar"));
        boolean isRunningChange = Boolean.parseBoolean((String) tempHash.get("isRunningChange"));
        
        try
        {
        	OEMProjectType child = OEMProjectType.newOEMProjectType();
           
            
            String numOid = (String) tempHash.get("oid");
            ReferenceFactory rf = new ReferenceFactory();
            NumberCode maker = (NumberCode)rf.getReference(numOid).getObject();
            
            child.setMaker(maker);
            
            OEMProjectType checkOem= CarLoad.checkCodeData(maker, "CARTYPE" , name);
            if(checkOem != null){
            	return "해당 차종이 존재 합니다. 다시 입력 하십시오.";
            }
            //Kogger.debug(getClass(), "#####################################      code:"+code);
            if (code != null && code.length() > 0 && !code.equals("null")){
                child.setCode(code);
            }else{
            	String year = DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy"));
            	code = ManageSequence.getSeqNo(year, "000", "OEMProjectType", OEMProjectType.CODE);
            	child.setCode(year+code);
            }
            
            
            // Parent Child Link
            child.setName(name);
            child.setIsSavePlan(false);
            child.setOemType(OEMType.toOEMType(oemtype));
            child.setIsEcoCar(isEcoCar);
            child.setIsRunningChange(isRunningChange);
            
            child = (OEMProjectType) PersistenceHelper.manager.save(child);

                  
            if (child != null)
            {
                returnTemp = "생성되었습니다.oid="+CommonUtil.getOIDString(child);
            }
            else
            {
                returnTemp = "생성되지 않았습니다.";
            }
            return returnTemp;
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return null;
    }
    
    
    
   protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse res)
    {
        QuerySpec query = null;
        try
        {
            query = new QuerySpec(ProjectOutPutType.class);
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        return query;
    }
   protected QuerySpec getQuerySpecOEM(HttpServletRequest req, HttpServletResponse res)
   {
       QuerySpec query = null;
       try
       {
           query = new QuerySpec(OEMProjectType.class);
       }
       catch (QueryException e)
       {
           Kogger.error(getClass(), e);
       }
       return query;
   }
   
    
    /**
     * 자  문서 타입 자동 수정
     * @param _docType
     * @author Seunghwan Choi
     * @date 2006.05.03
     */
    private void updateChildPath(ProjectOutPutType _docType)
    {
        try
        {
            QueryResult qr = PersistenceHelper.manager.navigate(_docType, "child", ParentChildLink.class);
            while (qr.hasMoreElements())
            {
            	ProjectOutPutType temp = (ProjectOutPutType) qr.nextElement();
                temp.setPath(_docType.getPath() + "/" + temp.getName());
                temp = (ProjectOutPutType)PersistenceHelper.manager.modify(temp);
                updateChildPath(temp);
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
    }
    private void updateChildPathOEM(OEMProjectType _docType)
    {
        try
        {
            QueryResult qr = PersistenceHelper.manager.navigate(_docType, "child", ParentChildLink.class);
            while (qr.hasMoreElements())
            {
            	OEMProjectType temp = (OEMProjectType) qr.nextElement();
                temp.setPath(_docType.getPath() + "/" + temp.getName());
                temp = (OEMProjectType)PersistenceHelper.manager.modify(temp);
                updateChildPathOEM(temp);
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
    }
    
    
    /**
     * 해당    문서 타입의 Path 반환
     * @param _docType
     * @return
     * @author Seunghwan Choi
     * @date 2006.05.03
     */
    private String getAllPath(ProjectOutPutType _partType)
    {
        return "/"+getParentPath(_partType, _partType.getName());
    }

    private String getParentPath(ProjectOutPutType _docType, String _path) 
    {
        try
        {
            QueryResult qr = PersistenceHelper.manager.navigate(_docType, "parent", ParentChildLink.class);
            while (qr.hasMoreElements())
            {
            	ProjectOutPutType temp = (ProjectOutPutType) qr.nextElement();
                return getParentPath(temp, temp.getName() + "/" + _path);
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return _path;
    }
    
    
    // OEM Type
    private String getAllPath2(OEMProjectType _partType)
    {
        return "/"+getParentPath2(_partType, _partType.getName());
    }

    private String getParentPath2(OEMProjectType _docType, String _path) 
    {
        try
        {
            QueryResult qr = PersistenceHelper.manager.navigate(_docType, "parent", ParentChildLink.class);
            while (qr.hasMoreElements())
            {
            	OEMProjectType temp = (OEMProjectType) qr.nextElement();
                return getParentPath2(temp, temp.getName() + "/" + _path);
            }
        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        return _path;
    }
    private String modifyOLevel(HttpServletRequest req, HttpServletResponse res)
    {
        String parentOid = req.getParameter("oid");
        OEMProjectType opt = null;
        String temp = "";
        String aaa = req.getParameter("viewtype");
        int oLevel = Integer.parseInt(aaa) ;

        if (parentOid != null && parentOid.length() > 0)
        	opt = (OEMProjectType) CommonUtil.getObject(parentOid);

        try
        {	
        	long makeId = 0;
        	if(opt != null){
        		makeId = CommonUtil.getOIDLongValue(opt.getMaker().getPersistInfo().getObjectIdentifier().getStringValue());
        	}
        	        	
        	QuerySpec qs = new QuerySpec();
        	qs.appendClassList(OEMProjectType.class, true);
    		if(oLevel == 0){
	        	SearchCondition sc = new SearchCondition(OEMProjectType.class, "oLevel", "=", oLevel);
	    		qs.appendWhere(sc ,new int[]{0});
	    		
	    		qs.appendAnd();
	    		
	    		SearchCondition sc2 = new SearchCondition(OEMProjectType.class, "oLevel", "<>", 0);
	    		qs.appendWhere(sc2 ,new int[]{0});
    		}else if( oLevel > 0){
    			SearchCondition sc = new SearchCondition(OEMProjectType.class, "oLevel", "=", oLevel);
	    		qs.appendWhere(sc ,new int[]{0});
	    		
	    		qs.appendAnd();
	    		
	    		SearchCondition sc2 = new SearchCondition(OEMProjectType.class, "makerReference.key.id", "=", makeId);
	    		qs.appendWhere(sc2, new int[]{0});
    		}
    		
    		
        	QueryResult qr = PersistenceHelper.manager.find(qs);
        	
        	boolean check = qr.hasMoreElements();
        	
    		if(check){
        		temp = "이미 등록된 Type입니다.";
        	}else{
        		if(opt != null){
        		opt.setOLevel(oLevel);
				opt = (OEMProjectType) PersistenceHelper.manager.modify(opt);
				temp = "수정되었습니다.";
        		}
        	}

        }
        catch (WTException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        return temp;
    }
    
}
