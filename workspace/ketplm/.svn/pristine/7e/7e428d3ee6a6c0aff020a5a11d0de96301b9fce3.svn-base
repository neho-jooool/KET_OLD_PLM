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
import java.util.ArrayList;

import e3ps.common.web.PageControl;

/**
 * 
 * @version 1.0
 **/

public class KETSearchEcoVO extends GeneralVO implements Serializable {
    private static final long               serialVersionUID = 1L;

    @SuppressWarnings("unused")
    private static final String             RESOURCE         = "e3ps.ecm.entity.entityResource";
    @SuppressWarnings("unused")
    private static final String             CLASSNAME        = KETSearchEcoVO.class.getName();

    private String                          partType;                                           //부품유형
    private String                          partTypeName;                                       //부품유형명
    private String                          partNumber;                                         //부품번호
    private String                          partName;                                           //부품명

    private String                          epmNumber;                                          //도면번호
    private String                          epmName;                                            //도면명

    private String                          dieNo;                                              //DieNo

    private String                          partOid;                                            //부품oid

    private String                          projectOid;                                         //프로젝트oid

    private String                          projectNo;                                          //프로젝트번호

    private String                          projectName;                                        //프로젝트명
    private String                          orgOid;                                             //부서oid
    private String                          orgName;                                            //부서명
    private String                          creatorOid;                                         //작성자oid
    private String                          ecoId;                                              //ECO번호
    private String                          divisionFlag;                                       //사업부구분

    private String                          createStartDate;                                    //작성시작일자
    private String                          createEndDate;                                      //작성종료일자
    private String                          prodMoldCls;                                        //제품/금형구분
    private String                          devYn;                                              //개발/양산구분
    private String                          SancStateFlag;                                      //상태구분
    private String                          ecoName;                                            //ECO제목
    private String                          ecoOid;                                             //ECO OID
    private ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList;                           //ECO 검색상세 List
    private int                             page;                                               //페이지번호
    private int                             perPage;                                            //페이지당 자료수
    private int                             totalCount;                                         //전체자료수(0이면 신규조회, 이외값인 경우 페이징조회)
    private int                             resultRows;                                         //현재페이지의 자료수

    private PageControl                     pageControl;                                        //PageControl
    private int                             pagingYn;                                           //페이지처리여부(0이면 페이지무시, 1:페이지처리)
    private String                          sortColumn;                                         //정렬컬럼
    private String                          param;                                              //iframe 여부(parent. 혹은 "")
    private String                          active;
    //2013.03.13 e3ps shkim add
    private String                          ecrNumber;                                          //ecr번호

    public String getActive() {
	return active;
    }

    public String getCreateEndDate() {
	return createEndDate;
    }

    public String getCreateStartDate() {
	return createStartDate;
    }

    public String getCreatorOid() {
	return creatorOid;
    }



    public String getDevYn() {
	return devYn;
    }

    public String getDieNo() {
	return dieNo;
    }


    public String getDivisionFlag() {
	return divisionFlag;
    }

    public String getEpmName() {
	return epmName;
    }

    public String getEpmNumber() {
	return epmNumber;
    }

    public String getEcoId() {
	return ecoId;
    }

    public String getEcoName() {
	return ecoName;
    }

    public String getEcoOid() {
	return ecoOid;
    }

    public String getEcrNumber() {
	return ecrNumber;
    }

    public ArrayList<KETSearchEcoDetailVO> getKetSearchEcoDetailVOList() {
	return ketSearchEcoDetailVOList;
    }

    public String getOrgName() {
	return orgName;
    }

    public String getOrgOid() {
	return orgOid;
    }

    public int getPage() {
	return page;
    }

    public PageControl getPageControl() {
	return pageControl;
    }

    public int getPagingYn() {
	return pagingYn;
    }

    public String getParam() {
	return param;
    }

    public String getPartName() {
	return partName;
    }

