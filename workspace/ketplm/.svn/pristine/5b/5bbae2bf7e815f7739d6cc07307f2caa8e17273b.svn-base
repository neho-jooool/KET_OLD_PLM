<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="wt.part.QuantityUnit"%>
<%@ page import="wt.part.WTPart"%>
<%@ page import="java.util.*"%>
<%@ page import="e3ps.bom.common.clipboard.BOMCodeData"%>
<%@ page import="ext.ket.part.util.*"%>
<%@ page import="ext.ket.bom.util.KETBomPartUtil"%>
<%@ page import="ext.ket.bom.query.*"%>
<%@page import="wt.session.SessionHelper"%>
<%@page import="wt.org.WTUser"%>
<%@page import="e3ps.common.code.*"%>
<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@include file="/jsp/common/InitMsgSvc.jsp"%>
<%
    //String oid = "615154004";
String oid  = (String)request.getParameter("oid");
String mode = "";
String partOid = "";
String partType=""; //P-제품 M-금형 D-Die
String partNumber = "";
String partRev = "";

String curURL = request.getRequestURL().toString();

if(oid==null || oid.equals(""))
{
%>
    <script>alert("부품이 없습니다.");</script>
<%    
    return;
}
    //oid= "wt.part.WTPart:839343035";

KETBomPartUtil pUtil = new KETBomPartUtil();

WTPart part = pUtil.getPart(oid);

partNumber = part.getNumber();
partRev = wt.vc.VersionControlHelper.getVersionIdentifier(part).getValue();
//System.out.println("partNumber==>"+partNumber);
partType = PartSpecGetter.getPartSpec(part, PartSpecEnum.SpPartType );

//System.out.println("partType==>"+partType);
long id = pUtil.getPartLongId(part);

partOid = Long.toString(id);

//System.out.println("partOid==>"+partOid);
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
//unitH = "{"+ unitH.substring(0, unitH.length()-1)+"}";
System.out.println("unit====>"+unit);
System.out.println("unitH====>"+unitH);

KETBOMQueryBean bean = new KETBOMQueryBean();
Hashtable checkData = bean.getEcoBomHeaderChecker(partNumber, partRev);
String ecoNumber = "";
String gubun = "";
String ecoState = "";
Hashtable charger = new Hashtable();
if(checkData!=null && checkData.size()>0)
{
    ecoNumber = (String)checkData.get("ecoNumber");
    gubun = (String)checkData.get("gubun");
    charger = (Hashtable)checkData.get("charger");  
    ecoState = (String)checkData.get("state");
}

System.out.println("ecoNumber====>"+ecoNumber);
System.out.println("gubun====>"+gubun);
System.out.println("ecoState====>"+ecoState);

String isEditable= "N";//수정블가


String disabledValue = "true"; 
String disabledSave = "true"; 



WTUser usr = (WTUser) SessionHelper.manager.getPrincipal();
String loginId = usr.getName();


if(ecoNumber!=null && !ecoNumber.equals("") && ecoState.equals("INWORK"))
{
    //if(charger.containsValue(loginId)||loginId.equals("Administrator"))
	if(charger.containsValue(loginId))
    {
        isEditable= "Y"; //수정가능
    }
}

//if(loginId.equals("Administrator"))
    //isEditable= "Y"; //수정가능

HashMap nCode = new HashMap();
nCode = (HashMap)pUtil.getNumberCode("SPECMATERIALMOLD");
//System.out.println(nCode.values().toString());
 
  
  
Set keys = nCode.keySet();

Iterator<String> mkey = keys.iterator();  

String materialArr = "";  
int ncCnt = 0;
while(mkey.hasNext())
{
    ncCnt++;
    //System.out.println(mkey.next());
    String numCode = (String)mkey.next();
    String numDesc = (String)nCode.get(numCode);
    
    materialArr+="[\""+numCode+"\",\""+numDesc+"\"]";
    if(ncCnt!=keys.size())
    materialArr+=",";   
}

System.out.println(materialArr);

String debug = "true"; //false : debug,  true : not debug (모정보 확인용)
//String debug ="false";

String historyBomUrl = "KETVersionBOMList.jsp?partOid="+partOid+"&partType="+partType;
String viewBomUrl = "KETBOMList.jsp?partOid="+partOid+"&partType="+partType;
String editBomUrl = "KETBOMEditList.jsp?gubun="+gubun+"&ecoNumber="+ecoNumber+"&partNumber="+partNumber+"&partType="+partType;

    
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BOM 편집기</title>
<link rel="stylesheet" type="text/css" href="/plm/extcore/extjs50/resource/ext-theme-classic/ext-theme-classic-all.css"/>
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


<script type="text/javascript" src="/plm/portal/js/common.js"></script>
<script type="text/javascript" src="/plm/extcore/js/part/partUtil.js"></script>
<script type="text/javascript" src="/plm/extcore/extjs50/examples/shared/include-ext.js?theme=classic"></script>
<script type="text/javascript">
var storeLoad; 
var newpartaddFn;
var replaceFn;
var bomaddFn;
var bomaddMultiFn;
var curMode = 'view';
var viewUrl = '<%=viewBomUrl%>';
var editUrl = '<%=editBomUrl%>';

var loginId = '<%=loginId%>';
var partType='<%=partType%>';

var validWin; 
var isValid = false;
var validResult = '';
var ecoState ='<%=ecoState%>';

var mPartOid = new Array();
var mPartNo = new Array();
var mNewWeight = new Array();
var mNewSweight = new Array();
var mNewTweight = new Array();
var mNewMaterial = new Array();
var mOldWeight = new Array();
var mOldSweight = new Array();
var mOldTweight = new Array();
var mOldMaterial = new Array();

var unitInfo = new Array();
<%=unitH%>

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



Ext.define('Override.tree.ViewDropZone', {
    override : 'Ext.tree.ViewDropZone',
        
        targetChildAppend : false,
        
        getPosition: function(e, node) {
        
        var view = this.view,
        record = view.getRecord(node),
        y = e.getY(),
        noAppend = record.isLeaf(),
        noBelow = false,
        region = Ext.fly(node).getRegion(),
        fragment;
                
            
            if (record.isRoot()) {
                return 'append';
            }
                
            if (this.appendOnly) {
                return 'append';
            }
    
            if (!this.allowParentInserts) {
                noBelow = true;
            }
    
            fragment = (region.bottom - region.top) / (noAppend ? 2 : 3);
            
            if (y >= region.top && y < (region.top + fragment)) {
                
            }
            else if (!noBelow && (noAppend || (y >= (region.bottom - fragment) && y <= region.bottom))) {
                
            }
            else {
                return 'append';
            }
            
    }
    
});



