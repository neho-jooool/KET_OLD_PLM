package e3ps.bom;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import e3ps.bom.command.cut.CutCmd;
import e3ps.bom.command.paste.PasteCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.clipboard.BOMCodePool;
import e3ps.bom.common.clipboard.BOMECOBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.jtreetable.JTreeTable;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMECOQueryBean;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class BOMRegisterApplicationPanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	AbstractAIFUIApplication app;

	public MainEditorPanel mainEditorPane;
    public BOMStatusPanel publicStatusPanel;

    private CardLayout cardLayout = new CardLayout();
    private JPanel statusPanels;

    ///////////////////////////////
    // 메뉴바 활성화를 위한 Status Flag
	private int openPublicStatus = 0;   // 0 : Not Opened,  1 : Opened  - 공동작업자
    private int checkStatus = 1;			// 1 : Check-In,    2 : Check-Out - 공동작업자
    private int userStatus = 0;				// 0 : 공동작업자,    1 : 조회자 ,  2 : Admin
    private String bomStatus = "";
    private int myStatus = 0;				// 1 : Not Access, 2 : Check-In, 3 : Check-Out,  4:End-Working

    private boolean publicIsView = true;     // 속성 정보를 고칠수 있는지 없는지 설정 - 공동작업자
	private boolean isConnected = false;

	Registry appReg;

	public BOMRegisterApplicationPanel( AbstractAIFUIApplication app )
	{
		super ( new BorderLayout() );
		this.app = app;

		appReg = Registry.getRegistry(app);

		try
		{
			isConnected = true;	// 임시로 true 처리
        	setCodeValue();			// 제품, 금형 설계변경사유 코드와 값을 미리 BOMBasicInfoPool에 넣어둔다.

			jInit();

			setMenuBarEnabled();
		}
		catch(Exception e)
		{
			Kogger.error(getClass(), e);
		}
	}

    ///////////////////////////////////////////////////////////////////////////
	// 메뉴바 동작을 위한 Status 처리 함수.

    public void setOpenPublicStatus(int status)
	{
        openPublicStatus = status;
    }

    public void setCheckStatus(int status)
	{
        checkStatus = status;
    }

    public void setUserStatus(int status)
	{
        userStatus = status;
    }

    public void setBomStatus(String status)
	{
        bomStatus = status;
    }

    public void setMyStatus(int status)
	{
        myStatus = status;
    }

    public boolean isPublicView()
	{
        return publicIsView;
    }

    public int getUserStatus()
    {
		return userStatus;          // 0 : 공동작업자,	 1 : 조회자 ,	2 : Admin
    }

    public String getBomStatus()
    {
		return bomStatus;
    }

	public int getMyStatus()
	{
		return myStatus;
	}

    public void setMenuBarEnabled()
	{
Kogger.debug(getClass(), ">>> openPublicStatus : " + openPublicStatus);
Kogger.debug(getClass(), ">>> userStatus : " + userStatus);
Kogger.debug(getClass(), ">>> checkStatus : " + checkStatus);
Kogger.debug(getClass(), ">>> myStatus : " + myStatus);
Kogger.debug(getClass(), ">>> bomStatus : " + bomStatus);
        BOMRegisterApplicationMenuBar menubar = (BOMRegisterApplicationMenuBar)app.getApplicationMenuBar();
        BOMRegisterApplicationToolBar toolbar = (BOMRegisterApplicationToolBar)app.getApplicationToolBar();

		if (openPublicStatus == 1) {

			if (userStatus == 0) {				// 공동작업자 인 경우.

				if (bomStatus.equalsIgnoreCase("UNDERREVIEW") || bomStatus.equalsIgnoreCase("APPROVED") || bomStatus.equalsIgnoreCase("REJECTED")) {
					menubar.setPublicViewMenu(userStatus);
					toolbar.setPublicViewBtn();
					publicIsView = true;
				} else	{
					if (checkStatus == 1) {
						if(myStatus == 4) {
							menubar.setPublicEndMenu();
							toolbar.setPublicEndMenu();
							publicIsView = true;
						} else {
							// Check-In 상태
							menubar.setPublicCoworkerViewMenu();
							toolbar.setPublicCoworkerViewBtn();
							publicIsView = true;
						}
					} else {
						menubar.setPublicEditMenu();
						toolbar.setPublicEditBtn();
						publicIsView = false;
					}
				}
			} else if (userStatus == 2) {		// Admin 인 경우.

				if (bomStatus.equalsIgnoreCase("REJECTED")) {
					menubar.setPublicViewMenu(userStatus);
					toolbar.setPublicViewBtn();
					publicIsView = true;
				} else	{
					if (checkStatus == 1) {
						if(myStatus == 4) {
							menubar.setPublicEndMenu();
							toolbar.setPublicEndMenu();
							publicIsView = true;
						} else	{		// Check-In 상태
							menubar.setPublicCoworkerViewMenu();
							toolbar.setPublicCoworkerViewBtn();
							publicIsView = true;
						}
					} else {
						if(myStatus == 3) {
							// Check-Out 상태
							menubar.setAdminMenu();
							toolbar.setPublicEditBtn();
							publicIsView = false;
						} else {
							menubar.setPublicCoworkerViewMenu();
							toolbar.setPublicCoworkerViewBtn();
							publicIsView = true;
						}
					}
				}
			} else {							// 단순 조회자용..

				menubar.setPublicViewMenu(userStatus);
				toolbar.setPublicViewBtn();
				publicIsView = true;
			}
		} else {
			menubar.setInitMenu();
			toolbar.setInitMenu();
			publicIsView = true;
		}
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // 제품, 금형 설계변경사유 코드와 값을 미리 BOMBasicInfoPool에 넣어둔다.
    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void setCodeValue()
	{
		DBConnectionManager res = null;
		Connection conn = null;

		try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(appReg.getString("plm"));

            BOMSearchDao dao = new BOMSearchDao();
            dao.reasonCodeQuery(conn);

            BOMCodePool.setProdReason(dao.getProdReason());		// 제품 변경사유
            BOMCodePool.setMoldReason(dao.getMoldReason());	// 금형 변경사유
			isConnected = true;
        } catch (Exception ex) {
			Kogger.error(getClass(), ex);
			isConnected = false;
        } finally {
			if(res != null) {
				res.freeConnection(appReg.getString("plm"), conn);
			}
		}
    }

	public boolean isConnectSuccess()
	{
		return isConnected;
	}

    public void changeMouseShape(boolean flag)
	{
        if (flag)
		{
            mainEditorPane.getTreeTable().setCursor(Cursor.getPredefinedCursor(3)); // 모래시계로 변형
        }
		else
		{
            mainEditorPane.getTreeTable().setCursor(Cursor.getPredefinedCursor(0)); // 정상로 변형
        }
    }

    // 메뉴 또는 Toolbar 를 통해서 부품 정보를 수정할려는 경우.
    public void showPartProperty()
	{
		mainEditorPane.showPartPropertyDlg(publicIsView);
    }

    // 메뉴 또는 Toolbar 를 통해서 신규 금형부품 정보를 등록하려는 경우.
    public void showPartPropertyInsert()
	{
		mainEditorPane.showPartPropertyInsertDlg();
    }

    public JTreeTable getTreeTable()
	{
		return mainEditorPane.getTreeTable();
	}

    public BOMTreeTableModel getTreeTableModel()
	{
		return mainEditorPane.getTreeTableModel();
    }

    public int getTotalDataCount()
	{
		return mainEditorPane.getTotalDataCount();
    }

    public boolean getValidationCheck()
	{
		return true;
    }

	public void openBOMData(BOMAssyComponent rootComponent, Vector bomData)
	{
		setCodeValue();		// 제품, 금형 설계변경사유 코드와 값을 미리 BOMBasicInfoPool에 넣어둔다.

		mainEditorPane.openBOMData(rootComponent, bomData);

		setOpenPublicStatus(1);

		if (BOMBasicInfoPool.isAdmin())
		{
			setCheckStatus((BOMBasicInfoPool.getPublicCheckOutStatus() == null ||BOMBasicInfoPool.getPublicCheckOutStatus().equals("")) ?1:Integer.parseInt(BOMBasicInfoPool.getPublicCheckOutStatus()));
			setUserStatus(2);
			setMyStatus(BOMBasicInfoPool.getPublicMyStatus() == null ? 0 : Integer.parseInt(BOMBasicInfoPool.getPublicMyStatus()));
			setBomStatus( BOMBasicInfoPool.getPublicBOMStatus());
		}
		else
		{
			Vector coWorkerVec = BOMBasicInfoPool.getCoWorkerVec();

			String userId = Utility.checkNVL(BOMBasicInfoPool.getUserName()) + "(" + Utility.checkNVL(BOMBasicInfoPool.getUserEMail()) + ")";

			for(int i=0; i<coWorkerVec.size(); i++)
			{
				String coWorkerInfo = (String)coWorkerVec.elementAt(i);
				if (coWorkerInfo.equalsIgnoreCase(userId))
				{
					setUserStatus(0);
					setMyStatus(Integer.parseInt(BOMBasicInfoPool.getPublicMyStatus()));
					setBomStatus(BOMBasicInfoPool.getPublicBOMStatus());
					break;
				}
				else
				{
					setCheckStatus(1);
					setUserStatus(1);
				}
			}
		}

		//Kogger.debug(getClass(), "###################### headitem >>>>>>> : "+String.valueOf(rootComponent));
		setTransMenu(String.valueOf(rootComponent));

		setMenuBarEnabled();

		publicStatusPanel.setStatusBar();
	}

	// transferFlag값에 따라 메뉴를 enable 한다.
	public void setTransMenu(String itemcode)
	{
		String econo = BOMECOBasicInfoPool.getBomEcoNumber();
		BOMRegisterApplicationMenuBar menubar = (BOMRegisterApplicationMenuBar)app.getApplicationMenuBar();
Kogger.debug(getClass(), "@@@@@ econo : " + econo);

		String transFlag = "";
		if( econo.equals("") )		// 신규
		{
			KETBOMHeaderQueryBean query = new KETBOMHeaderQueryBean();
			KETBomHeader header = query.getBOMHeader( itemcode );

			if ( header != null ) {		// PLM 에서 신규로 등록한 BOM인 경우
				transFlag = StringUtil.checkNull(header.getTransferFlag());
			} else {						// Migration 된 BOM 인 경우 헤더가 없음
				transFlag = "1";
			}

			if(transFlag.equals("2") || transFlag.equals("3"))
			{
				menubar.setTransRequestMenu();
			}else{
				menubar.setTransSuccessMenu();
			}
		}
		else 							// 변경
		{
			KETBOMECOQueryBean query = new KETBOMECOQueryBean();
			KETBomEcoHeader header = query.getBOMECOHeader( econo, itemcode );

			if ( header != null ) {
				transFlag = StringUtil.checkNull(header.getTransferFlag());
			} else {
				transFlag = "1";
			}

			if(transFlag.equals("2") || transFlag.equals("3"))
			{
				 menubar.setTransRequestMenu();
			}else{
				menubar.setTransSuccessMenu();
			}
		}
	}

	// 현재 열려있는 BOM Panel의 List를 Clear하고, Empty로 세팅
    public void clearBOMList()
	{
		mainEditorPane.clearBOMList();
		setOpenPublicStatus(0);
		setCheckStatus(1);
		setUserStatus(0);
		setBomStatus("None");
		setMyStatus(0);
		// 메뉴바 조정
		setMenuBarEnabled();
    }

	// 서버와 클라이언트의 연결이 끊어지는 경우 함수가 호출된다.
	public void close()
	{
        mainEditorPane.clearBOMList();

        // Basic Pool 초기화.
        BOMBasicInfoPool.setUserDept("");
        BOMBasicInfoPool.setUserEMail("");
        BOMBasicInfoPool.setUserId("");
        BOMBasicInfoPool.setUserName("");
        BOMBasicInfoPool.setIsAdminFlag(false);
	}

