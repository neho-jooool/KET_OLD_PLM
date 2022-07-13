package ext.ket.edm.service;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import ext.ket.edm.entity.KETDrawingDistRequest;
import ext.ket.edm.entity.dto.KETDrawingDistReqDTO;
import ext.ket.shared.test.AbstractUnitTest;

/**
 * Sample service 객체의 테스트를 위한 test suite
 * 
 * @클래스명 : StandardDrawingDistServiceTest
 * @작성자 : sw775.park
 * @작성일 : 2014. 6. 5.
 * @설명 :
 * @수정이력 - 수정일,수정자,수정내용
 * 
 */
public class StandardDrawingDistServiceTest extends AbstractUnitTest {

    @Test
    public void testFind() throws Exception {
	assertThat(DrawingDistHelper.service.find(new KETDrawingDistReqDTO()).size(), not(0));
    }

    @Test
    public void testDelete() throws Exception {
	/*
	 * List<KETDrawingDistRequest> sampleList = DrawingDistHelper.service.find(new KETDrawingDistReqDTO()); KETDrawingDistReqDTO
	 * paramObject = new KETDrawingDistReqDTO(); for (KETDrawingDistRequest object : sampleList) {
	 * paramObject.setOid(CommonUtil.getOIDString(object)); DrawingDistHelper.service.delete(paramObject); break; }
	 * List<KETDrawingDistRequest> deleteSampleList = DrawingDistHelper.service.find(new KETDrawingDistReqDTO()); int delCount =
	 * deleteSampleList.size();
	 * 
	 * assertThat(sampleList.size(), not(delCount));
	 */
    }

    @Test
    public void testSave() throws Exception {

	KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();

	paramObject.setNumber("TKLEE001");
	paramObject.setName("TKLEE001");
	paramObject.setTitle("TKLEE001");
	paramObject.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]Junit 테스트에서 등록");
	paramObject.setDistType("APPROVED");
	paramObject.setDistReason("가공품 제작");
	paramObject.setDistSubcontractor("협력업체1");
	paramObject.setWriteDeptEnName("R&D Development 3 Team");
	paramObject.setWriteDeptKrName("연구개발3팀");
	paramObject.setWriteDeptCode("11737");

	// 배포 포멧
	paramObject.setDrawingDistFileTypeArray("PDF,DWG");

	// 배포 부서
	paramObject.setDrawingDistDeptArray("e3ps.groupware.company.Department:29845,e3ps.groupware.company.Department:226030535");

	KETDrawingDistRequest newSample = DrawingDistHelper.service.save(paramObject);
    }

    // @Test
    // public void testModify() throws Exception {
    // // String modifyText = "Modify_suceess";
    // KETDrawingDistReqDTO paramObject = new KETDrawingDistReqDTO();
    // paramObject.setNumber("TKLEE001");
    // paramObject.setName("TKLEE001");
    // paramObject.setTitle("TKLEE001");
    // paramObject.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]Junit 테스트에서 등록");
    // paramObject.setDistType(DrawingDistributeType.APPROVED.toString());
    // paramObject.setDistReason("가공품 제작");
    // paramObject.setDistSubcontractor("협력업체1");
    // paramObject.setWriteDeptEnName("R&D Development 3 Team");
    // paramObject.setWriteDeptKrName("연구개발3팀");
    // paramObject.setWriteDeptCode("11737");
    //
    // // 배포 포멧
    // paramObject.setDrawingDistFileTypeArray("PDF,DWG");
    //
    // // 배포 부서
    // paramObject.setDrawingDistDeptArray("e3ps.groupware.company.Department:29845,e3ps.groupware.company.Department:226030535");
    //
    // KETDrawingDistRequest newSample = DrawingDistHelper.service.save(paramObject);
    // // 수정
    // // paramObject.setOid(CommonUtil.getOIDString(newSample));
    // // paramObject.setTitle("Modify_suceess");
    // // KETDrawingDistRequest modifyedSample = DrawingDistHelper.service.modify(sampleDTO);
    // // assertThat(modifyedSample.getTitle(), is(modifyText));
    //
    // }

    // @Test
    // public void testSaveForPerformance() throws Exception {
    // List<KETDrawingDistRequest> sampleList = DrawingDistHelper.service.find(new KETDrawingDistReqDTO());
    // KETDrawingDistReqDTO sampleDTO = new KETDrawingDistReqDTO();
    // for (KETDrawingDistRequest sample : sampleList) {
    // paramObject.setOid(CommonUtil.getOIDString(sample));
    // DrawingDistHelper.service.delete(sampleDTO);
    // }
    //
    // for (int i = 1; i < 10000; i++) {
    // paramObject.setNumber("TKLEE-M" + i);
    // paramObject.setName("TKLEE-M" + i);
    // paramObject.setTitle("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]속도 테스트");
    // DrawingDistHelper.service.save(sampleDTO);
    // }
    // }

}
