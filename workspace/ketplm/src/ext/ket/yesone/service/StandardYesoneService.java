package ext.ket.yesone.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.MethodUtils;
import org.springframework.web.multipart.MultipartFile;

import wt.services.StandardManager;
import wt.util.WTException;

import com.dreamsecurity.exception.DVException;
import com.dreamsecurity.verify.DSTSPDFSig;
import com.epapyrus.api.ExportCustomFile;

import e3ps.common.util.DateUtil;
import e3ps.common.util.StringUtil;
import e3ps.cost.util.DBUtil;
import ext.ket.yesone.entity.KETYesoneBaseDTO;
import ext.ket.yesone.entity.KETYesoneDTO;
import ext.ket.yesone.service.internal.dao.YesoneDao;
import ext.ket.yesone.util.Convert2Object;
import ext.ket.yesone.util.CreateXml;
import ext.ket.yesone.util.FormTypeEnum;
import ext.ket.yesone.util.Pdf2Dto;
import ext.ket.yesone.util.XomUnmarshller;
import ext.ket.yesone.xom.DataOxm;
import ext.ket.yesone.xom.DocOxm;
import ext.ket.yesone.xom.FormOxm;
import ext.ket.yesone.xom.ManOxm;
import ext.ket.yesone.xom.YesoneOxm;

public class StandardYesoneService extends StandardManager implements YesoneService {
	private static final long serialVersionUID = 1L;

	public static StandardYesoneService newStandardYesoneService() throws WTException {
		StandardYesoneService instance = new StandardYesoneService();
		instance.initialize();
		return instance;
	}

	@Override
	public KETYesoneDTO validityCheckPDF(KETYesoneDTO ketYesonDTO) {
		// TODO Auto-generated method stub

		String p_pwd = ""; // 비밀번호
		String key = "XML"; // key

		String strXml = "";
		String validityMsg = null;

		try {

			/* 업로드 컨텐츠 목록 추출 */
			MultipartFile file = ketYesonDTO.getPrimaryFile();
			p_pwd = ketYesonDTO.getP_pwd();

			// path 제외한 파일명만 취득
			String[] filePath = file.getOriginalFilename().split("\\\\");
			String fileName = filePath[filePath.length - 1]; // 파일명(ex:abcd.pdf)
			System.out.println("\n\n<!-- [" + fileName + "] -->");

			// PDF파일이 아닌 경우 skip
			if (!fileName.toUpperCase().endsWith(".PDF")) {
				validityMsg = "PDF 파일이 아닙니다.";
			}

			// 파일내용을 읽음
			byte[] pdfBytes = file.getBytes();
			boolean isSuccess = false;

			/* [Step1] 전자문서 위변조 검증 */
			try {
				DSTSPDFSig dstsPdfsig = new DSTSPDFSig();

				dstsPdfsig.init(pdfBytes);
				dstsPdfsig.tokenParse();

				isSuccess = dstsPdfsig.tokenVerify();

				if (isSuccess) {
					System.out.println("<!-- 검증 완료(진본) -->");
				} else {
					String msg = dstsPdfsig.getTstVerifyFailInfo();
					validityMsg = "전자문서 위변조 검증에 실패하였습니다.";
					throw new Exception(msg);
				}
			} catch (DVException e) {
				e.printStackTrace();
				System.out.println("에러 코드 : " + e.getLastError());
				System.out.println("에러 메시지 : " + e.getMessage());

				validityMsg = "전자문서 위변조 검증 중 오류가 발생했습니다.";
			} catch (Exception e) {
				e.printStackTrace();
				validityMsg = "전자문서 위변조 검증 중 오류가 발생했습니다.";
			}

			/* [Step2] XML(or SAM) 데이터 추출 */

			try {
				if (isSuccess) {
					ExportCustomFile pdf = new ExportCustomFile();

					// 데이터 추출
					byte[] buf = pdf.NTS_GetFileBufEx(pdfBytes, p_pwd, key, false);
					int v_ret = pdf.NTS_GetLastError();

					if (v_ret == 1) {
						strXml = new String(buf, "utf-8");
						// 정상적으로 추출된 데이터 활용하는 로직 구현 부분
						System.out.print(strXml);

						if ("ok".equals(ketYesonDTO.getCreateDto())) {
							Pdf2Dto dtoUtil = new Pdf2Dto();
							ketYesonDTO.setStrXml(strXml);
							dtoUtil.CreateJavaFile(ketYesonDTO);
						}

						// System.out.print(strXml.replace("<", "&lt;").replace(">", "&gt;").replace(" ", "&nbsp;").replace("\n",
						// "<br/>").replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;")); // 예제에서는 화면에 출력
					} else if (v_ret == 0) {
						System.out.println("연말정산간소화 표준 전자문서가 아닙니다.");
						validityMsg = "연말정산간소화 표준 전자문서가 아닙니다.";
					} else if (v_ret == -1) {
						System.out.println("비밀번호가 틀립니다.");
						validityMsg = "비밀번호가 틀립니다.";
					} else if (v_ret == -2) {
						System.out.println("PDF문서가 아니거나 손상된 문서입니다.");
						validityMsg = "PDF문서가 아니거나 손상된 문서입니다.";
					} else {
						System.out.println("데이터 추출에 실패하였습니다.");
						validityMsg = "데이터 추출에 실패하였습니다.";
					}
				}

			} catch (Exception e) {
				System.out.println("데이터 추출 실패");
				e.printStackTrace();
				validityMsg = "PDF 데이터 추출 실패입니다.";

			}

		} catch (Exception e) {
			e.printStackTrace();
			validityMsg = "PDF 검증 중 알 수 없는 오류가 발생했습니다.";

		}

		ketYesonDTO.setStrXml(strXml);
		ketYesonDTO.setValidityMsg(validityMsg);

		return ketYesonDTO;
	}

