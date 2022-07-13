/////////////////////////////////////////////////////////////////////////////////////////////////////
// editor 갯수(1개~20개) 및 editor를 로딩할 영역의 ID 설정
// 페이지별로 editor의 갯수를 설정하고자 하는 경우는 페이지별로 선언할 것
// 사이트 전체에 일괄적으로 1개 또는 N개 로딩하는 경우면 아래부분의 주석을 제거하고 적용

var g_arrSetEditorArea = new Array();
g_arrSetEditorArea[0] = "EDITOR_AREA_CONTAINER";// 이노디터를 위치시킬 영역의 ID값 설정
/////////////////////////////////////////////////////////////////////////////////////////////////////



// skin 선택(0~9사이의 skin 선택)
var g_nSkinNumber = 0;

var g_strPath_Image = "/plm/portal/innoditor_u/image/";
var g_strPath_JS = "/plm/portal/innoditor_u/js/";
var g_strPath_CSS = "/plm/portal/innoditor_u/css/";
var g_strPath_Property = "/plm/portal/innoditor_u/";
var g_strPath_License = "/plm/portal/innoditor_u/";

var g_nEditorWidth = 670;
var g_nEditorHeight = 600;


// 제품 도움말 URL
var g_strHelpPageURL = "/plm/portal/innoditor_u/pop_help.html";// 한국어만 사용하는 경우
//var g_strHelpPageURL = "/plm/portal/innoditor_u/pop_help_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strHelpPageURL = "/plm/portal/innoditor_u/pop_help_u_en.html";// 언어 직접 설정 - 영어
//var g_strHelpPageURL = "/plm/portal/innoditor_u/pop_help_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strHelpPageURL = "/plm/portal/innoditor_u/pop_help_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// 제품정보 URL
var g_strProductInfoURL = "/plm/portal/innoditor_u/pop_productinfo.html";// 한국어만 사용하는 경우
//var g_strProductInfoURL = "/plm/portal/innoditor_u/pop_productinfo_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strProductInfoURL = "/plm/portal/innoditor_u/pop_productinfo_u_en.html";// 언어 직접 설정 - 영어
//var g_strProductInfoURL = "/plm/portal/innoditor_u/pop_productinfo_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strProductInfoURL = "/plm/portal/innoditor_u/pop_productinfo_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// Image 업로드 Page URL
var g_strUploadImageURL = "/plm/portal/innoditor_u/pop_simple_img.html";
//var g_strUploadImageURL = "/plm/portal/innoditor_u/pop_upload_img.html";// 한국어만 사용하는 경우
//var g_strUploadImageURL = "/plm/portal/innoditor_u/pop_upload_img_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strUploadImageURL = "/plm/portal/innoditor_u/pop_upload_img_u_en.html";// 언어 직접 설정 - 영어
//var g_strUploadImageURL = "/plm/portal/innoditor_u/pop_upload_img_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strUploadImageURL = "/plm/portal/innoditor_u/pop_upload_img_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// Flash 업로드 Page URL
var g_strUploadFlashURL = "/plm/portal/innoditor_u/pop_simple_flash.html";
//var g_strUploadFlashURL = "/plm/portal/innoditor_u/pop_upload_flash.html";// 한국어만 사용하는 경우
//var g_strUploadFlashURL = "/plm/portal/innoditor_u/pop_upload_flash_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strUploadFlashURL = "/plm/portal/innoditor_u/pop_upload_flash_u_en.html";// 언어 직접 설정 - 영어
//var g_strUploadFlashURL = "/plm/portal/innoditor_u/pop_upload_flash_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strUploadFlashURL = "/plm/portal/innoditor_u/pop_upload_flash_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// Media 업로드 Page URL
var g_strUploadMediaURL = "/plm/portal/innoditor_u/pop_simple_media.html";
//var g_strUploadMediaURL = "/plm/portal/innoditor_u/pop_upload_media.html";// 한국어만 사용하는 경우
//var g_strUploadMediaURL = "/plm/portal/innoditor_u/pop_upload_media_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strUploadMediaURL = "/plm/portal/innoditor_u/pop_upload_media_u_en.html";// 언어 직접 설정 - 영어
//var g_strUploadMediaURL = "/plm/portal/innoditor_u/pop_upload_media_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strUploadMediaURL = "/plm/portal/innoditor_u/pop_upload_media_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// 배경 Image 업로드 및 설정 Page URL
var g_strUploadBackgroundImageURL = "/plm/portal/innoditor_u/pop_simple_img_bg.html";
//var g_strUploadBackgroundImageURL = "/plm/portal/innoditor_u/pop_upload_img_bg.html";// 한국어만 사용하는 경우
//var g_strUploadBackgroundImageURL = "/plm/portal/innoditor_u/pop_upload_img_bg_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strUploadBackgroundImageURL = "/plm/portal/innoditor_u/pop_upload_img_bg_u_en.html";// 언어 직접 설정 - 영어
//var g_strUploadBackgroundImageURL = "/plm/portal/innoditor_u/pop_upload_img_bg_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strUploadBackgroundImageURL = "/plm/portal/innoditor_u/pop_upload_img_bg_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// 문서 Templete 삽입 Page URL
var g_strInsertDocTempleteURL = "/plm/portal/innoditor_u/pop_doc_templete.html";// 한국어만 사용하는 경우
//var g_strInsertDocTempleteURL = "/plm/portal/innoditor_u/pop_doc_templete_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 문서 Templete 삽입 페이지는 사용되는 경우 고객사에서 자유롭게 설정하는 예제 페이지임으로
// -- 언어별 강제 설정 적용 페이지는 없음
// --------------------------------------------------------------------------------------------------------------- //


