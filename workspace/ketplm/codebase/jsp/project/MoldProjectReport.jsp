<%@ page contentType="text/html;charset=UTF-8"%>

<jsp:useBean id="wtcontext" class="wt.httpgw.WTContextBean" scope="session" />
<jsp:setProperty name="wtcontext" property="request" value="<%=request%>" />
<%@page import = "wt.fc.QueryResult,
                  java.util.ArrayList,
                  java.util.Hashtable,
                  e3ps.ews.beans.EWSUtil,
                  e3ps.common.code.NumberCode,
                                    e3ps.common.code.NumberCodeHelper,
                  e3ps.common.util.StringUtil,
                  e3ps.common.web.PageControl"%>

<jsp:useBean id="messageService" class="e3ps.common.message.KETMessageService" scope="session" />

<%
    ArrayList list = (ArrayList)request.getAttribute("moldList");
    ArrayList addlist = (ArrayList)request.getAttribute("addMoldList");
    Hashtable condition = (Hashtable)request.getAttribute("condition");

    Hashtable moldProject = null;
    int sp11 = 0;
    int sp12 = 0;
    int sp21 = 0;
    int sp22 = 0;
    int sp31 = 0;
    int sp32 = 0;
    int sp41 = 0;
    int sp42 = 0;
    int sp51 = 0;
    int sp52 = 0;
    int sp61 = 0;
    int sp62 = 0;
    int sp71 = 0;
    int sp72 = 0;
    int sp81 = 0;
    int sp82 = 0;

    int sm11 = 0;
    int sm12 = 0;
    int sm21 = 0;
    int sm22 = 0;
    int sm31 = 0;
    int sm32 = 0;
    int sm41 = 0;
    int sm42 = 0;
    int sm51 = 0;
    int sm52 = 0;
    int sm61 = 0;
    int sm62 = 0;
    int sm71 = 0;
    int sm72 = 0;
    int sm81 = 0;
    int sm82 = 0;

    int p11 = 0;
    int p12 = 0;
    int p13 = 0;
    int p14 = 0;
    int p21 = 0;
    int p22 = 0;
    int p23 = 0;
    int p24 = 0;
    int p31 = 0;
    int p32 = 0;
    int p33 = 0;
    int p34 = 0;
    int p41 = 0;
    int p42 = 0;
    int p43 = 0;
    int p44 = 0;
    int p51 = 0;
    int p52 = 0;
    int p53 = 0;
    int p54 = 0;
    int p61 = 0;
    int p62 = 0;
    int p63 = 0;
    int p64 = 0;
    int p71 = 0;
    int p72 = 0;
    int p73 = 0;
    int p74 = 0;
    int p81 = 0;
    int p82 = 0;
    int p83 = 0;
    int p84 = 0;

    int m11 = 0;
    int m12 = 0;
    int m13 = 0;
    int m14 = 0;
    int m21 = 0;
    int m22 = 0;
    int m23 = 0;
    int m24 = 0;
    int m31 = 0;
    int m32 = 0;
    int m33 = 0;
    int m34 = 0;
    int m41 = 0;
    int m42 = 0;
    int m43 = 0;
    int m44 = 0;
    int m51 = 0;
    int m52 = 0;
    int m53 = 0;
    int m54 = 0;
    int m61 = 0;
    int m62 = 0;
    int m63 = 0;
    int m64 = 0;
    int m71 = 0;
    int m72 = 0;
    int m73 = 0;
    int m74 = 0;
    int m81 = 0;
    int m82 = 0;
    int m83 = 0;
    int m84 = 0;

    if( list != null && list.size() > 0 ){
        for(int inx = 0 ; inx < list.size(); inx++){
            moldProject = (Hashtable)list.get(inx);
            if( ((String)moldProject.get("itemType")).equals("Press")
                 && ((String)moldProject.get("state")).equals("COMPLETED")
                 && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p11 = p11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p12 = p12 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p14 = p14 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p13 = p13 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p21 = p21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p22 = p22 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p24 = p24 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p23 = p23 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                        && ((String)moldProject.get("state")).equals("COMPLETED")
                        && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                        && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p31 = p31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p32 = p32 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p34 = p34 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p33 = p33 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p41 = p41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p42 = p42 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p44 = p44 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p43 = p43 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p51 = p51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p52 = p52 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p54 = p54 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                p53 = p53 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p61 = p61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p62 = p62 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p64 = p64 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                p63 = p63 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p71 = p71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p72 = p72 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p74 = p74 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                p73 = p73 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p81 = p81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p82 = p82 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  p84 = p84 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Press")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                p83 = p83 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                        && ((String)moldProject.get("state")).equals("COMPLETED")
                        && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                        && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m11 = m11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m12 = m12 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m14 = m14 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m13 = m13 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m21 = m21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m22 = m22 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m24 = m24 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m23 = m23 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                        && ((String)moldProject.get("state")).equals("COMPLETED")
                        && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                        && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m31 = m31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m32 = m32 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m34 = m34 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m33 = m33 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m41 = m41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m42 = m42 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m44 = m44 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("사내")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m43 = m43 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m51 = m51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m52 = m52 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m54 = m54 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                m53 = m53 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m61 = m61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m62 = m62 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m64 = m64 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                m63 = m63 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m71 = m71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m72 = m72 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m74 = m74 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                m73 = m73 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && ((String)moldProject.get("state")).equals("COMPLETED")
                       && ((String)moldProject.get("endDate")).equals((String)condition.get("year"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m81 = m81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("PMOINWORK")
                            || ((String)moldProject.get("state")).equals("DEVASSIGN")
                            || ((String)moldProject.get("state")).equals("INWORK")
                            || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                            || ((String)moldProject.get("state")).equals("APPROVED")
                            || ((String)moldProject.get("state")).equals("REJECTED")
                            || ((String)moldProject.get("state")).equals("REWORK")
                            || ((String)moldProject.get("state")).equals("PLANCHANGE")
                            || ((String)moldProject.get("state")).equals("PROGRESS")
                            || (((String)moldProject.get("state")).equals("COMPLETED") && !((String)moldProject.get("endDate")).equals((String)condition.get("year"))) )
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m82 = m82 + Integer.parseInt((String)moldProject.get("count"));
                if( (((String)moldProject.get("state")).equals("INWORK")
                  || ((String)moldProject.get("state")).equals("UNDERREVIEW")
                  || ((String)moldProject.get("state")).equals("APPROVED")
                  || ((String)moldProject.get("state")).equals("REJECTED")
                  || ((String)moldProject.get("state")).equals("REWORK")
                  || ((String)moldProject.get("state")).equals("PLANCHANGE")
                  || ((String)moldProject.get("state")).equals("PROGRESS") )
                    && ((String)moldProject.get("pjtState")).equals("지연") ) {
                  m84 = m84 + Integer.parseInt((String)moldProject.get("count"));
              }
          }else if( ((String)moldProject.get("itemType")).equals("Mold")
                       && (((String)moldProject.get("state")).equals("STOPED") || ((String)moldProject.get("state")).equals("WITHDRAWN"))
                       && ((String)moldProject.get("making")).equals("외주")
                       && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                m83 = m83 + Integer.parseInt((String)moldProject.get("count"));
          }
        }
  }

  if( addlist != null && addlist.size() > 0 ){
        for(int inx = 0 ; inx < addlist.size(); inx++){
            moldProject = (Hashtable)addlist.get(inx);
            if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp11 = sp11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp12 = sp12 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp21 = sp21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp22 = sp22 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp31 = sp31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp32 = sp32 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp41 = sp41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp42 = sp42 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp51 = sp51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sp52 = sp52 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp61 = sp61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sp62 = sp62 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp71 = sp71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sp72 = sp72 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp81 = sp81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Press") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sp82 = sp82 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm11 = sm11 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm12 = sm12 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm21 = sm21 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm22 = sm22 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm31 = sm31 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("사내") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm32 = sm32 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm41 = sm41 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("사내")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm42 = sm42 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm51 = sm51 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("시작") ) {
                sm52 = sm52 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm61 = sm61 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("시작Mo") || ((String)moldProject.get("moldType")).equals("시작Fa")) ) {
                sm62 = sm62 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm71 = sm71 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O")
                 && ((String)moldProject.get("making")).equals("외주") && ((String)moldProject.get("moldType")).equals("양산") ) {
                sm72 = sm72 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("N") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm81 = sm81 + Integer.parseInt((String)moldProject.get("count"));
          }else if( ((String)moldProject.get("itemType")).equals("Mold") && ((String)moldProject.get("state")).equals("O") && ((String)moldProject.get("making")).equals("외주")
                 && (((String)moldProject.get("moldType")).equals("양산Mo") || ((String)moldProject.get("moldType")).equals("양산Fa")) ) {
                sm82 = sm82 + Integer.parseInt((String)moldProject.get("count"));
          }
        }
  }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Untitled Document</title>
