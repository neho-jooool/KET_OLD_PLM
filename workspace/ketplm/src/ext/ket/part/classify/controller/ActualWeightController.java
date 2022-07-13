package ext.ket.part.classify.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.part.WTPart;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.VersionUtil;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.ecm.entity.KETProdECOPartLink;
import e3ps.groupware.company.PeopleData;
import ext.ket.part.classify.service.ActualWeightHelper;
import ext.ket.part.entity.KETPartClassLink;
import ext.ket.part.entity.KETPartClassification;

@Controller
@RequestMapping("/part/classify/*")
public class ActualWeightController {

    @RequestMapping("/actualWeightInputForm")
    public void actualWeightInputForm(String eco_oid, Model model) throws Exception {

	List<HashMap<String, String>> partHashMapList = new ArrayList<HashMap<String, String>>();
	Locale locale = KETMessageService.getMessageService().getLocale();

	KETProdChangeOrder ketProdChangeOrder = (KETProdChangeOrder) CommonUtil.getObject(eco_oid);

	QueryResult qrPartList = PersistenceHelper.manager.navigate(ketProdChangeOrder, "part", KETProdECOPartLink.class);

	if (qrPartList.hasMoreElements()) {
	    while (qrPartList.hasMoreElements()) {
		WTPart wtPart = (WTPart) qrPartList.nextElement();
		HashMap<String, String> partHashMap = new HashMap<String, String>();
		partHashMap.put("part_oid", CommonUtil.getOIDString(wtPart));
		partHashMap.put("part_no", wtPart.getNumber());
		partHashMap.put("part_name", wtPart.getName());
		partHashMap.put("part_ver", VersionUtil.getMajorVersion(wtPart));
		partHashMap.put("part_state", wtPart.getState().getState().getDisplay(locale));

		WTUser create_User = (WTUser) wtPart.getCreator().getPrincipal();
		PeopleData peopleData = new PeopleData(create_User);
		partHashMap.put("part_creator_name", peopleData.name);

		QueryResult qr = PersistenceHelper.manager.navigate(wtPart.getMaster(), "classific", KETPartClassLink.class);
		if (qr.hasMoreElements()) {
		    while (qr.hasMoreElements()) {
			KETPartClassification ketPartClassification = (KETPartClassification) qr.nextElement();
			partHashMap.put("part_claz_namekr", ketPartClassification.getClassKrName());
			break;
		    }
		}

		partHashMapList.add(partHashMap);
	    }
	}

	model.addAttribute("eco_oid", eco_oid);
	model.addAttribute("partHashMapList", partHashMapList);

    }

    @RequestMapping("/actualWeightBomIframeInputForm")
    public void actualWeightBomIframeInputForm(String part_oid, Model model) throws Exception {
	List<HashMap<String, String>> partHashMapList = new ArrayList<HashMap<String, String>>();
	Locale locale = KETMessageService.getMessageService().getLocale();

	partHashMapList = ActualWeightHelper.service.getPartList(part_oid, locale);
	model.addAttribute("partHashMapList", partHashMapList);
    }

    @RequestMapping("/actualWeightBomSave")
    public void actualWeightBomSave(String eco_oid, String part_no[], String part_sp_net_weight[], String part_sp_total_weight[], String part_oid[], String part_sp_scrab_weight[],
	    HttpServletResponse response, HttpServletRequest request) throws Exception {
	boolean result = ActualWeightHelper.service.actualWeightBomSave(eco_oid, part_no, part_sp_net_weight, part_sp_total_weight, part_oid, part_sp_scrab_weight);

	if (result) {
	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    str = "\n <script language=\"javascript\">\n alert(\"" + "저장되었습니다." + "\");" + "</script>";
	    pwriter.println(str);
	    pwriter.close();
	    return;
	}
	else {
	    String str = "";
	    response.setContentType("text/html;charset=KSC5601");
	    PrintWriter pwriter = response.getWriter();
	    str = "\n <script language=\"javascript\">\n alert(\"" + "저장에 실패 하였었니다." + "\");" + "</script>";
	    pwriter.println(str);
	    pwriter.close();
	    return;
	}
    }
}
