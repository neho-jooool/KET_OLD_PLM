package e3ps.project.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import wt.fc.PersistenceHelper;
import wt.fc.QueryResult;
import wt.org.WTUser;
import wt.query.QuerySpec;
import wt.query.SearchCondition;
import e3ps.common.db.DBCPManager;
import e3ps.common.query.LoggableStatement;
import e3ps.common.util.CommonUtil;
import e3ps.common.util.KETStringUtil;
import e3ps.common.util.PlmDBUtil;
import e3ps.project.E3PSProject;
import e3ps.project.ModelInfo;
import e3ps.project.ProductInfo;
import e3ps.project.ProductProject;
import e3ps.project.ProjectProductInfoLink;
import e3ps.project.ReviewProject;
import e3ps.project.trySchdule.beans.TrySchduleHelper;
import ext.ket.shared.log.Kogger;

public class ProductHelper  {

	public static String getCarName(String piOid) throws Exception{
		String carName = "";
		ProductInfo pi = (ProductInfo)CommonUtil.getObject(piOid);
		QueryResult qr = getMoldeInfo(pi);

		while(qr.hasMoreElements()){
			Object oo[] = (Object[])qr.nextElement();
			ModelInfo mi = (ModelInfo)oo[0];
			//if(mi.getModel()!=null){
				if( carName.length() == 0){
					 if(mi.getModel() != null){
						  if(mi.getModel().getName() != null){
							  carName = mi.getModel().getName();
						  }
					  }else{
						  if(mi.getName() != null){
							  carName = mi.getName();
						  }
					  }
				}else{
					//carName += ", " + mi.getModel().getName();
					if(mi.getModel() != null){
						  carName += "," + mi.getModel().getName();
					  }else{
						  carName += "," + mi.getName();
					  }
				}
			//}
		}
		Kogger.debug(ProductHelper.class, "carName =========== " + carName);
		return carName;
	}

	public static QueryResult getMoldeInfo(ProductInfo pi)throws Exception{

		long oidLong = pi.getPersistInfo().getObjectIdentifier().getId();
		QuerySpec qs = new QuerySpec();
    	int idx2 = qs.addClassList(ModelInfo.class, true);
    	SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=", oidLong );
    	qs.appendWhere(sc, new int[] { idx2});
		QueryResult qr = PersistenceHelper.manager.find(qs);
		return qr;

	}

