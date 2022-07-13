<%@ page language='java' contentType='text/html; charset=UTF-8'
    pageEncoding='UTF-8'%>
<%@ taglib prefix="ket" uri="ext.ket.shared.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE >
<html>
<head>
<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>
<meta http-equiv='X-UA-Compatible' content='IE=edge' />
<!-- EJS TreeGrid Start -->
<script src='/plm/portal/js/treegrid/GridE.js'></script>
<!-- EJS TreeGrid End -->
<title>Insert title here</title>

<script>
$(document).ready(function(){
	
    var grid = TreeGrid({
        Debug : 0,
        Data : {
            Url : '/plm/ext/cost/costInterfaceTreeGridData',
            Method : 'POST',
            Format : 'JSON',
            Param : {
                oid : "${oid}"
            },
            Timeout : 0
        },
        Export : {
            Url : "/plm/jsp/common/treegrid/ExcelExport.jsp",
            Data : "TGData",
            Param : {
                File : "KPLUS_Grid_list"
            }
        },
        Layout : {
            Data : {
                Cfg : {
                    id : "costErpInterfaceDetailGrid",
                    Style : gridStyle,
                    SuppressCfg : cookiesType,
                    IdPrefix : "T",
                    IdChars : "0123456789",
                    NumberId : 1,
                    Undo : 1,
                    Sorting : 0,
                    Editing : 0,
                    Deleting : 0,
                    ShowDeleted : 0,
                    Selecting : 0,
                    CopySelected : 1,
                    CopyPasteTree : 1,
                    Pasting : 0,
                    Dragging : 1,
                    Dropping : 1,
                    ExportFormat : 1,
                    ExportType : "Filtered,Hidden,Strings,Dates",
                    SaveSession : 1,
                    UsePrefix : 1,
                    Alternate : 0,
                    MainCol : "partType",
                    SuppressMessage : '1',
                    /* 
                    ConstHeight : '1',ConstWidth : '1',
                    MaxHeight : '1', MaxWidth : '1',
                    MinHeight : '100', MinTagHeight : '100'
                     */
                },
                Toolbar : { Visible : 0 }, //하단 도구 비표시 처리
                Panel : {
                    Visible : 0,
                    Move : 1,
                    Copy : 1,
                    CanHide : 0,
                    CopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow,AddChild,AddChildEnd",
                },
                
                LeftCols : [
						{Name : 'partType', Width:130, Align : 'left', CanSort : '0', CanEdit : '0'},
						{Name : 'Level', Width:35, Align : 'center', CanSort : '0', CanEdit : '0'},
						{Name : 'partNo', Width:75, Align : 'left', CanSort : '0', CanEdit : '0'},
						{Name : 'realPartNo', Width:75, Align : 'left', CanSort : '0', CanEdit : '0'},
						{Name : 'partName', Width:100, Align : 'left', CanSort : '0', CanEdit : '0'},
                        ],
                Cols : [
                        {Name : 'us', Width:20, Align : 'right', CanSort : '0', CanEdit : '0',Type : "Int", Format : "###,###"},
                        {Name : 'packUnitPriceOption', Width:55, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'rawMaterialCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'productLossExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'assyLossExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'materialCost', Width:80, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        
                        
                        {Name : 'indirectRndRate', Width:90, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "0.0%", Spanned : '1'},
                        {Name : 'indirectLabourRate', Width:90, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "0.0%", Spanned : '1'},
                        
                        {Name : 'rndExpr', Width:80, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'laborCostExpr', Width:80, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'indirectLabourRndCost', Width:80, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'laborCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'inDirectLaborCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'facReduceCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'machineDpcCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'facReducePrice', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'elecCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'tabaryuExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'moldMaintenance', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'etcReducePrice', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'directCost', Width:80, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'packUnitPriceSum', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'inDirectCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'inDirectCost2Expr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'manageMtrLogisExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'manageMtrdutieExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'mtrLtCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'mtrLtRateExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'payCostLtExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'payRateLtExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'payRateLtExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'indirectCostRnd', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'inDirectCost', Width:230, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'moldReducePrice', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'outUnitCostVal', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'apUnitCostVal', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'apUnitCostOption', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'corporateMarginCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'outUnitCost', Width:120, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'etcCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'coManageExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'defectCostExpr', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'salesManageCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'scrapSalesCost', Width:70, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                        {Name : 'productCostTotal', Width:240, Align : 'right', CanSort : '0', CanEdit : '0', Type : "Float", Format : "###,##0.0000"},
                       ],
                       
                Head : [{
                        Kind : "Topbar",
                        Cells : "ExpandAll,CollapseAll,Reload,Export,Print,Columns",
                        AddCopyMenu : "CopyRow,CopyRowBelow,CopyTree,CopyTreeBelow,AddRow,AddRowBelow",
                        AddChildCopyMenu : "AddChild,AddChildEnd",
                        Styles : 2,
                        Calculated : 1,
                        Link : 0,
                    },{
                        Kind : "Header",
                        id : "Header",
                        RowSpan : 0,
                        Visible : 0
                    },{
                        Kind : "Header",
                        id : "Header1",
                        RowSpan : 0,
                        CanDelete : '0', 
                        Align : 'Center',
                        
                        partType : '구분',
                        Level : 'Level',
                        partNo : '임시품번',
                        realPartNo : '품번',
                        partName : '품명',
                        us : 'us',
                        packUnitPriceOption : '(A)\n포장비\n합계',
                        rawMaterialCostExpr : '(B)\n원재료비',
                        productLossExpr : '(C)\n생산로스',
                        assyLossExpr : '(D)\n조립로스',
                        materialCost : '(E)\n재료비\n(A+B+C+D)', materialCostBackground : '#F5ECCE',
                        
                        indirectRndRate : '(F)\n간접인건비비율\nR&D',
                        indirectLabourRate : '(G)\n간접인건비비율\n노무비',
                        rndExpr : '(H)\nR&D비',
                        laborCostExpr : '(I)\n노무비',
                        indirectLabourRndCost : '(J)\n간접인건비\nR&D\n(H*F)',
                        
                        laborCost : '(K)\n직접\n인건비\n(I - (I*G))', laborCostBackground : '#F5ECCE',
                        inDirectLaborCost : '(L)\n간접\n인건비\n(I*G + J)', inDirectLaborCostBackground : '#F5ECCE',
                        facReduceCost : '(M)\n설비감가',
                        machineDpcCostExpr : '(N)\n기계감가',
                        facReducePrice : '(O)\n설비\n감가비\n(M+N)', facReducePriceBackground : '#F5ECCE',
                        elecCostExpr : '(P)\n전력비',
                        tabaryuExpr : '(Q)\n타발유',
                        moldMaintenance : '(R)\n금형\n유지비',
                        etcReducePrice : '(S)\n기타\n감가비',
                        directCost : "(T)\n직접경비\n(P+Q+R+S)", directCostBackground : '#F5ECCE',
                        packUnitPriceSum : "(U)\n개별포장비\n합계",
                        inDirectCostExpr : "(V)\n간접경비",
                        inDirectCost2Expr : "(W)\n간접경비2",
                        manageMtrLogisExpr : "(X)\n원재료\n물류비",
                        manageMtrdutieExpr : "(Y)\n원재료\n관세",
                        mtrLtCostExpr : "(Z)\n공정\n물류비",
                        mtrLtRateExpr : "(AA)\n공정\n관세",
                        payCostLtExpr : "(BB)\n납입\n물류비",
                        payRateLtExpr : "(CC)\n납입관세",
                        indirectCostRnd : "(DD)\n간접경비\nR&D\n(H-J)",
                        inDirectCost : "(EE)\n간접경비\n(U+V+W+X+Y+Z+AA+BB+CC+DD)", inDirectCostBackground : '#F5ECCE',
                        moldReducePrice : "(FF)\n금형\n감가비", moldReducePriceBackground : '#F5ECCE',
                        outUnitCostVal : "(GG)\n외주단가",
                        apUnitCostVal : "(HH)\n후도금\n단가",
                        apUnitCostOption : "(II)\n후도금비\n(옵션)",
                        corporateMarginCostExpr : "(JJ)\n법인간\n마진비용",
                        outUnitCost : "(KK)\n외주\n가공비\n(GG+HH+II+JJ)", outUnitCostBackground : '#F5ECCE',
                        etcCost : "(LL)\n기타원가", etcCostBackground : '#F5ECCE',
                        coManageExpr : "(MM)\n일반\n관리비",
                        defectCostExpr : "(NN)\n불량비율",
                        salesManageCost : "(OO)\n판관비\n(MM+NN)", salesManageCostBackground : '#F5ECCE',
                        scrapSalesCost : "(PP)\n스크랩\n매출", scrapSalesCostBackground : '#F5ECCE',
                        productCostTotal : "총원가\n(E+K+L+O+T+EE+FF+KK+LL+OO-PP)", productCostTotalBackground : '#FE9A2E',
                    }
                ],
                Body : [ { Pos : 0 }],
                Lang : {
                    MenuCopy : {
                        CopyRow       : '위에 아이템 복사'
                        ,CopyRowBelow  : '아래에 아이템 복사'
                        ,CopyTree      : '위에 Tree 복사'
                        ,CopyTreeBelow : '아래에 Tree 복사'
                        ,AddRow        : '위에 신규 아이템 추가'
                        ,AddRowBelow   : '아래에 신규 아이템 추가'
                        ,AddChild      : '첫번째 하위 아이템 추가'
                        ,AddChildEnd   : '마지막 하위 아이템 추가'
                        <%-- 
                        CopyRow       : '<%=messageService.getString("e3ps.message.ket_message", "02230") %>'       //위에 아이템 복사
                        CopyRowBelow  : '<%=messageService.getString("e3ps.message.ket_message", "02070") %>'       //아래에 아이템 복사
                        CopyTree      : '<%=messageService.getString("e3ps.message.ket_message", "02231") %>'       //위에 Tree 복사
                        CopyTreeBelow : '<%=messageService.getString("e3ps.message.ket_message", "02071") %>'       //아래에 Tree 복사
                        AddRow        : '<%=messageService.getString("e3ps.message.ket_message", "02232") %>'       //위에 신규 아이템 추가
                        AddRowBelow   : '<%=messageService.getString("e3ps.message.ket_message", "02072") %>'       //아래에 신규 아이템 추가
                        AddChild      : '<%=messageService.getString("e3ps.message.ket_message", "02801") %>'       //첫번째 하위 아이템 추가
                        AddChildEnd   : '<%=messageService.getString("e3ps.message.ket_message", "01356") %>'       //마지막 하위 아이템 추가
                         --%>
                    },
                    Alert : {
                        DelRow : "%d 아이템을 삭제하시겠습니까?"        //아이템을 삭제하시겠습니까?
                        <%-- DelRow : "%d <%=messageService.getString("e3ps.message.ket_message", "00494") %>"      //아이템을 삭제하시겠습니까? --%>
                    }
                }
            }
        }
    }, "treeGrid");
    
    treeGridResize("#costErpInterfaceDetailGrid","#treeGrid",true);//크롬에서 그리드 리사이즈 하기 위한 코드 param1 : grid Id, param2 : divId
    
    Grids.OnSave = function(G, row, autoupdate) {
    }
    
    
   
    Grids.OnReload = function(G) {
        //alert();
    }
    
    Grids.OnAfterSave = function ( G, result, autoupdate){
    }
    
    
    Grids.OnRenderPageFinish = function(){
        var G = Grids[0];
        
        var rows = G.Rows;
        var rowIds = Object.keys(rows);
        
        for(var i = 0; i < rowIds.length; i++){
            
            var row = rows[rowIds[i]];
            
            gridColChange(G, row, "materialCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "laborCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "inDirectLaborCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "facReducePrice", '#5858FA',2,row.Kind);
            gridColChange(G, row, "directCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "inDirectCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "moldReducePrice", '#5858FA',2,row.Kind);
            gridColChange(G, row, "outUnitCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "etcCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "salesManageCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "scrapSalesCost", '#5858FA',2,row.Kind);
            gridColChange(G, row, "productCostTotal", '#5858FA',2,row.Kind);
        }
    }
    
    gridColChange = function(G, tRow, attr, color, size, kind){
    	if(kind == 'Header'){
    		G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font size="+size+ ">", 1);	
    	}else{
    		G.SetAttribute(tRow, attr, "HtmlPrefix", "<b><font color="+color+ ">", 1);
    	}
        
        G.SetAttribute(tRow, attr, "Postfix", "</font></b>", 1);
        G.SetAttribute(tRow, attr, "Changed", 0, 1);
    }

    $('body').addClass('popup-background02 popup-space');

});

</script>
</head>
<body>
    <div class="contents-wrap">
        <div class="popup-title">
            <img src="/plm/portal/images/icon_3.png" />개발원가 컨버팅 내역 상세
        </div>
        <table border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
                <td class="space10"></td>
            </tr>
            <tr>
                <td class="space2"></td>
            </tr>
        </table>
        <div id='treeGrid' style='width: 100%;'></div>
    </div>
</body>
</html>