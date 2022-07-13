package e3ps.bom.command.clearbom;

import e3ps.bom.framework.aif.*;
import e3ps.bom.*;

public class ClearBOMOperation extends AbstractAIFOperation 
{
    private BOMRegisterApplicationPanel bomPanel;

    public ClearBOMOperation(BOMRegisterApplicationPanel bomPanel) 
	{
        this.bomPanel = bomPanel;
    }

    public void executeOperation() throws Exception
	{
        bomPanel.clearBOMList();
        System.gc();
    	System.runFinalization();
    }
}
