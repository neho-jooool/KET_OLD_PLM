<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="wt.part.QuantityUnit"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import=" java.util.*"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%
String modelOid = request.getParameter("modelOid");

String debug = "true"; //false : debug,  true : not debug (모정보 확인용)
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CAD BOM</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/>
<style type="text/css">
<!--
body {
    margin-left: 10px;
    margin-top: 0px;
    margin-right: 10px;
    margin-bottom: 5px;
}
.ellipsis { border: 0;padding: 0 0 0 3px;margin: 0;text-overflow: ellipsis;overflow: hidden;white-space: nowrap; }
-->
</style>

<script type="text/javascript" src="/plm/extcore/extjs50/build/ext-all-debug.js"></script>
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/ext-theme-classic-all-debug.css"/>
<!-- <link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/resource/ext-theme-classic/ext-theme-classic-all.css"/> -->
<!-- <script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script> -->

<script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script>
<script type="text/javascript">

var saveFn;

Ext.Loader.setConfig({
    enabled: true
});Ext.Loader.setPath('Ext.ux', '/plm/extcore/extjs50/examples/ux');

Ext.require([    
    'Ext.Action',
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*',
    'Ext.tip.*',
    'Ext.util.*',
    'Ext.toolbar.*',
    'Ext.ux.CheckColumn',
    'Ext.container.Viewport',
    'Ext.layout.container.Border',
    'Ext.form.field.File',
    'Ext.form.RadioGroup'
]);

