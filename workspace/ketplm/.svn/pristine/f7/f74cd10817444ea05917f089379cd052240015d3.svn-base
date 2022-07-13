package ext.ket.edm.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import wt.org.WTUser;
import wt.session.SessionHelper;
import e3ps.common.util.CommonUtil;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.edm.entity.dto.KETMaterialDTO;
import ext.ket.shared.dao.CommonDao;

@Service
public class PlmHpIfService {
    @Inject
    private CommonDao dao;

    public void save(KETMaterialDTO materialDTO) throws Exception {

	dao.delete("material.deleteMaterial", materialDTO);
	if (materialDTO.getMaterialAttr() != null) {

	    int iSize = materialDTO.getMaterialAttr().length;

	    String[] material = materialDTO.getMaterialAttr();
	    String[] materialProperties = materialDTO.getMaterialPropertiesAttr();

	    materialDTO.setLoginUserId(CommonUtil.getOIDString((WTUser) SessionHelper.manager.getPrincipal()));

	    for (int i = 0; i < iSize; i++) {
		materialDTO.setMaterial(material[i]);
		materialDTO.setMaterialProperties(materialProperties[i]);

		dao.insert("material.insertMaterial", materialDTO);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    public List<KETMaterialDTO> getMaterialList(KETMaterialDTO materialDTO) throws Exception {
	List<KETMaterialDTO> materialDTOList = dao.find("material.searchMaterial", materialDTO);
	// materialDTO.setMaterialDTOList(materialDTOList);
	return materialDTOList;
    }

    public String sendHp(KETDrawingDistReqDTO ketDrawingDistReqDTO) throws Exception {
	String ret = "FAIL";

	boolean bSendHp = DrawingDistHelper.service.sendHp(ketDrawingDistReqDTO);

	if (bSendHp)
	    ret = "OK";
	// List<Map<String, Object>> distReqEpmDocList = DrawingDistHelper.service.sendHpList(ketDrawingDistReqDTO);

	// 전송할 정보의 sql을 작성한다.
	// 작성한 값을 list에 담아 mssql 관련 메소드로 호출시킨다.

	return ret;

    }

}
