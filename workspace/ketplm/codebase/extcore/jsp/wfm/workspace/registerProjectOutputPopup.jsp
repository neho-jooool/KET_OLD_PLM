<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />
<script src="/plm/portal/js/viewObject.js"></script>
<script language="javascript" src="/plm/portal/js/checkbox2.js"></script>
<script src="/plm/portal/js/ajax.js"></script>
<script type="text/javascript">
<!--
function doCreateOutput(){
    
    var tasksize = ${tasksize};
    var locflag = true;
    var isResister = false;
    var checkedFlag = false;
    var objTypeFlag = true;
    var validateArr = new Array();
    var oidArr = new Array();
    for(var i=0; i < tasksize; i++){
        $('input[name=LID'+i+']:checked').each(function() {
          checkedFlag = true;
          var loc = $(this).attr('location');
          if($(this).attr('isRegister') == 'true') isResister = true;
          if($(this).attr('objType') != '문서') objTypeFlag = false;
          var oid = $(this).val();
          validateArr[validateArr.length] = loc;
          oidArr[oidArr.length] = oid;
          for(var y=0; y < validateArr.length; y++){
              var validate = validateArr[y];
              if(validate != loc) locflag = false;
          }
       });
    }
    if(!checkedFlag){
        alert('<%=messageService.getString("e3ps.message.ket_message", "05118")%>');/* 산출물을 선택하여 주십시오 */
        return;
    }
    else if(isResister){
        alert('<%=messageService.getString("e3ps.message.ket_message", "05119")%>');/* 산출물이 이미 등록되어 있습니다 */
        return;
    }
    else if(!objTypeFlag){
        alert('<%=messageService.getString("e3ps.message.ket_message", "05158")%>');/* 동일 산출물 등록은 산출물 유형이 문서인 경우에만 가능합니다. */
        return;
    }
    else{
        if(locflag){
            var url = "/plm/jsp/dms/CreateDocument.jsp";
            var params = "?"; //?projectOid=&isPop=Y&security=";
            for ( var i = 0; i < oidArr.length; i++) {
                var oid = oidArr[i];
                params += 'projectOid='+oid+'&';
            }
            getOpenWindow2(url+params, 'CreateProjectOutputPopup',800,800);
        }
        else{
            alert("<%=messageService.getString("e3ps.message.ket_message", "05120")%>");/* 문서분류가 동일하지 않습니다.\n 동일 산출물 등록 시 문서분류가 동일한 산출물만 등록 가능합니다. */
            return;
        }
    }
}

function doLinkOutput(){

    var tasksize = ${tasksize};
    var locflag = true;
    var isResister = false;
    var checkedFlag = false;
    var objTypeFlag = true;
    var validateArr = new Array();
    var oidArr = new Array();
    for(var i=0; i < tasksize; i++){
        $('input[name=LID'+i+']:checked').each(function() {
          checkedFlag = true;
          var loc = $(this).attr('location');
          if($(this).attr('isRegister') == 'true') isResister = true;
          if($(this).attr('objType') != '문서') objTypeFlag = false;
          var oid = $(this).val();
          validateArr[validateArr.length] = loc;
          oidArr[oidArr.length] = oid;
          for(var y=0; y < validateArr.length; y++){
              var validate = validateArr[y];
              if(validate != loc) locflag = false;
          }
       });
    }
    if(!checkedFlag){
        alert('<%=messageService.getString("e3ps.message.ket_message", "05118")%>');/* 산출물을 선택하여 주십시오 */
        return;
    }
    else if(isResister){
        alert('<%=messageService.getString("e3ps.message.ket_message", "05119")%>');/* 산출물이 이미 등록되어 있습니다 */
        return;
    }
    else if(!objTypeFlag){
        alert('<%=messageService.getString("e3ps.message.ket_message", "05158")%>');/* 동일 산출물 등록은 산출물 유형이 문서인 경우에만 가능합니다. */
        return;
    }
    else{
        if(locflag){
            var url = "/plm/ext/wfm/workspace/linkProjectDocumentPopup.do";
            var params = "?";
            for ( var i = 0; i < oidArr.length; i++) {
                var oid = oidArr[i];
                params += 'outputoid=' + oid + '&';
            }
            getOpenWindow2(url+params, 'LinkProjectDocumentPopup',1000,800);
        }
        else{
            alert("<%=messageService.getString("e3ps.message.ket_message", "05120")%>");/* 문서분류가 동일하지 않습니다.\n 동일 산출물 등록 시 문서분류가 동일한 산출물만 등록 가능합니다. */
            return;
        }
    }
}

