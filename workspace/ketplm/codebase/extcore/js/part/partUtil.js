
var PartUtil = {

    // 부품 조회
	openView : function( oid ){
		openView(oid);
	},
	
	// 부품 등록
	regView : function( _projectOid, _moldDieNo ){
		
		var projectOid = (_projectOid && typeof _projectOid != "undefined" && _projectOid !=null )? _projectOid:"";
		var moldDieNo = (_moldDieNo && typeof _moldDieNo != "undefined" && _moldDieNo !=null )? _moldDieNo:"";
		
	    var url = "/plm/ext/part/base/registPartForm.do?popup=popup&projectOid="+projectOid+"&moldDieNo="+moldDieNo;
	    //var regWin = window.open(url,"","toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=1,width=1150,height=800");
	    //return regWin;
	    var name = "";
	    var defaultWidth = 1150; // 1150;
	    var defaultHeight = 800; // 800;
	    var opts = "toolbar=0,location=0,directory=0,status=1,menubar=0,scrollbars=1,resizable=0";
	    getOpenWindow2(url,name, defaultWidth, defaultHeight, opts);
	}
		
};


function ClazNode(id, name) {
	this.id = id;
	this.name = name;
};