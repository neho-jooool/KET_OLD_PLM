package e3ps.project;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="MoldItemInfo"),
   roleA=@GeneratedRole(
      name="makingPlace",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="modelItem",
      type=e3ps.project.MoldItemInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class MoldItemMakingPlaceLink extends _MoldItemMakingPlaceLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static MoldItemMakingPlaceLink newMoldItemMakingPlaceLink( e3ps.common.code.NumberCode makingPlace, e3ps.project.MoldItemInfo modelItem ) throws wt.util.WTException {
      MoldItemMakingPlaceLink MoldItemMakingPlaceLink_instance = new MoldItemMakingPlaceLink();
      MoldItemMakingPlaceLink_instance.initialize(makingPlace, modelItem);
      return MoldItemMakingPlaceLink_instance;
   }
}
