<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ page import="e3ps.cost.util.StringUtil"%>
<%@ page import="java.util.ArrayList, java.util.Hashtable, java.util.Calendar"%>
<%@ page import="java.net.URLDecoder"%>
<%

    int k = 0;
    k =  Integer.parseInt(StringUtil.checkNullZero(request.getParameter("k")));

    String maker_name = "";
    String page_name	= StringUtil.checkNull(request.getParameter("page_name"))             ;
    String search_first	= StringUtil.checkNull(request.getParameter("search_first"))             ;

    String p_pro_type      = StringUtil.checkNull(request.getParameter("p_pro_type"))         ;

    String make		= StringUtil.checkNull(request.getParameter("make")) ;
    String unitcost		= StringUtil.checkNull(request.getParameter("unitcost"))              ;
    String unitcost_txt = StringUtil.checkNull(request.getParameter("unitcost_txt"))          ;
    unitcost_txt = URLDecoder.decode(URLDecoder.decode(unitcost_txt, "utf-8"),"utf-8");
    String unitcost_2	= StringUtil.checkNull(request.getParameter("unitcost_2"))            ;
    String unitcost_3	= StringUtil.checkNull(request.getParameter("unitcost_3"))            ;
    String make_type	= StringUtil.checkNull(request.getParameter("make_type"))             ;

    //unitcost = new String(unitcost.getBytes("8859_1"), "utf-8");
    //unitcost_2 = new String(unitcost_2.getBytes("8859_1"), "utf-8");
    //unitcost_3 = new String(unitcost_3.getBytes("8859_1"), "utf-8");



    //비철
    String maker_1_txt	= StringUtil.checkNull(request.getParameter("maker_1_txt"))           ;
    String grade_1_txt 	= StringUtil.checkNull(request.getParameter("grade_1_txt"))           ;

    String plating 		= StringUtil.checkNull(request.getParameter("plating"))               ;
    String m_maker		= StringUtil.checkNull(request.getParameter("m_maker"))               ;

    //m_maker = new String(m_maker.getBytes("8859_1"),"euc-kr");
    m_maker = URLDecoder.decode(URLDecoder.decode(m_maker, "utf-8"),"utf-8");



    String meterial 		= StringUtil.checkNull(request.getParameter("meterial"))          ;

    String order_con	= StringUtil.checkNull(request.getParameter("order_con"))             ;
    double h_weight = 0;
    double h_scrap  = 0;
    double t_weight = 0;
    double t_size	= 0;
    double w_size	= 0;
    double p_size 	= 0;


    if(!StringUtil.checkNull(request.getParameter("h_weight")).equals("")){
        h_weight = Double.valueOf(StringUtil.checkNull(request.getParameter("h_weight")));
    }

    if(!StringUtil.checkNull(request.getParameter("h_scrap")).equals("")){
        h_scrap = Double.valueOf(StringUtil.checkNull(request.getParameter("h_scrap")));
    }

    if(!StringUtil.checkNull(request.getParameter("t_weight")).equals("")){
        t_weight = Double.valueOf(StringUtil.checkNull(request.getParameter("t_weight")));
    }

    if(!StringUtil.checkNull(request.getParameter("t_size")).equals("")){
        t_size = Double.valueOf(StringUtil.checkNull(request.getParameter("t_size")));
    }

    if(!StringUtil.checkNull(request.getParameter("w_size")).equals("")){
        w_size = Double.valueOf(StringUtil.checkNull(request.getParameter("w_size")));
    }

    if(!StringUtil.checkNull(request.getParameter("p_size")).equals("")){
        p_size = Double.valueOf(StringUtil.checkNull(request.getParameter("p_size")));
    }



     //수지
    String maker_2			= StringUtil.checkNull(request.getParameter("maker_2"))       ;

    //maker_2 = new String(maker_2.getBytes("8859_1"),"utf-8");

    String maker_txt		= StringUtil.checkNull(request.getParameter("maker_txt"))     ;
    String grade_name_txt 		= StringUtil.checkNull(request.getParameter("grade_name_txt"));
    String material_name		= StringUtil.checkNull(request.getParameter("material_name")) ;

    String recycle			= StringUtil.checkNull(request.getParameter("recycle"))	;
    String grade_name  		= StringUtil.checkNull(request.getParameter("grade_name"))    ;
    String grade_color  		= StringUtil.checkNull(request.getParameter("grade_color"))   ;

    if(search_first.equals("first")){
        maker_2 = m_maker;
        material_name = meterial;
    }

    double h_weight_2	= 0;
    double h_scrap_2	= 0;
    double t_weight_2	= 0;

    if(request.getParameter("h_weight_2") != null){
        h_weight_2 = Double.valueOf(StringUtil.checkNull(request.getParameter("h_weight_2")));
    }
    if(request.getParameter("h_scrap_2") != null){
        h_scrap_2 = Double.valueOf(StringUtil.checkNull(request.getParameter("h_scrap_2")));
    }
    if(request.getParameter("t_weight_2") != null){
        t_weight_2 = Double.valueOf(StringUtil.checkNull(request.getParameter("t_weight_2")));
    }

    //벤도리아
    String meterial_4 	= StringUtil.checkNull(request.getParameter("meterial_4"))	        ;
    String meterial_4_c = StringUtil.checkNull(request.getParameter("meterial_4_c"))	        ;
    String unitcost_4	= StringUtil.checkNull(request.getParameter("unitcost_4"))            ;
    //unitcost_4 = new String(unitcost_4.getBytes("8859_1"), "utf-8");
    String p_m_mone = "";
    String p_order_con = "";
    String order_con_2 = "";
    String m_mone_2 = "";
    String m_mone = "";
    String type_2 = "";
    String p_type_2 = "";
    String p_make = "";
    String p_upload_data = "";
    String m_grade_name = "";
    String plating_cost = "";
    String sul_cost = "";


    if(p_pro_type.equals("TML") || p_pro_type.equals("TML-조립") || p_pro_type.equals("조립")){
        p_make = "make_1";
        p_upload_data = "yes";
    }else if(p_pro_type.equals("HSG") || p_pro_type.equals("HSG-Box") || p_pro_type.equals("Insert")){
        p_make = "make_2";
        p_upload_data = "yes";
    }else if(p_pro_type.equals("구매") || p_pro_type.equals("부재료")){
        p_make = "make_3";
        p_upload_data = "yes";
    }else{
        p_upload_data = "no";
    }


    if(p_upload_data.equals("yes")){
        make = p_make;
        meterial_4 	= meterial;
        meterial_4_c 	= meterial;
        unitcost_4	= unitcost_txt;

        if(p_make.equals("make_1")){
            m_maker		= m_maker;
            meterial 	= meterial;
            unitcost	= unitcost_txt;
        }else if(p_make.equals("make_2")){
            maker_2		= m_maker;

               meterial 	= meterial.trim();

               grade_name  	= grade_name.trim();
            m_grade_name  	= "\\+"+grade_name;
            m_grade_name  	= m_grade_name.replaceAll(" ","\\+");

            material_name 	= meterial.replaceAll(" ","\\+");
            material_name 	= material_name.replaceAll(m_grade_name,"");
            material_name 	= material_name.trim();

            maker_txt	    = m_maker;
            grade_name_txt  = meterial;
               unitcost_2	    = unitcost_txt;
            recycle		    = recycle;
            grade_name  	= grade_name.trim();
            grade_color  	= grade_color.trim();
            h_weight_2	= h_weight;
            h_scrap_2	= h_scrap;
            t_weight_2	= t_weight;
        }else{
            unitcost_3	= unitcost_txt;
        }
    }else{
        if(make.equals("make_1") ){
            if(!unitcost.equals("")){
                unitcost = unitcost;
            }else{
                unitcost = "표준단가";
            }
        }else if(make.equals("make_2")){
            if(!unitcost_2.equals("")){
                unitcost_2 = unitcost_2;
            }else{
                unitcost_2 = "표준단가";
            }
        }else{
            if(!unitcost_3.equals("")){
                unitcost_3 = unitcost_3;
            }else{
                unitcost_3 = "표준단가";
            }
        }
    }

    if(!unitcost_4.equals("")){
        unitcost_4 = unitcost_4;
    }else{
        unitcost_4 = "표준단가";
    }

    String Search_ok = StringUtil.checkNull(request.getParameter("Search_ok"));
    ArrayList SearchPress = (ArrayList)request.getAttribute("SearchPress");
    ArrayList SearchBandolier = (ArrayList)request.getAttribute("SearchBandolier");
    ArrayList SearchMaker_2 = (ArrayList)request.getAttribute("SearchMaker_2");
    ArrayList SearchMaterial_name = (ArrayList)request.getAttribute("SearchMaterial_name");
    ArrayList SearchGrade_name = (ArrayList)request.getAttribute("SearchGrade_name");
    ArrayList SearchGrade_color = (ArrayList)request.getAttribute("SearchGrade_color");
    ArrayList SearchMaker_name = (ArrayList)request.getAttribute("SearchMaker_name");
    ArrayList SearchGrade_name2 = (ArrayList)request.getAttribute("SearchGrade_name2");
    ArrayList SearchGrade_name3 = (ArrayList)request.getAttribute("SearchGrade_name3");
    ArrayList SearchGrade_name4 = (ArrayList)request.getAttribute("SearchGrade_name4");

    Hashtable PressMap = null;
    Hashtable BandolierMap = null;
    Hashtable Maker_2Map = null;
    Hashtable Material_nameMap = null;
    Hashtable Grade_nameMap = null;
    Hashtable SearchGradeMap = null;
    Hashtable SearchMaker_nameMap = null;
    Hashtable SearchGrade_name2Map = null;
    Hashtable SearchGrade_name3Map = null;
    Hashtable SearchGrade_name4Map = null;

    double s_gravity = 0;
    double s_gravity_1 = 0;
    double Carrier_cost = 0;
    double Carrier_weight = 0;
    String Carrier_grade = "";
    double Wire_cost = 0;

    if(SearchPress!= null){
        for(int i=0; i<SearchPress.size(); i++){
            PressMap = (Hashtable)SearchPress.get(i);
            String s_1 = (String)PressMap.get("s_gravity") ;
            String s_2 = (String)PressMap.get("s_gravity_1") ;
            s_gravity = Double.valueOf(s_1).doubleValue();
            s_gravity_1 = Double.valueOf(s_2).doubleValue();
        }
    }

    if(make.equals("make_1")){ //비철의 경우 원재료만 변경시 총중량 및 스크랩중량 재계산(개발자들의 요구사항 적용-2012.02월~03월 진행된 인터뷰 기준)
        if(!String.valueOf(t_size).equals("")  && !String.valueOf(w_size).equals("") && !String.valueOf(p_size).equals("")){

            t_weight = ((t_size *w_size*p_size)*s_gravity)/1000;
            h_scrap = t_weight - h_weight;
        }
    }

    if(SearchBandolier!= null){
        for(int i=0; i<SearchBandolier.size(); i++){
            BandolierMap = (Hashtable)SearchBandolier.get(i);
            String c_1 = (String)BandolierMap.get("Carrier_cost") ;
            String c_2 = (String)BandolierMap.get("Carrier_weight") ;
            Carrier_grade = (String)BandolierMap.get("Carrier_grade") ;
            String c_4 = (String)BandolierMap.get("Wire_cost") ;
            Carrier_cost = Double.valueOf(c_1).doubleValue();
            Carrier_weight = Double.valueOf(c_2).doubleValue();
            Wire_cost = Double.valueOf(c_4).doubleValue();
        }
    }

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>한국단자 종합 개발 시스템 </title>
</head>
<style type="text/css">
<!--
.style1 {font-size: 12px;text-align:left; color:#000000; font-weight: bold;}
.style2 {font-size: 12px;text-align:center}
.style3 {border-style:dotted; border-width:1px;}
.style4 {font-size: 12px;text-align:left; color:#D3235C}
.style5 {font-size: 12px;text-align:left;}
  #bg_color {background:#E6E6E6}
#menu1 {display:none;}
#menu2 {display:block;}
#menu3 {display:none;}
#menu4 {display:none;}

-->
</style>
<script language="javascript">
/**********************************************
총중량 - 비철 ((t*w*p)*8.9)/1000
toFixed() 소수점자리지정
**********************************************/

function t_weight_sum()
{

var size_t
var size_w
var size_p
var weight_sum

if (document.grade_input.t_size.value == "" )
  {
    size_t = 0;
  }
else
  {
    size_t = parseFloat(document.grade_input.t_size.value,10);
  }

if (document.grade_input.w_size.value == "" )
  {
       size_w = 0;
  }
else
  {
       size_w = parseFloat(document.grade_input.w_size.value,10);
  }
if (document.grade_input.p_size.value == "" )
  {
       size_p = 0;
  }
else
  {
       size_p = parseFloat(document.grade_input.p_size.value,10);
  }


weight_sum = ((size_t * size_w * size_p) * <%=s_gravity%>) / 1000;

document.grade_input.t_weight.value = weight_sum.toFixed (3);

}
/**********************************************
총중량 - 비철
**********************************************/

function weight_sum()
{

var weight_1
var weight_2
var s_weight

if (document.grade_input.h_weight.value == "" )
  {
    weight_1 = 0;
  }
else
  {
    weight_1 = parseFloat(document.grade_input.h_weight.value,10);
  }

if (document.grade_input.t_weight.value == "" )
  {
       weight_2 = 0;
  }
else
  {
       weight_2 = parseFloat(document.grade_input.t_weight.value,10);
  }


s_weight = weight_2 - weight_1


document.grade_input.h_scrap.value = s_weight.toFixed (3);

}
/**********************************************
총중량 - 수지
**********************************************/

function weight_sum_2()
{

var weight_1
var weight_2
var weight_sum
alert(document.grade_input.h_weight_2);
if (document.grade_input.h_weight_2.value == "" )
  {
    weight_1 = 0;
  }
else
  {
    weight_1 = parseFloat(document.grade_input.h_weight_2.value,10);
  }

if (document.grade_input.h_scrap_2.value == "" )
  {
       weight_2 = 0;
  }
else
  {
       weight_2 = parseFloat(document.grade_input.h_scrap_2.value,10);
  }


weight_sum = weight_1 + weight_2


document.grade_input.t_weight_2.value = weight_sum.toFixed (3);

}
/**********************************************
비철/수지 구분
**********************************************/
function maker_select(obj)
 {
    // document.grade_input.action = "./cost_grade.asp?aa=0&T=0"
       document.grade_input.submit();
 }

 /**********************************************
비철/수지 구분
**********************************************/
function meterial_select(obj)
 {
       document.grade_input.submit();

 }

/**********************************************
직접 입력선택1
**********************************************/
function chk_mk()
 {
    if (document.grade_input.maker_chk.checked == true)
    {
        document.grade_input.maker_txt.disabled = false;
           document.grade_input.maker_txt.style.background = "#FFFFFF";
           document.grade_input.maker_2.disabled = true;
        document.grade_input.unitcost_2.style.background = "#FFFFFF";
        document.grade_input.unitcost_2.disabled = false;
        //document.grade_input.unitcost_2.value = "";
    }
    else
    {
        document.grade_input.maker_txt.disabled = true;
           document.grade_input.maker_txt.style.background = "#E6E6E6";
           document.grade_input.maker_2.disabled = false;
        document.grade_input.unitcost_2.style.background = "#E6E6E6";
        document.grade_input.unitcost_2.value = "표준단가";
        document.grade_input.unitcost_2.disabled = true ;
    }

 }
/**********************************************
직접 입력선택1_1
**********************************************/
function chk_mk_1()
 {
    if (document.grade_input.maker_chk_1.checked == true)
    {
        document.grade_input.maker_1_txt.disabled = false;
           document.grade_input.maker_1_txt.style.background = "#FFFFFF";
           document.grade_input.m_maker.disabled = true;
        document.grade_input.unitcost.disabled = false;
        document.grade_input.unitcost.style.background = "#FFFFFF";
        //document.grade_input.unitcost.value = "";
    }
    else
    {
        document.grade_input.maker_1_txt.disabled = true;
           document.grade_input.maker_1_txt.style.background = "#E6E6E6";
           document.grade_input.m_maker.disabled = false;
        document.grade_input.unitcost.style.background = "#E6E6E6";
        document.grade_input.unitcost.disabled = true;
        document.grade_input.unitcost.value = "표준단가";
    }

 }
/**********************************************
직접 입력선택2
**********************************************/
function chk_na()
 {
    if (document.grade_input.name_chk.checked == true)
    {
        document.grade_input.grade_name_txt.disabled = false;
        document.grade_input.m_mone_2.disabled = false;
        document.grade_input.unitcost_2.disabled = false;
           document.grade_input.grade_name_txt.style.background = "#FFFFFF";
           document.grade_input.m_mone_2.style.background = "#FFFFFF";
        document.grade_input.unitcost_2.style.background = "#FFFFFF";
           document.grade_input.grade_name.disabled = true;
           //document.grade_input.unitcost_2.value = "";
        document.grade_input.order_con_2.disabled = false;
        document.grade_input.order_con_2.style.background = "#FFFFFF";
    }
    else
    {
        document.grade_input.grade_name_txt.disabled = true;
           document.grade_input.grade_name_txt.style.background = "#E6E6E6";
           document.grade_input.m_mone_2.style.background = "#E6E6E6";
        document.grade_input.unitcost_2.style.background = "#E6E6E6";
           document.grade_input.grade_name.disabled = false;
           document.grade_input.m_mone_2.disabled = true;
        document.grade_input.unitcost_2.disabled = true;
        document.grade_input.unitcost_2.value = "표준단가";
        document.grade_input.order_con_2.disabled = true;
        document.grade_input.order_con_2.style.background = "#E6E6E6";

    }

 }

/**********************************************
직접 입력선택2_1
**********************************************/
function chk_na_1()
 {
    if (document.grade_input.name_chk_1.checked == true)
    {
        document.grade_input.grade_1_txt.disabled = false;
           document.grade_input.grade_1_txt.style.background = "#FFFFFF";
           document.grade_input.m_mone.style.background = "#FFFFFF";
        document.grade_input.unitcost.style.background = "#FFFFFF";

           document.grade_input.m_mone.disabled = false;
        document.grade_input.unitcost.disabled = false;
           document.grade_input.meterial.disabled = true;
        //document.grade_input.unitcost.value = "";

    }
    else
    {
        document.grade_input.grade_1_txt.disabled = true;
        document.grade_input.meterial.disabled = false;
           document.grade_input.m_mone.disabled = true ;
        document.grade_input.unitcost.disabled = true ;
           document.grade_input.grade_1_txt.style.background = "#E6E6E6";
           document.grade_input.m_mone.style.background = "#E6E6E6";
        document.grade_input.unitcost.style.background = "#E6E6E6";
        document.grade_input.unitcost.value = "표준단가";
    }

 }

/**********************************************
비철/수지 구분
**********************************************/

    function make_ab(a)
    {
        var form = document.forms[0];
         form.cmd.value = "popGrade";
         form.action =  '/plm/servlet/e3ps/costComServlet';

        if(form.Search_ok.value != 'no'){
            form.submit();
        }

        document.grade_input.param_search_first.value = "";
        self.focus();

         if (document.grade_input.make[0].checked == true)
          {
             document.grade_input.all.menu4.style.display = "none";
             document.grade_input.all.menu3.style.display = "none";
             document.grade_input.all.menu2.style.display = "block";
             document.grade_input.all.menu1.style.display = "none";
                 document.grade_input.m_maker.disabled = false;
                 document.grade_input.plating.disabled = false;
                 document.grade_input.meterial.disabled = false;
                 document.grade_input.t_size.disabled = false;
              document.grade_input.w_size.disabled = false;
              document.grade_input.p_size.disabled = false;
                 document.grade_input.order_con.disabled = false;
                 document.grade_input.h_weight.disabled = false;
                 document.grade_input.h_scrap.disabled = false;
                 //document.grade_input.t_weight.disabled = false;
                 document.grade_input.grade_1_txt.disabled = false;
                 document.grade_input.maker_1_txt.disabled = false;
              document.grade_input.maker_chk_1.disabled = false;
             document.grade_input.name_chk_1.disabled = false;
             document.grade_input.m_mone.disabled = true;
                 document.grade_input.unitcost.disabled = true;

             document.grade_input.maker_2.disabled = true;
                 document.grade_input.grade_name.disabled = true;
              document.grade_input.maker_chk.disabled = true;
             document.grade_input.name_chk.disabled = true;
              document.grade_input.m_mone_2.disabled = true;
                 document.grade_input.unitcost_2.disabled = true;
                 document.grade_input.order_con_2.disabled = true;
                 document.grade_input.h_weight_2.disabled = true;
                 document.grade_input.h_scrap_2.disabled = true;
                 document.grade_input.t_weight_2.disabled = true;
                 document.grade_input.recycle.disabled = true;

              document.grade_input.t_size.style.background = "#FFFFFF";
                 document.grade_input.w_size.style.background = "#FFFFFF";
                 document.grade_input.p_size.style.background = "#FFFFFF";
                 document.grade_input.plating.style.background = "#FFFFFF";
                 document.grade_input.order_con.style.background = "#FFFFFF";
                 document.grade_input.h_weight.style.background = "#FFFFFF";
                 document.grade_input.h_scrap.style.background = "#FFFFFF";
                 //document.grade_input.t_weight.style.background = "#FFFFFF";

                 document.grade_input.unitcost.style.background = "#E6E6E6";
                 document.grade_input.unitcost_2.style.background = "#E6E6E6";
                 document.grade_input.order_con_2.style.background = "#E6E6E6";
                 document.grade_input.h_weight_2.style.background = "#E6E6E6";
                 document.grade_input.h_scrap_2.style.background = "#E6E6E6";
                 document.grade_input.t_weight_2.style.background = "#E6E6E6";
                 document.grade_input.recycle.style.background = "#E6E6E6";

          }
        else if (document.grade_input.make[1].checked == true)

        {
            document.grade_input.all.menu4.style.display = "none";
            document.grade_input.all.menu3.style.display = "none";
            document.grade_input.all.menu2.style.display = "none";
            document.grade_input.all.menu1.style.display = "block";
                 document.grade_input.m_maker.disabled = true;
                 document.grade_input.plating.disabled = true;
                 document.grade_input.meterial.disabled = true;
                 document.grade_input.t_size.disabled = true;
              document.grade_input.w_size.disabled = true;
              document.grade_input.p_size.disabled = true;
                 document.grade_input.m_mone.disabled = true;
                 document.grade_input.unitcost.disabled = true;
                 document.grade_input.order_con.disabled = true;
                 document.grade_input.h_weight.disabled = true;
                 document.grade_input.h_scrap.disabled = true;
                 document.grade_input.t_weight.disabled = true;
                 document.grade_input.grade_1_txt.disabled = true;
                 document.grade_input.maker_1_txt.disabled = true;
              document.grade_input.maker_chk_1.disabled = true;
           document.grade_input.name_chk_1.disabled = true;
           document.grade_input.m_mone_2.disabled = true;
                 document.grade_input.unitcost_2.disabled = true;

                 document.grade_input.maker_2.disabled = false;
                 document.grade_input.grade_name.disabled = false;
              document.grade_input.maker_chk.disabled = false;
             document.grade_input.name_chk.disabled = false;
                 document.grade_input.order_con_2.disabled = false;
                 document.grade_input.h_weight_2.disabled = false;
                 document.grade_input.h_scrap_2.disabled = false;
                 document.grade_input.t_weight_2.disabled = false;
                 document.grade_input.recycle.disabled = false;
                 document.grade_input.order_con_2.disabled = true;
            document.grade_input.order_con_2.style.background = "#E6E6E6";

              document.grade_input.t_size.style.background = "#E6E6E6";
                 document.grade_input.w_size.style.background = "#E6E6E6";
                 document.grade_input.p_size.style.background = "#E6E6E6";
                 document.grade_input.plating.style.background = "#E6E6E6";
                 document.grade_input.unitcost.style.background = "#E6E6E6";
                 document.grade_input.order_con.style.background = "#E6E6E6";
                 document.grade_input.h_weight.style.background = "#E6E6E6";
                 document.grade_input.h_scrap.style.background = "#E6E6E6";
                 //document.grade_input.t_weight.style.background = "#E6E6E6";

                 document.grade_input.unitcost_2.style.background = "#E6E6E6";
                 document.grade_input.order_con_2.style.background = "#FFFFFF";
                 document.grade_input.h_weight_2.style.background = "#FFFFFF";
                 document.grade_input.h_scrap_2.style.background = "#FFFFFF";
                 document.grade_input.t_weight_2.style.background = "#FFFFFF";
                 document.grade_input.recycle.style.background = "#FFFFFF";

        }
        else if (document.grade_input.make[2].checked == true)
         {
            document.grade_input.all.menu4.style.display = "none";
            document.grade_input.all.menu3.style.display = "block";
            document.grade_input.all.menu2.style.display = "none";
            document.grade_input.all.menu1.style.display = "none";
        }
        else
         {
            document.grade_input.all.menu4.style.display = "block";
            document.grade_input.all.menu3.style.display = "none";
            document.grade_input.all.menu2.style.display = "none";
            document.grade_input.all.menu1.style.display = "none";
            <%if(make_type.equals("make_out")){%>
            document.grade_input.make_type[1].checked = true;
              document.grade_input.meterial_4.disabled = true;
              document.grade_input.unitcost_4.disabled = false;
              document.grade_input.m_mone_4.disabled = false;
              document.grade_input.sul_cost.disabled = true;
              document.grade_input.sul_cost.style.background = "#E6E6E6";

              document.grade_input.m_mone_4.style.background = "#FFFFFF";
              document.grade_input.unitcost_4.value = "";
              document.grade_input.unitcost_4.style.background = "#FFFFFF";
              <%}else{%>
              document.grade_input.make_type[0].checked = true;
              document.grade_input.meterial_4_c.disabled = true;
              //document.grade_input.sul_cost.disabled = false;
              document.grade_input.unitcost_4.disabled = true;
              document.grade_input.m_mone_4.disabled = true;
              document.grade_input.unitcost_4.style.background = "#E6E6E6";
              document.grade_input.m_mone_4.style.background = "#E6E6E6";
              <%}%>

              document.grade_input.t_weight_4.disabled = true;
              document.grade_input.t_weight_4.style.background = "#E6E6E6";
        }

     }

    /**********************************************
     비철/수지 구분
     **********************************************/
     function sub_ok()
      {
      if (document.grade_input.reset_chk.checked == false)
      {
        if (document.grade_input.make[0].checked == true)
         {
            if (document.grade_input.maker_chk_1.checked == true)
             {
                    if (document.grade_input.maker_1_txt.value == "" )
                       {
                           alert("업체명을 입력해주세요");
                           document.grade_input.maker_1_txt.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("m_maker",<%=k%>,document.grade_input.maker_1_txt.value);
                            }
            }
            else
            {
                    if (document.grade_input.m_maker[0].selected == true)
                       {
                           alert("업체명을 선택해주세요");
                           document.grade_input.m_maker.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("m_maker",<%=k%>, document.grade_input.m_maker.value);
                            }
            }
            if (document.grade_input.name_chk_1.checked == true)
             {

                 if (document.grade_input.grade_1_txt.value == "")
                      {
                          alert("재질명을 입력해주세요");
                          document.grade_input.grade_1_txt.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>, document.grade_input.grade_1_txt.value);
                            }
            }
            else
            {
                  if (document.grade_input.meterial[0].selected == true)
                       {
                           alert("재질명을 선택해주세요");
                           document.grade_input.meterial.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>, document.grade_input.meterial.value);
                            }
            }

              if (document.grade_input.t_size.value == "")
               {
                   alert("두께를 입력해주세요");
                   document.grade_input.t_size.focus();
                        return false;
              }
              if (document.grade_input.w_size.value == "")
               {
                   alert("폭을 입력해주세요");
                   document.grade_input.w_size.focus();
                       return false;
              }
              if (document.grade_input.p_size.value == "")
               {
                   alert("피치를 입력해주세요");
                   document.grade_input.p_size.focus();
                        return false;
              }
              if (document.grade_input.h_weight.value == "")
               {
                   alert("부품중량을 입력해주세요");
                   document.grade_input.h_weight.focus();
                        return false;
              }

               <%if(page_name.equals("edit")){%>
               opener.document.WiseGrid3.SetCellValue("unitcost_txt",<%=k%>,document.grade_input.unitcost.value);
               <%}%>
               opener.document.WiseGrid3.SetCellValue("make",<%=k%>, document.grade_input.make[0].value);
               opener.document.WiseGrid3.SetCellValue("plating",<%=k%>, document.grade_input.plating.value);
               opener.document.WiseGrid3.SetCellValue("t_size",<%=k%>, document.grade_input.t_size.value);
               opener.document.WiseGrid3.SetCellValue("w_size",<%=k%>, document.grade_input.w_size.value);
               opener.document.WiseGrid3.SetCellValue("p_size",<%=k%>, document.grade_input.p_size.value);
               opener.document.WiseGrid3.SetCellValue("m_mone",<%=k%>, document.grade_input.m_mone.value);
               opener.document.WiseGrid3.SetCellValue("unitcost",<%=k%>, document.grade_input.unitcost.value);
               opener.document.WiseGrid3.SetCellValue("order_con",<%=k%>, document.grade_input.order_con.value);
               opener.document.WiseGrid3.SetCellValue("h_weight",<%=k%>, document.grade_input.h_weight.value);
               opener.document.WiseGrid3.SetCellValue("h_scrap",<%=k%>, document.grade_input.h_scrap.value);
               opener.document.WiseGrid3.SetCellValue("t_weight",<%=k%>, document.grade_input.t_weight.value);
               opener.document.WiseGrid3.SetCellValue("recycle",<%=k%>, document.grade_input.recycle.value);

             self.close();
        }
        else if (document.grade_input.make[1].checked == true)
        {

            if (document.grade_input.maker_chk.checked == true)
             {
                    if (document.grade_input.maker_txt.value == "" )
                       {
                           alert("업체명을 입력해주세요");
                           document.grade_input.maker_txt.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("m_maker",<%=k%>,document.grade_input.maker_txt.value);
                            }
            }
            else
            {
                    if (document.grade_input.maker_2[0].selected == true)
                       {
                           alert("업체명을 선택해주세요");
                           document.grade_input.maker_2.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("m_maker",<%=k%>,document.grade_input.maker_2.value);
                            }
            }
            if (document.grade_input.name_chk.checked == true)
             {
                if (document.grade_input.grade_name_txt.value == "")
                     {
                         alert("Grade명을 입력해주세요");
                         document.grade_input.grade_name_txt.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>,document.grade_input.meterial.value + " " + document.grade_input.grade_name_txt.value );
                            }
            }
            else
            {
                  if (document.grade_input.grade_name[0].selected == true)
                       {
                           alert("Grade명을 선택해주세요");
                           document.grade_input.grade_name.focus();
                                return false;
                            }
                        else
                            {
                                opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>,document.grade_input.material_name.value + " " + document.grade_input.grade_name.value );
                            }
            }
            if (document.grade_input.h_weight_2.value == "")
               {
                   alert("부품중량을 입력해주세요");
                   document.grade_input.h_weight_2.focus();
                        return false;
               }
               <%if(page_name.equals("edit")){%>
               opener.document.WiseGrid3.SetCellValue("unitcost_txt",<%=k%>, document.grade_input.unitcost_2.value);
               <%}%>
               opener.document.WiseGrid3.SetCellValue("make",<%=k%>, document.grade_input.make[1].value);
               opener.document.WiseGrid3.SetCellValue("plating",<%=k%>, document.grade_input.grade_color.value);
               opener.document.WiseGrid3.SetCellValue("m_mone",<%=k%>, document.grade_input.m_mone_2.value);
               opener.document.WiseGrid3.SetCellValue("unitcost",<%=k%>, document.grade_input.unitcost_2.value);
               opener.document.WiseGrid3.SetCellValue("order_con",<%=k%>, document.grade_input.order_con_2.value);
               opener.document.WiseGrid3.SetCellValue("h_weight",<%=k%>, document.grade_input.h_weight_2.value);
               opener.document.WiseGrid3.SetCellValue("h_scrap",<%=k%>, document.grade_input.h_scrap_2.value);
               opener.document.WiseGrid3.SetCellValue("t_weight",<%=k%>, document.grade_input.t_weight_2.value);
               opener.document.WiseGrid3.SetCellValue("recycle",<%=k%>, document.grade_input.recycle.value);
               opener.document.WiseGrid3.SetCellValue("grade_name",<%=k%>, document.grade_input.grade_name.value);
               opener.document.WiseGrid3.SetCellValue("grade_color",<%=k%>, document.grade_input.grade_color.value);

             self.close();
        }
            else if (document.grade_input.make[2].checked == true)
        {
                <% if(p_pro_type.equals("부재료")){%>
                if (document.grade_input.unitcost_3.value == "")
                   {
                       alert("원재료 단가는 필수입력사항입니다.");
                       document.grade_input.unitcost_3.focus();
                            return false;
                  }
            <% }%>

               <%if(page_name.equals("edit")){%>
               opener.document.WiseGrid3.SetCellValue("unitcost_txt",<%=k%>, document.grade_input.unitcost_3.value);
               <%}%>
               opener.document.WiseGrid3.SetCellValue("m_maker",<%=k%>, document.grade_input.maker_3_txt.value);
               opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>, document.grade_input.grade_3_txt.value);
               opener.document.WiseGrid3.SetCellValue("plating",<%=k%>, document.grade_input.plating_3.value);
               opener.document.WiseGrid3.SetCellValue("w_size",<%=k%>, document.grade_input.w_size_3.value);
               opener.document.WiseGrid3.SetCellValue("make",<%=k%>, document.grade_input.make[2].value);
               opener.document.WiseGrid3.SetCellValue("m_mone",<%=k%>, document.grade_input.m_mone_3.value);
               opener.document.WiseGrid3.SetCellValue("unitcost",<%=k%>, document.grade_input.unitcost_3.value);
               opener.document.WiseGrid3.SetCellValue("order_con",<%=k%>, document.grade_input.order_con_3.value);
               opener.document.WiseGrid3.SetCellValue("t_weight",<%=k%>,document.grade_input.t_weight_3.value);

             self.close();
        }else{

            // Wire 실중량 계산 : 0.64 * 0.64 * 길이(w_size) * 원재료비중 (기본 : 8.55)

            var w_size_value
            var h_weight_value
            var t_weight_value

            w_size_value = document.grade_input.w_size_4.value;
            h_weight_value = (0.64 * 0.64 * w_size_value * <%=s_gravity_1%>) * 0.001 ;
            t_weight_value = <%=Carrier_weight%> + parseFloat(h_weight_value,10) ;

            if (document.grade_input.make_type[0].checked == true)
            {

               <%if(page_name.equals("edit")){%>
               opener.document.WiseGrid3.SetCellValue("unitcost_txt",<%=k%>, document.grade_input.unitcost_4.value);
               opener.document.WiseGrid3.SetCellValue("unitcost",<%=k%>,<%=Wire_cost%>);
               <%}else{%>
               opener.document.WiseGrid3.SetCellValue("unitcost",<%=k%>, document.grade_input.unitcost_4.value);
               <%}%>
               opener.document.WiseGrid3.SetCellValue("make",<%=k%>, document.grade_input.make[1].value);
               opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>, document.grade_input.meterial_4.value);
               opener.document.WiseGrid3.SetCellValue("h_weight",<%=k%>, h_weight_value.toFixed (3));
               opener.document.WiseGrid3.SetCellValue("h_scrap",<%=k%>, <%=Carrier_weight%>);
               opener.document.WiseGrid3.SetCellValue("t_weight",<%=k%>, t_weight_value.toFixed (3));
               opener.document.WiseGrid3.SetComboSelectedHiddenValue("make_type",<%=k%>,"사내");
               opener.document.WiseGrid3.SetComboSelectedHiddenValue("pro_1",<%=k%>,"생산2");

               if (document.grade_input.type_2[0].selected == false){
                   opener.document.WiseGrid3.SetComboSelectedHiddenValue("plating_type",<%=k%>,"후도금");
               }

            }
            else
            {
                  opener.document.WiseGrid3.SetCellValue("h_scrap",<%=k%>,<%=Carrier_weight%>);
                  opener.document.WiseGrid3.SetComboSelectedHiddenValue("make_type",<%=k%>,"구매");
                   opener.document.WiseGrid3.SetComboSelectedHiddenValue("pro_1",<%=k%>,"유상");
                opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>,document.grade_input.meterial_4_c.value);
                alert(document.grade_input.unitcost_4.value);
                opener.document.WiseGrid3.SetCellValue("type_cost",<%=k%>,document.grade_input.unitcost_4.value);

            }
                opener.document.WiseGrid3.SetCellValue("make",<%=k%>,document.grade_input.make[3].value);
                   opener.document.WiseGrid3.SetCellValue("m_mone",<%=k%>,document.grade_input.m_mone_4.value);
                  opener.document.WiseGrid3.SetCellValue("plating",<%=k%>,document.grade_input.plating_4.value);
                   opener.document.WiseGrid3.SetCellValue("w_size",<%=k%>,document.grade_input.w_size_4.value);
                  opener.document.WiseGrid3.SetComboSelectedHiddenValue("type_2",<%=k%>,document.grade_input.type_2.value);
                   opener.document.WiseGrid3.SetCellValue("plating_cost",<%=k%>,document.grade_input.plating_cost.value);
                   opener.document.WiseGrid3.SetCellValue("sul_cost",<%=k%>,document.grade_input.sul_cost.value);

                 self.close();
            }
        }else{
            <%if(page_name.equals("edit")){%>
                   opener.document.WiseGrid3.SetCellValue("unitcost_txt",<%=k%>,'');
            <%}%>

           opener.document.WiseGrid3.SetCellValue("meterial",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("m_maker",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("make",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("plating",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("t_size",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("w_size",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("p_size",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("m_mone",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("unitcost",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("order_con",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("h_weight",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("h_scrap",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("t_weight",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("recycle",<%=k%>,'');

           opener.document.WiseGrid3.SetCellValue("grade_name",<%=k%>,'');
           opener.document.WiseGrid3.SetCellValue("grade_color",<%=k%>,'');

    self.close();
        }
      }
     /**********************************************
     사내/구매 구분
     **********************************************/

        function make_c(a)
        {
             if (document.grade_input.make_type[0].checked == true)
               {
              document.grade_input.m_mone_4.disabled = true;
              document.grade_input.sul_cost.disabled = false;
                 document.grade_input.unitcost_4.disabled = true;
                 document.grade_input.t_weight_4.disabled = true;
                 document.grade_input.meterial_4_c.disabled = true;
                 document.grade_input.meterial_4.disabled = false;

              document.grade_input.m_mone_4.style.background = "#E6E6E6";
              document.grade_input.sul_cost.style.background = "#FFFFFF";
              document.grade_input.unitcost_4.style.background = "#E6E6E6";
              document.grade_input.t_weight_4.style.background = "#E6E6E6";

            }
            else
            {
              document.grade_input.m_mone_4.disabled = false;
              document.grade_input.sul_cost.disabled = true;
                 document.grade_input.unitcost_4.disabled = false;
                 document.grade_input.t_weight_4.disabled = false;
                 document.grade_input.meterial_4_c.disabled = false;
                 document.grade_input.meterial_4.disabled = true;
              document.grade_input.m_mone_4.style.background = "#FFFFFF";
              document.grade_input.sul_cost.style.background = "#E6E6E6";
              document.grade_input.unitcost_4.style.background = "#FFFFFF";
              document.grade_input.t_weight_4.style.background = "#FFFFFF";
            }

          }
</script>
<body onLoad= 'make_ab();'>
<Form method="post" name="grade_input" action='/plm/servlet/e3ps/costComServlet'>
<input type="hidden" name="Search_ok" value="<%=Search_ok%>">
<input type="hidden" name="cmd" value="popGrade">
<input type="hidden" name="wise_gb" value="No">
<input type="hidden" name="param_m_maker" value="<%=m_maker%>">
<input type="hidden" name="param_k" value="<%=k%>">
<input type="hidden" name="param_page_name" value="<%=page_name%>">
<input type="hidden" name="param_meterial" value="<%=meterial%>">
<input type="hidden" name="param_make" value="<%=make%>">
<input type="hidden" name="param_plating" value="<%=plating%>">
<input type="hidden" name="param_t_size" value="<%=t_size%>">
<input type="hidden" name="param_w_size" value="<%=w_size%>">
<input type="hidden" name="param_p_size" value="<%=p_size%>">
<input type="hidden" name="param_m_mone" value="<%=m_mone%>">
<input type="hidden" name="param_unitcost_txt" value="<%=unitcost_txt%>">
<input type="hidden" name="param_order_con" value="<%=order_con%>">
<input type="hidden" name="param_h_weight" value="<%=h_weight%>">
<input type="hidden" name="param_h_scrap" value="<%=h_scrap%>">
<input type="hidden" name="param_t_weight" value="<%=t_weight%>">
<input type="hidden" name="param_recycle" value="<%=recycle%>">
<input type="hidden" name="param_grade_name" value="<%=grade_name%>">
<input type="hidden" name="param_grade_color" value="<%=grade_color%>">
<input type="hidden" name="param_p_pro_type" value="<%=p_pro_type%>">
<input type="hidden" name="param_sul_cost" value="<%=sul_cost%>">
<input type="hidden" name="param_type_2" value="<%=type_2%>">
<input type="hidden" name="param_plating_cost" value="<%=plating_cost%>">
<input type="hidden" name="param_search_first" value="<%=search_first%>">
<input type="hidden" name="param_material_name" value="<%=material_name%>">


  <table width="833" border="0" align="center" cellspacing="0" bordercolor="#232E34" class="style3">
  <tr>
    <td width="177" height="25" bgcolor="#A0ACBA" class="style1">&nbsp;■ 주재료 내역      </td>
    <td height="25" bgcolor="#A0ACBA" class="style1"><div align="right">
      <input type="checkbox" name="reset_chk" value="Yes" >입력내용지우기 <input type="button" name="date_in" value="확인" onClick="sub_ok();">
    </div></td>
  </tr>
  <tr>
  <tr>
    <td height="23" colspan="2" bgcolor="#FDF9F2" class="style5"><input type="radio" name="make" value="make_1" onClick="make_ab(this.value);"
    <% if(make.equals("make_1") ){ out.println("checked"); }%>>비철

  <input type="radio" name="make" value="make_2" onClick="make_ab(this.value);" <%if(make.equals("make_2")){out.println("checked"); }%>>
수지
<input type="radio" name="make" value="make_3" onClick="make_ab(this.value);" <%if(make.equals("make_3")){out.println("checked"); }%>>
기타(구매/부재료등)
<input type="radio" name="make" value="make_4" onClick="make_ab(this.value);" <%if(make.equals("make_4")){out.println("checked"); }%>>
벤도리아핀</td>
  </tr>
   <tr id="menu1">
    <td height="23" colspan="2" bgcolor="#FDF9F2" class="style5"><table width="869" border="0" align="center" cellspacing="0" bordercolor="#232E34" class="style5">
      <tr>
        <td height="20" colspan="7" bgcolor="#F7F7F7" class="style5"><table width="869" border="0" cellpadding="0" cellspacing="1" bordercolor="#232E34" >
            <tr>
              <td width="172" height="25" rowspan="2" bgcolor="#E1E1E1" class="style2">업체명</td>
              <td width="126" rowspan="2" bgcolor="#E1E1E1" class="style2">Material</td>
              <td width="241" height="25" rowspan="2" bgcolor="#E1E1E1" class="style2">Grade명</td>
              <td width="54" rowspan="2" bgcolor="#E1E1E1" class="style2">Color</td>
              <td width="33" rowspan="2" bgcolor="#E1E1E1" class="style2">재생<br />
                비율(%)</td>
              <td rowspan="2" bgcolor="#E1E1E1" class="style2">단가<br />
                (\/Kg)</td>
              <td rowspan="2" bgcolor="#E1E1E1" class="style2">발주<br />
                조건</td>
              <td colspan="3" bgcolor="#E1E1E1" class="style2">제품중량</td>
            </tr>
            <tr>
              <td bgcolor="#E1E1E1" class="style2">부품</td>
              <td bgcolor="#E1E1E1" class="style2">스크랩</td>
              <td bgcolor="#E1E1E1" class="style2">총중량</td>
            </tr>
            <tr>
              <td height="25" bgcolor="#FDF9F2" class="style5"><select name="maker_2" onChange="maker_select(this.value);" >
                    <option selected="selected"></option>
                  <%
                      if(SearchMaker_2!= null){
                          for(int i=0; i<SearchMaker_2.size(); i++){
                              Maker_2Map = (Hashtable)SearchMaker_2.get(i);
                              maker_name = (String)Maker_2Map.get("maker_name") ;

                  %>

                  <option value="<%=maker_name%>" <% if(maker_2.equals(maker_name)){ out.println("selected"); } %>><%=maker_name%></option>
                  <%	}
                        }
                  %>
                </select>
                  <input type="checkbox" name="maker_chk" value="Yes" onClick="chk_mk();" />
                직접입력</td>
              <td rowspan="2" bgcolor="#FDF9F2" class="style5"><select name="material_name" onChange="maker_select(this.value);">
                  <option selected="selected"></option>
                  <%
                      if(SearchMaterial_name!= null){
                          for(int i=0; i<SearchMaterial_name.size(); i++){
                              Material_nameMap = (Hashtable)SearchMaterial_name.get(i);
                              String p_material_name = (String)Material_nameMap.get("material_name") ;
                  %>
                  <option value="<%=p_material_name%>" <% if(p_material_name.equals(material_name)){ out.println("selected"); } %>><%=p_material_name%></option>
                  <%	}
                        }
                  %>

                </select></td>
              <td height="25" bgcolor="#FDF9F2" class="style5"><select name="grade_name" onChange="maker_select(this.value);">
                  <option selected="selected"></option>
                  <%
                      if(SearchGrade_name!= null){
                          for(int i=0; i<SearchGrade_name.size(); i++){
                              Grade_nameMap = (Hashtable)SearchGrade_name.get(i);
                              String p_grade_name = (String)Grade_nameMap.get("grade_name") ;
                  %>
                  <option value="<%=p_grade_name%>" <% if(p_grade_name.equals(grade_name)){ out.println("selected"); } %>><%=p_grade_name%></option>
                  <%	}
                        }
                  %>

                </select>
                  <input type="checkbox" name="name_chk" value="Yes"  onclick="chk_na();" />
                직접입력</td>
              <td rowspan="2" bgcolor="#FDF9F2" class="style5"><select name="grade_color" >
                  <option selected="selected"></option>
                  <%
                      if(SearchGrade_color!= null){
                          for(int i=0; i<SearchGrade_color.size(); i++){
                              SearchGradeMap = (Hashtable)SearchGrade_color.get(i);
                              String p_grade_color = (String)SearchGradeMap.get("grade_color") ;
                  %>
                  <option value="<%=p_grade_color%>" <% if(p_grade_color.equals(grade_color)){ out.println("selected"); } %>><%=p_grade_color%></option>
                  <%	}
                        }
                  %>

              </select></td>
              <td rowspan="2" bgcolor="#FDF9F2" class="style5"><input name="recycle" type="text" class="style2" size="4" value="<%=recycle%>"> </td>
              <td width="60" rowspan="2" bgcolor="#FDF9F2" class="style2"><select name="m_mone_2" class="style2" id="bg_color" disabled="disabled" >
                  <option value="EUR" <% if(p_m_mone.equals("EUR")){ out.println("selected");}%>>EUR</option>
                  <option value="CNY" <% if(p_m_mone.equals("CNY")){ out.println("selected");}%>>CNY</option>
                  <option value="KRW" <% if(p_m_mone.equals("KRW") || m_mone_2.equals("")){ out.println("selected");}%> >KRW</option>
                  <option value="JPY" <% if(p_m_mone.equals("JPY")){ out.println("selected");}%>>JPY</option>
                  <option value="USD" <% if(p_m_mone.equals("USD")){ out.println("selected");}%>>USD</option>
                </select>
                  <input name="unitcost_2" type="text" class="style2" size="7" id="bg_color" value="<%=unitcost_2%>"></td>
              <td width="58" rowspan="2" bgcolor="#FDF9F2" class="style2"><select name="order_con_2" class="style2" >
                  <option value="내자"<% if(p_order_con.equals("내자") || order_con_2.equals("")){ out.println("selected"); }%>>내자</option>
                  <option value="CIF" <% if(p_order_con.equals("CIF"))  {out.println("selected");}%>>CIF</option>
                  <option value="FOB" <% if(p_order_con.equals("FOB"))  {out.println("selected");}%>>FOB</option>
                  <option value="L/C" <% if(p_order_con.equals("L/C"))  {out.println("selected");}%>>L/C</option>
              </select></td>
              <td width="32" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="h_weight_2" type="text" class="style2" size="5" value="<%=h_weight_2%>" onchange= "weight_sum_2();" /></td>
              <td width="39" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="h_scrap_2" type="text" class="style2" size="5" value="<%=h_scrap_2%>" onchange= "weight_sum_2();" /></td>
              <td width="43" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="t_weight_2" type="text" class="style2" size="5" value="<%=t_weight_2%>"></td>
            </tr>
            <tr>
              <td height="25" bgcolor="#FDF9F2" class="style5"><input name="maker_txt" type="text" size="23" id="bg_color" disabled="disabled" value="<%=maker_txt%>" ></td>
              <td height="25" bgcolor="#FDF9F2" class="style5"><input name="grade_name_txt" type="text" size="32" id="bg_color" disabled="disabled" value="<%=grade_name_txt%>" ></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="20" colspan="7" bgcolor="#F7F7F7" class="style4">ex) LG화학 GP1000H BA28</td>
      </tr>
    </table></td>
  </tr>
  <tr id="menu2">
    <td height="23" colspan="2" bgcolor="#FDF9F2" class="style5"><table width="833" border="0" align="left" cellspacing="0" bordercolor="#232E34" class="style5">
      <tr>
        <td colspan="7" bgcolor="#F7F7F7" class="style5"><table width="869" border="0" cellpadding="0" cellspacing="1" bordercolor="#232E34" class="style5">
            <tr>
              <td width="175" rowspan="2" bgcolor="#E1E1E1" class="style2">업체명</td>
              <td width="155" rowspan="2" bgcolor="#E1E1E1" class="style2">Material</td>
              <td width="96" rowspan="2" bgcolor="#E1E1E1" class="style2">Finish</td>
              <td height="16" colspan="3" bgcolor="#E1E1E1" class="style2">Size</td>
              <td rowspan="2" bgcolor="#E1E1E1" class="style2">단가<br />
                (\/Kg)</td>
              <td rowspan="2" bgcolor="#E1E1E1" class="style2">발주<br />
                조건</td>
              <td colspan="3" bgcolor="#E1E1E1" class="style2">제품중량</td>
            </tr>
            <tr>
              <td width="40" height="20" bgcolor="#E1E1E1" class="style2">t</td>
              <td height="20" bgcolor="#E1E1E1" class="style2">w</td>
              <td height="20" bgcolor="#E1E1E1" class="style2">P</td>
              <td bgcolor="#E1E1E1" class="style2">부품</td>
              <td bgcolor="#E1E1E1" class="style2">스크랩</td>
              <td bgcolor="#E1E1E1" class="style2">총중량</td>
            </tr>
            <tr>
              <td height="25" bgcolor="#FDF9F2" class="style5"><select name="m_maker" onChange="maker_select(this.value);">
                  <option selected="selected"></option>
                  <%
                      if(SearchMaker_name!= null){
                          for(int i=0; i<SearchMaker_name.size(); i++){
                              SearchMaker_nameMap = (Hashtable)SearchMaker_name.get(i);
                              String p_maker_name = (String)SearchMaker_nameMap.get("maker_name") ;
                  %>
                  <option value="<%=p_maker_name%>" <% if(p_maker_name.equals(m_maker)){ out.println("selected"); } %>><%=p_maker_name%></option>
                  <%	}
                        }
                  %>

                </select>
                  <input type="checkbox" name="maker_chk_1" value="Yes" onClick="chk_mk_1();" />
                직접입력1</td>
              <td bgcolor="#FDF9F2" class="style5"><select name="meterial" onChange="meterial_select(this.value);">
                  <option selected="selected"></option>
                  <%
                      if(SearchGrade_name2!= null){
                          for(int i=0; i<SearchGrade_name2.size(); i++){
                              SearchGrade_name2Map = (Hashtable)SearchGrade_name2.get(i);
                              String p_grade_name = (String)SearchGrade_name2Map.get("grade_name") ;
                  %>
                  <option value="<%=p_grade_name%>" <% if(p_grade_name.equals(meterial)){ out.println("selected"); } %>><%=p_grade_name%></option>
                  <%	}
                        }
                  %>
                </select>
                  <input type="checkbox" name="name_chk_1" value="Yes"  onclick="chk_na_1();" />
                직접입력</td>
              <td rowspan="2" bgcolor="#FDF9F2" class="style2"><select name="plating" <% if(make.equals("make_2")) {out.println("disabled");}%>>
                <option value="Unplate" <% if(plating.equals("Unplate")){out.println("selected");}%>>Unplate </option>
                <option value="Pre-Tin" <% if(plating.equals("Pre-Tin") || plating.equals("")){ out.println("selected"); }%> >Pre-Tin</option>
              </select></td>
              <td width="40" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="t_size" type="text" size="5" value="<%=t_size%>" onChange="t_weight_sum();" <% if(make.equals("make_2")){ out.println("disabled id=bg_color"); }%>></td>
              <td width="40" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="w_size" type="text" size="5" value="<%=w_size%>" onChange="t_weight_sum();" <% if(make.equals("make_2")){ out.println("disabled id=bg_color"); }%>></td>
              <td width="40" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="p_size" type="text" size="5" value="<%=p_size%>" onChange="t_weight_sum();" <% if(make.equals("make_2")){ out.println("disabled id=bg_color"); }%>></td>
              <td width="107" rowspan="2" bgcolor="#FDF9F2" class="style2"><select name="m_mone" class="style2" id="bg_color" disabled="disabled" >
                  <option value="EUR" <% if(m_mone.equals("EUR")) {out.println("selected");}%>>EUR</option>
                  <option value="CNY" <% if(m_mone.equals("CNY")) {out.println("selected");}%>>CNY</option>
                  <option value="KRW" <% if(m_mone.equals("KRW") || m_mone_2.equals("")) {out.println("selected");}%> >KRW</option>
                  <option value="JPY" <% if(m_mone.equals("JPY")){ out.println("selected");}%>>JPY</option>
                  <option value="USD" <% if(m_mone.equals("USD")){ out.println("selected");}%>>USD</option>
                </select>
                  <input name="unitcost" type="text" class="style2" size="7" id="select" value="<%=unitcost%>" disabled="disabled"></td>
              <td width="60" rowspan="2" bgcolor="#FDF9F2" class="style2"><select name="order_con" class="style2">
                  <option value="내자"<% if(order_con.equals("내자") || order_con_2.equals("")){ out.println("selected");}%>>내자</option>
                  <option value="CIF" <% if(order_con.equals("CIF"))  { out.println("selected"); }%>>CIF</option>
                  <option value="FOB" <% if(order_con.equals("FOB"))  { out.println("selected");}%>>FOB</option>
                  <option value="L/C" <% if(order_con.equals("L/C"))  { out.println("selected");}%>>L/C</option>
              </select></td>
              <td width="48" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="h_weight" type="text" class="style2" size="5" value="<%=h_weight%>" onChange="weight_sum();" /></td>
              <td width="48" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="h_scrap" type="text" class="style2" size="5" value="<%=h_scrap%>" readonly="readonly"></td>
              <td width="48" rowspan="2" bgcolor="#FDF9F2" class="style2"><input name="t_weight" type="text" class="style2" id="select" size="5"  value="<%=t_weight%>" readonly="readonly"></td>
            </tr>
            <tr>
              <td height="25" bgcolor="#FDF9F2" class="style5"><input name="maker_1_txt" type="text" size="25" id="bg_color" disabled="disabled" value="<%=maker_1_txt%>" ></td>
              <td bgcolor="#FDF9F2" class="style5"><input name="grade_1_txt" type="text" size="22" id="bg_color" value="<%=grade_1_txt%>" disabled="disabled"></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="20" colspan="7" bgcolor="#F7F7F7" class="style4">ex) 풍산 C2600R (TP) 1/4H 0.6 x 205 </td>
      </tr>
    </table></td>
  </tr>
  <tr id="menu3">
    <td height="20" colspan="7" bgcolor="#F7F7F7" class="style4"><table width="869" border="0" align="center" cellspacing="0" bordercolor="#232E34" class="style5">
      <tr>
        <td height="20" colspan="7" bgcolor="#F7F7F7" class="style5"><table width="869" border="0" cellpadding="0" cellspacing="1" bordercolor="#232E34" class="style5">
            <tr>
              <td width="175" bgcolor="#E1E1E1" class="style2">업체명</td>
              <td width="154" bgcolor="#E1E1E1" class="style2">Material</td>
              <td width="109" bgcolor="#E1E1E1" class="style2">Finish</td>
              <td width="69" bgcolor="#E1E1E1" class="style2">Size</td>
              <td bgcolor="#E1E1E1" class="style2">단가<br />
                (\/Kg &amp; \/M)</td>
              <td bgcolor="#E1E1E1" class="style2">발주<br />
                조건</td>
              <td width="82" bgcolor="#E1E1E1" class="style2">중량 및 길이 </td>
              <td bgcolor="#E1E1E1" class="style2">단위</td>
            </tr>
            <tr>
              <td bgcolor="#FDF9F2" class="style5"><input name="maker_3_txt" type="text" size="25" value="<%=m_maker%>"></td>
              <td bgcolor="#FDF9F2" class="style5"><input name="grade_3_txt" type="text" size="22" value="<%=meterial%>"></td>
              <td bgcolor="#FDF9F2" class="style2"><input name="plating_3" type="text" size="13" value="<%=plating%>"></td>
              <td bgcolor="#FDF9F2" class="style2"><input name="w_size_3" type="text" size="8" value="<%=w_size%>"></td>
              <td width="131" bgcolor="#FDF9F2" class="style2"><select name="m_mone_3" class="style2" <% if( p_pro_type.equals("구매")){ out.println("disabled");}%>>
                  <option value="EUR" <% if(m_mone.equals("EUR")){ out.println("selected");}%>>EUR</option>
                  <option value="CNY" <% if(m_mone.equals("CNY")) { out.println("selected");}%>>CNY</option>
                  <option value="KRW" <% if(m_mone.equals("KRW") || m_mone_2.equals("")){ out.println("selected");}%> >KRW</option>
                  <option value="JPY" <% if(m_mone.equals("JPY")){ out.println("selected");}%>>JPY</option>
                  <option value="USD" <% if(m_mone.equals("USD")){ out.println("selected");}%>>USD</option>
                </select>
                &nbsp;
                <input name="unitcost_3" type="text" class="style2" size="8" value="<%=unitcost_3%>" <% if(p_pro_type.equals("구매")) { out.println("disabled id='bg_color'");}%>></td>
              <td width="59" bgcolor="#FDF9F2" class="style2"><select name="order_con_3" class="style2">
                  <option value="내자"<% if(order_con.equals("내자") || order_con_2.equals("")){ out.println("selected");}%>>내자</option>
                  <option value="CIF" <% if(order_con.equals("CIF")){ out.println("selected");}%>>CIF</option>
                  <option value="FOB" <% if(order_con.equals("FOB")){ out.println("selected");}%>>FOB</option>
                  <option value="L/C" <% if(order_con.equals("L/C")){ out.println("selected");}%>>L/C</option>
              </select></td>
              <td bgcolor="#FDF9F2" class="style2"><input name="t_weight_3" type="text" class="style2" size="12" value="<%=t_weight%>" ></td>
              <td width="81" bgcolor="#FDF9F2" class="style2"><select name="dan_type" class="style2">
                  <option value="g" selected="selected" >g/EA</option>
                  <option value="mm" >mm/EA</option>
              </select></td>
            </tr>
        </table></td>
      </tr>
      <tr>
        <td height="20" colspan="7" bgcolor="#F7F7F7" class="style4">ex) 솔더크림 등의 구매,부재료류 일때 입력 </td>
      </tr>
    </table></td>
  </tr>
  <tr id="menu4">
        <td height="20" colspan="7" bgcolor="#F7F7F7" class="style5">
          <input type="radio"  name="make_type" value="make_in" onClick="make_c(this.value);" <%if(make_type.trim().equals("make_in")){ out.println("checked");}%> >사내 <input type="radio"  name="make_type" value="make_out" onClick="make_c(this.value);"  <%if(make_type.equals("make_out")){ out.println("checked");}%>  >구매
          <table width="781" border="0" cellpadding="0" cellspacing="1" bordercolor="#232E34" class="style5">
          <tr>
            <td colspan="2" bgcolor="#E1E1E1" class="style2">Material</td>
            <td width="83" rowspan="2" bgcolor="#E1E1E1" class="style2">Finish</td>
            <td rowspan="2" bgcolor="#E1E1E1" class="style2">길이</td>
            <td rowspan="2" bgcolor="#E1E1E1" class="style2">도금/후처리</td>
            <td rowspan="2" bgcolor="#E1E1E1" class="style2">후도금비<br>(\/EA)</td>
            <td rowspan="2" bgcolor="#E1E1E1" class="style2">설비수정비<br>
              (천원)</td>
            <td rowspan="2" bgcolor="#E1E1E1" class="style2">구매 단가<br>(\/EA)</td>
            <td rowspan="2" bgcolor="#E1E1E1" class="style2">구매시 Carrier중량<br>
            (g/EA)</td>
          </tr>
          <tr>
            <td width="84" bgcolor="#E1E1E1" class="style2">Wire</td>
            <td width="84" bgcolor="#E1E1E1" class="style2">Carrier</td>
          </tr>
          <tr>
            <td bgcolor="#FDF9F2" class="style5"><select name="meterial_4" onChange="meterial_select(this.value);">
              <option selected="selected"></option>
                 <%
                  if(SearchGrade_name3!= null){
                      for(int i=0; i<SearchGrade_name3.size(); i++){
                          SearchGrade_name3Map = (Hashtable)SearchGrade_name3.get(i);
                          String p_grade_name = (String)SearchGrade_name3Map.get("grade_name") ;
                 %>
                  <option value="<%=p_grade_name%>" <% if(p_grade_name.equals(meterial_4)){ out.println("selected"); } %>><%=p_grade_name%></option>
                 <%	}
                  }
                 %>

            </select></td>
            <td bgcolor="#FDF9F2" class="style5"><select name="meterial_4_c" onChange="meterial_select(this.value);">
              <option selected="selected"></option>
                 <%
                  if(SearchGrade_name4!= null){
                      for(int i=0; i<SearchGrade_name4.size(); i++){
                          SearchGrade_name4Map = (Hashtable)SearchGrade_name4.get(i);
                          String p_grade_name = (String)SearchGrade_name4Map.get("grade_name") ;
                 %>
                  <option value="<%=p_grade_name%>" <% if(p_grade_name.equals(meterial_4_c)){ out.println("selected"); } %>><%=p_grade_name%></option>
                 <%	}
                  }
                 %>

            </select></td>
            <td bgcolor="#FDF9F2" class="style2"><select name="plating_4">
                <option value="Unplate" <%if(plating.equals("Unplate")) { out.println("selected");}%>>Unplate </option>
                <option value="Pre-Tin" <%if(plating.equals("Pre-Tin")) { out.println("selected");}%>>Pre-Tin</option>
            </select></td>
            <td width="72" bgcolor="#FDF9F2" class="style2"><input name="w_size_4" type="text" class="style2" size="7" value="<%=w_size%>">mm</td>
            <td width="77" bgcolor="#FDF9F2" class="style2"><select name="type_2" >
              <option value="empty" <%if(type_2.equals("empty") || p_type_2.equals("")){ out.println("selected"); }%>> </option>
              <option value="Sn"    <%if(type_2.equals("Sn")) { out.println("selected");}%>>Sn</option>
              <option value="Au"    <%if(type_2.equals("Au")) { out.println("selected");}%>>Au</option>
              <option value="Ag"    <%if(type_2.equals("Ag")) { out.println("selected");}%>>Ag</option>
          <option value="3가Cr" <%if(type_2.equals("3가Cr")){ out.println("selected");}%>>3가Cr</option>
            <option value="기타"  <%if(type_2.equals("기타")){ out.println("selected");}%>>기타</option>
          </select></td>
            <td width="64" bgcolor="#FDF9F2" class="style2"><input name="plating_cost" type="text" size="8" value="<%=plating_cost%>"></td>
            <td width="70" bgcolor="#FDF9F2" class="style2"><input name="sul_cost"  type="text" size="8" value="<%=sul_cost%>" ></td>
            <td width="114" bgcolor="#FDF9F2" class="style2"><select name="m_mone_4" class="style2" >
                <option value="EUR" <% if(m_mone.equals("EUR")) { out.println("selected"); }%>>EUR</option>
                  <option value="CNY" <% if(m_mone.equals("CNY")) { out.println("selected");}%>>CNY</option>
                  <option value="KRW" <% if(m_mone.equals("KRW") || m_mone_2.equals("")) { out.println("selected");}%> >KRW</option>
                  <option value="JPY" <% if(m_mone.equals("JPY")) { out.println("selected");}%>>JPY</option>
                  <option value="USD" <% if(m_mone.equals("USD")) { out.println("selected");}%>>USD</option>
            </select>&nbsp;<input name="unitcost_4" type="text" class="style2" size="7" value=<%=unitcost_4%>></td>
            <td width="123" bgcolor="#FDF9F2" class="style2"><input name="t_weight_4" type="text" class="style2"  size="18" value="<%=h_scrap%>" ></td>
          </tr>
        </table></td>
    </tr>

</table>
</Form>
</body>
</html>