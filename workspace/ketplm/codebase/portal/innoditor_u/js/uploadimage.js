/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// uploadImage.js -	image 파일 Upload 관련
//					
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


// 변수 선언
var g_objUploadObject = null;
var g_objLoadURLObject = null;

var g_objUploadObjectProp = new Object();
var g_objLoadURLObjectProp = new Object();

var g_strUploadImageURL = "";

var g_nSelectedTabIndex = 1;

var g_strImageDirPath = "./image/editwin/ko/";


// 이미지 최대크기 사이즈 설정
// 이미지 최대 가로크기를 기준으로 동일비율로 높이를 계산하여 설정함으로 최대높이 설정은 없음
// 이미지 최대크기는 기본적으로 매우 큰 값으로 설정하였으며, 고객사의 적용 방식에 따라 사이즈 수정
var g_nMaxWidth = 10000;


function fnAdjustPage()
{
	try
	{
		var objFile = document.getElementById("fileUpload");

		if(window.attachEvent)
		{
			objFile.onpropertychange = fnChangeImage;
			objFile.style.display = "none";

			setTimeout("fnAdjustFilePosition()", 100);
		}
		else
		{
			objFile.onchange = fnChangeImage;
			//objFile.style.left = "-180px";
		}
	}
	catch(e)
	{
	}
}

function fnAdjustFilePosition()
{
	var objFile = document.getElementById("fileUpload");
	objFile.style.display = "";
}

function fnChangeImage()
{
	try
	{
		var objFileValue = document.getElementById("fileUpload").value;
		fnLoadImage(objFileValue, 1, true, true);
	}
	catch(e)
	{
	}
}

function fnLoadImage(strUploadImageURL, nLoadType, bUpdateWH, bUpload)
{
	try
	{
		g_strUploadImageURL = "";
		if(("" == strUploadImageURL) || ("undefined" == strUploadImageURL)) return;

		g_strUploadImageURL = strUploadImageURL;

		if(1 == nLoadType)
		{
			if(g_browserCHK.ie)
			{
				if(g_browserCHK.ie6)
				{
					setTimeout("fnLoadImageInfo(" + nLoadType + "," + bUpdateWH + "," + bUpload + ")", 100);
				}
				else
				{
					var nFilePathLength = strUploadImageURL.length;
					var nFileNameIndex = strUploadImageURL.lastIndexOf("\\");
					var strFileName = strUploadImageURL.substr(nFileNameIndex+1, nFilePathLength-nFileNameIndex);

					var strFileInfoHTML = fnFileInfoHTML(strFileName);

					document.getElementById("img_main").innerHTML = "<img src='" + g_strImageDirPath + "pop_upload_image_main_title_1.gif' border='0' />";
					document.getElementById("img_preview").innerHTML = strFileInfoHTML;
					document.getElementById("txtWidth").value = "0";
					document.getElementById("txtHeight").value = "0";
					document.getElementById("txtAlt").value = "";
					document.getElementById("txtBorder").value = "0";
				}
			}
			else if(g_browserCHK.ff || g_browserCHK.wk || g_browserCHK.op)
			{
				var nFilePathLength = strUploadImageURL.length;
				var nFileNameIndex = strUploadImageURL.lastIndexOf("\\");
				var strFileName = strUploadImageURL.substr(nFileNameIndex+1, nFilePathLength-nFileNameIndex);

				var strFileInfoHTML = fnFileInfoHTML(strFileName);

				document.getElementById("img_main").innerHTML = "<img src='" + g_strImageDirPath + "pop_upload_image_main_title_1.gif' border='0' />";
				document.getElementById("img_preview").innerHTML = strFileInfoHTML;
				document.getElementById("txtWidth").value = "0";
				document.getElementById("txtHeight").value = "0";
				document.getElementById("txtAlt").value = "";
				document.getElementById("txtBorder").value = "0";
			}
		}
		else
		{
			setTimeout("fnLoadImageInfo(" + nLoadType + "," + bUpdateWH + "," + bUpload + ")", 100);
		}
	}
	catch(e)
	{
	}
}

function fnFileInfoHTML(strFileName)
{
	try
	{
		var strFileInfoHTML = "";
		strFileInfoHTML += "<div style='margin-top:60px;'>";
		strFileInfoHTML += "선택한 파일명<br />";
		strFileInfoHTML += "<b>" + strFileName + "</b>";
		strFileInfoHTML += "</div>";

		return strFileInfoHTML;
	}
	catch(e)
	{
	}

	return "";
}

