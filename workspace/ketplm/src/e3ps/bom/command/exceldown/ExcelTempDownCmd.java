package e3ps.bom.command.exceldown;

import javax.swing.*;

import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.jtreetable.BOMTreeTableModel;
import e3ps.bom.framework.aif.*;

public class ExcelTempDownCmd extends AbstractAIFCommand
{
    private JFrame parent;
    AbstractAIFUIApplication app;

    public ExcelTempDownCmd(JFrame frame, AbstractAIFUIApplication app)
    {
        this.app = app;
		this.parent = app.getDesktop();
    }

    protected void executeCommand() throws Exception
    {
		BOMTreeTableModel model = (BOMTreeTableModel)((BOMRegisterApplicationPanel)app.getApplicationPanel()).getTreeTableModel();
        setRunnable(new ExcelTempDownDialog(parent, app, (BOMTreeNode)model.getRootNode()));
    }
}
