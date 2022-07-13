package e3ps.common.web;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.pds.StatementSpec;


public class QueryBroker implements wt.method.RemoteAccess, java.io.Serializable {
	
	//boolean isAdvanced = false;
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;
	
	static public QueryResult search(StatementSpec qs, boolean isAdvanced) throws Exception{
		
		if(isAdvanced && !SERVER){
			Class argTypes[] = new Class[]{StatementSpec.class, boolean.class};
			Object args[] = new Object[]{qs, new Boolean(isAdvanced)};
			return (QueryResult)wt.method.RemoteMethodServer.getDefault().invoke(
					"search",
					"e3ps.common.web.QueryBroker",
					null,
					argTypes,
					args);
		}

		if(isAdvanced && !qs.isAdvancedQueryEnabled()) {
			qs.setAdvancedQueryEnabled(true);
		}
		
		return PersistenceHelper.manager.find(qs);
	}
	
}
