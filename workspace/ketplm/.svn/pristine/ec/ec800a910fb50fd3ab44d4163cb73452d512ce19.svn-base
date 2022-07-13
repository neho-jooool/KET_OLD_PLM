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
String partNo = request.getParameter("partNumber");
String partRev = request.getParameter("partRev");




if(partRev.indexOf(".")!=-1)
{
    partRev= partRev.substring(0,partRev.indexOf("."));
}

KETBOMQueryBean bean = new KETBOMQueryBean();
KETBomPartUtil pUtil = new KETBomPartUtil();

String oid =bean.getPartOid(partNo, partRev);
WTPart part = pUtil.getPart(oid);

long id = pUtil.getPartLongId(part);
String partOid = Long.toString(id);

ArrayList revList = new ArrayList();

//revList = bean.getVersionList(partNo);
revList = bean.getPartRevList(partNo, "");

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
String partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType );
String debug = "true"; //false : debug,  true : not debug (모정보 확인용)
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM <%=messageService.getString("e3ps.message.ket_message", "09382") %><%--역전개--%></title>
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
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<!-- script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script-->
<script type="text/javascript" src="/plm/extcore/extjs50/build/ext-all-debug.js"></script>
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/packages/ext-theme-classic/build/resources/ext-theme-classic-all-debug.css"/>
<script type="text/javascript">

/*
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
*/

Ext.define('BomModel', {
    extend: 'Ext.data.TreeModel',
    fields: [
        {name: 'partNo',    type: 'string'}, //, convert: undefined
        {name: 'rev',       type: 'string'},
        {name: 'newrev',    type: 'string'},
        {name: 'ict',       type: 'string'},
        {name: 'partName',  type: 'string'},
        {name: 'lvl',       type: 'string'},
        {name: 'seq',       type: 'string'},
        {name: 'qty',       type: 'string'},
        {name: 'unit',      type: 'string'},
        {name: 'econo',     type: 'string'},
        {name: 'checkout',  type: 'string'},
        {name: 'parent',    type: 'string'},
        {name: 'pver',      type: 'string'}
    ]
});


