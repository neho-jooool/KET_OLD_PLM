package e3ps.common.impl;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="Tree"),
   roleA=@GeneratedRole(
      name="child",
      type=e3ps.common.impl.Tree.class
   ),
   roleB=@GeneratedRole(
      name="parent",
      type=e3ps.common.impl.Tree.class,
      cardinality=Cardinality.ONE,
      columnProperties=@ColumnProperties(
         index=true
      )
   )
)

public class ParentChildLink extends _ParentChildLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ParentChildLink newParentChildLink( e3ps.common.impl.Tree child, e3ps.common.impl.Tree parent ) throws wt.util.WTException {
      ParentChildLink ParentChildLink_instance = new ParentChildLink();
      ParentChildLink_instance.initialize(child, parent);
      return ParentChildLink_instance;
   }
}
