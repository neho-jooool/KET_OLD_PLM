package e3ps.project.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.ReferenceFactory;
import e3ps.common.jdf.message.MessageBox;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.StringUtil;
import e3ps.project.E3PSProject;
import ext.ket.shared.log.Kogger;
//import e3ps.common.db.DBCPManager;

public class ProjectERPConMgr {
	private String JCO_CLIENT_CLIENT = MessageBox.getInstance("sapLogin").getMessage("jco.client.client");
	private String EAI_DBCP_NAME = MessageBox.getInstance("distribute").getMessage("eai.dbcp.name");
	private String PJT_TYPE_PRODUCE[] = MessageBox.getInstance("eSolution").getArray("project.type.produceproject");
	private String PJT_TYPE_DEV[] = MessageBox.getInstance("eSolution").getArray("project.type.devproject");
	
	static ProjectERPConMgr instance;
	
	public static ProjectERPConMgr getInstance() {
		if(instance==null)instance = new ProjectERPConMgr();
		return instance;
	}
	
	public ArrayList getProjectNO(String tempNO) {
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
		ArrayList vec = new ArrayList();
        
		try {
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			if(con == null){
				return vec;
			}
			StringBuffer sb = new StringBuffer();
			
			/**
			 * SQL
			 * select a.item_cd project_no,
			 * 		  a.item_nam||'..'||a.item_sp||'..'||a.unit project_name,
			 * 		  a.out_day project_delivereddate,
			 * 		  (select distinct aa.process_gcd||'-'||bb.gcd_name
			 * 		   from teb0340 aa, teb0460 bb
			 * 		   where aa.process_gcd = bb.process_gcd and aa.item_cd = a.item_cd
			 * 		   ) product,
			 * 		  b.cuname customer_name
			 * from teb0030 a, tsm0050 b
			 * where substr(a.item_cd, 1, 2) like '01' and substr(a.item_cd, 4, 1) not in ('0', '8') and a.ju_cust = b.cucode
			 * order by a.item_cd
			 */
			
			sb.append("select a.item_cd project_no, ");
			sb.append("a.item_nam||"+"'..'"+"||a.item_sp||"+"'..'"+"||a.unit project_name, ");
			sb.append("a.out_day project_delivereddate, ");
			sb.append("(");
			sb.append("select distinct aa.process_gcd||"+"'-'"+"||bb.gcd_name ");
			sb.append("from teb0340 aa, teb0460 bb ");
			sb.append("where aa.process_gcd = bb.process_gcd ");
			sb.append("and aa.item_cd = a.item_cd");
			sb.append(") PRODUCT, ");
			sb.append("b.cuname CUSTOMER_NAME ");
			sb.append("from teb0030 a, tsm0050 b ");
			sb.append("where a.item_cd = '"+tempNO.toUpperCase()+"' ");
			sb.append("and a.ju_cust = b.cucode ");
			sb.append("order by a.item_cd");
			
			st = con.createStatement();
            rs = st.executeQuery(sb.toString());
            
            while(rs.next()) {
            	String[] pjtField = new String[5];
            	
            	pjtField[0] = rs.getString(1)==null?"":rs.getString(1);		//PROJECT_NO
            	pjtField[1] = rs.getString(2)==null?"":rs.getString(2);		//PROJECT_NAME
            	pjtField[2] = rs.getString(3)==null?"":rs.getString(3);		//PROJECT_DELIVEREDDATE
            	pjtField[3] = rs.getString(4)==null?"":rs.getString(4);		//PRODUCT
            	pjtField[4] = rs.getString(5)==null?"":rs.getString(5);		//CUSTOMER_NAME
            	vec.add(pjtField);
            }
			
//			sb.append("select a.item_cd, a.item_nam, ");
//			sb.append("a.item_nam||"+"'-'"+"||a.item_sp||"+"'-'"+"||a.unit, ");
//			sb.append("a.JU_CUST,");
//			sb.append("a.OUT_DAY ");
//			sb.append("from TEB0030 a ");
//			sb.append("where a.item_cd = "+"'"+tempNO.toUpperCase()+"' ");
//			
//			
//			st = con.createStatement();
//			Kogger.debug(getClass(), sb.toString());
//            rs = st.executeQuery(sb.toString());
//            
//            while(rs.next()) {
//            	String[] pjtField = new String[5];
//            	
//            	int i = 0;
//            	pjtField[0] = rs.getString(1); //프로젝트NO
//            	pjtField[1] = rs.getString(2); //PRODUCT
//            	pjtField[2] = rs.getString(3); //프로젝트 명(장비명)
//            	pjtField[3] = rs.getString(4)==null? "":rs.getString(4); //거래처
//            	pjtField[4] = rs.getString(5)==null? "":rs.getString(5); //출하일자
//            	
//            	vec.add(pjtField);
//            	i++;
//            }
		} catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
		return vec;
	}
	
