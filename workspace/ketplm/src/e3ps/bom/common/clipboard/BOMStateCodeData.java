package e3ps.bom.common.clipboard;

public class BOMStateCodeData 
{
    private String code = "";
    private String value = "";

    public BOMStateCodeData(String code, String value) 
	{
        if (code != null)
            this.code = code;
        if (value != null)
            this.value = value;
    }

    public String getCode() 
	{
        return code;
    }

    public String getValue() 
	{
        return value;
    }

    public String toString() 
	{
        if (code.trim().length() == 0 && value.trim().length() ==0)
            return "";

        return value;
    }

    public boolean equals(Object target) 
	{
        if (target instanceof BOMStateCodeData) 
		{
            BOMStateCodeData targetData = (BOMStateCodeData)target;
            if (code.equalsIgnoreCase(targetData.getCode()) && value.equalsIgnoreCase(targetData.getValue()) )
                return true;
        }
        return false;
    }

}
