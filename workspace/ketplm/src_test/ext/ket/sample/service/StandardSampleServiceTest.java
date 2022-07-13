package ext.ket.sample.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import e3ps.common.util.CommonUtil;
import ext.ket.sample.entity.Sample;
import ext.ket.sample.entity.SampleDTO;
import ext.ket.shared.test.AbstractUnitTest;

/**
 * Sample service 객체의 테스트를 위한 test suite
 * 
 * @클래스명 : StandardSampleServiceTest
 * @작성자 : sw775.park
 * @작성일 : 2014. 6. 5.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StandardSampleServiceTest extends AbstractUnitTest {

    @Test
    public void testFind() throws Exception {
	assertThat(SampleHelper.service.find(new SampleDTO()).size(), not(0));
    }

    @Test
    public void testModify() throws Exception {
	String modifyText = "Modify_suceess";
	SampleDTO sampleDTO = new SampleDTO();
	sampleDTO.setName("swpark");
	sampleDTO.setNum(1);
	sampleDTO.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]Junit 테스트에서 등록");
	Sample newSample = SampleHelper.service.save(sampleDTO);
	// 수정
	sampleDTO.setOid(CommonUtil.getOIDString(newSample));
	sampleDTO.setTitle("Modify_suceess");
	Sample modifyedSample = SampleHelper.service.modify(sampleDTO);
	assertThat(modifyedSample.getTitle(), is(modifyText));

    }

    @Test
    public void testDelete() throws Exception {
	List<Sample> sampleList = SampleHelper.service.find(new SampleDTO());
	SampleDTO sampleDTO = new SampleDTO();
	for (Sample sample : sampleList) {
	    sampleDTO.setOid(CommonUtil.getOIDString(sample));
	    SampleHelper.service.delete(sampleDTO);
	    break;
	}
	List<Sample> deleteSampleList = SampleHelper.service.find(new SampleDTO());
	assertThat(sampleList.size(), not(deleteSampleList.size()));
    }

    @Test
    public void testSave() throws Exception {
	SampleDTO sampleDTO = new SampleDTO();
	sampleDTO.setNum(9999);
	sampleDTO.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]Junit 테스트에서 등록");
	SampleHelper.service.save(sampleDTO);
    }

    @Test
    public void testSaveForPerformance() throws Exception {
	List<Sample> sampleList = SampleHelper.service.find(new SampleDTO());
	SampleDTO sampleDTO = new SampleDTO();
	for (Sample sample : sampleList) {
	    sampleDTO.setOid(CommonUtil.getOIDString(sample));
	    SampleHelper.service.delete(sampleDTO);
	}

	for (int i = 1; i < 10000; i++) {
	    sampleDTO.setNum(i);
	    sampleDTO.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]속도 테스트");
	    SampleHelper.service.save(sampleDTO);
	}
    }

}