	public ArrayList getUpdateProjectNO(String projectOID) {
		E3PSProject jelProject = (E3PSProject) CommonUtil.getObject(projectOID);
		try {
			if(jelProject != null) {
//				JELProjectData jelData = new JELProjectData(jelProject);
				
				String old_No = null;				//프로젝트NO
				String old_Product = null;			//PRODUCT
				String old_Name = null;				//프로젝트 명(장비명)
				String old_Customer = null;			//거래처
				String old_DeliveredDate = null;	//출하일자
				
				String new_No = null;
				String new_Product = null;
				String new_Name = null;
				String new_Customer = null;
				String new_DeliveredDate = null;
				
//				ArrayList old_Array = getProjectNO(jelData.pjtNo);
//				for (int i = 0; i < old_Array.size(); i++) {
//					Object[] obj = (Object[]) old_Array.get(i);
//					old_No = StringUtil.checkNull(String.valueOf(obj[0]));
//					old_Product = StringUtil.checkNull(String.valueOf(obj[1]));
//					old_Name = StringUtil.checkNull(String.valueOf(obj[2]));
//					old_Customer = StringUtil.checkNull(String.valueOf(obj[3]));
//					old_DeliveredDate = StringUtil.checkNull(String.valueOf(obj[4]));
//				}
				
//				if(!(jelData.pjtNo.trim()).equalsIgnoreCase(old_No.trim())) {
//					new_No = old_No.trim();
//				}
				/*if(!(jelData.pjtProduct.trim()).equalsIgnoreCase(old_Product.trim())) {
					new_Product = old_Product.trim();
				}*/
//				if(!(jelData.pjtName.trim()).equalsIgnoreCase(old_Name.trim())) {
//					new_Name = old_Name.trim();
//				}
				//if(!(jelData.pjtCustomerLinkName.trim()).equalsIgnoreCase(old_Customer.trim())) {
					//new_Customer = old_Customer.trim();
				//}
				/*if(!(StringUtil.changeString(DateUtil.getDateString(jelData.pjtDeliveredDate,"D"), "/", "").trim()).equalsIgnoreCase(old_DeliveredDate.trim())) {
					new_DeliveredDate = old_DeliveredDate.trim();
				}*/
				
//				if(StringUtil.checkString(new_No) || StringUtil.checkString(new_Product) || StringUtil.checkString(new_Name) || StringUtil.checkString(new_Customer) || StringUtil.checkString(new_DeliveredDate)) {
//					return old_Array;
//				}
			}
		} catch (Exception e) {
			Kogger.error(getClass(), e);
		}
		return null;
	}

