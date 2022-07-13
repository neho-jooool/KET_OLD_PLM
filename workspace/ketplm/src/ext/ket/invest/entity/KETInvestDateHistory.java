package ext.ket.invest.entity;

/* bcwti≪≫≪≫Copyright (c) 2008 Parametric Technology Corporation (PTC).
 * All Rights Reserved.≪≫≪≫This software is the confidential and proprietary
 * information of PTC and is subject to the terms of a software license
 * agreement. You shall not disclose such confidential information and shall
 * use it only in accordance with the terms of the license agreement.≪≫≪≫ecwti
 */
import java.sql.Timestamp;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.ForeignKeyRole;
import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedForeignKey;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.MyRole;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

import e3ps.common.impl.OwnPersistable;
import wt.fc.InvalidAttributeException;
import wt.util.WTException;

/**
 *
 * <p>
 * Use the <code>newKETInvestDateHistory</code> static factory method(s), not the
 * <code>KETInvestDateHistory</code> constructor, to construct instances of this
 * class.  Instances must be constructed using the static factory(s), in
 * order to ensure proper initialization of the instance.
 * <p>
 *
 *
 * @version   1.0
 **/

@GenAsPersistable(interfaces={OwnPersistable.class}, serializable=Serialization.EXTERNALIZABLE_BASIC,
   properties={
   @GeneratedProperty(name="orgRequestDate", type=Timestamp.class,javaDoc="완료요청일자"),
   @GeneratedProperty(name="changeRequestDate", type=Timestamp.class,javaDoc="완료일자"),
   @GeneratedProperty(name="changeHistory", type=String.class,javaDoc="변경사유",constraints=@PropertyConstraints(upperLimit=4000)),
   },
   foreignKeys={
   @GeneratedForeignKey(myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="worker", type=wt.org.WTUser.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="theKETInvestDateHistory", cardinality=Cardinality.ONE)),
   @GeneratedForeignKey(name="KETInvestDateHistoryLink", myRoleIsRoleA=false,
      foreignKeyRole=@ForeignKeyRole(name="investMaster", type=ext.ket.invest.entity.KETInvestMaster.class,
         constraints=@PropertyConstraints(required=true)),
      myRole=@MyRole(name="investHistory"))
   })
public class KETInvestDateHistory extends _KETInvestDateHistory {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETInvestDateHistory
     * @exception wt.util.WTException
     **/
    public static KETInvestDateHistory newKETInvestDateHistory() throws WTException {

	KETInvestDateHistory instance = new KETInvestDateHistory();
	instance.initialize();
	return instance;
    }

    @Override
    public void checkAttributes() throws InvalidAttributeException {
	// TODO Auto-generated method stub

    }

    @Override
    public String getIdentity() {
	// TODO Auto-generated method stub
	return null;
    }

    protected void initialize() throws WTException {

    }

}
