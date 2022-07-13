/*
 * bcwti
 * Copyright (c) 2008 Parametric Technology Corporation (PTC). All Rights
 * Reserved.
 * This software is the confidential and proprietary information of PTC
 * and is subject to the terms of a software license agreement. You shall
 * not disclose such confidential information and shall use it only in accordance
 * with the terms of the license agreement.
 * ecwti
 */

package e3ps.project;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import wt.method.RemoteInterface;
import wt.util.WTException;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartListItemDTO;

/**
 * @version 1.0
 **/

@RemoteInterface
public interface E3PSProjectService {

    /**
     * TemplateProject 생성
     * 
     * @param hash
     * @return TemplateProject
     * @exception wt.util.WTException
     **/
    public TemplateProject createTemplateProject(Hashtable hash) throws WTException;

    /**
     * TemplateProject 수정
     * 
     * @param hash
     * @return TemplateProject
     * @exception wt.util.WTException
     **/
    public TemplateProject updateTemplateProject(Hashtable hash) throws WTException;

    /**
     * TemplateProject 삭제
     * 
     * @param hash
     * @return String
     * @exception wt.util.WTException
     **/
    public String deleteTemplateProject(Hashtable hash) throws WTException;

    /**
     * TemplateProject LOAD
     * 
     * @param hash
     * @return TemplateProject
     * @exception wt.util.WTException
     **/
    public TemplateProject loadTemplateProject(Hashtable hash) throws WTException;

    /**
     * JELProject 생성
     * 
     * @param hash
     * @return E3PSProject
     * @exception wt.util.WTException
     **/
    public E3PSProject createE3PSProject(Hashtable hash) throws WTException;

    /**
     * JELProject 수정
     * 
     * @param hash
     * @return E3PSProject
     * @exception wt.util.WTException
     **/
    public E3PSProject updateE3PSProject(Hashtable hash) throws WTException;

    /**
     * JELProject 삭제
     * 
     * @param hash
     * @return String
     * @exception wt.util.WTException
     **/
    public String deleteE3PSProject(Hashtable hash) throws WTException;

    /**
     * 중지
     * 
     * @param hash
     * @return E3PSProject
     * @exception wt.util.WTException
     **/
    public E3PSProject holdE3PSProject(Hashtable hash) throws WTException;

    /**
     * 취소
     * 
     * @param hash
     * @return E3PSProject
     * @exception wt.util.WTException
     **/
    public E3PSProject revocationE3PSProject(Hashtable hash) throws WTException;

    /**
     * @param projectoid
     * @return
     * @throws Exception
     * @메소드명 : getProductInfoWTPart
     * @작성자 : Jason, Han
     * @작성일 : 2014. 7. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     */
    public List<HashMap<String, String>> getProductInfoWTPart(String projectoid) throws Exception;

    /**
     * Project No의 Project Name을 반환
     * 
     * @param projectNo
     * @return
     * @throws Exception
     * @메소드명 : getProjectNameByProjectNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 27.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public String getProjectNameByProjectNo(String projectNo) throws Exception;

    /**
     * 제품정보 수정(기존 프로젝트 수정에서 분리)
     * 
     * @param hash
     * @param project
     * @return
     * @throws Exception
     * @메소드명 : updateProductInfo
     * @작성자 : sw775.park
     * @작성일 : 2014. 8. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public Hashtable updateProductInfo(Hashtable hash, ProductProject project) throws Exception;

    /**
     * 프로젝트 No를 Oid로 반환 한다.
     * 
     * @param projectNo
     * @return
     * @throws Exception
     * @메소드명 : getProjectOidByProjectNo
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 24.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public E3PSProject getProjectByProjectNo(String projectNo) throws Exception;

    /**
     * 금형 Item 속성정보 수정
     * 
     * @param map
     * @메소드명 : updateMoldItem
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 29.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateMoldItem(Map<String, Object> map) throws Exception;

    /**
     * RANK(수정시) S이면 관련 모듈의 RANK도 변경 한다.
     * 
     * @param project
     * @메소드명 : changeRankByProject
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void changeRankByProject(E3PSProject project, String beforeRank, String afterRank);

    /**
     * MoldItem 정보 업데이트
     * 
     * @param partNo
     * @param dieNo
     * @throws Exception
     * @메소드명 : updateMoldItemForSync
     * @작성자 : sw775.park
     * @작성일 : 2014. 10. 30.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public void updateMoldItemForSync(String partNo, String dieNo) throws Exception;

    /**
     * Partlist에서 필요한 프로젝트 정보 API1 적용차종 SOP 차종일정 제품개발 책임자 이름/부서 제품개발(H/W) 책임자 이름/부서 차종 예상수량(1년~6년) 및 합계
     * 
     * @param projectNo
     * @param partListDTO
     * @return
     * @throws Exception
     * @메소드명 : findProjectInfo1ForPartlist
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PartListDTO findProjectInfo1ForPartlist(String projectNo, PartListDTO partListDTO) throws Exception;

    /**
     * Partlist에서 필요한 프로젝트 정보 API2 부품과 dieNo의 투자비
     * 
     * @param partNo
     * @param dieNo
     * @param partListItemDTO
     * @return
     * @throws Exception
     * @메소드명 : findProjectInfo2ForPartlist
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PartListItemDTO findProjectInfo2ForPartlist(String partNo, String dieNo, PartListItemDTO partListItemDTO) throws Exception;

    /**
     * Partlist에서 필요한 프로젝트 정보 API3 부품과 dieNo의 투자비(예산) 및 설비 Ton
     * 
     * @param partNo
     * @param partListItemDTO
     * @return
     * @throws Exception
     * @메소드명 : findProjectInfo4ForPartlist
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PartListItemDTO findProjectInfo3ForPartlist(String partNo, String dieNo, PartListItemDTO partListItemDTO) throws Exception;

    /**
     * Partlist에서 필요한 프로젝트 정보 API4 부품의 고객처 정보
     * 
     * @param partNo
     * @param partListItemDTO
     * @return
     * @throws Exception
     * @메소드명 : findProjectInfo4ForPartlist
     * @작성자 : sw775.park
     * @작성일 : 2014. 11. 7.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public PartListItemDTO findProjectInfo4ForPartlist(String partNo, PartListItemDTO partListItemDTO) throws Exception;
    
    /**
     * 
     * 
     * @param project
     * @throws Exception
     * @메소드명 : deleteLinkProject
     * @작성자 : neho
     * @작성일 : 2020. 10. 13.
     * @설명 : 
     * @수정이력 - 수정일,수정자,수정내용  					   
     *
     */
    public void deleteLinkProject(E3PSProject project) throws Exception;
}
