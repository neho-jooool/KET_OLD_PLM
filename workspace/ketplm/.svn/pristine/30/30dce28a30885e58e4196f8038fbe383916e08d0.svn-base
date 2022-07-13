package e3ps.bom.command.loadexcelbom;

import java.awt.Cursor;
import java.awt.Frame;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import e3ps.bom.BOMRegisterApplicationPanel;
import e3ps.bom.command.saveexcel.ExcelFilter;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.aif.AbstractAIFDialog;
import e3ps.bom.framework.aif.AbstractAIFUIApplication;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import e3ps.common.util.JExcelUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

public class LoadExcelBOMDialog extends AbstractAIFDialog {
    private static final long serialVersionUID = 1L;
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");
    private AbstractAIFUIApplication app;
    private Frame frame;
    private JFrame desktop;
    Registry appReg;

    BOMRegisterApplicationPanel pnl;
    BOMAssyComponent rootComponent;
    BOMAssyComponent assyComponent = null;
    BOMSubAssyComponent subComponent = null;

    Vector bomVec = new Vector();
    Vector allSubstituteVec = new Vector();

    String itemCodeStr = "";
    String itemDescStr = "";
    String itemQuantityStr = "";
    String itemUitStr = "";
    String itemStatusStr = "";
    String itemVersionStr = "";

    DBConnectionManager resource = null;
    Connection connection = null;
    Statement stmt = null;
    Statement stmtItem = null;
    ResultSet rsItem = null;

    Workbook readWorkbook = null;

    private int[] sortLevel;
    private String cmpItemCodeStr = "";
    private String parentItemCodeStr = "";
    private String pItemCode = "";
    private String sortOrder = "";
    private int level = 0;
    private int nowLevelNum = 0;
    private Vector checkOutItemVec = new Vector();

    public LoadExcelBOMDialog(JFrame frame, AbstractAIFUIApplication app) {
	this.app = app;
	this.frame = frame;
	this.desktop = frame;
	appReg = Registry.getRegistry(app);
	setEvent();
    }

    private void setEvent() {
	loadExcelOperation();
    }

