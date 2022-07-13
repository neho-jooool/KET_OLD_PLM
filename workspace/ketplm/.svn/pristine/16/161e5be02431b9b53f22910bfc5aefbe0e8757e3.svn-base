/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : WFBomViewDao.java
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 23.
 */
package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : WFBomViewDao
 * 설명 : 결재 BOM View 조회 
 * 작성자 : 신대범
 * 작성일자 : 2010. 11. 01.
 */
public class WFBomViewDao {
	
	private Connection conn;
	public boolean isLeafLevel1 = false;
	public String[] isLineExist = {"false", "false", "false", "false", "false", "false", "false", "false", "false", "false", "false"};  // 0번째는 사용안함 (1레벨부터 그려지므로)
	
	public WFBomViewDao(Connection conn){
		this.conn = conn;
	}
	
	/**
	 * 함수명 : ViewWFBomList
	 * 설명 : 
	 * @param hash
	 * @return ArrayList
	 * @throws Exception 
	 * @throws 
	 * 작성자 : 신대범
	 * 작성일자 : 2010. 11. 23.
	 */
	public ArrayList ViewWFBomList ( Hashtable hash ) throws Exception{
		LoggableStatement pstmt = null;
		int pstmtCnt = 1;
		StringBuffer sb = null;
		ResultSet rs = null;
		ArrayList<Hashtable<String, String>> wfTreeList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> wfTree = null;
		String headCode = "";
		try {
			headCode = ViewWFBomHeadStr((String) hash.get("wtcode"));

			Kogger.debug(getClass(), "----------------------쿼리실행시작 : "+headCode);
			
			sb = new StringBuffer();
			
			sb.append( "   SELECT rownum*10 as row_id, LPAD(' ', LEVEL*4) as space																											\n" );
			sb.append( "             , parentitemcode,  childitemcode  as itemcode, bomlevel, itemseq, sequencenumber																	\n" );
			 sb.append( "    		, material, hardnessfrom, hardnessto ,  designdate																											\n" );
			 sb.append( "    		, name as itemname , version, status, quantity, unit, usernm   																								\n" );
			 sb.append( "   FROM    																																										\n" );
			 sb.append( "           (																																										\n" );
			 sb.append( "    		select * from 																																						\n" );
			 sb.append( "    				(																																								\n" );
			 sb.append( "    				select 	 k.newbomcode, '' parentitemcode, k.newbomcode childitemcode, '0' bomlevel, 10 itemseq 											\n" );
			 sb.append( "    						, k.quantity , k.unitofquantity as unit, '0001' as sequencenumber 																			\n" );
			 sb.append( "    						, '' as material, '' as hardnessfrom, '' as hardnessto , '' as designdate 																	\n" );
			 sb.append( "    						, m.name, ph.name as status, p.versionida2versioninfo as version, u.name as usernm													\n" );
			 sb.append( "    				from 	 ketbomheader k, people u																														\n" );
			 sb.append( "    				        , wtpart p, wtpartmaster m, phasetemplate ph, phaselink pl																					\n" );
			 sb.append( "    				where	  k.newbomcode = '"+headCode+"'																												\n" );
			 sb.append( "    			     and     m.wtpartnumber = k.newbomcode																											\n" );
			 sb.append( "    				 and     m.ida2a2=p.ida3masterreference																												\n" );
			 sb.append( "    				 and     pl.ida3a5 = p.ida3a2state																														\n" );
			 sb.append( "    				 and     pl.ida3b5 = ph.ida2a2																															\n" );
			 sb.append( "    				 and     ph.phasestate = p.statestate																													\n" );
			 sb.append( "    				 and     p.statecheckoutinfo != 'wrk'																													\n" );
			 sb.append( "    				 and     p.latestiterationinfo = 1																															\n" );
			 sb.append( "    				 and     p.versionida2versioninfo = 0																													\n" );
			 sb.append( "					 and     k.ida3a7 = u.ida3b4																																\n" );
			 sb.append( "    				union all																																						\n" );
			 sb.append( "    				select   t.newbomcode, t.parentitemcode, t.childitemcode, t.bomlevel, to_number(t.itemseq) as itemseq 										\n" );
			 sb.append( "    						, t.quantity, t.unit, t.sequencenumber , t.material, t.hardnessfrom, t.hardnessto, t.designdate 											\n" );
			 sb.append( "    						, wm.name, ph.name as status, p.versionida2versioninfo as version, '' as usernm														\n" );
			 sb.append( "    				from 	 ketbomcomponent t,	 wtpart p,   wtpartmaster wm																							\n" );
			 sb.append( "    				        , phasetemplate ph, phaselink pl																													\n" );
			 sb.append( "    				where  t.newbomcode = '"+headCode+"'																												\n" );
			 sb.append("						and 	 wm.wtpartnumber = t.childitemcode																											\n" );
			 sb.append( "    				and 	 wm.ida2a2 = p.ida3masterreference																												\n" );
			 sb.append( "    				and 	 pl.ida3a5 = p.ida3a2state																															\n" );
			 sb.append( "    				and 	 pl.ida3b5 = ph.ida2a2																																\n" );
			 sb.append( "    				and 	 ph.phasestate = p.statestate																														\n" );
			 sb.append( "    				and 	 p.statecheckoutinfo != 'wrk'																														\n" );
			 sb.append( "    				and 	 p.latestiterationinfo = 1																															\n" );
			 sb.append( "    				and 	 p.versionida2versioninfo = 0																														\n" );
			 sb.append( "					order by  bomlevel, itemseq																																\n" );
			 sb.append( "    			) ut																																								\n" );
			 sb.append( "           )																																										\n" );
			 sb.append( "  WHERE  newbomcode = '"+headCode+"'																																\n" );
			 sb.append( "  START WITH childitemcode  = '"+headCode+"'																															\n" );
			 sb.append( "  CONNECT BY PRIOR childitemcode  = parentitemcode 																												\n" );
			
			pstmt = new LoggableStatement( conn, sb.toString() );
			
			Kogger.debug(getClass(), "----------------------쿼리실행 : "+pstmt.getQueryString());
			rs = pstmt.executeQuery();
			
			String itemCd = "";
			String bomlv = "";
			String itemseq = "";
			String parentItem = "";
			String trId = "";
			String userNmTmp = "";
			String userNm = "";
			String subCode = "";
			String subName = "";
			while (rs.next()){
				itemCd = StringUtil.checkNull(rs.getString("itemcode"));
				bomlv = StringUtil.checkNull(rs.getString("bomlevel"));
				itemseq = StringUtil.checkNull(rs.getString("itemseq"));
				parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
				trId = StringUtil.checkNull(rs.getString("sequencenumber"));
				userNmTmp = StringUtil.checkNull(rs.getString("usernm"));
				subCode = this.getSubItemInfo( headCode, parentItem, itemCd );
				
				if(!userNmTmp.equals(""))
				{
					userNm = userNmTmp;
				}
				
				String lastChk = CheckLastItemStr(headCode, itemCd, trId);
				wfTree = new Hashtable<String, String>();
				wfTree.put("row_id", StringUtil.checkNull(rs.getString("row_id"))); 
				wfTree.put("space", StringUtil.checkNull(rs.getString("space"))); 
				wfTree.put("itemcode", itemCd); 
				wfTree.put("bomlevel", bomlv); 
				wfTree.put("itemseq", itemseq); 
				wfTree.put("itemname", StringUtil.checkNull(rs.getString("itemname"))); 
				wfTree.put("sequencenumber", trId); 
				wfTree.put("version", StringUtil.checkNull(rs.getString("version"))); 
				wfTree.put("status", StringUtil.checkNull(rs.getString("status"))); 
				wfTree.put("quantity", StringUtil.checkNull(rs.getString("quantity"))); 
				wfTree.put("unit", StringUtil.checkNull(rs.getString("unit"))); 
				//wfTree.put("lastchk", lastChk); 
				wfTree.put("crosschk", CheckCrossStr(headCode, parentItem, bomlv, itemseq, lastChk, trId )); 
				wfTree.put("linechk", CheckLineStr(headCode, itemCd, bomlv)); 
				wfTree.put("material", StringUtil.checkNull(rs.getString("material"))); 
				wfTree.put("hardnessfrom", StringUtil.checkNull(rs.getString("hardnessfrom"))); 
				wfTree.put("hardnessto", StringUtil.checkNull(rs.getString("hardnessto")));  
				wfTree.put("designdate", StringUtil.checkNull(rs.getString("designdate")));  
				wfTree.put("usernm", userNm);  
				wfTree.put("subcode", subCode);  
				wfTree.put("subname", subName);  
				
				wfTreeList.add(wfTree);
			}
			
			Kogger.debug(getClass(), "----------------------쿼리실행종료");
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		
		return wfTreeList;
	}
	
	//oid로 head itemcode 가져옴.
	public String ViewWFBomHeadStr( String wtCode ) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    String reSult = "";
		try {
			sb = new StringBuffer();
			sb.append( " select bh.newbomcode from 											\n" );
			sb.append( " ketbomheader bh, ketwfmapprovalmaster wm						\n" );
//			sb.append( " where bh.ida3a7 = wm.ida3owner									\n" );
			sb.append( " where bh.ida2a2 = wm.ida3b4										\n" );
			sb.append( " and   wm.ida3b4	= '"+wtCode+"' 									\n" );
//Kogger.debug(getClass(), "----------------------쿼리1 : "+sb.toString());	
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				reSult = StringUtil.checkNull(rs.getString("newbomcode"));
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		//Kogger.debug(getClass(), "----------------------리턴값 : "+reSult);
		return reSult;
	}
	
	// 자식노드를 가지고있는지 체크
	public String CheckLastItemStr( String headCode , String itemCode , String trId) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    //String reSult = "<img style='vertical-align:top;' src='/plm/portal/images/img_wtbom/page.gif'>";
		String reSult = "";
		try {
			sb = new StringBuffer();
			sb.append( " select * from ketbomcomponent t 		\n" );
			sb.append( " where newbomcode = '"+headCode+"'	\n" );
			sb.append( " and parentitemcode = '"+itemCode+"'	\n" );
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				//reSult = "<img style='vertical-align:top;' src='/plm/portal/images/img_wtbom/folderopen.gif' style='cursor:hand' onclick=\"javascript:tree_comp('"+trId+"')\" id='img_"+trId+"'>";
				reSult = "OK";
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		return reSult;
	}
	
	//부모 item의 하위 seq가 있는지 체크하여 선을 넣어줌.
	public String CheckLineStr( String headCode , String itemCode, String bomlv ) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    String reSult = "";
	    Vector vc = new Vector();
		try {
			sb = new StringBuffer();
			
			sb.append( " select distinct k.bomlevel from ketbomcomponent k, 						\n" );
			sb.append( " (																						\n" );
			sb.append( " SELECT childitemcode ,bomlevel, itemseq FROM ketbomcomponent		\n" );
			sb.append( " WHERE  newbomcode = '"+headCode+"'										\n" );
			sb.append( "          START WITH childitemcode  = '"+itemCode+"'						\n" );
			sb.append( "          CONNECT BY  childitemcode  = PRIOR parentitemcode 			\n" );
			sb.append( " ) t																						\n" );
			sb.append( " where k.bomlevel = t.bomlevel													\n" );
			sb.append( " and  to_number(k.itemseq) > to_number(t.itemseq)							\n" );
			sb.append( " and t.childitemcode  <> '"+itemCode+"'										\n" );
			sb.append( " order by bomlevel 																	\n" );
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				vc.add(StringUtil.checkNull(rs.getString("bomlevel")));
			}
				
			for(int i=0 ; i< new Integer(bomlv) ;i++)
			{
				if(vc.contains(String.valueOf(i)))
				{
					if ( isLineExist[i].equals("false") ) {
						reSult = reSult+"<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/line.gif'>";
					} else { 
						reSult = reSult+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				}else{
					reSult = reSult+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				if(i==0){ reSult = "";}
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		return reSult;
	}
	
	//동일레벨 하위가 있는지 체크
	public String CheckCrossStr( String headCode , String itemCode , String bomlv, String itemseq, String lastChk , String trId) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    String reSult = "";
	    
	    if(lastChk.equals("OK"))
	    {
	    	reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/minusbottom.gif' style='cursor:hand' onclick=\"javascript:tree_comp('"+trId+"', '2' )\" id='img_"+trId+"'>";	// 동일레벨 최하위 부품(하위노드를가짐)
	    	isLineExist[Integer.parseInt(bomlv)] = "true";
	    }else{
	    	reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/joinbottom.gif'>";		// 하위노드가 없는 동일레벨 최하단 부품('ㄴ')
	    }
		try {
			sb = new StringBuffer();
			sb.append( " select childitemcode from ketbomcomponent t  		\n" );
			sb.append( " WHERE  newbomcode = '"+headCode+"'				\n" );
			sb.append( " and  parentitemcode  = '"+itemCode+"' 				\n" );
			sb.append( " and bomlevel = '"+bomlv+"'  							\n" );
			sb.append( " and to_number(itemseq) > "+itemseq+"  				\n" );
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				if(lastChk.equals("OK"))
				{
					reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/minus.gif' style='cursor:hand' onclick=\"javascript:tree_comp('"+trId+"', '1' )\" id='img_"+trId+"'>";	// 동일레벨 하위에 부품이 존재하는 부품(하위노드를가진) 인 경우
					isLineExist[Integer.parseInt(bomlv)] = "false";
				}else{
					reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/join.gif'>";	// 하위노드가 존재하는 동일레벨 중간 부품('ㅏ')
				}
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		return reSult;
	}
	
	//대체 부품 정보
	public String getSubItemInfo( String headcode, String parent, String itemCd ) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		String reSult = "";
		boolean isExist = false;
		
		try {
			sb = new StringBuffer();
			sb.append( " select t.substituteitemcode as subcode								\n" );
			sb.append( " from ketbomsubstitute t												\n" );
			sb.append( " where t.newbomcode = '"+headcode+"'							\n" );
			sb.append( " and t.parentitemcode = '"+parent+"'								\n" );
			sb.append( " and t.childitemcode = '"+itemCd+"'									\n" );

//Kogger.debug(getClass(), "----------------------쿼리1 : "+sb.toString());	
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				isExist = true;
				reSult = reSult + ","+ StringUtil.checkNull(rs.getString("subcode"));
			}
			// 결과가 있는 경우에만 잘라야 함 
			if (isExist) {
				reSult = reSult.substring(1,reSult.length());
			}
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		//Kogger.debug(getClass(), "----------------------리턴값 : "+reSult);
		return reSult;
	}
	
	//bomeco--------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	/**
	 * 함수명 : ViewWFBomEcoList
	 * 설명 : 
	 * @param hash
	 * @return ArrayList
	 * @throws Exception 
	 * @throws 
	 * 작성자 : 신대범
	 * 작성일자 : 2010. 12. 06.
	 */
	public ArrayList ViewWFBomEcoList ( Hashtable hash ) throws Exception{
		LoggableStatement pstmt = null;
		int pstmtCnt = 1;
		StringBuffer sb = null;
		ResultSet rs = null;
		ArrayList<Hashtable<String, String>> wfTreeList = new ArrayList<Hashtable<String, String>>();
		Hashtable<String, String> wfTree = null;
		String headCode = "";
		String ecoCode = "";
		try {
			Hashtable ht = ViewWFBomEcoHeadStr((String) hash.get("wtcode"));
			headCode = (String)ht.get("headitem");
			ecoCode = (String)ht.get("ecocode");
			//Kogger.debug(getClass(), "----------------------쿼리실행시작 : "+headCode);
			
			sb = new StringBuffer();
			
			sb.append( " SELECT rownum*10 as row_id, LPAD(' ', LEVEL*4) as space , ecoheadernumber														\n" );
			sb.append( "          ,parentitemcode,  childitemcode  as itemcode, bomlevel, itemseq, sequencenumber										\n" ); 
			sb.append( "          , material, hardnessfrom, hardnessto, designdate																				\n" ); 
			sb.append( "          , name as itemname , version, status, quantity, unit, usernm																	\n" ); 
			sb.append( " FROM																																				\n" ); 
			sb.append( "          (																																			\n" ); 
			sb.append( "          select * from																															\n" ); 
			sb.append( "                 (																																	\n" ); 
			sb.append( "                 select 	k.ecoheadernumber, '' parentitemcode, k.ecoitemcode childitemcode, '0' bomlevel, 10 itemseq		\n" ); 
			sb.append( "                           , k.quantity , k.unitofquantity as unit, '0001' as sequencenumber												\n" ); 
			sb.append( "                           , '' as material, '' as hardnessfrom, '' as hardnessto, '' as designdate   										\n" ); 
			sb.append( "                           , w.name, w.status, w.version, u.name as usernm																\n" ); 
			sb.append( "                 from 		ketbomecoheader k, people u,																					\n" ); 
			sb.append( "                            (																														\n" ); 
			sb.append( "                             select 	ph.name as status , m.wtpartnumber, m.name , versionida2versioninfo as version			\n" ); 
			sb.append( "                             from 		wtpart p, wtpartmaster m, phasetemplate ph, phaselink pl										\n" ); 
			sb.append( "                             where 	m.wtpartnumber='"+headCode+"'																	\n" ); 
			sb.append( "                             and 		m.ida2a2=p.ida3masterreference																		\n" ); 
			sb.append( "                             and 		pl.ida3a5 = p.ida3a2state																				\n" ); 
			sb.append( "                             and 		pl.ida3b5 = ph.ida2a2																					\n" ); 
			sb.append( "                             and 		ph.phasestate = p.statestate																			\n" ); 
			sb.append( "    						 	and 		p.statecheckoutinfo != 'wrk'																			\n" );
			sb.append( "    							and 		p.latestiterationinfo = 1																				\n" );
			sb.append( "                             ) w																													\n" ); 
			sb.append( "                 where 	k.ecoheadernumber  = '"+ecoCode+"'																			\n" ); 
			sb.append( "                 and 		w.wtpartnumber = k.ecoitemcode 																				\n" ); 
			sb.append( "                 and k.ida3a7 = u.ida3b4																										\n" ); 
			sb.append( "                 union all																															\n" ); 
			sb.append( "                 select 	t.ecoheadernumber , t.parentitemcode, t.childitemcode, t.bomlevel, to_number(t.itemseq) as itemseq	\n" ); 
			sb.append( "                           , t.afterquantity as quantity, t.afterunit as unit, t.sequencenumber 											\n" ); 
			sb.append( "                           , t.aftermaterial material, t.afterhardnessfrom hardnessfrom, t.afterhardnessto hardnessto				\n" ); 
			sb.append( "                           , afterdesigndate as designdate , wm.name, w.status, w.version, '' as usernm								\n" ); 
			sb.append( "                 from 		ketbomecocomponent t,  wtpartmaster wm,																	\n" ); 
			sb.append( "                            (																														\n" ); 
			sb.append( "                             select 	ph.name as status , m.wtpartnumber, m.name , versionida2versioninfo as version			\n" ); 
			sb.append( "                             from 		wtpart p, wtpartmaster m, phasetemplate ph, phaselink pl										\n" ); 
			sb.append( "                             where 	m.wtpartnumber='"+headCode+"'																	\n" ); 
			sb.append( "                             and 		m.ida2a2=p.ida3masterreference																		\n" ); 
			sb.append( "                             and 		pl.ida3a5 = p.ida3a2state																				\n" ); 
			sb.append( "                             and 		pl.ida3b5 = ph.ida2a2																					\n" ); 
			sb.append( "                             and 		ph.phasestate = p.statestate																			\n" ); 
			sb.append( "    						 	and 		p.statecheckoutinfo != 'wrk'																			\n" );
			sb.append( "    							and 		p.latestiterationinfo = 1																				\n" );
			sb.append( "                            ) w																														\n" ); 
			sb.append( "                 where t.ecoheadernumber = '"+ecoCode+"'          																		\n" ); 
			sb.append( "                 and wm.wtpartnumber = t.childitemcode																					\n" ); 
			sb.append( "					and t.afterquantity > 0																											\n" ); 
			sb.append( "                 order by bomlevel, itemseq																									\n" ); 
			sb.append( "                ) ut																																	\n" ); 
			sb.append( "           )																																			\n" ); 
			sb.append( " WHERE  	ecoheadernumber = '"+ecoCode+"'																								\n" ); 
			sb.append( " START WITH childitemcode  = '"+headCode+"'																							\n" ); 
			sb.append( " CONNECT BY PRIOR childitemcode  = parentitemcode																					\n" ); 
			
			pstmt = new LoggableStatement( conn, sb.toString() );
			
			Kogger.debug(getClass(), "----------------------쿼리실행 : "+pstmt.getQueryString());
			rs = pstmt.executeQuery();
			
			String itemCd = "";
			String bomlv = "";
			String itemseq = "";
			String parentItem = "";
			String trId = "";
			String userNmTmp = "";
			String userNm = "";
			String subCode="";
			String subName="";
			while (rs.next()){
				itemCd = StringUtil.checkNull(rs.getString("itemcode"));
				bomlv = StringUtil.checkNull(rs.getString("bomlevel"));
				itemseq = StringUtil.checkNull(rs.getString("itemseq"));
				parentItem = StringUtil.checkNull(rs.getString("parentitemcode"));
				trId = StringUtil.checkNull(rs.getString("sequencenumber"));
				userNmTmp = StringUtil.checkNull(rs.getString("usernm"));
				subCode =  this.getEcoSubItemInfo( ecoCode, parentItem, itemCd );
				
				if(!userNmTmp.equals(""))
				{
					userNm = userNmTmp;
				}
				String lastChk = CheckBomEcoLastItemStr(ecoCode, itemCd, trId);
				wfTree = new Hashtable<String, String>();
				wfTree.put("row_id", StringUtil.checkNull(rs.getString("row_id"))); 
				wfTree.put("space", StringUtil.checkNull(rs.getString("space"))); 
				wfTree.put("econo", StringUtil.checkNull(rs.getString("ecoheadernumber"))); 
				wfTree.put("itemcode", itemCd); 
				wfTree.put("bomlevel", bomlv); 
				wfTree.put("itemseq", itemseq); 
				wfTree.put("itemname", StringUtil.checkNull(rs.getString("itemname"))); 
				wfTree.put("sequencenumber", trId); 
				wfTree.put("version", StringUtil.checkNull(rs.getString("version"))); 
				wfTree.put("status", StringUtil.checkNull(rs.getString("status"))); 
				wfTree.put("quantity", StringUtil.checkNull(rs.getString("quantity"))); 
				wfTree.put("unit", StringUtil.checkNull(rs.getString("unit"))); 
				//wfTree.put("lastchk", lastChk); 
				wfTree.put("crosschk", CheckBomEcoCrossStr(ecoCode, parentItem, bomlv, itemseq, lastChk, trId )); 
				wfTree.put("linechk", CheckBomEcoLineStr(ecoCode, itemCd, bomlv)); 
				wfTree.put("material", StringUtil.checkNull(rs.getString("material"))); 
				wfTree.put("hardnessfrom", StringUtil.checkNull(rs.getString("hardnessfrom"))); 
				wfTree.put("hardnessto", StringUtil.checkNull(rs.getString("hardnessto")));  
				wfTree.put("designdate", StringUtil.checkNull(rs.getString("designdate")));  
				wfTree.put("usernm", userNm);  
				wfTree.put("subcode", subCode);  
				wfTree.put("subname", subName);  
				
				wfTreeList.add(wfTree);
			}
			
			Kogger.debug(getClass(), "----------------------쿼리실행종료");
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		
		return wfTreeList;
	}
	
	//oid로 head itemcode 가져옴.
	public Hashtable ViewWFBomEcoHeadStr( String wtCode ) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		Hashtable reSult = new Hashtable();
		try {
			sb = new StringBuffer();
			sb.append( " select bh.ecoitemcode, ecoheadernumber from 					\n" );
			sb.append( " ketbomecoheader bh, ketwfmapprovalmaster wm				\n" );
			sb.append( " where bh.ida3a7 = wm.ida3owner									\n" );
			sb.append( " and bh.ida2a2 = wm.ida3b4											\n" );
			sb.append( " and wm.ida3b4	= '"+wtCode+"' 										\n" );
//Kogger.debug(getClass(), "----------------------쿼리1 : "+sb.toString());	
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				reSult.put("headitem", StringUtil.checkNull(rs.getString("ecoitemcode")));
				reSult.put("ecocode", StringUtil.checkNull(rs.getString("ecoheadernumber")));
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		//Kogger.debug(getClass(), "----------------------리턴값 : "+reSult);
		return reSult;
	}
	
	//트리의 마지막인지 체크. 
	public String CheckBomEcoLastItemStr( String ecoCode , String itemCode , String trId) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    //String reSult = "<img style='vertical-align:top;' src='/plm/portal/images/img_wtbom/page.gif'>";
		String reSult = "";
		try {
			sb = new StringBuffer();
			sb.append( " select * from ketbomecocomponent t 		\n" );
			sb.append( " where ecoheadernumber = '"+ecoCode+"'	\n" );
			sb.append( " and parentitemcode = '"+itemCode+"'	    \n" );
			sb.append( " and afterquantity > 0	                        \n" );
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			if (rs.next()){
				//reSult = "<img style='vertical-align:top;' src='/plm/portal/images/img_wtbom/folderopen.gif' style='cursor:hand' onclick=\"javascript:tree_comp('"+trId+"')\" id='img_"+trId+"'>";
				reSult = "OK";
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		return reSult;
	}
	
	//부모 item의 하위 seq가 있는지 체크하여 선을 넣어줌.
	public String CheckBomEcoLineStr( String ecoCode , String itemCode, String bomlv ) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    String reSult = "";
	    Vector vc = new Vector();
		try {
			sb = new StringBuffer();
			
			sb.append( " select distinct k.bomlevel from ketbomecocomponent k, 					\n" );
			sb.append( " (																						\n" );
			sb.append( " SELECT childitemcode ,bomlevel, itemseq FROM ketbomecocomponent	\n" );
			sb.append( " WHERE  ecoheadernumber = '"+ecoCode+"'									\n" );
			sb.append( "          START WITH childitemcode  = '"+itemCode+"'						\n" );
			sb.append( "          CONNECT BY  childitemcode  = PRIOR parentitemcode 			\n" );
			sb.append( " ) t																						\n" );
			sb.append( " where k.bomlevel = t.bomlevel													\n" );
			sb.append( " and  to_number(k.itemseq) > to_number(t.itemseq)							\n" );
			sb.append( " and t.childitemcode  <> '"+itemCode+"'										\n" );
			sb.append( " and k.afterquantity > 0	                        							    \n" );
			sb.append( " order by bomlevel 																	\n" );
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				vc.add(StringUtil.checkNull(rs.getString("bomlevel")));
			}
			
			for(int i=0 ; i< new Integer(bomlv) ;i++)
			{
				if(vc.contains(String.valueOf(i)))
				{
				reSult = reSult+"<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/line.gif'>";
				}else{
				reSult = reSult+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";	
				}
				if(i==0){ reSult = "";}
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		return reSult;
	}
	
	//동일레벨 하위가 있는지 체크
	public String CheckBomEcoCrossStr( String ecoCode , String itemCode , String bomlv, String itemseq, String lastChk , String trId) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
	    String reSult = "";
	    if(lastChk.equals("OK"))
	    {
	    	reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/minusbottom.gif' style='cursor:hand' onclick=\"javascript:tree_comp('"+trId+"', '2' )\" id='img_"+trId+"'>";
	    }else{
	    	reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/joinbottom.gif'>";	
	    }
		try {
			sb = new StringBuffer();
			sb.append( " select childitemcode from ketbomecocomponent t  	\n" );
			sb.append( " WHERE  ecoheadernumber = '"+ecoCode+"'			\n" );
			sb.append( " and  parentitemcode  = '"+itemCode+"' 				\n" );
			sb.append( " and bomlevel = '"+bomlv+"'  							\n" );
			sb.append( " and to_number(itemseq) > "+itemseq+"  				\n" );
			sb.append( " and t.afterquantity > 0	                        		    \n" );
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
		
			if (rs.next()){
				if(lastChk.equals("OK"))
				{
					reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/minus.gif' style='cursor:hand' onclick=\"javascript:tree_comp('"+trId+"', '1' )\" id='img_"+trId+"'>";
				}else{
					reSult = "<img height='100%' style='vertical-align:top;' src='/plm/portal/images/img_wtbom/join.gif'>";	
				}
			}
			
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		return reSult;
	}
	
	//eco 대체 부품 정보
	public String getEcoSubItemInfo( String ecocode, String parent, String itemCd ) throws Exception{
		LoggableStatement pstmt = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		String reSult = "";
		boolean isExist = false;
		
		try {
			sb = new StringBuffer();
			sb.append( " select t.substituteitemcode as subcode								\n" );
			sb.append( " from ketbomecosubstitute t											\n" );
			sb.append( " where t.ecoheadernumber = '"+ecocode+"'						\n" );
			sb.append( " and t.parentitemcode = '"+parent+"'								\n" );
			sb.append( " and t.childitemcode = '"+itemCd+"'									\n" );

//Kogger.debug(getClass(), "----------------------쿼리1 : "+sb.toString());	
			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();
			
			while (rs.next()){
				isExist = true;
				reSult = reSult +","+ StringUtil.checkNull(rs.getString("subcode"));
			}
			// 결과가 있는 경우에만 잘라야 함 
			if (isExist) {
				reSult = reSult.substring(1,reSult.length());
			}
		} catch (SQLException se) {
			Kogger.error(getClass(), se);
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		} finally {
			sb.delete( 0 , sb.length() );
        	statementRsClose(rs, pstmt);
		}
		//Kogger.debug(getClass(), "----------------------리턴값 : "+reSult);
		return reSult;
	}
//common.----------------------------------------------------------------------------------------------------	
	
	/**
	 * 함수명 : statementRsClose
	 * 설명 : 
	 * @throws Exception
	 * 작성자 : 신대범
	 * 작성일자 : 2010. 11. 23.
	 */
	public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception
	{
		if( rs != null )
			rs.close();
		
		if( pstmt != null )
			pstmt.close();
	}
	
}
