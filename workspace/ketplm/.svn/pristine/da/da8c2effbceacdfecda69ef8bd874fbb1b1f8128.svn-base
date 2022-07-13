var SuggestUtil = {
		
	/**
	 * 자동 완성을 위한 입력 field bind method 
	 * @param suggest 타입(필수)
	 *        예) USERDEPT : 사용자검색 선택시 (부서 displaySubFieldId, hiddenSubFieldId)
	 *            DEPTUSER : 부서검색 선택시 (사용자 displaySubFieldId, hiddenSubFieldId)
	 * @param displayMainFieldId
	 * @param hiddenMainFieldId
	 * @param displaySubFieldId
	 * @param hiddenSubFieldId
	 */
	multiBind : function (suggestType, displayMainFieldId, hiddenMainFieldId, displaySubFieldId, hiddenSubFieldId){
		var displayMainFieldObj, hiddenMainFieldObj, displaySubFieldObj, hiddenSubFieldObj;
		displayMainFieldObj = CommonUtil.getJqueryObject(displayMainFieldId);
		hiddenMainFieldObj = CommonUtil.getJqueryObject(hiddenMainFieldId);
		displaySubFieldObj = CommonUtil.getJqueryObject(displaySubFieldId);
		hiddenSubFieldObj = CommonUtil.getJqueryObject(hiddenSubFieldId);
			
		this.suggestMulti = $(displayMainFieldObj).autocomplete({
			delay: 0,
			autoFocus: true,
			minLength : 1,
			source : function(request, response) {
				$.ajax({
					url : '/plm/ext/suggest/find.do',
					dataType : 'json',
					data : {
						suggestType : suggestType,
						inputParam : request.term.toUpperCase()
					},
					success : function(data) {
						response($.map(data, function(item) {
							return {
								label : item.label,
								value : item.value,
								subLabel : item.subLabel,
								subValue : item.subValue
							}
						}));
					}
				});
			},
			focus : function(event, ui){ //focus event
				return false;
			},
			select : function(event, ui){ // select event(enter key / click 포함)
				return SuggestUtil.setMultiValue(event, ui, displayMainFieldObj,hiddenMainFieldObj,displaySubFieldObj,hiddenSubFieldObj);
			}
		});
		$(this.suggestMulti).autocomplete("search", "");
		//delete / back space key를 입력시 hidden값 삭제
		this.suggestMulti.keydown(function(event, data, ui){
			var focusIndex = SuggestUtil.getFieldIndex(displayMainFieldObj, event.target);
	        if (event.which == 8 || event.which == 46) {
	            $(hiddenFieldObj).eq(focusIndex).val('').trigger('change');
	        }
	    });
		//focus 이동시 hidden값이 없으면 display값 삭제
		this.suggestMulti.blur(function(event, data, ui){
			var focusIndex = SuggestUtil.getFieldIndex(displayMainFieldObj, event.target);
			if($(hiddenSubFieldObj).eq(focusIndex).length > 0 && $(hiddenSubFieldObj).eq(focusIndex).val() == ''){
				$(displayMainFieldObj).eq(focusIndex).val('').trigger('change');
				if(displaySubFieldObj) $(displaySubFieldObj).eq(focusIndex).val('').trigger('change');
				$(hiddenSubFieldObj).eq(focusIndex).val('').trigger('change');
			}
		});
		
	},

	/**
	 *  자동 완성을 위한 입력 field bind method 
	 * @param suggest 타입(필수)
	 *        예) USERDEPT : 사용자검색 선택시 (callBackFunc 호출 OBJECT 리턴)
	 *            DEPTUSER : 부서검색 선택시 (callBackFunc 호출 OBJECT 리턴)
	 * @param suggestType
	 * @param displayMainFieldId
	 * @param hiddenMainFieldId
	 * @param callBackFunc
	 */
	multiBindCallBackFunc : function (suggestType, displayMainFieldId, hiddenMainFieldId, callBackFunc){
		var displayMainFieldObj, hiddenMainFieldObj;
		displayMainFieldObj = CommonUtil.getJqueryObject(displayMainFieldId);
		hiddenMainFieldObj = CommonUtil.getJqueryObject(hiddenMainFieldId);
			
		this.suggestMulti = $(displayMainFieldObj).autocomplete({
			delay: 0,
			autoFocus: true,
			minLength : 1,
			source : function(request, response) {
				$.ajax({
					url : '/plm/ext/suggest/find.do',
					dataType : 'json',
					data : {
						suggestType : suggestType,
						inputParam : request.term.toUpperCase()
					},
					success : function(data) {
						response($.map(data, function(item) {
							return {
								label : item.label,
								value : item.value,
								subLabel : item.subLabel,
								subValue : item.subValue
							}
						}));
					}
				});
			},
			focus : function(event, ui){ //focus event
				return false;
			},
			select : function(event, ui){ // select event(enter key / click 포함)
				return SuggestUtil.setMultiValueAfterCallFunc(event, ui, displayMainFieldObj,hiddenMainFieldObj,callBackFunc);
			}
		});
		$(this.suggestMulti).autocomplete("search", "");
		//delete / back space key를 입력시 hidden값 삭제
		this.suggestMulti.keydown(function(event, data, ui){
			var focusIndex = SuggestUtil.getFieldIndex(displayMainFieldObj, event.target);
	        if (event.which == 8 || event.which == 46) {
	            $(hiddenFieldObj).eq(focusIndex).val('').trigger('change');
	        }
	    });
		//focus 이동시 hidden값이 없으면 display값 삭제
		this.suggestMulti.blur(function(event, data, ui){
			var focusIndex = SuggestUtil.getFieldIndex(displayMainFieldObj, event.target);
			if($(hiddenFieldObj).eq(focusIndex).length > 0 && $(hiddenFieldObj).eq(focusIndex).val() == ''){
				$(displayMainFieldObj).eq(focusIndex).val('').trigger('change');
			}else if($(hiddenFieldObj).eq(focusIndex).length > 0 && $(displayFieldObj).eq(focusIndex).val() == ''){
				$(hiddenFieldObj).eq(focusIndex).val('').trigger('change');
				$(nameFieldObj).eq(focusIndex).val('').trigger('change');
			}
		});
	},
	
	setMultiValueAfterCallFunc : function(event, ui, displayMainFieldObj,hiddenMainFieldObj,callBackFunc){
		var label = ui.item.label;
		var value = ui.item.value;
		var subLabel = ui.item.subLabel;
		var subValue = ui.item.subValue;
		
		var focusIndex = SuggestUtil.getFieldIndex(displayMainFieldObj, event.target);
		
		$(displayMainFieldObj).eq(focusIndex).val(label.split('/')[0]);
		
		$(displayMainFieldObj).eq(focusIndex).trigger('change');
		
		//hiddenfieldID가 있을 경우에 value값을 추가 한다.
		if(hiddenMainFieldObj && hiddenMainFieldObj != ''){
			//change event가 있을 경우 강제로 event를 발생시킨다.--> event가 않먹는 현상 발생
			$(hiddenFieldObj).eq(focusIndex).val(value).trigger('change');
		}
		
		var obj = new Object();
		obj.USERID =  value;
		obj.USERNAME = label.split('/')[0];
		obj.DEPTID = subValue;
		obj.DEPTNAME = subLabel;
		
		callBackFunc(obj);
		
		return false;
	},
	
	setMultiValue : function(event, ui, displayMainFieldObj,hiddenMainFieldObj,displaySubFieldObj,hiddenSubFieldObj){
		var label = ui.item.label;
		var value = ui.item.value;
		var subLabel = ui.item.subLabel;
		var subValue = ui.item.subValue;
		
		var focusIndex = SuggestUtil.getFieldIndex(displayMainFieldObj, event.target);
		
		$(displayMainFieldObj).eq(focusIndex).val(label.split('/')[0]);
		
		$(displayMainFieldObj).eq(focusIndex).trigger('change');
		
		//hiddenfieldID가 있을 경우에 value값을 추가 한다.
		if(hiddenMainFieldObj && hiddenMainFieldObj != ''){
			//change event가 있을 경우 강제로 event를 발생시킨다.--> event가 않먹는 현상 발생
			$(hiddenMainFieldObj).eq(focusIndex).val(value).trigger('change');
			SuggestUtil.setFont($(hiddenMainFieldObj).eq(focusIndex), $(displayMainFieldObj).eq(focusIndex));
		}
		//nameFieldObj가 있을 경우에 value값을 추가 한다.
		if(displaySubFieldObj && displaySubFieldObj != ''){
			//change event가 있을 경우 강제로 event를 발생시킨다.--> event가 않먹는 현상 발생
			$(displaySubFieldObj).eq(focusIndex).val(subLabel).trigger('change');
		}
		//nameFieldObj가 있을 경우에 value값을 추가 한다.
		if(hiddenSubFieldObj && hiddenSubFieldObj != ''){
			//change event가 있을 경우 강제로 event를 발생시킨다.--> event가 않먹는 현상 발생
			$(hiddenSubFieldObj).eq(focusIndex).val(subValue).trigger('change');
			SuggestUtil.setFont($(hiddenSubFieldObj).eq(focusIndex), displaySubFieldObj?$(displaySubFieldObj).eq(focusIndex):null);
		}
		
		return false;
	},
	
	/**
	 * Autocomplete 기능 제거
	 * @param displayFieldId
	 */
	bindDestroy : function(displayFieldId){
		displayFieldObj = CommonUtil.getJqueryObject(displayFieldId);
		$(displayFieldObj).autocomplete( "destroy" );
	},
	
	/**
	 * 자동 완성을 위한 입력 field bind method
	 * @param suggest 타입(필수)
	 *        예) USER : 사용자검색
	 *            DEPARTMENT : 부서검색
	 *            DIENO : Die No 검색
	 *            REVIEWPROJNO : 검토 Project No 검색
	 *            PRODUCTPROJNO : 제품 Project No 검색
	 *            MOLDPROJNO : 금형 Project No 검색
	 *            CARTYPE : 차종 검색
	 *            PROJECTDOCTYPE : 개발산출물 문서 분류
	 *            TECHDOCTYPE : 기술 문서 분류
	 *            CUSTOMER : 고객사
	 *            PARTNO : 부품 검색
	 *            EPMNO : 도면 검색
	 *            PRODUCTPARTNO : 제품부품 검색
	 *            PRODUCTTYPE :제품분류 
	 *            ECONO : ECO 번호
	 *            ECNNO : ECN 번호 
	 *            ECRNO : ECR 번호
	 *            CUSTOMEREVENT : 최종사용처 
	 *            SPSERIES : 시리즈 
	 *            PARTCLAZ : 부품분류 LEAF (END LEVEL)
	 *            PART2LVLCLAZ : 부품분류 2레벨
	 * @param display 입력Field id/name (필수)
	 *        예) userText
	 * @param hidden 입력 Field id/name(선택)
	 *        예) userTextValue
	 * @param nameFieldId Field id/name(선택)
	 */
	bind : function(suggestType, displayFieldId, hiddenFieldId, nameFieldId){
		var displayFieldObj, hiddenFieldObj, nameFieldObj;
		displayFieldObj = CommonUtil.getJqueryObject(displayFieldId);
		hiddenFieldObj = CommonUtil.getJqueryObject(hiddenFieldId);
		nameFieldObj = CommonUtil.getJqueryObject(nameFieldId);
		
		this.suggest = $(displayFieldObj).autocomplete({
			delay: 500,
			autoFocus: true,
			minLength : this.setMinLength(suggestType),
			source : function(request, response) {
				$.ajax({
					url : '/plm/ext/suggest/find.do',
					dataType : 'json',
					data : {
						suggestType : suggestType,
						inputParam : request.term.toUpperCase()
					},
					success : function(data) {
						response($.map(data, function(item) {
							return {
								label : item.label,
								value : item.value,
								name : item.name
							}
						}));
					}
				});
			},
			focus : function(event, ui){ //focus event
				return false;
			},
			select : function(event, ui){ // select event(enter key / click 포함)
				return SuggestUtil.setValue(event, ui, suggestType,displayFieldObj,hiddenFieldObj,nameFieldObj);
			}
		});
		$(this.suggest).autocomplete("search", "");
		//delete / back space key를 입력시 hidden값 삭제
		this.suggest.keydown(function(event, data, ui){
			var focusIndex = SuggestUtil.getFieldIndex(displayFieldObj, event.target);
	        if (event.which == 8 || event.which == 46) {
	            $(hiddenFieldObj).eq(focusIndex).val('').trigger('change');
	            SuggestUtil.setFont($(hiddenFieldObj).eq(focusIndex), $(displayFieldObj).eq(focusIndex));
	        }
	    });
		//focus 이동시 hidden값이 없으면 display값 삭제
		this.suggest.blur(function(event, data, ui){
			var focusIndex = SuggestUtil.getFieldIndex(displayFieldObj, event.target);
			if(hiddenFieldObj && $(hiddenFieldObj).eq(focusIndex).length > 0 && $(hiddenFieldObj).eq(focusIndex).val() == ''){
				$(displayFieldObj).eq(focusIndex).val('').trigger('change');
				$(nameFieldObj).eq(focusIndex).val('').trigger('change');
				SuggestUtil.setFont($(hiddenFieldObj).eq(focusIndex), $(displayFieldObj).eq(focusIndex));
			}else if(hiddenFieldObj && $(hiddenFieldObj).eq(focusIndex).length > 0 && $(displayFieldObj).eq(focusIndex).val() == ''){
				$(hiddenFieldObj).eq(focusIndex).val('').trigger('change');
				$(nameFieldObj).eq(focusIndex).val('').trigger('change');
				SuggestUtil.setFont($(hiddenFieldObj).eq(focusIndex), $(displayFieldObj).eq(focusIndex));
			}
		});
	},
	
	getFieldIndex : function(arrField, targetObj){
		return $.inArray(targetObj, arrField);
	},
	
	setValue : function(event, ui, suggestType, displayFieldObj,hiddenFieldObj,nameFieldObj){
		var label = ui.item.label;
		var value = ui.item.value;
		var name = ui.item.name;
		var focusIndex = SuggestUtil.getFieldIndex(displayFieldObj, event.target);
		if(suggestType == 'USER'){
			$(displayFieldObj).eq(focusIndex).val(label.split('/')[0]);
		}else if(suggestType == 'CARTYPE' || suggestType == 'PRODUCTTYPE'){
			$(displayFieldObj).eq(focusIndex).val(label.substring(label.indexOf('/')+1));			
		}else if(suggestType == 'PROJECTDOCTYPE'){
			$(displayFieldObj).eq(focusIndex).val(label.split('/')[2]);			
		}else if(suggestType == 'TECHDOCTYPE'){
			$(displayFieldObj).eq(focusIndex).val(label.split('/')[3]);			
		}else{
			$(displayFieldObj).eq(focusIndex).val(label);
		}
		$(displayFieldObj).eq(focusIndex).trigger('change');
		
		//hiddenfieldID가 있을 경우에 value값을 추가 한다.
		if(hiddenFieldObj && hiddenFieldObj != ''){
			//change event가 있을 경우 강제로 event를 발생시킨다.--> event가 않먹는 현상 발생
			$(hiddenFieldObj).eq(focusIndex).val(value).trigger('change');
			SuggestUtil.setFont($(hiddenFieldObj).eq(focusIndex), $(displayFieldObj).eq(focusIndex));
		}
		//nameFieldObj가 있을 경우에 value값을 추가 한다.
		if(nameFieldObj && nameFieldObj != ''){
			//change event가 있을 경우 강제로 event를 발생시킨다.--> event가 않먹는 현상 발생
			$(nameFieldObj).eq(focusIndex).val(name).trigger('change');
		}
		return false;
	},
	
	setFont : function(hiddendFieldObj, displayfieldObj){
		if($(hiddendFieldObj).length == 0){
			return;
		}
		if($(hiddendFieldObj).val() != ''){
			$(displayfieldObj).css('color','blue');//#1c8a6f
			$(displayfieldObj).css('font-weight','bold');
		}else{
			$(displayfieldObj).css('color','black');
			$(displayfieldObj).css('font-weight','normal');
		}
	},

	setMinLength : function(suggestType) {
		if (suggestType == 'USER' || suggestType == 'DEPARTMENT'
			|| suggestType == 'PARTCLAZ'
			|| suggestType == 'PART2LVLCLAZ'
			|| suggestType == 'PROJECTDOCTYPE'
			|| suggestType == 'TECHDOCTYPE'
			|| suggestType == 'CUSTOMER'
			|| suggestType == 'PRODUCTTYPE' || suggestType == 'CARTYPE'
			|| suggestType == 'CUSTOMEREVENT' || suggestType == 'SPSERIES'){
			return 1;// 1자
		}
		else if (suggestType == 'DIENO' || suggestType == 'ECONO'
				|| suggestType == 'ECNNO' || suggestType == 'ECRNO'
				|| suggestType == 'REVIEWPROJNO'
				|| suggestType == 'PRODUCTPROJNO'
				|| suggestType == 'MOLDPROJNO' ){
			return 3; // 3자
		}
		else if (suggestType == 'PARTNO' || suggestType == 'PRODUCTPARTNO' 
			  || suggestType == 'EPMNO'){
			return 4; // 4자
		}
	}
};