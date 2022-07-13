package e3ps.common.impl;

@SuppressWarnings({"cast", "deprecation", "unchecked"})
public interface _OwnPersistable extends wt.access.AccessControlled, wt.fc.Persistable {
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public static final java.lang.String OWNER = "owner";
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public wt.org.WTPrincipalReference getOwner();
   /**
    * @see e3ps.common.impl.OwnPersistable
    */
   public void setOwner(wt.org.WTPrincipalReference owner) throws wt.util.WTPropertyVetoException;

}
