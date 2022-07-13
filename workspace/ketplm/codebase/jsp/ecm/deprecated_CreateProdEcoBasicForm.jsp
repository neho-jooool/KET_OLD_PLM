<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style type="text/css">
<!--
.headerLock {
  position: relative;
  top:expression(document.getElementById("div_scroll").scrollTop);
  z-index:99;
 }

.headerLock2 {
  position: relative;
  top:expression(document.getElementById("div_scroll2").scrollTop);
  z-index:99;
 }

 .headerLock3 {
  position: relative;
  top:expression(document.getElementById("div_scroll3").scrollTop);
  z-index:99;
 }
-->
</style>

<script type="text/javascript">
<!--
function poupProduct() {
	showProcessing();
	SearchUtil.selectOnePart('selectedPart','pType=P');
}
function selectedPart(objArr) {
    if(objArr.length == 0) {
        return;
    }
    var targetTable = document.getElementById("relPartTable");

    var trArr;
    var str = "";
    for(var i = 0; i < objArr.length; i++) {
        trArr = objArr[i];
        if( !partDuplicateCheck2(trArr[0]))
        {
            var tableRows = targetTable.rows.length;
            var newTr = targetTable.insertRow(tableRows);
            newTr.onmouseover=function(){relPartTable.clickedRowIndex=this.rowIndex};

            //전체선택 checkbox
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "20";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<a href=\"#\" onclick=\"javascript:$(this).parent().parent().remove();\"><b>-</b></a>"
                            + "<div style=\"display:none;\"><input name='chkSelectRelPart' type='checkbox' value=''></div>"
                            ;

            //Part No
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "85";
            newTd.className = "tdwhiteM";
            str = "";
            str += "<a href=\"javascript:viewRelPart('" + trArr[0] + "');\">" + trArr[1] + "</a>";
            str += "<input type='hidden' name='relPartOid' value='" + trArr[0] + "'>";
            str += "<input type='hidden' name='relPartLinkOid' value=''>";
            newTd.innerHTML = str;

            //Part Name
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "";
            newTd.className = "tdwhiteL";
            newTd.Title= trArr[2];
            str = "";
            str +="<font title=\""+trArr[2]+"\">";
            str += "<div class='ellipsis' style='width:120;'><nobr>";
            str += trArr[2] +"</nobr></div></font>";
            newTd.innerHTML = str;

            //Die No
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "90";
            newTd.className = "tdwhiteM";
            str = "";
            if( trArr[5] != '' )
            {
                //str += getTruncStr(trArr[5], 10);
                //str += "<input type='hidden' name='relDieNo' value='" + trArr[5] + "'>";

                if( trArr[7] > 1 )
                {
                    str += "<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:55' readonly>&nbsp;";
                    str +="&nbsp;<a href=\"javascript:searchDieNo('"+trArr[1]+"');\" onfocus=\"this.blur();\"><img src=\"/plm/portal/images/icon_5.png\" border=\"0\"></a>";
                }
                else
                {
                    str += "&nbsp;<input type='text' name='relDieNo' value='"+trArr[5]+"' class='txt_field' style='width:100%' readonly>&nbsp;";
                }
                newTd.innerHTML = str;
            }
            else
            {
                str +=  "&nbsp;"
                str += "<input type='hidden' name='relDieNo' value=''>";
                newTd.innerHTML = str;
            }

            //Rev
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "45";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = trArr[3] + "&nbsp;";

            //예상비용(천원)
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "100";
            newTd.className = "tdwhiteM";
            newTd.innerHTML = "<input type='text' name='expectCost' class='txt_fieldR' style='width:100%'>";

            //비용확보여부
            newTd = newTr.insertCell(newTr.cells.length);
            newTd.width = "112";
            newTd.className = "tdwhiteL0";
            str = "";

            var partStr = trArr[1]+"";
            subValue =  partStr.substr(0,3);

            if( (subValue == 'R40'  || subValue == 'R41' || subValue == 'R50' || subValue == 'R60' || subValue == 'R68' )
                || (subValue == 'H42'  || subValue == 'H43' || subValue == 'H45'  || subValue == 'H46' || subValue == 'H47' || subValue == 'H52' || subValue == 'H54' || subValue == 'H57' || subValue == 'H59' || subValue == 'H64' || subValue == 'H65' || subValue == 'H66' )
                || (subValue == 'K50')
                || (partStr.substr(0,2) == '68') )
            {
                str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
                str += "  <tr>";
                str += "    <td width='45' align='middle'><input type='hidden' name='budget' value='확보'><input type='hidden' name='secureBudgetYn' value='Y'></td>";
                str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
                str += "      <tr>";
                str += "      <td width='30'>&nbsp;</td>";
                //str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
                //str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'>확인</a></td>";
                //str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
                str += "      </tr>";
                str += "    </table></td>";
                str += "  </tr>";
                str += "</table>";
            }
            else
            {
                str += "<table width='100%' border='0' cellspacing='0' cellpadding='0'>";
                str += "  <tr>";
                str += "    <td width='45' align='middle'><input type='text' name='budget' value='미확보' class='txt_field' style='width:45' readonly><input type='hidden' name='secureBudgetYn' value='N'></td>";
                str += "    <td align='right' width=''><table border='0' cellspacing='0' cellpadding='0'>";
                str += "      <tr>";
                str += "        <td width='10'><img src='/plm/portal/images/btn_1.gif'></td>";
                str += "        <td class='btn_blue' background='/plm/portal/images/btn_bg1.gif'><a href='javascript:checkBudget();' class='btn_blue'><%=messageService.getString("e3ps.message.ket_message", "03226") %><%--확인--%></a></td>";
                str += "        <td width='10'><img src='/plm/portal/images/btn_2.gif'></td>";
                str += "      </tr>";
                str += "    </table></td>";
                str += "  </tr>";
                str += "</table>";
            }

            newTd.innerHTML = str;
        }
    }
    
    hideProcessing();
}

