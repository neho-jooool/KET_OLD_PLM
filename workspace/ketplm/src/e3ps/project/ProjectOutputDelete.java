package e3ps.project;

import wt.fc.PersistenceHelper;
import e3ps.common.util.CommonUtil;
import ext.ket.shared.log.Kogger;

public class ProjectOutputDelete {

    public static void main(String args[]) {
	if (args != null) {
	    try {
		String projectOutputOid = args[0];
		ProjectOutput output = (ProjectOutput) CommonUtil.getObject("e3ps.project.ProjectOutput:" + projectOutputOid);
		PersistenceHelper.manager.delete(output);
	    } catch (Exception e) {
		Kogger.error(ProjectOutputDelete.class, e);
	    }
	}
    }
}