Ext.onReady(function() 
{		//start onReady 
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
    
    
    var revStore = Ext.create('Ext.data.ArrayStore',{
        fields: ['rev', 'newrev'],
        //data : [['0','0'],['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6'],['7','7'],['8','8'],['9','9']]
          data : [
                  <%
                  if(revList!=null && revList.size()>0)
                  {
                      String revData = "";
                      String revNewData = "";
                      for(int i=0; i < revList.size(); i++)
                      {
                	    Hashtable pdata =  (Hashtable)revList.get(i);
                	    revData = (String)pdata.get("rev");
                	    revNewData = (String)pdata.get("newrev");
                	    
                	    if(i==(revList.size()-1))
                		   out.print("['"+revData+"','"+revNewData+"']");
                	    else
                		  out.print("['"+revData+"','"+revNewData+"'],");
                      }
                  }
                  %>
                 ],
                 renderer: function(value){
                     console.log(value);
                 }        
    }); 
    
    var revCombo = Ext.create('Ext.form.ComboBox',{
        store: revStore,
        displayField: 'newrev',
        //fieldLabel:'Rev',
        mode: 'local',
        valueField: 'rev',
        triggerAction: 'all',
        value: '<%=partRev%>',
        width:50,
        getListParent: function() {
            return this.el.up('.x-menu');
        },
        iconCls: 'no-icon'
    });
    
    var lvl_store = Ext.create('Ext.data.ArrayStore',{
        fields: ['id', 'lvl'],
        data : [['All','All'],['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6'],['7','7'],['8','8'],['9','9']]
    }); 
    
    var levelCombo = Ext.create('Ext.form.ComboBox',{
        store: lvl_store,
        displayField: 'lvl',
        //fieldLabel:'레벨',
        mode: 'local',
        valueField: 'id',
        triggerAction: 'all',
        value: 'All',
        width:50,
        getListParent: function() {
            return this.el.up('.x-menu');
        },
        iconCls: 'no-icon'
    });
    
    var bomtoolbar = Ext.create('Ext.toolbar.Toolbar',{
        region: 'north',
        //layout: 'fit',
        //renderTo: 'toolbarDiv'
        
    });  
    bomtoolbar.render(Ext.getBody());
    bomtoolbar.suspendLayouts();
    
    bomtoolbar.add( 
    		{xtype: 'tbspacer', width:10},  
    		//{xtype: 'label', text:'Rev'}, 
    		//{xtype: 'tbspacer', width:5},  
    		//revCombo,
            //{xtype: 'tbspacer', width:20},   
            {xtype: 'label', text:'<%=messageService.getString("e3ps.message.ket_message", "01346") %>'},//레벨 01346
            {xtype: 'tbspacer', width:5},  
            levelCombo,
            {xtype: 'tbspacer', width:5},
            {cls:'x-btn-icon', icon:'img/BOM_LevelView.gif', tooltip:'<%=messageService.getString("e3ps.message.ket_message", "09383") %>', id:'levelview', disabled:false, handler:onButtonClick}//레벨확장 09383
            );
    
    //Event
    function onButtonClick(btn){
    	//levelview
        if(btn.id=='levelview')
        {
        	var cvalue = levelCombo.getRawValue();
        	//var rvalue = revCombo.getValue();
        	var rvalue = '';
        	//alert(cvalue+":"+rvalue);
        	/*
        	store.setProxy({
        	    type: 'ajax',
        	    timeout: 180000,
        	    url: 'KETBOMReverseList.jsp?partNumber=<!-%=partNo%->&partRev=' + rvalue + '&level=' + cvalue,
        	    reader : {
                    type : 'json'
                }		
        	});
        	
        	store.load();
        	*/
        	
        	
             var cvalue = levelCombo.getRawValue();
             
             if(cvalue=='All')
             {
                 tree.getRootNode().expand(true);
             }else
             {
                 tree.getRootNode().expand(true);
                 root = tree.getRootNode();
                 root.cascadeBy(function(child){
                     var dp = child.data.depth-1;
                     
                     if(dp==cvalue && !child.data.leaf)
                     {
                         child.collapse();
                     }
                 });
             }
            
        	
        	/*
        	var cvalue = levelCombo.getRawValue();
            
            if(cvalue=='All')
            {
                tree.getRootNode().expand(true);
            }else
            {
                tree.getRootNode().expand(true);
                root = tree.getRootNode();
                root.cascadeBy(function(child){
                    var dp = child.data.depth-1;
                    
                    if(dp==cvalue && !child.data.leaf)
                    {
                        child.collapse();
                    }
                });
            }
            */
        }
        
    }
    
    bomtoolbar.resumeLayouts(true);
    
    
  //data 
    var store = Ext.create('Ext.data.TreeStore', {
        model: 'BomModel',
        autoload: true,
        loading: true,
        proxy: {
            type: 'ajax',
            timeout: 180000,
            url: 'KETBOMReverseList.jsp?partOid=<%=partOid%>&partNumber=<%=partNo%>&partRev=<%=partRev%>&level=All',
            reader : {
                type : 'json'
            }
           
        },

        lazyFill: false,
        sorters: [{property:'partNo', direction: 'ASC'}],
        listeners: {
           load: function (str, recs, suc) {
        	    tree.expandAll();
               }
            }
    });
  
   
    var tree = Ext.create('Ext.tree.Panel', {
        title: 'BOM',
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
            text: '<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>',
            width: 150,
            stopSelection: true,
            sortable: true,
            dataIndex: 'partNo',
            locked: true
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'rev',
            hidden: <%=debug%>,
            hideable: false,
            align: 'center'            
        }, {
            text: 'Rev',
            width: 80,
            sortable: true,
            dataIndex: 'newrev',
            align: 'center'            
        }, {
            text: 'Ict',
            width: 80,
            dataIndex: 'ict',
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
            dataIndex: 'partName',
            width: 200,
            align: 'left' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01346") %><%--레벨--%>',
            dataIndex: 'lvl',
            width: 40,
            align: 'center' 
        }, {
            header: 'Seq',
            dataIndex: 'seq',
            width: 50,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,            
            align: 'right'
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01925") %><%--수량--%>',
            dataIndex: 'qty',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            renderer: Ext.util.Format.numberRenderer('0.000'),
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%>',
            dataIndex: 'unit',
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
            dataIndex: 'material',
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
            dataIndex: 'hardnessFrom',
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
            dataIndex: 'hardnessTo',
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
            dataIndex: 'designDate',
            width: 80,
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,        
            align: 'center' 
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "00771") %><%--결재상태--%>',
            dataIndex: 'state',
            width: 80,
            align: 'center' 
        }, {
            header: 'ECO No',
            dataIndex: 'econo',
            width: 100,
            align: 'center',
            renderer: function(value){
                return '<a href="javascript:openViewEco(\''+value+'\');">'+value+'</a>';
            }  
        }, {
            header: '<%=messageService.getString("e3ps.message.ket_message", "01205") %><%--담당자--%>',
            dataIndex: 'checkout',
            width: 80,
            align: 'center' 
        }, {
            header: 'Ref No(TOP)',
            dataIndex: 'reftop',
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
            dataIndex: 'refbtm',
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
            dataIndex: 'parentNo',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }, {
            header: 'parentRev',
            dataIndex: 'pver',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }],
        enableDD : false,
        listeners: {
        	itemdblclick : function ( iView, record, item, index, e, eOpts )
        	{
        		getPartView();
        	}
        }
        

    });
    
    var viewport = Ext.create('Ext.container.Viewport',{
        layout : 'border',
        items :[bomtoolbar, tree]
    });
    
    //topPanel.getHeader().hide();
    
    //tree.getRootNode().expand(true);
    tree.getHeader().hide();
    
    
    function getPartView()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        var pn =rec.data.partNo;
        var ver =rec.data.rev;    
        
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
    
   
}); //end onReady   

//부품 상세조회 팝업
function viewPart(v_poid){
  var url = "/plm/jsp/bom/ViewPart.jsp?poid=" + v_poid;
  openWindow(url,"","1024","768","scrollbars=no,resizable=no,top=200,left=250");
}

</script>

</head>
<body  oncontextmenu="return false">
</body>
</html>