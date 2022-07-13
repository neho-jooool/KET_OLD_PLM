<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="wt.part.QuantityUnit"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="java.util.*"%>
<%@ page import="e3ps.bom.common.clipboard.BOMCodeData"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.*"%>
<%@ page import="wt.session.SessionHelper"%>
<%@ page import="wt.org.WTUser"%>
<%@ page import="e3ps.common.code.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>


<%
String srcPartNo = request.getParameter("srcPartNo");
String srcPartRev = request.getParameter("srcRev");
String desPartNo = request.getParameter("desPartNo");
String desPartRev = request.getParameter("desRev");

KETBomPartUtil pUtil = new KETBomPartUtil();
KETBOMQueryBean bean = new KETBOMQueryBean();

String oid = bean.getPartOid(srcPartNo, srcPartRev);
WTPart part = pUtil.getPart(oid);
String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType );


String[] strValue = null;
QuantityUnit[] qUnit = QuantityUnit.getQuantityUnitSet();

String unit="";

for( int inx = 0; inx < qUnit.length; inx++ ) {
    String strCode = qUnit[inx].toString();
    String strDesc = qUnit[inx].getLongDescription();
    unit+="[\""+strCode+"\",\""+qUnit[inx].getDisplay()+"\"]";
        if(inx!=(qUnit.length-1))
            unit+=",";   
    
}

//String partType="M"; //P-제품 M-금형 D-Die
String debug = "true"; //false : debug,  true : not debug (모정보 확인용)
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM <%=messageService.getString("e3ps.message.ket_message", "01634") %><%--비교--%></title>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/> 
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/resource/ext-theme-classic/ext-theme-classic-all.css"/>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/extcore/extjs50/build/ext-all-debug.js"></script>
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/ext-theme-classic-all-debug.css"/>
<!-- <script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script> -->
<script type="text/javascript">
/*
Ext.Loader.setConfig({
    enabled: true
});Ext.Loader.setPath('Ext.ux', '/plm/extcore/extjs50/examples/ux');

Ext.require([    
    'Ext.Action',
    'Ext.data.*',
    'Ext.grid.*',
    'Ext.tree.*',
    'Ext.toolbar.*',
    'Ext.container.Viewport',
    'Ext.layout.container.Border',
    'Ext.ux.CheckColumn',
    'Ext.EventManager'
]);
*/

var partType='<%=partType%>';
var  savExcel;
var srcTreeGrid;
var desTreeGrid;

