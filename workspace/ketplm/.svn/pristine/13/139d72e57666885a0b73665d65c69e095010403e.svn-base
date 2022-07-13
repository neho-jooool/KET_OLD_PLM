package e3ps.bom;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class BOMRegisterApplication extends AbstractAIFUIApplication
{
    public BOMRegisterApplicationPanel bomPanel;

    public BOMRegisterApplication(JFrame desktop) throws Exception
    {
        super(desktop);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int k = dimension.width;
        int l = dimension.height;
        desktop.setBounds(0, 0, k, l);
        desktop.repaint();
        bomPanel = (BOMRegisterApplicationPanel)getApplicationPanel();
        
    }

    public BOMRegisterApplication(JFrame mybom, String tmp) throws Exception
    {
        super(mybom);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int k = dimension.width;
        int l = dimension.height;
        mybom.setBounds(0, 0, k, l);
        mybom.repaint();
    }

    public boolean close() throws Exception
    {
        bomPanel.close();
        return super.close();
    }

    public void expand()
    {
    }

    public void expandBelow()
    {
    }

    public void initializeDisplay()
    {
        bomPanel.initializeDisplay();
    }

    public void refresh()
    {
    }

}
