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

public class KETMoldECOPartLinkVO extends GeneralVO implements Serializable {


   private static final String RESOURCE = "e3ps.ecm.entity.entityResource";
   private static final String CLASSNAME = KETMoldECOPartLinkVO.class.getName();

	private static final long serialVersionUID = 7855542857249798800L;
	private String relPartLinkOid;//oid
	private String relPartOid;//부품oid
	private String expectCost;//예상비용
	private String secureBudgetYn;//비용확보여부
	private String changeReqComment;//요청내용
	private String relPartNumber;//부품번호
	private String relPartName;//부품명
	private String relPartRev;//부품버젼
	private String secureBudgetYnName;//비용확보여부명
	
	private String dieType; // 1.Press 2.Mold 3.Press. 4.Mold


	public String getRelPartLinkOid() {
		return relPartLinkOid;
	}
	public void setRelPartLinkOid(String relPartLinkOid) {
		this.relPartLinkOid = relPartLinkOid;
	}
	public String getRelPartOid() {
		return relPartOid;
	}
	public void setRelPartOid(String relPartOid) {
		this.relPartOid = relPartOid;
	}
	public String getExpectCost() {
		return expectCost;
	}
	public void setExpectCost(String expectCost) {
		this.expectCost = expectCost;
	}
	public String getSecureBudgetYn() {
		return secureBudgetYn;
	}
	public void setSecureBudgetYn(String secureBudgetYn) {
		this.secureBudgetYn = secureBudgetYn;
	}
	public String getChangeReqComment() {
		return changeReqComment;
	}
	public void setChangeReqComment(String changeReqComment) {
		this.changeReqComment = changeReqComment;
	}
	public String getRelPartNumber() {
		return relPartNumber;
	}
	public void setRelPartNumber(String relPartNumber) {
		this.relPartNumber = relPartNumber;
	}
	public String getRelPartName() {
		return relPartName;
	}
	public void setRelPartName(String relPartName) {
		this.relPartName = relPartName;
	}
	public String getRelPartRev() {
		return relPartRev;
	}
	public void setRelPartRev(String relPartRev) {
		this.relPartRev = relPartRev;
	}
	public String getSecureBudgetYnName() {
		return secureBudgetYnName;
	}
	public void setSecureBudgetYnName(String secureBudgetYnName) {
		this.secureBudgetYnName = secureBudgetYnName;
	}
	public String getDieType() {
		return dieType;
	}
	public void setDieType(String dieType) {
		this.dieType = dieType;
	}
}
