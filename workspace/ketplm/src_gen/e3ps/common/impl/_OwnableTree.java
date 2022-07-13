package e3ps.common.impl;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public interface _OwnableTree extends e3ps.common.impl.Tree {
   /**
    * @see e3ps.common.impl.OwnableTree
    */
   public static final java.lang.String OWNER = "owner";
   /**
    * @see e3ps.common.impl.OwnableTree
    */
   public wt.org.WTPrincipalReference getOwner();
   /**
    * @see e3ps.common.impl.OwnableTree
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException;

}
