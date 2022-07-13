<%@page import="java.util.Enumeration"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.servlet.CostComServlet"%>
<%@ page import="e3ps.cost.dao.CostComDao"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="e3ps.cost.util.DBUtil"%>
<%@ page import="java.sql.Connection"%>
<%
    String pk_cr       = StringUtil.checkNull(request.getParameter("pk_cr"));
    String k       = StringUtil.checkNull(request.getParameter("k"));
    String a_usage = StringUtil.checkNull(request.getParameter("a_usage"));
%>

<html>
<head>
<title>원가요청서</title>
<link rel='stylesheet' href="/plm/jsp/cost/css/wisegrid.css" type='text/css'>
<script language="JavaScript" src="/plm/jsp/cost/js/WiseGridUni_TAG.js"></script>
<script language="javascript" src="/plm/jsp/cost/js/WiseGrid_Property.js"></script>
<!--
    WiseGrid 오브젝트가 생성되고 초기화된 후 발생하는
    JavaScript Event인 Initialize()를 받아 그리드의 헤더를 셋팅한다.
-->
<script language=javascript for="WiseGrid9" event="Initialize()">
//alert("222");
init9();
</script>

<!--    서버와의 통신이 정상적으로 완료되면 발생한다.   -->
<script language=javascript for="WiseGrid9" event="EndQuery()">
    GridEndQuery9();
</script>

<!--     WiseGrid의 셀의 내용이 변경되었을때 발생한다. -->
<script language=javascript for="WiseGrid9" event="ChangeCell(strColumnKey,nRow,nOldValue,nNewValue)">
    GridChangeCell9(strColumnKey, nRow);
</script>

<!--     WiseGrid의 t_combo타입의 컬럼내용이 변경되었을때 발생합니다  -->
<script language=javascript for="WiseGrid9" event="ChangeCombo(strColumnKey,nRow,nOldIndex,nNewIndex)">
    GridChangeCombo9(strColumnKey, nRow);
</script>

