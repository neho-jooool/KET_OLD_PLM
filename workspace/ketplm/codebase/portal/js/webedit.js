//made by ys@lastcom.pe.kr

//??????
function init() {
	HtmlEditor.document.onmousedown = new Function("HtmlEditorEvent()");
	iText = HtmlEditor;
	iText.document.designMode = "On";
	HtmlEditorView.style.display="inline";
	initEditor();
	bLoad = true;

}

//???????? ???????? ????
function Edit(){
	init();
	HtmlEditorView.style.display="inline";
	if (document.all.DocContent != null) {
		HtmlEditor.document.body.innerHTML = document.all.DocContent.innerHTML;
	}
}

//???????? ?????? ????
function initEditor() {
	var bHeader = "<style>p {margin-top:0px;margin-bottom:0px;margin-left:3px;margin-right:3px}</style>";
	iText.document.open();
	iText.document.write(bHeader);
	iText.document.close();
	iText.document.body.style.fontSize = "9pt";
	iText.document.body.style.fontFamily = "????";
}

function HtmlEditorEvent(){
if (HtmlEditor.event.button==2){

	var oSource = HtmlEditor.event.srcElement ;
    if (!oSource.isTextEdit) 
        oSource = HtmlEditor.event.srcElement.parentTextEdit;

		var strValue = HtmlEditor.event.srcElement.tagName; //?????? ?????? ???? ????
    if ((strValue == "IMG" || strValue == "HR") && oSource != null) {

        var oTextRange = oSource.createTextRange();
     }

		var selectedRange = HtmlEditor.document.selection.createRange();
		var edValue = selectedRange.htmlText;

	var strX = HtmlEditor.event.x;
	var strY = HtmlEditor.event.y+180;

	if (strValue == "IMG") 
		strH = "180px";
	else if (strValue == "HR" || strValue == "TABLE")
		strH = "135px";
	else
		strH = "340px";

	var strParam = "dialogLeft:" + strX + ";dialogTop:" + strY + ";"
	strParam = strParam + "center:no;dialogWidth:150px; dialogHeight:" + strH + ";status:0;scroll:0; help:0;unadorned:yes;"
	}
}

/*???? ?? ???? */
//action event
function check(param){
	if(document.forms[0].html_check[0].checked){
		document.forms[0].content.value = HtmlEditor.document.body.innerHTML;
		if( !document.forms[0].content.value ){
			alert( "?????? ????????????!^^");
			HtmlEditor.document.body.focus();
			return;
		}
	}else{
		document.forms[0].content.value = document.forms[0].text_body.value;
			if( !document.forms[0].content.value ){
			alert( "?????? ????????????!^^");
			document.forms[0].text_body.focus();
			return;
		}
	}
	document.forms[0].method='post';
	document.forms[0].action=param;
	document.forms[0].submit();
//	document.forms[0].enctype='multipart/form-data';
}


//since 2002/03/17
function paste(param){
	HtmlEditor.focus();
	var sel = HtmlEditor.document.selection;
	if (sel!=null) {
		var rng = sel.createRange();
	    if (rng!=null)
		    rng.pasteHTML(param);
	}
}

//since 2002.4.20
function layer_change(hidden_key,view_key){
	if(hidden_key == "text_layer")
		HtmlEditor.document.body.innerHTML = document.forms[0].text_body.value;
	else
		document.forms[0].text_body.value = HtmlEditor.document.body.innerHTML;
		document.all[hidden_key].style.display = "none";
		document.all[view_key].style.display = "block";
}
/*???? ?? ???? */

function ButtonUp(param) {
	param.style.border="1px outset";
	param.style.background="#D4D4D4";
}

function ButtonOut(param) {
	param.style.border="";
	param.style.background="";

}
function MenuOver(param) {
	param.style.fontColor="white";
	param.style.backgroundColor="navy";
}

function MenuOut(param) {
	param.style.fontcolor="white";
	param.style.backgroundColor="#C0C0C0";
}


function block_style(o, cmd) {
	var ed=HtmlEditor.document.selection.createRange();
	ed.execCommand(cmd, false, o.value);
}


function SelectionCommand(Btn, cmd) {
	HtmlEditor.focus();
	var EdRange=HtmlEditor.document.selection.createRange();
	EdRange.execCommand(cmd);
}

/*
function ChFontColor(param,cmd){
	var ed
	ed = HtmlEditor.document.selection.createRange();
	ed.execCommand(cmd, false, param);
}*/
/** modified by neoburi-Inkuk*/
function ChFontColor(cmd){
	var ed
	var value = showModalDialog( "/plm/jsp/common/webedit/selcolor.jsp",
                             "",
                             "font-family:Verdana; font-size:12;dialogWidth:30em; dialogHeight:25em;status:no;help:no;self-close:no" );
	if( value != null ){
		ed = HtmlEditor.document.selection.createRange();
		ed.execCommand(cmd, true, value);
	}
	HtmlEditor.document.body.focus();
}

function OpenWin(URL) {
	var str;
	var scrWidth = (screen.availWidth / 2 ) - 200;
	var scrHeight = (screen.availHeight / 2) - 150;
	str="'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=no,copyhistory=no,";
	str=str+"width=400";
	str=str+",height=300',top="+scrHeight+",left="+scrWidth;
	wopen = window.open(URL,'remote',str);
}
