<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<title>PLM Portal</title>
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/main.css" />
<link rel="stylesheet" type="text/css" href="/plm/extcore/css/loading.css" />
<div id="wrap">
  <!-- 왼쪽 사이드 메뉴 끝 -->
  <div id="contents_Wrap">
    <div class="contents-box">
      <div class="contents box-size">
        <div class="main-info clearfix">
        <%
        int n = (int) (Math.random() * 10) + 1;
        if(n > 4){
            n = (int) Math.floor(n / 2);
            if(n == 5)
                n = (int) Math.floor(n / 2);
        }
        %>
          <img alt="" src="/plm/jsp/common/error<%=n %>.jpg">
        </div>
      </div>
    </div>
  </div>
</div>
</head>
</html>
