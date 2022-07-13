package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="user",
      type=wt.org.WTUser.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class UserTheLessonLearn extends _UserTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static UserTheLessonLearn newUserTheLessonLearn( wt.org.WTUser user, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      UserTheLessonLearn UserTheLessonLearn_instance = new UserTheLessonLearn();
      UserTheLessonLearn_instance.initialize(user, theLessonLearn);
      return UserTheLessonLearn_instance;
   }
}
