var CalendarUtil = {
	/**
	 * 휴무일(WC에서는 휴무일로 명칭함)
	 */
	nonWorkingDays : [],
	/**
	 * 날짜 입력 calrendar
	 * @param 시작Field
	 * @param 종료Field(기한을 적용할 경우에만 사용 된다) - 생략 가능
	 */	
	dateInputFormat : function(startDateField, endDateField, options) {
		var startDateObj = CommonUtil.getJqueryObject(startDateField,true);
		var endDateObj = CommonUtil.getJqueryObject(endDateField);
		var callBackFn;
		//maskedinput plugin 설정
		if(endDateField && typeof(endDateField) == 'function'){
			callBackFn = endDateField;
			this.setMask(startDateObj, callBackFn);
		}else if(endDateField && typeof(endDateField) == 'string'){
			this.setMask(startDateObj);	
			this.setMask(endDateObj);
		}else{
			this.setMask(startDateObj,endDateField);
		}
		
		//datepicker plugin 설정
		var datepickerObj = CalendarUtil.getDatepickerObj();
		//CallBackFn이 있으면
		if(callBackFn){
			datepickerObj.onSelect = function(selectedDate, obj){
				callBackFn(selectedDate,obj.input);
			};
		}
		//option이 있으면 datepickerOjbj에 추가 한다.
		if(options){
			$.extend(datepickerObj, options);
		}
		//datepicker 설정
		if(arguments.length == 2 && typeof(endDateField) != 'function'){
			datepickerObj.onSelect = function(selectedDate) {
				var option = this.id == $(startDateObj).attr('id') ? "minDate" : "maxDate",
				    instance = $(this).data("datepicker"),
				    date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
				dates.not(this).datepicker("option", option, date);
			};
//			if($(startDateObj).not('empty')){
//				datepickerObj.minDate = new Date($(startDateObj).val());
//			}
//			if($(endDateObj).not('empty')){
//				datepickerObj.maxDate = new Date($(endDateObj).val());
//			}
			var dates = $('[name=' + startDateField + '], [name='+ endDateField + ']').datepicker(datepickerObj);
		}else{
			$(startDateObj).datepicker(datepickerObj);			
		}
	},
	
	/**
	 * 년월 입력 (yyyy-mm)
	 * @param 시작Field
	 * @param 종료Field(기한을 적용할 경우에만 사용 된다) - 생략 가능
	 */	
	monthInputFormat : function(startMonthField, endMonthField, options) {
		var startMonthObj = CommonUtil.getJqueryObject(startMonthField,true);
		var endMonthObj = CommonUtil.getJqueryObject(endMonthField);
		var callBackFn;
		//maskedinput plugin 설정
		if(endMonthField && typeof(endMonthField) == 'function'){
			callBackFn = endMonthField;
			this.setMonthMask(startMonthObj, callBackFn);
		}else if(endMonthField && typeof(endMonthField) == 'string'){
			this.setMonthMask(startMonthObj);	
			this.setMonthMask(endMonthObj);
		}else{
			this.setMonthMask(startMonthObj,endMonthField);
		}
		
		//monthpicker plugin 설정
		var monthpickerObj = CalendarUtil.getMonthpickerObj();
		//CallBackFn이 있으면
		if(callBackFn){
			monthpickerObj.onSelect = function(selectedMonth, obj){
				callBackFn(selectedMonth,obj.input);
			};
		}
		
		//option이 있으면 monthpickerObj에 추가 한다.
		if(options){
			$.extend(monthpickerObj, options);
		}
		
		//monthpicker 설정
		if(arguments.length == 2 && typeof(endMonthField) != 'function'){
			/*
			monthpickerObj.onSelect = function(selectedMonth) {
				var option = this.id == $(startMonthObj).attr('id') ? "minDate" : "maxDate",
				    instance = $(this).data("monthpicker"),
				    date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
				dates.not(this).datepicker("option", option, date);
			};
			*/
			var dates = $('[name=' + startMonthField + '], [name='+ endMonthField + ']').monthpicker(monthpickerObj);
			
		}else{
			$(startMonthObj).monthpicker(monthpickerObj);			
		}
		$('[name=' + startMonthField + '], [name='+ endMonthField + ']').css("margin-right","3px");
		$('[name=' + startMonthField + '], [name='+ endMonthField + ']').css("width","60px");
	},
	getDatepickerObj : function(){
		return {
			showOn: "button", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
			buttonImage: "/plm/portal/images/icon_6.png", // 버튼 이미지
			buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
			changeMonth : true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
			changeYear : true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
			nextText : '다음 달', // next 아이콘의 툴팁.
			prevText : '이전 달', // prev 아이콘의 툴팁.
			yearRange : 'c-4:c+1', // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
			currentText : '오늘 날짜', // 오늘 날짜로 이동하는 버튼 패널
			closeText : '닫기', // 닫기 버튼 패널
			dateFormat : "yy-mm-dd", // 텍스트 필드에 입력되는 날짜 형식.
			showAnim : "slide", // 애니메이션을 적용한다.
			showMonthAfterYear : true, // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
			dayNames: ['일','월','화','수','목','금','토'],
			dayNamesMin : ['일', '월', '화', '수', '목', '금', '토'], // 요일의 한글 형식.
			dayNamesShort: ['일','월','화','수','목','금','토'],
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],// 월의 한글 형식.
			beforeShow : function(input, inst){
				var date = new Date();
				if($(input).val() && $(input).val() == '____-__-__'){
					$(input).val(''); // 혹시모를 '____-__-__'이 있을 수 있으니 지워준다.
					date = $.datepicker.parseDate('yy-mm-dd', $(this).val());
				}
			}
		}
	},
	getMonthpickerObj : function(){
		return {
			showOn: "button", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
			buttonImage: "/plm/portal/images/icon_6.png", // 버튼 이미지
			buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
			changeMonth : true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
			changeYear : true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
			nextText : '다음 년도', // next 아이콘의 툴팁.
			prevText : '이전 년도', // prev 아이콘의 툴팁.
			yearRange : 'c-4:c+1', // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
			dateFormat : "yy-mm", // 텍스트 필드에 입력되는 날짜 형식.
			showAnim : "slide", // 애니메이션을 적용한다.
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			monthNamesShort : ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],// 월의 한글 형식.
			beforeShow : function(input, inst){
				var date = new Date();
				if($(input).val() && $(input).val() == '____-__'){
					$(input).val(''); // 혹시모를 '____-__이 있을 수 있으니 지워준다.
				}
			}
		}
	},
	setMask : function(field, callBackFn){
		$.mask.definitions['y'] = '[12]';
		$.mask.definitions['m'] = '[01]';
		$.mask.definitions['d'] = '[0-3]';
		$(field).mask('y999-m9-d9',{
			completed : function() {
				var datevalue = this.val();
				if (datevalue != null || datevalue != '') {
					var tmp = datevalue.split('-');
					var year = tmp[0];
					var month = tmp[1];
					var day = tmp[2];
					var fromYear = 1900;
					var now = new Date();

					if (day >= 1 && day <= 31) {
						if (month >= 1 && month <= 12) {
							if (year >= fromYear) {
							} else {
								alert(datevalue+' 해당 날짜는 잘못된 날짜 형식입니다.');
								this.select();
							}
						} else {
							alert(datevalue+' 해당 날짜는 잘못된 날짜 형식입니다.');
							this.select();
						}
					} else {
						alert(datevalue+' 해당 날짜는 잘못된 날짜 형식입니다.');
						this.select();
					}
				}
				if(callBackFn){
					callBackFn(datevalue,this);
				}
			}
		});
	},
	setMonthMask : function(field, callBackFn){
		$.mask.definitions['y'] = '[12]';
		$.mask.definitions['m'] = '[01]';
		$(field).mask('y999-m9',{
			completed : function() {
				var datevalue = this.val();
				if (datevalue != null || datevalue != '') {
					var tmp = datevalue.split('-');
					var year = tmp[0];
					var month = tmp[1];
					var fromYear = 1900;
					var now = new Date();
					
					window.console.log(month);
					
					if (month >= 1 && month <= 12) {
						if (year >= fromYear) {
						} else {
							alert(datevalue+' 해당 날짜는 잘못된 날짜 형식입니다.');
							this.select();
						}
					}else{
						alert(datevalue+' 해당 날짜는 잘못된 날짜 형식입니다.');
						this.select();
					}
				}
				if(callBackFn){
					callBackFn(datevalue,this);
				}
			}
		});
	},
	
	/**
	 * 달력에서 휴무일을 비활성화 시킨다. 
	 * @param date
	 * @returns {Array}
	 */
	setDisableDay : function(date){
		var ymd = date.getFullYear(); 
		if(date.getMonth()+1 <10) ymd += "-0"+(date.getMonth()+1);
		else ymd += "-"+(date.getMonth()+1);
		if(date.getDate()<10) ymd += "-0"+date.getDate();
		else ymd += '-'+date.getDate();
		for(var i=0; i<CalendarUtil.nonWorkingDays.length;i++){
			if(ymd == CalendarUtil.nonWorkingDays[i]){
				return [false];
			}
		}
		return [true];
	},
	
	/**
	 * 휴무일을 서버로 부터 갱신 한다.
	 * 갱신 주기(달력 open, 날짜 변경)
	 * @param year
	 * @param month
	 */
	loadNonWorkingDays : function(year, month){
		var yyyymm;
		if (year != undefined && month != undefined) {
			yyyymm = year + '-' +month;
		}else{
			var date = new Date();
			year = date.getFullYear();
			month = date.getMonth() + 1;
		}
		if(month < 10) month = '0'+month;
		yyyymm = year + '-' +month;			
		$.ajax({
			url : '/plm/ext/calendar/getNonWorkingDaysByMonth.do?yyyymm=' + yyyymm,
			type : "POST",
			dataType : 'json',
			async : false,
			success : function(json) {
				for(var i=0; i<json.length; i++){
					CalendarUtil.nonWorkingDays[i] = json[i];
				}
			}
		});
	},
	
	/**
	 * 휴무일 check
	 * @param yyyy-mm-dd
	 */
	isNonWokingDay : function(checkDate) {
		$.ajax({
			url : "/plm/ext/calendar/isNonWorkingDay.do?checkDate=" + checkDate,
			type : "POST",
			dataType : 'json',
			success : function(json) {
				if (json) {
					alert('선택된 날짜는 휴무일 입니다.');
				}
			}
		});
	}
};