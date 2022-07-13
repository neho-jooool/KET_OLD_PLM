package ext.ket.part.util;

import org.apache.commons.beanutils.BeanUtils;

import wt.part.WTPart;
import wt.part.WTPartTypeInfo;
import wt.part.WTPartTypeInterface;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class PartSpecSetter {

    public static void setPartSpec(WTPart wtPart, PartSpecEnum partSpecEnum, String value) throws Exception {

	WTPartTypeInterface wtPartTypeInterface = (WTPartTypeInterface) wtPart;
	WTPartTypeInfo wTPartTypeInfo = wtPartTypeInterface.getTypeInfoWTPart();

	Kogger.debug(PartSpecSetter.class, "#" + partSpecEnum.getAttrCode() + ":" +  partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase() + ":" 
	+ partSpecEnum.getAttrName() + ":" + StringUtil.checkNull(value) );
	
	BeanUtils.setProperty(wTPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase(), value);
    }

    public static void setPartSpecWithType(WTPartTypeInfo wtPartTypeInfo, PartSpecEnum partSpecEnum, String value) throws Exception {

	Kogger.debug(PartSpecSetter.class, "#" + partSpecEnum.getAttrCode() + ":" +  partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase() + ":" 
	+ partSpecEnum.getAttrName() + ":" + StringUtil.checkNull(value) );
	BeanUtils.setProperty(wtPartTypeInfo, partSpecEnum.getColumnName().replace("TYPEINFOWTPART", "").toLowerCase(), value);
    }

}