// 속성 Page URL
var g_strPropertyPageURL = "/plm/portal/innoditor_u/pop_property.html";// 속성창의 경우는 사용자 OS에 따른 다국어지원(단, <title></title>의 제목은 한글)
//var g_strPropertyPageURL = "/plm/portal/innoditor_u/pop_property_u.html";// 속성창의 경우는 사용자 OS에 따른 다국어지원(단, <title></title>의 제목은 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strPropertyPageURL = "/plm/portal/innoditor_u/pop_property_u_en.html";// 언어 직접 설정 - 영어
//var g_strPropertyPageURL = "/plm/portal/innoditor_u/pop_property_u_ja.html";// 언어 직접 설정 - 일본어
//var g_strPropertyPageURL = "/plm/portal/innoditor_u/pop_property_u_zh-cn.html";// 언어 직접 설정 - 중국어
// --------------------------------------------------------------------------------------------------------------- //


// 미리보기 Page URL
//var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview.html";// 한국어만 사용하는 경우
//var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview_u.html";// 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview_x.html";// XHTML 출력방식으로 설정한 경우(한국어)
//var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview_x_u.html";// XHTML 출력방식으로 설정한 경우 - 사용자 OS에 따른 다국어용(한국어, 영어, 일본어, 중국어 - 기타 영어)
// --------------------------------------------------------------------------------------------------------------- //
// -- 아래는 사용자 OS에 따른 다국어 자동 분기를 사용하지 않고 강제적으로 해당 언어를 설정하고자 하는 경우만 사용
//var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview_x_u_en.html";// 언어 직접 설정(XHTML) - 영어
//var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview_x_u_ja.html";// 언어 직접 설정(XHTML) - 일본어
//var g_strPreviewPageURL = "/plm/portal/innoditor_u/pop_preview_x_u_zh-cn.html";// 언어 직접 설정(XHTML) - 중국어
// --------------------------------------------------------------------------------------------------------------- //



// 라이센스
var g_arrDomainName = new Array();
g_arrDomainName[0] = "localhost";


var g_arrLicenseKey = new Array();
g_arrLicenseKey[0] = "Mv5Oi$BZ+q3Pm/Lq4h@MX4Nh#AYs26EYo&@Tbq3Pm/Lhf+Ap7Y{D";



// 메뉴바 show 또는 hidden 만 지원(메뉴레이어)
var g_bCustomize_MenuBar_Display = true;

// Bottom Tab바 show 또는 hidden 만 지원(미리보기,편집창,소스창 버튼)
var g_bCustomize_TabBar_Display = true;

// 첫번째 툴바 show 또는 hidden 설정(세부 항목은 따로 설정)
var g_bCustomize_ToolBar1_Display = true;

// 두번째 툴바 show 또는 hidden 설정(세부 항목은 따로 설정)
var g_bCustomize_ToolBar2_Display = true;

// 세번째 툴바 show 또는 hidden 설정(세부 항목은 따로 설정)
var g_bCustomize_ToolBar3_Display = true;

// 사용자 정의 툴바(이노디터에서 제공되는 기능을 사용하지 않을 경우 이노디터 Interface 만 연동)
var g_bCustomize_CustomToolbar_Display = false;
var g_bCustomize_CustomToolbar_Layout = 0;// 0 - 해당사항 없음, 1 - Top(툴바 상단 영역), 2 - Bottom(툴바 하단 영역)
var g_bCustomize_CustomToolbar_HTML = "";// 사용자정의 툴바에 들어갈 HTML 내용(<table> ~ </table> : table로 시작하여 table로 끝나야 함)


// 툴바 셋팅용 변수 선언 (툴바 셋팅 세부 항목 샘플은 customize_ui.js 참조)
var g_arrCustomToolbar1 = new Array();
var g_arrCustomToolbar2 = new Array();
var g_arrCustomToolbar3 = new Array();





///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// V2.1.5.6 - 동적 에디터 로딩을 위한 추가 부분
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

var g_bDynamicLoadMode = true;
var g_objRegisterEnd = {nCount_Total:0, nCount_Cur:0, bCount_End:false};

var g_objLoadStatus = {nCount_Call:0, nCount_Done:0, strSyncJsFilePath:null};
var g_arrJsFilePathList_Sync_Wait = new Array();

// 이노디터 관련 스크립트 로딩을 완료 후 호출하는 함수명 정의
var g_strFunctionName_Script_Load_Done = "fn_Innoditor_Script_Load_Done";


