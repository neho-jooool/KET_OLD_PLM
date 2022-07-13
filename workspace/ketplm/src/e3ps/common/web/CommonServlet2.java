/**
 *	@(#) CommonHttpServlet.java
 *	Copyright (c) jerred. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2004. 3. 3.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc	
 */

package e3ps.common.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wt.fc.PagingQueryResult;
import wt.fc.PagingSessionHelper;
import wt.httpgw.WTContextBean;
import wt.pds.StatementSpec;
import wt.query.QuerySpec;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;
/**
 *	시스템에서 공통적으로 사용하는 서블릿 클래스
 */
public abstract class CommonServlet2 extends HttpServlet {
	/**
	 * GET,POST으로 들어온 요청을 처리할 작업을 기술한다.
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected abstract void doService(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException;
	
	/**
	 * GET 방식을 처리한다.
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		WTContextBean wtcontextbean = new WTContextBean();
		wtcontextbean.setRequest(req);

		doService(req, res);
	}

	/**
	 * POST방식을 처리한다.
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws IOException, ServletException {
		doGet(req,res);
	}
	
	/**
     * PageControl을 반환한다.
     * 
     * @param req
     * @param res
     * @return
     */
    protected PageControl getPageControl(HttpServletRequest req, HttpServletResponse res) {
        String sessionid = req.getParameter("sessionid");

        if ( sessionid != null && sessionid.length() > 0 && !sessionid.equals("0") ) return find(req, sessionid);
        else {
        	String page = req.getParameter("page");
        	if (page != null && page.length() > 0) {
        		return find(req, res,StringUtil.parseInt(page,1));
        	} else {
        		return find(req, res);
        	}
        }
    }