/////////////////////////////////////////////////////////////////////////////
	public void initializeDisplay() {}
///////////////////////////////////////////////////////////////////////////////

	private void jInit() throws Exception
	{
        mainEditorPane = new MainEditorPanel(app, this);

        // Status Panel 붙이기
        statusPanels = new JPanel();
        statusPanels.setLayout(cardLayout);

        publicStatusPanel = new BOMStatusPanel(mainEditorPane, app);

        statusPanels.add(publicStatusPanel, "Public");
        cardLayout.show(statusPanels, "Public");
        add ( "North",  statusPanels);
//		setMenuBarEnabled();

		add("Center",mainEditorPane);

        // Key Action Listener 등록
        KeyActionListener keyAction = new KeyActionListener();
        // Cut Action
        KeyStroke cutKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK, true);
        // Paste Action
        KeyStroke pasteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK, true);

        registerKeyboardAction(keyAction, "Cut Action", cutKeyStroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        registerKeyboardAction(keyAction, "Paste Action", pasteKeyStroke, JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public BOMTreeNode[] getSelectedTreeNode()
	{
		return mainEditorPane.getSelectedTreeNode();
    }

    public BOMTreeNode[] getSelectedNode()
	{
		return mainEditorPane.getSelectedNode();
    }

	class KeyActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("Cut Action"))
			{
                BOMRegisterApplicationMenuBar menubar = (BOMRegisterApplicationMenuBar)app.getApplicationMenuBar();
                if (!menubar.cutMenu.isEnabled())
				{
                    return;
				}

                try
				{
                    CutCmd cmd = new CutCmd(app.getDesktop(), app);
                    cmd.executeModal();
                }
				catch(Exception ex)
				{
                    Kogger.error(getClass(), ex);
                }
			}
			else if (e.getActionCommand().equals("Paste Action"))
			{
                BOMRegisterApplicationMenuBar menubar = (BOMRegisterApplicationMenuBar)app.getApplicationMenuBar();
                if (!menubar.pasteMenu.isEnabled())
				{
                    return;
				}

                try
				{
                    PasteCmd cmd = new PasteCmd(app.getDesktop(), app);
                    cmd.executeModal();
                }
				catch(Exception ex)
				{
                    Kogger.error(getClass(), ex);
                }
            }
		}
	}

	public JPanel getMainEditorPane()
	{
		return mainEditorPane;
	}

    public String getSelectedPanel()
	{
		return "BOMEditor";
	}

}
