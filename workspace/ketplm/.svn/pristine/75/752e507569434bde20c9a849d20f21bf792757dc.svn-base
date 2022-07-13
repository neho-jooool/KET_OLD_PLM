package ext.ket.project.program.service;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ext.ket.project.program.entity.ProgramDTO;
import ext.ket.project.program.entity.ProgramEventDTO;
import ext.ket.project.program.entity.ProgramNotifyDTO;

public class StandardProgramServiceTest {

    @Test
    public void testFind() {
	fail("Not yet implemented");
    }

    @Test
    public void testFindPaging() {
	fail("Not yet implemented");
    }

    @Test
    public void testSave() throws Exception {
	ProgramEventDTO eventDTO = new ProgramEventDTO();
	eventDTO.setCarEventOid("e3ps.project.outputtype.OEMPlan:747701667");
	List<ProgramEventDTO> events = new ArrayList<ProgramEventDTO>();
	events.add(eventDTO);
	ProgramDTO programDTO = new ProgramDTO();
	programDTO.setEvents(events);
	programDTO.setProgramName("Unit 테스트123");
	programDTO.setProgramAdmin("wt.org.WTUser:28475");
	programDTO.setDivisionCode("CA"); // 자동차사업부
	programDTO.setCarType("e3ps.project.outputtype.OEMProjectType:156405520"); // 차종
	programDTO.setLastUsingBuyer("e3ps.common.code.NumberCode:463728419");// 최종사용처
	programDTO.setSubContractor("e3ps.common.code.NumberCode:448241513");// 고객처
	programDTO.setFormType("e3ps.common.code.NumberCode:156404761");// 형태
	assertThat(ProgramHelper.service.save(programDTO), not(null));
    }

    @Test
    public void testModify() {
	fail("Not yet implemented");
    }

    @Test
    public void testDelete() {
	fail("Not yet implemented");
    }

    @Test
    public void testFindEventByCarType() throws Exception {
	String carTypeOid = "e3ps.project.outputtype.OEMProjectType:156405520";
	assertThat(ProgramHelper.service.findEventByCarType(carTypeOid).size(), not(0));
    }

    @Test
    public void testFindEventByProgram() throws Exception {
	String programOid = "ext.ket.project.program.entity.KETProgramSchedule:100000026045";
	assertThat(ProgramHelper.service.findEventByProgram(programOid).size(), not(0));
    }

    @Test
    public void testFindProgramByProject() throws Exception {
	String projectOid = "e3ps.project.ProductProject:100000329016";
	assertThat(ProgramHelper.service.findProgramByProject(projectOid).size(), not(0));
    }

    @Test
    public void testFindProjectByProject() throws Exception {
	String projectOid = "e3ps.project.ProductProject:100000329016";
	assertThat(ProgramHelper.service.findProjectByProject(projectOid).size(), not(0));
    }

    @Test
    public void testCompareNotifyHistory() throws Exception {
	List<String> versions = new ArrayList<String>();
	List<String> versionOids = new ArrayList<String>();
	versions.add("2");
	versions.add("1");
	versions.add("0");
	versionOids.add("ext.ket.project.program.entity.KETProgramSchedule:100000041543");
	versionOids.add("ext.ket.project.program.entity.KETProgramSchedule:100000041489");
	versionOids.add("ext.ket.project.program.entity.KETProgramSchedule:100000041418");
	ProgramNotifyDTO notifyDTO = new ProgramNotifyDTO();
	notifyDTO.setVersions(versions);
	notifyDTO.setVersionOids(versionOids);
	assertThat(ProgramHelper.service.compareNotifyHistory(notifyDTO).size(), not(0));
    }
}
