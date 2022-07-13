package ext.ket.sysif.sap.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

import e3ps.cost.util.StringUtil;
import e3ps.sap.RFCConnect;
import ext.ket.shared.log.Kogger;

@Service
public class SapService {

    /**
     * DIE NO의 집행가 정보를 반환 한다.
     * 
     * @param dieNo
     * @return
     * @메소드명 : getOrderByDieNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 16.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Map<String, String> getOrderByDieNo(String dieNo) {
	JCO.Client client = null;
	IRepository repository = null;
	Map<String, String> datas = new HashMap<String, String>();
	Kogger.debug(getClass(), "dieNo >> " + dieNo);
	try {
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    // DIE NO의 투자오더를 가져온다.
	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_BUDGET_BASE_DIENO");
	    JCO.Function function = tmpl.getFunction();
	    function.getImportParameterList().setValue(dieNo, "I_USER0");
	    client.execute(function);
	    String orderNumber = (String) function.getExportParameterList().getValue("E_AUFNR");
	    Kogger.debug(getClass(), "orderNumber >> " + orderNumber);

	    // 투자오더의 집행 예산 정보를 가져온다.
	    tmpl = repository.getFunctionTemplate("Z_ST_SELECT_BUDGET_EXECUTION");
	    function = tmpl.getFunction();
	    function.getImportParameterList().setValue(orderNumber, "I_AUFNR");
	    client.execute(function);
	    String re = (String) function.getExportParameterList().getValue("E_RETURN");

	    if ("S".equals(re)) {
		// E_AUART
		// E_WTGES_B //초기예산(계획)
		// E_WTGES_R //초기예산(기초)
		// E_WTGES_S //추가예산
		// E_WOGBTR //집행가
		// E_RETURN //성공여부 "S"성공
		// E_CNT //처리건수
		// E_MSG //Message
		datas.put("I_AUFNR", orderNumber);
		datas.put("E_WTGES_B", StringUtil.checkNull(function.getExportParameterList().getString("E_WTGES_B")));
		datas.put("E_WTGES_S", StringUtil.checkNull(function.getExportParameterList().getString("E_WTGES_S")));
		datas.put("E_WOGBTR", StringUtil.checkNull(function.getExportParameterList().getString("E_WOGBTR")));
		datas.put("E_WTGES_R", StringUtil.checkNull(function.getExportParameterList().getString("E_WTGES_R")));

	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (client != null) {
		client.disconnect();
		repository = null;
	    }
	}

	return datas;
    }

    public boolean ExistBudgetByOrderNo(String orderNumber) {
	JCO.Client client = null;
	IRepository repository = null;

	String re = "";
	try {
	    client = RFCConnect.getConnection();
	    client.connect();
	    repository = JCO.createRepository("BFREPOSITORY", client);

	    // 투자오더의 집행 예산 정보를 가져온다.
	    IFunctionTemplate tmpl = repository.getFunctionTemplate("Z_ST_SELECT_BUDGET_EXECUTION");
	    JCO.Function function = tmpl.getFunction();
	    function.getImportParameterList().setValue(orderNumber, "I_AUFNR");
	    client.execute(function);
	    re = (String) function.getExportParameterList().getValue("E_RETURN");

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	} finally {
	    if (client != null) {
		client.disconnect();
		repository = null;
	    }
	}

	return "S".equals(re);
    }
}