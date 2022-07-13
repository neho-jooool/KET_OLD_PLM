package ext.ket.project.trycondition.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=true,
   tableProperties=@TableProperties(tableName="KETTryPress"),
   roleA=@GeneratedRole(
      name="theKETTryPress",
      type=ext.ket.project.trycondition.entity.KETTryPress.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="scrapProcess",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ONE
   )
)

public class ScrapProcessLink extends _ScrapProcessLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static ScrapProcessLink newScrapProcessLink( ext.ket.project.trycondition.entity.KETTryPress theKETTryPress, e3ps.common.code.NumberCode scrapProcess ) throws wt.util.WTException {
      ScrapProcessLink ScrapProcessLink_instance = new ScrapProcessLink();
      ScrapProcessLink_instance.initialize(theKETTryPress, scrapProcess);
      return ScrapProcessLink_instance;
   }
}
