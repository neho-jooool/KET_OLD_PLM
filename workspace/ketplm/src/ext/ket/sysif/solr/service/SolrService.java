package ext.ket.sysif.solr.service;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.impl.XMLResponseParser;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;
import ext.ket.sysif.solr.entity.IndexSearchDTO;

/**
 * SolrJ를 통해 WC 번들인 Solr 검색엔진에 query를 날릴 수 있는 서비스 객체
 * 
 * <p>
 * 검색 keyword 및 결과는 아래 schema 구조처럼 검색 결과에 표시됩니다.
 * </p>
 * <p>
 * 참조 D:\ptc\Windchill_10.2\Windchill\solr-home\wblib\conf\schema.xml
 * </p>
 * 
 * <pre>
 * <fields>
 *   <!-- Modelled And/Or fixed fields. Case insensitive fields are mapped as Text -->
 *   <field name="id" type="string" indexed="true" stored="true" required="true" />
 *   <field name="indexRequestId" type="long" stored="true" />
 *   <field name="objectId" type="long" stored="true" />
 *   <field name="ufid" type="string" indexed="true" stored="true"/>
 *   <field name="name" type="text" indexed="true" stored="true"/>
 *   <field name="number" type="text" indexed="true" stored="true"/>
 *   <field name="containerReference" type="textSimple" indexed="true" stored="true"/>
 *   <field name="sharedContainerReference" type="textSimple" indexed="true" stored="true"/>
 *   <field name="organizationReference" type="string" indexed="true" stored="true"/>
 *   <field name="createTimestamp" type="date" indexed="true" stored="true"/>
 *   <field name="modifyTimestamp" type="date" indexed="true" stored="true"/>
 *   <field name="businessType" type="text_ws" indexed="true" stored="true"/>
 *   <field name="creator" type="text" indexed="true" stored="true"/>
 *   <field name="modifier" type="text" indexed="true" stored="true"/>
 *   <field name="iscontent" type="string" indexed="false" stored="true"/>
 *   <field name="contentType" type="string" indexed="false" stored="true"/>
 *   <field name="lifeCycleState" type="string" indexed="true" stored="true"/>   
 *   ~~~~~ 생략 ~~~~
 * </fields>
 * 
 * </pre>
 * 
 * @클래스명 : SolrService
 * @작성자 : sw775.park
 * @작성일 : 2014. 5. 20.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
@SuppressWarnings("deprecation")
@Service
public class SolrService {
    /**
     * 아래 property는 src_resources/properties/context.properties에 정의 되어 있습니다.
     */
    @Value("#{contextProperties['solr.server.url']}")
    String SOLR_SERVER_URL;

    /**
     * 검색엔진으로 부터 쿼리 실행 method
     * <p>
     * 아래 Exception이 날 경우<br>
     * queryString의 문법 오류인 경우에 발생할 수 있습니다.
     * </p>
     * 
     * <pre>
     * 	org.apache.solr.client.solrj.SolrServerException: Server at http://plm.ket.com/plm-Solr/wblib returned non ok status:400, message:Bad Request
     * </pre>
     * 
     * @param indexSearchDTO
     * @return
     * @throws Exception
     * @메소드명 : query
     * @작성자 : sw775.park
     * @작성일 : 2014. 5. 20.
     * @설명 :
     * @수정이력 - 수정일,수정자,수정내용
     * 
     */
    public SolrDocumentList query(IndexSearchDTO indexSearchDTO) throws Exception {
	if (StringUtil.isEmpty(indexSearchDTO.getQueryString())) {
	    return new SolrDocumentList();
	}

	HttpSolrServer server = new HttpSolrServer(SOLR_SERVER_URL);
	// server.setMaxRetries(1); // defaults to 0. > 1 not recommended.
	// server.setConnectionTimeout(5000); // 5 seconds to establish TCP
	server.setParser(new XMLResponseParser());
	DefaultHttpClient httpClient = (DefaultHttpClient) server.getHttpClient();
	Credentials credentials = new UsernamePasswordCredentials("wcadmin", "wcadmin");
	httpClient.getCredentialsProvider().setCredentials(new AuthScope("plm.ket.com", 80, AuthScope.ANY_REALM), credentials);

	SolrQuery query = new SolrQuery();
	query.setParam("fl", "ufid");
	// keywords_en 한글/영문 통합 자연어 검색
	query.setQuery("(keywords_en:(" + indexSearchDTO.getQueryString() + "))");
	// 특정 businessType으로 filter
	query.addFilterQuery("businessType:(e3ps.dms.entity.KETTechnicalDocument or e3ps.dms.entity.KETProjectDocument)");
	// sort
	query.addSortField("createTimestamp", SolrQuery.ORDER.desc);
	// // paging rows
	// query.setRows(solrDTO.getOffSet());
	// // paging start
	// query.setStart(solrDTO.getLimit());
	// 쿼리 실행
	QueryResponse rsp = server.query(query);
	// 쿼리 결과
	SolrDocumentList docs = rsp.getResults();
	// Kogger.debug(getClass(), "result >> " + docs); // --> 참조하면 JSON Type으로 결과가 나타나는걸 확인할 수 있습니다.
	// for (SolrDocument solrDocument : docs) {
	// Kogger.debug(getClass(), "ufid >> " + solrDocument.getFieldValue("ufid"));
	// // Kogger.debug(getClass(), "id >> " + solrDocument.getFieldValue("id"));
	// }
	return docs;
    }

    public SolrDocumentList suggest(String queryString) throws Exception {

	HttpSolrServer server = new HttpSolrServer(SOLR_SERVER_URL);
	server.setParser(new XMLResponseParser());
	DefaultHttpClient httpClient = (DefaultHttpClient) server.getHttpClient();
	Credentials credentials = new UsernamePasswordCredentials("wcadmin", "wcadmin");
	httpClient.getCredentialsProvider().setCredentials(new AuthScope("plm.ket.com", 80, AuthScope.ANY_REALM), credentials);

	SolrQuery query = new SolrQuery();
	// suggester
	query.setParam("spellcheck", "true");
	query.setParam("spellcheck.q", queryString);
	query.setParam("spellcheck.build", "true");
	// keywords_en 한글/영문 통합 자연어 검색
	// query.setQuery(queryString);
	// 특정 businessType으로 filter
	query.addFilterQuery("businessType:(e3ps.dms.entity.KETTechnicalDocument or e3ps.dms.entity.KETProjectDocument)");
	// sort
	// query.addSortField("createTimestamp", SolrQuery.ORDER.desc);
	// 쿼리 실행
	QueryResponse rsp = server.query(query);
	// 쿼리 결과
	SolrDocumentList docs = rsp.getResults();
	Kogger.debug(getClass(), "result >> " + docs); // --> 참조하면 JSON Type으로 결과가 나타나는걸 확인할 수 있습니다.
	for (SolrDocument solrDocument : docs) {
	    Kogger.debug(getClass(), "ufid >> " + solrDocument.getFieldValue("ufid"));
	    Kogger.debug(getClass(), "id >> " + solrDocument.getFieldValue("id"));
	}
	return docs;
    }
}
