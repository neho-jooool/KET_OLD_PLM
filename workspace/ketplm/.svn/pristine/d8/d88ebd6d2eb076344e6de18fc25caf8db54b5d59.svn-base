package ext.ket.yesone.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;

public class Convert2Object {

	public ArrayList<Object> YesoneDtoSetUp(String formCode, ArrayList<Object> YesoneList, ArrayList<Object> setUpParameter) throws Exception, InvocationTargetException {
		try {

			if (formCode == null)
				return null;

			FormTypeEnum[] arry = FormTypeEnum.values();
			Object targetObject = null;
			String EnumFormCode = null;
			for (FormTypeEnum item : arry) {
				EnumFormCode = item.getFormCode();
				if (formCode.equalsIgnoreCase(EnumFormCode) && StringUtils.isNotEmpty(item.getLegacyTBInfo())) {
					String ClassName = (String) item.getObject();

					Class<?> TargetClass = Class.forName(ClassName);
					targetObject = TargetClass.newInstance();

					for (Object param : setUpParameter) {
						BeanUtils.copyProperties(targetObject, param);
					}
					YesoneList.add(targetObject);

					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return YesoneList;
	}

}
