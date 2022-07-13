package e3ps.bom.command.wfhistorydetails;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplet;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import ext.ket.shared.log.Kogger;

public class WFHistoryDetailsCmd extends AbstractAIFCommand {
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    BOMRegisterApplicationPanel pnl;
    private AbstractAIFUIApplication app;
    BOMRegisterDesktop desktop;

	public WFHistoryDetailsCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = frame;

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        desktop = (BOMRegisterDesktop)parent;
    }

    protected void executeCommand() throws Exception
    {
		init();
    }

	private void init() 
	{
		String urlStr = "";
		String itemCodeStr = "";
		String itemOid = "";

//        BOMTreeNode selectedNodes[] = pnl.getSelectedTreeNode(); 
//        if (selectedNodes == null || selectedNodes.length == 0) 
//		{
//            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow9"), "Error", MessageBox.ERROR);
//            m.setVisible(true);
//            m.setModal(true);
//            return;
//        }
//
//		// 둘 이상의 Node 를 선택한 경우 메세지 처리
//		if (selectedNodes.length != 1) 
//		{
//			MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow3"), "Error", MessageBox.ERROR);
//			m.setVisible(true);
//			m.setModal(true);
//			return;
//		}

		BOMTreeNode selectedNodes = pnl.getTreeTableModel().getRootNode();
        try 
		{
			itemCodeStr = selectedNodes.getBOMComponent().getItemCodeStr() == null ? "" : selectedNodes.getBOMComponent().getItemCodeStr().trim();
			
			itemOid = KETBomHelper.service.getItemOid(itemCodeStr);
	
			urlStr = BOMBasicInfoPool.getServerCodebase().trim() + "jsp/wfm/ApprovalHistory.jsp?pboOid=" +  itemOid.trim();
		
			((BOMRegisterApplet)desktop.getParentApplet()).openExplore(urlStr, "_blank");
        }
        catch (Exception e) 
		{
			Kogger.error(getClass(), e);

			MessageBox messagebox = new MessageBox(app.getDesktop(), e.toString(), "Warning", MessageBox.WARNING);
			messagebox.setModal(true);
			messagebox.setVisible(true);
			return;
		}
	}
}