    /**
     * PageControl을 반환한다.
     * 
     * @param req
     * @param res
     * @return
     */
    protected PageControl getPageControl(HttpServletRequest req, HttpServletResponse res, QuerySpec spec) {
        String sessionid = req.getParameter("sessionid");
        int perPage = StringUtil.getIntParameter (req.getParameter ( "perPage" ) ,PageControl.PERPAGE );
        int formPage = StringUtil.getIntParameter ( req.getParameter ( "formPage" ) , PageControl.FORMPAGE );
//        String page = req.getParameter("page");
        int page = StringUtil.getIntParameter (req.getParameter ( "page" ) ,0 );
        
        Kogger.debug(getClass(), "sessionid : " + sessionid);
        Kogger.debug(getClass(), "perPage : " + perPage);
        Kogger.debug(getClass(), "formPage : " + formPage);
        Kogger.debug(getClass(), "page : " + page);
        Kogger.debug(getClass(), "spec : " + spec);

        if ( sessionid != null && sessionid.length() > 0 && !sessionid.equals("0") ) return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), new Integer(page), sessionid);
        else {
            if (req.getParameter ( "page" ) != null && req.getParameter ( "page" ).length() > 0) {
                return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), spec, new Integer(StringUtil.parseInt(req.getParameter ( "page" ),1)));
            } else {
                return CommonSearchHelper.find(new Integer(perPage), new Integer(formPage), spec);
            }
        }
    }
	/**
     * 검색할 Query를 생성한다.
     * 
     * @param req
     * @param res
     * @return
     */
    protected StatementSpec getQuerySpec(HttpServletRequest req, HttpServletResponse res) {
        return null;
    }
	
    /**
	 * 최초 페이지를 리스트할 때 사용한다.
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	protected PageControl find(HttpServletRequest req, HttpServletResponse res) {
		int perPage = StringUtil.getIntParameter (req.getParameter ( "perPage" ) ,PageControl.PERPAGE );
		int formPage = StringUtil.getIntParameter ( req.getParameter ( "formPage" ) , PageControl.FORMPAGE );
		try {
			PagingQueryResult paging = PagingSessionHelper.openPagingSession (0 ,perPage ,getQuerySpec ( req , res ) );
			return new PageControl ( paging , 0 , formPage , perPage );
		} catch (Exception e) {
			e.printStackTrace ();
			return null;
		}
	}

	/**
	 * 최초 페이지를 리스트할 때 사용한다.
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	protected PageControl find(HttpServletRequest req, HttpServletResponse res,int page) {
		int perPage = StringUtil.getIntParameter (req.getParameter ( "perPage" ) ,PageControl.PERPAGE );
		int formPage = StringUtil.getIntParameter ( req.getParameter ( "formPage" ) , PageControl.FORMPAGE );
		try {
			PagingQueryResult paging = PagingSessionHelper.openPagingSession (page ,perPage ,getQuerySpec ( req , res ) );
			return new PageControl ( paging , page , formPage , perPage );
		} catch (Exception e) {
			e.printStackTrace ();
			return null;
		}
	}

	/**
	 * PageSessionId가 있는 경우 사용된다.
	 * 
	 * @param req
	 * @param sessionid
	 * @return
	 */
	protected PageControl find(HttpServletRequest req, String sessionid) {
		int perPage = StringUtil.getIntParameter (req.getParameter ( "perPage" ) ,PageControl.PERPAGE );
		int formPage = StringUtil.getIntParameter ( req.getParameter ( "formPage" ) , PageControl.FORMPAGE );
		try {
			int page = StringUtil.getIntParameter (req.getParameter ( "page" ) ,0 );
			PagingQueryResult paging = PagingSessionHelper.fetchPagingSession (( page - 1 ) * perPage ,perPage ,Long.parseLong ( sessionid ) );
			return new PageControl ( paging , page , formPage , perPage );
		} catch (Exception e) {
			e.printStackTrace ();
			return null;
		}
	}
	
	/**
	 *  서블릿 호출후 호출결과를 Redirect 사용하는 Method
	 */
	protected void gotoResult(HttpServletRequest req, HttpServletResponse res, String address)
		throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(address);
		dispatcher.forward(req,res);
		return;
	}
	
	/**
	 *<pre>
	 * javascript alert 메시지를 보여준다.
	 *</pre>
	 *
	 * @param msg alert으로 보여줄 메시지
	 * @param res  PrintWriter 얻기위해
	 */
	protected void alert(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
		  	String rtn_msg = "";
		  	rtn_msg = "\n <script language=\"javascript\">"
				  + "\n   alert(\"" + replaceMsg(msg) + "\");"
				  + "\n </script>";
		  	out.println(rtn_msg);
		} catch (Exception e) {
		  	Kogger.error(getClass(), e);
		}
	}

	/**
	*<pre>
   	* javascript alert 메시지를 보여주고 url페이지로 이동한다.
   	*</pre>
   	*
   	* @param msg alert으로 보여줄 메시지
   	* @param url alert후 이동할 url
   	* @param res  PrintWriter 얻기위해
   	*/
	protected void alertNgo(HttpServletResponse res,String msg, String url) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
		  	String rtn_msg = "";
		  	rtn_msg = "\n <script language=\"javascript\">"
						+ "\n   alert(\"" + replaceMsg(msg) + "\");"
						+ "\n   location.href = '" + url + "';"
						+ "\n </script>";
		  	out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
	
	protected void onlyAlertFromException(Exception e, HttpServletResponse res){
		String message = e.getLocalizedMessage();
		if(e.getCause() != null){
			message = e.getCause().getLocalizedMessage();
		}
		onlyAlert(res,  message);
	}
	
	protected void onlyAlert(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
		  	String rtn_msg = "";
		  	rtn_msg = "alert(\"" + replaceMsg(msg) + "\");";
		  	out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
    
	/**
     * 부모창을 submit 한후  메시지를 보여주고 opener를  submit 한후, 윈도우를 close한다.
     * @param res
     */
    protected void OpenerParentSubmitNclose(HttpServletResponse res,String msg) {
        try {
		    res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
            String rtn_msg = "";
            res.setContentType("text/html;charset=euc-kr");
            rtn_msg = "\n<script language=\"javascript\">"
            				+ "\n   alert(\"" + replaceMsg(msg) + "\");"           	
                			+ "\n   opener.parent.document.forms[0].submit();"
                			+ "\n  window.close();"
                			+ "\n </script>";
            out.println(rtn_msg);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }	
	
    /**
    *<pre>
    * javascript alert 메시지를 보여주고 부모창이 url페이지로 이동한다.
    *</pre>
    *
    * @param msg alert으로 보여줄 메시지
    * @param url alert후 부모창이 이동할 url
    * @param res  PrintWriter 얻기위해
    */
    protected void alertNgoNclose(HttpServletResponse res,String msg, String url) {
        try {
            res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">"
                        + "\n   alert(\"" + replaceMsg(msg) + "\");"
                        + "\n   opener.location.href = '" + url + "';"
                        + "\n   window.close();"
                        + "\n </script>";
            out.println(rtn_msg);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }

	/**
	 *<pre>
	 * javascript alert 메시지를 보여주고 history.back(-1)한다.
	 *</pre>
	 *
	 * @param msg alert으로 보여줄 메시지
	 * @param res  PrintWriter 얻기위해
	 */
	protected void alertNback(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
	  		String rtn_msg = "";
	  		rtn_msg = "\n <script language=\"javascript\">"
		  				+ "\n   alert(\"" + replaceMsg(msg) + "\");"
		  				+ "\n   history.back(-1);"
		  				+ "\n </script>";
	  		out.println(rtn_msg);
		} catch (Exception e) {
	  		Kogger.error(getClass(), e);
		}
	}

	/**
	*<pre>
	* javascript alert 메시지를 보여주고 윈도우를 close한다.
	*</pre>
	*
	* @param msg alert으로 보여줄 메시지
	* @param res  PrintWriter 얻기위해
	*/
	protected void alertNclose(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
	  		String rtn_msg = "";
	  		rtn_msg = "\n <script language=\"javascript\">"
		  				+ "\n   alert(\"" + replaceMsg(msg) + "\");"
		  				+ "\n   if(opener!=null) window.close();"
		  				+ "\n </script>";
		  	out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	  } 
	  
	/**
	*<pre>
	* javascript alert 메시지를 보여주고 프레임을 reload한다.
	*</pre>
	*
	* @param msg alert으로 보여줄 메시지
	* @param res  PrintWriter 얻기위해
	*/
	protected void alertNfrmReload(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
	  		String rtn_msg = "";
	  		rtn_msg = "\n <script language=\"javascript\">"
		  				+ "\n   alert(\"" + replaceMsg(msg) + "\");"
		  				+ "\n   parent.location.reload();"
		  				+ "\n </script>";
		  	out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	  } 
	
	protected void alertNreload(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
	  		String rtn_msg = "";
	  		rtn_msg = "\n <script language=\"javascript\">"
		  				+ "\n   alert(\"" + replaceMsg(msg) + "\");"
		  				+ "\n   location.reload();"
		  				+ "\n </script>";
		  	out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	  } 
	
	/**
	*<pre>
	* javascript alert 메시지를 보여주고 opener를 reload 한후, 윈도우를 close한다.
	*</pre>
	*
	* @param msg alert으로 보여줄 메시지
	* @param res  PrintWriter 얻기위해
	*/
	protected void alertNreloadNclose(HttpServletResponse res,String msg) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
			res.setContentType("text/html;charset=euc-kr");
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">"
							+ "\n   alert(\"" + replaceMsg(msg) + "\");"
							+ "\n   opener.location.reload();"
							+ "\n   window.close();"
							+ "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}

    /**
     *<pre>
     * javascript alert 메시지를 보여주고 부모창이 url페이지로 이동한다.
     *</pre>
     *
     * @param msg alert으로 보여줄 메시지
     * @param url alert후 부모창이 이동할 url
     * @param res  PrintWriter 얻기위해
     */
     protected void alertNgoNcloseParent(HttpServletResponse res,String msg, String url) {
         try {
             res.setContentType("text/html;charset=KSC5601");
             PrintWriter out = res.getWriter();
             String rtn_msg = "";
             rtn_msg = "\n <script language=\"javascript\">"
                         + "\n   alert(\"" + replaceMsg(msg) + "\");"
                         + "\n   opener.parent.location.href = '" + url + "';"
                         + "\n   window.close();"
                         + "\n </script>";
             out.println(rtn_msg);
         } catch (Exception e) {
             Kogger.error(getClass(), e);
         }
     }
	
	
	
	protected void closeNreload(HttpServletResponse res) {
		try {
		    res.setContentType("text/html;charset=KSC5601");
			PrintWriter out = res.getWriter();
			res.setContentType("text/html;charset=euc-kr");
			String rtn_msg = "";
			rtn_msg = "\n <script language=\"javascript\">"
							+ "\n   opener.location.reload();"
							+ "\n   window.close();"
							+ "\n </script>";
			out.println(rtn_msg);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
	}
	
	/**
     * 부모창을 submit 한후 창을 닫느다.
     * 
     * @param res
     */
    protected void submitNclose(HttpServletResponse res) {
        try {
		    res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
            res.setContentType("text/html;charset=euc-kr");
            String rtn_msg = "";
            rtn_msg = "\n<script language=\"javascript\">"
                			+ "\n   opener.document.forms[0].submit();"
                			+ "\n  window.close();"
                			+ "\n </script>";
            out.println(rtn_msg);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }
    
    /**
     * 부모창을 submit 한후 alert 메세지를 보여준다..
     * 
     * @param res
     * @param msg
     */
    protected void alertNsubmit(HttpServletResponse res, String msg) {
        try {
		    res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
            res.setContentType("text/html;charset=euc-kr");
            String rtn_msg = "\n<script language=\"javascript\">" 
                					+ "\n opener.document.forms[0].submit();"
                					+ "\n  alert(\"" + replaceMsg(msg) + "\");\n</script>";
            out.println(rtn_msg);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }
    
    /**
     * 부모창을 submit 한후 alert 메세지를 보여주고  자식창을 닫는다.
     * 
     * @param res
     * @param msg
     */
    protected void alertNsubmitNclose(HttpServletResponse res, String msg) {
        try {
		    res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
            res.setContentType("text/html;charset=euc-kr");
            String rtn_msg = "\n<script language=\"javascript\">" 
                					+ "\n opener.document.forms[0].submit();" 
                					+ "\n alert(\"" + replaceMsg(msg) + "\");\n   " + "   window.close();"
                					+ "\n </script>";
            out.println(rtn_msg);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }
    
    private String replaceMsg(String msg)
    {
        msg = msg.replaceAll("\"", "&quot;");
        return msg.replaceAll("\n", " ");
    }
    
    protected void alertSelfLocation(HttpServletResponse res,String msg, String url) {
        try {
            res.setContentType("text/html;charset=KSC5601");
            PrintWriter out = res.getWriter();
            String rtn_msg = "";
            rtn_msg = "\n <script language=\"javascript\">"
                        + "\n   alert(\"" + replaceMsg(msg) + "\");"
                        + "\n   location.href = '" + url + "';"
                        + "\n   window.close();"
                        + "\n </script>";
            out.println(rtn_msg);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        }
    }
}