<script language="JavaScript">
 var GridObj9;

 function init9() {
	 
	      GridObj9 = document.WiseGrid9;
	        setProperty(GridObj9);
	        GridObj9.bHDMoving = false;
	        GridObj9.bHDSwapping = false;
	        GridObj9.strHDClickAction = 'select'; 
	        GridObj9.bStatusbarVisible = false;
	        GridObj9.strRowSizing = "autofree";
	        //ROW 전체선택여부
	        //GridObj9.bRowSelectorVisible=true;
	         //엑셀 붙여넣기(그리드기반으로 설정함)
	          GridObj9.strBlockPaste='gridareabase';
	        //GridObj9.strActiveRowBgColor="254|252|219";
	        GridObj9.bFormulaModeAutoRecalc = true;
	        
	        setHeader9();
	        doQuery9();
	}

 function setHeader9() {
	 
	    //그리드에 컬럼을 등록한다.
	    GridObj9.AddHeader("cost_pack_id",         "PK",                         "t_text",         20,         40,    true); 
        GridObj9.AddHeader("SELECTED",             "",                         "t_checkbox",         2,         20,    true);
	    GridObj9.AddHeader("CRUD",                 "",                    "t_text",             8,         40,    true);
	    GridObj9.AddHeader("pack_item",            "포장타입",             "t_combo",             100,            80,        true);
	    GridObj9.AddHeader("pack_unitcost",        "포장단가",             "t_number",     10.4,        80,    true);
	    GridObj9.AddHeader("pack_quantity",        "포장수량",             "t_number",     10.4,        80,    true);
	    GridObj9.AddHeader("in_pack",              "내장\n(EA/봉지)",      "t_number",     10.4,        80,    true);
	    GridObj9.AddHeader("out_pack",             "외장\n(EA/Box)",      "t_number",     10.4,        80,    true);
	    GridObj9.AddHeader("pack_recovery",        "회수여부",             "t_combo",       100,            80,        true);
	    GridObj9.AddHeader("pack_inputcost_using", "총투입비용\n산출유무",  "t_combo",       100,            80,        true);
        GridObj9.AddHeader("pack_amount",          "총판매수량",           "t_number",     10.4,        80,    true);
	    GridObj9.AddHeader("pack_pro_quantity",    "제작수량",             "t_number",     10.4,        80,    true);
	    GridObj9.AddHeader("pack_inputcost",       "총투입비용",            "t_number",     10.4,        80,    false);
	    GridObj9.AddHeader("pack_unitcost_total",  "총포장비",             "t_number",     10.4,        80,    false);

	    
	    //그룹생성
	    GridObj9.AddGroup("CHK_TOTAL",      "선택");

	/****************************************************************
	    그룹에 헤더 추가
	*****************************************************************/
	    //select
	      GridObj9.AppendHeader("CHK_TOTAL", "SELECTED");
	      GridObj9.AppendHeader("CHK_TOTAL", "CRUD");
	      
	      
	      //마우스오른쪽비활성-내용부분
	      //GridObj1.bUseDefaultContextMenu=false;
	    //GridObj1.bUserContextMenu=true;

	    //AddHeader를 완료한 후 헤더를 그리드에 바인딩한다
	    GridObj9.BoundHeader();
	  //  GridObj9.SetFormulaMode();
	  //저장모드를 사용해 서버사이드와 통신한다.
	   GridObj9.SetCRUDMode("CRUD", "NEW", "EDIT", "DEL");
 
	/****************************************************************
	    콤보리스트 생성
	*****************************************************************/
	    //포장유형
	    GridObj9.AddComboListValue("pack_item", ""   ,  "empty" );
	    GridObj9.AddComboListValue("pack_item", "비닐"         ,  "비닐"    );
	    GridObj9.AddComboListValue("pack_item", "Reel"       ,  "Reel"  );
	    GridObj9.AddComboListValue("pack_item", "회수용"    ,  "회수용");
	    GridObj9.AddComboListValue("pack_item", "Tray"       ,  "Tray"  );
	    GridObj9.AddComboListValue("pack_item", "골판지"    ,  "골판지");
	    
	    //회수유무
	    GridObj9.AddComboListValue("pack_recovery", "無", "N");
	    GridObj9.AddComboListValue("pack_recovery", "有", "Y");
	    
	    //총 투입비용 산출유무
        GridObj9.AddComboListValue("pack_inputcost_using", "無", "N");
        GridObj9.AddComboListValue("pack_inputcost_using", "有", "Y");
        
        //컬럼 숨기기
          GridObj9.SetColHide("cost_pack_id", true);
        //GridObj9.SetColHide("CRUD", true);
        
        //전체선택
        GridObj9.SetColHDCheckBoxVisible("SELECTED", true);
    
      //t_number 타입의 컬럼 포맷타입 지정
        
        GridObj9.SetNumberFormat("pack_unitcost",   "0,###.###");
        GridObj9.SetNumberFormat("pack_amount",   "0,###.###");
        GridObj9.SetNumberFormat("pack_pro_quantity",   "0,###.###");
        GridObj9.SetNumberFormat("pack_inputcost",   "0,###.###");
        GridObj9.SetNumberFormat("pack_unitcost_total",   "0,###.###");
        
	}
 /********************************************************************
 조회 
********************************************************************/
function doQuery9() {

    var servlet_url = "/plm/servlet/e3ps/costComServlet";
    //WiseGrid가 서버에 전송할 Param을 셋팅한다.
    GridObj9.SetParam("mode", "cost_packSearch");
    GridObj9.SetParam("request_id", document.part_1.request_id.value);

    //WiseGrid가 서버와 통신시에 데이터를 전달한다.
    GridObj9.DoQuery(servlet_url);
}
/**********************************************
서버와의 통신이 정상적으로 완료되면 발생한다.
**********************************************/
 function GridEndQuery9() {
    //서버에서 mode로 셋팅한 파라미터를 가져온다.
    var mode = GridObj9.GetParam("mode");
   // alert("!!"+mode);
    if(mode == "cost_packSearch") {
       
          //  alert("111111");
        for(a = 0; a < GridObj9.GetRowCount(); a++){   
	        //회수여부 활성비활성구분
	        if(GridObj9.GetCellHiddenValue("pack_recovery", a) == "N")
	        {
	              GridObj9.SetCellActivation('pack_amount', a, 'disable');
	              GridObj9.SetCellBgColor('pack_amount', a, '226|226|226');
	         }else{
	        	  GridObj9.SetCellActivation('pack_amount', a, 'edit');
	        	  GridObj9.SetCellBgColor('pack_amount', a, '255|255|255');
	          }
	       //총투입비용 산출여부 활성비활성구분
	        if(GridObj9.GetCellHiddenValue("pack_inputcost_using", a) == "N")
	        {
	        	  GridObj9.SetCellActivation('pack_pro_quantity', a, 'disable');
	        	  GridObj9.SetCellBgColor('pack_pro_quantity', a, '226|226|226');
	        }else{
	        	  GridObj9.SetCellActivation('pack_pro_quantity', a, 'edit');
	        	  GridObj9.SetCellBgColor('pack_pro_quantity', a, '255|255|255');
	          }
        }
        
     } else  if(mode == "cost_pack_save") {
    	 alert("저장되었습니다.");
    	 
    	 var op_pack_item = GridObj9.GetCellHiddenValue("pack_item", 0);
    	 var op_in_pack   = GridObj9.GetCellValue("in_pack", 0);
    	 var op_out_pack  = GridObj9.GetCellValue("out_pack", 0);
    	 
    	// alert(op_pack_item);
    	 
    	 opener.document.WiseGrid3.SetCellValue("pack_type",<%=k%>, op_pack_item);
    	 opener.document.WiseGrid3.SetCellValue("in_pack",<%=k%>, op_in_pack );
    	 opener.document.WiseGrid3.SetCellValue("out_pack",<%=k%>, op_out_pack );
    	 opener.document.WiseGrid3.SetCellValue("pack_cost",<%=k%>, document.part_1.pack_cost.value);
    	 
    	 self.close();
     } else  {
            var error_msg = GridObj9.GetMessage(); // 서버에서 전송한 상태코드값이 false라면 에러메세지를 가져온다.
            alert(error_msg);
     }
        //doQuery9();
      //  var currentRow = 1;
      //  for(var currentRowIdx=0; currentRowIdx<GridObj9.GetRowCount(); currentRowIdx++) {
        	//총포장비
      //      GridObj9.SetCellHiddenValue ("pack_unitcost_total",currentRowIdx, '=(C'+currentRow+'*D'+currentRow+')/F'+currentRow+'');
     //       currentRow = currentRow + 1
     //   }
      //  GridObj9.CalculateFormulas();
    }
    
 /**********************************************
Add row
**********************************************/
function lineInsert(strColumnKey, nRow) {

	 var pRow = GridObj9.getRowCount();
	 	 
        GridObj9.AddRow();
        GridObj9.SetCellValue('pack_amount', pRow, '0');
        GridObj9.SetCellValue('pack_pro_quantity', pRow, '0');
        
        GridObj9.SetCellActivation('pack_amount', pRow, 'disable');
        GridObj9.SetCellActivation('pack_pro_quantity', pRow, 'disable');
        
        GridObj9.SetCellBgColor('pack_amount', pRow, '226|226|226');
        GridObj9.SetCellBgColor('pack_pro_quantity', pRow, '226|226|226');
        
     //   GridObj9.SetCellHiddenValue ("pack_unitcost_total",pRow, '=(C'+sRow+'*D'+sRow+')/F'+sRow+'');
       // GridObj9.CalculateFormulas();

}
/**********************************************
Del row
**********************************************/
function lineDelete(strColumnKey, nRow) {
    for(i = 0; i < GridObj9.GetRowCount(); i++)
    {
         if(GridObj9.GetCellValue("SELECTED", i) == "1"){
           	  // alert(i);
         	    GridObj9.DeleteRow(i)
       	    }
                    //return true;    
     }
}

