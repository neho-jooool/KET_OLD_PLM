package ext.ket.project.atft.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import e3ps.common.message.KETMessageService;
import e3ps.common.util.StringUtil;
import ext.ket.project.atft.entity.KETAtftDTO;
import ext.ket.project.atft.service.AtftHelper;

/**
 * all-tool, full-tool 관리 Controller
 * 
 * @클래스명 : atftController
 * @작성자 : 황정태
 * @작성일 : 2016. 12. 26.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@Controller
@RequestMapping("/project/atft/*")
public class atftController {
    
    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping("/getAtftStats")
    public Map<String, Object> getAtftStats(@RequestBody Map<String, Object> reqMap) {

        Map<String, Object> resMap = new HashMap<String, Object>();

        try {
            
            /*List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
            
            //############################### 삭제 예정 #######################################
            Map<Integer, String> flags = new HashMap<Integer, String>();
            flags.put(0, "치수");
            flags.put(1, "외관");
            flags.put(2, "기능/성능");
            flags.put(3, "신뢰성");
            flags.put(4, "사출(C/T)");
            flags.put(5, "프레스(SPM)");
            flags.put(6, "조립(T/T)");
            flags.put(7, "Fool-Proof");
            flags.put(8, "공정불량");
            flags.put(9, "협력사");
            flags.put(10, "작업자/검사자");
            flags.put(11, "JIG준비(취출/조립/검사)");
            flags.put(12, "개발BOM");
            flags.put(13, "양산BOM");
            flags.put(14, "판매가");
            flags.put(15, "구매가");
            flags.put(16, "임가공가");
            flags.put(17, "포장사양");
            flags.put(18, "생산처");
            
            Map<String, String> stateCheck = new HashMap<String, String>();
            
            for(int i = 0; i < 50; i++) {
                
                //#####필드값##################
                //classification : 세부구분
                //                치수
                //                외관
                //                기능/성능
                //                신뢰성
                //                사출(C/T)
                //                프레스(SPM)
                //                조립(T/T)
                //                Fool-Proof
                //                공정불량
                //                협력사
                //                작업자/검사자
                //                JIG준비(취출/조립/검사)
                //                개발BOM
                //                양산BOM
                //                판매가
                //                구매가
                //                임가공가
                //                포장사양
                //                생산처
                //totalState : 종합상태
                //state : 개별상태
                //projectNo : 프로젝트 번호
                //projectOid : 프로젝트OID
                //task : task
                //decision : 판정근거
                
                
                Map<String, String> data = new HashMap<String, String>();
                int divFlag = (int) (Math.random() * 20);
                int statFlag = (int) Math.round(Math.random());
                
                String classification = flags.get(divFlag);
                String checkState = stateCheck.get(classification);
                
                if(!StringUtil.checkString(checkState)) stateCheck.put(classification, "OK");
                
                data.put("classification", classification);
                data.put("projectOid", String.valueOf((int) (Math.random() * 1000000)));
                data.put("projectNo", String.valueOf((int) (Math.random() * 1000000)));
                
                if(statFlag == 0) {
                    data.put("state", "OK");
                    data.put("task", "제품합격 : 전치수 합격");
                }else {
                    data.put("state", "NG");
                    data.put("task", "제품합격 : 전치수 불합격");
                    stateCheck.put(classification, "NG");
                }
                
                dataList.add(data);
            }
            
            for(Map<String, String> data : dataList) {
                String classification = data.get("classification");
                
                if(stateCheck.get(classification) == "OK") {
                    data.put("decision", "제품합격 : 전치수 합격");
                }else {
                    data.put("decision", "제품합격 : 전치수 불합격");
                }
                data.put("totalState", stateCheck.get(classification));
            }*/
            
            //############################### 삭제 예정 #######################################
            
            String projectOid = (String)reqMap.get("oid");
            String sheetdivision = (String)reqMap.get("sheetdivision");
            List<Map<String, String>> dataList = AtftHelper.service.autoCheckData(projectOid,sheetdivision);
            
            
            
            resMap.put("dataList", dataList);
            resMap.put("result", true);

        } catch (Exception e) {
            e.printStackTrace();
            resMap.put("result", false);
            resMap.put("message", e.getLocalizedMessage());
        }

        return resMap;
    }
    
    
    /**
     * <pre>
     * @description  
     * @author dhkim
     * @date 2018. 8. 31. 오전 9:17:30
     * @method atftAutoCheck
     * @param projectOid
     * @param model
     * </pre>
     */
    @RequestMapping("/atftAutoCheck")
    public void atftAutoCheck(String projectOid, String sheetdivision, String readOnly, Model model) {
        
        model.addAttribute("projectOid", projectOid);
        model.addAttribute("sheetdivision", sheetdivision);
        model.addAttribute("readOnly", readOnly);
    }

    /**
     * ATFT 검색 화면
     * 
     * @메소드명 : list
     * @작성자 : 황정태
     * @작성일 : 2016. 12. 26.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/listAtft")
    public void listAtft() {
    }

    @RequestMapping("/createAtftForm")
    public void createAtftForm(String projectOid, Model model) {
    model.addAttribute("projectOid", projectOid);
    }

    @RequestMapping("/atftCreate")
    public void atftCreate(KETAtftDTO dto, HttpServletResponse response, HttpServletRequest request) throws Exception {

    String errMsg = "오류가 발생했습니다. 관리자에게 문의바랍니다.";
    String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
    
    try {
        AtftHelper.service.createAtft(dto);

        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
                    + "\n try{ parent.opener.location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }  \n";
       //str += "\n parent.location.href='/plm/ext/project/atft/viewAtftForm.do?projectOid=" + dto.getProjectOid()  + "';" + "\n </script>";
        str += "\n self.close();" + "\n </script>";
        pwriter.println(str);
        pwriter.close();

    } catch (Exception e) {
        
        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "\n <script language=\"javascript\">\n alert(\"" + errMsg + "\");"
            + "\n try{  } catch(error){ }  \n";
        str += "\n parent.location.href='/plm/ext/project/atft/createAtftForm.do?projectOid=" + dto.getProjectOid() +"';" + "\n </script>";
        
        pwriter.println(str);
        pwriter.close();
    }

    return;
    }
    
    @RequestMapping("/atftModify")
    public void atftModify(KETAtftDTO dto, HttpServletResponse response, HttpServletRequest request) throws Exception {

    String errMsg = "오류가 발생했습니다. 관리자에게 문의바랍니다.";
    String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
    
    try {
        AtftHelper.service.modifyAtft(dto);

        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        
        String str = "";

        str = "\n <script language=\"javascript\">\n alert(\""+msg+ "\");"
        + "\n try{ parent.opener.location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }"
        + "\n self.close();" + "\n </script>";
            pwriter.println(str);
            pwriter.close();
    } catch (Exception e) {
        
        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "";

        str = "\n <script language=\"javascript\">\n alert(\""+errMsg+ "\");"
        + "\n try{ parent.opener.location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }"
        + "\n self.close();" + "\n </script>";

        pwriter.println(str);
        pwriter.close();
    }

    return;
    }
    
    @RequestMapping("/viewAtftForm")
    public void viewAtftForm(String projectOid, Model model) {
    model.addAttribute("projectOid", projectOid);
    }
    
    @RequestMapping("/getAtftIfno")
    @ResponseBody
    public List<Map<String, Object>> getAtftIfno(String pjtNo, Model model) {
    
    List<Map<String, Object>> returnObjList = null;
        try {
        returnObjList = AtftHelper.service.lastestAtftList(pjtNo);
        } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
        }
    return returnObjList;
    }
    
    @RequestMapping("/modifyAtftForm")
    public void modifyAtftForm(String pjtNo, String projectOid, Model model) {
    model.addAttribute("pjtNo", pjtNo);
    model.addAttribute("projectOid", projectOid);
    }
    
    @RequestMapping("/completeAtft")
    public void completeAtft(KETAtftDTO dto, HttpServletResponse response, HttpServletRequest request) throws Exception {

    String errMsg = "오류가 발생했습니다. 관리자에게 문의바랍니다.";
    String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
    
    try {
        AtftHelper.service.completeAtft(dto);

        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
                    + "\n try{ location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }  \n</script>";
        pwriter.println(str);
        pwriter.close();

    } catch (Exception e) {
        
        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "\n <script language=\"javascript\">\n alert(\"" + errMsg + "\");"
                    + "\n try{ location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }  \n</script>";
        pwriter.println(str);
        pwriter.close();
    }

    return;
    }
    
    @RequestMapping("/reviseAtft")
    public void reviseAtft(KETAtftDTO dto, HttpServletResponse response, HttpServletRequest request) throws Exception {
    String errMsg = "오류가 발생했습니다. 관리자에게 문의바랍니다.";
    String msg = KETMessageService.getMessageService().getString("e3ps.message.ket_message", "02454");/* "저장되었습니다." */
    
    try {
        AtftHelper.service.reviseAtft(dto);

        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "\n <script language=\"javascript\">\n alert(\"" + msg + "\");"
                    + "\n try{ location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }  \n</script>";
        pwriter.println(str);
        pwriter.close();

    } catch (Exception e) {
        
        response.setContentType("text/html;charset=KSC5601");
        PrintWriter pwriter = response.getWriter();
        String str = "\n <script language=\"javascript\">\n alert(\"" + errMsg + "\");"
                    + "\n try{ location.href='/plm/jsp/project/ProjectExtView9.jsp?oid="+dto.getProjectOid()+ "&popup=popup';" + "} catch(error){ }  \n</script>";
        pwriter.println(str);
        pwriter.close();
    }

    return;

    }
}
