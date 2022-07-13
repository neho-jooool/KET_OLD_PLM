package ext.ket.shared.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
//import org.apache.commons.exec.CommandLine;
//import org.apache.commons.exec.DefaultExecutor;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.MultiStepRescaleOp;

public class msConverterUtil {

    public static final String _PROGRESS_DIRECTORY_ = "D://KET_DOCUMENT//ipdftest//PROGRESS";

    public static void main(String args[]) throws Throwable {

	ExecutorService execService = Executors.newFixedThreadPool(2);
	// System.out.println("CPU코어 수에 해당하는 최대 쓰레드 : "+Runtime.getRuntime().availableProcessors());

	try {

	    String log4j = "D://ptc//Windchill_10.2//Windchill//codebase//ext//ket//shared//util//log4j.xml";
	    DOMConfigurator.configure(log4j);

	    Logger log = Logger.getLogger("ext.ket.shared.util");

	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");
	    log.info("###################################################");
	    log.info("############### inner program start ###############");

	    while (true) {
		// Create a file object
		File file = new File("D://KET_DOCUMENT//ipdftest");

		// 1. check if the file exists or not
		boolean isExists = file.exists();

		if (!isExists) {
		    System.out.println("There is nothing.");
		}

		// 2. check if the object is directory or not.
		if (file.isDirectory()) {
		    File[] fileList = file.listFiles();
		    for (File tFile : fileList) {
			if (!tFile.isDirectory()) {
			    String fileName = FilenameUtils.getExtension(tFile.getName());
			    if ("txt".equals(fileName)) {
				TimeUnit.SECONDS.sleep(1);
				String target = FilenameUtils.removeExtension(tFile.getName());
				File PROGRESS = new File(_PROGRESS_DIRECTORY_);

				if (new File(_PROGRESS_DIRECTORY_ + "//" + tFile.getName()).exists()) {
				    tFile.delete();
				} else {
				    FileUtils.moveFileToDirectory(tFile, PROGRESS, true);
				}
				execService.execute(new MyThreadTask(target + ".xlsx"));

				TimeUnit.SECONDS.sleep(1);
			    } else {

			    }
			}
		    }
		} else {

		}

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    execService.shutdown();
	}

    }

}

class MyThreadTask implements Runnable {
    public static final String ForderPath = "D:\\KET_DOCUMENT\\ipdftest";
    public String orgFile = "DN8RCL_작업표준서_Rev0_180827.xlsx";
    public static final String vbsFile = "C:\\Users\\user\\Desktop\\officeConverter.vbs";
    public static final String imgfileExt = "png"; // 파일 형식은 jpg (png나 bmp 같은 다른 포맷도 됩니다) jpg는 Maximum supported image dimension is 65500 오류
						   // 발생할수있음

    public static final String _PROGRESS_DIRECTORY_ = "D://KET_DOCUMENT//ipdftest//PROGRESS";
    public static final String _SUCCESS_DIRECTORY_ = "D://KET_DOCUMENT//ipdftest//SUCCESS";
    public static final String _FAIL_DIRECTORY_ = "D://KET_DOCUMENT//ipdftest//FAIL";

