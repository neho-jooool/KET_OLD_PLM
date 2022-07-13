package ext.ket.project.trycondition.util;

import org.junit.Test;

import e3ps.common.util.CommonUtil;
import ext.ket.project.trycondition.entity.KETTryCondition;
import ext.ket.shared.test.AbstractUnitTest;

public class TryExcelExportUtilTest extends AbstractUnitTest {

    @Test
    public void testTryExcelExportUtil() throws Exception {
	// KETTryCondition tryCondition = (KETTryCondition) CommonUtil.getObject("ext.ket.project.trycondition.entity.KETTryMold:100002079527");
	KETTryCondition tryCondition = (KETTryCondition) CommonUtil.getObject("ext.ket.project.trycondition.entity.KETTryPress:100002434758");
	TryExcelExportUtil excelExport = new TryExcelExportUtil(tryCondition);
	excelExport.exportExcelTemplate();
	excelExport.saveAs("D:\\new.xlsx");
    }

}
