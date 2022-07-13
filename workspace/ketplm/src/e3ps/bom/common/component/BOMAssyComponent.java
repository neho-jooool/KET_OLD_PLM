package e3ps.bom.common.component;

import java.io.Serializable;
import java.util.Vector;

public class BOMAssyComponent implements Serializable
{
	private static final long serialVersionUID = 1L;
	protected String componentTypeStr;
    public static final String ASSY_TYPE = "1";
    public static final String MODEL_TYPE = "2";
    public static final String PART_TYPE = "3";
	public final static String COMMONASSY_TYPE = "4";

    protected String itemCodeStr;
    protected String reviseStr;
    protected String checkOutStr;
    protected Integer seqInt;
    protected Integer levelInt;
    protected Integer itemSeqInt;
    protected Integer opSeqInt;
    protected String descStr;
    protected String versionStr;
    protected String statusStr;
    protected String uitStr;
    protected String uomStr;
    protected Double quantityDbl;
    protected Double boxQuantityDbl;
    protected String costRollUpStr;
    protected String supplyTypeStr;
    protected Vector designatorComponent;
    protected Vector subAssyComponent;
    protected String ecoNoStr;
    protected String startDate;
    protected String endDate;
    protected String parentItemCodeStr;
	protected String sortOrderStr;
	protected String newFlagStr;
	protected String firstMarkStr;
	protected String secondMarkStr;
    protected Vector sourceAssyComponent;
    protected Vector targetAssyComponent;

    protected String ecoHeaderNumberStr;
	protected String ecoItemCodeStr;
	protected String ecoCodeStr;			//  Add/Remove/Update/공백(변경이 없는 경우)
	protected Double beforeQuantityDbl;
	protected String beforeUnit;
	protected String beforeMaterial;
	protected String beforeHardnessFrom;
	protected String beforeHardnessTo;
	protected String beforeDesignDate;
	protected String beforeCostRollUpStr;
	protected String beforeSupplyTypeStr;
	protected String beforeStartDate;
	protected String beforeEndDate;
	protected String beforeBomType;
	protected Double beforeBoxQuantityDbl;

	protected String basicAssyModel;

	//shin...
	protected String materialStr;
	protected String hardnessFrom;
	protected String hardnessTo;
	protected String designDate;
	protected String statusKrStr;
	protected String bomType;
	protected String isDeleted;

	//shin....confirm용
	protected String orgViewStr;
	protected String userDeptStr;
	protected String userNmStr;
	protected String userDutyStr;
	protected String userIdStr;
	protected String userEmailStr;

	//shin.....gen
	protected String subitemCodeStr;  //대체부품코드
	protected String subitemNameStr;  //대체부품명

	/////////////////////////////////////////////////
	// Added by MJOH, 2011-04-07
	// 제품/금형 부품 유형 구분을 위한 속성
	protected String ibaPartType;
	/////////////////////////////////////////////////

