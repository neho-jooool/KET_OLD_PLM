/* bcwti
 *
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 *
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 *
 * ecwti
 */
package e3ps.project;

import e3ps.project.E3PSProject;
import e3ps.project.MoldItemInfo;
import e3ps.project.machine.MoldMachine;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.fc.ObjectReference;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newMoldProject</code> static factory method(s), not the
 * <code>MoldProject</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(superClass=E3PSProject.class,
   properties={
   @GeneratedProperty(name="productWeight", type=String.class,
      javaDoc="제품중량"),
   @GeneratedProperty(name="scrapWeight", type=String.class,
      javaDoc="Scrap중량"),
   @GeneratedProperty(name="specialSpec", type=String.class,
      javaDoc="특수사양",
      constraints=@PropertyConstraints(upperLimit=4000)),
   @GeneratedProperty(name="remark", type=String.class,
      javaDoc="비고"),
   @GeneratedProperty(name="outSourcing", type=String.class,
      javaDoc="제작처"),
   @GeneratedProperty(name="shrinkage", type=String.class)
   },
   foreignKeys={
   @GeneratedForeignKey(name="MoldProjectMoldInfoLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldInfo", type=e3ps.project.MoldItemInfo.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="moldProject", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="moldMachine", type=e3ps.project.machine.MoldMachine.class),
      myRole=@MyRole(name="theMoldProject", cardinality=Cardinality.ONE))
   })
public class MoldProject extends _MoldProject {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @return    MoldProject
    * @exception wt.util.WTException
    **/
   public static MoldProject newMoldProject()
            throws WTException {

      MoldProject instance = new MoldProject();
      instance.initialize();
      return instance;
   }

}
