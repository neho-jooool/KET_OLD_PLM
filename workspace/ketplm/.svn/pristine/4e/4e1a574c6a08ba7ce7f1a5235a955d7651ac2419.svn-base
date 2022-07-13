package e3ps.bom.command.searchappliedproduct;

import java.awt.Cursor;

import javax.swing.JFrame;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.framework.aif.AbstractAIFCommand;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import ext.ket.shared.log.Kogger;

public class SearchAppliedProductCmd extends AbstractAIFCommand {

	private JFrame parent;
	AbstractAIFUIApplication app;
	BOMRegisterApplicationPanel bomPanel;

	public SearchAppliedProductCmd(JFrame frame, AbstractAIFUIApplication app) {
		this.app = app;
		parent = frame;
	}

	public SearchAppliedProductCmd() {}

	protected void executeCommand() throws Exception {
		try {
			bomPanel = (BOMRegisterApplicationPanel)app.getApplicationPanel();

			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setRunnable(new SearchAppliedProductDialog(parent, app));
			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		} catch (Exception ex) {
			bomPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			Kogger.error(getClass(), ex);
		}
	}

}
