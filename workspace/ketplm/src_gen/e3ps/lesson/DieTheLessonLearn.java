package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="die",
      type=wt.part.WTPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class DieTheLessonLearn extends _DieTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static DieTheLessonLearn newDieTheLessonLearn( wt.part.WTPart die, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      DieTheLessonLearn DieTheLessonLearn_instance = new DieTheLessonLearn();
      DieTheLessonLearn_instance.initialize(die, theLessonLearn);
      return DieTheLessonLearn_instance;
   }
}
