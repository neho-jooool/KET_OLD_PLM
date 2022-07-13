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
<title>BOM 역전개</title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/resource/ext-theme-classic/ext-theme-classic-all.css"/>
<style type="text/css">
    .task {
        background-image: url('/plm/extcore/jsp/bom/img/part.gif') !important;
    }
    .task-folder {
        background-image: url('/plm/extcore/jsp/bom/img/prod.gif') !important;
    }
</style>
<script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script>
<script type="text/javascript">


Ext.require([    
    'Ext.Action',
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*',
    'Ext.toolbar.*',
    'Ext.ux.CheckColumn',
    'Ext.container.Viewport',
    'Ext.layout.container.Border',
    'Ext.form.Label'
]);


Ext.define('BomModel', {
    extend: 'Ext.data.TreeModel',
    fields: [
        {name: 'modelNo',           type: 'string'}, //, convert: undefined
        {name: 'modelRev',          type: 'string'},
        {name: 'cad',               type: 'string'},
        {name: 'modelName',         type: 'string'},
        {name: 'partNo',            type: 'string'},
        {name: 'partRev',           type: 'string'},
        {name: 'partName',          type: 'string'},
        {name: 'partCadRelation',   type: 'string'},
        {name: 'partBomRelation',   type: 'string'},
        {name: 'modelOid',          type: 'string'},
        {name: 'partOid',           type: 'string'},
        {name: 'parentModelNo',     type: 'string'}
    ]
});


Ext.onReady(function() 
{		//start onReady 
    Ext.QuickTips.init();
    var w = Ext.getBody().getWidth();
    
    
    
   
    
  //data 
    var store = Ext.create('Ext.data.TreeStore', {
        model: 'BomModel',
        autoload: true,
        loading: true,
        proxy: {
            type: 'ajax',
            timeout: 180000,
            url: 'KETCadBomList.jsp?modelOid=<%=modelOid%>',
            reader : {
                type : 'json'
            }
           
        },

        lazyFill: false,
        sorters: [{property:'modelNo', direction: 'ASC'}],
        listeners: {
           load: function (str, recs, suc) {
        	    tree.expandAll();
               }
            }
    });
  
   
    var tree = Ext.create('Ext.tree.Panel', {
        title: 'CAD BOM',
        region: 'center',
        margins: '0 1 0 0',
        renderTo: Ext.getBody(),
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
            text: '도면번호',
            width: 150,
            stopSelection: true,
            sortable: true,
            dataIndex: 'modelNo',
            locked: true
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'modelRev',
            align: 'center'            
        }, {
            text: 'CAD',
            width: 80,
            dataIndex: 'cad',
            sortable: true,
            align: 'center' 
        }, {
            header: '도면명',
            dataIndex: 'modelName',
            width: 200,
            align: 'center' 
        }, {
            header: '부품번호',
            dataIndex: 'partNo',
            width: 40,
            align: 'center' 
        }, {
            header: 'Rev',
            dataIndex: 'partRev',
            width: 50,     
            align: 'center'
        }, {
            header: '부품명',
            dataIndex: 'partName',
            width: 70,
            align: 'left' 
        }, {
            header: 'P2CadRel',
            dataIndex: 'partCadRelation',
            width: 80,
            align: 'center' 
        }, {
            header: 'P2BomRel',
            dataIndex: 'partBomRelation',
            width: 80,
            align: 'center' 
        }, {
            header: 'modelOid',
            dataIndex: 'modelOid',
            width: 80,
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
        }],
        enableDD : false,
        listeners: {
        
        }
        

    });
    
    var viewport = Ext.create('Ext.container.Viewport',{
        layout : 'border',
        items :[bomtoolbar, tree]
    });
    
    //topPanel.getHeader().hide();
    
    //tree.getRootNode().expand(true);
    tree.getHeader().hide();
    
   
}); //end onReady   
</script>

</head>
<body  oncontextmenu="return false">
</body>
</html>