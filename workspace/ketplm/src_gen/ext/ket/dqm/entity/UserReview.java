package ext.ket.dqm.entity;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="KETDqmClose"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="review",
      type=ext.ket.dqm.entity.KETDqmClose.class,
      cardinality=Cardinality.ZERO_TO_ONE
   )
)

public class UserReview extends _UserReview implements Externalizable {
   static final long serialVersionUID = 1;
   public static UserReview newUserReview( wt.org.WTUser user, ext.ket.dqm.entity.KETDqmClose review ) throws wt.util.WTException {
      UserReview UserReview_instance = new UserReview();
      UserReview_instance.initialize(user, review);
      return UserReview_instance;
   }
}
