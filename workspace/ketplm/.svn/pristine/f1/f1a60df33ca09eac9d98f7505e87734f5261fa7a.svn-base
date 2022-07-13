<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<script type="text/javascript">
<!--
    
//-->
</script>
</head>
<body>
<form name="ProjectSearch" id="ProjectSearch" action="" method="post">
<input type="hidden" name="command" />
<input type="hidden" name="perPage" value="<%=pagingSize %>" />
<input type="hidden" name="sap" />
<input type="hidden" name="planStartStartDate2" value="" />
<input type="hidden" name="planStartEndDate2" value=""/>
<input type="hidden" name="radio" value="2"/>

<table style="width: 100%;" >
        <tr>
            <td>&nbsp;</td>
            <td align="right">
                <table>
                <tr>
                    <td>
                        <table>
                        <tr>
                            <td><b><%=messageService.getString("e3ps.message.ket_message", "01248") %></b>&nbsp;&nbsp;</td>
                            <td style="width: 200px;"><input type="text" id="carTypeInfo2" name="carTypeInfo2" class="txt_field" style="width: 98%;" value=""></td>
                        </tr>
                        </table>
                    </td>
                    <td width="20">&nbsp;</td>
                    <td>
                        <table>
                        <tr>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_1.gif"></td>
                            <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                <a href="javascript:search();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "00705") %><%--검색--%></a>
                            </td>
                            <td style="width: 10px;"><img src="/plm/portal/images/btn_2.gif"></td>
                        </tr>
                        </table>
                    </td>
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
        
<table style="width: 100%; height: 100%;">
<tr>
    <td valign="top">
        <table style="width: 100%;">
        <tr>
            <td class="space5"></td>
        </tr>
        </table>
        <!-- EJS TreeGrid Start -->
        <div class="content-main">
            <div class="container-fluid">
                <div class="row-fluid">
                    <div style="WIDTH:770px; HEIGHT:550px;" id="projectDiv" name="projectDiv">

                        <bdo Debug="1" AlertError="0"
                            Layout_Url="/plm/jsp/project/ProjectSearchProdGridLayout.jsp"
                            Layout_Param_Pagingsize="<%=pagingSize %>"
                            Layout_Param_Sapauth="<%=sapAuth %>"
                            Data_Url="/plm/jsp/common/treegrid/InitGridData.jsp"
                            Data_Method="POST"
                            Data_Param_Result="<%=tgData %>"
                            Data_Param_Pagingsize="<%=pagingSize %>"
                            Export_Url="/plm/jsp/common/treegrid/ExcelExport.jsp" Export_Data="TGData" Export_Param_File="Search_Project_List"
                            Page_Url="/plm/jsp/common/treegrid/InitGridPage.jsp"
                            Page_Format="Internal"
                            Page_Data="TGData"
                            Page_Method="POST"
                        ></bdo>
                    </div>
                </div>
            </div>
        </div>
        <!-- EJS TreeGrid Start -->
    </td>
</tr>
</table>
</form>
</body>
</html>
