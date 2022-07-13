package e3ps.bom.command.copy;

import e3ps.bom.common.jtreetable.*;
import e3ps.bom.common.clipboard.*;
import e3ps.bom.framework.aif.*;

public class CopyOperation extends AbstractAIFOperation 
{
	private BOMTreeNode[] nodes;
	AbstractAIFUIApplication app;
	String type = "";

	public CopyOperation(AbstractAIFUIApplication app, BOMTreeNode[] nodes, String type) 
	{
		this.nodes = nodes;
		this.app = app;
		this.type = type;
	}

    public void executeOperation() throws Exception 
	{
        // 선택된 Node가 Null 아닌 경우에만 Clipboard에 저장한다.
        if (nodes != null) 
		{
             ClipBoardPool.addTreeNode(app, nodes, type);
        }
    }
}
