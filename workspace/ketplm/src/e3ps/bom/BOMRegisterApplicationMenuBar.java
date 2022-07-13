package e3ps.bom;

import java.util.Enumeration;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AIFUIApplicationMenuBar;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.common.util.KETObjectUtil;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

/**
 * BOM 등록 메뉴바 구성을 위한 클래스
 */
public class BOMRegisterApplicationMenuBar extends AIFUIApplicationMenuBar
{
	private static final long serialVersionUID = 1L;

	boolean isViewable = false;
	boolean bomGubunFlag = (BOMECOBasicInfoPool.getECONo() == null ? "" : BOMECOBasicInfoPool.getECONo().trim()).equals("") ? true : false;

	// System Menu
	JMenu newBomMenu;
    JMenuItem excelTempDownMenu;
	JMenuItem loadExcelBomMenu;
	JMenuItem generalBomMenu;
	JMenuItem myBomMenu;
	JMenu manageBomMenu;

	JMenuItem abortEndworkingMenu;
	JMenuItem endWorkingBomMenu;
	JMenuItem confirmBomMenu;

	JMenuItem manageCoWorkerMenu;
	JMenuItem bomValidationMenu;
	JMenuItem saveExcelMenu;
	JMenuItem clearMenu;
	JMenuItem exitMenu;

	// Edit Menu
	//JMenuItem checkInMenu;
	//JMenuItem checkOutMenu;
	//JMenuItem cancelCheckOutMenu;
	JMenuItem cutMenu;
	JMenuItem clipBoardMenu;
	JMenuItem pasteMenu;
	JMenu updateBomMenu;
	JMenuItem addMenu;
	JMenuItem replaceMenu;
	JMenuItem copyChildMenu;
	JMenuItem removeMenu;
	JMenuItem bomPropertyMenu;
	JMenuItem bomPropertyInsertMenu;

	// View Menu
	JMenuItem bomEcoDetailsMenu;
	JMenuItem documentDetailsMenu;
	JMenuItem drawingDetailsMenu;
	JMenuItem pProjectDetailsMenu;
	JMenuItem mProjectDetailsMenu;
	JMenuItem wfHistoryDetailsMenu;
	JMenuItem compareBomMenu;

	// Search Menu
	JMenuItem searcItemMenu;
	JMenuItem findItemMenu;
	JMenu searchBomMenu;
	JMenuItem downwardExplosionMenu;
	JMenuItem upwardExplosionMenu;
	JMenuItem searchAppliedProductMenu;

	//전송 Menu
	JMenuItem erptransMenu;

    // 마우스 오른쪽 버튼 클릭시 나타나는 Pop-up Menu
	public JMenuItem checkInPopupMenu;
    public JMenuItem checkOutPopupMenu;
    public JMenuItem cancelCheckOutPopupMenu;
    public JMenuItem cutPopupMenu;
    public JMenuItem pastePopupMenu;
    public JMenu updateBomPopupMenu;
    public JMenuItem addPopupMenu;
	public JMenuItem replacePopupMenu;
    public JMenuItem copyChildPopupMenu;
    public JMenuItem removePopupMenu;
	public JMenuItem bomPropertyPopupMenu;
	public JMenuItem bomPropertyInsertPopupMenu;
	public JMenuItem bomEcoDetailsPopupMenu;
	public JMenuItem documentDetailsPopupMenu;
	public JMenuItem drawingDetailsPopupMenu;
	public JMenuItem pProjectDetailsPopupMenu;
	public JMenuItem mProjectDetailsPopupMenu;
	public JMenuItem searchAppliedProductPopupMenu;

	private AbstractAIFUIApplication app = null;
	BOMTreeNode rootNode;

