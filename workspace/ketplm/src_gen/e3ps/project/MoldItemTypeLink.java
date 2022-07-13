package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="moldType",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="moldItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldItemTypeLink extends _MoldItemTypeLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldItemTypeLink newMoldItemTypeLink( e3ps.common.code.NumberCode moldType, e3ps.project.MoldItemInfo moldItem ) throws wt.util.WTException {
      MoldItemTypeLink MoldItemTypeLink_instance = new MoldItemTypeLink();
      MoldItemTypeLink_instance.initialize(moldType, moldItem);
      return MoldItemTypeLink_instance;
   }
}
