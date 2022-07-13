<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService"
    class="e3ps.common.message.KETMessageService" scope="session" />
<style type="text/css">
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
</style>
<script type="text/javascript">
$(document).ready(function(){
    window.focus();
})

</script>
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td valign="top">    
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td width="20"><img src="/plm/portal/images/icon_4.png"></td>
                        <td class="font_03">${sales.stepName}</td>
                        <td align="right">
                            <table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                    <td>
                                        <table border="0" cellspacing="0" cellpadding="0">
                                            <!-- <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a
                                                            href="javascript:dqm.actionDelete();" class="btn_blue">삭제</a></td>
                                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td width="5">&nbsp;</td> -->
                                            <td width="5">&nbsp;</td>
                                            <td>
                                                <table border="0" cellspacing="0" cellpadding="0">
                                                    <tr>
                                                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                                                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                                                            <a href="javascript:window.close();" class="btn_blue">닫기</a></td>
                                                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                                                    </tr>
                                                </table>
                                            </td>
                                           </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td class="space5"></td>
                        </tr>
                    </table>
                    <table border="0" cellspacing="0" cellpadding="0" width="100%">
                        <tr>
                            <td  class="tab_btm2"></td>
                        </tr>
                    </table>
                    <form name="updateForm" method="post" enctype="multipart/form-data">
                       <table id="viewTable" border="0" cellspacing="0" cellpadding="0" width="100%" style="table-layout: fixed;">
                           <tr>
                               <td class="tdblueL">진행현황</td>
                                                      
                               <td  class="tdblueL">문제점</td>
                               <td  class="tdblueL">해결방안</td>
                               </td>
                           </tr>
                           <tr>
	                           <td valign="top" class="tdwhiteL">
	                           <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;">${sales.propelwebEditor_1}</div>
	                           </td>
			                   <td valign="top" class="tdwhiteL">
			                   <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;">${sales.problemwebEditor_1}</div>
			                   </td>
			                   <td valign="top" class="tdwhiteL">
			                   <div id="divView" style="width: 100%;overflow-x: auto; overflow-y: auto;">${sales.planwebEditor_1}</div>
			                   </td>
		                   </tr>
                      </table>
                   <iframe name="download" align="center" width="0" height="0" border="0" frameborder="0"></iframe>
           </td>
        </tr>
   </table>