Ext.onReady(function() 
{		//start onReady 
    Ext.QuickTips.init();
    var w = Ext.getBody().getWidth();
    
    
    Ext.define('BomModel', {
        extend: 'Ext.data.TreeModel',
        fields: [
            {name: 'modelNo',           type: 'string'}, //, convert: undefined
            {name: 'modelRev',          type: 'string'},
            {name: 'cad',               type: 'string'},
            {name: 'modelName',         type: 'string'},
            {name: 'partNo',            type: 'string'},
            {name: 'partNewRev',        type: 'string'},
            {name: 'partRev',           type: 'string'},
            {name: 'partName',          type: 'string'},
            {name: 'qty',               type: 'string'},
            {name: 'partBomRelation',   type: 'string'},
            {name: 'partCadRelation',   type: 'string'},
            {name: 'modelOid',          type: 'string'},
            {name: 'partOid',           type: 'string'},
            {name: 'parentModelNo',     type: 'string'},
            {name: 'parentPartNo',     type: 'string'}
        ]
    });
  
   
 // 2D DRW validation 파라미터 추가
    var check2DMessage = "";
 
  //data 
    var store = Ext.create('Ext.data.TreeStore', {
        model: 'BomModel',
        autoload: true,
        loading: true,
        proxy: {
            type: 'ajax',
            timeout: 180000,
            url: 'KETCad2BomList.jsp?modelOid=<%=modelOid%>',
            reader : {
                type : 'json'
            }
           
        },
        sorters: [{property:'modelNo', direction: 'ASC'}],
        lazyFill: false,
        //sorters: [{property:'modelNo', direction: 'ASC'}],
        listeners: {
           load: function (str, recs, suc) {
        	   
            	    tree.expandAll();
            	   
            	    // 2D DRW validation 값 추가 시작
            	    
            	    var items = tree.getStore().data.items;  
                    var check2DInfoArr = new Array();
                    check2DMessage = '';
                    for(i=0;i<items.length;i++){
                    	check2DInfoArr[i] = items[i].data.check2DInfo;
                        //alert('validation 3DNO:' + check2DInfoArr[i]);
                    	if(check2DInfoArr[i] != ''){
                        	if(check2DMessage == ''){
                        		check2DMessage = check2DInfoArr[i]; 
                        	}else{
                        		check2DMessage = check2DMessage + ", "+ check2DInfoArr[i];
                        	}
                    	}
                    }
                    
                    // 2D DRW validation 값 추가 종료
               }
            }
    });
  
   
    var tree = Ext.create('Ext.tree.Panel', {
    	title: 'CAD BOM',
        region: 'center',
        collapsible: true,
        useArrows: true,
        rootVisible: false,
        store: store,
        multiSelect: false,
        rowLines:true,
        plugins: [
                  new Ext.grid.plugin.BufferedRenderer()
              ],
        columns: [{
            xtype: 'treecolumn',
            text: '<%=messageService.getString("e3ps.message.ket_message", "01275") %><%--도면번호--%>',
            width: 200,
            stopSelection: true,
            sortable: true,
            dataIndex: 'modelNo',
            locked: true,
            renderer: function(value){
            	//alert(value);
            	return value;
            }
        }, {
            text: 'Rev',
            width: 50,
            sortable: true,
            dataIndex: 'modelRev',
            align: 'center',
            locked: false                    
        }, {
            text: 'CAD',
            width: 80,
            dataIndex: 'cad',
            sortable: true,
            align: 'center',
            locked: false,
            renderer: function(value){
            	//alert(this.el.getHeight());
            	return '<img src="/plm/servlet/e3ps/ProdEcoServlet?cmd=thumbview&oid='+ value +'">';
           }
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01270") %><%--도면명--%>',
            dataIndex: 'modelName',
            width: 200,
            align: 'left',
            locked: false    
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>',
            dataIndex: 'partNo',
            width: 150,
            align: 'center',
            locked: false    
        }, {
            header: 'Rev',
            dataIndex: 'partRevWT',
            width: 50,     
            align: 'center',
            locked: false   
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>',
            dataIndex: 'partName',
            width: 200,
            align: 'left',
            locked: false    
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%>',
            dataIndex: 'qty',
            width: 80,
            align: 'center',
            locked: false    
        }, {
            header: 'BOM',
            dataIndex: 'partBomRelation',
            width: 60,
            align: 'center',
            locked: false   
        }, {
            header: 'Relation',
            dataIndex: 'partCadRelation',
            width: 80,
            align: 'center',
            locked: false    
        }, {
            header: 'Rev',
            dataIndex: 'partRev',
            width: 50,  
            hidden: <%=debug%>,
            align: 'center',
            locked: false   
        }, {
            header: 'modelOid',
            dataIndex: 'modelOid',
            width: 60,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center'
        }, {
            header: 'partOid',
            dataIndex: 'partOid',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'parentModelNo',
            dataIndex: 'parentModelNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'parentPartNo',
            dataIndex: 'parentPartNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'check2DInfo',
            dataIndex: 'check2DInfo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }],
        enableDD : false,
        listeners: {
        
        }
        

    });
    
    var viewport = Ext.create('Ext.container.Viewport',{
        layout : 'border',
        items :[tree]
    });
   
    tree.getHeader().hide();
    
    // cad2bom 적용 버튼 누르면 아래 함수 호출
    saveFn = function(epmOid)
    {
    	
    	// 2D DRW validation 값으로 체크 로직 추가
        if(check2DMessage !=  ''){
            alert('2D Drawing 도면이 없는 3D가 존재하여 적용할 수 없습니다.\n2D Drawing을 체크한 후에 다시 시도해 주십시요.\n3D 모델 번호 : ' + check2DMessage);
            return;   
        }
    	
    	//alert('테스트로 일단 못넘어가게 막음');
    	//return;
    	
        //showProcessing();
        // "F"; //ROOT_NOT_FIRST_CAD
        // "E"; //ROOT_NOT_INCLUDE_EOHEADER
       // "B"; // ROOT_HAS_BOM_ALREADY
        // top model oid를 넘겨줘서를 Validation을 만들어 준다.
        
        var errMsg = "";
     Ext.Ajax.request({
        type: 'get',
        async:false,
        url: '/plm/ext/edm/cad2bom/checkRootValid.do?oid='+epmOid,
        success: function (dataObj) {
           var _data = dataObj.responseText;	
           var data = _data.replace(/\"/g, '');
           
           errMsg = data;
           //alert(data);
           if(data != 'Fail' && data != ''){
               //disabledAllBtn();
               //console.log(data);
               var cadCheckFlag = data.substring(0, data.indexOf('↕'));
               try{
                  if(cadCheckFlag == 'F'){
                       var topPart = data.substring(data.indexOf('↕')+1);
                       alert("<%=messageService.getString("e3ps.message.ket_message", "09428") %> '" + topPart + "' <%=messageService.getString("e3ps.message.ket_message", "09429") %>" );  //최상위 부품 //은 초도가 아닙니다.                    
                       return;
                   }else if(cadCheckFlag == 'E'){
        	          var topPart = data.substring(data.indexOf('↕')+1);
                      alert("<%=messageService.getString("e3ps.message.ket_message", "09428") %> '" + topPart + "' <%=messageService.getString("e3ps.message.ket_message", "09430") %>" );//최상위 부품 //은 EO의 활동계획에 포함되지 않았습니다.
                      return;
                   }else if(cadCheckFlag == 'B'){
                      var topPart = data.substring(data.indexOf('↕')+1);
                      alert("<%=messageService.getString("e3ps.message.ket_message", "09428") %> '" + topPart + "' <%=messageService.getString("e3ps.message.ket_message", "09431") %>" );//최상위 부품 //은 BOM이 존재하여 더 이상 진행할 수 없습니다.                 
                      return;
                   }  
               }catch(e){alert(e.message);}                
           }else{
        	   
        	   if(data == ''){
        		   // validation 통과
        	   }else{
                   alert("<%=messageService.getString("e3ps.message.ket_message", "09432") %> \n <%=messageService.getString("e3ps.message.ket_message", "05140") %>");//Validation 중 오류가 발생했습니다.  //관리자에게 문의하십시요.
                   return;
        	   }
           }
           
           //hideProcessing();
        },
        fail : function(){
            alert("Ajax <%=messageService.getString("e3ps.message.ket_message", "09469") %>"); //Ajax 연결 실패하였습니다.
            return;
            //hideProcessing();
        }
      });
    
     if(errMsg=='')
     {
    	var items = tree.getStore().data.items;  
        
    	var modelNoArr = new Array();
        var modelRevArr = new Array();
        var modelNameArr = new Array();
        
        var partNoArr = new Array();
        var partRevArr = new Array();
        var partNameArr = new Array();
        var qtyArr = new Array();
        
        var partCadRelationArr = new Array();
        var partBomRelationArr = new Array();
        
        var modelOidArr = new Array();
        var partOidArr = new Array();
        
        var parentModelNoArr = new Array();
        var parentPartNoArr = new Array();
        
        for(i=0;i<items.length;i++)
        {
        	modelNoArr[i] = items[i].data.modelNo;
        	modelRevArr[i] = items[i].data.modelRev;
        	modelNameArr[i] = items[i].data.modelName;
        	
        	partNoArr[i] = items[i].data.partNo;
        	partRevArr[i] = items[i].data.partRev;
        	partNameArr[i] = items[i].data.partName;
        	qtyArr[i] = items[i].data.qty;
        	
        	partCadRelationArr[i] = items[i].data.partCadRelation;
        	partBomRelationArr[i] = items[i].data.partBomRelation;
        	
        	modelOidArr[i] = items[i].data.modelOid;
        	partOidArr[i] = items[i].data.partOid;
        	parentModelNoArr[i] = items[i].data.parentModelNo;
        	parentPartNoArr[i] = items[i].data.parentPartNo;
        }
        
        
        Ext.Ajax.request({
            url: './KETCad2BomSave.jsp',
            async:false,
            method: 'POST', 
            params: {
                           "modelNo" : modelNoArr, 
                           "modelRev": modelRevArr, 
                           "modelName": modelNameArr, 
                           "partNo": partNoArr, 
                           "partRev": partRevArr, 
                           "partName": partNameArr, 
                           "qty": qtyArr, 
                           "partCadRelation": partCadRelationArr, 
                           "partBomRelation": partBomRelationArr, 
                           "modelOid": modelOidArr, 
                           "partOid": partOidArr, 
                           "parentModelNo": parentModelNoArr, 
                           "parentPartNo": parentPartNoArr             
                     },
            success: function(response, opts){
                
                //var validRsult = Ext.decode(response.responseText);
                //console.log(response.responseText);
                alert("<%=messageService.getString("e3ps.message.ket_message", "09418") %>");//저장되었습니다.
                store.load();
              }
        });  
      }   
    }
     
   
}); //end onReady   


</script>
<style type="text/css">
    .task {
        background-image: url('/plm/extcore/jsp/bom/img/part.gif') !important;
    }
    .task-folder {
        background-image: url('/plm/extcore/jsp/bom/img/prod.gif') !important;
    }
    
   
</style>
</head>
<body  oncontextmenu="return false">
</body>
</html>