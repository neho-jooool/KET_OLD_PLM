// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BOMRegisterApplicationToolBar.java

package e3ps.bom;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JButton;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.common.util.Utility;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.aif.AbstractAIFUIApplicationToolBar;
import e3ps.common.util.KETObjectUtil;
import ext.ket.shared.log.Kogger;

public class BOMRegisterApplicationToolBar extends AbstractAIFUIApplicationToolBar
{
	private static final long serialVersionUID = 1L;
	
	JButton exitBtn;
    JButton myBomBtn;
    JButton checkOutBtn;
    JButton checkInBtn;
    JButton cancelCheckOutBtn;
    JButton cutBtn;
    JButton copyBtn;
    JButton pasteBtn;
	JButton addBtn;
	JButton replaceBtn;
	JButton copyChildBtn;
    JButton deleteBtn;
    JButton moveUpBtn;
    JButton moveDownBtn;
    JButton bomPropertyBtn;
    JButton cellCopyBtn;
    JButton findPNBtn;
    JButton searchItemBtn;
	JButton refreshBtn;

    private Hashtable htblStatus = new Hashtable();
	private AbstractAIFUIApplication app = null;
	private BOMRegisterApplicationPanel pnl = null;
	BOMTreeNode rootNode;	

    public BOMRegisterApplicationToolBar(AbstractAIFUIApplication app)
    {
        super(app);

		this.app = app;

        exitBtn = addToolBarSmallButton("exitbom");
        refreshBtn = addToolBarSmallButton("refresh");
        addSeparator();
        myBomBtn = addToolBarSmallButton("mybom");
        searchItemBtn = addToolBarSmallButton("searchitem");
        findPNBtn = addToolBarSmallButton("finditem");
        addSeparator();
        
        try {
			if (KETObjectUtil.isAdmin()) {
				checkOutBtn = addToolBarSmallButton("checkout");
				checkInBtn = addToolBarSmallButton("checkin");
				cancelCheckOutBtn = addToolBarSmallButton("cancelcheckout");
				addSeparator();
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
        cutBtn = addToolBarSmallButton("cut");
        copyBtn = addToolBarSmallButton("copy");
        cellCopyBtn = addToolBarSmallButton("clipboard");
        pasteBtn = addToolBarSmallButton("paste");
        addSeparator();
		addBtn = addToolBarSmallButton("add");
		replaceBtn = addToolBarSmallButton("replace");
		copyChildBtn = addToolBarSmallButton("copychild");
        deleteBtn = addToolBarSmallButton("remove");
        addSeparator();
       
		moveUpBtn = addToolBarSmallButton("moveup");
        moveDownBtn = addToolBarSmallButton("movedown");
		
        addSeparator();
		bomPropertyBtn = addToolBarSmallButton("bomproperty");
        addSeparator();
    }

    public void setInitMenu()
    {
        findPNBtn.setEnabled(false);
        searchItemBtn.setEnabled(true);
        
        try {
			if (KETObjectUtil.isAdmin()) {
		        checkOutBtn.setEnabled(false);
		        checkInBtn.setEnabled(false);
		        cancelCheckOutBtn.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		
        cutBtn.setEnabled(false);
        copyBtn.setEnabled(false);
        cellCopyBtn.setEnabled(false);
        pasteBtn.setEnabled(false);
		addBtn.setEnabled(false);
		replaceBtn.setEnabled(false);
		copyChildBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        moveUpBtn.setEnabled(false);
        moveDownBtn.setEnabled(false);
        bomPropertyBtn.setEnabled(false);
		refreshBtn.setEnabled(false);
    }

    public void setPublicViewBtn()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);
        
        try {
			if (KETObjectUtil.isAdmin()) {
		        checkOutBtn.setEnabled(false);
		        checkInBtn.setEnabled(false);
		        cancelCheckOutBtn.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
        cutBtn.setEnabled(false);
        cellCopyBtn.setEnabled(true);
        pasteBtn.setEnabled(false);
		addBtn.setEnabled(false);
		replaceBtn.setEnabled(false);
		copyChildBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        moveUpBtn.setEnabled(false);
        moveDownBtn.setEnabled(false);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

    public void setPublicCoworkerViewBtn()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);

		setCheckInOutBtn();

        cutBtn.setEnabled(false);
        cellCopyBtn.setEnabled(true);
        copyBtn.setEnabled(false);
        pasteBtn.setEnabled(false);
		addBtn.setEnabled(false);
		replaceBtn.setEnabled(false);
		copyChildBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        moveUpBtn.setEnabled(false);
        moveDownBtn.setEnabled(false);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

    public void setPublicEditBtn()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);
        
        try {
			if (KETObjectUtil.isAdmin()) {
		        checkOutBtn.setEnabled(false);
		        checkInBtn.setEnabled(true);
		        cancelCheckOutBtn.setEnabled(true);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
        cutBtn.setEnabled(true);
        copyBtn.setEnabled(true);
        cellCopyBtn.setEnabled(true);
        pasteBtn.setEnabled(true);
		addBtn.setEnabled(true);
		replaceBtn.setEnabled(true);
		copyChildBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        moveUpBtn.setEnabled(true);
        moveDownBtn.setEnabled(true);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

    public void setSearchCopyBtn()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);
        
        try {
			if (KETObjectUtil.isAdmin()) {
		        checkOutBtn.setEnabled(false);
		        checkInBtn.setEnabled(false);
		        cancelCheckOutBtn.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
        cutBtn.setEnabled(false);
        copyBtn.setEnabled(true);
        cellCopyBtn.setEnabled(true);
        pasteBtn.setEnabled(false);
		addBtn.setEnabled(false);
		replaceBtn.setEnabled(false);
		copyChildBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        moveUpBtn.setEnabled(false);
        moveDownBtn.setEnabled(false);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

    public void setSearchViewBtn()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);
        
        try {
			if (KETObjectUtil.isAdmin()) {
		        checkOutBtn.setEnabled(false);
		        checkInBtn.setEnabled(false);
		        cancelCheckOutBtn.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
        cutBtn.setEnabled(false);
        copyBtn.setEnabled(false);
        cellCopyBtn.setEnabled(true);
        pasteBtn.setEnabled(false);
		addBtn.setEnabled(false);
		replaceBtn.setEnabled(false);
		copyChildBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        moveUpBtn.setEnabled(false);
        moveDownBtn.setEnabled(false);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

    public void setAdminMenu()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);

		setCheckInOutBtn();

        cutBtn.setEnabled(true);
        copyBtn.setEnabled(true);
        cellCopyBtn.setEnabled(true);
        pasteBtn.setEnabled(true);
		addBtn.setEnabled(true);
		replaceBtn.setEnabled(true);
		copyChildBtn.setEnabled(true);
        deleteBtn.setEnabled(true);
        moveUpBtn.setEnabled(true);
        moveDownBtn.setEnabled(true);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

    public void setPublicEndMenu()
    {
        findPNBtn.setEnabled(true);
        searchItemBtn.setEnabled(true);
        
        try {
			if (KETObjectUtil.isAdmin()) {
		        checkOutBtn.setEnabled(false);
		        checkInBtn.setEnabled(false);
		        cancelCheckOutBtn.setEnabled(false);
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
        cutBtn.setEnabled(false);
        copyBtn.setEnabled(true);
        cellCopyBtn.setEnabled(true);
        pasteBtn.setEnabled(false);
		addBtn.setEnabled(false);
		replaceBtn.setEnabled(false);
		copyChildBtn.setEnabled(false);
        deleteBtn.setEnabled(false);
        moveUpBtn.setEnabled(false);
        moveDownBtn.setEnabled(false);
        bomPropertyBtn.setEnabled(true);

		if(!Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber()).equals(""))
		{
			refreshBtn.setEnabled(false);
		}
		else
		{
			refreshBtn.setEnabled(true);
		}
    }

	public void setCheckInOutBtn()
	{
		try
		{
			pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();

			String myId = Utility.checkNVL(BOMBasicInfoPool.getUserId());

			String checkStatus = "1";

			if(pnl != null)
			{
				if ((pnl.getUserStatus() == 0 || pnl.getUserStatus() == 2) &&  pnl.getMyStatus() < 4 && pnl.getBomStatus() != null && 
				   (pnl.getBomStatus().equalsIgnoreCase("INWORK") ||
					pnl.getBomStatus().equalsIgnoreCase("REJECTED") ||
					pnl.getBomStatus().equalsIgnoreCase("UNDERREVIEW") ||
					pnl.getBomStatus().equalsIgnoreCase("REWORK") ||
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

				if(checkStatus.equals("1"))
				{
			        try {
						if (KETObjectUtil.isAdmin()) {
							checkOutBtn.setEnabled(true);
							checkInBtn.setEnabled(false);
							cancelCheckOutBtn.setEnabled(false);
						}
					} catch (Exception e) {
						Kogger.error(getClass(), e);
					}					
				}
				else if(checkStatus.equals("2"))
				{
			        try {
						if (KETObjectUtil.isAdmin()) {
							checkOutBtn.setEnabled(false);
							checkInBtn.setEnabled(true);
							cancelCheckOutBtn.setEnabled(true);
						}
					} catch (Exception e) {
						Kogger.error(getClass(), e);
					}		
				}
				else
				{
			        try {
						if (KETObjectUtil.isAdmin()) {
							checkOutBtn.setEnabled(false);
							checkInBtn.setEnabled(false);
							cancelCheckOutBtn.setEnabled(false);
						}
					} catch (Exception e) {
						Kogger.error(getClass(), e);
					}		
				}
			}
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
	}
}
