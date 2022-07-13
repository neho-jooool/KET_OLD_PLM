var Atft = {
	
	goRevise : function (){
		if(confirm("개정하시겠습니까?")){
			$('[name=AtftListForm]').attr('action', '/plm/ext/project/atft/reviseAtft.do');
		    $('[name=AtftListForm]').submit();
		    $('[name=AtftListForm]').attr('target', 'download');
		    showProcessing();
		}
	},
	
	goComplete : function (gubun){
		if(gubun=='P1' && P1_completeYN != 'Y'){
			alert("P1 판정근거 항목이 모두 입력되어야 완료 할 수 있습니다.");
			return false;
		}
		if(gubun=='P2' && P2_completeYN != 'Y'){
			alert("P2 판정근거 항목이 모두 입력되어야 완료 할 수 있습니다.");
			return false;
		}
		
		$('[name=sheetdivision]').val(gubun);
	    
		if(confirm("완료하시겠습니까?")){
			$('[name=AtftListForm]').attr('action', '/plm/ext/project/atft/completeAtft.do');
		    $('[name=AtftListForm]').submit();
		    $('[name=AtftListForm]').attr('target', 'download');
		    showProcessing();
		}
	    
	    
	},
	
	goCreate : function(projectOid){
		getOpenWindow2('/plm/ext/project/atft/createAtftForm.do?projectOid='+projectOid,'AtftPopup'+projectOid,1100,800);
	},
	
	atftSave : function(mode){
		var atftArray = new Array();
		var atftInfo;
		
	    $('select[name=category]').each(function(i) {
	        
	    	atftInfo = new Object();
	        atftInfo.classification = $(this).data("classification");
	        atftInfo.sheetdivision = $(this).data("sheetdivision");
	        if(mode == 'U'){
	        	atftInfo.oid = $(this).data("oid");
	        }
	        
	        atftInfo.desision = $(this).val();
	        
	        $('textarea[name=category]').each(function(j) {
	        	
	        	if(i==j){
	        		atftInfo.basis = $(this).val();
	        	}
	        });

	        atftArray.push(atftInfo);
	    });	 
	    
	    var totalAtftInfo = new Object();
	    
	    totalAtftInfo.aftfData = atftArray;
	    
	    var aftfInfo = JSON.stringify(totalAtftInfo);
	    
	    $('[name=aftfInfo]').val(aftfInfo);
	    
	    //console.log(jsonInfo);
	    if(mode == 'C'){
	    	$('[name=atftForm]').attr('action', '/plm/ext/project/atft/atftCreate.do');
	    }
	    
	    if(mode == 'U'){
	    	$('[name=atftForm]').attr('action', '/plm/ext/project/atft/atftModify.do');
	    }
	    
	    //$('[name=atftForm]').attr('target', 'download');
	        
	    $('[name=atftForm]').submit();
	    showProcessing();
	    
	    /* $.ajax({
	    	    method: "post",
	            type: "json",
	            //contentType: "application/json",
	            data : $('[name=atftForm]').serialize(),
	            async: false,
	            cache: false,
	            url: '/plm/ext/project/atft/atftCreate.do',
	            success: function (data) {
	                try{
	                    if(data == 'S'){
	                        alert("저장되었습니다.");
	                    }else if(data == 'Fail'){
	                        alert("실패하였습니다.");
	                    }else{
	                        alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기  바랍니다.");
	                    }
	                    
	                }catch(e){alert(e.message); ret = "E"; }
	            },
	            fail : function(){
	                alert("알 수 없는 오류가 발생하였습니다\n관리자에게 문의하시기 바랍니다");
	                ret = "E";
	            }
	    }); */
	},
	
	p1Pass : function(option){
		
		
		$('select[name=category]').each(function(i) {
    		
            target_division = $(this).data("sheetdivision");
            
    		
    		if(target_division == 'P1'){
    			
    			$(this).find('option').remove();
    			
    			if(option =='P'){
                	$(this).append("<option value='PASS'>PASS</option>");
    			}
    			
    			if(option =='C'){
                	$(this).append("<option value=''>해당없음</option>");
                	$(this).append("<option value='OK'>OK</option>");
                	$(this).append("<option value='NG'>NG</option>");
                	$(this).val('').prop("selected", true);
    			}
            }
    		
        });
		
		$('textarea[name=category]').each(function(i) {
    		
            target_division = $(this).data("sheetdivision");
            
    		
    		if(target_division == 'P1'){
    			
    			if(option =='P'){
    				$(this).val('N/A');
    			}
    			
    			if(option =='C'){
    				$(this).val('');
    			}
            }
    		
        });
        
		if(option =='P'){
            $('#P1_PASS').css("display", "none");   
        	$('#P1_RESET').css("display", "block");
		}
		if(option =='C'){
			$('#P1_PASS').css("display", "block");   
            $('#P1_RESET').css("display", "none"); 
		}
	},
	
	goModify : function(pjtNo,projectOid){
		getOpenWindow2('/plm/ext/project/atft/modifyAtftForm.do?pjtNo='+pjtNo+'&projectOid='+projectOid,'AtftPopup'+pjtNo,1100,800);
	}
}