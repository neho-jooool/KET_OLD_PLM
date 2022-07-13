package e3ps.bom.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Vector;

import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.jtreetable.BOMTreeNode;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.Registry;
import e3ps.common.util.KETStringUtil;
import ext.ket.shared.log.Kogger;

public class BOMSaveDao
{
    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    PreparedStatement pstmt = null;

    Vector subAssyVec = new Vector();

    Registry registry = Registry.getRegistry("e3ps.bom.bom");

    public BOMSaveDao() {}

    public void saveBomList(Connection conn, String newBomCode, Vector insertData) throws Exception
    {
        StringBuffer strSql = new StringBuffer();

        subAssyVec.removeAllElements();

        try
        {
// ---------------------------------- BOM 저장 -------------------------------//
            // ketbomcomponent Table 에 데이타 저장하기...
            String componentColumn = "	 newbomcode				\n" +
                                                    ", parentitemcode		\n" +
                                                    ", childitemcode			\n" +
                                                    ", sequencenumber		\n" +
                                                    ", bomlevel				\n" +
                                                    ", itemseq					\n" +
                                                    ", quantity				\n" +
                                                    ", material				\n" +
                                                    ", hardnessFrom			\n" +
                                                    ", hardnessTo			\n" +
                                                    ", designdate				\n" +
                                                    ", startdate				\n" +
                                                    ", enddate				\n" +
//													", bomType				\n" +
                                                    ", newflag				\n" +
                                                    ", firstRemark			\n" +
                                                    ", secondRemark		\n" +
                                                    ", unit						\n" +
                                                    ", bomversion			\n";

            strSql.append(" INSERT INTO ketbomcomponent (" + componentColumn + ")		\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)							\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for (int idx=0; idx<insertData.size(); idx++)
            {
                BOMAssyComponent bomcomponent = (BOMAssyComponent)insertData.elementAt(idx);

                if(!bomcomponent.getItemCodeStr().equalsIgnoreCase(newBomCode.trim()))
                {
                    pstmt.setString(1, newBomCode);
                    pstmt.setString(2, (bomcomponent.getParentItemCodeStr()));
                    pstmt.setString(3, (bomcomponent.getItemCodeStr()));
                    pstmt.setString(4, bomcomponent.getSortOrderStr()); // Sort Order  ==> sequence_number
                    pstmt.setString(5, "" + bomcomponent.getLevelInt().intValue());
                    pstmt.setString(6, "" + bomcomponent.getItemSeqInt().intValue());
                    pstmt.setString(7, String.valueOf(bomcomponent.getQuantityDbl()));
                    pstmt.setString(8, Utility.checkNVL(bomcomponent.getMaterialStr()));
                    pstmt.setString(9, Utility.checkNVL(bomcomponent.getHardnessFrom()));
                    pstmt.setString(10, Utility.checkNVL(bomcomponent.getHardnessTo()));
                    pstmt.setString(11, Utility.checkNVL(bomcomponent.getDesignDate()));
                    pstmt.setString(12, Utility.checkNVL(bomcomponent.getStartDate()));
                    pstmt.setString(13, Utility.checkNVL(bomcomponent.getEndDate()));
//					pstmt.setString(14, Utility.checkNVL(bomcomponent.getBomType()));
                    pstmt.setString(14, bomcomponent.getNewFlagStr());
                    pstmt.setString(15, bomcomponent.getFirstMarkStr());
                    pstmt.setString(16, bomcomponent.getSecondMarkStr());
                    pstmt.setString(17, KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr()));
                    pstmt.setString(18, (bomcomponent.getVersionStr()).trim());

                    pstmt.executeUpdate();

                    for(int i=0; i<bomcomponent.getSubAssyComponent().size(); i++)
                    {
                        TmpSubAssyData subAssyData = new TmpSubAssyData();
                        subAssyData.parentItemCode = bomcomponent.getParentItemCodeStr();
                        subAssyData.childItemCode = bomcomponent.getItemCodeStr();
                        subAssyData.sequenceNumber = bomcomponent.getSortOrderStr();
                        subAssyData.subAssyComp = (BOMSubAssyComponent)bomcomponent.getSubAssyComponent().elementAt(i);
                        subAssyVec.addElement(subAssyData);
                    }
                }
            }
            strSql.delete(0, strSql.length());

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", quantity						\n" +
                                                    ", unit								\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", newbomcode					\n";

            strSql.append(" INSERT INTO ketbomsubstitute (" + substituteColumn + ")				\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)													\n");

            pstmt = conn.prepareStatement(strSql.toString());

              for(int i=0; i<subAssyVec.size(); i++)
            {
                TmpSubAssyData tmpSubAssyData = (TmpSubAssyData)subAssyVec.elementAt(i);
                  BOMSubAssyComponent subAssyComponent = tmpSubAssyData.subAssyComp;
                  pstmt.setString(1, tmpSubAssyData.parentItemCode.trim());
                pstmt.setString(2, tmpSubAssyData.childItemCode.trim());
                pstmt.setString(3, subAssyComponent.getSubstituteItemCodeStr().trim());
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, tmpSubAssyData.sequenceNumber.trim());
                pstmt.setString(9, newBomCode);
                  pstmt.executeUpdate();
              }
            strSql.delete(0, strSql.length());

// ---------------------------------- BOM 저장 -------------------------------//
          }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
              try{pstmt.close();}catch(Exception e){}
          }
    }


    // ---------------------------------- BOM Excel 저장 -------------------------------//
    public void saveExcelBomList(Connection conn, String newBomCode, Vector insertData) throws Exception
    {

        StringBuffer strSql = new StringBuffer();
        subAssyVec.removeAllElements();
        try
        {
            // ketbomcomponent Table 에 데이타 저장하기...
            String componentColumn = "	 newbomcode					\n" +
                                                    ", parentitemcode			\n" +
                                                    ", childitemcode				\n" +
                                                    ", sequencenumber			\n" +
                                                    ", bomlevel					\n" +
                                                    ", itemseq					    \n" +
                                                    ", quantity					\n" +
                                                    ", material					\n" +
                                                    ", hardnessFrom				\n" +
                                                    ", hardnessTo				\n" +
                                                    ", designdate					\n" +
                                                    ", startdate					\n" +
                                                    ", enddate					\n" +
//													", bomType					\n" +
                                                    ", newflag					\n" +
                                                    ", firstRemark				\n" +
                                                    ", secondRemark			\n" +
                                                    ", unit							\n" +
                                                    ", bomversion				\n" ;


            strSql.append(" INSERT INTO ketbomcomponent (" + componentColumn + ")		\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?)							\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for (int idx=0; idx< insertData.size() ; idx++)
            {

                BOMAssyComponent assyComponent = (BOMAssyComponent)insertData.elementAt(idx);
                pstmt.setString(1, newBomCode);
                pstmt.setString(2, assyComponent.getParentItemCodeStr());
                pstmt.setString(3, assyComponent.getItemCodeStr());
                pstmt.setString(4, assyComponent.getSortOrderStr()); // Sort Order  ==> sequence_number
                pstmt.setString(5, String.valueOf(assyComponent.getLevelInt()));
                pstmt.setString(6, String.valueOf(assyComponent.getItemSeqInt()));
                pstmt.setString(7, String.valueOf(assyComponent.getQuantityDbl()));
                pstmt.setString(8, assyComponent.getMaterialStr());
                pstmt.setString(9, assyComponent.getHardnessFrom());
                pstmt.setString(10, assyComponent.getHardnessTo());
                pstmt.setString(11, assyComponent.getDesignDate());
                pstmt.setString(12, Utility.checkNVL(assyComponent.getStartDate()));
                pstmt.setString(13, Utility.checkNVL(assyComponent.getEndDate()));
//				pstmt.setString(14, Utility.checkNVL(assyComponent.getBomType()));
                pstmt.setString(14, assyComponent.getNewFlagStr());
                pstmt.setString(15, ""); //assyComponent.getFirstMarkStr());
                pstmt.setString(16, assyComponent.getSecondMarkStr());
                pstmt.setString(17, KETStringUtil.replaceSpecialTag(assyComponent.getUitStr()));
                pstmt.setString(18, (assyComponent.getVersionStr()).trim());
                pstmt.executeUpdate();
            }
            strSql.delete(0, strSql.length());
          }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
              try{pstmt.close();}catch(Exception e){}
          }
    }

    // 잠시 Vector에 넣어두기 위한 구조.
    class TmpSubAssyData
    {
        String parentItemCode;
        String childItemCode;
        String sequenceNumber;
        BOMSubAssyComponent subAssyComp;

        public TmpSubAssyData() {}
    }


    public void saveRefBomList(Vector insertData) throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            String newBomCode = BOMBasicInfoPool.getPublicModelName().trim();

            subAssyVec.removeAllElements();

            stmt = conn.createStatement();

// ---------------------------------- BOM 저장 -------------------------------//
            // ketbomcomponent Table 에 데이타 저장하기...
            String componentColumn = "	 newbomcode					\n" +
                                                    ", parentitemcode			\n" +
                                                    ", childitemcode				\n" +
                                                    ", sequencenumber			\n" +
                                                    ", bomlevel					\n" +
                                                    ", itemseq						\n" +
                                                    ", quantity					\n" +
                                                    ", unit							\n" +
                                                    ", material					\n" +
                                                    ", hardnessFrom				\n" +
                                                    ", hardnessTo				\n" +
                                                    ", designdate					\n" +
                                                    ", startdate					\n" +
                                                    ", enddate					\n" +