Ext.onReady(function() 
{		
    Ext.QuickTips.init();
    var w = Ext.getBody().getWidth();
    
    var unitStore = Ext.create('Ext.data.ArrayStore',{
        fields: ['id', 'unit'],
        data : [<%=unit%>]
    }); 
    
    var comboBoxRenderer = function(combo) {
		  return function(value) {
		    var idx = combo.store.find(combo.valueField, value);
		    var rec = combo.store.getAt(idx);
		    return (rec === null ? '' : rec.get(combo.displayField) );
		  };
		} 
    
    //data model 정의 
    Ext.define('BomModel', {
        extend: 'Ext.data.TreeModel',
        fields: [
            
            {name: 'partNo',             type: 'auto'},
            {name: 'parentNo',           type: 'auto'},
            {name: 'lvl',                type: 'auto'},
            
            {name: 'srcpartNo',         type: 'string'},
            {name: 'srcrev',            type: 'string'},
            {name: 'newsrcrev',         type: 'string'},
            {name: 'srcpartName',       type: 'string'},
            {name: 'srclvl',            type: 'string'},
            {name: 'srcqty',            type: 'string'},
            {name: 'srcreftop',         type: 'string'},
            {name: 'srcrefbtm',         type: 'string'},
            {name: 'srchardnessFrom',   type: 'string'},
            {name: 'srchardnessTo',     type: 'string'},
            {name: 'srcparentNo',       type: 'string'},
            {name: 'srcpver',           type: 'string'},
            {name: 'srcict',            type: 'string'},
            {name: 'srcdesignDate',     type: 'string'},
            {name: 'srcunit',           type: 'string'},
            {name: 'srcmaterial',       type: 'string'},
            
            {name: 'despartNo',         type: 'string'},
            {name: 'desrev',            type: 'string'},
            {name: 'newdesrev',         type: 'string'},
            {name: 'despartName',       type: 'string'},
            {name: 'deslvl',            type: 'string'},
            {name: 'desqty',            type: 'string'},
            {name: 'desreftop',         type: 'string'},
            {name: 'desrefbtm',         type: 'string'},
            {name: 'deshardnessFrom',   type: 'string'},
            {name: 'deshardnessTo',     type: 'string'},
            {name: 'desparentNo',       type: 'string'},
            {name: 'despver',           type: 'string'},
            {name: 'desict',            type: 'string'},
            {name: 'desdesignDate',     type: 'string'},
            {name: 'desunit',           type: 'string'},
            {name: 'desmaterial',       type: 'string'}
        ]
    });
    
    
    
    //data 
   
    var store = Ext.create('Ext.data.TreeStore', {
        model: 'BomModel',
        proxy: {
            type: 'ajax',
            url: 'KETBomCompareList.jsp?srcPartNo=<%=srcPartNo%>&srcPartRev=<%=srcPartRev%>&desPartNo=<%=desPartNo%>&desPartRev=<%=desPartRev%>'
        },
        listeners: {
            load: function(store, records) {
            	
            } 
        }
    });
    
    
    
   
    srcTreeGrid = Ext.create('Ext.tree.Panel', {
        title: 'BOM <%=messageService.getString("e3ps.message.ket_message", "01634") %><%--비교--%>',
        width: Ext.getBody().getViewSize().width / 2,
        height: 800,
        id: 'srcTree',
        //layout: 'fit',
        region :'west',
        margins: '0 1 0 0',
        renderTo: Ext.getBody(),
        collapsible: true,
        useArrows: true,
        rootVisible: false,
        store: store,
        multiSelect: false,
        columns: [{
            xtype: 'treecolumn',
            text: '<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>',
            width: 150,
            stopSelection: true,
            sortable: true,
            dataIndex: 'srcpartNo',
            locked: true,
            renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
            	if(value=="")
                {
            		//alert("src==>"+ value);
            		//record.data.iconCls = 'task-blank ';                        
                }
            	return value;
            }
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'srcrev',
            hidden: <%=debug%>,
            hideable: false,
            align: 'center'            
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'newsrcrev',
            align: 'center'            
        }, {
            text: 'Ict',
            width: 80,
            dataIndex: 'srcict',
            sortable: true,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>',
            dataIndex: 'srcpartName',
            width: 200,
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%>',
            dataIndex: 'srcqty',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            //renderer: Ext.util.Format.numberRenderer('0.000'),
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%>',
            dataIndex: 'srcunit',
            width: 80,
            editor :{
                        xtype:'combo',
                        store: unitStore,
                        displayField: 'unit',                    
                        valueField: 'id',
                        queryMode: 'local',
                        editable: false,
                        forceSelection: true
                        }, 
            renderer: function(value){
                            if(value != 0 && value != "")
                            {
                                if(unitStore.findRecord("id", value) != null)
                                    return unitStore.findRecord("id", value).get('unit');
                                else 
                                    return value;
                            }
                            else
                                return ""; 
                        },          
            align: 'center'
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%>',
            dataIndex: 'srcmaterial',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,            
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "00792") %><%--경도--%>(F)',
            dataIndex: 'srchardnessFrom',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,            
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "00792") %><%--경도--%>(T)',
            dataIndex: 'srchardnessTo',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,        
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01865") %><%--설계일자--%>',
            dataIndex: 'srcdesignDate',
            width: 80,
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,        
            align: 'center' 
        }, {
            header: 'Ref No(TOP)',
            dataIndex: 'srcreftop',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: 'Ref No(BOTTOM))',
            dataIndex: 'srcrefbtm',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: 'parentNo',
            dataIndex: 'srcparentNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'parentRev',
            dataIndex: 'srcpver',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'allPartNo',
            dataIndex: 'partNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'allParentNo',
            dataIndex: 'parentNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'allLvl',
            dataIndex: 'lvl',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }],
        enableDD : false,
        listeners: {        	
        	select : function(view, record, item, index){
        		
        		         var selectionModel = srcTreeGrid.getSelectionModel();
                         selectionModel.select(item);
        		
                 },
                 itemdblclick : function ( iView, record, item, index, e, eOpts )
                 {
                	 //var rec = srcTree.getSelectionModel().getSelection()[0];
                     var pn =record.data.srcpartNo;
                     var ver =record.data.srcrev;    
                     
                     Ext.Ajax.request({
                         url: 'getPartOid.jsp',
                         method: 'POST',
                         params: {                                      
                                        "partNo" : pn, 
                                        "rev": ver          
                                  },
                         success: function(response, opts){
                             //console.log(response.responseText);
                             viewPart(response.responseText);
                           }
                     });     
                 }     
        }
        
    
    });
    
    srcTreeGrid.getRootNode().expand(true);
    srcTreeGrid.getHeader().hide();
    
    
    
    desTreeGrid = Ext.create('Ext.tree.Panel', {
        title: 'BOM <%=messageService.getString("e3ps.message.ket_message", "01634") %><%--비교--%>',
        width: Ext.getBody().getViewSize().width / 2,
        height: 800,
        id: 'desTree',
        //layout: 'fit',
        region :'center',
        margins: '0 1 0 0',
        renderTo: Ext.getBody(),
        collapsible: true,
        useArrows: true,
        rootVisible: false,
        store: store,
        multiSelect: false,
        columns: [{
            xtype: 'treecolumn',
            text: '<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>',
            width: 150,
            stopSelection: true,
            sortable: true,
            dataIndex: 'despartNo',
            locked: true,
            renderer: function(value, metaData, record, rowIndex, colIndex, store, view){
            	if(value=="")
                {
                	//alert("des ==>"+ value);
                	//record.data.iconCls = 'task-blank ';
                }
            	
                return value;
            }
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'desrev',
            hidden: <%=debug%>,
            hideable: false,
            align: 'center'            
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'newdesrev',
            align: 'center'            
        }, {
            text: 'Ict',
            width: 80,
            dataIndex: 'desict',
            sortable: true,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>',
            dataIndex: 'despartName',
            width: 200,
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%>',
            dataIndex: 'desqty',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            //renderer: Ext.util.Format.numberRenderer('0.000'),
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%>',
            dataIndex: 'desunit',
            width: 80,
            editor :{
                        xtype:'combo',
                        store: unitStore,
                        displayField: 'unit',                    
                        valueField: 'id',
                        queryMode: 'local',
                        editable: false,
                        forceSelection: true
                        }, 
            renderer: function(value){
                            if(value != 0 && value != "")
                            {
                                if(unitStore.findRecord("id", value) != null)
                                    return unitStore.findRecord("id", value).get('unit');
                                else 
                                    return value;
                            }
                            else
                                return ""; 
                        },          
            align: 'center'
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "02452") %><%--재질--%>',
            dataIndex: 'desmaterial',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,            
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "00792") %><%--경도--%>(F)',
            dataIndex: 'deshardnessFrom',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,            
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "00792") %><%--경도--%>(T)',
            dataIndex: 'deshardnessTo',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,        
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01865") %><%--설계일자--%>',
            dataIndex: 'desdesignDate',
            width: 80,
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,        
            align: 'center' 
        }, {
            header: 'Ref No(TOP)',
            dataIndex: 'desreftop',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: 'Ref No(BOTTOM))',
            dataIndex: 'desrefbtm',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: 'parentNo',
            dataIndex: 'desparentNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'parentRev',
            dataIndex: 'despver',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }],
        enableDD : false,
        listeners: {
            select : function(view, record, item, index){
                          var selectionModel = desTreeGrid.getSelectionModel();
                          selectionModel.select(item);
            	           //srcTreeGrid.getSelectionModel().select(index);
            	
                       },
     itemmouseenter: function(view, record, item, index, e, options)
                       {
                    	   //var selectionModel = srcTreeGrid.getSelectionModel();
                           //selectionModel.select(item);
                       },
     itemmouseleave: function(view, record, item, index, e, options)
                       {
                    	   //var selectionModel = srcTreeGrid.getSelectionModel();
                           //selectionModel.deselect(item);
                       },
                       itemdblclick : function ( iView, record, item, index, e, eOpts )
                       {
                    	   var pn =record.data.despartNo;
                           var ver =record.data.desrev;    
                           
                           Ext.Ajax.request({
                               url: 'getPartOid.jsp',
                               method: 'POST',
                               params: {                                      
                                              "partNo" : pn, 
                                              "rev": ver          
                                        },
                               success: function(response, opts){
                                   //console.log(response.responseText);
                                   viewPart(response.responseText);
                                 }
                           });     
                       }   
        }
        
    
    });
    
    desTreeGrid.getRootNode().expand(true);
    desTreeGrid.getHeader().hide();
    
    
    var viewport = Ext.create('Ext.container.Viewport',{
    	layout : 'border',
    	items :[srcTreeGrid, desTreeGrid]
    });
    
    
    srcTreeGrid.view.getEl().on('scroll', function(e, t) {
    	desTreeGrid.view.getEl().dom.scrollTop = t.scrollTop;
    	desTreeGrid.view.getEl().dom.scrollLeft = t.scrollLeft;
    });
    desTreeGrid.view.getEl().on('scroll', function(e, t) {
    	srcTreeGrid.view.getEl().dom.scrollLeft = t.scrollLeft;
    });
    
    
    
   
    Ext.on('resize', function () {
        var width = Ext.getBody().getViewSize().width / 2;
        var height = Ext.getBody().getViewSize().height - 10;
        //alert(width+":"+height);
        Ext.getCmp('srcTree').setSize(width, height);
        Ext.getCmp('desTree').setSize(width, height);
        });
    
  
    
    saveExcel = function()
    {
    	
    	//alert("excel 저장");
    	var items = srcTreeGrid.getStore().data.items;  
        
    	var srcpartNoArr = new Array();
        var srclvlArr = new Array();       
        var srcpartNameArr = new Array();
        var srcqtyArr = new Array();
        var srcunitArr = new Array();
        var srcrevArr = new Array();
        var srcreftopArr = new Array();
        var srcrefbtmArr = new Array();        
        var srcmaterialArr = new Array();
        var srchardnessFromArr = new Array();
        var srchardnessToArr = new Array();
        var srcdesignDateArr = new Array();        
        var srcparentNoArr = new Array();
        var srcpverArr = new Array();
        
        var despartNoArr = new Array();
        var deslvlArr = new Array();       
        var despartNameArr = new Array();
        var desqtyArr = new Array();
        var desunitArr = new Array();
        var desrevArr = new Array();
        var desreftopArr = new Array();
        var desrefbtmArr = new Array();        
        var desmaterialArr = new Array();
        var deshardnessFromArr = new Array();
        var deshardnessToArr = new Array();
        var desdesignDateArr = new Array();        
        var desparentNoArr = new Array();
        var despverArr = new Array();
        
    	
    	for(i=0; i<items.length;i++)
        {
            //console.log(items[i].data.srcpartNo);  
            var itemData = items[i].data;
            console.log(itemData);
            
            //itemData = itemData.replace("/<font color='/gi","");
            //itemData = itemData.replace("/'>/gi","$*$");
            //itemData = itemData.replace("/</font>/gi","");
            
            srcpartNoArr[i]       = itemData.srcpartNo;
            srclvlArr[i]          = itemData.srclvl;
            srcpartNameArr[i]     = itemData.srcpartName;
            srcqtyArr[i]          = itemData.srcqty;
            srcunitArr[i]         = itemData.srcunit;
            srcrevArr[i]          = itemData.newsrcrev;
            srcreftopArr[i]       = itemData.srcreftop;
            srcrefbtmArr[i]       = itemData.srcrefbtm;
            srcmaterialArr[i]     = itemData.srcmaterial;
            srchardnessFromArr[i] = itemData.srchardnessFrom;
            srchardnessToArr[i]   = itemData.srchardnessTo;
            srcdesignDateArr[i]   = itemData.srcdesignDate;
            srcparentNoArr[i]     = itemData.srcparentNo;
            srcpverArr[i]         = itemData.srcpver;
            
            despartNoArr[i]       = itemData.despartNo;
            deslvlArr[i]          = itemData.deslvl;
            despartNameArr[i]     = itemData.despartName;
            desqtyArr[i]          = itemData.desqty;
            desunitArr[i]         = itemData.desunit;
            desrevArr[i]          = itemData.newdesrev;
            desreftopArr[i]       = itemData.desreftop;
            desrefbtmArr[i]       = itemData.desrefbtm;
            desmaterialArr[i]     = itemData.desmaterial;
            deshardnessFromArr[i] = itemData.deshardnessFrom;
            deshardnessToArr[i]   = itemData.deshardnessTo;
            desdesignDateArr[i]   = itemData.desdesignDate;
            desparentNoArr[i]     = itemData.desparentNo;
            despverArr[i]         = itemData.despver;
        }
    	
    	Ext.Ajax.request({
            url: 'KETBomCompareListExcel.jsp?partType='+partType,
            method: 'POST',
            params: {
                           "srcpartNo" : srcpartNoArr, 
                           "srclvl": srclvlArr, 
                           "srcpartName": srcpartNameArr, 
                           "srcqty": srcqtyArr, 
                           "srcunit": srcunitArr, 
                           "srcrev": srcrevArr, 
                           "srcreftop": srcreftopArr, 
                           "srcrefbtm": srcrefbtmArr, 
                           "srcmaterial": srcmaterialArr, 
                           "srchardnessFrom": srchardnessFromArr, 
                           "srchardnessTo": srchardnessToArr, 
                           "srcdesignDate": srcdesignDateArr, 
                           "srcparentNo": srcparentNoArr, 
                           "srcpver": srcpverArr,
                           "despartNo" : despartNoArr, 
                           "deslvl": deslvlArr, 
                           "despartName": despartNameArr, 
                           "desqty": desqtyArr, 
                           "desunit": desunitArr, 
                           "desrev": desrevArr, 
                           "desreftop": desreftopArr, 
                           "desrefbtm": desrefbtmArr, 
                           "desmaterial": desmaterialArr, 
                           "deshardnessFrom": deshardnessFromArr, 
                           "deshardnessTo": deshardnessToArr, 
                           "desdesignDate": desdesignDateArr, 
                           "desparentNo": desparentNoArr, 
                           "despver": despverArr,
                     },
            success: function(response, opts){
                //var objhtml = response.responseText; //content returned from server side
                //console.log(response.responseText);
                //document.hiddenFrame.location.href=response.responseText;
            	document.hiddenFrame.location.href='KETExcelDownLoad.jsp?filepath='+response.responseText;
              }
        });           
    }
    
}); //end onReady   

//부품 상세조회 팝업
function viewPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

</script>
<style type="text/css">
    .task {
        background-image: url('/plm/extcore/jsp/bom/img/part.gif') !important;
    }
    .task-folder {
        background-image: url('/plm/extcore/jsp/bom/img/prod.gif') !important;
    }
    .task-blank {
        background-image: url('/plm/extcore/jsp/bom/img/blank.gif') !important;
    }
    
   
</style>
</head>
<body  oncontextmenu="return false">
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>