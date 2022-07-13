package e3ps.bom;

import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.multiplebomeco.MultipleECOCmd;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.command.updatebom.UpdateBOMDialog;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMCodePool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AIFDesktopToolBar;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMECOQueryBean;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;
import gui.JMouseWheelFrame;

import java.applet.Applet;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JPanel;

import wt.util.WTException;

public class BOMRegisterDesktop extends JMouseWheelFrame
{
	private static final long serialVersionUID = 1L;
	private Applet applet;
    private AIFDesktopToolBar toolBar;
    private JPanel jpanel;

    private Hashtable reasonProdHash;
    private Hashtable reasonMoldHash;
	private Hashtable reasonDetailHash;

	private static Registry appReg = Registry.getRegistry("e3ps.bom.bom");
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

	public AbstractAIFUIApplication app;
    public static void main(String args[])
    {
    	try
    	{
			BOMRegisterDesktop bomRegister = new BOMRegisterDesktop();
	        bomRegister.setVisible(true);

	        bomRegister.addWindowListener(new WindowAdapter()
			{
	            public void windowClosing(WindowEvent windowevent)
	            {
	                System.exit(0);
	            }
	        });
    	}
    	catch(Exception ex)
    	{
    		Kogger.error(BOMRegisterDesktop.class, ex);
    	}
    }

    public BOMRegisterDesktop()
    {
        start();
    }

    public void start()
    {
        try
        {
            app = new BOMRegisterApplication(this);

            toolBar = new AIFDesktopToolBar(this);
            setJMenuBar(app.getApplicationMenuBar());
            toolBar.setToolBar(app.getApplicationToolBar());
            jpanel = app.getApplicationPanel();
            getContentPane().add("North", toolBar);
            getContentPane().add("Center", jpanel);
            validate();
            repaint();
        }
        catch(Exception e)
        {
			Kogger.error(getClass(), e);
        }
    }

    public BOMRegisterDesktop(String initBomOid, String workType, String objectType, String ecoType, Applet parent,
    								   String childItemCode, String ecoNumber, String parentItemCodes)
    {
        applet = parent;
        start(initBomOid, workType, objectType, ecoType, childItemCode, ecoNumber, parentItemCodes);
    }

    public void start(String initBomOid, String workType, String objectType, String ecoType,
    				     String childItemCode, String ecoNumber, String parentItemCodes)
    {
        try
        {
	        this.setTitle("BOM Editor");

	        //shin.... 아래의 getApp()에서 사용하기 위해...
            app = new BOMRegisterApplication(this);

            toolBar = new AIFDesktopToolBar(this);
            setJMenuBar(app.getApplicationMenuBar());
            toolBar.setToolBar(app.getApplicationToolBar());
            jpanel = app.getApplicationPanel();
            getContentPane().add("North", toolBar);
            getContentPane().add("Center", jpanel);
            validate();
            repaint();
            setVisible(true);


            setInitBom(initBomOid, workType, objectType, ecoType, app, childItemCode, ecoNumber, parentItemCodes);

			if (!((BOMRegisterApplicationPanel)jpanel).isConnectSuccess())
			{
				MessageBox mbox = new MessageBox(this, messageRegistry.getString("serverConnectionError"), "Initialization Error", 0);
				mbox.setModal(true);
				mbox.setVisible(true);

				DBConnectionManager.getInstance().release();
				this.dispose();
			}
        }
        catch(Exception e)
        {
			Kogger.error(getClass(), e);
        }
    }