    public BOMRegisterApplicationMenuBar(AbstractAIFUIApplication abstractaifuiapplication)
    {
		super(abstractaifuiapplication);

		this.app = abstractaifuiapplication;

		addSystemMenu();
		addEditMenu();
		addViewMenu();
		addSearchMenu();


//		boolean reSult = false;
		boolean reSult = true;
//		try{
//			Vector vecHeaders = new Vector();
//			Vector vecItemCode = KETBomHelper.service.searchWorkList();
//
//			//bom일때
//			KETBomHeader header = null;
//			KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
//			vecHeaders = query.getAllBomHeaders(BOMBasicInfoPool.getOrgCode(), vecItemCode, BOMBasicInfoPool.getUserId());
//			String transFlag = "";
//			if (vecHeaders.size() > 0) {
//				for(int i=0; i<vecHeaders.size(); i++)
//				{
//					header = (KETBomHeader) vecHeaders.elementAt(i);
//					transFlag = StringUtil.checkNull(header.getTransferFlag());
////Kogger.debug(getClass(), "################################### itemcode : "+header.getNewBOMCode());
////Kogger.debug(getClass(), "################################### transFlag : "+transFlag);
//					if(transFlag.equals("2") || transFlag.equals("3"))
//					{
//						reSult = true;
//					}
//				}
//			}
//
//			//bomeco 일때.
//			KETBomEcoHeader ecoheader = null;
//			KETBOMECOQueryBean ecoquery = new KETBOMECOQueryBean();
//			vecHeaders = ecoquery.getAllBomEcoHeaders(BOMBasicInfoPool.getOrgCode(), vecItemCode, BOMBasicInfoPool.getUserId());
//			String ecotransFlag = "";
//			if (vecHeaders.size() > 0) {
//				for(int i=0; i<vecHeaders.size(); i++)
//				{
//					ecoheader = (KETBomEcoHeader) vecHeaders.elementAt(i);
//					ecotransFlag = StringUtil.checkNull(ecoheader.getTransferFlag());
////Kogger.debug(getClass(), "################################### itemcode : "+ecoheader.getEcoItemCode());
////Kogger.debug(getClass(), "################################### transFlag : "+transFlag);
//					if(ecotransFlag.equals("2") || ecotransFlag.equals("3"))
//					{
//						reSult = true;
//					}
//				}
//			}
//
//		}catch(Exception e){
//			e.getStackTrace();
//		}
//		Kogger.debug(getClass(), "############################@@@@ reSult : "+reSult);

		if(BOMBasicInfoPool.isAdmin() || reSult) {
			isViewable = true;
			addTransMenu();
		}

        // 우측마우스 Pop-up Menu 를 생성하기 위해서 임시로 생성해둔 메뉴바.
        addTmpMenu();

    }

	private void addSystemMenu()
	{
	    JMenu systemMenu = new JMenu(registry.getString("SystemMenu.NAME"));
		JMenu newBomMenu = new JMenu("     " + registry.getString("NewBOM.NAME"));
		systemMenu.add(newBomMenu);
		generalBomMenu = addMenuItem(newBomMenu, "generalbom");
		excelTempDownMenu = addMenuItem(newBomMenu, "exceltempdown");
		loadExcelBomMenu = addMenuItem(newBomMenu, "loadexcelbom");
		myBomMenu = addMenuItem(systemMenu, "mybom");
		JMenu manageBomMenu = new JMenu("     " + registry.getString("ManageBOM.NAME"));
		systemMenu.add(manageBomMenu);

		abortEndworkingMenu = addMenuItem(manageBomMenu, "abortendworking");
		endWorkingBomMenu = addMenuItem(manageBomMenu, "endworking");
		confirmBomMenu = addMenuItem(manageBomMenu, "confirmbom");

		manageCoWorkerMenu = addMenuItem(systemMenu, "managecoworker");

		systemMenu.addSeparator();
		saveExcelMenu = addMenuItem(systemMenu, "saveexcel");
		systemMenu.addSeparator();
		bomValidationMenu = addMenuItem(systemMenu, "bomvalidation");

		systemMenu.addSeparator();
		clearMenu = addMenuItem(systemMenu, "clearbom");
		exitMenu = addMenuItem(systemMenu, "exitbom");

		add(systemMenu);
	}

	private void addTransMenu()
	{
		JMenu transMenu = new JMenu(registry.getString("TransMenu.NAME"));
		erptransMenu = addMenuItem(transMenu, "erptrans");
		add(transMenu);
	}

	private void addEditMenu()
	{
	    JMenu editMenu = new JMenu(registry.getString("EditMenu.NAME"));
		//checkInMenu = addMenuItem(editMenu, "checkin");
		//checkOutMenu = addMenuItem(editMenu, "checkout");
		//cancelCheckOutMenu = addMenuItem(editMenu, "cancelcheckout");
		//editMenu.addSeparator();
		cutMenu = addMenuItem(editMenu, "cut");
		clipBoardMenu = addMenuItem(editMenu, "clipboard");
		pasteMenu = addMenuItem(editMenu, "paste");
		JMenu updateBomMenu = new JMenu("     " + registry.getString("UpdateBOM.NAME"));
		editMenu.add(updateBomMenu);
		addMenu = addMenuItem(updateBomMenu, "add");
		replaceMenu = addMenuItem(updateBomMenu, "replace");
		copyChildMenu = addMenuItem(updateBomMenu, "copychild");
		removeMenu = addMenuItem(updateBomMenu, "remove");
		editMenu.addSeparator();
		bomPropertyMenu = addMenuItem(editMenu, "bomproperty");
		bomPropertyInsertMenu = addMenuItem(editMenu, "bompropertyinsert");

		add(editMenu);
	}

