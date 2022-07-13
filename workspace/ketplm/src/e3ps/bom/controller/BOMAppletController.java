package e3ps.bom.controller;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

//import laf.support.collection.LData;
//import laf.support.collection.LMultiData;

import e3ps.bom.dao.BOMAppletDao;
import wt.util.WTException;

public class BOMAppletController 
{
	public BOMAppletController(){}

	public Vector getBOMStateController() throws UnsupportedEncodingException, WTException
	{
		BOMAppletDao dao =  new BOMAppletDao();					
		return dao.getBOMState();
	}

	public Vector getBOMECOStateController() throws UnsupportedEncodingException, WTException
	{
		BOMAppletDao dao =  new BOMAppletDao();				
		return dao.getBOMECOState();
	}
	
//	public LMultiData getUserInfoController(LData paramData) throws UnsupportedEncodingException, WTException
//	{
//		BOMAppletDao dao =  new BOMAppletDao();				
//		return dao.getUserInfo(paramData);
//	}
}
