<%@page import="e3ps.common.code.NumberCodeHelper"%>
<%@page import="e3ps.common.code.NumberCode"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<%

Map <String, Object> parameter = new HashMap<String, Object>();
parameter.put("locale", messageService.getLocale());
parameter.put("codeType", "SUBJECTTYPE");
List<Map<String, Object>> numCode = NumberCodeHelper.manager.getNumberCodeList(parameter);

%>
<script type="text/javascript"
    src="/plm/extcore/js/sales/project/salesProject.js"></script>
<script type="text/javascript">


<!--
    $(document).ready(function() {
        $(document).attr('title', "영업수주현황 등록");//'금형 Try 조건표 [MOLD] 등록'
        CalendarUtil.dateInputFormat('sopDate'); //sop
        CommonUtil.singleSelect('salesRank', 140); //중요도
        CommonUtil.singleSelect('developType', 140); //프로젝트 유형
        CommonUtil.multiSelect('competitorCompany', 140); //경쟁사
        CommonUtil.singleSelect('nation', 140); //국가
        CommonUtil.singleSelect('mainCategory', 250); //대분류제품군
        CommonUtil.singleSelect('workShopYN',140);// 워크샵 여부
        
        SuggestUtil.bind('CARTYPE', 'tempmodel', 'model');
        
    });
//-->
</script>
<!-- 이노디터 JS Include Start -->
<script type="text/javascript">
    //<![CDATA[
    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
    var g_arrSetEditorArea = new Array();
    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정

    //]]>
</script>
<script type="text/javascript"
    src="/plm/portal/innoditor_u/js/customize.js"></script>
<script type="text/javascript"
    src="/plm/portal/innoditor_u/js/customize_ui.js?ver=0.1"></script>
<script type="text/javascript">
    //<![CDATA[
    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
    // Skin 재정의
    //g_nSkinNumber = 0;
    // 크기, 높이 재정의
    g_nEditorWidth = 885;
    g_nEditorHeight = 340;
    //]]>
