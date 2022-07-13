package ext.ket.material.entity;

import java.io.IOException;
import java.io.ObjectInput;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.project.material.MoldMaterial;
import wt.fc.ObjectToObjectLink;
import wt.part.WTPartMaster;
import wt.util.WTException;

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
versions={2538346186404157511L},
properties={
 @GeneratedProperty(name="partNo", type=String.class),
 @GeneratedProperty(name="partName", type=String.class),
 @GeneratedProperty(name="partType", type=String.class),
 @GeneratedProperty(name="price", type=String.class),
},
roleA=@GeneratedRole(name="material", type=MoldMaterial.class),
roleB=@GeneratedRole(name="part", type=WTPartMaster.class,
   cardinality=Cardinality.ONE_TO_MANY),
tableProperties=@TableProperties(tableName="KETPartMaterialLink")
)
public class KETPartMaterialLink extends _KETPartMaterialLink {
    static final long serialVersionUID = 1;
    
    public static KETPartMaterialLink newKETPartMaterialLink( MoldMaterial material, WTPartMaster part )
            throws WTException {

      KETPartMaterialLink instance = new KETPartMaterialLink();
      instance.initialize( material, part );
      return instance;
   }
    
    boolean readVersion2538346186404157511L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {

      if ( !superDone )                                             // if not doing backward compatibility
         super.readExternal( input );                               // handle super class


      return true;
   }

}