<% /* 비용확보 - 확인 */ %>
function checkBudget()
{
    var form = document.forms[0];
    var pos = relPartTable.clickedRowIndex;
    var targetTable = document.getElementById("relPartTable");
    var tableRows = targetTable.rows.length;

    
    pos -= 1;
    tableRows -= 1;
    
    
    var division  = "";
    var dev_yn = "";
    var dieno = "";
    var expectCost = "";

    if( '<%=userGroupStr%>' == '자동차사업부' )
    {
        division = "1";
    }
    else
    {
        division = "2";
    }

    var dev_yn = document.all("dev_yn");
    var str_dev_yn = '';
    for( var inx=0; inx < dev_yn.length ; inx++)
    {
        if( dev_yn[inx].checked )
        {
            str_dev_yn  = dev_yn[inx].value;
        }
    }

    if( str_dev_yn == "D" )
    {
        dev_yn = "1";
    }
    else
    {
        dev_yn = "2";
    }

    if( tableRows > 1 )
    {
        dieno = form.relDieNo[pos].value;
        expectCost = form.expectCost[pos].value;
    }
    else
    {
        dieno = form.relDieNo.value;
        expectCost = form.expectCost.value;
    }

    if( dev_yn == "2" && isNumber(expectCost) ||  dev_yn == "1" && isNumber(expectCost) && dieno != ''  )
    {
        
    	showProcessing();
    	document.forms[0].target="setCarData";
        document.forms[0].action="/plm/jsp/ecm/BudgetInterfaceCheck.jsp?devYn="+dev_yn+"&division="+division+"&dieNo="+dieno+"&cost="+expectCost+"&rowInx="+pos;
        document.forms[0].submit();
        
    }
    else
    {
        if( dev_yn == "1"  && dieno == ''  )
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "00906") %><%--관련 Die No가 존재하지 않아서 예산을 확인할 수 없습니다--%>");
        }
        else if( !isNumber(expectCost)  )
        {
            alert("<%=messageService.getString("e3ps.message.ket_message", "01648") %><%--비용이 존재하지 않아서 예산을 확인할 수 없습니다--%>");
            if( tableRows > 1 )
            {
                form.expectCost[pos].focus();
            }
            else
            {
                form.expectCost.focus();
            }
        }
        return;
    }

}

