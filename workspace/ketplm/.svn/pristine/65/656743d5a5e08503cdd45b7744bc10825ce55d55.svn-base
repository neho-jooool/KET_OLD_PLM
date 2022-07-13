var carTypeRequire = false;
var cost = {
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'TrackingSearchGrid',
			Sync : 1,
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['TrackingSearchGrid'])?Grids['TrackingSearchGrid'].PageLength:CommonGrid.pageSize
				},
        		Params : decodeURIComponent($('[name=searchForm]').serialize())
			},
			Sort : '-createStamp',Sorting : '1',AutoSort : '1', MaxSort : '1',
			perPageOnChange : 'javascript:cost.search(Value);',
			Cols : [
					//{Name : 'devTeamName', Width:120, Align : 'center', CanSort : '1', CanEdit : '0'},
			        {Name : 'pjtNo', Width:100, Align : 'center', CanSort : '1', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:openView(Row.oid + "&key=popup&value=popup");', 
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'pjtName', MinWidth:150, RelWidth : 1, Align : 'center', CanSort : '1', CanEdit : '0',
						Type : "Text", OnClick : 'javascript:openView(Row.oid + "&key=popup&value=popup");',
						HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
					{Name : 'realPartNo', Width:100, Align : 'left', CanSort : '1', CanEdit : '0'},
                    {Name : 'partNo', Width:100, Align : 'left', CanSort : '1', CanEdit : '0'},
                    //{Name : 'grade', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'customer', Width:90, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'model', Width:100, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'sopDate', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
                    {Name : 'dr0lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'dr1lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'dr2lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'dr3lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'dr4lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'dr5lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'dr6lvProfitRate', Width:100, Align : 'center', CanSort : '0', CanEdit : '0', Type : "Text" },
                    {Name : 'increase', Width:100, Type : "Text", Align : 'center', CanSort : '0', CanEdit : '0'},
                    {Name : 'createStamp', Visible : 0, Align : 'center', CanSort : '0', CanEdit : '0'}
			],Header :{
				CanDelete : '0', Align : 'Center',
				CanSelect : 1,
				//devTeamName	: "제품개발팀",
				pjtNo		: "프로젝트 번호",
				pjtName		: "프로젝트명",
				realPartNo	: "실제부품번호",
				partNo		: "부품번호",
				//grade		: "GRADE",
				customer	: "고객사",
				model		: "차종",
				sopDate		: "SOP",
				increase	: "증감",
				dr0lvProfitRate : "DR0",
				dr1lvProfitRate : "DR1",
				dr2lvProfitRate : "DR2",
				dr3lvProfitRate : "DR3",
				dr4lvProfitRate : "DR4",
				dr5lvProfitRate : "DR5",
				dr6lvProfitRate : "DR6"
			},
			SelectingSingle : '1',
			Panel : {
				Width : '20', Visible : '1',CanHide : '0'
			}
		}),'listGrid');
		
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/cost/findPagingList.do?sortName=*Sort0';
			
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
		}
		//row click event
		//Grids.OnClick = cost.goView;
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['TrackingSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['TrackingSearchGrid'].Source.Data.Url = '/plm/ext/cost/findPagingList.do?sortName=*Sort0';
		Grids['TrackingSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['TrackingSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());//"&title=dd&status=";
		Grids['TrackingSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['TrackingSearchGrid'].ReloadBody();
		//Grids[0].Reload();
	},
	
	clear : function(){
		$('[name=searchForm]')[0].reset();
	},
	setProjectNo : function(objArr){
        $('[name=pjtNo]').val(objArr[0][1]);
        $('[name=pjtOid]').val(objArr[0][0]).trigger('change');;
        $('[name=pjtName]').val(objArr[0][2]);
	}
}