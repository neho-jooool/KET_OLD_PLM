/**
 * @author : Park Pil Keun (pkpark@lgcns.com)
 * @since  : 2006.09.20
 */
package e3ps.bom.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.org.WTUser;
import wt.part.WTPart;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QueryException;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.util.WTException;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.common.clipboard.BOMCodePool;
import e3ps.bom.dao.WFBomEcoPartUsageQry;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.common.util.DateUtil;
import e3ps.common.util.KETObjectUtil;
import e3ps.ecm.beans.EcmSearchHelper;
import e3ps.ecm.entity.KETMoldChangeOrder;
import e3ps.ecm.entity.KETProdChangeOrder;
import e3ps.sap.BomEcoInfoInterface;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.part.util.PartSpecGetter;
import ext.ket.part.util.PartUtil;
import ext.ket.shared.log.Kogger;

public class KETBOMECOQueryBean {

	/**
	 * My Bom / ECO 에서 ECO 검색하기 
	 */
	public Vector searchBomEco(Hashtable inputParams) {
		String itemCode = checkNVL(inputParams.get("itemCode"));
		String itemName = checkNVL(inputParams.get("itemName"));
		String ecoNo = checkNVL(inputParams.get("ecoNo"));
		String reasonType = checkNVL(inputParams.get("reasonType"));
		String reason = checkNVL(inputParams.get("reason"));
		String state = checkNVL(inputParams.get("state"));
		String createdFrom = checkNVL(inputParams.get("createdFrom"));
		String createdTo = checkNVL(inputParams.get("createdTo"));
		String orgCode = checkNVL(inputParams.get("orgCode"));
		
		return searchBomEco(itemCode, itemName, ecoNo, reasonType, reason, state, createdFrom, createdTo, orgCode);
	}

