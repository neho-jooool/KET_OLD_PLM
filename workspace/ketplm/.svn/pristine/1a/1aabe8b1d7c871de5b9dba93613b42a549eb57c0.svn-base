package e3ps.project.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.KETQueryUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.MoldProject;
import e3ps.project.ProductProject;
import e3ps.project.ReviewProject;
import ext.ket.shared.log.Kogger;

/**
 * [PLM System 1차개선]
 * 클래스명 : ProjectScheduleHistoryDao
 * 설명 : Project 일정변경이력 Dao
 * 작성자 : 김무준
 * 작성일자 : 2013. 7. 17.
 */

public class ProjectScheduleHistoryDao {

    private Connection conn;

    public ProjectScheduleHistoryDao(Connection conn) {
        this.conn = conn;
    }

    public ProjectScheduleHistoryDao() {
    }

    public List<SqlRowMap> getProjectVersionList(Map _map) {
        List<SqlRowMap> list = null;

        KETParamMapUtil map = KETParamMapUtil.getMap(_map);

        try {
            String oid = map.getString("oid"); // E3PSProjectMaster oid
            String ptype = map.getString("ptype"); // P(roject) | M(old) | R(eview)
            long ida2a2 = CommonUtil.getOIDLongValue(oid);
            String type = map.getString("type"); // L(최신) | W(전체) | V(버전) | D(변경일)
            String prehist = map.getString("prehist");
            String posthist = map.getString("posthist");
            String detail = map.getString("detail"); // Y(상세) | otherwise
            String[] versionAry = map.getStringArray("version");
            String predate = map.getString("predate");
            String postdate = map.getString("postdate");

            StringBuilder qrybuf = new StringBuilder();
            qrybuf.append(" select         \n");
            qrybuf.append("   p_oid, pjtHistory, pjtIteration, planstartdate, planenddate         \n");
            qrybuf.append(" from         \n");
            qrybuf.append(" (         \n");
            qrybuf.append("   select         \n");
            qrybuf.append("     p.classnamea2a2||':'||p.ida2a2 as p_oid         \n");
            qrybuf.append("     , p.pjtHistory, p.pjtIteration         \n");
            qrybuf.append("     , to_char(s.planstartdate, 'yyyy-MM-dd') as planstartdate         \n");
            qrybuf.append("     , to_char(s.planenddate, 'yyyy-MM-dd') as planenddate         \n");
            qrybuf.append("     , p.modifystampa2         \n");
            qrybuf.append("     , p.workingCopy         \n");
            qrybuf.append("     , rank() over (partition by p.pjtHistory order by p.pjtIteration desc) as rk_ite         \n");
            qrybuf.append("     , dense_rank() over (order by p.pjtHistory desc) as rk_hist         \n");
            qrybuf.append("   from         \n");
            qrybuf.append("     E3PSProjectMaster pm         \n");
            qrybuf.append("     , ").append(getProjectTableByPtype(ptype)).append(" p         \n");
            qrybuf.append("     , ExtendScheduleData s         \n");
            qrybuf.append("   where 1=1         \n");
            qrybuf.append("     and pm.ida2a2 = p.ida3b8         \n");
            qrybuf.append("     and p.ida3a8 = s.ida2a2         \n");
            qrybuf.append("     and pm.ida2a2 = ").append(ida2a2).append("         \n");
            // 버전 범위
            if ("V".equals(type)) {
                if ("Y".equals(detail)) { // 상세
                    if (versionAry != null && versionAry.length > 0) {
                        qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("(p.pjtHistory||'.'||p.pjtIteration)", versionAry, false)).append("        \n");
                    }
                }
                else {
                    if (StringUtil.checkString(prehist)) {
                        qrybuf.append("     and p.pjtHistory >= ").append(prehist).append("         \n");
                    }
                    if (StringUtil.checkString(posthist)) {
                        qrybuf.append("     and p.pjtHistory <= ").append(posthist).append("         \n");
                    }
                }
            }
            // 변경일 범위
            if ("D".equals(type)) {
                if (StringUtil.checkString(predate)) {
                    qrybuf.append("     and p.modifystampa2 >= to_date('").append(predate).append(" 000000', 'YYYY-MM-DD HH24MISS')         \n");
                }
                if (StringUtil.checkString(postdate)) {
                    qrybuf.append("     and p.modifystampa2 <= to_date('").append(postdate).append(" 235959', 'YYYY-MM-DD HH24MISS')         \n");
                }
            }
            qrybuf.append(" ) a         \n");
            if ("V".equals(type) == false || "Y".equals(detail) == false) { // 버전 & 상세 경우 last iteration 아님
                qrybuf.append(" where rk_ite = 1         \n"); // only last iteration
            }
            // 최신
            if ("L".equals(type)) {
                qrybuf.append("   and (rk_hist in (1, 2) or pjtHistory = 0)         \n");
            }
            qrybuf.append(" order by pjtHistory, pjtIteration, workingCopy          \n");

            Kogger.debug(getClass(), "ProjectScheduleHistoryDao.getProjectVersionList: qry=\n" + qrybuf.toString());

            list = KETQueryUtil.getSqlResultList(qrybuf.toString(), conn);
            Kogger.debug(getClass(), "ProjectScheduleHistoryDao.getProjectVersionList: list size=" + list.size());
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }

