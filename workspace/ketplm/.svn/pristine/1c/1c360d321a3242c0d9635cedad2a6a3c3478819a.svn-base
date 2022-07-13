function initPage(_isInitContent) {
	form = document.forms[0];

	var lCategory = form.category.value;
	var lCadAppType = form.cadAppType.value;

	initReledPart(lCategory,_isInitContent);

	initModel(lCategory,_isInitContent);

	initHelp(lCategory,_isInitContent);

	initDefault(lCategory,_isInitContent);

	
}

function initReledPart(_cat,_isInitContent) {

	var lTorepPartDivObj = document.getElementById("torepPartDiv");
	var lRelatedPartsDivObj = document.getElementById("relatedPartsDiv");

	var lEssMarkByPartObj = document.getElementById("essentialElementMarkByPart");

	if(_isInitContent==true) {
		removeSeledItem('part','torep');
		deleteRowByReledPart(true, '');

		lEssMarkByPartObj.style.display = "";

		lTorepPartDivObj.style.display = "none";
		lRelatedPartsDivObj.style.display="none";

		return;
	}
	
	if( _cat == '') {
		removeSeledItem('part','torep');
		deleteRowByReledPart(true, '');

		lEssMarkByPartObj.style.display = "";

		lTorepPartDivObj.style.display = "none";
		lRelatedPartsDivObj.style.display="none";

	} else {
		
		if(checkNonPart(_cat)) {
			lTorepPartDivObj.style.display = "none";
			lRelatedPartsDivObj.style.display="none";

		} else {
			lTorepPartDivObj.style.display = "";
			
			if(!isOnlyRefesCat(_cat)) {
				lRelatedPartsDivObj.style.display="";
			} else {
				lRelatedPartsDivObj.style.display="none";
			}

			if(checkEssPartCat(_cat)) {
				lEssMarkByPartObj.style.display = "";
			} else {
				lEssMarkByPartObj.style.display = "none";
			}
		}
		
		deleteRowByReledPart(true, '');
		removeSeledItem('part','torep');
	}
}

function initModel(_cat,_isInitContent) {

	var lTorelModelNonPartDiv = document.getElementById("torelModelNonPartDiv");
	if(_isInitContent == true) {
		lTorelModelNonPartDiv.style.display="none";
		removeSeledItem('epm','torelmodel');
		return;
	}

	if(checkNonPart(_cat) && checkTypeOnModelRef(_cat)) {
		lTorelModelNonPartDiv.style.display="";
	}else{
		lTorelModelNonPartDiv.style.display="none";
		removeSeledItem('epm','torelmodel');
	}

}

function initHelp(_cat,_isInitContent) {
	if(defedCatValueArr.length) {
		for(var i = 0; i < defedCatValueArr.length; i++) {
			var descRef = document.getElementById("desc_"+defedCatValueArr[i]);
			if( (descRef != null) && (descRef != "undefined") ) {
				if(_isInitContent == true) {
					descRef.style.display = "none";
					continue;
				}
				
				if(defedCatValueArr[i] == _cat) {
					descRef.style.display = "inline";
				}else{
					descRef.style.display = "none";
				}
			}
		}
	}	
}

//도면 번호/명, 첨부파일
function initDefault(_cat,_isInitContent) {
	form = document.forms[0];

	form.number.value = "";
	form.name.value = "";

	if(checkTypeOnKeyInNums(_cat)) {
		form.number.readOnly = false;
		form.number.style.backgroundColor="";
	}else{
		form.number.readOnly = true;
		form.number.style.backgroundColor="#EFEFEF";
	}

	if(checkTypeOnKeyInNames(_cat)) {
		form.name.readOnly = false;
		form.name.style.backgroundColor="";
	}else{
		form.name.readOnly = true;
		form.name.style.backgroundColor="#EFEFEF";
	}


	var lToDwgDivObj = document.getElementById("toDwgDiv");
	if(_isInitContent == true) {
		lToDwgDivObj.style.display = "none";
	} else if((_cat == '') || checkEcadCats(_cat)) {//if((_cat == '') || (_cat=='ECAD_DRAWING')) {
		lToDwgDivObj.style.display = "none";
	} else {
		lToDwgDivObj.style.display = "";
	}
	

	var lTableDummyfileFlagObj = document.getElementById("table_DummyFile_Flag");
	if(_isInitContent == true) {
		lTableDummyfileFlagObj.style.display = "none";
	} else if((_cat == '') || !checkDummyFileCats(_cat)) {
		lTableDummyfileFlagObj.style.display = "none";
	} else {
		lTableDummyfileFlagObj.style.display = "";
	}

	selectRadio('isDummyFile','false');

	var lToEcadDivObj = document.getElementById("toEcadDiv");
	if(checkEcadCats(_cat)) {//if(_cat=='ECAD_DRAWING') {
		lToEcadDivObj.style.display = "";

		form.number_pcb.value = "";
		form.name_pcb.value = "";

		form.number_sch.value = "";
		form.name_sch.value = "";

		form.number_dwg.value = "";
		form.name_dwg.value = "";

	} else {

		form.number_pcb.value = "";
		form.name_pcb.value = "";

		form.number_sch.value = "";
		form.name_sch.value = "";

		form.number_dwg.value = "";
		form.name_dwg.value = "";

		lToEcadDivObj.style.display = "none";
	}
}
