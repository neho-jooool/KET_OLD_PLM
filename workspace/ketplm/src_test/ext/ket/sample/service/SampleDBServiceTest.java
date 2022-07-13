package ext.ket.sample.service;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;

import ext.ket.sample.entity.SampleDTO;
import ext.ket.shared.test.AbstractUnitTest;

public class SampleDBServiceTest extends AbstractUnitTest {
    @Inject
    private SampleDBService sampleDBService;

    @Test
    public void testFindPaging() throws Exception {
	SampleDTO sampleDTO = new SampleDTO();
	sampleDTO.setPage(1);
	sampleDTO.setFormPage(20);
	assertThat(sampleDBService.findPanging(sampleDTO).size(), not(0));
	sampleDTO.setPage(2);
	sampleDTO.setFormPage(20);
	assertThat(sampleDBService.findPanging(sampleDTO).size(), not(0));
    }
}