//													", bomType					\n" +
                                                    ", newflag					\n" +
                                                    ", firstRemark				\n" +
                                                    ", secondRemark			\n" +
                                                    ", bomversion				\n" ;

            strSql.append(" INSERT INTO ketbomcomponent (" + componentColumn + ")		\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)							\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for(int i=0; i<insertData.size(); i++)
            {
                BOMTreeNode addedNode = (BOMTreeNode)insertData.elementAt(i);
                Enumeration data = addedNode.preorderEnumeration();

                while (data.hasMoreElements())
                {

                    BOMTreeNode node = (BOMTreeNode)data.nextElement();
                    BOMAssyComponent bomcomponent = (BOMAssyComponent)node.getBOMComponent();

Kogger.debug(getClass(), "ItemCode : " + bomcomponent.getItemCodeStr());

                    if(!bomcomponent.getItemCodeStr().equalsIgnoreCase(newBomCode.trim().substring(3)))
                    {
                        pstmt.setString(1, newBomCode);
                        pstmt.setString(2, bomcomponent.getParentItemCodeStr());
                        pstmt.setString(3, bomcomponent.getItemCodeStr());
                        pstmt.setString(4, bomcomponent.getSortOrderStr()); // Sort Order  ==> sequence_number
                        pstmt.setString(5, "" + bomcomponent.getLevelInt().intValue());
                        pstmt.setString(6, "" + bomcomponent.getItemSeqInt().intValue());
                        pstmt.setDouble(7, bomcomponent.getQuantityDbl().doubleValue());
                        pstmt.setString(8, KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr()));
                        pstmt.setString(9, bomcomponent.getMaterialStr());
                        pstmt.setString(10, bomcomponent.getHardnessFrom());
                        pstmt.setString(11, bomcomponent.getHardnessTo());
                        pstmt.setString(12, bomcomponent.getDesignDate());
                        pstmt.setString(13, bomcomponent.getStartDate());
                        pstmt.setString(14, bomcomponent.getEndDate());
//						pstmt.setString(15, bomcomponent.getBomType());
                        pstmt.setString(15, bomcomponent.getNewFlagStr());
                        pstmt.setString(16, bomcomponent.getFirstMarkStr());
                        pstmt.setString(17, bomcomponent.getSecondMarkStr());
                        pstmt.setString(18, (bomcomponent.getVersionStr()).trim());

                        pstmt.executeUpdate();

                        for(int k=0; k<bomcomponent.getSubAssyComponent().size(); k++)
                        {
                            TmpSubAssyData subAssyData = new TmpSubAssyData();
                            subAssyData.parentItemCode = bomcomponent.getParentItemCodeStr();
                            subAssyData.childItemCode = bomcomponent.getItemCodeStr();
                            subAssyData.sequenceNumber = bomcomponent.getSortOrderStr();
                            subAssyData.subAssyComp = (BOMSubAssyComponent)bomcomponent.getSubAssyComponent().elementAt(k);
                            subAssyVec.addElement(subAssyData);
                        }
                    }
                }
            }
            strSql.delete(0, strSql.length());

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", quantity						\n" +
                                                    ", unit								\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", newbomcode					\n";

            strSql.append(" INSERT INTO ketbomsubstitute (" + substituteColumn + ")		\n")
            .append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)												\n");

            pstmt = conn.prepareStatement(strSql.toString());

              for(int i=0; i<subAssyVec.size(); i++)
            {
                TmpSubAssyData tmpSubAssyData = (TmpSubAssyData)subAssyVec.elementAt(i);
                  BOMSubAssyComponent subAssyComponent = tmpSubAssyData.subAssyComp;

                  pstmt.setString(1,  tmpSubAssyData.parentItemCode.trim());
                pstmt.setString(2,  tmpSubAssyData.childItemCode.trim());
                pstmt.setString(3, subAssyComponent.getSubstituteItemCodeStr().trim());
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr()));
                pstmt.setString(6, subAssyComponent.getStartDate());
                pstmt.setString(7, subAssyComponent.getEndDate());
                pstmt.setString(8, tmpSubAssyData.sequenceNumber.trim());
                pstmt.setString(9, newBomCode);

                  pstmt.executeUpdate();
              }
            strSql.delete(0, strSql.length());

            conn.commit();
// ---------------------------------- BOM 저장 -------------------------------//
          }
        catch(Exception exception)
        {
              conn.rollback();
              throw exception;
          }
        finally
        {
            stmt.close();
              try{pstmt.close();}catch(Exception e){}
            if(res != null)
            {
                res.freeConnection(registry.getString("plm"), conn);
            }
          }
    }

    // BOMECO 저장 :: 신규 부품 등록 함수에서 호출.
    public void saveBomEco(String bomEcoHeaderNumber, Vector insertData) throws Exception
    {
        StringBuffer strSql = new StringBuffer();

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            subAssyVec.removeAllElements();

            stmt = conn.createStatement();

// ---------------------------------- BOM 저장 -------------------------------//
            // ketbomecocomponent Table 에 데이타 저장하기...
            String componentColumn = "	 ecoHeaderNumber				\n" +
                                                    ", parentitemcode			\n" +
                                                    ", childitemcode				\n" +
                                                    ", sequencenumber			\n" +
                                                    ", bomlevel					\n" +
                                                    ", itemseq						\n" +
                                                    ", beforeQuantity			\n" +
                                                    ", afterQuantity				\n" +
                                                    ", afterBoxQuantity			\n" +
                                                    ", afterUnit					\n" +
                                                    ", afterMaterial				\n" +
                                                    ", afterHardnessFrom		\n" +
                                                    ", afterHardnessTo			\n" +
                                                    ", afterDesignDate			\n" +
                                                    ", afterStartDate				\n" +
                                                    ", afterEndDate				\n" +
//													", afterBomType				\n" +
                                                    ", ecoItemCode				\n" +
                                                    ", appliedProductCode		\n" +
                                                    ", ecoCode					\n" +
                                                    ", bomversion				\n" ;

            strSql.append(" INSERT INTO ketbomecocomponent (" + componentColumn + ")		\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,?, ?  )						\n");

            pstmt = conn.prepareStatement(strSql.toString());

//Kogger.debug(getClass(), "@@@@@@@@@@@ insertData : " + insertData);
//Kogger.debug(getClass(), "@@@@@@@@@@@ insertData.size() : " + insertData.size());

            for (int idx=0; idx<insertData.size(); idx++)
            {
                BOMAssyComponent bomcomponent = (BOMAssyComponent)insertData.elementAt(idx);
//Kogger.debug(getClass(), "@@@@@@@@@@ bomcomponent [BOMSaveDao] : " + bomcomponent + " : " + bomcomponent.getItemSeqInt() + "");

                if(!bomcomponent.getItemCodeStr().equalsIgnoreCase(Utility.checkNVL(bomEcoHeaderNumber)))
                {
                    pstmt.setString(1, Utility.checkNVL(bomEcoHeaderNumber));
                    pstmt.setString(2,  Utility.checkNVL(bomcomponent.getParentItemCodeStr()));
                    pstmt.setString(3,  Utility.checkNVL(bomcomponent.getItemCodeStr()));
                    pstmt.setString(4, Utility.checkNVL(bomcomponent.getSortOrderStr())); // Sort Order  ==> sequence_number
                    pstmt.setString(5, "" + bomcomponent.getLevelInt().intValue());
                    pstmt.setString(6, "" + bomcomponent.getItemSeqInt().intValue());
                    pstmt.setDouble(7, 1.0);	// BeforeQuantity는 1.0 으로 셋팅
                    pstmt.setDouble(8, bomcomponent.getQuantityDbl().doubleValue());
                    pstmt.setDouble(9, bomcomponent.getBoxQuantityDbl().doubleValue());
                    pstmt.setString(10, Utility.checkNVL(KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr())));
                    pstmt.setString(11, Utility.checkNVL(bomcomponent.getMaterialStr()));
                    pstmt.setString(12, Utility.checkNVL(bomcomponent.getHardnessFrom()));
                    pstmt.setString(13, Utility.checkNVL(bomcomponent.getHardnessTo()));
                    pstmt.setString(14, Utility.checkNVL(bomcomponent.getDesignDate()));
                    pstmt.setString(15, Utility.checkNVL(bomcomponent.getStartDate()));
                    pstmt.setString(16, Utility.checkNVL(bomcomponent.getEndDate()));
//					pstmt.setString(16, Utility.checkNVL(bomcomponent.getBomType()));
                    pstmt.setString(17, Utility.checkNVL(bomcomponent.getParentItemCodeStr()));
                    pstmt.setString(18, Utility.checkNVL(bomcomponent.getParentItemCodeStr()));
                    pstmt.setString(19, Utility.checkNVL(bomcomponent.getEcoCodeStr()));
                    pstmt.setString(20, Utility.checkNVL( (bomcomponent.getVersionStr()).trim() ));
                    pstmt.executeUpdate();
                }
            }
            strSql.delete(0, strSql.length());

            conn.commit();
