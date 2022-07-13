Ext.define('Ket.main.LeftMenuPanel',{
	extend : 'Ext.panel.Panel',
	initComponent : function(){
		Ext.apply(this, {
			id : 'leftPanel',
			region : 'west',
			title : 'Menu',
			iconCls : 'menuIcon',
			layout:'border',
			width: 234,
	        margin:'15 0 5 0',
			split:true,
//			lockedPanel : false,
			collapsible: true,
	        collapseDirection: 'right',
//	        tools: [{ 
//	        	type:'pin',
//	        	tooltip : '고정',
//	        	callback : function(panel, tool, event){
//	        		if(tool.type == 'pin'){
//	        			tool.setType('unpin');
//	        			panel.setConfig(panel.collapsible,false);
//	        			panel.collapsible = false;
//	        			Ext.getCmp('workspace').expand(true);
//	        		}else{
//	        			tool.setType('pin');
//	        			panel.setConfig(floatable,true);
//	        		}
//	        	}	        	
//	        },{ 
//	        	type:'help',
//	        	tooltio : 'HelpDesk',
//	        	handler : function(event, toolEl, panelHeader){
////	        		this.setConfig('collapsed', false);
//	        	}
//	        }],
			items : [
			    this.createAccordion()			    
			]
		});
		this.callParent(arguments);
	},

	createAccordion : function(){
		this.accPanel = Ext.create('Ext.panel.Panel',{
			id : 'menuPanel',
			region : 'center',
			layout : 'accordion',
			border : false,
			cls: 'ket-accordion',
	        layoutConfig: {
	            // layout-specific configs go here
	        	animate: true,
	        	titleCollapse: true,
	        	expandedItem: -1,
	        	activeOnTop: true
	        },
	        bodyStyle :{
	        	backgroundColor:"#d6eff4"
	    	},
			items : [
			    this.createMenuTree('workspaceIcon','Workspace','workspace'),
			    this.createMenuTree('projectIcon','Project','project'),
			    this.createMenuTree('moldIcon',LocaleUtil.getMessage('09534'),'mold'),
			    this.createMenuTree('documentIcon','Document','document'),
			    this.createMenuTree('drawingsIcon','Drawing','drawings'),
			    this.createMenuTree('partIcon','Part','part'),
				this.createMenuTree('ecmIcon','ECM','ecm'),
				this.createMenuTree('ewsIcon','EWS','ews'),
				this.createMenuTree('developIcon','Helpdesk','helpdesk'),
				this.createMenuTree('adminIcon',LocaleUtil.getMessage('00945'),'admin'),
				this.createMenuTree('dashboardIcon','Dashboard','dashboard'),
				this.createMenuTree('costIcon',LocaleUtil.getMessage('09249'),'cost')
			]
		});	
		return this.accPanel; 
	},
	
	/**
	 * tree 메뉴 생성
	 * @param title
	 * @param menuCode
	 * @returns {Ext.tree.TreePanel}
	 */
	createMenuTree : function(iconCls,title, menuCode){
		if(menuCode == 'admin' && !isAdmin) return null;
		if(menuCode == 'ews' && isKETSUser) return null;
		if(isSqMember){
			if(menuCode == 'project' || menuCode == 'mold' || menuCode == 'document' || menuCode == 'drawings' ){
				
			}else{
				return null;
			}
		}		
		
		var menuTree = Ext.create('Ext.tree.Panel',{
			id : menuCode,
			title : title,
			iconCls : iconCls,
			border : false,
			lines : true,			
			useArrows : false,
			rootVisible : false,
			singleExpand : false,
			hideCollapseTool: false,
//			collapsed : menuCode == 'workspace'?false:true,
			bodyStyle:{
	        	backgroundColor:"#e0edf7",
	        	borderBottomWidth:'1px',
	        	borderBottomColor:'#99bbe8'
	        },
			store : Ext.create('Ext.data.TreeStore', {
				autoLoad : menuCode == 'workspace' || (isSqMember && menuCode == 'project'),
				proxy: {
					type : 'ajax',
					url : '/plm/ext/menuData.do',
					extraParams : {
						menuCode : menuCode
					}
				},
				loadMask : true,
				requestMethod : 'POST'
			}),
			listeners: {        
			     beforeload: function (store, operation, eOpts) {            
			          if(store.isLoading()) return false;        
			     }    
			},
			tools: [{
				type: !isSqMember && menuCode == 'workspace'?'minus':'plus' ,
				type: isSqMember && menuCode == 'project'?'minus':'plus',
	        	callback : function(panel, tool, event){
	        		if(tool.type == 'plus'){
	        			tool.setType('minus');	        			
	        			panel.expand();
	        		}else{
	        			tool.setType('plus');	        			
	        			panel.collapse();
	        		}
	        	}	        	
	        }],
		});
		menuTree.on('itemclick',this.changeMenu,this);
		menuTree.on('expand',function(tree){
			if(!tree.getRootNode().isExpanded()){
				tree.getRootNode().expand();				
			}
			if(tree.down('tool[type=plus]') && tree.down('tool[type=plus]').type == 'plus'){
				tree.down('tool[type=plus]').setType('minus');
			}
		},this);		
		menuTree.on('collapse',function(tree){
			if(tree.down('tool[type=minus]') && tree.down('tool[type=minus]').type == 'minus'){
				tree.down('tool[type=minus]').setType('plus');				
			}
		},this);
		return menuTree;
	},
	
	/**
	 * Node를 클릭했을때 event
	 * mainPanel에 panelId에 해당하는 panel을 보여준다.
	 */
	changeMenu : function(view, record, item, index, e, eOpts){
		var panelId = record.data.panelId;
		var panelUrl = record.data.url; 
		var panelTitle = record.data.text;
		var parameter = record.data.parameter;
		
		//node가 child를 가지면 expand되게 한다.
		if(!record.isExpanded() && record.hasChildNodes()){
			record.expand(true,true,null,this);
			return;
		}
			
		if(panelId && panelId == 'admin01'){
			window.open('/plm/portal/admin/index.jsp?u=39','_blank');
		}else if(panelId && panelId == 'cost01'){
			//window.open('/plm/jsp/cost/common/login_ok.jsp','_blank');
			window.open('/plm/extcore/logincheck.jsp?j_username=dhyoon','_blank');
			window.opener='nothing';
            window.open('','_parent','');
            window.close();
		}else if(panelId && panelId == 'sales03'){
			window.open("/plm/ext/sales/project/viewsalesCSMeetingImgForm.do?oid=&csYN=Y", "viewsalesCSMeetingImgFormFormPopup", "width=1300,height=800");
		}else if(panelId && panelId == 'sales04'){
			window.open("/plm/ext/sales/project/viewSalesCSProjectForm.do?oid=&csYN=Y", "viewSalesCSProjectFormPopup", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'project08'){
			window.open("/plm/jsp/project/projectType/ManageOEMMain.jsp?oemtype=CARTYPE&codetype=CUSTOMEREVENT", "viewManageOEMMainFormPopup", "width=1050,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'retireWork'){
			//window.location.href = "/plm/ext/main.do?id="+parameter;
			window.open('/plm/extcore/jsp/login/loginketretire.jsp?j_username='+parameter,'_blank');
			window.opener='nothing';
            window.open('','_parent','');
            window.close();
		}else if(panelId && panelId == 'costCode00'){
			window.open("/plm/ext/cost/code/costPartTypeEditor.do", "costPartTypeEditor", "width=1100,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode01'){
			window.open("/plm/ext/cost/code/costPartTypeLink.do", "costPartType", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode09'){
			window.open("/plm/ext/cost/code/costWorkTimePopup.do", "costWorkTimePopup", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode02'){
			window.open("/plm/ext/cost/viewNMetalCSIPopup.do", "NMetalCSI", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode03'){
			window.open("/plm/ext/cost/viewProfitCSIPopup.do", "ProfitCSI", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode04'){
			window.open("/plm/ext/cost/viewExRateCSIPopup.do", "ExRateCSI", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode05'){
			window.open("/plm/ext/cost/code/costReducePopup.do", "Reduce", "width=1050,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode06'){
			window.open("/plm/ext/cost/code/CostLogisticsPartPopup.do", "Reduce", "width=1050,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode07'){
			window.open("/plm/ext/cost/code/CostLogisticsMtrPopup.do", "Reduce", "width=1050,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costCode08'){
			window.open("/plm/jsp/common/code/ManageCodeMainByCost.jsp", "CostBaseCode", "width=1200,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costFomula01'){
			window.open("/plm/ext/cost/costFormulaEditorPopup.do", "FormulaEditor", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costAcl01'){
			window.open("/plm/ext/cost/manage/costBomACLPopup", "costBomACL", "width=1200,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'costAcl02'){
			window.open("/plm/ext/cost/manage/costPartTypeACL", "costPartACL", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'workspace15'){
			window.open("/plm/ext/wfm/workspace/viewListTotalWork", "viewListTotalWorkPopup", "width=1300,height=800,scrollbars=yes,resizable=yes");
		}else if(panelId && panelId == 'erp01'){
			window.open('http://192.168.1.217:8080/ssomanager2/gateway?passId='+parameter+'&tCode=ZPPR1800', '_blank');
			window.opener='nothing';
            window.open('','_parent','');
		}else if(panelId){
			Ext.getCmp('mainPanel').changeMainPanel(record.data);			
		}
	}
});