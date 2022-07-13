package e3ps.bom.service;

import java.sql.Timestamp;
import java.util.Hashtable;
import java.util.Vector;

import wt.access.AccessControlHelper;
import wt.access.AccessPermission;
import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.fc.ReferenceFactory;
import wt.fc.WTObject;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerRef;
import wt.lifecycle.LifeCycleHelper;
import wt.org.OrganizationServicesHelper;
import wt.org.WTOrganization;
import wt.org.WTPrincipal;
import wt.org.WTUser;
import wt.part.QuantityUnit;
import wt.part.WTPart;
import wt.pdmlink.PDMLinkProduct;
import wt.pds.StatementSpec;
import wt.query.ClassAttribute;
import wt.query.ColumnExpression;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.session.SessionHelper;
import wt.vc.config.ConfigSpec;
import wt.vc.config.LatestConfigSpec;
import wt.workflow.work.WorkItem;
import wt.workflow.work.WorkflowHelper;
import e3ps.bom.command.mybom.MyBOMQry;
import e3ps.bom.entity.KETBomEcoHeader;
import e3ps.bom.entity.KETBomHeader;
import e3ps.common.util.KETObjectUtil;
import e3ps.common.util.StringUtil;
import e3ps.common.util.WCUtil;
import ext.ket.shared.log.Kogger;

public class KETBOMHeaderQueryBean
{
	public KETBOMHeaderQueryBean()
	{
	}
	
	public Vector searchItem( Hashtable searchAttrHash )
	{
		Vector itemVector = new Vector();
		try
		{
			String itemCode = checkNVL((String)searchAttrHash.get("itemCode")).trim();
			String description = checkNVL((String)searchAttrHash.get("description")).trim();
			String creator = checkNVL((String)searchAttrHash.get("creator")).trim();
			String createFromDate = checkNVL((String)searchAttrHash.get("createFromDate")).trim();
			String createToDate = checkNVL((String)searchAttrHash.get("createToDate")).trim();
			String orgCode = checkNVL((String)searchAttrHash.get("orgCode")).trim();
			String bomFlag = checkNVL((String)searchAttrHash.get("bomFlag")).trim();
			String bomAllowed = checkNVL((String)searchAttrHash.get("bomAllowed")).trim();

Kogger.debug(getClass(), "#####################################");			
Kogger.debug(getClass(), "--->> itemCode : " + itemCode);			
Kogger.debug(getClass(), "--->> description : " + description);			
Kogger.debug(getClass(), "--->> creator : " + creator);			
Kogger.debug(getClass(), "--->> createFromDate : " + createFromDate);			
Kogger.debug(getClass(), "--->> createToDate : " + createToDate);			
Kogger.debug(getClass(), "--->> orgCode : " + orgCode);			
Kogger.debug(getClass(), "--->> bomFlag : " + bomFlag);		
Kogger.debug(getClass(), "--->> bomAllowed : " + bomAllowed);
Kogger.debug(getClass(), "#####################################");	

			QuerySpec qs =  new QuerySpec(WTPart.class);
			qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NAME, false), new int[] {0} );
			
			if( itemCode != null && itemCode.length() != 0 )
			{
					if(itemCode.indexOf("*")>-1)
					{
						itemCode = itemCode.replace("*", "%");
						qs.appendAnd();
						qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.LIKE, itemCode), new int[] {0} );
					}else{
						qs.appendAnd();
						qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, itemCode), new int[] {0} );	
					}
			}

			if( description != null && description.length() != 0 )
			{
				if(description.indexOf("*")>-1)
				{	
					description = description.replace("*", "%");
					qs.appendAnd();
					qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NAME, SearchCondition.LIKE, description, false), new int[] {0});
				}else{
					qs.appendAnd();
					qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NAME, SearchCondition.EQUAL, description, false), new int[] {0});	
				}
			}

			QueryResult qr = null;

			Object obj = new LatestConfigSpec();
			qs = ((ConfigSpec)(obj)).appendSearchCriteria(qs);

			Kogger.debug(getClass(), "================ searchItem Query ==================");
			Kogger.debug(getClass(), qs);
			Kogger.debug(getClass(), "================ searchItem Query ==================");

			qr = PersistenceHelper.manager.find( (StatementSpec)qs );
			qr = ((ConfigSpec)(obj)).process(qr);

