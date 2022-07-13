package e3ps.project.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.tree.TreeNode;

import org.json.JSONObject;

import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.SqlRowMap;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.ParamUtil;
import e3ps.project.E3PSProject;
import e3ps.project.E3PSTask;
import e3ps.project.MoldProject;
import e3ps.project.beans.DefaultProjectTreeNode;
import e3ps.project.beans.ProjectTreeModel;
import e3ps.project.beans.ProjectTreeNodeData;
import e3ps.project.dao.ProjectScheduleHistoryDao;
import e3ps.project.historyprocess.HistoryHelper;
import ext.ket.shared.log.Kogger;

/**
 * [PLM System 1차개선]
 * 클래스명 : ProjectScheduleHistoryServlet
 * 설명 : Project 일정변경이력 Servlet
 * 작성자 : 김무준
 * 작성일자 : 2013. 7. 17.
 */

public class ProjectScheduleHistoryServlet extends CommonServlet {

    protected void doService(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        String cmd = ParamUtil.getStrParameter(req.getParameter("cmd"));

        if (cmd.equalsIgnoreCase("search")) {
            search(req, res);
        }
        else if (cmd.equalsIgnoreCase("versionAjax")) {
            versionAjax(req, res);
        }
    }

    private void search(HttpServletRequest req, HttpServletResponse res) {
        KETParamMapUtil paramMap = KETParamMapUtil.getMap(req);

        Connection conn = null;
        try {
            conn = PlmDBUtil.getConnection();

            ProjectScheduleHistoryDao dao = new ProjectScheduleHistoryDao(conn);

            // 1. 검색조건에 따른 project version list 질의
            List<SqlRowMap> versionList = dao.getProjectVersionList(paramMap);
            if (versionList == null || versionList.size() == 0) {
                Kogger.debug(getClass(), "ProjectScheduleHistoryServlet.getProjectScheduleHistory: (versionList == null || versionList.size() == 0)");

                req.setAttribute( "searchCondition", paramMap );

                gotoResult(req, res, "/jsp/project/schedule/ViewProjectScheduleHistory.jsp");

                return;
            }

            // [START] 2. version 별로 일정 비교 및 결과 node 저장 (기존 비교 로직)

            // 버전별로 task 검색 위한 map list (one node map == one version)
            List<Map<String, DefaultProjectTreeNode>> nodeMapList = new ArrayList<Map<String, DefaultProjectTreeNode>>();

            E3PSProject prevProject = null;
            DefaultProjectTreeNode lastRootNode = null;
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < versionList.size(); ++i) {
                SqlRowMap row = versionList.get(i);

                String pjtOid = row.getString("p_oid");
                int pjtHistory = row.getInt("pjtHistory");
                int pjtIteration = row.getInt("pjtIteration");

                E3PSProject curProject = (E3PSProject) CommonUtil.getObject(pjtOid);
                DefaultProjectTreeNode rootNode; // root: project
                if (prevProject != null) {
                    rootNode = (DefaultProjectTreeNode) HistoryHelper.getCompareProject(curProject, prevProject, null);
                }
                else { // first version..
                    rootNode = ProjectTreeModel.getLoadTree(curProject);
                }
                Map<String, DefaultProjectTreeNode> nodeMap = new HashMap<String, DefaultProjectTreeNode>();
                putNodeToMap(rootNode, nodeMap);
                nodeMapList.add(nodeMap);

                prevProject = curProject;
                lastRootNode = rootNode;

                /*
                 *  version 목록 format: [0] pjtHistory, [1] pjtIteration, [2] '(작업중)', [3] project oid
                 *  (버전별 구분자=",", 항목별 구분자="/")
                 */
                if (i > 0) buf.append(",");
                buf.append(pjtHistory);
                buf.append("/").append(pjtIteration);
                if (curProject.isWorkingCopy()) {
                    buf.append("/").append("(작업중)");
                }
                else {
                    buf.append("/").append("");
                }
                buf.append("/").append(pjtOid);
            }

            int nodeMapListSize = nodeMapList.size();

            String versions = buf.toString(); // grid 컬럼을 동적으로 생성하기 위한 version 목록
//            Kogger.debug(getClass(), "ProjectScheduleHistoryServlet.getProjectScheduleHistory: versions=[" + versions + "]");

            // [END] 2. version 별로 일정 비교 및 결과 node 저장 (기존 비교 로직)

            // [START] 3. 저장된 비교 결과 tree node 정보를 Grid Data로 구성

            TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();
            DefaultProjectTreeNode node = lastRootNode; // 검색결과 중 최종 버전을 좌트리에 표시

            int prevLevel = 0;
            while (node != null) { // task 단위로 loop -> 한 task가 grid 한 라인
                int level = node.getLevel();

                // 삭제된 task 표시 안함
                if (node.getCompareResult() == DefaultProjectTreeNode.DELTE) {
                    node = (DefaultProjectTreeNode) node.getNextNode();
                    prevLevel = level;
                    continue;
                }

                boolean isLeaf = node.isLeaf();
                String id = getNodeId(node);
                String name = getNodeName(node);

                // Tree 구조에서 Summary Data 구성 완료 처리
                if ( level < prevLevel ) {
                    for (int i = 0; i < (prevLevel - level); i++) {
                        strBuffer.append( "</I> \n" );
                    }
                }

                // Grid Data 구성 - task 좌트리
                strBuffer.append( "<I" );
                strBuffer.appendRepl( " id=\"" + id + "\"" );
                strBuffer.appendRepl( " TaskName=\"" + name + "\"" );
                if ( level <= 1 ) {     // Project or 단계 Task
                    strBuffer.appendRepl( " TaskNameHtmlPrefix=\"<b>\" TaskNameHtmlPostfix=\"</b>\"" ); // Task명 Bold 처리
                }
                strBuffer.appendRepl( " CanDrag=\"0\"" );
                strBuffer.appendRepl( " CanDelete=\"0\"" );

                // Grid Data 구성 - 버전별 일정 변경 이력 (grid용 version 목록과 순서 동일하도록)
                for (int i = 0; i < nodeMapListSize; ++i) { // version 단위로 loop
                    Map<String, DefaultProjectTreeNode> nodeMap = nodeMapList.get(i);
                    DefaultProjectTreeNode tnode = nodeMap.get(getNodeKey(node));
                    if (tnode != null) {
                        int result = tnode.getCompareResult();
                        if (i == 0 || result != DefaultProjectTreeNode.DEFAULT) { // 첫 버전 날짜, 변경된 날짜 표시
                            String planStartDate = getNodePlanStartDate(tnode);
                            String planEndDate = getNodePlanEndDate(tnode);

                            strBuffer.appendRepl( " Version"+i+"_Start=\""+planStartDate+"\"" );
                            strBuffer.appendRepl( " Version"+i+"_End=\""+planEndDate+"\"" );
                        }
                    }
                }

                // Task Definition 설정 (false : Summary, true : Task)
                if (isLeaf == false) {
//                    strBuffer.appendRepl( " Def=\"Summary\"" );
                    strBuffer.append( "> \n" );
                }
                else {
//                    strBuffer.appendRepl( " Def=\"Task\"" );
                    strBuffer.append( "/> \n" );
                }

                node = (DefaultProjectTreeNode) node.getNextNode();
                prevLevel = level;
            }

            // Project 일정(Root) Grid Data 구성 완료
            strBuffer.append( "</I> \n" );

            // [END] 3. 저장된 비교 결과 tree node 정보를 Grid Data로 구성

            // 버전 콤보용 데이터
            String ptype = paramMap.getString("ptype");
            long pjtMstIda2a2 = (prevProject!=null)?CommonUtil.getOIDLongValue(prevProject.getMaster()):0;
            List<SqlRowMap> rowlist = dao.getAllPjtHistoryOfProject(ptype, pjtMstIda2a2);
            List<String> allPjtHistory = new ArrayList<String>();
            for (SqlRowMap row : rowlist) {
                allPjtHistory.add(row.toString("pjtHistory"));
            }

            req.setAttribute( "searchCondition", paramMap );
            req.setAttribute( "tgData", strBuffer.toString() );
            req.setAttribute( "versions", versions );
            req.setAttribute( "allPjtHistory", allPjtHistory );

            gotoResult(req, res, "/jsp/project/schedule/ViewProjectScheduleHistory.jsp");

        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(conn);
        }
    }

    private static String getProjectName(E3PSProject project) {
        String projectName = null;
        if ( project instanceof MoldProject ) {     // 금형 Project Name - PJT_NO (PART_NAME)
            String partName = StringUtil.checkNull(((MoldProject)project).getMoldInfo().getPartName());
            projectName = project.getPjtNo() + "(" + partName + ")";
        }
        else {                                      // 검토, 제품 Project Name - PJT_NO (PJT_NAME)
            projectName = project.getPjtNo() + "(" + project.getPjtName() + ")";
        }
        return projectName;
    }

    /*
     * grid tree에 사용
     */
    private static String getNodeName(DefaultProjectTreeNode node) {
        if (node == null) return null;

        String name = node.tmpName;
        if (name == null) {
            ProjectTreeNodeData data = (ProjectTreeNodeData) node.getUserObject();
            Object obj = data.getData();
            if (obj instanceof E3PSProject) {
                name = getProjectName((E3PSProject) obj);
            }
            else if (obj instanceof E3PSTask) {
                name = ((E3PSTask) obj).getTaskName();
            }
            node.tmpName = name;
        }
        return name;
    }
    /*
     * task 검색용 map key
     */
    private static String getNodeKey(DefaultProjectTreeNode node) {
        if (node == null) return null;

        String key = node.tmpKey;
        if (key == null) {
            // 'root~자신까지의 task명 경로'를 key로 설정
            TreeNode[] nodePath = node.getPath();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < nodePath.length; ++i) {
                if (i > 0) sb.append("/");
                sb.append(getNodeName((DefaultProjectTreeNode) nodePath[i]));
            }
            key = sb.toString();
            node.tmpKey = key;
        }
        return key;
    }
    /*
     * grid row id로 사용(ida2a2)
     */
    private static String getNodeId(DefaultProjectTreeNode node) {
        if (node == null) return null;

        String id = node.tmpId;
        if (id == null) {
            ProjectTreeNodeData data = (ProjectTreeNodeData) node.getUserObject();
            Object obj = data.getData();
            if (obj instanceof E3PSProject) {
                id = "P-"+CommonUtil.getOIDString((E3PSProject) obj);
            }
            else if (obj instanceof E3PSTask) {
                id = "T-"+CommonUtil.getOIDString((E3PSTask) obj);
            }
            node.tmpId = id;
        }
        return id;
    }
    private static String getNodePlanStartDate(DefaultProjectTreeNode node) {
        if (node == null) return null;

        String planStartDate = node.tmpPlanStartDate;
        if (planStartDate == null) {
            ProjectTreeNodeData data = (ProjectTreeNodeData) node.getUserObject();
            planStartDate = DateUtil.getDateString(data.getPlanStartDate(), "d");
            node.tmpPlanStartDate = planStartDate;
        }
        return planStartDate;
    }
    private static String getNodePlanEndDate(DefaultProjectTreeNode node) {
        if (node == null) return null;

        String planEndDate = node.tmpPlanEndDate;
        if (planEndDate == null) {
            ProjectTreeNodeData data = (ProjectTreeNodeData) node.getUserObject();
            planEndDate = DateUtil.getDateString(data.getPlanEndDate(), "d");
            node.tmpPlanEndDate = planEndDate;
        }
        return planEndDate;
    }

    /*
     * tree의 모든 node를 검색용 map에 저장, recursively (getNodeKey() 참고)
     */
    private static void putNodeToMap(DefaultProjectTreeNode node, Map<String, DefaultProjectTreeNode> nodeMap) {
        String key = getNodeKey(node);
        nodeMap.put(key, node);
        for(int i = 0; i < node.getChildCount(); i++) {
            putNodeToMap((DefaultProjectTreeNode) node.getChildAt(i), nodeMap);
        }
    }

    private void versionAjax(HttpServletRequest req, HttpServletResponse res) {
        KETParamMapUtil paramMap = KETParamMapUtil.getMap(req);

        Connection conn = null;
        try {
            conn = PlmDBUtil.getConnection();

            ProjectScheduleHistoryDao dao = new ProjectScheduleHistoryDao(conn);

            String ptype = paramMap.getString("ptype");
            String pjtMstOid = paramMap.getString("oid"); // E3PSProjectMaster oid
            long pjtMstIda2a2 = CommonUtil.getOIDLongValue(pjtMstOid);
            String pjtHistories = paramMap.getString("history");

            List<SqlRowMap> rowlist = dao.getVersionOfProject(ptype, pjtMstIda2a2, pjtHistories);
            List<String> versionList = new ArrayList<String>();
            for (SqlRowMap row : rowlist) {
                versionList.add(row.getString("version"));
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("versionList", versionList);

            res.setContentType("application/x-json; charset=UTF-8");
            res.getWriter().print(jsonObject);
        }
        catch(Exception e) {
            Kogger.error(getClass(), e);
        }
        finally {
            PlmDBUtil.close(conn);
        }
    }

}
