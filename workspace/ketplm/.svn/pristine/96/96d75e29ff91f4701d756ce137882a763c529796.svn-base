package officeConverterServer.daemon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mortennobel.imagescaling.AdvancedResizeOp;
import com.mortennobel.imagescaling.MultiStepRescaleOp;

public class WorkTask implements Runnable {

    public static final String ForderPath = "F:\\ConvertFileDownload";
    public String convertServerReqFilePath = "";
    public String sourceFilePath = "";
    public String reqJsonFileName = "";
    public String divide = "";
    public static final String vbsFile = "F:\\converterServer\\BatchScript\\officeConverterServer\\util\\officeConverter.vbs";
    public static final String imgfileExt = "png"; // 파일 형식은 jpg (png나 bmp 같은 다른 포맷도 가능) jpg는 Maximum supported image dimension is 65500 오류
	                                           // 발생할수있음

    public static final String _PROGRESS_DIRECTORY_ = ForderPath + "\\PROGRESS";
    public static final String _SUCCESS_DIRECTORY_ = ForderPath + "\\SUCCESS";
    public static final String _FAIL_DIRECTORY_ = ForderPath + "\\FAIL";
    public JSONObject jsonObject = null;
    Logger log = Logger.getLogger("officeConverterServer");

    public WorkTask(String reqJsonPath) throws Exception {

	File jsonFile = new File(reqJsonPath);

	if (!jsonFile.exists()) {
	    log.error(reqJsonPath + " 파일이 없습니다.");
	    throw new Exception(reqJsonPath + " 파일이 없습니다.");
	}

	JSONParser parser = new JSONParser();

	Object obj = parser.parse(new FileReader(reqJsonPath));

	jsonObject = (JSONObject) obj;
	this.sourceFilePath = (String) jsonObject.get("sourcePath");
	this.reqJsonFileName = jsonFile.getName();
	this.convertServerReqFilePath = (String) jsonObject.get("convertServerReqFilePath");
	this.divide = (String) jsonObject.get("divide");
    }

