var bom = {
		getSrcRevList : function(){
			
							if ( $("#srcPartNo").val() != null ) {
								var choiceNo  = $("#srcPartNo").val();
								
								$("#srcRev").empty().data('options');
				         		$.ajax({
				         			url : "/plm/extcore/jsp/bom/getPartRevList.jsp?partNo="+choiceNo,
				         			type : "POST",
				         			dataType : 'json',
				         			async : false,
				         			success: function(data) {
				         				//console.log(data);
				         				$("#srcPartName").attr("value",data.partName);
				                        $.each(data.RevList, function() {
				                             $("#srcRev").append("<option value='"+this.rev+"'>"+ this.newrev +"</option>");
				                        });
				                         
				                        
				                     }
				         		});
							}
					 },
		getDesRevList : function(){
				
							if ( $("#desPartNo").val() != null ) {
								var choiceNo  = $("#desPartNo").val();
								
								$("#desRev").empty().data('options');
				         		$.ajax({
				         			url : "/plm/extcore/jsp/bom/getPartRevList.jsp?partNo="+choiceNo,
				         			type : "POST",
				         			dataType : 'json',
				         			async : false,
				         			success: function(data) {
				         				$("#desPartName").attr("value",data.partName);
				                        $.each(data.RevList, function() {
				                             $("#desRev").append("<option value='"+this.rev+"'>"+ this.newrev +"</option>");
				                        });
				                         
				                         
				                         
				                     }
				         		});
							}
						},
		getSrcPartName : function(){
			
						 },	
		getDesPartName : function(){
				
						 }								 
}