package ext.ket.shared.suggest.service;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import ext.ket.shared.log.Kogger;
import ext.ket.shared.suggest.entity.KETSuggestDTO;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardKETSuggestServiceTest extends AbstractUnitTest {

    @Test
    public void testFind4ECO() throws Exception {
	KETSuggestDTO paramObject = new KETSuggestDTO();
	paramObject.setSuggestType(KETSuggestDTO.SUGGEST_ECONO);
	paramObject.setInputParam("1406");

	List<Map<String, String>> resultList = KETSuggestHelper.service.find(paramObject);
	for (Map<String, String> map : resultList) {
	    Kogger.debug(getClass(), map);
	}
	assertThat(resultList.size(), not(0));
    }

    @Test
    public void testFind4ECR() throws Exception {
	KETSuggestDTO paramObject = new KETSuggestDTO();
	paramObject.setSuggestType(KETSuggestDTO.SUGGEST_ECRNO);
	paramObject.setInputParam("1406");

	List<Map<String, String>> resultList = KETSuggestHelper.service.find(paramObject);
	for (Map<String, String> map : resultList) {
	    Kogger.debug(getClass(), map);
	}
	assertThat(resultList.size(), not(0));
    }

    @Test
    public void testFind4CustomerEvent() throws Exception {
	KETSuggestDTO paramObject = new KETSuggestDTO();
	paramObject.setSuggestType(KETSuggestDTO.SUGGEST_CUSTOMEREVENT);
	paramObject.setInputParam("H");

	List<Map<String, String>> resultList = KETSuggestHelper.service.find(paramObject);
	for (Map<String, String> map : resultList) {
	    Kogger.debug(getClass(), map);
	}
	assertThat(resultList.size(), not(0));
    }

}