    public BOMAssyComponent()
    {
        componentTypeStr = "";
        itemCodeStr = "";
        reviseStr = "";
        checkOutStr = "";
        seqInt = new Integer(0);
        levelInt = new Integer(0);
        itemSeqInt = new Integer(0);
        opSeqInt = new Integer(0);
        descStr = "";
        versionStr = "";
        statusStr = "";
        uitStr = "";
        uomStr = "";
        quantityDbl = new Double(1.0D);
        boxQuantityDbl = new Double(1.0D);
        costRollUpStr = "";
        supplyTypeStr = "";
        designatorComponent = new Vector();
        subAssyComponent = new Vector();
        ecoNoStr = "";
        startDate = "";
        endDate = "";
        parentItemCodeStr = "";
		sortOrderStr = "";
		newFlagStr = "";
        firstMarkStr = "";
        secondMarkStr = "";
        sourceAssyComponent = new Vector();
        targetAssyComponent = new Vector();
        itemCodeStr = "Empty";
        setComponentTypeStr("3");

        ecoHeaderNumberStr = "";
        ecoItemCodeStr = "";
		ecoCodeStr = "";				//  Add/Remove/Update/공백(변경이 없는 경우)
		beforeQuantityDbl = new Double(1.0D);
		beforeUnit = "";
		beforeMaterial = "";
		beforeHardnessFrom = "";
		beforeHardnessTo = "";
		beforeDesignDate = "";
		beforeCostRollUpStr = "";
		beforeSupplyTypeStr = "";
		beforeStartDate = "";
		beforeEndDate = "";
		beforeBomType = "";
		beforeBoxQuantityDbl = new Double(1.0D);

		basicAssyModel = "";

		//shin...
		materialStr = "";
    	hardnessFrom = "";
    	hardnessTo = "";
    	designDate = "";
    	statusKrStr = "";
    	bomType = "";
    	isDeleted = "";

    	 orgViewStr = "";
    	 userDeptStr = "";
    	 userNmStr = "";
    	 userDutyStr = "";
    	 userIdStr = "";
    	 userEmailStr = "";

    	//shin.....gen
 		subitemCodeStr = "";  //대체부품코드
 		subitemNameStr = "";  //대체부품명

 		/////////////////////////////////////////////////
 		// Added by MJOH, 2011-04-07
 		// 제품/금형 부품 유형 구분을 위한 속성
 		ibaPartType = "";
 		/////////////////////////////////////////////////
    }

    public BOMAssyComponent(String itemCodeStr)
    {
        componentTypeStr = "";
        this.itemCodeStr = "";
        reviseStr = "";
        checkOutStr = "";
        seqInt = new Integer(0);
        levelInt = new Integer(0);
        itemSeqInt = new Integer(0);
        opSeqInt = new Integer(0);
        descStr = "";
        versionStr = "";
        statusStr = "";
        uitStr = "";
        uomStr = "";
        quantityDbl = new Double(1.0D);
        boxQuantityDbl = new Double(1.0D);
        costRollUpStr = "";
        supplyTypeStr = "";
        designatorComponent = new Vector();
        subAssyComponent = new Vector();
        ecoNoStr = "";
        startDate = "";
        endDate = "";
        parentItemCodeStr = "";
		sortOrderStr = "";
		newFlagStr = "";
        firstMarkStr = "";
        secondMarkStr = "";
        sourceAssyComponent = new Vector();
        targetAssyComponent = new Vector();
        this.itemCodeStr = itemCodeStr;
        setComponentTypeStr("3");

        ecoHeaderNumberStr = "";
        ecoItemCodeStr = "";
		ecoCodeStr = "";					//  Add/Remove/Update/공백(변경이 없는 경우)
		beforeQuantityDbl = new Double(1.0D);
		beforeUnit = "";
		beforeMaterial = "";
		beforeHardnessFrom = "";
		beforeHardnessTo = "";
		beforeDesignDate = "";
		beforeCostRollUpStr = "";
		beforeSupplyTypeStr = "";
		beforeStartDate = "";
		beforeEndDate = "";
		beforeBomType = "";
		beforeBoxQuantityDbl = new Double(1.0D);

		basicAssyModel = "";

		//shin...
		materialStr = "";
    	hardnessFrom = "";
    	hardnessTo = "";
    	designDate = "";
    	statusKrStr = "";
    	bomType = "";
    	isDeleted = "";

    	 orgViewStr = "";
    	 userDeptStr = "";
    	 userNmStr = "";
    	 userDutyStr = "";
    	 userIdStr = "";
    	 userEmailStr = "";

    	//shin.....gen
		subitemCodeStr = "";  //대체부품코드
		subitemNameStr = "";  //대체부품명

		/////////////////////////////////////////////////
		// Added by MJOH, 2011-04-07
		// 제품/금형 부품 유형 구분을 위한 속성
		ibaPartType = "";
		/////////////////////////////////////////////////
    }

