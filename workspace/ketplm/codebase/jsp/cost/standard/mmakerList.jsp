<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="java.net.URLDecoder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel='stylesheet' href='/plm/jsp/cost/css/wisegrid.css' type='text/css'>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGridTag.js"></script>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGrid_Property.js"></script>
<script language="JavaScript" src="/plm/jsp/cost/js/gridView/standardGrid.js" ></script>
<script language="JavaScript" for="WiseGridM_maker" event="Initialize()">
initM_maker();
</script>
<!--  	서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WiseGridM_maker" event="EndQuery()">
	GridEndQuery();
</script>
<script type="text/javascript">

/*조회*/
function doQuery(){
	var servlet_url = "/plm/servlet/e3ps/CostStandardServlet";
	//WiseGrid가 서버에 전송할 Param을 셋팅한다.
	GridObjM_maker.SetParam("mode", "search_Mmaker");

	//WiseGrid가 서버와 통신시에 데이터를 전달한다.
	GridObjM_maker.DoQuery(servlet_url);
}

/*신규등록*/
function doLineInsert() {
	var GridObjM_maker = document.WiseGridM_maker;

	//그리드의 마지막 열에 빈 로우를 추가한다.
	GridObjM_maker.AddRow();
}

/* 삭제 */
function doDelete(strColumnKey) {
	var GridObjM_maker = document.WiseGridM_maker;
	// Active된 로우의 인덱스 위치의 행을 삭제한다.
	var cnt = GridObjM_maker.GetRowCount()
		chkCount = 0;

	if(!chkSelected()) {
		alert("선택된 건이 없습니다.");
		return;
	}

	for(var i=0; i<cnt; i++) {

		if(GridObjM_maker.getCellValue("CRUD", i) != "NEW") {
			if(GridObjM_maker.GetCellValue("SELECTED", i) == "1")
				GridObjM_maker.DeleteRow(i);
		} else {
			if(GridObjM_maker.GetCellValue("SELECTED", i) == "1")
				GridObjM_maker.DeleteRow(i);
			i = i-1;
			cnt = cnt-1;
		}
	}
	//GridObjM_maker.DeleteRow(GridObjM_maker.GetActiveRowIndex());
}
/*로우 체크 확인*/
function chkSelected() {
	chkCount = 0;

	for(i = 0; i < GridObjM_maker.GetRowCount(); i++) { //그리드 데이터의 로우수를 반환한다.

		if(GridObjM_maker.GetCellValue("SELECTED", i) == "1") //지정한 셀의 값을 가져온다.
			chkCount = chkCount + 1;
	}

	if(chkCount == 0) {
		return false;
	}
	return true;
}
/* 저장 */
function doSave() {
	var GridObjM_maker = document.WiseGridM_maker;
	var servlet_url = "/plm/servlet/e3ps/CostStandardServlet";

	//WiseGrid가 서버에 전송할 mode를 셋팅한다.
	GridObjM_maker.SetParam("mode", "save_Mmaker");

	//WiseGrid가 서버와 통신시에 데이터를 전달한
	GridObjM_maker.DoQuery(servlet_url, "CRUD");
}

/* 저장모드에서 저장 플래그를 모두 삭제하고 초기 데이터로 롤백한다. */
function doSaveCance() {
	var GridObjM_maker = document.WiseGridM_maker;

	if(confirm("저장 플래그를 모두 초기화 합니다")){
		GridObjM_maker.CancelCRUD();
	}
}
/* 서버와의 통신이 정상적으로 완료되면 발생한다. */
function GridEndQuery() {
	var GridObjM_maker = document.WiseGridM_maker;

	//서버에서 mode로 셋팅한 파라미터를 가져온다.
	var mode = GridObjM_maker.GetParam("mode");

	if(mode == "search_Mmaker") {
		if(GridObjM_maker.GetStatus() == "true") 	{// 서버에서 전송한 상태코드를 가져온다.
		} else	{
			var error_msg = GridObjM_maker.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
			alert(error_msg);
		}
	}

	if(mode == "save_Mmaker") {
		if(GridObjM_maker.GetStatus() == "true") 	{// 서버에서 전송한 상태코드를 가져온다.
			//서버에서 saveData 셋팅한 파라미터를 가져온다.
				alert("변경된 내용이 저장되었습니다.");
		} else	{
			var error_msg = GridObjM_maker.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
			alert(error_msg);
		}
	}
}
</script>
</head>
<body>
<form name="M_makerList">
<table width="720" height="293" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td height="31">수지원재료DataBase</td>
  </tr>
  <tr>
    <td height="28" align="right"><table border=0 cellpadding="0" cellspacing="0" >
				<tr>
					<td><script language="javascript">btn("doQuery()","조회")</script></td>
					<td><script language="javascript">btn("doDelete()","삭제")</script></td>
					<td><script language="javascript">btn("doLineInsert()","행추가")</script></td>
					<td><script language="javascript">btn("doSave()","저장")</script></td>
					<td><script language="javascript">btn("doSaveCance()","저장취소")</script></td>
				</tr>
			</table></td>
  </tr>
  <tr>
    <td><script>initWiseGrid("WiseGridM_maker", "720", "600");</script></td>
  </tr>
</table>
</form>
</body>
</html>