// ---------------------------------- BOM 저장 -------------------------------//
          }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              conn.rollback();
              throw exception;
          }
        finally
        {
            stmt.close();
              try{pstmt.close();}catch(Exception e){}
            if(res != null)
            {
                res.freeConnection(registry.getString("plm"), conn);
            }
          }
    }


    // BOMECO 구조 저장 :: 최초 BOM ECO 오픈 시 사용
    public void saveBomEcoList(String bomEcoHeaderNumber, Vector insertData) throws Exception
    {
        StringBuffer strSql = new StringBuffer();

        String bomEcoItemCode = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            subAssyVec.removeAllElements();

            stmt = conn.createStatement();

// --------------------- BOM 저장하기전 기존 Data 삭제 함 ----------------------//
            strSql.append(" DELETE																									\n")
                    .append(" FROM ketbomecocomponent																		\n")
                    .append(" WHERE ecoheadernumber = '" + Utility.checkNVL(bomEcoHeaderNumber) + "'			\n")
                    .append(" AND    ecoItemCode = '" + bomEcoItemCode + "'											\n");
            stmt.executeUpdate(strSql.toString());
            strSql.delete(0, strSql.length());

            strSql.append(" DELETE																									\n")
                    .append(" FROM ketbomecosubstitute																		\n")
                    .append(" WHERE ecoheadernumber = '" + Utility.checkNVL(bomEcoHeaderNumber) + "'			\n")
                    .append(" AND    ecoItemCode = '" + bomEcoItemCode + "'											\n");
            stmt.executeUpdate(strSql.toString());
            strSql.delete(0, strSql.length());
// --------------------- BOM 저장하기전 기존 Data 삭제 함 ----------------------//

// ---------------------------------- BOM 저장 -------------------------------//
            // ketbomecocomponent Table 에 데이타 저장하기...
            String componentColumn = "	 ecoHeaderNumber				\n" +
                                                    ", parentitemcode			\n" +
                                                    ", childitemcode				\n" +
                                                    ", sequencenumber			\n" +
                                                    ", bomlevel					\n" +
                                                    ", itemseq						\n" +
                                                    ", beforeBoxQuantity		\n" +
                                                    ", afterBoxQuantity			\n" +
                                                    ", beforeQuantity			\n" +
                                                    ", afterQuantity				\n" +
                                                    ", beforeUnit					\n" +
                                                    ", afterUnit					\n" +
                                                    ", beforeMaterial			\n" +
                                                    ", afterMaterial				\n" +
                                                    ", beforeHardnessFrom		\n" +
                                                    ", afterHardnessFrom		\n" +
                                                    ", beforeHardnessTo		\n" +
                                                    ", afterHardnessTo			\n" +
                                                    ", beforeDesignDate		\n" +
                                                    ", afterDesignDate			\n" +
                                                    ", beforeStartDate			\n" +
                                                    ", afterStartDate				\n" +
                                                    ", beforeEndDate			\n" +
                                                    ", afterEndDate				\n" +
                                                    ", ecoItemCode				\n" +
                                                    ", appliedProductCode		\n" +
                                                    ", ecoCode					\n" +
                                                    ", bomversion				\n" ;

            strSql.append(" INSERT INTO ketbomecocomponent (" + componentColumn + ")				\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,?, ? )			\n");

            pstmt = conn.prepareStatement(strSql.toString());

Kogger.debug(getClass(), "################ insertData : " + insertData);
Kogger.debug(getClass(), "################ insertData.size() : " + insertData.size());

            for (int idx=0; idx<insertData.size(); idx++)
            {
                BOMAssyComponent bomcomponent = (BOMAssyComponent)insertData.elementAt(idx);
//Kogger.debug(getClass(), "@@@@@@@@@@ bomcomponent [BOMSaveDao] : " + bomcomponent + " : " + bomcomponent.getItemSeqInt() + "");
Kogger.debug(getClass(), "@@@@@@@@@@@ ParentItemCode : " + bomcomponent.getParentItemCodeStr());
Kogger.debug(getClass(), "@@@@@@@@@@@ ChildItemCode : " + bomcomponent.getItemCodeStr());

                if(!bomcomponent.getItemCodeStr().equalsIgnoreCase(Utility.checkNVL(bomEcoHeaderNumber)))
                {
                    pstmt.setString(1, Utility.checkNVL(bomEcoHeaderNumber));
                    pstmt.setString(2,  Utility.checkNVL(bomcomponent.getParentItemCodeStr()));
                    pstmt.setString(3,  Utility.checkNVL(bomcomponent.getItemCodeStr()));
                    pstmt.setString(4, Utility.checkNVL(bomcomponent.getSortOrderStr())); // Sort Order  ==> sequence_number
                    pstmt.setString(5, "" + bomcomponent.getLevelInt().intValue());
                    pstmt.setString(6, "" + bomcomponent.getItemSeqInt().intValue());
                    pstmt.setDouble(7, bomcomponent.getBeforeBoxQuantityDbl().doubleValue());
                    pstmt.setDouble(8, bomcomponent.getBoxQuantityDbl().doubleValue());
                    pstmt.setDouble(9, bomcomponent.getBeforeQuantityDbl().doubleValue());
                    pstmt.setDouble(10, bomcomponent.getQuantityDbl().doubleValue());
                    pstmt.setString(11, Utility.checkNVL(KETStringUtil.replaceSpecialTag(bomcomponent.getBeforeUnitStr())));
                    pstmt.setString(12, Utility.checkNVL(KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr())));
                    pstmt.setString(13, Utility.checkNVL(bomcomponent.getBeforeMaterialStr()));
                    pstmt.setString(14, Utility.checkNVL(bomcomponent.getMaterialStr()));
                    pstmt.setString(15, Utility.checkNVL(bomcomponent.getBeforeHardnessFrom()));
                    pstmt.setString(16, Utility.checkNVL(bomcomponent.getHardnessFrom()));
                    pstmt.setString(17, Utility.checkNVL(bomcomponent.getBeforeHardnessTo()));
                    pstmt.setString(18, Utility.checkNVL(bomcomponent.getHardnessTo()));
                    pstmt.setString(19, Utility.checkNVL(bomcomponent.getBeforeDesignDate()));
                    pstmt.setString(20, Utility.checkNVL(bomcomponent.getDesignDate()));
                    pstmt.setString(21, Utility.checkNVL(bomcomponent.getBeforeStartDate()));
                    pstmt.setString(22, Utility.checkNVL(bomcomponent.getStartDate()));
                    pstmt.setString(23, Utility.checkNVL(bomcomponent.getBeforeEndDate()));
                    pstmt.setString(24, Utility.checkNVL(bomcomponent.getEndDate()));
                    pstmt.setString(25, bomEcoItemCode);
                    pstmt.setString(26, bomEcoItemCode);
                    pstmt.setString(27, Utility.checkNVL(bomcomponent.getEcoCodeStr()));
                    pstmt.setString(28, Utility.checkNVL( (bomcomponent.getVersionStr()).trim() ));
                    pstmt.executeUpdate();

                    for(int i=0; i<bomcomponent.getSubAssyComponent().size(); i++)
                    {
                        TmpSubAssyData subAssyData = new TmpSubAssyData();
                        subAssyData.parentItemCode = bomcomponent.getParentItemCodeStr();
                        subAssyData.childItemCode = bomcomponent.getItemCodeStr();
                        subAssyData.sequenceNumber = bomcomponent.getSortOrderStr();
                        subAssyData.subAssyComp = (BOMSubAssyComponent)bomcomponent.getSubAssyComponent().elementAt(i);
                        subAssyVec.addElement(subAssyData);
                    }
                }
            }
            strSql.delete(0, strSql.length());

            // ketbomecosubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode					\n" +
                                                    ", childitemcode				\n" +
                                                    ", substituteitemcode		\n" +
                                                    ", beforeQuantity			\n" +
                                                    ", afterQuantity				\n" +
                                                    ", beforeUnit					\n" +
                                                    ", afterUnit					\n" +
                                                    ", beforeStartDate			\n" +
                                                    ", afterStartDate				\n" +
                                                    ", beforeEndDate			\n" +
                                                    ", afterEndDate				\n" +
                                                    ", sequencenumber			\n" +
                                                    ", ecoCode					\n" +
                                                    ", ecoItemCode				\n" +
                                                    ", ecoHeaderNumber		\n";

            strSql.append(" INSERT INTO ketbomecosubstitute (" + substituteColumn + ")	\n")
                    .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?)						\n");

            pstmt = conn.prepareStatement(strSql.toString());

              for(int i=0; i<subAssyVec.size(); i++)
            {
                TmpSubAssyData tmpSubAssyData = (TmpSubAssyData)subAssyVec.elementAt(i);
                  BOMSubAssyComponent subAssyComponent = tmpSubAssyData.subAssyComp;

                  pstmt.setString(1,  Utility.checkNVL(tmpSubAssyData.parentItemCode));
                pstmt.setString(2,  Utility.checkNVL(tmpSubAssyData.childItemCode));
                pstmt.setString(3,  Utility.checkNVL(subAssyComponent.getSubstituteItemCodeStr()));
                pstmt.setDouble(4, subAssyComponent.getBeforeQuantityDbl().doubleValue());
                pstmt.setDouble(5, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(6,  Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getBeforeUnitStr())));
                pstmt.setString(7,  Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(8, Utility.checkNVL(subAssyComponent.getBeforeStartDate()));
                pstmt.setString(9, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(10, Utility.checkNVL(subAssyComponent.getBeforeEndDate()));
                pstmt.setString(11, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(12, Utility.checkNVL(tmpSubAssyData.sequenceNumber));
                pstmt.setString(13, Utility.checkNVL(subAssyComponent.getEcoCodeStr()));
                pstmt.setString(14, bomEcoItemCode);
                pstmt.setString(15, Utility.checkNVL(bomEcoHeaderNumber));

                  pstmt.executeUpdate();
              }
            strSql.delete(0, strSql.length());

            conn.commit();
// ---------------------------------- BOM 저장 -------------------------------//
          }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              conn.rollback();
              throw exception;
          }
        finally
        {
            stmt.close();
              try{pstmt.close();}catch(Exception e){}
            if(res != null)
            {
                res.freeConnection(registry.getString("plm"), conn);
            }
          }
    }

    public void saveRefBomEcoList(Vector insertData) throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            String bomEcoHeaderNumber = Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber());
            String bomEcoItemCode = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

            subAssyVec.removeAllElements();

