package e3ps.project;

import org.junit.Test;

import wt.fc.QueryResult;
import wt.folder.Folder;
import wt.folder.FolderEntry;
import wt.folder.FolderHelper;
import wt.inf.container.WTContainerHelper;
import wt.inf.container.WTContainerRef;
import ext.ket.part.entity.dto.PartListDTO;
import ext.ket.part.entity.dto.PartListItemDTO;
import ext.ket.shared.test.AbstractUnitTest;

public class StandardE3PSProjectServiceTest extends AbstractUnitTest {

    @Test
    public void testGetProjectNameByProjectNo() throws Exception {
	// Kogger.debug(getClass(), "getProjectNameByProjectNo >> " + E3PSProjectHelper.service.getProjectNameByProjectNo("A14M006"));

	Folder folder2 = FolderHelper.service.getFolder("/Default/", WTContainerHelper.getExchangeRef());

	WTContainerRef wtContainer = WTContainerHelper.service.getByPath("/wt.inf.container.OrgContainer=ket/wt.pdmlink.PDMLinkProduct=KET");
	Folder folder = FolderHelper.service.getFolder("/Default/자동차사업부/프로젝트/GATE", wtContainer);

	QueryResult result = FolderHelper.service.findFolderContents(folder2);
	while (result.hasMoreElements()) {
	    FolderEntry sheet = (FolderEntry) result.nextElement();
	    FolderHelper.service.changeFolder((FolderEntry) sheet, folder);
	}
    }

    @Test
    public void testFindProjectInfo1ForPartlist() throws Exception {
	// LF HEV 차종
	E3PSProjectHelper.service.findProjectInfo1ForPartlist("A14B063", new PartListDTO());
	E3PSProjectHelper.service.findProjectInfo2ForPartlist("H615539", "23205000", new PartListItemDTO());
	E3PSProjectHelper.service.findProjectInfo3ForPartlist("H615539", "23205000", new PartListItemDTO());
	E3PSProjectHelper.service.findProjectInfo4ForPartlist("H645830-3", new PartListItemDTO());
    }

}
