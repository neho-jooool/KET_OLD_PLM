package e3ps.bom.command.abortendworking;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import wt.part.WTPart;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMCodePool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.KetMainJTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMECOQueryBean;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class AbortEndworkingCmd extends AbstractAIFCommand
{
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel pnl;
	Registry appReg;

    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

	String bomOid = "";
    String itemCodeStr = "";
    String wfStatus = "";
	boolean bomGubunFlag = false;

	private Hashtable reasonProdHash;
    private Hashtable reasonMoldHash;

    public AbortEndworkingCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = frame;

		appReg = Registry.getRegistry(app);

        res = null;
        conn = null;
        stmt = null;
        rs = null;

        bomOid = BOMBasicInfoPool.getPublicBOMOid() == null ? "" : BOMBasicInfoPool.getPublicBOMOid().trim();
		wfStatus = BOMBasicInfoPool.getPublicBOMStatus() == null ? "" : BOMBasicInfoPool.getPublicBOMStatus().trim();
		itemCodeStr = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();
		bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false;

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
    }

    protected void executeCommand() throws Exception
    {
		if(itemCodeStr.equalsIgnoreCase("Empty"))
		{
			MessageBox mbox = new MessageBox(parent, messageRegistry.getString("openBOMWorkspace"), "Warning", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		}
		else
		{
			if( wfStatus.equalsIgnoreCase("INWORK") || wfStatus.equalsIgnoreCase("REWORK") )
			{
				if(isAllEndWorking() && !(wfStatus.equalsIgnoreCase("REWORK")))
				{
					MessageBox mbox = new MessageBox(parent, messageRegistry.getString("endWorkingStatus"), "Warning", 0);
					mbox.setModal(true);
					mbox.setVisible(true);
					return;
				}

				if (isEndWorking())
				{
					int n = JOptionPane.showConfirmDialog(parent, messageRegistry.getString("pressAbortEndworking"), "Confirm", JOptionPane.YES_NO_OPTION);
					if(n==JOptionPane.YES_OPTION)
					{
						abortEndworking();
					}
				}
				else
				{
					MessageBox mbox = new MessageBox(parent, messageRegistry.getString("notEndWorkingStatus"), "Warning", 0);
					mbox.setModal(true);
					mbox.setVisible(true);
					return;
				}
			}
			else if(wfStatus.equalsIgnoreCase("UNDERREVIEW"))
			{
				MessageBox mbox = new MessageBox(parent, messageRegistry.getString("underApproveStatus"), "Warning", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
				return;
			}
			else if(wfStatus.equalsIgnoreCase("APPROVED"))
			{
				MessageBox mbox = new MessageBox(parent, messageRegistry.getString("completedStatus"), "Warning", 0);
				mbox.setModal(true);
				mbox.setVisible(true);
				return;
			}
		}
    }

    private boolean isEndWorking()
    {
        boolean isEnd = false;
        String status = "";
        String SQL = "";

        try
        {
            connectDB(appReg.getString("plm"));

			if(bomGubunFlag)
			{
				SQL =  "SELECT endWorkingFlag status																";
				SQL += " FROM ketbomcoworker																	";
				SQL += " WHERE newbomcode = '" + itemCodeStr + "'											";
				SQL += " AND coworkerid='" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'		";
			}
			else
			{
				SQL =  "SELECT endWorkingFlag status																							";
				SQL += " FROM ketbomecocoworker																							";
				SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'			";
				SQL += " AND ecoitemcode='" + itemCodeStr + "'																			";
				SQL += " AND coworkerid='" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'									";
			}

            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                status = rs.getString("status");
                if(status.equalsIgnoreCase("4"))
				{
                    isEnd = true;
				}
            }
            closeDB(appReg.getString("plm"));
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        return isEnd;
    }

    private boolean isAllEndWorking()
    {
        boolean isAllEndWorking = false;
        String status = "";
        String SQL = "";

        try
        {
            connectDB(appReg.getString("plm"));

			if(bomGubunFlag)
			{
				SQL =  "SELECT endWorkingFlag status								";
				SQL += " FROM ketbomcoworker									";
				SQL += " WHERE newbomcode = '" + itemCodeStr + "'			";
				SQL += " AND endWorkingFlag <> '4'								";
			}
			else
			{
				SQL =  "SELECT endWorkingFlag status																							";
				SQL += " FROM ketbomecocoworker																							";
				SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'			";
				SQL += " AND ecoitemcode='" + itemCodeStr + "'																			";
				SQL += " AND endWorkingFlag <> '4'																							";
			}

            rs = stmt.executeQuery(SQL);

            if(rs.next())
            {
				isAllEndWorking = false;
            }
			else
			{
				isAllEndWorking = true;
			}
			closeDB(appReg.getString("plm"));
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
			throw e;
        }
        finally
        {
			return isAllEndWorking;
        }
    }

    private void abortEndworking()
    {
        String SQL = "";
		String resultStr = "";
        try
        {
			connectDB(appReg.getString("plm"));

			if(bomGubunFlag)
			{
				SQL =  "UPDATE ketbomcoworker																		";
				SQL += " SET endWorkingFlag = '2'																		";
				SQL += " WHERE newbomcode = '" + itemCodeStr +	 "'											";
				SQL += " AND coworkerid='" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'			";
			}
			else
			{
				SQL =  "UPDATE ketbomecocoworker																								";
				SQL += " SET endWorkingFlag = '2'																									";
				SQL += " WHERE ecoheadernumber = '" + Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()) + "'				";
				SQL += " AND ecoitemcode='" + itemCodeStr + "'																			";
				SQL += " AND coworkerid='" + Utility.checkNVL(BOMBasicInfoPool.getUserId()) + "'										";
			}

			stmt.executeUpdate(SQL);

			conn.commit();
			closeDB(appReg.getString("plm"));

			BOMBasicInfoPool.setValidationForEnd("");
			BOMBasicInfoPool.setBomValidationResult(false);
			BOMBasicInfoPool.setHasErrorInValidation(false);
			BOMBasicInfoPool.setPublicMyStatus("2");
			pnl.setMyStatus(2);
			pnl.setCheckStatus(1);
			pnl.publicStatusPanel.setStatusBar();
			pnl.setMenuBarEnabled();

			viewBOMBtn_process();

			MessageBox mbox = new MessageBox(parent, messageRegistry.getString("successAbortEndworking"), "Information", 1);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void connectDB(String dbname) throws ConnectException
    {
        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(dbname);

            conn.setAutoCommit(false);
            stmt = conn.createStatement();
        }
        catch(Exception e)
        {
            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("dbConnectionError"), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
    }

    private void closeDB(String dbname) throws ConnectException
    {
        try
        {
            if(rs != null)
                rs.close();
            stmt.close();
        }
        catch(Exception e)
        {
            MessageBox mbox = new MessageBox(parent, messageRegistry.getString("dbCloseError"), "Error", 0);
            mbox.setModal(true);
            mbox.setVisible(true);
        }
        finally
        {
            if(res != null)
            {
                res.freeConnection(dbname, conn);
            }
        }
    }

    //shin....add...... 작업재계후 자동 체크아웃 시키고 화면 다시 뿌려줌.
    private void viewBOMBtn_process() 	{
		try {
			KetMainJTreeTable km = new KetMainJTreeTable();
			BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

			BOMTreeTableModel model = (BOMTreeTableModel)pnl.getTreeTableModel();
			BOMTreeNode rootNode = (BOMTreeNode)model.getRootNode();

			KETBOMHeaderQueryBean kq = new KETBOMHeaderQueryBean();
			String bomOid = KETObjectUtil.getOidString(kq.getBOMHeader(String.valueOf(rootNode)));

			pnl.clearBOMList();
			pnl.repaint();

			if (bomGubunFlag) {			// 신규
				setBOMInfoPool(bomOid);
			} else {							// 변경
				setBOMECOInfoPool(bomOid);
			}

			// root node 가 제품인지 금형인지 구분하여 Main Editor 컬럼정보를 셋팅함
			String strRootModelName = BOMBasicInfoPool.getPublicModelName();
			WTPart part = KETPartHelper.service.getPart(strRootModelName);
			String strType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType);

			if (PartUtil.isProductType(strType)) {	// 제품인 경우
				km.setGenMain(app);
			} else {												// 금형인 경우
				km.setMoldMain(app);
			}

			Vector vecOpenPublicBOM = new Vector();
			vecOpenPublicBOM.removeAllElements();

			MyBOMQry qry = new MyBOMQry();
			if (bomGubunFlag) {
				vecOpenPublicBOM = qry.getBOMOpen(BOMBasicInfoPool.getPublicModelName());
			} else {
				vecOpenPublicBOM = qry.getBOMEcoOpen(BOMECOBasicInfoPool.getBomEcoNumber(), strRootModelName);
			}

			Vector version = new Vector();
			Vector itemVec = new Vector();
			Hashtable inputParams = new Hashtable();

			BOMAssyComponent rootComponent = new BOMAssyComponent(BOMBasicInfoPool.getPublicModelName(), true);
			rootComponent.setLevelInt(new Integer(0));
			rootComponent.setSeqInt(new Integer(0));
			rootComponent.setItemSeqInt(new Integer(10));
			rootComponent.setOpSeqInt(new Integer(1));
			rootComponent.setSortOrderStr("0001");
			rootComponent.setNewFlagStr( PartSpecGetter.getPartSpec(part, PartSpecEnum.SpBOMFlag));
			rootComponent.setDescStr(BOMBasicInfoPool.getPublicModelDesc());			// 부품명
			rootComponent.setUomStr(BOMBasicInfoPool.getPublicModelUom());			// 기본단위
			rootComponent.setUitStr(BOMBasicInfoPool.getPublicModelUom());				// 기본단위
			rootComponent.setStatusStr(BOMBasicInfoPool.getPublicModelStatus());		// 결재상태 영문
			rootComponent.setStatusKrStr(BOMBasicInfoPool.getPublicBOMStatusKr());	//결재상태 한글
			// Added by MJOH, 2011-04-07
			rootComponent.setIBAPartType( strType );

//			Vector checkOutVec = new Vector();
//			Vector coworkerVec = new Vector();
//
//			checkOutVec.addElement(BOMBasicInfoPool.getPublicModelName());
//			coworkerVec = KETBomHelper.service.getCheckOuter(checkOutVec);
//
//			if (coworkerVec != null && coworkerVec.size() > 0) {
//				if (BOMBasicInfoPool.getPublicModelName().trim().equals(coworkerVec.elementAt(0).toString().substring(0, coworkerVec.elementAt(0).toString().indexOf("|")))) {
//					rootComponent.setCheckOutStr(coworkerVec.elementAt(0).toString().substring(coworkerVec.elementAt(0).toString().indexOf("|")+1));
//				}
//			}

			// 해당 모부품을 체크아웃한 사용자가 있는지 확인 (있는경우 RootNode 에 셋팅함)
			Hashtable coworker = checkoutDao.getCheckOuterInfo(BOMBasicInfoPool.getPublicModelName());
			if ( coworker != null && coworker.size() > 0 ) {
				if ( BOMBasicInfoPool.getPublicModelName().trim().equals(coworker.get("itemcode")) ) {
					rootComponent.setCheckOutStr( coworker.get("user_name") + "(" + coworker.get("user_email") + ")" );
				}
			}

			itemVec.addElement(BOMBasicInfoPool.getPublicModelName());
			inputParams.put("orgCode", BOMBasicInfoPool.getOrgCode());
			inputParams.put("itemCode", itemVec);

			version = KETBomHelper.service.getItemVersion(inputParams);
			String versionStr = "";

			for (int k=0; k<version.size(); k++) {
				if (version.size() > 0) {
					versionStr = version.elementAt(k) == null ? "" : version.elementAt(k).toString();
					if (BOMBasicInfoPool.getPublicModelName().equals(versionStr.substring(0, versionStr.indexOf("@")))) {
						rootComponent.setVersionStr(versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf(".")));
					}
				}
			}

			BOMBasicInfoPool.setCoWorkerVec(qry.getCoworkerData(BOMBasicInfoPool.getPublicModelName().trim()));
			BOMBasicInfoPool.setPublicMyStatus(qry.getMyStatus(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId()));
			BOMBasicInfoPool.setOrgCode(BOMBasicInfoPool.getOrgCode());
			BOMBasicInfoPool.setOrgName(BOMBasicInfoPool.getOrgName());

			pnl.openBOMData(rootComponent, vecOpenPublicBOM);

			if (bomGubunFlag) {
				// 체크아웃할 부품 정보 담기 (신규 부품인 경우에만 넣는다) ::  BOM 상태가 INWORK, REWORK 인 경우에만 담는다
				Object obj = null;
				BOMAssyComponent component = null;
				String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();
				if ( !bomStatus.equals("") && (bomStatus.equals("INWORK") || bomStatus.equals("REWORK")) ) {
					for (int inx = 0; inx < vecOpenPublicBOM.size(); inx++) {
						obj = (Object) vecOpenPublicBOM.get(inx);
						if (obj instanceof BOMAssyComponent) {
							component = (BOMAssyComponent) obj;
							if (!component.getNewFlagStr().equals("") && component.getNewFlagStr().equals("NEW")) {
								itemVec.add(component.getItemCodeStr());
							}
						}
					}
				}
Kogger.debug(getClass(), "@@@@@ checkOutItemVec : " + itemVec);
			}

			// 로그인한 사용자가 작업자(공동작업자 포함)이거나 작업상태가 4(작업완료) 가 아닌 경우에만 자동으로 Check-Out 처리 함
			String strStatus = "";
			BOMSearchUtilDao utilDao = new  BOMSearchUtilDao();
			strStatus = utilDao.getEndWorkingFlagNew(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
			String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();

			if ( (strStatus != null && !strStatus.equals("") && !strStatus.equals("4")) && (bomStatus.equals("INWORK") || bomStatus.equals("REWORK")) ) {

				//shin...체크아웃 상태로 만듬............................................................................................................................................................................
//				rootComponent.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
				CheckOutCmd coc = new CheckOutCmd(app.getDesktop(), app);
				BOMTreeNode[] nodes = pnl.getSelectedTreeNode();
				coc.checkOut( nodes, itemVec, BOMBasicInfoPool.getUserName(), true, versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("(")) );
//				checkoutDao.setCheckOut( BOMBasicInfoPool.getPublicModelName(), versionStr.substring(versionStr.indexOf("@")+1, versionStr.indexOf("(")), BOMBasicInfoPool.getUserId() );
				// .................................................................................................................................................................................................................................
			}
	        pnl.requestFocus();
		} catch(Exception e) {
		    Kogger.error(getClass(), e);
		}
	}

	private void setBOMInfoPool(String oid) {

		try {
			Hashtable hasHeader = new Hashtable();
			hasHeader = KETBomHelper.service.getBom(bomOid);

			BOMBasicInfoPool.setPublicBOMStatus((String)hasHeader.get("lifeCycleState"));
			BOMBasicInfoPool.setPublicBOMStatusKr((String)hasHeader.get("lifeCycleStateKr"));
			BOMBasicInfoPool.setPublicApproveDate((String)hasHeader.get("publicApproveDate"));
			BOMBasicInfoPool.setPublicApproveDept((String)hasHeader.get("publicApproveDept"));
			BOMBasicInfoPool.setPublicApproveId((String)hasHeader.get("publicApproveID"));
			BOMBasicInfoPool.setPublicApproveName((String)hasHeader.get("publicApproveName"));
			BOMBasicInfoPool.setPublicBasicModelName((String)hasHeader.get("publicBasicModelName"));
			BOMBasicInfoPool.setPublicBasicModelDesc((String)hasHeader.get("publicBasicModelDesc"));
			BOMBasicInfoPool.setPublicOwnerDate((String)hasHeader.get("publicOwnerDate"));
			BOMBasicInfoPool.setPublicOwnerDept((String)hasHeader.get("publicOwnerDept"));
			BOMBasicInfoPool.setPublicOwnerId((String)hasHeader.get("publicOwnerID"));
			BOMBasicInfoPool.setPublicOwnerName((String)hasHeader.get("publicOwnerName"));
			BOMBasicInfoPool.setPublicOwnerEmail((String)hasHeader.get("publicOwnerEmail"));
			BOMBasicInfoPool.setPublicModelName((String)hasHeader.get("publicModelName"));
			BOMBasicInfoPool.setPublicModelDesc((String)hasHeader.get("publicModelDesc"));
			BOMBasicInfoPool.setPublicModelUom((String)hasHeader.get("publicModelUom"));
//			BOMBasicInfoPool.setPublicModelUserItemType((String)hasHeader.get("publicModelUserItemType"));
			BOMBasicInfoPool.setPublicModelStatus((String)hasHeader.get("publicModelStatus"));
			BOMBasicInfoPool.setPublicTransFlag((String)hasHeader.get("publicTransFlag"));
			BOMBasicInfoPool.setPublicCheckOutStatus((String)hasHeader.get("publicCheckOutStatus"));
			BOMBasicInfoPool.setPublicBOMOid(bomOid);
			BOMBasicInfoPool.setUserRole("Owner");
			BOMBasicInfoPool.setBomEcoType("");
			BOMBasicInfoPool.setBomEcoNumber("");
			BOMBasicInfoPool.setBomHeaderPartType(Utility.checkNVL((String)hasHeader.get("bomHeaderType")));
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

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
Kogger.debug(getClass(), "@@@@@@@@@@@ header : " + header);
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

}
