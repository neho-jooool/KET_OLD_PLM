package ext.ket.bom.query;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ext.ket.shared.test.AbstractUnitTest;

public class KETBOMQueryBeanTest extends AbstractUnitTest {

    @Test
    public void testGetLatestChildPartByProduct() throws Exception {
	KETBOMQueryBean queryBean = new KETBOMQueryBean();
	List<String> productPartOidList = new ArrayList<String>();
	productPartOidList.add("wt.part.WTPart:615154004");
	assertThat(queryBean.getLatestChildPartByProduct(productPartOidList).size(), not(0));
    }

}