function fnLoadImageInfo(nLoadType, bUpdateWH, bUpload)
{
	try
	{
		if(bUpload)
		{
			g_objUploadObject = null;
			g_objUploadObject = new Image();
			g_objUploadObject.src = g_strUploadImageURL;
		}
		else
		{
			g_objLoadURLObject = null;
			g_objLoadURLObject = new Image();
			g_objLoadURLObject.src = g_strUploadImageURL;
		}

		fnLoadImageSize(nLoadType, bUpdateWH, bUpload);
	}
	catch(e)
	{
	}
}

function fnLoadImageSize(nLoadType, bUpdateWH, bUpload)
{
	try
	{
		var nWidth = (bUpload ? g_objUploadObject.width : g_objLoadURLObject.width);
		var nHeight = (bUpload ? g_objUploadObject.height : g_objLoadURLObject.height);

		if(0 == nWidth || 0 == nHeight)
		{
			setTimeout("fnLoadImageSize(" + nLoadType + "," + bUpdateWH + "," + bUpload + ")", 100);
		}
		else
		{
			fnLoadImgSizeDone(nLoadType, bUpdateWH, bUpload);
		}
	}
	catch(e)
	{
	}
}

function fnLoadImgSizeDone(nLoadType, bUpdateWH, bUpload)
{
	try
	{
		var nWidth = (bUpload ? g_objUploadObject.width : g_objLoadURLObject.width);
		var nHeight = (bUpload ? g_objUploadObject.height : g_objLoadURLObject.height);

		var nCheckWidth = nWidth;
		var nCheckHeight = nHeight;

		if(1 == nLoadType)
		{
			if(nWidth > 146) nCheckWidth = 146;
			if(nHeight > 146) nCheckHeight = 146;
		}
		else if(2 == nLoadType)
		{
			if(nWidth > 396) nCheckWidth = 396;
			if(nHeight > 406) nCheckHeight = 406;
		}
		else return;

		var strImageHTML = fnMakeImageHTML(nCheckWidth, nCheckHeight, bUpload);

		if(1 == nLoadType)
		{
			var objPreview = document.getElementById("img_preview");
			objPreview.innerHTML = strImageHTML;

			if(bUpdateWH)
			{
				if(nWidth > g_nMaxWidth)
				{
					document.getElementById("txtWidth").value = g_nMaxWidth;
					document.getElementById("txtHeight").value = fnSetHeightByRatio(nWidth, nHeight, g_nMaxWidth);
				}
				else
				{
					document.getElementById("txtWidth").value = nWidth;
					document.getElementById("txtHeight").value = nHeight;
				}
			}

			document.getElementById("img_main").innerHTML = "<img src='" + g_strImageDirPath + "pop_upload_image_main_title_1.gif' border='0' />";
			document.getElementById("txtAlt").value = "";
			document.getElementById("txtBorder").value = "0";

			g_objUploadObject = null;
		}
		else if(2 == nLoadType)
		{
			var objImgMain = document.getElementById("img_main");
			objImgMain.innerHTML = strImageHTML;

			if(bUpdateWH)
			{
				if(nWidth > g_nMaxWidth)
				{
					document.getElementById("txtWidth").value = g_nMaxWidth;
					document.getElementById("txtHeight").value = fnSetHeightByRatio(nWidth, nHeight, g_nMaxWidth);
				}
				else
				{
					document.getElementById("txtWidth").value = nWidth;
					document.getElementById("txtHeight").value = nHeight;
				}
			}
		}
		else return;
	}
	catch(e)
	{
	}
}

function fnMakeImageHTML(nWidth, nHeight, bUpload)
{
	try
	{
		var strImageHTML = "";

		if(bUpload)
		{
			strImageHTML = "<img src='" + g_objUploadObject.src + "' ";
		}
		else
		{
			strImageHTML = "<img src='" + g_objLoadURLObject.src + "' ";
		}

		strImageHTML += "width='" + nWidth + "' ";
		strImageHTML += "height='" + nHeight + "' border='0' />";

		return strImageHTML;
	}
	catch(e)
	{
	}

	return "";
}