// ---------------------------------- BOM 저장 -------------------------------//
            // ketbomecocomponent Table 에 데이타 저장하기...
            String componentColumn = "	 ecoHeaderNumber			\n" +
                                                    ", parentitemcode		\n" +
                                                    ", childitemcode			\n" +
                                                    ", sequencenumber		\n" +
                                                    ", bomlevel				\n" +
                                                    ", itemseq					\n" +
                                                    ", afterBoxQuantity		\n" +
                                                    ", afterQuantity			\n" +
                                                    ", afterUnit				\n" +
                                                    ", afterMaterial			\n" +
                                                    ", afterHardnessFrom	\n" +
                                                    ", afterHardnessTo		\n" +
                                                    ", afterDesignDate		\n" +
                                                    ", afterStartDate			\n" +
                                                    ", afterEndDate			\n" +
                                                    ", ecoItemCode			\n" +
                                                    ", ecoCode				\n" +
                                                    ", bomversion			\n" ;

            strSql.append(" INSERT INTO ketbomecocomponent (" + componentColumn + ")		\n")
            .append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?)								\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for(int i=0; i<insertData.size(); i++)
            {
                BOMTreeNode addedNode = (BOMTreeNode)insertData.elementAt(i);
                Enumeration data = addedNode.preorderEnumeration();

                while (data.hasMoreElements())
                {
                    BOMTreeNode node = (BOMTreeNode)data.nextElement();
                    BOMAssyComponent bomcomponent = (BOMAssyComponent)node.getBOMComponent();

Kogger.debug(getClass(), "@@@@@ [saveRefBomEcoList] ItemCode : " + bomcomponent.getItemCodeStr());
//Kogger.debug(getClass(), "@@@@@ [saveRefBomEcoList] bomcomponent : " + bomcomponent);
Kogger.debug(getClass(), "@@@@@ [saveRefBomEcoList] getBoxQuantityDbl : " + bomcomponent.getBoxQuantityDbl().doubleValue());

                    if(!bomcomponent.getItemCodeStr().equalsIgnoreCase(bomEcoHeaderNumber))
                    {
                        pstmt.setString(1, Utility.checkNVL(bomEcoHeaderNumber));
                        pstmt.setString(2, Utility.checkNVL(bomcomponent.getParentItemCodeStr()));
                        pstmt.setString(3, Utility.checkNVL(bomcomponent.getItemCodeStr()));
                        pstmt.setString(4, Utility.checkNVL(bomcomponent.getSortOrderStr())); // Sort Order  ==> sequence_number
                        pstmt.setString(5, "" + bomcomponent.getLevelInt().intValue());
                        pstmt.setString(6, "" + bomcomponent.getItemSeqInt().intValue());
                        pstmt.setDouble(7, bomcomponent.getBoxQuantityDbl().doubleValue());
                        pstmt.setDouble(8, bomcomponent.getQuantityDbl().doubleValue());
                        pstmt.setString(9, Utility.checkNVL(KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr())));
                        pstmt.setString(10, Utility.checkNVL(bomcomponent.getMaterialStr()));
                        pstmt.setString(11, Utility.checkNVL(bomcomponent.getHardnessFrom()));
                        pstmt.setString(12, Utility.checkNVL(bomcomponent.getHardnessTo()));
                        pstmt.setString(13, Utility.checkNVL(bomcomponent.getDesignDate()));
                        pstmt.setString(14, Utility.checkNVL(bomcomponent.getStartDate()));
                        pstmt.setString(15, Utility.checkNVL(bomcomponent.getEndDate()));
                        pstmt.setString(16, bomEcoItemCode);
                        pstmt.setString(17, Utility.checkNVL(bomcomponent.getEcoCodeStr()));
                        pstmt.setString(18, Utility.checkNVL( (bomcomponent.getVersionStr()).trim() ));

                        pstmt.executeUpdate();

                        for(int k=0; k<bomcomponent.getSubAssyComponent().size(); k++)
                        {
                            TmpSubAssyData subAssyData = new TmpSubAssyData();
                            subAssyData.parentItemCode = bomcomponent.getParentItemCodeStr();
                            subAssyData.childItemCode = bomcomponent.getItemCodeStr();
                            subAssyData.sequenceNumber = bomcomponent.getSortOrderStr();
                            subAssyData.subAssyComp = (BOMSubAssyComponent)bomcomponent.getSubAssyComponent().elementAt(k);
                            subAssyVec.addElement(subAssyData);
                        }
                    }
                }
            }
            strSql.delete(0, strSql.length());

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode					\n" +
                                                    ", childitemcode				\n" +
                                                    ", substituteitemcode		\n" +
                                                    ", afterQuantity				\n" +
                                                    ", afterUnit					\n" +
                                                    ", afterStartDate				\n" +
                                                    ", afterEndDate				\n" +
                                                    ", sequencenumber			\n" +
                                                    ", ecoCode					\n" +
                                                    ", ecoItemCode				\n" +
                                                    ", ecoHeaderNumber		\n";

            strSql.append(" INSERT INTO ketbomecosubstitute (" + substituteColumn + ")		\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)												\n");


            pstmt = conn.prepareStatement(strSql.toString());

              for(int i=0; i<subAssyVec.size(); i++)
            {
                TmpSubAssyData tmpSubAssyData = (TmpSubAssyData)subAssyVec.elementAt(i);
                  BOMSubAssyComponent subAssyComponent = tmpSubAssyData.subAssyComp;
                  pstmt.setString(1,  Utility.checkNVL(tmpSubAssyData.parentItemCode));
                pstmt.setString(2,  Utility.checkNVL(tmpSubAssyData.childItemCode));
                pstmt.setString(3, Utility.checkNVL(subAssyComponent.getSubstituteItemCodeStr()));
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, Utility.checkNVL(tmpSubAssyData.sequenceNumber));
                pstmt.setString(9, Utility.checkNVL(subAssyComponent.getEcoCodeStr()));
                pstmt.setString(10, bomEcoItemCode);
                pstmt.setString(11, Utility.checkNVL(bomEcoHeaderNumber));

                  pstmt.executeUpdate();
              }
            strSql.delete(0, strSql.length());

            conn.commit();
// ---------------------------------- BOM 저장 -------------------------------//
          }
        catch(Exception exception)
        {
              conn.rollback();
              throw exception;
          }
        finally
        {
              try{pstmt.close();}catch(Exception e){}
            if(res != null)
            {
                res.freeConnection(registry.getString("plm"), conn);
            }
          }
    }

    public void saveTmpBomEco(Vector insertData) throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            String bomEcoHeaderNumber = Utility.checkNVL(BOMBasicInfoPool.getBomEcoNumber());
            String bomEcoItemCode = Utility.checkNVL(BOMBasicInfoPool.getPublicModelName());

            subAssyVec.removeAllElements();

// ---------------------------------- BOM 저장 -------------------------------//
            // ketbomecotempcomponent Table 에 데이타 저장하기...
            String componentColumn = "	 ecoHeaderNumber			\n" +
                                                    ", parentitemcode		\n" +
                                                    ", childitemcode			\n" +
                                                    ", afterBoxQuantity		\n" +
                                                    ", afterQuantity			\n" +
                                                    ", afterUnit				\n" +
                                                    ", afterMaterial			\n" +
                                                    ", afterHardnessFrom	\n" +
                                                    ", afterHardnessTo		\n" +
                                                    ", afterDesignDate		\n" +
                                                    ", afterStartDate			\n" +
                                                    ", afterEndDate			\n" +
                                                    ", ecoItemCode			\n" +
                                                    ", sequenceNumber		\n" +
                                                    ", bomversion			\n" ;

            strSql.append(" INSERT INTO ketbomecotempcomponent (" + componentColumn + ")	\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )										\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for(int i=0; i<insertData.size(); i++)
            {
//				BOMAssyComponent bomcomponent = (BOMAssyComponent)insertData.elementAt(i);
                BOMTreeNode addedNode = (BOMTreeNode)insertData.elementAt(i);
                Enumeration data = addedNode.preorderEnumeration();

                while (data.hasMoreElements())
                {
                    BOMTreeNode node = (BOMTreeNode)data.nextElement();
                    BOMAssyComponent bomcomponent = (BOMAssyComponent)node.getBOMComponent();

                    if(!bomcomponent.getItemCodeStr().equalsIgnoreCase(bomEcoHeaderNumber))
                    {
Kogger.debug(getClass(), "@@@@@ [saveTmpBomEco] ItemCode : " + bomcomponent.getItemCodeStr());
Kogger.debug(getClass(), "@@@@@ [saveTmpBomEco] bomcomponent : " + bomcomponent);

                        pstmt.setString(1, Utility.checkNVL(bomEcoHeaderNumber));
                        pstmt.setString(2, Utility.checkNVL(bomcomponent.getParentItemCodeStr()));
                        pstmt.setString(3, Utility.checkNVL(bomcomponent.getItemCodeStr()));
                        pstmt.setDouble(4, bomcomponent.getBoxQuantityDbl().doubleValue());
                        pstmt.setDouble(5, bomcomponent.getQuantityDbl().doubleValue());
                        pstmt.setString(6, Utility.checkNVL(KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr())));
                        pstmt.setString(7, Utility.checkNVL(bomcomponent.getMaterialStr()));
                        pstmt.setString(8, Utility.checkNVL(bomcomponent.getHardnessFrom()));
                        pstmt.setString(9, Utility.checkNVL(bomcomponent.getHardnessTo()));
                        pstmt.setString(10, Utility.checkNVL(bomcomponent.getDesignDate()));
                        pstmt.setString(11, Utility.checkNVL(bomcomponent.getStartDate()));
                        pstmt.setString(12, Utility.checkNVL(bomcomponent.getEndDate()));
                        pstmt.setString(13, bomEcoItemCode);
                        pstmt.setString(14, Utility.checkNVL(bomcomponent.getSortOrderStr()));
                        pstmt.setString(15, Utility.checkNVL(bomcomponent.getVersionStr()));

                        pstmt.executeUpdate();

                        for(int k=0; k<bomcomponent.getSubAssyComponent().size(); k++)
                        {
                            TmpSubAssyData subAssyData = new TmpSubAssyData();
                            subAssyData.parentItemCode = bomcomponent.getParentItemCodeStr();
                            subAssyData.childItemCode = bomcomponent.getItemCodeStr();
                            subAssyData.sequenceNumber = bomcomponent.getSortOrderStr();
                            subAssyData.subAssyComp = (BOMSubAssyComponent)bomcomponent.getSubAssyComponent().elementAt(k);
                            subAssyVec.addElement(subAssyData);
                        }
                    }
                }
            }
            strSql.delete(0, strSql.length());

            // ketbomecotempsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode					\n" +
                                                    ", childitemcode				\n" +
                                                    ", substituteitemcode		\n" +
                                                    ", afterQuantity				\n" +
                                                    ", afterUnit					\n" +
                                                    ", afterStartDate				\n" +
                                                    ", afterEndDate				\n" +
                                                    ", ecoHeaderNumber		\n" +
                                                    ", ecoItemCode				\n" +
                                                    ", sequenceNumber			\n" ;

            strSql.append(" INSERT INTO ketbomecotempsubstitute (" + substituteColumn + ")		\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)														\n");

            pstmt = conn.prepareStatement(strSql.toString());

              for(int i=0; i<subAssyVec.size(); i++)
            {
                TmpSubAssyData tmpSubAssyData = (TmpSubAssyData)subAssyVec.elementAt(i);
                  BOMSubAssyComponent subAssyComponent = tmpSubAssyData.subAssyComp;

                  pstmt.setString(1,  Utility.checkNVL(tmpSubAssyData.parentItemCode));
                pstmt.setString(2, Utility.checkNVL(tmpSubAssyData.childItemCode));
                pstmt.setString(3, Utility.checkNVL(subAssyComponent.getSubstituteItemCodeStr()));
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, Utility.checkNVL(bomEcoHeaderNumber));
                pstmt.setString(9, Utility.checkNVL(bomEcoItemCode));
                pstmt.setString(10, Utility.checkNVL(tmpSubAssyData.sequenceNumber));

                  pstmt.executeUpdate();
              }
            strSql.delete(0, strSql.length());

            conn.commit();
