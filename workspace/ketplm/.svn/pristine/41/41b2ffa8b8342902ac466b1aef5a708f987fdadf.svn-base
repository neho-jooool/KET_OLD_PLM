package ext.ket.sysif.sap.controller;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ext.ket.sysif.sap.service.SapService;

@Controller
@RequestMapping("/sap/*")
public class SapController {
    @Inject
    private SapService service;

    @RequestMapping("/getOrderCostByDieNo")
    @ResponseBody
    public Map<String, String> getOrderCostByDieNo(String dieNo) {
	return service.getOrderByDieNo(dieNo);
    }
}
