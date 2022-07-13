package ext.ket.shared.code.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.drools.core.util.StringUtils;

import wt.services.ServiceFactory;
import e3ps.common.code.NumberCode;
import e3ps.common.message.KETMessageService;
import ext.ket.shared.code.entity.NumberCodeDTO;
import ext.ket.shared.log.Kogger;

/**
 * @클래스명 : CodeHelper
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class CodeHelper {

    public static final CodeService service = ServiceFactory.getService(CodeService.class);

    public static CodeHelper manager = new CodeHelper();

    /**
     * @param codetype
     * @param code
     * @return
     * @throws Exception
     * @메소드명 : getCodeValue
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getCodeValue(String codetype, String code) throws Exception {

	return getCodeValue(codetype, code, null);
    }

    /**
     * @param codetype
     * @param code
     * @param locale
     * @return
     * @throws Exception
     * @메소드명 : getCodeValue
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getCodeValue(String codetype, String code, Locale locale) throws Exception {

	String codeValue = "";
	try {
	    if (StringUtils.isEmpty(code)) {
		return "";
	    }
	    if (locale == null) {
		KETMessageService messageService = KETMessageService.getMessageService();
		locale = messageService.getLocale();
	    }
	    @SuppressWarnings("unchecked")
	    List<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(codetype);
	    if (arrayList == null || arrayList.size() == 0) {
		// CodeCache에 없을 경우 NumberCodeValue 테이블을 조회한다.
		codeValue = CodeHelper.service.getNumberCodeValue(codetype, code, locale.toString());
	    } else {
		for (NumberCodeDTO numberCodeDTO : arrayList) {
		    if (code.equals(numberCodeDTO.getCode()))
			codeValue = numberCodeDTO.getDisplay(locale);
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return codeValue;
    }

    /**
     * @param codetype
     * @param code
     * @return
     * @throws Exception
     * @메소드명 : getCodeDescription
     * @작성자 : yjlee
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getCodeDescription(String codetype, String code) throws Exception {

	return getCodeDescription(codetype, code, null);
    }

    /**
     * @param codetype
     * @param code
     * @param locale
     * @return
     * @throws Exception
     * @메소드명 : getCodeDescription
     * @작성자 : yjlee
     * @작성일 : 2014. 9. 23.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getCodeDescription(String codetype, String code, Locale locale) throws Exception {

	String codeDescription = "";
	try {
	    if (locale == null) {
		KETMessageService messageService = KETMessageService.getMessageService();
		locale = messageService.getLocale();
	    }
	    @SuppressWarnings("unchecked")
	    List<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(codetype);
	    if (arrayList == null || arrayList.size() == 0) {
		// CodeCache에 없을 경우 NumberCodeValue 테이블을 조회한다.
		codeDescription = CodeHelper.service.getNumberCodeDescription(codetype, code, locale.toString());
	    } else {
		for (NumberCodeDTO numberCodeDTO : arrayList) {
		    if (code.equals(numberCodeDTO.getCode()))
			codeDescription = numberCodeDTO.getDescription();
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return codeDescription;
    }

    /**
     * @param codetype
     * @param name
     * @param locale
     * @return
     * @throws Exception
     * @메소드명 : getCodeCodeByValue
     * @작성자 : yjlee
     * @작성일 : 2014. 9. 25.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public String getCodeCodeByValue(String codetype, String name) throws Exception {

	String codeCode = "";
	try {
	    // if (locale == null) {
	    // KETMessageService messageService = KETMessageService.getMessageService();
	    // locale = messageService.getLocale();
	    // }
	    @SuppressWarnings("unchecked")
	    List<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(codetype);
	    if (arrayList == null || arrayList.size() == 0) {
		// CodeCache에 없을 경우 NumberCodeValue 테이블을 조회한다.
		codeCode = CodeHelper.service.getNumberCodeCodeByName(codetype, name, Locale.KOREA.toString());
	    } else {
		for (NumberCodeDTO numberCodeDTO : arrayList) {
		    if (name != null && name.equals(numberCodeDTO.getValueKO())) {
			codeCode = numberCodeDTO.getCode();
		    }
		}
	    }
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	}

	return codeCode;
    }

    /**
     * @param codetype
     * @param code
     * @return
     * @throws Exception
     * @메소드명 : getNumberCode
     * @작성자 : Jason, Han
     * @작성일 : 2014. 10. 22.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public NumberCode getNumberCode(String codetype, String code) throws Exception {

	NumberCode numberCode = null;
	@SuppressWarnings("unchecked")
	List<NumberCodeDTO> arrayList = (ArrayList<NumberCodeDTO>) CacheManager.getItem(codetype);
	for (NumberCodeDTO numberCodeDTO : arrayList) {
	    if (code.equals(numberCodeDTO.getCode()))
		numberCode = numberCodeDTO.getNumbercode();
	}
	return numberCode;
    }
}
