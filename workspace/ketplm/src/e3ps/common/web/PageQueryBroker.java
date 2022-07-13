package e3ps.common.web;

import javax.servlet.http.*;

import e3ps.common.util.CommonUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ProjectMemberLink;

import wt.fc.*;
import wt.pds.StatementSpec;
import wt.query.*;

import java.util.*;

public class PageQueryBroker implements wt.method.RemoteAccess, java.io.Serializable {
	
	int psize = 10;
	int cpage = 1;
	int total = 0;
	int pageCount = 10;
	int topListCount = 0;	
	long sessionid;
	
	QuerySpec qs;
	HttpServletRequest req;
	PagingQueryResult result;

	String key;
	
	boolean isAdvanced = false;
	
	static final boolean SERVER = wt.method.RemoteMethodServer.ServerFlag;

	public PageQueryBroker(HttpServletRequest req,QuerySpec spec){
		this(req,spec,"");
	}

	public PageQueryBroker(HttpServletRequest req,QuerySpec spec,String key){
		this.req = req;
		this.qs = spec;
		this.key = key;
		String sessionidstring = req.getParameter("sessionid" + key);
		if(sessionidstring==null || sessionidstring.length() == 0)sessionidstring = "0";
		sessionid = Long.parseLong(sessionidstring.trim());
	}
	
	public void setAdvanced(boolean isAdvanced){
		this.isAdvanced = isAdvanced;
	}
	
	public static PagingQueryResult openPagingSession(int i,int j,StatementSpec qs,boolean isAdvanced)throws Exception{
		
		if(isAdvanced && !SERVER){
			Class argTypes[] = new Class[]{int.class,int.class,StatementSpec.class,boolean.class};
			Object args[] = new Object[]{new Integer(i),new Integer(j),qs,new Boolean(isAdvanced)};
			return (PagingQueryResult)wt.method.RemoteMethodServer.getDefault().invoke(
					"openPagingSession",
					"com.e3ps.common.web.PageQueryBroker",
					null,
					argTypes,
					args);
		}

		if(isAdvanced && !qs.isAdvancedQueryEnabled()) {
			qs.setAdvancedQueryEnabled(true);
		}
		
		return PagingSessionHelper.openPagingSession(i, j, qs);
	}
	public static PagingQueryResult fetchPagingSession(int i,int j,long sessionid,StatementSpec qs,boolean isAdvanced)throws Exception{
		
		try{
			return PagingSessionHelper.fetchPagingSession(i, j, sessionid);
		}catch(Exception ex){
			return openPagingSession(i, j, qs, isAdvanced);
		}
	}
	
	public PagingQueryResult search() throws Exception{
		String pagestring = req.getParameter("tpage" + key);
		if(pagestring!=null && pagestring.length()>0)cpage = Integer.parseInt(pagestring);
		String psizestring = req.getParameter("psize" + key);
		if(psizestring!=null && psizestring.length()>0)psize = Integer.parseInt(psizestring);
		if(sessionid <= 0){
			result = openPagingSession(0, psize, qs,isAdvanced);
		}
		else{
			result = fetchPagingSession((cpage-1) * psize, psize, sessionid,qs,isAdvanced);
		}
		if(result!=null){
			total = result.getTotalSize();
			sessionid = result.getSessionId();
			topListCount = total - ((cpage - 1) * psize);
		}
		return result;
	}
	
	public void setPsize(int psize){
		this.psize = psize;
	}
	public void setCpage(int cpage){
		this.cpage = cpage;
	}
	public void setTotal(int total){
		this.total = total;
	}
	public void setPageCount(int pageCount){
		this.pageCount = pageCount;
	}
	public void setSessionid(long sessionid){
		this.sessionid = sessionid;
	}
	public void setQuerySpec(QuerySpec qs){
		this.qs = qs;
	}
	public void setRequest(HttpServletRequest request){
		this.req = request;
	}
	public void setResult(PagingQueryResult result){
		this.result = result;
	}
	public int getPsize(){
		return psize;
	}
	public int getCpage(){
		return  cpage;
	}
	public int getTotal(){
		return  total;
	}
	public int getPageCount(){
		return  pageCount;
	}
	public long getSessionid(){
		return  sessionid;
	}
	public QuerySpec getQuerySpec(){
		return  qs;
	}
	public HttpServletRequest getRequest(){
		return  req;
	}
	public PagingQueryResult getResult(){
		return  result;
	}
	