    public BOMAssyComponent(String itemCodeStr, boolean flag)
    {
        componentTypeStr = "";
        this.itemCodeStr = "";
        reviseStr = "";
        checkOutStr = "";
        seqInt = new Integer(0);
        levelInt = new Integer(0);
        itemSeqInt = new Integer(0);
        opSeqInt = new Integer(0);
        descStr = "";
        versionStr = "";
        statusStr = "";
        uitStr = "";
        uomStr = "";
        quantityDbl = new Double(1.0D);
        boxQuantityDbl = new Double(1.0D);
        costRollUpStr = "";
        supplyTypeStr = "";
        designatorComponent = new Vector();
        subAssyComponent = new Vector();
        ecoNoStr = "";
        startDate = "";
        endDate = "";
        parentItemCodeStr = "";
        sourceAssyComponent = new Vector();
        targetAssyComponent = new Vector();
        this.itemCodeStr = itemCodeStr;
        if(flag)
		{
            setComponentTypeStr("2");
		}
        setLevelInt(new Integer(0));
        setSeqInt(new Integer(0));
		setSortOrderStr("0001");
		newFlagStr = "";
        firstMarkStr = "";
        secondMarkStr = "";

        ecoHeaderNumberStr = "";
        ecoItemCodeStr = "";
		ecoCodeStr = "";
		beforeQuantityDbl = new Double(1.0D);
		beforeUnit = "";
		beforeMaterial = "";
		beforeHardnessFrom = "";
		beforeHardnessTo = "";
		beforeDesignDate = "";
		beforeCostRollUpStr = "";
		beforeSupplyTypeStr = "";
		beforeStartDate = "";
		beforeEndDate = "";
		beforeBomType = "";
		beforeBoxQuantityDbl = new Double(1.0D);

		basicAssyModel = "";

		//shin...
		materialStr = "";
    	hardnessFrom = "";
    	hardnessTo = "";
    	designDate = "";
    	statusKrStr = "";
    	bomType = "";
    	isDeleted = "";

    	 orgViewStr = "";
    	 userDeptStr = "";
    	 userNmStr = "";
    	 userDutyStr = "";
    	 userIdStr = "";
    	 userEmailStr = "";

    	//shin.....gen
 		subitemCodeStr = "";  //대체부품코드
 		subitemNameStr = "";  //대체부품명

 		/////////////////////////////////////////////////
 		// Added by MJOH, 2011-04-07
 		// 제품/금형 부품 유형 구분을 위한 속성
 		ibaPartType = "";
 		/////////////////////////////////////////////////
    }

    public String getComponentTypeStr()
    {
        return componentTypeStr;
    }

    public String getItemCodeStr()
    {
        return itemCodeStr;
    }

    public String getReviseStr()
    {
        return reviseStr;
    }

    public String getCheckOutStr()
    {
        return checkOutStr;
    }

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

    public Integer getOpSeqInt()
    {
        return opSeqInt;
    }

    public String getDescStr()
    {
        return descStr;
    }

    public String getVersionStr()
    {
        return versionStr;
    }

    public String getStatusStr()
    {
        return statusStr;
    }

    public String getUitStr()
    {
        return uitStr;
    }

    public String getUomStr()
    {
        return uomStr;
    }

    public Double getQuantityDbl()
    {
        return quantityDbl;
    }

    public Double getBoxQuantityDbl()
    {
        return boxQuantityDbl;
    }

    public String getCostRollUpStr()
    {
        return costRollUpStr;
    }

    public String getSupplyTypeStr()
    {
        return supplyTypeStr;
    }

    public Vector getDesignatorComponent()
    {
        return designatorComponent;
    }

    public Vector getSubAssyComponent()
    {
        return subAssyComponent;
    }

