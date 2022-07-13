package e3ps.project.beans;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.part.WTPartMaster;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.util.WTException;
import e3ps.common.query.SearchUtil;
import ext.ket.shared.log.Kogger;

public class ProjectPartMasterHelper {
	public static final ProjectPartMasterHelper manager = new ProjectPartMasterHelper();
	public boolean checkPartMaster(String pjtNo) {
		//select * from wtpartmaster where wtpartnumber = '51303025'
		
		QuerySpec spec = null;
		QueryResult result = null;
		
		try {
			spec = new QuerySpec();
			
			Class target = WTPartMaster.class;
			int idx_target = spec.addClassList(target, true);
			
			SearchUtil.appendEQUAL(spec, target, "number", pjtNo.trim(), idx_target);
			result = PersistenceHelper.manager.find(spec);
			if(result.size() > 0) return true;
		} catch (QueryException e) {
			Kogger.error(getClass(), e);
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
		
		return false;
	}
}
