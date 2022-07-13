package e3ps.bom.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;

import wt.lifecycle.State;
import wt.part.WTPart;
import wt.util.WTException;
import e3ps.bom.dao.PartDao;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETPartHelper;
import e3ps.common.content.fileuploader.FormUploader;
import e3ps.common.content.uploader.FileUploader;
import e3ps.common.content.uploader.WBFile;
import e3ps.common.message.KETMessageService;
import e3ps.common.treegrid.TreeGridStringBuffer;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.KETParamMapUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.PropertiesUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import e3ps.common.web.PageControl;
import e3ps.common.web.ParamUtil;
import e3ps.common.web.WebUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.project.beans.MoldProjectHelper;
import e3ps.sap.ErpBomMigInfoInterface;
import e3ps.sap.ItemMasterInterface;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class PartServlet extends CommonServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doService(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String cmd = ParamUtil.getParameter(req, "cmd");
		Kogger.debug(getClass(), "=====> PartServlet: cmd=[" + cmd + "]");

		if ("create".equalsIgnoreCase(cmd)) {
			create(req, res);
		} else if ("createExcel".equalsIgnoreCase(cmd)) {
			excel(req, res);
		} else if ("modify".equalsIgnoreCase(cmd)) {
			modify(req, res);
		} else if ("openSearch".equalsIgnoreCase(cmd)) {
			openSearch(req, res);
		} else if ("search".equalsIgnoreCase(cmd)) {
			search(req, res);
		} else if ("openSearchPopup".equalsIgnoreCase(cmd)) {
			openSearchPopup(req, res);
		} else if ("searchPop".equalsIgnoreCase(cmd)) {
			searchPop(req, res);
		} else if ("searchExcel".equalsIgnoreCase(cmd)) {
			excel(req, res);
		} else if ("delete".equalsIgnoreCase(cmd)) {
			// delete(req, res);
		} else if ("mig".equalsIgnoreCase(cmd)) {
			migration(req, res);
		} else if ("part".equalsIgnoreCase(cmd)) {
			migrationPart(req, res);
		}
	}

	/**
	 * 
	 * 함수명 : create 설명 : 부품정보 등록
	 * 
	 * @param req
	 * @param res
	 *            작성자 : 홍길동 작성일자 : 2010. 09. 13.
	 */
	private void create(HttpServletRequest req, HttpServletResponse res) {
		Hashtable<?, ?> hashTestPart = null;
		Hashtable<String, String> hash = null;

		try {
			hash = WebUtil.getHttpParams(req);

			hashTestPart = KETPartHelper.service.create(hash);
			if (hashTestPart != null) {
				alert(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00307")/* 저장 성공 */+ "!");
				if (hash.get("partType").equals("DIEM")) { // 금형부품인 경우에만 I/F 처리함
					// ItemMasterInterface.setItemMasterResultInfo((String)hashTestPart.get("number"), true);
					ItemMasterInterface.setItemMasterResultInfo(hash, true);
				}
			} else {
				alert(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/* 저장 실패 */+ "!");
			}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
	}

	/**
	 * 
	 * 함수명 : createExcel 설명 : Excel 업로드를 통한 부품 생성
	 * 
	 * @param req
	 * @param res
	 *            작성일자 : 2010. 10. 22.
	 */
	private void createExcel(HttpServletRequest req, HttpServletResponse res) {

		Hashtable<?, ?> hashPartExcel = null;
		Hashtable<String, String> hash = null;
		try {

			hash = WebUtil.getHttpParams(req);
			// hash[0] = new Hashtable<String, String>();
			// hash[1] = new Hashtable<String, String>();
			// hash[2] = new Hashtable<String, String>();
			//
			// hash[0].put("itemCode", "0000000033");
			// hash[0].put("itemName", "DIE PLATE(350*370*31)");
			// hash[0].put("unit", "ea");
			//
			// hash[1].put("itemCode", "0000000034");
			// hash[1].put("itemName", "DIE PLATE(350*370*32)");
			// hash[1].put("unit", "ea");
			//
			// hash[2].put("itemCode", "0000000035");
			// hash[2].put("itemName", "DIE PLATE(350*370*33)");
			// hash[2].put("unit", "ea");

			hashPartExcel = KETPartHelper.service.createByExcel(hash);
			if (hashPartExcel != null) {
				alert(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00307")/* 저장 성공 */+ "!");
			} else {
				alert(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/* 저장 실패 */+ "!");
			}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
	}

	/**
	 * 
	 * 함수명 : update 설명 : 부품 정보 수정
	 * 
	 * @param req
	 * @param res
	 *            작성일자 : 2010. 11. 1.
	 */
	private void modify(HttpServletRequest req, HttpServletResponse res) {

		Hashtable<?, ?> hashPartExcel = null;
		Hashtable<String, String> hash = null;
		try {

			hash = WebUtil.getHttpParams(req);

			hashPartExcel = KETPartHelper.service.modify(hash);
			if (hashPartExcel != null) {
				alert(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00242")/* 수정 성공 */+ "!");
			} else {
				alert(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00243")/* 수정 실패 */+ "!");
			}
		} catch (WTException e) {
			Kogger.error(getClass(), e);
		}
	}

	// 부품 검색
	private void search_orig(HttpServletRequest req, HttpServletResponse res) {

		Connection conn = null;
		Hashtable rsHash = new Hashtable();
		ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();

		try {

			// Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			Hashtable<String, String> hash = uploader.getFormParameters();
			Kogger.debug(getClass(), "@@@@@@@@@@ hash  : " + hash);
			// 검색 페이지
			int page = StringUtil.getIntParameter(hash.get("page"), 1);
			// 페이지 당 목록수
			int perPage = StringUtil.getIntParameter(hash.get("perPage"), 10);

			int startRow = (page - 1) * perPage + 1;
			int endRow = page * perPage;

			// 검색 목록 시작&끝 셋팅
			hash.put("startRow", Integer.toString(startRow));
			hash.put("endRow", Integer.toString(endRow));
			hash.put("sort", StringUtil.checkReplaceStr(hash.get("sort"), "1 DESC"));
			hash.put("isExcel", "N");

			// 커넥션 생성
			conn = PlmDBUtil.getConnection();
			PartDao dao = new PartDao(conn);
			// 목록 결과
			partList = dao.searchPartList(hash);
			// 목록 결과 갯수
			int listCnt = dao.searchPartListCnt(hash);

			// 페이지 정보
			PageControl pageControl = new PageControl(page, 10, perPage, listCnt);
			pageControl.setPostMethod();
			pageControl.setHref("/plm/servlet/e3ps/PartServlet");

			rsHash.put("partList", partList);
			rsHash.put("pControl", pageControl);

			req.setAttribute("tmpHash", rsHash);
			req.setAttribute("reqData", hash);

			// 검색화면으로 이동
			// if (hash.get("modal") != null && !hash.get("modal").equals("")) {
			// gotoResult(req, res, "/jsp/bom/SearchPartPopup.jsp");
			// } else {
			gotoResult(req, res, "/jsp/bom/SearchPart.jsp");
			// }

		} catch (ServletException e) {
			Kogger.error(getClass(), e);
		} catch (IOException e) {
			Kogger.error(getClass(), e);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			// 커넥션 종료
			PlmDBUtil.close(conn);
		}
	}

	/*
	 * [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용 수정일자 : 2013. 5. 29 수정자 : 오명재
	 */
	// 부품 검색
	private void search(HttpServletRequest req, HttpServletResponse res) {
		Connection conn = null;

		try {
			// Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

			// String[] inputPartNoAry = null;
			// String inputPartNos = paramMap.getString("inputPartNos");
			// if (StringUtil.checkString(inputPartNos)) {
			// inputPartNoAry = inputPartNos.split(",");
			// Kogger.debug(getClass(), "PartServlet.search: inputPartNoAry length=" + inputPartNoAry.length);
			// }

			if (!paramMap.getString("inputPartNos").equals("")) {
				paramMap.put("number", paramMap.getString("inputPartNos"));
			}

			// 커넥션 생성
			conn = PlmDBUtil.getConnection();
			PartDao dao = new PartDao(conn);

			// 부품번호 입력 팝업으로부터의 데이터가 있으면 임시테이블에 저장 (이후 검색 시 join)
			// String partNoTableKey = null;
			// if (inputPartNoAry != null && inputPartNoAry.length > 0) {
			// String loginId = KETObjectUtil.getLoginUserId();
			// partNoTableKey = dao.getPartNoTableKey(loginId, inputPartNoAry);
			// paramMap.put("PartNoTableKey", partNoTableKey);
			// }

			// 목록 결과
			// [Start] 결과 내 재검색
			/*
			 * ArrayList<Hashtable<String, String>> partList = dao.searchPartList(paramMap); // 기존 검색 주석처리
			 */
			boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
			List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchPart", searchInResult, req);
			conditionList.add(paramMap);
			ArrayList<Hashtable<String, String>> partList = dao.searchPartListSIR(conditionList);
			// [End] 결과 내 재검색

			TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();
			KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();
			int resultSize = partList.size();

			for (Hashtable<String, String> partHash : partList) {

				/*
				 * Start [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 5. 29, 오명재
				 */
				// part.put( "number", StringUtil.checkNull(rs.getString("wtpartnumber")) );
				// part.put( "name", StringUtil.checkNull(rs.getString("name")) );
				// part.put( "type", StringUtil.checkNull(rs.getString("type")) );
				// part.put( "isDeleted", StringUtil.checkNull(rs.getString("isDeleted")) );
				// part.put( "state", rs.getString("state") );
				// part.put( "stateKr", rs.getString("stateKr") );
				// part.put( "unit", StringUtil.checkNull(rs.getString("unit")) );
				// part.put( "versionSort", StringUtil.checkNull( rs.getString("versionSort") ) );
				// part.put( "version", StringUtil.checkNull( rs.getString("version") ) );
				// part.put( "iteration", rs.getString("iteration") );
				// part.put( "latestIteration", rs.getString("latestIteration") );
				// part.put( "checkout", rs.getString("checkout") );
				// part.put( "oid", StringUtil.checkNull(rs.getString("oid")) );
				// part.put( "oidMaster", StringUtil.checkNull(rs.getString("oidMaster")) );
				/*
				 * End [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 5. 29, 오명재
				 */

				WTPart wt = (WTPart) KETObjectUtil.getObject("wt.part.WTPart:" + partHash.get("oid"));

				String part_group = null; // PartSpecGetter.getPartSpec(wt, PartSpecEnum.PartGroup); // 삭제됨 - 1차고도화
				if (StringUtil.isEmpty(part_group)) {
					part_group = "-";
				}
				String part_weight = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpNetWeight);
				if (StringUtil.isEmpty(part_weight)) {
					part_weight = "-";
				}
				String part_isYazaki = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpIsYazaki);
				if (StringUtil.isEmpty(part_isYazaki)) {
					part_isYazaki = "-";
				}
				String part_isYazakiNo = PartSpecGetter.getPartSpec(wt, PartSpecEnum.SpYazakiNo);
				if (StringUtil.isEmpty(part_isYazakiNo)) {
					part_isYazakiNo = "-";
				}

				String viewUrl = "javascript:viewPart('" + partHash.get("oid") + "');";

				strBuffer.append("<I ");
				strBuffer.appendRepl(" NoColor=\"2\" CanDelete=\"0\" Calculated=\"1\"");
				strBuffer.appendRepl(" RowNum=\"" + resultSize-- + "\"");
				strBuffer.appendRepl(" PartType=\"" + partHash.get("type") + "\"");
				strBuffer.appendRepl(" PartNo=\"" + partHash.get("number") + "\"" + " PartNoOnClick=\"" + viewUrl + "\"" + " PartNoHtmlPrefix=\"<font color='"
						+ PropertiesUtil.getSearchGridLinkColor() + "'>\" PartNoHtmlPostfix=\"</font>\"");
				strBuffer.appendRepl(" PartName=\"").appendContent(partHash.get("name")).appendRepl("\"");
				strBuffer.appendRepl(" Ver=\"" + partHash.get("version") + "\"");
				strBuffer.appendRepl(" Status=\"" + State.toState(partHash.get("state")).getDisplay() + "\"");
				strBuffer.appendRepl(" Unit=\"" + bean.getUnitDisplayValue(partHash.get("unit")) + "\"");
				strBuffer.appendRepl(" Del=\"" + partHash.get("isDeleted") + "\"");
				strBuffer.appendRepl(" PartGroup=\"" + part_group + "\"");
				strBuffer.appendRepl(" PartWeight=\"" + part_weight + "\"");
				strBuffer.appendRepl(" IsYazaki=\"" + part_isYazaki + "\"");
				strBuffer.appendRepl(" YazakiNo=\"" + part_isYazakiNo + "\"");
				strBuffer.append("/>");
			}

			/*
			 * Start [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 5. 29, 오명재
			 */
			req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
			req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터
			/*
			 * End [PLM System 1차개선] 수정내용 : 검색기능 서블릿 적용, 2013. 5. 29, 오명재
			 */
			gotoResult(req, res, "/jsp/bom/SearchPart.jsp");

			// 검색 쿼리 실행 후 부품번호 임시테이블 데이터 삭제
			// if (StringUtil.checkString(partNoTableKey)) {
			// // dao.clearPartNoTable(partNoTableKey);
			// }

		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			// 커넥션 종료
			PlmDBUtil.close(conn);
		}
	}

	private void openSearch(HttpServletRequest req, HttpServletResponse res) {
		try {
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
			req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
			gotoResult(req, res, "/jsp/bom/SearchPart.jsp");
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	// 부품 검색 팝업
	private void searchPop(HttpServletRequest req, HttpServletResponse res) {
		Connection conn = null;

		try {
			// Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			KETParamMapUtil paramMap = KETParamMapUtil.getMap(uploader);

			// String[] inputPartNoAry = null;
			// String inputPartNos = paramMap.getString("inputPartNos");
			// if (StringUtil.checkString(inputPartNos)) {
			// inputPartNoAry = inputPartNos.split(",");
			// Kogger.debug(getClass(), "PartServlet.searchPop: inputPartNoAry length=" + inputPartNoAry.length);
			// }

			if (!paramMap.getString("inputPartNos").equals("")) {
				paramMap.put("number", paramMap.getString("inputPartNos"));
			}

			// 커넥션 생성
			conn = PlmDBUtil.getConnection();
			PartDao dao = new PartDao(conn);

			// 부품번호 입력 팝업으로부터의 데이터가 있으면 임시테이블에 저장 (이후 검색 시 join)
			// String partNoTableKey = null;
			// if (inputPartNoAry != null && inputPartNoAry.length > 0) {
			// String loginId = KETObjectUtil.getLoginUserId();
			// partNoTableKey = dao.getPartNoTableKey(loginId, inputPartNoAry);
			// paramMap.put("PartNoTableKey", partNoTableKey);
			// }

			// 목록 결과
			// [Start] 결과 내 재검색
			/*
			 * ArrayList<Hashtable<String, String>> partList = dao.searchPartList(paramMap); // 기존 검색 주석처리
			 */
			boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
			List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchPartPopup", searchInResult, req);
			conditionList.add(paramMap);
			ArrayList<Hashtable<String, String>> partList = dao.searchPartListSIR(conditionList);
			// [End] 결과 내 재검색

			Kogger.debug(getClass(), "PartServlet.searchPop: partList size=" + partList.size());

			TreeGridStringBuffer strBuffer = new TreeGridStringBuffer();

			String die_no = "";
			String dieName = "";
			String dieCnt = "0";
			String isDeleted = "";

			for (int listCnt = 0; listCnt < partList.size(); listCnt++) {
				Hashtable<String, String> part = partList.get(listCnt);

				/*
				 * if( part.get("type").equals("제품") ) { die_no = StringUtil.checkNull(MoldProjectHelper.getDieNo(part.get("number"))); dieCnt = MoldProjectHelper.getDieNoCnt(
				 * part.get("number") ); } else if( part.get("type").equals("Die No") ) { die_no = StringUtil.checkNull(EcmSearchHelper.manager.getRelatedPartNo(
				 * part.get("number")) ); }
				 */

				// 제품
				if (PartUtil.isProductType(part.get("typeCode"))) {
					die_no = StringUtil.checkNull(MoldProjectHelper.getDieNo(part.get("number")));
					dieCnt = MoldProjectHelper.getDieNoCnt(part.get("number"));
				}
				// 금형
				else if ("D".equals(part.get("typeCode"))) {
					die_no = StringUtil.checkNull(EcmSearchHelper.manager.getRelatedPartNo(part.get("number")));
				}

				dieName = StringUtil.checkNull(EcmSearchHelper.manager.getRelatedPartName(die_no));

				isDeleted = part.get("isDeleted");

				String viewUrl = "javascript:viewPart('" + part.get("oid") + "');";

				// 삭제 Flag 값이 "N" 인 겻들만 화면에 보여줌 (Y: 삭제됨, N: 공백문자)
				if (!isDeleted.equals("삭제됨")) {
					strBuffer.append("<I ");
					strBuffer.appendRepl(" NoColor=\"2\" CanDelete=\"0\"");
					strBuffer.appendRepl(" PartType=\"" + part.get("type") + "\"");
					strBuffer.appendRepl(" PartNumber=\"" + part.get("number") + "\"" + " PartNumberOnClick=\"" + viewUrl + "\"" + " PartNumberHtmlPrefix=\"<font color='"
							+ PropertiesUtil.getSearchGridLinkColor() + "'>\" PartNumberHtmlPostfix=\"</font>\"");
					strBuffer.appendRepl(" PartName=\"").appendContent(part.get("name")).appendRepl("\"");
					strBuffer.appendRepl(" PartVersion=\"" + part.get("version") + "\"");
					strBuffer.appendRepl(" State=\"" + part.get("stateKr") + "\"");

					strBuffer.appendRepl(" Oid=\"" + part.get("oid") + "\"");
					strBuffer.appendRepl(" DieNo=\"" + die_no + "\"");
					strBuffer.appendRepl(" DieName=\"" + dieName + "\"");
					strBuffer.appendRepl(" DieCnt=\"" + dieCnt + "\"");
					strBuffer.appendRepl(" OidMaster=\"" + part.get("oidMaster") + "\"");
					strBuffer.appendRepl(" PartIteration=\"" + part.get("iteration") + "\"");
					strBuffer.append("/>");
				}
			}

			req.setAttribute("searchCondition", paramMap); // 검색 화면에서 받은 검색조건
			req.setAttribute("tgData", strBuffer.toString()); // 검색 결과 데이터

			// 검색화면으로 이동
			gotoResult(req, res, "/jsp/bom/SearchPartPopup.jsp");

			// 검색 쿼리 실행 후 부품번호 임시테이블 데이터 삭제
			// if (StringUtil.checkString(partNoTableKey)) {
			// dao.clearPartNoTable(partNoTableKey);
			// }
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			// 커넥션 종료
			PlmDBUtil.close(conn);
		}
	}

	private void openSearchPopup(HttpServletRequest req, HttpServletResponse res) {
		try {
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			KETParamMapUtil map = KETParamMapUtil.getMap(uploader);
			req.setAttribute("searchCondition", map); // 검색 화면에서 받은 검색조건
			gotoResult(req, res, "/jsp/bom/SearchPartPopup.jsp");
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

	// 부품 검색결과 Excel 출력
	private void excel(HttpServletRequest req, HttpServletResponse res) {

		Connection conn = null;
		Hashtable rsHash = new Hashtable();
		ArrayList<Hashtable<String, String>> partList = new ArrayList<Hashtable<String, String>>();

		try {

			// Form 데이터 받기
			FormUploader uploader = FormUploader.newFormUploader(req, res, getServletContext());
			Hashtable hash = uploader.getFormParameters();

			// 엑셀여부 셋팅
			hash.put("isExcel", "Y");
			hash.put("sort", StringUtil.checkReplaceStr((String) hash.get("sort"), "1 DESC"));

			Kogger.debug(getClass(), "@@@@@@@@@ hash : " + hash);

			// 커넥션 생성
			conn = PlmDBUtil.getConnection();
			PartDao dao = new PartDao(conn);

			// 목록 결과
			// [Start] 결과 내 재검색
			/*
			 * partList = dao.searchPartList(hash); // 기존 검색 주석처리
			 */
			KETParamMapUtil paramMap = KETParamMapUtil.getMap(hash);
			boolean searchInResult = ("searchInSearch".equals(paramMap.getString("searchInSearch")));
			List<Map> conditionList = KETParamMapUtil.getConditionListForSearchInResult("SearchPart", searchInResult, req);
			conditionList.add(paramMap);
			partList = dao.searchPartListSIR(conditionList);
			// [End] 결과 내 재검색

			// 검색조건 셋팅
			req.setAttribute("condition", hash);
			// 목록 결과 셋팅
			req.setAttribute("partList", partList);

			// 화면으로 이동
			gotoResult(req, res, "/jsp/bom/SearchPartExcel.jsp");

		} catch (ServletException e) {
			Kogger.error(getClass(), e);
		} catch (IOException e) {
			Kogger.error(getClass(), e);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			// 커넥션 종료
			PlmDBUtil.close(conn);
		}
	}

	// BOM MigrationTest
	private void migration(HttpServletRequest req, HttpServletResponse res) {

		// 다국어 처리
		KETMessageService messageService = KETMessageService.getMessageService(req);
		// String type = req.getContentType();
		String strType = "";
		String strNumber = "";

		String currentDate = DateUtil.getCurrentDateString(new SimpleDateFormat("yyyy-MM-dd"));
		String currentTime = DateUtil.getCurrentDateString(new SimpleDateFormat("HH:mm:ss"));
		String strFileName = "BomMigrationLog_" + currentDate + "_" + currentTime.replace(":", "") + ".log";

		FileUploader uploader = null;
		/*
		 * if ((type.toLowerCase().startsWith("multipart/form-data"))) { type = "excel"; uploader = FileUploader.newFileUploader(null, req, res); strType =
		 * uploader.getFormParameter("partType"); strNumber = uploader.getFormParameter("number"); }
		 */
		uploader = FileUploader.newFileUploader(null, req, res);
		strType = uploader.getFormParameter("partType");
		strNumber = uploader.getFormParameter("number");

		String excelName = "";
		if (uploader.getFiles().size() > 0) {
			Vector files = uploader.getFiles();
			WBFile file_;
			file_ = (WBFile) files.elementAt(0);
			File excel = file_.getFile();
			excelName = excel.getName();
		}

		Kogger.debug(getClass(), "================ BOM Migration TEST ==================");
		Kogger.debug(getClass(), "@@@@@@@@@ strType : " + strType);
		Kogger.debug(getClass(), "@@@@@@@@@ strNumber : " + strNumber);
		Kogger.debug(getClass(), "=================================================");

		boolean isSuccess = false;
		JSONObject jsonObject = new JSONObject();
		Map<String, Object> parameter = new HashMap<String, Object>();
		List<Map<String, Object>> returnObj = new ArrayList<Map<String, Object>>();
		String msg = "";
		ErpBomMigInfoInterface em = new ErpBomMigInfoInterface();
		ArrayList<Hashtable<String, String>> list;
		Object[] result = new Object[2];
		String checkparmStrType = "";
		String langCode = "";

		try {
			// msg = em.MigBomValidation(uploader,strNumber);
			if (PartUtil.isProductType(strType)) {// 제품 BOM
				checkparmStrType = "1";
				if (uploader.getFiles().size() > 0) {
					result = em.getMigResultFromExcel(checkparmStrType, strNumber, "", "", strFileName, uploader);
					strNumber = excelName;

				} else {
					if (!StringUtils.isEmpty(strNumber.trim())) {
						result = em.getMigResult2(checkparmStrType, strNumber, "", "", strFileName);
					}
				}

			} else {// 금형 BOM
				checkparmStrType = "2";
				if (uploader.getFiles().size() > 0) {
					result = em.getMigResultFromExcel(checkparmStrType, strNumber, "", "", strFileName, uploader);
					strNumber = excelName;
				} else {
					if (!StringUtils.isEmpty(strNumber.trim())) {
						result = em.getMigResult2(checkparmStrType, strNumber, "", "", strFileName);
					}
				}
				// isSuccess = em.getMigResult("2", strNumber, "", "", strFileName); // 해당 Die No migration
				// em.getMigResult("2", "", "2009-01-01", "2010-01-01"); // 날짜별 부분 migration
				// em.getMigResult("2", "", "", ""); // 전체 migration
			}

			msg = (String) result[0];
			list = (ArrayList<Hashtable<String, String>>) result[1];

			if (StringUtils.isEmpty(msg)) {
				if (list != null && list.size() > 0) {
					isSuccess = em.setKetPartUsageLink(list, Integer.parseInt(checkparmStrType));
				} else {
					isSuccess = false;
				}
			}

			if (isSuccess) {
				if ("1".equals(checkparmStrType)) {
					langCode = "00320";
				} else {
					langCode = "00100";
				}
				msg = messageService.getString("e3ps.bom.bom_editor_message", langCode, strNumber);/* 제품or 금형 BOM [{0}] Migration 성공! */
			} else {
				if ("1".equals(checkparmStrType)) {
					langCode = "00319";
				} else {
					langCode = "00101";
				}

				if (StringUtils.isEmpty(msg)) {
					msg = messageService.getString("e3ps.bom.bom_editor_message", langCode, strNumber);/* 제품or 금형 BOM [{0}] Migration 실패! */
				} else {
					msg += messageService.getString("e3ps.bom.bom_editor_message", langCode, strNumber);/* 제품or 금형 BOM [{0}] Migration 실패! */
				}
			}

			parameter.put("msg", msg);
			returnObj.add(parameter);

			try {
				jsonObject.put("returnObj", returnObj);
			} catch (Exception e) {
				Kogger.error(getClass(), e);
			}
			res.setContentType("application/x-json; charset=UTF-8");
			res.getWriter().print(jsonObject);

		} catch (IOException e) {
			alertNgo(res, messageService.getString("e3ps.bom.bom_editor_message", "00047")/* Migration 실패! */, "/plm/jsp/bom/MigrationTest.jsp");
			Kogger.error(getClass(), e);
		}
	}

	// 자재 MigrationTest
	private void migrationPart(HttpServletRequest req, HttpServletResponse res) {

		String strReturn = "";
		String[] strArry = { "", "", "", "", "", "", "", "", "", "", "", "", "", "" };

		Vector vec = new Vector();
		Hashtable<String, String> hashTestPart = new Hashtable<String, String>();
		Hashtable<String, String> hashTestPartRtn = null;
		Hashtable<String, String> hash = null;

		StringTokenizer token = null;
		String strToekn = "";
		String strDel = "";
		String strBomFlag = "";
		String strProdCode = "";
		String strProdName = "";
		String strIsYazaki = "";
		String strYazakiNo = "";
		String strPartWeight = "";
		String strWeightUnit = "";
		String strPartGroupDesc = "";

		try {
			hash = WebUtil.getHttpParams(req);

			vec = ItemMasterInterface.getItemMasterInfo((String) hash.get("number"));

			Kogger.debug(getClass(), "@@@@@@@@@@  SAP Part InterfaceTest : vec size : " + vec.size());

			for (int i = 0; i < vec.size(); i++) {

				strReturn = (String) vec.elementAt(i);

				int index = 0;
				token = new StringTokenizer(strReturn, "||");
				while (token.hasMoreElements()) {
					strToekn = token.nextToken();
					strArry[index] = strToekn;
					if (index < 14)
						index++;
				}

				hashTestPart.put("number", strArry[0]);
				hashTestPart.put("name", strArry[1]);
				hashTestPart.put("unit", strArry[2]);
				hashTestPart.put("partType", strArry[3]);
				if ("O".equals(strArry[4])) {
					hashTestPart.put("partGroup", "");
				} else {
					hashTestPart.put("partGroup", strArry[4]);
				}
				hashTestPart.put("isDeleted", strArry[5]);
				hashTestPart.put("bomFlag", strArry[6]);
				hashTestPart.put("prodCode", strArry[7]);
				hashTestPart.put("prodName", strArry[8]);
				// hashTestPart.put("isNew", strArry[9]);
				hashTestPart.put("yazaki", strArry[9]);
				hashTestPart.put("yazakiNo", strArry[10]);
				hashTestPart.put("partWeight", strArry[11]);
				hashTestPart.put("weightUnit", strArry[12]);
				hashTestPart.put("partGroupDesc", strArry[13]);

				Kogger.debug(getClass(), "@@@@@@@@@@ hashTestPart : " + hashTestPart);
				// 해당 부품 등록
				try {
					hashTestPartRtn = KETPartHelper.service.modify(hashTestPart);
					Kogger.debug(getClass(), "@@@@@@@@@ hashTestPartRtn : " + hashTestPartRtn);
				} catch (Exception e) {
					alertNgo(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/* 저장 실패 */+ "!", "/plm/jsp/bom/sapInterfaceTest.jsp");

					// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기
					strDel = hashTestPart.get("isDeleted");
					strBomFlag = hashTestPart.get("bomFlag");
					strProdCode = hashTestPart.get("prodCode");
					strProdName = hashTestPart.get("prodName");
					strIsYazaki = hashTestPart.get("yazaki");
					strYazakiNo = hashTestPart.get("yazakiNo");
					strPartWeight = hashTestPart.get("partWeight");
					strWeightUnit = hashTestPart.get("weightUnit");
					strPartGroupDesc = hashTestPart.get("partGroupDesc");

					if (strDel != null && strDel.equals("O"))
						hashTestPart.put("isDeleted", "");
					if (strBomFlag != null && strBomFlag.equals("O"))
						hashTestPart.put("bomFlag", "");
					if (strProdCode != null && strProdCode.equals("O"))
						hashTestPart.put("prodCode", "");
					if (strProdName != null && strProdName.equals("O"))
						hashTestPart.put("prodName", "");
					if (strIsYazaki != null && strIsYazaki.equals("O"))
						hashTestPart.put("yazaki", "");
					if (strYazakiNo != null && strYazakiNo.equals("O"))
						hashTestPart.put("yazakiNo", "");
					if (strPartWeight != null && strPartWeight.equals("O"))
						hashTestPart.put("partWeight", "");
					if (strWeightUnit != null && strWeightUnit.equals("O"))
						hashTestPart.put("weightUnit", "");
					if (strPartGroupDesc != null && strPartGroupDesc.equals("O"))
						hashTestPart.put("partGroupDesc", "");

					ItemMasterInterface.setItemMasterResultInfo(hashTestPart, false);
				}

				// 인터페이스 결과 Update 하기 전에 빈문자열 처리 다시 변경해주기
				strDel = hashTestPart.get("isDeleted");
				strBomFlag = hashTestPart.get("bomFlag");
				strProdCode = hashTestPart.get("prodCode");
				strProdName = hashTestPart.get("prodName");
				strIsYazaki = hashTestPart.get("yazaki");
				strYazakiNo = hashTestPart.get("yazakiNo");
				strPartWeight = hashTestPart.get("partWeight");
				strWeightUnit = hashTestPart.get("weightUnit");
				strPartGroupDesc = hashTestPart.get("partGroupDesc");

				if (strDel != null && strDel.equals("O"))
					hashTestPart.put("isDeleted", "");
				if (strBomFlag != null && strBomFlag.equals("O"))
					hashTestPart.put("bomFlag", "");
				if (strProdCode != null && strProdCode.equals("O"))
					hashTestPart.put("prodCode", "");
				if (strProdName != null && strProdName.equals("O"))
					hashTestPart.put("prodName", "");
				if (strIsYazaki != null && strIsYazaki.equals("O"))
					hashTestPart.put("yazaki", "");
				if (strYazakiNo != null && strYazakiNo.equals("O"))
					hashTestPart.put("yazakiNo", "");
				if (strPartWeight != null && strPartWeight.equals("O"))
					hashTestPart.put("partWeight", "");
				if (strWeightUnit != null && strWeightUnit.equals("O"))
					hashTestPart.put("weightUnit", "");
				if (strPartGroupDesc != null && strPartGroupDesc.equals("O"))
					hashTestPart.put("partGroupDesc", "");

				// I/F 결과 테이블 업데이트
				String partOid = (String) hashTestPartRtn.get("oid");
				Kogger.debug(getClass(), "@@@@@@@ partOid : " + partOid);
				if (partOid != null && !partOid.equals("") && !partOid.equals("null")) {
					alertNgo(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00307")/* 저장 성공 */+ "!", "/plm/jsp/bom/sapInterfaceTest.jsp");
					Kogger.debug(getClass(), "@@@@@@@@@@ 저장 성공!");
					ItemMasterInterface.setItemMasterResultInfo(hashTestPart, true);
				} else {
					alertNgo(res, KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00308")/* 저장 실패 */+ "!", "/plm/jsp/bom/sapInterfaceTest.jsp");
					Kogger.debug(getClass(), "@@@@@@@@@@ 저장 실패!");
					ItemMasterInterface.setItemMasterResultInfo(hashTestPart, false);
				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
}
