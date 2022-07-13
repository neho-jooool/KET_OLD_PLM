package ext.ket.part.base.controller.view;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import wt.util.WTProperties;
import ext.ket.part.entity.dto.PartCatalogueDTO;
import ext.ket.shared.log.Kogger;

public class CatalogueExcelView extends ExcelViewSupport {

    private List<PartCatalogueDTO> rows = null;
    private File destFile = null;
    private String fileExcelPath = null;
    
    public CatalogueExcelView(List<PartCatalogueDTO> rows) throws Exception {
	
	this.rows = rows;
	
	WTProperties prop = WTProperties.getServerProperties();
	String wthome = prop.getProperty("wt.home");

	String templateExcelFilePath = wthome + "\\codebase\\extcore\\jsp\\part\\base\\Catalogue_Template.xls";
	File srcFile = new File(templateExcelFilePath);
	
	SimpleDateFormat ff = new SimpleDateFormat("yyyyMMddHHmmss");
	String yyyyMMddHHmmss = ff.format(new Date());
	File destFile = new File( wthome + "\\temp\\download\\" + "Catalogue_" +  yyyyMMddHHmmss + ".xls");
	
	FileUtils.copyFile(srcFile, destFile);
	
	this.destFile = destFile;
	
	fileExcelPath = wthome + "\\temp\\download\\" + "Catalogue_" +  yyyyMMddHHmmss; 
	
	setUrl(fileExcelPath);
    }
    
    protected HSSFWorkbook getTemplateSource(String url, HttpServletRequest request) throws Exception {
	
	setContentType("application/vnd.ms-excel;charset=UTF-8");
	
	Resource inputFile = new FileSystemResource(destFile); 
	POIFSFileSystem filein = new POIFSFileSystem(inputFile.getInputStream());
//	POIFSFileSystem filein = new POIFSFileSystem(new FileInputStream(destFile));
	HSSFWorkbook wb = new HSSFWorkbook(filein);
	setSheet(wb, 1);
	
	for (PartCatalogueDTO dto : rows) {

	    setText(nextRow(), "KR", leftData);
	    setText(nextCell(), "Y", leftData);
	    setText(nextCell(), dto.getCatalogueCode(), leftData);
	    setText(nextCell(), dto.getPartclassKrName(), leftData);
	    setText(nextCell(), dto.getPartNumber(), leftData) ;
	    setText(nextCell(), dto.getPartName(), leftData);
	    setText(nextCell(), dto.getSpNoOfPole(), leftData);
	    setText(nextCell(), dto.getSpWireRangeAWG(), centerData);
	    setText(nextCell(), dto.getSpWireRangeMm(), centerData);
	    setText(nextCell(), dto.getSpTabThickness(), leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), dto.getSpPlating(), leftData);
	    setText(nextCell(), dto.getSpMaterialInfo(), leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), dto.getSpMaterNotFe(), leftData);
	    setText(nextCell(), dto.getSpColor(), leftData);
	    setText(nextCell(), dto.getSpProdSize(), leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), dto.getSpBracketSizeD(), leftData);
	    setText(nextCell(), dto.getSpBracketSizeH(), leftData);
	    setText(nextCell(), dto.getSpBracketSizeT(), leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), dto.getSpCharact1(), leftData);
	    setText(nextCell(), "", leftData);
	    setText(nextCell(), dto.getSpCertified(), leftData);
	    setText(nextCell(), dto.getRepresentImage(), leftData);
	    setText(nextCell(), dto.getRepresentImageExt(), leftData);
	}
	
	return wb;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) {

	String userAgent = request.getHeader("User-Agent");
	String fileName = null;

	if (userAgent.indexOf("MSIE") > -1) {
	    try {
	        fileName = URLEncoder.encode(destFile.getName(), "utf-8");
            } catch (UnsupportedEncodingException e) {
	        Kogger.error(getClass(), e);
            }
	} else {
	    try {
	        fileName = new String(destFile.getName().getBytes("utf-8"), "iso-8859-1");
            } catch (UnsupportedEncodingException e) {
	        Kogger.error(getClass(), e);
            }
	}

	response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
	response.setHeader("Content-Transfer-Encoding", "binary");
    }
}