Ext.onReady(function() 
{
    Ext.QuickTips.init();
    var w = Ext.getBody().getWidth()
    
    var copydata = null;
    var copyid;
    var prodUploadWin;
    var moldUploadWin;
    var downloadWin;
       
    
    var unitStore = Ext.create('Ext.data.ArrayStore',{
        fields: ['group', 'id', 'unit'],
        data : [<%=unit%>],
        listeners:{
        	
        }
    }); 
    
    
    
    
    var materialStore = Ext.create('Ext.data.ArrayStore',{
        fields: ['id', 'material'],
        data : [<%=materialArr%>]
    }); 
    
    /*    
    var comboBoxRenderer = function(combo) {
          return function(value) {
            var idx = combo.store.find(combo.valueField, value);
            var rec = combo.store.getAt(idx);
            return (rec === null ? '' : rec.get(combo.displayField) );
         };
    }  
    */
   
    var lvl_store = Ext.create('Ext.data.ArrayStore',{
        fields: ['id', 'lvl'],
        data : [['All','All'],['0','0'],['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6'],['7','7'],['8','8'],['9','9']]
    }); 
    
   
    
    var levelCombo = Ext.create('Ext.form.ComboBox',{
        store: lvl_store,
        displayField: 'lvl',
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
    
    //D, I, K, L, M, N, R, T
    var ictStore = Ext.create('Ext.data.ArrayStore',{
        fields: ['id', 'ict'],
        //data : [['',''],['D','D'],['I','I'],['K','K'],['L','L'],['M','M'],['N','N'],['R','R'],['T','T']]
        data : [['',''],['L','L[재고품목]'],['N','N[비재고품목]']]
    }); 
    
    
    //toolbar 생성
    var bomtoolbar = Ext.create('Ext.toolbar.Toolbar',{
        region: 'north',        
    });  
    bomtoolbar.render(Ext.getBody());
    bomtoolbar.suspendLayouts();
      
    bomtoolbar.add( 
            {xtype: 'tbspacer', width:10},
            {xtype: 'button', text:'수정', id:'edit', disabled:<%if(isEditable.equals("Y")){%>false<%}else{%>true<%}%>, handler:onButtonClick},
            {xtype: 'button', text:'이전', id:'view', disabled:true, handler:onButtonClick},
            {xtype: 'button', text:'작업BOM', id:'work', disabled:<% if(ecoState.equals("UNDERREVIEW")){%>false<%}else{%>true<%} %>, handler:onButtonClick},
            {xtype: 'button', text:'이력BOM', id:'history', disabled:false, handler:onButtonClick},
            {xtype: 'button', text:'재질/중량', id:'material', disabled:false, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/BOM_CompareBOM.gif', tooltip:'BOM비교', id:'compare', disabled:false, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_UpwardExplosion.gif', tooltip:'역전개', id:'reverse', disabled:false, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            levelCombo,
            {xtype: 'tbspacer', width:5},
            {cls:'x-btn-icon', icon:'img/BOM_LevelView.gif', tooltip:'레벨확장', id:'levelview', disabled:false, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/BOM_ExcelTempleatDown.gif', tooltip:'Excel Template Down', id:'exceldown', disabled:false, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_GeneralBOM.gif', tooltip:'제품엑셀 업로드', id:'prodexcelup', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_LoadExcelBOM.gif', tooltip:'금형엑셀 업로드', id:'moldexcelup', disabled:true, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/part_create.gif', tooltip:'신규 부품 등록 추가', id:'newpartadd', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_Add.gif', tooltip:'검색 추가', id:'bomadd', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_Remove.gif', tooltip:'제거', id:'remove', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_Replace.gif', tooltip:'교체', id:'replace', disabled:true, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/BOM_Copy.gif', tooltip:'복사', id:'bomcopy', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_CopyChild.gif', tooltip:'자부품 복사', id:'childcopy', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_Cut.gif', tooltip:'잘라내기', id:'cut', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_Paste.gif', tooltip:'붙여넣기', id:'paste', disabled:true, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/BOM_MoveUp.gif', tooltip:'Move Up', id:'moveup', disabled:true, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_MoveDown.gif', tooltip:'Move Down', id:'movedown', disabled:true, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/BOM_BOMValidation.gif', tooltip:'BOM 검증', id:'validation', disabled:true, handler:onButtonClick},
            {xtype: 'tbseparator'},
            {xtype: 'tbspacer', width:10},
            {cls:'x-btn-icon', icon:'img/BOM_Clear.gif', tooltip:'초기화', id:'bominit', disabled:false, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_SaveExcel.gif', tooltip:'Excel 저장', id:'excelsave', disabled:false, handler:onButtonClick},
            {cls:'x-btn-icon', icon:'img/BOM_Save.gif', tooltip:'저장', id:'bomsave', disabled:true, handler:onButtonClick},
            {xtype: 'tbseparator'}
            
            
            );
   
    
   //템플릿 다운로드 폼패널
    var downFileForm =  Ext.create('Ext.form.Panel',{
        layout:'fit',
        items : [
             {
                 xtype: 'radiogroup',
                 columns: 2,
                 anchor: '100%',
                 items:[
                         {
                             boxLabel: '제품',
                             name: 'template',
                             inputValue: 'prod',
                             checked:true
                         },
                         {
                             boxLabel: '금형',
                             name: 'template',
                             inputValue: 'mold'
                         }
                       ]
             }
         ],
         buttons: [{
             text: '다운로드',
             handler: function(){
                 var form = this.up('form').getForm();
                 var formVal = form.getValues().template;
                 
                 if(formVal=='prod')
                 {
                     document.hiddenFrame.location = './ProdBOMTemplate.xls';    
                 }else
                 {
                     document.hiddenFrame.location = './MoldBOMTemplate.xls';    
                 }
                 downloadWin.close();
                 
             }
         },{
             text: '닫기',
             handler: function(){                            
                 downloadWin.close();
             }
         }]
    });
    
    
    //제품첨부파일 폼패널
    var prodFileForm =  Ext.create('Ext.form.Panel',{
        layout:'fit',
        items : [
             {
                 xtype: 'filefield',
                 name: 'document',
                 buttonText : '찾아보기',
                 msgTarget: 'side',
                 allowBlank: false,
                 anchor: '100%'
             }
         ],
         buttons: [{
             text: '초기화',
             handler: function(){                            
                 var form = this.up('form').getForm();
                 form.reset();
             }
         },{
             text: '저장',
             handler: function(){
                 var form = this.up('form').getForm();
                 if (form.isValid()) {
                     
                	 var items = tree.getStore().data.items;  
                	 
                	 var econoAssy = items[0].data.econo;
                	 var checkoutAssy = items[0].data.checkout;
                	 var checkoutIdAssy = items[0].data.checkoutId;
                	 var stateAssy = items[0].data.state;
                	
                	
                	 form.submit({
                         url: './KETExcelUpload.jsp?gubun=P&partNumber=<%=partNumber%>&partRev=<%=partRev%>&econo='+econoAssy+'&checkout='+checkoutAssy+'&checkoutId='+checkoutIdAssy+'&state='+stateAssy,
                         waitMsg: 'Uploading...',
                         success: function(fp, o) {
                             
                        	//var rootNd = tree.getRootNode();
                             //var childNd = rootNd.childNodes[0];
                             var childNd = tree.getSelectionModel().getSelection()[0];
                             childNd.data.children = new Array();
                             
                             var childData = childNd.data;
                             //var textData = Ext.decode(response.responseText);
                             var errMsg = o.result.result.errlog;
                             
                             //console.log(childNd);
                             //console.log(o.result.result.data);
                             if(errMsg=='')
                             {
                                 //childNd.remove();   
                                 //rootNd.appendChild(childData);
                                 
                                 //var newchildNd = rootNd.childNodes[0];
                                 for(i=0; i< o.result.result.data.length;i++)
                                 {
                                     //console.log(o.result.result.data[i]);  
                                     appendJsonData(o.result.result.data[i], childNd);
                                     //childNd.appendChild(o.result.result.data[i]);
                                 }
                                 tree.getRootNode().expand(true);
                                 tree.getHeader().hide();
                                 moldUploadWin.close();
                             }else
                             {
                                //errMsg = 
                                alert(o.result.result.errlog);
                             }
                             
                             
                         },
                         failure: function(fp, o) {
                             //Ext.Msg.alert('Fail', o.result.result.data);
                         }
                     });
                 }
             }
         }]
    });
  
    //금형첨부파일 폼패널
    var moldFileForm =  Ext.create('Ext.form.Panel',{
        layout:'fit',
        items : [
             {
                 xtype: 'filefield',
                 name: 'document',
                 buttonText : '찾아보기',
                 msgTarget: 'side',
                 allowBlank: false,
                 anchor: '100%'
             }
         ],
         buttons: [{
             text: '초기화',
             handler: function(){                            
                 var form = this.up('form').getForm();
                 form.reset();
             }
         },{
             text: '저장',
             handler: function(){
                 var form = this.up('form').getForm();
                 if (form.isValid()) {
                	 
                	 var items = tree.getStore().data.items;  
                     
                     var econoAssy = items[0].data.econo;
                     var checkoutAssy = items[0].data.checkout;
                     var checkoutIdAssy = items[0].data.checkoutId;
                     var stateAssy = items[0].data.state;
                	 
                     form.submit({
                    	 url: './KETExcelUpload.jsp?gubun=D&partNumber=<%=partNumber%>&partRev=<%=partRev%>&econo='+econoAssy+'&checkout='+checkoutAssy+'&checkoutId='+checkoutIdAssy+'&state='+stateAssy,
                         waitMsg: 'Uploading...',
                         success: function(response, o) {//fp
                            
                             //var rootNd = tree.getRootNode();
                             //var childNd = rootNd.childNodes[0];
                             var childNd = tree.getSelectionModel().getSelection()[0];
                             childNd.data.children = new Array();
                             
                             var childData = childNd.data;
                             //var textData = Ext.decode(response.responseText);
                             var errMsg = o.result.result.errlog;
                             
                             //console.log(childNd);
                             //console.log(o.result.result.data);
                             if(errMsg=='')
                             {
                                 //childNd.remove();   
                                 //rootNd.appendChild(childData);
                                 
                                 //var newchildNd = rootNd.childNodes[0];
                                 for(i=0; i< o.result.result.data.length;i++)
                                 {
                                	 //console.log(o.result.result.data[i]);  
                                	 appendJsonData(o.result.result.data[i], childNd);
                                	 //childNd.appendChild(o.result.result.data[i]);
                                 }
                                 tree.getRootNode().expand(true);
                                 tree.getHeader().hide();
                                 moldUploadWin.close();
                             }else
                             {
                                //errMsg = 
                                alert(o.result.result.errlog);
                             }
                             
                         },
                         failure: function(fp, o) {
                             //Ext.Msg.alert('Fail', o.result.result.data);
                         }
                     });
                 }
             }
         }]
    });

   
    //Event
    function onButtonClick(btn){
        
        if(btn.id=='edit')
        {
            curMode = 'edit';
            bomtoolbar.items.get('edit').disable();
            bomtoolbar.items.get('view').enable();
            bomtoolbar.items.get('history').disable();
            
            var gubun = '<%=gubun%>';
            if(gubun!='ECOBOM')
            {
                if(partType=='D'||partType=='M')
                {
                    bomtoolbar.items.get('moldexcelup').enable();
                }else
                {
                    bomtoolbar.items.get('prodexcelup').enable();
                }
            }
            
            bomtoolbar.items.get('material').enable();
            bomtoolbar.items.get('validation').enable();
            bomtoolbar.items.get('newpartadd').enable();
            bomtoolbar.items.get('bomadd').enable();
            bomtoolbar.items.get('replace').enable();
            bomtoolbar.items.get('bomcopy').enable();
            bomtoolbar.items.get('childcopy').enable();
            bomtoolbar.items.get('paste').enable();
            bomtoolbar.items.get('bomsave').enable();
            if(!(partType=='D' || partType=='M'))
                bomtoolbar.items.get('material').enable();
            else
                bomtoolbar.items.get('material').disable();
            bomtoolbar.items.get('moveup').enable();
            bomtoolbar.items.get('movedown').enable();
            bomtoolbar.items.get('cut').enable();
            
            add.enable();
            addSearch.enable();
            del.enable();
            change.enable();
            bomCopy.enable();
            copyChild.enable();
            cut.enable();
            paste.enable();
            moveup.enable();
            movedown.enable();
            
            
            tree.plugins.push(cellEdit);
            tree.getView().refresh();
            
            store.getProxy().url = editUrl;
            store.load();
            
            
            
            //tree.getView().refresh();
            //cellEdit.init(tree);
            //tree.plugins.push(cellEdit);
            //console.log(tree.getView());
            //tree.plugins.cellEdit.enable();
        }
        
        if(btn.id=='view')
        {
        	var msg;
        	if(ecoState=='UNDERREVIEW'){
        		  msg = '이전 화면으로 돌아가시겠습니까?';
        	}else{
	        	if(curMode!='historyBom')
	        		  msg = '작업중인 데이터가 사라집니다.진행하시겠습니까?';
	        	else
	        		  msg = '이전 화면으로 돌아가시겠습니까?';
        	}
        	
            Ext.Msg.confirm('경고', msg, function (id, value) {
                if (id === 'yes') {
                    curMode = 'view';
                    
                    bomtoolbar.items.get('view').disable();
                    
                    if(ecoState=='UNDERREVIEW'){
                        bomtoolbar.items.get('work').enable();
                        bomtoolbar.items.get('edit').disable();
                        bomtoolbar.items.get('history').enable();
                    }else{
                    	
                        bomtoolbar.items.get('work').disable();
                        if(ecoState=='INWORK')
                            bomtoolbar.items.get('edit').enable();
                        else
                        	bomtoolbar.items.get('edit').disable(); 	
                        bomtoolbar.items.get('history').enable();
                    }
                    
                    bomtoolbar.items.get('prodexcelup').disable();
                    bomtoolbar.items.get('moldexcelup').disable();
                    bomtoolbar.items.get('newpartadd').disable();
                    bomtoolbar.items.get('bomadd').disable();
                    bomtoolbar.items.get('replace').disable();
                    bomtoolbar.items.get('bomcopy').disable();
                    bomtoolbar.items.get('childcopy').disable();
                    bomtoolbar.items.get('paste').disable();
                    bomtoolbar.items.get('bomsave').disable();
                    bomtoolbar.items.get('material').disable();
                    bomtoolbar.items.get('moveup').disable();
                    bomtoolbar.items.get('movedown').disable();
                    bomtoolbar.items.get('validation').disable(); //material
                    bomtoolbar.items.get('material').enable();
                    
                    add.disable();
                    addSearch.disable();
                    del.disable();
                    change.disable();
                    bomCopy.disable();
                    copyChild.disable();
                    cut.disable();
                    paste.disable();
                    moveup.disable();
                    movedown.disable();
                    
                    //cellEdit.disable();
                    store.getProxy().url = viewUrl;
                    store.load();
                }
            }, this);
            
        }
        
        
        if(btn.id=='work')
        {
            
                    curMode = 'work';
                    bomtoolbar.items.get('edit').disable();
                    bomtoolbar.items.get('view').enable();
                    bomtoolbar.items.get('work').disable();
                    bomtoolbar.items.get('history').disable();
                    
                    bomtoolbar.items.get('prodexcelup').disable();
                    bomtoolbar.items.get('moldexcelup').disable();
                    bomtoolbar.items.get('newpartadd').disable();
                    bomtoolbar.items.get('bomadd').disable();
                    bomtoolbar.items.get('replace').disable();
                    bomtoolbar.items.get('bomcopy').disable();
                    bomtoolbar.items.get('childcopy').disable();
                    bomtoolbar.items.get('paste').disable();
                    bomtoolbar.items.get('bomsave').disable();
                    bomtoolbar.items.get('material').disable();
                    bomtoolbar.items.get('moveup').disable();
                    bomtoolbar.items.get('movedown').disable();
                    bomtoolbar.items.get('validation').disable();
                    
                    bomtoolbar.items.get('material').disable();
                    
                    
                    add.disable();
                    addSearch.disable();
                    del.disable();
                    change.disable();
                    bomCopy.disable();
                    copyChild.disable();
                    cut.disable();
                    paste.disable();
                    moveup.disable();
                    movedown.disable();
                    
                    //cellEdit.disable();
                    store.getProxy().url = editUrl;
                    store.load();
            
        }
        
        //history
        if(btn.id=='history')
        {
        	curMode = 'historyBom';
        	bomtoolbar.items.get('view').enable();
        	bomtoolbar.items.get('history').disable();
        	bomtoolbar.items.get('material').disable();
        	
        	store.getProxy().url = '<%=historyBomUrl%>';
            store.load();
        }
        
        
        //bom compare
        if(btn.id=='compare')
        {
            winOpenFn('./KETBomCompareMain.jsp','BOM Compare','scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=1100,height=800');
        }
        
        //material
        if(btn.id=='material')
        {
            materialUpdate();
        }
        
        //bom reverse
        if(btn.id=='reverse')
        {
            reverseFn();          
        }
                
        //levelview
        if(btn.id=='levelview')
        {
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
        }
        
        //Excel Template Down
        if(btn.id=='exceldown')
        {
                 
            if(downloadWin) {
                downloadWin.show();
            } else {
                downloadWin = Ext.create('widget.window', {
                    autoShow: true,
                    layout: 'fit',
                    bodyPadding: 10,
                    title: '엑셀 다운로드',
                    closeAction: 'hide',
                    width:200,
                    height:105,
                    border: false,
                    x: 40,
                    y: 60,
                    items: [downFileForm]
                });
                
            }
        }
        
        //제품엑셀 업로드        
        if(btn.id=='prodexcelup')
        {
            
            
            if(prodUploadWin) {
                prodUploadWin.show();
            } else {
                prodUploadWin = Ext.create('widget.window', {
                    autoShow: true,
                    layout: 'fit',
                    bodyPadding: 10,
                    title: '제품엑셀 업로드',
                    closeAction: 'hide',
                    width:450,
                    height:105,
                    border: false,
                    x: 40,
                    y: 60,
                    items: [prodFileForm]
                });
                
            }
        }
      
        //금형엑셀 업로드
        if(btn.id=='moldexcelup')
        {
            
            
            if(moldUploadWin) {
                moldUploadWin.show();
            } else {
                moldUploadWin = Ext.create('widget.window', {
                    autoShow: true,
                    layout: 'fit',
                    bodyPadding: 10,
                    title: '금형엑셀 업로드',
                    closeAction: 'hide',
                    width:450,
                    height:105,
                    border: false,
                    x: 100,
                    y: 60,                    
                    items:  [moldFileForm]
                });
            } 
        }
        
        
        
        //newpartadd
        if(btn.id=='newpartadd')
        {
            //newpartaddFn();
            createPart('newpartaddFn','pType=');
        }
        
        //bomadd
        if(btn.id=='bomadd')
        {
            //bomaddFn();
            searchPartBOMMulti('bomaddMultiFn','pType=');
        }
      
        //remove
        if(btn.id=='remove')
        {
            removeFn();
        }
        
        //replace
        if(btn.id=='replace')
        {
            searchPartBOM('replaceFn','pType=');
            //replaceFn(); 
        }
        
        //cut
        if(btn.id=='cut')
        {
            cutFn();
        }
        
        //copy
        if(btn.id=='bomcopy')
        {
            bomcopyFn();
        }
        
        //childcopy
        if(btn.id=='childcopy')
        {           
            childcopyFn();
        }
        
        //paste
        if(btn.id=='paste')
        {
            pasteFn();
        }
        
        //moveup
        if(btn.id=='moveup')
        {
            moveupFn();
        }
       
        //movedown
        if(btn.id=='movedown')
        {
           movedownFn();
        }
      
        //BOM 검증
        if(btn.id=='validation')
        {
            //validResult = '';
            validWin =  winOpenFn('KETBomValidationResult.jsp','BOM Validation','scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=740,height=350');  
            
        }
        
        //초기화
        if(btn.id=='bominit')
        {
            bominitFn();
        }
        
        //Excel 저장
        if(btn.id=='excelsave')
        {
            var items = tree.getStore().data.items;  
            //console.log(items);
            var partNoArr = new Array();
            var noArr = new Array();
            var lvlArr = new Array();
            
            var ictArr = new Array();
            var seqArr = new Array();
            var partNameArr = new Array();
            var qtyArr = new Array();
            var unitArr = new Array();
            var revArr = new Array();
            var stateArr = new Array();
            var econoArr = new Array();
            var checkoutArr = new Array();
            var reftopArr = new Array();
            var refbtmArr = new Array();
            
            var materialArr = new Array();
            var hardnessFromArr = new Array();
            var hardnessToArr = new Array();
            var designDateArr = new Array();
            
            var parentNoArr = new Array();
            var pverArr = new Array();
            
            
            
            for(i=0;i<items.length;i++)
            {
                partNoArr[i]       = items[i].data.partNo;
                noArr[i]           = i;
                lvlArr[i]          = items[i].data.lvl;
                ictArr[i]          = items[i].data.ict;
                seqArr[i]          = items[i].data.seq;
                
                partNameArr[i]     = items[i].data.partName;
                qtyArr[i]          = items[i].data.qty;
                unitArr[i]         = items[i].data.unit;
                revArr[i]          = items[i].data.rev;
                stateArr[i]        = items[i].data.state;
                econoArr[i]        = items[i].data.econo;
                checkoutArr[i]     = items[i].data.checkout;
                reftopArr[i]       = items[i].data.reftop;
                refbtmArr[i]       = items[i].data.refbtm;
                materialArr[i]     = items[i].data.material;
                hardnessFromArr[i] = items[i].data.hardnessFrom;
                hardnessToArr[i]   = items[i].data.hardnessTo;
                designDateArr[i]   = items[i].data.designDate;
                
                parentNoArr[i]     = items[i].data.parentNo;
                pverArr[i]         = items[i].data.pver;
                
                //console.log(items[i].data);     
            }
           
            Ext.Ajax.request({
                url: './KETBomListExcel.jsp?partType='+partType,
                method: 'POST',
                async : false,
                params: {
                               "partNo" : partNoArr, 
                               "index": noArr, 
                               "lvl": lvlArr, 
                               "ict": ictArr, 
                               "seq": seqArr, 
                               "partName": partNameArr, 
                               "qty": qtyArr, 
                               "unit": unitArr, 
                               "rev": revArr, 
                               "state": stateArr, 
                               "econo": econoArr, 
                               "checkout": checkoutArr, 
                               "reftop": reftopArr, 
                               "refbtm": refbtmArr, 
                               "material": materialArr, 
                               "hardnessFrom": hardnessFromArr, 
                               "hardnessTo": hardnessToArr, 
                               "designDate": designDateArr, 
                               "parentNo": parentNoArr, 
                               "pver": pverArr                               
                         },
                success: function(response, opts){
                    //var objhtml = response.responseText; //content returned from server side
                    //console.log(response.responseText);
                    document.hiddenFrame.location.href=response.responseText;
                    
                  }
            });           
            
        }
        
        //bom save
        if(btn.id=='bomsave')
        {
            //var isValid = false;
            //var validResult = '';
            
            if(isValid && validResult!='')
            {
            	var msg = 'BOM이 올바르게 작성되지 않았습니다. 그래도 진행하시겠습니까?';
                /*
            	Ext.Msg.confirm('경고', msg, function (id, value) {
                	if(id=='yes')
                	{
                		validResult = '';
                		
                	}else
                	{
                		return;
                	}
                },this);
                */
                if(confirm(msg))
                {
                	validResult = '';
                }else
                {
                	return;
                }
            }
            
            if(isValid && validResult=='')
            {
                var items = tree.getStore().data.items;  
                //console.log(items);
                var partNoArr = new Array();
                var noArr = new Array();
                var lvlArr = new Array();
                
                var ictArr = new Array();
                var seqArr = new Array();
                var partNameArr = new Array();
                var qtyArr = new Array();
                var unitArr = new Array();
                var revArr = new Array();
                var stateArr = new Array();
                var econoArr = new Array();
                var checkoutArr = new Array();
                var reftopArr = new Array();
                var refbtmArr = new Array();
                
                var materialArr = new Array();
                var hardnessFromArr = new Array();
                var hardnessToArr = new Array();
                var designDateArr = new Array();
                
                var parentNoArr = new Array();
                var pverArr = new Array();
                
                
                
                for(i=0;i<items.length;i++)
                {
                    partNoArr[i]       = items[i].data.partNo;
                    noArr[i]           = i;
                    lvlArr[i]          = items[i].data.lvl;
                    ictArr[i]          = items[i].data.ict;
                    seqArr[i]          = items[i].data.seq;
                    
                    partNameArr[i]     = items[i].data.partName;
                    qtyArr[i]          = items[i].data.qty;
                    //alert(items[i].data.qty);
                    unitArr[i]         = items[i].data.unit;
                    
                    revArr[i]          = items[i].data.rev;
                    stateArr[i]        = items[i].data.state;
                    econoArr[i]        = items[i].data.econo;
                    checkoutArr[i]     = items[i].data.checkout;
                    reftopArr[i]       = items[i].data.reftop;
                    refbtmArr[i]       = items[i].data.refbtm;
                    materialArr[i]     = items[i].data.material;
                    hardnessFromArr[i] = items[i].data.hardnessFrom;
                    hardnessToArr[i]   = items[i].data.hardnessTo;
                    designDateArr[i]   = items[i].data.designDate;
                    
                    parentNoArr[i]     = items[i].data.parentNo;
                    pverArr[i]         = items[i].data.pver;
                    
                    if(unitArr[i]=='')
                    {
                    	alert("UNIT 정보가 누락되었습니다.("+partNoArr[i] +")");
                        return;
                    }
                    
                    if(i>0)
                    {
	                    if(partType=='D'||partType=='M')
	                    {
	                    	if(materialArr[i]=='')
	                        {
	                    		alert("재질 정보가 누락되었습니다.("+partNoArr[i] +")");
	                            return;
	                        }
	                    	
	                    	if(hardnessFromArr[i]=='')
	                        {
	                    		alert("HardnessFrom 정보가 누락되었습니다.("+partNoArr[i] +")");
	                            return;
	                        }
	                    	
	                    	if(hardnessToArr[i]=='')
	                        {
	                    		alert("HardnessTo 정보가 누락되었습니다.("+partNoArr[i] +")");
	                            return;
	                        }
	                    	
	                    	if(designDateArr[i]=='')
	                        {
	                    		alert("설계일자 정보가 누락되었습니다.("+partNoArr[i] +")");
	                            return;
	                        }
	                    }else
	                    {
	                    	if(ictArr[i]=='')
	                        {
	                            alert("ICT 정보가 누락되었습니다.("+partNoArr[i] +")");
	                            return;
	                        }
	                    }
                    }
                }
           
                Ext.Ajax.request({
                    url: './KETBomListSave.jsp?partType='+partType+'&ecoNumber=<%=ecoNumber%>&gubun=<%=gubun%>',
                    method: 'POST',
                    async : false,
                    params: {
                                   "partNo" : partNoArr, 
                                   "index": noArr, 
                                   "lvl": lvlArr, 
                                   "seq": seqArr, 
                                   "ict": ictArr, 
                                   "partName": partNameArr, 
                                   "qty": qtyArr, 
                                   "unit": unitArr, 
                                   "rev": revArr, 
                                   "state": stateArr, 
                                   "econo": econoArr, 
                                   "checkout": checkoutArr, 
                                   "reftop": reftopArr, 
                                   "refbtm": refbtmArr, 
                                   "material": materialArr, 
                                   "hardnessFrom": hardnessFromArr, 
                                   "hardnessTo": hardnessToArr, 
                                   "designDate": designDateArr, 
                                   "parentNo": parentNoArr, 
                                   "pver": pverArr                               
                             },
                    success: function(response, opts){
                        //var objhtml = response.responseText; //content returned from server side
                        //console.log(response.responseText);
                        alert(response.responseText);
                        validResult = "";
                        isValid = false;
                        store.load();
                      }
                });  
            }else
            {
                if(!isValid)
                     alert("BOM 검증이 되지 않았습니다.");
                if(validResult!='')
                    alert("BOM이 올바르게 작성되지 않았습니다. 다시 확인하시기 바랍니다.");
            }
        }
    }
    
    
    bomtoolbar.resumeLayouts(true);
    
    
    
    //data model 정의 
    Ext.define('BomModel', {
        extend: 'Ext.data.TreeModel',
        fields: [
            {name: 'partNo',        type: 'auto'},
            {name: 'rev',           type: 'auto'},
            {name: 'newrev',        type: 'auto'},
            {name: 'partName',      type: 'auto'},
            {name: 'lvl',           type: 'number'},
            {name: 'seq',           type: 'number'},
            {name: 'qty',           type: 'float'},
            {name: 'unit',          type: 'auto'},
            {name: 'econo',         type: 'auto'},
            {name: 'checkout',      type: 'auto'},
            {name: 'parentNo',      type: 'auto'},
            {name: 'pver',          type: 'auto'},
            {name: 'ict',           type: 'auto'},            
            {name: 'reftop',        type: 'auto'},
            {name: 'refbtm',        type: 'auto'},
            {name: 'material',      type: 'auto'},
            {name: 'hardnessFrom',  type: 'number'},
            {name: 'hardnessTo',    type: 'number'},
            {name: 'designDate',    type: 'date', dateFormat:'Y-m-d'},
            {name: 'state',         type: 'auto'},
            {name: 'checkoutId',      type: 'auto'}
        ]
    });
    
    //data 
    var store = Ext.create('Ext.data.TreeStore', {
        model: 'BomModel',
        autoload: false,
        proxy: {
            type: 'ajax',
            url: viewUrl,
           
        },
        <%if(PartUtil.isProductType(partType)){%>       
        sorters: [{property:'partNo', direction: 'ASC'}],
        <%}else{%>
        <%}%>
        listeners: {
            load: function (str, recs, suc) {
                tree.expandAll();
                var selectionModel = tree.getSelectionModel();
                selectionModel.select(0);
                }
            }
    });
    
   
    
    //cellEdit Plugin
    var beforeUnitInfo;
    var beforeUnit="";
    var cellEdit = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1,
            listeners:{              
            	
            	beforeedit : function(e, editor, options) { 
                    //console.log(e);
                    //console.log(options);
                    if(curMode=='view')
                    {
                        return false;
                    }else
                    {
                    
                   	
                    	beforeUnitInfo = unitInfo[editor.record.data.unit];
                    	beforeUnit =editor.record.data.unit; 
                    	
                    	var gubun = '<%=gubun%>';
                        if(gubun=='ECOBOM')
                        {
	                    	if(editor.record.data.lvl=='0')
	                        {
	                            return true;
	                        }else
	                        {
	                            var pnode = editor.record.parentNode;
	                            
	                            if(pnode.data.checkoutId!=loginId)
	                            {
	                                  return false; 
	                            }else
	                            {
	                                return true;
	                            }
	                        }
                        }else
                        {
                        	return true;
                        }
                        
                        
                    }
                },
                afteredit: function(e, editor){
                                  	
                    
                	if(e.context.originalValue!=e.context.value)
                    {
                        <%if(PartUtil.isProductType(partType)){%>
                        store.sort('seq', 'ASC');
                        <%}else{%>
                        store.sort('partNo', 'ASC');
                        <%}%>
                    }
                },
                edit: function (e, editor){
                   
                	var afterUnitInfo =  unitInfo[editor.record.data.unit];
                    
                    if(beforeUnitInfo!=afterUnitInfo)
                    {
                            if(!confirm("동일계열 단위가 아닙니다. 그래도 진행하시겠습니까?"))
                            {
                            	editor.record.data.unit = beforeUnit;
                            }
                            
                    }
                    
                                        
                }
            }
        });
    
   
    /*
    function returnAnswer(callback) {
        Ext.Msg.confirm('경고', '동일계열 단위가 아닙니다. 그래도 진행하시겠습니까?', 
        function(btn) {
           callback.call(this, btn === 'yes');
        });
    }
    */
    
    //BOM Tree
    var tree = Ext.create('Ext.tree.Panel', {
        title: 'BOM 편집기',
        region: 'center',
        collapsible: true,
        useArrows: true,
        rootVisible: false,
        store: store,
        multiSelect: false,
        rowLines:true,
     
        plugins: [
            cellEdit, 'bufferedrenderer'
        ],
        columns: [{
            xtype: 'treecolumn',
            text: '부품번호',
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
                 xtype:'combo',
                 store: ictStore,
                 displayField: 'ict',                    
                 valueField: 'id',
                 queryMode: 'local',
                 editable: false,
                 forceSelection: true
                 }, 
                 renderer: function(value){
                                 if(value != 0 && value != "")
                                 {
                                     if(ictStore.findRecord("id", value) != null)
                                         return ictStore.findRecord("id", value).get('ict');
                                     else 
                                         return value;
                                 }
                                 else
                                     return ""; 
                             },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: '부품명',
            dataIndex: 'partName',
            width: 200,
            align: 'left' 
        }, {
            header: '레벨',
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
            header: '수량',
            dataIndex: 'qty',
            width: 70,
            editor: {
                allowBlank: false,
                editable: true
            },
            renderer: Ext.util.Format.numberRenderer('0.000'),
            align: 'right' 
        }, {
            header: '기본단위',
            dataIndex: 'unit',
            width: 80,
            editor :
            	new Ext.form.field.ComboBox({
                    store: unitStore,
                    displayField: 'unit',
                    valueField: 'id',
                    queryMode: 'local',
                    editable: false,
                    forceSelection: true, 
                    listeners: {
                        beforequery: function(queryEvent){
                        	//console.log(queryEvent);
                        	
                            //queryEvent.combo.store.filter('group',queryEvent.lastValue);
                        }    
                    }
                })
                        //xtype:'combo',
                        //store: unitStore,
                        //displayField: 'unit',                    
                        //valueField: 'id',
                        //queryMode: 'local',
                        //editable: false,
                        //forceSelection: true
                     , 
            renderer: function(value, metaData, record){
                           
                           //console.log() 
            	           //alert(unitStore.findRecord("id", value).get('group'));//group
                           //unitStore.filter('group', 'NA');
                           //alert(unitInfo[value]);
                           //unitStore.filter('group', unitInfo[value]);
                           //console.log("renderer==>"+record.data.unit);
                           //console.log("beforeUnit==>"+beforeUnit);
                           //console.log(metaData);
                           //unitStore.filter('group', unitInfo[value]);
                           var returnVar = "";
                                                                             
                            
                            if(value != 0 && value != "")
                            {
                                if(unitStore.findRecord("id", value) != null)
                               	{
                                	//alert(unitStore.findRecord("id", value).get('group'));//group
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
            header: '재질',
            dataIndex: 'material',
            width: 70,
            /*
            editor :{
                xtype:'combo',
                store: materialStore,
                displayField: 'material',                    
                valueField: 'id',
                queryMode: 'local',
                editable: false,
                forceSelection: true
                }, 
                */
    renderer: function(value){
                    if(value != 0 && value != "")
                    {
                        if(materialStore.findRecord("id", value) != null)
                            return materialStore.findRecord("id", value).get('material');
                        else 
                            return value;
                    }
                    else
                        return ""; 
                },   
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,            
            align: 'center' 
        }, {
            header: '경도(F)',
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
            header: '경도(T)',
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
            header: '설계일자',
            dataIndex: 'designDate',
            width: 80,
            xtype: 'datecolumn',
            format: 'Y-m-d',
            editor: {
                xtype : 'datefield',
                format: 'Y-m-d',
                allowBlank: false
            },
            hidden: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,        
            align: 'center' 
        }, {
            header: '결재상태',
            dataIndex: 'state',
            width: 80,
            align: 'center' 
        }, {
            header: 'EcoNo',
            dataIndex: 'econo',
            width: 80,
            align: 'center' 
        }, {
            header: '담당자',
            dataIndex: 'checkout',
            width: 80,
            align: 'center' 
        }, {
            header: 'Ref No(TOP)',
            dataIndex: 'reftop',
            width: 100,
            editor: {
                allowBlank: false,
                editable: true
            },
            hidden: <%=(PartUtil.isProductType(partType))? "false" : "true"%>,
            hideable: <%=(PartUtil.isProductType(partType))? "true" : "false"%>,        
            align: 'center' 
        }, {
            header: 'Ref No(BOTTOM)',
            dataIndex: 'refbtm',
            width: 100,
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
        }, {
            header: '담당자ID',
            dataIndex: 'checkoutId',
            width: 80,
            hidden: <%=debug%>,
            hideable: false,
            align: 'center' 
        }],
        listeners: {
        	cellclick : function(iView, td, cellIndex, record, tr, rowIndex, e, eOpts ){  
        		
        		//console.log(record.data.unit);
        		
        		if(cellIndex==7)
        		{
        			 var unitData = record.data.unit;
        			 unitStore.filter('group', unitInfo[unitData]);
        		}
        		
        	},
        	afterrender : function(){
        		
            },
            select : function(view, record, item, index){
                
                var pnode = record.parentNode;
                
               
                
                if(curMode!='view')
                {
                	var gubun = '<%=gubun%>';
                    if(gubun=='ECOBOM')
                    {
	                	if(record.data.lvl=='0')
	                    {
	                        bomtoolbar.items.get('newpartadd').enable();
	                        bomtoolbar.items.get('bomadd').enable();
	                        bomtoolbar.items.get('replace').disable();
	                        bomtoolbar.items.get('bomcopy').enable();
	                        bomtoolbar.items.get('childcopy').enable();
	                        bomtoolbar.items.get('paste').enable();
	                        bomtoolbar.items.get('moveup').disable();
	                        bomtoolbar.items.get('movedown').disable();
	                        bomtoolbar.items.get('remove').disable();
	                        bomtoolbar.items.get('cut').disable();
	                        
	                        add.enable();
	                        addSearch.enable();
	                        del.disable();
	                        change.disable();
	                        bomCopy.enable();
	                        copyChild.enable();
	                        cut.disable();
	                        paste.enable();
	                        moveup.disable();
	                        movedown.disable();
	                        
	                    }else
	                    {
	                        if(record.data.checkoutId!=loginId && pnode.data.checkoutId!=loginId) 
	                        {
	                            bomtoolbar.items.get('newpartadd').disable();
	                            bomtoolbar.items.get('bomadd').disable();
	                            bomtoolbar.items.get('replace').disable();
	                            bomtoolbar.items.get('bomcopy').enable();
	                            bomtoolbar.items.get('childcopy').enable();
	                            bomtoolbar.items.get('paste').disable();
	                            bomtoolbar.items.get('moveup').disable();
	                            bomtoolbar.items.get('movedown').disable();
	                            bomtoolbar.items.get('remove').disable();
	                            bomtoolbar.items.get('cut').disable();
	                            
	                            add.disable();
	                            addSearch.disable();
	                            del.disable();
	                            change.disable();
	                            bomCopy.enable();
	                            copyChild.enable();
	                            cut.disable();
	                            paste.disable();
	                            moveup.disable();
	                            movedown.disable();
	                        }else if(record.data.checkoutId!=loginId && pnode.data.checkoutId==loginId)
	                        {
	                            bomtoolbar.items.get('newpartadd').disable();
	                            bomtoolbar.items.get('bomadd').disable();
	                            bomtoolbar.items.get('replace').enable();
	                            bomtoolbar.items.get('bomcopy').enable();
	                            bomtoolbar.items.get('childcopy').enable();
	                            bomtoolbar.items.get('paste').disable();
	                            bomtoolbar.items.get('moveup').enable();
	                            bomtoolbar.items.get('movedown').enable();
	                            bomtoolbar.items.get('remove').enable();
	                            bomtoolbar.items.get('cut').enable();
	                            
	                            add.disable();
	                            addSearch.disable();
	                            del.enable();
	                            change.enable();
	                            bomCopy.enable();
	                            copyChild.enable();
	                            cut.enable();
	                            paste.disable();
	                            moveup.enable();
	                            movedown.enable();
	                        }else if(record.data.checkoutId==loginId && pnode.data.checkoutId!=loginId)
	                        {
	                            bomtoolbar.items.get('newpartadd').enable();
	                            bomtoolbar.items.get('bomadd').enable();
	                            bomtoolbar.items.get('replace').disable();
	                            bomtoolbar.items.get('bomcopy').enable();
	                            bomtoolbar.items.get('childcopy').enable();
	                            bomtoolbar.items.get('paste').enable();
	                            bomtoolbar.items.get('moveup').disable();
	                            bomtoolbar.items.get('movedown').disable();
	                            bomtoolbar.items.get('remove').disable();
	                            bomtoolbar.items.get('cut').disable();
	                            
	                            add.enable();
	                            addSearch.enable();
	                            del.disable();
	                            change.disable();
	                            bomCopy.enable();
	                            copyChild.enable();
	                            cut.disable();
	                            paste.enable();
	                            moveup.disable();
	                            movedown.disable();
	                        }else if(record.data.checkoutId==loginId && pnode.data.checkoutId==loginId)
	                        {
	                            bomtoolbar.items.get('newpartadd').enable();
	                            bomtoolbar.items.get('bomadd').enable();
	                            bomtoolbar.items.get('replace').enable();
	                            bomtoolbar.items.get('bomcopy').enable();
	                            bomtoolbar.items.get('childcopy').enable();
	                            bomtoolbar.items.get('paste').enable();
	                            bomtoolbar.items.get('moveup').enable();
	                            bomtoolbar.items.get('movedown').enable();
	                            bomtoolbar.items.get('remove').enable();
	                            bomtoolbar.items.get('cut').enable();
	                            
	                            add.enable();
	                            addSearch.enable();
	                            del.enable();
	                            change.enable();
	                            bomCopy.enable();
	                            copyChild.enable();
	                            cut.enable();
	                            paste.enable();
	                            moveup.enable();
	                            movedown.enable();
	                        }
	                             
	                    }
                   	}else
                   	{
                   		if(record.data.lvl=='0')
                        {                   		
                   			bomtoolbar.items.get('newpartadd').enable();
                            bomtoolbar.items.get('bomadd').enable();
                            bomtoolbar.items.get('replace').disable();
                            bomtoolbar.items.get('bomcopy').enable();
                            bomtoolbar.items.get('childcopy').enable();
                            bomtoolbar.items.get('paste').enable();
                            bomtoolbar.items.get('moveup').disable();
                            bomtoolbar.items.get('movedown').disable();
                            bomtoolbar.items.get('remove').disable();
                            bomtoolbar.items.get('cut').disable();
                            
                            add.enable();
                            addSearch.enable();
                            del.disable();
                            change.disable();
                            bomCopy.enable();
                            copyChild.enable();
                            cut.disable();
                            paste.enable();
                            moveup.disable();
                            movedown.disable();
                        }else
                        {
                        	
                        	bomtoolbar.items.get('newpartadd').enable();
                            bomtoolbar.items.get('bomadd').enable();
                            bomtoolbar.items.get('replace').enable();
                            bomtoolbar.items.get('bomcopy').enable();
                            bomtoolbar.items.get('childcopy').enable();
                            bomtoolbar.items.get('paste').enable();
                            bomtoolbar.items.get('moveup').enable();
                            bomtoolbar.items.get('movedown').enable();
                            bomtoolbar.items.get('remove').enable();
                            bomtoolbar.items.get('cut').enable();
                            
                            add.enable();
                            addSearch.enable();
                            del.enable();
                            change.enable();
                            bomCopy.enable();
                            copyChild.enable();
                            cut.enable();
                            paste.enable();
                            moveup.enable();
                            movedown.enable();
                        }
                   	}
                }
                
            }
            
        },
        viewConfig: {
            stripeRows: true,
            allowCopy:true,
            listeners: {
            	itemcontextmenu: function(view, rec, node, index, e) {
                                            
                    e.stopEvent();
                    tree_menu.showAt(e.getXY());
                    return false;
                },
                drop: function (node, dragdata, overModel, dropPosition, eOpts) {         
                           
                	if(curMode=='edit')
                    {
                           var oldParentPartNo = dragdata.records[0].data.parentNo; 
                           var oldParentPartRev = dragdata.records[0].data.pver; 
                           
                           dragdata.records[0].data.parentNo = overModel.data.partNo;
                           dragdata.records[0].data.pver = overModel.data.rev;
                           
                           var new_lvl = overModel.data.lvl;
                           var n_lvl = Number(new_lvl)+1;
                           
                           dragdata.records[0].data.lvl = n_lvl;
                           overModel.data.leaf = false;
                           overModel.data.iconCls = 'task-folder';
                           overModel.expand(true);
                                  
                                    
                           store.sort('seq', 'ASC');
                    }else
                    	{
                    	 return false;
                    	}
                           
                      },       
                beforedrop: function(node, dragdata, overModel, dropPosition, dropFunction ) {
                                    
	                	if(curMode=='edit')
	                    {             
                		         var oldParentNode = dragdata.records[0].parentNode;
                                  
                                  if(oldParentNode.childNodes.length==1)
                                  {
                                    oldParentNode.data.leaf = true;
                                    oldParentNode.data.iconCls = 'task';
                                  }
                                  
                                  //console.log(overModel);
                                  
                                  var lastChildSeq = 10;
                                  if(overModel.lastChild!=null)
                                  {
                                      lastChildSeq = overModel.lastChild.data.seq;
                                      lastChildSeq = lastChildSeq+10;
                                      //alert(lastChildSeq);
                                  }
                                  
                                  dragdata.records[0].data.seq = lastChildSeq;
                           }else
                           {
                               return false;
                              }
                                  
                    }   
                      
            },   
            enableDD : true,
            plugins : {
                ptype : 'treeviewdragdrop',
                appendOnly: false,
                allowParentInserts : false,                
            }
        }
    
    });
    
    
  //Viewport 처리
    Ext.create('Ext.container.Viewport', {
        layout: 'border',
        items: [bomtoolbar, tree]        
    });
    
    
  
    //tree.getRootNode().expand(true);
    tree.getHeader().hide(); 
    
   
   
       
    //ContextMenu Item 정의
    var add = Ext.create('Ext.Action', {
        text: '신규 부품 등록 추가',
        icon:'img/part_create.gif',
        disabled: true,
        handler: function(widget, event) {
                       //newpartaddFn();
                       createPart('newpartaddFn','pType=');
                 }
    });
    
    var addSearch = Ext.create('Ext.Action', {
        text: '검색 추가',
        icon:'img/BOM_Add.gif',
        disabled: true,
        handler: function(widget, event) {
                      //bomaddFn();
                      searchPartBOMMulti('bomaddMultiFn','pType=');
                 }
    });
    
    var del = Ext.create('Ext.Action', {
        text: '제거',
        icon:'img/BOM_Remove.gif',
        disabled: true,
        handler: function(widget, event) {
                      removeFn(); 
                 }
    });
    
    var change = Ext.create('Ext.Action', {
        text: '교체',
        icon:'img/BOM_Replace.gif',
        disabled: true,
        handler: function(widget, event) {
                        //replaceFn(); 
                       searchPartBOM('replaceFn','pType=');
                 }
    });
    
    var bomCopy = Ext.create('Ext.Action', {
        text: '복사',
        icon:'img/BOM_Copy.gif',
        disabled: true,
        handler: function(widget, event) {
                       bomcopyFn();
                 }
    });
    
    var copyChild = Ext.create('Ext.Action', {
        text: '자부품 복사',
        icon:'img/BOM_CopyChild.gif',
        disabled: true,
        handler: function(widget, event) {
                      childcopyFn();
                 }
    });
    
    var cut = Ext.create('Ext.Action', {
        text: '잘라내기',
        icon:'img/BOM_Cut.gif',
        disabled: true,
        handler: function(widget, event) {
                     cutFn();
                 }
    });
    
    var paste = Ext.create('Ext.Action', {
        text: '붙여넣기',
        icon:'img/BOM_Paste.gif',
        disabled: true,
        handler: function(widget, event) {
                        pasteFn();                      
                 }
    });
    
    var moveup = Ext.create('Ext.Action', {
        text: 'Move Up',
        icon:'img/BOM_MoveUp.gif',
        disabled: true,
        handler: function(widget, event) {
                        moveupFn();                        
                 }
    });
    
    var movedown = Ext.create('Ext.Action', {
        text: 'Move Down',
        icon:'img/BOM_MoveDown.gif',
        disabled: true,
        handler: function(widget, event) {
                       movedownFn(); 
                        
                 }
    });
    
    var reverse = Ext.create('Ext.Action', {
        text: '역전개',
        icon:'img/BOM_UpwardExplosion.gif',
        disabled: false,
        handler: function(widget, event) {
                        reverseFn();                          
                 }
    });
    
    var partinfo = Ext.create('Ext.Action', {
        text: '부품정보',
        icon:'img/details.gif',
        disabled: false,
        handler: function(widget, event) {
                      getPartView();
                 }
    });
    
    
    var menuItems = [
                     add,addSearch,del,change,bomCopy,copyChild,cut,paste,moveup,movedown,reverse,partinfo
                    ];

    
    var tree_menu = new Ext.menu.Menu({
        items:menuItems
       });
    
    
    
    
    //--------------------------------------------------functions------------------------------------------------------//
        
    
    //JsonData
    function getJsonData(record){
            var nodeModel = {
            	partNo: record.data.partNo,
                rev: record.data.rev,
                newrev: record.data.newrev,
                partName: record.data.partName,
                lvl: record.data.lvl,
                seq: record.data.seq,
                qty: record.data.qty,
                unit: record.data.unit,
                econo: record.data.econo,
                checkout: record.data.checkout,
                parentNo: record.data.parentNo,
                pver: record.data.pver,
                ict: record.data.ict,
                reftop: record.data.reftop,
                refbtm: record.data.refbtm,
                material: record.data.material,
                hardnessFrom: record.data.hardnessFrom,
                hardnessTo: record.data.hardnessTo,
                designDate: record.data.designDate,
                state: record.data.state,
                checkoutId: record.data.checkoutId,
                leaf: record.data.leaf,
                iconCls: record.data.iconCls,
                children: []
            };
            
            for(var i=0; i<record.childNodes.length; i++){
                   nodeModel.children.push(getJsonData(record.childNodes[i]));
            }
                
                
            return nodeModel;
            
    }
    
    //appendJsonData
    function appendJsonData(childData, appendNode){
               
        var appendNodeDepth = appendNode.data.depth;
        childData.lvl = appendNodeDepth;
        appendNode.appendChild(childData);
        
        
         for(var i=0; i<childData.children.length; i++){
             appendJsonData(childData.children[i], appendNode.lastChild);
         }
        
    }
    
   
    //신규부품추가
    newpartaddFn = function(objArr)
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        //var parentRec = rec.parentNode;
        
        var lastChildSeq = 10;
        if(rec.lastChild!=null)
        {
            lastChildSeq = rec.lastChild.data.seq;
            lastChildSeq = lastChildSeq+10;
            //alert(lastChildSeq);
        }
        
        //for(var a=0; a <objArr.length; a++)
        //{
        	//alert(objArr[a]);
        //}
        
        var partOid = objArr[0];
        var partNum = objArr[1];
        var wtRevision = objArr[2];
        var ketRevision = objArr[3];
        var defaultUnit = objArr[4];
        var partNm = objArr[6];
        var partMaterial = objArr[5];
        
        
        if(partNum.indexOf("R10")!=-1)
        {
        	if(defaultUnit=="KG")
        		   defaultUnit = "KET_G";
        }
        
        if(partNum.indexOf("R20")!=-1)
        {
        	if(defaultUnit=="KG")
        		   defaultUnit = "KET_G";
        }
        
        if(partNum.indexOf("S")!=-1)
        {
        	if(rPartNo.substring(0,1)=="S" && defaultUnit=="KG")
        	defaultUnit = "KET_G";
        }
        
        
        if(defaultUnit.indexOf("KET_")==-1)
        {
        	defaultUnit = "KET_"+defaultUnit;
        }
        
        
       
        var tmp = {
                rev: "",
                newrev: "",
                econo: "",
                partNo: "",
                partName: "",
                ict: "-",
                parentNo: "",
                qty: "1.000",
                leaf: true,
                iconCls: "task",
                unit: "",
                material : "",
                lvl: "",
                seq: "",
                pver: "",
                checkout: "",
                children: []
            };
        
        if(rec.data.leaf)
        {
            rec.data.leaf=false;
            rec.data.iconCls = 'task-folder';   
            
        }
        
        tmp.partNo =partNum;
        tmp.rev =wtRevision;
        tmp.newrev =ketRevision;
        tmp.partName =partNm;
        tmp.unit = defaultUnit;
        tmp.seq = lastChildSeq;
        tmp.lvl = rec.data.depth+1; 
        tmp.parentNo = rec.data.partNo; 
        tmp.pver = rec.data.rev; 
        tmp.material = partMaterial;
        
        var check_dup =  check_duplication(tmp.parentNo, tmp.partNo);
        
        if(check_dup=="")
        {
            rec.appendChild(tmp, false, true);
            rec.expand(true);
        }else
        {
            alert(check_dup);
        }
        
        
    }
    
  //bomadd
    bomaddMultiFn = function (objArr)
    {
        var rPartOid = "";
        var rPartNo = "";
        var rRev = "";
        var rPartName = "";
        var rPartType = "";
        var rNewRev = "";
        var rNewMaterial = "";
        var rPartUnit ="";
        
        var mrec = tree.getSelectionModel().getSelection()[0];
       
        
        var parentRec = mrec.parentNode;
        
        var lastChildSeq = 0;
        
        //if(rec.lastChild!=null)
            //lastChildSeq = rec.lastChild.data.seq;
        //else
        	//lastChildSeq = 0;
        
        //lastChildSeq = lastChildSeq+10;
        //console.log("1==>"+lastChildSeq);
        var newLvl = mrec.data.depth;
        var newLvlNo = Number(newLvl)+1;
        
        //console.log(objArr.length);
        for ( var x = 0; x < objArr.length; x++ ) {
            
            rPartOid    = objArr[x][0];
            rPartNo     = objArr[x][1];
            rPartName   = objArr[x][2];
            rRev        = objArr[x][3];
            rPartType   = objArr[x][4];
            rNewRev     = objArr[x][12];
            rMaterial   = objArr[x][13];
            rPartUnit   = objArr[x][11];
            //alert(rNewRev);
            
           // alert(rPartUnit);
            
            if(rPartNo.indexOf("R10")!=-1)
            {
            	if(rPartUnit=="KG")
            		   rPartUnit = "KET_G";
            }
            
            if(rPartNo.indexOf("R20")!=-1)
            {
            	if(rPartUnit=="KG")
            		   rPartUnit = "KET_G";
            }
            
            if(rPartNo.indexOf("S")!=-1)
            {            	
            	if(rPartNo.substring(0,1)=="S" && rPartUnit=="KG")
            		   rPartUnit = "KET_G";
            }
            
            
            if(rPartUnit.indexOf("KET_")==-1)
            {            	
            	rPartUnit = "KET_"+rPartUnit;
            }
            
           //alert(rPartNo+" : "+rPartUnit);
            
            Ext.Ajax.request({
                url: 'KETAddBOMList.jsp',
                method: 'POST',
                async : false,
                params: {
                               "partOid" : rPartOid, 
                               "partNo" : rPartNo, 
                               "partName": rPartName, 
                               "rev": rRev, 
                               "newrev": rNewRev, 
                               "newLvl": newLvlNo,
                               "partType": rPartType                
                         },
                success: function(response, opts){
                    //console.log(response.responseText);
                    
                    if(mrec.lastChild!=null)
                    {
                        lastChildSeq = mrec.lastChild.data.seq;
                        lastChildSeq = lastChildSeq;
                        //alert(lastChildSeq);
                    }
                   
                    
                    
                    var new_rec = Ext.decode(response.responseText);
                    //console.log(new_rec[0]);
                    if(new_rec!=undefined)
                    {
	                    new_rec[0].lvl = newLvlNo; 
	                    new_rec[0].parentNo = mrec.data.partNo; 
	                    new_rec[0].pver = mrec.data.rev; 
	                    new_rec[0].seq = lastChildSeq+10;
	                    new_rec[0].qty = "1.000";
	                    new_rec[0].material = rMaterial;
	                    new_rec[0].unit = rPartUnit;
	                    //new_rec[0].newrev = rNewRev;
	                    //new_rec[0].unit = "KET_EA";
	                    
	                    var check_dup =  check_duplication(mrec.data.partNo, new_rec[0].partNo);
	                    
	                    if(check_dup=="")
	                    {
	                        appendJsonData(new_rec[0], mrec);
	                        
	                        mrec.expand(true);
	                        
	                        //mrec.appendChild(new_rec, false, true);
	                        <%if(PartUtil.isProductType(partType)){%>
	                        store.sort('seq', 'ASC');
	                        <%}else{%>
	                        store.sort('partNo', 'ASC');
	                        <%}%>
	                    }else
	                    {
	                        alert(check_dup);
	                    }
	                 }else
	                 {
	                	 //console.log(response.responseText);
	                 }
                }
            }); 
        }    
        
            
    }
    
    
    
    //bomadd
    bomaddFn = function (objArr)
    {
        //var rec = tree.getSelectionModel().getSelection()[0];
        //alert('준비중입니다.');
        
        var rPartOid = "";
        var rPartNo = "";
        var rRev = "";
        var rNewRev = "";
        var rPartName = "";
        var rPartType = "";
        var rPartUnit = "";
        var rMaterial = "";
        
        for ( var i = 0; i < objArr.length; i++ ) {
            
            rPartOid    = objArr[i][0];
            rPartNo     = objArr[i][1];
            rPartName   = objArr[i][2];
            rRev        = objArr[i][3];
            rPartType   = objArr[i][4];
            rNewRev     = objArr[i][12];
            rPartUnit   = objArr[i][11];
            rMaterial   = objArr[i][13];;
            
            
            if(rPartNo.indexOf("R10")!=-1)
            {
                if(rPartUnit=="KG")
                       rPartUnit = "KET_G";
            }
            
            if(rPartNo.indexOf("R20")!=-1)
            {
                if(rPartUnit=="KG")
                       rPartUnit = "KET_G";
            }
            
            if(rPartNo.indexOf("S")!=-1)
            {               
                if(rPartNo.substring(0,1)=="S" && rPartUnit=="KG")
                       rPartUnit = "KET_G";
            }
            
            
            
            if(rPartUnit.indexOf("KET_")==-1)
            {
            	rPartUnit = "KET_"+rPartUnit;
            }
        }    
        
        var new_rec;
        
        var rec = tree.getSelectionModel().getSelection()[0];
        
        //console.log(rec);
        
        var lastChildSeq = 10;
        if(rec.lastChild!=null)
        {
            lastChildSeq = rec.lastChild.data.seq;
            lastChildSeq = lastChildSeq+10;
            //alert(lastChildSeq);
        }
        	
        
        var parentRec = rec.parentNode;
        
        Ext.Ajax.request({
            url: 'KETAddBOMList.jsp',
            method: 'POST',
            async : false,
            params: {
                           "partOid" : rPartOid, 
                           "partNo" : rPartNo, 
                           "partName": rPartName, 
                           "rev": rRev, 
                           "newrev": rNewRev, 
                           "partType": rPartType                
                     },
            success: function(response, opts){
                //console.log(response.responseText);
                new_rec = Ext.decode(response.responseText);
                
                rec = tree.getSelectionModel().getSelection()[0];
                
                console.log("--->"+rec.data.depth);
                //if(rec.data.lvl==null)
                
                new_rec[0].lvl = rec.data.depth+1; 
                new_rec[0].parentNo = rec.data.partNo; 
                new_rec[0].pver = rec.data.rev; 
                new_rec[0].seq = lastChildSeq;
                new_rec[0].qty = "1.000";
                new_rec[0].newrev = rNewRev;
                new_rec[0].unit = rPartUnit;
                new_rec[0].material = rMaterial; 
                //console.log(new_rec[0]);
                var check_dup =  check_duplication(rec.data.partNo, new_rec[0].partNo);
                
                if(check_dup=="")
                {
                    appendJsonData(new_rec[0], rec);
                    
                    rec.expand(true);
                    
                    //rec.appendChild(new_rec, false, true);
                    <%if(PartUtil.isProductType(partType)){%>
                    store.sort('seq', 'ASC');
                    <%}else{%>
                    store.sort('partNo', 'ASC');
                    <%}%>
                }else
                {
                    alert(check_dup);
                }
                
                
              }
        });     
      
    }
    
    //remove
    function removeFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        
        var rec_seq = rec.data.seq;     
        var pnode = rec.parentNode;
        
        for(var i=0; i< pnode.childNodes.length;i++)
        {
            c_rec = pnode.childNodes[i];
            if(c_rec.data.seq>rec_seq)
                pnode.childNodes[i].data.seq= c_rec.data.seq-10;
        }
        
        if(pnode.childNodes.length==1)
        {
          pnode.data.iconCls = 'task';
          pnode.data.leaf = true;
        }
        
        rec.remove(); 
    }
    
    
    function getPartView()
    {
    	var rec = tree.getSelectionModel().getSelection()[0];
        var pn =rec.data.partNo;
        var ver =rec.data.rev;    
        
        Ext.Ajax.request({
            url: 'getPartOid.jsp',
            method: 'POST',
            async : false,
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
    
    
    //초기화
    function bominitFn()
    {
       
    	var msg = '초기화 하시겠습니까?';
        
        if(confirm(msg))
        {
        	var rec = tree.getSelectionModel().getSelection()[0];
            
            
            tree.getSelectionModel().clearSelections();
            store.load();
            tree.getRootNode().expand(true);
            tree.getHeader().hide();
        }
    	
    	
    }
    
    //moveup
    function moveupFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        var cur_idx = rec.data.index;
        
        var c_t = rec.get('seq');
        
        if(cur_idx>0)
        {
            var upNode = tree.getSelectionModel().getSelection()[0].parentNode.childNodes[cur_idx-1];
            var u_t = upNode.get('seq');
            upNode.set('seq', c_t);
            rec.set('seq', u_t);
            upNode.data.index = cur_idx;
            rec.data.index = cur_idx-1;
            store.sort('seq', 'ASC');
        }
    }
    
    //movedown
    function movedownFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        var downNodeSize = tree.getSelectionModel().getSelection()[0].parentNode.childNodes.length;
         
        var cur_idx = rec.data.index;                        
        var c_t = rec.get('seq');
         
        
        if((downNodeSize-cur_idx-1)>0)
        {
             var downNode = tree.getSelectionModel().getSelection()[0].parentNode.childNodes[cur_idx+1];
             var d_t = downNode.get('seq');
             downNode.set('seq', c_t);
             rec.set('seq', d_t);
             downNode.data.index = cur_idx;
             rec.data.index = cur_idx+1;
             store.sort('seq', 'ASC');
        }
    }
    
    //bomcopy
    function bomcopyFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        copyid = 'bomcopy';
        copydata =[];
        copydata.push(getJsonData(rec));
    }
    
  //자부품복사
    function childcopyFn(){
        var rec = tree.getSelectionModel().getSelection()[0];
        var isLeafCnt=rec.childNodes.length;
        console.log(isLeafCnt);
        
       
        for(i=0; i<rec.childNodes.length;i++)
        {
            
            if(rec.childNodes[i].data.leaf)
            {
                isLeafCnt--;
            }
        }
        
        if(isLeafCnt==0)
        {
            copyid = 'childcopy';                                             
            copydata = [];                      
            for(i=0; i<rec.childNodes.length;i++)
            {
                console.log(rec.childNodes[i]);
            	copydata.push(rec.childNodes[i].copy());
            }
            
            console.log(copydata);
        }
        
    }
    
    
    //cut
    function cutFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        copyid = 'cut';
        copydata = rec;
    }
    
    //replace
    replaceFn = function (objArr)
    {
       
        var rPartOid = "";
        var rPartNo = "";
        var rRev = "";
        var rPartName = "";
        var rPartType = "";
        var rNewRev = "";
        
        
        for ( var i = 0; i < objArr.length; i++ ) {
            
            rPartOid    = objArr[i][0];
            rPartNo     = objArr[i][1];
            rPartName   = objArr[i][2];
            rRev        = objArr[i][3];
            rPartType   = objArr[i][4];
            rNewRev     = objArr[i][12];
        }    
        
        var new_rec;
        
        var rec = tree.getSelectionModel().getSelection()[0];
        
        var parentRec = rec.parentNode;
        
        Ext.Ajax.request({
            url: 'KETAddBOMList.jsp',
            method: 'POST',
            async : false,
            params: {
                           "partOid" : rPartOid, 
                           "partNo" : rPartNo, 
                           "partName": rPartName, 
                           "rev": rRev, 
                           "newrev": rNewRev, 
                           "partType": rPartType                
                     },
            success: function(response, opts){
                //console.log(response.responseText);
                new_rec = Ext.decode(response.responseText);
                
                new_rec[0].lvl = rec.data.depth; 
                new_rec[0].seq = rec.data.seq; 
                new_rec[0].parentNo = parentRec.data.partNo; 
                new_rec[0].pver = parentRec.data.rev; 
                new_rec[0].newrev = rNewRev; 
                //console.log(new_rec[0]);
                
                var check_dup =  check_duplication(new_rec[0].parentNo, new_rec[0].partNo);
                
                if(check_dup=="")
                {
                    appendJsonData(new_rec[0], parentRec);
                    
                    parentRec.expand(true);
                    
                    //rec.appendChild(new_rec, false, true);
                    <%if(PartUtil.isProductType(partType)){%>
                    store.sort('seq', 'ASC');
                    <%}else{%>
                    store.sort('partNo', 'ASC');
                    <%}%>
                    
                    removeFn();
                    tree.getSelectionModel().select(0);
                }else
                {
                    alert(check_dup);   
                }
                
                
              }
        });     
      
    }
    
    //paste
    function pasteFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        
        if(rec.data.leaf && (copydata!=null))
        {
            rec.data.leaf=false;
            rec.data.iconCls = 'task-folder';
        }
        
        if(copyid == 'childcopy')
        {
        	var check_dup;
        	for(x=0; x<copydata.length; x++)
            {
               
                
                var copyChildNode = copydata[x].data;
                copyChildNode.id= '';
                copyChildNode.parentId= '';
                copyChildNode.parentNo= rec.data.partNo;
                copyChildNode.pver= rec.data.rev;
                copyChildNode.lvl= rec.data.depth;
                
                
                //rec.appendChild(copyChildNode);
                
                check_dup =  check_duplication(copyChildNode.parentNo, copyChildNode.partNo);
                
                if(check_dup=="")
                {
                    rec.appendChild(copyChildNode);
                }else
                {
                    alert(check_dup);
                }
                
                
                
            }
        }
        
        if(copyid == 'bomcopy')
        {
            
            copydata.parentNo = rec.data.partNo;
            copydata.pver = rec.data.rev;  
            
            var check_dup =  check_duplication(copydata.parentNo, copydata[0].partNo);
            
            if(check_dup=="")
            {
                appendJsonData(copydata[0], rec);
            }else
            {
                alert(check_dup);
            }
            
            
        }
        
        if(copyid == 'cut')
        {
            
            copydata.data.parentNo= rec.data.partNo;
            copydata.data.pver= rec.data.rev;
            copydata.data.parentId='';
            copydata.data.lvl=rec.data.depth;
            var lvldepth=0;
            copydata.cascadeBy(function(child){
                child.data.lvl=rec.data.depth+lvldepth;
                lvldepth++;
            });
            
            rec.appendChild(copydata);
            /*
            var check_dup =  check_duplication(copydata.data.parentNo, copydata.data.partNo);
            
            if(check_dup=="")
            {
                 
            }else
            {
                alert(check_dup);
            }
             */          
        }
                
        rec.expand(true);
        
        copyid='';
        copydata=null;
    }
    
    //POPUP Window
    function winOpenFn(popupurl, t_name, p_status)
    {
        
        var bomwin = window.open(popupurl, t_name, p_status);
        bomwin.focus();   
        
        return bomwin;
    }
    
    //Bom Reverse
    function reverseFn()
    {
        var rec = tree.getSelectionModel().getSelection()[0];
        var pn =rec.data.partNo;
        var ver =rec.data.rev;
        
        winOpenFn('KETBomMain.jsp?url=KETBomReverse.jsp&partNumber='+pn+'&partRev='+ver,'BOM Reverse','scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=1100,height=650');
    }
    
  
    function searchPartBOMMulti(callBackFn, dataParam)
    {
        var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        var url="/plm/ext/part/base/listPartPopup.do?mode=m&modal=N&fncall="+callBackFn+"&" + _dataParam;
        window.open(url,"부품검색","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=1024,height=768");
    }
    
    function searchPartBOM(callBackFn, dataParam)
    {
        var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        var url="/plm/ext/part/base/listPartPopup.do?mode=s&modal=N&fncall="+callBackFn+"&" + _dataParam;
        window.open(url,"부품검색","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=1024,height=768");
    }
    
    
    function createPart(callBackFn, dataParam)
    {
        
        var win = PartUtil.regView();
        //var _dataParam = (dataParam && dataParam !=null )? dataParam:"";
        //var url="/plm/servlet/e3ps/PartServlet?cmd=opencreatePopup&mode=s&modal=N&fncall="+callBackFn+"&" + _dataParam;
        //window.open(url,"신규부품추가","scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=725,height=515");
    }
    
    
    function materialUpdate()
    {
        var items = tree.getStore().data.items;  
        var partNoArr = new Array();
        var noArr = new Array();
        var lvlArr = new Array();
        
        var ictArr = new Array();
        var seqArr = new Array();
        var partNameArr = new Array();
        var qtyArr = new Array();
        var unitArr = new Array();
        var revArr = new Array();
        var stateArr = new Array();
        var econoArr = new Array();
        var checkoutArr = new Array();
        var reftopArr = new Array();
        var refbtmArr = new Array();
        
        var materialArr = new Array();
        var hardnessFromArr = new Array();
        var hardnessToArr = new Array();
        var designDateArr = new Array();
        
        var parentNoArr = new Array();
        var pverArr = new Array();
        
        
        
        for(i=0;i<items.length;i++)
        {
            partNoArr[i]       = items[i].data.partNo;
            noArr[i]           = i;
            lvlArr[i]          = items[i].data.lvl;
            ictArr[i]          = items[i].data.ict;
            seqArr[i]          = items[i].data.seq;
            
            partNameArr[i]     = items[i].data.partName;
            qtyArr[i]          = items[i].data.qty;
            unitArr[i]         = items[i].data.unit;
            revArr[i]          = items[i].data.rev;
            stateArr[i]        = items[i].data.state;
            econoArr[i]        = items[i].data.econo;
            checkoutArr[i]     = items[i].data.checkout;
            reftopArr[i]       = items[i].data.reftop;
            refbtmArr[i]       = items[i].data.refbtm;
            materialArr[i]     = items[i].data.material;
            hardnessFromArr[i] = items[i].data.hardnessFrom;
            hardnessToArr[i]   = items[i].data.hardnessTo;
            designDateArr[i]   = items[i].data.designDate;
            
            parentNoArr[i]     = items[i].data.parentNo;
            pverArr[i]         = items[i].data.pver;
            
        }
        
       
        Ext.Ajax.request({
                url: './KETBomMaterialList.jsp?partType='+partType+'&ecoNumber=<%=ecoNumber%>&gubun=<%=gubun%>',
                method: 'POST',
                async : false,
                target: validWin, 
                params: {
                               "partNo" : partNoArr, 
                               "index": noArr, 
                               "lvl": lvlArr, 
                               "ict": ictArr, 
                               "seq": seqArr, 
                               "partName": partNameArr, 
                               "qty": qtyArr, 
                               "unit": unitArr, 
                               "rev": revArr, 
                               "state": stateArr, 
                               "econo": econoArr, 
                               "checkout": checkoutArr, 
                               "reftop": reftopArr, 
                               "refbtm": refbtmArr, 
                               "material": materialArr, 
                               "hardnessFrom": hardnessFromArr, 
                               "hardnessTo": hardnessToArr, 
                               "designDate": designDateArr, 
                               "parentNo": parentNoArr, 
                               "pver": pverArr                               
                         },
                success: function(response, opts){
                    
                    var materialResult = Ext.decode(response.responseText);
                    //console.log(materialResult);
                    var errLog =  materialResult.result.errlog;
                    var matResultArr =  materialResult.result.data;
                    
                    mPartOid = new Array();
                    mPartNo = new Array();
                    mOldWeight = new Array();
                    mOldSweight = new Array();
                    mOldTweight = new Array();
                    mOldMaterial = new Array();
                    mNewWeight = new Array();
                    mNewSweight = new Array();
                    mNewTweight = new Array();
                    mNewMaterial = new Array();
                    
                    
                    
                    for(i=0; i < matResultArr.length; i++)
                    {
                        mPartNo[i] = matResultArr[i].partNo;
                        mPartOid[i] = matResultArr[i].partOid;
                        mOldWeight[i] = matResultArr[i].old_weight;
                        mOldSweight[i] = matResultArr[i].old_sweight;
                        mOldTweight[i] = matResultArr[i].old_tweight;
                        mOldMaterial[i] = matResultArr[i].old_material;
                        mNewWeight[i] = matResultArr[i].new_weight;
                        mNewSweight[i] = matResultArr[i].new_sweight;
                        mNewTweight[i] = matResultArr[i].new_tweight;
                        mNewMaterial[i] = matResultArr[i].new_material;
                    }
                    
                    
                    var validWin =  winOpenFn('KETBomMaterialResult.jsp?ecoNumber=<%=ecoNumber%>&topPartNo=<%=partNumber%>&topRev=<%=partRev%>','BOM Material Weight Update','scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=800,height=400');
                    
                  }
            });  
    }
    
    storeLoad = function()
    {
    	//alert("Stroe Load");
    	store.load();
    }
    
    bomValidation = function ()
    {
        validResult = "";
        var items = tree.getStore().data.items;  
        var partNoArr = new Array();
        var noArr = new Array();
        var lvlArr = new Array();
        
        var ictArr = new Array();
        var seqArr = new Array();
        var partNameArr = new Array();
        var qtyArr = new Array();
        var unitArr = new Array();
        var revArr = new Array();
        var stateArr = new Array();
        var econoArr = new Array();
        var checkoutArr = new Array();
        var reftopArr = new Array();
        var refbtmArr = new Array();
        
        var materialArr = new Array();
        var hardnessFromArr = new Array();
        var hardnessToArr = new Array();
        var designDateArr = new Array();
        
        var parentNoArr = new Array();
        var pverArr = new Array();
        
        
        
        for(i=0;i<items.length;i++)
        {
            partNoArr[i]       = items[i].data.partNo;
            noArr[i]           = i;
            lvlArr[i]          = items[i].data.lvl;
            ictArr[i]          = items[i].data.ict;
            seqArr[i]          = items[i].data.seq;
            
            partNameArr[i]     = items[i].data.partName;
            qtyArr[i]          = items[i].data.qty;
            unitArr[i]         = items[i].data.unit;
            revArr[i]          = items[i].data.rev;
            stateArr[i]        = items[i].data.state;
            econoArr[i]        = items[i].data.econo;
            checkoutArr[i]     = items[i].data.checkout;
            reftopArr[i]       = items[i].data.reftop;
            refbtmArr[i]       = items[i].data.refbtm;
            materialArr[i]     = items[i].data.material;
            hardnessFromArr[i] = items[i].data.hardnessFrom;
            hardnessToArr[i]   = items[i].data.hardnessTo;
            designDateArr[i]   = items[i].data.designDate;
            
            parentNoArr[i]     = items[i].data.parentNo;
            pverArr[i]         = items[i].data.pver;
            
        }
        
        //var validWin =  winOpenFn('KETBomValidationResult.jsp','BOM Validation','scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=500,height=400');
        //validWin =  winOpenFn('KETBomValidationResult.jsp','BOM Validation','scrollbars,toolbar=no,location=no,directories=no,status=yes,menubar=no,resizable=yes,width=500,height=400');
        
        
        Ext.Ajax.request({
                url: './KETBomValidation.jsp?partType='+partType+'&ecoNumber=<%=ecoNumber%>&gubun=<%=gubun%>',
                method: 'POST',
                async : false,
                target: validWin, 
                params: {
                               "partNo" : partNoArr, 
                               "index": noArr, 
                               "lvl": lvlArr, 
                               "ict": ictArr, 
                               "seq": seqArr, 
                               "partName": partNameArr, 
                               "qty": qtyArr, 
                               "unit": unitArr, 
                               "rev": revArr, 
                               "state": stateArr, 
                               "econo": econoArr, 
                               "checkout": checkoutArr, 
                               "reftop": reftopArr, 
                               "refbtm": refbtmArr, 
                               "material": materialArr, 
                               "hardnessFrom": hardnessFromArr, 
                               "hardnessTo": hardnessToArr, 
                               "designDate": designDateArr, 
                               "parentNo": parentNoArr, 
                               "pver": pverArr                               
                         },
                success: function(response, opts){
                    
                    var validRsult = Ext.decode(response.responseText);
                    
                    var errLog =  validRsult.result.errlog;
                    var checkdata1 =  validRsult.result.checkdata1;
                    var checkdata2 =  validRsult.result.checkdata2;
                    var checkdata3 =  validRsult.result.checkdata3;
                    //var checkdata3 =  "";
                    var checkdata4 =  validRsult.result.checkdata4;
                    //var checkdata4 =  "";
                    var checkdata5 =  validRsult.result.checkdata5;
                    var checkdata6 =  validRsult.result.checkdata6;
                    var checkdata7 =  validRsult.result.checkdata7;
                    
                    var rsltCnt = 0;
                    
                    
                    if(errLog=='')
                    {
                        if(checkdata1=='')
                        {
                            validWin.document.all.check1.checked = true;  
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata1").innerHTML =checkdata1;
                            validResult+=checkdata1;
                        }
                        
                        if(checkdata2=='')
                        {
                            validWin.document.all.check2.checked = true;   
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata2").innerHTML =checkdata2;
                            validResult+=checkdata2;
                        }
                        
                        if(checkdata3=='')
                        {
                            validWin.document.all.check3.checked = true;  
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata3").innerHTML =checkdata3;
                            validResult+=checkdata3;
                        }
                        
                        if(checkdata4=='')
                        {
                            validWin.document.all.check4.checked = true;   
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata4").innerHTML =checkdata4;
                            validResult+=checkdata4;
                        }
                        
                        if(checkdata5=='')
                        {
                            validWin.document.all.check5.checked = true;    
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata5").innerHTML =checkdata5;
                            validResult+=checkdata5;
                        }
                        
                        if(checkdata6=='')
                        {
                            validWin.document.all.check6.checked = true; 
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata6").innerHTML =checkdata6;
                            validResult+=checkdata6;
                        }
                        
                        if(checkdata7=='')
                        {
                            validWin.document.all.check7.checked = true;  
                            rsltCnt++;
                        }else
                        {
                            validWin.document.getElementById("checkdata7").innerHTML =checkdata7;
                            validResult+=checkdata7;
                        }
                        
                        validWin.document.getElementById("rsltCnt").innerHTML ='(체크항목 : '+rsltCnt + ' 건)';
                        
                        validWin.alertMsg('BOM 검증이 완료되었습니다.');
                        isValid=true;
                    }else
                    {
                        validWin.alertMsg('BOM 검증 중에 오류가 발생하였습니다.\\n('+errLog+')');
                        validResult+=errLog;
                    }
                    
                    
                    
                  }
            });  
    }
    
    
    function check_duplication(parentitemcode, childitemcode)
    {
        var items = tree.getStore().data.items;  
        
         //var partNoArr = new Array();
         //parentNoArr[i]     = items[i].data.parentNo;
         
         var result = "";
        
        for(var z=0; z<items.length; z++)
        {
                if(parentitemcode==items[z].data.parentNo && childitemcode==items[z].data.partNo)
                {
                    result +=  "중복된 데이터입니다.("+childitemcode+")";
                }
        }
        
        return result;
    }
    
    
});


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
    
   
</style>
</head>
<body  oncontextmenu="return false">
<iframe id="hiddenFrame" name="hiddenFrame" style="display:none;"></iframe>
</body>
</html>