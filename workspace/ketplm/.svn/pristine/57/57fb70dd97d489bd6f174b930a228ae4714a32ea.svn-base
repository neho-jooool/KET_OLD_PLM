package ext.ket.sysif.solr.service;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import ext.ket.shared.test.AbstractUnitTest;
import ext.ket.sysif.solr.entity.IndexSearchDTO;

/**
 * Solr 검색엔진 query 테스트를 위한 test 객체
 * 
 * @클래스명 : SolrServiceTest
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class SolrServiceTest extends AbstractUnitTest {
    @Inject
    private SolrService solrService;

    @Test
    public void testQuery() throws Exception {
	IndexSearchDTO solrDTO = new IndexSearchDTO();
	solrDTO.setQueryString("임영근");
	// 일반 자연어 text 검색
	assertThat(solrService.query(solrDTO).size(), not(0));
	// 특수 검색(AND 또는 OR) -> 대문자로 사용해야 함.
	// solrDTO.setQueryString("문서 AND H610*");
	// assertThat(solrService.query(solrDTO).size(), not(0));
	// // schema를 이용한 검색()
	// solrDTO.setQueryString("name:문서 AND (creator:홍길동 OR modifier:홍길동)");
	// assertThat(solrService.query(solrDTO).size(), is(0));
    }

    @Test
    public void testSuggest() throws Exception {
	IndexSearchDTO solrDTO = new IndexSearchDTO();
	solrDTO.setQueryString("TEST");
	assertThat(solrService.suggest("TEST").size(), not(0));
    }

    @Test
    public void testWtQuery() throws Exception {
	IndexSearchDTO dto = new IndexSearchDTO();
	dto.setSearchCategory("");
	dto.setSearchKeyword("문서");
	IndexSearchHelper.service.query(dto);
    }
}