	public Vector searchBomEco(String itemCode, String itemName, String ecoNo, String reasonType, String reason, String state, String createdFrom, String createdTo, String orgCode) {

		Vector returnVec = new Vector();
		boolean isWhere = false;
		
		SQLFunction upper = null;
		ColumnExpression ce = null;
		ClassAttribute ca = null; 
		ClassAttribute ca0 = null;
		ClassAttribute ca1 = null;
		ClassAttribute ca2 = null;

		QuerySpec qs = null;
		QuerySpec qs2 = null;
		int moldChangeIndex = 0;
		int bomEcoHeaderIndex = 0;
		int prodChangeIndex = 0;
		int bomEcoHeaderIndex2 = 0;
		
		try {
			
			if (reasonType != null) {
				// 금형 설변 선택 또는 전체 검색인 경우
				if (reasonType.equals("") || reasonType.equals("D")) {
					qs =  new QuerySpec();			// 금형 설변
					moldChangeIndex = qs.appendClassList(KETMoldChangeOrder.class, true);
					bomEcoHeaderIndex = qs.appendClassList(KETBomEcoHeader.class, true);

					ca0 = new ClassAttribute(KETMoldChangeOrder.class, KETMoldChangeOrder.ECO_ID);						// 금형 설변  JOIN
					ca1 = new ClassAttribute(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER);			// BOM ECO Header
					SearchCondition sc0 = new SearchCondition(ca0, "=", ca1);
					sc0.setFromIndicies(new int[]{moldChangeIndex, bomEcoHeaderIndex}, 0);
					qs.appendWhere(sc0);
				}
				// 제품 설변 선택 또는 전체 검색인 경우 
				if (PartUtil.isProductType(reasonType)) {
					qs2 =  new QuerySpec();			// 제품 설변
					prodChangeIndex = qs2.appendClassList(KETProdChangeOrder.class, true);
					bomEcoHeaderIndex2 = qs2.appendClassList(KETBomEcoHeader.class, true);
					
					ca2 = new ClassAttribute(KETProdChangeOrder.class, KETProdChangeOrder.ECO_ID);		
					// 제품 설변  JOIN
					if (PartUtil.isProductType(reasonType)) {	// 구분이 선택되지 않은 경우에는 전체검색이므로 BOM ECO Header 를 또 생성할 필요 없음 
						ca1 = new ClassAttribute(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER);			// BOM ECO Header
					}
					SearchCondition sc2 = new SearchCondition(ca2, "=", ca1);
					sc2.setFromIndicies(new int[]{prodChangeIndex, bomEcoHeaderIndex2}, 0);
					qs2.appendWhere(sc2);
				}
			}
	        isWhere = true;
	        
	        // 부품번호
			if (itemCode != null && !itemCode.equals("")) {
				
	        	ca = new ClassAttribute(KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE);
	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
	        			        
	        	// "*" 가 있는 경우 Like 검색 수행, 아닌 경우 Equal 검색 수행
	        	if (itemCode.indexOf("*") >= 0) {
	        		ce = ConstantExpression.newExpression((itemCode.trim().toUpperCase()).replace("*", "%"));
					if (reasonType != null) {
						// 금형 설변 선택 또는 전체 검색인 경우
						if (reasonType.equals("") || reasonType.equals("D")) {
							if (isWhere)	qs.appendAnd();
							qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { bomEcoHeaderIndex });		// 금형
						}
						// 제품 설변 선택 또는 전체 검색인 경우
						if (PartUtil.isProductType(reasonType)) {
							if (isWhere)	qs2.appendAnd();
							qs2.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { bomEcoHeaderIndex2 });		// 제품
						}
					}
	        	}else {
	        		ce = ConstantExpression.newExpression(itemCode.trim().toUpperCase());
					if (reasonType != null) {
						// 금형 설변 선택 또는 전체 검색인 경우
						if (reasonType.equals("") || reasonType.equals("D")) {
							if (isWhere)	qs.appendAnd();
							qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex });	// 금형
						}
						// 제품 설변 선택 또는 전체 검색인 경우
						if (PartUtil.isProductType(reasonType)) {
							if (isWhere)	qs2.appendAnd();
							qs2.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex2 });// 제품
						}
					}
		        }
				
				isWhere = true;
			}
			
			// 부품명
			if (itemName != null && !itemName.equals("")) {
				
	        	ca = new ClassAttribute(KETBomEcoHeader.class, KETBomEcoHeader.DESCRIPTION);
	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				
	        	// "*" 가 있는 경우 Like 검색 수행, 아닌 경우 Equal 검색 수행
	        	if (itemName.indexOf("*") >= 0) {
	        		ce = ConstantExpression.newExpression((itemName.trim().toUpperCase()).replace("*", "%"));
					if (reasonType != null) {
						// 금형 설변 선택 또는 전체 검색인 경우
						if (reasonType.equals("") || reasonType.equals("D")) {
							if (isWhere)	qs.appendAnd();
							qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { bomEcoHeaderIndex });		// 금형
						}
						// 제품 설변 선택 또는 전체 검색인 경우
						if (PartUtil.isProductType(reasonType)) {
							if (isWhere)	qs2.appendAnd();
							qs2.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { bomEcoHeaderIndex2 });		// 제품
						}
					}
	        	}else {
	        		ce = ConstantExpression.newExpression(itemName.trim().toUpperCase());
					if (reasonType != null) {
						// 금형 설변 선택 또는 전체 검색인 경우
						if (reasonType.equals("") || reasonType.equals("D")) {
							if (isWhere)	qs.appendAnd();
							qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex });	// 금형
						}
						// 제품 설변 선택 또는 전체 검색인 경우
						if (PartUtil.isProductType(reasonType)) {
							if (isWhere)	qs2.appendAnd();
							qs2.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex2 });// 제품
						}
					}
		        }
				
				isWhere = true;
			}
			
			// 개정 상태 조건 추가 (Y: 개정됨, N:개정취소)  TODO 추가됨 
        	ca = new ClassAttribute(KETBomEcoHeader.class, KETBomEcoHeader.ATTRIBUTE1);
        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
        	ce = ConstantExpression.newExpression("Y");   
        	
			// 금형 설변 선택 또는 전체 검색인 경우
			if (reasonType.equals("") || reasonType.equals("D")) {
				if (isWhere)	qs.appendAnd();
				qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex });	// 금형
			}
			// 제품 설변 선택 또는 전체 검색인 경우
			if (PartUtil.isProductType(reasonType)) {
				if (isWhere)	qs2.appendAnd();
				qs2.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex2 });// 제품
			}
			isWhere = true;

			// ECO No
			if (ecoNo != null && !ecoNo.equals("")) {
				
	        	ca = new ClassAttribute(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER);
	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				
	        	// "*" 가 있는 경우 Like 검색 수행, 아닌 경우 Equal 검색 수행
	        	if (ecoNo.indexOf("*") >= 0) {
	        		ce = ConstantExpression.newExpression((ecoNo.trim().toUpperCase()).replace("*", "%"));
					if (reasonType != null) {
						// 금형 설변 선택 또는 전체 검색인 경우
						if (reasonType.equals("") || reasonType.equals("D")) {
							if (isWhere)	qs.appendAnd();
							qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { bomEcoHeaderIndex });		// 금형
						}
						// 제품 설변 선택 또는 전체 검색인 경우
						if (PartUtil.isProductType(reasonType)) {
							if (isWhere)	qs2.appendAnd();
							qs2.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { bomEcoHeaderIndex2 });		// 제품
						}
					}
	        	}else {
	        		ce = ConstantExpression.newExpression(ecoNo.trim().toUpperCase());
					if (reasonType != null) {
						// 금형 설변 선택 또는 전체 검색인 경우
						if (reasonType.equals("") || reasonType.equals("D")) {
							if (isWhere)	qs.appendAnd();
							qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex });	// 금형
						}
						// 제품 설변 선택 또는 전체 검색인 경우
						if (PartUtil.isProductType(reasonType)) {
							if (isWhere)	qs2.appendAnd();
							qs2.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { bomEcoHeaderIndex2 });//  제품
						}
					}
		        }
				
				isWhere = true;
			}
			
			// 변경사유
			if (reason != null && !reason.equals("")) {
				
				if (reasonType != null) {
					// 금형 설변 선택 또는 전체 검색인 경우
					if (reasonType.equals("") || reasonType.equals("D")) {
						if (isWhere)	qs.appendAnd();
						qs.appendWhere(new SearchCondition(KETMoldChangeOrder.class, KETMoldChangeOrder.CHANGE_REASON, SearchCondition.LIKE, "%" + reason + "%"), new int[] {moldChangeIndex});
					}
					// 제품 설변 선택 또는 전체 검색인 경우 
					if (PartUtil.isProductType(reasonType)) {
						if (isWhere)	qs2.appendAnd();
						qs2.appendWhere(new SearchCondition(KETProdChangeOrder.class, KETProdChangeOrder.CHANGE_REASON, SearchCondition.LIKE, "%" + reason + "%"), new int[] {prodChangeIndex});
					}
				}
				isWhere = true;
			}
			
			// 결재상태
			if (state != null && !state.equals("")) {
				
				if (reasonType != null) {
					// 금형 설변 선택 또는 전체 검색인 경우
					if (reasonType.equals("") || reasonType.equals("D")) {
						if (isWhere)	qs.appendAnd();
						qs.appendWhere(new SearchCondition(KETMoldChangeOrder.class, KETMoldChangeOrder.LIFE_CYCLE_STATE, SearchCondition.EQUAL, state), new int[] {moldChangeIndex});
					}
					// 제품 설변 선택 또는 전체 검색인 경우 
					if (PartUtil.isProductType(reasonType)) {
						if (isWhere)	qs2.appendAnd();
						qs2.appendWhere(new SearchCondition(KETProdChangeOrder.class, KETProdChangeOrder.LIFE_CYCLE_STATE, SearchCondition.EQUAL, state), new int[] {prodChangeIndex});
					}
				}

				isWhere = true;
			}
			
			// 작성일자 (시작)
			if (createdFrom != null && !createdFrom.equals("")) {
				
				if (reasonType != null) {
					// 금형 설변 선택 또는 전체 검색인 경우
					if (reasonType.equals("") || reasonType.equals("D")) {
						if (isWhere)	qs.appendAnd();
						qs.appendWhere(new SearchCondition(KETMoldChangeOrder.class, KETMoldChangeOrder.CREATE_TIMESTAMP, SearchCondition.GREATER_THAN_OR_EQUAL, Timestamp.valueOf(createdFrom + " 00:00:00.000000000")), new int[] {moldChangeIndex});
					}
					// 제품 설변 선택 또는 전체 검색인 경우 
					if (PartUtil.isProductType(reasonType)) {
						if (isWhere)	qs2.appendAnd();
						qs2.appendWhere(new SearchCondition(KETProdChangeOrder.class, KETProdChangeOrder.CREATE_TIMESTAMP, SearchCondition.GREATER_THAN_OR_EQUAL, Timestamp.valueOf(createdFrom + " 00:00:00.000000000")), new int[] {prodChangeIndex});
					}
				}

				isWhere = true;
			}
			
			// 작성일자 (끝)
			if (createdTo != null && !createdTo.equals("")) {
				
				if (reasonType != null) {
					// 금형 설변 선택 또는 전체 검색인 경우
					if (reasonType.equals("") || reasonType.equals("D")) {
						if (isWhere)	qs.appendAnd();
						qs.appendWhere(new SearchCondition(KETMoldChangeOrder.class, KETMoldChangeOrder.CREATE_TIMESTAMP, SearchCondition.LESS_THAN_OR_EQUAL, Timestamp.valueOf(createdTo + " 00:00:00.000000000")), new int[] {moldChangeIndex});
					}
					// 제품 설변 선택 또는 전체 검색인 경우 
					if (PartUtil.isProductType(reasonType)) {
						if (isWhere)	qs2.appendAnd();
						qs2.appendWhere(new SearchCondition(KETProdChangeOrder.class, KETProdChangeOrder.CREATE_TIMESTAMP, SearchCondition.LESS_THAN_OR_EQUAL, Timestamp.valueOf(createdTo + " 00:00:00.000000000")), new int[] {prodChangeIndex});
					}
				}

				isWhere = true;
			}
			
			Kogger.debug(getClass(), "================ searchBomEco[금형] Query ==================");
			Kogger.debug(getClass(), qs);
			Kogger.debug(getClass(), "================ searchBomEco[금형] Query ==================");
			Kogger.debug(getClass(), "================ searchBomEco[제품] Query ==================");
			Kogger.debug(getClass(), qs2);
			Kogger.debug(getClass(), "================ searchBomEco[제품] Query ==================");

			QueryResult qr = null;
			QueryResult qr2 = null;
			if (qs != null) {
				qr = PersistenceHelper.manager.find((StatementSpec)qs);		// 금형 ECO 검색
			}
			if (qs2 != null) {
				qr2 = PersistenceHelper.manager.find((StatementSpec)qs2);	// 제품 ECO 검색
			}

			KETBomEcoHeader bomEcoHeader = null;
			KETProdChangeOrder prodChange = null;
			KETMoldChangeOrder moldChange = null;

			// 검색결과가 없을 경우 바로 빈 객체 리턴함 
			if ( qr == null && qr2 == null ) return returnVec;

			// 금형 ECO 정보 저장
			if (qr != null) {
				for (int i=0; i<qr.size(); i++) {
					Object[] object = (Object[])qr.nextElement();
					
					moldChange = (KETMoldChangeOrder)object[0];
					bomEcoHeader = (KETBomEcoHeader)object[1];
					
					Hashtable ecoData = new Hashtable();
					ecoData.put("ecoNo", bomEcoHeader.getEcoHeaderNumber());
					ecoData.put("itemCode", bomEcoHeader.getEcoItemCode());
					ecoData.put("itemDesc", bomEcoHeader.getDescription());
					
					ecoData.put("reason", moldChange.getChangeReason());
					ecoData.put("title", checkNVL(moldChange.getEcoName()));
					ecoData.put("createdBy", moldChange.getCreatorFullName());
					
					String ecoType = bomEcoHeader.getIsMultiple();
					if (ecoType != null && ecoType.equals("Y")) ecoData.put("ecoType", "multiple");
					else  ecoData.put("ecoType", "standard");
					
					ecoData.put("created", DateUtil.getDateString(bomEcoHeader.getCreateTimestamp(), "d"));
					ecoData.put("state", moldChange.getLifeCycleState().getDisplay());
					ecoData.put("ecoOid", bomEcoHeader.toString());
	
					returnVec.addElement(ecoData);
				}
			}
			
			// 제품 ECO 정보 저장
			if (qr2 != null) {
	 			for (int i=0; i<qr2.size(); i++) {
					Object[] object = (Object[])qr2.nextElement();
					
					prodChange = (KETProdChangeOrder)object[0];
					bomEcoHeader = (KETBomEcoHeader)object[1];
					
					Hashtable ecoData = new Hashtable();
					ecoData.put("ecoNo", bomEcoHeader.getEcoHeaderNumber());
					ecoData.put("itemCode", bomEcoHeader.getEcoItemCode());
					ecoData.put("itemDesc", bomEcoHeader.getDescription());
					
					ecoData.put("reason", prodChange.getChangeReason());
					ecoData.put("title", checkNVL(prodChange.getEcoName()));
					ecoData.put("createdBy", prodChange.getCreatorFullName());
					
					String ecoType = bomEcoHeader.getIsMultiple();
					if (ecoType != null && ecoType.equals("Y")) ecoData.put("ecoType", "multiple");
					else  ecoData.put("ecoType", "standard");
					
					ecoData.put("created", DateUtil.getDateString(bomEcoHeader.getCreateTimestamp(), "d"));
					ecoData.put("state", prodChange.getLifeCycleState().getDisplay());
					ecoData.put("ecoOid", bomEcoHeader.toString());
	
					returnVec.addElement(ecoData);
				}
			}
		} catch (QueryException qe) {
			Kogger.error(getClass(), qe);
		} catch (WTException wte) {
			Kogger.error(getClass(), wte);
		}
		return returnVec;
	}

	public Vector getAllBomEcoHeaders(String orgCode, ArrayList<Hashtable> vecItemCode, String userId) {
	    Vector bomEcoVec = new Vector();
	    
		try {
			/*
			select header object from header table 
			where 
			orgCode = ordCode
			and
			(
				ecoHeaderNumber in (vecItemCode)
			)
			 */
			if(vecItemCode.size() > 0) {
				QuerySpec qs = new QuerySpec(KETBomEcoHeader.class);

				if(orgCode != null && orgCode.length() > 0)	{
					qs.appendWhere( new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ORG_CODE, SearchCondition.EQUAL, orgCode), new int[] {0} );
					qs.appendAnd();
				}

				qs.appendOpenParen();
				
				qs.appendOpenParen();
				qs.appendWhere( new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, SearchCondition.EQUAL,  ((vecItemCode.get(0)).get("ecoitemcode")).toString()) , new int[] {0} );
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER, SearchCondition.EQUAL,  ((vecItemCode.get(0)).get("ecoheadernumber")).toString()) , new int[] {0} );
				qs.appendCloseParen();
				
				for (int i=1; i<vecItemCode.size(); i++) {
					qs.appendOr();
					qs.appendOpenParen();
					qs.appendWhere( new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, SearchCondition.EQUAL,   ((vecItemCode.get(i)).get("ecoitemcode")).toString()), new int[] {0} );
					qs.appendAnd();
					qs.appendWhere( new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER, SearchCondition.EQUAL,   ((vecItemCode.get(i)).get("ecoheadernumber")).toString()), new int[] {0} );
					qs.appendCloseParen();
				}
				qs.appendCloseParen();

				Kogger.debug(getClass(), "================ getAllBomEcoHeaders Query ==================");
				Kogger.debug(getClass(), qs);
				Kogger.debug(getClass(), "================ getAllBomEcoHeaders Query ==================");
					
				QueryResult qr = null;
				qr = PersistenceHelper.manager.find( (StatementSpec)qs );
				
				while (qr.hasMoreElements())	{
					KETBomEcoHeader bomEco = (KETBomEcoHeader)qr.nextElement();
					bomEcoVec.addElement(bomEco);
				}
			}
		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		}
		return bomEcoVec;		
	}

	// 내 작업목록 가져오기
	public Vector myBomEco( Hashtable inputParams )  throws WTException {
		Vector outputParams = new Vector();
		
		try {
			if(inputParams.size() > 0) {
				String userId = "";
				String orgCode = "";
				String strItemCode = "";
				
                Vector vecHeaders = new Vector();
                String bomOid = "";

                KETProdChangeOrder  prodChange = null;
                KETMoldChangeOrder  moldChange = null;
                KETBOMECOQueryBean query = new KETBOMECOQueryBean();
 		        KETBomEcoHeader bomEcoHeader = null;
 		        ReferenceFactory rf = new ReferenceFactory();

 		        ArrayList<Hashtable> rtnHash = new ArrayList<Hashtable>();
 		        
				userId = checkNVL((String)inputParams.get("userId"));
				orgCode = checkNVL((String)inputParams.get("orgCode"));
				rtnHash = (ArrayList<Hashtable>)inputParams.get("vecItemCode");
				
				vecHeaders = query.getAllBomEcoHeaders(orgCode, rtnHash, userId);

				String ecoNo = "";
				String itemCode = "";
				String strType = "";
				String strReason = "";
				String strTitle = "";
				String createdBy = "";
				String created = "";
				String state = "";
	            for(int i=0; i<vecHeaders.size(); i++) {
	    	        bomEcoHeader = (KETBomEcoHeader) vecHeaders.elementAt(i);
			        bomOid= rf.getReferenceString(bomEcoHeader);

			        itemCode = bomEcoHeader.getEcoItemCode();
					ecoNo = checkNVL(bomEcoHeader.getEcoHeaderNumber());
					strType = PartSpecGetter.getPartSpec((WTPart)KETPartHelper.service.getPart(itemCode), PartSpecEnum.SpPartType);
					if (PartUtil.isProductType(strType)) {	// 제품 설계변경인 경우
						prodChange = (KETProdChangeOrder) EcmSearchHelper.manager.getEcoObject(ecoNo);
						strReason = prodChange.getChangeReason();
						strTitle = prodChange.getEcoName();
						createdBy = prodChange.getCreatorFullName();
						created = DateUtil.getDateString(prodChange.getCreateTimestamp(), "d");
						state = prodChange.getLifeCycleState().getDisplay();
					} else {													// 금형 설계변경인 경우
						moldChange = (KETMoldChangeOrder) EcmSearchHelper.manager.getEcoObject(ecoNo);
						strReason = moldChange.getChangeReason();
						strTitle = moldChange.getEcoName();
						createdBy = moldChange.getCreatorFullName();
						created = DateUtil.getDateString(moldChange.getCreateTimestamp(), "d");
						state = moldChange.getLifeCycleState().getDisplay();
					}
					
					Hashtable hasHeader = new Hashtable();
					hasHeader.put("itemCode", itemCode);
					hasHeader.put("itemDesc", bomEcoHeader.getDescription());
					hasHeader.put("ecoNo", ecoNo);
					hasHeader.put("reason", strReason);
					String ecoType = bomEcoHeader.getIsMultiple();
					
					// Modified by MJOH, 2007-04-21
//					if( ecoType.equals("Y") ) {
//						hasHeader.put("ecoType", "Multiple");
//					} else if( ecoType.equals("N") ) {
//						hasHeader.put("ecoType", "Standard");
//					} else if( ecoType.equals("R") ) {
//						hasHeader.put("ecoType", "Release");
//					} else if( ecoType.equals("D") ) {
//						hasHeader.put("ecoType", "Divide");
//					}
					
					if( ecoType != null && !ecoType.equals("")) {
						if( ecoType.equals("Y") ) {
							hasHeader.put("ecoType", "multiple");
						} else {
							hasHeader.put("ecoType", "standard");
						}
					}
					
					hasHeader.put("title", strTitle);
					hasHeader.put("createdBy", checkNVL(createdBy));
					hasHeader.put("created", checkNVL(created));
					hasHeader.put("state", checkNVL(state));
					hasHeader.put("oid", checkNVL(bomOid));
			        
			        outputParams.addElement(hasHeader);		    
				}    
			}			
		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		}
		return outputParams;
	}
	/**
	 * Bom - ECO detail view의 정보 가져오기
	 * @parameter	inputParams : ECONo
	 */
	public Hashtable getBomEco(String strEcoNo, String strItemCode) {

		Hashtable outputParams = new Hashtable();
		if (strEcoNo == null || strEcoNo.trim().equals("")) return outputParams;
		
		String strType = "";
		String strReason = "";
		String strReasonDetail = "";
		String strTitle = "";
		String createdBy = "";
		String created = "";
		String state = "";
		String stateKr = "";
		String strTemp = "";
		
		StringTokenizer token = null;
		
        KETProdChangeOrder  prodChange = null;
        KETMoldChangeOrder  moldChange = null;
		
		Hashtable reasonProdHash = BOMCodePool.getProdReason();
		Hashtable reasonMoldHash = BOMCodePool.getMoldReason();
		
		try {
			strType = PartSpecGetter.getPartSpec((WTPart)KETPartHelper.service.getPart(strItemCode), PartSpecEnum.SpPartType);
			if (PartUtil.isProductType(strType)) {	// 제품 설계변경인 경우
				prodChange = (KETProdChangeOrder) EcmSearchHelper.manager.getEcoObject(strEcoNo);
				strReason = prodChange.getChangeReason();
				strTitle = prodChange.getEcoName();
				createdBy = prodChange.getCreatorFullName();
				created = DateUtil.getDateString(prodChange.getCreateTimestamp(), "d");
				state = prodChange.getLifeCycleState().toString();
				stateKr = prodChange.getLifeCycleState().getDisplay();
				
				if (!strReason.equals("") && strReason.indexOf("|") == -1) {		// 변경사유가 하나인 경우 
					strReasonDetail = (String) reasonProdHash.get(strReason);
				} else {																	// 변경사유가 multi 선택 된 경우 
					
					token = new StringTokenizer(strReason, "|");
					while(token.hasMoreElements()){
						strTemp = token.nextToken();
						strReasonDetail += (String) reasonProdHash.get(strTemp) + ",";
					}
					strReasonDetail = strReasonDetail.substring(0, strReasonDetail.lastIndexOf(","));
				}
			} else {													// 금형 설계변경인 경우
				moldChange = (KETMoldChangeOrder) EcmSearchHelper.manager.getEcoObject(strEcoNo);
				strReason = moldChange.getChangeReason();
				strTitle = moldChange.getEcoName();
				createdBy = moldChange.getCreatorFullName();
				created = DateUtil.getDateString(moldChange.getCreateTimestamp(), "d");
				state = moldChange.getLifeCycleState().toString();
				stateKr = moldChange.getLifeCycleState().getDisplay();
				
				if (!strReason.equals("") && strReason.indexOf("|") == -1) {		// 변경사유가 하나인 경우 
					strReasonDetail = (String) reasonMoldHash.get(strReason);
				} else {																	// 변경사유가 multi 선택 된 경우 
					
					token = new StringTokenizer(strReason, "|");
					while(token.hasMoreElements()){
						strTemp = token.nextToken();
						strReasonDetail += (String) reasonMoldHash.get(strTemp) + ",";
					}
					strReasonDetail = strReasonDetail.substring(0, strReasonDetail.lastIndexOf(","));
				}
			}
			
//Kogger.debug(getClass(), "@@@@@@@@@ strReason : " + strReason);			
//Kogger.debug(getClass(), "@@@@@@@@@ strReasonDetail : " + strReasonDetail);			
			
			outputParams.put("ecoNo", checkNVL(strEcoNo));
			outputParams.put("relatedEcrNo", "");
			outputParams.put("reason", strReason);
			outputParams.put("reasonDetail", strReasonDetail);
			outputParams.put("title", checkNVL(strTitle));
			outputParams.put("createdBy", createdBy);
			outputParams.put("created", created);
			outputParams.put("state", state);
			outputParams.put("stateKr", stateKr);
//			outputParams.put("coworker", KETObjectUtil.getCoWorkerVector(bomEcoHeader.toString()));
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}

		return outputParams;
	}

	/**
	 * Multiple ECO 에서 사용하는 Parent Item 검색
	 * @param	itemCode : child item Code
	 * @param	itemDesc : item의 Description
	 * @return	Vector
	 */
	public Vector searchParentItem(String itemCode, String itemDesc, String orgCode) {
		Vector returnVec = new Vector();

//		try {
//			Class itemClass = WTPart.class;
//			Class bomMasterClass = LSISBOMMaster.class;
//			Class partMasterClass = WTPartMaster.class;
//
//			QuerySpec qs =  new QuerySpec();
//			int itemIndex = qs.appendClassList(itemClass, true);
//			int bomMasterIndex = qs.appendClassList(bomMasterClass, true);
//			int partMasterIndex = qs.appendClassList(partMasterClass, true);
//
//			qs.appendJoin(bomMasterIndex, LSISBOMMaster.USED_BY_ROLE, itemIndex);
//			qs.appendJoin(bomMasterIndex, LSISBOMMaster.USES_ROLE, partMasterIndex);
//
//			qs.appendWhere(new SearchCondition(WTPartMaster.class, WTPartMaster.NAME, SearchCondition.EQUAL, itemDesc), new int[] {partMasterIndex});
//			qs.appendAnd();
//			qs.appendWhere(new SearchCondition(LSISBOMMaster.class, "childItemCode", SearchCondition.EQUAL, orgCode + itemCode), new int[] {bomMasterIndex});
//
//			Object obj = new LatestConfigSpec();
//			qs = ((ConfigSpec)obj).appendSearchCriteria(qs);
//
//			QueryResult bomMasterQr = PersistenceHelper.manager.find((StatementSpec)qs);
//
//			while (bomMasterQr.hasMoreElements()) {
//				Object[] object = (Object[])bomMasterQr.nextElement();
//				WTPart item = (WTPart)object[0];						// 모 part
//				Hashtable parentItem = new Hashtable();
//
//				parentItem.put("productItemCode", "");
//				parentItem.put("parentItemCode", item.getNumber());
//				parentItem.put("description", item.getName());
//				parentItem.put("version", item.getVersionIdentifier().getValue() + "." + item.getIterationIdentifier().getValue());
//				parentItem.put("uit", item.getDefaultUnit());
//				parentItem.put("status", item.getLifeCycleState().toString());
//				parentItem.put("state", item.getState().toString());
////				parentItem.put("uom", item.getUom());
////				parentItem.put("supplyType", item.getSupplyTypeName());
////				parentItem.put("startDate", "");
////				parentItem.put("ecoNo", Utility.checkNVL(item.getEcoNo()) );
//				parentItem.put("oid", item.toString());
//				parentItem.put("itemType", String.valueOf(item.getPartType()) );
//
//				returnVec.addElement(parentItem);
//			}
//
//		} catch (Exception e) {
//			Kogger.error(getClass(), e);
//		}

		return returnVec;
	}