	public String getHtml(String formName){
		
		int ksize = total/psize;
		int x = total%psize;
		if(x>0) ksize++;
		int temp = cpage/pageCount;
		if( (cpage%pageCount)>0)temp++;
		int start = (temp-1)*pageCount+1;
		int end = start + pageCount-1;
		if(end> ksize){
			end = ksize;
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("function gotoPageQuery"+formName+key+"(p){");
		sb.append("document."+formName+".sessionid"+key+".value='"+sessionid+"';");
		sb.append("document."+formName+".tpage"+key+".value=p;");
		sb.append("document."+formName+".submit();");
		sb.append("}");
		sb.append("</script>");
		
		sb.append("<input type=hidden name=sessionid"+key+" value="+sessionid+">");
		sb.append("<input type=hidden name=tpage"+key+" value="+cpage+">");

		
		sb.append("<table border=0 cellspacing=0 cellpadding=0 width=100% align=center bgcolor=white>");
		sb.append("<tr bgcolor=white>");
		//if (CommonUtil.isUSLocale()) sb.append("  <td class='small' width=200><span class='small'>[Pages:"+ksize+"][Items:"+total+"]</span></td>");
		sb.append("  <td class='small' width=200><span class='small'>(전체페이지:"+ksize+")</span></td>");
		sb.append("  <td>");
		sb.append("		<table border=0 align=center cellpadding=0 cellspacing=0  bgcolor=white>");
		sb.append("			<tr  bgcolor=white>");
		sb.append("				<td width='30' align='center'>");
		
		if(start>1){
			sb.append("<a href='JavaScript:gotoPageQuery"+formName+key+"(1)' class='small'><img src='/plm/portal/images/btn_arrow4.gif' border='0' align='middle'></a>");
		}
		sb.append("</td>");
		sb.append("				<td width='1' bgcolor='#dddddd'></td>");
		
		if(start>1){
		sb.append("				<td width='30' class='quick' align='center'><a href='JavaScript:gotoPageQuery"+formName+key+"("+(start-1)+")' class='smallblue'><img src='/plm/portal/images/btn_arrow3.gif' border='0' align='middle'></a></td>");
		sb.append("				<td width='1' bgcolor='#dddddd'></td>");
		}
		
		for(int i=start; i<= end; i++){

		sb.append("				<td style='padding:2 8 0 7;cursor:hand' onMouseOver='this.style.background=\"#ECECEC\"' OnMouseOut='this.style.background=\"\"' class='nav_on' onclick='gotoPageQuery"+formName+key+"("+i+")'>");
			
			if(i==cpage){ sb.append("<b>"); }
			sb.append(""+i+"</td>");
		}
		if(end < ksize){
		sb.append("				<td width='1' bgcolor='#dddddd'></td>");
		sb.append("				<td width='30' align='center'><a href='JavaScript:gotoPageQuery"+formName+key+"("+(end+1)+")' class='smallblue'><img src='/plm/portal/images/btn_arrow1.gif' border='0' align='middle'></a></td>");
		};
		sb.append("				<td width='1' bgcolor='#dddddd'></td>");
		sb.append("				<td width='30'align='center'>");
		
		if(end<ksize){
			sb.append("<a href='JavaScript:gotoPageQuery"+formName+key+"("+ksize+")' class='small'><img src='/plm/portal/images/btn_arrow2.gif' border='0' align='middle'></a>");
		}
		sb.append("				</td>");
		sb.append("			</tr>");
		sb.append("		</table>");
		sb.append("  </td>");
		sb.append("  <td class='small' width=200 align='right'><span class='small'>(전체개수:"+total+")</span></td>");
		sb.append("</tr>");
		sb.append("</table>");
		
		return sb.toString();
	}
	public int getTopListCount() {
		return topListCount;
	}

	public void setTopListCount(int topListCount) {
		this.topListCount = topListCount;
	}
}