/********************************************************************
저장1
********************************************************************/
function doSave9() {

   var servlet_url = "/plm/servlet/e3ps/costComServlet";

   //WiseGrid가 서버에 전송할 mode를 셋팅한다.
   GridObj9.SetParam("mode", "cost_pack_save");
   GridObj9.SetParam("request_id", document.part_1.request_id.value);
   GridObj9.SetParam("pack_cost", document.part_1.pack_cost.value);
   GridObj9.SetParam("table_row", GridObj9.getRowCount());

   //WiseGrid가 서버와 통신시에 데이터를 전달한
   GridObj9.DoQuery(servlet_url, "WISEGRIDDATA_ALL");
}

/*******************************************************************************************************
cell값 변경
********************************************************************************************************/
function GridChangeCell9(strColumnKey, nRow) {
	
	var p_unit     = GridObj9.GetCellValue("pack_unitcost", nRow);
	var p_quan     = GridObj9.GetCellValue("pack_quantity", nRow);
	var p_out      = GridObj9.GetCellValue("out_pack", nRow);
	var p_amount   = GridObj9.GetCellValue("pack_amount", nRow);
	var p_pro_quan = GridObj9.GetCellValue("pack_pro_quantity", nRow);
	
	//alert(p_unit+"\n"+p_quan+"\n"+p_out);
        var p_unit_total = (p_unit*p_quan)/p_out;
       // alert("BB");    
	if(GridObj9.GetCellHiddenValue("pack_recovery", nRow) == "N")//회수여부
	  {
	    GridObj9.SetCellValue ("pack_inputcost",nRow, p_unit*p_quan);
	    GridObj9.SetCellValue ("pack_unitcost_total",nRow, p_unit_total);	 
        
	  }else{
		  if(GridObj9.GetCellHiddenValue("pack_inputcost_using", nRow) == "N") // 총투입비용 산출유무
	        {
              GridObj9.SetCellValue ("pack_inputcost",nRow, p_unit*p_quan);
              GridObj9.SetCellValue ("pack_unitcost_total",nRow, p_unit_total);    

	        }else{
	        //	alert("AA");
	              var p_inputcost_total = (p_unit* p_pro_quan)/(p_amount*<%=a_usage%>);

	               GridObj9.SetCellValue ("pack_inputcost",nRow, p_unit* p_pro_quan);
	               GridObj9.SetCellValue ("pack_unitcost_total",nRow, p_inputcost_total);   
	        }
	  }
	
	var pack_cost_total = 0

	for(i = 0; i < GridObj9.GetRowCount(); i++)
     {
          var cost_total = parseFloat(GridObj9.GetCellValue("pack_unitcost_total", i),10);
          pack_cost_total = pack_cost_total + cost_total;
      }
	 
	 document.part_1.pack_cost.value = pack_cost_total;
	 
	//  var pRow = nRow + 1;   
//   GridObj9.SetCellHiddenValue ("pack_unitcost_total",nRow, '=(C'+pRow+'*D'+pRow+')/F'+pRow+'');
    
}


