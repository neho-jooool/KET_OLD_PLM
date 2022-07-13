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

import wt.fc.WTObject;
import wt.util.WTException;

import com.ptc.windchill.annotations.metadata.GenAsPersistable;
import com.ptc.windchill.annotations.metadata.GeneratedProperty;
import com.ptc.windchill.annotations.metadata.PropertyConstraints;
import com.ptc.windchill.annotations.metadata.Serialization;
import com.ptc.windchill.annotations.metadata.TableProperties;

/**
 * 
 * <p>
 * Use the <code>newTaskTrustResult</code> static factory method(s), not the <code>TaskTrustResult</code> constructor, to construct
 * instances of this class. Instances must be constructed using the static factory(s), in order to ensure proper initialization of the
 * instance.
 * <p>
 * 
 * 
 * @version 1.0
 **/

@GenAsPersistable(superClass = WTObject.class, serializable = Serialization.EXTERNALIZABLE_BASIC, properties = {
        @GeneratedProperty(name = "rev", type = int.class, initialValue = "1", javaDoc = "신뢰성평가차수"), @GeneratedProperty(name = "estimateDate", type = String.class, javaDoc = "평가일"),
        @GeneratedProperty(name = "okCnt", type = int.class, initialValue = "0", javaDoc = "OK 갯수"), @GeneratedProperty(name = "ngCnt", type = int.class, initialValue = "0", javaDoc = "NG 갯수"),
        @GeneratedProperty(name = "description", type = String.class, javaDoc = "사유 및 항목", constraints = @PropertyConstraints(upperLimit = 4000)) }, tableProperties = @TableProperties(tableName = "KETTaskTrustResult"))
public class TaskTrustResult extends _TaskTrustResult {

    static final long serialVersionUID = 1;

    /**
     * Default factory for the class.
     * 
     * @return TaskTrustResult
     * @exception wt.util.WTException
     **/
    public static TaskTrustResult newTaskTrustResult() throws WTException {

	TaskTrustResult instance = new TaskTrustResult();
	instance.initialize();
	return instance;
    }

}
