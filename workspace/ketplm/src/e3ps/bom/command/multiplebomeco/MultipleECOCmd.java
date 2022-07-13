/**
 * @author : Park Pil Keun (pkpark@lgcns.com)
 * @since  : 2006.09.22
 */
package e3ps.bom.command.multiplebomeco;

import javax.swing.JFrame;

import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;

public class MultipleECOCmd extends AbstractAIFCommand  {
	private JFrame parent;
	AbstractAIFUIApplication app;
	String itemCode;
	String itemDesc;
	String parentItems;

	public MultipleECOCmd(JFrame frame, AbstractAIFUIApplication app, String itemCode, String itemDesc, String parentItems) {
		this.app = app;
		parent = frame;
		this.itemCode = itemCode;
		this.itemDesc = itemDesc;
		this.parentItems = parentItems;
	}

	public void executeCommand() throws Exception {
		setRunnable(new MultipleECODialog(parent, app, itemCode, itemDesc, parentItems));
	}
}
