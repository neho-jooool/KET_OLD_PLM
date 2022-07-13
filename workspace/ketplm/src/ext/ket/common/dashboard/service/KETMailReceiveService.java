package ext.ket.common.dashboard.service;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import wt.method.RemoteInterface;
import wt.org.WTUser;
import ext.ket.common.dashboard.entity.dto.KETMailReceiveDTO;
import ext.ket.dashboard.entity.DashBoardDTO;

@RemoteInterface
public interface KETMailReceiveService {

    public List<KETMailReceiveDTO> searchFullList() throws Exception;

    public List<KETMailReceiveDTO> searchFullList(String oid) throws Exception;

    public void save(KETMailReceiveDTO dto) throws Exception;

    public void delete(String oid) throws Exception;

    public HashMap<String, String> getTaskCount(DashBoardDTO dashBoardDTO, SqlSession session) throws Exception;
    
    public List<WTUser> dashboardSendMail() throws Exception;
    
    public void pjtMainScheduleSendMail() throws Exception;
}