	@Override
	public KETYesoneDTO xmlConvert2rdb(KETYesoneDTO ketYesonDTO) {
		/*
		 * 1단계 : 파싱된 xml 태그 문자열을 xml 파일로 생성한다 2단계 : 생성된 xml파일을 java object로 변환한다 3단계 : 변환된 java object들을 가지고 국세청에서 제공한 서식코드별로 class정보를 모두 찾아서(FormTypeEnum.java에 정의되어있다) instance를
		 * 생성하고, 생성된 각각의 자바 오브젝트의 멤버변수에 값을 할당하고 모든 instance를 ArrayList에 담는다 4단계 : ArrayList를 루핑 돌면서 DataBase Insert
		 */
		boolean goFlag = false;

		String filePath = null;
		String passFilePath = null;

		if ("Y".equals(ketYesonDTO.getDifferentYn())) {
			goFlag = true;
			passFilePath = "D:\\ptc\\yesone\\" + ketYesonDTO.getEmpNo() + "\\" + ketYesonDTO.getEmpNo() + ".xml";
		} else {
			try {
				CreateXml cxml = new CreateXml();

				ketYesonDTO = cxml.String2xml(ketYesonDTO); // 1단계 : xml 파일생성
				goFlag = true;
			} catch (Exception e) {
				e.printStackTrace();
				ketYesonDTO.setValidityMsg("xml 생성 중 오류가 발생했습니다.");
			}

		}
		YesoneOxm root = null;

		if (goFlag) {

			try {

				XomUnmarshller unmarshller = new XomUnmarshller();

				if ("Y".equals(ketYesonDTO.getDifferentYn())) {
					filePath = passFilePath;
				} else {

					filePath = ketYesonDTO.getFilePath();
				}

				root = unmarshller.getYesoneOxmRoot(filePath);// 2단계 : xml을 java object로 변환
			} catch (Exception e) {
				e.printStackTrace();
				ketYesonDTO.setValidityMsg("xml 변환 중 오류가 발생했습니다.");
				goFlag = false;
			}

		}

		String errMsg = null;

		KETYesoneBaseDTO baseDto = new KETYesoneBaseDTO();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			if (goFlag) {

				DocOxm doc = root.getDoc();
				System.out.println(doc.getDoc_type());
				System.out.println(doc.getSeq());
				System.out.println(doc.getAtt_year());

				String cur_year = Integer.toString(Integer.parseInt(DateUtil.getThisYear()) - 1);

				if (!cur_year.equals(doc.getAtt_year())) {
					errMsg = "EPDF문서가 " + cur_year + "년도가 아닙니다.";
					throw new Exception(errMsg);
				}

				ArrayList<Object> YesoneList = new ArrayList<Object>();

				ArrayList<Object> setUpParameter = null;

				String form_cd = null;

				Convert2Object convertUtil = new Convert2Object();

				TreeMap<String, String> differ = new TreeMap<String, String>();

				int ftypeCnt = 0;

				for (FormOxm formElement : root.getFormOxmList()) {
					System.out.println("form_cd : " + formElement.getForm_cd());
					form_cd = formElement.getForm_cd();

					for (ManOxm manElement : formElement.getManOxmList()) {
						differ.put(manElement.getResid(), manElement.getName());
						for (DataOxm dataElement : manElement.getDataOxmList()) {

							setUpParameter = new ArrayList<Object>();
							setUpParameter.add(formElement);
							setUpParameter.add(manElement);
							setUpParameter.add(dataElement);

							YesoneList = convertUtil.YesoneDtoSetUp(form_cd, YesoneList, setUpParameter);// 3단계 : xml java object를 각 서식코드별로
							// 쪼개져있는 class정보를 모두 찾아 멤버변수에 값을
							// 할당한다

							FormTypeEnum Ftype = FormTypeEnum.IsChild(form_cd);
							if (Ftype == null) {
								errMsg = "E정의되지 않은 서식코드가 존재합니다. [ " + form_cd + " ]";
								ftypeCnt++;
								break;
								// throw new Exception(errMsg);
							}
							if (Ftype.isIsChild()) {
								Object target = YesoneList.get(YesoneList.size() - 1);
								MethodUtils.invokeMethod(target, "setMM_List", dataElement.getAmtOxmList());
							}

						}

					}
				}

				if (YesoneList.size() < 2) {
					errMsg = "E내역이 모두 합산된 PDF를 업로드하셔야합니다(단건 불가)";
					throw new Exception(errMsg);
				}

				if (ftypeCnt > 0) {
					errMsg = "E정의되지 않은 서식코드가 존재합니다. [ " + form_cd + " ]";
					throw new Exception(errMsg);
				}

				baseDto.setYear(doc.getAtt_year());
				baseDto.setEmp_no(ketYesonDTO.getEmpNo());

				if (!"ok".equals(ketYesonDTO.getCreateDto())) {

					conn = DBUtil.getConnection(false);

					YesoneDao dao = new YesoneDao(conn, pstmt, rs, baseDto);

					ArrayList<TreeMap<String, String>> CommonCheck = dao.getLoginInfo(baseDto);

					String site = null;
					String resid = null;
					String empNo = null;
					for (TreeMap<String, String> ds : CommonCheck) {

						if ("0".equals(ds.get("REL_CODE"))) {
							site = ds.get("SITE");
							resid = ds.get("MY_RES_NO");
							empNo = ds.get("EMP_NO");
						} else {
							continue;
						}

					}

					String dfName = "";
					int dfcnt = 0;
					int validcnt = 0;

					if (StringUtils.isEmpty(resid)) {
						errMsg = "먼저 [가족등록] 에서 본인내역을 입력하시기 바랍니다.";
						throw new Exception(errMsg);
					}

					// 검증시작 => 1. 본인의 pdf인지 검증, 2. 국세청 pdf와 가족내역이 일치하는지 검증
					for (TreeMap<String, String> ds : CommonCheck) {
						for (int i = 0; i < differ.size(); i++) {

							if (StringUtils.isNotEmpty(differ.get(resid))) {// 본인의 pdf파일인지 검증
								validcnt++;
							}

							if (StringUtils.isEmpty(differ.get(ds.get("MY_RES_NO")))) {// 국세청 pdf와 가족내역이 일치하는지 검증
								dfcnt++;
							}

						}
						if (dfcnt > 0) {
							dfName += ds.get("NAME") + ",";
						}
						dfcnt = 0;
					}

					if (validcnt < 1) {
						errMsg = ketYesonDTO.getName() + " 님 본인의 PDF 파일이 아닙니다.";
						throw new Exception(errMsg);
					}

					dfcnt = 0;

					if (!"Y".equals(ketYesonDTO.getDifferentYn())) {// 국세청 pdf와 가족내역이 일치하는지 검증

						Iterator<String> it = differ.keySet().iterator();

						String name = null;
						it = differ.keySet().iterator();
						for (int i = 0; i < differ.size(); i++) {

							String res_id = it.next();
							name = differ.get(res_id);

							for (TreeMap<String, String> ds : CommonCheck) {

								if (res_id.equals(ds.get("MY_RES_NO"))) {
									dfcnt++;
								}

							}

							if (dfcnt < 1) {
								dfName += name + ",";
							}
							dfcnt = 0;
						}
					}

					if (!StringUtils.isEmpty(dfName)) {
						dfName = StringUtils.removeEnd(dfName, ",");

						dfName = StringUtil.removeDuplicateStringToken(dfName, ",");// 중복 문자열제거
					}

					if (!"Y".equals(ketYesonDTO.getDifferentYn()) && StringUtils.isNotEmpty(dfName)) {
						errMsg = dfName + "_err0101";
						ketYesonDTO.setValidityMsg(errMsg);
						return ketYesonDTO;
					}
					// 검증 end
					baseDto.setSite(site);
					baseDto.setResid(resid);
					String chasu = dao.getBaseChasu(baseDto); // 차수는 PDF업로드 횟수를 의미한다.

					baseDto.setChasu(chasu);

					dao.deleteLegacy(baseDto);// 기존 Legacy데이터 모두 delete

					dao.import2KETYesoneBaseDTO(baseDto);// 4단계 : 기본정보 테이블에 INSERT

					String maxChasu = dao.getMaxChasu(baseDto);

					if (StringUtils.isEmpty(maxChasu)) {
						errMsg = "[데이터 이관중 오류]기본 데이터가 존재하지 않습니다.";
						throw new Exception(errMsg);
					}

					int pk_helper = 0;

					for (Object yesoneDto : YesoneList) {

						dao.import2DtoMappingData(yesoneDto, baseDto, pk_helper);// 4단계 : 서식코드별로 쪼개져있는 테이블에 insert
						pk_helper++;

					}

					FormTypeEnum[] arry = FormTypeEnum.values();

					int DepositInsert = 0; // 개인연금저축, 연금저축,퇴직연금, 장기집합투자증권저축,주택마련저축은 하나의 테이블에 insert되므로 두번 실행되지 않기 위한 플래그

					for (FormTypeEnum item : arry) {

						if (DepositInsert > 0 && item.isIsDeposit()) {
							continue;
						}

						dao.insertLegacy(item.getFormCode(), baseDto, maxChasu);// 5단계 legacy의 실제 연말정산 테이블에 insert

						if (item.isIsDeposit()) {
							DepositInsert++;
						}
					}

					// 신용카드, 직불카드, 현금영수증, 교복, 직업훈련, 교육,보험
					// 국세청에 카드 정보가 없는 사람은 없을 거라 믿고 무조건 한번 수행
					dao.updateLegacy(form_cd, baseDto, maxChasu); // 6단계 legacy 실제 연말정산 테이블 update (HCLRN17)

					conn.commit();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

			if (StringUtils.isNotEmpty(errMsg) && errMsg.substring(0, 1).equals("E")) {

			} else {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				if (StringUtils.isEmpty(errMsg)) {
					errMsg = "데이터 이관 중 오류가 발생했습니다.";
				}
			}

			ketYesonDTO.setValidityMsg(StringUtils.removeStart(errMsg, "E"));

		} finally {
			if ("ok" != ketYesonDTO.getCreateDto() && StringUtils.isNotEmpty(errMsg) && errMsg.substring(0, 1).equals("E")) {
				DBUtil.close(rs);
				DBUtil.close(pstmt);
				DBUtil.close(conn);
			}

		}

		return ketYesonDTO;
	}
}
