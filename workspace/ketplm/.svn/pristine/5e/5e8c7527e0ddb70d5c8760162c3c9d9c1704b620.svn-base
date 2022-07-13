package ext.ket.part.code;

import ext.ket.part.code.internal.PartDieNoGenerator;
import ext.ket.part.code.internal.PartEliecItNoGenerator;
import ext.ket.part.code.internal.PartFertNoGenerator;
import ext.ket.part.code.internal.PartHalbNoGenerator;
import ext.ket.part.code.internal.PartHawaNoGenerator;
import ext.ket.part.code.internal.PartMoldNoGenerator;
import ext.ket.part.code.internal.PartOtherNoGenerator;
import ext.ket.part.code.internal.PartPackageNoGenerator;
import ext.ket.part.code.internal.PartRohNoGenerator;
import ext.ket.part.code.internal.PartScrabNoGenerator;
import ext.ket.part.spec.util.PartTypeEnum;

/**
 * 
 * @클래스명 : DaoFactory
 * @작성자 : tklee
 * @작성일 : 2014. 6. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public final class PartNumberingFactory {

    private static PartNumberingFactory instance = new PartNumberingFactory();

    private PartNumberingFactory() {

    }

    public static PartNumberingFactory getInstance() {
	return instance;
    }

    public PartNoGenerable getGenerator(String partType, String numberingCode) {
	
	if(numberingCode != null && ( numberingCode.startsWith("K") || numberingCode.startsWith("HK"))){
	    return new PartEliecItNoGenerator();
	}
	
	PartNoGenerable partNoGenObj = null;
	PartTypeEnum ptype = PartTypeEnum.toPartTypeEnum(partType);
	switch (ptype) {

	case FERT:
	    partNoGenObj = new PartFertNoGenerator();
	    break;
	case HALB:
	    partNoGenObj = new PartHalbNoGenerator();
	    break;
	case ROH:
	    partNoGenObj = new PartRohNoGenerator();
	    break;
	case PACK:
	    partNoGenObj = new PartPackageNoGenerator();
	    break;
	case SCRP:
	    partNoGenObj = new PartScrabNoGenerator();
	    break;
	case DIENO:
	    partNoGenObj = new PartDieNoGenerator();
	    break;
	case MOLD:
	    partNoGenObj = new PartMoldNoGenerator();
	    break;
	case HAWA:
	    partNoGenObj = new PartHawaNoGenerator();
	    break;
	case OTHER:
	    partNoGenObj = new PartOtherNoGenerator();
	    break;
	}

	return partNoGenObj;
    }

}
