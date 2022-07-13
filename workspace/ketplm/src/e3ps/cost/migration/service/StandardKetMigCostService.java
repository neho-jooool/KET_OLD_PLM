package e3ps.cost.migration.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wt.services.StandardManager;
import wt.util.WTException;
import e3ps.cost.dao.CostReportDao;
import e3ps.cost.migration.CostReportPropDTO;
import e3ps.cost.migration.CostReportPropLoader;
import e3ps.cost.util.DBUtil;
import ext.ket.part.entity.KETPartClassification;
import ext.ket.shared.log.Kogger;
import ext.ket.shared.query.QueryFactory;
import ext.ket.shared.query.SimpleQuerySpec;

public class StandardKetMigCostService extends StandardManager implements KetMigCostService {
    
    private static final long serialVersionUID = 1L;
    private SimpleQuerySpec query = QueryFactory.getInstance().getQuerySpec();
    private Map<String, KETPartClassification> clazMap = new HashMap<String, KETPartClassification>();

    public static StandardKetMigCostService newStandardKetMigCostService() throws WTException {
	StandardKetMigCostService instance = new StandardKetMigCostService();
	instance.initialize();
	return instance;
    }


    @Override
    public void migCostRerpot(String filePath, String lastSheet, String fileName) throws Exception {
	// TODO Auto-generated method stub
	try {

	    CostReportPropLoader loader = new CostReportPropLoader();
	    int lastSheetInt = Integer.parseInt(lastSheet);

	    for (int k = 0; k <= lastSheetInt; k++) {
		Kogger.debug(getClass(), "****************  Excel Extract Start " + fileName + k + " **************************");

		loader.load(filePath, fileName, k);
		List<CostReportPropDTO> propList = loader.getPropList();
		/*for(CostReportPropDTO dto :propList){
		    loader.printDTO(dto);
		}*/
		List<String> projectNos = new ArrayList<String>();
		boolean isInsert = true;
	        for (int i = 0; i < propList.size(); i++) {
	            CostReportPropDTO dto = propList.get(i);
	            if (projectNos.contains(dto.getProjectNo()+dto.getRevision())) {
	        	isInsert = false;
	        	System.out.println("ERROR !! ************ 동일한 리비전의 프로젝트는 업로드 할 수 없습니다. 번호 : " +dto.getProjectNo() +" 리비전 : "+ dto.getRevision());
	        	//throw new WTException("************ 동일한 리비전의 프로젝트는 업로드 할 수 없습니다. 번호 : " +dto.getProjectNo() +" 리비전 : "+ dto.getRevision());
	            }
	            projectNos.add(dto.getProjectNo()+dto.getRevision());
	        }
	        if(isInsert){
	            insertCostReportProp(propList);
	        }
		propList.clear();

		Kogger.debug(getClass(), "****************  Excel Extract End " + fileName + k + " **************************");
	    }

	} catch (Exception e) {
	    Kogger.error(getClass(), e);
	    throw e;
	} finally {
	}
	
    }
    
    private void insertCostReportProp(List<CostReportPropDTO> propList){
	String pk_crp = ""; //cost_report pk 보고서
        String pk_wid = ""; //wf_info pk  결재
   
        Connection conn = null;
        try{
            conn = DBUtil.getConnection(false);
            CostReportDao codeDao = new CostReportDao(conn);
            PreparedStatement pstmt = null;
            StringBuffer sb = new StringBuffer();
            ResultSet rs = null;
            
            
    	      //loader.printDTO(dto);
    	    codeDao.InsertRerpotMig(propList);
    	    
            conn.commit();
        }catch(Exception e){
            try {
		conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
    	    DBUtil.close(conn);
        }
    }
    
}