        return list;
    }

    public List<SqlRowMap> getAllPjtHistoryOfProject(String ptype, long pjtMstIda2a2) {
        List<SqlRowMap> list = null;
        try {
            StringBuilder qrybuf = new StringBuilder();
            qrybuf.append(" select        \n");
            qrybuf.append("   p.pjtHistory        \n");
            qrybuf.append(" from        \n");
            qrybuf.append("   E3PSProjectMaster pm        \n");
            qrybuf.append("   , ").append(getProjectTableByPtype(ptype)).append(" p        \n");
            qrybuf.append(" where 1=1        \n");
            qrybuf.append("   and pm.ida2a2 = p.ida3b8        \n");
            qrybuf.append("   and pm.ida2a2 = ").append(pjtMstIda2a2) .append("       \n");
            qrybuf.append(" group by p.pjtHistory        \n");
            qrybuf.append(" order by p.pjtHistory        \n");

            Kogger.debug(getClass(), "ProjectScheduleHistoryDao.getAllPjtHistoryOfProject: qry=\n" + qrybuf.toString());

            list = KETQueryUtil.getSqlResultList(qrybuf.toString(), conn);
            Kogger.debug(getClass(), "ProjectScheduleHistoryDao.getAllHistoryOfProject: list size=" + list.size());
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
        return list;
    }

    public List<SqlRowMap> getVersionOfProject(String ptype, long pjtMstIda2a2, String pjtHistories) {
        List<SqlRowMap> list = null;
        try {
            StringBuilder qrybuf = new StringBuilder();
            qrybuf.append(" select        \n");
            qrybuf.append("   p.pjtHistory||'.'||p.pjtIteration as version        \n");
            qrybuf.append(" from        \n");
            qrybuf.append("   E3PSProjectMaster pm        \n");
            qrybuf.append("   , ").append(getProjectTableByPtype(ptype)).append(" p        \n");
            qrybuf.append(" where 1=1        \n");
            qrybuf.append("   and pm.ida2a2 = p.ida3b8        \n");
            qrybuf.append("   and pm.ida2a2 = ").append(pjtMstIda2a2) .append("       \n");
            qrybuf.append("   and ").append(KETQueryUtil.getSqlQueryForMultiSearch("p.pjtHistory", pjtHistories, false)).append("       \n");
            qrybuf.append(" group by p.pjtHistory, p.pjtIteration        \n");
            qrybuf.append(" order by p.pjtHistory, p.pjtIteration        \n");

            Kogger.debug(getClass(), "ProjectScheduleHistoryDao.getVersionOfProject: qry=\n" + qrybuf.toString());

            list = KETQueryUtil.getSqlResultList(qrybuf.toString(), conn);
            Kogger.debug(getClass(), "ProjectScheduleHistoryDao.getVersionOfProject: list size=" + list.size());
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
        return list;
    }

    private static String getProjectTableByPtype(String ptype) {
        String ret = null;
        if ("P".equals(ptype)) {
            ret = "ProductProject";
        }
        else if ("M".equals(ptype)) {
            ret = "MoldProject";
        }
        else if ("R".equals(ptype)) {
            ret = "ReviewProject";
        }
        else if ("W".equals(ptype)) {
            ret = "WorkProject";
        }
        return ret;
    }
    private static String getProjectTableByProjectObj(E3PSProject project) {
        String ret = null;
        if (project instanceof ProductProject) {
            ret = "ProductProject";
        }
        else if (project instanceof MoldProject) {
            ret = "MoldProject";
        }
        else if (project instanceof ReviewProject) {
            ret = "ReviewProject";
        }
        return ret;
    }
    private static String getProjectTableByProjectOid(String oid) {
        String ret = null;
        if (oid.startsWith("e3ps.project.ProductProject:")) {
            ret = "ProductProject";
        }
        else if (oid.startsWith("e3ps.project.MoldProject:")) {
            ret = "MoldProject";
        }
        else if (oid.startsWith("e3ps.project.ReviewProject:")) {
            ret = "ReviewProject";
        }
        return ret;
    }


}
