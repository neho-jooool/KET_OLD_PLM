package e3ps.bom.common.component;

import java.util.Vector;

public class BOMDesignatorComponent
{
    private Double quantityDbl;
    private String designatorNoStr;
    private String commentsStr;
	private String sortOrderStr;

	protected Vector sourceDesignatorComponent = new Vector();
	protected Vector targetDesignatorComponent = new Vector();
	private BOMAssyComponent assyComponent;

	private String ecoCodeStr;
	private Double beforeQuantityDbl;
	private String beforeCommentsStr;
	private String parentItemStr;
	private String childItemStr;
	
    public BOMDesignatorComponent()
    {
        sourceDesignatorComponent = new Vector();
        targetDesignatorComponent = new Vector();
        designatorNoStr = "";
        quantityDbl = new Double(1.0D);
        commentsStr = "";
		sortOrderStr = "";
        assyComponent = new BOMAssyComponent();

		ecoCodeStr = "";
		beforeQuantityDbl = new Double(1.0D);
		beforeCommentsStr = "";
		parentItemStr = "";
		childItemStr = "";
    }
    
    public String getDesignatorNoStr()
    {
        return designatorNoStr;
    }

    public Double getQuantityDbl()
    {
        return quantityDbl;
    }

    public String getCommentsStr()
    {
        return commentsStr;
    }

	public String getSortOrderStr() 
	{
		return sortOrderStr;
	}

    public BOMAssyComponent getAssyComponent()
    {
        return assyComponent;
    }

    public Vector getSourceDesignatorComponent()
    {
        return sourceDesignatorComponent;
    }

    public Vector getTargetDesignatorComponent()
    {
        return targetDesignatorComponent;
    }

	public String getEcoCodeStr()
	{
		return ecoCodeStr;
	}

    public Double getBeforeQuantityDbl()
    {
        return beforeQuantityDbl;
    }

	public String getBeforeCommentsStr()
	{
		return beforeCommentsStr;
	}

	public String getParentItemStr()
	{
		return parentItemStr;
	}

	public String getChildItemStr()
	{
		return childItemStr;
	}

    public void setDesignatorNoStr(String str)
    {
        if(str != null && !str.equals(""))
		{
            designatorNoStr = str;
		}
    }

    public void setQuantityDbl(Double dbl)
    {
        if(dbl != null)
		{
            quantityDbl = dbl;
		}
    }

    public void setCommentsStr(String str)
    {
        if(str != null && !str.equals(""))
		{
            commentsStr = str;
		}
    }

    public void setSortOrderStr(String str) 
	{
		if (str != null)
		{
			this.sortOrderStr = str;
		}
    }

    public void setAssyComponent(BOMAssyComponent assyComponent)
    {
        if(assyComponent != null)
		{
            this.assyComponent = assyComponent;
		}
    }

    public void setSourceDesignatorComponent(Vector vec)
    {
        sourceDesignatorComponent = vec;
    }

    public void setTargetDesignatorComponent(Vector vec)
    {
        targetDesignatorComponent = vec;
    }

    public void setEcoCodeStr(String str) 
	{
		if (str != null)
		{
			ecoCodeStr = str;
		}
    }

    public void setBeforeQuantityDbl(Double dbl)
    {
        if(dbl != null)
		{
            beforeQuantityDbl = dbl;
		}
    }

    public void setBeforeCommentsStr(String str) 
	{
		if (str != null)
		{
			beforeCommentsStr = str;
		}
    }

    public void setParentItemStr(String str) 
	{
		if (str != null)
		{
			parentItemStr = str;
		}
    }

    public void setChildItemStr(String str) 
	{
		if (str != null)
		{
			childItemStr = str;
		}
    }

    public String toString()
    {
        return designatorNoStr;
    }

    public BOMDesignatorComponent createNewDesignatorComponent()
    {
        BOMDesignatorComponent newComponent = new BOMDesignatorComponent();
        newComponent.setDesignatorNoStr(getDesignatorNoStr());
        newComponent.setQuantityDbl(getQuantityDbl());
        newComponent.setCommentsStr(getCommentsStr());
		newComponent.setSortOrderStr(getSortOrderStr());

		newComponent.setEcoCodeStr(getEcoCodeStr());
		newComponent.setBeforeQuantityDbl(getBeforeQuantityDbl());
		newComponent.setBeforeCommentsStr(getBeforeCommentsStr());
		newComponent.setParentItemStr(getParentItemStr());
		newComponent.setChildItemStr(getChildItemStr());
        return newComponent;
    }

}