</script>
<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
<script>
    function fnUploadResult(url){
        alert(url);
    }
    //첨부파일 관련 스크립트 시작
    function insertFileLine() {
        var innerRow = fileTable.insertRow();
        innerRow.height = "27";
        var innerCell;

        var filePath = "filePath";
        var filehtml = "";

        for ( var k = 0; k < 2; k++) {
            innerCell = innerRow.insertCell();
            innerCell.height = "23";

            if (k == 0) {
                innerCell.className = "tdwhiteM";
                innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>"
                        + "<div style=\"display:none;\"><input name='fileSelect' type='checkbox' class='chkbox'></div>";
            } else if (k == 1) {
                innerCell.className = "tdwhiteL0";
                innerCell.innerHTML = "<input name='secondaryFiles' type='file' class='txt_fieldRO' style='width: 100%;'>";
            }
        }
    }
    
    
    function stepReset(){
        var idx = 0;
        
        $('#salesTargetTable tr').each(function(i) {
            if (this.rowIndex > 1){
                idx = i-1;
                this.cells[1].innerHTML = "<a onclick='javascript:openTaskWebEditor("+idx+"); return false;' href='#'>Step "+idx+"</a>";
            }
        });
    }
    
    function setWebEditor(objArr){
/*         for(var i=0; i<objArr.length;i++){
            alert(objArr[i]);
        } */
        
        var propelwebEditor = objArr[0];
        var propelwebEditorText = objArr[1];

        var problemwebEditor = objArr[2];
        var problemwebEditorText = objArr[3];
        
        var planwebEditor = objArr[4];
        var planwebEditorText = objArr[5];
        
        var curIdx = objArr[6];
        
        $('textarea[name=propelwebEditor]').each(function(i) {
            if(curIdx == i+1){
                $(this).val(propelwebEditor);
            }
        });
        
        $('textarea[name=propelwebEditorText]').each(function(i) {
            if(curIdx == i+1){
                $(this).val(propelwebEditorText);
            }
        });
        
        $('textarea[name=problemwebEditor]').each(function(i) {
            if(curIdx == i+1){
                $(this).val(problemwebEditor);
            }
        });
        
        $('textarea[name=problemwebEditorText]').each(function(i) {
            if(curIdx == i+1){
                $(this).val(problemwebEditorText);
            }
        });
        
        $('textarea[name=planwebEditor]').each(function(i) {
            if(curIdx == i+1){
                $(this).val(planwebEditor);
            }
        });
        
        $('textarea[name=planwebEditorText]').each(function(i) {
            if(curIdx == i+1){
                $(this).val(planwebEditorText);
            }
        });
        
    }
    
    function openTaskWebEditor(idx){
        //alert(idx);
        
        var goFlag = true;
        var planCheck;
        $('select[name=planCheck]').each(function(i) {
            //alert(i);
            if(idx == i+1){
                if($(this).val() == ''){
                    alert("진행상황(신호등)을 선택하시기 바랍니다.");
                    goFlag = false;
                }else{
                    planCheck = $(this).val();
                }
            }
        });
        
        if(goFlag){
            $('[name="idx"]').val(idx);
            $('[name="stepName"]').val("Step "+idx);
            
            $('[name=salesProjectForm]').attr('target', 'salesTaskCreateFormPopup');
            $('[name=salesProjectForm]').attr('action', '/plm/ext/sales/project/salesTaskCreateForm.do');
            
            //getOpenWindow2('/plm/ext/sales/project/salesTaskCreateForm.do?planCheck='+planCheck+'&stepName=Step '+idx+'&idx='+idx,'salesTaskCreateFormPopup',1300,500);
            
            window.open("", "salesTaskCreateFormPopup", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1300,height=500");
            
            $('[name=salesProjectForm]').submit();
            
            //showProcessing();
        }
    }
    
    function scrollControl(){
        //스크롤 맨아래로
        $('html, body').scrollTop( $(document).height() );
    }
    
    function createMemberTable(infoArr){
        // [0] - wtuser oid // [1] - people oid // [2] - dept oid
        // [3] - user id    // [4] - name       // [5] - dept name
        // [6] - duty       // [7] - duty code  // [8] - email
        var addContent = "";
        
        addContent += "<tr>";
        addContent += "<td class=tdwhiteM><a href=# onclick=javascript:$(this).parent().parent().remove(); return false;><b><img src=/plm/portal/images/b-minus.png></b></a><input type='hidden' name='membserOids' id='membserOids' value="+infoArr[0]+"></td>";
        addContent += "<td class=tdwhiteM>"+infoArr[4]+"</td>";
        addContent += "<td class=tdwhiteM>"+infoArr[5]+"</td>";
        addContent += "<td class=tdwhiteM>"+infoArr[6]+"</td>";
        addContent += "</tr>";
        
        $('#projectMemberTable > tbody:last').append(addContent);
        
    }
    
    function insertTargetMemberCb(arrObj){
    	var realObj = new Array();
        if ( typeof arrObj == "undefined" || arrObj == null ) {
            return;
        }
        
        var isDup = false;
        
        for(var j=0; j < arrObj.length; j++){
            $.each($('[name=membserOids]'), function(i, val){
                if(val.value == arrObj[j][0]) {
                    isDup = true;
                    return false;
                }
            });
            
            if(isDup == false){
                infoArr = arrObj[j];
                createMemberTable(infoArr);
            }
            isDup = false;
        }
    }
    
    function insertTargetMember() {
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=m&invokeMethod=insertTargetMemberCb";
        getOpenWindow2(url,'insertTargetMember', 800, 720);
    }
    
    var targetIdx = 0;
    function insertTargetLine() {
        //var idx = 0;
        
        //idx = $('#salesTargetTable tbody tr').length-1;
        targetIdx = targetIdx+1;
        addContent = "<tr>";
        addContent +="<td class=\"tdwhiteM\"><a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove(); stepReset(); return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a><input type='hidden' name='taskOids' id='taskOids' value='new'></td>"; 
        addContent +="<td class=\"tdwhiteM\"><a href=\"#\" onclick=\"javascript:insertTargetLine(); return false;\"></a></td>";
        //addContent +="<td class=\"tdwhiteM\"><input type=\"text\" name=\"subject\" id=\"subject\" class=\"txt_field\" style=\"width: 95%\" esse='true' esseLabel='Subject'></td>";
        
        addContent +="<td class=\"tdwhiteL\">";
        addContent +="<select id='subject' name='subject' esse='true' className='fm_jmp' style='width: 100%; border: none;'>";
        addContent += "<option value=''>선택</option>";
        <%for (int i = 0; i < numCode.size(); i++) {%>
        addContent += "<option value=<%=numCode.get(i).get("code") %>><%=numCode.get(i).get("value")%></option>";
        <%}%>
        addContent +="</select>";
        addContent +="</td>";
        
        addContent +="<td class=\"tdwhiteM\"><input type=\"text\" name=\"salsePlan\" id=\"salsePlan\" class=\"txt_field\" style=\"width: 95%\" esse='true' esseLabel='영업 추진 계획'></td>";
        addContent +="<td class=\"tdwhiteL\"><input type=\"text\" name=\"targetPeopleName\" class=\"txt_field\" style=\"width: 60%\">"
        addContent +="<input type=\"hidden\" name=\"targetPeopleOidAttr\" esse='true' esseLabel='담당자'><a href=\"javascript:;\">"
        addContent +=" <img id=\"img_TargetPeople\" src=\"/plm/portal/images/icon_user.gif\" border=\"0\" onclick=\"addUser(this); return false;\"></a> <a href=\"javascript:;\">"
        addContent +="<img id=\"img_TargetPeopleDel\" src=\"/plm/portal/images/icon_delete.gif\" border=\"0\" onclick=\"delUser(this); return false;\"></a></td>"
        
        addContent +="<td class=\"tdwhiteL\">";
        addContent +="<input type=\"text\" name=\"planDate\" class=\"txt_field\" esse='true' esseLabel='계획일자' style=\"width: 60%\">"
        addContent +="<img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\" onclick=\"javascript:CommonUtil.deleteDateValue('planDate');\" style=\"cursor:pointer; cursor: hand;\">";
        addContent +="</td>";
        
        addContent +="<td class=\"tdwhiteL\">";
        addContent +="<input type=\"text\" name=\"resultDate\" class=\"txt_field\" style=\"width: 60%\">"
        addContent +="<img src=\"/plm/portal/images/icon_delete.gif\" border=\"0\" onclick=\"javascript:CommonUtil.deleteDateValue('resultDate');\" style=\"cursor:pointer; cursor: hand;\">";
        addContent +="</td>";
        
        addContent +="<td class=\"tdwhiteM\">";
        addContent +="<input name='step' type='hidden' value='step"+targetIdx+"'/>";
        
        addContent +="<textarea name='propelwebEditor' rows='0' cols='0' style='display: none'></textarea>";
        addContent +="<textarea name='propelwebEditorText' rows='0' cols='0' style='display: none'></textarea>";
        
        addContent +="<textarea name='problemwebEditor' rows='0' cols='0' style='display: none'></textarea>";
        addContent +="<textarea name='problemwebEditorText' rows='0' cols='0' style='display: none'></textarea>";
        
        addContent +="<textarea name='planwebEditor' rows='0' cols='0' style='display: none'></textarea>";
        addContent +="<textarea name='planwebEditorText' rows='0' cols='0' style='display: none'></textarea>";
        
        addContent +="<select id=\"planCheck\" name=\"planCheck\" esse='true' className=\"fm_jmp\" style=\"width: 95%; border: none;\" onchange=\"sales.fn_changeCombo2(this); return false;\">";
        addContent +="<option value=\"none\">선택</option>";
        addContent +="<option value=\"green\">G</option>";
        addContent +="<option value=\"yellow\">Y</option>";
        addContent +="<option value=\"red\">R</option>";
        addContent +="<option value=\"gray\">P</option>";
        addContent +="</select>";
        addContent +="</td>";
        addContent +="</tr>";
        
        //idx = 0;
        $('#salesTargetTable > tbody:last').append(addContent);
        CalendarUtil.dateInputFormat('planDate');
        CalendarUtil.dateInputFormat('resultDate');
        
        SuggestUtil.bind('USER', 'targetPeopleName', 'targetPeopleOidAttr');
        
        stepReset();
        
        scrollControl();
        /* $('input[name=step]').each(function(i) {
            $(this).val('Step'+targetIdx);
        }); */
        //$("#content").scrollTop($("#content")[0].scrollHeight);
    }
    
    window.AddIndex = "";
    
    function addUser(obj) {
        
        AddIndex = $(obj).index("img#img_TargetPeople");
        
        var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=s&invokeMethod=addUserCallBack";
        getOpenWindow2(url,'searchDqmPopup', 820, 710);
        
    }
    
    function addUserCallBack(arrObj){
    	if ( typeof arrObj == "undefined" || arrObj == null ) {
            return;
        }
    	
    	var userId = "";     // Id
        var userName = "";   
        
        for ( var i=0; i < arrObj.length; i++ ) {
            // [0] - wtuser oid // [1] - people oid // [2] - dept oid
            // [3] - user id    // [4] - name       // [5] - dept name
            // [6] - duty       // [7] - duty code  // [8] - email

            var infoArr = arrObj[i];
            userId = infoArr[0];
            userName = infoArr[4];
        }
        
        $('input[name=targetPeopleName]').each(function(i) {
            if(AddIndex == i){
                 $(this).val(userName);
            }
        });
        
        $('input[name=targetPeopleOidAttr]').each(function(i) {
            if(AddIndex == i){
                $(this).val(userId);
            }
        });
    }
    
    function delUser(obj){
        var index = $(obj).index("img#img_TargetPeopleDel");
        
        $('input[name=targetPeopleName]').each(function(i) {
            if(index == i){
                
                $(this).val('');
                
            }
        });
        
        $('input[name=targetPeopleOidAttr]').each(function(i) {
            if(index == i){
                $(this).val('');
            }
        });
    }
    
    function loadPartProperty(returnValue){
        //alert('a');
        
        var valueField = 'partClazOid';
        var displayField = 'partClazKrName';
        try{
            $('[name='+valueField+']').val(returnValue[0].id);//oid
            $('[name='+displayField+']').val(returnValue[0].name);//kr name
        }catch(e){
            alert("최상위 노드(부퓸분류)는 선택불가합니다.");
        }
        
        
        
    }
    window.MainpartClazOid = '';
    function selectPartClaz(){
        
        MainpartClazOid = $('[name=partClazOid]').val();
        
        if(MainpartClazOid == ''){
            alert('먼저 Main 부품분류를 입력하시기 바랍니다.');
            hideProcessing();
            return false;
        }
        
        SearchUtil.selectPartClazPopUp('insertPartClassLine','onlyLeaf=Y&openAll=N','createSalesProject');
        
    }
    
    var indexCnt = 0;
    
    function insertPartClassLine(arr){
        
        hideProcessing();
        
        for(j=0; j<arr.length; j++){
            var rtnFlag = false;
            
            if(typeof arr == "undefined" || arr == null) {
                rtnFlag = true;
                return;
            }
           
            if(arr[j].id == ''){
                rtnFlag = true;
                return;
            }
            
            if(MainpartClazOid == arr[j].id){
                alert(LocaleUtil.getMessage('09104',arr[j].name));//"선택한 부품은 이미 추가된 부품입니다."
                return false;
            }
            
            $.each($('[name=classOidArr]'), function(i, val){
                if(val.value == arr[j].id) {
                    alert(LocaleUtil.getMessage('09104',arr[j].name));//"선택한 부품은 이미 추가된 부품입니다."
                    return false;
                    rtnFlag = true;
                }
                
            });
            
            if(rtnFlag){
                return;
            }
            
            
            
            var innerRow = partClassTable.insertRow();
            
            indexCnt = indexCnt + 1; //인덱스 증가
            
            innerRow.height = "27";
            
            for ( var k = 0; k < 8; k++) {
                innerCell = innerRow.insertCell();
                innerCell.height = "23";
                if (k == 0) {
                    innerCell.width = "2%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();return false;\"><b><img src=\"/plm/portal/images/b-minus.png\"></b></a>";
                }else if (k == 1) {
                    innerCell.width = "4%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input type='hidden' name='mainCheck' value='N'>";
                } else if (k == 2) {
                    innerCell.width = "22%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = arr[j].name;
                    /* innerCell.innerHTML = "<input type='text' name='productTypeLevel2Name"+indexCnt+"' class='txt_field' style='width: 80%'>"
                                        + "<input type='hidden' name='productTypeLevel2"+indexCnt+"' value=''>"
                                        + "<a href=\"javascript:SearchUtil.selectOnePartClaz('productTypeLevel2"+indexCnt+"','productTypeLevel2Name"+indexCnt+"','onlyLeaf=Y&openAll=N&depthLevel2=Y');\">"
                                        + "<img src='/plm/portal/images/icon_5.png' border='0'></a>"
                                        + "<a href=\"javascript:CommonUtil.deleteValue('productTypeLevel2"+indexCnt+"','productTypeLevel2Name"+indexCnt+"');\">"
                                        + "<img src='/plm/portal/images/icon_delete.gif' border='0'></a>"; */
                } else if (k == 3) {
                    innerCell.width = "24%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input name='investCost' id='investCost' type='text' class='txt_field' style='width:95%' value='0'  esse='true' esseLabel='투자비' onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/>";
                } else if (k == 4) {
                    innerCell.width = "12.5%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input name='planTotal' id='planTotal' type='text' class='txt_field' style='width:95%' value='0' esse='true' esseLabel='총대수' onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/>";
                } else if (k == 5) {
                    innerCell.width = "12.5%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input name='planYear' id='planYear' type='text' class='txt_field' style='width:95%' value='0' esse='true' esseLabel='년대수' onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/>";
                    
                } else if (k == 6) {
                    innerCell.width = "12.5%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input name='expectSalesTotal' id='expectSalesTotal' type='text' class='txt_field' style='width:95%' value='0'  esse='true' esseLabel='총매출' onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/>";
                    
                    
                } else if (k == 7) {
                    innerCell.width = "12.5%";
                    innerCell.className = "tdwhiteM";
                    innerCell.innerHTML = "<input name='expectSalesYear' id='expectSalesYear' type='text' class='txt_field' style='width:95%' value='0'  esse='true' esseLabel='년매출' onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/><input name='classOidArr' type='hidden' value='"+arr[j].id+"'/>";
                } 
                
            }
        }
        
        
    }
    
    function onlyNumber(obj){
        
        //방향키 인경우는 제외 처리(글쓰다가 앞으로 다시 안가지는 경우때문)
        if (event.keyCode >= 37 && event.keyCode <= 40) return;

        val=obj.value;
        re=/[^0-9.]/gi;   //가능 문자에 . 추가
        val = val.replace(re,"");
          

        //최초 . 사용하지 못하게 처리함.
        if( val.indexOf(".") == 0){
            val = "";
        }

        // 아래내용은 소수점이 2개이상인지 확인 후 재귀호출해서 여러번 kye up 없이 누르더라도

        // 소숫점이 하나만 남도록 처리

        v_cnt = val.indexOf(".",val.indexOf(".")+1);
          
        if( v_cnt > -1 ){
            val = val.substring(0,v_cnt) + val.substring(v_cnt + 1,val.length);
            obj.value = val;
            SetNum(obj);
        }
        
        obj.value = val;


    }
    
    function selectMultiSubContractor(returnValue){
        var nodeIdStr='', nodeNameStr='';
        for(var i=0; i < returnValue.length; i++){
                if(i == returnValue.length - 1){
                        nodeIdStr += returnValue[i][0];
                        nodeNameStr += returnValue[i][2];
                }else{
                        nodeIdStr += returnValue[i][0]+',';     
                        nodeNameStr += returnValue[i][2]+',';
                }
        }
        $('[name=customerCode]').val(nodeIdStr);
        $('[name=customerName]').val(nodeNameStr);
    }
    
    //사용안함. 만약 자동차사를 멀티선택해야한다면 활성화해야한다
    function selectLastUsingBuyer(returnValue){
        var nodeIdStr='', nodeNameStr='';
        for(var i=0; i < returnValue.length; i++){
                if(i == returnValue.length - 1){
                        nodeIdStr += returnValue[i][0];
                        nodeNameStr += returnValue[i][2];
                }else{
                        nodeIdStr += returnValue[i][0]+',';     
                        nodeNameStr += returnValue[i][2]+',';
                }
        }
        $('[name=lastBuyerCode]').val(nodeIdStr);
        $('[name=lastBuyerName]').val(nodeNameStr);
    }
    
    function openTagManagePopup(){
    	openPopup("/plm/ext/common/tag/tagManagePopup", "Tag 등록/수정", 600, 720);
    }
    
    function setLastBuyer(returnValue){
    	$('[name=lastBuyerCode]').val(returnValue[0][0]);//id
        $('[name=lastBuyerName]').val(returnValue[0][2]);//name
    }
    
    function setCarType(returnValue){
        $('[name=model]').val(returnValue[0][0]);
        $('[name=tempmodel]').val(returnValue[0][1]);
    }
    
</script>
<!-- 이노디터 JS Include End -->
<form name="salesProjectForm" method="post" enctype="multipart/form-data">
    <input type="hidden" name="idx" value="">
    <input type="hidden" name="stepName" value="">
    <input type="hidden" name="isTransient" value="">
    <input type="hidden" name="basicEditAuth" value="ok">
    
    <div class="contents-wrap">
        <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
        <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea>
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 : DB에 컨텐츠 내용과는 별도로 저장할 것-->
        <input type="hidden" name="hdnBackgroundColor" value="" />
        <input type="hidden" name="hdnBackgroundImage" value="" /> 
        <input type="hidden" name="hdnBackgroundRepeat" value="" />
        <!-- 이 부분은 배경색 또는 배경이미지 버튼을 사용하는 경우에만 해당 -->
        <!-- 이노디터에서 작성된 내용을 보낼 Form End -->
        <table style="width: 100%;">
            <tr>
                <td background="/plm/portal/images/logo_popupbg.png">
                    <table style="height: 28px;">
                        <tr>
                            <td width="7"></td>
                            <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                            <td class="font_01">영업수주현황 등록</td>
                        </tr>
                    </table>
                </td>
                <td width="136" align="right"><img
                    src="/plm/portal/images/logo_popup.png"></td>
            </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space5"></td>
            </tr>
        </table>
        <div class="float-r" style="text-align: right">
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:sales.create('transient')" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02453")%><!-- 저장 --></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
            <span class="in-block v-middle">
                <span class="pro-table">
                    <span class="pro-cell b-left"></span>
                    <span class="pro-cell b-center"><a href="javascript:window.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "02887")%><!-- 취소 --></a></span>
                    <span class="pro-cell b-right"></span>
                </span>
            </span>
        </div>
        <div class="sub-title-02 b-space15 clear">
            <span class="r-space9"><img
                src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "01120")%><!-- 기본정보 -->
        </div>
        <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table id="createTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed" border="1">
                <colgroup>
                    <col width="15%" />
                    <col width="35%" />
                    <col width="15%" />
                    <col width="35%" />

                </colgroup>
                <tbody>
                    <tr>
                        <td class="tdblueL">중요도<span class="red">*</td>
                        <td class="tdwhiteL">
                          <ket:select id="salesRank" name="salesRank" className="fm_jmp" value="" codeType="SALESRANK" useOidValue="true" multiple='multiple' esse='true' esseLabel='중요도' />
                        </td>
                        <td class="tdblueL">프로젝트 명<span class="red">*</td>
                        <td class="tdwhiteL"><input type="text" class="txt_field" style="width: 100%" value="" name="projectName" id="projectName" esse='true' esseLabel='프로젝트 명'/></td>

                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%><span class="red">*</td>
                        <td class="tdwhiteL">
                            <input type="text" name="customerName" class="txt_fieldRO" style="width: 70%" readonly>
                            <input type="hidden" name="customerCode" esse='true' esseLabel='고객사'>
                            <a href="javascript:SearchUtil.selectMultiSubContractorPopUp('selectMultiSubContractor');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('customerCode','customerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        
                        <td class="tdblueL">자동차사<span class="red">*</td>
                        <td class="tdwhiteL">
                            <input type="text" name="lastBuyerName" class="txt_fieldRO" style="width: 70%" readonly>
                            <input type="hidden" name="lastBuyerCode" esse='true' esseLabel='자동차사'>
                            <a href="javascript:SearchUtil.selectOneLastUsingBuyerPopup('setLastBuyer');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('lastBuyerCode','lastBuyerName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">프로젝트 유형<span class="red">*</td>
                        <td class="tdwhiteL">
                          <ket:select id="developType" name="developType" value="" className="fm_jmp"  codeType="DEVELOPENTTYPE" useOidValue="true" multiple='multiple' esse='true' esseLabel='프로젝트 유형' />
                        </td>
                        <td class="tdblueL">적용부</td>
                        <td class="tdwhiteL"><input type="text" id="applyArea" name="applyArea" class="txt_field" style="width: 100%" value=""/></td>
                    </tr>

                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01248")%><%--대표차종--%><span class="red">*</td>
                        <td class="tdwhiteL">
                          <div id="modelDiv">
                          <input type="text" id="tempmodel" name="tempmodel" class="txt_field" style="width: 70%">
                          <input type="hidden" id="model" name="model" value="" esse='true' esseLabel='대표차종'>
                          <a href="javascript:SearchUtil.selectCarType('model','tempmodel','setCarType');"><img src="/plm/portal/images/icon_5.png" border="0"></a> 
                          <a href="javascript:CommonUtil.deleteValue('model','tempmodel');"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>
                        </td>
                        
                        <td class="tdblueL">SOP</td>
                        <td class="tdwhiteL">
                          <input type="text" class="txt_field" name="sopDate" style="width: 30%" />
                          <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('sopDate');" style="cursor:pointer;cursor:hand;">
                        </td>
                    </tr>
                    <tr>
                       <td class="tdblueL">경쟁사</td>
                       <td class="tdwhiteL">
                           <ket:select id="competitorCompany" name="competitorCompany" value="" className="fm_jmp"  codeType="SALESCOMPETITOR" useOidValue="true" multiple='multiple' />
                       </td>
                       <td class="tdblueL">국가<span class="red">*</span></td>
                       <td class="tdwhiteL">
                           <ket:select id="nation" name="nation" value="" className="fm_jmp"  codeType="SALESNATION" useOidValue="true" multiple='multiple' esse='true' esseLabel='국가'/>
                       </td>
                    </tr>
                    
                    <tr>
                       <td class="tdblueL">대분류 제품군<span class="red">*</span></td>
                       <td class="tdwhiteL">
                           <ket:select id="mainCategory" name="mainCategory" value="" className="fm_jmp" style="width: 170px;" codeType="SALESMAINCATEGORY" useOidValue="true" multiple='multiple' esse='true' esseLabel='대분류 제품군' />
                       </td>
                       <td class="tdblueL">WorkShop</td>
                       <td class="tdwhiteL">
                           <select onBlur="fm_jmp" id="workShopYN" name="workShopYN" multiple="multiple" style="width:130px">
                                <option value="">선택</option>
                                <option value="Y">YES</option>
                           </select>
                       </td>
                    </tr>
                    
                    <tr>
                       <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02578") %><%--제품구분--%><span class="red">*</span></td>
                       <td class="tdwhiteL" colspan="3">
                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
                               <tr>
                                   <td class="space5"></td>
                               </tr>
                           </table>
                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                               <table id="partClassTable" width="100%" cellpadding="0" cellspacing="0">
                                   <tbody id="partclassTableBody">
                                       <colgroup>
                                       <col width="2%" />
                                       <col width="3%" />
                                       <col width="17%" />
                                       <col width="8%" />
                                       <col width="10%" />
                                       <col width="10%" />
                                       <col width="10%" />
                                       <col width="10%" />

                                       </colgroup>
                                       <tr>
                                           <td class="tdgrayM" rowspan="2"><a href="#" onclick="javascript:showProcessing();selectPartClaz();"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                           <td class="tdgrayM" rowspan="2">구분</td>
                                           <td class="tdgrayM" rowspan="2">부품분류</td>
                                           <td class="tdgrayM" rowspan="2">투자비<br>(백만원)</td>
                                           <td class="tdgrayM" colspan="2">기획 대수(만대)</td>
                                           <td class="tdgrayM" colspan="2">예상 매출(백만원)</td>
                                       </tr>
                                       <tr>
                                           <td class="tdgrayM">총 대수</td>
                                           <td class="tdgrayM">년 대수</td>
                                           <td class="tdgrayM">총 매출</td>
                                           <td class="tdgrayM">년 매출</td>
                                       </tr>
                                       
                                       <tr>
                                           <td class="tdwhiteM"></td>
                                           <td class="tdwhiteM">Main<input type='hidden' name='mainCheck' value='Y'></td>
                                           <td class="tdwhiteM"><input type="text" id="partClazKrName" name="partClazKrName" class="txt_fieldRO" style="width: 70%" readonly>
                                           <input type="hidden" id="partClazOid" name="partClazOid" value="" esse="true" esseLabel="제품구분">
                                           <a href="javascript:SearchUtil.selectOnePartClazPopUp('loadPartProperty','openAll=N');">
                                           <img src="/plm/portal/images/icon_5.png" border="0"></a>
                                           <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                                           <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                                           <td class="tdwhiteM"><input name='investCost' id='investCost' type='text' class='txt_field' style='width:95%' value='0' esse="true" esseLabel="투자비" onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/></td>
                                           <td class="tdwhiteM"><input name='planTotal' id='planTotal' type='text' class='txt_field' style='width:95%' value='0' esse="true" esseLabel="총대수" onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/></td>
                                           <td class="tdwhiteM"><input name='planYear' id='planYear' type='text' class='txt_field' style='width:95%' value='0' esse="true" esseLabel="년대수" onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/></td>
                                           <td class="tdwhiteM"><input name='expectSalesTotal' id='expectSalesTotal' type='text' class='txt_field' style='width:95%' value='0' esse="true" esseLabel="총매출" onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/></td>
                                           <td class="tdwhiteM"><input name='expectSalesYear' id='expectSalesYear' type='text' class='txt_field' style='width:95%' value='0' esse="true" esseLabel="년매출" onkeyup='onlyNumber(this)' style='ime-mode:disabled;'/></td>
                                       </tr>

                                       
                                  </tbody>
                               </table>
                           </div>
                              <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                  <tr>
                                      <td class="space5"></td>
                                  </tr>
                              </table>
                        </td>
                    </tr>
                    
                    <tr>
                        <td class="tdblueL">영업<br>목표</td>
                        <td class="tdwhiteL" colspan="3">
                          <textarea name="documentDescription" rows="0" cols="0" style="display: none"></textarea>
                          <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea>
                          <!-- Editor Area Container : Start -->
                          <div id="EDITOR_AREA_CONTAINER"></div>
                          <!-- Editor Area Container : End -->
                        </td>
                    </tr>
                    <!--  
                    <tr>
                        <td class="tdblueL">비고</td>
                        <td class="tdwhiteL" colspan="3">
                          <textarea name="bigo" rows="5" cols="3" style="width: 98%; height:95%"></textarea>
                        </td>
                    </tr>
                    -->
                    <tr>
                       <td class="tdblueL">프로젝트 참여자<br>(조회권한)</td>
                       <td class="tdwhiteL0" colspan="3">
                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
                               <tr>
                                   <td class="space5"></td>
                               </tr>
                           </table>
                           <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                               <table id="projectMemberTable" width="100%" cellpadding="0" cellspacing="0">
                                   
                                       <colgroup>
                                       <col width="2%" />
                                       <col width="32%" />
                                       <col width="34%" />
                                       <col width="32%" />
                                       </colgroup>
                                   
                                       <tr>
                                           <td class="tdgrayM" rowspan="2"><a href="#" onclick="javascript:insertTargetMember(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                           <td class="tdgrayM" rowspan="2">이름</td>
                                           <td class="tdgrayM" rowspan="2">부서</td>
                                           <td class="tdgrayM" rowspan="2">직위</td>
                                       </tr>
                                  <tbody>                                       
                                  </tbody>
                               </table>
                           </div>
                           <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                           </table>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796")%><%--첨부파일--%></td>
                        <td colspan="3" class="tdwhiteL">
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>
                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                <tr>
                                    <td class="space5"></td>
                                </tr>
                            </table>

                            <div id="div_scroll3" style="overflow-x: hidden; overflow-y: auto;" class="table_border">
                                <table width="100%" cellpadding="0" cellspacing="0">
                                    <tr class="headerLock3">
                                        <td>
                                            <table border="0" cellpadding="0" cellspacing="0" width="100%" style="table-layout: fixed">
                                                <tr>
                                                    <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                                                    <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961")%><%--파일명--%></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
	                                    <td>
	                                    <table width="100%" cellpadding="0" cellspacing="0" style="table-layout: fixed">
                                            <col width="20">
                                            <col width="">
                                            <tbody id="fileTable"></tbody>
                                        </table>
                                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                            <tr>
                                                <td class="space5"></td>
                                            </tr>
                                        </table>
	                                    </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
