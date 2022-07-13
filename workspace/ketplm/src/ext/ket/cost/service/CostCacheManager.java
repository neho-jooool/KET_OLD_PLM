package ext.ket.cost.service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import wt.method.MethodServerException;
import ext.ket.shared.code.service.CacheManager;
import ext.ket.shared.log.Kogger;

public class CostCacheManager {

	private static CostFormulaCache costFCache = null;
	private static CostPartTypeCache costPTCache = null;

	// ######################### COST FORMULA CACHE ###########################################

	private static CostFormulaCache getCostFormulaCache() {
		if (costFCache == null)
			newCostFCache();
		return costFCache;
	}

	private static synchronized void newCostFCache() {

		if (costFCache == null) {
			try {
				costFCache = new CostFormulaCache();
			} catch (RemoteException e) {
				Kogger.error(CacheManager.class, e);
				throw new MethodServerException("Error to create CostFormulaCache", e);
			}
		}
	}

	public static DefaultMutableTreeNode getCostFItem(String key) throws Exception {
		CostFormulaCache cache = getCostFormulaCache();
		DefaultMutableTreeNode obj = (DefaultMutableTreeNode) cache.get(key);
		return obj;
	}

	public static void removeCostFByKey(String key) throws Exception {
		CostFormulaCache cache = getCostFormulaCache();
		cache.remove(key);
	}

	public static void updateCostFByKey(String key, DefaultMutableTreeNode obj) throws Exception {
		System.out.println("update CostFormulaCache #### " + key);
		CostFormulaCache cache = getCostFormulaCache();
		cache.put(key, obj);
	}

	public static void removeCostFAll() throws Exception {
		CostFormulaCache cache = getCostFormulaCache();
		cache.reset();
	}

	// ######################### COST PART TYPE CACHE ###########################################

	private static CostPartTypeCache getCostPartTypeCache() {
		if (costPTCache == null)
			newCostPTCache();
		return costPTCache;
	}

	private static synchronized void newCostPTCache() {

		if (costPTCache == null) {
			try {
				costPTCache = new CostPartTypeCache();
			} catch (RemoteException e) {
				Kogger.error(CacheManager.class, e);
				throw new MethodServerException("Error to create CostPartTypeCache", e);
			}
		}
	}

	public static List<Map<String, Object>> getCostPTItem(String key) throws Exception {
		CostPartTypeCache cache = getCostPartTypeCache();
		List<Map<String, Object>> obj = (List<Map<String, Object>>) cache.get(key);
		return obj;
	}

	public static void removeCostPTByKey(String key) throws Exception {
		CostPartTypeCache cache = getCostPartTypeCache();
		cache.remove(key);
	}

	public static void updateCostPTByKey(String key, List<Map<String, Object>> obj) throws Exception {
		System.out.println("update PartTypeCache #### " + key);
		CostPartTypeCache cache = getCostPartTypeCache();
		cache.put(key, obj);
	}

	public static void removeCostPTAll() throws Exception {
		CostPartTypeCache cache = getCostPartTypeCache();
		cache.reset();
	}
}
