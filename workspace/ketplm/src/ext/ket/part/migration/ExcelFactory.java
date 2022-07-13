package ext.ket.part.migration;

import java.io.File;

public class ExcelFactory {

    private static ExcelFactory instance = new ExcelFactory();

    private ExcelFactory() {

    }

    public static ExcelFactory getInstance() {
	return instance;
    }

    public ExcelHandle getExcelManager(String filePath) throws Exception {
	return new DefaultExcelHandler(filePath);
    }

    public ExcelHandle getExcelManager(File file) throws Exception {
	return new DefaultExcelHandler(file);
    }
}