    public String getPartNumber() {
	return partNumber;
    }

    public String getPartOid() {
	return partOid;
    }

    public String getPartType() {
	return partType;
    }

    public String getPartTypeName() {
	return partTypeName;
    }

    public int getPerPage() {
	return perPage;
    }

    public String getProdMoldCls() {
	return prodMoldCls;
    }

    public String getProjectName() {
	return projectName;
    }

    public String getProjectNo() {
	return projectNo;
    }

    public String getProjectOid() {
	return projectOid;
    }

    public int getResultRows() {
	return resultRows;
    }

    public String getSancStateFlag() {
	return SancStateFlag;
    }

    public String getSortColumn() {
	return sortColumn;
    }

    public int getTotalCount() {
	return totalCount;
    }

    public void setActive(String active) {
	this.active = active;
    }

    public void setCreateEndDate(String createEndDate) {
	this.createEndDate = createEndDate;
    }

    public void setCreateStartDate(String createStartDate) {
	this.createStartDate = createStartDate;
    }

    public void setCreatorOid(String creatorOid) {
	this.creatorOid = creatorOid;
    }

    public void setDevYn(String devYn) {
	this.devYn = devYn;
    }

    public void setDieNo(String dieNo) {
	this.dieNo = dieNo;
    }

    public void setDivisionFlag(String divisionFlag) {
	this.divisionFlag = divisionFlag;
    }

    public void setEpmName(String epmName) {
	this.epmName = epmName;
    }

    public void setEpmNumber(String epmNumber) {
	this.epmNumber = epmNumber;
    }

    public void setEcoId(String ecoId) {
	this.ecoId = ecoId;
    }

    public void setEcoName(String ecoName) {
	this.ecoName = ecoName;
    }

    public void setEcoOid(String ecoOid) {
	this.ecoOid = ecoOid;
    }

    public void setEcrNumber(String ecrNumber) {
	this.ecrNumber = ecrNumber;
    }

    public void setKetSearchEcoDetailVOList(ArrayList<KETSearchEcoDetailVO> ketSearchEcoDetailVOList) {
	this.ketSearchEcoDetailVOList = ketSearchEcoDetailVOList;
    }

    public void setOrgName(String orgName) {
	this.orgName = orgName;
    }

    public void setOrgOid(String orgOid) {
	this.orgOid = orgOid;
    }

    public void setPage(int page) {
	this.page = page;
    }

    public void setPageControl(PageControl pageControl) {
	this.pageControl = pageControl;
    }

    public void setPagingYn(int pagingYn) {
	this.pagingYn = pagingYn;
    }

    public void setParam(String param) {
	this.param = param;
    }

    public void setPartName(String partName) {
	this.partName = partName;
    }

    public void setPartNumber(String partNumber) {
	this.partNumber = partNumber;
    }

    public void setPartOid(String partOid) {
	this.partOid = partOid;
    }

    public void setPartType(String partType) {
	this.partType = partType;
    }

    public void setPartTypeName(String partTypeName) {
	this.partTypeName = partTypeName;
    }

    public void setPerPage(int perPage) {
	this.perPage = perPage;
    }

    public void setProdMoldCls(String prodMoldCls) {
	this.prodMoldCls = prodMoldCls;
    }

    public void setProjectName(String projectName) {
	this.projectName = projectName;
    }

    public void setProjectNo(String projectNo) {
	this.projectNo = projectNo;
    }

    public void setProjectOid(String projectOid) {
	this.projectOid = projectOid;
    }

    public void setResultRows(int resultRows) {
	this.resultRows = resultRows;
    }

    public void setSancStateFlag(String sancStateFlag) {
	SancStateFlag = sancStateFlag;
    }

    public void setSortColumn(String sortColumn) {
	this.sortColumn = sortColumn;
    }

    public void setTotalCount(int totalCount) {
	this.totalCount = totalCount;
    }

}
