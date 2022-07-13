package e3ps.bom.common.component;

public class BOMSubAssyComponent
{
    protected String substituteItemCodeStr;
    protected String parentItemCodeStr;
    protected String childItemCodeStr;
    protected String descStr;
    protected String statusStr;
    protected String uitStr;
    protected String uitDisplayStr;
    protected String uomStr;
    protected Double quantityDbl;
    protected String supplyTypeStr;
    protected String startDate;
    protected String endDate;
	protected String sortOrderStr;

	protected String ecoCodeStr;
    protected Double beforeQuantityDbl;
    protected String beforeUnitStr;
	protected String beforeSupplyTypeStr;
	protected String beforeStartDate;
	protected String beforeEndDate;

	//shin...
	protected Integer seqInt;
    protected Integer levelInt;
	protected Integer itemSeqInt;
	protected String versionStr;
	protected String newFlagStr;
	protected String firstMarkStr;
	protected String secondMarkStr;
	protected String materialStr;
	protected String hardnessFrom;
	protected String hardnessTo;
	protected String designDate;
	protected String statusKrStr;
	
    public BOMSubAssyComponent()
    {
        substituteItemCodeStr = "";
        parentItemCodeStr = "";
        childItemCodeStr = "";
        descStr = "";
        statusStr = "";
        uitStr = "";
        uitDisplayStr = "";
        uomStr = "";
        quantityDbl = new Double(1.0D);
//        quantityDbl = "";
        supplyTypeStr = "";
        startDate = "";
        endDate = "";
		sortOrderStr = "";
        substituteItemCodeStr = "Empty";

		ecoCodeStr = "";
		beforeQuantityDbl = new Double(1.0D);
//		beforeQuantityDbl = "";
		beforeUnitStr = "";
		beforeSupplyTypeStr = "";
		beforeStartDate = "";
		beforeEndDate = "";

		//shin...
		seqInt = new Integer(0);
        levelInt = new Integer(0);
		itemSeqInt = new Integer(0);
		versionStr = "";
		newFlagStr = "";
        firstMarkStr = "";
        secondMarkStr = "";
    	materialStr = "";
    	hardnessFrom = "";
    	hardnessTo = "";
    	designDate = "";
		statusKrStr = "";
    }

    public BOMSubAssyComponent(String substituteItemCodeStr)
    {
        this.substituteItemCodeStr = "";
        parentItemCodeStr = "";
        childItemCodeStr = "";
        descStr = "";
        statusStr = "";
        uitStr = "";
        uitDisplayStr = "";
        uomStr = "";
        quantityDbl = new Double(1.0D);
//        quantityDbl = "";
        supplyTypeStr = "";
        startDate = "";
        endDate = "";
		sortOrderStr = "";
        this.substituteItemCodeStr = substituteItemCodeStr;

		ecoCodeStr = "";
		beforeQuantityDbl = new Double(1.0D);
//		beforeQuantityDbl = "";
		beforeUnitStr = "";
		beforeSupplyTypeStr = "";
		beforeStartDate = "";
		beforeEndDate = "";

		//shin...
		seqInt = new Integer(0);
        levelInt = new Integer(0);
		itemSeqInt = new Integer(0);
		versionStr = "";
		setLevelInt(new Integer(0));
        setSeqInt(new Integer(0));
        newFlagStr = "";
        firstMarkStr = "";
        secondMarkStr = "";
    	materialStr = "";
    	hardnessFrom = "";
    	hardnessTo = "";
    	designDate = "";
		statusKrStr = "";
    }

    public String getSubstituteItemCodeStr()
    {
        return substituteItemCodeStr;
    }

    public String getParentItemCodeStr()
    {
        return parentItemCodeStr;
    }

    public String getChildItemCodeStr()
    {
        return childItemCodeStr;
    }

    public String getDescStr()
    {
        return descStr;
    }

    public String getStatusStr()
    {
        return statusStr;
    }

