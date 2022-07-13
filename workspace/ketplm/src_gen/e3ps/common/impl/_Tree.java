package e3ps.common.impl;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public interface _Tree extends wt.fc.Persistable {
   /**
    * @see e3ps.common.impl.Tree
    */
   public static final java.lang.String PARENT = "parent";
   /**
    * @see e3ps.common.impl.Tree
    */
   public static final java.lang.String PARENT_REFERENCE = "parentReference";
   /**
    * @see e3ps.common.impl.Tree
    */
   public e3ps.common.impl.Tree getParent();
   /**
    * @see e3ps.common.impl.Tree
    */
   public wt.fc.ObjectReference getParentReference();
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParent(e3ps.common.impl.Tree the_parent) throws wt.util.WTPropertyVetoException, wt.util.WTException;
   /**
    * @see e3ps.common.impl.Tree
    */
   public void setParentReference(wt.fc.ObjectReference the_parentReference) throws wt.util.WTPropertyVetoException;

}
