<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="wt.part.QuantityUnit"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%
     
String partNumber  = (String)request.getParameter("partNo");

System.out.println("View###################################partNumber=========>"+partNumber);

if(partNumber==null || partNumber.equals(""))
    partNumber = "S1";

     QuantityUnit[] qUnit = QuantityUnit.getQuantityUnitSet();

     String unit="";

     String unitH = "";

     for( int inx = 0; inx < qUnit.length; inx++ ) {
         String strCode = qUnit[inx].toString();
         String strDesc = qUnit[inx].getLongDescription();
         
         
         if(strCode.startsWith("KET_"))
         {
             unit+="[\""+qUnit[inx].getLongDescription()+"\",\""+strCode+"\",\""+qUnit[inx].getDisplay()+"\"]";
             unitH+= "unitInfo[\""+strCode +"\"]=\""+qUnit[inx].getLongDescription()+"\";";
         }
         if(unit.lastIndexOf("]")!=-1)
         {
             unit+=","; 
             unitH+="\n";
         }
         System.out.println("strCode["+strCode+"]==="+"LongDescription["+qUnit[inx].getLongDescription()+"]==="+"strDesc["+strDesc+"]");
     }

     unit = unit.substring(0, unit.length()-1);
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=messageService.getString("e3ps.message.ket_message", "01565") %><%--부품검색--%></title>
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/resource/ext-theme-classic/ext-theme-classic-all.css"/>
<link href="/plm/portal/css/e3ps.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/examples/ux/css/CheckHeader.css" />
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

<script type="text/javascript" src="/plm/extcore/js/shared/localeUtil.js"></script>
<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/extcore/js/part/partUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script>


<script type="text/javascript">
Ext.require([
             'Ext.grid.*',
             'Ext.data.*',
             'Ext.panel.*',
             'Ext.container.Viewport',
             'Ext.layout.container.Border',
             'Ext.grid.column.*'
         ]);
         
var selPartNo = "";   
var selPartName = "";
var listView;
var partStore;
var selectRow=0;

/*
Ext.define('Webapp.ux.grid.column.RadioGroupColumn', {
    extend: 'Ext.grid.column.Column',
    alias: 'widget.radiogroupcolumn',
 
    
    defaultRenderer: function(value, metadata, record, rowIndex, colIndex, store, view) {
        var column = view.getGridColumns()[colIndex];
        var html = '';
        if (column.radioValues) {
            for (var x in column.radioValues) {
                var radioValue = column.radioValues[x],
                    radioDisplay;
                if (radioValue && radioValue.fieldValue) {
                    radioDisplay = radioValue.fieldDisplay;
                    radioValue = radioValue.fieldValue;
                } else {
                    radioDisplay = radioValue;
                }
                if (radioValue == value) {
                    html = html + column.getHtmlData(record.internalId, store.storeId, rowIndex, radioValue, radioDisplay, 'checked');
                } else {
                    html = html + column.getHtmlData(record.internalId, store.storeId, rowIndex, radioValue, radioDisplay);
                }
            }
        }
        return html;
    },
 
    getHtmlData: function(recordId, storeId, rowIndex, value, display, optional) {
        var me = this,
            clickHandler, readOnly;
        var name = storeId + '_' + recordId;
        var clickHandler;
        var onClick;
        if (me.readOnly) {
            readOnly = 'readonly';
            onClick = '';
        } else {
            readOnly = '';
            onClick = "onclick=\"this.checked=true;Ext.StoreManager.lookup('" + storeId + "').getAt(" + rowIndex + ").set('" + me.dataIndex + "', '" + value + "');\"'";
        }
        return "<input " + onClick + " type='radio' " + optional + ">" + display;
    }
});
*/