    public String getUitStr()
    {
        return uitStr;
    }

    public String getUitDisplayStr()
    {
        return uitDisplayStr;
    }
    
    public String getUomStr()
    {
        return uomStr;
    }

    public Double getQuantityDbl()
    {
        return quantityDbl;
    }

    public String getSupplyTypeStr()
    {
        return supplyTypeStr;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public String getSortOrderStr() 
	{
        return sortOrderStr;
    }

    public String getEcoCodeStr()
    {
        return ecoCodeStr;
    }

    public Double getBeforeQuantityDbl()
    {
        return beforeQuantityDbl;
    }

    public String getBeforeUnitStr()
    {
        return beforeUnitStr;
    }
    
    public String getBeforeSupplyTypeStr()
    {
        return beforeSupplyTypeStr;
    }

    public String getBeforeStartDate()
    {
        return beforeStartDate;
    }

    public String getBeforeEndDate()
    {
        return beforeEndDate;
    }

	 public String getStatusKrStr()
    {
        return statusKrStr;
    }

	//shin............................
	public Integer getSeqInt()
    {
        return seqInt;
    }

    public Integer getLevelInt()
    {
        return levelInt;
    }

	public Integer getItemSeqInt()
    {
        return itemSeqInt;
    }
	
	public String getVersionStr()
	{
	    return versionStr;
	}
	public String getNewFlagStr()
    {
        return newFlagStr;
    }

    public String getFirstMarkStr() 
	{
        return firstMarkStr;
    }

    public String getSecondMarkStr() 
	{
        return secondMarkStr;
    }
    
    public String getMaterialStr() 
	{
        return materialStr;
    }
    
    public String getHardnessFrom() 
	{
        return hardnessFrom;
    }
    
    public String getHardnessTo() 
	{
        return hardnessTo;
    }
    
    public String getDesignDate() 
	{
        return designDate;
    }
    
	//................................


    public void setSubstituteItemCodeStr(String str)
    {
        if(str != null)
		{
            substituteItemCodeStr = str;
		}
    }

    public void setParentItemCodeStr(String str)
    {
        if(str != null)
		{
            parentItemCodeStr = str;
		}
    }

    public void setChildItemCodeStr(String str)
    {
        if(str != null)
		{
            childItemCodeStr = str;
		}
    }

    public void setDescStr(String str)
    {
        if(str != null)
		{
            descStr = str;
		}
    }

    public void setStatusStr(String str)
    {
        if(str != null)
		{
            statusStr = str;
		}
    }

    public void setUitStr(String str)
    {
        if(str != null)
		{
            uitStr = str;
		}
    }
    
    public void setUitDisplayStr(String str)
    {
        if(str != null)
		{
            uitDisplayStr = str;
		}
    }

    public void setUomStr(String str)
    {
        if(str != null)
		{
            uomStr = str;
		}
    }

    public void setQuantityDbl(Double num)
    {
        if(num != null)
		{
            quantityDbl = num;
		}
    }

    public void setSupplyTypeStr(String str)
    {
        if(str != null)
		{
            supplyTypeStr = str;
		}
    }

    public void setStartDate(String date)
    {
        if(date != null)
		{
            startDate = date;
		}
    }

    public void setEndDate(String date)
    {
        if(date != null)
		{
            endDate = date;
		}
    }

    public void setSortOrderStr(String str) 
	{
        if (str != null)
		{
            sortOrderStr = str;
		}
    }

    public void setEcoCodeStr(String str)
    {
        if(str != null)
		{
            ecoCodeStr = str;
		}
    }

    public void setBeforeQuantityDbl(Double num)
    {
        if(num != null)
		{
            beforeQuantityDbl = num;
		}
    }

    public void setBeforeUnitStr(String str)
    {
        if(str != null)
		{
            beforeUnitStr = str;
		}
    }
    
    public void setBeforeSupplyTypeStr(String str)
    {
        if(str != null)
		{
            beforeSupplyTypeStr = str;
		}
    }

    public void setBeforeStartDate(String str)
    {
        if(str != null)
		{
            beforeStartDate = str;
		}
    }

    public void setBeforeEndDate(String str)
    {
        if(str != null)
		{
            beforeEndDate = str;
		}
    }

	//shin...........................
	 public void setSeqInt(Integer num)
    {
        if(num != null)
		{
            seqInt = num;
		}
    }

    public void setLevelInt(Integer num)
    {
        if(num != null)
		{
            levelInt = num;
		}
    }

	public void setItemSeqInt(Integer num)
    {
        if(num != null)
		{
            itemSeqInt = num;
		}
    }
	
    public void setVersionStr(String str)
    {
	        if(str != null)
			{
	            versionStr = str;
			}
    }
    
    public void setNewFlagStr(String str) 
	{
        if (str != null)
		{
            newFlagStr = str;
		}
    }

    public void setFirstMarkStr(String str) 
	{
        if (str != null)
		{
            firstMarkStr = str;
		}
    }

    public void setSecondMarkStr(String str) 
	{
        if (str != null)
		{
            secondMarkStr = str;
		}
    }
    
    public void setMaterialStr(String str) 
	{
        if (str != null)
		{
        	materialStr = str;
		}
    }
    
    public void setHardnessFrom(String str) 
	{
        if (str != null)
		{
        	hardnessFrom = str;
		}
    }
    
    public void setHardnessTo(String str) 
	{
        if (str != null)
		{
        	hardnessTo = str;
		}
    }
    
    public void setDesignDate(String str) 
	{
        if (str != null)
		{
        	designDate = str;
		}
    }

	public void setStatusKrStr(String str)
    {
        if(str != null)
		{
            statusKrStr = str;
		}
    }
	//...............................

    public String toString()
    {
        return substituteItemCodeStr;
    }

    public BOMSubAssyComponent createNewComponent()
    {
        BOMSubAssyComponent newComponent = new BOMSubAssyComponent(getSubstituteItemCodeStr());
        newComponent.setParentItemCodeStr(getParentItemCodeStr());
        newComponent.setChildItemCodeStr(getChildItemCodeStr());
        newComponent.setDescStr(getDescStr());
        newComponent.setStatusStr(getStatusStr());
        newComponent.setUitStr(getUitStr());
        newComponent.setUomStr(getUomStr());
        newComponent.setQuantityDbl(getQuantityDbl());
        newComponent.setSupplyTypeStr(getSupplyTypeStr());
        newComponent.setStartDate(getStartDate());
        newComponent.setEndDate(getEndDate());
		newComponent.setSortOrderStr(getSortOrderStr());

		newComponent.setEcoCodeStr(getEcoCodeStr());
		newComponent.setBeforeQuantityDbl(getBeforeQuantityDbl());
		newComponent.setBeforeUnitStr(getBeforeUnitStr());
		newComponent.setBeforeSupplyTypeStr(getBeforeSupplyTypeStr());
		newComponent.setBeforeStartDate(getBeforeStartDate());
		newComponent.setBeforeEndDate(getBeforeEndDate());

		//shin...
		newComponent.setSeqInt(getSeqInt());
		newComponent.setLevelInt(getLevelInt());
		newComponent.setItemSeqInt(getItemSeqInt());
		newComponent.setVersionStr(getVersionStr());
		newComponent.setNewFlagStr(getNewFlagStr());
		newComponent.setFirstMarkStr(getFirstMarkStr());
		newComponent.setSecondMarkStr(getSecondMarkStr());
		newComponent.setMaterialStr(getMaterialStr());
		newComponent.setHardnessFrom(getHardnessFrom());
		newComponent.setHardnessTo(getHardnessTo());
		newComponent.setDesignDate(getDesignDate());
		newComponent.setStatusKrStr(getStatusKrStr());
		
		 return newComponent;
    }

}