<link href="../../portal/css/e3ps.css" rel="stylesheet" type="text/css">
<%@include file="/jsp/common/processing.html" %>
<script language=JavaScript src="/plm/portal/js/org.js"></script>
<script language=JavaScript src="/plm/portal/js/common.js"></script>

<style type="text/css">
<!--

-->
</style>

<script language='javascript'>
<!--

    //Project Report 화면 Open
    function goView(cmd, rowcon1, rowcon2, colcon1, colcon2){
        var url = '/plm/jsp/project/ProjectReportList.jsp'
                   + '?cmd=' + cmd + '&itemType=' + rowcon1 + '&statestate=' + rowcon2 + '&making=' + colcon1 + '&moldType=' + colcon2
                   + '&year=<%=(String)condition.get("year")%>&division=<%=(String)condition.get("division")%>&dept=<%=(String)condition.get("dept")%>&customerNo=<%=(String)condition.get("customerNo")%>';
        openWindow(url, '',880,750);
    }

//-->
</script>

</head>
<body>
<table border="0" cellspacing="0" cellpadding="0" width="780">
    <col width='43'><col width='92'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'><col width='43'>
  <tr>
    <td rowspan='2' colspan='2' class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "00969") %><%--구분--%></td>
    <td rowspan='2' class="tdblueMB"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td colspan='7' class="tdblueMB">Press</td>
    <td colspan='7' class="tdblueM0">Mold</td>
  </tr>
  <tr>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02022") %><%--신규--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02321") %><%--이월--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02688") %><%--중단--%></td>
    <td class="tdblueMB"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02022") %><%--신규--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02321") %><%--이월--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02171") %><%--완료--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02726") %><%--진행--%></td>
    <td class="tdblueM"><%=messageService.getString("e3ps.message.ket_message", "02688") %><%--중단--%></td>
    <td class="tdblueM0"><%=messageService.getString("e3ps.message.ket_message", "02703") %><%--지연--%></td>
  </tr>
  <tr>
      <td rowspan='5' class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01655") %><%--사내--%><br><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02017") %><%--시작금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '사내', 'MAT001');"><b><%=sp11+sp12+sm11+sm12%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '사내', 'MAT001');"><b><%=sp11%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '사내', 'MAT001');"><b><%=sp12%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '사내', 'MAT001');"><b><%=sp11+sp12%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT001');"><b><%=p11%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT001');"><b><%=p12%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT001');"><b><%=p13%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT001');"><b><font color="red"><%=p14%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '사내', 'MAT001');"><b><%=sm11%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '사내', 'MAT001');"><b><%=sm12%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '사내', 'MAT001');"><b><%=sm11+sm12%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT001');"><b><%=m11%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT001');"><b><%=m12%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT001');"><b><%=m13%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT001');"><b><font color="red"><%=m14%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02016") %><%--시작Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '사내', 'MAT003,MAT005');"><b><%=sp21+sp22+sm21+sm22%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '사내', 'MAT003,MAT005');"><b><%=sp21%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '사내', 'MAT003,MAT005');"><b><%=sp22%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '사내', 'MAT003,MAT005');"><b><%=sp21+sp22%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT003,MAT005');"><b><%=p21%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT003,MAT005');"><b><%=p22%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT003,MAT005');"><b><%=p23%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT003,MAT005');"><b><font color="red"><%=p24%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '사내', 'MAT003,MAT005');"><b><%=sm21%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '사내', 'MAT003,MAT005');"><b><%=sm22%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '사내', 'MAT003,MAT005');"><b><%=sm21+sm22%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT003,MAT005');"><b><%=m21%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT003,MAT005');"><b><%=m22%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT003,MAT005');"><b><%=m23%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT003,MAT005');"><b><font color="red"><%=m24%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02086") %><%--양산금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '사내', 'MAT002');"><b><%=sp31+sp32+sm31+sm32%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '사내', 'MAT002');"><b><%=sp31%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '사내', 'MAT002');"><b><%=sp32%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '사내', 'MAT002');"><b><%=sp31+sp32%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT002');"><b><%=p31%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT002');"><b><%=p32%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT002');"><b><%=p33%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT002');"><b><font color="red"><%=p34%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '사내', 'MAT002');"><b><%=sm31%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '사내', 'MAT002');"><b><%=sm32%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '사내', 'MAT002');"><b><%=sm31+sm32%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT002');"><b><%=m31%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT002');"><b><%=m32%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT002');"><b><%=m33%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT002');"><b><font color="red"><%=m34%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02082") %><%--양산Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '사내', 'MAT004,MAT006');"><b><%=sp41+sp42+sm41+sm42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '사내', 'MAT004,MAT006');"><b><%=sp41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '사내', 'MAT004,MAT006');"><b><%=sp42%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '사내', 'MAT004,MAT006');"><b><%=sp41+sp42%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', 'MAT004,MAT006');"><b><%=p41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', 'MAT004,MAT006');"><b><%=p42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', 'MAT004,MAT006');"><b><%=p43%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', 'MAT004,MAT006');"><b><font color="red"><%=p44%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '사내', 'MAT004,MAT006');"><b><%=sm41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '사내', 'MAT004,MAT006');"><b><%=sm42%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '사내', 'MAT004,MAT006');"><b><%=sm41+sm42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', 'MAT004,MAT006');"><b><%=m41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', 'MAT004,MAT006');"><b><%=m42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', 'MAT004,MAT006');"><b><%=m43%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', 'MAT004,MAT006');"><b><font color="red"><%=m44%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01913") %><%--소계--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '사내', '');"><b><%=sp11+sp12+sm11+sm12+sp21+sp22+sm21+sm22+sp31+sp32+sm31+sm32+sp41+sp42+sm41+sm42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '사내', '');"><b><%=sp11+sp21+sp31+sp41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '사내', '');"><b><%=sp12+sp22+sp32+sp42%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '사내', '');"><b><%=sp11+sp12+sp21+sp22+sp31+sp32+sp41+sp42%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '사내', '');"><b><%=p11+p21+p31+p41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '사내', '');"><b><%=p12+p22+p32+p42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '사내', '');"><b><%=p13+p23+p33+p43%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '사내', '');"><b><font color="red"><%=p14+p24+p34+p44%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '사내', '');"><b><%=sm11+sm21+sm31+sm41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '사내', '');"><b><%=sm12+sm22+sm32+sm42%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '사내', '');"><b><%=sm11+sm12+sm21+sm22+sm31+sm32+sm41+sm42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '사내', '');"><b><%=m11+m21+m31+m41%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '사내', '');"><b><%=m12+m22+m32+m42%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '사내', '');"><b><%=m13+m23+m33+m43%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '사내', '');"><b><font color="red"><%=m14+m24+m34+m44%></font></b></a></td>
  </tr>
  <tr>
    <td  colspan="17" class="tab_btm2"></td>
  </tr>
  <tr>
      <td rowspan='5' class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02184") %><%--외주--%><br><%=messageService.getString("e3ps.message.ket_message", "02530") %><%--제작--%></td>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02017") %><%--시작금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '외주', 'MAT001');"><b><%=sp51+sp52+sm51+sm52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '외주', 'MAT001');"><b><%=sp51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '외주', 'MAT001');"><b><%=sp52%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '외주', 'MAT001');"><b><%=sp51+sp52%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT001');"><b><%=p51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT001');"><b><%=p52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '외주', 'MAT001');"><b><%=p53%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT001');"><b><font color="red"><%=p54%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '외주', 'MAT001');"><b><%=sm51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '외주', 'MAT001');"><b><%=sm52%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '외주', 'MAT001');"><b><%=sm51+sm52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT001');"><b><%=m51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT001');"><b><%=m52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '외주', 'MAT001');"><b><%=m53%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT001');"><b><font color="red"><%=m54%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02016") %><%--시작Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '외주', 'MAT003,MAT005');"><b><%=sp61+sp62+sm61+sm62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '외주', 'MAT003,MAT005');"><b><%=sp61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '외주', 'MAT003,MAT005');"><b><%=sp62%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '외주', 'MAT003,MAT005');"><b><%=sp61+sp62%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT003,MAT005');"><b><%=p61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT003,MAT005');"><b><%=p62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '외주', 'MAT003,MAT005');"><b><%=p63%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT003,MAT005');"><b><font color="red"><%=p64%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '외주', 'MAT003,MAT005');"><b><%=sm61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '외주', 'MAT003,MAT005');"><b><%=sm62%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '외주', 'MAT003,MAT005');"><b><%=sm61+sm62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT003,MAT005');"><b><%=m61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT003,MAT005');"><b><%=m62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '외주', 'MAT003,MAT005');"><b><%=m63%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT003,MAT005');"><b><font color="red"><%=m64%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02086") %><%--양산금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '외주', 'MAT002');"><b><%=sp71+sp72+sm71+sm72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '외주', 'MAT002');"><b><%=sp71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '외주', 'MAT002');"><b><%=sp72%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '외주', 'MAT002');"><b><%=sp71+sp72%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT002');"><b><%=p71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT002');"><b><%=p72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '외주', 'MAT002');"><b><%=p73%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT002');"><b><font color="red"><%=p74%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '외주', 'MAT002');"><b><%=sm71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '외주', 'MAT002');"><b><%=sm72%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '외주', 'MAT002');"><b><%=sm71+sm72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT002');"><b><%=m71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT002');"><b><%=m72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '외주', 'MAT002');"><b><%=m73%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT002');"><b><font color="red"><%=m74%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02082") %><%--양산Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '외주', 'MAT004,MAT006');"><b><%=sp81+sp82+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '외주', 'MAT004,MAT006');"><b><%=sp81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '외주', 'MAT004,MAT006');"><b><%=sp82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '외주', 'MAT004,MAT006');"><b><%=sp81+sp82%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', 'MAT004,MAT006');"><b><%=p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', 'MAT004,MAT006');"><b><%=p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '외주', 'MAT004,MAT006');"><b><%=p83%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', 'MAT004,MAT006');"><b><font color="red"><%=p84%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '외주', 'MAT004,MAT006');"><b><%=sm81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '외주', 'MAT004,MAT006');"><b><%=sm82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '외주', 'MAT004,MAT006');"><b><%=sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', 'MAT004,MAT006');"><b><%=m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', 'MAT004,MAT006');"><b><%=m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '외주', 'MAT004,MAT006');"><b><%=m83%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', 'MAT004,MAT006');"><b><font color="red"><%=m84%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "01913") %><%--소계--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '외주', '');"><b><%=sp51+sp52+sm51+sm52+sp61+sp62+sm61+sm62+sp71+sp72+sm71+sm72+sp81+sp82+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '외주', '');"><b><%=sp51+sp61+sp71+sp81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '외주', '');"><b><%=sp52+sp62+sp72+sp82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '외주', '');"><b><%=sp51+sp52+sp61+sp62+sp71+sp72+sp81+sp82%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '외주', '');"><b><%=p51+p61+p71+p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '외주', '');"><b><%=p52+p62+p72+p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '외주', '');"><b><%=p53+p63+p73+p83%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '외주', '');"><b><font color="red"><%=p54+p64+p74+p84%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '외주', '');"><b><%=sm51+sm61+sm71+sm81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '외주', '');"><b><%=sm52+sm62+sm72+sm82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '외주', '');"><b><%=sm51+sm52+sm61+sm62+sm71+sm72+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '외주', '');"><b><%=m51+m61+m71+m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '외주', '');"><b><%=m52+m62+m72+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '외주', '');"><b><%=m53+m63+m73+m83%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '외주', '');"><b><font color="red"><%=m54+m64+m74+m84%></font></b></a></td>
  </tr>
  <tr>
    <td  colspan="17" class="tab_btm2"></td>
  </tr>
  <tr>
      <td rowspan='5' class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02017") %><%--시작금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '', 'MAT001');"><b><%=sp11+sp12+sm11+sm12+sp51+sp52+sm51+sm52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '', 'MAT001');"><b><%=sp11+sp51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '', 'MAT001');"><b><%=sp12+sp52%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '', 'MAT001');"><b><%=sp11+sp12+sp51+sp52%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT001');"><b><%=p11+p51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT001');"><b><%=p12+p52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '','MAT001');"><b><%=p13+p53%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT001');"><b><font color="red"><%=p14+p54%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '', 'MAT001');"><b><%=sm11+sm51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '', 'MAT001');"><b><%=sm12+sm52%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '', 'MAT001');"><b><%=sm11+sm12+sm51+sm52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT001');"><b><%=m11+m51%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT001');"><b><%=m12+m52%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '', 'MAT001');"><b><%=m13+m53%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT001');"><b><font color="red"><%=m14+m54%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02016") %><%--시작Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '', 'MAT003,MAT005');"><b><%=sp21+sp22+sm21+sm22+sp61+sp62+sm61+sm62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '', 'MAT003,MAT005');"><b><%=sp21+sp61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '', 'MAT003,MAT005');"><b><%=sp22+sp62%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '', 'MAT003,MAT005');"><b><%=sp21+sp22+sp61+sp62%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT003,MAT005');"><b><%=p21+p61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT003,MAT005');"><b><%=p22+p62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '', 'MAT003,MAT005');"><b><%=p23+p63%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT003,MAT005');"><b><font color="red"><%=p24+p64%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '', 'MAT003,MAT005');"><b><%=sm21+sm61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '', 'MAT003,MAT005');"><b><%=sm22+sm62%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '', 'MAT003,MAT005');"><b><%=sm21+sm22+sm61+sm62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT003,MAT005');"><b><%=m21+m61%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT003,MAT005');"><b><%=m22+m62%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '', 'MAT003,MAT005');"><b><%=m23+m63%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT003,MAT005');"><b><font color="red"><%=m24+m64%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02086") %><%--양산금형--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '', 'MAT002');"><b><%=sp31+sp32+sm31+sm32+sp71+sp72+sm71+sm72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '', 'MAT002');"><b><%=sp31+sp71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '', 'MAT002');"><b><%=sp32+sp72%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '', 'MAT002');"><b><%=sp31+sp32+sp71+sp72%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT002');"><b><%=p31+p71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT002');"><b><%=p32+p72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '', 'MAT002');"><b><%=p33+p73%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT002');"><b><font color="red"><%=p34+p74%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '', 'MAT002');"><b><%=sm31+sm71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '', 'MAT002');"><b><%=sm32+sm72%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '', 'MAT002');"><b><%=sm31+sm32+sm71+sm72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT002');"><b><%=m31+m71%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT002');"><b><%=m32+m72%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '', 'MAT002');"><b><%=m33+m73%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT002');"><b><font color="red"><%=m34+m74%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02082") %><%--양산Mo/Fa--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '', 'MAT004,MAT006');"><b><%=sp41+sp42+sm41+sm42+sp81+sp82+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '', 'MAT004,MAT006');"><b><%=sp41+sp81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '', 'MAT004,MAT006');"><b><%=sp42+sp82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '', 'MAT004,MAT006');"><b><%=sp41+sp42+sp81+sp82%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', 'MAT004,MAT006');"><b><%=p41+p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', 'MAT004,MAT006');"><b><%=p42+p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '', 'MAT004,MAT006');"><b><%=p43+p83%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '', 'MAT004,MAT006');"><b><font color="red"><%=p44+p84%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '', 'MAT004,MAT006');"><b><%=sm41+sm81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '', 'MAT004,MAT006');"><b><%=sm42+sm82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '', 'MAT004,MAT006');"><b><%=sm41+sm42+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', 'MAT004,MAT006');"><b><%=m41+m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', 'MAT004,MAT006');"><b><%=m42+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '', 'MAT004,MAT006');"><b><%=m43+m83%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '', 'MAT004,MAT006');"><b><font color="red"><%=m44+m84%></font></b></a></td>
  </tr>
  <tr>
    <td class="tdwhiteM"><%=messageService.getString("e3ps.message.ket_message", "02485") %><%--전체--%></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList5', '', '', '', '');"><b><%=sp11+sp12+sm11+sm12+sp21+sp22+sm21+sm22+sp31+sp32+sm31+sm32+sp41+sp42+sm41+sm42+sp51+sp52+sm51+sm52+sp61+sp62+sm61+sm62+sp71+sp72+sm71+sm72+sp81+sp82+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'N', '', '');"><b><%=sp11+sp21+sp31+sp41+sp51+sp61+sp71+sp81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Press', 'O', '', '');"><b><%=sp12+sp22+sp32+sp42+sp52+sp62+sp72+sp82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Press', '', '', '');"><b><%=sp11+sp12+sp21+sp22+sp31+sp32+sp41+sp42+sp51+sp52+sp61+sp62+sp71+sp72+sp81+sp82%></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'C', '', '');"><b><%=p11+p21+p31+p41+p51+p61+p71+p81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'P', '', '');"><b><%=p12+p22+p32+p42+p52+p62+p72+p82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Press', 'S', '', '');"><b><%=p13+p23+p33+p43+p53+p63+p73+p83%></b></a></td>
    <td class="tdwhiteMB"><a href="javascript:goView('reportList4', 'Press', 'D', '', '');"><b><font color="red"><%=p14+p24+p34+p44+p54+p64+p74+p84%></font></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'N', '', '');"><b><%=sm11+sm21+sm31+sm41+sm51+sm61+sm71+sm81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList5', 'Mold', 'O', '', '');"><b><%=sm12+sm22+sm32+sm42+sm52+sm62+sm72+sm82%></b></a></td>
    <td class="tdblueM"><a href="javascript:goView('reportList5', 'Mold', '', '', '');"><b><%=sm11+sm12+sm21+sm22+sm31+sm32+sm41+sm42+sm51+sm52+sm61+sm62+sm71+sm72+sm81+sm82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'C', '', '');"><b><%=m11+m21+m31+m41+m51+m61+m71+m81%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'P', '', '');"><b><%=m12+m22+m32+m42+m52+m62+m72+m82%></b></a></td>
    <td class="tdwhiteM"><a href="javascript:goView('reportList4', 'Mold', 'S', '', '');"><b><%=m13+m23+m33+m43+m53+m63+m73+m83%></b></a></td>
    <td class="tdwhiteM0"><a href="javascript:goView('reportList4', 'Mold', 'D', '', '');"><b><font color="red"><%=m14+m24+m34+m44+m54+m64+m74+m84%></font></b></a></td>
  </tr>
</table>
</body>
</html>
