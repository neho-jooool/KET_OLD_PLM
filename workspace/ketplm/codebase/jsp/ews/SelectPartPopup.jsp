<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />

<jsp:useBean id="searchCondition" class="e3ps.common.util.KETParamMapUtil" scope="request" />
<jsp:useBean id="tgData" class="java.lang.String" scope="request"/>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session"/>

<%
    //Url Character를 UTF-8로 설정
    request.setCharacterEncoding("utf-8");

    // EJS TreeGrid Start
    response.addHeader("Cache-Control","max-age=1, must-revalidate");
    // EJS TreeGrid End
%>

<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="UTF-8">
<title>Untitled Document</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css">

<!-- EJS TreeGrid Start -->
<script src="/plm/portal/js/treegrid/GridE.js"></script>
<script src="/plm/portal/js/treegrid/Grid_KET.js"></script>
<!-- EJS TreeGrid End -->

<style type="text/css">
body {
    margin-left: 10px;
    margin-top: 5px;
    margin-right: 20px;
    margin-bottom: 5px;
}
table {border-spacing: 0;border: 0px;}
table th, table td {padding: 0}
img {vertical-align: middle;border: 0;}
input {vertical-align:middle;line-height:22px;}
</style>

<script type="text/javascript">
//<![CDATA[

    //선택된 항목 반환
    function doSelect() {

        var G = Grids[0];
        var arr = new Array();
        var subarr = new Array();//0:code, 1:name

        var R = G.GetSelRows();

        if( R.length == 0 ){
            alert("<%=messageService.getString("e3ps.message.ket_message", "01819") %><%--선택한 제품/부품이 없습니다--%>");
            return;
        }

        var idx = 0;

        if ( G != null ) {
            for(var i=0,del=0;i<R.length;i++){
                subarr = new Array();

                subarr[0] = R[i].partNo;//부품번호
                subarr[1] = R[i].partName;//부품명
                subarr[2] = R[i].dieNo;//Die No
                subarr[3] = R[i].type;//구분
                subarr[4] = R[i].proTeam;//생산처
                subarr[5] = R[i].dieProTeam;//제작처
                subarr[6] = R[i].oid;//oid
                subarr[7] = R[i].proTeamName;//생산처명

                arr[idx++] = subarr;
            }
        }

        window.returnValue = arr;
        window.close();
    }
//]]>
</script>
</head>

<body>

<form method="post">

<table style="width: 810px; height: 100%;">
<tr>
    <td height="50" valign="top">
        <table style="width: 810px;" >
        <tr>
            <td background="/plm/portal/images/logo_popupbg.png">
                <table style="height: 28px;">
                <tr>
                    <td width="7"></td>
                    <td width="20"><img src="/plm/portal/images/icon_3.png"></td>
                    <td class="font_01"><%=messageService.getString("e3ps.message.ket_message", "02558") %><%--제품/부품 검색--%></td>
                </tr>
                </table>
            </td>
            <td width="136" align="right"><img src="/plm/portal/images/logo_popup.png"></td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td valign="top">
        <table style="width: 810px;">
        <tr>
            <td width="10px">&nbsp;</td>
            <td valign="top">
                <table style="width: 790px;">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <table style="width: 790px;" >
                <tr>
                    <td align="right">
                        <table >
                        <tr>
                            <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:doSelect();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>
                            <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
			                <td width="10">&nbsp;</td>
			                <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
			                <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:self.close();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01197") %><%--닫기--%></a></td>
			                <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                      </td>
                </tr>
                </table>
                <table style="width: 790px;">
                <tr>
                    <td class="space5"></td>
                </tr>
                </table>
                <table style="width: 790px;">
                <tr>
                    <td  class="tab_btm2"></td>
                </tr>
                </table>
                <table style="width: 790px;">
                <tr>
                    <td width="100px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03114") %><%--프로젝트번호--%></td>
                    <td width="295px" class="tdwhiteL">
                        <input type="text" name="pjtNo" class="txt_fieldRO" style="width:285px" readonly value="<%=searchCondition.getString("pjtNo")%>">
                    </td>
                    <td width="100px" class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "03113") %><%--프로젝트명--%></td>
                    <td width="295px" class="tdwhiteL0">
                        <input type="text" name="pjtName" class="txt_fieldRO" style="width:285px" readonly value="<%=searchCondition.getString("pjtName")%>">
                    </td>
                </tr>
                </table>
                <table style="width: 790px;">
                <tr>
                    <td class="space15"></td>
                </tr>
                </table>
                <div class="content-main">
                    <div class="container-fluid">
                        <div class="row-fluid">
                            <div style="WIDTH:100%;HEIGHT:100%">
                                <bdo Debug="1" AlertError="0"
                                    Layout_Url="/plm/jsp/ews/SelectPartPopupGridLayout.jsp"
                                    Data_Url="/plm/jsp/common/searchGridData.jsp"
                                    Data_Method="POST"
                                    Data_Param_Result="<%=tgData %>"
                                ></bdo>
                            </div>
                        </div>
                    </div>
                </div>

                <table style="width: 790px;">
                <tr>
                    <td class="space10"></td>
                </tr>
                </table>
                
            </td>
        </tr>
        </table>
    </td>
</tr>
<tr>
    <td height="30" valign="bottom">
        <table style="width: 790px;" >
        <tr>
            <td width="10">&nbsp;</td>
            <td height="30"><iframe src="../../portal/common/copyright_p.jsp" name="copyright" width="790px" height="24" frameborder="0" marginwidth="0" marginheight="0" scrolling="no"></iframe></td>
        </tr>
        </table>
    </td>
</tr>
</table>
</form>
</body>
</html>
