package ext.ket.part.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETPartInfo"),
   roleA=@GeneratedRole(
      name="rivalPart",
      type=ext.ket.part.entity.RivalPartInfo.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="theKETPartInfo",
      type=ext.ket.part.entity.KETPartInfo.class,
      cardinality=Cardinality.ONE
   )
)

public class RivalPartTheKETPartInfo extends _RivalPartTheKETPartInfo implements Externalizable {
   static final long serialVersionUID = 1;
   public static RivalPartTheKETPartInfo newRivalPartTheKETPartInfo( ext.ket.part.entity.RivalPartInfo rivalPart, ext.ket.part.entity.KETPartInfo theKETPartInfo ) throws wt.util.WTException {
      RivalPartTheKETPartInfo RivalPartTheKETPartInfo_instance = new RivalPartTheKETPartInfo();
      RivalPartTheKETPartInfo_instance.initialize(rivalPart, theKETPartInfo);
      return RivalPartTheKETPartInfo_instance;
   }
}