    @Override
    public void run() {

	File orgExcel = new File(ForderPath + "\\" + orgFile);

	try {
	    // msConverterUtil util = null;
	    boolean isConvert = true;

	    // util = new msConverterUtil();
	    // util.RemoveProcess();

	    System.out.println("OfficeDoc to PDF Convert START");

	    // isConvert = convertExcel2pdf();

	    if (isConvert) {// EXCEL TO PDF CONVERT 성공
		System.out.println(orgFile + " OfficeDoc to PDF Convert END");

		// System.out.println("PDF MERGE START");
		//
		// doPdfMerge();// sheet별로 각각 생성된 pdf를 병합한다
		//
		// System.out.println("PDF MERGE END");

		// File mergePdf = new File(ForderPath + "\\" + FilenameUtils.removeExtension(orgExcel.getName()) + ".pdf");

		// System.out.println("Merge PDF FILE : " + mergePdf.getName());
		//
		// if (mergePdf.exists()) {
		// System.out.println("IMAGE CREATE & MERGE START");
		// doImage(mergePdf);
		// System.out.println("IMAGE CREATE & MERGE END");
		// }else{
		// System.out.println(orgExcel.getName() + ".pdf 가 존재하지 않습니다.");
		// workComplete(_FAIL_DIRECTORY_ , orgExcel);
		// }
		System.out.println("PDF TO IMAGE CONVERT START");
		pdf2Image();
		System.out.println("PDF TO IMAGE CONVERT END");

		workComplete(_SUCCESS_DIRECTORY_, orgExcel);

	    } else {
		System.err.println("excel sheet to pdf convert FAIL !!");
		workComplete(_FAIL_DIRECTORY_, orgExcel);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    try {
		workComplete(_FAIL_DIRECTORY_, orgExcel);
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	} finally {
	    // util.RemoveProcess();
	}
    }

    public MyThreadTask(String filePath) {
	this.orgFile = filePath;
    }

    private void workComplete(String result, File comJSONfile) throws Exception {
	System.out.println("변환 결과 : " + result);
	File targetFile = new File(_PROGRESS_DIRECTORY_ + "//" + FilenameUtils.removeExtension(comJSONfile.getName()) + ".txt");
	if (targetFile.exists()) {
	    if (!new File(result + "//" + FilenameUtils.removeExtension(comJSONfile.getName()) + ".txt").exists()) {
		FileUtils.moveFileToDirectory(targetFile, new File(result), true);
	    }

	}
    }

    private boolean convertExcel2pdf() throws Exception {

	String standardOutput = "";
	String errOutPut = "";
	Process oProcess = null;
	// File orgExcel = new File(ForderPath + "\\" + orgExcelFile);

	boolean isSuccess = true;

	try {
	    System.out.println(orgFile + " 변환 start");
	    oProcess = new ProcessBuilder("cscript", vbsFile, ForderPath + "\\" + orgFile, "B").start();

	    if (0 != oProcess.waitFor()) {
		System.out.println("Command Execute Fail");
	    }

	    // 외부 프로그램 출력 읽기
	    BufferedReader stdOut = new BufferedReader(new InputStreamReader(oProcess.getInputStream()));
	    BufferedReader stdError = new BufferedReader(new InputStreamReader(oProcess.getErrorStream()));

	    // "표준 출력"과 "표준 에러 출력"을 출력
	    while ((standardOutput = stdOut.readLine()) != null) {
		if (StringUtils.isNotEmpty(standardOutput)) {
		    if (standardOutput.toUpperCase().startsWith("ERR")) {
			isSuccess = false;
		    }
		    System.out.println("vbs 실행 표준 출력 : " + standardOutput);
		} else {
		}
	    }
	    while ((errOutPut = stdError.readLine()) != null) {
		isSuccess = false;
		if (StringUtils.isNotEmpty(errOutPut)) {
		    System.err.println("vbs 실행 오류 출력" + errOutPut);
		} else {
		}
	    }

	    // OutputStream pus = oProcess.getOutputStream();

	    // 외부 프로그램 반환값 출력 (이 부분은 필수가 아님)
	    // System.out.println("Exit Code: " + oProcess.exitValue());
	    // System.out.println("output : " + pus);

	} catch (IOException e) {

	    throw new RuntimeException(e);

	} catch (InterruptedException e) {

	    throw new RuntimeException(e);

	} finally {

	    if (null != oProcess) {

		IOUtils.closeQuietly(oProcess.getInputStream());

		IOUtils.closeQuietly(oProcess.getOutputStream());

		IOUtils.closeQuietly(oProcess.getErrorStream());

		oProcess.destroy();

	    } else {

		System.out.println("Cannot close Process Streams");

		throw new RuntimeException("Cannot close Process Streams");

	    }

	}

	return isSuccess;
    }

    private void doPdfMerge() throws Exception {
	List<PDDocument> PDDocList = new ArrayList<PDDocument>();
	List<File> MergeTargetList = new ArrayList<File>();

	Workbook workBook = null;
	File orgExcel = null;
	try {

	    orgExcel = new File(ForderPath + "\\" + orgFile);

	    FileInputStream input = new FileInputStream(orgExcel);

	    if (orgExcel.isFile() && orgExcel.exists()) {
		workBook = WorkbookFactory.create(input);

		int sheetCount = workBook.getNumberOfSheets();
		System.out.println("sheetCount : " + sheetCount);
		for (int i = 0; i < sheetCount; i++) {
		    Sheet sheet = workBook.getSheetAt(i);
		    File pdfFile = new File(ForderPath + "\\" + sheet.getSheetName() + ".pdf");
		    if (pdfFile.isFile() && pdfFile.exists()) {
			MergeTargetList.add(pdfFile);
			PDDocument doc = PDDocument.load(pdfFile);
			PDDocList.add(doc);
		    }
		}
	    }

	    // Instantiating PDFMergerUtility class
	    PDFMergerUtility PDFmerger = new PDFMergerUtility();

	    String destFileName = ForderPath + "\\" + FilenameUtils.removeExtension(orgExcel.getName()) + ".pdf";
	    // Setting the destination file
	    PDFmerger.setDestinationFileName(destFileName);

	    for (File target : MergeTargetList) {
		// adding the source files
		PDFmerger.addSource(target);
	    }

	    // Merging the two documents
	    PDFmerger.mergeDocuments();

	    for (PDDocument target : PDDocList) {
		// Closing the documents
		target.close();
	    }

	    System.out.println("Documents merged");
	    // Closing the documents

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	    throw e;
	} catch (IOException e) {
	    e.printStackTrace();
	    throw e;
	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	}
    }

    private void pdf2Image() throws Exception {

	Workbook workBook = null;
	File source = null;
	try {

	    source = new File(ForderPath + "\\" + orgFile);

	    if (source.isFile() && source.exists()) {

		FileInputStream input = new FileInputStream(source);
		workBook = WorkbookFactory.create(input);

		int sheetCount = workBook.getNumberOfSheets();

		for (int i = 0; i < sheetCount; i++) {
		    Sheet sheet = workBook.getSheetAt(i);

		    String targetPdf = ForderPath + "\\" + sheet.getSheetName() + ".pdf";
		    System.out.println(targetPdf + " " + sheet.isSelected());
		    File pdfFile = new File(ForderPath + "\\" + sheet.getSheetName() + ".pdf");
		    if (pdfFile.isFile() && pdfFile.exists()) {
			System.out.println("총 " + sheetCount + " 개의 시트 중 " + (i + 1) + " 번째 이미지 생성 시작 ->" + pdfFile.getName());
			doImage(pdfFile);
			System.out.println("총 " + sheetCount + " 개의 시트 중 " + (i + 1) + " 번째 이미지 생성 완료 ->" + pdfFile.getName());
		    } else {
			// throw new Exception("생성된 PDF가 없습니다.");
		    }
		}
	    } else {

	    }

	} catch (Exception e) {
	    e.printStackTrace();
	    throw e;
	}
    }

    private void doImage(File file) throws Exception {

	PDDocument document = PDDocument.load(file); // document 생성

	List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>(); // 변환될 이미지 객체를 담을 List 선언

	PDFRenderer renderer = new PDFRenderer(document);

	File source = new File(ForderPath + "\\" + orgFile);
	String targetExt = FilenameUtils.getExtension(source.getName());
	boolean isExcel = false;

	int maxWidth = 0;

	if (targetExt.toUpperCase().startsWith("XL")) {
	    isExcel = true;

	    for (int i = 0; i < document.getNumberOfPages(); i++) {
		BufferedImage image = renderer.renderImageWithDPI(i, 130); // 해상도 조절
		int imgWidth = getTrimmedWidth(image);

		if (imgWidth > maxWidth)
		    maxWidth = imgWidth; // 병합될 이미지의 최대 너비를 구합니다.
	    }
	}

	int width = 0, height = 0; // 병합될 이미지 파일의 너비와 높이 값을 담을 변수

	for (int i = 0; i < document.getNumberOfPages(); i++) {

	    BufferedImage image = renderer.renderImageWithDPI(i, 130); // 해상도 조절

	    if (isExcel) {
		image = whiteSpaceTrim(image, maxWidth);
	    }

	    bufferedImages.add(image);

	    if (image.getWidth() > width)
		width = image.getWidth(); // 병합될 이미지의 최대 너비를 구합니다.
	    height += image.getHeight(); // 병합될 이미지의 최대 높이를 구합니다.

	}

	BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); // 병합될 이미지 객체 생성
	Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics(); // 그래픽 객체를 생성하고,

	graphics.setBackground(Color.WHITE); // 배경을 흰색으로 지정합니다.

	int idx = 0; // 이 부분부터 이미지를 하나로 병합하는 과정입니다.

	for (BufferedImage obj : bufferedImages) { // 이미지 List에서 이미지를 하나씩 꺼내와서
	    if (idx == 0)
		height = 0; // 첫 번째 이미지의 경우 높이를 0으로 설정합니다.
	    graphics.drawImage(obj, 0, height, null); // 그래픽 객체에 꺼내온 이미지를 그려줍니다.

	    height += obj.getHeight(); // 높이값을 이미지의 높이만큼 더합니다.
	    idx++;
	}
	System.out.println("imageReScale start");
	// imageReScale(bufferedImage, file);
	ImageIO.write(bufferedImage, imgfileExt, new File(ForderPath + "//" + FilenameUtils.removeExtension(file.getName()) + "."
	        + imgfileExt)); // 마지막으로 병합된 이미지를 생성합니다.
	System.out.println("imageReScale end");

	graphics.dispose(); // 그래픽 리소스 해제

    }

    private BufferedImage whiteSpaceTrim(BufferedImage img, int width) throws Exception {
	// int width = getTrimmedWidth(img);
	int height = getTrimmedHeight(img);
	if (height < 1) {
	    height = 1;
	}
	BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	Graphics2D g = newImg.createGraphics();
	g.setBackground(Color.WHITE);
	g.drawImage(img, 0, 0, null);
	// ImageIO.write(newImg, fileExt, new File(ForderPath+"//"+ FilenameUtils.removeExtension(file.getName())+".jpg" )); // 각각의 이미지 생성.
	g.dispose(); // 그래픽 리소스 해제

	return newImg;
    }

    private int getTrimmedWidth(BufferedImage img) {
	int height = img.getHeight();
	int width = img.getWidth();
	int trimmedWidth = 0;

	for (int i = 0; i < height; i++) {
	    for (int j = width - 1; j >= 0; j--) {
		if (img.getRGB(j, i) != Color.WHITE.getRGB() && j > trimmedWidth) {
		    trimmedWidth = j;
		    break;
		}
	    }
	}

	return trimmedWidth;
    }

    private int getTrimmedHeight(BufferedImage img) {
	int width = img.getWidth();
	int height = img.getHeight();
	int trimmedHeight = 0;

	for (int i = 0; i < width; i++) {
	    for (int j = height - 1; j >= 0; j--) {
		if (img.getRGB(i, j) != Color.WHITE.getRGB() && j > trimmedHeight) {
		    trimmedHeight = j;
		    break;
		}
	    }
	}

	return trimmedHeight;
    }

    private void RemoveProcess() throws Exception {

	killOfficeExe("EXCEL.EXE");
	killOfficeExe("POWERPNT.EXE");
	killOfficeExe("WINWORD.EXE");

	// while (isProcessRuning("EXCEL.EXE")) {
	// Runtime.getRuntime().exec("taskkill /f /t /IM EXCEL.EXE");
	// Thread.sleep(1000);
	// }
    }

    private void killOfficeExe(String exe) throws Exception {

	System.out.println("taskKill " + exe + " start");

	Runtime.getRuntime().exec("taskkill /f /t /IM " + exe);
	Thread.sleep(1000);

	System.out.println("taskKill " + exe + " End");
    }

    private boolean isProcessRuning(String serviceName) throws Exception {
	Process p = Runtime.getRuntime().exec(serviceName);
	BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	String line;
	while ((line = reader.readLine()) != null) {
	    if (line.contains(serviceName)) {
		return true;
	    }
	}
	return false;
    }

    private void imageReScale(BufferedImage img, File file) throws Exception {// 해상도 변경

	// java-image-scaling 라이브러리
	MultiStepRescaleOp rescale = new MultiStepRescaleOp(1920, 1080);
	rescale.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);

	BufferedImage resizedImage = rescale.filter(img, null);

	ImageIO.write(resizedImage, imgfileExt, new File(ForderPath + "//" + FilenameUtils.removeExtension(file.getName()) + "."
	        + imgfileExt)); // 마지막으로 병합된 이미지를 생성합니다.

    }
}