function fnUploadFile()
{
	try
	{
		var objFrmUpload = document.frmUpload;
		var objFile = objFrmUpload.fileUpload;

		var strImageFilePath = objFile.value;
		strImageFilePath = strImageFilePath.toLowerCase();

		if(0 == strImageFilePath.length || "" == strImageFilePath)
		{
			g_strUploadImageURL = "";
			alert("파일선택에서 이미지파일을 먼저 선택하세요");
			return;
		}

		if((-1==strImageFilePath.indexOf(".jpg")) && (-1==strImageFilePath.indexOf(".gif")) && (-1==strImageFilePath.indexOf(".png")))
		{
			alert("이미지파일(jpg,gif,png) 파일만 올리실 수 있습니다");
			return;
		}


		// 파일 확장자 추가검사 - Start
		var nFindIndex = -1;
		var nFilePathLength = strImageFilePath.length;
		if(-1 != strImageFilePath.indexOf(".jpg"))
		{
			nFindIndex = strImageFilePath.indexOf(".jpg");
			if(nFilePathLength != (nFindIndex+4))
			{
				alert("이미지파일(jpg,gif,png) 파일만 올리실 수 있습니다");
				return;
			}
		}
		if(-1 != strImageFilePath.indexOf(".gif"))
		{
			nFindIndex = strImageFilePath.indexOf(".gif");
			if(nFilePathLength != (nFindIndex+4))
			{
				alert("이미지파일(jpg,gif,png) 파일만 올리실 수 있습니다");
				return;
			}
		}
		if(-1 != strImageFilePath.indexOf(".png"))
		{
			nFindIndex = strImageFilePath.indexOf(".png");
			if(nFilePathLength != (nFindIndex+4))
			{
				alert("이미지파일(jpg,gif,png) 파일만 올리실 수 있습니다");
				return;
			}
		}
		// 파일 확장자 추가검사 - End


		document.getElementById("uploadStatus").style.display = "";

		objFrmUpload.action = g_strUploadPath_Image;
		objFrmUpload.target = "ifrmUpload";
		objFrmUpload.submit();
	}
	catch(e)
	{
	}
}

function fnLoadURLImage()
{
	try
	{
		var strLoadURL = document.getElementById("txtLoadURL").value;
		if("" == strLoadURL)
		{
			alert("이미지 URL을 입력하세요");
			document.getElementById("txtLoadURL").focus();
			return;
		}

		fnLoadImage(strLoadURL, 2, true, false);
	}
	catch(e)
	{
	}
}

function fnUploadResult(strUploadImageURL)
{
	if("" == strUploadImageURL)
	{
		alert("업로드가 실패하였습니다\n이미지 파일이 아니거나 용량을 초과하였습니다");
		g_strUploadImageURL = "";
	}
	else
	{
		fnLoadImage(strUploadImageURL, 2, true, true);
	}

	document.getElementById("uploadStatus").style.display = "none";
}

function fnTabMouseOver(objTab, nTabIndex)
{
	if(nTabIndex != g_nSelectedTabIndex)
	{
		objTab.src = g_strImageDirPath + "pop_upload_tab_image_" + nTabIndex + "_r.gif";
	}
}

function fnTabMouseOut(objTab, nTabIndex)
{
	if(nTabIndex != g_nSelectedTabIndex)
	{
		objTab.src = g_strImageDirPath + "pop_upload_tab_image_" + nTabIndex + ".gif";
	}
}

