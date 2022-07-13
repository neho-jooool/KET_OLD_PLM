/*
 * @(#) EncodingFilter.java  2004. 9. 7.
 * Copyright (c) e3ps. All rights reserverd
 */
package e3ps.common.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * ServletRequest객체에 EUC-KR 인코딩을 설정하는 Filter클래스.
 * <br>
 * web.xml에 아래 사항을 추가만 하면 된다.<br>
 * &lt;filter&gt;<br>
 *	&nbsp;&nbsp;    &lt;filter-name&gt;Encoding Filter&lt;/filter-name&gt;<br>
 *	&nbsp;&nbsp;    &lt;display-name&gt;Encoding Filter&lt;/display-name&gt;<br>
 *	&nbsp;&nbsp;    &lt;filter-class&gt;e3ps.common.web.EncodingFilter&lt;/filter-class&gt;<br>
 *	&nbsp;&nbsp;    &lt;init-param&gt;<br>
 *	&nbsp;&nbsp;&nbsp;	    &lt;param-name&gt;encoding&lt;/param-name&gt;<br>
 *	&nbsp;&nbsp;&nbsp;	    &lt;param-value&gt;EUC-KR&lt;/param-value&gt;<br>
 *	&nbsp;&nbsp;    &lt;/init-param&gt;<br>
 * &lt;/filter&gt;<br>
 * &lt;filter-mapping&gt;<br>
 *	&nbsp;&nbsp;    &lt;filter-name&gt;Encoding Filter&lt;/filter-name&gt;<br>
 *	&nbsp;&nbsp;    &lt;url-pattern&gt;/servlet/*&lt;/url-pattern&gt;<br>
 * &lt;/filter-mapping&gt;<br>
 */
public class EncodingFilter implements Filter {
	private String encoding;
	protected FilterConfig filterConfig;

	public void destroy() {
		this.encoding = null;
		this.filterConfig = null;
	}

	/**
	 * ServletRequest객체에 web.xml에서 전달된 인코딩을 설정하는 메써드.
	 */
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		if (request.getCharacterEncoding() == null) {
			if (encoding != null) {
				request.setCharacterEncoding(encoding);
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * web.xml에서 전달된 인코딩 값을 초기화하는 메써드.
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}

	public FilterConfig getFilterConfig() {
		return filterConfig;
	}

	public void setFilterConfig(FilterConfig cfg) {
		filterConfig = cfg;
	}
}