// ---------------------------------- BOM 저장 -------------------------------//
          }
        catch(Exception exception)
        {
              conn.rollback();
              throw exception;
          }
        finally
        {
              try{pstmt.close();}catch(Exception e){}
            if(res != null)
            {
                res.freeConnection(registry.getString("plm"), conn);
            }
          }
    }

    // Item Add 시 선택한 Item 의 정보를 Update 한다.
    public void addItemUpdate(Connection conn, BOMAssyComponent assyComponent) throws Exception
    {
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);
Kogger.debug(getClass(), "@@@@@ assyComponent [addItemUpdate]  : " + assyComponent);

        String query = "UPDATE ";
        query = query + " ketbomcomponent ";
        query = query + " SET ";
        query = query + "		itemseq = '" + assyComponent.getItemSeqInt().intValue() + "' , ";
        query = query + "		bomlevel = '" + assyComponent.getLevelInt().intValue() + "' , ";
        query = query + "		childitemcode = '" + assyComponent.getItemCodeStr().trim() + "' , ";
        query = query + "		quantity = '" + assyComponent.getQuantityDbl() + "' , ";
        query = query + "		material = '" + assyComponent.getMaterialStr() + "' , ";
        query = query + "		hardnessFrom = '" + assyComponent.getHardnessFrom() + "' , ";
        query = query + "		hardnessTo = '" + assyComponent.getHardnessTo() + "' , ";
        query = query + "		designDate = '" + assyComponent.getDesignDate() + "' , ";
        query = query + "		startdate = '" + assyComponent.getStartDate().trim() + "' , ";
        query = query + "		enddate = '" + assyComponent.getEndDate().trim() + "' , ";
