package e3ps.bom.command.searchappliedproduct;

public class SearchAppliedProductData
{
	private String noStr;
	private String productStr;
	private String parentItemCodeStr;
	private String descriptionStr;
	private String userItemTypeStr;
	private String statusStr;
	private String statusKrStr;
	private String uomStr;
	private Double quantityDbl;
	private String supplyTypeStr;
	private String ecoNoStr;
	private String startDateStr;
	private String endDateStr;
	protected String ecoCodeStr;

    public SearchAppliedProductData()
	{
		noStr = "";
		productStr = "";
		parentItemCodeStr = "";
		descriptionStr = "";
		userItemTypeStr = "";
		statusStr = "";
		uomStr = "";
		quantityDbl = new Double(1.0);
		supplyTypeStr = "";
		ecoNoStr = "";
		startDateStr = "";
		endDateStr = "";
		statusKrStr = "";
		ecoCodeStr = "";
    }

    //////////////////////////////////////////////////////////////////////
    // Get Method

    public String getNoStr()
	{
        return noStr;
    }

    public String getProductStr()
	{
        return productStr;
    }

	public String getParentItemCodeStr()
	{
        return parentItemCodeStr;
    }

	public String getDescriptionStr()
	{
        return descriptionStr;
    }

	public String getUserItemTypeStr()
	{
        return userItemTypeStr;
    }

	public String getStatusStr()
	{
        return statusStr;
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

	public String getEcoNoStr()
	{
        return ecoNoStr;
    }

	public String getStartDateStr()
	{
        return startDateStr;
    }

	public String getEndDateStr()
	{
        return endDateStr;
    }

	public String getStatusKrStr()
	{
        return statusKrStr;
    }

	public String getEcoCodeStr()
	{
		return ecoCodeStr;
	}
	
    ////////////////////////////////////////////////////////////////////////
    // Set Method
    public void setNoStr(String s)
	{
        if (s != null)
            noStr = s;
    }

    public void setProductStr(String s)
	{
        if (s != null)
            productStr = s;
    }
    
	public void setParentItemCodeStr(String s)
	{
        if (s != null)
            parentItemCodeStr = s;
    }
    
	public void setDescriptionStr(String s)
	{
        if (s != null)
            descriptionStr = s;
    }
    
	public void setUserItemTypeStr(String s)
	{
        if (s != null)
            userItemTypeStr = s;
    }

	public void setStatusStr(String s)
	{
        if (s != null)
            statusStr = s;
    }
    
	public void setUomStr(String s)
	{
        if (s != null)
            uomStr = s;
    }

    public void setQuantityDbl(Double dbl) 
	{
        if (dbl != null)
            quantityDbl = dbl;
    }
    
	public void setSupplyTypeStr(String s)
	{
        if (s != null)
            supplyTypeStr = s;
    }
    
	public void setEcoNoStr(String s)
	{
        if (s != null)
            ecoNoStr = s;
    }

	public void setStartDateStr(String s)
	{
        if (s != null)
            startDateStr = s;
    }

	public void setEndDateStr(String s)
	{
        if (s != null)
            endDateStr = s;
    }
	
	public void setStatusKrStr(String s)
	{
        if (s != null)
            statusKrStr = s;
    }

	public void setEcoCodeStr(String str)
	{
        if (str != null)
		{
            ecoCodeStr = str;
		}
	}

}
