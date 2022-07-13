var CreateTemplateAssessSheet = {
	
	//client Paging Grid
	createPaingGrid : function() {
		var gridConfig = {
				id : 'CreateTemplateAssessSheetSearchGrid',
				Data : {
					Url : '/plm/ext/project/gate/findListTemplateGateCheckSheet.do?pjtOid='+$('[name=oid]').val()+'&sortName=*Sort0', // 
					Method : 'POST'
					,
					Format : 'Form',
					Param : {
						formPage : (Grids['CreateTemplateAssessSheetSearchGrid'])?Grids['CreateTemplateAssessSheetSearchGrid'].PageLength:CommonGrid.pageSize
					}
				},
//				Upload : {
//					Url : '',
//					Format : 'JSON'
//				},
				Sort : 'orderNo',
				perPageOnChange : 'javascript:CreateTemplateAssessSheet.search(Value);',
				LeftCols : [
							{Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0', Spanned : '1'},
						    {Name:'orderNo', Visible:'0'},
						    {Name:'rowNum', Width:'30', Align:'Center', CanSort:'0', CanEdit:'0', Spanned : '1'},
						    {Name:'targetType', Width:80, Align:'Center', CanSort:'1', CanEdit:'0', Spanned : '1', Type:'Enum', EnumKeys:$('[name=targetTypeEnumKey]').val(),  Enum:$('[name=targetTypeEnum]').val()},
						    {Name:'checkSheetName', Width:260, Align:'left', CanSort:'1', Spanned : '1', CanEdit:'0'},
						    {Name:'achieveBase', Width:80, Align:'left', CanSort:'1', Spanned : '1', CanEdit:'0'},
				],
				Cols : [
				    //{Name:'unit', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned : '1'},
				    //{Name:'criteria', Width:50, Align:'Center', CanSort:'1', CanEdit:'0', Spanned : '1'},
				    {Name:'select1', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG1]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target1', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG1]').val()},
				    {Name:'select7', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG7]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target7', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG7]').val()},
				    {Name:'select2', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG2]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target2', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG2]').val()},
				    {Name:'select8', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG8]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target8', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG8]').val()},
				    {Name:'select3', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG3]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target3', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG3]').val()},
				    {Name:'select9', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG9]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target9', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG9]').val()},
				    {Name:'select10', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG10]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target10', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG10]').val()},
				    {Name:'select4', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG4]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target4', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG4]').val()},
				    {Name:'select5', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG5]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target5', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG5]').val()},
				    {Name:'select6', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG6]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target6', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG6]').val()},
				    {Name:'select11', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG11]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target11', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG11]').val()},
				    {Name:'select12', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG12]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target12', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG12]').val()},
				    {Name:'select13', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG13]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target13', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG13]').val()},
				    {Name:'select14', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG14]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target14', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG14]').val()},
				    {Name:'select15', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG15]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target15', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG15]').val()},
				    {Name:'select16', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG16]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target16', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG16]').val()},
				    {Name:'select17', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG17]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target17', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG17]').val()},
				    {Name:'select18', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG18]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target18', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG18]').val()},
				    {Name:'select19', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG19]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target19', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG19]').val()},
				    {Name:'select20', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG20]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target20', Width:50, Align:'Center', CanSort:'0', CanEdit:'1', Spanned:'1', Visible:$('[name=gateExistG20]').val()}
				],
				Head :
					[
					 {
						 CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
						 id : 'Header', Spanned : '1',
//							 CanDelete : '0',
						 rowNum :'No', rowNumRowSpan:'2',
						 targetType : LocaleUtil.getMessage('07134'), targetTypeRowSpan:'2', //targetTypeWrap:'0',	//'목표구분',
						 checkSheetName : LocaleUtil.getMessage('02989'), checkSheetNameRowSpan:'2',	//'평가항목',
						 achieveBase : LocaleUtil.getMessage('07135'), achieveBaseRowSpan:'2',	//'달성기준',
						 unit :  LocaleUtil.getMessage('01194'), unitRowSpan:'2',	//'단위',
						 criteria : LocaleUtil.getMessage('07136'), criteriaRowSpan:'2',		//'기준',
						 select1Span:'2', 
						 select7Span:'2',
						 select2Span:'2',
						 select8Span:'2',
						 select3Span:'2',
						 select9Span:'2', 
						 select10Span:'2',
						 select4Span:'2', 
						 select5Span:'2', 
						 select6Span:'2', 
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
						 select1 : $('[name=gateExistG1Name]').val(),
						 target1 : '',
						 select7 : $('[name=gateExistG7Name]').val(),
						 target7 : '',
						 select2 : $('[name=gateExistG2Name]').val(),
						 target2 : '',
						 select8 : $('[name=gateExistG8Name]').val(),
						 target8 : '',
						 select3 : $('[name=gateExistG3Name]').val(),
						 target3 : '',
						 select9 : $('[name=gateExistG9Name]').val(),
						 target9 : '',
						 select10 : $('[name=gateExistG10Name]').val(),
						 target10 : '',
						 select4 : $('[name=gateExistG4Name]').val(),
						 target4 : '',
						 select5 : $('[name=gateExistG5Name]').val(),
						 target5 : '',
						 select6 : $('[name=gateExistG6Name]').val(),
						 target6 : '',
						 select11 : $('[name=gateExistG11Name]').val(),
						 target11 : '',
						 select12 : $('[name=gateExistG12Name]').val(),
						 target12 : '',
						 select13 : $('[name=gateExistG13Name]').val(),
						 target13 : '',
						 select14 : $('[name=gateExistG14Name]').val(),
						 target14 : '',
						 select15 : $('[name=gateExistG15Name]').val(),
						 target15 : '',
						 select16 : $('[name=gateExistG16Name]').val(),
						 target16 : '',
						 select17 : $('[name=gateExistG17Name]').val(),
						 target17 : '',
						 select18 : $('[name=gateExistG18Name]').val(),
						 target18 : '',
						 select19 : $('[name=gateExistG19Name]').val(),
						 target19 : '',
						 select20 : $('[name=gateExistG20Name]').val(),
						 target20 : ''
					 },
					
					 {
						 CanDelete : '0', CanEdit : '0', Align : 'Center', CanSort : '1',
						 id : 'HeaderTop',
						 orderNo : LocaleUtil.getMessage('01981'),//'순서',
						 //Spanned : '1',
						 select1 : LocaleUtil.getMessage('01802'),//'선택',
						 target1 : LocaleUtil.getMessage('01381'),//'목표',
						 select7 : LocaleUtil.getMessage('01802'),//'선택',
						 target7 : LocaleUtil.getMessage('01381'),//'목표',
						 select2 : LocaleUtil.getMessage('01802'),//'선택',
						 target2 : LocaleUtil.getMessage('01381'),//'목표',
						 select8 : LocaleUtil.getMessage('01802'),//'선택',
						 target8 : LocaleUtil.getMessage('01381'),//'목표',
						 select3 : LocaleUtil.getMessage('01802'),//'선택',
						 target3 : LocaleUtil.getMessage('01381'),//'목표',
						 select9 : LocaleUtil.getMessage('01802'),//'선택',
						 target9 : LocaleUtil.getMessage('01381'),//'목표',
						 select10 : LocaleUtil.getMessage('01802'),//'선택',
						 target10 : LocaleUtil.getMessage('01381'),//'목표',
						 select4 : LocaleUtil.getMessage('01802'),//'선택',
						 target4 : LocaleUtil.getMessage('01381'),//'목표',
						 select5 : LocaleUtil.getMessage('01802'),//'선택',
						 target5 : LocaleUtil.getMessage('01381'),//'목표',
						 select6 : LocaleUtil.getMessage('01802'),//'선택',
						 target6 : LocaleUtil.getMessage('01381'),//'목표',
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
			};

		var visibleCount = 0;
		for(var i=0;i<gridConfig.Cols.length;i++){
			if(gridConfig.Cols[i].Visible == '1'){
				visibleCount++;
			}
		}
		if(visibleCount > 6){
			for(var i=0;i<gridConfig.LeftCols.length;i++){
				if(gridConfig.LeftCols[i]['RelWidth']){
					delete gridConfig.LeftCols[i].RelWidth;
				}
			}
		}
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig(gridConfig), 'listCheckGrid');
	
		//Grids.OnClick = CreateTemplateAssessSheet.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findListTemplateGateCheckSheet.do?pjtOid='+$('[name=oid]').val()+'&sortName=*Sort0';
			grid.Data.Page.Param = {
				page : grid.GetPageNum(row),
				formPage : grid.PageLength,
				targetType : $('[name=targetType]').val(),
				checkSheetName : $('[name=checkSheetName]').val(),
				achieveBase : $('[name=achieveBase]').val(),
				unit : $('[name=unit]').val(),
				criteria : $('[name=criteria]').val(),
				select1 : $('[name=select1]').val(),
				target1 : $('[name=target1]').val(),
				select2 : $('[name=select2]').val(),
				target2 : $('[name=target2]').val(),
				select3 : $('[name=select3]').val(),
				target3 : $('[name=target3]').val(),
				select4 : $('[name=select4]').val(),
				target4 : $('[name=target4]').val(),
				select5 : $('[name=select5]').val(),
				target5 : $('[name=target5]').val(),
				select6 : $('[name=select6]').val(),
				target6 : $('[name=target6]').val(),
				select7 : $('[name=select7]').val(),
				target7 : $('[name=target7]').val(),
				select8 : $('[name=select8]').val(),
				target8 : $('[name=target8]').val(),
				select9 : $('[name=select9]').val(),
				target9 : $('[name=target9]').val(),
				select10 : $('[name=select10]').val(),
				target10 : $('[name=target10]').val(),
				select11 : $('[name=select11]').val(),
				target11 : $('[name=target11]').val(),
				select12 : $('[name=select12]').val(),
				target12 : $('[name=target12]').val(),
				select13 : $('[name=select13]').val(),
				target13 : $('[name=target13]').val(),
				select14 : $('[name=select14]').val(),
				target14 : $('[name=target14]').val(),
				select15 : $('[name=select15]').val(),
				target15 : $('[name=target15]').val(),
				select16 : $('[name=select16]').val(),
				target16 : $('[name=target16]').val(),
				select17 : $('[name=select17]').val(),
				target17 : $('[name=target17]').val(),
				select18 : $('[name=select18]').val(),
				target18 : $('[name=target18]').val(),
				select19 : $('[name=select19]').val(),
				target19 : $('[name=target19]').val(),
				select20 : $('[name=select20]').val(),
				target20 : $('[name=target20]').val()
			}
		}
	},

	search : function(perPage) {
		if(!perPage) perPage = Grids['CreateTemplateAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['CreateTemplateAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['CreateTemplateAssessSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=CreateTemplateAssessSheetSearchForm]').serialize());
		Grids['CreateTemplateAssessSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['CreateTemplateAssessSheetSearchGrid'].Reload();
		
	},
	
	clear : function() {
		$('[name=CreateTemplateAssessSheetSearchForm]')[0].reset();
	},
	
	/*
	 * 평가시트 수정(updateTemplateAssessForm.jsp)에서 저장버튼(수정) 클릭시
	 */
	updateTemplateGateCheckSheetList : function() {
		var G = Grids[0];
		G.Data.Upload.Params = decodeURIComponent($('[name=CreateTemplateAssessSheetUpdateForm]').serialize());
		G.Data.Upload.Url = '/plm/ext/project/gate/updateTemplateAssessActive.do?activeState='+$('[name=active]').val();
		G.Data.Upload.Method = 'Form';	//Form
		G.Data.Upload.Data='gridData';
		G.Data.Upload.Format = 'JSON';
		G.Data.Upload.Type = 'All';
		G.Data.Upload.Flags = 'AllCols';
		G.Data.Upload_Xml = 2;
		
		G.Save();	
		return 'success';
		
	},

	
	goList : function() {
		location.href = '/plm/ext/project/gate/listTemplateAssess.do';
	},
	
	update : function() {
		location.href = '/plm/ext/project/gate/updateTemplateAssessForm.do?oid='+$('[name=oid]').val();
	},

	deleteObj : function() {
		if(confirm(LocaleUtil.getMessage('01707'))) {//삭제하시겠습니까?
			location.href = '/plm/ext/project/gate/deleteTemplateAssess.do?oid='+$('[name=oid]').val();
			return 'success';
		}
	}
	
}