//================================= KETBOMECOQueryBean 안에서 쓰는 메소드들 정리 ==============================================//
	/**
	 * String 형태의 Object 들을 받아서 null 처리 해주기 
	 * @return String
	 */
	private String checkNVL(Object str) {
		if (str == null || ((String)str).equals("null")) {
			return "";
		} else {
			return ((String)str).trim();
		}
	}

	
	// 내 작업목록 가져오기
	public ArrayList<Hashtable> searchWorkEcoList() {
		
		ArrayList<Hashtable> pboNames = new ArrayList<Hashtable>();
		try {
			WTUser currentUser = (WTUser)SessionHelper.getPrincipal();
			MyBOMQry myBom = new MyBOMQry();
			pboNames = myBom.getMyBOMEcoList(currentUser.getName(), KETObjectUtil.getIda2a2(currentUser));
			
		} catch (Exception ex) {
			Kogger.error(getClass(), ex);
		}
		return pboNames;
	}
	
	//bomecoheader 가져오기
	public KETBomEcoHeader getBOMECOHeader( String strEcoNo, String ecoItemCode ) {
		
		try {
			QuerySpec qs = new QuerySpec(KETBomEcoHeader.class);
			qs.appendWhere(new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, "=", ecoItemCode), new int[] { 0 });
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER, "=", strEcoNo), new int[] { 0 });
		    QueryResult qr = PersistenceHelper.manager.find(qs);
		    
		    if ( qr.hasMoreElements() ) {
		        Object o = qr.nextElement();
		        if ( o instanceof KETBomEcoHeader ) {
		            return (KETBomEcoHeader) o;
		        } else {
		            Object[] arry = (Object[]) o;
		            return (KETBomEcoHeader) arry[0];
		        }
		    }
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}
	
	//BOMECO 인터페이스
	public static boolean getBomEcoInterface(String oid)
	{
		boolean reSult = false;
		BomEcoInfoInterface bef = new BomEcoInfoInterface();
		if(bef.getInterFaceResult(oid))
		{
			WFBomEcoPartUsageQry we = new WFBomEcoPartUsageQry();
			if(we.getPartUsageResult(oid))
			{
				reSult = true;
			}
		}
		return reSult;
	}

}
