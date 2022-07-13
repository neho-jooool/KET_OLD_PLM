package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="property",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="moldItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldItemPropertyLink extends _MoldItemPropertyLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldItemPropertyLink newMoldItemPropertyLink( e3ps.common.code.NumberCode property, e3ps.project.MoldItemInfo moldItem ) throws wt.util.WTException {
      MoldItemPropertyLink MoldItemPropertyLink_instance = new MoldItemPropertyLink();
      MoldItemPropertyLink_instance.initialize(property, moldItem);
      return MoldItemPropertyLink_instance;
   }
}