/*******************************************************************************************************
t_combo값 변경
********************************************************************************************************/
function GridChangeCombo9(strColumnKey, nRow) {    
	//alert("AA"+strColumnKey);
	
    if(strColumnKey == "pack_recovery"){

        if(GridObj9.GetCellHiddenValue("pack_recovery", nRow) == "N")
        {
              GridObj9.SetCellActivation('pack_amount', nRow, 'disable');
              GridObj9.SetCellValue('pack_amount', nRow, '0');              
              GridObj9.SetCellBgColor('pack_amount', nRow, '226|226|226');
        }
        else
        {
              GridObj9.SetCellActivation('pack_amount', nRow, 'edit');
              
              GridObj9.SetCellBgColor('pack_amount', nRow, '255|255|255');
        }
    }else if(strColumnKey == "pack_inputcost_using"){

        if(GridObj9.GetCellHiddenValue("pack_inputcost_using", nRow) == "N")
        {
              GridObj9.SetCellActivation('pack_pro_quantity', nRow, 'disable');
              GridObj9.SetCellValue('pack_pro_quantity', nRow, '0');
              
              GridObj9.SetCellBgColor('pack_pro_quantity', nRow, '226|226|226');
        }
        else
        {
              GridObj9.SetCellActivation('pack_pro_quantity', nRow, 'edit');
              
              GridObj9.SetCellBgColor('pack_pro_quantity', nRow, '255|255|255');
        }
    }
    
 //  var pRow = nRow + 1;   
//   GridObj9.SetCellHiddenValue ("pack_unitcost_total",nRow, '=(C'+pRow+'*D'+pRow+')/F'+pRow+'');
    
}

</script>
</head>
<body onselectstart="return false">
<form name="part_1">
<input name="request_id" type="hidden" size="2" value=<%=pk_cr%>>
<input name="pack_cost" type="hidden" size="2">
<table id="tb_product" width="942" height="250" border="0" cellpadding="0" cellspacing="0" >
            <tr>
                <td height="20" align="right" valign="bottom">
                    <img src="/plm/jsp/cost/acc_img/btn_rowadd.jpg"  border="0" onClick="lineInsert();" style="cursor:pointer;"/>
                    <img src="/plm/jsp/cost/acc_img/btn_Delete.gif"  border="0" onClick="lineDelete();" style="cursor:pointer;"/>
                    <img src="/plm/jsp/cost/acc_img/btn_Save.gif" width="51" height="17" border="0" onClick="doSave9();" style="cursor:pointer;"/>
                </td>
            </tr>
            <tr>
                <td bgcolor="#00455d" height="3" colspan="2"></td>
            </tr>
            <tr>
              <td align="left" valign="top" colspan="2"><script>initWiseGrid("WiseGrid9", "942", "100%");</script></td>
            </tr>
</table>
</form>
</body>
</html>