	private void addViewMenu()
	{
	    JMenu viewMenu = new JMenu(registry.getString("ViewMenu.NAME"));
	    drawingDetailsMenu = addMenuItem(viewMenu, "drawingdetails");
		documentDetailsMenu = addMenuItem(viewMenu, "documentdetails");
		bomEcoDetailsMenu = addMenuItem(viewMenu, "bomecodetails");
		pProjectDetailsMenu = addMenuItem(viewMenu, "pprojectdetails");
		mProjectDetailsMenu = addMenuItem(viewMenu, "mprojectdetails");
		wfHistoryDetailsMenu = addMenuItem(viewMenu, "wfhistorydetails");
		viewMenu.addSeparator();
		compareBomMenu = addMenuItem(viewMenu, "comparebom");

		add(viewMenu);
	}

	private void addSearchMenu()
	{
	    JMenu searchMenu = new JMenu(registry.getString("SearchMenu.NAME"));
		searcItemMenu = addMenuItem(searchMenu, "searchitem");
		findItemMenu = addMenuItem(searchMenu, "finditem");
		JMenu searchBomMenu = new JMenu("     " + registry.getString("SearchBOM.NAME"));
		searchMenu.add(searchBomMenu);
		downwardExplosionMenu = addMenuItem(searchBomMenu, "downwardexplosion");
		upwardExplosionMenu = addMenuItem(searchBomMenu, "upwardexplosion");
		searchAppliedProductMenu = addMenuItem(searchBomMenu, "searchappliedproduct");

		add(searchMenu);
	}