function fnTabClick(nTabIndex)
{
	if(nTabIndex != g_nSelectedTabIndex)
	{
		g_nSelectedTabIndex = nTabIndex;

		if(1 == nTabIndex)
		{
			document.getElementById("imgTab_1").src = g_strImageDirPath + "pop_upload_tab_image_1_r.gif";
			document.getElementById("imgTab_2").src = g_strImageDirPath + "pop_upload_tab_image_2.gif";

			document.getElementById("imgLoadArea").style.display = "none";
			document.getElementById("imgPreviewArea").style.display = "";
			document.getElementById("imgPreviewDummy").style.display = "none";

			if(g_objLoadURLObject)
			{
				g_objLoadURLObjectProp.width = document.getElementById("txtWidth").value;
				g_objLoadURLObjectProp.height = document.getElementById("txtHeight").value;
				g_objLoadURLObjectProp.alt = document.getElementById("txtAlt").value;
				g_objLoadURLObjectProp.border = document.getElementById("txtBorder").value;
			}

			if(g_objUploadObject)
			{
				fnLoadImage(g_objUploadObject.src, 2, false, true);

				document.getElementById("txtWidth").value = g_objUploadObjectProp.width;
				document.getElementById("txtHeight").value = g_objUploadObjectProp.height;
				document.getElementById("txtAlt").value = g_objUploadObjectProp.alt;
				document.getElementById("txtBorder").value = g_objUploadObjectProp.border;
			}
			else
			{
				document.getElementById("img_main").innerHTML = "<img src='" + g_strImageDirPath + "pop_upload_image_main_title_1.gif' border='0' />";

				document.getElementById("txtWidth").value = "0";
				document.getElementById("txtHeight").value = "0";
				document.getElementById("txtAlt").value = "";
				document.getElementById("txtBorder").value = "0";
			}
		}
		else
		{
			document.getElementById("imgTab_1").src = g_strImageDirPath + "pop_upload_tab_image_1.gif";
			document.getElementById("imgTab_2").src = g_strImageDirPath + "pop_upload_tab_image_2_r.gif";

			document.getElementById("imgLoadArea").style.display = "";
			document.getElementById("imgPreviewArea").style.display = "none";
			document.getElementById("imgPreviewDummy").style.display = "";

			if(g_objUploadObject)
			{
				g_objUploadObjectProp.width = document.getElementById("txtWidth").value;
				g_objUploadObjectProp.height = document.getElementById("txtHeight").value;
				g_objUploadObjectProp.alt = document.getElementById("txtAlt").value;
				g_objUploadObjectProp.border = document.getElementById("txtBorder").value;
			}

			if(g_objLoadURLObject)
			{
				fnLoadImage(g_objLoadURLObject.src, 2, false, false);

				document.getElementById("txtWidth").value = g_objLoadURLObjectProp.width;
				document.getElementById("txtHeight").value = g_objLoadURLObjectProp.height;
				document.getElementById("txtAlt").value = g_objLoadURLObjectProp.alt;
				document.getElementById("txtBorder").value = g_objLoadURLObjectProp.border;
			}
			else
			{
				document.getElementById("img_main").innerHTML = "<img src='" + g_strImageDirPath + "pop_upload_image_main_title_2.gif' border='0' />";

				document.getElementById("txtWidth").value = "0";
				document.getElementById("txtHeight").value = "0";
				document.getElementById("txtAlt").value = "";
				document.getElementById("txtBorder").value = "0";
			}
		}
	}
}

function fnKeyUpWidthHeight(nFlag)
{
	try
	{
		var nOriWidth = 0;
		var nOriHeight = 0;

		if(1 == g_nSelectedTabIndex)
		{
			nOriWidth = g_objUploadObject.width;
			nOriHeight = g_objUploadObject.height;
		}
		else
		{
			nOriWidth = g_objLoadURLObject.width;
			nOriHeight = g_objLoadURLObject.height;
		}
		if((0 == nOriWidth) || (0 == nOriHeight)) return;


		var bSameRatio = document.getElementById("chkSameRatio").checked;
		if(bSameRatio)
		{
			if(1 == nFlag)
			{
				var nWidth = document.getElementById("txtWidth").value;
				if(isNaN(nWidth))
				{
					document.getElementById("txtWidth").value = nOriWidth;
					return;
				}

				var dRatio = ((nWidth * 100) / nOriWidth) / 100;
				var nRatioHeight = parseInt(nOriHeight * dRatio);
				document.getElementById("txtHeight").value = nRatioHeight;
			}
			else
			{
				var nHeight = document.getElementById("txtHeight").value;
				if(isNaN(nHeight))
				{
					document.getElementById("txtHeight").value = nOriHeight;
					return;
				}

				var dRatio = ((nHeight * 100) / nOriHeight) / 100;
				var nRatioWidth = parseInt(nOriWidth * dRatio);
				document.getElementById("txtWidth").value = nRatioWidth;
			}
		}
	}
	catch(e)
	{
	}
}

function fnSetHeightByRatio(nOriWidth, nOriHeight, nMaxWidth)
{
	try
	{
		if((0 == nOriWidth) || (0 == nOriHeight)) return;

		var dRatio = ((nMaxWidth * 100) / nOriWidth) / 100;
		var nRatioHeight = parseInt(nOriHeight * dRatio);

		return nRatioHeight;
	}
	catch(e)
	{
	}

	return 0;
}

// 키보드 제어 관련 추가부분
function fnFired_Focus_File(objElement)
{
	var objParent = objElement.parentNode;
	objParent.style.borderLeft = "1px dashed #d3d3d3";
	objParent.style.borderTop = "1px dashed #d3d3d3";
	objParent.style.borderRight = "1px dashed #d3d3d3";
}

// 키보드 제어 관련 추가부분
function fnFired_Blur_File(objElement)
{
	var objParent = objElement.parentNode;
	objParent.style.border = "0px dashed #d3d3d3";
}
