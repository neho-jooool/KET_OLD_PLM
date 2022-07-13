package ext.ket.edm.stamping.service;

import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:/spring/context-*.xml" })
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
//@Transactional
public class StandardStampingServiceTest { // extends AbstractUnitTest {

//    private static KETDrawingDistRequest newSample = null;
//
//
//    private static final boolean NEED_NEW_OBJECT = false;
//
//    private static String reqOid = null;
//
//    private static String TEST_DIST_REQ_NO = "TKLEE_002";
//
//    protected MockHttpServletRequest request = new MockHttpServletRequest();
//    protected MockHttpServletResponse response = new MockHttpServletResponse();

//    @Before(value = "")
//    public void initialize() throws Exception {
//
//	RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//	ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//	HttpServletRequest httpRequest = attr.getRequest();
//	ServletContext servletContext = httpRequest.getSession().getServletContext();
//	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(servletContext);
//
//	RemoteMethodServer.getDefault().setUserName("wcadmin");
//	RemoteMethodServer.getDefault().setPassword("wcadmin");
//    }

//    @Test
//    public void testInit() throws Exception {
//
//	if (NEED_NEW_OBJECT) {
//
//	    KETDrawingDistRequest oldSample = TempHelper.service.getDistReq(TEST_DIST_REQ_NO);
//
//	    if (oldSample != null) {
//		TempHelper.service.deleteDistRequest(oldSample);
//	    }
//
//	    KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();
//
//	    paramObject.setNumber(TEST_DIST_REQ_NO);
//	    paramObject.setName("TKLEE_001");
//	    paramObject.setTitle("TKLEE_001");
//	    paramObject.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]Junit 테스트에서 등록");
//	    paramObject.setDistType("APPROVED");
//	    paramObject.setDistReason("가공품 제작");
//	    paramObject.setDistSubcontractor("협력업체1");
//	    paramObject.setWriteDeptEnName("R&D Development 3 Team");
//	    paramObject.setWriteDeptKrName("연구개발3팀");
//	    paramObject.setWriteDeptCode("11737");
//
//	    // 배포 포멧
//	    paramObject.setDrawingDistFileTypeArray("PDF,DWG,ONEDO,STEP");
//
//	    // 배포 부서
//	    paramObject.setDrawingDistDeptArray("e3ps.groupware.company.Department:29845,e3ps.groupware.company.Department:226030535");
//
//	    // 배포 도면 - case CREO Ass'y, CREO 단품, NX Ass'y, NX 단품, DWG2
//	    paramObject.setDrawingDisEpmArray("wt.epm.EPMDocument:572901947,wt.epm.EPMDocument:813929193" // CREO
//		    + ",wt.epm.EPMDocument:65587861,wt.epm.EPMDocument:84275895" // NX
//		    + ",wt.epm.EPMDocument:44176192,wt.epm.EPMDocument:44176225"); // DWG
//
//	    paramObject.setSheetTypeArray("All,All,Active,All,None,None");
//	    // paramObject.setSheetTypeArray("All,All,All,Active,None,None");
//
//	    // 배포 문서 - 기술문서, 개발산출물
//	    paramObject.setDrawingDistDocArray("e3ps.dms.entity.KETProjectDocument:574469658,e3ps.dms.entity.KETTechnicalDocument:668615222");
//
//	    try {
//
//		newSample = TempHelper.service.createDistReq(paramObject);
//
//	    } catch (Exception e) {
//		Kogger.error(getClass(), e);
//	    }
//
//	} else {
//
//	    newSample = TempHelper.service.getDistReq(TEST_DIST_REQ_NO);
////	    TempHelper.service.deleteDistRequest(newSample);
//	    Kogger.debug(getClass(), newSample);
//	}
//
//	reqOid = CommonUtil.getOIDString(newSample);
//    }

     @Test
     public void testInputQueueDrawingDistReq() throws Exception {
	 boolean ret = StampingHelper.service.inputQueueDrawingDistReq();
     }

    // @Test
    // public void testCreateKETStampingWhenQueueInput() throws Exception {
    // StampingHelper.service.createKETStampingWhenQueueInput(newSample);
    // }

    // @Test
    // public void testIsReceivedAlready() throws Exception {
    // boolean ret = StampingHelper.service.isReceivedAlready(reqOid);
    // }

//    String xmlFileFullPath = "E:\\Stamping\\2014\\07\\27\\TKLEE_001\\17\\TKLEE_001.xml";

//    @Test
//    public void testGetXmlNCadDataByOid() throws Exception {
//	String xmlFileFullPath = StampingHelper.service.getXmlNCadDataByOid(reqOid);
//    }

//     @Test
//     public void testSaveDrawingDeployRequest() throws Exception {
//	 boolean ret = StampingHelper.service.saveDrawingDeployRequest(reqOid, xmlFileFullPath);
//     }

//     @Test
//     public void testSaveErrorLog() throws Exception {
//	 
//         String errorWhere = "TEST";
//         String errorType = "TEST";
//         String errorLog = "TEST";
//         
//         if( reqOid == null) {
//             newSample = TempHelper.service.getDistReq(TEST_DIST_REQ_NO);
//	     Kogger.debug(getClass(), newSample);
//	     reqOid = CommonUtil.getOIDString(newSample);
//	}
//        
//         boolean ret = StampingHelper.service.saveErrorLog(reqOid, errorWhere, errorType, errorLog, xmlFileFullPath);
//     }


}