Kogger.debug(getClass(), "--->> qr.size() : " + qr.size());
			
			WTPart item = null;
			
			while( qr.hasMoreElements() )
			{
				item = (WTPart)qr.nextElement();
				itemVector.addElement(item);
			}
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
		return itemVector;
	}

	public WTPart searchItem( String itemCode )
	{
		WTPart item = null;
		try
		{
			QuerySpec qs =  new QuerySpec(WTPart.class);

			qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, itemCode), new int[] {0} );
			
			QueryResult qr = null;

			Object obj = new LatestConfigSpec();
			qs = ((ConfigSpec)(obj)).appendSearchCriteria(qs);

			qr = PersistenceHelper.manager.find( (StatementSpec)qs );
			qr = ((ConfigSpec)(obj)).process(qr);
			
			if( qr.hasMoreElements() )
			{
				item = (WTPart)qr.nextElement();
			}
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
		return item;
	}

	public Vector searchDuplicationItem( Hashtable searchAttrHash )
	{
		Vector searchDupItemVector = new Vector();
		try
		{
			String itemCode = checkNVL((String)searchAttrHash.get("itemCode")).trim();
			String orgCode = checkNVL((String)searchAttrHash.get("orgCode")).trim();

			QuerySpec qs =  new QuerySpec(KETBomHeader.class);
		
			qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, false), new int[] {0} );
			
			if( itemCode != null && itemCode.length() != 0 )
			{
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, SearchCondition.EQUAL, itemCode), new int[] {0} );
			}

			if( orgCode != null && orgCode.length() != 0 )
			{
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.ORG_CODE, SearchCondition.EQUAL, orgCode), new int[] {0} );
			}
			
			QueryResult qr = null;
			qr = PersistenceHelper.manager.find( (StatementSpec)qs );

			KETBomHeader header = null;
			
			while( qr.hasMoreElements() )
			{
				header = (KETBomHeader)qr.nextElement();
				searchDupItemVector.addElement(header);
			}
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
		return searchDupItemVector;
	}

	// KETBomHeader 생성
	public String createBom( Hashtable createAttrHash ) {
		String oid= "";

		String orgCode = checkNVL((String)createAttrHash.get("orgCode"));
		String newBomCode = checkNVL((String)createAttrHash.get("newBomCode"));
		String descriptionTfl = checkNVL((String)createAttrHash.get("description"));
		String quantityTfl = checkNVL((String)createAttrHash.get("quantity"));
		String boxQuantityTfl = checkNVL((String)createAttrHash.get("boxQuantity"));
		String unitTfl = checkNVL((String)createAttrHash.get("unit"));
		String statusTfl = checkNVL((String)createAttrHash.get("status"));
		String versionStr = checkNVL((String)createAttrHash.get("versionStr"));
		//String bomtext = checkNVL((String)createAttrHash.get("bomtext"));
		
		Vector coworkersVec 	= (Vector)createAttrHash.get("coworkers");

		try {
			KETBomHeader header = KETBomHeader.newKETBomHeader();
			
			WTOrganization wtOrg = OrganizationServicesHelper.manager.getOrganization(SessionHelper.manager.getPrincipal());			
			
			PDMLinkProduct e3psProduct = WCUtil.getPDMLinkProduct();
	        WTContainerRef containerRef = WTContainerRef.newWTContainerRef(e3psProduct);
			
			header.setContainerReference( containerRef );
			header.setContainer( containerRef.getReferencedContainerReadOnly() );
			
			header.setOrgCode(orgCode);
			header.setNewBOMCode(newBomCode);
			header.setDescription(descriptionTfl);
			header.setUnitOfQuantity(unitTfl);  			// 단위
			header.setQuantity(quantityTfl); 				// 수량
			header.setBoxQuantity(boxQuantityTfl); 		// 포장수량
			header.setSubstitudeBOM("1");    			// 대체 BOM
			header.setBOMUse("2");						// BOM 용도 - DEV에서 2로 세팅
			header.setBOMStatus(statusTfl);  				// BOM 상태
			header.setCheckoutStatus("1");				// 체크아웃 상태
			header.setBOMVersion(versionStr.trim());    // header 버젼.
			header.setTransferFlag("1");					// Default '1'로 셋팅
			header.setBOMUse("2");						// Default '2'로 고정 (개발 BOM 의미임)
			
			Folder folder = FolderHelper.service.getFolder("/Default", containerRef);
			FolderHelper.assignLocation( (FolderEntry)header, folder );
			
			LifeCycleHelper.setLifeCycle( header, LifeCycleHelper.service.getLifeCycleTemplate("KET_COMMON_LC", containerRef) );

			header = (KETBomHeader)PersistenceHelper.manager.save(header);

			ReferenceFactory rf = new ReferenceFactory();
			oid = rf.getReferenceString(header);

			if (coworkersVec != null && coworkersVec.size() > 0) {
				KETObjectUtil.setCoWorker(oid, coworkersVec);
			}
		} catch(Exception e) {
			Kogger.error(getClass(), e);
		}
       return oid;  
	}
	

	// 최상위 모부품 수정에서 헤더정보 수정하기 
	public String udpateBom( Hashtable updateAttrHash, boolean bomGubunFlag ) {
		
		String oid= "";
		KETBomHeader header = null;
		KETBomEcoHeader ecoHeader = null;
		
		String newBomCode = (String) updateAttrHash.get("newBomCode");
		String ecoItemCode = (String) updateAttrHash.get("ecoItemCode");
		String ecoHeaderNo = (String) updateAttrHash.get("ecoHeaderNo");
		String boxQuantity = (String) updateAttrHash.get("boxQuantity");
		
		try {
			if (bomGubunFlag) {		// 신규 BOM
				oid = KETObjectUtil.getOidString(getBOMHeader(newBomCode));
				
	            if ( StringUtil.checkString(oid) ) {
	                ReferenceFactory rf = new ReferenceFactory();
	                header = (KETBomHeader) rf.getReference(oid).getObject();
	                
	                header.setBoxQuantity(boxQuantity);
	                header = (KETBomHeader) PersistenceHelper.manager.modify(header);
	            }
			} else {						// 변경 BOM
				oid = KETObjectUtil.getOidString(getBOMEcoHeader(ecoItemCode, ecoHeaderNo));
				
				if ( StringUtil.checkString(oid) ) {
					ReferenceFactory rf = new ReferenceFactory();
					ecoHeader = (KETBomEcoHeader) rf.getReference(oid).getObject();
					
					ecoHeader.setBoxQuantity(boxQuantity);
					ecoHeader = (KETBomEcoHeader) PersistenceHelper.manager.modify(ecoHeader);
				}
			}
			
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return oid;
	}
	
	// 최상위 모부품 수정에서 BOM 헤더정보 가져오기 
	public KETBomHeader getBOMHeader( String newBomCode ) {
	
		try {
			QuerySpec qs = new QuerySpec(KETBomHeader.class);
			qs.appendWhere(new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, "=", newBomCode), new int[] { 0 });
		    QueryResult qr = PersistenceHelper.manager.find(qs);
		    
		    if ( qr.hasMoreElements() ) {
		        Object o = qr.nextElement();
		        if ( o instanceof KETBomHeader ) {
		            return (KETBomHeader) o;
		        } else {
		            Object[] arry = (Object[]) o;
		            return (KETBomHeader) arry[0];
		        }
		    }
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}
	
	// 최상위 모부품 수정에서 BOM ECO 헤더정보 가져오기 
	public KETBomEcoHeader getBOMEcoHeader( String ecoItemCode, String ecoHeaderNo ) {
	
		try {
			QuerySpec qs = new QuerySpec(KETBomEcoHeader.class);
			qs.appendWhere(new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_HEADER_NUMBER, "=", ecoHeaderNo), new int[] { 0 });
			qs.appendAnd();
			qs.appendWhere(new SearchCondition(KETBomEcoHeader.class, KETBomEcoHeader.ECO_ITEM_CODE, "=", ecoItemCode), new int[] { 0 });
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
	
	public Vector searchBomHeaders(String orgCode, String itemCode, String itemName, 
											 String createdDateFrom, String createdDateTo, String state, String strCreated)
	{
	    Vector searchBomVec = new Vector();
	    
		try
		{
			SQLFunction upper = null;
			ColumnExpression ce = null;
			ClassAttribute ca = null; 
			ReferenceFactory rf = new ReferenceFactory();
			
			QuerySpec qs =  new QuerySpec(KETBomHeader.class);
			qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, false), new int[] {0} );
		
			if ( itemCode != null && itemCode.length() != 0 )	{
				qs.appendAnd();
//				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, SearchCondition.LIKE, itemCode), new int[] {0} );
				
	        	ca = new ClassAttribute(KETBomHeader.class, KETBomHeader.NEW_BOMCODE);
	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
	        			        
	        	// "*" 가 있는 경우 Like 검색 수행, 아닌 경우 Equal 검색 수행
	        	if (itemCode.indexOf("*") >= 0) {
	        		ce = ConstantExpression.newExpression((itemCode.trim().toUpperCase()).replace("*", "%"));
	        		qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });
	        	}else {
	        		ce = ConstantExpression.newExpression(itemCode.trim().toUpperCase());
		        	qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { 0 });
		        }
			}
			
			if ( itemName != null && itemName.length() != 0 ) {
				qs.appendAnd();
//				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.DESCRIPTION, SearchCondition.LIKE, itemName), new int[] {0} );
				
	        	ca = new ClassAttribute(KETBomHeader.class, KETBomHeader.DESCRIPTION);
	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
				
	        	// "*" 가 있는 경우 Like 검색 수행, 아닌 경우 Equal 검색 수행
	        	if (itemName.indexOf("*") >= 0) {
	        		ce = ConstantExpression.newExpression((itemName.trim().toUpperCase()).replace("*", "%"));
	        		qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });
	        	}else {
	        		ce = ConstantExpression.newExpression(itemName.trim().toUpperCase());
		        	qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { 0 });
		        }
			}

			if( orgCode != null && orgCode.length() != 0 ){
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.ORG_CODE, SearchCondition.EQUAL, orgCode), new int[] {0} );
			}

			if ( strCreated != null && strCreated.length() != 0 )	{
				qs.appendAnd();
				
				WTUser user = (WTUser) OrganizationServicesHelper.manager.getAuthenticatedUser (strCreated);
				qs.appendWhere(new SearchCondition(KETBomHeader.class, "creator.key.id", "=", 	user.getPersistInfo().getObjectIdentifier().getId() ),new int[] { 0 });
				
//	        	ca = new ClassAttribute(KETBomHeader.class, KETBomHeader.CREATOR_FULL_NAME);
//	        	upper = SQLFunction.newSQLFunction(SQLFunction.UPPER, ca);
//	        			        
//	        	// "*" 가 있는 경우 Like 검색 수행, 아닌 경우 Equal 검색 수행
//	        	if (strCreated.indexOf("*") >= 0) {
//	        		ce = ConstantExpression.newExpression((strCreated.trim().toUpperCase()).replace("*", "%"));
//	        		qs.appendWhere(new SearchCondition(upper, SearchCondition.LIKE, ce), new int[] { 0 });
//	        	}else {
//	        		ce = ConstantExpression.newExpression(strCreated.trim().toUpperCase());
//		        	qs.appendWhere(new SearchCondition(upper,  SearchCondition.EQUAL, ce), new int[] { 0 });
//		        }
			}


			if( createdDateFrom != null && createdDateFrom.length() != 0 ){
				Timestamp createFromDateTime = new Timestamp(0);
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.CREATE_TIMESTAMP, SearchCondition.GREATER_THAN_OR_EQUAL, createFromDateTime.valueOf(createdDateFrom + " 00:00:00.0")), new int[] {0} );
			}

			if( createdDateTo != null && createdDateTo.length() != 0 ){
				Timestamp createToDateTime = new Timestamp(0);
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.CREATE_TIMESTAMP, SearchCondition.LESS_THAN_OR_EQUAL, createToDateTime.valueOf(createdDateTo + " 23:59:59.0")), new int[] {0} );
			}
			
			if( state != null && state.length() != 0 ) {
				qs.appendAnd();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, "state.state", SearchCondition.EQUAL, state), new int[] {0} );
			}			
			
			QueryResult qr = null;
			qr = PersistenceHelper.manager.find( (StatementSpec)qs );

			Kogger.debug(getClass(), "================ searchBomHeaders Query ==================");
			Kogger.debug(getClass(), qs);
			Kogger.debug(getClass(), "================ searchBomHeaders Query ==================");
			
		    while (qr.hasMoreElements()) {
		    	KETBomHeader bom = (KETBomHeader)qr.nextElement();
		        searchBomVec.addElement(bom);
		    }
		} catch(Exception ex) {
			Kogger.error(getClass(), ex);
		}
		return searchBomVec;		
	}

	public Vector getAllBomHeaders(String orgCode, Vector vecItemCode, String userId)
	{
	    Vector bomVec = new Vector();

		try	{
			/*
			select header object from header table 
			where 
			orgCode = ordCode
			and
			(
			(
			state not in (completed, canceled) 
			and
			newBomCode in (vecItemCode)
			)
			)
			 */
			
			if(vecItemCode.size() > 0) {
				QuerySpec qs = new QuerySpec(KETBomHeader.class);

				if(orgCode != null && orgCode.length() > 0)	{
					qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.ORG_CODE, SearchCondition.EQUAL, orgCode), new int[] {0} );
					qs.appendAnd();
				}

				qs.appendOpenParen();

				qs.appendWhere( new SearchCondition(KETBomHeader.class, "state.state", SearchCondition.EQUAL, "INWORK"), new int[] {0} );		// 작성중
				
				qs.appendOr();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, "state.state", SearchCondition.EQUAL, "UNDERREVIEW"), new int[] {0} );// 검토중

				qs.appendOr();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, "state.state", SearchCondition.EQUAL, "REJECTED"), new int[] {0} );		// 반려됨

				qs.appendOr();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, "state.state", SearchCondition.EQUAL, "REWORK"), new int[] {0} );		// 재작업

				qs.appendCloseParen();
				
				qs.appendAnd();

				qs.appendOpenParen();
				qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, SearchCondition.EQUAL, vecItemCode.elementAt(0).toString()), new int[] {0} );
				
				for (int i=1; i<vecItemCode.size(); i++) {
					qs.appendOr();
					qs.appendWhere( new SearchCondition(KETBomHeader.class, KETBomHeader.NEW_BOMCODE, SearchCondition.EQUAL, vecItemCode.elementAt(i).toString()), new int[] {0} );
				}
				qs.appendCloseParen();

				Kogger.debug(getClass(), "================ getAllBomHeaders Query ==================");
				Kogger.debug(getClass(), qs);
				Kogger.debug(getClass(), "================ getAllBomHeaders Query ==================");
					
				QueryResult qr = null;
				qr = PersistenceHelper.manager.find( (StatementSpec)qs );
				
				while (qr.hasMoreElements())	{
					KETBomHeader bom = (KETBomHeader)qr.nextElement();
					bomVec.addElement(bom);
				}
			}
		} catch(Exception ex){
			Kogger.error(getClass(), ex);
		}
		return bomVec;		
	}

	public Vector getVersionInfo(String orgCode, Vector inputParams)
	{
		Vector returnVec = new Vector();
		try
		{
			if(inputParams.size() > 0)
			{
				QuerySpec qs =  new QuerySpec(WTPart.class);
/*			
				if(orgCode != null && orgCode.length() > 0)
				{
					qs.appendWhere( new SearchCondition(WTPart.class, "orgCode", SearchCondition.EQUAL, orgCode), new int[] {0} );
					qs.appendAnd();
				}
*/
				qs.appendOpenParen();
				qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, inputParams.elementAt(0).toString()), new int[] {0} );

				for (int i=1; i<inputParams.size(); i++) 
				{
					qs.appendOr();
					qs.appendWhere( new SearchCondition(WTPart.class, WTPart.NUMBER, SearchCondition.EQUAL, inputParams.elementAt(i).toString()), new int[] {0} );
				}
				
				qs.appendCloseParen();

				Kogger.debug(getClass(), "================ getVersionInfo Query ==================");
				Kogger.debug(getClass(), qs);
				Kogger.debug(getClass(), "================ getVersionInfo Query ==================");
				
				QueryResult qr = null;

				Object obj = new LatestConfigSpec();
				qs = ((ConfigSpec)(obj)).appendSearchCriteria(qs);
				qr = PersistenceHelper.manager.find( (StatementSpec)qs );
				qr = ((ConfigSpec)(obj)).process(qr);

				WTPart item = null;
				
				while( qr.hasMoreElements() )
				{
					item = (WTPart)qr.nextElement();

Kogger.debug(getClass(), "version Info : " + (String)item.getNumber() + "@" + checkNVL(item.getIterationDisplayIdentifier().toString()));

					returnVec.addElement(checkNVL((String)item.getNumber()) + "@" + checkNVL(item.getIterationDisplayIdentifier().toString()));
				}
			}
		}
		catch (Exception ex)
		{
			Kogger.error(getClass(), ex);
		}
		return returnVec;
	}
	
	// 내 작업목록 가져오기
	public Vector searchWorkList()
	{

		 Vector<String> pboNames = new Vector();
			try {
				WTUser currentUser = (WTUser)SessionHelper.getPrincipal();
				MyBOMQry myBom = new MyBOMQry();
				pboNames = myBom.getMyBOMList(currentUser.getName(), KETObjectUtil.getIda2a2(currentUser));
			} catch (Exception ex) {
			    Kogger.error(getClass(), ex);
			}
			return pboNames;
	}	
	
	public Vector checkAuthority( Vector itemCodeVec )
	{
		 WTPart item = null;
		 Vector returnVec = null;
		 try{
			WTPrincipal principal = SessionHelper.manager.getPrincipal();
			returnVec = new Vector();
			String itemCode = "";
			boolean flag = false;
			
			for( int i=0; i<itemCodeVec.size(); i++ )
			{
				itemCode = (String)itemCodeVec.elementAt( i );
				item = searchItem( itemCode );
			   
				if( item != null )
				{
					flag = AccessControlHelper.manager.hasAccess( principal, item, AccessPermission.MODIFY ) ;
					if( !flag )
					{
						itemCode = (item.getNumber()).substring(3);
						returnVec.addElement( itemCode );
					}
					itemCode = "";
					item = null;
				}
			}
		 }catch(Exception e){}

			return returnVec;
	}
	
	//shin...KET에서는 End Working에서 빠지고 결재에서 들어가야한다 함.
	public String setAllEndWorking( String bomOid, String headerType )
	{
		WorkItem workitem = null;
        Vector routingEvents = new Vector();	
        String returnStr = "";
		try
		{
			WTObject pbo = null;
			WorkItem tmpWI = null;
			String pboOid = "";
			ReferenceFactory rf = new ReferenceFactory();

			QueryResult qr = WorkflowHelper.service.getUncompletedWorkItems();
			while(qr.hasMoreElements())
			{
				tmpWI = (WorkItem)qr.nextElement();
				if (tmpWI.getPrimaryBusinessObject() != null) 
				{
					try
					{
						pbo = (WTObject)tmpWI.getPrimaryBusinessObject().getObject();
					}
					catch (Exception eee)
					{
						continue;
					}

					//if(headerType.equals("BOM"))
					//{
						if (pbo instanceof KETBomHeader) 
						{
							pboOid= rf.getReferenceString(pbo);
//							if (bomOid.equals(((LSISBOMHeader)pbo).toString()))

							if (bomOid.equals(pboOid))
							{
								workitem = tmpWI;
								routingEvents.addElement("EndWorking");
								WorkflowHelper.service.workComplete(workitem, SessionHelper.manager.getPrincipalReference(), routingEvents);
							}
						}
					//}
					//else
					//{
						/*if (pbo instanceof LSISBOMECOHeader) 
						{
							if (bomOid.equals(((LSISBOMECOHeader)pbo).toString()))
							{
								workitem = tmpWI;
								routingEvents.addElement("EndWorking");
								WorkflowHelper.service.workComplete(workitem, SessionHelper.manager.getPrincipalReference(), routingEvents);
							}
						}*/
					//}
				}
			}
			returnStr = "OK";
		}
		catch(Exception ex)
		{
			Kogger.error(getClass(), ex);
			returnStr = "Fail";
		}

		return returnStr;
	}
	
	// 단위 Display 값을 리턴하는 함수 
	public String getUnitDisplayValue(String strUnit) {
		String strReturn = "";
		String strUnitValue = "";
		
		QuantityUnit [] qUnit = QuantityUnit.getQuantityUnitSet();
		for (int inx = 0; inx < qUnit.length; inx++) {
			strUnitValue = qUnit[inx].toString();
			if (!strUnitValue.equals("") && strUnitValue.equals(strUnit)) {
				strReturn = qUnit[inx].getDisplay();
				break;
			}
		}
		return strReturn;
	}
	
	private String checkNVL( String str) 
	{ 
		if(str == null || str.equals("null"))
		{
			return "";
		}
		else 
		{
			return str;
		}
	}
	
}
