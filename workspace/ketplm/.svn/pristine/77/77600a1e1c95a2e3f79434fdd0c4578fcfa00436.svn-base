var ViewAssessSheet = {
	
	//client Paging Grid
	createPaingGrid : function() {
		var gridConfig = {
			id : 'ViewAssessSheetSearchGrid',
			Sort : 'orderNo',
			Data : {
				Url : '/plm/ext/project/gate/findViewVerGateCheckSheet.do?oid='+$('[name=oid]').val()+'&versionNo='+$('[name=versionNo]').val()+'&sortName=*Sort0', // 
				Method : 'POST',
				Format : 'Form',
				Param : {
					formPage : (Grids['ViewAssessSheetSearchGrid'])?Grids['ViewAssessSheetSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},

			Head :
			[
//				[
//				,
			 {
				 id : 'Header',
				 Align : 'Center',
				orderNo : LocaleUtil.getMessage('01981'),//'순서',
				 Spanned : '1',
				 targetType : LocaleUtil.getMessage('07134'),//'목표구분',
				 checkSheetName : LocaleUtil.getMessage('02989'),//'평가항목',
				 achieveBase : LocaleUtil.getMessage('07135'),//'달성기준',
//				 unit : '단위',
//				 criteria : '기준',
				 targetTypeRowSpan:'2', 
				 targetTypeWrap:'0',
				 checkSheetNameRowSpan:'2', 
				 achieveBaseRowSpan:'2',
				 unitRowSpan:'2', 
				 criteriaRowSpan:'2', 
				 select1Span:'2', 
				 select2Span:'2', 
				 select3Span:'2', 
				 select4Span:'2', 
				 select5Span:'2', 
				 select6Span:'2', 
				 select7Span:'2', 
				 select8Span:'2', 
				 select9Span:'2', 
				 select10Span:'2', 
				 select11Span:'2', 
				 select12Span:'2', 
				 select13Span:'2', 
				 select14Span:'2', 
				 select15Span:'2', 
				 select16Span:'2', 
				 select17Span:'2', 
				 select18Span:'2', 
				 select19Span:'2', 
				 select20Span:'2', 
				 select1 : $('[name=gateExistG1Name]').val() + '(' + $('[name=GATE1_CNT]').val() + ')',
				 target1 : '',
				 select2 : $('[name=gateExistG2Name]').val() + '(' + $('[name=GATE2_CNT]').val() + ')',
				 target2 : '',
				 select3 : $('[name=gateExistG3Name]').val() + '(' + $('[name=GATE3_CNT]').val() + ')',
				 target3 : '',
				 select4 : $('[name=gateExistG4Name]').val() + '(' + $('[name=GATE4_CNT]').val() + ')',
				 target4 : '',
				 select5 : $('[name=gateExistG5Name]').val() + '(' + $('[name=GATE5_CNT]').val() + ')',
				 target5 : '',
				 select6 : $('[name=gateExistG6Name]').val() + '(' + $('[name=GATE6_CNT]').val() + ')',
				 target6 : '',
				 select7 : $('[name=gateExistG7Name]').val() + '(' + $('[name=GATE7_CNT]').val() + ')',
				 target7 : '',
				 select8 : $('[name=gateExistG8Name]').val() + '(' + $('[name=GATE8_CNT]').val() + ')',
				 target8 : '',
				 select9 : $('[name=gateExistG9Name]').val() + '(' + $('[name=GATE9_CNT]').val() + ')',
				 target9 : '',
				 select10 : $('[name=gateExistG10Name]').val() + '(' + $('[name=GATE10_CNT]').val() + ')',
				 target10 : '',
				 select11 : $('[name=gateExistG11Name]').val() + '(' + $('[name=GATE11_CNT]').val() + ')',
				 target11 : '',
				 select12 : $('[name=gateExistG12Name]').val() + '(' + $('[name=GATE12_CNT]').val() + ')',
				 target12 : '',
				 select13 : $('[name=gateExistG13Name]').val() + '(' + $('[name=GATE13_CNT]').val() + ')',
				 target13 : '',
				 select14 : $('[name=gateExistG14Name]').val() + '(' + $('[name=GATE14_CNT]').val() + ')',
				 target14 : '',
				 select15 : $('[name=gateExistG15Name]').val() + '(' + $('[name=GATE15_CNT]').val() + ')',
				 target15 : '',
				 select16 : $('[name=gateExistG16Name]').val() + '(' + $('[name=GATE16_CNT]').val() + ')',
				 target16 : '',
				 select17 : $('[name=gateExistG17Name]').val() + '(' + $('[name=GATE17_CNT]').val() + ')',
				 target17 : '',
				 select18 : $('[name=gateExistG18Name]').val() + '(' + $('[name=GATE18_CNT]').val() + ')',
				 target18 : '',
				 select19 : $('[name=gateExistG19Name]').val() + '(' + $('[name=GATE19_CNT]').val() + ')',
				 target19 : '',
				 select20 : $('[name=gateExistG20Name]').val() + '(' + $('[name=GATE20_CNT]').val() + ')',
				 target20 : ''
			 }
			 ,
			 {
				 id : 'HeaderTop',
				 Spanned : '1',
				 Align : 'Center',
				 CanEdit:'0',
//					 targetType : '목표구분',
//					 checkSheetName : '평가항목',
//					 achieveBase : '달성기준',
//					 unit : '단위',
//					 criteria : '기준',
				 select1 : LocaleUtil.getMessage('01802'),//'선택',
				 target1 : LocaleUtil.getMessage('01381'),//'목표',
				 select2 : LocaleUtil.getMessage('01802'),//'선택',
				 target2 : LocaleUtil.getMessage('01381'),//'목표',
				 select3 : LocaleUtil.getMessage('01802'),//'선택',
				 target3 : LocaleUtil.getMessage('01381'),//'목표',
				 select4 : LocaleUtil.getMessage('01802'),//'선택',
				 target4 : LocaleUtil.getMessage('01381'),//'목표',
				 select5 : LocaleUtil.getMessage('01802'),//'선택',
				 target5 : LocaleUtil.getMessage('01381'),//'목표',
				 select6 : LocaleUtil.getMessage('01802'),//'선택',
				 target6 : LocaleUtil.getMessage('01381'),//'목표',
				 select7 : LocaleUtil.getMessage('01802'),//'선택',
				 target7 : LocaleUtil.getMessage('01381'),//'목표',
				 select8 : LocaleUtil.getMessage('01802'),//'선택',
				 target8 : LocaleUtil.getMessage('01381'),//'목표',
				 select9 : LocaleUtil.getMessage('01802'),//'선택',
				 target9 : LocaleUtil.getMessage('01381'),//'목표',
				 select10 : LocaleUtil.getMessage('01802'),//'선택',
				 target10 : LocaleUtil.getMessage('01381'),//'목표',
				 select11 : LocaleUtil.getMessage('01802'),//'선택',
				 target11 : LocaleUtil.getMessage('01381'),//'목표',
				 select12 : LocaleUtil.getMessage('01802'),//'선택',
				 target12 : LocaleUtil.getMessage('01381'),//'목표',
				 select13 : LocaleUtil.getMessage('01802'),//'선택',
				 target13 : LocaleUtil.getMessage('01381'),//'목표',
				 select14 : LocaleUtil.getMessage('01802'),//'선택',
				 target14 : LocaleUtil.getMessage('01381'),//'목표',
				 select15 : LocaleUtil.getMessage('01802'),//'선택',
				 target15 : LocaleUtil.getMessage('01381'),//'목표',
				 select16 : LocaleUtil.getMessage('01802'),//'선택',
				 target16 : LocaleUtil.getMessage('01381'),//'목표',
				 select17 : LocaleUtil.getMessage('01802'),//'선택',
				 target17 : LocaleUtil.getMessage('01381'),//'목표',
				 select18 : LocaleUtil.getMessage('01802'),//'선택',
				 target18 : LocaleUtil.getMessage('01381'),//'목표',
				 select19 : LocaleUtil.getMessage('01802'),//'선택',
				 target19 : LocaleUtil.getMessage('01381'),//'목표',
				 select20 : LocaleUtil.getMessage('01802'),//'선택',
				 target20 : LocaleUtil.getMessage('01381') //'목표'
			 }
			]
			,
			LeftCols : [
					    {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
					    {Name:'orderNo', Width:80, Align:'Center', CanSort:'0', Visible:'0'},
					    {Name:'targetType', RelWidth:80, Width:80, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1', Type:'Enum', EnumKeys:$('[name=targetTypeEnumKey]').val(),  Enum:$('[name=targetTypeEnum]').val()},
					    {Name:'checkSheetName', RelWidth:180, Width:180, Align:'left', CanSort:'1', CanEdit:'0', Spanned:'1'},
					    {Name:'achieveBase', RelWidth:80, Width:80, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1'}
			],
			Cols : [
//			    {Name:'unit', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1'},
//			    {Name:'criteria', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned:'1', Type:'Enum', EnumKeys:$('[name=creteriaEnumKey]').val(),  Enum:$('[name=creteriaEnum]').val()},
			    {Name:'select1', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG1]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target1', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG1]').val()},
			    {Name:'select2', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG2]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target2', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG2]').val()},
			    {Name:'select3', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG3]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target3', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG3]').val()},
			    {Name:'select4', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG4]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target4', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG4]').val()},
			    {Name:'select5', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG5]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target5', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG5]').val()},
			    {Name:'select6', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG6]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target6', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG6]').val()},
			    {Name:'select7', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG7]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target7', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG7]').val()},
			    {Name:'select8', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG8]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target8', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG8]').val()},
			    {Name:'select9', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG9]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target9', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG9]').val()},
			    {Name:'select10', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG10]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target10', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG10]').val()},
			    {Name:'select11', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG11]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target11', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG11]').val()},
			    {Name:'select12', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG12]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target12', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG12]').val()},
			    {Name:'select13', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG13]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target13', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG13]').val()},
			    {Name:'select14', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG14]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target14', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG14]').val()},
			    {Name:'select15', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG15]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target15', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG15]').val()},
			    {Name:'select16', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG16]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target16', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG16]').val()},
			    {Name:'select17', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG17]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target17', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG17]').val()},
			    {Name:'select18', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG18]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target18', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG18]').val()},
			    {Name:'select19', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG19]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target19', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG19]').val()},
			    {Name:'select20', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG20]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
			    {Name:'target20', Width:55, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG20]').val()}
			]
		};
		var visibleCount = 0;
		for(var i=0;i<gridConfig.Cols.length;i++){
			if(gridConfig.Cols[i].Visible == '1'){
				visibleCount++;
			}
		}
		if(visibleCount > 8){
			alert(visibleCount);
			for(var i=0;i<gridConfig.LeftCols.length;i++){
				if(gridConfig.LeftCols[i]['RelWidth']){
					delete gridConfig.LeftCols[i].RelWidth;
				}
			}
		}
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig(gridConfig), 'listCheckGrid');
	
		//Grids.OnClick = ViewAssessSheet.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findViewVerGateCheckSheet.do?oid='+$('[name=pjtOid]').val()+'&versionNo='+$('[name=versionNo]').val()+'&sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				targetType : $('[name=targetType]').val(),
				checkSheetName : $('[name=checkSheetName]').val(),
				achieveBase : $('[name=achieveBase]').val(),
				unit : $('[name=unit]').val(),
				criteria : $('[name=criteria]').val(),
				select1 : $('[name=select1]').val(),
				target1 : $('[name=target1]').val()
			}
		}
	},

	search : function(perPage) {
		if(!perPage) perPage = Grids['ViewAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['ViewAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['ViewAssessSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=ViewAssessSheetSearchForm]').serialize());
		Grids['ViewAssessSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['ViewAssessSheetSearchGrid'].Reload();
	},
	
	clear : function() {
		$('[name=ViewAssessSheetSearchForm]')[0].reset();
	},
	
	
	goView : function(grid, row, col, x, y) {
//		getOpenWindow2('/plm/ext/project/gate/viewTemplateAssessForm.do?oid='+row.oid,'TemplateAssessSheetViewPopup',900,650);
	},
	
}