    private void loadExcelOperation() {
	// int outFileLength = -1;
	resource = DBConnectionManager.getInstance();
	connection = resource.getConnection(appReg.getString("plm"));
	try {
	    pnl = (BOMRegisterApplicationPanel) app.getApplicationPanel();
	    pnl.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	    /************************** JFileChooser 를 이용한 Excel Open *******************************/
	    JFileChooser fchooser = new JFileChooser();
	    fchooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
	    fchooser.addChoosableFileFilter(new ExcelFilter("xls", "Microsoft Excel File"));

	    int rval = fchooser.showOpenDialog(app.getDesktop());
	    if (rval == JFileChooser.APPROVE_OPTION) {
		if (!fchooser.getSelectedFile().getName().substring(fchooser.getSelectedFile().getName().lastIndexOf(".")).equalsIgnoreCase(".xls")) {
		    pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		    MessageBox mbox = new MessageBox(app.getDesktop(), fchooser.getSelectedFile().getName() + " is not Excel File.", "Input Error", 0);
		    mbox.setModal(true);
		    mbox.setVisible(true);
		    return;
		}

		File file = fchooser.getSelectedFile();

		/**
		 * 애플릿 환경차이로 인해 잠시 주석처리함 // 업로드할 파일이 암호화 되어 있는 파일인지 확인 // 리턴 값 (0 : 일반파일, 1 : 암호화된파일) int nRet = JNICipher.IsEncrypted( file.getAbsolutePath() );
		 * 
		 * if( nRet == 1 ) { outFileLength = JNICipher.DecryptFileInPlace( file.getAbsolutePath() ); } Kogger.debug(getClass(), ">>>>>> outFileLength : " + outFileLength); // 0 : 복호화 성공 // 1 : 복호화
		 * 실패 // 2, 3 : 라이브러리 관련 오류(masdmsjni.dll)
		 */
		// ///////////////////////////////////////////////////////////////////////////////////
		// Added by MJOH, 2011-03-30
		// 엑셀파일 복호화 로직 추가
		// ///////////////////////////////////////////////////////////////////////////////////
		Kogger.debug(getClass(), "=======> 엑셀파일 복호화 로직 시작!!!");
		InputStream in = null;
		String dirString = "";

		CashFileUploader uploader = new CashFileUploader("wcadmin");
		// CashFileUploader uploader = new CashFileUploader(userId);
		uploader.addUploadFile(file, true);
		uploader.excute();

		String folder = uploader.getFolder();
		dirString = CachFileUpload.TEMPDIR + "/" + folder;
		String drmPath = dirString + "/" + file.getName();

		ContentDownload down = new ContentDownload();
		down.addContentStream(drmPath);
		down.execute();

		in = down.getInputStream();
		// wb = Workbook.getWorkbook(in);
		Kogger.debug(getClass(), "=======> 엑셀파일 복호화 로직 종료!!!");
		// ///////////////////////////////////////////////////////////////////////////////////

		readWorkbook = Workbook.getWorkbook(in);
		Sheet s = readWorkbook.getSheet(0);

		Cell[] row = null;

		// BOM Max Depth 는 20으로 설정한다.
		sortLevel = new int[20];
		for (int idx = 0; idx < 20; idx++)
		    sortLevel[idx] = 0;

		try { // getContents() 함수때문에 try-catch 로 묶음

		    String strContents = s.getRow(0)[0].getContents();
		    if (strContents == null) {
			strContents = "";
		    } else {
			strContents = s.getRow(0)[0].getContents().trim();
		    }

		    if (!strContents.equalsIgnoreCase(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00102")/* 금형번호 */)) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

			MessageBox mbox = new MessageBox(app.getDesktop(), messageRegistry.getString("excelFile"), "Input Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		} catch (Exception e) {
		}

		String s_parentitemcode = "";
		String s_childitemcode = "";
		String s_desc = "";
		String s_quantity = "";
		String s_designdate = "";
		String s_material = "";
		String s_hardnessfrom = "";
		String s_hardnessto = "";
		String s_cad_number = "";
		String s_con_item = "";

		Vector vc = new Vector();
		Hashtable ht = null;
		String strVal = "";
		// s.getRows() 엑셀 행갯수
		for (int i = 1; i < s.getRows(); i++) {
		    row = s.getRow(i);
		    // for (int j = 0; row!=null && j<row.length; j++)
		    // {
		    // if (row[j]!=null && row[j].getType() != CellType.EMPTY)
		    // {
		    // / if(i > 0) // row 1번 부터 Data
		    // {
		    try {
			s_parentitemcode = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 0));
		    } catch (Exception e) {
		    }
		    try {
			s_childitemcode = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 1));
		    } catch (Exception e) {
		    }
		    try {
			s_desc = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 2));
		    } catch (Exception e) {
		    }
		    try {
			s_quantity = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 3));
		    } catch (Exception e) {
		    }
		    try {
			s_designdate = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 4));
		    } catch (Exception e) {
		    }
		    try {
			s_material = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 5));
		    } catch (Exception e) {
		    }
		    try {
			s_hardnessfrom = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 6));
		    } catch (Exception e) {
		    }
		    try {
			s_hardnessto = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 7));
		    } catch (Exception e) {
		    }
		    try {
			s_cad_number = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 8));
		    } catch (Exception e) {
		    }
		    try {
			s_con_item = StringUtil.checkNull(JExcelUtil.getContent(s.getRow(i), 9));
		    } catch (Exception e) {
		    }

		    // [2011-02-28] 추가요구사항 처리 :: BOM 신규 생성시 ERP 에 존재여부 확인
		    strVal = KETBomHelper.service.getIsBomComponent(s_parentitemcode);
		    if (strVal != null && !strVal.equals("") && strVal.equals("X")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop,
			        KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00117", s_parentitemcode)/* 금형셋트({0}) 는 이미 ERP에 BOM이 구성되어있습니다. */, "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }

		    if (s_desc.equals("")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00111", s_childitemcode)/* 금형부품번호({0})의 금형부품 설명에 값이 없습니다. */,
			        "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		    if (s_quantity.equals("")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00113", s_childitemcode)/* 금형부품번호({0})의 수량에 값이 없습니다. */, "Error",
			        0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		    if (s_designdate.equals("")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00112", s_childitemcode)/* 금형부품번호({0})의 설계일자에 값이 없습니다. */,
			        "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		    if (s_material.equals("")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00114", s_childitemcode)/* 금형부품번호({0})의 재질에 값이 없습니다. */, "Error",
			        0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		    if (s_hardnessfrom.equals("")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00109", s_childitemcode)/* 금형부품번호({0})의 경도(From)에 값이 없습니다.. */,
			        "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		    if (s_hardnessto.equals("")) {
			pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			MessageBox mbox = new MessageBox(desktop, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00110", s_childitemcode)/* 금형부품번호({0})의 경도(To)에 값이 없습니다.. */,
			        "Error", 0);
			mbox.setModal(true);
			mbox.setVisible(true);
			return;
		    }
		    /*
	             * if( s_cad_number.equals("") ) { pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); MessageBox mbox = new MessageBox(desktop, "금형부품번호("+s_childitemcode +
	             * ")의 CAD_Number에 값이 없습니다..", "Error", 0); mbox.setModal(true); mbox.setVisible(true); return; } if( s_cad_type.equals("") ) {
	             * pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)); MessageBox mbox = new MessageBox(desktop, "금형부품번호("+s_childitemcode + ")의 CAD유형에 값이 없습니다..", "Error", 0);
	             * mbox.setModal(true); mbox.setVisible(true); return; }
	             */

		    // date 형 문제로 체크 메소드 하나 넣음... (YYYY-MM-DD)
		    s_designdate = getDateval(s_designdate);

		    ht = new Hashtable();
		    ht.put("PARENTITEMCODE", s_parentitemcode); // 금형(Die) 번호
		    ht.put("CHILDITEMCODE", s_childitemcode); // 금형부품 번호
		    ht.put("DESC", s_desc); // 금형부품 명
		    ht.put("QUANTITY", s_quantity); // 수량
		    ht.put("DESIGNDATE", s_designdate); // 설계일자
		    ht.put("MATERIAL", s_material); // 재질
		    ht.put("HARDNESSFROM", s_hardnessfrom); // 경도 From
		    ht.put("HARDNESSTO", s_hardnessto); // 경도 To
		    ht.put("CADNO", s_cad_number); // CAD Number (도면 유/무)
		    ht.put("CONITEM", s_con_item); // 대표부품 번호
		    vc.add(ht);
		    // }

		    // break;
		    // } //if (row[j]!=null && row[j].getType() != CellType.EMPTY)
		    // }
		}
		confirmLineSearch(vc);

	    }
	    pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	    /************************************************************************************************/
	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    pnl.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

	    try {
		connection.rollback();
	    } catch (Exception dbex) {
	    }

	    pnl.clearBOMList();

	    MessageBox mbox = new MessageBox(app.getDesktop(), "Load Excel BOM Error : " + e.toString(), "Error", 0);
	    mbox.setModal(true);
	    mbox.setVisible(true);
	} finally {
	    try {
		if (rsItem != null) {
		    rsItem.close();
		    rsItem.close();
		}

		if (stmtItem != null) {
		    stmtItem.close();
		    stmtItem.close();
		}
	    } catch (Exception ex) {
		Kogger.error(getClass(), ex);
	    }

	    if (resource != null) {
		resource.freeConnection(appReg.getString("plm"), connection);
	    }

	    if (readWorkbook != null) {
		readWorkbook.close();
	    }
	}
    }

    private void confirmLineSearch(Vector vc) {
	try {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

	    LoadExcelBOMFrame lineSearchDlg = new LoadExcelBOMFrame(this.desktop, app, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00104")/* 금형부품 목록 */, vc);

	    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	} catch (Throwable ex) {
	    this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	    Kogger.error(getClass(), ex);
	}
    }

    private String getDateval(String s_designdate) {
	String vail_date = "";
	String s_year = "";
	String s_month = "";
	String s_day = "";
	try {
	    if (s_designdate.indexOf("/") > 0) {
		try {
		    StringTokenizer token = new StringTokenizer(s_designdate, "/");
		    s_month = token.nextToken();
		    s_day = token.nextToken();
		    s_year = token.nextToken();

		    if (s_month.length() == 1) {
			s_month = "0" + s_month;
		    }
		    if (s_day.length() == 1) {
			s_day = "0" + s_day;
		    }
		    if (s_year.length() == 4) {
			s_designdate = s_year + "-" + s_month + "-" + s_day;
		    } else if (s_year.length() == 2) {
			vail_date = "20" + s_year + "-" + s_month + "-" + s_day;
		    }
		} catch (Exception e) {
		    vail_date = s_designdate;
		}
	    } else if (s_designdate.indexOf(".") > 0) {
		try {
		    StringTokenizer token = new StringTokenizer(s_designdate, ".");
		    s_year = token.nextToken();
		    s_month = token.nextToken();
		    s_day = token.nextToken();
		    if (s_month.length() == 1) {
			s_month = "0" + s_month;
		    }
		    if (s_day.length() == 1) {
			s_day = "0" + s_day;
		    }
		    if (s_year.length() == 4) {
			vail_date = s_year + "-" + s_month + "-" + s_day;
		    }
		} catch (Exception e) {
		    vail_date = s_designdate;
		}
	    } else {
		try {
		    String s_temp = String.valueOf(Integer.parseInt(s_designdate));
		    s_year = s_temp.substring(0, 4);
		    s_month = s_temp.substring(4, 6);
		    s_day = s_temp.substring(6, 8);
		    vail_date = s_year + "-" + s_month + "-" + s_day;
		} catch (Exception e) {
		    vail_date = s_designdate;
		}
	    }
	} catch (Exception e) {
	    vail_date = s_designdate;
	}
	// Kogger.debug(getClass(), "############# vail_date : "+vail_date);
	return vail_date;
    }
}
