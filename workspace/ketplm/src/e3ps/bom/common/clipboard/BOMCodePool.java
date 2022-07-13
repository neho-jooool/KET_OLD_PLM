package e3ps.bom.common.clipboard;

import java.util.Hashtable;

public class BOMCodePool
{
    private static Hashtable supplyType = new Hashtable();
	private static Hashtable reasonProd = new Hashtable();		// 제품 변경사유
	private static Hashtable reasonMold = new Hashtable();	// 금형 변경사유
	private static Hashtable reasonDetail = new Hashtable();

    public BOMCodePool()
    {
    }

    public static final Hashtable getSupplyType()
    {
        return supplyType;
    }

    public static final Hashtable getProdReason()
    {
        return reasonProd;
    }
    
    public static final Hashtable getMoldReason()
    {
        return reasonMold;
    }

    public static final Hashtable getReasonDetail()
    {
        return reasonDetail;
    }

    public static final void setSupplyType(Hashtable h)
    {
        supplyType = h;
    }

    public static final void setProdReason(Hashtable h)
    {
        reasonProd = h;
    }
    
    public static final void setMoldReason(Hashtable h)
    {
        reasonMold = h;
    }

    public static final void setReasonDetail(Hashtable h)
    {
        reasonDetail = h;
    }

}
