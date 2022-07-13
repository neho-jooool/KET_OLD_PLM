var searchCategoryComboWin;

Ext.Loader.setConfig({
	enabled: true,
	paths: {
	    'Ket' : '/plm/extcore/js'
	}
});


Ext.require('Ket.main.LeftMenuPanel');
Ext.require('Ket.main.MainPanel');

/**
 * 페이지가 Loading후 
 * Viewport를 정의 한다.
 */
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
	Ext.get('header').show();
	
	//tab statefull을 위한 Provider 
	Ext.state.Manager.setProvider(new Ext.state.CookieProvider({
		expires: new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 7))
    }));
	
	//작업장의 Panel을 배치한다.
	var viewport = Ext.create('Ext.container.Viewport',{
		layout : 'border',
		border : false,
		layoutConfig: {
	         minWidth: 1280
		},
		items : [{
			region : 'center',
			layout : 'border',
		    items : [{
            	 region : 'north',
            	 height : 47,
            	 border : false,
            	 contentEl : 'header',
             },
             Ext.create('Ket.main.LeftMenuPanel'), 
             Ext.create('Ket.main.MainPanel')
		    ]
		}]
	});
	
	//loading image hide	
	var hideMask = function() {
		Ext.get('loading').remove();
		Ext.fly('loading-mask').fadeOut( {
			remove : true
		});
	};
	Ext.Function.defer(hideMask, 100);
	
	//Logout anchor tag click
	Ext.get('logoutAnchor').on('click',function(){
		//windchill logout api가 별도로 없으므로 창만 닫아준다.
		//document.execCommand('ClearAuthenticationCache'); //ep까지 logout 됨
		window.close();
	});
	
	//통합검색
	Ext.get('goSearch').on('click',function(){
		goSearch();
	});
	Ext.get('searchKeyword').on('keypress',function(e){
		if (e.keyCode == 13) {
			e.preventDefault();
			goSearch();
		}
	});
	
	Ext.get('searchCategoryText').on('click',function(){
		if(!searchCategoryComboWin){
			Ext.get('searchCategoryDiv').show();
			searchCategoryComboWin = Ext.create('Ext.window.Window',{
				cls : 'select-check box-size',
				width: Ext.get('searchCategoryDiv').getWidth(),
				height: Ext.get('searchCategoryDiv').getHeight(),
			    layout: 'fit',
			    border : false,
			    plain : true,
			    frame : false,
			    header : false,
			    closable : false,
			    closeAction : 'hide',
			    resizable : false,
			    model : true,
			    x : Ext.get('searchCategoryText').getX(),
			    y : Ext.get('searchCategoryText').getY(),
			    html : Ext.get('searchCategoryDiv').getHtml()
			});
		}
		
		$(document).click(function(e){
			if($(e.target).is('#'+searchCategoryComboWin.getId()+', #'+searchCategoryComboWin.getId()+' *'))return;
			selectCategory();
		});
		searchCategoryComboWin.show('searchCategoryText',function(){
		});
	});

	//local change
	Ext.get('localeChange').on('change',function(){
		document.LangForm.action = '/plm/ext/main.do';
		document.LangForm.submit();
	});
});

function selectCategory(winEl, event, eOpts){
	if(searchCategoryComboWin){
		//통합검색 유형 선택
		$('#searchCategoryText')[0].innerText = '';
		$('[name=searchCategory]').each(function(){
			if($(this).val() && $(this)[0].checked){
				if($('#searchCategoryText')[0].innerText){
					$('#searchCategoryText')[0].innerText = $('#searchCategoryText')[0].innerText + ','+ $(this).parent().parent()[0].innerText;
				}else{
					$('#searchCategoryText')[0].innerText = $(this).parent().parent()[0].innerText;
				}
			}
		});
		searchCategoryComboWin.hide();
	}
}

function selectAllCategory(obj){
	var selectCategoryArray  = Ext.query('[name=searchCategory]');
	Ext.each(selectCategoryArray, function(element){
		if(obj.checked){
			element.checked = true;
		}else{
			element.checked = false;
		}
	});
}

function goSearch(){
	if(!$('[name=searchKeyword]').val()) return;		
	var searchCategoryArray = '';
	$('[name=searchCategory]').each(function(){
		if($(this).val() && $(this)[0].checked){
			searchCategoryArray += ','+ $(this).val();
		}
	});
	Ext.getCmp('mainPanel').openIndexSearchTab(searchCategoryArray, $('[name=searchKeyword]').val());
}

function goTaskTab(opt){
	Ext.getCmp('mainPanel').openAndReloadTab({
        panelId : 'workspace08',
        text : 'My Task',
        url : '/plm/ext/wfm/workspace/listMyTask.do?opt='+opt
    });
}

function goProjectTaskTab(opt){
	Ext.getCmp('mainPanel').openAndReloadTab({
        panelId : 'workspace09',
        text : 'My Project',
        url : '/plm/ext/wfm/workspace/listMyProject.do?opt='+opt
    });
}

function goTodoTaskTab(opt){
	Ext.getCmp('mainPanel').openAndReloadTab({
        panelId : 'workspace07',
        text : 'My Todo',
        url : '/plm/ext/wfm/workspace/listMyTodo.do?opt='+opt
    });
}

