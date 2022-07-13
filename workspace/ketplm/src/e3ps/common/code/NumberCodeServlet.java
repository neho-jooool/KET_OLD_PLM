package e3ps.common.code;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.BinaryLink;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.introspection.WTIntrospectionException;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import e3ps.common.jdf.config.Config;
import e3ps.common.jdf.config.ConfigEx;
import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.web.CommonServlet;
import ext.ket.shared.log.Kogger;

public class NumberCodeServlet extends CommonServlet
{
    protected void doService(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        String cmd = req.getParameter("cmd");
        String codetype = req.getParameter("codetype");

        GenNumberCode gen = new GenNumberCode();

        if ("create".equals(cmd))
        {
            if (gen.checkCode(codetype, req.getParameter("code").toUpperCase()))
            {
                alertNreloadNclose(res, "입력하신 코드가 이미 등록되어 있습니다. 다시 확인 후 등록해 주십시요.");
            }
            else
            {
                alertNsubmitNclose(res, create(req));
            }
        }
        else if ("delete".equals(cmd))
        {
            alertNgo(res, delete(req, res), "/plm/servlet/e3ps/NumberCodeServlet?codetype=" + codetype);
        }
        else if ("modify".equals(cmd))
        {
            alertNreloadNclose(res, modify(req));
        }
        else if ("multi".equals(cmd))
        {
//        	listMulti(req, res);
        }
        else if("ecrMulti".equals(cmd))
        {
        	listMultiForEcr(req, res);
        }
        else
        {
//            list(req, res);
        }
    }

//    private void list(HttpServletRequest req, HttpServletResponse res)
//    {
//        try
//        {
//            req.setAttribute("control", getPageControl(req, res));
//            gotoResult(req, res, "/jsp/common/code/ListNumberCode.jsp?codetype=" + req.getParameter("codetype"));
//        }
//        catch (Exception e)
//        {
//            Kogger.error(getClass(), e);
//        }
//    }
//    private void listMulti(HttpServletRequest req, HttpServletResponse res)
//    {
//        try
//        {
//            req.setAttribute("control", getPageControl(req, res));
//            gotoResult(req, res, "/jsp/common/code/ListNumberCodeMulti.jsp?codetype=" + req.getParameter("codetype"));
//        }
//        catch (Exception e)
//        {
//            Kogger.error(getClass(), e);
//        }
//    }

    private void listMultiForEcr(HttpServletRequest req, HttpServletResponse res)
    {
    	try
        {
//            req.setAttribute("control", getPageControl(req, res));
            gotoResult(req, res, "/jsp/common/code/ListNumberCodeForEcr.jsp?codetype=" + req.getParameter("codetype"));
        }
        catch (Exception e)
        {
            Kogger.error(getClass(), e);
        }
    }