//        query = query + "		bomType = '" + assyComponent.getBomType().trim() + "' , ";
        query = query + "		firstRemark = '" + assyComponent.getFirstMarkStr().trim() + "' , ";
        query = query + "		secondRemark = '" + assyComponent.getSecondMarkStr().trim() + "' , ";
        query = query + "		parentitemcode = '" + assyComponent.getParentItemCodeStr().trim() + "' , ";
        query = query + "		newflag = '" + assyComponent.getNewFlagStr().trim() + "' , ";
        query = query + "		bomversion = '" + (assyComponent.getVersionStr()).trim() + "' , ";
        query = query + "		unit = '" + KETStringUtil.replaceSpecialTag(assyComponent.getUitStr().trim()) + "' ";

        query = query + "	 WHERE newbomcode = '" + modelName + "' ";
        query = query + "	 AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' ";

        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
            try
            {
                stmt.close();
            }
            catch(Exception e){}
        }
    }

    // Item Change 시 자 Item 정보를 Update 한다.
    public void changeChildItemUpdate(Connection conn, Vector childList)  throws Exception
    {
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        String query = "UPDATE ";
        query = query + " ketbomcomponent ";

        query = query + " SET ";
        query = query + "		itemseq = ? ,	";
        query = query + "		bomlevel = ? ,	 ";
        query = query + "		childitemcode = ? ,	";
        query = query + "		quantity = ? ,	";
        query = query + "		material = ? ,	";
        query = query + "		hardnessFrom = ? ,	";
        query = query + "		hardnessTo = ? ,	";
        query = query + "		designDate = ? ,	";
        query = query + "		startdate = ? ,	";
        query = query + "		enddate = ? ,	";
//        query = query + "		bomType = ? ,	";
        query = query + "		firstRemark = ? ,	";
        query = query + "		secondRemark = ? ,	 ";
        query = query + "		parentitemcode = ? ,	";
        query = query + "		newflag = ? ,	";
        query = query + "		unit = ? 			";
        query = query + "		,bomversion = ? ";
        query = query + "	 WHERE newbomcode = ? ";
        query = query + "	 AND sequencenumber = ? ";

        try
        {
            pstmt = conn.prepareStatement(query);

            for(int i=0; i<childList.size(); i++)
            {
                BOMAssyComponent component = (BOMAssyComponent)childList.elementAt(i);

                pstmt.setString(1, component.getItemSeqInt().intValue() + "");
                pstmt.setString(2, component.getLevelInt().intValue() + "");
                pstmt.setString(3, component.getItemCodeStr().trim());
                pstmt.setString(4, ""+component.getQuantityDbl());
                pstmt.setString(5, component.getMaterialStr().trim());
                pstmt.setString(6, component.getHardnessFrom().trim());
                pstmt.setString(7, component.getHardnessTo().trim());
                pstmt.setString(8, component.getDesignDate().trim());
                pstmt.setString(9, component.getStartDate().trim());
                pstmt.setString(10, component.getEndDate().trim());
//                pstmt.setString(11, component.getBomType().trim());
                pstmt.setString(11, component.getFirstMarkStr().trim());
                pstmt.setString(12, component.getSecondMarkStr().trim());
                pstmt.setString(13,  component.getParentItemCodeStr().trim());
                pstmt.setString(14, component.getNewFlagStr().trim());
                pstmt.setString(15, KETStringUtil.replaceSpecialTag(component.getUitStr().trim()));
                pstmt.setString(16, (component.getVersionStr()).trim());
                pstmt.setString(17, modelName);
                pstmt.setString(18, component.getSortOrderStr().trim());

                pstmt.executeUpdate();
            }
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
            throw exception;
        }
        finally
        {
            try
            {
                pstmt.close();
            }
            catch(Exception e) {}
        }

        String query3 = "UPDATE	";
        query3 = query3 + "ketbomsubstitute	"
                                + " SET	"
                                + "		parentitemcode = ?	"
                                + " WHERE newbomcode = ?	"
                                + "	 AND sequencenumber = ?	 ";

          try
        {
              pstmt = conn.prepareStatement(query3);

              for(int i=0; i<childList.size(); i++)
            {
                  BOMAssyComponent component = (BOMAssyComponent)childList.elementAt(i);

                pstmt.setString(1, component.getParentItemCodeStr().trim());
                pstmt.setString(2, modelName);
                pstmt.setString(3, component.getSortOrderStr().trim());

                pstmt.executeUpdate();
              }
          }
        catch(Exception exception)
        {
              throw exception;
          }
        finally
        {
            try
            {
                pstmt.close();
            }
            catch(Exception e) {}
          }
    }

    // Item Change 시 선택한 Item 정보를 Update 한다.
    public void changeItemUpdate(Connection conn, String itemCode, BOMAssyComponent assyComponent) throws Exception
    {
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();
        String itemCodeStr = "";

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        itemCodeStr =  itemCode;

        String query = "UPDATE ";
        query = query + " ketbomcomponent ";
        query = query + " SET ";
        query = query + "		itemseq = '" + assyComponent.getItemSeqInt().intValue() + "' , ";
        query = query + "		bomlevel = '" + assyComponent.getLevelInt().intValue() + "' , ";
        query = query + "		childitemcode = '" + assyComponent.getItemCodeStr().trim() + "' , ";
        query = query + "		quantity = '" + assyComponent.getQuantityDbl() + "' , ";
        query = query + "		startdate = '" + assyComponent.getStartDate().trim() + "' , ";
        query = query + "		material = '" + assyComponent.getMaterialStr().trim() + "' , ";
        query = query + "		hardnessFrom = '" + assyComponent.getHardnessFrom().trim() + "' , ";
        query = query + "		hardnessTo = '" + assyComponent.getHardnessTo().trim() + "' , ";
        query = query + "		designDate = '" + assyComponent.getDesignDate().trim() + "' , ";
        query = query + "		enddate = '" + assyComponent.getEndDate().trim() + "' , ";
//        query = query + "		bomType = '" + assyComponent.getBomType().trim() + "' , ";
        query = query + "		firstRemark = '" + assyComponent.getFirstMarkStr().trim() + "' , ";
        query = query + "		secondRemark = '" + assyComponent.getSecondMarkStr().trim() + "' , ";
        query = query + "		parentitemcode = '" + assyComponent.getParentItemCodeStr().trim() + "' , ";
        query = query + "		newflag = '" + assyComponent.getNewFlagStr().trim() + "' , ";
        query = query + "		bomversion = '" + (assyComponent.getVersionStr()).trim() + "' , ";
        query = query + "		unit = '" + KETStringUtil.replaceSpecialTag(assyComponent.getUitStr().trim()) + "' ";
        query = query + "	 WHERE newbomcode = '" + modelName + "' ";
        query = query + "	 AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' ";

        Statement stmt = null;
        try
        {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
            try
            {
                stmt.close();
            }
            catch(Exception e){}
        }

        String query3 = " UPDATE ";
        query3 = query3 + "	ketbomsubstitute ";
        query3 = query3 + "	SET childitemcode = '" + itemCodeStr + "' ";
        query3 = query3 + "	WHERE newbomcode = '" + modelName + "' ";
        query3 = query3 + "	AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' ";

        Statement stmt3 = null;
        try
        {
            stmt3 = conn.createStatement();
            stmt3.executeUpdate(query3);
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
            try
            {
                stmt3.close();
            }
            catch(Exception e){}
        }
    }

    // BOM 에 연결된 item 해제
    public void removeBomList(Connection conn, String sortOrder) throws Exception
    {
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

Kogger.debug(getClass(), "===>> removeBomList modelName : " + modelName);

        String ss = sortOrder.substring(sortOrder.length()-4, sortOrder.length());
        Integer orderInt = new Integer(ss);
        int sortNum = orderInt.intValue() + 1;
        String midSortOrder = sortOrder.substring(0, sortOrder.length()-4);
        String endSortOrder = "";
        if (sortNum < 10)
            endSortOrder = midSortOrder + "000" + sortNum;
        else if (sortNum < 100)
            endSortOrder = midSortOrder + "00" + sortNum;
        else if (sortNum < 1000)
            endSortOrder = midSortOrder + "0" + sortNum;
        else if (sortNum < 10000)
            endSortOrder = midSortOrder + "" + sortNum;

        String query = " DELETE	";
        query = query + "	 ketbomcomponent	";
        query = query + "	 WHERE sequencenumber >= ?	 ";
        query = query + "	 AND sequencenumber < ?	";
        query = query + "	 AND newbomcode = ?	 ";

Kogger.debug(getClass(), "===>> sortOrder : " + sortOrder);
Kogger.debug(getClass(), "===>> endSortOrder : " + endSortOrder);

        try
        {
            pstmt = conn.prepareStatement(query);

            pstmt.setString(1, sortOrder);
            pstmt.setString(2, endSortOrder);
            pstmt.setString(3, modelName);

            pstmt.executeUpdate();

            conn.commit();
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
            try
            {
                pstmt.close();
            }
            catch(Exception e){}
        }

          String query3 = " DELETE	 ";
        query3 = query3 + "	ketbomsubstitute	";
          query3 = query3 + "	WHERE newbomcode = ?	 ";
        query3 = query3 + "	AND sequencenumber >= ?	";
        query3 = query3 + "	AND sequencenumber < ?	";

          try {
              pstmt = conn.prepareStatement(query3);

              pstmt.setString(1, modelName);
              pstmt.setString(2, sortOrder);
              pstmt.setString(3, endSortOrder);

            pstmt.executeUpdate();

            conn.commit();
          }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
            try
            {
                pstmt.close();
            }
            catch(Exception e){}
        }
    }

    // Copy Child Item 정보 Update
    public void copyChildBomList(Connection conn, Vector dataVec)  throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

        subAssyVec.removeAllElements();

        String componentColumn = "	 newbomcode				\n" +
                                                ", parentitemcode		\n" +
                                                ", childitemcode			\n" +
                                                ", sequencenumber		\n" +
                                                ", bomlevel				\n" +
                                                ", itemseq					\n" +
                                                ", quantity				\n" +
                                                ", material				\n" +
                                                ", hardnessFrom			\n" +
                                                ", hardnessTo			\n" +
                                                ", designdate				\n" +
                                                ", startdate				\n" +
                                                ", enddate				\n" +
                                                ", newflag				\n" +
                                                ", firstRemark			\n" +
                                                ", secondRemark		\n" +
                                                ", unit						\n" +
                                                ", bomversion			\n" ;

        strSql.append(" INSERT INTO ketbomcomponent (" + componentColumn + ")		\n")
                 .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )				\n");

        try
        {
            pstmt = conn.prepareStatement(strSql.toString());

            for(int i=0; i<dataVec.size(); i++)
            {
                BOMTreeNode addedNode = (BOMTreeNode)dataVec.elementAt(i);
                Enumeration data = addedNode.preorderEnumeration();

                while (data.hasMoreElements())
                {
                    BOMTreeNode node = (BOMTreeNode)data.nextElement();
                    BOMAssyComponent bomcomponent = (BOMAssyComponent)node.getBOMComponent();

                    pstmt.clearParameters();

                    pstmt.setString(1, modelName);
                    pstmt.setString(2, (bomcomponent.getParentItemCodeStr()));
                    pstmt.setString(3, (bomcomponent.getItemCodeStr()));
                    pstmt.setString(4, bomcomponent.getSortOrderStr()); // Sort Order  ==> sequence_number
                    pstmt.setString(5, "" + bomcomponent.getLevelInt().intValue());
                    pstmt.setString(6, "" + bomcomponent.getItemSeqInt().intValue());
                    pstmt.setDouble(7, bomcomponent.getQuantityDbl().doubleValue());
                    pstmt.setString(8, Utility.checkNVL(bomcomponent.getMaterialStr()));
                    pstmt.setString(9, Utility.checkNVL(bomcomponent.getHardnessFrom()));
                    pstmt.setString(10, Utility.checkNVL(bomcomponent.getHardnessTo()));
                    pstmt.setString(11, Utility.checkNVL(bomcomponent.getDesignDate()));
                    pstmt.setString(12, Utility.checkNVL(bomcomponent.getStartDate()));
                    pstmt.setString(13, Utility.checkNVL(bomcomponent.getEndDate()));
                    pstmt.setString(14, bomcomponent.getNewFlagStr());
                    pstmt.setString(15, bomcomponent.getFirstMarkStr());
                    pstmt.setString(16, bomcomponent.getSecondMarkStr());
                    pstmt.setString(17, KETStringUtil.replaceSpecialTag(bomcomponent.getUitStr()));
                    pstmt.setString(18, (bomcomponent.getVersionStr()).trim());

                    pstmt.executeUpdate();

                    for(int k=0; k<bomcomponent.getSubAssyComponent().size(); k++)
                    {
                        TmpSubAssyData subAssyData = new TmpSubAssyData();
                        subAssyData.parentItemCode = bomcomponent.getParentItemCodeStr();
                        subAssyData.childItemCode = bomcomponent.getItemCodeStr();
                        subAssyData.sequenceNumber = bomcomponent.getSortOrderStr();
                        subAssyData.subAssyComp = (BOMSubAssyComponent)bomcomponent.getSubAssyComponent().elementAt(k);
                        subAssyVec.addElement(subAssyData);
                    }
                }
            }

            strSql.delete(0, strSql.length());

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", quantity						\n" +
                                                    ", unit								\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", newbomcode					\n";

            strSql.append(" INSERT INTO ketbomsubstitute (" + substituteColumn + ")				\n")
                    .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )												\n");

            pstmt = conn.prepareStatement(strSql.toString());

              for(int y=0; y<subAssyVec.size(); y++)
            {
                TmpSubAssyData tmpSubAssyData = (TmpSubAssyData)subAssyVec.elementAt(y);
                  BOMSubAssyComponent subAssyComponent = tmpSubAssyData.subAssyComp;

                pstmt.clearParameters();

                  pstmt.setString(1, (tmpSubAssyData.parentItemCode.trim()));
                pstmt.setString(2, (tmpSubAssyData.childItemCode.trim()));
                pstmt.setString(3, (subAssyComponent.getSubstituteItemCodeStr().trim()));
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, tmpSubAssyData.sequenceNumber.trim());
                pstmt.setString(9, modelName);

                  pstmt.executeUpdate();
              }
            strSql.delete(0, strSql.length());
          }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
              throw exception;
          }
        finally
        {
              try{pstmt.close();}catch(Exception e){}
          }
    }

    // 신규 BOM 속성값 수정
    public void changeBomProperty(Connection conn, BOMAssyComponent assyComponent) throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;

        try
        {
            String query = "UPDATE 																											\n";
            query = query + " ketbomcomponent 																							\n";
            query = query + " SET 																											\n";
            query = query + "		bomversion = '" + (assyComponent.getVersionStr()).trim() + "' , 								\n";
            query = query + "		parentitemcode = '" + assyComponent.getParentItemCodeStr().trim() + "' , 					\n";
            query = query + "		childitemcode = '" + assyComponent.getItemCodeStr().trim() + "' , 							\n";
            query = query + "		bomlevel = '" + assyComponent.getLevelInt().intValue() + "' , 									\n";
            query = query + "		itemseq = '" + assyComponent.getItemSeqInt().intValue() + "' , 								\n";
            query = query + "		boxquantity = '" + assyComponent.getBoxQuantityDbl() + "' , 									\n";
            query = query + "		quantity = '" + assyComponent.getQuantityDbl() + "' , 											\n";
            query = query + "		unit = '" + KETStringUtil.replaceSpecialTag(assyComponent.getUitStr().trim()) + "' , 			\n";
            query = query + "		material = '" + assyComponent.getMaterialStr().trim() + "' , 										\n";
            query = query + "		hardnessfrom = '" + assyComponent.getHardnessFrom().trim() + "' , 							\n";
            query = query + "		hardnessto = '" + assyComponent.getHardnessTo().trim() + "' , 								\n";
            query = query + "		designdate = '" + assyComponent.getDesignDate().trim() + "' , 									\n";
            query = query + "		startdate = '" + assyComponent.getStartDate().trim() + "' , 										\n";
            query = query + "		enddate = '" + assyComponent.getEndDate().trim() + "' , 										\n";
//			query = query + "		bomtype = '" + assyComponent.getBomType().trim() + "' , 										\n";
            query = query + "		firstremark = '" + assyComponent.getFirstMarkStr().trim() + "' , 								\n";
            query = query + "		secondremark = '" + assyComponent.getSecondMarkStr().trim() + "' , 						\n";
            query = query + "		newflag = '" + assyComponent.getNewFlagStr().trim() + "' 										\n";
            query = query + "	 WHERE newbomcode = '" + modelName + "' 														\n";
            query = query + "	 AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' 						\n";

            stmt = conn.createStatement();
            stmt.executeUpdate(query);
Kogger.debug(getClass(), ">>> query [신규 BOM 속성값 수정 : " + query);
            strSql.delete(0, strSql.length());

            String query2 = " DELETE ";
            query2 = query2 + " ketbomsubstitute ";
            query2 = query2 + "	WHERE newbomcode = '" + modelName + "' ";
            query2 = query2 + "	AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' ";

            stmt2 = conn.createStatement();
            stmt2.executeUpdate(query2);

            subAssyVec = assyComponent.getSubAssyComponent();

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", quantity						\n" +
//													", unit								\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", newbomcode					\n";

            strSql.append(" INSERT INTO ketbomsubstitute (" + substituteColumn + ")				\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)															\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for(int y=0; y<subAssyVec.size(); y++)
            {
                BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subAssyVec.elementAt(y);

                pstmt.setString(1, (assyComponent.getParentItemCodeStr().trim()));
                pstmt.setString(2, (assyComponent.getItemCodeStr().trim()));
                pstmt.setString(3, (subAssyComponent.getSubstituteItemCodeStr().trim()));
                pstmt.setString(4, ""+assyComponent.getQuantityDbl());
//				pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(5, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(7, assyComponent.getSortOrderStr().trim());
                pstmt.setString(8, modelName);

                pstmt.executeUpdate();
            }
            strSql.delete(0, strSql.length());
        } catch(Exception exception) {
            Kogger.error(getClass(), exception);
            throw exception;
        } finally {
            try{stmt.close();}catch(Exception e){}
            try{stmt1.close();}catch(Exception e){}
            try{stmt2.close();}catch(Exception e){}
            try{pstmt.close();}catch(Exception e){}
        }
    }

    // 변경 BOM 속성값 수정
    public void changeBomEcoProperty(Connection conn, BOMAssyComponent assyComponent) throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();
        String ecoHeaderNo = BOMBasicInfoPool.getBomEcoNumber() == null ? "" : BOMBasicInfoPool.getBomEcoNumber().trim();
        String ecoItemCode = assyComponent.getEcoItemCodeStr() == null ? "" : assyComponent.getEcoItemCodeStr().trim();