function deleteOutput(opid){
  if ( !confirm("<%=messageService.getString("e3ps.message.ket_message", "01918") %>") ) {/* 산출물을 삭제 합니다.\n등록된 산출물의 경우 프로젝트에서는 삭제되지만,\n문서관리에서 남아 있습니다.\n삭제하시겠습니까? */
    return;
  }
  $.ajax({
      url : "/plm/ext/wfm/workspace/deleteProjectOutput.do",
      type : "POST",
      data : {
          outputoid : opid
      },
      dataType : 'json',
      async : false,
      success : function(flag) {
          if(flag){
              alert('<%=messageService.getString("e3ps.message.ket_message", "05121")%>');/* 정상적으로 삭제되었습니다. */
              document.location.reload();
          }
          else{
              alert('에러.');
          }
      }
  });
}

//-->
</script>
<body class="popup-background body-space">
  <form>
    <div class="contents-wrap">
      <div class="sub-title b-space20">
        <img src="/plm/portal/images/icon_3.png" /><%=messageService.getString("e3ps.message.ket_message", "05122")%><!-- 동일 산출물 등록 -->
      </div>
      <div class="float-r" style="text-align: right">
        <span class="in-block v-middle r-space7"> <span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
              href="javascript:doCreateOutput();" class="btn_blue"
            ><%=messageService.getString("e3ps.message.ket_message", "02722")%><%--직접작성--%></a></span><span class="pro-cell b-right"></span></span></span> <span class="in-block v-middle r-space7"><span
          class="pro-table"
        ><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a href="javascript:doLinkOutput();" class="btn_blue"><%=messageService.getString("e3ps.message.ket_message", "01352")%><%--링크등록--%></a></span><span
            class="pro-cell b-right"
          ></span></span></span> <span class="in-block v-middle r-space7"><span class="pro-table"><span class="pro-cell b-left"></span><span class="pro-cell b-center"> <a
              href="javascript:self.close();" class="btn_blue"
            ><%=messageService.getString("e3ps.message.ket_message", "01197")%><%--닫기--%></a></span><span class="pro-cell b-right"></span></span></span>
      </div>
      <div class="sub-title-02 b-space40 t-space30">
        <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span><%=messageService.getString("e3ps.message.ket_message", "00482")%><!-- Task 명 -->
        : ${taskname}
      </div>
      <c:forEach items="${tasks}" var="task" varStatus="i">
        <div class="sub-title-02 b-space15 t-space30">
          <span class="r-space9"><img src="/plm/portal/images/icon_4.png" /></span>${task.pjtNo} : ${task.pjtName}
        </div>
        <div class="b-space40">
          <table cellpadding="0" class="table-style-01" summary="">
            <colgroup>
              <col width="3%" />
              <col width="15%" />
              <col width="8%" />
              <col width="16%" />
              <col width="8%" />
              <col width="8%" />
              <col width="8%" />
              <col width="8%" />
              <col width="5%" />
              <col width="5%" />
              <col width="4%" />
            </colgroup>
            <thead>
              <tr>
                <td><input type="checkbox" name="allCheck${i.index}" onclick="selectAll(document.forms[0].allCheck${i.index}, document.forms[0].LID${i.index})" /></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "01433")%><!-- 문서제목 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "02918")%><!-- 타입 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "01424")%><!-- 문서분류 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "02856")%><!-- 최종작성자 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "01481")%><!-- 버전 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "01760")%><!-- 상태 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "02428")%><!-- 작성일 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "02957")%><!-- 파일 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "02730")%><!-- 진행률 --></td>
                <td><%=messageService.getString("e3ps.message.ket_message", "01690")%><!-- 삭제 --></td>
                <td>Template</td>
              </tr>
            </thead>
            <tbody>
              <c:choose>
                <c:when test="${empty task.outputs}">
                  <tr>
                    <td class="tdwhiteM" colspan="12"><%=messageService.getString("e3ps.message.ket_message", "05123")%><!-- 정의된 산출물이 없습니다. --></td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <c:forEach items="${task.outputs}" var="output">
                    <c:choose>
                      <c:when test="${output.creatorName == '&nbsp;'}">
                        <c:set var="isRegister">false</c:set>
                      </c:when>
                      <c:otherwise>
                        <c:set var="isRegister">true</c:set>
                      </c:otherwise>
                    </c:choose>
                    <tr>
                      <td class="center"><input type="checkbox" name="LID${i.index}" id="LID${i.index}" value="${output.outputOid}" location="${output.location }" isRegister="${isRegister }" objType="${output.objType }" /></td>
                      <td class="center">${output.outputName }</td>
                      <td class="center">${output.objType }</td>
                      <td class="center">${output.location }</td>
                      <td class="center">${output.creatorName }</td>
                      <td class="center">${output.version }</td>
                      <td class="center">${output.state }</td>
                      <td class="center">${output.createDate }</td>
                      <td class="center">${output.fileIconUrl }</td>
                      <td class="center">${output.completion }</td>
                      <td class="center">${output.deleteIconUrl }</td>
                      <td class="center">${output.templateLinkUrl }</td>
                    </tr>
                  </c:forEach>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>
      </c:forEach>
    </div>
  </form>
</body>