    public String getEcoNoStr()
    {
        return ecoNoStr;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public String getParentItemCodeStr()
    {
        return parentItemCodeStr;
    }

    public String getSortOrderStr()
    {
        return sortOrderStr;
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

    public Vector getSourceAssyComponent()
    {
        return sourceAssyComponent;
    }

    public Vector getTargetAssyComponent()
    {
        return targetAssyComponent;
    }

	public String getEcoHeaderNumberStr()
	{
		return ecoHeaderNumberStr;
	}

	public String getEcoItemCodeStr()
	{
		return ecoItemCodeStr;
	}

	public String getEcoCodeStr()
	{
		return ecoCodeStr;
	}

	public Double getBeforeQuantityDbl()
	{
		return beforeQuantityDbl;
	}

	public Double getBeforeBoxQuantityDbl()
	{
		return beforeBoxQuantityDbl;
	}

	public String getBeforeUnitStr()
	{
		return beforeUnit;
	}

	public String getBeforeMaterialStr()
	{
		return beforeMaterial;
	}

	public String getBeforeHardnessFrom()
	{
		return beforeHardnessFrom;
	}

	public String getBeforeHardnessTo()
	{
		return beforeHardnessTo;
	}

	public String getBeforeDesignDate()
	{
		return beforeDesignDate;
	}

	public String getBeforeCostRollUpStr()
	{
		return beforeCostRollUpStr;
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

	public String getBeforeBomType()
	{
		return beforeBomType;
	}

	public Double getBeforeBoxQuantity()
	{
		return beforeBoxQuantityDbl;
	}

	public String getBasicAssyModel()
	{
		return basicAssyModel;
	}

	//shin....
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

    public String getStatusKrStr()
    {
        return statusKrStr;
    }

	public String getBomType()
	{
		return bomType;
	}

	public String getIsDeleted()
	{
		return isDeleted;
	}

    public String getOrgViewStr()
    {
        return orgViewStr;
    }
    public String getUserDeptStr()
    {
        return userDeptStr;
    }
    public String getUserNmStr()
    {
        return userNmStr;
    }
    public String getUserDutyStr()
    {
        return userDutyStr;
    }
    public String getUserIdStr()
    {
        return userIdStr;
    }
    public String getUserEmailStr()
    {
        return userEmailStr;
    }

    public String getSubItemCodeStr()
    {
        return subitemCodeStr;
    }

    public String getSubItemNameStr()
    {
        return subitemNameStr;
    }

	/////////////////////////////////////////////////
	// Added by MJOH, 2011-04-07
	// 제품/금형 부품 유형 구분을 위한 속성
    public String getIBAPartType()
    {
        return ibaPartType;
    }
	/////////////////////////////////////////////////

    public void setComponentTypeStr(String type)
    {
        componentTypeStr = type;
    }

    public void setItemCodeStr(String itemCodeStr)
    {
        this.itemCodeStr = itemCodeStr;
    }

    public void setReviseStr(String str)
    {
        if(str != null)
		{
            reviseStr = str;
		}
    }

    public void setCheckOutStr(String str)
    {
        if(str != null)
		{
            checkOutStr = str;
		}
    }

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

    public void setOpSeqInt(Integer num)
    {
        if(num != null)
		{
            opSeqInt = num;
		}
    }

    public void setDescStr(String str)
    {
        if(str != null)
		{
            descStr = str;
		}
    }

    public void setVersionStr(String str)
    {
        if(str != null)
		{
            versionStr = str;
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

    public void setBoxQuantityDbl(Double num)
    {
        if(num != null)
		{
            boxQuantityDbl = num;
		}
    }

    public void setCostRollUpStr(String str)
    {
        if(str != null)
		{
            costRollUpStr = str;
		}
    }

    public void setSupplyTypeStr(String str)
    {
        if(str != null)
		{
            supplyTypeStr = str;
		}
    }

    public void setDesignatorComponent(Vector vec)
    {
        designatorComponent = vec;
    }

    public void setSubAssyComponent(Vector vec)
    {
        subAssyComponent = vec;
    }

    public void setEcoNoStr(String str)
    {
        if(str != null)
		{
            ecoNoStr = str;
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

    public void setParentItemCodeStr(String str)
    {
        if(str != null)
		{
            parentItemCodeStr = str;
		}
    }

    public void setSortOrderStr(String str)
	{
        if (str != null)
		{
            sortOrderStr = str;
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

    public void setSourceAssyComponent(Vector vec)
    {
        sourceAssyComponent = vec;
    }

    public void setTargetAssyComponent(Vector vec)
    {
        targetAssyComponent = vec;
    }

	public void setEcoHeaderNumberStr(String str)
	{
        if (str != null)
		{
            ecoHeaderNumberStr = str;
		}
	}

	public void setEcoItemCodeStr(String str)
	{
        if (str != null)
		{
            ecoItemCodeStr = str;
		}
	}

	public void setEcoCodeStr(String str)
	{
        if (str != null)
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
        if (str != null)
		{
            beforeUnit = str;
		}
    }

    public void setBeforeMaterialStr(String str)
	{
        if (str != null)
		{
            beforeMaterial = str;
		}
    }

    public void setBeforeHardnessFrom(String str)
	{
        if (str != null)
		{
            beforeHardnessFrom = str;
		}
    }

    public void setBeforeHardnessTo(String str)
	{
        if (str != null)
		{
            beforeHardnessTo = str;
		}
    }

    public void setBeforeDesignDate(String str)
	{
        if (str != null)
		{
            beforeDesignDate = str;
		}
    }

    public void setBeforeCostRollUpStr(String str)
	{
        if (str != null)
		{
            beforeCostRollUpStr = str;
		}
    }

    public void setBeforeSupplyTypeStr(String str)
	{
        if (str != null)
		{
            beforeSupplyTypeStr = str;
		}
    }

    public void setBeforeStartDate(String str)
	{
        if (str != null)
		{
            beforeStartDate = str;
		}
    }

    public void setBeforeEndDate(String str)
	{
        if (str != null)
		{
            beforeEndDate = str;
		}
    }

    public void setBeforeBomType(String str)
	{
        if (str != null)
		{
            beforeBomType = str;
		}
    }

    public void setBeforeBoxQuantityDbl(Double num)
    {
        if(num != null)
		{
        	beforeBoxQuantityDbl = num;
		}
    }

    public void setBasicAssyModel(String str)
    {
		basicAssyModel = str;
    }

	//shin......
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

    public void setBomType(String str)
	{
        if (str != null)
		{
            bomType = str;
		}
    }

    public void setIsDeleted(String str)
	{
        if (str != null)
		{
            isDeleted = str;
		}
    }

    public void setOrgViewStr(String str)
    {
        if(str != null)
		{
        	orgViewStr = str;
		}
    }
    public void setUserDeptStr(String str)
    {
        if(str != null)
		{
        	userDeptStr = str;
		}
    }
    public void setUserNmStr(String str)
    {
        if(str != null)
		{
        	userNmStr = str;
		}
    }
    public void setUserDutyStr(String str)
    {
        if(str != null)
		{
        	userDutyStr = str;
		}
    }
    public void setUserIdStr(String str)
    {
        if(str != null)
		{
        	userIdStr = str;
		}
    }
    public void setUserEmailStr(String str)
    {
        if(str != null)
		{
        	userEmailStr = str;
		}
    }

    public void setSubItemCodeStr(String str)
    {
        if(str != null)
		{
        	subitemCodeStr = str;
		}
    }
    public void setSubItemnameStr(String str)
    {
        if(str != null)
		{
        	subitemNameStr = str;
		}
    }

	/////////////////////////////////////////////////
	// Added by MJOH, 2011-04-07
	// 제품/금형 부품 유형 구분을 위한 속성
    public void setIBAPartType( String str )
    {
        if(str != null)
		{
        	ibaPartType = str;
		}
    }
	/////////////////////////////////////////////////

    public String toString()
    {
        return itemCodeStr;
    }

    public BOMAssyComponent createNewComponent()
    {
        BOMAssyComponent newComponent = new BOMAssyComponent( getItemCodeStr() );

        newComponent.setComponentTypeStr( getComponentTypeStr() );
        newComponent.setReviseStr( getReviseStr() );
        newComponent.setCheckOutStr( getCheckOutStr() );
        newComponent.setSeqInt( getSeqInt() );
        newComponent.setLevelInt( getLevelInt() );
        newComponent.setItemSeqInt( getItemSeqInt() );
        newComponent.setOpSeqInt( getOpSeqInt() );
        newComponent.setDescStr( getDescStr() );
        newComponent.setVersionStr( getVersionStr() );
        newComponent.setStatusStr( getStatusStr() );
        newComponent.setUitStr( getUitStr() );
        newComponent.setUomStr( getUomStr() );
        newComponent.setQuantityDbl( getQuantityDbl() );
        newComponent.setBoxQuantityDbl( getBoxQuantityDbl() );
        newComponent.setCostRollUpStr( getCostRollUpStr() );
        newComponent.setSupplyTypeStr( getSupplyTypeStr() );
        newComponent.setDesignatorComponent( getDesignatorComponent() );
        newComponent.setSubAssyComponent( getSubAssyComponent() );
        newComponent.setEcoNoStr( getEcoNoStr() );
        newComponent.setStartDate( getStartDate() );
        newComponent.setEndDate( getEndDate() );
        newComponent.setParentItemCodeStr( getParentItemCodeStr() );
		newComponent.setSortOrderStr( getSortOrderStr() );
		newComponent.setNewFlagStr( getNewFlagStr() );
		newComponent.setFirstMarkStr( getFirstMarkStr() );
		newComponent.setSecondMarkStr( getSecondMarkStr() );
        newComponent.setSourceAssyComponent( getSourceAssyComponent() );
        newComponent.setTargetAssyComponent( getTargetAssyComponent() );

		newComponent.setEcoHeaderNumberStr( getEcoHeaderNumberStr() );
		newComponent.setEcoItemCodeStr( getEcoItemCodeStr() );
		newComponent.setEcoCodeStr( getEcoCodeStr() );
        newComponent.setBeforeBoxQuantityDbl( getBeforeBoxQuantityDbl() );
        newComponent.setBeforeQuantityDbl( getBeforeQuantityDbl() );
        newComponent.setBeforeUnitStr( getBeforeUnitStr() );
        newComponent.setBeforeMaterialStr( getBeforeMaterialStr() );
        newComponent.setBeforeHardnessFrom( getBeforeHardnessFrom() );
        newComponent.setBeforeHardnessTo( getBeforeHardnessTo() );
        newComponent.setBeforeDesignDate( getBeforeDesignDate() );
        newComponent.setBeforeCostRollUpStr( getBeforeCostRollUpStr() );
        newComponent.setBeforeSupplyTypeStr( getBeforeSupplyTypeStr() );
        newComponent.setBeforeStartDate( getBeforeStartDate() );
        newComponent.setBeforeEndDate( getBeforeEndDate() );

		newComponent.setBasicAssyModel( getBasicAssyModel() );

		//shin....
		newComponent.setMaterialStr( getMaterialStr() );
		newComponent.setHardnessFrom( getHardnessFrom() );
		newComponent.setHardnessTo( getHardnessTo() );
		newComponent.setDesignDate( getDesignDate() );
		newComponent.setStatusKrStr( getStatusKrStr() );

		newComponent.setOrgViewStr( getOrgViewStr() );
		newComponent.setUserDeptStr( getUserDeptStr() );
		newComponent.setUserNmStr( getUserNmStr() );
		newComponent.setUserDutyStr( getUserDutyStr() );
		newComponent.setUserIdStr( getUserIdStr() );
		newComponent.setUserEmailStr( getUserEmailStr() );

		newComponent.setSubItemCodeStr( getSubItemCodeStr() );
		newComponent.setSubItemnameStr( getSubItemNameStr() );

		//////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-04-07
		// 제품/금형 부품 유형 구분을 위한 속성
		newComponent.setIBAPartType( getIBAPartType() );
		//////////////////////////////////////////////////////////////

        return newComponent;
    }
}
