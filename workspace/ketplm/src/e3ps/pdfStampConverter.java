package e3ps;

import java.io.File;
import java.io.FileOutputStream;






import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

import wt.util.WTProperties;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
//import com.itextpdf.io.image.ImageData;
//import com.itextpdf.io.image.ImageDataFactory;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfReader;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Image;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

import e3ps.common.util.DateUtil;


public class pdfStampConverter {
    
    public static void main(String args[]) throws Exception{
	
/*	Locale locale1 = new Locale("vi", "VN");
	Locale locale2 = new Locale("pl", "PL");
	
	// print locale  
	System.out.println("Locale 1: " + locale1);  
	  
	// print language  
	System.out.println("Language 1: " + locale1.getDisplayLanguage());  
	
	// print country   
	System.out.println("Country Name 1: "  + locale1.getDisplayCountry());  
	
	
	// print locale  
	System.out.println("Locale 2: " + locale2);  
		  
	// print language  
	System.out.println("Language 2: " + locale2.getDisplayLanguage());  
		
	// print country   
	System.out.println("Country Name 2: "  + locale2.getDisplayCountry());  */
	
/*	WTProperties wtproperties = WTProperties.getLocalProperties();
	String tempdir = wtproperties.getProperty("wt.temp", "");
	
	File tempDir = new File(tempdir);
	String currentDate = DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd"));
	
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	pdfStampConverter co = new pdfStampConverter();
	
	co.tempFileDelete(currentDate, simpleDateFormat, tempDir);
	
	tempDir = new File(tempdir+File.separator+"drm");
	co.tempFileDelete(currentDate, simpleDateFormat, tempDir);
	
	tempDir = new File(tempdir+File.separator+"drm"+File.separator+"DownloadTemp");
	co.tempFileDelete(currentDate, simpleDateFormat, tempDir);
	
	tempDir = new File(tempdir+File.separator+"drm"+File.separator+"UploadTemp");
	co.tempFileDelete(currentDate, simpleDateFormat, tempDir);*/
	
	String path = "D:/ptc/Windchill_10.2/Windchill/codebase/temp/";
	
	Image image = Image.getInstance(path+"전자직인 (1).png");
        //image.setAbsolutePosition(170, 440);
	
	String targetPdfPath = path+"H791320-3_재질성적서_재질_풍산_C2600R(TP) H 0.6 190828.pdf";
	
	File pdf = new File(targetPdfPath);
	
	String stampingFileName = path + StringUtils.removeEnd(pdf.getName().toUpperCase(), ".PDF")+"_stamping.pdf";

	PdfReader reader = new PdfReader(targetPdfPath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(stampingFileName));
        
        int total = reader.getNumberOfPages() + 1;
        
        
        PdfContentByte over = null;

        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.3f);

        
        for(int i = 1; i < total; i++) {
            System.out.println("Procesando Pagina: " + i);
            image.setAbsolutePosition(reader.getPageSize(i).getLeft() + (reader.getPageSize(i).getWidth() / 2)-50 , (reader.getPageSize(i).getHeight() / 2) + 120);
            //image.scalePercent(60.5f);
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            over.addImage(image);
            over.restoreState();
        }

        
        stamper.close();
        
        
	System.out.println("Image added");
    }
    
    public void tempFileDelete(String currentDate, SimpleDateFormat simpleDateFormat, File tempDir){
	if(tempDir.exists()) {

	    File[] folder_list = tempDir.listFiles(); // 파일리스트 얻어오기

	    for (int j = 0; j < folder_list.length; j++) {

		if (folder_list[j].isFile()) {
		    long lastModified = folder_list[j].lastModified();
		    Date lastModifiedDate = new Date(lastModified);
		    String fileModyDate = simpleDateFormat.format(lastModifiedDate);

		    if (!currentDate.equals(fileModyDate)) {
			folder_list[j].delete(); // 파일 삭제
		    }
		}
	    }
	}
    }
}
