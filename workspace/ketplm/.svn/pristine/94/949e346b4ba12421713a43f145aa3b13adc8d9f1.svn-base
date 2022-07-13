package ext.ket.part.naming.service.internal;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import e3ps.common.code.NumberCodeType;
import e3ps.common.util.CommonUtil;
import ext.ket.part.code.NumberCodeUtil;
import ext.ket.part.entity.KETPartClassNamingLink;
import ext.ket.part.entity.KETPartNaming;
import ext.ket.part.entity.dto.PartNamingDTO;
import ext.ket.part.util.PartConstants;

public class PartNamingBuilder {

    public KETPartNaming buildDto2Pers(PartNamingDTO paramObject, KETPartNaming _partNaming) throws Exception {

	KETPartNaming partNaming = _partNaming == null ? KETPartNaming.newKETPartNaming() : _partNaming ;

	if(_partNaming == null){
	    String code = NumberCodeUtil.getNextNamingCode(PartConstants.NAMING_SEQUENCE);
	    partNaming.setNamingCode(code);
	}else{
	    partNaming.setNamingCode(paramObject.getNamingCode());
	}
	partNaming.setNamingName(paramObject.getNamingName());
	partNaming.setLev1NumberCodeType(paramObject.getLev1NumberCodeType());
	partNaming.setLev2NumberCodeType(paramObject.getLev2NumberCodeType());
	partNaming.setLev3NumberCodeType(paramObject.getLev3NumberCodeType());
	partNaming.setLev4NumberCodeType(paramObject.getLev4NumberCodeType());
	partNaming.setLev5NumberCodeType(paramObject.getLev5NumberCodeType());
	partNaming.setUseNaming(paramObject.getUseNaming());
	
	return partNaming;
    }
    
    public PartNamingDTO buildPers2Dto(KETPartNaming partNaming, PartNamingDTO paramObject, KETPartClassNamingLink oldNamingLink, Locale locale) throws Exception {

	paramObject.setPartNamingOid(CommonUtil.getOIDString(partNaming));
	paramObject.setNamingCode(partNaming.getNamingCode());
	paramObject.setNamingName(partNaming.getNamingName());
	paramObject.setLev1NumberCodeType(partNaming.getLev1NumberCodeType());
	paramObject.setLev2NumberCodeType(partNaming.getLev2NumberCodeType());
	paramObject.setLev3NumberCodeType(partNaming.getLev3NumberCodeType());
	paramObject.setLev4NumberCodeType(partNaming.getLev4NumberCodeType());
	paramObject.setLev5NumberCodeType(partNaming.getLev5NumberCodeType());
	paramObject.setLev1NumberCodeTypeName(getCodeTypeName(partNaming.getLev1NumberCodeType(), locale));
	paramObject.setLev2NumberCodeTypeName(getCodeTypeName(partNaming.getLev2NumberCodeType(), locale));
	paramObject.setLev3NumberCodeTypeName(getCodeTypeName(partNaming.getLev3NumberCodeType(), locale));
	paramObject.setLev4NumberCodeTypeName(getCodeTypeName(partNaming.getLev4NumberCodeType(), locale));
	paramObject.setLev5NumberCodeTypeName(getCodeTypeName(partNaming.getLev5NumberCodeType(), locale));
	paramObject.setUseNaming(partNaming.isUseNaming());
	// 분류체계 연결되어 있는가?
	paramObject.setExistClazNamingLink(oldNamingLink != null);

	return paramObject;
    }
    
    private String getCodeTypeName(String codeType, Locale locale)throws Exception{
	
	if(StringUtils.isEmpty(codeType)) return StringUtils.EMPTY;
	
	NumberCodeType numberCodeType = NumberCodeType.toNumberCodeType(codeType);
	return numberCodeType.getDisplay(locale);
    }

    public static class Builder {

	private String namingCode;
	private String namingName;
	private boolean useNaming;
	private String lev1NumberCodeType;
	private String lev2NumberCodeType;
	private String lev3NumberCodeType;
	private String lev4NumberCodeType;
	private String lev5NumberCodeType;

	public Builder() {

	}

	public KETPartNaming build() throws Exception {

	    KETPartNaming partNaming = KETPartNaming.newKETPartNaming();

	    partNaming.setNamingCode(namingCode);
	    partNaming.setNamingName(namingName);
	    partNaming.setUseNaming(useNaming);
	    partNaming.setLev1NumberCodeType(lev1NumberCodeType);
	    partNaming.setLev2NumberCodeType(lev2NumberCodeType);
	    partNaming.setLev3NumberCodeType(lev3NumberCodeType);
	    partNaming.setLev4NumberCodeType(lev4NumberCodeType);
	    partNaming.setLev5NumberCodeType(lev5NumberCodeType);

	    return partNaming;
	}

	public Builder setNamingCode(String namingCode) {
	    this.namingCode = namingCode;
	    return this;
	}

	public Builder setNamingName(String namingName) {
	    this.namingName = namingName;
	    return this;
	}

	public Builder setUseNaming(boolean useNaming) {
	    this.useNaming = useNaming;
	    return this;
	}

	public Builder setLev1NumberCodeType(String lev1NumberCodeType) {
	    this.lev1NumberCodeType = lev1NumberCodeType;
	    return this;
	}

	public Builder setLev2NumberCodeType(String lev2NumberCodeType) {
	    this.lev2NumberCodeType = lev2NumberCodeType;
	    return this;
	}

	public Builder setLev3NumberCodeType(String lev3NumberCodeType) {
	    this.lev3NumberCodeType = lev3NumberCodeType;
	    return this;
	}

	public Builder setLev4NumberCodeType(String lev4NumberCodeType) {
	    this.lev4NumberCodeType = lev4NumberCodeType;
	    return this;
	}

	public Builder setLev5NumberCodeType(String lev5NumberCodeType) {
	    this.lev5NumberCodeType = lev5NumberCodeType;
	    return this;
	}
	
    }
}
