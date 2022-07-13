package e3ps.bom.command.exitbom;

import java.awt.Frame;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import netscape.javascript.JSObject;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.BOMRegisterDesktop;
import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkin.CheckInCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.Registry;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class ExitBOMCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;

    BOMTreeNode rootNode; //shin...
    BOMRegisterApplicationPanel pnl; //shin....

    public ExitBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;

        parent = app.getDesktop();
    }

    public ExitBOMCmd(){}

    protected void executeCommand() throws Exception
    {
        try
        {
            int n = JOptionPane.showConfirmDialog(parent, messageRegistry.getString("closeBom"), "Confirm", JOptionPane.YES_NO_OPTION);
            if(n==0)
            {
                Frame[] allFrames = Frame.getFrames();

                for (int i = 0; i < allFrames.length; i++)
                {
                    Frame f = allFrames[i];

                    if( f.getTitle().toString().equalsIgnoreCase("My BOM") || f.getTitle().toString().equalsIgnoreCase("Search Item") ||
                        f.getTitle().toString().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00317")/*정전개*/) || f.getTitle().toString().equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/) ||
                        f.getTitle().toString().equalsIgnoreCase("New Project") || f.getTitle().toString().equalsIgnoreCase("BOM Release") ||
                        f.getTitle().toString().equalsIgnoreCase("BOM Divide") )
                    {
                        f.dispose();
                        System.gc();
                    }
                }

            //shin------------------------------------------------------------------- 체크인
                setAutocheckin();
            //------------------------------------------------------------------------

                 BOMBasicInfoPool.setPublicBOMOid("");
                 DBConnectionManager.getInstance().release();

                 // Added by MJOH, 2007-03-15
                 // BOM Applet 을 실행시킨 웹브라우저 창을 다는다.
                 JSObject win = (JSObject) JSObject.getWindow( ((BOMRegisterDesktop)parent).getParentApplet() );
                 win.call("winClose", null);
                 ////////////////////////////////////////////////////////////////////////////

                 parent.dispose();

             }
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    private void setAutocheckin()
    {
        try{
        BOMCheckOutInDao bcoi = new BOMCheckOutInDao();
        Vector vccom = new Vector();

        BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
        pnl = (BOMRegisterApplicationPanel)app.getApplicationPanel();
        rootNode = (BOMTreeNode)model.getRootNode();
        Enumeration rootEnum = rootNode.preorderEnumeration();

        while (rootEnum.hasMoreElements())
        {
            BOMTreeNode treeNode = (BOMTreeNode)rootEnum.nextElement();
            BOMAssyComponent bomComponent = treeNode.getBOMComponent();
            vccom.add(bomComponent.getItemCodeStr());
            bcoi.setCheckIn( bomComponent.getItemCodeStr(), BOMBasicInfoPool.getUserId() );
        }

        boolean bomGubunFlag = (BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim()).equals("") ? true : false ;

        CheckInCmd ci = new CheckInCmd(parent, app);
        ci.checkIn(vccom, bomGubunFlag);
        }catch(Exception e){

        }
    }

}