    protected QuerySpec getQuerySpec(HttpServletRequest req, HttpServletResponse arg1)
    {
        QuerySpec query = null;

        try
        {
            query = new QuerySpec(NumberCode.class);
            query.appendWhere(new SearchCondition(NumberCode.class, "codeType", "=", req.getParameter("codetype")), new int[] { 0 });

            if(!CommonUtil.isAdmin()){
	            if(query.getConditionCount() > 0){
	            	query.appendAnd();
	            }
	            query.appendOpenParen();
	            query.appendWhere(new SearchCondition(NumberCode.class, "disabled", SearchCondition.IS_FALSE),new int[] { 0 });
	            query.appendOr();
	            query.appendWhere(new SearchCondition(NumberCode.class, "disabled", SearchCondition.IS_NULL), new int[]{0});
	            query.appendCloseParen();
            }

            SearchUtil.setOrderBy(query, NumberCode.class, 0, NumberCode.CODE, false);
        }
        catch (QueryException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (WTIntrospectionException e)
        {
            Kogger.error(getClass(), e);
        }
        catch (Exception e){
        	Kogger.error(getClass(), e);
        }

        return query;
    }

    private String create(HttpServletRequest req)
    {
        Config conf = ConfigEx.getInstance("message");
        String msg = conf.getString("save.success");
        // Common
        String name = req.getParameter("name").trim();
        String code = req.getParameter("code").trim();
        String desc = null;
        String codetype = req.getParameter("codetype");

        if (StringUtil.checkString(req.getParameter("description"))) desc = req.getParameter("description").trim();

        try
        {
            NumberCode nCode = NumberCode.newNumberCode();

            nCode.setName(name);
            nCode.setCode(code.toUpperCase());
            nCode.setDescription(desc);
            nCode.setCodeType(NumberCodeType.toNumberCodeType(codetype));
            nCode = (NumberCode) PersistenceHelper.manager.store(nCode);
        }
        catch (WTException e)
        {
            msg = e.getLocalizedMessage(Locale.KOREA);
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            msg = e.getLocalizedMessage(Locale.KOREA);
            Kogger.error(getClass(), e);
        }
        return msg;
    }

    private String modify(HttpServletRequest req)
    {
        Config conf = ConfigEx.getInstance("message");
        String msg = conf.getString("update.success");
        String oid = req.getParameter("oid");
        String name = req.getParameter("name").trim();
        String code = req.getParameter("code").trim();
        String desc = null;

        if (StringUtil.checkString(req.getParameter("description"))) desc = req.getParameter("description").trim();

        try
        {
            NumberCode nCode = (NumberCode) CommonUtil.getObject(oid);
            nCode.setName(name);
            nCode.setCode(code.toUpperCase());
            nCode.setDescription(desc);

            nCode = (NumberCode) PersistenceHelper.manager.modify(nCode);
        }
        catch (WTException e)
        {
            msg = e.getLocalizedMessage(Locale.KOREA);
            Kogger.error(getClass(), e);
        }
        catch (WTPropertyVetoException e)
        {
            msg = e.getLocalizedMessage(Locale.KOREA);
            Kogger.error(getClass(), e);
        }
        return msg;
    }

    private String delete(HttpServletRequest req, HttpServletResponse res)
    {
        Config conf = ConfigEx.getInstance("message");
        String msg = conf.getString("delete.success");
        try
        {
            String oid = req.getParameter("oid");
            NumberCode nCode = (NumberCode) CommonUtil.getObject(oid);

            QueryResult qr = PersistenceHelper.manager.navigate(nCode, "ALL", BinaryLink.class, false);
            if (qr.size() == 0)
            {
                PersistenceHelper.manager.delete(nCode);
            }
            else
            {
                msg = "사용중인 코드입니다";
            }
        }
        catch (Exception e)
        {
            msg = e.getMessage();
            Kogger.error(getClass(), e);
        }

        return msg;
    }
}

/* $Log: not supported by cvs2svn $
/* Revision 1.2  2009/08/21 06:00:32  jspark
/*
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/08/11 04:16:21  administrator
/* Init Source
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.1  2009/02/25 01:26:32  smkim
/* 최초 작성
/* Committed on the Free edition of March Hare Software CVSNT Server.
/* Upgrade to CVS Suite for more features and support:
/* http://march-hare.com/cvsnt/
/*
/* Revision 1.6  2008/10/14 04:37:40  jspark
/* *** empty log message ***
/*
/* Revision 1.5  2008/10/13 10:26:21  smkim
/* *** empty log message ***
/*
/* Revision 1.4  2008/09/05 05:50:41  jspark
/* *** empty log message ***
/*
/* Revision 1.3  2008/03/27 02:24:36  smkim
/* CodeType 으로  List Servlet 추가
/*
/* Revision 1.2  2008/01/31 05:41:12  khchoi
/* [완료] 코드체계관리 등록/수정/삭제
/*
/* Revision 1.1  2008/01/29 06:25:03  sjhan
/* 주성 기본 패키지 정리작업 완료
/*
/* Revision 1.1  2008/01/23 09:51:53  sjhan
/* e3ps package 정리 완료본
/* jsp 소스는 확인 후 수정해야 될 필요 있음
/*
/* Revision 1.1  2007/09/27 01:43:28  khchoi
/* [20070927 최강훈]
/* *.java Source 정리
/*
/* Revision 1.1.1.1  2007/04/10 06:40:18  administrator
/* no message
/*
/* Revision 1.1.1.1  2007/02/14 07:53:56  administrator
/* no message
/*
/* Revision 1.5  2006/11/15 09:20:57  kskim
/* 정렬기준 변경
/* 이름->코드
/*
/* Revision 1.4  2006/10/25 06:16:05  shchoi
/* error 메세지 출력 변경
/*
/* Revision 1.3  2006/06/27 06:30:39  shchoi
/* create, modify, delete 소스 수정 및 안정화
/**/
