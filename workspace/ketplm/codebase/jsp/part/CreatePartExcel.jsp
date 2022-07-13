<%@page contentType="application/vnd.ms-excel; charset=UTF-8" %>
<%
//타이틀 속성 변수
String title_td_height = "30";
String title_font_size = "18px";

//조회조건 속성 변수
String query_td_height = "21";

//메인데이타 속성 변수
String data_title_td_height = "21";
String data_row_td_height = "20";
%>
<%@ page import="e3ps.common.code.*,
     e3ps.common.jdf.config.*,
     e3ps.common.util.WCUtil,
     e3ps.common.web.HtmlTagUtil,
     e3ps.part.entity.KETNewPartList,
     e3ps.common.web.PageControl"%>
<%@page import="wt.lifecycle.LifeCycleTemplate,
    wt.fc.*,
    wt.lifecycle.LifeCycleHelper,
    wt.lifecycle.PhaseTemplate,
    java.util.Hashtable"%>

<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40">
<meta name=ProgId content=Excel.Sheet>
<meta name=Generator content="Microsoft Excel 10">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<style>
<!--
table{mso-displayed-decimal-separator:"\."; mso-displayed-thousand-separator:"\,";}
@page{margin:1.0in .75in 1.0in .75in;
    mso-header-margin:.5in;
    mso-footer-margin:.5in;
    mso-page-orientation:landscape;}
p, td, br, body, div, form, center, pre, blockquote { COLOR: #000000; FONT-FAMILY: "HY견고딕", Dotum, verdana, geneva, helvetica, arial, sans-serif; FONT-SIZE: 11px; line-height:11pt;}
.text_tit_c{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #1F1F1F; text-align: center; background: #B0D8FF;padding:2 4 0 4; mso-number-format:"\@";}
.text_top_tit_line{font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #1F1F1F; text-align: center;background: #CCFFCC;padding:2 4 0 4; mso-number-format:"\@"; border:.5pt hairline}
.text_tit_c_line{font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #1F1F1F; text-align: center;background: #B0D8FF;padding:2 4 0 4; mso-number-format:"\@"; border:.5pt hairline}
.text_tit_l{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #1F1F1F; text-align: left; padding:2 4 0 4; background: #FDE5DE; mso-number-format:"\@";}
.title_line{ background: #FEE8EC;}
.text_con_c{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: center;padding:0 4 0 4; mso-number-format:"\@";}
.text_con_c_b{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: center;background: #E2EBF5;padding:0 4 0 4; mso-number-format:"\@";}
.text_con_c_g{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: center;background: #FFFFCC;padding:0 4 0 4; mso-number-format:"\@";}
.text_con_l{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: left; padding:0 4 0 4; mso-number-format:"\@";}
.text_con_r{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: right; padding:0 4 0 4; mso-number-format:"* ###,###,##0"}
.text_con_r_dot1{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: right; padding:0 4 0 4; mso-number-format:"* ###,###,##0.0"}
.text_con_r_dot2{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: right; padding:0 4 0 4; mso-number-format:"* ###,###,##0.00"}
.text_con_r_dot3{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: right; padding:0 4 0 4; mso-number-format:"* ###,###,##0.000"}
.text_con_b{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: center; font-weight: bold; padding:0 4 0 4; mso-number-format:"\@";}
.text_con_d{ font-family: "HY견고딕", verdana, Dotum, geneva, helvetica, arial, sans-serif; font-size: 11px; color: #333333; text-align: center;padding:0 4 0 4; mso-number-format:"yyyy\-mm\-dd";}
-->
</style>
<!--[if gte mso 9]><xml>
    <x:ExcelWorkbook>
        <x:ExcelWorksheets>
            <x:ExcelWorksheet>
                <x:Name>Sheet1</x:Name>
                <x:WorksheetOptions>
                    <x:Print>
                        <x:ValidPrinterInfo/>
                    </x:Print>
                    <x:Selected/>
                </x:WorksheetOptions>
            </x:ExcelWorksheet>
        </x:ExcelWorksheets>
    </x:ExcelWorkbook>
</xml><![endif]-->
</head>
<%
    Hashtable partList = (Hashtable)request.getAttribute("partList");
    KETNewPartList ketNewPartList = new KETNewPartList();

%>
<body>
<table width="1000" border="1" cellpadding="0" cellspacing="0">
    <!-- 타이틀 -->
    <tr>
        <td height="<%=data_title_td_height%>" class="text_tit_c" >NO</td>
        <td class="text_tit_c">부품번호</td>
        <td class="text_tit_c">부품명</td>
        <td class="text_tit_c">원재료명</td>
        <td class="text_tit_c">고객사</td>
        <td class="text_tit_c">작성자</td>
        <td class="text_tit_c">작성일자</td>
        <td class="text_tit_c">개발구분</td>
        <td class="text_tit_c">삭제여부</td>
    </tr>
    <!-- 그리드 -->


<%
    //파일명 지정
    String fileName = new String("부품채번관리".getBytes("euc-kr"),"8859_1") + "_" + new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date(System.currentTimeMillis()));
    String convName = java.net.URLEncoder.encode(new String(fileName .getBytes("8859_1"), "euc-kr"),"UTF-8");
    response.setHeader("Content-Disposition","attachment;filename="+convName+".xls");
    response.setHeader("Content-Description","JSP Generated Data");
    response.setHeader("Content-Transfer-Encoding", "binary;");
    response.setHeader("Pragma", "no-cache;");
    response.setHeader("Expires", "-1;");


    if( partList != null && partList.size() > 0 ){
        int idx = 0;
        String style = "";
        for(int ii = 0 ; ii <  partList.size(); ii++){
            idx = partList.size()-ii;
            ketNewPartList = (KETNewPartList) partList.get(ii+"");
%>
    <tr>
        <td height="<%=data_row_td_height%>" class="text_con_r"><%=idx%></td>
        <td class="text_con_c"><%=ketNewPartList.getPartNumber()%></td>
        <td class="text_con_c"><%=ketNewPartList.getPartName()%></td>
        <td class="text_con_c"><%=ketNewPartList.getRawMaterial()%></td>
        <td class="text_con_c"><%=ketNewPartList.getCustomer()%></td>
        <td class="text_con_c"><%=ketNewPartList.getRegister()%></td>
        <td class="text_con_c"><%=ketNewPartList.getRegDate()%></td>
        <td class="text_con_c"><%=ketNewPartList.getDescription()%></td>
        <td class="text_con_c"><%=ketNewPartList.getDel()%></td>
    </tr>
    <%
        }
    %>
<%
    } else {
%>
    <tr>
        <td height="25" class="text_con_c" colspan="9">검색된 항목이 없습니다.</td>
    </tr>
<%
    }
%>
</table>
</body>
</html>
