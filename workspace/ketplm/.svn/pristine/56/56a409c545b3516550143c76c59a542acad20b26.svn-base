/**
 * @(#) OwnableTreeHelper.java Copyright (c) e3ps. All rights reserverd
 * @version 1.00
 * @since jdk 1.4.02
 * @createdate 2005. 4. 6..
 * @author Cho Sung Ok, jerred@e3ps.com
 * @desc
 */

package e3ps.common.impl;

import java.util.ArrayList;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import wt.session.SessionHelper;

import e3ps.common.query.SearchUtil;
import e3ps.common.util.CommonUtil;

public class OwnableTreeHelper {
	public static final OwnableTreeHelper manager = new OwnableTreeHelper ();

	protected OwnableTreeHelper() {
	}

	public ArrayList getTopList(Class treeClass) {
		ArrayList returndata = new ArrayList ();
		try {
			QuerySpec spec = getTopQuerySpec ( treeClass );
			QueryResult qr = PersistenceHelper.manager.find ( spec );
			while (qr.hasMoreElements ()) {
				Object[] obj = (Object[]) qr.nextElement ();
				returndata.add ( obj[0] );
			}
		} catch (Exception e) {
			e.printStackTrace ();
		}
		return returndata;
	}

	public QuerySpec getTopQuerySpec(Class treeClass) throws Exception {
		WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal ();

		QuerySpec spec = new QuerySpec ();
		Class mainClass = treeClass;
		int mainClassPos = spec.addClassList ( mainClass , true );

		spec.appendWhere (new SearchCondition ( mainClass , "owner.key.id" ,SearchCondition.EQUAL , CommonUtil.getOIDLongValue ( wtuser ) ) ,mainClassPos );
		spec.appendAnd ();
		spec.appendWhere (new SearchCondition ( mainClass , "parentReference.key.id" ,SearchCondition.EQUAL , (long) 0 ) ,mainClassPos );
		return spec;
	}

	public ArrayList getChildList(Class treeClass, Tree tree) {
		ArrayList returndata = new ArrayList ();
		try {
			QuerySpec spec = getChildQuerySpec ( treeClass , tree );
			QueryResult qr = PersistenceHelper.manager.find ( spec );
			while (qr.hasMoreElements ()) {
				Object[] obj = (Object[]) qr.nextElement ();
				returndata.add ( obj[0] );
			}
		} catch (Exception e) {
			e.printStackTrace ();
		}
		return returndata;
	}

	public QuerySpec getChildQuerySpec(Class treeClass, Tree tree)
		throws Exception {
		WTUser wtuser = (WTUser) SessionHelper.manager.getPrincipal ();

		QuerySpec spec = new QuerySpec ();
		Class mainClass = treeClass;
		int mainClassPos = spec.addClassList ( mainClass , true );

		spec.appendWhere (new SearchCondition ( mainClass , "owner.key.id" ,SearchCondition.EQUAL , CommonUtil.getOIDLongValue ( wtuser ) ) ,mainClassPos );
		spec.appendAnd ();
		spec.appendWhere ( new SearchCondition ( mainClass ,"parentReference.key.id" , SearchCondition.EQUAL , CommonUtil.getOIDLongValue ( tree ) ) , mainClassPos );
		SearchUtil.setOrderBy (spec ,mainClass ,mainClassPos ,"thePersistInfo.createStamp" ,"createDate" ,true );
		return spec;
	}

	public ArrayList getAllChildList(Class treeClass, Tree tree, ArrayList returnlist) {
		try {
			QuerySpec spec = getChildQuerySpec ( treeClass , tree );
			QueryResult qr = PersistenceHelper.manager.find ( spec );
			while (qr.hasMoreElements ()) {
				Object[] obj = (Object[]) qr.nextElement ();
				returnlist.add ( obj[0] );
				getAllChildList ( treeClass , (Tree) obj[0] , returnlist );
			}
		} catch (Exception e) {
			e.printStackTrace ();
		}
		return returnlist;
	}

	public ArrayList getParentsList(Tree tree, ArrayList returnlist) {
		Tree parent = tree.getParent ();
		if (parent != null) {
			returnlist.add ( parent );
			getParentsList ( parent , returnlist );
		}
		return returnlist;
	}
}
