package e3ps.common.code;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="NumberCode"),
   roleA=@GeneratedRole(
      name="parent",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="child",
      type=e3ps.common.code.NumberCode.class
   )
)

public class NCodeNCodeLink extends _NCodeNCodeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static NCodeNCodeLink newNCodeNCodeLink( e3ps.common.code.NumberCode parent, e3ps.common.code.NumberCode child ) throws wt.util.WTException {
      NCodeNCodeLink NCodeNCodeLink_instance = new NCodeNCodeLink();
      NCodeNCodeLink_instance.initialize(parent, child);
      return NCodeNCodeLink_instance;
   }
}
