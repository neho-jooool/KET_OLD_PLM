package ext.ket.sysif.solr.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import wt.fc.Persistable;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import e3ps.project.beans.ProjectHelper;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.util.EjsConverUtil;
import ext.ket.sysif.solr.entity.IndexSearchDTO;
import ext.ket.sysif.solr.service.IndexSearchHelper;
import ext.ket.sysif.solr.service.SolrService;

@Controller
@RequestMapping("/index/*")
public class IndexSearchController {
    @Inject
    private SolrService solrService;

    /**
     * 통합 검색 화면
     * 
     * @throws Exception
     * @메소드명 : searchList
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 13.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/searchList")
    public void searchList(IndexSearchDTO indexSearchDTO, Model model) throws Exception {
	model.addAttribute("dto", indexSearchDTO);
    }

    /**
     * 통합 검색 query
     * 
     * @param indexSearchDTO
     * @return
     * @throws Exception
     * @메소드명 : findDoc
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 18.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findDoc")
    @ResponseBody
    public Map<String, Object> findDoc(IndexSearchDTO indexSearchDTO) throws Exception {
	List<IndexSearchDTO> docList = new ArrayList<IndexSearchDTO>();
	E3PSProject pjt = null;
	boolean existPjt = false;
	for (String ufid : IndexSearchHelper.service.query(indexSearchDTO)) {
	    Persistable persistable = (Persistable) CommonUtil.getObject(ufid);
	    if (persistable != null) {
		try {
		    if (persistable instanceof E3PSProject && !existPjt) {
			pjt = (E3PSProject) persistable;
			if (!pjt.isLastest()) {
			    continue;
			}
			existPjt = true;
		    }
		    IndexSearchDTO dto = new IndexSearchDTO(persistable);
		    // title이 있고, 중복이 없으면 리스트에 담는다.
		    if (!StringUtil.isEmpty(dto.getTitle()) && !docList.contains(dto)) {
			docList.add(dto);
		    }
		} catch (Exception e) {
		    Kogger.error(getClass(), e);
		    continue;
		}
	    }
	}
	if (!existPjt && pjt != null) {// 이유는 모르겠으나 검색엔진에서 최신 프로젝트를 가져오지 못하는 경우가 있어 아래와 같이 처리한다 2021.03.24 황정태
	    pjt = ProjectHelper.getProject(pjt.getPjtNo());
	    IndexSearchDTO dto = new IndexSearchDTO(pjt);
	    if (!StringUtil.isEmpty(dto.getTitle()) && !docList.contains(dto)) {
		docList.add(dto);
	    }

	}
	return EjsConverUtil.convertToDto(docList);
    }

    /**
     * 문서 검색 query
     * 
     * @param indexSearchDTO
     * @return
     * @throws Exception
     * @메소드명 : findDoc
     * @작성자 : sw775.park
     * @작성일 : 2014. 6. 18.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    @RequestMapping("/findPagingDoc")
    @ResponseBody
    public Map<String, Object> findPagingDoc(IndexSearchDTO indexSearchDTO) throws Exception {
	SolrDocumentList solrDocumentList = solrService.query(indexSearchDTO);
	List<IndexSearchDTO> docList = new ArrayList<IndexSearchDTO>();

	for (SolrDocument solrDocument : solrDocumentList) {
	    // Kogger.debug(getClass(), "ufid >> " + solrDocument.getFieldValue("ufid"));
	    // Kogger.debug(getClass(), "id >> " + solrDocument.getFieldValue("id"));
	    Persistable wtDocument = (Persistable) CommonUtil.getObject((String) solrDocument.getFieldValue("ufid"));
	    if (wtDocument != null) {
		IndexSearchDTO dto = new IndexSearchDTO(wtDocument);
		docList.add(dto);
	    }
	}
	return EjsConverUtil.convertToDto(docList);
    }
}
