package e3ps.ecm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETProdChangeActivity"),
   roleA=@GeneratedRole(
      name="prodECO",
      type=e3ps.ecm.entity.KETProdChangeOrder.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="prodECA",
      type=e3ps.ecm.entity.KETProdChangeActivity.class
   )
)

public class KETProdChangeActivityLink extends _KETProdChangeActivityLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static KETProdChangeActivityLink newKETProdChangeActivityLink( e3ps.ecm.entity.KETProdChangeOrder prodECO, e3ps.ecm.entity.KETProdChangeActivity prodECA ) throws wt.util.WTException {
      KETProdChangeActivityLink KETProdChangeActivityLink_instance = new KETProdChangeActivityLink();
      KETProdChangeActivityLink_instance.initialize(prodECO, prodECA);
      return KETProdChangeActivityLink_instance;
   }
}
