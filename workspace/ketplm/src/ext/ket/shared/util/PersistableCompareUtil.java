package ext.ket.shared.util;

import java.util.ArrayList;
import java.util.List;

import wt.fc.Persistable;
import wt.fc.PersistenceHelper;

public class PersistableCompareUtil<E> {

    // 동일
    private List<E> sameList;
    // 추가
    private List<E> addList;
    // 삭제
    private List<E> deleteList;

    public PersistableCompareUtil() {
	sameList = new ArrayList<E>();
	addList = new ArrayList<E>();
	deleteList = new ArrayList<E>();
    }

    public void compare(List<E> oldList, List<E> newList) throws Exception {

	if (oldList == null || newList == null)
	    throw new Exception("Compare List Object can't be null");

	for (E oldObject : oldList) { // old
	    boolean same = false;
	    for (E newObject : newList) { // new
		if (PersistenceHelper.isEquivalent((Persistable) oldObject, (Persistable) newObject)) {
		    sameList.add(newObject);
		    same = true;
		    break;
		}
	    }

	    if (!same) {
		deleteList.add(oldObject);
	    }
	}

	for (E newObject : newList) { // new
	    boolean same = false;
	    for (E sameObject : sameList) { // same
		if (PersistenceHelper.isEquivalent((Persistable) sameObject, (Persistable) newObject)) {
		    same = true;
		    break;
		}
	    }

	    if (!same) {
		addList.add(newObject);
	    }
	}
    }

    public List<E> getSameList() {
	return sameList;
    }

    public List<E> getAddList() {
	return addList;
    }

    public List<E> getDeleteList() {
	return deleteList;
    }

    public static void main(String[] args) {

	// using...
	// List<KETPartAttribute> oldAttrList = new ArrayList<>();
	// List<KETPartAttribute> newAttrList = new ArrayList<>();
	//
	// PersistableCompareUtil<KETPartAttribute> comparator = new PersistableCompareUtil<KETPartAttribute>();
	// comparator.compare(oldAttrList, newAttrList);
	// List<KETPartAttribute> sameList = comparator.getSameList();
	// List<KETPartAttribute> addList = comparator.getAddList();
	// List<KETPartAttribute> deleteList = comparator.getDeleteList();
    }

}
