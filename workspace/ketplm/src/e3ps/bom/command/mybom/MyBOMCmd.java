package e3ps.bom.command.mybom;

import java.awt.Frame;

import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import ext.ket.shared.log.Kogger;

public class MyBOMCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;

	public MyBOMCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
        parent = null;
    }
	
    protected void executeCommand() throws Exception
    {
    	try
    	{
			JFrame desktop = app.getDesktop();
			parent = desktop;

			Frame[] allFrames = Frame.getFrames();

			for (int i = 0; i < allFrames.length; i++) 
			{
				Frame f = allFrames[i];
				
				if (f.getTitle().toString().equalsIgnoreCase("My BOM")) 
				{
					f.dispose();
					System.gc();
				}
			}

			setRunnable(new MyBOMFrame(parent, app));
    	}
    	catch(Exception ex)
    	{
    		Kogger.error(getClass(), ex);
    	}
    }
 
}
