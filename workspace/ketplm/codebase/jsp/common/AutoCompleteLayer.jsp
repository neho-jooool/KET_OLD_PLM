<%@ page pageEncoding="UTF-8"%>
<!-- Layer begin -->
<style type="text/css"; charset="UTF-8"></style>
<style>
 <!--
#layer0
{
	font: 0.8em arial, helvetica, sans-serif;
	background-color: #fff;
	color: #333;
	border: 1px solid gray;
	visibility:hidden;
	position:absolute;
	z-index:300;
}

#layer0content
{
	margin: 0;
	border: 0;
	padding: 0;
	overflow-x:auto;
	overflow-y:auto;
	scrollbar-highlight-color:#f4f6fb;
	scrollbar-3dlight-color:#c7d0e6;
	scrollbar-face-color:#f4f6fb;
	scrollbar-shadow-color:#f4f6fb;
	scrollbar-darkshadow-color:#c7d0e6;
	scrollbar-track-color:#f4f6fb;
	scrollbar-arrow-color:#607ddb;
}

#layer0content ul
{
	margin: 0;
	padding: 0;
	list-style-type: none;
}

#layer0content li
{
	margin-left: .2em;
	margin-right: .2em;
	margin-top: .2em;
	border: 0;
	padding: 0;
}

#layer0content a
{
	display: block;
	color: #036;
	background-color: #FFF;
	text-decoration: none;
	width:100%;
	border: 0;
	padding: 0;
	margin:0;
}

#layer0content a:hover
{
	background-color: #4682B4;
	color: #FFF;
}

#layer0footer
{
	clear: both;
	margin: 0;
	padding: 0;
	color: #333;
	background-color: #ddd;
	text-align:right;
	width:100%;
}

#layer0footer a
{
	display: block;
	color: #036;
	font-weight: normal;
	text-decoration: none;
}


 // -->
 </style>
<script language="javascript">
<!--
var lwidth = 200;
var lheight = 150;

var lxloc = 420;
var lyloc = 195;

function offLayer(layerid) {
	offLayerClear("layer0content");
	var tLayer = document.getElementById(layerid);
	tLayer.style.visibility = "hidden";
}

function onLayer(layerid, xloc, yloc) {
	onLayer(layerid, xloc, yloc, lwidth, lheight);

}

function onLayer(layerid, xloc, yloc, lw, lh) {
	var tLayer = document.getElementById(layerid);
	var tcLayer = document.getElementById(layerid+"content");
	var tfLayer = document.getElementById(layerid+"footer");

	if(lw == null || lw == 'undefined') {
		lw = lwidth;
	}

	if(lh == null || lh == 'undefined') {
		lh = lheight;
	}

	xloc = xloc+document.body.scrollLeft;
	yloc = yloc+document.body.scrollTop;

	tLayer.style.left = xloc;
	tLayer.style.top = yloc;
	tLayer.style.width = lw;
	tcLayer.style.width = lw;
	tcLayer.style.height = lh;
	tfLayer.style.width = lw;
	tLayer.style.visibility = "visible";
}

function onLayerClear(contentid) {
	/* var layerContent = document.getElementById(contentid);
	var layerUL = layerContent.all.tags("ul");

	var childLen = layerContent.children.length;
	if(childLen) {
		for(var i = 0; i < childLen; i++) {
			layerContent.removeChild(layerContent.children(i));
		}
	} */
	$('#'+contentid).html('');
}

function doUnfoldingLayer(oObj,w,h,layerid) {

	var bodyHeight = document.body.offsetHeight;
	var bodyWidth = document.body.offsetWidth;

	var rectObj = oObj.getBoundingClientRect();

	var xStartLoc = rectObj.left-1;
	var yStartLoc = rectObj.bottom;

	if( (bodyHeight - yStartLoc) < h) {
		yStartLoc = rectObj.top - oObj.offsetHeight - h - 1;
	}

	if( (bodyWidth - xStartLoc) < w) {
		xStartLoc = rectObj.left - w - 1;
	}

	onLayer(layerid, xStartLoc, yStartLoc, w, h);
}

function offLayerClear(contentid) {
	onLayerClear(contentid);

	var layerContent = document.getElementById(contentid);
	layerContent.innerHTML = "<ul><img src=\"/plm/portal/images/img_loading/ajax-loader1.gif\" hspace='84' vspace='59' border='0' width='32' height='32'></ul>";
}

function showLayerLocation(layerid){
	//var cx = window.event.clientX;
	//var cy = window.event.clientY;
	var tLayer = document.getElementById(layerid);
	tLayer.style.left = lxloc;//cx-200;
	tLayer.style.top = lyloc;//cy+10;
}
// -->
</script>
<div id="layer0" style="width:200px;">
	<div id="layer0content" style="height:150px;">
		<ul>
			<!--
			<li><a href="#">content1</a></li>
			<li><a href="#">content2</a></li>
			<li><a href="#">content3</a></li>
			<li><a href="#">content3</a></li>
			 -->
			<img src='/plm/portal/images/img_loading/ajax-loader1.gif' hspace='84' vspace='59' border='0' width='32' height='32'>
		</ul>
	</div>
	<div id="layer0footer">
		<a href="#" onclick="javascript:offLayer('layer0');">
			[close]
		<!--
			<img src='/plm/portal/images/img_common/autosearch_close.gif' border='0' hspace='2' vspace='1' width='13' height='13'>
		 -->
		</a>
	</div>
</div>
<!-- Layer end -->
