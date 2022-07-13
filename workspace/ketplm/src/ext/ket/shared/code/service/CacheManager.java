package ext.ket.shared.code.service;

import java.rmi.RemoteException;
import java.util.List;

import wt.method.MethodServerException;
import ext.ket.shared.code.entity.NumberCodeCache;
import ext.ket.shared.dto.BaseDTO;
import ext.ket.shared.log.Kogger;

/**
 * @클래스명 : CacheManager
 * @작성자 : Jason, Han
 * @작성일 : 2014. 6. 24.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 */
public class CacheManager {

    private static NumberCodeCache numberCodeCache    = null;
    public static final String     CARNUMBERCODECACHE = "CARNUMBERCODECACHE";

    private static NumberCodeCache getNumberCodeCache() {
	if (numberCodeCache == null)
	    newNumberCodeCache();
	return numberCodeCache;
    }

    private static synchronized void newNumberCodeCache() {

	if (numberCodeCache == null) {
	    try {
		numberCodeCache = new NumberCodeCache();
	    } catch (RemoteException e) {
		Kogger.error(CacheManager.class, e);
		throw new MethodServerException("Error to create NumberCode Cache", e);
	    }
	}
    }

    @SuppressWarnings("unchecked")
    public static List<?> getItem(String key) throws Exception {

	NumberCodeCache cache = getNumberCodeCache();
	List<BaseDTO> list = (List<BaseDTO>) cache.get(key);

	return list;
    }

    public static void removeByKey(String key) throws Exception {

	NumberCodeCache cache = getNumberCodeCache();
	cache.remove(key);
    }

    public static void updateByKey(String key, List<?> list) throws Exception {

	NumberCodeCache cache = getNumberCodeCache();
	cache.put(key, list);
    }
}
