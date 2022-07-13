package e3ps.bom.command.confirmbom;

import java.awt.Cursor;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.bomvalidation.BOMValidationOperation;
import e3ps.bom.command.endworking.EndWorkingCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.entity.KETBomHeader;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class ConfirmBomCmd extends AbstractAIFCommand
{
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private JFrame parent;
    AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel bomPanel;
    ConfirmBomFrame jframe;

    public ConfirmBomCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    public ConfirmBomCmd() { }

    protected void executeCommand() throws Exception
    {
        try
        {
            bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

            bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            //setRunnable(new ConfirmBomFrame(parent, app, "결재요청"));
            JFrame desktop = app.getDesktop();
            parent = desktop;

//            String strValidationForEnd = BOMBasicInfoPool.getValidationForEnd();

            String itemCodeStr = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

            if( itemCodeStr.equalsIgnoreCase("Empty") )
            {
                bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                MessageBox mbox = new MessageBox(parent, messageRegistry.getString("openBOMWorkspace"), "Warning", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
                return;
            }
            else
            {
                EndWorkingCmd ew = new EndWorkingCmd(desktop, app);
//Kogger.debug(getClass(), "@@@@@@@@@@@@@  isEndWorking : "+ew.isEndWorking());
                if( ew.isConfirmWorking() )
                {
//                    BOMBasicInfoPool.setValidationForEnd("LAST");
                    BOMValidationOperation op = new BOMValidationOperation(app,false);
                    op.executeOperation();
                }
                else
                {
                    bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00269")/*완료되지 않은 작업입니다.*/, "Warning", 0);
                    messagebox.setModal(true);
                    messagebox.setVisible(true);
                    return;
                }

                KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
                KETBomHeader wp = bean.getBOMHeader( itemCodeStr );
                //WTPart wp = bean.searchItem( itemCodeStr );
                String statusStr = wp.getState().toString();
                Kogger.debug(getClass(), "###################### Confirm statusStr : "+statusStr);

                if( !(statusStr.equals("INWORK") || statusStr.equals("REWORK")) )
                {
                    bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    MessageBox messagebox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00078")/*결재요청이 가능한 작업상태가 아닙니다..*/, "Warning", 0);
                    messagebox.setModal(true);
                    messagebox.setVisible(true);
                    return;
                }
            }
Kogger.debug(getClass(), "@@@@ getBomValidationResult() : " +BOMBasicInfoPool.getBomValidationResult() );
//Kogger.debug(getClass(), "@@@@ getErrorFrame() : " +BOMBasicInfoPool.getErrorFrame() );

//            if( BOMBasicInfoPool.getErrorFrame() ) //에러 없을때...
//            if(BOMBasicInfoPool.getBomValidationResult()) //에러 없을때... (경고는 있어도 됨)
//            {
                setRunnable(new ConfirmBomFrame(parent, app));
//            }

            bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
            bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }
}
