var PartList = {
	
	/**
	 * server paging grid
	 */
	createPaingGrid : function(){
		this.grid = TreeGrid(CommonGrid.getPagingGridConfig({
			id : 'PartListSearchGrid',
			Sort : '-createDate',
			perPageOnChange : 'javascript:PartList.search(Value);',
			MinHeight : '350', MinTagHeight : '350',
			Data : {
				Url : '/plm/jsp/common/treegrid/InitGridData.jsp',
				Method : 'POST',
				Format : 'Form',
				Timeout : 0,
				Param : {
					formPage : (Grids['PartListSearchGrid'])?Grids['PartListSearchGrid'].PageLength:CommonGrid.pageSize
				}
			},
			LeftCols : [
	            {Name : 'partOid', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
	            {Name : 'partMastOid', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
	            {Name : 'spPartType', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
	            {Name : 'partNumber', Align : 'Left', CanSort : '1', CanEdit : '0'},
	            {Name : 'partName', Align : 'Left', CanSort : '1', CanEdit : '0'},
	            {Name : 'spPartRevision', Align : 'Center', CanSort : '1', CanEdit : '0'}
			],
			Cols : [
			    {Name : 'partRevision', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
			    {Name : 'partIteration', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
			    {Name : 'spMaterDieCode', Width: '0', Align : 'Center', CanSort : '0', CanEdit : '0', Visible:'0'},
			    {Name : 'partState', Align : 'Center', CanSort : '1', CanEdit : '0'},
			    {Name : 'spPartTypeName', Align : 'Center', CanSort : '1', CanEdit : '0'},
			    {Name : 'partClazNameKr', Align : 'Left', CanSort : '1', CanEdit : '0'},
			    {Name : 'projectNo', Align : 'Center', CanSort : '1', CanEdit : '0'},
			    {Name : 'ecoNo', Align : 'Center', CanSort : '1', CanEdit : '0'},
			    {Name : 'dieNo', Align : 'Center', CanSort : '1', CanEdit : '0', Type : 'Html'},
			    {Name : 'primaryEpmNo', Align : 'Center', CanSort : '1', CanEdit : '0', Type : 'Html'},
				{Name : 'createDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'modifyDate', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'partDefaultUnit', Align : 'Center', CanSort : '1', CanEdit : '0'},
				// SPEC ADD
				{Name : 'spNetWeight', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spTotalWeight', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spScrabWeight', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spIsYazaki', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spYazakiNo', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spManufAt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spManufacPlace', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCustomRev', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spScrabCode', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spWaterProof', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spStandardConnect', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spConnectorStyle', Align : 'Center', CanSort : '1', CanEdit : '0'},				
				{Name : 'spMaterialMark', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spColor', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spColorPurch', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spColorElec', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSeries', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSeriesLAMP', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSeriesBulb', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spProdSize', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spEnvFriend', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTGenConn', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTPCBConn', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTLAMPConn', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTBulbConn', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTHiVolt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTCharger', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spT2Charger', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spT1HiVoltFuz', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTHiVoltFuse', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTJnB', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTPRA', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTGNSS', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTComModule', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTMultiMedUnit', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spLanceType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spNoOfPole', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMTerminal', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spMConnector', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spMatchBulb', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spMClip', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spMRH', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spMCover', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spLockingType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spLockTTML', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spLockTConn', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMWireSeal', Align : 'Left', CanSort : '1', CanEdit : 'html'},
				{Name : 'spRepresentM', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterMaker', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterialInfo', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterNotFe', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterDie', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterPurch', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPlating', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spPlatingPurch', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCustomPartNo', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPermitVolt', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spWireRangeAWG', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spWireRAWGElec', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spWireRangeMm', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spTabThickness', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaterThick', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spBracketSizeD', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spBracketSizeH', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spBracketSizeT', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spRelayCapa', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spEResistVal', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spVoltSensCapa', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spManufPartNo', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spValueValue', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spTolerance', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spManufacturer', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spRatedVoltage', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spWatt', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spOperTemp', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spStoreTemp', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPackageType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMSL', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spESD', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spAECQ', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCharact1', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spCharact2', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spHoleSize', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spWireRangeSQ', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spWATTW', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSizemm', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPermitTempC', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spIsResin', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spThickness', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spHoleShape', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCapaEResist', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spClothesMater', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spIsShield', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spESIRApproval', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spISIRApproval', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spConAppNoESIR', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spLayer', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spOrgDskPartNo', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPCBMaterial', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTg', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCharact', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSurfaceTreat', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spAppearanProc', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSoldResColor', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spInstallPos', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSwVersion', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spUnitUnit', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spResUnit', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spINDUnit', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCapUnit', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spRLCPackType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spRLCTolerance', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spFestPrevent', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spConnCombDir', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCertified', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spPackageSpec', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTabSize', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spSealType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spStudSize', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPitch', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spSoldering', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spConstrucMeth', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spOptimumTemp', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spContactRes', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spInsulatRes', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spConntHeight', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spCableConMeth', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTerminalType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTermBarrelSz', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spTermPrezType', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spGWITMaterAt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spInterface', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spPinNShape', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spSUBSuppliy', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spCodingNColor', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spPlant', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spPuchaseGroup', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spDevManNm', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spDesignManNm', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spManufacManNm', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMContractSAt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMMoldAt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMMakingAt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spMProdAt', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spDieWhere', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spObjectiveCT', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spCavityStd', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMountWayApE', Align : 'Center', CanSort : '1', CanEdit : '0'},
				{Name : 'spThickWH', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spPropEtc', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spHighVoltageSensingLimit', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMovVoltage', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spToleranceSensing', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spEsirApprove', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMainChipset', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spFrequency', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spDcpower', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spInterfaceIt', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spFeatures', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spDevSpec', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spFlameLevel', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spERatedVoltage', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spQCNo', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spActuatorLctn', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spMaxFrequency', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'spImpedance', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'homepageIF', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'homepage2DIF', Align : 'Left', CanSort : '1', CanEdit : '0'},
				{Name : 'hompage3DIF', Align : 'Left', CanSort : '1', CanEdit : '0'}
				
			],Header :{
				
				CanDelete : '0', Align : 'Center',
				partNumber : '부품번호',//
				partName : '부품명',//
				spPartRevision : 'Rev',//
				partState : '결재상태',//
				spPartTypeName : '부품유형', // 
				partClazNameKr : '부품분류',//
				projectNo : '프로젝트번호',//
				ecoNo : 'ECO NO',//
				dieNo : 'Die NO',//
				primaryEpmNo : '제품도면 NO',//
				createDate : LocaleUtil.getMessage('01335'),//'등록일',
				modifyDate : LocaleUtil.getMessage('01951'),//'수정일'
				partDefaultUnit : '기본단위',
				// SPEC ADD
				spNetWeight : '부품중량', // 부품중량
				spTotalWeight : '총중량', // 총중량
				spScrabWeight : '스크랩중량', // 스크랩중량
				spIsYazaki : 'YAZAKI여부', // YAZAKI여부
				spYazakiNo : 'YAZAKI제번', // YAZAKI제번
				spManufAt : '생산처구분', // 생산처구분
				spManufacPlace : '생산처', // 생산처
				spCustomRev : '고객 Rev', // 고객 Rev
				spScrabCode : '스크랩 코드', // 스크랩 코드
				spWaterProof : '방수여부', // 방수여부
				spStandardConnect : '표준커넥터', // 표준커넥터
				spConnectorStyle : '커넥터스타일', // 커넥터스타일
				spMaterialMark : '재질마킹', // 재질마킹
				spColor : '색상(color)', // 색상(color)
				spColorPurch : '색상(구매품)', // 색상(구매품)
				spColorElec : '색상(color)_IT 부품', // 색상(color)_IT 부품
				spSeries : '시리즈', // 시리즈
				spSeriesLAMP : '시리즈(LAMP)', // 시리즈(LAMP)
				spSeriesBulb : '시리즈(Bulb)', // 시리즈(Bulb)
				spProdSize : '제품 Size(L*W*H)', // 제품 Size(L*W*H)
				spEnvFriend : '친환경', // 친환경
				spTGenConn : 'Type(일반커넥터)', // Type(일반커넥터)
				spTPCBConn : 'Type(PCB 커넥터)', // Type(PCB 커넥터)
				spTLAMPConn : 'Type(LAMP)', // Type(LAMP)
				spTBulbConn : 'Type(Bulb)', // Type(Bulb)
				spTHiVolt : 'Type(고전압)', // Type(고전압)
				spTCharger : 'Type(충전기)', // Type(충전기)
				spT2Charger : 'Type2(충전기)', // Type2(충전기)
				spT1HiVoltFuz : 'Type1(고전압퓨즈)', // Type1(고전압퓨즈)
				spTHiVoltFuse : 'Type2(고전압퓨즈)', // Type2(고전압퓨즈)
				spTJnB : 'Type(J/B)', // Type(J/B)
				spTPRA : 'Type(PRA)', // Type(PRA)
				spTGNSS : 'Type(GNSS 모듈)', // Type(GNSS 모듈)
				spTComModule : 'Type(통신모듈)', // Type(통신모듈)
				spTMultiMedUnit : 'Type(멀티미디어 유닛)', // Type(멀티미디어 유닛)
				spLanceType : 'Lance Type', // Lance Type
				spNoOfPole : '극수', // 극수
				spMTerminal : '매칭터미널', // 매칭터미널
				spMConnector : '매칭커넥터', // 매칭커넥터
				spMatchBulb : '매칭전구', // 매칭전구
				spMClip : '매칭Clip', // 매칭Clip
				spMRH : '매칭R/H', // 매칭R/H
				spMCover : '매칭 Cover', // 매칭 Cover
				spLockingType : 'Locking Type', // Locking Type
				spLockTTML : 'TPA 적용', // TPA 적용
				spLockTConn : 'CPA 적용', // CPA 적용
				spMWireSeal : '매칭 Wire Seal', // 매칭 Wire Seal
				spRepresentM : '대표금형', // 대표금형
				spMaterMaker : '원재료 Maker', // 원재료 Maker
				spMaterType : '원재료 Type', // 원재료 Type
				spMaterialInfo : '재질(수지)', // 재질(수지)
				spMaterNotFe : '재질(비철)', // 재질(비철)
				spMaterDie : '재질(금형)', // 재질(금형)
				spMaterPurch : '재질(구매품)', // 재질(구매품)
				spPlating : '도금', // 도금
				spPlatingPurch : '도금(구매품)', // 도금(구매품)
				spCustomPartNo : '고객사 품번', // 고객사 품번
				spPermitVolt : '허용전류', // 허용전류
				spWireRangeAWG : 'Wire Range', // Wire Range
				spWireRAWGElec : 'WireRange_AWG(전자)', // WireRange_AWG(전자)
				spWireRangeMm : 'Insulation Range', // Insulation Range
				spTabThickness : 'TabThickness', // TabThickness
				spMaterThick : 'Material_Thick', // Material_Thick
				spBracketSizeD : 'BracketSize_D', // BracketSize_D
				spBracketSizeH : 'BracketSize_H', // BracketSize_H
				spBracketSizeT : 'BracketSize_T', // BracketSize_T
				spRelayCapa : 'RELAY 용량', // RELAY 용량
				spEResistVal : '저항값', // 저항값
				spVoltSensCapa : '전류센서용량', // 전류센서용량
				spManufPartNo : '제조사 품번', // 제조사 품번
				spValueValue : 'Value', // Value
				spTolerance : 'Tolerance', // Tolerance
				spManufacturer : '제조사', // 제조사
				spRatedVoltage : 'Rated voltage', // Rated voltage
				spWatt : 'Watt', // Watt
				spOperTemp : 'Operating Temperature(TA)', // Operating Temperature(TA)
				spStoreTemp : 'Storage Temperature(Tstg)', // Storage Temperature(Tstg)
				spPackageType : 'Package Type', // Package Type
				spMSL : 'MSL', // MSL
				spESD : 'ESD(HBM)', // ESD(HBM)
				spAECQ : 'AEC-Q', // AEC-Q
				spCharact1 : '특성1', // 특성1
				spCharact2 : '특성2', // 특성2
				spHoleSize : 'Hole Size(Ø)', // Hole Size(Ø)
				spWireRangeSQ : 'Wire Range(SQ)', // Wire Range(SQ)
				spWATTW : 'WATT(W)', // WATT(W)
				spSizemm : 'Size(mm)', // Size(mm)
				spPermitTempC : '허용온도(℃)', // 허용온도(℃)
				spIsResin : '레진 여부', // 레진 여부
				spThickness : 'Thickness', // Thickness
				spHoleShape : 'Hole 형상', // Hole 형상
				spCapaEResist : '용량(Ω)', // 용량(Ω)
				spClothesMater : '피복재질', // 피복재질
				spIsShield : '쉴드여부', // 쉴드여부
				spESIRApproval : '커넥터 승인', // 커넥터 승인
				spISIRApproval : 'ISIR 승인', // ISIR 승인
				spConAppNoESIR : '커넥터 승인EO', // 커넥터 승인EO
				spLayer : 'Layer', // Layer
				spOrgDskPartNo : '원판 품번', // 원판 품번
				spPCBMaterial : 'PCB 재질', // PCB 재질
				spTg : 'Tg', // Tg
				spCharact : '특성', // 특성
				spSurfaceTreat : '표면처리', // 표면처리
				spAppearanProc : '외형가공', // 외형가공
				spSoldResColor : 'Solder Resistor Color', // Solder Resistor Color
				spInstallPos : '장착위치', // 장착위치
				spSwVersion : 'S/W Version', // S/W Version
				spUnitUnit : '단위', // 단위
				spResUnit : 'RES 단위', // RES 단위
				spINDUnit : 'IND 단위', // IND 단위
				spCapUnit : 'CAP 단위', // CAP 단위
				spRLCPackType : 'RLC Package type', // RLC Package type
				spRLCTolerance : 'RLC Tolerance', // RLC Tolerance
				spFestPrevent : '제전방지', // 제전방지
				spConnCombDir : '커넥터 결합 방식', // 커넥터 결합 방식
				spCertified : 'Certified', // Certified
				spPackageSpec : '포장사양', // 포장사양
				spTabSize : 'tap size', // tap size
				spSealType : 'Seal Type', // Seal Type
				spStudSize : 'Stud Size', // Stud Size
				spPitch : 'Pitch', // Pitch
				spSoldering : '납땜 방식', // 납땜 방식
				spConstrucMeth : '조립 공법', // 조립 공법
				spOptimumTemp : '적용온도 (사용온도)', // 적용온도 (사용온도)
				spContactRes : '접촉저항', // 접촉저항
				spInsulatRes : '절연저항', // 절연저항
				spConntHeight : "conn't 높이 (height)", // conn't 높이 (height)
				spCableConMeth : 'Cable 연결 방식', // Cable 연결 방식
				spTerminalType : '터미널 TYPE', // 터미널 TYPE
				spTermBarrelSz : '터미널 BARREL-SIZE', // 터미널 BARREL-SIZE
				spTermPrezType : '터미널 압착 방식', // 터미널 압착 방식
				spGWITMaterAt : 'GWIT 재질여부', // GWIT 재질여부
				spInterface : '인터페이스', // 인터페이스
				spPinNShape : '핀수&형상', // 핀수&형상
				spSUBSuppliy : 'SUB 공급형태', // SUB 공급형태
				spCodingNColor : '코딩&색상', // 코딩&색상
				spPlant : '플랜트', // 플랜트
				spPuchaseGroup : '구매담당자', // 구매담당자
				spDevManNm : '개발담당자', // 개발담당자
				spDesignManNm : '금형담당자', // 금형담당자
				spManufacManNm : '제작담당자', // 제작담당자
				spMContractSAt : '사급구분', // 사급구분
				spMMoldAt : '금형구분', // 금형구분
				spMMakingAt : '제작구분', // 제작구분
				spMProdAt : '생산구분', // 생산구분
				spDieWhere : '금형제작처', // 금형제작처
				spObjectiveCT : '목표 C/T (SPM)', // 목표 C/T (SPM)
				spCavityStd : 'Cavity', // Cavity
				spMountWayApE : '실장방식&적용전선', // 실장방식&적용전선
				spThickWH : 'Thickness(W/H)', // Thickness(W/H)
			    spPropEtc : '기타', // 기타
			    spHighVoltageSensingLimit : '고전압 센싱 범위', // 고전압 센싱 범위
			    spMovVoltage : '동작 전압', // 동작 전압
			    spToleranceSensing : ' 톨러런스(센싱)', // 톨러런스(센싱)
			    spEsirApprove : 'ESIR 승인', // ESIR 승인
			    spMainChipset : 'Main Chipset', // Main Chipset
			    spFrequency : 'Frequency', // Frequency
			    spDcpower : 'DC Power', // DC Power
			    spInterfaceIt : 'Interface', // Interface
			    spFeatures : 'Features', // Features
			    spDevSpec : '개발 스펙',// 개발 스펙
			    spFlameLevel : '난연등급',// 난연등급
			    spERatedVoltage : '정격전압',// 정격전압
			    spQCNo : '품질인증번호',// 품질인증번호
			    spActuatorLctn : 'Actuator 위치구분',// Actuator 위치구분
			    spMaxFrequency : '최대허용주파수',// 최대허용주파수
			    spImpedance : '임피던스',// 임피던스
			    homepageIF : '홈페이지 등록',// 홈페이지 등록
			    homepage2DIF : '홈페이지 2D 등록',// 홈페이지 2D 등록
			    hompage3DIF : '홈페이지 3D 등록'// 홈페이지 3D 등록
			}
			/*
			,
			SelectingSingle : '1',
			Panel : {
				Width : '21', Visible : '1',CanHide : '0'
			}
			*/
		}),'listGrid');
		
		//row click event
		//Grids.OnClick = PartList.goView;
		
		//download page  event
		Grids.OnDownloadPage = function(grid,row){
			grid.Data.Page.Format = 'Form';
			grid.Data.Page.Method = 'Post';
			grid.Data.Page.Url = '/plm/ext/part/base/findPagingList.do?sortName=*Sort0&pageTotalSize='+ grid.RootCount;
			grid.Data.Page.Param = {
					page : grid.GetPageNum(row),
					formPage : grid.PageLength
			}
			grid.Data.Page.Params = decodeURIComponent($('[name=PartSearchForm]').serialize());
		}
	},
	
	search : function(perPage){
		if(!perPage) perPage = Grids['PartListSearchGrid'].Source.Layout.Data.Cfg.PageLength;
		Grids['PartListSearchGrid'].Source.Layout.Data.Cfg.PageLength=perPage;
		Grids['PartListSearchGrid'].Source.Data.Url = '/plm/ext/part/base/findPagingList.do?sortName=*Sort0&pageTotalSize=-1';
		Grids['PartListSearchGrid'].Source.Data.Params = decodeURIComponent($('[name=PartSearchForm]').serialize());
		Grids['PartListSearchGrid'].Source.Data.Param.formPage=perPage
		Grids['PartListSearchGrid'].Reload();
	},

	goView : function(grid,row,col,x,y){
		if(col != 'Panel' && row.partOid){
			
			openView(row.partOid);
		}
	},
	
	reload : function(){
		Grids['PartListSearchGrid'].Reload();
	}
	
	/*
	,
	clear : function(){
		$('[name=PartSearchForm]')[0].reset();
	},
	
	create : function(){
		$('[name=SampleCreateForm]').attr('action', '/plm/ext/sample/create.do');
		$('[name=SampleCreateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=SampleCreateForm]').submit();
	},
	
	update : function(){
		$('[name=SampleUpdateForm]').attr('action', '/plm/ext/sample/update.do');
		$('[name=SampleUpdateForm]').attr('enctype', 'multipart/form-data'); //첨부파일 전송을 위해 multipart/form-data형태로 submit한다.
		$('[name=webEditor]').val(fnGetEditorHTMLCode(false, 0));
		$('[name=webEditorText]').val(fnGetEditorText(0));
		$('[name=SampleUpdateForm]').submit();		
	},
	
	remove : function(){
		$('[name=SampleUpdateForm]').attr('action', '/plm/ext/sample/delete.do');
		$('[name=SampleUpdateForm]').submit();		
	},
	
	goList : function(){
		location.href = '/plm/ext/sample/list.do';
	},
	
	goCreate : function(grid,row,col,x,y){
		getOpenWindow2('/plm/ext/sample/createForm.do','SampleCreatePopup',800,600);
	}
	*/
	
}