//Kogger.debug(getClass(), "@@@@@@@@@@@@ modelName : " + modelName);
//Kogger.debug(getClass(), "@@@@@@@@@@@@ ecoHeaderNo : " + ecoHeaderNo);
//Kogger.debug(getClass(), "@@@@@@@@@@@@ ecoItemCode : " + ecoItemCode);

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;

        try
        {
            String query = "UPDATE ";
            query = query + " ketbomecocomponent ";
            query = query + " SET ";
            query = query + "		bomversion = '" + (assyComponent.getVersionStr()).trim() + "' , ";
            query = query + "		parentitemcode = '" + assyComponent.getParentItemCodeStr().trim() + "' , ";
            query = query + "		childitemcode = '" + assyComponent.getItemCodeStr().trim() + "' , ";
            query = query + "		bomlevel = '" + assyComponent.getLevelInt().intValue() + "' , ";
            query = query + "		itemseq = '" + assyComponent.getItemSeqInt().intValue() + "' , ";
            query = query + "		afterBoxQuantity = '" + assyComponent.getBoxQuantityDbl() + "' , ";
            query = query + "		afterQuantity = '" + assyComponent.getQuantityDbl() + "' , ";
            query = query + "		afterUnit = '" + KETStringUtil.replaceSpecialTag(assyComponent.getUitStr().trim()) + "' , ";
            query = query + "		afterMaterial = '" + assyComponent.getMaterialStr().trim() + "' , ";
            query = query + "		afterHardnessfrom = '" + assyComponent.getHardnessFrom().trim() + "' , ";
            query = query + "		afterHardnessto = '" + assyComponent.getHardnessTo().trim() + "' , ";
            query = query + "		afterDesigndate = '" + assyComponent.getDesignDate().trim() + "' , ";
            query = query + "		afterStartdate = '" + assyComponent.getStartDate().trim() + "' , ";
            query = query + "		afterEnddate = '" + assyComponent.getEndDate().trim() + "' , ";
//			query = query + "		afterBomType = '" + assyComponent.getBomType().trim() + "' , ";
            query = query + "		ecoItemCode = '" + modelName + "' , ";
            query = query + "		appliedProductCode = '" + modelName + "' ";
            query = query + "	 WHERE ecoHeaderNumber = '" + ecoHeaderNo + "' ";
            query = query + "	 AND ecoItemCode = '" + modelName + "' ";
            query = query + "	 AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' ";

            stmt = conn.createStatement();
            stmt.executeUpdate(query);

            strSql.delete(0, strSql.length());

            String query2 = " DELETE ";
            query2 = query2 + " ketbomecosubstitute ";
            query2 = query2 + "	WHERE ecoHeaderNumber = '" + ecoHeaderNo + "' ";
            query2 = query2 + "	AND ecoItemCode = '" + modelName + "' ";
            query2 = query2 + "	AND sequencenumber = '" + assyComponent.getSortOrderStr().trim() + "' ";

            stmt2 = conn.createStatement();
            stmt2.executeUpdate(query2);

            subAssyVec = assyComponent.getSubAssyComponent();

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", afterQuantity					\n" +
                                                    ", afterUnit						\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", ecoitemcode					\n";


            strSql.append(" INSERT INTO ketbomecosubstitute (" + substituteColumn + ")				\n")
            .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)															\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for(int y=0; y<subAssyVec.size(); y++)
            {
                BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subAssyVec.elementAt(y);

                pstmt.setString(1, (assyComponent.getParentItemCodeStr().trim()));
                pstmt.setString(2, (assyComponent.getItemCodeStr().trim()));
                pstmt.setString(3, (subAssyComponent.getSubstituteItemCodeStr().trim()));
                pstmt.setString(4, ""+assyComponent.getQuantityDbl());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, assyComponent.getSortOrderStr().trim());
                pstmt.setString(9, modelName);

                pstmt.executeUpdate();
            }
            strSql.delete(0, strSql.length());
        } catch(Exception exception) {
            Kogger.error(getClass(), exception);
            throw exception;
        } finally {
            try{stmt.close();}catch(Exception e){}
            try{stmt1.close();}catch(Exception e){}
            try{stmt2.close();}catch(Exception e){}
            try{pstmt.close();}catch(Exception e){}
        }
    }

    public void changeMoveItem(Connection conn, BOMAssyComponent selectCmp, BOMAssyComponent targetCmp) throws Exception
    {
        StringBuffer strSql = new StringBuffer();
        String modelName = BOMBasicInfoPool.getPublicModelName() == null ? "" : BOMBasicInfoPool.getPublicModelName().trim();

        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        Statement stmt3 = null;
        Statement stmt4 = null;
        Statement stmt5 = null;

        try
        {
            // Selected Component Data Update
            String query = "UPDATE ";
            query = query + " ketbomcomponent ";
            query = query + " SET ";
            query = query + "		itemseq = '" + selectCmp.getItemSeqInt().intValue() + "' , ";
            query = query + "		bomlevel = '" + selectCmp.getLevelInt().intValue() + "' , ";
            query = query + "		childitemcode = '" + selectCmp.getItemCodeStr().trim() + "' , ";
            query = query + "		quantity = '" + selectCmp.getQuantityDbl() + "' , ";
            query = query + "		material = '" + selectCmp.getMaterialStr().trim() + "' , ";
            query = query + "		hardnessfrom = '" + selectCmp.getHardnessFrom().trim() + "' , ";
            query = query + "		hardnessto = '" + selectCmp.getHardnessTo().trim() + "' , ";
            query = query + "		designdate = '" + selectCmp.getDesignDate().trim() + "' , ";
            query = query + "		startdate = '" + selectCmp.getStartDate().trim() + "' , ";
            query = query + "		enddate = '" + selectCmp.getEndDate().trim() + "' , ";
//			query = query + "		bomType = '" + selectCmp.getBomType().trim() + "' , ";
            query = query + "		firstRemark = '" + selectCmp.getFirstMarkStr().trim() + "' , ";
            query = query + "		secondRemark = '" + selectCmp.getSecondMarkStr().trim() + "' , ";
            query = query + "		parentitemcode = '" + selectCmp.getParentItemCodeStr().trim() + "' , ";
            query = query + "		newflag = '" + selectCmp.getNewFlagStr().trim() + "' , ";
            query = query + "		bomversion = '" + (selectCmp.getVersionStr()).trim() + "' , ";
            query = query + "		unit = '" + KETStringUtil.replaceSpecialTag(selectCmp.getUitStr().trim()) + "' ";
            query = query + "	 WHERE newbomcode = '" + modelName + "' ";
            query = query + "	 AND sequencenumber = '" + selectCmp.getSortOrderStr().trim() + "' ";

            stmt = conn.createStatement();
            stmt.executeUpdate(query);

            strSql.delete(0, strSql.length());

            String query2 = " DELETE ";
            query2 = query2 + " ketbomsubstitute ";
            query2 = query2 + "	WHERE newbomcode = '" + modelName + "' ";
            query2 = query2 + "	AND sequencenumber = '" + selectCmp.getSortOrderStr().trim() + "' ";

            stmt2 = conn.createStatement();
            stmt2.executeUpdate(query2);

            subAssyVec = selectCmp.getSubAssyComponent();

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", quantity						\n" +
                                                    ", unit								\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", newbomcode					\n";

            strSql.append(" INSERT INTO ketbomsubstitute (" + substituteColumn + ")				\n")
                      .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)												\n");

            pstmt = conn.prepareStatement(strSql.toString());

            for(int y=0; y<subAssyVec.size(); y++)
            {
                BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subAssyVec.elementAt(y);

                pstmt.setString(1, (subAssyComponent.getParentItemCodeStr().trim()));
                pstmt.setString(2, (subAssyComponent.getChildItemCodeStr().trim()));
                pstmt.setString(3, (subAssyComponent.getSubstituteItemCodeStr().trim()));
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, selectCmp.getSortOrderStr().trim());
                pstmt.setString(9, modelName);

                pstmt.executeUpdate();
            }
            strSql.delete(0, strSql.length());

            // Target Component Data Update
            String query3 = "UPDATE ";
            query3 = query3 + " ketbomcomponent ";
            query3 = query3 + " SET ";
            query3 = query3 + "		itemseq = '" + targetCmp.getItemSeqInt().intValue() + "' , ";
            query3 = query3 + "		bomlevel = '" + targetCmp.getLevelInt().intValue() + "' , ";
            query3 = query3 + "		childitemcode = '" + targetCmp.getItemCodeStr().trim() + "' , ";
            query3 = query3 + "		quantity = '" + targetCmp.getQuantityDbl() + "' , ";
            query3 = query3 + "		material = '" + selectCmp.getMaterialStr().trim() + "' , ";
            query3 = query3 + "		hardnessfrom = '" + selectCmp.getHardnessFrom().trim() + "' , ";
            query3 = query3 + "		hardnessto = '" + selectCmp.getHardnessTo().trim() + "' , ";
            query3 = query3 + "		designdate = '" + selectCmp.getDesignDate().trim() + "' , ";
            query3 = query3 + "		startdate = '" + targetCmp.getStartDate().trim() + "' , ";
            query3 = query3 + "		enddate = '" + targetCmp.getEndDate().trim() + "' , ";
