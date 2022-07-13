var carTypeRequire = false;
var cost = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'interfaceSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['interfaceSearchGrid'])?Grids['interfaceSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-createStamp',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:cost.search(Value);',
			Cols : [
					//{Name : 'devTeamName', Width:120, Align : 'center', CanSort : '1', CanEdit : '0'},
			        {Name : 'pjtNo', Width:90, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text", OnClick : 'javascript:openProject(Row.pjtNo);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'realPartNo', Width:70, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'partNo', Width:85, Align : 'left', CanSort : '0', CanEdit : '0'},
                    {Name : 'version', Width:30, Align : 'center', CanSort : '0', CanEdit : '0', OnClick : 'javascript:cost.partPopup(Row.taskOid,Row.partOid);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
                    {Name : 'partDetailLabel', Width:50, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'drStep', Width:50, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'lastest', Width:50, Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'materialCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                    {Name : 'laborCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text", Type : "Float", Format : "###,##0.0000" },
                    {Name : 'inDirectLaborCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'facReducePrice', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'directCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'inDirectCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'moldReducePrice', Width:100, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'outUnitCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'etcCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'salesManageCost', Width:70, Type : "Text", Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                    {Name : 'scrapSalesCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0',  Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'salesTargetCostTotal', Width:70,   Align : 'right', CanSort : '0', CanEdit : '0',  Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'productCostTotal', Width:70,  Align : 'right', CanSort : '0', CanEdit : '0',  Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'orgProductCostTotal', Width:70,  Align : 'right', CanSort : '0', CanEdit : '0',  Type : "Text" , Type : "Float", Format : "###,##0.0000"},
                    {Name : 'transferFlag', Width:70,  Align : 'center', CanSort : '0', CanEdit : '0',  Type : "Text" },
                    {Name : 'transferDate', Width:70,  Align : 'center', CanSort : '0', CanEdit : '0',  Type : "Text" },
                    {Name : 'transferMsg', Width:150,  Align : 'left', CanSort : '0', CanEdit : '0',  Type : "Text" }
			],Header :{
				CanDelete : '0', Align : 'Center',
				CanSelect : 1,
				//devTeamName	: "제품개발팀",
				pjtNo		: "프로젝트 번호",
				realPartNo	: "실제품번",
				partNo		: "가품번",
				version	: "버전",
				partDetailLabel : "세부\n내역",
				drStep		: "DR단계",
				lastest : "최종여부",
				materialCost		: "재료비",
				laborCost	: "직접인건비",
				inDirectLaborCost : "간접인건비",
				facReducePrice : "설비감가비",
				directCost : "직접경비",
				inDirectCost : "간접경비",
				moldReducePrice : "금형감가비",
				outUnitCost : "외주가공비",
				etcCost : "기타원가",
				salesManageCost : "판관비",
				scrapSalesCost : "스크랩매출",
				salesTargetCostTotal : "판매목표가",
				productCostTotal : "총원가\n(재계산)",
				orgProductCostTotal : "총원가\n(원본)",
				transferFlag : "ERP전송",
				transferDate : "전송일",
				transferMsg : "전송오류로그"
			},
			SelectingSingle : '0',
			Panel : {
				Width : '20', Visible : '1',CanHide : '0'
			}
		}),'listGrid');
		
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/cost/findPagingInterfaceList.do?sortName=*Sort0';
			
			var param = {
		       		page : grid.GetPageNum(row),
		            formPage : grid.Source.Layout.Data.Cfg.PageLength
		        }

	        $('input,select').each(function(){
	        	var name = $(this).attr('name');
	            var value = $(this).val();
	            param[name] = value;
	        });
	        grid.Data.Page.Param = param;
		},
		
		Grids.OnRenderPageFinish = function(){
		},
		
		Grids.OnRenderFinish = function(){
        }
		//row click event
		//Grids.OnClick = cost.goView;
	},
	
	partPopup : function (taskOid,partOid){
		
		getOpenWindow2("/plm/ext/cost/costPartCalculatePopup.do?taskOid="+taskOid+"&oid=" + partOid, taskOid+partOid, 1280, 720);
	},
	
	detailPartPopup : function (partOid){
		
		getOpenWindow2("/plm/ext/cost/costErpInterfaceDetail.do?oid=" + partOid, partOid, 1280, 720);
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['interfaceSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['interfaceSearchGrid'].Source.Data.Url = '/plm/ext/cost/findPagingInterfaceList.do?sortName=*Sort0';
		Grids['interfaceSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['interfaceSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['interfaceSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['interfaceSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	}
}