var SearchUtil = {
	/**
	 * 사용자(단건 선택)
	 * @param 사용자Oid 필드
	 * @param 사용자명 필드
	 */
	selectOneUser : function(valueField, displayField, invokeMethod){
		
		if (invokeMethod === undefined) {
			invokeMethod = 'unknown';
		}
		
		var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod="+invokeMethod+"&valueField="+valueField+"&displayField="+displayField;
		
		if(invokeMethod == ''){
			
			var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
	    	if (typeof attacheMembers == "undefined" || attacheMembers == null || attacheMembers.length == 0) {
	            return;
	    	}
	    	var infoArr = attacheMembers[0];
	    	$('[name='+valueField+']').val(infoArr[0]);
	        $('[name='+displayField+']').val(infoArr[4]);
	        
		}else{
			var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=800,height=600";
			window.open(url,invokeMethod,opts);
			//getOpenWindow2(url,name, 800, 600, opts);
		}
		
    	
	},
	
	selectOneUserById : function(valueField, displayField, invokeMethod){
		if (invokeMethod === undefined) {
			invokeMethod = 'unknown';
		}
		var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod="+invokeMethod+"&valueField="+valueField+"&displayField="+displayField;
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		getOpenWindow2(url,'', 800, 600, opts)
	},
	
	selectOneUserByClass : function(valueField, displayField,invokeMethod){
		if (invokeMethod === undefined) {
			invokeMethod = '';
		}
		var url = "/plm/jsp/project/AddProjectPeopleFrm.jsp?mode=o&invokeMethod="+invokeMethod;
		
		if(invokeMethod == ''){
			var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
	    	if (typeof attacheMembers == "undefined" || attacheMembers == null || attacheMembers.length == 0) {
	            return;
	    	}
	    	var infoArr = attacheMembers[0];
	    	$('.'+valueField).val(infoArr[0]);
	        $('.'+displayField).val(infoArr[4]);	
		}else{
			var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=800,height=600";
			window.open(url,invokeMethod,opts);
		}
    	
	},

	/**
	 * 개발원가 결재 담당자(단건 선택) Id
	 * @param 사용자Oid 필드
	 * @param 사용자명 필드
	 */
	selectCostOneUserById : function(valueField, displayField){
		var url = "/plm/jsp/cost/common/AddCostPeopleFrm.jsp?mode=o";
    	var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
    	if (typeof attacheMembers == "undefined" || attacheMembers == null || attacheMembers.length == 0) {
            return;
    	}
    	var infoArr = attacheMembers[0];
    	var totalArray = new Array(3);
    	totalArray[0] = infoArr[5];
    	totalArray[1] = infoArr[6];
    	totalArray[2] = infoArr[4];
    	$('#'+valueField).val(infoArr[3]);
    	$('#'+displayField).val(infoArr[4]);
    	addTR(totalArray);
	},
	/**
	 * 개발원가 결재 담당자(단건 선택) name
	 * @param 사용자Oid 필드
	 * @param 사용자명 필드
	 */
	selectCostOneUser : function(valueField, displayField){
		var url = "/plm/jsp/cost/common/AddCostPeopleFrm.jsp?mode=o";
    	var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
    	if (typeof attacheMembers == "undefined" || attacheMembers == null || attacheMembers.length == 0) {
            return;
    	}
    	var infoArr = attacheMembers[0];
    	$('[name='+valueField+']').val(infoArr[0]);
        $('[name='+displayField+']').val(infoArr[4]);
	},
	
	/**
	 * 선행 담당자(단건 선택)
	 * @param 사용자Oid 필드
	 * @param 사용자명 필드
	 */
	selectAnalysisOneUser : function(valueField, displayField){
		var url = "/plm/extcore/jsp/arm/AddAnalysisPeopleFrm.jsp?mode=o";
    	var attacheMembers = window.showModalDialog(url,window,"help=no; resizable=no; status=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
    	if (typeof attacheMembers == "undefined" || attacheMembers == null || attacheMembers.length == 0) {
            return;
    	}
    	var infoArr = attacheMembers[0];
    	$('[name='+valueField+']').val(infoArr[0]);
        $('[name='+displayField+']').val(infoArr[4]);
	},
	
    /**
     * 해석 의뢰 접수 문서 검색
     */
    selectAnalysisDocument : function(callBackFn){
    	var url="/plm/ext/arm/master/SearchAnalysisPopup.do";
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1020px; dialogHeight:700px; center:yes");
        if ( typeof returnValue == "undefined" || returnValue == null ) {
            return;
        }
        callBackFn(returnValue);
    },

	/**
	 * 부서
	 * @param 멀티선택 유무(팝업에서 멀티 선택 가능)
	 * @param 부서 oid 필드
	 * @param 부서명 필드
	 */
	addDepartment : function(isMulti, valueField, displayField) {
		var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode="+ (isMulti?'m':'s')+"&valueField="+valueField+"&displayField="+displayField+"&callbackFncYn=N&invokeMethod=unknown";
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		
		getOpenWindow2(url,'', 430, 600, opts);
		
		
	},
	
	/**
	 * 부서
	 * @param 멀티선택 유무(단건 - root부서도 선택 가능)
	 * @param 부서 oid 필드
	 * @param 부서명 필드
	 */
	addAllDepartment : function(valueField, displayField, root) {
		var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode=s&valueField="+valueField+"&displayField="+displayField+"&callbackFncYn=N&root="+(root?'y':'n')+"&valueField="+valueField+"&displayField="+displayField+"&callbackFncYn=N&invokeMethod=unknown";
		
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		
		getOpenWindow2(url,'', 430, 600, opts);
		
	},
	
	
	
	/**
	 * 부서
	 * @param 멀티선택 유무(팝업에서 멀티 선택 가능)
	 * @param 부서 oid 필드
	 * @param 부서명 필드
	 */
	addDepartmentAfterFunc : function(isMulti, callbackFnc) {
		var url = "/plm/extcore/jsp/project/AddProjectDeptFrm.jsp?mode="+ (isMulti?'m':'s')+"&callbackFncYn=Y&callbackFnc="+callbackFnc+"&invokeMethod="+callbackFnc;
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		getOpenWindow2(url,'', 430, 465, opts);
	},
	
	/**
	 * 차종(단건 선택)
	 * @param valueField
	 * @param displayField
	 * @param cbMethod
	 */
	selectCarType : function(valueField, displayField, cbMethod) {
		if (cbMethod === undefined) {
			cbMethod = 'unknown';
		}
		
		var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=s&cbMethod="+cbMethod+"&valueField="+valueField+"&displayField="+displayField;
		
		if(cbMethod == ''){
			
			var returnValue = window.showModalDialog(url, window, "help=no; resizable=no; status=no; scroll=no; dialogWidth=700px; dialogHeight:460px; center:yes");
			if (typeof returnValue == "undefined" || returnValue == null) {
				return;
			}
			$('[name='+valueField+']').val(returnValue[0][0]).trigger('change');//id
	        $('[name='+displayField+']').val(returnValue[0][1]).trigger('change');//name
	        
		}else{
			
			var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=700,height=460";
			window.open(url,cbMethod,opts);
			//getOpenWindow2(url,name, 700, 460, opts);
			
		}
		
	},
	
	
	/**
	 * 차종 (다중 선택)
	 * @param callBackFn
	 */
	selectCarTypeMulti : function(callBackFn) {
		
		var url = "/plm/jsp/project/projectType/SelectOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT&mode=m&cbMethod="+callBackFn;
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0,width=700,height=460";
		
		getOpenWindow2(url,callBackFn, 700, 460, opts);
		
	},
	
    
	/**
	 * 최종사용처 (단건 선택)
	 * @param valueField
	 * @param displayField
	 */
	selectOneLastUsingBuyer : function(valueField, displayField) {
		this.commonContractorUrl(850, 1, 'CUSTOMEREVENT','single', '',valueField, displayField);
//		var returnValue = this.commonContractorUrl(850, 1, 'CUSTOMEREVENT','single', '',valueField, displayField);
//		if(typeof returnValue == "undefined" || returnValue == null) {
//			return;
//		}
//		$('[name='+valueField+']').val(returnValue[0][0]);//id
//		$('[name='+displayField+']').val(returnValue[0][2]);//name
	},
	/**
	 * 최종사용처 (단건 코드 선택)
	 * @param valueField
	 * @param displayField
	 */
	selectOneLastUsingBuyerCode : function(valueField, displayField) {
		this.commonContractorUrl(850, 1, 'CUSTOMEREVENT','single', '',valueField, displayField);
	},
	
	/**
	 * 최종사용처 (다중 선택)
	 * @param callBackFn
	 */
	selectLastUsingBuyer : function(callBackFn) {
		this.commonContractorUrlPopUp(850, 1, 'CUSTOMEREVENT','multi', '',callBackFn);
	},
	
	/**
	 * 최종사용처 (단건 선택)
	 * @param valueField
	 * @param displayField
	 */
	selectOneLastUsingBuyerPopup : function(callBackFn) {
		this.commonContractorUrlPopUp(850, 1, 'CUSTOMEREVENT','single', '', callBackFn);
	},

	/**
	 * 최종사용처 (다중 선택)
	 * @param callBackFn
	 */
	selectLastUsingBuyerPopup : function(callBackFn) {
		this.commonContractorUrlPopUp(850, 1, 'CUSTOMEREVENT','multi', '', callBackFn);
	},
	
	/**
	 * 생산처 (단건 선택)
	 * @param valueField
	 * @param displayField
	 */
	selectPlan : function(valueField, displayField){
        this.commonContractorUrl(680, 0, 'PRODUCTIONDEPT','single', 'noTree',valueField, displayField);
	},
	
    /**
     * 고객처 (단건 선택)
     * @param valueField
	 * @param displayField
     */
	selectOneSubContractor : function(valueField, displayField) {
        this.commonContractorUrl(680, 0, 'SUBCONTRACTOR','single', 'noTree',valueField, displayField);
    },
    
    selectOneSubContractorPopUp : function(callBackFn){
    	this.commonContractorUrlPopUp(850, 0, 'SUBCONTRACTOR','single', 'noTree', callBackFn);
    },
    
    /**
     * 납입처 (단건 선택)
     * @param valueField
	 * @param displayField
     */
    selectOneSapSubContractor : function(valueField, displayField) {
    	this.commonContractorUrl(680, 0, 'SAPSUBCONTRACTOR','single', 'noTree',valueField,displayField);
    },    
    
    /**
     * 고객처 (다중 선택)
     * @param callBackFn
     */
    selectMultiSubContractor : function(callBackFn) {
    	
    	SearchUtil.selectMultiSubContractorPopUp(callBackFn);
    },
    
    selectMultiSubContractorPopUp : function(callBackFn){
    	this.commonContractorUrlPopUp(850, 0, 'SUBCONTRACTOR','multi', '', callBackFn);
    },
	
	/**
	  * 납입처 (다중 선택)
	 * @param callBackFn
	 */
    selectMultiSapSubContractor : function(callBackFn) {
		this.commonContractorUrlPopUp(850, 0, 'SAPSUBCONTRACTOR', 'multi', '',callBackFn);
	},
	
	
	/**
	 * 최종사용/고객처/납입처/생산처 공통 Popup
	 * @param 팝업창 width
	 * @param depth
	 * @param codeType
	 * @param mode
	 * @param noTree: Tree 포함
	 * @returns
	 */
    commonContractorUrlPopUp : function(width, depth, codeType, mode, viewType,fncall){
    	
    	var disable = "";
    	
    	if(codeType == 'SPECSERIES'){
    		disable = "0";
    	}
    	
    	var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+codeType
    				+"&command=select&mode="+mode+"&viewType="+viewType+"&disable="+disable+"&invokeMethod="+fncall;
    	
    	var defaultWidth = width;
        var defaultHeight = 850;
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0, width="+defaultWidth+", height="+defaultHeight;
        
        window.open(url, fncall, opts);
        
        //getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    	
    },
	
	/**
	 * 최종사용/고객처/납입처/생산처 공통 Modal Popup
	 * @param 팝업창 width
	 * @param depth
	 * @param codeType
	 * @param mode
	 * @param noTree: Tree 포함
	 * @returns
	 */
    commonContractorUrl : function(width, depth, codeType, mode, viewType, valueField, displayField){
    	
    	var disable = "";
    	
    	if (displayField === undefined) {
    		displayField = '';
		}
    	if (valueField === undefined) {
    		valueField = '';
		}
    	
    	if(codeType == 'SPECSERIES'){
    		disable = "0";
    	}
    	
    	var url = "/plm/jsp/common/code/NumberCodeMgtFrame.jsp?expandedDepth="+depth+"&selectedDepth="+depth+"&codetype="+codeType
    				+"&command=select&mode="+mode+"&viewType="+viewType+"&disable="+disable+"&invokeMethod=unknown&displayField="+displayField+"&valueField="+valueField;
    	
    	var defaultWidth = width;
        var defaultHeight = 850;
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0, width="+defaultWidth+", height="+defaultHeight;
        
        getOpenWindow2(url,'', defaultWidth, defaultHeight, opts);
    	
    	//return window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth="+ width + "px; dialogHeight:480px; center:yes");
    	
    },
    
	/**
	 * 개발산출물 (단건 선택)
	 * @param callBackFn
	 */
   selectOneDevDocCategory : function(callBackFn) {
	    var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?docRoot=DEV&modal=N&singleSel=true&fnCall="+callBackFn;
	    
	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		
		getOpenWindow2(url,callBackFn, 450, 350, opts);
	},
	
	/**
	 * 개발산출물 (다중 선택)
	 * @param callBackFn
	 */
	selectDevDocCategory : function(callBackFn) {
		var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?docRoot=DEV&modal=N&fnCall="+callBackFn;
		
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		
		getOpenWindow2(url,callBackFn, 450, 450, opts);
	},
	
	/**
	 * 기술문서 (단건 선택)
	 * @param callBackFn
	 */
   selectOneTechDocCategory : function(valueField, displayField, idDesignField, dataParam) {
	    var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
	    var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?docRoot=TECH&singleSel=true&"+_dataParam;
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=450px; dialogHeight:350px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		
		$('[name='+valueField+']').val(returnValue[0].id);//id
    	$('[name='+displayField+']').val(returnValue[0].name);//name
    	$('[name='+idDesignField+']').val(returnValue[0].attribute1);//설계가이드 여부
    	if(valueField == 'categoryCode'){
    		$("[name=techDeptName]").val("");    		
    		$("[name=techDeptCode]").val("");
        	if($('[name='+idDesignField+']').val() == 'Y'){
        		//$("#standardTR").hide();
        		$("#deptTR").show();
        	}else{
        		//$("#standardTR").show();
        		$("#deptTR").hide();
        	}
    	}

	},
	
	/**
	 * 기술문서 (다중 선택)
	 * @param callBackFn
	 */
	selectTechDocCategory : function(callBackFn) {

		var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?docRoot=TECH&isSearch=Y&modal=N&fnCall="+callBackFn;
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		getOpenWindow2(url,callBackFn, 450, 450, opts);
		
	},
	
	/**
	 * 개발산출물/기술문서 (단건 선택)
	 * @param callBackFn
	 */
	selectOneDocCategory : function(valueField, displayField) {
		var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?singleSel=true";
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=450px; dialogHeight:450px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		$('[name='+valueField+']').val(returnValue[0].id);//id
		$('[name='+displayField+']').val(returnValue[0].name);//name
	},
	
	/**
	 * 개발산출물/기술문서 (다중 선택)
	 * @param callBackFn
	 */
   selectDocCategory : function(callBackFn) {
	    /*var url = "/plm/jsp/dms/DocuCateTreePopup.jsp";
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=yes; dialogWidth=450px; dialogHeight:300px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		callBackFn(returnValue);*/
	   SearchUtil.selectDocCategoryPopup(callBackFn);
	},
	
	/**
	 * 개발산출물/기술문서 (다중 선택) no modal
	 * @param callBackFn
	 */
	
	selectDocCategoryPopup : function(callBackFn) {
	    var url = "/plm/jsp/dms/DocuCateTreePopup.jsp?modal=N&fnCall="+callBackFn;
	    
	    var defaultWidth = 450;
        var defaultHeight = 300;
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,callBackFn, defaultWidth, defaultHeight, opts);
	},
	
	selectInputPartNumberPopup : function(callBackFn){
		
		var url = "/plm/jsp/bom/InputPartNumberPopup.jsp?modal=N&fnCall="+callBackFn;
	    
	    var defaultWidth = 520;
        var defaultHeight = 600;
        
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
	},
	
	/**
	 * 프로젝트(단건 선택)
	 */
	selectOneProject : function(callBackFn,dataParam) {
		/*var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
		var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&modal=Y&"+_dataParam;
		returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");
		if ( returnValue == null ) {
			return;
		}
		callBackFn(returnValue);*/
		SearchUtil.selectOneProjectPopUp(callBackFn,dataParam);
	},
	
	selectOneProjectPopUp : function(callBackFn,dataParam) {
		
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        //var url="/plm/ext/part/base/listPartPopup.do?mode=m&modal=N&fncall=" + fncall + "&" + _dataParam;
        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=one&modal=N&fncall="+ callBackFn + "&" +_dataParam;
        var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
        
	},
	
	/**
	 * 프로젝트(다중 선택)
	 */
	selectProjectPopUp : function(callBackFn,dataParam) {
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
		
		var url="/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=N&fncall="+ callBackFn + "&" +_dataParam;
        var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    },
	
	/**
	 * 프로젝트(다중 선택)
	 */
	selectProject : function(callBackFn,dataParam) {
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        var url="/plm/jsp/project/SearchPjtPop.jsp?mode=multi&modal=Y&"+_dataParam;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1024px; dialogHeight:768px; center:yes");
        if ( returnValue == null ) {
            return;
        }
        callBackFn(returnValue);
    },
	
    /**
     * 시리즈 (단건 선택) : 부품속성
     * @param valueField : code
	 * @param displayField : description
     */
    selectOneSpSeries : function(valueField, displayField) {
    	this.commonContractorUrl(680, 0, 'SPECSERIES','single', 'noTree',valueField, displayField);
    },
    
    
    /**
     * 시리즈 (단건 선택) : 부품속성
     * @param valueField : code
	 * @param displayField : description
     */
    selectOneSpSeriesPopUp : function(callBackFn) {
    	
    	this.commonContractorUrlPopUp(850, 0, 'SPECSERIES','single', 'noTree', callBackFn);
    	
    },
    
	/**
	 * 부품 검색
	 * 
	 * type(부품분류) : 
	  	'A' 모두 
	  	,'P' 제품
	  	,'D' DIE NO 선택
	  	,'M' 금형부품
	 * 
	 * fncall :
	 * 	후처리 함수명(objectArray 로 리턴함)
         subArr[0] = "wt.part.WTPart:" + R[i].Oid;//oid
         subArr[1] = R[i].PartNumber;//number
         subArr[2] = R[i].PartName;//name
         subArr[3] = R[i].PartVersion;//version
         subArr[4] = R[i].PartType;//type
         subArr[5] = R[i].DieNo;//dieno
         subArr[6] = R[i].DieName;
         subArr[7] = R[i].DieCnt;
         subArr[8] = R[i].OidMaster;//wtpartmaster oid
	 */
      /*
	     searchPart : function(type, fncall) {
		
		 var url = "/plm/servlet/e3ps/PartServlet?cmd=openSearchPopup&mode=one&pType="+type+"&fncall="+fncall;
		 openWindow(url,"","540","500","status=1,scrollbars=no,resizable=no");
      }
    */
    
    /**
     * 부품 (단건 선택)
     * dataParam : popup 제어 ex) pType=P
     */
    selectOnePart : function(fncall, dataParam) {
    	/*
    	var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        var url="/plm/servlet/e3ps/PartServlet?cmd=openSearchPopup&mode=s&modal=Y&" + _dataParam;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=725px; dialogHeight:515px; center:yes");
        if ( typeof returnValue == "undefined" || returnValue == null ) {
        	
        	///*
        	이 모달창을 열때 showProcessing()을 한다. 
        	만약에 사용자가 모달창을 우측상단 아이콘(?)을 이용하여 닫을 경우 프로세스바를 닫아준다.
        	//* /
        	try {
        		hideProcessing();
        	} catch(e) {}
        	
            return;
        }
        callBackFn(returnValue);
        */
    	var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
    	var url="/plm/ext/part/base/listPartPopup.do?mode=s&modal=N&fncall=" + fncall + "&" + _dataParam;
    	var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    },
    
    /**
     * 부품 (다중 선택)
     * dataParam : popup 제어 ex) pType=P
     */
	selectPart : function(fncall, dataParam) {
		/*
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        var url="/plm/servlet/e3ps/PartServlet?cmd=openSearchPopup&mode=m&modal=Y&" + _dataParam;
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=725px; dialogHeight:515px; center:yes");
        if ( typeof returnValue == "undefined" || returnValue == null ) {
            return;
        }
        callBackFn(returnValue);
        */
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        var url="/plm/ext/part/base/listPartPopup.do?mode=m&modal=N&fncall=" + fncall + "&" + _dataParam;
        var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    },
    
    /**
     * 도면 (단건 선택)
     */
    selectOneDrawing : function(callBackFn){
    	var url="/plm/servlet/e3ps/EDMServlet?command=openSearchPopup&mode=s&modal=Y";
        returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=1020px; dialogHeight:700px; center:yes");
        if ( typeof returnValue == "undefined" || returnValue == null ) {
            return;
        }
        callBackFn(returnValue);
    },
    
    /**
     * 도면 (다중 선택)
     */
    selectDrawing : function(callBackFn){
    	var url="/plm/servlet/e3ps/EDMServlet?command=openSearchPopup&mode=m&fncall="+callBackFn;
    	var name = callBackFn;
        var defaultWidth = 1100;
        var defaultHeight = 700;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    },
    
    /**
	 * 부품분류 (단건 선택)
	 * @param valueField : oid
	 * @param displayField : text
	 * @param dataParam Leaf만 선택하려면 : onlyLeaf=Y
	 * @param dataParam 전체 open하려면   : openAll=Y
	 */
   selectOnePartClaz : function(valueField, displayField, dataParam) {
	    var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
	    var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=true&modal=n&callBackFn=unknown&valueField="+valueField+"&displayField="+displayField+"&" + _dataParam;
	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
	    getOpenWindow2(url,"selectOnePartClazPopUp", 700, 700, opts);
	},
	
	/**
	 * 부품분류 (단건 선택)
	 * @param callBackFn : callBackMethod
	 * @param dataParam Leaf만 선택하려면 : onlyLeaf=Y
	 * @param dataParam 전체 open하려면   : openAll=Y
	 */
   selectOnePartClazPopUp : function(callBackFn, dataParam,name) {
	   var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
	   var popName = (name && name !=null )? name:"selectOnePartClazPopUp";
	   var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=true&" + dataParam+"&callBackFn="+callBackFn+"&modal=n";
	   
	   var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
	   getOpenWindow2(url,popName, 700, 700, opts);
	},
	
	/**
	 * 부품분류 (단건 선택)
	 * @param valueField : oid
	 * @param displayField : text
	 * @param dataParam Leaf만 선택하려면 : onlyLeaf=Y
	 * @param dataParam 전체 open하려면   : openAll=Y
	 * @param dataParam 2Level까지 표시하려면   : depthLevel2=Y
	 */
   selectOnePartClazWithCallBack : function(callBackFn, dataParam) {
	   
	   var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
	   var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=true&" + _dataParam;
	   var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=700px; dialogHeight:700px; center:yes");
	   if(typeof returnValue == "undefined" || returnValue == null) {
		   return;
	   }
	   callBackFn(returnValue);
	},
	
	/**
	 * 부품분류 (다중 선택)
	 * @param callBackFn
	 * @param dataParam 전체 open하려면   : openAll=Y
	 * @param dataParam 2Level까지 표시하려면   : depthLevel2=Y
	 */
	selectPartClaz : function(callBackFn, dataParam) {
		SearchUtil.selectPartClazPopUp(callBackFn, dataParam,'');
		/*var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
		var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=false&" + _dataParam;
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=700px; dialogHeight:700px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		callBackFn(returnValue);*/
	},
	
	/**
	 * 부품분류 (다중 선택)
	 * @param callBackFn
	 * @param dataParam 전체 open하려면   : openAll=Y
	 * @param dataParam 2Level까지 표시하려면   : depthLevel2=Y
	 */
	selectPartClazPopUp : function(callBackFn, dataParam, name) {
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
		var popName = (name && name !=null )? name:"selectOnePartClazPopUp";
		var url = "/plm/jsp/dms/partClazTreePopup.jsp?singleSel=false&" + dataParam+"&callBackFn="+callBackFn+"&modal=n";
		var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
		
		getOpenWindow2(url,popName, 700, 700, opts);
	},
	
	/**
	 * 품질문제 (단건 선택)
	 * @param callBackFn
	 */
   selectOneDqm : function(callBackFn) {
	    var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle=1&callBackFn="+callBackFn;
	    
	    getOpenWindow2(url,'searchDqmPopup', 800, 600);
	    /*
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		callBackFn(returnValue);
		*/
	},
	
	/**
	 * 품질문제 (다중 선택)
	 * @param callBackFn
	 */
	selectMultiDqm : function(callBackFn) {
		var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle='0'&callBackFn="+callBackFn;
	    
	    getOpenWindow2(url,'searchDqmPopup', 800, 600);
		/*
		var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle=0&callBackFn="+callBackFn;
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		callBackFn(returnValue);
		*/
	},
	
	
	/**
	 * 품질문제 (단건 선택)
	 * @param callBackFn
	 */
   selectOneDqmAction : function(callBackFn) {
	    var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle='1'&mode='actionSearch'&callBackFn="+callBackFn;
	    
	    getOpenWindow2(url,'searchDqmPopup', 800, 600);
	    /*
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		callBackFn(returnValue);
		*/
	},
	
	/**
	 * 품질문제 (다중 선택)
	 * @param callBackFn
	 */
	selectMultiDqmAction : function(callBackFn) {
		var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle='0'&mode='actionSearch'&callBackFn="+callBackFn;
	    
	    getOpenWindow2(url,'searchDqmPopup', 800, 600);
		/*
		var url = "/plm/ext/dqm/searchDqmPopup.do?isSingle=0&callBackFn="+callBackFn;
		var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
		if(typeof returnValue == "undefined" || returnValue == null) {
			return;
		}
		callBackFn(returnValue);
		*/
	},
	
	/**
	 * 문서 전체 검색 (다중선택)
	 * @param callBackFn
	 */
	selectTotalDocument : function(callBackFn) {
		var url = "/plm/ext/dms/listTotalDocumentPopup.do?isSingle=0&callBackFn="+callBackFn;
	    
	    getOpenWindow2(url,'searchDqmPopup', 800, 600);
	},
	
	/**
	 * 문서 전체 검색 (하나선택)
	 * @param callBackFn
	 */
	selectTotalOneDocument : function(callBackFn) {
		var url = "/plm/ext/dms/listTotalDocumentPopup.do?isSingle='1'&callBackFn="+callBackFn;
	    
	    getOpenWindow2(url,'searchDqmPopup', 800, 600);
	},
	
	/**
	 * 프로그램 검색 팝업(다중선택)
	 * @param callBackFn
	 */
	selectProgram : function(callBackFn,projectOid){
		var url = '/plm/ext/project/program/listProgramPopup.do?projectOid='+projectOid+'&callBackFn='+callBackFn;
		//var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=800px; dialogHeight:600px; center:yes");
		getOpenWindow2(url,'searchDqmPopup', 800, 600);
//		if(typeof returnValue == "undefined" || returnValue == null) {
//			return;
//		}
//		callBackFn(returnValue);
	},
	
	/**
	 * 프로젝트 Card
	 * @param pjtNo
	 */
	projectCard : function(oid, pjtNo){
		var url = '/plm/ext/dashboard/projectCard.do&key=oid&value='+oid+'&key=pjtNo&value='+pjtNo;
		getOpenWindow2('/plm/jsp/common/loading2.jsp?url='+url,'searchDqmPopup', 1100, 740);
		//window.open('/plm/ext/dashboard/projectCard.do?pjtNo='+pjtNo,'projectCard','width=1100,height=1000');
	},
	
	/**
	 * 프로그램 Card
	 * @param programNo
	 */
	programCard : function(programNo){
		var url = '/plm/ext/dashboard/programCard.do&key=programNo&value='+programNo;
		getOpenWindow2('/plm/jsp/common/loading2.jsp?url='+url,'searchDqmPopup', 1100, 1000);
		//window.open('/plm/ext/dashboard/projectCard.do?pjtNo='+pjtNo,'projectCard','width=1100,height=1000');
	},
	
	/**
	 * 표준 WBS 검색 팝업(단건선택)
	 * @param oid
	 */
	selectOneWBSTemplate : function(moldType, callBackFn){
		var url = "/plm/servlet/e3ps/SearchProjectTemplateServlet?popup=yes&selector=project&moldType="+moldType+"&callBackFn="+callBackFn;
	    var title = "wbsCopySearchPopup";
	    openWindow2(url, title, 900, 600);
	},
	
	/**
	 * dieNo별 Try조건표 검색
	 * @param dieNo
	 */
	openTryConditionListByDieNo : function(dieNo){
		var url = "/plm/ext/project/trycondition/listTryCondtionByDieNoPopup?dieNo="+dieNo;
		var title = "TryConditionPopup";
		openWindow2(url, title, 700, 300);
		
	},
	
	/**
     * 영업프로젝트 (단건 선택)
     * 
     */
    selectOneSalesProject : function(fncall) {
    	var url="/plm/ext/sales/project/listSalesProjectPopup.do?mode=s&modal=N&fncall=" + fncall;
    	var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
        
        //window.open(url, "listSalesProjectPopup", "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1024,height=768");
    },
    
    /**
     * 영업프로젝트 (다중 선택)
     * 
     */
    selectMultiSalesProject : function(fncall, isCLock) {
    	if(isCLock == null) isCLock = false;
    	var url="/plm/ext/sales/project/listSalesProjectPopup.do?mode=m&modal=N&fncall=" + fncall + "&isCLock=" + isCLock;
    	var name = "";
        var defaultWidth = 1024;
        var defaultHeight = 768;
        var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
        getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
    },
    
    
    /**
	 * 원가 부품분류 (단건 선택)
	 * @param valueField : oid
	 * @param displayField : text
	 * @param dataParam Leaf만 선택하려면 : onlyLeaf=Y
	 * @param dataParam 전체 open하려면   : openAll=Y
	 * @param dataParam 2Level까지 표시하려면   : depthLevel2=Y
	 */
   selectOneCostPartTypeWithCallBack : function(callBackFn, dataParam) {
	    var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
	    var url = "/plm/jsp/dms/costPartTypeTreePopup.jsp?singleSel=true&" + _dataParam;
		
	    if(showModalDialogSupported){
			var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=700px; dialogHeight:700px; center:yes");
			
			//returnValue[0].name   -> partTypeName
			//returnValue[0].id     -> partTypeOid
			//returnValue[0].url    -> 경로
			//returnValue[0].lvl    	-> Type별 레벨
			//returnValue[0].lvlOption  -> Type별 레벨 부등호 : = , > , < , <= , >=
			//returnValue[0].childCfg   -> 자부품 생성여부
			//returnValue[0].parentCfg  -> 모부품 코드
		
			if(typeof returnValue == "undefined" || returnValue == null) {
				return;
			}
			if(typeof callBackFn == "string"){
				window[callBackFn](returnValue);
			}else{
				callBackFn(returnValue);
			}
			
		}else{
			window.showModalDialogFn(url, null,"help=no;scroll=no;dialogWidth=700px;dialogHeight=700px;center=yes", callBackFn);
		}
	},
	
	/**
	 * 원가 부품분류 (다중 선택)
	 * @param callBackFn
	 * @param dataParam 전체 open하려면   : openAll=Y
	 * @param dataParam 2Level까지 표시하려면   : depthLevel2=Y
	 */
	selectCostPartType : function(callBackFn, dataParam) {
		var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
		
		var url = "/plm/jsp/dms/costPartTypeTreePopup.jsp?singleSel=false&" + _dataParam;
		
		if(showModalDialogSupported){
			var returnValue = window.showModalDialog(url,window,"help=no; scroll=no; dialogWidth=700px; dialogHeight:700px; center:yes");
			
			if(typeof returnValue == "undefined" || returnValue == null) {
				return;
			}
			if(typeof callBackFn == "string"){
				window[callBackFn](returnValue);
			}else{
				callBackFn(returnValue);
			}
			
		}else{
			window.showModalDialogFn(url, null,"help=no;scroll=no;dialogWidth=700px;dialogHeight=700px;center=yes", callBackFn);
		}
		
	},
	
	selectArrayCostCodeByPartType : function(returnValue){
		   var obj = new Object();
		   obj.paramData = returnValue;
		   var rvData;
		   try{
		        $.ajax({
		            url : "/plm/ext/cost/code/getCostCodeByPartType.do",
		            type : "POST",
		            dataType : 'json',
		            contentType : "application/json",
		            data  : JSON.stringify(obj),
		            async : false,
		            cache : false,
		            success: function(data) {
		                //window.console.log(eval(data));
		                
		                /*$.each(eval(data), function(i) {
		                	 window.console.log(this.numberCodeName);
		                });*/
		            	rvData = eval(data);
		            },
		            
		            error    : function(xhr, status, error){
		                         hideProcessing();
		                         alert(xhr+"  "+status+"  "+error);
		                         
		            }
		        });
		    }catch(e){
		        alert(e);
		    }
		    /*
		    level
		    leaf
		    partTypeOid
		    partTypeName
		    numberCodeName
		    numberCodeOid
		    oid
		    parentOid
		    */
		    return rvData;
	   }
};