<% /* Feedback - 비용확보 - 확인 */ %>
function setBudgetYn( budget_yn, row_inx, msg )
{
	//alert('setBudgetYn( '+ budget_yn +', '+ row_inx +', '+ msg +' )');
    var budget = document.getElementsByName("budget");
    if(budget == null || typeof budget == 'undefined') return;
    
    var budgetLength = budget.length;
    if( budgetLength > 1 )
    {
        if( budget_yn == 'N' )
        {
            document.forms[0].budget[row_inx].value = '미확보';
        }
        else if( budget_yn =='Y')
        {
            document.forms[0].budget[row_inx].value = '확보';
        }

        document.forms[0].secureBudgetYn[row_inx].value = budget_yn;

        if( msg != '')
        {
            alert(msg);
        }
    }
    else
    {
        if( budget_yn == 'N' )
        {
            document.forms[0].budget.value = '미확보';
        }
        else if( budget_yn =='Y')
        {
            document.forms[0].budget.value = '확보';
        }
        document.forms[0].secureBudgetYn.value = budget_yn;

        if( msg != '')
        {
            alert(msg);
        }
    }
    
    
    hideProcessing();
}

<% /* deprecated */ %>
function partDuplicateCheck(poid) {
//부품추가시 선택된 부품정보를 중복체크한다.
    var tBody = document.getElementById("relPartTable");
    var rowsLen = tBody.rows.length;
    if(rowsLen > 0) {
        var primaryPart = document.forms[0].relPartOid;
        var partLen = primaryPart.length;
        if(partLen > 0 ) {
            for(var i = 0; i < partLen; i++) {
                if(primaryPart[i].value == poid) {
                    return true;
                }
            }
        } else {
            if(primaryPart.value == poid) {
                return true;
            }
        }
    }
    return false;
}

function partDuplicateCheck2( poid )
{
  var isDuplicate = false;

  var list = document.getElementsByName("relPartOid");
  for( var cnt=0; cnt < list.length; cnt++ )
  {
    if( list[cnt].value == poid )
      isDuplicate = true;
  }

  return isDuplicate;
}

<% /* deprecated */ %>
function deletePartLine() {
//부품삭제버튼 클릭시 선택된 부품정보를 삭제한다.
    var body = document.getElementById("iPartTable");
    if (body.rows.length == 0) return;
    var part_checks = document.forms[0].iPartChk;

    if (body.rows.length == 1) {
        if (part_checks[0]=="[object]"){
            if (part_checks[0].checked){
                body.deleteRow(0);
            }
        }else{
            if (part_checks.checked){
                body.deleteRow(0);
            }
        }
    } else {
        for (var i = body.rows.length; i > 0; i--) {
            if (part_checks[i-1].checked) body.deleteRow(i - 1);
        }
    }
}

//-->
</script>

<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <tr>
    <td  class="tab_btm2"></td>
  </tr>