	public void getERPTableUpdate(String inputPN, String settingNo) {
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
	
		try {
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			
			if(con == null){
				return;
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append("update project_info_table set is_created = '"+settingNo+"' where project_no = '"+inputPN+"'and server_id ='"+JCO_CLIENT_CLIENT+"'");
			
			st = con.createStatement();
			Kogger.debug(getClass(), "####################");
			Kogger.debug(getClass(), JCO_CLIENT_CLIENT + "Project MIS Update Delete Query \n"+sb.toString());
			Kogger.debug(getClass(), "####################");
            rs = st.executeQuery(sb.toString());
            rs.close();
		} catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
	}
	
	public ArrayList getERPProjectList(String inputPN, String initType) {
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
		ArrayList array = new ArrayList();
        String inputno = "";
		String initTypeValue = "";
        
     	if(!inputPN.equals("")){
			inputno = inputPN;
		}
		if(initType.equals("")){
			initTypeValue = "produceproject";
		}else{
			initTypeValue = initType;
		}
		
		
		try {
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			
			if(con == null){
				return array;
			}
			
			StringBuffer sb = new StringBuffer();
			
			/**
			 * SQL
			 * select a.item_cd project_no,
			 * 		  a.item_nam||'..'||a.item_sp||'..'||a.unit project_name,
			 * 		  a.item_nam project_nam,
			 * 		  a.item_sp project_spec,
			 * 		  a.unit project_unit,
			 * 		  a.out_day project_delivereddate,
			 * 		  to_char((
			 * 		  			select sum(nvl(pm_qty, 0))
			 * 					from teb0340
			 * 					where item_cd = a.item_cd
			 * 				  )) pm_qty,
			 * 		  (select distinct aa.process_gcd||'-'||bb.gcd_name
			 * 		   from teb0340 aa, teb0460 bb
			 * 		   where aa.process_gcd = bb.process_gcd and aa.item_cd = a.item_cd
			 * 		   ) product,
			 * 		  a.ju_cust customer,
			 * 		  b.cuname customer_name,
			 * 		  decode( '#'||a.projectcd, '#', '', '#'||a.projectcd ) project_no_min,
			 * 		  decode( upper(substr(a.item_cd, 3, 1)), 'P', 'R', 'P' ) project_gubun
			 * from teb0030 a, tsm0050 b
			 * where substr(a.item_cd, 1, 2) like '01' and a.item_cd like '%50%'
			 * and substr(a.item_cd, 4, 1) not in ('0', '8') and a.ju_cust = b.cucode
			 * order by a.item_cd
			 */
			if(inputno.equals("")){
				if(initTypeValue.equals("devproject")){
					sb.append("select * from project_info_table \n");
					sb.append("where is_created = '0' and server_id ='"+JCO_CLIENT_CLIENT+"' \n");
					
					for(int i = 0; i < PJT_TYPE_DEV.length; i++) {
						if(i == 0)
							sb.append("and ( \n");
						else
							sb.append(" or \n");
						sb.append(" project_type='"+PJT_TYPE_DEV[i]+"' \n");
						
						if( (i+1) == PJT_TYPE_DEV.length) {
							sb.append(" ) ");
						}
					}
					
				}else{
					sb.append("select * from project_info_table \n");
					sb.append("where is_created = '0' and server_id ='"+JCO_CLIENT_CLIENT+"' \n");
					for(int i = 0; i < PJT_TYPE_PRODUCE.length; i++) {
						if(i == 0)
							sb.append("and ( \n");
						else
							sb.append(" or \n");
						
						sb.append(" project_type='"+PJT_TYPE_PRODUCE[i]+"' \n");
						
						if( (i+1) == PJT_TYPE_PRODUCE.length) {
							sb.append(" ) ");
						}
					}
				}
				
			}else{
				if(initTypeValue.equals("devproject")){
					sb.append("select * from project_info_table \n");
					sb.append("where project_no like '%"+inputno.toUpperCase()+"%' and is_created = '0' and server_id ='"+JCO_CLIENT_CLIENT+"' \n");	
					for(int i = 0; i < PJT_TYPE_DEV.length; i++) {
						if(i == 0)
							sb.append("and ( \n");
						else
							sb.append(" or \n");
						sb.append(" project_type='"+PJT_TYPE_DEV[i]+"' \n");
						
						if( (i+1) == PJT_TYPE_DEV.length) {
							sb.append(" ) ");
						}
					}
				}else{
					sb.append("select * from project_info_table ");
					sb.append("where project_no like '%"+inputno.toUpperCase()+"%' and is_created = '0' and server_id ='"+JCO_CLIENT_CLIENT+"' \n");	
					for(int i = 0; i < PJT_TYPE_PRODUCE.length; i++) {
						if(i == 0)
							sb.append("and ( \n");
						else
							sb.append(" or \n");
						
						sb.append(" project_type='"+PJT_TYPE_PRODUCE[i]+"' \n");
						
						if( (i+1) == PJT_TYPE_PRODUCE.length) {
							sb.append(" ) ");
						}
					}
				}
			}
			
			
			/*			
  			sb.append("select a.item_cd project_no, ");
			sb.append("a.item_nam||"+"'..'"+"||a.item_sp||"+"'..'"+"||a.unit project_name, ");
			sb.append("a.item_nam project_nam, ");
			sb.append("a.item_sp project_spec, ");
			sb.append("a.unit project_unit, ");
			sb.append("a.out_day project_delivereddate, ");
			sb.append("TO_CHAR((");
			sb.append("select sum(nvl(pm_qty, 0)) ");
			sb.append("from teb0340 ");
			sb.append("where item_cd = a.item_cd");
			sb.append(")) PM_QTY, ");
			sb.append("(");
			sb.append("select distinct aa.process_gcd||"+"'-'"+"||bb.gcd_name ");
			sb.append("from teb0340 aa, teb0460 bb ");
			sb.append("where aa.process_gcd = bb.process_gcd ");
			sb.append("and aa.item_cd = a.item_cd");
			sb.append(") PRODUCT, ");
			sb.append("a.ju_cust CUSTOMER, ");
			sb.append("b.cuname CUSTOMER_NAME, ");
			sb.append("decode(");
			sb.append("'#'"+"||a.projectcd, "+"'#', "+"'', "+"'#'"+"||a.projectcd");
			sb.append(") PROJECT_NO_MIN, ");
			sb.append("decode(");
			sb.append("upper(substr(a.item_cd, 3, 1)), "+"'P', "+"'R', "+"'P'");
			sb.append(") PROJECT_GUBUN ");
			sb.append("from teb0030 a, tsm0050 b ");
			sb.append("where substr(a.item_cd, 1, 2) like '01' ");
			sb.append("and upper(a.item_cd) like '%"+inputPN.toUpperCase()+"%'");
			sb.append("and substr(a.item_cd, 4, 1) not in("+"'0', "+"'8') ");
			sb.append("and a.ju_cust = b.cucode ");
			sb.append("order by a.item_cd");
			*/
			
			st = con.createStatement();
			Kogger.debug(getClass(), "####################");
			Kogger.debug(getClass(), JCO_CLIENT_CLIENT + "Project MIS Query \n"+sb.toString());
			Kogger.debug(getClass(), "####################");
            rs = st.executeQuery(sb.toString());
            
            while(rs.next()) {
            	String[] pjtField = new String[12];
            	
            	pjtField[0] = StringUtil.changeString(rs.getString(1), "\"", "\\");		//PROJECT_NO
            	pjtField[1] = StringUtil.changeString(rs.getString(2), "\"", "\\");		//PROJECT_NAME
            	pjtField[2] = StringUtil.changeString(rs.getString(3), "\"", "\\");		//PROJECT_TYPE
            	pjtField[3] = StringUtil.changeString(rs.getString(4), "\"", "\\");		//ACCEPTANCEDATE
            	pjtField[4] = StringUtil.changeString(rs.getString(5), "\"", "\\");		//DELIVEREDDATE
            	pjtField[5] = StringUtil.changeString(rs.getString(6), "\"", "\\");		//PROJECT_FABNAME
            	pjtField[6] = StringUtil.changeString(rs.getString(7), "\"", "\\");		//PRODUCT
            	pjtField[7] = StringUtil.changeString(rs.getString(8), "\"", "\\");		//CUSTOMER
            	pjtField[8] = StringUtil.changeString(rs.getString(9), "\"", "\\");		//CONSIGNMENT_TYPE
            	pjtField[9] = StringUtil.changeString(rs.getString(10), "\"", "\\");	//SITE_TYPE
            	pjtField[10] = StringUtil.changeString(rs.getString(11), "\"", "\\");	//IS_CREATED
            	pjtField[11] = StringUtil.changeString(rs.getString(12), "\"", "\\");	//IS_MODIFIED
            	
            	if(!ProjectHelper.manager.checkProjectNo(pjtField[0])) {
            		array.add(pjtField);
            	}

            }
		} catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
		
		return array;
	}
	
	public boolean isERPTableCheck(String inputPN){
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
		boolean ischeck = false;
		try{
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			if(con == null){
				return false;
			}
			
			StringBuffer sb = new StringBuffer();
			
					sb.append("select * from project_info_table ");
					sb.append("where project_no = '"+inputPN.toUpperCase()+"' and is_modified = '1' and is_created='1' and server_id ='"+JCO_CLIENT_CLIENT+"'" );	
					st = con.createStatement();
					Kogger.debug(getClass(), "####################");
					Kogger.debug(getClass(), JCO_CLIENT_CLIENT + "  isERPTableCheck checked Query \n"+sb.toString());
					Kogger.debug(getClass(), "####################");
		            rs = st.executeQuery(sb.toString());
		            	
		            while(rs.next()){
		            	Kogger.debug(getClass(), "isERPTableCheck true");
		            	ischeck = true;
		            }
		            
		}catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
		return ischeck;
	}
	
	
	public ArrayList isERPTableUpdateValue(String inputPN){
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
		ArrayList array = new ArrayList();
		
		
		try{
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			if(con == null){
				return array;
			}
			
			StringBuffer sb = new StringBuffer();
			
					sb.append("select * from project_info_table ");
					sb.append("where project_no = '"+inputPN.toUpperCase()+"' and is_modified = '1' and is_created='1' and server_id ='"+JCO_CLIENT_CLIENT+"'" );	
					st = con.createStatement();
					Kogger.debug(getClass(), "####################");
					Kogger.debug(getClass(), JCO_CLIENT_CLIENT + "  isERPTableCheck checked Query \n"+sb.toString());
					Kogger.debug(getClass(), "####################");
		            rs = st.executeQuery(sb.toString());
		            
		            while(rs.next()) {
		            	String[] pjtField = new String[12];
		            	
		            	pjtField[0] = StringUtil.changeString(rs.getString(1), "\"", "\\");		//PROJECT_NO
		            	pjtField[1] = StringUtil.changeString(rs.getString(2), "\"", "\\");		//PROJECT_NAME
		            	pjtField[2] = StringUtil.changeString(rs.getString(3), "\"", "\\");		//PROJECT_TYPE
		            	pjtField[3] = StringUtil.changeString(rs.getString(4), "\"", "\\");		//ACCEPTANCEDATE
		            	pjtField[4] = StringUtil.changeString(rs.getString(5), "\"", "\\");		//DELIVEREDDATE
		            	pjtField[5] = StringUtil.changeString(rs.getString(6), "\"", "\\");		//PROJECT_FABNAME
		            	pjtField[6] = StringUtil.changeString(rs.getString(7), "\"", "\\");		//PRODUCT
		            	pjtField[7] = StringUtil.changeString(rs.getString(8), "\"", "\\");		//CUSTOMER
		            	pjtField[8] = StringUtil.changeString(rs.getString(9), "\"", "\\");		//CONSIGNMENT_TYPE
		            	pjtField[9] = StringUtil.changeString(rs.getString(10), "\"", "\\");	//SITE_TYPE
		            	pjtField[10] = StringUtil.changeString(rs.getString(11), "\"", "\\");	//IS_CREATED
		            	pjtField[11] = StringUtil.changeString(rs.getString(12), "\"", "\\");	//IS_MODIFIED
		            	
		            	array.add(pjtField);
		            }
		            
		}catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
		return array;
	}
	public boolean getPLMUpdateSchedule(String inputPN){
		
		String pjtArr[] = null;
		String projectOID = "";								//프로젝트 OID 
		
		//SAP Attribute
		String projectProduct = "";							//PRODUCT
	    String projectName = "";							//프로젝트 명(장비명)
	    String projectAcceptanceDate = "";					//수주일자
	    String projectDeliveredDate = "";					//출하일자
	    String projectConsignment = "";						//출하조건
	    
	    //JELProject Attribute && SAP Attribute
	    String projectFab = "";								//FAB Name
	    String projectCustomer = "";						//거래처 명 (String) 추가 됨
	    String projectSite = "";							//SITE (JELProjectSiteLink)
	    
	    ReferenceFactory rf = new ReferenceFactory();
	    
	    try{
		    Hashtable hash = new Hashtable();
			ArrayList array = isERPTableUpdateValue(inputPN);
			E3PSProject project = null;
			
			for(int i = 0; i < array.size() ; i++){
				
				pjtArr = (String[])array.get(i);
//				project = (JELProject)ProjectHelper.getProject(pjtArr[0]);
//				projectOID = rf.getReferenceString(rf.getReference(project));
				
				Kogger.debug(getClass(), projectOID);
				projectName =pjtArr[1];
				projectAcceptanceDate =pjtArr[3];
				projectDeliveredDate =pjtArr[4];
				projectFab =pjtArr[5];
				projectProduct =pjtArr[6];
				projectCustomer =pjtArr[7];	
				projectConsignment =pjtArr[8];
				projectSite = pjtArr[9];
				
				Kogger.debug(getClass(), "plm Schedule test $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
				Kogger.debug(getClass(), "projectOID  :" +projectOID );
				Kogger.debug(getClass(), "projectName  :" +projectName );
				Kogger.debug(getClass(), "projectAcceptanceDate  :" +projectAcceptanceDate );
				Kogger.debug(getClass(), "projectDeliveredDate  :" +projectDeliveredDate );
				Kogger.debug(getClass(), "projectFab  :" +projectFab );
				Kogger.debug(getClass(), "projectProduct  :" +projectProduct );
				Kogger.debug(getClass(), "projectCustomer  :" +projectCustomer );
				Kogger.debug(getClass(), "projectConsignment  :" +projectConsignment );
				Kogger.debug(getClass(), "projectSite  :" +projectSite );
				
				hash.put("mode", "erpUpdate");
				hash.put("projectOID", projectOID);
				hash.put("projectName", projectName);
				hash.put("projectAcceptanceDate", projectAcceptanceDate);
				hash.put("projectDeliveredDate", projectDeliveredDate);
				hash.put("projectFab", projectFab);
				hash.put("projectProduct", projectProduct);
				hash.put("projectCustomer", projectCustomer);
				hash.put("projectConsignment", projectConsignment);
				hash.put("projectSite", projectSite);
				
			}
			
//				JELProject jelProject = JELProjectHelper.service.updateJELProject(hash);
//				if(jelProject == null){
//					return false;
//				}
				return true;
		
		}catch(Exception e){
			Kogger.error(getClass(), e);
		}
		return false;
	}
	
	public void syncProjectInterface(){
		
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
		boolean ischeck = false;
		try{
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			
			if(con == null){
				return;
			}
			
			StringBuffer sb = new StringBuffer();
			
					sb.append("select project_no from project_info_table ");
					sb.append("where is_modified = '1' and is_created='1' and server_id ='"+JCO_CLIENT_CLIENT+"'" );	
					st = con.createStatement();
					rs = st.executeQuery(sb.toString());
		            	
		            while(rs.next()){
		            	String updateProjectNo = rs.getString("project_no");
		            	Kogger.debug(getClass(), "updateProjectNo = " + updateProjectNo);
		            	getERPTableUpdateSchedule(updateProjectNo, "0");
		            	
		            }
		            
		}catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
		
	}
	
	public void getERPTableUpdateSchedule(String inputPN, String settingNo) {
		Statement st = null;
        ResultSet rs = null;
		Connection con = null;
		
//		if(!isERPTableCheck(inputPN)){
//			Kogger.debug(getClass(), "ERPTABLE에  is_modified ='1' is_created='1'의 조건 값이 없습니다 ");
//			return;
//		}
		
		if(!getPLMUpdateSchedule(inputPN)){
			Kogger.debug(getClass(), "PLM Update failure");
			return;
		}
		
		boolean isUpdate = false;
		try {
			if(con==null) {
//				con = DBCPManager.getConnection(EAI_DBCP_NAME);
			}
			StringBuffer sb = new StringBuffer();
			sb.append("update project_info_table set is_modified = '"+settingNo+"' where project_no = '"+inputPN+"' and server_id ='"+JCO_CLIENT_CLIENT+"'");
			//J-S3A13
			//sb.append("update project_info_table set is_modified = '"+settingNo+"' where project_no like '%"+inputPN.toUpperCase()+"%' and server_id ='"+JCO_CLIENT_CLIENT+"'");
			
			st = con.createStatement();
			Kogger.debug(getClass(), "####################");
			Kogger.debug(getClass(), JCO_CLIENT_CLIENT + "Project MIS getERPTableUpdateSchedule Query \n"+sb.toString());
			Kogger.debug(getClass(), "####################");
            isUpdate = st.execute(sb.toString());
            Kogger.debug(getClass(), "==>" + isUpdate);
            
		} catch (SQLException e) {
			Kogger.error(getClass(), e);
		} finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(getClass(), e);
            }
        }
	}
	
	public static void main(String args[])throws Exception{
		try{
			ProjectERPConMgr mgr = new ProjectERPConMgr();
			
			mgr.getERPTableUpdateSchedule("J-S3A11500","0");
			Kogger.debug(ProjectERPConMgr.class, "-----------------------------------------------");
			
			
		}catch(Exception ex){
			Kogger.error(ProjectERPConMgr.class, ex);
		}

	}
	
}
