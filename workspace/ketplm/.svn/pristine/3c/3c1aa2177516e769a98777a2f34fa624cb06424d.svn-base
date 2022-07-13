/* bcwti????Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.????This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.????ecwti
 */

package ext.ket.issue.entity;

import java.io.IOException;
import java.io.ObjectInput;
import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ColumnProperties;
import com.ptc.windchill.annotations.metadata.ColumnType;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.TableProperties;

import e3ps.project.E3PSProjectMaster;
import wt.fc.ObjectToObjectLink;
import wt.part.WTPartMaster;
import wt.util.WTException;


/**
 *
 * <p>
 * Use the <code>newKETIssuePartLink</code> static factory method(s),
 * not the <code>KETIssuePartLink</code> constructor, to construct
 * instances of this class.  Instances must be constructed using the static
 * factory(s), in order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsBinaryLink(superClass=ObjectToObjectLink.class, 
   versions={2538346186404157511L},
   properties={
    @GeneratedProperty(name="partNo", type=String.class),
    @GeneratedProperty(name="partName", type=String.class)
   },
   roleA=@GeneratedRole(name="issueMaster", type=KETIssueMaster.class),
   roleB=@GeneratedRole(name="partMaster", type=WTPartMaster.class,
      cardinality=Cardinality.ONE_TO_MANY),
   tableProperties=@TableProperties(tableName="KETIssuePartLink")
)
public class KETIssuePartLink extends _KETIssuePartLink {


   static final long serialVersionUID = 1;




   /**
    * Default factory for the class.
    *
    * @param     issueMaster
    * @param     partMaster
    * @return    KETIssuePartLink
    * @exception wt.util.WTException
    **/
   public static KETIssuePartLink newKETIssuePartLink( KETIssueMaster issueMaster, WTPartMaster partMaster )
            throws WTException {

      KETIssuePartLink instance = new KETIssuePartLink();
      instance.initialize( issueMaster, partMaster );
      return instance;
   }

   /**
    * Reads the non-transient fields of this class from an external source.
    *
    * @param     input
    * @param     readSerialVersionUID
    * @param     superDone
    * @return    boolean
    * @exception java.io.IOException
    * @exception java.lang.ClassNotFoundException
    **/
   boolean readVersion2538346186404157511L( ObjectInput input, long readSerialVersionUID, boolean superDone )
            throws IOException, ClassNotFoundException {

      if ( !superDone )                                             // if not doing backward compatibility
         super.readExternal( input );                               // handle super class


      return true;
   }

}