    public void setInitBom(String initBomOid, String workType, String objectType, String ecoType, AbstractAIFUIApplication app,
    							String childItemCode, String ecoNumber, String parentItemCodes)
    {
		if (initBomOid != null && initBomOid.length() >0 && !initBomOid.equalsIgnoreCase("null"))
		{
			if(objectType.equals("BOM"))
			{
				BOMRegisterApplicationPanel pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
				try	{
					setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

					Vector vecOpenPublicBOM = new Vector();
					BOMBasicInfoPool.setPublicModelName("Empty");
					Hashtable hasHeader = new Hashtable();

					pnl.clearBOMList();

Kogger.debug(getClass(), ">>>>> initBomOid [objectType -> BOM] : " + initBomOid);

					hasHeader = KETBomHelper.service.getBom(initBomOid);

					BOMBasicInfoPool.setPublicBOMStatus(Utility.checkNVL((String)hasHeader.get("lifeCycleState")));
					BOMBasicInfoPool.setPublicBOMStatusKr(Utility.checkNVL((String)hasHeader.get("lifeCycleStateKr")));
					BOMBasicInfoPool.setPublicApproveDate(Utility.checkNVL((String)hasHeader.get("publicApproveDate")));
					BOMBasicInfoPool.setPublicApproveDept(Utility.checkNVL((String)hasHeader.get("publicApproveDept")));
					BOMBasicInfoPool.setPublicApproveId(Utility.checkNVL((String)hasHeader.get("publicApproveID")));
					BOMBasicInfoPool.setPublicApproveName(Utility.checkNVL((String)hasHeader.get("publicApproveName")));
					BOMBasicInfoPool.setPublicBasicModelName(Utility.checkNVL((String)hasHeader.get("publicBasicModelName")));
					BOMBasicInfoPool.setPublicBasicModelDesc(Utility.checkNVL((String)hasHeader.get("publicBasicModelDesc")));
					BOMBasicInfoPool.setPublicOwnerDate(Utility.checkNVL((String)hasHeader.get("publicOwnerDate")));
					BOMBasicInfoPool.setPublicOwnerDept(Utility.checkNVL((String)hasHeader.get("publicOwnerDept")));
					BOMBasicInfoPool.setPublicOwnerId(Utility.checkNVL((String)hasHeader.get("publicOwnerID")));
					BOMBasicInfoPool.setPublicOwnerName(Utility.checkNVL((String)hasHeader.get("publicOwnerName")));
					BOMBasicInfoPool.setPublicOwnerEmail(Utility.checkNVL((String)hasHeader.get("publicOwnerEmail")));
					BOMBasicInfoPool.setPublicModelName(Utility.checkNVL((String)hasHeader.get("publicModelName")));
					BOMBasicInfoPool.setPublicModelDesc(Utility.checkNVL((String)hasHeader.get("publicModelDesc")));
					BOMBasicInfoPool.setPublicModelUom(Utility.checkNVL((String)hasHeader.get("publicModelUom")));
					BOMBasicInfoPool.setPublicModelUserItemType(Utility.checkNVL((String)hasHeader.get("publicModelUserItemType")));
					BOMBasicInfoPool.setPublicModelStatus(Utility.checkNVL((String)hasHeader.get("publicModelStatus")));
					BOMBasicInfoPool.setPublicTransFlag(Utility.checkNVL((String)hasHeader.get("publicTransFlag")));
					BOMBasicInfoPool.setPublicCheckOutStatus(Utility.checkNVL((String)hasHeader.get("publicCheckOutStatus")));
					BOMBasicInfoPool.setPublicBOMOid(initBomOid);
					BOMBasicInfoPool.setUserRole("Owner");
					BOMBasicInfoPool.setBomEcoType("");
					BOMBasicInfoPool.setBomEcoNumber("");
					BOMBasicInfoPool.setBomHeaderPartType(Utility.checkNVL((String)hasHeader.get("bomHeaderType")));

					String strModelName = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

Kogger.debug(getClass(), ">>> strModelName : " + strModelName);
Kogger.debug(getClass(), ">>> Utility.checkNVL(BOMBasicInfoPool.getOrgCode()) : " + Utility.checkNVL(BOMBasicInfoPool.getOrgCode()));

	        		// Added by MJOH, 2007-03-16	//////////////////////////////////////////////////////////////////////
					String newBomOrgCode = strModelName.substring(0, 3);

					Kogger.debug(getClass(),  "==============> NewBOM OrgCode : " + newBomOrgCode );
					Kogger.debug(getClass(),  "==============> Before Pool OrgCode : " + BOMBasicInfoPool.getOrgCode() );

					if( !newBomOrgCode.equals(Utility.checkNVL(BOMBasicInfoPool.getOrgCode())) ) {
						BOMSearchUtilDao bomSearchUtilDao = new BOMSearchUtilDao();
			            String bomOrgID = bomSearchUtilDao.getOrgID( newBomOrgCode );
						BOMBasicInfoPool.setOrgCode( newBomOrgCode );
						BOMBasicInfoPool.setOrgId( bomOrgID );

						pnl.publicStatusPanel.setStatusBar();
					}

					Kogger.debug(getClass(),  "==============> After Pool OrgCode : " + BOMBasicInfoPool.getOrgCode() );
					/////////////////////////////////////////////////////////////////////////////////////////////////////////

					MyBOMQry qry = new MyBOMQry();
					vecOpenPublicBOM = qry.getBOMOpen(strModelName);

					BOMBasicInfoPool.setCoWorkerVec(qry.getCoworkerData(strModelName));
					BOMBasicInfoPool.setPublicMyStatus(qry.getMyStatus(strModelName, Utility.checkNVL(BOMBasicInfoPool.getUserId())));

					BOMAssyComponent rootComponent = new BOMAssyComponent(strModelName, true);
					rootComponent.setLevelInt(new Integer(0));
					rootComponent.setSeqInt(new Integer(0));
					rootComponent.setSortOrderStr("0001");
					rootComponent.setNewFlagStr("NEW");
					rootComponent.setDescStr(Utility.checkNVL(BOMBasicInfoPool.getPublicModelDesc()));
					rootComponent.setUomStr(Utility.checkNVL(BOMBasicInfoPool.getPublicModelUom()));
					rootComponent.setUitStr(Utility.checkNVL(BOMBasicInfoPool.getPublicModelUserItemType()));
					rootComponent.setStatusStr(Utility.checkNVL(BOMBasicInfoPool.getPublicModelStatus()));
					// Added by MJOH, 2011-04-07
					rootComponent.setIBAPartType( Utility.checkNVL((String)hasHeader.get("bomHeaderType")) );

					Vector version = new Vector();
					Vector itemVec = new Vector();
					Hashtable inputParams = new Hashtable();

					Vector checkOutVec = new Vector();
					checkOutVec.addElement(BOMBasicInfoPool.getPublicModelName());

Kogger.debug(getClass(), "====>> myBOM BOMBasicInfoPool.getPublicModelName() : " + BOMBasicInfoPool.getPublicModelName());

					Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutVec);

Kogger.debug(getClass(), "====>> coworkerVec : " + coworkerVec.size());
					if(coworkerVec != null && coworkerVec.size() > 0) {
						rootComponent.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|")+1));
					}

					itemVec.addElement(BOMBasicInfoPool.getPublicModelName());
					inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
					inputParams.put("itemCode", itemVec);

					version = KETBomHelper.service.getItemVersion(inputParams);
					String versionStr = "";

					for(int k=0; k<version.size(); k++) {
						if(version.size() > 0) {
							versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
							if(BOMBasicInfoPool.getPublicModelName().equals(versionStr.substring(0, versionStr.indexOf("@")))) {
								rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
							}
						}
					}

					pnl.openBOMData(rootComponent, vecOpenPublicBOM);

					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} catch(Exception ex) {
					setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					pnl.clearBOMList();
					Kogger.error(getClass(), ex);
				}
			}
			else
			{
				if(ecoType.equals("standard") || ecoType.equals("")) {			// 일반 변경

					BOMRegisterApplicationPanel pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
					KetMainJTreeTable km = new KetMainJTreeTable();
					BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

					try {
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

						Vector vecOpenPublicBOM = new Vector();
						BOMBasicInfoPool.setPublicModelName("Empty");

						pnl.clearBOMList();

Kogger.debug(getClass(), ">>>>> initBomOid [ecoType ->standard] : " + initBomOid);

						setBOMECOInfoPool(initBomOid.substring(initBomOid.indexOf(":")+1));

						String strBomEcoNumber = Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber());
						String strModelName = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

						// root node 가 제품인지 금형인지 구분하여 Main Editor 컬럼정보를 셋팅함
//						WTPart part = KETPartHelper.service.getPart(strModelName);
//						String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

//						if (strType != null && strType.equals("P")) {	// 제품인 경우
//							km.setGenMain(app);
//						} else {												// 금형인 경우
//							km.setMoldMain(app);
//						}

		        		// Added by MJOH, 2007-03-16	//////////////////////////////////////////////////////////////////////
						String ecoOrgCode = "000";		// 한국단자 임시 OrgCode

						Kogger.debug(getClass(),  "==============> ECO OrgCode : " + ecoOrgCode );
						Kogger.debug(getClass(),  "==============> Before Pool OrgCode : " + BOMBasicInfoPool.getOrgCode() );

						if( !ecoOrgCode.equals(Utility.checkNVL(BOMBasicInfoPool.getOrgCode())) ) {
							BOMSearchUtilDao bomSearchUtilDao = new BOMSearchUtilDao();
				            String ecoOrgID = bomSearchUtilDao.getOrgID( ecoOrgCode );
							BOMBasicInfoPool.setOrgCode( ecoOrgCode );
							BOMBasicInfoPool.setOrgId( ecoOrgID );

							pnl.publicStatusPanel.setStatusBar();
						}

						Kogger.debug(getClass(),  "==============> After Pool OrgCode : " + BOMBasicInfoPool.getOrgCode() );
						/////////////////////////////////////////////////////////////////////////////////////////////////////////

						MyBOMQry qry = new MyBOMQry();
						vecOpenPublicBOM = qry.getBOMEcoOpen(strBomEcoNumber, strModelName);

						BOMBasicInfoPool.setCoWorkerVec(qry.getCoworkerData(strBomEcoNumber));
						BOMBasicInfoPool.setPublicMyStatus(qry.getBOMEcoMyStatus(strBomEcoNumber, Utility.checkNVL(BOMBasicInfoPool.getUserId())));

						BOMAssyComponent rootComponent = new BOMAssyComponent(strModelName, true);
						rootComponent.setLevelInt(new Integer(0));
						rootComponent.setSeqInt(new Integer(0));
						rootComponent.setSortOrderStr("0001");
						rootComponent.setNewFlagStr("OLD");
						rootComponent.setEcoHeaderNumberStr(strBomEcoNumber);
						
Kogger.debug(getClass(), "@@@@@ rootComponent : " + rootComponent);

						/********************************************/
						// Root Node 의 Item 정보 Query
						Hashtable itemInputParams = new Hashtable();
						itemInputParams.put("itemCode", BOMBasicInfoPool.getPublicModelName().trim());
						itemInputParams.put("description", "");
						itemInputParams.put("creator", "");
						itemInputParams.put("createFromDate", "");
						itemInputParams.put("createToDate", "");
						itemInputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
						itemInputParams.put("bomAllowed", "");
						itemInputParams.put("bomFlag", "");

						Vector queryResult = new Vector();
						queryResult = KETBomHelper.service.searchItem(itemInputParams);

						String modelDesc = "";
						String modelUom = "";
						String modelUit = "";
						String modelStatus = "";
						String modelStatusKr = "";
						String modelVersion = "";
						// Added by MJOH, 2011-04-07
						String partTypeStr = "";

						if(queryResult.size() > 0)
						{
							Hashtable hasSearchItemResult = new Hashtable();

							hasSearchItemResult = (Hashtable)queryResult.elementAt(0);

							modelDesc = (String)hasSearchItemResult.get("description");
							modelUit = (String)hasSearchItemResult.get("defaultunit");
							modelStatus = (String)hasSearchItemResult.get("status");
							modelStatusKr = (String)hasSearchItemResult.get("statusKr");
							modelVersion = (String)hasSearchItemResult.get("version");
							// Added by MJOH, 2011-04-07
							partTypeStr = (String)hasSearchItemResult.get("typeValue");
						}
						/********************************************/
Kogger.debug(getClass(), "############# partTypeStr : " + partTypeStr);
						rootComponent.setDescStr(modelDesc);
						rootComponent.setUitStr(modelUit);
						rootComponent.setStatusStr(modelStatus);
						rootComponent.setStatusKrStr(modelStatusKr);
						// Added by MJOH, 2011-04-07
						rootComponent.setIBAPartType( partTypeStr );
						BOMBasicInfoPool.setBomHeaderPartType( partTypeStr );

						if (BOMECOBasicInfoPool.getBomBoxQuantity() != null && !BOMECOBasicInfoPool.getBomBoxQuantity().equals("")) {
							rootComponent.setBoxQuantityDbl(Double.parseDouble(BOMECOBasicInfoPool.getBomBoxQuantity()));	// Box Quantity
						} else {
							rootComponent.setBoxQuantityDbl(1.0);
						}

						Vector itemVec = new Vector();
						itemVec.addElement(BOMBasicInfoPool.getPublicModelName());

//						Vector checkOutVec = new Vector();
//						checkOutVec.addElement(BOMBasicInfoPool.getPublicModelName());
//						Vector coworkerVec = KETBomHelper.service.getCheckOuter(checkOutVec);
//						if(coworkerVec != null && coworkerVec.size() > 0) {
//							rootComponent.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|")+1));
//						}

						// 해당 모부품을 체크아웃한 사용자가 있는지 확인 (있는경우 RootNode 에 셋팅함)
						Hashtable coworker = checkoutDao.getCheckOuterInfo(BOMBasicInfoPool.getPublicModelName());
						if ( coworker != null && coworker.size() > 0 ) {
							if ( BOMBasicInfoPool.getPublicModelName().trim().equals(coworker.get("itemcode")) ) {
								rootComponent.setCheckOutStr( coworker.get("user_name") + "(" + coworker.get("user_email") + ")" );
							}
						}

						rootComponent.setVersionStr(modelVersion);
						rootComponent.setItemSeqInt(new Integer(10));

						BOMBasicInfoPool.setCoWorkerVec(qry.getBOMEcoCoworkerData(BOMBasicInfoPool.getBomEcoNumber().trim()));
						BOMBasicInfoPool.setPublicMyStatus(qry.getBOMEcoMyStatus(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()), Utility.checkNVL(BOMBasicInfoPool.getUserId())));
						BOMBasicInfoPool.setOrgCode(BOMBasicInfoPool.getOrgCode());
						BOMBasicInfoPool.setOrgName(BOMBasicInfoPool.getOrgName());
						
						// root node 가 제품인지 금형인지 구분하여 Main Editor 컬럼정보를 셋팅함
						if (PartUtil.isProductType(partTypeStr)) {	// 제품인 경우
							km.setGenMain(app);
						} else {												// 금형인 경우
							km.setMoldMain(app);
						}

						// 메인 에디터 초기화(자동체크인 없이)
//						pnl.mainEditorPane.clearBOMListNotCheckIn();

						if(vecOpenPublicBOM.size() > 0) {
							pnl.openBOMData(rootComponent, vecOpenPublicBOM);
						} else {
							Vector vecOpenBOM = new Vector();
							BOMTreeNode selectedNode = new BOMTreeNode(rootComponent);
							UpdateBOMDialog insertRefBom = new UpdateBOMDialog();
							insertRefBom.insertReferenceBom(BOMBasicInfoPool.getPublicModelName().trim(), selectedNode, appReg.getString("plm"), false);
							vecOpenBOM = qry.getBOMEcoOpen(BOMBasicInfoPool.getBomEcoNumber(), BOMBasicInfoPool.getPublicModelName().trim());

							rootComponent.setOpSeqInt(new Integer(1));
							rootComponent.setItemSeqInt(new Integer(10));
							pnl.openBOMData(rootComponent, vecOpenBOM);
						}

						// 작업자의 작업상태가 4(작업완료) 가 아닌 경우에만 자동으로 Check-Out 처리 함
						String strStatus = "";
						BOMSearchUtilDao utilDao = new  BOMSearchUtilDao();
						strStatus = utilDao.getEndWorkingFlag(BOMBasicInfoPool.getBomEcoNumber(), BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
						String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();
Kogger.debug(getClass(), "@@@@ strStatus : " + strStatus);
Kogger.debug(getClass(), "@@@@ bomStatus : " + bomStatus);
						if ( (strStatus != null && !strStatus.equals("") && !strStatus.equals("4")) && (bomStatus.equals("INWORK") || bomStatus.equals("REWORK")) ) {

							//shin...체크아웃 상태로 만듬............................................................................................................................................................................
//							rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
							CheckOutCmd coc = new CheckOutCmd(app.getDesktop(), app);
							BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
							coc.checkOut(nodes, itemVec, BOMBasicInfoPool.getUserName(), true, modelVersion);
//							checkoutDao.setCheckOut( BOMBasicInfoPool.getPublicModelName(), modelVersion, BOMBasicInfoPool.getUserId() );
							// .................................................................................................................................................................................................................................
						}

						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					} catch(Exception ex) {
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						pnl.clearBOMList();
						Kogger.error(getClass(), ex);
					}
				}
				else
				{														// 일괄 변경
					try {
						setMultiBOMECOInfoPool(ecoNumber, parentItemCodes);
						String desc = getItemDesc(childItemCode);

						MultipleECOCmd multipleEco = new MultipleECOCmd(this, app, childItemCode, desc, parentItemCodes);
						multipleEco.executeCommand();
					} catch(Exception ex) {
						Kogger.error(getClass(), ex);
					}
				}
			}
		}
		else
		{
			BOMBasicInfoPool.setPublicModelName("Empty");
		}
    }


	// 일반 변경일 경우, ECO 관련 BOM/ECO 정보 셋팅
	private void setBOMECOInfoPool(String oid) {

		String strEcoNo = "";
		String strItemCode = "";
		Hashtable header = null;
		Hashtable ecoHash = null;

        KETProdChangeOrder  prodChange = null;
        KETMoldChangeOrder  moldChange = null;

		reasonProdHash = BOMCodePool.getProdReason();
		reasonMoldHash = BOMCodePool.getMoldReason();

		try {
//Kogger.debug(getClass(), "@@@@@@@@@@@ oid : " + oid);
			header = KETBomHelper.service.getBomEco(oid);
			KETBOMECOQueryBean bean = new KETBOMECOQueryBean();
//Kogger.debug(getClass(), "@@@@@@@@@@@ header : " + header);
			if (header != null) {

				strItemCode = ((String) header.get("itemCode")).trim();
				strEcoNo = ((String) header.get("ecoNo")).trim();
				ecoHash = bean.getBomEco(strEcoNo, strItemCode);

				BOMECOBasicInfoPool.setPublicBOMStatus(header.get("lifeCycleStateKr") == null ? "" : ((String) header.get("lifeCycleStateKr")).trim());
				BOMECOBasicInfoPool.setPublicOwnerId(header.get("creatorId") == null ? "" : ((String) header.get("creatorId")).trim());
				BOMECOBasicInfoPool.setPublicOwnerDate(header.get("creatorDate") == null ? "" : ((String) header.get("creatorDate")).trim());
				BOMECOBasicInfoPool.setPublicOwnerName(header.get("creatorName") == null ? "" : ((String) header.get("creatorName")).trim());
				BOMECOBasicInfoPool.setPublicModelName(header.get("itemCode") == null ? "" : ((String) header.get("itemCode")).trim());
				BOMECOBasicInfoPool.setPublicTransFlag(header.get("transferflag") == null ? "" : ((String) header.get("transferflag")).trim());
				BOMECOBasicInfoPool.setPublicBOMOid(header.get("headerId") == null ? "" : ((String) header.get("headerId")).trim());
				BOMECOBasicInfoPool.setBomEcoNumber(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
				BOMECOBasicInfoPool.setBomBoxQuantity(header.get("boxQuantity") == null ? "" : ((String) header.get("boxQuantity")).trim());
				BOMECOBasicInfoPool.setBomEcoType(header.get("ecoType") == null ? "" : ((String) header.get("ecoType")).trim());
				BOMECOBasicInfoPool.setECONo(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
				BOMECOBasicInfoPool.setBomEcoReason(ecoHash.get("reason") == null ? "" : ((String) ecoHash.get("reason")).trim());
				BOMECOBasicInfoPool.setBomEcoReasonDetails(ecoHash.get("reasonDetail") == null ? "" : ((String) ecoHash.get("reasonDetail")).trim());
				BOMECOBasicInfoPool.setBomEcoRelatedEcrNo("");
				BOMECOBasicInfoPool.setBomEcoTitle(ecoHash.get("title") == null ? "" : ((String) ecoHash.get("title")).trim());


				BOMBasicInfoPool.setPublicBOMStatus(header.get("lifeCycleState") == null ? "" : ((String) header.get("lifeCycleState")).trim());
				BOMBasicInfoPool.setPublicBOMStatusKr(header.get("lifeCycleStateKr") == null ? "" : ((String) header.get("lifeCycleStateKr")).trim());
				BOMBasicInfoPool.setPublicOwnerId(header.get("creatorId") == null ? "" : ((String) header.get("creatorId")).trim());
				BOMBasicInfoPool.setPublicOwnerDate(header.get("creatorDate") == null ? "" : ((String) header.get("creatorDate")).trim());
				BOMBasicInfoPool.setPublicOwnerName(header.get("creatorName") == null ? "" : ((String) header.get("creatorName")).trim());
				BOMBasicInfoPool.setPublicModelName(header.get("itemCode") == null ? "" : ((String) header.get("itemCode")).trim());
				BOMBasicInfoPool.setPublicTransFlag(header.get("transferflag") == null ? "" : ((String) header.get("transferflag")).trim());
				BOMBasicInfoPool.setPublicBOMOid(header.get("headerId") == null ? "" : ((String) header.get("headerId")).trim());
				BOMBasicInfoPool.setBomEcoNumber(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
				BOMBasicInfoPool.setBomBoxQuantity(header.get("boxQuantity") == null ? "" : ((String) header.get("boxQuantity")).trim());
				BOMBasicInfoPool.setBomEcoType(header.get("ecoType") == null ? "" : ((String) header.get("ecoType")).trim());
				BOMBasicInfoPool.setECONo(header.get("headerNumber") == null ? "" : ((String) header.get("headerNumber")).trim());
				BOMBasicInfoPool.setBomEcoReason(ecoHash.get("reason") == null ? "" : ((String) ecoHash.get("reason")).trim());
				BOMBasicInfoPool.setBomEcoReasonDetails(ecoHash.get("reasonDetail") == null ? "" : ((String) ecoHash.get("reasonDetail")).trim());
				BOMBasicInfoPool.setBomEcoRelatedEcrNo("");
				BOMBasicInfoPool.setBomEcoTitle(ecoHash.get("title") == null ? "" : ((String) ecoHash.get("title")).trim());
				BOMBasicInfoPool.setPublicCheckOutStatus(header.get("publicCheckOutStatus") == null ? "" : ((String) header.get("publicCheckOutStatus")).trim());
			}

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	// 일괄 변경일 경우, ECO 관련 BOM/ECO 정보 셋팅
	private void setMultiBOMECOInfoPool(String ecoHeaderNumber, String parentItemCodes)
	{
		KETBOMHeaderQueryBean bean  = new KETBOMHeaderQueryBean();
		
		BOMECOBasicInfoPool.setPublicTransFlag("1");
		BOMECOBasicInfoPool.setBomEcoNumber(ecoHeaderNumber);
		BOMECOBasicInfoPool.setBomParentItemCodes(parentItemCodes);
		
		String strParent = "";
		String strOid = "";
		
		try {
			if ( parentItemCodes.indexOf(",") > 0 )
			{
				strParent = parentItemCodes.substring(0, parentItemCodes.indexOf(","));
				strOid = KETObjectUtil.getOidString(bean.getBOMEcoHeader(strParent, ecoHeaderNumber));
				setBOMECOInfoPool(strOid);
			}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		
	}

	private String getItemDesc(String itemCode)
	{
		DBConnectionManager res = null;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String itemDesc = "";
		try {
			res = DBConnectionManager.getInstance();
			conn = res.getConnection(appReg.getString("plm"));

			BOMSearchDao searchDao = new BOMSearchDao();
			searchDao.getItemDesc(conn, itemCode);

			itemDesc = searchDao.getResultListString();
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			if (res != null) {
				res.freeConnection(appReg.getString("plm"), conn);
			}
		}
		return itemDesc;
	}

    public Applet getParentApplet()
    {
        return applet;
    }

    //shin......
    public AbstractAIFUIApplication getApp()
    {
        return app;
    }
}