<!--                     <tr>
                        <td class="tdblueL">관련 Tag</td>
                        <td colspan="3" class="tdwhiteL">
                        <span class="REFTAG"></span>
                        <span class="in-block v-middle r-space7" style="float:right;">
                            <span class="pro-table">
                                <span class="pro-cell b-left"></span>
                                <span class="pro-cell b-center"> <a href="javascript:openTagManagePopup();" class="btn_blue">Tag 편집</a></span>
                                <span class="pro-cell b-right"></span>
                            </span>
                        </span>
                        </td>
                    </tr> -->
                </tbody>
            </table>
        </div>
        <div class="sub-title-02 b-space15 clear">
            <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>영업추진현황
        </div>
        <div class="b-space30">
            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td class="tab_btm2"></td>
                </tr>
            </table>
            <table id="salesTargetTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed" border="1">
                <colgroup>
                    <col width="2%" />
                    <col width="5%" />
                    <col width="15%" />
                    <col width="27%" />
                    <col width="13%" />
                    <col width="13%" />
                    <col width="13%" />
                    <col width="*" />
                </colgroup>
                <tbody>
                <tr>
                    <td rowspan="2" class="tdblueM"><a href="#" onclick="javascript:insertTargetLine(); return false;"><b><img src="/plm/portal/images/b-plus.png"></b></a></td>
                    <td rowspan="2" class="tdblueM">단계</td>
                    <td rowspan="2" class="tdblueM">Subject</td>
                    <td rowspan="2" class="tdblueM">영업 추진 계획</td>
                    <td rowspan="2" class="tdblueM">담당자</td>
                    <td colspan="2" class="tdblueM">완료일정</td>
                    <td rowspan="2" class="tdblueM">진행상황<br>(G,Y,R,P)</td>
                </tr>
                <tr>
                    <td class="tdblueM">계획</td>
                    <td class="tdblueM">실적</td>
                </tr>
                