	private void addTmpMenu()
	{
        JMenu tmpMenu = new JMenu("TEMP");
		try {
			if (KETObjectUtil.isAdmin()) {
				checkOutPopupMenu = addMenuItem(tmpMenu, "checkout");
				checkInPopupMenu = addMenuItem(tmpMenu, "checkin");
				cancelCheckOutPopupMenu = addMenuItem(tmpMenu, "cancelcheckout");
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		cutPopupMenu = addMenuItem(tmpMenu, "cut");
		pastePopupMenu = addMenuItem(tmpMenu, "paste");
		addPopupMenu = addMenuItem(tmpMenu, "add");
		replacePopupMenu = addMenuItem(tmpMenu, "replace");
		copyChildPopupMenu = addMenuItem(tmpMenu, "copychild");
		removePopupMenu = addMenuItem(tmpMenu, "remove");
		bomPropertyPopupMenu = addMenuItem(tmpMenu, "bomproperty");
		bomPropertyInsertPopupMenu = addMenuItem(tmpMenu, "bompropertyinsert");
		bomEcoDetailsPopupMenu = addMenuItem(tmpMenu, "bomecodetails");
		documentDetailsPopupMenu = addMenuItem(tmpMenu, "documentdetails");
		drawingDetailsPopupMenu = addMenuItem(tmpMenu, "drawingdetails");
		pProjectDetailsPopupMenu = addMenuItem(tmpMenu, "pprojectdetails");
		mProjectDetailsPopupMenu = addMenuItem(tmpMenu, "mprojectdetails");

		searchAppliedProductPopupMenu = addMenuItem(tmpMenu, "searchappliedproduct");
    }


    //////////////////////////////////////////////////////////////////////////
    // BOM 모듈이 처음 로딩될 때, 메뉴를 각각 Disable 시켜준다.
    public void setInitMenu()
	{
        // System Menu
		excelTempDownMenu.setEnabled(true);
		loadExcelBomMenu.setEnabled(true);
		generalBomMenu.setEnabled(true);
		myBomMenu.setEnabled(true);
        //abortEndworkingMenu.setEnabled(false);
        //endWorkingBomMenu.setEnabled(false);
		confirmBomMenu.setEnabled(false);
		//manageCoWorkerMenu.setEnabled(false);
		bomValidationMenu.setEnabled(false);
		//saveExcelMenu.setEnabled(false);
		clearMenu.setEnabled(true);
		exitMenu.setEnabled(true);

        // Edit Menu
		//checkInMenu.setEnabled(false);
		//checkOutMenu.setEnabled(false);
		//cancelCheckOutMenu.setEnabled(false);
		cutMenu.setEnabled(false);
		clipBoardMenu.setEnabled(true);
		pasteMenu.setEnabled(false);
		addMenu.setEnabled(false);
		replaceMenu.setEnabled(false);
		copyChildMenu.setEnabled(false);
		removeMenu.setEnabled(false);
		bomPropertyMenu.setEnabled(false);
		bomPropertyInsertMenu.setEnabled(false);

		// View Menu
		bomEcoDetailsMenu.setEnabled(false);
		documentDetailsMenu.setEnabled(false);
		drawingDetailsMenu.setEnabled(false);
		pProjectDetailsMenu.setEnabled(false);
		mProjectDetailsMenu.setEnabled(false);
		wfHistoryDetailsMenu.setEnabled(false);
		compareBomMenu.setEnabled(false);

		// Search Menu
		searcItemMenu.setEnabled(true);
		findItemMenu.setEnabled(false);
		downwardExplosionMenu.setEnabled(true);
		upwardExplosionMenu.setEnabled(true);
		searchAppliedProductMenu.setEnabled(true);

		// 우측마우스 Pop-up Menu
		try {
			if (KETObjectUtil.isAdmin()) {
				checkInPopupMenu.setEnabled(false);
				checkOutPopupMenu.setEnabled(false);
				cancelCheckOutPopupMenu.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		cutPopupMenu.setEnabled(false);
		pastePopupMenu.setEnabled(false);
		addPopupMenu.setEnabled(false);
		replacePopupMenu.setEnabled(false);
		copyChildPopupMenu.setEnabled(false);
		removePopupMenu.setEnabled(false);
		bomPropertyPopupMenu.setEnabled(false);
		bomPropertyInsertPopupMenu.setEnabled(false);
		bomEcoDetailsPopupMenu.setEnabled(false);
		documentDetailsPopupMenu.setEnabled(false);
		drawingDetailsPopupMenu.setEnabled(false);
		pProjectDetailsPopupMenu.setEnabled(false);
		mProjectDetailsPopupMenu.setEnabled(false);
		searchAppliedProductPopupMenu.setEnabled(false);

		//재전송 메뉴
		if (isViewable)
			erptransMenu.setEnabled(true);
	}

    // 단순 조회용..
    public void setPublicViewMenu(int userStatus)
	{
		// System Menu
		excelTempDownMenu.setEnabled(true);
		loadExcelBomMenu.setEnabled(true);
		generalBomMenu.setEnabled(true);
		myBomMenu.setEnabled(true);

        //abortEndworkingMenu.setEnabled(false);
        //endWorkingBomMenu.setEnabled(false);
		confirmBomMenu.setEnabled(false);

		//manageCoWorkerMenu.setEnabled(false);
		bomValidationMenu.setEnabled(true);
		//saveExcelMenu.setEnabled(true);
		clearMenu.setEnabled(true);
		exitMenu.setEnabled(true);

        // Edit Menu
		//checkInMenu.setEnabled(false);
		//checkOutMenu.setEnabled(false);
		//cancelCheckOutMenu.setEnabled(false);
		cutMenu.setEnabled(false);
		clipBoardMenu.setEnabled(true);
		pasteMenu.setEnabled(false);
		addMenu.setEnabled(false);
		replaceMenu.setEnabled(false);
		copyChildMenu.setEnabled(false);
		removeMenu.setEnabled(false);
		bomPropertyMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴) :: 제품이거나, 체크인상태일 때 사용불가
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertMenu.setEnabled(false);
		} else {
			bomPropertyInsertMenu.setEnabled(true);
		}

		// View Menu
		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsMenu.setEnabled(false);
		} else {
			bomEcoDetailsMenu.setEnabled(true);
		}
		documentDetailsMenu.setEnabled(true);
		drawingDetailsMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsMenu.setEnabled(true);
			mProjectDetailsMenu.setEnabled(false);
		} else {
			pProjectDetailsMenu.setEnabled(false);
			mProjectDetailsMenu.setEnabled(true);
		}
		wfHistoryDetailsMenu.setEnabled(true);
		compareBomMenu.setEnabled(true);

		// Search Menu
		searcItemMenu.setEnabled(true);
		findItemMenu.setEnabled(true);
		downwardExplosionMenu.setEnabled(true);
		upwardExplosionMenu.setEnabled(true);
		searchAppliedProductMenu.setEnabled(true);

		// PopUp Menu
		try {
			if (KETObjectUtil.isAdmin()) {
				checkOutPopupMenu.setEnabled(false);
				checkInPopupMenu.setEnabled(false);
				cancelCheckOutPopupMenu.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		cutPopupMenu.setEnabled(false);
		pastePopupMenu.setEnabled(false);
		addPopupMenu.setEnabled(false);
		replacePopupMenu.setEnabled(false);
		copyChildPopupMenu.setEnabled(false);
		removePopupMenu.setEnabled(false);
		bomPropertyPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertPopupMenu.setEnabled(false);
		} else {
			bomPropertyInsertPopupMenu.setEnabled(true);
		}

		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsPopupMenu.setEnabled(false);
		} else {
			bomEcoDetailsPopupMenu.setEnabled(true);
		}
		documentDetailsPopupMenu.setEnabled(true);
		drawingDetailsPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsPopupMenu.setEnabled(true);
			mProjectDetailsPopupMenu.setEnabled(false);
		} else {
			pProjectDetailsPopupMenu.setEnabled(false);
			mProjectDetailsPopupMenu.setEnabled(true);
		}
		searchAppliedProductPopupMenu.setEnabled(true);

