package e3ps.project.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import wt.method.RemoteAccess;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.project.dao.ProjectScheduleHistoryDao;
import ext.ket.shared.log.Kogger;

public class ProjectScheduleHistoryHelper implements Serializable,RemoteAccess {

    public static List<String> getVersionOfProject(String ptype, long pjtMstIda2a2, String pjtHistories) {
        List<String> versionList = new ArrayList<String>();

        if (StringUtil.isEmpty(pjtHistories)) {
            return versionList;
        }

        Connection conn = null;
        try {
            conn = PlmDBUtil.getConnection();

            ProjectScheduleHistoryDao dao = new e3ps.project.dao.ProjectScheduleHistoryDao(conn);
            List<SqlRowMap> rowlist = dao.getVersionOfProject(ptype, pjtMstIda2a2, pjtHistories);
            for (SqlRowMap row : rowlist) {
                versionList.add(row.getString("version"));
            }
        }
        catch(Exception e) {
            Kogger.error(ProjectScheduleHistoryHelper.class, e);
            Kogger.debug(ProjectScheduleHistoryHelper.class, "ProjectScheduleHistoryHelper.getVersionOfProject: Error: ptype=" + ptype + ", pjtMstIda2a2=" + pjtMstIda2a2 + ", pjtHistories=" + pjtHistories);
        }
        finally {
            PlmDBUtil.close(conn);
        }

        return versionList;
    }

}
