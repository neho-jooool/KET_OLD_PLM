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

import java.io.Serializable;


/**
 *
 * @version   1.0
 **/

public class KETMoldChangeActivityVO extends GeneralVO implements Serializable {


   private static final String RESOURCE = "e3ps.ecm.entity.entityResource";
   private static final String CLASSNAME = KETMoldChangeActivityVO.class.getName();

	private KETMoldChangeActivity  ketMoldChangeActivity;//???ECO??????
	private int  totalCount = 0;//????????


	public KETMoldChangeActivity getKetMoldChangeActivity() {
		return ketMoldChangeActivity;
	}
	public void setKetMoldChangeActivity(KETMoldChangeActivity ketMoldChangeActivity) {
		this.ketMoldChangeActivity = ketMoldChangeActivity;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