Ext.onReady(function(){
    Ext.QuickTips.init();
    
    
    var unitStore = Ext.create('Ext.data.ArrayStore',{
        fields: ['group', 'id', 'unit'],
        data : [<%=unit%>],
        listeners:{
            
        }
    }); 
    
    
  //data model 정의 

    Ext.define('scrapModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'checkdata',      type: 'auto'},
            {name: 'no',            type: 'number'},
            {name: 'partNo',        type: 'auto'},
            {name: 'rev',           type: 'auto'},
            {name: 'newrev',        type: 'auto'},
            {name: 'partName',      type: 'auto'},
            {name: 'unit',          type: 'auto'},
            {name: 'state',          type: 'auto'}
        ]
    });
    
  
  partStore =  Ext.create('Ext.data.Store', {
	   model: 'scrapModel',
	   autoLoad : false,
	   proxy: {
	          type: 'ajax',
	          url: './searchScrapPartA.jsp?partNo=<%=partNumber%>'
	    },
	    listeners: {
            load: function (str, recs, suc) {
	                var selectionModel = listView.getSelectionModel();
	                selectionModel.select(0);
	                
	                //var rec = selectionModel.getSelection()[0];
	                
	                //console.log(rec);
                
                }
            }
  });
  
  var cellEdit = Ext.create('Ext.grid.plugin.CellEditing', {
      clicksToEdit: 1,
      listeners:{              
          
          beforeedit : function(e, editor, options) { 
              
              
          },
          afteredit: function(e, editor){
                              
              
             
          },
          edit: function (e, editor){
             
             //console.log(editor);
                                  
          }
      }
  });
 
  listView = Ext.create('Ext.grid.Panel', {
	  region: 'center',
      collapsible:true,
      title:'부품검색',
      renderTo: Ext.getBody(),
      store: partStore,
      multiSelect: true,
      plugins: [
                cellEdit, 'bufferedrenderer'
            ],
      columns: [
                {
                	
                    text: '<%=messageService.getString("e3ps.message.ket_message", "01802") %><%--선택--%>',
                    dataIndex: 'checkdata',
                    width: 90,
                    stopSelection: false,
                    renderer:function(value, metaData, record){    
                    	return value;
                    },
                    align: 'center'    
                },{
			          text: 'No',
			          width: 80,
			          sortable: true,
			          dataIndex: 'no',
			          align: 'center'            
			    },{
	                  text: '<%=messageService.getString("e3ps.message.ket_message", "01569") %><%--부품번호--%>',
	                  width: 80,
	                  sortable: true,
	                  dataIndex: 'partNo',
	                  align: 'center'            
                }, {
                	  text: 'Rev',
                	  width: 80,
                	  sortable: true,
                	  dataIndex: 'newrev',
                	  align: 'center'            
	            }, {
	            	  text: 'Rev',
	            	  width: 80,
	            	  sortable: true,
	            	  hidden: true,
	            	  dataIndex: 'rev',
	            	  align: 'center'            
	            }, {
	            	  text: '<%=messageService.getString("e3ps.message.ket_message", "01586") %><%--부품명--%>',
	            	  width: 200,
	            	  sortable: true,
	            	  dataIndex: 'partName',
	            	  align: 'left'            
		        }, {
		        	  text: '<%=messageService.getString("e3ps.message.ket_message", "01119") %><%--기본단위--%>',
		        	  width: 80,
		        	  sortable: true,
		        	  dataIndex: 'unit',
		        	  renderer: function(value, metaData, record){    
                         
                           var returnVar = "";
                            if(value != 0 && value != "")
                            {
                                if(unitStore.findRecord("id", value) != null)
                                {
                                    returnVar =  unitStore.findRecord("id", value).get('unit');
                                } else{ 
                                    returnVar =  value;
                                }
                            }
                            else
                            {
                                returnVar = ""; 
                            }
                            
                           
                            return returnVar;
                        },
		        	  align: 'center'            
		        }, {
                    text: '<%=messageService.getString("e3ps.message.ket_message", "01760") %><%--상태--%>',
                    width: 200,
                    sortable: true,
                    dataIndex: 'state',
                    align: 'left'            
              }  
      ],
      listeners: {
    	  select : function(view, record, item, index){
    		         //console.log(record);
    	           }
      }
  });

//Viewport 처리
  Ext.create('Ext.container.Viewport', {
      layout: 'border',
      renderTo: 'list',
      items: [listView]        
  });
  
  partStore.load();
  listView.getHeader().hide(); 
  
  
  
});

var checkedData = function()
{
	
	var listItem = listView.getStore().data.items[selectRow].data;
	
	//var cbox = Ext.get("rowSel"+1);
	console.log(listItem);
	
	var dataArr = new Array();
	dataArr[0] = listItem.partNo;
	dataArr[1] = listItem.partName;
	dataArr[2] = listItem.newrev;
	dataArr[3] = listItem.rev;
	dataArr[4] = listItem.unit;
	dataArr[5] = listItem.state;
	
	return dataArr;
}

var checkValue = function(cnt)
{
	//alert(cnt);
	selectRow = cnt;
}
</script>
</head>
<body>

</body>
</html>