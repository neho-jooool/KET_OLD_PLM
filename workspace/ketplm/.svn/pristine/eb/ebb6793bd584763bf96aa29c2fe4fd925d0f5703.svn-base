/**
 *	@(#) PageControl.java
 *	Copyright (c) jerred. All rights reserverd
 * 
 *	@version 1.00
 *	@since jdk 1.4.02
 *	@createdate 2004. 3. 17.
 *	@author Cho Sung Ok, jerred@bcline.com
 *	@desc	
 */

package e3ps.common.web;

import java.io.Serializable;
import java.util.List;

import wt.fc.PagingQueryResult;

public class PageControl implements Serializable {

    private static final long serialVersionUID = 4597456999408259709L;
    public static final int PERPAGE = 20; // 기본 한페이지당 보여주는 리스트 수 ( 전체 공용 사용)
    public static final int FORMPAGE = 15; // 기본 한화면에 보여주는 페이지링크 수 ( 전체 공용 사용)
    private int initPerPage = 10; // 한페이지당 보여주는 리스트 수
    private int initFormPage = 15; // 한화면에 보여주는 페이지링크 수

    private PagingQueryResult result;
    private int topListCount; // 현재 페이지 게시물의 순서, 즉 전체리스트에서 현재 페이지 게시물의 순서 중 가장
	                      // 상위 카운트
    private int pageScope; // 화면에 보이는 페이지 링크

    private long sessionid; // Paging Session ID
    private int totalCount; // 총 게시물 수
    private int currentPage; // 현재 페이지
    private int startPage; // 시작 페이지
    private int endPage; // 마지막 페이지
    private int totalPage; // 총 페이지

    private String href; // 페이징 URL
    private String param = ""; // 페이징 URL 파라미터

    private List<?> resltList;

    private boolean isPostMethod = false; // 페이지 넘기는 방식 ( true : post 방식,
	                                  // false : get 방식 )

    public PageControl() {
    }

    public PageControl(PagingQueryResult _result, int _pageNo, int _initFormPage, int _initPerPage) {
	this.initFormPage = _initFormPage;
	this.initPerPage = _initPerPage;

	this.result = _result;
	sessionid = _result.getSessionId();

	// 현재 페이지 설정
	if (_pageNo <= 0)
	    this.currentPage = 1;
	else
	    this.currentPage = _pageNo;

	// 페이지뷰 설정
	this.pageScope = 0;
	if (currentPage == 1)
	    pageScope = 0;
	else
	    pageScope = (currentPage - 1) / this.initFormPage;
	this.startPage = pageScope * this.initFormPage + 1;
	this.totalPage = 0;
	if (_result.getTotalSize() == 0) {
	    return;
	}
	if (_result.getTotalSize() % this.initPerPage == 0)
	    this.totalPage = _result.getTotalSize() / this.initPerPage;
	else
	    this.totalPage = _result.getTotalSize() / this.initPerPage + 1;
	this.endPage = this.startPage + this.initFormPage - 1;
	if (this.totalPage < this.endPage)
	    this.endPage = this.totalPage;

	this.totalCount = _result.getTotalSize();
	this.topListCount = this.totalCount - ((this.currentPage - 1) * this.initPerPage);
    }

    /**
     * Non-Windchill PageControl
     * 
     * @param _pageNo
     *            현재 페이지 번호
     * @param _initFormPage
     *            한화면에 보여주는 페이지링크 수
     * @param itemPerPage
     *            한페이지당 보여주는 리스트 수
     * @param totalCount
     *            총 게시물 수
     */
    public PageControl(int _pageNo, int _initFormPage, int itemPerPage, int totalCount) {
	this.initFormPage = _initFormPage;
	this.initPerPage = itemPerPage;

	this.totalCount = totalCount;

	// 현재 페이지 설정
	if (_pageNo <= 0)
	    this.currentPage = 1;
	else
	    this.currentPage = _pageNo;

	// 페이지뷰 설정
	this.pageScope = 0;
	if (currentPage == 1)
	    pageScope = 0;
	else
	    pageScope = (currentPage - 1) / this.initFormPage;
	this.startPage = pageScope * this.initFormPage + 1;
	this.totalPage = 0;
	if (totalCount % this.initPerPage == 0)
	    this.totalPage = totalCount / this.initPerPage;
	else
	    this.totalPage = totalCount / this.initPerPage + 1;
	this.endPage = this.startPage + this.initFormPage - 1;
	if (this.totalPage < this.endPage)
	    this.endPage = this.totalPage;

	this.topListCount = this.totalCount - ((this.currentPage - 1) * this.initPerPage);
    }

