package e3ps.groupware.company;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="People"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ONE
   ),
   roleB=@GeneratedRole(
      name="people",
      type=e3ps.groupware.company.People.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class WTUserPeopleLink extends _WTUserPeopleLink implements Externalizable {
   static final long serialVersionUID = 1;
   public static WTUserPeopleLink newWTUserPeopleLink( wt.org.WTUser user, e3ps.groupware.company.People people ) throws wt.util.WTException {
      WTUserPeopleLink WTUserPeopleLink_instance = new WTUserPeopleLink();
      WTUserPeopleLink_instance.initialize(user, people);
      return WTUserPeopleLink_instance;
   }
}
