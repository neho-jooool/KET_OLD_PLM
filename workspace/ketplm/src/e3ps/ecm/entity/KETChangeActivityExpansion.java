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

package e3ps.ecm.entity;

import java.sql.Timestamp;

import wt.enterprise.Managed;
import wt.fc.ObjectToObjectLink;
import wt.fc.Persistable;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.Cardinality;
import com.ptc.windchill.annotations.metadata.GenAsBinaryLink;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.GeneratedRole;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newKETChangeActivityExpansion</code> static factory method(s), not the <code>KETChangeActivityExpansion</code> constructor,
 * to construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization
 * of the instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsBinaryLink(superClass = ObjectToObjectLink.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "subContractorCode", type = String.class, javaDoc = "NumberCode�� �ڵ�(�?ó), CodeType : SUBCONTRACTOR"),
        @GeneratedProperty(name = "subContractorApprovalDate", type = Timestamp.class, javaDoc = "�?������"),
        @GeneratedProperty(name = "erpFlag", type = String.class, javaDoc = "ERP �������̽� ���� Flag") }, roleA = @GeneratedRole(name = "some", type = Persistable.class), roleB = @GeneratedRole(name = "eca", type = Managed.class, cardinality = Cardinality.ONE), tableProperties = @TableProperties(tableName = "KETChangeActivityExpansion"))
public class KETChangeActivityExpansion extends _KETChangeActivityExpansion {


    static final long serialVersionUID = 1;




    /**
     * Default factory for the class.
     * 
     * @param some
     * @param eca
     * @return KETChangeActivityExpansion
     * @exception wt.util.WTException
     **/
    public static KETChangeActivityExpansion newKETChangeActivityExpansion(Persistable some, Managed eca) throws WTException {

	KETChangeActivityExpansion instance = new KETChangeActivityExpansion();
	instance.initialize(some, eca);
	return instance;
    }

}