    @Override
    public void run() {
	// TODO Auto-generated method stub

	long start = 0;
	long end = 0;
	long total = 0;
	String timeStr = "";
	try {
	    boolean isConvert = false;

	    start = System.currentTimeMillis();

	    log.info("OfficeDoc to PDF Convert START");

	    isConvert = convertDoc2pdf();

	    end = System.currentTimeMillis();

	    total = end - start;

	    log.info("=========================================");

	    timeStr = String.format("%d min, %d sec", TimeUnit.MILLISECONDS.toMinutes(total), TimeUnit.MILLISECONDS.toSeconds(total)
		    - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(total)));

	    log.info("=========================================");

	    if (isConvert) {// EXCEL TO PDF CONVERT 성공
		log.info(sourceFilePath + " OfficeDoc to PDF Convert END");

		log.info("PDF TO IMAGE CONVERT START");
		pdf2Image();
		log.info("PDF TO IMAGE CONVERT END");

		workComplete(_SUCCESS_DIRECTORY_);

		try {
		    callPlmServerMethod();
		} catch (Exception e) {
		    log.error(e);
		    workComplete(_FAIL_DIRECTORY_);
		}

	    } else {
		log.info("excel sheet to pdf convert FAIL !!");
		workComplete(_FAIL_DIRECTORY_);
	    }

	} catch (Exception e) {

	    try {
		workComplete(_FAIL_DIRECTORY_);
	    } catch (Exception e1) {
		// TODO Auto-generated catch block
		log.error(e1);
	    }
	} finally {
	    log.info("finished [ " + sourceFilePath + "]  : " + total + " milliseconds, " + timeStr + " minutes");
	    // util.RemoveProcess();
	}
    }

    private void callPlmServerMethod() throws Exception {

	String param = jsonObject.toString();

	// URL url = new URL("http://plmapdev.ket.com/plm/ext/orther/updateConvertFiles2PLM.do");
	String hostName = (String) jsonObject.get("HostName");
	URL url = new URL("http://" + hostName + "/plm/ext/dms/officeDocConvert/updateConvertFiles2PLM.do");
	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	conn.setDoOutput(true);
	conn.setRequestMethod("POST");
	conn.setRequestProperty("Content-Type", "application/json");
	String auth = "wcadmin:wcadmin";

	byte[] authEncBytes = org.apache.commons.net.util.Base64.encodeBase64(auth.getBytes());
	String authStringEnc = new String(authEncBytes);
	conn.setRequestProperty("Authorization", "Basic " + authStringEnc);

	OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

	osw.write(param);
	osw.flush();

	BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	String line = null;
	StringBuffer data = new StringBuffer();
	while ((line = br.readLine()) != null) {
	    data.append(line);
	}
	osw.close();
	log.info(data.toString());
	// 요청 방식 구하기
	log.info("getRequestMethod():" + conn.getRequestMethod());
	// 응답 콘텐츠 유형 구하기
	log.info("getContentType():" + conn.getContentType());
	// 응답 코드 구하기
	log.info("getResponseCode():" + conn.getResponseCode());
	// 응답 메시지 구하기
	log.info("getResponseMessage():" + conn.getResponseMessage());

	conn.disconnect();
    }

    private void workComplete(String result) throws Exception {
	log.info("Convert Result : " + result);
	File targetFile = new File(_PROGRESS_DIRECTORY_ + "//" + reqJsonFileName);
	if (targetFile.exists()) {
	    if (!new File(result + "//" + reqJsonFileName).exists()) {
		FileUtils.moveFileToDirectory(targetFile, new File(result), true);
	    }
	}
    }

    private boolean convertDoc2pdf() {

	boolean isSuccess = true;
	String standardOutput = "";
	String errOutPut = "";
	Process oProcess = null;

	try {

	    log.info(sourceFilePath + " Convert start");

	    oProcess = new ProcessBuilder("cscript", vbsFile, sourceFilePath, divide).start();

	    if (0 != oProcess.waitFor()) {
		log.info("Command Execute Fail");
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
		    log.info("vbs 실행 표준 출력 : " + standardOutput);
		} else {
		}
	    }
	    while ((errOutPut = stdError.readLine()) != null) {
		isSuccess = false;
		if (StringUtils.isNotEmpty(errOutPut)) {
		    log.info("vbs 실행 오류 출력" + errOutPut);
		} else {
		}
	    }

	    // CommandLine cmdLine = new CommandLine("cmd.exe");
	    // cmdLine.addArgument("/c");
	    // cmdLine.addArgument("start");
	    //
	    // cmdLine.addArgument(vbsFile);
	    // cmdLine.addArgument(sourceFilePath);
	    // cmdLine.addArgument("A");
	    //
	    // DefaultExecutor executor = new DefaultExecutor();
	    //
	    // DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	    //
	    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    // PumpStreamHandler streamHandler = new PumpStreamHandler(baos);
	    // executor.setStreamHandler(streamHandler);
	    //
	    // executor.execute(cmdLine, resultHandler);
	    //
	    // resultHandler.waitFor();
	    //
	    // if (resultHandler.getException() != null) {
	    // log.error(resultHandler.getException());
	    // isSuccess = false;
	    // }
	    //
	    // byte[] rtnByte = baos.toByteArray();
	    //
	    // String rtnStr = new String(rtnByte, "UTF-8");
	    // log.info("rtnStr : "+rtnStr);
	    //
	    // if(StringUtils.contains(rtnStr, "ERR")){
	    // isSuccess = false;
	    // }

	} catch (IOException e) {
	    log.error(e);
	    isSuccess = false;

	} catch (Exception e) {
	    log.error(e);
	    isSuccess = false;
	} finally {

	}

	return isSuccess;
    }

    private void pdf2Image() throws Exception {

	Workbook workBook = null;
	File source = null;
	int sheetCount = 0;

	try {

	    source = new File(sourceFilePath);

	    if (source.isFile() && source.exists()) {

		String targetExt = FilenameUtils.getExtension(source.getName());
		if (targetExt.toUpperCase().startsWith("XL")) {

		    if ("OK".equals(divide)) {

			FileInputStream input = new FileInputStream(source);
			workBook = WorkbookFactory.create(input);
			sheetCount = workBook.getNumberOfSheets();

			for (int i = 0; i < sheetCount; i++) {
			    Sheet sheet = workBook.getSheetAt(i);

			    if (workBook.isSheetHidden(i) || workBook.isSheetVeryHidden(i) || sheet.getPhysicalNumberOfRows() < 1) {
				continue;
			    }

			    String sheetName = sheet.getSheetName().replaceAll("[|<>\"]*", "");

			    String pdfFilePath = convertServerReqFilePath + "\\" + sheetName + ".pdf";
			    String imageFilePath = convertServerReqFilePath + "\\" + sheetName + "." + imgfileExt;

			    File pdfFile = new File(pdfFilePath);

			    if (pdfFile.isFile() && pdfFile.exists()) {
				log.info("총 " + sheetCount + " 개의 시트 중 " + (i + 1) + " 번째 이미지 생성 시작 ->" + pdfFile.getName());
				doImage(pdfFile);
				log.info("총 " + sheetCount + " 개의 시트 중 " + (i + 1) + " 번째 이미지 생성 완료 ->" + pdfFile.getName());
			    } else {
				throw new Exception(pdfFilePath + " 생성된 PDF가 없습니다.");
			    }

			    // File imageFile = new File(imageFilePath);
			    //
			    // if (imageFile.isFile() && imageFile.exists()) {
			    //
			    // }else{
			    // throw new Exception(imageFilePath + " 생성된 image가 없습니다.");
			    // }
			}
		    } else {
			String fileName = FilenameUtils.removeExtension(source.getName());
			String pdfFilePath = convertServerReqFilePath + "\\" + fileName + ".pdf";

			File pdfFile = new File(pdfFilePath);

			if (pdfFile.isFile() && pdfFile.exists()) {
			    log.info("이미지 생성 시작 ->" + pdfFile.getName());
			    doImage(pdfFile);
			    log.info("이미지 생성 완료 ->" + pdfFile.getName());
			} else {
			    throw new Exception(pdfFilePath + " 생성된 PDF가 없습니다.");
			}
		    }

		} else {
		    String pdfFilePath = convertServerReqFilePath + "\\" + FilenameUtils.removeExtension(source.getName()) + ".pdf";

		    File pdfFile = new File(pdfFilePath);
		    log.info(" 이미지 생성 시작 ->" + pdfFile.getName());
		    if (pdfFile.isFile() && pdfFile.exists()) {

			doImage(pdfFile);

		    } else {
			throw new Exception(pdfFilePath + " 생성된 PDF가 없습니다.");
		    }
		    log.info("이미지 생성 완료 ->" + pdfFile.getName());

		}

	    } else {
		throw new Exception(sourceFilePath + " 존재하지 않습니다");
	    }

	} catch (Exception e) {
	    log.error(e);
	    throw e;
	}
    }

    private void doImage(File file) throws Exception {
	PDDocument document = null;

	try {

	    document = PDDocument.load(file); // document 생성

	    List<BufferedImage> bufferedImages = new ArrayList<BufferedImage>(); // 변환될 이미지 객체를 담을 List 선언

	    PDFRenderer renderer = new PDFRenderer(document);

	    File source = new File(sourceFilePath);
	    String targetExt = FilenameUtils.getExtension(source.getName());
	    boolean isExcel = false;

	    int maxWidth = 0;

	    // if (targetExt.toUpperCase().startsWith("XL")) {
	    // isExcel = true;
	    //
	    // for (int i = 0; i < document.getNumberOfPages(); i++) {
	    // BufferedImage image = renderer.renderImageWithDPI(i, 130); // 해상도 조절
	    // int imgWidth = getTrimmedWidth(image);
	    //
	    // if (imgWidth > maxWidth)
	    // maxWidth = imgWidth; // 병합될 이미지의 최대 너비
	    // }
	    // }

	    int width = 0, height = 0; // 병합될 이미지 파일의 너비와 높이 값을 담을 변수

	    log.info("pdf pages : " + document.getNumberOfPages());
	    for (int i = 0; i < document.getNumberOfPages(); i++) {

		BufferedImage image = renderer.renderImageWithDPI(i, 130); // 해상도 조절

		// if (isExcel) {
		// image = whiteSpaceTrim(image, maxWidth);
		// }

		bufferedImages.add(image);

		if (image.getWidth() > width)
		    width = image.getWidth(); // 병합될 이미지의 최대 너비
		height += image.getHeight(); // 병합될 이미지의 최대 높이

	    }

	    log.info("png width size = " + width);
	    log.info("png height size = " + height);

	    if (height > 50000) {
		createDivideImage(document.getNumberOfPages(), renderer, file);
	    } else {
		BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // 병합될 이미지 객체 생성

		Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics(); // 그래픽 객체를 생성하고,

		graphics.setBackground(Color.WHITE); // 배경을 흰색으로 지정

		int idx = 0; // 이 부분부터 이미지를 하나로 병합하는 과정

		for (BufferedImage obj : bufferedImages) { // 이미지 List에서 이미지를 하나씩 꺼내와서
		    if (idx == 0) {
			height = 0; // 첫 번째 이미지의 경우 높이를 0으로 설정
		    }
		    graphics.drawImage(obj, 0, height, null); // 그래픽 객체에 꺼내온 이미지를 그린다

		    height += obj.getHeight(); // 높이값을 이미지의 높이만큼 더함
		    idx++;
		}
		// log.info("imageReScale start");
		// imageReScale(bufferedImage, file);
		ImageIO.write(bufferedImage, imgfileExt,
		        new File(convertServerReqFilePath + "//" + FilenameUtils.removeExtension(file.getName()) + "." + imgfileExt)); // 마지막으로
		                                                                                                                       // 병합된
		                                                                                                                       // 이미지를
		                                                                                                                       // 생성한다
		// log.info("imageReScale end");

		graphics.dispose(); // 그래픽 리소스 해제
	    }

	} catch (Exception e) {
	    log.error("이미지 변환 중 오류가 발생했습니다." + file.getName(), e);
	    throw e;
	} finally {
	    document.close();
	}

    }

    private void createDivideImage(int pages, PDFRenderer renderer, File file) throws Exception {

	for (int i = 0; i < pages; i++) {

	    BufferedImage image = renderer.renderImageWithDPI(i, 130); // 해상도 조절
	    image = whiteSpaceTrim(image, image.getWidth());
	    Graphics2D graphics = (Graphics2D) image.getGraphics(); // 그래픽 객체를 생성하고,

	    graphics.setBackground(Color.WHITE); // 배경을 흰색으로 지정
	    graphics.drawImage(image, 0, image.getHeight(), null);

	    ImageIO.write(image, imgfileExt, new File(convertServerReqFilePath + "//" + FilenameUtils.removeExtension(file.getName()) + "_"
		    + i + "." + imgfileExt)); // 마지막으로 병합된 이미지를 생성
	    graphics.dispose();
	}

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

    private void imageReScale(BufferedImage img, File file) throws Exception {// 해상도 변경

	// java-image-scaling 라이브러리
	MultiStepRescaleOp rescale = new MultiStepRescaleOp(1920, 1080);
	rescale.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Soft);

	BufferedImage resizedImage = rescale.filter(img, null);

	ImageIO.write(resizedImage, imgfileExt, new File(ForderPath + "//" + FilenameUtils.removeExtension(file.getName()) + "."
	        + imgfileExt)); // 마지막으로 병합된 이미지를 생성.

    }

}
