package e3ps.project.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.db.DBCPManager;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet2;
import e3ps.common.web.ParamUtil;
import e3ps.groupware.company.People;
import e3ps.project.E3PSProject;
import e3ps.project.ElectronTemplateProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.MoldProject;
import e3ps.project.MoldTemplateProject;
import e3ps.project.ProductProject;
import e3ps.project.ProductTemplateProject;
import e3ps.project.ProjectMaster;
import e3ps.project.ReviewProject;
import e3ps.project.ScheduleData;
import e3ps.project.TemplateProject;
import e3ps.project.WorkProject;
import e3ps.project.beans.ClassAttributeData;
import e3ps.project.beans.ProjectHelper;
import e3ps.project.beans.TaskHelper;
import e3ps.project.beans.TemplateProjectData;
import e3ps.project.wbsupload.WBSUpload;
import ext.ket.shared.log.Kogger;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.CompoundQuerySpec;
import wt.query.OrderBy;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SetOperator;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;

public class SearchProjectTemplateServlet extends CommonServlet2 {

    public void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        req.setAttribute("control", this.getPageControl(req, res));
        String mode = ParamUtil.getStrParameter(req.getParameter("mode"));

        if ("s".equals(mode)) {
            String function = ParamUtil.getStrParameter(req.getParameter("function"));
            gotoResult(req, res, "/jsp/project/SelectProjectTemplateList.jsp?mode=s&function=" + function);
        } else if ("updateAct".equals(mode)) {
            activeTypeUpdate(req, res);
        } else if ("updateInfo".equals(mode)) {
            pjtInfoUpdate(req, res);
        } else if ("reSetting".equals(mode)) {
            reSetting(req, res);
        } else {
            searchWBSList(req, res);
        }
    }

    private void reSetting(HttpServletRequest req, HttpServletResponse res) {
        String originOid = req.getParameter("originOid");
        String copyOid = req.getParameter("copyOid");
        String[] category = req.getParameterValues("category");
        String productType = req.getParameter("productType");

        E3PSProject project = (E3PSProject) CommonUtil.getObject(originOid);
        TemplateProject tp = (TemplateProject) CommonUtil.getObject(copyOid);

        String projecType = project.toString();
        try {
            if (projecType.startsWith("e3ps.project.ProductProject")) {
                ProjectHelper.manager.wbsCopyProjectTaskNew(tp, project, category, productType);
            } else if (projecType.startsWith("e3ps.project.MoldProject")) {
                ProjectHelper.manager.wbsCopyProjectTask(tp, project);
            } else {
                ProjectHelper.manager.wbsCopyProjectTaskNew(tp, project, category);
            }

            // LEAF TASK VALUE SETTING
            if (originOid != null) {
                TaskHelper.manager.updateLeafTaskByProject(originOid);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", "success");
            res.setContentType("application/x-json; charset=UTF-8");
            res.getWriter().print(jsonObject);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Kogger.error(getClass(), e);
        }

    }

    private void pjtInfoUpdate(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String type = req.getContentType();
        String oid = "";
        String devType = "";
        String description = "";
        String name = "";
        String clientCompany = "";
        String division = "";
        String wbsType = "";
        String devStep = "";
        String moldType = "";
        String making = "";
        String makeOffice = "";
        String tempid = req.getParameter("tempid");

        File file = null;

        if ((type.toLowerCase().startsWith("multipart/form-data"))) {
            Kogger.debug(getClass(), "진입");

            FormUploader uploader = FormUploader.newFormUploader(req);
            Hashtable param = uploader.getFormParameters();

            oid = (String) param.get("oid");
            devType = (String) param.get("devType");
            description = (String) param.get("description");
            name = (String) param.get("name");
            clientCompany = (String) param.get("clientCompany");
            division = (String) param.get("division");
            wbsType = (String) param.get("wbsType");
            devStep = (String) param.get("devStep");
            moldType = (String) param.get("moldType");
            making = (String) param.get("making");
            makeOffice = (String) param.get("makeOffice");

            Vector v = uploader.getFiles();

            WBFile fileName = null;
            if (v != null && v.get(0) != null) {
                fileName = (WBFile) v.get(0);
            }

            if (fileName != null) {
                file = fileName.getFile();
            }

        } else {

            oid = req.getParameter("oid");
            devType = req.getParameter("devType");
            description = req.getParameter("description");
            name = req.getParameter("name");
            clientCompany = req.getParameter("clientCompany");
            division = req.getParameter("division");
            wbsType = req.getParameter("wbsType");
            devStep = req.getParameter("devStep");
            moldType = req.getParameter("moldType");
            making = req.getParameter("making");
            makeOffice = req.getParameter("makeOffice");
        }
        
        int pjtType = 0;
        if ("검토".equals(wbsType) || "업무".equals(wbsType)) {
            if ("자동차사업부".equals(division)) {
                pjtType = 1;
            } else {
                pjtType = 0;
            }
        } else if ("제품".equals(wbsType)) {
            if ("자동차사업부".equals(division)) {
                pjtType = 2;
            } else if ("전자사업부".equals(division)) {
                pjtType = 4;
            } else if ("KETS".equals(division)) {
                pjtType = 5;
            }
        } else {
            if ("자동차사업부".equals(division)) {
                pjtType = 3;
            } else if ("전자사업부".equals(division)) {
                pjtType = 4;
            } else if ("KETS".equals(division)) {
                pjtType = 6;
            }
        }

        TemplateProject project = (TemplateProject) CommonUtil.getObject(oid);
        if("purchase".equals(project.getMoldType())) moldType = project.getMoldType();
        if("work".equals(project.getMoldType())) moldType = project.getMoldType();

        ProjectMaster master = project.getMaster();

        try {
            project.setDevType(devType);
            project.setPjtDesc(description);
            project.getMaster().setPjtName(name);
            project.setClientCompany(clientCompany);
            project.setDivision(division);
            project.setPjtType(pjtType);
            project.setDevStep(devStep);
            project.setMoldType(moldType);
            project.setMaking(making);
            project.setMakeOffice(makeOffice);
            PersistenceHelper.manager.modify(project);

            master.setPjtName(name);
            PersistenceHelper.manager.modify(master);

            TemplateProject oldPJT = null;
            // TemplateProjectData oldPJTData = null;

            if (StringUtil.checkString(tempid)) {
                oldPJT = (TemplateProject) CommonUtil.getObject(tempid);
                // oldPJTData = new TemplateProjectData(oldPJT);
            }

            if (oldPJT != null) {
                Connection con = null;
                Statement st = null;
                try {
                    con = DBCPManager.getConnection("plm");

                    con.setAutoCommit(false);
                    st = (Statement) con.createStatement();

                    st.executeQuery(
                            "DELETE FROM TEMPLATETASK WHERE ida2a2 IN (SELECT b.ida2a2 FROM TEMPLATEPROJECT A, TEMPLATETASK b WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid
                                    + "' AND A.ida2a2 = b.ida3b4 UNION SELECT B.IDA2A2 FROM ELECTRONTEMPLATEPROJECT A, TEMPLATETASK B WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid
                                    + "' AND A.ida2a2 = b.ida3b4 UNION SELECT B.IDA2A2 FROM PRODUCTTEMPLATEPROJECT A, TEMPLATETASK B WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid
                                    + "' AND A.ida2a2 = b.ida3b4 UNION SELECT B.IDA2A2 FROM MOLDTEMPLATEPROJECT A, TEMPLATETASK B WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid + "' AND A.ida2a2 = b.ida3b4)");

                    con.commit();
                    con.setAutoCommit(true);

                } catch (Exception e) {
                    Kogger.error(getClass(), e);
                    throw new Exception(e);
                } finally {
                    try {
                        if (st != null)
                            st.close();
                        if (con != null)
                            con.close();

                    } catch (SQLException e) {
                        Kogger.error(getClass(), e);
                    }
                }

                TaskHelper.manager.copyTemplateProjectFromTemplateProject(project, oldPJT);
            }

            if (file != null) {
                Connection con = null;
                Statement st = null;
                try {
                    con = DBCPManager.getConnection("plm");

                    con.setAutoCommit(false);
                    st = (Statement) con.createStatement();

                    st.executeQuery(
                            "DELETE FROM TEMPLATETASK WHERE ida2a2 IN (SELECT b.ida2a2 FROM TEMPLATEPROJECT A, TEMPLATETASK b WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid
                                    + "' AND A.ida2a2 = b.ida3b4 UNION SELECT B.IDA2A2 FROM ELECTRONTEMPLATEPROJECT A, TEMPLATETASK B WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid
                                    + "' AND A.ida2a2 = b.ida3b4 UNION SELECT B.IDA2A2 FROM PRODUCTTEMPLATEPROJECT A, TEMPLATETASK B WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid
                                    + "' AND A.ida2a2 = b.ida3b4 UNION SELECT B.IDA2A2 FROM MOLDTEMPLATEPROJECT A, TEMPLATETASK B WHERE A.CLASSNAMEA2A2||':'||A.ida2a2 = '"
                                    + oid + "' AND A.ida2a2 = b.ida3b4)");

                    con.commit();
                    con.setAutoCommit(true);

                } catch (Exception e) {
                    Kogger.error(getClass(), e);
                    throw new Exception(e);
                } finally {
                    try {
                        if (st != null)
                            st.close();
                        if (con != null)
                            con.close();

                    } catch (SQLException e) {
                        Kogger.error(getClass(), e);
                    }
                }
                WBSUpload.createWBS(project, file);
            }
        } catch (WTPropertyVetoException e) {
            // TODO Auto-generated catch block
            Kogger.error(getClass(), e);
        } catch (WTException e) {
            // TODO Auto-generated catch block
            Kogger.error(getClass(), e);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            Kogger.error(getClass(), e);
        }

    }

    private void activeTypeUpdate(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String activeType = req.getParameter("activeType");
        String oid = req.getParameter("oid");
        TemplateProject project = (TemplateProject) CommonUtil.getObject(oid);

        try {
            project.setActiveType(activeType);
            PersistenceHelper.manager.modify(project);
        } catch (WTPropertyVetoException e) {
            // TODO Auto-generated catch block
            Kogger.error(getClass(), e);
        } catch (WTException e) {
            // TODO Auto-generated catch block
            Kogger.error(getClass(), e);
        }

        res.setContentType("text/html;charset=KSC5601");
        PrintWriter out = res.getWriter();
        String rtn_msg = "";
        rtn_msg = "상태가 " + activeType + "으로 변경되었습니다.";
        out.println(rtn_msg);
    }

    /*
     * [PLM System 1차개선] 수정내용 : WBS 검색 서블릿 적용 수정일자 : 2013. 7. 02 수정자 : 김종호
     */
    private void searchWBSList(HttpServletRequest req, HttpServletResponse res) {
        QueryResult result = null;

        StringBuffer strBuffer = new StringBuffer();
        int rowCount = 1;

        try {
            // Form 데이터 받기
            FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
            KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader.getFormParameters());

            String divisionValue = ParamUtil.getStrParameter(req.getParameter("division"));
            String wbsTypeValue = ParamUtil.getStrParameter(req.getParameter("wbsType"));

            String name = ParamUtil.getStrParameter(req.getParameter("name"));

            String planType = ParamUtil.getStrParameter(req.getParameter("planType"));
            String assembling = ParamUtil.getStrParameter(req.getParameter("assembling"));
            String developType = ParamUtil.getStrParameter(req.getParameter("developType"));
            String makeType = ParamUtil.getStrParameter(req.getParameter("makeType"));
            String productType = ParamUtil.getStrParameter(req.getParameter("productType"));

            String popup = req.getParameter("popup");
            String divide = req.getParameter("divide");
            String selector = req.getParameter("selector");
            String origin = req.getParameter("origin");
            String init = StringUtil.checkNull(req.getParameter("initValue"));

            if ("".equals(init)) {
                init = "init";
            }

            String perPage = paramMap.getString("perPage");
            /*
             * [PLM 고도화] 수정일자: 2014.07.07 수정내용: 검색 파라미터 추가 수정자 : 박진영
             */
            HashMap<String, String> map = new HashMap<String, String>();

            if (selector != null && selector != "") {
                if ("project".equals(selector)) {
                    map.put("activeType", "활성");
                    req.setAttribute("activeType", "활성");
                }
            } else {
                map.put("activeType", KETParamMapUtil.toString(paramMap.getStringArray("activeType")));
            }
            map.put("devStep", KETParamMapUtil.toString(paramMap.getStringArray("devStep")));
            map.put("devCategory", KETParamMapUtil.toString(paramMap.getStringArray("devCategory")));
            map.put("type", KETParamMapUtil.toString(paramMap.getStringArray("type")));
            map.put("making", KETParamMapUtil.toString(paramMap.getStringArray("making")));

            String[] divisionCode = new String[1];
            if (CommonUtil.isMember("전자사업부")) {
                divisionCode[0] = "전자사업부";
            } else if (CommonUtil.isMember("자동차사업부")) {
                divisionCode[0] = "자동차사업부";
            } else if (CommonUtil.isMember("KETS")) {
                divisionCode[0] = "KETS";
            }

            int length = paramMap.getStringArray("division").length;
            if (length > 0) {
                map.put("division", KETParamMapUtil.toString(paramMap.getStringArray("division")));
            } else {
                if ("init".equals(init)) {
                    map.put("division", KETParamMapUtil.toString(divisionCode));
                }
            }

            if (CommonUtil.isMember("KETS")) {
                map.put("division", KETParamMapUtil.toString(divisionCode));
            }

            String moldType = ParamUtil.getStrParameter(req.getParameter("moldType"));
            String clientCompany = ParamUtil.getStrParameter(req.getParameter("clientCompany"));
            String makeOffice = ParamUtil.getStrParameter(req.getParameter("makeOffice"));

            if (StringUtil.checkString(origin)) {
                
                E3PSProject project = (E3PSProject) CommonUtil.getObject(origin);
                
                if(project instanceof ProductProject) {
                    moldType = "product";
                }else if(project instanceof ReviewProject) {
                    moldType = "review";
                }else if(project instanceof WorkProject) {
                    moldType = "work";
                }else {
                    MoldProject mProject= (MoldProject) project;
                    MoldItemInfo moldInfo = mProject.getMoldInfo();
                    
                    if("구매품".equals(moldInfo.getItemType())){
                        moldType = "purchase";
                    }else {
                        moldType = "mold";
                    }
                }
            }
            
            if (!StringUtil.checkString(moldType)) {
                if (CommonUtil.isMember("KETS")) {
                    moldType = "product";
                } else if(StringUtil.checkString(divide)){
                    moldType = divide;
                }else {
                    moldType = "review";
                }
            }

            Hashtable hash = new Hashtable();
            hash.put("name", name);
            hash.put("planType", planType);
            hash.put("assembling", assembling);
            hash.put("developType", developType);
            hash.put("makeType", makeType);
            hash.put("productType", productType);
            hash.put("activeType", map);
            hash.put("devStep", map);
            hash.put("devCategory", map);
            hash.put("type", map);
            hash.put("making", map);
            hash.put("division", map);
            hash.put("moldType", moldType);
            hash.put("clientCompany", clientCompany);
            hash.put("makeOffice", makeOffice);
            hash.put("perPage", perPage);

            Vector attr = getClassAttr();
            // if (divisionValue.length() > 0) {
            // if ("자동차사업부".equals(divisionValue)) {
            // if (wbsTypeValue.length() > 0) {
            // if ("제품 Project".equals(wbsTypeValue)) {
            // hash.put("projectType", "2");
            // } else if ("금형 Project".equals(wbsTypeValue)) {
            // hash.put("projectType", "3");
            // } else {
            // hash.put("projectType", "1");
            // }
            // } else {
            // hash.put("projectType", "1,2,3");
            // }
            // } else if ("전자사업부".equals(divisionValue)) {
            // if (wbsTypeValue.length() > 0) {
            // if ("제품 Project".equals(wbsTypeValue)) {
            // hash.put("projectType", "4");
            // } else {
            // hash.put("projectType", "0");
            // }
            // } else {
            // hash.put("projectType", "0,4");
            // }
            // }
            // }
            if ("mold".equals(moldType) || "purchase".equals(moldType)) // 금형, 구매품 프로젝트
            {
                if ("자동차사업부".equals(map.get("division"))) { // 자동차사업부
                    hash.put("projectType", "3");
                } else if ("전자사업부".equals(map.get("division"))) { // 전자사업부
                    hash.put("projectType", "4");
                } else if ("KETS".equals(map.get("division"))) { // 전자사업부
                    hash.put("projectType", "6");
                }
            } else if ("review".equals(moldType)) // 검토프로젝트
            {
                if ("자동차사업부".equals(map.get("division"))) { // 자동차사업부
                    hash.put("projectType", "1");
                } else if ("전자사업부".equals(map.get("division"))) { // 전자사업부
                    hash.put("projectType", "0");
                }
            } else if ("work".equals(moldType)) // 검토프로젝트
            {
                if ("자동차사업부".equals(map.get("division"))) { // 자동차사업부
                    hash.put("projectType", "1");
                } else if ("전자사업부".equals(map.get("division"))) { // 전자사업부
                    hash.put("projectType", "0");
                }
            } else // 제품프로젝트
            {
                if ("자동차사업부".equals(map.get("division"))) { // 자동차사업부
                    hash.put("projectType", "2");
                } else if ("전자사업부".equals(map.get("division"))) { // 전자사업부
                    hash.put("projectType", "4");
                } else if ("KETS".equals(map.get("division"))) { // 전자사업부
                    hash.put("projectType", "5");
                }
            }

            QuerySpec queryA = ProjectHelper.getWBSSearchQuery(hash, TemplateProject.class, attr);
            QuerySpec queryB = ProjectHelper.getWBSSearchQuery(hash, ProductTemplateProject.class, attr);
            QuerySpec queryC = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, attr);
            QuerySpec queryD = ProjectHelper.getWBSSearchQuery(hash, ElectronTemplateProject.class, attr);

            CompoundQuerySpec query = new CompoundQuerySpec();
            query.setSetOperator(SetOperator.UNION);

            if ("mold".equals(moldType) || "purchase".equals(moldType)) {
                query.addComponent(queryC);
            } else if ("review".equals(moldType) || "work".equals(moldType)) {
                query.addComponent(queryA);
            } else {
                query.addComponent(queryB);
                query.addComponent(queryD);
            }

            result = PersistenceHelper.manager.find(query);

            TemplateProjectData data = null;

            if (result.size() > 0) {

                Object resultObj[] = null;

                while (result.hasMoreElements()) {
                    resultObj = (Object[]) result.nextElement();
                    TemplateProject template = null;

                    if (resultObj[0] instanceof String)
                        template = (TemplateProject) CommonUtil.getObject((String) resultObj[0]);
                    else
                        template = (TemplateProject) resultObj[0];

                    boolean isCheckOut = template.isCheckOut();
                    data = new TemplateProjectData(template);

                    String tempName = "";
                    if (isCheckOut) {
                        tempName = data.tempName + "(checkout됨)";
                    } else {
                        tempName = data.tempName;
                    }

                    strBuffer.append("<I NoColor=&quot;2&quot; CanDelete=&quot;0&quot;");
                    strBuffer.append(" RowNum=&quot;" + rowCount++ + "&quot;");
                    strBuffer.append(" TempName=&quot;" + tempName + "&quot;"
                            + " TempNameOnClick=&quot;javascript:searchPopup(&apos;/plm/servlet/e3ps/ProjectScheduleServlet?oid="
                            + data.tempProjectOID
                            + "&apos;,&apos;/plm/jsp/project/template/TemplateProjectView.jsp?oid="
                            + data.tempProjectOID + "&popup=" + popup + "&type=" + moldType + "&apos;);&quot;"
                            + " TempNameHtmlPrefix=&quot;&lt;font color=&apos;"
                            + PropertiesUtil.getSearchGridLinkColor() + "&apos;&gt;&quot;"
                            + " TempNameHtmlPostfix=&quot;&lt;/font&gt;&quot;");
                    strBuffer.append(" TempDu=&quot;" + data.tempDuration + "&quot;");
                    strBuffer.append(" TempDivision=&quot;" + StringUtil.checkNull(template.getDivision()) + "&quot;");
                    if ("mold".equals(moldType) || "purchase".equals(moldType)) {
                        strBuffer.append(
                                " MakeOffice=&quot;" + StringUtil.checkNull(template.getMakeOffice()) + "&quot;");
                        strBuffer.append(" MoldType=&quot;"
                                + StringUtil.checkNull(StringUtils.capitalize(template.getMoldType())) + "&quot;");
                        strBuffer.append(" Making=&quot;" + StringUtil.checkNull(template.getMaking()) + "&quot;");
                    } else if ("review".equals(moldType)) {
                        strBuffer.append(" DevType=&quot;" + StringUtil.checkNull(template.getDevType()) + "&quot;");
                        strBuffer.append(
                                " ClientCompany=&quot;" + StringUtil.checkNull(template.getClientCompany()) + "&quot;");
                    } else {
                        strBuffer.append(" DevType=&quot;" + StringUtil.checkNull(template.getDevType()) + "&quot;");
                        strBuffer.append(
                                " ClientCompany=&quot;" + StringUtil.checkNull(template.getClientCompany()) + "&quot;");
                        strBuffer.append(" DevStep=&quot;"
                                + StringUtil.checkNull(StringUtils.capitalize(template.getDevStep())) + "&quot;");
                    }
                    strBuffer.append(" ProjectOid=&quot;" + data.tempProjectOID + "&quot;");
                    strBuffer.append(" ActiveType=&quot;" + StringUtil.checkNull(template.getActiveType()) + "&quot;");
                    strBuffer.append(
                            " TempCreateDate=&quot;" + DateUtil.getDateString(data.tempCreateDate, "D") + "&quot;");
                    strBuffer.append(
                            " TempModifyDate=&quot;" + DateUtil.getDateString(data.tempModifyDate, "D") + "&quot;");
                    strBuffer.append(" CreatorName=&quot;" + template.getCreatorFullName() + "&quot;");
                    strBuffer.append("/>");
                }
            }

            req.setAttribute("popup", popup);
            req.setAttribute("divide", divide);
            req.setAttribute("selector", selector);
            req.setAttribute("origin", origin);

            if (data != null) {
                req.setAttribute("taskUrl", "/plm/servlet/e3ps/ProjectScheduleServlet?oid=" + data.tempProjectOID);
            }
            req.setAttribute("searchCondition", hash); // 검색 화면에서 받은 검색조건
            req.setAttribute("moldType", moldType);
            req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
            gotoResult(req, res, "/jsp/project/template/TemplateProjectList.jsp");
        } catch (QueryException e) {
            Kogger.error(getClass(), e);
        } catch (WTIntrospectionException e) {
            Kogger.error(getClass(), e);
        } catch (WTPropertyVetoException e) {
            Kogger.error(getClass(), e);
        } catch (WTException e) {
            Kogger.error(getClass(), e);
        } catch (ServletException e) {
            Kogger.error(getClass(), e);
        } catch (IOException e) {
            Kogger.error(getClass(), e);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

    protected StatementSpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
        try {

            // boolean isAdmin = CommonUtil.isAdmin();

            String divisionValue = ParamUtil.getStrParameter(req.getParameter("division"));
            String wbsTypeValue = ParamUtil.getStrParameter(req.getParameter("wbsType"));

            String name = ParamUtil.getStrParameter(req.getParameter("name"));

            String planType = ParamUtil.getStrParameter(req.getParameter("planType"));
            String assembling = ParamUtil.getStrParameter(req.getParameter("assembling"));
            String developType = ParamUtil.getStrParameter(req.getParameter("developType"));
            String makeType = ParamUtil.getStrParameter(req.getParameter("makeType"));
            String productType = ParamUtil.getStrParameter(req.getParameter("productType"));
            String sortKey = ParamUtil.getStrParameter(req.getParameter("sortKey"));
            String dsc = ParamUtil.getStrParameter(req.getParameter("dsc"));
            if (dsc.length() == 0) {
                dsc = "true";
            }
            if (sortKey.length() == 0) {
                sortKey = "modifyStamp";
            }

            Hashtable hash = new Hashtable();
            hash.put("name", name);

            hash.put("planType", planType);
            hash.put("assembling", assembling);
            hash.put("developType", developType);
            hash.put("makeType", makeType);
            hash.put("productType", productType);
            hash.put("sortKey", sortKey);
            hash.put("dsc", dsc);
            Vector attr = getClassAttr();
            if (divisionValue.length() > 0) {
                if ("자동차사업부".equals(divisionValue)) {
                    if (wbsTypeValue.length() > 0) {
                        if ("제품 Project".equals(wbsTypeValue)) {
                            hash.put("projectType", "2");
                        } else if ("금형 Project".equals(wbsTypeValue)) {
                            hash.put("projectType", "3");
                        } else {
                            hash.put("projectType", "1");
                        }
                    } else {
                        hash.put("projectType", "1,2,3");
                    }
                } else if ("전자사업부".equals(divisionValue)) {
                    if (wbsTypeValue.length() > 0) {
                        if ("제품 Project".equals(wbsTypeValue)) {
                            hash.put("projectType", "4");
                        } else {
                            hash.put("projectType", "0");
                        }
                    } else {
                        hash.put("projectType", "0,4");
                    }
                }
            }

            Kogger.debug(getClass(), hash);

            QuerySpec queryA = ProjectHelper.getWBSSearchQuery(hash, TemplateProject.class, attr);
            QuerySpec queryB = ProjectHelper.getWBSSearchQuery(hash, ProductTemplateProject.class, attr);
            QuerySpec queryC = ProjectHelper.getWBSSearchQuery(hash, MoldTemplateProject.class, attr);
            QuerySpec queryD = ProjectHelper.getWBSSearchQuery(hash, ElectronTemplateProject.class, attr);

            CompoundQuerySpec query = new CompoundQuerySpec();
            query.setSetOperator(SetOperator.UNION);
            query.addComponent(queryA);
            query.addComponent(queryB);
            query.addComponent(queryC);
            query.addComponent(queryD);

            String sort = (String) hash.get("sortKey");
            String isDesc = (String) hash.get("dsc");

            if ((sort != null) && (sort.trim().length() > 0)) {

                if ((isDesc == null) || (isDesc.trim().length() == 0)) {
                    isDesc = "FALSE";
                }

                ClassAttributeData sortCa = null;
                for (int i = 0; i < attr.size(); i++) {
                    ClassAttributeData attrData = (ClassAttributeData) attr.get(i);

                    if (sort.equals(attrData.ca.getColumnAlias())) {
                        sortCa = attrData;
                        break;
                    }
                }

                if (sortCa != null) {
                    OrderBy orderby = new OrderBy(sortCa.ca, "TRUE".equals(isDesc.toUpperCase()));
                    query.appendOrderBy(orderby);
                }

            }
            return query;

        } catch (Exception exception) {
        }
        return null;
    }

    private static Vector getClassAttr() throws Exception {
        Vector vector = new Vector();
        ClassAttribute ca = new ClassAttribute(TemplateProject.class, TemplateProject.PJT_NAME);
        ca.setColumnAlias("wbsName");
        vector.add(new ClassAttributeData(ca, 0));
        ca = new ClassAttribute(TemplateProject.class, "thePersistInfo.createStamp");
        ca.setColumnAlias("createStamp");
        vector.add(new ClassAttributeData(ca, 0));

        ca = new ClassAttribute(TemplateProject.class, "thePersistInfo.modifyStamp");
        ca.setColumnAlias("modifyStamp");
        vector.add(new ClassAttributeData(ca, 0));
        ca = new ClassAttribute(ScheduleData.class, ScheduleData.DURATION);
        ca.setColumnAlias("duration");
        vector.add(new ClassAttributeData(ca, 1));

        ca = new ClassAttribute(People.class, People.NAME);
        ca.setColumnAlias("peopleName");
        vector.add(new ClassAttributeData(ca, 1));

        return vector;
    }
}
