package e3ps.bom.command.searchitem;

import java.awt.Cursor;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.log.Kogger;

public class UpwardExplosionCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;
    BOMRegisterApplicationPanel bomPanel;

    public UpwardExplosionCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }

    public UpwardExplosionCmd() { }

    protected void executeCommand() throws Exception
    {
        try
        {
            bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

            bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            setRunnable(new SearchItemFrame(parent, app, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00265")/*역전개*/));
            bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
        catch(Exception ex)
        {
            bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            Kogger.error(getClass(), ex);
        }
    }

}
