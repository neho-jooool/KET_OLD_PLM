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

package ext.ket.edm.entity;

import java.sql.Timestamp;

import wt.doc.WTDocument;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;

/**
 * 
 * <p>
 * Use the <code>newKETDrawingDistRequest</code> static factory method(s), not the <code>KETDrawingDistRequest</code> constructor, to
 * construct instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of
 * the instance.
 * <p>
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTDocument.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "distType", type = String.class),
        @GeneratedProperty(name = "distReason", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "downloadExpireDate", type = Timestamp.class),
        @GeneratedProperty(name = "distSubcontractor", type = String.class, constraints = @PropertyConstraints(upperLimit = 4000)),
        @GeneratedProperty(name = "writeDeptEnName", type = String.class),
        @GeneratedProperty(name = "writeDeptKrName", type = String.class), @GeneratedProperty(name = "writeDeptCode", type = String.class),
        @GeneratedProperty(name = "distDeptName", type = String.class), @GeneratedProperty(name = "backgroundYn", type = String.class),
        @GeneratedProperty(name = "hpSendStatus", type = String.class), @GeneratedProperty(name = "hpSendDate", type = Timestamp.class),
        @GeneratedProperty(name = "refPart", type = String.class), @GeneratedProperty(name = "etcYn", type = String.class) })
public class KETDrawingDistRequest extends _KETDrawingDistRequest {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return KETDrawingDistRequest
     * @exception wt.util.WTException
     **/
    public static KETDrawingDistRequest newKETDrawingDistRequest() throws WTException {

	KETDrawingDistRequest instance = new KETDrawingDistRequest();
	instance.initialize();
	return instance;
    }

}
