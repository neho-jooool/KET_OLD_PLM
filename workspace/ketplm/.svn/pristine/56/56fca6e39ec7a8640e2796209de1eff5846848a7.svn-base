var CreateAssessSheet = {
	
	//client Paging Grid
	createPaingGrid : function() {
		var gridConfig = {
				id : 'CreateAssessSheetSearchGrid',
				Sort : 'orderNo',
				Data : {
					Url : '/plm/ext/project/gate/findListGateCheckSheet.do?oid='+$('[name=pjtOid]').val()+'&sortName=*Sort0', // 
					Method : 'POST',
					Format : 'Form',
					Param : {
						formPage : (Grids['CreateAssessSheetSearchGrid'])?Grids['CreateAssessSheetSearchGrid'].PageLength:CommonGrid.pageSize
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
//					 unit : LocaleUtil.getMessage('01194'),//'단위',
					 criteria : LocaleUtil.getMessage('07136'),//'기준',
					 targetTypeRowSpan:'2', 
					 targetTypeWrap:'0',
					 checkSheetNameRowSpan:'2', 
					 achieveBaseRowSpan:'2',
					 unitRowSpan:'2', 
					 criteriaRowSpan:'2', 
					 select1Span:'3', 
					 select7Span:'3',
					 select2Span:'3',
					 select8Span:'3',
					 select3Span:'3',
					 select9Span:'3', 
					 select10Span:'3',
					 select4Span:'3', 
					 select5Span:'3', 
					 select6Span:'3', 
					 select11Span:'3', 
					 select12Span:'3', 
					 select13Span:'3', 
					 select14Span:'3', 
					 select15Span:'3', 
					 select16Span:'3', 
					 select17Span:'3', 
					 select18Span:'3', 
					 select19Span:'3', 
					 select20Span:'3', 
					 select1 : $('[name=gateExistG1Name]').val() + ' (' + $('[name=GATE1_CNT]').val() + ')',
					 target1 : '',
					 result1 : '',
					 select7 : $('[name=gateExistG7Name]').val() + ' (' + $('[name=GATE7_CNT]').val() + ')',
					 target7 : '',
					 result7 : '',
					 select2 : $('[name=gateExistG2Name]').val() + ' (' + $('[name=GATE2_CNT]').val() + ')',
					 target2 : '',
					 result2 : '',
					 select8 : $('[name=gateExistG8Name]').val() + ' (' + $('[name=GATE8_CNT]').val() + ')',
					 target8 : '',
					 result8 : '',
					 select3 : $('[name=gateExistG3Name]').val() + ' (' + $('[name=GATE3_CNT]').val() + ')',
					 target3 : '',
					 result3 : '',
					 select9 : $('[name=gateExistG9Name]').val() + ' (' + $('[name=GATE9_CNT]').val() + ')',
					 target9 : '',
					 result9 : '',
					 select10 : $('[name=gateExistG10Name]').val() + ' (' + $('[name=GATE10_CNT]').val() + ')',
					 target10 : '',
					 result10 : '',
					 select4 : $('[name=gateExistG4Name]').val() + ' (' + $('[name=GATE4_CNT]').val() + ')',
					 target4 : '',
					 result4 : '',
					 select5 : $('[name=gateExistG5Name]').val() + ' (' + $('[name=GATE5_CNT]').val() + ')',
					 target5 : '',
					 result5 : '',
					 select6 : $('[name=gateExistG6Name]').val() + ' (' + $('[name=GATE6_CNT]').val() + ')',
					 target6 : '',
					 result6 : '',
					 select11 : $('[name=gateExistG11Name]').val() + ' (' + $('[name=GATE11_CNT]').val() + ')',
					 target11 : '',
					 result11 : '',
					 select12 : $('[name=gateExistG12Name]').val() + ' (' + $('[name=GATE12_CNT]').val() + ')',
					 target12 : '',
					 result12 : '',
					 select13 : $('[name=gateExistG13Name]').val() + ' (' + $('[name=GATE13_CNT]').val() + ')',
					 target13 : '',
					 result13 : '',
					 select14 : $('[name=gateExistG14Name]').val() + ' (' + $('[name=GATE14_CNT]').val() + ')',
					 target14 : '',
					 result14 : '',
					 select15 : $('[name=gateExistG15Name]').val() + ' (' + $('[name=GATE15_CNT]').val() + ')',
					 target15 : '',
					 result15 : '',
					 select16 : $('[name=gateExistG16Name]').val() + ' (' + $('[name=GATE16_CNT]').val() + ')',
					 target16 : '',
					 result16 : '',
					 select17 : $('[name=gateExistG17Name]').val() + ' (' + $('[name=GATE17_CNT]').val() + ')',
					 target17 : '',
					 result17 : '',
					 select18 : $('[name=gateExistG18Name]').val() + ' (' + $('[name=GATE18_CNT]').val() + ')',
					 target18 : '',
					 result18 : '',
					 select19 : $('[name=gateExistG19Name]').val() + ' (' + $('[name=GATE19_CNT]').val() + ')',
					 target19 : '',
					 result19 : '',
					 select20 : $('[name=gateExistG20Name]').val() + ' (' + $('[name=GATE20_CNT]').val() + ')',
					 target20 : '',
					 result20 : ''
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
					 result1 : LocaleUtil.getMessage('02032'),//'실적',
					 select7 : LocaleUtil.getMessage('01802'),//'선택',
					 target7 : LocaleUtil.getMessage('01381'),//'목표',
					 result7 : LocaleUtil.getMessage('02032'),//'실적',
					 select2 : LocaleUtil.getMessage('01802'),//'선택',
					 target2 : LocaleUtil.getMessage('01381'),//'목표',
					 result2 : LocaleUtil.getMessage('02032'),//'실적',
					 select8 : LocaleUtil.getMessage('01802'),//'선택',
					 target8 : LocaleUtil.getMessage('01381'),//'목표',
					 result8 : LocaleUtil.getMessage('02032'),//'실적',
					 select3 : LocaleUtil.getMessage('01802'),//'선택',
					 target3 : LocaleUtil.getMessage('01381'),//'목표',
					 result3 : LocaleUtil.getMessage('02032'),//'실적',
					 select9 : LocaleUtil.getMessage('01802'),//'선택',
					 target9 : LocaleUtil.getMessage('01381'),//'목표',
					 result9 : LocaleUtil.getMessage('02032'),//'실적',
					 select10 : LocaleUtil.getMessage('01802'),//'선택',
					 target10 : LocaleUtil.getMessage('01381'),//'목표',
					 result10 : LocaleUtil.getMessage('02032'),//'실적',
					 select4 : LocaleUtil.getMessage('01802'),//'선택',
					 target4 : LocaleUtil.getMessage('01381'),//'목표',
					 result4 : LocaleUtil.getMessage('02032'),//'실적',
					 select5 : LocaleUtil.getMessage('01802'),//'선택',
					 target5 : LocaleUtil.getMessage('01381'),//'목표',
					 result5 : LocaleUtil.getMessage('02032'),//'실적',
					 select6 : LocaleUtil.getMessage('01802'),//'선택',
					 target6 : LocaleUtil.getMessage('01381'),//'목표',
					 result6 : LocaleUtil.getMessage('02032'),//'실적',
					 select11 : LocaleUtil.getMessage('01802'),//'선택',
					 target11 : LocaleUtil.getMessage('01381'),//'목표',
					 result11 : LocaleUtil.getMessage('02032'),//'실적',
					 select12 : LocaleUtil.getMessage('01802'),//'선택',
					 target12 : LocaleUtil.getMessage('01381'),//'목표',
					 result12 : LocaleUtil.getMessage('02032'),//'실적',
					 select13 : LocaleUtil.getMessage('01802'),//'선택',
					 target13 : LocaleUtil.getMessage('01381'),//'목표',
					 result13 : LocaleUtil.getMessage('02032'),//'실적',
					 select14 : LocaleUtil.getMessage('01802'),//'선택',
					 target14 : LocaleUtil.getMessage('01381'),//'목표',
					 result14 : LocaleUtil.getMessage('02032'),//'실적',
					 select15 : LocaleUtil.getMessage('01802'),//'선택',
					 target15 : LocaleUtil.getMessage('01381'),//'목표',
					 result15 : LocaleUtil.getMessage('02032'),//'실적',
					 select16 : LocaleUtil.getMessage('01802'),//'선택',
					 target16 : LocaleUtil.getMessage('01381'),//'목표',
					 result16 : LocaleUtil.getMessage('02032'),//'실적',
					 select17 : LocaleUtil.getMessage('01802'),//'선택',
					 target17 : LocaleUtil.getMessage('01381'),//'목표',
					 result17 : LocaleUtil.getMessage('02032'),//'실적',
					 select18 : LocaleUtil.getMessage('01802'),//'선택',
					 target18 : LocaleUtil.getMessage('01381'),//'목표',
					 result18 : LocaleUtil.getMessage('02032'),//'실적',
					 select19 : LocaleUtil.getMessage('01802'),//'선택',
					 target19 : LocaleUtil.getMessage('01381'),//'목표',
					 result19 : LocaleUtil.getMessage('02032'),//'실적',
					 select20 : LocaleUtil.getMessage('01802'),//'선택',
					 target20 : LocaleUtil.getMessage('01381'),//'목표'
					 result20 : '실적'
				 }
				]
				,
				LeftCols : [
						    {Name:'oid', Width:0, Align:'Center', CanSort:'0', Visible:'0'},
						    {Name:'orderNo', Width:80, Align:'Center', CanSort:'0', Visible:'0'},
						    {Name:'targetType', RelWidth:80, Width:80, Align:'Center', CanSort:'0', CanEdit:$('[name=canEdit]').val(), Spanned:'1', Type:'Enum', EnumKeys:$('[name=targetTypeEnumKey]').val(),  Enum:$('[name=targetTypeEnum]').val()},
						    {Name:'checkSheetName', RelWidth:180, Width:180, Align:'left', CanSort:'0', CanEdit:$('[name=canEdit]').val(),  Spanned:'1'},
						    {Name:'achieveBase', RelWidth:80, Width:80, Align:'left', CanSort:'0', CanEdit:$('[name=canEdit]').val(),  Spanned:'1'},
				],
				Cols : [
				    //{Name:'unit', Width:50, RelWidth:'50', Align:'Center', CanSort:'1', CanEdit:$('[name=canEdit]').val(), Spanned:'1'},
				    //{Name:'criteria', Width:80, Align:'Center', CanSort:'0', CanEdit:$('[name=canEdit]').val(), Spanned:'1'},
				    {Name:'select1', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG1]').val(), Spanned:'1', Visible:$('[name=gateExistG1]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●', Spanned:"1"},
				    {Name:'target1', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG1]').val(), Spanned:'1', Visible:$('[name=gateExistG1]').val()},
				    {Name:'result1', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG1]').val()},
				    {Name:'select7', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG7]').val(), Spanned:'1', Visible:$('[name=gateExistG7]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target7', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG7]').val(), Spanned:'1', Visible:$('[name=gateExistG7]').val()},
				    {Name:'result7', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1', Visible:$('[name=gateExistG7]').val()},
				    {Name:'select2', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG2]').val(), Spanned:'1', Visible:$('[name=gateExistG2]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target2', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG2]').val(), Spanned:'1', Visible:$('[name=gateExistG2]').val()},
				    {Name:'result2', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG2]').val()},
				    {Name:'select8', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG8]').val(), Spanned:'1', Visible:$('[name=gateExistG8]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target8', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG8]').val(), Spanned:'1', Visible:$('[name=gateExistG8]').val()},
				    {Name:'result8', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG8]').val()},
				    {Name:'select3', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG3]').val(), Spanned:'1', Visible:$('[name=gateExistG3]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target3', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG3]').val(), Spanned:'1', Visible:$('[name=gateExistG3]').val()},
				    {Name:'result3', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG3]').val()},
				    {Name:'select9', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG9]').val(), Spanned:'1', Visible:$('[name=gateExistG9]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target9', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG9]').val(), Spanned:'1', Visible:$('[name=gateExistG9]').val()},
				    {Name:'result9', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG9]').val()},
				    {Name:'select10', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG10]').val(), Spanned:'1', Visible:$('[name=gateExistG10]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target10', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG10]').val(), Spanned:'1', Visible:$('[name=gateExistG10]').val()},
				    {Name:'result10', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG10]').val()},
				    {Name:'select4', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG4]').val(), Spanned:'1', Visible:$('[name=gateExistG4]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target4', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG4]').val(), Spanned:'1', Visible:$('[name=gateExistG4]').val()},
				    {Name:'result4', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG4]').val()},
				    {Name:'select5', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG5]').val(), Spanned:'1', Visible:$('[name=gateExistG5]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target5', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG5]').val(), Spanned:'1', Visible:$('[name=gateExistG5]').val()},
				    {Name:'result5', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG5]').val()},
				    {Name:'select6', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG6]').val(), Spanned:'1', Visible:$('[name=gateExistG6]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target6', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG6]').val(), Spanned:'1', Visible:$('[name=gateExistG6]').val()},
				    {Name:'result6', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG6]').val()},
				    {Name:'select11', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG11]').val(), Spanned:'1', Visible:$('[name=gateExistG11]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●', Spanned:"1"},
				    {Name:'target11', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG11]').val(), Spanned:'1', Visible:$('[name=gateExistG11]').val()},
				    {Name:'result11', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG11]').val()},
				    {Name:'select12', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG12]').val(), Spanned:'1', Visible:$('[name=gateExistG12]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target12', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG12]').val(), Spanned:'1', Visible:$('[name=gateExistG12]').val()},
				    {Name:'result12', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG12]').val()},
				    {Name:'select13', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG13]').val(), Spanned:'1', Visible:$('[name=gateExistG13]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target13', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG13]').val(), Spanned:'1', Visible:$('[name=gateExistG13]').val()},
				    {Name:'result13', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG13]').val()},
				    {Name:'select14', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG14]').val(), Spanned:'1', Visible:$('[name=gateExistG14]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target14', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG14]').val(), Spanned:'1', Visible:$('[name=gateExistG14]').val()},
				    {Name:'result14', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG14]').val()},
				    {Name:'select15', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG15]').val(), Spanned:'1', Visible:$('[name=gateExistG15]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target15', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG15]').val(), Spanned:'1', Visible:$('[name=gateExistG15]').val()},
				    {Name:'result15', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG15]').val()},
				    {Name:'select16', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG16]').val(), Spanned:'1', Visible:$('[name=gateExistG16]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target16', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG16]').val(), Spanned:'1', Visible:$('[name=gateExistG16]').val()},
				    {Name:'result16', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG16]').val()},
				    {Name:'select17', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG17]').val(), Spanned:'1', Visible:$('[name=gateExistG17]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target17', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG17]').val(), Spanned:'1', Visible:$('[name=gateExistG17]').val()},
				    {Name:'result17', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG17]').val()},
				    {Name:'select18', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG18]').val(), Spanned:'1', Visible:$('[name=gateExistG18]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target18', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG18]').val(), Spanned:'1', Visible:$('[name=gateExistG18]').val()},
				    {Name:'result18', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG18]').val()},
				    {Name:'select19', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG19]').val(), Spanned:'1', Visible:$('[name=gateExistG19]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target19', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG19]').val(), Spanned:'1', Visible:$('[name=gateExistG19]').val()},
				    {Name:'result19', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG19]').val()},
				    {Name:'select20', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG20]').val(), Spanned:'1', Visible:$('[name=gateExistG20]').val(), Type:'Enum', EnumKeys:'|0|1',  Enum:'||●'},
				    {Name:'target20', Width:50, Align:'Center', CanSort:'0', CanEdit:$('[name=gateCanEditG20]').val(), Spanned:'1', Visible:$('[name=gateExistG20]').val()},
				    {Name:'result20', Width:50, Align:'Center', CanSort:'0', CanEdit:'0', Spanned:'1',Visible:$('[name=gateExistG20]').val()}
				]
				,
				SelectingSingle : '0',
				Deleting : '0',
				Panel : {
					Width : '20', Visible : $('[name=canEdit]').val(),CanHide : '0'
				}
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
	
		//Grids.OnClick = CreateAssessSheet.goView;
		
		Grids.OnDownloadPage = function(grid, row) {
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/project/gate/findListGateCheckSheet.do?oid='+$('[name=pjtOid]').val()+'&sortName=*Sort0';
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
//				,
//				select2 : $('[name=select2]').val(),
//				target2 : $('[name=target2]').val(),
//				select3 : $('[name=select3]').val(),
//				target3 : $('[name=target3]').val(),
//				select4 : $('[name=select4]').val(),
//				target5 : $('[name=target5]').val(),
//				target6 : $('[name=target6]').val(),
//				target7 : $('[name=target7]').val(),
//				target8 : $('[name=target8]').val(),
//				target9 : $('[name=target9]').val(),
//				target10 : $('[name=target10]').val()
			}
		}
	},

	search : function(perPage) {
		if(!perPage) perPage = Grids['CreateAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['CreateAssessSheetSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['CreateAssessSheetSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=CreateAssessSheetSearchForm]').serialize());
		Grids['CreateAssessSheetSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['CreateAssessSheetSearchGrid'].Reload();
	},
	
	clear : function() {
		$('[name=CreateAssessSheetSearchForm]')[0].reset();
	},
	
	/*
	 * 평가시트 수정(updateAssessForm.jsp)에서 저장버튼(수정) 클릭시
	 */
	updateGateCheckSheetList : function() {
		var G = Grids[0];
		G.Data.Upload.Params = decodeURIComponent($('[name=CreateAssessSheetUpdateForm]').serialize());
		G.Data.Upload.Url = '/plm/ext/project/gate/updateGateCheckSheetList.do';
		G.Data.Upload.Method = 'Form';	//Form
		G.Data.Upload.Data='gridData';
		G.Data.Upload.Format = 'JSON';
		G.Data.Upload.Type = 'All';
		G.Data.Upload.Flags = 'AllCols';
		G.Data.Upload_Xml = 2;
		
		G.Save();	
		Grids.OnAfterSave = function(){
			alert(LocaleUtil.getMessage('02518'));//정상적으로 처리되었습니다
			document.location.reload();
		}
		
		//setTimeout("document.location.reload()",3000);

	},
	
	/*
	 * 평가시트 수정(updateAssessForm.jsp)에서 저장버튼(수정) 클릭시(경고창 없이 수정처리)
	 */
	updateGateCheckSheetListNoAlert : function() {
		var G = Grids[0];
		G.Data.Upload.Params = decodeURIComponent($('[name=CreateAssessSheetUpdateForm]').serialize());
		G.Data.Upload.Url = '/plm/ext/project/gate/updateGateCheckSheetList.do';
		G.Data.Upload.Method = 'Form';	//Form
		G.Data.Upload.Data='gridData';
		G.Data.Upload.Format = 'JSON';
		G.Data.Upload.Type = 'All';
		G.Data.Upload.Flags = 'AllCols';
		G.Data.Upload_Xml = 2;
		
		G.Save();	
		Grids.OnAfterSave = function(){
			document.location.reload();
		}
		
		//setTimeout("document.location.reload()",3000);

	},
	
	goView : function(grid, row, col, x, y) {
//		getOpenWindow2('/plm/ext/project/gate/viewTemplateAssessForm.do?oid='+row.oid,'TemplateAssessSheetViewPopup',900,650);
	},
	
	goDeleteCheck : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows();
		location.href = '/plm/ext/project/gate/deleteGateCheck.do?oid='+rows[0].oid + '&pjtOid='+$('[name=oid]').val();
	},
	
	addRowAbove : function(grid, row, col, x, y) {
		var G = Grids[0];
		if(G.RowCount<=0) {
			G.AddRow(null, null, true); //Row가 없으면 맨하위에 추가 parent와 next 모두 null이면 맨 하위에 Empty Row추가
			return;
		}
		var rows = G.GetSelRows();
		if(rows.length>1) {
			alert(LocaleUtil.getMessage('06109'));//하나의 Row를 선택해 주세요
			return;
		}
		if(rows=='') {
			alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
			return;
		}
		G.AddRow(G, rows[0], true);	//선택한 Row 상위추가
	},
	
	addRowBelow : function(grid, row, col, x, y) {
		var G = Grids[0];
		if(G.RowCount<=0) {
			G.AddRow(null, null, true);
			return;
		}
		var rows = G.GetSelRows(); //선택한 Row
		if(rows.length>1) {
			alert(LocaleUtil.getMessage('06109'));//하나의 Row를 선택해 주세요
			return;
		}
		if(rows=='') {
			alert(LocaleUtil.getMessage('01957'));//수정할 데이터를 선택하세요
			return;
		}
		var nextRow = G.GetNext(rows[0]); //선택한 Row + 다음 Row
		if(nextRow==null) {
			G.AddRow(null, null, true);	//선택한 Row 다음 Row가 없으면 맨하위 추가
		}else {
			G.AddRow(G, nextRow, true);	//선택한 Row 하위에 추가
		}
	},
	remove : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows(); //선택한 Row
		alert('1:'+rows[0].oid+ 'pjtOid='+$('[name=oid]').val());
		//type =  1 – delete + confirm dialog, 2 – delete, 3 – undelete. 
		//G.DeleteRow(rows[0], 1);     
		//G.RemoveRow(rows[0]);
		
		location.href = '/plm/ext/project/gate/deleteGateCheck.do?oid='+rows[0].oid + '&pjtOid='+$('[name=oid]').val();
	},
	removeRow : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows(); //선택한 Row
		if(rows=='') {
			alert(LocaleUtil.getMessage('01710'));//삭제할 데이터를 선택하세요
			return;
		}
		
		
		var delOids = $('[name=delOids]').val();
		for(var i=0;i<rows.length;i++) {
			var sel = rows[i].select1;
			if($('[name=gateCanEditG1]').val()=='0') {
				sel = rows[i].select1;
				//alert('sel:'+sel);
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324', '1'));	//'Gate1이 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG2]').val()=='0') {
				sel = rows[i].select2;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324', '2'));	//'Gate2이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG3]').val()=='0') {
				sel = rows[i].select3;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '3');	//'Gate3이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG4]').val()=='0') {
				sel = rows[i].select4;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '4');	//'Gate4이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG5]').val()=='0') {
				sel = rows[i].select5;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '5');	//'Gate5이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG6]').val()=='0') {
				sel = rows[i].select6;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '6');	//'Gate6이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}if($('[name=gateCanEditG7]').val()=='0') {
				sel = rows[i].select7;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '7');	//'Gate7이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG8]').val()=='0') {
				sel = rows[i].select8;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '8');	//'Gate8이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG9]').val()=='0') {
				sel = rows[i].select9;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '9');	//'Gate9이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG10]').val()=='0') {
				sel = rows[i].select10;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '10');	//'Gate10이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG11]').val()=='0') {
				sel = rows[i].select11;
				alert('sel:'+sel);
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '11');	//'Gate11이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG12]').val()=='0') {
				sel = rows[i].select12;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '12');	//'Gate12이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG13]').val()=='0') {
				sel = rows[i].select13;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '13');	//'Gate13이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG14]').val()=='0') {
				sel = rows[i].select14;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '14');	//'Gate14이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG15]').val()=='0') {
				sel = rows[i].select15;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '15');	//'Gate15이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG16]').val()=='0') {
				sel = rows[i].select16;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '16');	//'Gate16이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG17]').val()=='0') {
				sel = rows[i].select17;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '17');	//'Gate17이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG18]').val()=='0') {
				sel = rows[i].select18;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '18');	//'Gate18이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG19]').val()=='0') {
				sel = rows[i].select19;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '19');	//'Gate19이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			if($('[name=gateCanEditG20]').val()=='0') {
				sel = rows[i].select20;
				if(sel=='1') {
					alert(LocaleUtil.getMessage('07324'), '20');	//'Gate20이(가) 승인완료되어 삭제할 수 없습니다'
					return;
				}
			}
			
			G.RemoveRow(rows[i]);
			
			if(delOids=='') delOids = rows[i].oid;
			else delOids = delOids + ',' + rows[i].oid;
		}
		
		$('[name=delOids]').val(delOids);
		//alert($('[name=delOids]').val());
		
		CreateAssessSheet.updateGateCheckSheetList();
		
		
	},
	
	moveToAbove : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows(); //선택한 Row		GetPrev    (TRow row, int type)            
		var prevRow = G.GetPrev(rows[0], 1); //선택한 Row + 다음 Row
		//G.MoveRow(rows[0], prevRow, null, 1);	//MoveRow(TRow row, TRow parent, TRow next, bool show = false)           
		G.MoveRows(rows[0], prevRow, 1);	//G.MoveRows(TRow row, TRow rowto, int type);
	},
	moveToBelow : function(grid, row, col, x, y) {
		var G = Grids[0];
		var rows = G.GetSelRows(); //선택한 Row
		var prevRow = G.GetPrev(rows[0], 1); //선택한 Row + 이전 Row
		var nextRow = G.GetNext(rows[0]); //선택한 Row + 다음 Row
		//G.MoveRow(rows[0], nextRow, rows[0],  1);	//MoveRow(TRow row, TRow parent, TRow next, bool show = false)           
		G.MoveRows(rows[0], nextRow, 2);	//G.MoveRows(TRow row, TRow rowto, int type);
	}

	
	       
}