//			query3 = query3 + "		bomType = '" + targetCmp.getBomType().trim() + "' , ";
            query3 = query3 + "		firstRemark = '" + targetCmp.getFirstMarkStr().trim() + "' , ";
            query3 = query3 + "		secondRemark = '" + targetCmp.getSecondMarkStr().trim() + "' , ";
            query3 = query3 + "		parentitemcode = '" + targetCmp.getParentItemCodeStr().trim() + "' , ";
            query3 = query3 + "		newflag = '" + targetCmp.getNewFlagStr().trim() + "' , ";
            query3 = query3 + "		bomversion = '" + (targetCmp.getVersionStr()).trim() + "' , ";
            query3 = query3 + "		unit = '" + KETStringUtil.replaceSpecialTag(targetCmp.getUitStr().trim()) + "' ";
            query3 = query3 + "	 WHERE newbomcode = '" + modelName + "' ";
            query3 = query3 + "	 AND sequencenumber = '" + targetCmp.getSortOrderStr().trim() + "' ";

            stmt3 = conn.createStatement();
            stmt3.executeUpdate(query3);

            strSql.delete(0, strSql.length());

            String query5 = " DELETE ";
            query5 = query5 + " ketbomsubstitute ";
            query5 = query5 + "	WHERE newbomcode = '" + modelName + "' ";
            query5 = query5 + "	AND sequencenumber = '" + targetCmp.getSortOrderStr().trim() + "' ";

            stmt5 = conn.createStatement();
            stmt5.executeUpdate(query5);

            subAssyVec.removeAllElements();
            subAssyVec = targetCmp.getSubAssyComponent();

            // ketbomsubstitute Table 에 데이타 저장하기...
            String substituteColumn1 = "	 parentitemcode						\n" +
                                                    ", childitemcode					\n" +
                                                    ", substituteitemcode			\n" +
                                                    ", quantity						\n" +
                                                    ", unit								\n" +
                                                    ", startdate						\n" +
                                                    ", enddate						\n" +
                                                    ", sequencenumber				\n" +
                                                    ", newbomcode					\n";

            strSql.append(" INSERT INTO ketbomsubstitute (" + substituteColumn1 + ")			\n")
                    .append(" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?)													\n");

            pstmt = conn.prepareStatement(strSql.toString());

Kogger.debug(getClass(), "===>> subAssyVec size : " + subAssyVec.size());

            for(int y=0; y<subAssyVec.size(); y++)
            {
                BOMSubAssyComponent subAssyComponent = (BOMSubAssyComponent)subAssyVec.elementAt(y);
                pstmt.setString(1, (subAssyComponent.getParentItemCodeStr().trim()));
                pstmt.setString(2, (subAssyComponent.getChildItemCodeStr().trim()));
                pstmt.setString(3, (subAssyComponent.getSubstituteItemCodeStr().trim()));
                pstmt.setDouble(4, subAssyComponent.getQuantityDbl().doubleValue());
                pstmt.setString(5, Utility.checkNVL(KETStringUtil.replaceSpecialTag(subAssyComponent.getUitStr())));
                pstmt.setString(6, Utility.checkNVL(subAssyComponent.getStartDate()));
                pstmt.setString(7, Utility.checkNVL(subAssyComponent.getEndDate()));
                pstmt.setString(8, targetCmp.getSortOrderStr().trim());
                pstmt.setString(9, modelName);

                pstmt.executeUpdate();
            }
            strSql.delete(0, strSql.length());
        }
        catch(Exception exception)
        {
            Kogger.error(getClass(), exception);
            throw exception;
        }
        finally
        {
            try{stmt.close();}catch(Exception e){}
            try{stmt1.close();}catch(Exception e){}
            try{stmt2.close();}catch(Exception e){}
            try{stmt3.close();}catch(Exception e){}
            try{stmt4.close();}catch(Exception e){}
            try{stmt5.close();}catch(Exception e){}

            try{pstmt.close();}catch(Exception e){}
        }
    }

    public void changeItemInfo(Connection conn, BOMAssyComponent assyComponent)  throws Exception
    {
        NumberFormat format = NumberFormat.getInstance();
        format.setMinimumFractionDigits(3);

        String query = "Update ";
        query = query + " ketbomcomponent ";
        query = query + " SET ";
        query = query + " ITEMSEQ = '" + assyComponent.getItemSeqInt().intValue() + "' , ";
        query = query + " BOMLEVEL = '" + assyComponent.getLevelInt().intValue() + "' , ";
        query = query + " CHILDITEMCODE = '" + assyComponent.getItemCodeStr().trim() + "' , ";
        query = query + " QUANTITY = '" + format.format(assyComponent.getQuantityDbl()) + "' , ";
        query = query + " material = '" + assyComponent.getMaterialStr().trim() + "' , ";
        query = query + " hardnessfrom = '" + assyComponent.getHardnessFrom().trim() + "' , ";
        query = query + " hardnessto = '" + assyComponent.getHardnessTo().trim() + "' , ";
        query = query + " designdate = '" + assyComponent.getDesignDate().trim() + "' , ";
        query = query + " STARTDATE = '" + assyComponent.getStartDate().trim() + "' , ";
        query = query + " ENDDATE = '" + assyComponent.getEndDate().trim() + "' , ";
//        query = query + " BOMTYPE = '" + assyComponent.getBomType().trim() + "' , ";
        query = query + " FIRSTREMARK = '" + assyComponent.getFirstMarkStr().trim() + "' , ";
        query = query + " SECONDREMARK = '" + assyComponent.getSecondMarkStr().trim() + "' , ";
        query = query + " PARENTITEMCODE = '" + assyComponent.getParentItemCodeStr().trim() + "' , ";
        query = query + " NEWFLAG = '" + assyComponent.getNewFlagStr().trim() + "' ";
        query = query + "		bomversion = '" + (assyComponent.getVersionStr()).trim() + "' , ";
        query = query + "		unit = '" + KETStringUtil.replaceSpecialTag(assyComponent.getUitStr().trim()) + "' ";
        query = query + " WHERE NEWBOMCODE = '" + BOMBasicInfoPool.getPublicModelName() + "' ";
        query = query + "   and SEQUENCENUMBER = '" + assyComponent.getSortOrderStr().trim() + "' ";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            int result = stmt.executeUpdate(query);
        } finally {
            try{stmt.close();} catch(Exception e){}
        }

        conn.commit();
    }

}