<!--                 <tr>
                    <td class="tdwhiteM"><a href=# onclick="javascript:$(this).parent().parent().remove();return false;"><b><img src="/plm/portal/images/b-minus.png"></b></a></td>
                    <td class="tdwhiteM"><a href="#" onclick="javascript:insertTargetLine(); return false;">Step 11</a></td>
                    <td class="tdwhiteM"><input type="text" name="subject" id="subject" class="txt_field" style="width: 95%"></td>
                    <td class="tdwhiteM"><input type="text" name="salsePlan" id="salsePlan" class="txt_field" style="width: 95%"></td>
                    <td class="tdwhiteL"><input type="text" name="targetPeopleName" class="txt_field" style="width: 60%">
                    <input type=hidden name=targetPeopleOidAttr><a href="javascript:;">
                    <img id=img_TargetPeople src=/plm/portal/images/icon_user.gif border=0 onclick="addUser(this); return false;"></a> <a href=javascript:;>
                    <img id=img_TargetPeopleDel src=/plm/portal/images/icon_delete.gif border=0 onclick="delUser(this); return false;"></a></td>
                    
                    <td class=tdwhiteL>
                    <input type=text name=planDate class=txt_field style="width: 60%">
                    <img src=/plm/portal/images/icon_delete.gif border=0 onclick="javascript:CommonUtil.deleteDateValue('planDate');" style=cursor:pointer; cursor: hand;>
                    </td>
                    
                    <td class=tdwhiteL>
                    <input type=text name=resultDate class=txt_field style="width: 60%">
                    <img src=/plm/portal/images/icon_delete.gif border=0 onclick="javascript:CommonUtil.deleteDateValue('endDate');" style=cursor:pointer; cursor: hand;>
                    </td>
                    
                    <td class=tdwhiteM>
                    <select id="planCheck" name="planCheck" className="fm_jmp" style="width: 95%;" border: none; onchange="fn_changeCombo2(this); return false;">
                    <option value=>선택</option>
                    <option value=green>G</option>
                    <option value=yellow>Y</option>
                    <option value=red>R</option>
                    </select>
                    </td>
                </tr> -->
                
                </tbody>
            </table>
        </div>
    </div>
    <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
</form>