package ext.ket.part.classify.oxm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import ext.ket.edm.stamping.oxm.OxmMarshaller;
import ext.ket.part.classify.service.internal.PartClazBuilder;
import ext.ket.part.entity.dto.PartClassificationDTO;
import ext.ket.shared.log.Kogger;

public class ClazOxmParser {

    public String getClazXmlObject(List<PartClassificationDTO> list) throws Exception {

	OxmMarshaller oxmMarshaller = new OxmMarshaller();

	ClazXmlGridObject clazXmlGridObject = getClazXmlGridObject(list);
	String result = oxmMarshaller.convertClassification(clazXmlGridObject);

	return result;

    }

    private ClazXmlGridObject getClazXmlGridObject(List<PartClassificationDTO> list) {

	ClazXmlGridObject grid = new ClazXmlGridObject();

	ClazXmlBodyObject body = new ClazXmlBodyObject();
	grid.setClazXmlBodyObject(body);

	ClazXmlPageObject page = new ClazXmlPageObject();
	body.setClazXmlPageObject(page);

	Map<String, ClazXmlRowObject> clazMap = new HashMap<String, ClazXmlRowObject>();

	// TODO CanDeleted="1" 체크된 상위는 무조건 1을 넣어주기
	for (PartClassificationDTO dto : list) {
	    makeClazXmlRowObject(dto, page, clazMap);
	}

	return grid;
    }

    private ClazXmlRowObject makeClazXmlRowObject(PartClassificationDTO dto, ClazXmlPageObject page, Map<String, ClazXmlRowObject> clazMap) {

	PartClazBuilder builder = new PartClazBuilder();

	ClazXmlRowObject row = builder.getDto2Xml(dto);

	Kogger.debug(getClass(), "ClazXmlRowObject :" + dto.getClassKrName());

	if (StringUtils.isEmpty(dto.getParentOid())) {
	    page.addClazXmlRowObject(row);
	} else {
	    clazMap.get(dto.getParentOid()).addClazXmlRowObject(row);
	}

	clazMap.put(dto.getClazOid(), row);

	return row;
    }

    public static void main(String[] args) {

    }

}
