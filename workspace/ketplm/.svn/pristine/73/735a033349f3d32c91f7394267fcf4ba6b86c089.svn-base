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

import e3ps.project.ProjectOutput;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.ClassNotFoundException;
import java.lang.Object;
import java.lang.String;
import java.sql.SQLException;
import wt.enterprise.Master;
import wt.fc.ObjectToObjectLink;
import wt.pds.PersistentRetrieveIfc;
import wt.pds.PersistentStoreIfc;
import wt.pom.DatastoreException;
import wt.util.WTException;
import wt.util.WTPropertyVetoException;
import com.ptc.windchill.annotations.metadata.*;

/**
 *
 * <p>
 * Use the <code>newOutputDocumentLink</code> static factory method(s),
 * not the <code>OutputDocumentLink</code> constructor, to construct instances
 * of this class.  Instances must be constructed using the static factory(s),
 * in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="branchIdentifier", type=long.class,
      columnProperties=@ColumnProperties(index=true)),
   @GeneratedProperty(name="docClassName", type=String.class)
   },
   roleA=@GeneratedRole(name="output", type=ProjectOutput.class,
      cardinality=Cardinality.ONE),
   roleB=@GeneratedRole(name="outputObject", type=Master.class,
      cardinality=Cardinality.ONE_TO_MANY),
   tableProperties=@TableProperties(tableName="OutputDocumentLink")
)
public class OutputDocumentLink extends _OutputDocumentLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     output
    * @param     outputObject
    * @return    OutputDocumentLink
    * @exception wt.util.WTException
    **/
   public static OutputDocumentLink newOutputDocumentLink( ProjectOutput output, Master outputObject )
            throws WTException {

      OutputDocumentLink instance = new OutputDocumentLink();
      instance.initialize( output, outputObject );
      return instance;
   }

}
