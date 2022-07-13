package e3ps.lesson;

import java.io.Externalizable;
import com.ptc.windchill.annotations.metadata.*;

@GenAsBinaryLink(superClass=wt.fc.ForeignKeyLink.class, myRoleIsRoleA=false,
   tableProperties=@TableProperties(tableName="LessonLearn"),
   roleA=@GeneratedRole(
      name="factory",
      type=e3ps.common.code.NumberCode.class,
      cardinality=Cardinality.ZERO_TO_ONE
   ),
   roleB=@GeneratedRole(
      name="theLessonLearn",
      type=e3ps.lesson.LessonLearn.class,
      cardinality=Cardinality.ONE
   )
)

public class FactoryTheLessonLearn extends _FactoryTheLessonLearn implements Externalizable {
   static final long serialVersionUID = 1;
   public static FactoryTheLessonLearn newFactoryTheLessonLearn( e3ps.common.code.NumberCode factory, e3ps.lesson.LessonLearn theLessonLearn ) throws wt.util.WTException {
      FactoryTheLessonLearn FactoryTheLessonLearn_instance = new FactoryTheLessonLearn();
      FactoryTheLessonLearn_instance.initialize(factory, theLessonLearn);
      return FactoryTheLessonLearn_instance;
   }
}