		//재전송 메뉴
		if (isViewable)
			erptransMenu.setEnabled(true);
    }

    // 공동작업자이지만 Editing 이 불가능한 경우.
    public void setPublicCoworkerViewMenu()
	{
		// System Menu
		excelTempDownMenu.setEnabled(true);
		loadExcelBomMenu.setEnabled(true);
		generalBomMenu.setEnabled(true);
		myBomMenu.setEnabled(true);

        //abortEndworkingMenu.setEnabled(false);
        //endWorkingBomMenu.setEnabled(true);
//Kogger.debug(getClass(), "@@@@ setPublicCoworkerViewMenu : " +bomGubunFlag );
		if (bomGubunFlag) {		// 신규
			confirmBomMenu.setEnabled(true);
		} else {						// 변경
			confirmBomMenu.setEnabled(false);
		}

		//manageCoWorkerMenu.setEnabled(false);
		bomValidationMenu.setEnabled(true);
		//saveExcelMenu.setEnabled(true);
		clearMenu.setEnabled(true);
		exitMenu.setEnabled(true);

        setCheckInOutMenu();

        // Edit Menu
		cutMenu.setEnabled(false);
		clipBoardMenu.setEnabled(true);
		pasteMenu.setEnabled(false);
		addMenu.setEnabled(false);
		replaceMenu.setEnabled(false);
		copyChildMenu.setEnabled(false);
		removeMenu.setEnabled(false);
		bomPropertyMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertMenu.setEnabled(false);
		} else {
			bomPropertyInsertMenu.setEnabled(true);
		}

		// View Menu
		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsMenu.setEnabled(false);
		} else {
			bomEcoDetailsMenu.setEnabled(true);
		}
		documentDetailsMenu.setEnabled(true);
		drawingDetailsMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsMenu.setEnabled(true);
			mProjectDetailsMenu.setEnabled(false);
		} else {
			pProjectDetailsMenu.setEnabled(false);
			mProjectDetailsMenu.setEnabled(true);
		}
		wfHistoryDetailsMenu.setEnabled(true);
		compareBomMenu.setEnabled(true);

		// Search Menu
		searcItemMenu.setEnabled(true);
		findItemMenu.setEnabled(true);
		downwardExplosionMenu.setEnabled(true);
		upwardExplosionMenu.setEnabled(true);
		searchAppliedProductMenu.setEnabled(true);

		// 우측마우스 Pop-up Menu
		cutPopupMenu.setEnabled(false);
		pastePopupMenu.setEnabled(false);
		addPopupMenu.setEnabled(false);
		replacePopupMenu.setEnabled(false);
		copyChildPopupMenu.setEnabled(false);
		removePopupMenu.setEnabled(false);
		bomPropertyPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertPopupMenu.setEnabled(false);
		} else {
			bomPropertyInsertPopupMenu.setEnabled(true);
		}

		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsPopupMenu.setEnabled(false);
		} else {
			bomEcoDetailsPopupMenu.setEnabled(true);
		}
		documentDetailsPopupMenu.setEnabled(true);
		drawingDetailsPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsPopupMenu.setEnabled(true);
			mProjectDetailsPopupMenu.setEnabled(false);
		} else {
			pProjectDetailsPopupMenu.setEnabled(false);
			mProjectDetailsPopupMenu.setEnabled(true);
		}
		searchAppliedProductPopupMenu.setEnabled(true);

		//재전송 메뉴
		//if (isViewable)
		//	erptransMenu.setEnabled(false);
    }

    // Check-Out 권한을 통해서 모든 기능이 모두 활성화 되는 경우.
    public void setPublicEditMenu()
	{
		// System Menu
		excelTempDownMenu.setEnabled(true);
		loadExcelBomMenu.setEnabled(true);
		generalBomMenu.setEnabled(true);
		myBomMenu.setEnabled(true);

        //abortEndworkingMenu.setEnabled(true);
        //endWorkingBomMenu.setEnabled(false);

//Kogger.debug(getClass(), "@@@@ setPublicEditMenu : " +bomGubunFlag );
		if (bomGubunFlag) {		// 신규
			confirmBomMenu.setEnabled(true);
		} else {						// 변경
			confirmBomMenu.setEnabled(false);
		}

		//manageCoWorkerMenu.setEnabled(false);
		bomValidationMenu.setEnabled(true);
		//saveExcelMenu.setEnabled(true);
		clearMenu.setEnabled(true);
		exitMenu.setEnabled(true);

        // Edit Menu
		//checkInMenu.setEnabled(true);
		//checkOutMenu.setEnabled(true);
		//cancelCheckOutMenu.setEnabled(true);
		cutMenu.setEnabled(true);
		clipBoardMenu.setEnabled(true);
		pasteMenu.setEnabled(true);
		addMenu.setEnabled(true);
		replaceMenu.setEnabled(true);
		copyChildMenu.setEnabled(true);
		removeMenu.setEnabled(true);
		bomPropertyMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertMenu.setEnabled(false);
		} else {
			bomPropertyInsertMenu.setEnabled(true);
		}

		// View Menu
		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsMenu.setEnabled(false);
		} else {
			bomEcoDetailsMenu.setEnabled(true);
		}
		documentDetailsMenu.setEnabled(true);
		drawingDetailsMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsMenu.setEnabled(true);
			mProjectDetailsMenu.setEnabled(false);
		} else {
			pProjectDetailsMenu.setEnabled(false);
			mProjectDetailsMenu.setEnabled(true);
		}
		wfHistoryDetailsMenu.setEnabled(true);
		compareBomMenu.setEnabled(true);

		// Search Menu
		searcItemMenu.setEnabled(true);
		findItemMenu.setEnabled(true);
		downwardExplosionMenu.setEnabled(true);
		upwardExplosionMenu.setEnabled(true);
		searchAppliedProductMenu.setEnabled(true);

		// 우측마우스 Pop-up Menu
		try {
			if (KETObjectUtil.isAdmin()) {
				checkOutPopupMenu.setEnabled(false);
				checkInPopupMenu.setEnabled(true);
				cancelCheckOutPopupMenu.setEnabled(true);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		cutPopupMenu.setEnabled(true);
		pastePopupMenu.setEnabled(true);
		addPopupMenu.setEnabled(true);
		replacePopupMenu.setEnabled(true);
		copyChildPopupMenu.setEnabled(true);
		removePopupMenu.setEnabled(true);
		bomPropertyPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertPopupMenu.setEnabled(false);
		} else {
			bomPropertyInsertPopupMenu.setEnabled(true);
		}

		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsPopupMenu.setEnabled(false);
		} else {
			bomEcoDetailsPopupMenu.setEnabled(true);
		}
		documentDetailsPopupMenu.setEnabled(true);
		drawingDetailsPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsPopupMenu.setEnabled(true);
			mProjectDetailsPopupMenu.setEnabled(false);
		} else {
			pProjectDetailsPopupMenu.setEnabled(false);
			mProjectDetailsPopupMenu.setEnabled(true);
		}
		searchAppliedProductPopupMenu.setEnabled(true);

		//재전송 메뉴
		// if (isViewable)
		//	erptransMenu.setEnabled(false);
    }

    // End Working 상태
    public void setPublicEndMenu()
	{
		// System Menu
		excelTempDownMenu.setEnabled(true);
		loadExcelBomMenu.setEnabled(true);
		generalBomMenu.setEnabled(true);
		myBomMenu.setEnabled(true);

        //abortEndworkingMenu.setEnabled(true);
        //endWorkingBomMenu.setEnabled(false);

//Kogger.debug(getClass(), "@@@@ setPublicEndMenu : " +bomGubunFlag );
		if (bomGubunFlag) {		// 신규
			confirmBomMenu.setEnabled(true);
		} else {						// 변경
			confirmBomMenu.setEnabled(false);
		}

		//manageCoWorkerMenu.setEnabled(false);
		bomValidationMenu.setEnabled(true);
		//saveExcelMenu.setEnabled(true);
		clearMenu.setEnabled(true);
		exitMenu.setEnabled(true);

        // Edit Menu
		//checkInMenu.setEnabled(false);
		//checkOutMenu.setEnabled(false);
		//cancelCheckOutMenu.setEnabled(false);
		cutMenu.setEnabled(false);
		clipBoardMenu.setEnabled(true);
		pasteMenu.setEnabled(false);
		addMenu.setEnabled(false);
		replaceMenu.setEnabled(false);
		copyChildMenu.setEnabled(false);
		removeMenu.setEnabled(false);
		bomPropertyMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertMenu.setEnabled(false);
		} else {
			bomPropertyInsertMenu.setEnabled(true);
		}

		// View Menu
		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsMenu.setEnabled(false);
		} else {
			bomEcoDetailsMenu.setEnabled(true);
		}
		documentDetailsMenu.setEnabled(true);
		drawingDetailsMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsMenu.setEnabled(true);
			mProjectDetailsMenu.setEnabled(false);
		} else {
			pProjectDetailsMenu.setEnabled(false);
			mProjectDetailsMenu.setEnabled(true);
		}
		wfHistoryDetailsMenu.setEnabled(true);
		compareBomMenu.setEnabled(true);

		// Search Menu
		searcItemMenu.setEnabled(true);
		findItemMenu.setEnabled(true);
		downwardExplosionMenu.setEnabled(true);
		upwardExplosionMenu.setEnabled(true);
		searchAppliedProductMenu.setEnabled(true);

		// 우측마우스 Pop-up Menu
		try {
			if (KETObjectUtil.isAdmin()) {
				checkOutPopupMenu.setEnabled(true);
				checkInPopupMenu.setEnabled(true);
				cancelCheckOutPopupMenu.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		cutPopupMenu.setEnabled(false);
		pastePopupMenu.setEnabled(false);
		addPopupMenu.setEnabled(false);
		replacePopupMenu.setEnabled(false);
		copyChildPopupMenu.setEnabled(false);
		removePopupMenu.setEnabled(false);
		bomPropertyPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertPopupMenu.setEnabled(false);
		} else {
			bomPropertyInsertPopupMenu.setEnabled(true);
		}

		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsPopupMenu.setEnabled(false);
		} else {
			bomEcoDetailsPopupMenu.setEnabled(true);
		}
		documentDetailsPopupMenu.setEnabled(true);
		drawingDetailsPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsPopupMenu.setEnabled(true);
			mProjectDetailsPopupMenu.setEnabled(false);
		} else {
			pProjectDetailsPopupMenu.setEnabled(false);
			mProjectDetailsPopupMenu.setEnabled(true);
		}
		searchAppliedProductPopupMenu.setEnabled(true);

		//재전송 메뉴
		// if (isViewable)
		//	erptransMenu.setEnabled(false);
    }

    public void setAdminMenu()
	{
//Kogger.debug(getClass(), "============= setAdminMenu ===============");
		// System Menu
		excelTempDownMenu.setEnabled(true);
		loadExcelBomMenu.setEnabled(true);
		generalBomMenu.setEnabled(true);
		myBomMenu.setEnabled(true);

        //abortEndworkingMenu.setEnabled(false);
        //endWorkingBomMenu.setEnabled(false);
		//confirmBomMenu.setEnabled(false);

		//manageCoWorkerMenu.setEnabled(false);
		bomValidationMenu.setEnabled(true);
		//saveExcelMenu.setEnabled(true);
		clearMenu.setEnabled(true);
		exitMenu.setEnabled(true);

        // Edit Menu
		//checkInMenu.setEnabled(true);
		//checkOutMenu.setEnabled(true);
		//cancelCheckOutMenu.setEnabled(true);
		cutMenu.setEnabled(true);
		clipBoardMenu.setEnabled(true);
		pasteMenu.setEnabled(true);
		addMenu.setEnabled(true);
		replaceMenu.setEnabled(true);
		copyChildMenu.setEnabled(true);
		removeMenu.setEnabled(true);
		bomPropertyMenu.setEnabled(true);

        setCheckInOutMenu();

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertMenu.setEnabled(false);
		} else {
			bomPropertyInsertMenu.setEnabled(true);
		}

		// View Menu
		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsMenu.setEnabled(false);
		} else {
			bomEcoDetailsMenu.setEnabled(true);
		}
		documentDetailsMenu.setEnabled(true);
		drawingDetailsMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsMenu.setEnabled(true);
			mProjectDetailsMenu.setEnabled(false);
		} else {
			pProjectDetailsMenu.setEnabled(false);
			mProjectDetailsMenu.setEnabled(true);
		}
		wfHistoryDetailsMenu.setEnabled(true);
		compareBomMenu.setEnabled(true);

		// Search Menu
		searcItemMenu.setEnabled(true);
		findItemMenu.setEnabled(true);
		downwardExplosionMenu.setEnabled(true);
		upwardExplosionMenu.setEnabled(true);
		searchAppliedProductMenu.setEnabled(true);

		// 마우스 우측 Pop-up Menu
		cutPopupMenu.setEnabled(true);
		pastePopupMenu.setEnabled(true);
		addPopupMenu.setEnabled(true);
		replacePopupMenu.setEnabled(true);
		copyChildPopupMenu.setEnabled(true);
		removePopupMenu.setEnabled(true);
		bomPropertyPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(신규 금형부품 추가 메뉴)
		if( PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType())) || BOMBasicInfoPool.getPublicCheckOutStatus().equals("1") ) {
			bomPropertyInsertPopupMenu.setEnabled(false);
		} else {
			bomPropertyInsertPopupMenu.setEnabled(true);
		}

		if(Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals("")) {
			bomEcoDetailsPopupMenu.setEnabled(false);
		} else {
			bomEcoDetailsPopupMenu.setEnabled(true);
		}
		documentDetailsPopupMenu.setEnabled(true);
		drawingDetailsPopupMenu.setEnabled(true);

		// 해당 BOM의 최상위 부품 타입에 따라 메뉴 Display 설정(제품/금형 Profile 조회 메뉴)
		if(PartUtil.isProductType(Utility.checkNVL(BOMBasicInfoPool.getBomHeaderPartType()))) {
			pProjectDetailsPopupMenu.setEnabled(true);
			mProjectDetailsPopupMenu.setEnabled(false);
		} else {
			pProjectDetailsPopupMenu.setEnabled(false);
			mProjectDetailsPopupMenu.setEnabled(true);
		}
		searchAppliedProductPopupMenu.setEnabled(true);

		//재전송 메뉴
		if (isViewable)
			erptransMenu.setEnabled(true);
    }

    //재전송 성공했을때 혹은 재전송이 필요한 itemcode가 아닐때.
    public void setTransSuccessMenu()
    {
    	if (isViewable)
    		erptransMenu.setEnabled(true);
    }

  //재전송이 필요한 itemcode가 로드 되었을때
    public void setTransRequestMenu()
    {
    	if (isViewable)
    		erptransMenu.setEnabled(true);
    }

	public void setCheckInOutMenu()
	{
		try	{
			BOMRegisterApplicationPanel pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();
/*
			String bomOid = BOMBasicInfoPool.getPublicBOMOid();

			if(bomOid.length() > 0)
			{
				htblStatus = LSISBOMHelper.service.getBom(bomOid);
			}
*/
			String myId = BOMBasicInfoPool.getUserId();

			String checkStatus = "1";

			if(pnl != null) {
				if ((pnl.getUserStatus() == 0 || pnl.getUserStatus() == 2) &&  pnl.getMyStatus() < 4 && pnl.getBomStatus() != null &&
				   (pnl.getBomStatus().equalsIgnoreCase("INWORK") ||
					pnl.getBomStatus().equalsIgnoreCase("REJECTED") ||
					pnl.getBomStatus().equalsIgnoreCase("UNDERREVIEW") ||
					pnl.getBomStatus().equalsIgnoreCase("APPROVED")))
				{
					BOMTreeTableModel model = (BOMTreeTableModel)pnl.getTreeTableModel();
					rootNode = (BOMTreeNode)model.getRootNode();
					Enumeration rootEnum = rootNode.preorderEnumeration();
					boolean flag = false;
					String checkOuter = "";
					String id = "";
					int idx = 0;
					int idx1 = 0;

					while (rootEnum.hasMoreElements())
					{
						BOMTreeNode treeNode = (BOMTreeNode)rootEnum.nextElement();
						BOMAssyComponent bomComponent = treeNode.getBOMComponent();

						checkOuter = bomComponent.getCheckOutStr()==null?"":bomComponent.getCheckOutStr().toString().trim();

						if(!checkOuter.equals(""))
						{
							idx = checkOuter.indexOf("(");
							idx1 = checkOuter.indexOf(")");

							id = checkOuter.substring(idx+1, idx1);
							if(id.equals(myId))
							{
								flag = true;
								break;
							}
						}
					}

					if(BOMBasicInfoPool.getPublicCheckOutStatus().equals("2"))
					{
						if(flag)
						{
							checkStatus = "2";
						}
						else
						{
							checkStatus = "1";
						}
					}
					else
					{
						checkStatus = "1";
					}
				}

				if(checkStatus.equals("1"))	{
					//checkOutMenu.setEnabled(true);
					//checkInMenu.setEnabled(false);
					//cancelCheckOutMenu.setEnabled(false);

					// 우측마우스 Pop-up Menu
					try {
						if (KETObjectUtil.isAdmin()) {
							checkOutPopupMenu.setEnabled(true);
							checkInPopupMenu.setEnabled(false);
							cancelCheckOutPopupMenu.setEnabled(false);
						}
					} catch (Exception e) {
						Kogger.error(getClass(), e);
					}

				} else if(checkStatus.equals("2")) {
					//checkOutMenu.setEnabled(false);
					//checkInMenu.setEnabled(true);
					//cancelCheckOutMenu.setEnabled(true);

					// 우측마우스 Pop-up Menu
					try {
						if (KETObjectUtil.isAdmin()) {
							checkOutPopupMenu.setEnabled(false);
							checkInPopupMenu.setEnabled(true);
							cancelCheckOutPopupMenu.setEnabled(true);
						}
					} catch (Exception e) {
						Kogger.error(getClass(), e);
					}
				} else {
					//checkOutMenu.setEnabled(false);
					//checkInMenu.setEnabled(false);
					//cancelCheckOutMenu.setEnabled(false);

					// 우측마우스 Pop-up Menu
					try {
						if (KETObjectUtil.isAdmin()) {
							checkInPopupMenu.setEnabled(false);
							checkOutPopupMenu.setEnabled(false);
							cancelCheckOutPopupMenu.setEnabled(false);
						}
					} catch (Exception e) {
						Kogger.error(getClass(), e);
					}
				}
			}
		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		}
	}
}
