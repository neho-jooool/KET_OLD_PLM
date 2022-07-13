package ext.ket.part.migration;

import java.io.File;

public class ExcelFactoryHs {

    private static ExcelFactoryHs instance = new ExcelFactoryHs();

    private ExcelFactoryHs() {

    }

    public static ExcelFactoryHs getInstance() {
	return instance;
    }

    public ExcelHandleHs getExcelManager(String filePath) throws Exception {
	return new HssfExcelHandler(filePath);
    }

    public ExcelHandleHs getExcelManager(File file) throws Exception {
	return new HssfExcelHandler(file);
    }
}
