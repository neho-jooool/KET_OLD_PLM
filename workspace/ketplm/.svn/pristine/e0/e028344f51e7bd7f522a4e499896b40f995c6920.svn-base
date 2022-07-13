Ext.define('Ket.main.MainPanel',{
	extend: 'Ext.tab.Panel',
//    xtype: 'plain-tabs',
	activeItem: 0,
    plain: true,
    tabWidth : 130,
    minTabWidth: 100,
    resizeTabs:true,
    titleAlign : 'right',
//    tabBar:[{
//        dockedItems:[{ 
//            xtype: 'button',
//            text : 'Test Button'
//        }]
//    }],
	initComponent : function(){
//		var tabItems = Ext.util.Cookies.get('tabItems');
//		if(tabItems){
//			//alert(Ext.decode(tabItems).length);			
//		}
		Ext.apply(this, {
			id : 'mainPanel',
			region : 'center',
//			cls : 'ket-tab',
//			deferredRender : true,
			border : false,
			margin : '15 5 5 0',
			border : true,
			enableTabScroll:true,
			layoutOnTabChange : true,
			stateful: true,
	        stateId: 'tp1',
	        stateEvents: ['tabchange','add','close'],
	        getState: function() {
	        	return {
	        		activeTab: this.items.findIndex('id',this.getActiveTab().id)
	        	};
	        },
	        applyState: function(s) {
	          this.setActiveTab(s.activeTab);
	        },
			items : [{
				id : 'homePanel',
				xtype: 'component',
				layout : 'fit',
				iconCls : 'homeIcon',
				title : 'Home',
				autoEl: {
					tag: 'iframe',
					src:'/plm/jsp/common/loading2.jsp?url=/plm/ext/mainContents.do',
					frameborder: 0
				},
				tools : [{
					type : 'refresh',
					handler: function(event, toolEl, panel){
		            }
				}]				
			}]
		});
		this.callParent(arguments);
//		this.on('add',function(){
//			Ext.state.Manager.set('tabItems',);
//		},this);
		//Ext.util.Cookies.set('tabItems', d.data.machineName);
	},

	/**
	 * MainPanel 변경
	 */
	changeMainPanel : function(menuObj){
		var panelId = menuObj.panelId;
		var panelUrl = menuObj.url; 
		var panelTitle = menuObj.text;
	        if (menuObj.title) {
	            panelTitle = menuObj.title;
	        } else {
	            panelTitle = menuObj.text;
	        }
		if(!panelUrl) return;
		if(this.queryById(panelId)){
			this.setActiveTab(panelId);
		}else{
			var newTab = {
				id : panelId,
				xtype: 'container',
				layout : 'fit',
				title:panelTitle,
				tabWidth : 130,
				closable:true,
				tools : [{
					type : 'refresh'
				}],
				autoEl: {
					tag: 'iframe',
					src: panelId == 'indexSearch01'?panelUrl:'/plm/jsp/common/loading2.jsp?url='+panelUrl,
					frameborder: 0
				}
			};
			this.add(newTab).show();
//			var tabItems = Ext.decode(Ext.util.Cookies.get('tabItems'));
//			if(!tabItems){
//				tabItems = [];
//			}
//			tabItems[tabItems.length] = newTab;
//			Ext.util.Cookies.set('tabItems', tabItems);
		}
	},
	
	/**
	 * 통합 검색 Tab
	 * @param searchCategory
	 * @param searchKeyword
	 */
	openIndexSearchTab : function(searchCategory, searchKeyword){
		this.openAndReloadTab({
			panelId : 'indexSearch01',
			text : '통합검색',
			url : '/plm/ext/index/searchList.do?searchCategory='+searchCategory+'&searchKeyword='+searchKeyword
		});
//		this.openTabAndCallBack({
//			panelId : 'indexSearch01',
//			text : '통합검색',
//			url : '/plm/ext/index/searchList.do?searchCategory='+searchCategory+'&searchKeyword='+searchKeyword
//		},'IndexSearch.callMain(\''+searchCategory+'\', \''+searchKeyword+'\')');
//		if(this.queryById('indexSearch01')){
//			this.queryById('indexSearch01').getEl().dom.contentWindow.IndexSearch.callMain(searchCategory, searchKeyword);
//			this.setActiveTab('indexSearch01');
//		}else{
//			this.changeMainPanel({
//				panelId : 'indexSearch01',
//				text : '통합검색',
//				url : '/plm/ext/index/searchList.do?searchCategory='+searchCategory+'&searchKeyword='+searchKeyword
//			});
//		}
		return false;
	},
	
	/**
	 * tab 호출 및 function 호출
	 * @param panelObj
	 * @param callBackFn
	 */
	openTabAndCallBack : function(panelObj, callBackFn){
		if(this.queryById(panelObj.panelId)){
			if(callBackFn){
				eval('this.queryById(panelObj.panelId).getEl().dom.contentWindow.'+callBackFn);				
			}
			this.setActiveTab(panelObj.panelId);
		}else{
			this.changeMainPanel(panelObj);			
		}
	},
	
	/**
	 * tab 호출 및 reload
	 * @param panelObj
	 */
	openAndReloadTab : function(panelObj){
		if(this.queryById(panelObj.panelId)){
			this.setActiveTab(panelObj.panelId);
			$('#'+panelObj.panelId).attr('src',panelObj.url);
		}else{
			this.changeMainPanel(panelObj);			
		}
	},
	
	createSearchItemCombo : function(){
		var store = new Ext.data.SimpleStore({
			fields : ['key', 'value'],
			data : [
			    ['문서','document'],
			    ['프로젝트','project'],
			    ['부품','part']
			]
		});
		var combo = new Ext.form.ComboBox({
			store: store,
    		valueField: 'key',
          	displayField:'value',
            typeAhead: true,
            mode: 'local',
            forceSelection: true,
            triggerAction: 'all',
            selectOnFocus:true,
			lazyRender:true,
    		editable: false
		});
		return combo;
	}
});