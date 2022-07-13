var CommonUtil = {
	/**
	 * Jquery multiselect combo 생성 
	 * @param id
	 * @param minWidth
	 * @returns
	 */
	multiSelect : function(id, minWidth) {
		// Multi Combo 생성
        return $('#'+id).multiselect({
            minWidth: minWidth,
            noneSelectedText: LocaleUtil.getMessage('01802'),//선택
            checkAllText    : LocaleUtil.getMessage('02498'),//전체선택
            uncheckAllText  : LocaleUtil.getMessage('02500')//전체해제
        });
	},
	
	/**
	 * Jquery singleSelect combo 생성 
	 * @param id
	 * @param minWidth
	 * @returns
	 */
	singleSelect : function(id, minWidth) {
		// Multi Combo 생성
		return $('#'+id).multiselect({
			noneSelectedText: LocaleUtil.getMessage('01802'),//선택
			header : false,
			multiple : false,
			selectList : 1,
			minWidth: minWidth
        });
	},
	
	/**
	 * jquery object를 반환한다.
	 * 
	 * @param field name 또는 id
	 * @parma 동일한 field name이 여러개인 경우
	 * @returns
	 */
	getJqueryObject : function(field, isMulti) {
		if(typeof(field) == 'function'){
			return field;
		}
		if(typeof(field) == 'object'){
			return field;
		}
		if ($('#' + field).length > 0) {
			return $('#' + field);
		}
		if(isMulti){
			return $('[name*=' + field + ']');
		}else{
			return $('[name=' + field + ']');
		}
	},
	
	/**
	 * Tab 생성(iframe사용)
	 * @param tabId
	 */
	tabs : function(tabId, activeIdx){
		
		if(activeIdx == null) activeIdx = 0;
		
		var tabs = $('#'+tabId).tabs({
			cache: true,
			active: activeIdx,
			beforeActivate : function(event, ui){
				var tab = ui.newTab;
				var newPanel = ui.newPanel;
				var url = $(tab).context.rel;
				if ($(newPanel).find("iframe").length == 0 && url) {
			        var html = [];
			        html.push('<div class="tabIframeWrapper">');
			        html.push('<iframe class="iframetab" border=1 src="' + url + '">Load Failed?</iframe>');
			        html.push('</div>');
			        $(newPanel).append(html.join(""));
//			        $(newPanel).find("iframe").height($(window).height()+20);
			    }
			}
		});
		tabs.delegate( "span.ui-icon-close", "click", function() {
			var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
			$( "#" + panelId ).remove();
			tabs.tabs( "refresh" );
	    });
		return tabs;
	},

	/**
	 * field value 삭제(arguments 만큼 삭제)
	 */
	deleteValue : function() {
		for ( var i = 0; i < arguments.length; i++) {
			$('[name=' + arguments[i] + ']').val('');
		}
	},
	
	/**
	 * field value 삭제(arguments 만큼 삭제)
	 */
	deleteValueById : function() {
		for ( var i = 0; i < arguments.length; i++) {
			$('#' + arguments[i]).val('');
		}
	},
	
	/**
	 * field value 삭제(arguments 만큼 삭제)
	 */
	deleteValueByClass : function() {
		for ( var i = 0; i < arguments.length; i++) {
			$('.' + arguments[i]).val('');
		}
	},
	
	/**
	 * 달력 field value 삭제
	 * @param obj
	 */
	deleteDateValue : function(obj){
		var dateObj = $('[name=' + obj + ']');
		if(dateObj.length == 0){
			$(dateObj).val('');
		}else{
			$(dateObj).each(function(){
				if($(this).next().next()[0] == $(event.srcElement)[0]){
					$(this).val('');
				}
			});
		}
	},
	/**
	 * 공통 Validation
	 * @returns {Boolean} - validation check 성공, 실패
	 */
	checkEsseOk : function() {

	    var flag = true;
	    $("[esse=true]").each(function(){
	    	
	    	var alertMsg = "";
	    	switch (this.type) {
		        case "text":
		        case "hidden":
		        case "textarea":
		            alertMsg = LocaleUtil.getMessage('03458'); // \n 항목을 입력해 주세요."; // 03458
		            break;
		        case "select-multiple":
		        case "select-one":
		        case "radio":
		        case "checkbox":
		        case "file":
		            alertMsg = LocaleUtil.getMessage('03459'); // "\n 항목을 선택해 주세요."; // 03459
		            break;
		        case "select-one":
		            alertMsg = LocaleUtil.getMessage('03459'); // "\n 항목을 선택해 주세요."; // 03459
		            break;
	        }
	        
	        if (this.type == "select-multiple" && (this.options.selectedIndex == -1 || this.value.replace(/\s*$/, '') == "")) {
	            flag = false;
	            //if(this.getAttribute("ketMultiSelect")!=null && this.getAttribute("ketMultiSelect") == 'Y'){
	            //	alertMsg = LocaleUtil.getMessage('03460'); // "\n 항목을 추가해 주세요."; // 03460
	            //}
	        } else if (this.type == "select-one" &&  (this.options.selectedIndex == -1 || this.value.replace(/\s*$/, '') == "")) {
	            flag = false;
	        } else if (this.type == "checkbox") {
	            flag = false;
	        } else if (this.type == "radio") {
	            var rFlag = false;
	            var obj = document.getElementsByName(this.name);
	            for ( var j = 0; j < obj.length; j++) {
	                if (obj[j].checked == true) {
	                    rFlag = true;
	                }
	            }
	            flag = rFlag;
	        } else if (this.type == "hidden" && this.value.replace(/\s*$/, '') == "") {
	            flag = false;
	        } else if (this.type == "textarea" && this.value.replace(/\s*$/, '') == "") {
	            flag = false;
	        } else if (this.type == "text" && this.value.replace(/\s*$/, '') == "") {
	            flag = false;
	        } else if (this.type == "file" && this.value.replace(/\s*$/, '') == "") {
	            flag = false;
	        }

	        if (!flag) {
	            alert("'" + this.getAttribute('esseLabel') + "'" + alertMsg);
	            this.focus();
	            return false; // break;
	        }
	    	
	    });
	    
	    return flag;
	},
	
	/**
	 * 숫자 입력 필드
	 * @param 필드 name
	 * @param minus 기호 포함 유무
	 */
	setNumberField : function(fieldName,hasMinus){
		var reg = /[^\.0-9]/;
		if(hasMinus){
			reg = /[^\-\.0-9]/gi;
		}
		$('[name='+fieldName+']').keypress(function(e){
			var verified = (e.which == 8 || e.which == undefined || e.which == 0) ? null : String.fromCharCode(e.which).match(reg);
		    if (verified) {e.preventDefault();}
		});
	}
};