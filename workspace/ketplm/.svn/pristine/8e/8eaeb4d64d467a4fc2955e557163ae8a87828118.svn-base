package e3ps.groupware.folder;

import wt.enterprise.RevisionControlled;
import wt.fc.PersistenceHelper;
import wt.fc.PersistenceServerHelper;
import wt.fc.QueryResult;
import wt.folder.CabinetBased;
import wt.folder.Folder;
import wt.folder.FolderHelper;
import wt.folder.Foldered;
import wt.folder.IteratedShortcutLink;
import wt.folder.Shortcut;
import wt.folder.ShortcutLink;
import wt.query.ConstantExpression;
import wt.query.QuerySpec;
import wt.query.SQLFunction;
import wt.query.SearchCondition;
import wt.util.WTException;

import e3ps.common.util.CommonUtil;

public class ShortcutHelper {
	/**
	 * Create Shortcut
	 * 
	 * @param :
	 *                  folder
	 * @param :
	 *                  cabinetBased
	 * @return: Shortcut
	 * @since : 2004.08
	 */

	public static Shortcut addShortcut(Folder folder, CabinetBased cabinetBased)
			throws Exception {
		Shortcut shortcut = FolderHelper.service.addShortcut(folder,
				cabinetBased);
		return shortcut;
	}

	/**
	 * Create Shortcut
	 * 
	 * @param :
	 *                  folder
	 * @param :
	 *                  cabinetBased
	 * @return: Shortcut
	 * @since : 2004.08
	 */

	public static boolean isHaveShortcut(CabinetBased cabinetBased)
			throws Exception {
		long lRoleB = 0;
		Class targetClass = null;
		String field = "";
		if (cabinetBased instanceof RevisionControlled) {
			RevisionControlled rc = (RevisionControlled) cabinetBased;
			lRoleB = rc.getBranchIdentifier();
			targetClass = IteratedShortcutLink.class;
			field = "roleBObjectRef.key.branchId";
		} else {
			lRoleB = CommonUtil.getOIDLongValue(cabinetBased);
			targetClass = ShortcutLink.class;
			field = "roleBObjectRef.key.id";
		}

		QuerySpec query = new QuerySpec();
		int classIndex = query.appendClassList(targetClass, false);

		query.appendWhere(new SearchCondition(targetClass, field, "=", lRoleB),
				classIndex);

		QueryResult result = getSQLCount(query);

		int totalCount = 0;
		while (result.hasMoreElements()) {
			Object obj[] = (Object[]) result.nextElement();
			totalCount = ((java.math.BigDecimal) obj[0]).intValue();
		}

		if (totalCount <= 0)
			return false;
		else
			return true;
	}

	/**
	 * Get Shortcut from Foldered Object
	 * 
	 * @param :
	 *                  Foldered
	 * @return : Shortcut
	 * @since : 2004.09
	 */
	public static Shortcut getShortcut(Folder folder, CabinetBased cabinetBased)
			throws Exception {
		if (isHaveShortcut(cabinetBased)) {
			long lRoleA = CommonUtil.getOIDLongValue(folder);
			long lRoleB = 0;
			Class targetClass = null;
			String fieldA = "roleAObjectRef.key.id";
			String fieldB = "";
			if (cabinetBased instanceof RevisionControlled) {
				RevisionControlled rc = (RevisionControlled) cabinetBased;
				lRoleB = rc.getBranchIdentifier();
				targetClass = IteratedShortcutLink.class;
				fieldB = "roleBObjectRef.key.branchId";
			} else {
				lRoleB = CommonUtil.getOIDLongValue(cabinetBased);
				targetClass = ShortcutLink.class;
				fieldB = "roleBObjectRef.key.id";
			}

			QuerySpec query = new QuerySpec();
			int classIndex = query.appendClassList(targetClass, true);

			query.appendWhere(new SearchCondition(targetClass, fieldA, "=", lRoleA), classIndex);
			query.appendAnd();
			query.appendWhere(new SearchCondition(targetClass, fieldB, "=", lRoleB), classIndex);

			QueryResult result = PersistenceHelper.manager.find(query);

			Shortcut shortcut = null;
			while (result.hasMoreElements()) {
				Object[] object = (Object[]) result.nextElement();
				shortcut = (Shortcut) object[0];
			}
			return shortcut;
		} else {
			return addShortcut(folder, cabinetBased);
		}
	}

	/**
	 * Get FolderedObject from Shortcut
	 * 
	 * @param :
	 *                  shortcut
	 * @return : Foldered
	 * @since : 2004.02
	 */
	public static Foldered getFolderedObject(Shortcut shortcut)
			throws Exception {
		Foldered foldered = (Foldered) shortcut.getRoleBObject();
		return foldered;
	}

	public static QueryResult getSQLCount(QuerySpec query) throws WTException {
		// ##begin getSQLCount%4141238C0225g.body preserve=yes

		query.setAdvancedQueryEnabled(true);

        // Windchill 9.0 수정 08-02-20 구상수
//		ConstantExpression ce = new ConstantExpression("*", false);
		ConstantExpression ce = new ConstantExpression();

        SQLFunction sfCount = new SQLFunction(SQLFunction.COUNT, ce);

		query.appendSelect(sfCount, new int[]{0}, false);

		if (query != null) {
			return PersistenceServerHelper.manager.query(query);
		} else {
			return null;
		}

		// ##end getSQLCount%4141238C0225g.body
	}

	// ##end user.operations
}