    /**
     * Non-Windchill PageControl
     * 
     * @param _pageNo
     *            현재 페이지 번호
     * @param _resultList
     *            결과리스트
     * @param _initFormPage
     *            한화면에 보여주는 페이지링크 수
     * @param itemPerPage
     *            한페이지당 보여주는 리스트 수
     * @param totalCount
     *            총 게시물 수
     */
    public PageControl(int _pageNo, List<?> _resultList, int _initFormPage, int itemPerPage, int totalCount) {
	this.initFormPage = _initFormPage;
	this.initPerPage = itemPerPage;

	this.totalCount = totalCount;

	this.resltList = _resultList;

	// 현재 페이지 설정
	if (_pageNo <= 0)
	    this.currentPage = 1;
	else
	    this.currentPage = _pageNo;

	// 페이지뷰 설정
	this.pageScope = 0;
	if (currentPage == 1)
	    pageScope = 0;
	else
	    pageScope = (currentPage - 1) / this.initFormPage;
	this.startPage = pageScope * this.initFormPage + 1;
	this.totalPage = 0;
	if (totalCount % this.initPerPage == 0)
	    this.totalPage = totalCount / this.initPerPage;
	else
	    this.totalPage = totalCount / this.initPerPage + 1;
	this.endPage = this.startPage + this.initFormPage - 1;
	if (this.totalPage < this.endPage)
	    this.endPage = this.totalPage;

	this.topListCount = this.totalCount - ((this.currentPage - 1) * this.initPerPage);
    }

    /**
     * Non-Windchill PageControl
     * 
     * @param pageNo
     *            현재 페이지 번호
     * @param totalCount
     *            총 게시물 수
     */
    public PageControl(int pageNo, int totalCount) {
	this(pageNo, PageControl.FORMPAGE, PageControl.PERPAGE, totalCount);
    }

    public PageControl(PagingQueryResult _result, int _pageNo, int _initFormPage) {
	this(_result, _pageNo, _initFormPage, PageControl.PERPAGE);
    }

    public PageControl(PagingQueryResult _result, int _pageNo) {
	this(_result, _pageNo, PageControl.FORMPAGE);
    }

    public void setHref(String _href) {
	this.href = _href;
    }

    public void setParam(String _param) {
	this.param = _param;
    }

    public void setGetMethod() {
	this.isPostMethod = false;
    }

    public void setPostMethod() {
	this.isPostMethod = true;
    }

    /**
     * @return Returns the isPostMethod.
     */

    public PagingQueryResult getResult() {
	return result;
    } // 검색 결과를 리턴한다.

    public List<?> getResltList() {
	return resltList;
    }

    public int getTopListCount() {
	return this.topListCount;
    } // 리스트 페이지의 게시물 카운트를 리턴한다.

    public int getTotalPage() {
	return this.totalPage;
    } // 총 페이지를 리턴한다.

    public int getTotalCount() {
	return this.totalCount;
    } // 총 게시물 수를 리턴한다.

    public int getCurrentPage() {
	return this.currentPage;
    } // 현재 페이지를 리턴한다.

    public int getStartPage() {
	return startPage;
    } // 시작 페이지를 리턴한다.

    public int getEndPage() {
	return endPage;
    } // 마지막 페이지를 리턴한다.

    public String getHref() {
	return href;
    }

    public long getSessionId() {
	return this.sessionid;
    } // 페이지의 세션 ID를 리턴한다.

    public String getParam() {
	return param;
    }

    public boolean isPostMethod() {
	return isPostMethod;
    }

    public int getInitFormPage() {
	return initFormPage;
    } // 한화면에 보여주는 페이지링크 수

    public int getInitPerPage() {
	return initPerPage;
    } // 한페이지당 보여주는 리스트 수
}
/*
 * $Log: not supported by cvs2svn $ /* Revision 1.3 2010/11/15 07:21:17 thhwang /* *** empty log message *** /* /* Revision 1.1 2010/09/10 04:40:57 syoh /* 최초등록 /* /* Revision 1.1 2009/08/11 04:16:20
 * administrator /* Init Source /* Committed on the Free edition of March Hare Software CVSNT Server. /* Upgrade to CVS Suite for more features and support: /* http://march-hare.com/cvsnt/ /* /*
 * Revision 1.1 2009/02/25 01:26:02 smkim /* 최초 작성 /* Committed on the Free edition of March Hare Software CVSNT Server. /* Upgrade to CVS Suite for more features and support: /*
 * http://march-hare.com/cvsnt/ /* /* Revision 1.5 2008/04/03 08:53:55 sjhan /* *** empty log message *** /* /* Revision 1.3 2008/03/31 07:18:11 sjhan /* *** empty log message *** /* /* Revision 1.2
 * 2007/10/17 04:54:54 sjhan /* *** empty log message *** /* /* Revision 1.1.1.1 2007/04/17 01:21:26 administrator /* no message /* /* Revision 1.1 2006/05/09 02:35:00 shchoi /* *** empty log message
 * *** /* /* Revision 1.1 2006/05/09 01:23:47 shchoi /* *** empty log message *** /* /* Revision 1.1 2005/12/09 12:20:30 shchoi /* *** empty log message *** /* /* Revision 1.4 2005/11/25 01:34:49
 * shchoi /* Non-Windchill PageControl 추가 /*
 */
