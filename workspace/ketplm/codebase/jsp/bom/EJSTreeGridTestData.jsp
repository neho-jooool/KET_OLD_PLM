<%@page contentType="text/xml"%>
<%@page pageEncoding="UTF-8"%>

<%
    /*-----------------------------------------------------------------------------------------------------------------
      ! Support file only, run Grid.html instead !
      This file is used as Data_Url for TreeGrid
      It generates source data for TreeGrid from database
    ------------------------------------------------------------------------------------------------------------------*/

    response.addHeader("Cache-Control","max-age=1, must-revalidate");

    String resultStr  = request.getParameter("Result");
%>

<?xml version="1.0"?>

<Grid>
  <Cfg id='Editing5' Sorting='0' 
     MainCol='A'/>
  <Cols>
    <C Name='A' Type='Text' Width='310'
       Button='Defaults' 
       Defaults='|*RowsSibling'/>
    <C Name='B' Type='Date' Width='310'/>
    <C Name='C' Type='Int' Width='260'/>
  </Cols>
  <Header A='Text' B='Date' C='Int'/>
  <Head>
    <I Kind='Filter' 
       ADefaults='|*RowsAll' 
       BButton='Defaults' 
       BDefaults='|*Date|*RowsAll'/>
  </Head>
  <Body>
    <B>
      <I A='One' B='1/1/2000' 
         C='4' CButton='Defaults' 
         CDefaults='|*RowsAllMax3' 
         CanFilter='0'>
        <I A='One A' B='12:30' 
           BButton='None' BFormat='HH:mm' 
           C='1'/>
        <I A='One B' B='6/7/2005' C='1'/>
      </I>
      <I A='Two' AButton='None' 
         B='12/31/2006' 
         C='1' CButton='Defaults' 
         CDefaults='|*RowsAll'/>
      <I A='Three' 
         ADefaults='|*RowsSiblingMax5' 
         B='12/31/2006' 
         C='2' CButton='Defaults' 
         CDefaults='|*RowsAllMax3|10|20|30'/>
      <I A='Four' 
         ADefaults='|1|2|3|4|5|6|7|8|9' 
         B='12/31/2006' BButton='Button' 
         BButtonText='Click' 
         BWidthPad='40' C='1'/>
      <I A='Five' 
         AButton='Button' 
         AButtonText='Click' 
         AWidthPad='30' B='12/31/2006' 
         C='3'/>
      <I A='Six' 
         ADefaults='|*Button|*RowsSiblingMax5' 
         B='12/31/2006' C='1'/>
      <I A='Seven' 
         ADefaults='|*Button|1|2|3' 
         B='12:30' BFormat='hh:mm tt' 
         BButton='None' C='2'/>
      <I A='Eight' AButton='None' 
         B='12/31/2006' BButton='Defaults' 
         BDefaults='|*RowsSibling|*Date' 
         C='4'/>
      <I A='Nine' AButton='None' 
         B='12/31/2006' BButton='None'
         C='5'/>
    </B>
  </Body>
</Grid>