	public static String getYield(String piOid) throws Exception{
		int totalYield = 0;
		Long oidLong = e3ps.common.util.CommonUtil.getOIDLongValue(piOid);
    	QuerySpec qs = new QuerySpec();
    	int idx2 = qs.addClassList(ModelInfo.class, true);

    	SearchCondition sc = new SearchCondition(ModelInfo.class, "productReference.key.id", "=", oidLong );
    	qs.appendWhere(sc, new int[] { idx2});

		QueryResult qr = PersistenceHelper.manager.find(qs);

		while(qr.hasMoreElements()){
			Object oo[] = (Object[])qr.nextElement();
			ModelInfo mi = (ModelInfo)oo[0];

			int y1 = Integer.parseInt(mi.getYear1()==null?"0":mi.getYear1());
			int y2 = Integer.parseInt(mi.getYear2()==null?"0":mi.getYear2());
			int y3 = Integer.parseInt(mi.getYear3()==null?"0":mi.getYear3());
			int y4 = Integer.parseInt(mi.getYear4()==null?"0":mi.getYear4());
			int y5 = Integer.parseInt(mi.getYear5()==null?"0":mi.getYear5());
			int y6 = Integer.parseInt(mi.getYear6()==null?"0":mi.getYear6());
			int y7 = Integer.parseInt(mi.getYear7()==null?"0":mi.getYear7());
			int y8 = Integer.parseInt(mi.getYear8()==null?"0":mi.getYear8());
			int y9 = Integer.parseInt(mi.getYear9()==null?"0":mi.getYear9());
			int y10 = Integer.parseInt(mi.getYear10()==null?"0":mi.getYear10());
			totalYield += y1 + y2 + y3 + y4 + y5 + y6 + y7 + y8 + y9 + y10;

		}
		String Yield = Integer.toString(totalYield);
		return Yield ;
	}
	public static void syncProjectDRCostIF(E3PSProject project, String taskName) throws Exception {
		ArrayList sqeArr = getProjectCostIFSeqNo(project);
		QueryResult rs = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE, ProjectProductInfoLink.class);
		while(rs.hasMoreElements()){
			ProductInfo productInfo = (ProductInfo)rs.nextElement();
			drUpdateCostIF(productInfo, taskName);
		}
	}
	public static void drUpdateCostIF(ProductInfo productInfo, String taskName) throws Exception {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			con = DBCPManager.getConnection("plm");

			E3PSProject project = productInfo.getProject();
			E3PSProjectData projectData = new E3PSProjectData(project);

			String sql = "select partSqe from COST_i where projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'";
			st = con.prepareStatement(sql);

			rs = st.executeQuery();
			if(rs.next()) {
	            con.setAutoCommit(false);
	            StringBuffer sb = new StringBuffer();
	            sb.append("UPDATE COST_i");
	            sb.append(" set drProgress=?");
	            sb.append(" WHERE projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'");

	            st = con.prepareStatement(sb.toString());

		        //DrProgress
		        st.setString(1, taskName);

	            st.executeUpdate();
		        con.commit();
		        con.setAutoCommit(true);
			}
		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(st != null){
					st.close();
				}

                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }

		}
	}


	public static void saveCostIF(ProductInfo productInfo) throws Exception {

		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			con = DBCPManager.getConnection("plm");

			E3PSProject project = productInfo.getProject();
			E3PSProjectData projectData = new E3PSProjectData(project);

			String sql = "select partSqe from COST_i where projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'";
			st = con.prepareStatement(sql);

			rs = st.executeQuery();
			if(rs.next()) {
	            con.setAutoCommit(false);
	            StringBuffer sb = new StringBuffer();
	            sb.append("UPDATE COST_i");
	            sb.append(" set partNo=?, devUser=?, car=?, partName=?, devCostDate=?, devPartName=?, devLevel=?");
	            sb.append(", us=?, ea1=?, ea2=?, ea3=?, ea4=?, ea5=?, ea6=?, ea7=?, ea8=?, ea9=?, ea10=?");
	            sb.append(", expectValue=?, targetValue=?, targetRate=?, endModifyDate=sysdate, developentType=?, devDepartment=?");
	            sb.append(", sales=?, proposalDate=?, rank=?, subcontractor=?, customerevent=?, areas=?, reviewProjectCode=?, reviewPartSqe=?, linkUrl=?, partSqe=?");
	            sb.append(" WHERE projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'");

	            st = con.prepareStatement(sb.toString());

		        //partNo
		        if(productInfo.getPNum() != null && productInfo.getPNum().length() > 0)
		        	st.setString(1, productInfo.getPNum());
		        else
					st.setString(1, "");

		        //devUser
	        	st.setString(2, projectData.pjtPmName);
	        	Hashtable userH = TrySchduleHelper.getProjectRoleMember(project);
	        	//st.setString(2, projectData.pjtPmName); 연구기획팀이 PM으로 지정됨에 따라 제품개발ROLE 로 변경
		        WTUser user = (WTUser)userH.get("Team_PRODUCT01");
		        if(user != null){
		        	st.setString(2,  user.getFullName());
		        }else{
		        	st.setString(2, projectData.pjtPmName);
		        }

	        	//car
	        	String carName = getCarName(CommonUtil.getOIDString(productInfo));
	        	if(carName == null) carName = "";
	        	st.setString(3, carName);

	        	//partName
		        if(productInfo.getPName() != null && productInfo.getPName().length() > 0)
		        	st.setString(4, productInfo.getPName());
		        else
					st.setString(4, "");

		        //devCostDate
		        Date date = null;
				if(project instanceof ReviewProject) {
					if( ((ReviewProject)project).getEstimateDate() != null )
						date = new Date( ((ReviewProject)project).getEstimateDate().getTime() );
				}else if(project instanceof ProductProject) {
					if( ((ProductProject)project).getCostsDate() != null )
						date = new Date( ((ProductProject)project).getCostsDate().getTime() );
				}
				st.setDate(5, date);

				//devPartName
				st.setString(6, projectData.pjtName);

				//devLevel
				if(project instanceof ReviewProject) {
					st.setString(7, "개발검토");
				}else if(project instanceof ProductProject) {
					st.setString(7, project.getProcess().getName());
				}else
					st.setString(7, "");

				//us
				if(productInfo.getUsage() != null && productInfo.getUsage().length() > 0)
					st.setInt(8, Integer.parseInt(productInfo.getUsage()));
				else
					st.setInt(8, 0);

				//ea
				if(productInfo.getYear1() != null && productInfo.getYear1().length() > 0)
					st.setInt(9, Integer.parseInt(productInfo.getYear1()));
				else
					st.setInt(9, 0);
				if(productInfo.getYear2() != null && productInfo.getYear2().length() > 0)
					st.setInt(10, Integer.parseInt(productInfo.getYear2()));
				else
					st.setInt(10, 0);
				if(productInfo.getYear3() != null && productInfo.getYear3().length() > 0)
					st.setInt(11, Integer.parseInt(productInfo.getYear3()));
				else
					st.setInt(11, 0);
				if(productInfo.getYear4() != null && productInfo.getYear4().length() > 0)
					st.setInt(12, Integer.parseInt(productInfo.getYear4()));
				else
					st.setInt(12, 0);
				if(productInfo.getYear5() != null && productInfo.getYear5().length() > 0)
					st.setInt(13, Integer.parseInt(productInfo.getYear5()));
				else
					st.setInt(13, 0);
				if(productInfo.getYear6() != null && productInfo.getYear6().length() > 0)
					st.setInt(14, Integer.parseInt(productInfo.getYear6()));
				else
					st.setInt(14, 0);
				if(productInfo.getYear7() != null && productInfo.getYear7().length() > 0)
					st.setInt(15, Integer.parseInt(productInfo.getYear7()));
				else
					st.setInt(15, 0);
				if(productInfo.getYear8() != null && productInfo.getYear8().length() > 0)
					st.setInt(16, Integer.parseInt(productInfo.getYear8()));
				else
					st.setInt(16, 0);
				if(productInfo.getYear9() != null && productInfo.getYear9().length() > 0)
					st.setInt(17, Integer.parseInt(productInfo.getYear9()));
				else
					st.setInt(17, 0);
				if(productInfo.getYear10() != null && productInfo.getYear10().length() > 0)
					st.setInt(18, Integer.parseInt(productInfo.getYear10()));
				else
					st.setInt(18, 0);

				//expectValue
				if(productInfo.getPrice() != null && productInfo.getPrice().length() > 0)
					st.setInt(19, Integer.parseInt(productInfo.getPrice()));
				else
					st.setInt(19, 0);

				//targetValue
				if(productInfo.getCost() != null && productInfo.getCost().length() > 0)
					st.setInt(20, Integer.parseInt(productInfo.getCost()));
				else
					st.setInt(20, 0);

				//targetRate
				if(productInfo.getRate() != null && productInfo.getRate().length() > 0)
					st.setInt(21, Integer.parseInt(productInfo.getRate()));
				else
					st.setInt(21, 0);

				//developentType
				st.setString(22, projectData.developenttypeName);

				//devDepartment
				if(project instanceof ReviewProject) {
					if( ((ReviewProject)project).getDevDept() != null )
						st.setString(23, ((ReviewProject)project).getDevDept().getName());
					else
						st.setString(23, "");
				}else if(project instanceof ProductProject) {
					st.setString(23, projectData.department);
				}else
					st.setString(23, "");

				//sales
				st.setString(24, projectData.salesName);
				//proposalDate
		        date = null;
				if(project instanceof ReviewProject) {
					if( ((ReviewProject)project).getProposalDate() != null )
						date = new Date( ((ReviewProject)project).getProposalDate().getTime() );
				}
				st.setDate(25, date);
				//rank
				st.setString(26, projectData.rankName);
				//subcontractor
				st.setString(27, projectData.dependence);
				//customerevent
				st.setString(28, projectData.customer);

				//areas
				if(productInfo.getAreas() != null && productInfo.getAreas().length() > 0)
					st.setString(29, productInfo.getAreas());
				else
					st.setString(29, "");

				//reviewProjectCode
				if(productInfo.getReviewProjectNo() != null && productInfo.getReviewProjectNo().length() > 0)
					st.setString(30, productInfo.getReviewProjectNo());
				else
					st.setString(30, "");

				//reviewPartSqe
				if(productInfo.getReviewSeqNo() != null && productInfo.getReviewSeqNo().length() > 0)
					st.setString(31, productInfo.getReviewSeqNo());
				else
					st.setString(31, "");

				if(productInfo.getReviewProjectNo() != null && productInfo.getReviewProjectNo().length() > 0 && productInfo.getReviewSeqNo() != null && productInfo.getReviewSeqNo().length() > 0) {
					Hashtable linkUrlHash = getCostIFLinkUrl(productInfo.getReviewProjectNo(), productInfo.getReviewSeqNo());
					String linkUrl = (String)linkUrlHash.get("linkUrl");
					if(linkUrl == null) linkUrl = "";
					st.setString(32, linkUrl);
				}else
					st.setString(32, "");

				if(productInfo.getSeqNum() != null && productInfo.getSeqNum().length() > 0)
				{
					st.setString(33, productInfo.getSeqNum());
				}


				st.executeUpdate();
	            con.commit();
	            con.setAutoCommit(true);

			}else {
	            con.setAutoCommit(false);
	            sql = "insert into COST_i (projectCode, partSqe, partNo, devUser, car, partName, devCostDate, devPartName, devLevel, devPurpose, us, ea1, ea2, ea3, ea4, ea5, ea6, ea7, ea8, ea9, ea10, " +
	            		"expectValue, targetValue, targetRate, targetCost, targetInvestment, saleTarget, totalCost, endModifyDate, developentType, devDepartment, sales, proposalDate, rank, subcontractor," +
	            		"customerevent, areas, reviewProjectCode, reviewPartSqe, linkUrl) ";
	            sql = sql	+ "values( ?, ?, ?, ?, ?, ?, ?, ?, ?, '', ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, 0, 0, 0, sysdate, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				st = con.prepareStatement(sql);

				//projectCode
				st.setString(1, project.getPjtNo());

				//partSqe
		        if(productInfo.getSeqNum() != null && productInfo.getSeqNum().length() > 0)
		        	st.setString(2, productInfo.getSeqNum());
		        else
					st.setString(2, "");

		        //partNo
		        if(productInfo.getPNum() != null && productInfo.getPNum().length() > 0)
		        	st.setString(3, productInfo.getPNum());
		        else
					st.setString(3, "");

		        //devUser
		        
		        Hashtable userH = TrySchduleHelper.getProjectRoleMember(project);
	        	//st.setString(4, projectData.pjtPmName); 연구기획팀이 PM으로 지정됨에 따라 제품개발ROLE 로 변경
		        WTUser user = (WTUser)userH.get("Team_PRODUCT01");
		        if(user != null){
		        	st.setString(4,  user.getFullName());
		        }else{
		        	st.setString(4, projectData.pjtPmName);
		        }
	        	//car
	        	String carName = getCarName(CommonUtil.getOIDString(productInfo));
	        	if(carName == null) carName = "";
	        	st.setString(5, carName);

	        	//partName
		        if(productInfo.getPName() != null && productInfo.getPName().length() > 0)
		        	st.setString(6, productInfo.getPName());
		        else
					st.setString(6, "");

		        //devCostDate
		        Date date = null;
				if(project instanceof ReviewProject) {
					if( ((ReviewProject)project).getEstimateDate() != null )
						date = new Date( ((ReviewProject)project).getEstimateDate().getTime() );
				}else if(project instanceof ProductProject) {
					if( ((ProductProject)project).getCostsDate() != null )
						date = new Date( ((ProductProject)project).getCostsDate().getTime() );
				}
				st.setDate(7, date);

				//devPartName
				//if(project instanceof ReviewProject) {
					st.setString(8, projectData.pjtName);
				//}else
				//	st.setString(8, "");

				//devLevel
				if(project instanceof ReviewProject) {
					st.setString(9, "개발검토");
				}else if(project instanceof ProductProject) {
					st.setString(9, project.getProcess().getName());
				}else
					st.setString(9, "");

				//us
				if(productInfo.getUsage() != null && productInfo.getUsage().length() > 0)
					st.setInt(10, Integer.parseInt(productInfo.getUsage()));
				else
					st.setInt(10, 0);

				//ea
				if(productInfo.getYear1() != null && productInfo.getYear1().length() > 0)
					st.setInt(11, Integer.parseInt(productInfo.getYear1()));
				else
					st.setInt(11, 0);
				if(productInfo.getYear2() != null && productInfo.getYear2().length() > 0)
					st.setInt(12, Integer.parseInt(productInfo.getYear2()));
				else
					st.setInt(12, 0);
				if(productInfo.getYear3() != null && productInfo.getYear3().length() > 0)
					st.setInt(13, Integer.parseInt(productInfo.getYear3()));
				else
					st.setInt(13, 0);
				if(productInfo.getYear4() != null && productInfo.getYear4().length() > 0)
					st.setInt(14, Integer.parseInt(productInfo.getYear4()));
				else
					st.setInt(14, 0);
				if(productInfo.getYear5() != null && productInfo.getYear5().length() > 0)
					st.setInt(15, Integer.parseInt(productInfo.getYear5()));
				else
					st.setInt(15, 0);
				if(productInfo.getYear6() != null && productInfo.getYear6().length() > 0)
					st.setInt(16, Integer.parseInt(productInfo.getYear6()));
				else
					st.setInt(16, 0);
				if(productInfo.getYear7() != null && productInfo.getYear7().length() > 0)
					st.setInt(17, Integer.parseInt(productInfo.getYear7()));
				else
					st.setInt(17, 0);
				if(productInfo.getYear8() != null && productInfo.getYear8().length() > 0)
					st.setInt(18, Integer.parseInt(productInfo.getYear8()));
				else
					st.setInt(18, 0);
				if(productInfo.getYear9() != null && productInfo.getYear9().length() > 0)
					st.setInt(19, Integer.parseInt(productInfo.getYear9()));
				else
					st.setInt(19, 0);
				if(productInfo.getYear10() != null && productInfo.getYear10().length() > 0)
					st.setInt(20, Integer.parseInt(productInfo.getYear10()));
				else
					st.setInt(20, 0);

				//expectValue
				if(productInfo.getPrice() != null && productInfo.getPrice().length() > 0)
					st.setInt(21, Integer.parseInt(productInfo.getPrice()));
				else
					st.setInt(21, 0);

				//targetValue
				if(productInfo.getCost() != null && productInfo.getCost().length() > 0)
					st.setInt(22, Integer.parseInt(productInfo.getCost()));
				else
					st.setInt(22, 0);

				//targetRate
				if(productInfo.getRate() != null && productInfo.getRate().length() > 0)
					st.setInt(23, Integer.parseInt(productInfo.getRate()));
				else
					st.setInt(23, 0);

				//developentType
				st.setString(24, projectData.developenttypeName);

				//devDepartment
				if(project instanceof ReviewProject) {
					if( ((ReviewProject)project).getDevDept() != null )
						st.setString(25, ((ReviewProject)project).getDevDept().getName());
					else
						st.setString(25, "");
				}else if(project instanceof ProductProject) {
					st.setString(25, projectData.department);
				}else
					st.setString(25, "");

				//sales
				st.setString(26, projectData.salesName);
				//proposalDate
		        date = null;
				if(project instanceof ReviewProject) {
					if( ((ReviewProject)project).getProposalDate() != null )
						date = new Date( ((ReviewProject)project).getProposalDate().getTime() );
				}
				st.setDate(27, date);
				//rank
				st.setString(28, projectData.rankName);
				//subcontractor
				st.setString(29, projectData.dependence);
				//customerevent
				st.setString(30, projectData.customer);

				//areas
				if(productInfo.getAreas() != null && productInfo.getAreas().length() > 0)
					st.setString(31, productInfo.getAreas());
				else
					st.setString(31, "");

				//reviewProjectCode
				if(productInfo.getReviewProjectNo() != null && productInfo.getReviewProjectNo().length() > 0)
					st.setString(32, productInfo.getReviewProjectNo());
				else
					st.setString(32, "");

				//reviewPartSqe
				if(productInfo.getReviewSeqNo() != null && productInfo.getReviewSeqNo().length() > 0)
					st.setString(33, productInfo.getReviewSeqNo());
				else
					st.setString(33, "");

				if(productInfo.getReviewProjectNo() != null && productInfo.getReviewProjectNo().length() > 0 && productInfo.getReviewSeqNo() != null && productInfo.getReviewSeqNo().length() > 0) {
					Hashtable linkUrlHash = getCostIFLinkUrl(productInfo.getReviewProjectNo(), productInfo.getReviewSeqNo());
					String linkUrl = (String)linkUrlHash.get("linkUrl");
					if(linkUrl == null) linkUrl = "";
					st.setString(34, linkUrl);
				}else
					st.setString(34, "");

				st.executeUpdate();
		        con.commit();
		        con.setAutoCommit(true);
			}

		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
				if(rs != null){
					rs.close();
				}
				if(st != null){
					st.close();
				}

                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }

		}
	}

	public static void deleteCostIF(ProductInfo productInfo) throws Exception {

		Connection con = null;
		Statement st = null;
		try {
			con = DBCPManager.getConnection("plm");

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

			E3PSProject project = productInfo.getProject();
            st.executeQuery("delete from COST_i where projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'");

	        con.commit();
	        con.setAutoCommit(true);



		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
				if (st != null) st.close();
                if (con != null) con.close();

			} catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }

		}
	}

	public static void deleteProjectCostIF(String projectNo) throws Exception {

		Connection con = null;
		Statement st = null;
		try {
			con = DBCPManager.getConnection("plm");

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

            st.executeQuery("delete from COST_i where projectCode = '" + projectNo + "'");

	        con.commit();
	        con.setAutoCommit(true);



		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
				if (st != null) st.close();
                if (con != null) con.close();

			} catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }

		}
	}

	public static void deleteCostIF(String projectNo, String seqNo) throws Exception {

		Connection con = null;
		Statement st = null;
		try {
			con = DBCPManager.getConnection("plm");

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

            st.executeQuery("delete from COST_i where projectCode = '" + projectNo + "' AND partSqe = '" + seqNo + "'");

	        con.commit();
	        con.setAutoCommit(true);
		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
				if (st != null) st.close();
                if (con != null) con.close();

			} catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }

		}
	}

	public static void syncProjectCostIF(E3PSProject project) throws Exception {
		ArrayList sqeArr = getProjectCostIFSeqNo(project);

		QueryResult rs = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE, ProjectProductInfoLink.class);
		while(rs.hasMoreElements()){
			ProductInfo productInfo = (ProductInfo)rs.nextElement();

			if(productInfo.getReviewSeqNo() == null) {
				productInfo.setReviewSeqNo(productInfo.getSeqNum());
			}

			saveCostIF(productInfo);

			sqeArr.remove(productInfo.getSeqNum());
		}

		for(int i=0; i<sqeArr.size(); i++) {
			deleteCostIF(project.getPjtNo(), (String)sqeArr.get(i));
		}
	}

	/**
	 * 원가 테이블에 검토프로젝트 code가 없는 경우에 key in 한 string을 넣어 주는 코드 추가
	 *
	 */
	public static void syncProjectCostIF(E3PSProject project, String pjtno1) throws Exception {
		ArrayList sqeArr = getProjectCostIFSeqNo(project);

		QueryResult rs = PersistenceHelper.manager.navigate(project, ProjectProductInfoLink.PRODUCT_INFO_ROLE, ProjectProductInfoLink.class);
		while(rs.hasMoreElements()){
			ProductInfo productInfo = (ProductInfo)rs.nextElement();
			if(productInfo.getReviewProjectNo() == null) { // 기본으로 프로젝트 넘버가 들어 가도록 설정
				productInfo.setReviewProjectNo(KETStringUtil.nvl(pjtno1, project.getPjtNo()));
			}
			if(productInfo.getReviewSeqNo() == null) {
				productInfo.setReviewSeqNo(productInfo.getSeqNum());
			}

			saveCostIF(productInfo);

			sqeArr.remove(productInfo.getSeqNum());
		}

		for(int i=0; i<sqeArr.size(); i++) {
			deleteCostIF(project.getPjtNo(), (String)sqeArr.get(i));
		}
	}

	public static Hashtable getCostIF(ProductInfo productInfo) throws Exception
    {
    	Hashtable hash = new Hashtable();

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;

		try {
			con = PlmDBUtil.getConnection();

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

			E3PSProject project = productInfo.getProject();
            rs = st.executeQuery("select saleTarget, totalCost from COST_i where projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'");

            if(rs.next())
            {
            	hash.put("saleTarget", String.valueOf(rs.getInt(1)));
            	hash.put("totalCost", String.valueOf(rs.getInt(2)));
            }


		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }
		}

		return hash;
    }

	public static ArrayList getProjectCostIFSeqNo(E3PSProject project) throws Exception
    {
		ArrayList sqeArr = new ArrayList();

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;

		try {
			con = DBCPManager.getConnection("plm");

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

            rs = st.executeQuery("select partSqe from COST_i where projectCode = '" + project.getPjtNo() + "'");

            while(rs.next())
            {
            	sqeArr.add(rs.getString(1));
            }


		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }
		}

		return sqeArr;
    }

	public static Hashtable getCostIFLinkUrl(ProductInfo productInfo) throws Exception
    {
    	Hashtable hash = new Hashtable();

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;

		try {
			con = DBCPManager.getConnection("plm");

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

			E3PSProject project = productInfo.getProject();
            rs = st.executeQuery("select linkUrl from COST_i where projectCode = '" + project.getPjtNo() + "' AND partSqe = '" + productInfo.getSeqNum() + "'");

            if(rs.next())
            {
            	if(rs.getString(1) != null)
            		hash.put("linkUrl", rs.getString(1));
            }


		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }
		}

		return hash;
    }

	public static Hashtable getCostIFLinkUrl(String pjtNo, String seqNum) throws Exception
    {
    	Hashtable hash = new Hashtable();

		Connection con = null;
		ResultSet rs = null;
		Statement st = null;

		try {
			con = DBCPManager.getConnection("plm");

            con.setAutoCommit(false);
            st = (Statement) con.createStatement();

            rs = st.executeQuery("select linkUrl from COST_i where projectCode = '" + pjtNo + "' AND partSqe = '" + seqNum + "'");

            if(rs.next())
            {
            	if(rs.getString(1) != null)
            		hash.put("linkUrl", rs.getString(1));
            }


		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
			throw new Exception(e);
		} finally {
			try {
	            if (rs != null) rs.close();
	            if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Kogger.error(ProductHelper.class, e);
            }
		}

		return hash;
    }

	public static String ViewPartnerNo (String partnerName){
		LoggableStatement pstmt = null;
		Connection conn = null;
		StringBuffer sb = null;
		ResultSet rs = null;
		String partnerNo = null;

		try {
			conn = DBCPManager.getConnection("plm");

			sb = new StringBuffer();
			sb.append(" SELECT PARTNERNO ");
			sb.append("   FROM KETPARTNER ");
			sb.append(" WHERE 1=1");
			if (partnerName !=  null && !(partnerName.equals(""))){
				sb.append("     AND PARTNERNAME = '" + partnerName + "' ");
			}

			pstmt = new LoggableStatement( conn, sb.toString() );
			rs = pstmt.executeQuery();

			while (rs.next()){
				partnerNo = rs.getString(1);
			}

		} catch (SQLException se) {
			Kogger.error(ProductHelper.class, se);
		} catch (Exception e) {
			Kogger.error(ProductHelper.class, e);
		} finally {
			try {
	            if (rs != null) rs.close();
	            if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
			} catch (Exception e) {
				Kogger.error(ProductHelper.class, e);
			}
		}

		return partnerNo;
	}

	public static String getCarName2(String piOid) throws Exception{
		String carName = "";
		Long oidLong = e3ps.common.util.CommonUtil.getOIDLongValue(piOid);
		QuerySpec qs = new QuerySpec();
		  int idx = qs.appendClassList(ModelInfo.class, true);
		  SearchCondition sc5 = new SearchCondition(ModelInfo.class, "productReference.key.id", "=", oidLong);
		  qs.appendWhere(sc5, new int[] { idx});
		  QueryResult qr = PersistenceHelper.manager.find(qs);
		  while(qr.hasMoreElements()){
			  Object o[] = (Object[])qr.nextElement();
			  ModelInfo mi = (ModelInfo)o[0];
			  if(mi.getModel() != null){
				  carName = mi.getModel().getName();
			  }else{
				  carName = mi.getName();
			  }
		  }

		return carName ;
	}

	public static String SearchModelInfo(String piOid) throws Exception{
		String carName = "";
		Long oidLong = e3ps.common.util.CommonUtil.getOIDLongValue(piOid);
		QuerySpec qs = new QuerySpec();
		  int idx = qs.appendClassList(ModelInfo.class, true);
		  SearchCondition sc5 = new SearchCondition(ModelInfo.class, "productReference.key.id", "=", oidLong);
		  qs.appendWhere(sc5, new int[] { idx});
		  QueryResult qr = PersistenceHelper.manager.find(qs);
		  while(qr.hasMoreElements()){
			  Object o[] = (Object[])qr.nextElement();
			  ModelInfo mi = (ModelInfo)o[0];

			  if(mi.getModel()==null){
				  carName = mi.getName();
			  }else{
				  carName = mi.getModel().getName();
			  }
		  }

		return carName ;
	}

	public static boolean checkDirectInputProductInfo(String piOid) throws Exception{
		Long oidLong = e3ps.common.util.CommonUtil.getOIDLongValue(piOid);
		QuerySpec qs = new QuerySpec();
		int idx = qs.appendClassList(ModelInfo.class, true);
		SearchCondition sc5 = new SearchCondition(ModelInfo.class, "productReference.key.id", "=", oidLong);
		qs.appendWhere(sc5, new int[] { idx});
		QueryResult qr = PersistenceHelper.manager.find(qs);
		if(qr.size() == 1) {
			if(qr.hasMoreElements()){
				Object o[] = (Object[])qr.nextElement();
				ModelInfo mi = (ModelInfo)o[0];

				if(mi.getModel()==null)
					return true;
			}
		}

		return false ;
	}






}

