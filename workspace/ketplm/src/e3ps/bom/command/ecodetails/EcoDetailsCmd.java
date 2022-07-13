package e3ps.bom.command.ecodetails;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplet;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.command.searchitem.SearchBOMPanel;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import ext.ket.shared.log.Kogger;

public class EcoDetailsCmd extends AbstractAIFCommand {
	private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    BOMRegisterApplicationPanel pnl;
    private AbstractAIFUIApplication app;
    BOMRegisterDesktop desktop;
    SearchBOMPanel searchPnl;
    boolean isFromView = false;

	public EcoDetailsCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = frame;

        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        desktop = (BOMRegisterDesktop)parent;
    }
	
	public EcoDetailsCmd(JFrame frame, AbstractAIFUIApplication app, SearchBOMPanel searchPnl)
    {
		this.app = app;
        this.searchPnl = searchPnl;
        parent = frame;
        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        desktop = (BOMRegisterDesktop)pnl.mainEditorPane.desktop;
        
        isFromView = true;
    }

    public void executeCommand() throws Exception
    {
    	init();
    }

	private void init() 
	{
		String urlStr = "";
		String itemCodeStr = "";
		String itemOid = "";
		BOMTreeNode selectedNodes[] = null;

    	if (isFromView) {		// BOM 조회 화면에서 호출된경우 
    		selectedNodes = searchPnl.getSelectedTreeNode();
    	} else {				// 메인 편집화면에서 호출된 경우 
    		selectedNodes = pnl.getSelectedTreeNode();
    	}
		
        if (selectedNodes == null || selectedNodes.length == 0) 
		{
            MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow9"), "Error", MessageBox.ERROR);
            m.setVisible(true);
            m.setModal(true);
            return;
        }

		// 둘 이상의 Node 를 선택한 경우 메세지 처리
		if (selectedNodes.length != 1) 
		{
			MessageBox m = new MessageBox(desktop, messageRegistry.getString("selectRow3"), "Error", MessageBox.ERROR);
			m.setVisible(true);
			m.setModal(true);
			return;
		}

        try 
		{
			itemCodeStr = selectedNodes[0].getBOMComponent().getItemCodeStr() == null ? "" : selectedNodes[0].getBOMComponent().getItemCodeStr().trim();

			itemOid = KETBomHelper.service.getItemOid(itemCodeStr);

			urlStr = BOMBasicInfoPool.getServerCodebase().trim() + "jsp/bom/ViewRelatedList.jsp?type=eco&poid=" + itemOid.trim();
		
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
