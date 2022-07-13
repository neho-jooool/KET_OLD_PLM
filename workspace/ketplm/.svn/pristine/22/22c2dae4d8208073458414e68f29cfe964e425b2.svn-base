package ext.ket.part.code.internal;

import wt.org.WTUser;
import wt.session.SessionHelper;
import ext.ket.part.code.PartGenDTO;
import ext.ket.part.entity.dto.PartBaseDTO;

/**
 * 금형코드 8자리 + 구분자 1자리 + 부품코드 3자리 ( 기존 부품 번호의 확장 )
 * 
 * @클래스명 : PartMoldNoGenerator
 * @작성자 : yjlee
 * @작성일 : 2014. 8. 28.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class PartMoldNoGenerator extends AbsCodeGenerator {

    @Override
    public String generatePartNo(PartGenDTO dto, PartBaseDTO baseDto) throws Exception {

	// if (StringUtils.isEmpty(baseDto.getSpRepresentM()))
	// throw new Exception("##  대표금형(Die No) 값이  없습니다.");

	// 금형부품은 엑셀 일괄 업로드 (BOM Editor) 에서 자동 생성 합니다.
	// 개별 등록 시, 자동 채번 불가합니다.

	// if (dto.getNumberingCode() == null || dto.getNumberingCode().length() < 10)
	// throw new Exception("##  대표금형(Die No)에 맞는 최소 숫자 포멧이(10자)가 아닙니다.");

	// if (dto.getNumberingCode().indexOf("-") == -1)
	// throw new Exception("##  금형번호는 '-'를 포함해야 합니다.");

	// UI에서도 체크함.
	WTUser user = (WTUser) SessionHelper.manager.getPrincipalReference().getPrincipal();
	if (!"Administrator".equalsIgnoreCase(user.getName())) {
	    if (existErpNumber(baseDto.getPartNumber())) {
		throw new Exception(" ERP에 존재하는 부품이 있습니다.");
	    }
	}

	return baseDto.getPartNumber();
    }

}
