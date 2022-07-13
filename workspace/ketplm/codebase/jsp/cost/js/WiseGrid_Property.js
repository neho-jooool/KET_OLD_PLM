function setProperty(GridObj)
{
	GridObj.strHDFontName = "µ¸¿ò";
	GridObj.strCellFontName = "µ¸¿ò";
	// Cell Font Setting
	GridObj.nCellFontSize = 10;

	// Header Font Setting
	GridObj.nHDFontSize = 10;
	GridObj.bHDFontBold = true;
  GridObj.nHDLines = 3;  
	GridObj.bMultiRowMenuVisible = true;
  
	// Header Color
	GridObj.strHDBgColor = "242|242|242";
	GridObj.strHDFgColor="100|100|100";

	// Cell Color
	GridObj.strGridBgColor="255|255|255";
	GridObj.strCellBgColor="255|255|255";
	GridObj.strCellFgColor="51|51|51";

	// Border Style
	GridObj.strGridBorderColor = "204|204|204";
	GridObj.strGridBorderStyle = "solidline";
	GridObj.strHDBorderStyle = "solidline";
	GridObj.strCellBorderStyle = "solidline";

	// ETC Color
//	GridObj.strActiveRowBgColor="214|228|236";
	GridObj.strSelectedCellBgColor = "241|231|221";
	GridObj.strSelectedCellFgColor = "51|51|51";
	GridObj.strStatusbarBgColor = "243|243|243";
	GridObj.strStatusbarFgColor = "101|101|101";
	GridObj.strProgressbarColor = "0|126|174"; 

	// ETC
	GridObj.bRowSelectorVisible = false;
	GridObj.bHDSwapping = true;
	GridObj.nAlphaLevel = 0;
	GridObj.nRowHeight = 22;
	GridObj.SetHelpInfo();
	GridObj.bAbortQueryVisible = true;
}

function btn(a,b){
	document.write ("<table cellpadding=0 cellspacing=0 border=0 height=23 onClick="+a+" style=cursor:hand><tr><td width=15 class=btn_left>");
	document.write ("<img src=../images/blank.gif width=15 height=1></td>");
	document.write ("<td class=btn_txt valign=middle>"+b+"</td><td width=4 class=btn_right><img src=../images/blank.gif width=4></td></tr></table>");
}

var Mcolor = "222|227|242"; //Mandatory
var Ocolor = "223|241|230"; //Optionally
var Rcolor = "255|255|255"; //ReadOnly
var Scolor = "249|247|183"; //Summary Background Color
var Lcolor = "0|0|255"; //Link Color

var nfCaseAmt = "#,##0";
var nfCaseAmtFor = "#,##0.00";
var nfCaseQty = "#,##0.000";
var nfCasePrice = "#,##0.00000";