<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<!-- EJS TreeGrid Start -->
<script src="/plm/extcore/js/shared/commonGrid.js"></script>
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET_T.js"></script>
<!-- EJS TreeGrid End -->
<script type="text/javascript" src="/plm/extcore/js/sample/sample.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//------------ start suggest binding ------------//
    //사용자 suggest
    SuggestUtil.bind('USER', 'fstChargeName', 'fstCharge');
    //부서 suggest
    SuggestUtil.bind('DEPARTMENT', 'deptName', 'deptCode');
    //Die no suggest
    SuggestUtil.bind('DIENO', 'dieNumber');
    //검토 Project No suggest
    SuggestUtil.bind('REVIEWPROJNO', 'pjtNo');
    //제품 Project No suggest
    SuggestUtil.bind('PRODUCTPROJNO', 'pjtNo2');
    //차종 suggest
    SuggestUtil.bind('CARTYPE', 'carTypeText', 'carType');
    //개발산출물 문서 분류 suggest
    SuggestUtil.bind('PROJECTDOCTYPE', 'projectDocTypeTxt', 'projectDocType');
    //기술 문서 분류 suggest
    SuggestUtil.bind('TECHDOCTYPE', 'techDocTypeTxt', 'techDocType');
    //고객처(사) suggest
    SuggestUtil.bind('CUSTOMER', 'oemTypeTxt', 'oemType');
    //부품 suggest
    SuggestUtil.bind('PARTNO', 'partNo');
    //도면 suggest
    SuggestUtil.bind('EPMNO', 'epmNo');
    //제품분류 suggest
    SuggestUtil.bind('PRODUCTTYPE', 'productTypeText', 'productType');
    //ECO no suggest
    SuggestUtil.bind('ECONO', 'ecoNumber', 'ecoOid');
    //ECR no suggest
    SuggestUtil.bind('ECRNO', 'ecrNumber', 'ecrOid');
    //최종사용처 suggest
    SuggestUtil.bind('CUSTOMEREVENT', 'lastUsingBuyerTxt', 'lastUsingBuyer');
    //------------ end suggest binding ------------//
    // default 한달 설정
    $('[name=startDate]').val(predate);
    $('[name=endDate]').val(postdate);
    // Calener field 설정
    CalendarUtil.dateInputFormat('minDate', null, {minDate : new Date()});
    CalendarUtil.dateInputFormat('maxDate', null, {maxDate : new Date()});
    CalendarUtil.dateInputFormat('callBackDate', function(){
    	alert('selected');
    });
    CalendarUtil.dateInputFormat('startDate','endDate'); //기한 설정시 start와 end field를 설정한다.
    // multiselect
    CommonUtil.singleSelect('developType2',100);
    CommonUtil.multiSelect('customerEvent2',100);
    CommonUtil.multiSelect('pjtState2',100);
    CommonUtil.multiSelect('authorStatus',100);
    //client paging
    //Sample.createGrid();
    //server paging
	Sample.createPaingGrid();
});
</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><table width="100%" height="28" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                                <td class="font_01">Sample 검색</td>
                                <td align="right">
                                <img src="/plm/portal/images/icon_navi.gif">Home 
                                <img src="/plm/portal/images/icon_navi.gif">관리메뉴
                                <img src="/plm/portal/images/icon_navi.gif">Sample</td>
                            </tr>
                        </table></td>
                </tr>
                <tr>
                    <td class="head_line"></td>
                </tr>
                <tr>
                    <td class="space5"></td>
                </tr>
            </table>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Sample.goCreate();" class="btn_blue">등록</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="#"
                                                onclick="javascript:Sample.clear();" class="btn_blue">초기화</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                                <td width="5">&nbsp;</td>
                                <td><table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
                                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                href="javascript:Sample.search();" class="btn_blue">검색</a></td>
                                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                        </tr>
                                    </table></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <table style="width: 100%;">
            <tr>
                <td class="space5"></td>
            </tr>
            </table>
            <!-- [END] button -->
            <!-- [START] search condition -->
            <table style="width: 100%;">
            <tr>
                <td class="tab_btm2"></td>
            </tr>
            </table>
            <form name="SampleSearchForm">
                <table border="0" cellspacing="0" cellpadding="0" width="100%">
                    <tr>
                        <td width="100" class="tdblueL">이름</td>
                        <td width="*" class="tdwhiteL"><input type="text" name="name" class="txt_field" style="width: 98%"></td>
                        <td width="100" class="tdblueL">제목</td>
                        <td width="*" class="tdwhiteL"><input type="text" name="title" class="txt_field" style="width: 98%"></td>                        
                        <td width="100" class="tdblueL">사용자</td>
                        <td class="tdwhiteL">
                            <input type="text" name="fstChargeName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="fstCharge"> 
                            <a href="javascript:SearchUtil.selectOneUser('fstCharge','fstChargeName');">
                            <img src="/plm/portal/images/icon_user.gif" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('fstCharge','fstChargeName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">Project No(검토)</td>
                        <td class="tdwhiteL">
                            <input type="text" name="pjtNo" class="txt_field" style="width: 98%;"></td>                            
                        <td class="tdblueL">Project No(제품)</td>
                        <td class="tdwhiteL">
                            <input type="text" name="pjtNo2" class="txt_field" style="width: 98%;"></td>
                        <td class="tdblueL">부서</td>
                        <td class="tdwhiteL">
                            <input type="text" name="deptName" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="deptCode"> 
                            <a href="javascript:SearchUtil.addDepartment(true,'deptCode', 'deptName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('proteamNo','proteamName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">문서분류(개발)</td>
                        <td class="tdwhiteL"><input type="text" name="projectDocTypeTxt" class="txt_field" style="width: 70%">
                            <input type="hidden" name="projectDocType" value="">
                            <a href="javascript:SearchUtil.selectDevDocCategory(Sample.setDevDocCategory);">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('projectDocTypeTxt','projectDocType');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">문서분류(기술)</td>
                        <td class="tdwhiteL"><input type="text" name="techDocTypeTxt" class="txt_field" style="width: 70%">
                            <input type="hidden" name="techDocType" value="">
                            <a href="javascript:SearchUtil.selectTechDocCategory(Sample.setTechDocCategory);">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('techDocTypeTxt','techDocType');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">문서분류(통합)</td>
                        <td class="tdwhiteL"><input type="text" name="docTypeTxt" class="txt_field" style="width: 70%">
                            <input type="hidden" name="docType" value="">
                            <a href="javascript:SearchUtil.selectOneDocCategory('docType','docTypeTxt');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('docTypeTxt','docType');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">차종,대표모델</td>
                        <td class="tdwhiteL">
                            <input type="text" name="carTypeText" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="carType">
                            <a href="javascript:SearchUtil.selectCarType('carType','carTypeText');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('carType','carTypeText');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">제품분류</td>
                        <td class="tdwhiteL">
                            <input type="text" name="productTypeText" class="txt_field" style="width: 70%"> 
                            <input type="hidden" name="productType" value=""> 
                            <a href="javascript:SearchUtil.selectPlan('proteamNo','proteamName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('proteamNo','proteamName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">최종사용처</td>
                        <td class="tdwhiteL"><input type="text" name="lastUsingBuyerTxt" class="txt_field" style="width: 70%">
                            <input type="hidden" name="lastUsingBuyer"><a href="javascript:SearchUtil.selectOneLastUsingBuyer('lastUsingBuyer','lastUsingBuyerTxt');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('lastUsingBuyer','lastUsingBuyerTxt');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">생산처</td>
                        <td class="tdwhiteL">
                            <input type="text" name="proteamName" class="txt_fieldRO" style="width: 70%" readonly> 
                            <input type="hidden" name="proteamNo"> 
                            <a href="javascript:SearchUtil.selectPlan('proteamNo','proteamName');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('proteamNo','proteamName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">고객처</td>
                        <td class="tdwhiteL"><input type="text" name="subcontractorTxt" class="txt_fieldRO" style="width: 70%" readonly>
                            <input type="hidden" name="subcontractor">
                            <a href="javascript:SearchUtil.selectOneSubContractor('subcontractor','subcontractorTxt');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('subcontractor','subcontractorTxt');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">납입처</td>
                        <td class="tdwhiteL">
                            <input type="text" name="sapSubcontractorTxt" class="txt_fieldRO" style="width: 70%" readonly> 
                            <input type="hidden" name="sapSubcontractor"> 
                            <a href="javascript:SearchUtil.selectOneSapSubContractor('sapSubcontractor','sapSubcontractorTxt');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('proteamNo','proteamName');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                    </tr>
                    <tr>
                        <td class="tdblueL">부품분류(단건)</td>
                        <td class="tdwhiteL"><input type="text" name="onePartClazKrName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="onePartClazOid" value="">
                            <a href="javascript:SearchUtil.selectOnePartClaz('onePartClazOid', 'onePartClazKrName','onlyLeaf=Y&openAll=Y');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('onePartClazKrName','onePartClazOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <td class="tdblueL">부품분류(다중)</td>
                        <td class="tdwhiteL"><input type="text" name="partClazKrName" class="txt_field" style="width: 70%">
                            <input type="hidden" name="partClazOid" value="">
                            <a href="javascript:SearchUtil.selectPartClaz(setPartClazOid, 'openAll=Y');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partClazKrName','partClazOid');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <script type="text/javascript">
                        function setPartClazOid(checkedNode){
                            var nodeOIdStr='', nodeNameStr='';
                            for(var i=0; i < checkedNode.length; i++){
                                if(i == checkedNode.length - 1){
                                    nodeOIdStr += checkedNode[i].id;
                                    nodeNameStr += checkedNode[i].name;
                                }else{
                                    nodeOIdStr += checkedNode[i].id+','; 
                                    nodeNameStr += checkedNode[i].name+',';
                                }
                            }
                            $('[name=partClazOid]').val(nodeOIdStr);
                            $('[name=partClazKrName]').val(nodeNameStr);
                        }
                        </script>
                        <td class="tdblueL">문서 <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdwhiteL" colspan="5">
                            <ket:select id="authorStatus" name="authorStatus" className="fm_jmp" style="width: 170px;" multiple="multiple" lifeCycleState="KET_COMMON_LC"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">Project No</td>
                        <td class="tdwhiteL"><input type="text" name="projectNo" class="txt_field" style="width: 70%" readonly>
							<a href="javascript:SearchUtil.selectProject(setProjectNo,'project_type=2');">
							<img src="/plm/portal/images/icon_5.png" border="0"></a> 
							<a href="javascript:CommonUtil.deleteValue('projectNo');">
							<img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
						<script type="text/javascript">
						function setProjectNo(objArr){
						        var projectNo= "";
						        for ( var i = 0; i < objArr.length; i++ ) {
						            projectNo+= objArr[i][1] + ",";
						        }
						        if(projectNo.length > 0) projectNo= projectNo.substring(0, projectNo.length-1);
						        $('[name=projectNo]').val(projectNo);
						}
						</script>
                        <td class="tdblueL">도면 No</td>
                        <td class="tdwhiteL"><input type="text" name="epmNo" class="txt_field" style="width: 70%" >
                            <a href="javascript:SearchUtil.selectDrawing(setEpmNo);">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('epmNo');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <script type="text/javascript">
                        function setEpmNo(objArr){
                                var epmNo= "";
                                for ( var i = 0; i < objArr.length; i++ ) {
                                	epmNo+= objArr[i][1] + ",";
                                }
                                if(epmNo.length > 0) epmNo= epmNo.substring(0, epmNo.length-1);
                                $('[name=epmNo]').val(epmNo);
                        }
                        </script>    
                        <td class="tdblueL">Part No</td>
                        <td class="tdwhiteL"><input type="text" name="partNo" class="txt_field" style="width: 70%" >
                            <a href="javascript:showProcessing();SearchUtil.selectPart('setPartNo','pType=A');">
                            <img src="/plm/portal/images/icon_5.png" border="0"></a> 
                            <a href="javascript:CommonUtil.deleteValue('partNo');">
                            <img src="/plm/portal/images/icon_delete.gif" border="0"></a></td>
                        <script type="text/javascript">
                        function setPartNo(objArr){
                                var partNo= "";
                                for ( var i = 0; i < objArr.length; i++ ) {
                                	partNo+= objArr[i][1] + ",";
                                }
                                if(partNo.length > 0) partNo= partNo.substring(0, partNo.length-1);
                                $('[name=partNo]').val(partNo);
                        }
                        </script>
                    </tr>
                    <tr>
                        <td class="tdblueL">ECO 번호</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ecoNumber" class="txt_field" style="width: 98%">
                            <input type="hidden" name="ecoOid"></td>
                        <td class="tdblueL">ECR 번호</td>
                        <td class="tdwhiteL">
                            <input type="text" name="ecrNumber" class="txt_field" style="width: 98%">
                            <input type="hidden" name="ecrOid" value="" id="ecrOid"></td>
                        <td class="tdblueL">날짜(callBack)</td>
                        <td class="tdwhiteL">
                            <input type="text" name="callBackDate" class="txt_field" style="width: 80px">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('callBackDate');" style="cursor: hand;">
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">Die No</td>
                        <td class="tdwhiteL"><input type="text" name="dieNumber" class="txt_field" style="width: 98%" value=""></td>
                        <td class="tdblueL">날짜(minDate)</td>
                        <td class="tdwhiteL">
                            <input type="text" name="minDate" class="txt_field" style="width: 80px">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('minDate');" style="cursor: hand;"></td>
                        <td class="tdblueL">날짜(maxDate)</td>
                        <td class="tdwhiteL">
                            <input type="text" name="maxDate" class="txt_field" style="width: 80px">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('maxDate');" style="cursor: hand;"></td>
                    </tr>
                    <tr>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00625") %><%--개발구분--%></td>
                        <td class="tdwhiteL">
                            <ket:select id="developType2" name="developType2" className="fm_jmp" style="width: 170px;" codeType="DEVELOPENTTYPE" multiple="multiple"/>
                        </td>
                        <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02847") %><%--최종사용처--%></td>
                        <td  class="tdwhiteL">
                            <ket:select id="customerEvent2" name="customerEvent2" className="fm_jmp" style="width: 170px;" multiple="multiple" codeType="CUSTOMEREVENT" depthLevel="child"  value="740902093,463728558"/>
                        </td>
                        <td class="tdblueL">Project <%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%></td>
                        <td class="tdwhiteL">
                            <ket:select id="pjtState2" name="pjtState2" className="fm_jmp" style="width: 170px;" multiple="multiple" lifeCycleState="KET_PRODUCT_PMS_LC" value="APPROVED,UNDERREVIEW"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdblueL">기간</td>
                        <td class="tdwhiteL" colspan="5">
                            <input type="text" name="startDate" class="txt_field" style="width: 80px;" value="">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('startDate');" style="cursor: hand;"> 
                            ~ 
                            <input type="text" name="endDate" class="txt_field" style="width: 80px;">
                            <img src="/plm/portal/images/icon_delete.gif" border="0" onclick="javascript:CommonUtil.deleteDateValue('endDate');"
                            style="cursor: hand;"></td>
                    </tr>
                </table>
            </form>
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="right">
                        <table border="0" cellspacing="0" cellpadding="0" width="100%">
                            <tr>
                                <td class="space5"></td>
                            </tr>
                        </table>
                        <!-- EJS TreeGrid Start -->
                        <div class="content-main">
                            <div class="container-fluid">
                                <div class="row-fluid">
                                    <div id="listGrid" style="WIDTH: 100%; HEIGHT: 100%"></div>
                                </div>
                            </div>
                        </div>
                        <!-- EJS TreeGrid Start -->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>   