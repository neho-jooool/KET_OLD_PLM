package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="part",
      type=wt.part.WTPart.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class PartTheLessonLearn extends _PartTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static PartTheLessonLearn newPartTheLessonLearn( wt.part.WTPart part, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      PartTheLessonLearn PartTheLessonLearn_instance = new PartTheLessonLearn();
      PartTheLessonLearn_instance.initialize(part, theLessonLearn);
      return PartTheLessonLearn_instance;
   }
}
