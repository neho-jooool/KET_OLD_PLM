var material = {
		
		createPaingGrid : function(){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid',
				Sort : '-reqNo',Sorting : '1',AutoSort : '0', MaxSort : '1',
				perPageOnChange : 'javascript:material.search(Value);',
				Sync : 1,
				Upload : {
					Url : "/plm/ext/material/saveMaterial",
					Format : "JSON",
					Flags : "AllCols",
					Data : "treeData",
					Type : "Changes,Body",
		/* 			Param : {
						partListOid : "${partListOid}"
					}, */
				},
				Data : {
					Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['searchGrid'])?Grids['searchGrid'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				Cols : [
				        {Name : 'oid', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
				        {Name : 'spec_no', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'type', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
				        {Name : 'maker', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
				        {Name : 'grade', Width:150, Align : 'center', CanSort : '1', CanEdit : '0',Type : "Text", OnClick : 'javascript:openView(Row.oid);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>' },
				        {Name : 'temper', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 's_gravity', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'm_elasticity', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'e_conductivity', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'hardness', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'strength', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'elong', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'formability', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'residual_stress', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 't_melt', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 't_soft', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 't_conductivity', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'r_y', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'r_m', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'r_d', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'createDate', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'modifyDate', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0'}
						/*{Name : 'price', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0', Type : "Int", Format : "###,###"}*/
					],
					Head :[
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id : "Header",Spanned : '1',
								spec_no		  : "spec_no",
								type : "type",
								maker		  : "maker",
								grade	  : "grade",
								temper	  : "질별",
								s_gravity  : "비중",
								m_elasticity  : "탄성계수",
								e_conductivity  : "전기전도도",
								hardness  : "경도",
								strength	  : "인장강도",
								elong  : "연신율",
								formability	  : "성형성",
								residual_stress     : "내용력이완특성",
								t_melt	  : "녹는점",
								t_soft	  : "연화온도",
								t_conductivity	  : "열전도도",
								r_y	  : "r_y",
								r_m	  : "r_m",
								r_d	  : "r_d",
								createDate	  : "createDate",
								modifyDate	  : "modifyDate"
								/*price	  : "단가(원)"*/
							}
							]
				/*,
				SelectingSingle : '1',
				Panel : {
					Width : '21', Visible : '1',CanHide : '0'
				}*/
			}),'listGrid');

			
			//row click event
			//Grids.OnClick = issue.goView;
		},
		
		getGridId : function(){
			$("input:radio[name='gubun']").each(function(){
                var isChecked = $(this).is(":checked");
                if(isChecked){
                	gubun = $(this).val();
                }
            });
			
			var gridId = '';
			
			if(gubun == '비철'){
				gridId = 'searchGrid';
			}else if(gubun == '수지'){
				gridId = 'searchGrid2';
			}
			
			return gridId;
		},
		
		search : function(perPage){
			
			var gridId = material.getGridId();
			
			if(!perPage) perPage = Grids[gridId].Source.Layout.Data.Cfg.PageLength;
			Grids[gridId].Source.Data.Url = '/plm/ext/material/findPagingList.do?sortName=*Sort0';
			Grids[gridId].Source.Layout.Data.Cfg.PageLength=perPage;
			Grids[gridId].Source.Data.Params = decodeURIComponent($('[name=searchForm]').serialize());
			Grids[gridId].Source.Data.Param.formPage=perPage;
			Grids[gridId].ReloadBody();
			//Grids[0].Reload();
		},
		
		gridSave : function(){
			
			/*var gridId = material.getGridId();
			
			var G = Grids[gridId];
			console.log(G);
			G.Save();*/
			
	        if(confirm("저장하시겠습니까?")){
	        	var gridId = material.getGridId();
		        
		        var grid = Grids[gridId];

		        var colNames = grid.ColNames[1];
		        
		        var paramArr = new Array();
		        
		        for (var tRow = grid.GetFirstVisible(); tRow; tRow = grid.GetNextVisible(tRow) ) {
		        	var paramObj = {};
		        	for(var j = 0; j < colNames.length; j++){
		        		var value = grid.GetString(tRow, colNames[j]);
	            		paramObj[colNames[j]] = value;
		        	}
		        	paramArr.push(paramObj);
		        }
		        
		        if(paramArr.length < 1){
		        	alert("수정대상 데이터가 조회되지 않았습니다.");
		        	return;
		        }
		        
		        var gridParam = {};
		        gridParam.treeData = paramArr;
		        
		        var gridParam = new Object();
		        gridParam.jsonData = JSON.stringify(paramArr);
		        
		        ajaxCallServer("/plm/ext/material/saveMaterial", gridParam, function(data){
					
					alert("저장되었습니다.");
					grid.Reload();
					
			    });
	        }
	        
		},
		
		
		createPaingGrid2 : function(){
			
			this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
				id : 'searchGrid2',
				Sort : '-reqNo',Sorting : '1',AutoSort : '0', MaxSort : '1',
				perPageOnChange : 'javascript:material.search(Value);',
				Sync : 1,
				Upload : {
					Url : "/plm/ext/material/saveMaterial",
					Format : "JSON",
					Flags : "AllCols",
					Data : "treeData",
					Type : "Changes,Body",
		/* 			Param : {
						partListOid : "${partListOid}"
					}, */
				},
				Data : {
					Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['searchGrid2'])?Grids['searchGrid2'].PageLength:CommonGrid.pageSize
					},
	        		Params : decodeURIComponent($('[name=searchForm]').serialize())
				},
				Cols : [
				        {Name : 'oid', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
				        {Name : 'spec_no', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'type', Width:150, Align : 'center', CanSort : '1', CanEdit : '0'},
				        {Name : 'maker', Width:80, Align : 'center', CanSort : '1', CanEdit : '0'},
				        {Name : 'grade', Width:80, Align : 'center', CanSort : '1', CanEdit : '0',Type : "Text", OnClick : 'javascript:openView(Row.oid);', HtmlPrefix : '<font color="#4398BC">', HtmlPostfix : '</font>'},
				        {Name : 'melt_point', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'm_index', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 's_gravity', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'm_shrinkage', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'absorb', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 't_strength', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'elong', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'f_stringth', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'f_modulus', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'i_strength', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'h_dis_temp_18', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'h_dis_temp_4', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'flammability', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 's_flow', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'},
				        {Name : 'createDate', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0'},
						{Name : 'modifyDate', Width : 75,Align : 'center', CanSort : '1', CanEdit : '0'},
				        {Name : 'memo', Width:80, Align : 'center', CanSort : '1', CanEdit : '1'}
				        /*{Name : 'price', Width:80, Align : 'center', CanSort : '1', CanEdit : '0', Type : "Int", Format : "###,###"}*/
					],
					Head :[
							{
								CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
								id : "Header",Spanned : '1',
								spec_no		  : "spec_no",
								name	  : "name",
								maker		  : "maker",
								grade	  : "grade",
								melt_point  : "녹는점",
								m_index  : "MI",
								s_gravity  : "비중",
								m_shrinkage  : "수축율",
								absorb	  : "흡습율",
								t_strength  : "인장강도",
								elong	  : "연신율",
								f_stringth     : "굽힘강도",
								f_modulus	  : "굽힘탄성계수",
								i_strength	  : "충격강도",
								h_dis_temp_18	  : "열변형온도",
								h_dis_temp_4	  : "연변형온도",
								flammability	  : "난연성",
								s_flow	  : "s_flow",
								createDate	  : "createDate",
								modifyDate	  : "modifyDate",
								memo          : "memo"
								/*price : "단가(원)"*/
							}
							]
				/*,
				SelectingSingle : '1',
				Panel : {
					Width : '21', Visible : '1',CanHide : '0'
				}*/
			}),'listGrid2');

			
			//row click event
			//Grids.OnClick = issue.goView;
		},
		
	    clear : function(){
			$('[name=searchForm]')[0].reset();
			$('[name=searchForm] input').each(function(){
				$(this).val("");
			});
			$(".delayState").show();
		}
}