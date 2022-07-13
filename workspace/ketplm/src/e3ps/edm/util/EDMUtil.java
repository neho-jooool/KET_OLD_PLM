package e3ps.edm.util;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import wt.pdmlink.PDMLinkProduct;
import wt.query.QuerySpec;
import wt.query.SearchCondition;

public class EDMUtil {
	
	public static WTContainerRef getWTContinerRefByEDMDefault() throws Exception {
		EDMProperties edmProperties = EDMProperties.getInstance();
		return getWTContainerRef(edmProperties.getContainer());
	}
	
    public static WTContainerRef getWTContainerRef(String name) throws Exception {
        PDMLinkProduct wtProduct = null;
        if((name != null) && (name.trim().length() > 0)) {
        	wtProduct = getPDMLinkProduct(name);
        }
        
        WTContainerRef wtContainerRef = null;
        if (wtProduct != null) {
            wtContainerRef = WTContainerRef.newWTContainerRef(wtProduct);
        } else {
        	wtContainerRef = WTContainerHelper.service.getClassicRef();
        	//wtContainerRef = WTContainerHelper.service.getExchangeRef();
        	//wtContainerRef = WTContainerHelper.getExchangeRef();
        	//wtContainerRef = WTContainerHelper.getClassicRef();
        }

        return wtContainerRef;
    }

    public static PDMLinkProduct getPDMLinkProduct(String name) throws Exception
    {
        QuerySpec qs = new QuerySpec(PDMLinkProduct.class);
        SearchCondition sc1 = new SearchCondition(PDMLinkProduct.class, PDMLinkProduct.NAME, SearchCondition.EQUAL, name);
        qs.appendSearchCondition(sc1);
        QueryResult results = (QueryResult) PersistenceHelper.manager.find(qs);
        PDMLinkProduct wtProduct = null;
        if (results.hasMoreElements())
        {
            wtProduct = (PDMLinkProduct) results.nextElement();
        }

        return wtProduct;
    }

}
