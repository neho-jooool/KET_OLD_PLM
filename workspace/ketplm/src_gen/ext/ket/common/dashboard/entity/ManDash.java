package ext.ket.common.dashboard.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDashBoardMailSetting"),
   roleA=@GeneratedRole(
      name="man",
      type=e3ps.groupware.company.People.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="dash",
      type=ext.ket.common.dashboard.entity.KETDashBoardMailSetting.class,
      cardinality=Cardinality.ONE
   )
)

public class ManDash extends _ManDash implements Externalizable {
   static final long serialVersionUID = 1;
   public static ManDash newManDash( e3ps.groupware.company.People man, ext.ket.common.dashboard.entity.KETDashBoardMailSetting dash ) throws wt.util.WTException {
      ManDash ManDash_instance = new ManDash();
      ManDash_instance.initialize(man, dash);
      return ManDash_instance;
   }
}