</table>
<table border="0" cellspacing="0" cellpadding="0" width="100%">
  <!-- col width="110"><col width="248"><col width="90"><col width="220" -->
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02524") %><%--제목--%><span class="red">*</span></td>
    <td colspan="3" class="tdwhiteL0"><input type="text" name="eco_name" class="txt_field"  style="width:100%" id="textfield" ></td>
  </tr>
  <tr>
    <td style="width:15%;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01193") %><%--단계구분--%><span class="red">*</span></td>
    <td style="width:35%;" class="tdwhiteL">
    <%
    Hashtable<String, String> devFlag = new Hashtable<String, String>();
    for( int devCnt=0; devCnt < devFlagList.size(); devCnt++ )
    {
      devFlag = devFlagList.get(devCnt);
    %>
      <input name="dev_yn" type="radio" class="Checkbox" value='<%=devFlag.get("code") %>'  id ="dev_yn"><%=devFlag.get("name") %>&nbsp;
    <%
    }
    %>
    </td>
    <td style="width:15%;" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01663") %><%--사업부구분--%><span class="red">*</span></td>
    <td style="width:35%;" class="tdwhiteL0">
      <select name="div_flag" class="fm_jmp" style="width:100%"></select>
    </td>
  </tr>
  <tr>
    <td class="tdblueL">Project No</td>
    <td class="tdwhiteL">
      <input type='hidden' name='project_oid'>
      <input type="text" name="project_id" class="txt_fieldRO" style="width:70%" id="textfield2" readonly>
      &nbsp;<a href="javascript:popupProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_5.png" border="0"></a>&nbsp;<a href="javascript:delProject();" onfocus="this.blur();"><img src="/plm/portal/images/icon_delete.gif" border="0"></a>&nbsp;&nbsp;
    </td>
    <td class="tdblueL">Project Name</td>
    <td class="tdwhiteL0">
      <input type="text" name="project_name" class="txt_fieldRO" style="width:100%" id="textfield3" readOnly>
    </td>
  </tr>
  <%
  if( userGroupStr.equals("자동차사업부") )
  {
  %>
    <tr>
      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00837") %><%--고객사--%></td>
      <td class="tdwhiteL">
        <select name="domestic_yn" class="fm_jmp" style="width:49%" onchange="javascript:searchCarData('maker', this.value, '');"></select>
        <select name="car_maker"  id='car_maker' class="fm_jmp" style="width:49%" onchange="javascript:searchCarData('car', this.value, '');" >
          <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
        </select>
      </td>
      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02745") %><%--차종--%></td>
      <td class="tdwhiteL0">
        <select name="car_category" class="fm_jmp" style="width:100%">
          <option value=""><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></option>
        </select>
      </td>
    </tr>
  <%
  }
  else
  {
  %>
    <input type="hidden" name="domestic_yn">
    <input type="hidden" name="car_maker">
    <input type="hidden" name="car_category">
  <%
  }
  %>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02121") %><%--연계 ECR 정보--%></td>
    <td colspan="3" class="tdwhiteL0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="610" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupRelEcr();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif">
                        <a href="javascript:deleteDataLine('forms[0]', 'relEcrTable', 'chkSelectRelEcr', 'chkAllRelEcr', 'deleteRelEcrList' );" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a>
                      </td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      
      <div id="div_scroll" style="width=610;height=71;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0" id="relEcrTable">
        <tr class="">
              
                          <td width="20" class="tdgrayM"><a href="javascript:popupRelEcr();"><b>+</b></a></td>
                          <td width="110" class="tdgrayM">ECR No</td>
                          <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "00209") %><%--ECR 제목--%></td>
                          <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02425") %><%--작성부서--%></td>
                          <td width="140" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02431") %><%--작성자--%></td>
                          <td width="115" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01999") %><%--승인일자--%></td>

        </tr>
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
      </td>
    </tr>
    <tr>
      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02536") %><%--제품--%><span class="red">*</span></td>
      <td colspan="3" class="tdwhiteL0">
        <!-- table border="0" cellspacing="0" cellpadding="0" width="610">
          <tr>
            <td class="space5"></td>
          </tr>
        </table>
        <table width="610" border="0" cellspacing="0" cellpadding="0" >
          <tr>
            <td>&nbsp;</td>
            <td align="right">
              <table border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:popupPart('selectedPart');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                  <td width="5">&nbsp;</td>
                  <td>
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                        <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'relPartTable', 'chkSelectRelPart', 'chkAllRelPart', 'deleteRelPartList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                        <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                      </tr>
                    </table>
                  </td>
                </tr>
              </table>
            </td>
          </tr>
        </table>
        <table border="0" cellspacing="0" cellpadding="0" width="610">
          <tr>
            <td class="space5"></td>
          </tr>
        </table>
        
        <div id="div_scroll2" style="width=610;height=81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
        <table width="100%" cellpadding="0" cellspacing="0" id="relPartTable">
          <tr class="">

                        <td width="20" class="tdgrayM"><a href="javascript:poupProduct();"><b>+</b></a></td>
                        <td width="85" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%></td>
                        <td width="" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%></td>
                        <td width="90" class="tdgrayM">Die No</td>
                        <td width="45" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "01481") %><%--버전--%></td>
                        <td width="100" class="tdgrayM"><%=messageService.getString("e3ps.message.ket_message", "02150") %><%--예상비용(천원)--%></td>
                        <td width="112" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "01652") %><%--비용확보--%><span class="red">*</span></td>

          </tr>
        </table>
        <!-- /div>
        <table border="0" cellspacing="0" cellpadding="0" width="610">
          <tr>
            <td class="space5"></td>
          </tr>
        </table -->
      </td>
    </tr>
    <tr>
      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "01854") %><%--설계변경사유--%><span class="red">*</span></td>
      <td colspan="3" class="tdwhiteL0">
        <table width="610" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <%
          Hashtable<String, String> chgRs = new Hashtable<String, String>();
          int chgR = 0;
          for ( int chgRCnt=0; chgRCnt < chgReason.size(); chgRCnt++ ) {
            chgR++;
            chgRs = chgReason.get(chgRCnt);
            if ( !chgRs.get("code").equals("REASON_6") ) {
          %>
              <td width="150"><input type="checkbox" name="chk_chg_reason" value='<%=chgRs.get("code") %>'><%=chgRs.get("name") %></td>
          <%
            }
            else {
              chgR = chgR -1;
            }
            
            if ( chgRCnt > 0 && (chgR % 4) == 0 ) {
          %>
          </tr>
          <tr>
          <%
            }
          } // for ( int chgRCnt=0; chgRCnt < chgReason.size(); chgRCnt++ ) {
          %>
          </tr>
          <tr>
          <%
          chgRs = chgReason.get(chgReason.size()-1);
          %>
            <td colspan="4">
              <input type="checkbox" name="chk_chg_reason" value='<%=chgRs.get("code") %>' onclick="javascript:disable( 'other_reason', this.checked );"><%=chgRs.get("name") %>&nbsp;&nbsp;
              <input type="text" name="other_reason" class="txt_field" style="width:550" >
            </td>
          </tr>
        </table>
      </td>
    </tr>
    <tr>
      <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00865") %><%--고객확인구분--%></td>
      <td colspan="3" class="tdwhiteL0">
        <table width="610" border="0" cellspacing="0" cellpadding="0" style=table-layout:fixed >
          <tr>
          <%
          Hashtable<String, String> custFlag = new Hashtable<String, String>();
          for( int custCnt=0; custCnt < custChkList.size(); custCnt++ )
          {
            custFlag = custChkList.get(custCnt);
            if( custCnt == custChkList.size() -1 )
            {
          %>
              <td colspan="3"><input type="checkbox" name="chk_cust" id="checkbox15" value='<%=custFlag.get("code") %>'  onclick="javascript:disable( 'other_cust_flag', this.checked );"><%=custFlag.get("name") %>&nbsp;&nbsp;<input type="text" name="other_cust_flag" class="txt_field" style="width:253" id="textfield3">
          <%
            }
            else
            {
          %>
              <td><input type="checkbox" name="chk_cust" id="checkbox15" value='<%=custFlag.get("code") %>' ><%=custFlag.get("name") %></td>
          <%
            }
          %>
          <%
          }
          %>
          </tr>
          <tr>
            <td width="100"></td><td width="100"></td><td width="100"></td><td width="100"></td><td width="100"></td><td width="100"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02647") %><%--조정 및 변경사항--%></td>
    <td colspan="3" class="tdwhiteL0">
      <table width="610" border="0" cellspacing="0" cellpadding="0" style=table-layout:fixed >
        <tr>
        <%
        Hashtable<String, String> chgFact = new Hashtable<String, String>();
        for( int chgFCnt=0; chgFCnt < chgFactList.size() ; chgFCnt++)
        {
          chgFact = chgFactList.get(chgFCnt);
        %>
          <td width="100"><input type="checkbox" name="chk_chg_fact" id="checkbox"  value='<%=chgFact.get("code") %>'><%=chgFact.get("name") %></td>
        <%
        }
        %>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "00134") %><%--CU도면{0} 변경여부--%></td>
    <td colspan="3" class="tdwhiteL0">
      <input name="chk_cu" type="radio" class="Checkbox" value="Y">Yes&nbsp;<input name="chk_cu" type="radio" class="Checkbox" value="N" checked> No
    </td>
  </tr>
  <tr>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02469") %><%--적용시기--%></td>
    <td class="tdwhiteL">
      <select name="effective_date" class="fm_jmp" style="width:100%"></select>
    </td>
    <td class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02445") %><%--재고처리--%></td>
    <td class="tdwhiteL0">
      <select name="inv_process" class="fm_jmp" style="width:100%"></select>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04127") %><%--현상--%></td>
    <td colspan="2" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "04128") %><%--문제점 및 요구사항--%></td>
  </tr>     
  <tr>
  
	<!-- 이노디터 JS Include Start -->
	<script type="text/javascript">
	    //<![CDATA[
	
	    // -- editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
	    // -- 페이지별로 editor의 갯수를 설정하고자 하는 경우는 이 부분에서 설정
	    // -- 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 customize.js 에서 설정하고 아래부분은 제거할 것
	    var g_arrSetEditorArea = new Array();
	    g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
	    g_arrSetEditorArea[1] = "EDITOR_AREA_CONTAINER1";// 이노디터를 위치시킬 영역의 ID값 설정
	
	    //]]>
	</script>
	<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize.js"></script>
	<script type="text/javascript" src="/plm/portal/innoditor_u/js/customize_ui.js"></script>
	<script type="text/javascript">
	    //<![CDATA[
	
	    // -- 재정의 영역 : customize.js 또는 customize_ui.js 에서 선언된 설정값을 페이지별로 재정의 하고자 하는 경우
	
	    // Skin 재정의
	    //g_nSkinNumber = 0;
	
	    // 크기, 높이 재정의
	    g_nEditorWidth = 460;
	    g_nEditorHeight = 200;
	
	    //g_bCustomEditorWidthPercentageYN = true;
	
	//]]>
	</script>
	<script type="text/javascript" src="/plm/portal/innoditor_u/js/loadlayer.js"></script>
	
	<script language=javascript>
	<% /* @deprecated */ %>
	/* 
	function fnCustomerFunction_1(EditNumber)
	{
	    //alert("사용자 정의 툴바버튼 1");
	    var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
	    object.style.position = "absolute";
	    object.style.top = "100px";
	    object.style.left = "60px";
	    object.style.zIndex = 9999;
	    //object.style.width = 1000;
	    //object.style.height = 660;
	    
	    fnResizeEditor(EditNumber, 900, 600);
	    
	}
	*/
	 
	function fnCustomerFunction_2(EditNumber)
	{
	    //alert("사용자 정의 툴바버튼 2");
	    fnResizeEditor(0, 460, 600);
	    fnResizeEditor(1, 460, 600);
	}
	
	function fnCustomerFunction_3(EditNumber)
	{
	    //alert("사용자 정의 툴바버튼 3");
	    /* 
	    var object = document.getElementById("EDITOR_AREA_CONTAINER"+ ((EditNumber == 0) ? "" : EditNumber));
	    object.style.position = "";
	    */
	     
	    fnResizeEditor(0, 460, 200);
	    fnResizeEditor(1, 460, 200);
	}
	</script>
	
	<!-- 이노디터 JS Include End -->
  
    <td colspan="2" class="tdwhiteL">
    
    
                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                  <textarea name="webEditor" rows="0" cols="0" style="display: none"></textarea> 
                  <textarea name="webEditorText" rows="0" cols="0" style="display: none"></textarea> 
                  <!-- Editor Area Container : Start -->
                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                  <div id="EDITOR_AREA_CONTAINER"></div> 
                  <!-- Editor Area Container : End -->
                  
                  
    </td>
    <td colspan="2" class="tdwhiteL0">
    
    
                  <!-- 이노디터에서 작성된 내용을 보낼 Form Start -->
                  <textarea name="webEditor1" rows="0" cols="0" style="display: none"></textarea> 
                  <textarea name="webEditorText1" rows="0" cols="0" style="display: none"></textarea> 
                  <!-- Editor Area Container : Start -->
                  <!-- 이노디터에서 작성된 내용을 보낼 Form End --> 
                  <div id="EDITOR_AREA_CONTAINER1"></div> 
                  <!-- Editor Area Container : End -->
                  
                  
    </td>            
  </tr>     
  <tr>
    <td width="130" class="tdblueL"><%=messageService.getString("e3ps.message.ket_message", "02796") %><%--첨부파일--%></td>
    <td colspan="3" class="tdwhiteL0">
      <!-- table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      <table width="610" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>&nbsp;</td>
          <td align="right">
            <table border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:insertFileLine();" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "02861") %><%--추가--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
                <td width="5">&nbsp;</td>
                <td>
                  <table border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="10"><img src="/plm/portal/images/btn_1.gif"></td>
                      <td class="btn_blue" background="/plm/portal/images/btn_bg1.gif"><a href="javascript:deleteDataLine('forms[0]', 'fileTable', 'fileSelect', 'chkFileAll', 'deleteFileList');" class="btn_blue" onfocus="this.blur();"><%=messageService.getString("e3ps.message.ket_message", "01690") %><%--삭제--%></a></td>
                      <td width="10"><img src="/plm/portal/images/btn_2.gif"></td>
                    </tr>
                  </table>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table>
      
      <div id="div_scroll3" style="width=610;height=81;overflow-x:hidden;overflow-y:auto;" class="table_border"  -->
      <table width="100%" cellpadding="0" cellspacing="0" id="fileTable">

                    <td width="20" class="tdgrayM"><a href="#" onclick="javascript:insertFileLine();"><b>+</b></a></td>
                    <td width="" class="tdgrayM0"><%=messageService.getString("e3ps.message.ket_message", "02961") %><%--파일명--%></td>
   
      </table>
      <!-- /div>
      <table border="0" cellspacing="0" cellpadding="0" width="610">
        <tr>
          <td class="space5"></td>
        </tr>
      </table -->
    </td>
  </tr>
</table>