function fnDoNextJsLoad(strJsFilePath)
{
	try
	{
		g_objRegisterEnd.nCount_Cur++;
		if(g_objRegisterEnd.bCount_End)
		{
			if(g_objRegisterEnd.nCount_Total == g_objRegisterEnd.nCount_Cur)
			{
				eval(g_strFunctionName_Script_Load_Done + "();");
				return;
			}
		}

		if(g_objLoadStatus.strSyncJsFilePath == strJsFilePath)
		{
			g_objLoadStatus.nCount_Call = 0;
			g_objLoadStatus.nCount_Done = 0;


			for(var i=0; i<g_arrJsFilePathList_Sync_Wait.length; i++)
			{
				if(null == g_arrJsFilePathList_Sync_Wait[i]) continue;

				if(g_arrJsFilePathList_Sync_Wait[i][1])
				{
					g_objLoadStatus.strSyncJsFilePath = g_arrJsFilePathList_Sync_Wait[i][0];

					if(0 == g_objLoadStatus.nCount_Call)
					{
						fnDynamicLoad_Script(g_arrJsFilePathList_Sync_Wait[i][0]);
						g_arrJsFilePathList_Sync_Wait[i] = null;
						return;
					}
					else
					{
						g_arrJsFilePathList_Sync_Wait[i] = null;
						return;
					}
				}
				else
				{
					g_objLoadStatus.nCount_Call++;
					fnDynamicLoad_Script(g_arrJsFilePathList_Sync_Wait[i][0]);

					g_arrJsFilePathList_Sync_Wait[i] = null;
				}
			}

			g_objLoadStatus.strSyncJsFilePath = null;
		}
		else
		{
			g_objLoadStatus.nCount_Done++;
			if(g_objLoadStatus.nCount_Call == g_objLoadStatus.nCount_Done)
			{
				if(null != g_objLoadStatus.strSyncJsFilePath)
				{
					fnDynamicLoad_Script(g_objLoadStatus.strSyncJsFilePath);
				}
			}
		}
	}
	catch(e)
	{
	}
}

function fnDynamicLoad_Register_Script(strJsFilePath, bDoSyncLoad)
{
	try
	{
		g_objRegisterEnd.nCount_Total++;

		if(null == g_objLoadStatus.strSyncJsFilePath)
		{
			if(bDoSyncLoad)
			{
				g_objLoadStatus.strSyncJsFilePath = strJsFilePath;

				if(0 == g_arrJsFilePathList_Sync_Wait.length)
				{
				}
				else
				{
					return;
				}
			}
			else
			{
				g_objLoadStatus.nCount_Call++;
			}
		}
		else
		{
			g_arrJsFilePathList_Sync_Wait[g_arrJsFilePathList_Sync_Wait.length] = new Array(strJsFilePath, bDoSyncLoad);
			return;
		}

		fnDynamicLoad_Script(strJsFilePath);
	}
	catch(e)
	{
	}
}

function fnDynamicLoad_Register_End()
{
	try
	{
		g_objRegisterEnd.bCount_End = true;
	}
	catch(e)
	{

	}
}

function fnDynamicLoad_Script(strJsFilePath)
{
	try
	{
		var objHeadElement = document.getElementsByTagName("head")[0];

		var objScript = document.createElement("script");
		objScript.setAttribute("type", "text/javascript");


		var bLoaded = false;
		if(document.all)
		{
			objScript.onreadystatechange = function() {
				if("loaded" == this.readyState || "complete" == this.readyState)
				{
					if(bLoaded)
					{
						return;
					}

					bLoaded = true;

					fnDoNextJsLoad(strJsFilePath);
				}
			}
		}
		else
		{
			objScript.onload = function() {
				fnDoNextJsLoad(strJsFilePath);
			}
		}

		objScript.setAttribute("src", strJsFilePath);
		objHeadElement.appendChild(objScript);
	}
	catch(e)
	{
	}
}

function fnDynamicLoad_CSS(strCssFilePath)
{
	try
	{
		var objHeadElement = document.getElementsByTagName("head")[0];

		var objLink = document.createElement("link");
		objLink.setAttribute("rel", "stylesheet");
		objLink.setAttribute("type", "text/css");
		objLink.setAttribute("href", strCssFilePath);

		objHeadElement.appendChild(objLink);
	}
	catch(e)
	{
	}
}


fnDynamicLoad_Register_Script(g_strPath_JS + "browser.js", true);
fnDynamicLoad_Register_Script(g_strPath_JS + "indr489343715.js", false);
fnDynamicLoad_Register_Script(g_strPath_JS + "indr670454868.js", false);
fnDynamicLoad_Register_Script(g_strPath_JS + "indr873475877.js", false);
fnDynamicLoad_Register_Script(g_strPath_JS + "indr528318566.js", false);
fnDynamicLoad_Register_Script(g_strPath_JS + "indr696495397.js", false);
fnDynamicLoad_Register_Script(g_strPath_JS + "indr988789177.js", false);
fnDynamicLoad_Register_Script(g_strPath_JS + "customize_ui.js", true);
fnDynamicLoad_Register_Script(g_strPath_JS + "loadlayer_dynamic_call.js", false);
