package ext.ket.part.base.service;

import org.junit.Test;

import wt.epm.EPMDocument;
import wt.part.WTPart;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardPartBaseServiceTest extends AbstractUnitTest {

    @Test
    public void testPrintAttrCode() throws Exception {
	
//	WTPart part = (WTPart) CommonUtil.getObject("wt.part.WTPart:100000379412");
//	PartBaseHelper.service.reviseWTPartNGetVersion(part, null);
	
	//  1T417000 
//	EPMDocument args1 = (EPMDocument) CommonUtil.getObject("wt.epm.EPMDocument:108845439");
//	String args2 = "e3ps.ecm.entity.KETProdChangeOrder:100002419250";
//	WTPart args3 = (WTPart) CommonUtil.getObject("wt.part.WTPart:100002663584");
//	String args4 = "e3ps.ecm.entity.KETProdChangeOrder:100001061506";
	PartBaseHelper.service.testPart("H460008", "ECAD_DRAWING"); //13261000 , args2, args3, args4 
	
	//
	
//	validRelatedPartRevised((EPMDocument)args[0], (String)args[1]);
//	validRelatedEpmCancelRevised((WTPart)args[2], (String)args[3]);
	
//	wt.part.WTPart:100000955121	2001000
//	wt.part.WTPart:100000958117	13261000
	
//	part = (WTPart) CommonUtil.getObject("wt.part.WTPart:100000358354");
//	PartBaseHelper.service.reviseWTPartNGetVersion(part, "PROTO");
//	
//	part = (WTPart) CommonUtil.getObject("wt.part.WTPart:100000358430");
//	PartBaseHelper.service.reviseWTPartNGetVersion(part, "PILOT");
//	
//	part = (WTPart) CommonUtil.getObject("wt.part.WTPart:100000358411");
//	PartBaseHelper.service.reviseWTPartNGetVersion(part, "PRODUCTION");
	
//	WTPart diePart = getLatestPart("29119000");
//	WTPart moldPart = getLatestPart("21000A00-016");
//	WTPart moldPart2 = getLatestPart("H677501-5");
//	WTPart moldPart3 = getLatestPart("H677498");
//	WTPart diePart = getLatestPart("11154000");
//	WTPart prodPart = getLatestPart("H644148");
	
//	String epm3dMastOid = "wt.epm.EPMDocumentMaster:57046021";
	
    }
}