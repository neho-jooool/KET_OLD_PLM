var CommonGrid = {
	gridStyle : gridStyle,
	pagingList : pagingList,
	cookiesType : cookiesType,
	alternateType : alternateType,
	maxSort : maxSort,
	sortIcons : sortIcons,
	colMinWidth : colMinWidth,
	pageSize : pageSize,
	pagingNameList : (typeof(pagingNameList) == 'string')?pagingNameList:"|All|10|20|50|100",
	
	/**
	 * Server paing grid 생성을 위한 Object
	 * 예제 :  createPagingGrid({
	 *   id : '',
	 *   Data : {
	 *		Url : '/plm/ext/sample/findPagingList.do?sortName=*Sort0',
	 *		Method : 'POST',
	 *		Format : 'Form',
	 *		Param : {
	 *			formPage : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:'',
	 *			Pagingsize : (Grids['SampleSearchGrid'])?Grids['SampleSearchGrid'].PageLength:''
	 *	 },
	 *   excelDownloadFn : 'ecxel()',
	 *   Cols : [
	 *   	{Name : 'rowNum', Width:60, Align : 'Center', CanSort : '0'},
	 *		{Name : 'name', Width:100, Align : 'Center', CanSort : '1'},
	 *		{Name : 'title', Align : 'Left', CanSort : '1'},
	 *		{Name : 'createStamp', Align : 'Center', CanSort : '1'},
	 *		{Name : 'modifyStamp', Align : 'Center', CanSort : '1'}
	 *   ],
	 *   Header : {
	 *		CanDelete : '0', CadEdit : '0', Align : 'Center',
	 *		rowNum : 'No',
	 *		name : '이름',
	 *		title : '제목',
	 *		createStamp : '등록일',
	 *		modifyStamp : '수정일'
	 *	 },
	 *   DynamicSpan : '1',
	 *	 Head : [
	 *		{
	 *		 		CanDelete : '0', CadEdit : '0', Align : 'Center',
	 *				rowNum : 'No',rowNumSpan : '2',
	 *		 		name : '이름',nameRowSpan : '2',
	 *		 		title : '제목',titleRowSpan : '2',
	 *		 		createStamp : '등록일',createRowSpan : '2',
	 *		 		modifyStamp : '수정일'modifyStampRowSpan : '2',
	 *		},{
	 *			CanDelete : '0', CadEdit : '0', Align : 'Center',
	 *			rowNum : 'No', rowNumSpan : '2',
	 *			name : '이름', nameRowSpan : '2',
	 *			title : '제목', titleRowSpan : '2',
	 *			createStamp : '등록일', createRowSpan : '2',
	 *			modifyStamp : '수정일', modifyStampRowSpan : '2',
	 *			modifyStamp2 : '수정일'
	 *			modifyStamp3 : '수정일'
	 *		}
	 *	 ]
	 *   Sort : '-createStamp',
	 *   perPageOnChange : 'javascript:Sample.search(Value);',
	 *   useToolbar : true //Toolbar 사용 유무
	 * });
	 * @param gridConfig
	 * @returns {GridCfgObject}
	 */
	getGridConfig : function(gridConfig){
		var obj = {
			Debug : 'Error',
			Data : gridConfig.Data,
			Layout : {
				Data : {
					Cfg : {
						id : gridConfig.id,	//쿠키 설정을 저장하기 위한 식별자 : 모듈/화면간 중복되지 않도록 부여해야 함
						SuppressCfg : this.cookiesType, //쿠기 사용 여부를 설정 : 0 – uses, 1 – does not load, 2 – does not save, 3 – nothing
						SuppressMessage : '1',
						//페이징 처리 설정
						Paging : 2,					//1 - Show all ,2 - all client paging, 3 - all server paing 
						PageLength: this.pageSize,	//한 페이지 표현될 최대 row수 -default 20 
						AllPages:0,					//scroll에 페이지 이동
						//정렬 관련 설정
						Sort : gridConfig.Sort, Sorting : '1',AutoSort : '1', MaxSort : '1', sortIcons : this.sortIcons,
						//행 삭제 관련 설정
						Deleting : '1',ShowDeleted : '0',
						DynamicSpan : gridConfig.DynamicSpan?'1':'0',			//셀 병홥 설정
						Selecting : '1',			//행 선택 설정
						SelectingCells : '0',		//셀 선택 설정
						SelectingSingle : true,	//멀티 선택 --> 우찌 이것만 true, false로 인식
						Pasting : '0',
						Dragging : '0',				//drag & drop 설정
						//Grid 사이즈 설정
						ConstHeight : '1', ConstWidth : '1',
						MaxHeight : '1', MaxWidth : '1', MinHeight : '150', MinTagHeight : '150',//MaxTagHeight : '300',
						//엑셀 export 설정
						ExportFormat : 1,			//1 : xls, 2 : csv 
						ExportType : 'Filtered,Hidden,Strings,Dates',
						Style : this.gridStyle,			//그리드 기본 스타일 설정
						//기타 설정
						NoPager : '1',
						UsePrefix : '1',				//Uses prefix (GS,GL,GO,GM,GB,GP,GR) for custom class names to support all style
						Alternate : this.alternateType,	//Custom style setting, every third row will have different color
						PrintPageOrientation : 1 //Landscape Setting
					}
					,Toolbar : this.toolbar(gridConfig.excelDownloadFn, gridConfig.perPageOnChange, gridConfig.usePaging)
					,Solid : [this.pageSolid()],
			        //체크박스 설정 
			        Panel : {
			        	Width : '21', Visible : '0',CanHide : '0'
					},
					Cols : gridConfig.Cols,
					Header :gridConfig.Header
				}
			},
			Export : {
				Url : "/plm/jsp/common/treegrid/ExcelExport.jsp",
				Data : "TGData",
				Param : {
					File : "KPLUS_Grid_list"
				}
			}
		};
		
		Grids.OnDataSend = showProcessing;    
		Grids.OnDataReceive = hideProcessing;
		
		//false이면 toolbar를 숨김
		if(gridConfig.useToolbar === false){	// '===' 비교 연산자 오타가 아닙니다.
			obj.Layout.Data.Toolbar.Visible = 0;
			delete obj.Layout.Data.Solid;
		}
		
		//false이면 페이징 관련 Solid를 없앰
		if(gridConfig.usePaging === false){	// '===' 비교 연산자 오타가 아닙니다.
			obj.Layout.Data.Cfg.PageLength = 9999999;
			delete obj.Layout.Data.Solid;
		}
		
		//select설정이 있으면 설정 default는 single selection
		if(gridConfig.SelectingSingle){
			obj.Layout.Data.Cfg.SelectingSingle = gridConfig.SelectingSingle;
		}
		//panel 속성(선택 radio)이 있으면 panel 
		if(gridConfig.Panel){
			obj.Layout.Data.Panel = gridConfig.Panel;
		}
		//LeftCols 속성이 있으면 추가
		if(gridConfig.LeftCols){
			obj.Layout.Data.LeftCols = gridConfig.LeftCols;
		}
		//RightCols 속성이 있으면 추가
		if(gridConfig.RightCols){
			obj.Layout.Data.RightCols = gridConfig.RightCols;
		}
		//Head 속성이 있으면 추가
		if(gridConfig.Head){
			delete obj.Layout.Data.Header;
			obj.Layout.Data.Head = gridConfig.Head;
		}
		//Data 속성이 있으면 추가
		if(gridConfig.Body){
			obj.Layout.Data.Body = [gridConfig.Body];
		}
		//MinHeight 속성이 있으면 추가
		if(gridConfig.MinHeight){
			obj.Layout.Data.Cfg.MinHeight = gridConfig.MinHeight;
		}
		//MinTagHeight 속성이 있으면 추가
		if(gridConfig.MinTagHeight){
			obj.Layout.Data.Cfg.MinTagHeight = gridConfig.MinTagHeight;
		}
		//Toolbar 속성이 있으면 추가
		if(gridConfig.Toolbar){
			obj.Layout.Data.Toolbar = gridConfig.Toolbar;
		}
		//컬럼명에 ame(Name)이 들어가면 Excel export시 좌측정렬 되도록 자동 설정
		if(obj.Layout.Data.LeftCols){
			for(var i in obj.Layout.Data.LeftCols){
				obj.Layout.Data.LeftCols[i].CanExport=1;
				if(obj.Layout.Data.LeftCols[i].Name != null && obj.Layout.Data.LeftCols[i].Name.indexOf('ame') > 0){
					obj.Layout.Data.LeftCols[i].ExportStyle='text-align:left;vertical-align:middle';			
				}else{
					obj.Layout.Data.LeftCols[i].ExportStyle='text-align:center;vertical-align:middle';					
				}
			}
		}
		if(obj.Layout.Data.Cols){
			for(var i in obj.Layout.Data.Cols){
				if(obj.Layout.Data.Cols[i].CanExport == null) obj.Layout.Data.Cols[i].CanExport=1;
				if(obj.Layout.Data.Cols[i].Name != null && obj.Layout.Data.Cols[i].Name.indexOf('ame') > 0){
					obj.Layout.Data.Cols[i].ExportStyle='text-align:left;vertical-align:middle';			
				}else{
					obj.Layout.Data.Cols[i].ExportStyle='text-align:center;vertical-align:middle';					
				}
			}
		}
		
		if(gridConfig.Deleting){
			obj.Layout.Data.Cfg.Deleting = gridConfig.Deleting;
		}
		return obj
	},
	
	/**
	 * Server paing grid 생성을 위한 Object
	 * @param gridConfig
	 * @returns {GridCfgObject}
	 */
	getPagingGridConfig : function(gridConfig){
		var obj = this.getGridConfig(gridConfig);
		obj.Layout.Data.Cfg.Paging = 3;
		return obj;
	},
	
	/**
	 * set paging toolbar
	 */
	pageSolid : function(){
		return {
        	id:'PAGER', Cells:'PAGER,LIST', Space:'4', Calculated:'1', PAGERCanEdit:'1', NAVType:'Pager',
            PAGERType:'Pager', PAGERRelWidth:'1', PAGERAlign:'left', PAGEREditWidth:'70', PAGEREditHeight:'15',
            LISTType:'Pages', LISTWrap:'1', LISTRelWidth:'1', LISTAlign:'left', LISTLeft:'0', LISTCount:'20',
            Formula:'Grids.LoadedCount'
        };
	},
	
	/**
	 * set toolbar
	 */
	toolbar : function(excelDownFnName, perPageOnChange, usePaging){
		var toolbarObj = {
			id : 'TOOLBAR',
			Cells : 'Reload,Export,Print,Columns,Link,Empty1,Sap,SapTxt,excelDown,perPage,Formula',
            Styles : '2', Space:'0', Kind:'Topbar', Link:'0',
			Empty1RelWidth:'1', Empty1Type:'Html',
			excelDownType:'Img', excelDownIcon:'/plm/portal/images/iocn_excel.png', excelDownWidth:'16', excelDownCanEdit:'0',
			excelDownOnClick:'javascript:'+excelDownFnName,
			perPageType:'Enum', perPageWidth:'50', perPageEnumKeys:this.pagingList, perPageEnum:this.pagingNameList, perPage:this.pageSize,
			perPageOnChange:perPageOnChange,
			Formula:"'"+LocaleUtil.getMessage('02499')+": <b>'+((count()>0)?Grid.Body.childNodes.length:0)+'</b>  /  "+ LocaleUtil.getMessage('02496') + ": <b>'+Grid.RootCount+'</b> '"
		};
		
		//excelDownFnName 없으면 excel 다운로드 아이콘을 감춘다.
		if(!excelDownFnName){
			toolbarObj.Cells = 'Reload,Export,Print,Columns,Link,Empty1,perPage,Formula';
		}
		
		//usePaging 이 false 면 페이징 관련 항목을 감춘다
		if(usePaging === false){
			toolbarObj.Formula = "";
			toolbarObj.Cells = 'Reload,Export,Print,Columns,Link,Empty1';
		}
		return toolbarObj;
	},
	
	getHtmlPrefix : function(){
		return '<font color="'+this.linkColor+'">';
	},
	
	getHtmlPostfix : function(){
		return '</font>';
	}
};