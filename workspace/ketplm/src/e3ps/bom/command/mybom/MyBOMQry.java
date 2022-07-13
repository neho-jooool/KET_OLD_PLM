package e3ps.bom.command.mybom;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.JCheckBox;

import e3ps.bom.command.bomcheckoutin.BOMCheckOutInDao;
import e3ps.bom.command.checkout.CheckOutCmd;
import e3ps.bom.common.clipboard.BOMBasicInfoPool;
import e3ps.bom.common.component.BOMAssyComponent;
import e3ps.bom.common.component.BOMSubAssyComponent;
import e3ps.bom.common.util.Utility;
import e3ps.bom.dao.BOMSearchUtilDao;
import e3ps.bom.dao.pool.DBConnectionManager;
import e3ps.bom.framework.util.MessageBox;
import e3ps.bom.framework.util.Registry;
import e3ps.bom.service.KETBOMHeaderQueryBean;
import e3ps.bom.service.KETBomHelper;
import e3ps.common.message.KETMessageService;
import ext.ket.part.util.PartSpecEnum;
import ext.ket.shared.log.Kogger;

public class MyBOMQry
{
    DBConnectionManager res = null;
    Connection conn = null;
    Statement stmt = null;
    Statement stmtComponent = null;
    Statement stmtDesignator = null;
    Statement stmtSubstitute = null;
    Statement stmtItemComponent = null;
    Statement stmtItemSubstitute = null;

    ResultSet rs = null;
    ResultSet rsComponent = null;
    ResultSet rsDesignator = null;
    ResultSet rsSubstitute = null;
    ResultSet rsItemComponent = null;
    ResultSet rsItemSubstitute = null;

    Registry registry = Registry.getRegistry("e3ps.bom.bom");
    private static Registry messageRegistry = Registry.getRegistry("e3ps.bom.message");

    public MyBOMQry()
    {
    }

// -----------------------------------------------------------------------------
    public Vector getBOMOpen(String itemCode)
    {
        Vector vecResult = new Vector();
        Vector vecAllDesignator = new Vector();
        Vector vecAllSubAssy = new Vector();

        try {
            StringBuffer strSql = new StringBuffer();
            String query = "";
            KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmtComponent = conn.createStatement();

            //shin.....
            strSql.append(" SELECT                                                                \n")
                    .append("        childItemCode                                                \n")
                    .append(" ,    bomLevel                                                        \n")
                    .append(" ,    bomVersion                                                    \n")
                    .append(" ,    itemSeq                                                        \n")
                    .append(" ,    boxquantity                                                    \n")
                    .append(" ,    quantity                                                        \n")
                    .append(" ,    unit                                                            \n")
                    .append(" ,    material                                                        \n")
                    .append(" ,    hardnessfrom                                                \n")
                    .append(" ,    hardnessto                                                    \n")
                    .append(" ,    designdate                                                    \n")
                    .append(" ,    startDate                                                        \n")
                    .append(" ,    endDate                                                        \n")
                    .append(" ,    newFlag                                                        \n")
                    .append(" ,    firstRemark                                                    \n")
                    .append(" ,    secondRemark                                                \n")
                    .append(" ,    parentItemCode                                                \n")
                    .append(" ,    sequenceNumber                                            \n")
                    .append("  FROM ketbomcomponent                                    \n")
                    .append("    WHERE newBomCode = '" + itemCode + "'                \n")
                    .append("    ORDER BY                                                        \n")
                    .append("        sequenceNumber                                            \n");

            rsComponent = stmtComponent.executeQuery(strSql.toString());
            strSql.delete(0, strSql.length());

            stmtSubstitute = conn.createStatement();

            strSql.append(" SELECT                                                                \n")
                    .append("        newbomcode                                                \n")
                    .append(" ,    substituteItemCode                                            \n")
                    .append(" ,    quantity                                                        \n")
                    .append(" ,    startDate                                                        \n")
                    .append(" ,    endDate                                                        \n")
                    .append(" ,    parentItemCode                                                \n")
                    .append(" ,    childItemCode                                                \n")
                    .append(" ,    sequenceNumber                                            \n")
                    .append("  FROM ketbomsubstitute                                        \n")
                    .append("    WHERE newBomCode = '" + itemCode + "'                \n")
                    .append("    ORDER BY                                                        \n")
                    .append("        sequenceNumber                                            \n");

            rsSubstitute = stmtSubstitute.executeQuery(strSql.toString());
            strSql.delete(0, strSql.length());

            vecAllSubAssy.removeAllElements();

            // Substitute Item 의 Item 정보 쿼리를 위한 Vector
            BOMSubAssyComponent subComponent = null;

            while(rsSubstitute.next()) {
                subComponent = new BOMSubAssyComponent();

                String subItemCodeStr = rsSubstitute.getString("substituteItemCode") == null ? "" : rsSubstitute.getString("substituteItemCode");
                subComponent.setSubstituteItemCodeStr(subItemCodeStr);
                String subAssyQty = (rsSubstitute.getString("quantity") == null ? "" : rsSubstitute.getString("quantity"));
                if (!Utility.isDouble(subAssyQty))
                    subAssyQty = "0";
                subComponent.setQuantityDbl(new Double(subAssyQty));
                subComponent.setStartDate(rsSubstitute.getString("startDate") == null ? "" : rsSubstitute.getString("startDate"));
                subComponent.setEndDate(rsSubstitute.getString("endDate") == null ? "" : rsSubstitute.getString("endDate"));
                subComponent.setParentItemCodeStr(rsSubstitute.getString("parentItemCode") == null ? "" : rsSubstitute.getString("parentItemCode").toString().trim());
                subComponent.setChildItemCodeStr(rsSubstitute.getString("childItemCode") == null ? "" : rsSubstitute.getString("childItemCode").toString().trim());
                subComponent.setSortOrderStr(rsSubstitute.getString("sequenceNumber") == null ? "" : rsSubstitute.getString("sequenceNumber"));

                try    {
                    stmtItemSubstitute = conn.createStatement();
                    query = " SELECT                                                                                    \n";
                    query += "        m.name as descr                                                                \n    ";
                    query += " ,    i.statestate as  status                                                                \n    ";
                    query += " ,    m.defaultunit uom                                                                    \n    ";
                    query += "  FROM wtpartmaster m, wtpart i                                                    \n    ";
                    query += "    WHERE m.wtpartnumber = '" + subItemCodeStr + "'                            \n    ";
                    query += "    AND m.ida2a2 = i.ida3masterreference                                            \n    ";
                    query += "    ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC        ";

                    rsItemSubstitute = stmtItemSubstitute.executeQuery(query);

                    if(rsItemSubstitute.next()) {
                        subComponent.setDescStr(rsItemSubstitute.getString("descr") == null ? "" : rsItemSubstitute.getString("descr"));
                        subComponent.setStatusStr(rsItemSubstitute.getString("status") == null ? "" : rsItemSubstitute.getString("status"));
                        subComponent.setStatusKrStr(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/);        // PLM에서 부품의 상태는 모두 승인완료임
                        subComponent.setUitStr(rsItemSubstitute.getString("uom") == null ? "" : rsItemSubstitute.getString("uom"));
                    }
                } catch(Exception ex) {
                    Kogger.error(getClass(), ex);
                } finally {
                    if (rsItemSubstitute != null)  {rsItemSubstitute.close(); rsItemSubstitute.close();}
                    if (stmtItemSubstitute != null) {stmtItemSubstitute.close(); stmtItemSubstitute.close();}
                }
                vecAllSubAssy.addElement(subComponent);
            }

            int dataCount = 0;
            BOMAssyComponent component = null;
            Vector itemVec = new Vector();
            BOMCheckOutInDao checkoutDao = new BOMCheckOutInDao();

            while(rsComponent.next()) {
                dataCount++;
                String itemCodeStr = rsComponent.getString("childItemCode") == null ? "" : rsComponent.getString("childItemCode");
                String sortOrderStr = rsComponent.getString("sequenceNumber") == null ? "" : rsComponent.getString("sequenceNumber");

                Vector subAssyVec = new Vector();

                component = new BOMAssyComponent(itemCodeStr.trim());

                component.setSeqInt(new Integer(dataCount));
                component.setLevelInt(new Integer(rsComponent.getString("bomLevel") == null ? "1" : rsComponent.getString("bomLevel")));
                component.setVersionStr(rsComponent.getString("bomVersion") == null ? "1" : rsComponent.getString("bomVersion"));
                component.setItemSeqInt(new Integer(rsComponent.getString("itemSeq") == null ? "10" : rsComponent.getString("itemSeq")));
                String boxQty =  rsComponent.getString("boxquantity") == null ? "1.0" : rsComponent.getString("boxquantity");
                if (boxQty.startsWith("."))
                    boxQty = "0" + boxQty;
                component.setBoxQuantityDbl(new Double(boxQty));

                String qty =  rsComponent.getString("quantity") == null ? "1.000" : rsComponent.getString("quantity");
                if (qty.startsWith("."))
                    qty = "0" + qty;
                component.setQuantityDbl(new Double(qty));
                component.setStartDate(rsComponent.getString("startDate") == null ? "" : rsComponent.getString("startDate"));
                component.setEndDate(rsComponent.getString("endDate") == null ? "" : rsComponent.getString("endDate"));
                component.setNewFlagStr(rsComponent.getString("newFlag") == null ? "" : rsComponent.getString("newFlag"));
                component.setFirstMarkStr(rsComponent.getString("firstRemark") == null ? "" : rsComponent.getString("firstRemark"));
                component.setSecondMarkStr(rsComponent.getString("secondRemark") == null ? "" : rsComponent.getString("secondRemark"));
                component.setParentItemCodeStr(rsComponent.getString("parentItemCode") == null ? "" : rsComponent.getString("parentItemCode").toString().trim());
                component.setSortOrderStr(sortOrderStr);

                component.setUitStr(rsComponent.getString("unit") == null ? "" : rsComponent.getString("unit"));

                // 금형관련 정보 추가 셋팅
                component.setMaterialStr(rsComponent.getString("material"));                // 재질
                component.setHardnessFrom(rsComponent.getString("hardnessfrom"));    // 경도(From)
                component.setHardnessTo(rsComponent.getString("hardnessto"));            // 경도(To)
                component.setDesignDate(rsComponent.getString("designdate"));            // 설계일자

                // BOM 리스트를 Component 에 담을 때 Item 속성도 쿼리해서 Component 에 담아준다.
                try
                {
                    stmtItemComponent = conn.createStatement();
                    query = "  SELECT  m.name descr                                                                                                \n    ";
                    query += "         ,    i.versionida2versioninfo ver                                                                                \n    ";
                    query += "         ,    i.iterationida2iterationinfo iter                                                                                \n    ";
                    query += "         ,    i.statestate status                                                                                            \n    ";
                    query += "         ,    m.defaultunit unit                                                                                            \n    ";
                    query += "         , i."  + PartSpecEnum.SpPartType.getColumnName() + " partType                                                                                                \n    ";
                    query += "         , i."  + PartSpecEnum.SpIsDeleted.getColumnName() + " isDeleted                                                                                            \n    ";
                    query += " FROM   wtpartmaster m                                                                                                \n    ";
                    query += "          , wtpart i                                                                                                        \n    ";
                    query += "          , ( SELECT  MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid                        \n    ";
                    query += "               FROM  WTPart p2, WTPartMaster m2                                                                \n    ";
                    query += "              WHERE  m2.wtpartnumber =  '" + itemCodeStr + "'                                                \n    ";
                    query += "                AND   p2.ida3masterreference = m2.ida2a2                                                        \n    ";
                    query += "                AND   p2.statecheckoutinfo <> 'wrk'                                                                \n    ";
                    query += "                AND   p2.latestiterationinfo = 1                                                                        \n    ";
                    query += "             GROUP BY m2.wtpartnumber ) x                                                                        \n    ";
                    query += "WHERE  i.ida3masterreference = m.ida2a2                                                                            \n    ";
                    query += "  AND   i.ida2a2 = x.objid                                                                                            \n    ";
                    query += "ORDER BY m.wtpartnumber                                                                                            \n";

                    rsItemComponent = stmtItemComponent.executeQuery(query);

                    itemVec.addElement(itemCodeStr);

                    if(rsItemComponent.next()) {
                        component.setDescStr(rsItemComponent.getString("descr") == null ? "" : rsItemComponent.getString("descr"));
                        component.setVersionStr((rsItemComponent.getString("ver") == null ? "" : rsItemComponent.getString("ver")));
                        component.setStatusStr(rsItemComponent.getString("status") == null ? "" : rsItemComponent.getString("status"));
                        component.setStatusKrStr(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/);        // PLM에서 부품의 상태는 모두 승인완료임
                        component.setIsDeleted(rsItemComponent.getString("isDeleted") == null ? "" : rsItemComponent.getString("isDeleted"));
                        // Added by MJOH, 2011-04-07
                        component.setIBAPartType(rsItemComponent.getString("partType") == null ? "" : rsItemComponent.getString("partType"));
                    }
                }
                catch(Exception ex)
                {
                    Kogger.error(getClass(), ex);
                }
                finally
                {
                    if (rsItemComponent != null)    { rsItemComponent.close(); rsItemComponent.close();}
                    if (stmtItemComponent != null) { stmtItemComponent.close(); stmtItemComponent.close();}
                }

                // 작업자의 작업상태가 4(작업완료) 가 아닌 경우에만 자동으로 Check-Out 처리 함 :: TODO 추후 Check-out 수정 후 주석처리 해야 함
                String strStatus = "";
                BOMSearchUtilDao utilDao = new  BOMSearchUtilDao();
                strStatus = utilDao.getEndWorkingFlagNew(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
                String bomStatus = BOMBasicInfoPool.getPublicBOMStatus();

                // TODO 현재 BOM을 체크아웃한 사용자가 있는지 확인 후 진행함
                BOMCheckOutInDao check = new BOMCheckOutInDao();
                boolean isCheckOut = check.isItemCheckedOut(BOMBasicInfoPool.getPublicModelName());
                boolean isCheckOutByself = check.isItemCheckedOutCurrentUser(BOMBasicInfoPool.getPublicModelName(), BOMBasicInfoPool.getUserId());
Kogger.debug(getClass(), "@@@@ isRootCheckOut ?? : " + isCheckOut);

                if ( (!isCheckOut || isCheckOutByself) && (strStatus != null && !strStatus.equals("") && !strStatus.equals("4")) && ( bomStatus.equals("INWORK") || bomStatus.equals("REWORK") )  )
                {
                    //shin.....체크 아웃 ------------------------------------------------------------------------------------------------------------------
                    String versionStr = (component.getVersionStr()).equals("") ? "0" : component.getVersionStr();
                    component.setCheckOutStr(BOMBasicInfoPool.getUserName() + "(" + BOMBasicInfoPool.getUserEMail() + ")");
                    CheckOutCmd coc = new CheckOutCmd();
                    coc.workingUpdate(true);
                    checkoutDao.setCheckOut( component.getItemCodeStr(), versionStr, BOMBasicInfoPool.getUserId() );
                    // --------------------------------------------------------------------------------------------------------------------------------------
                }
                subAssyVec.removeAllElements();

                for (int inx = 0; inx < vecAllSubAssy.size(); inx++) {
                    BOMSubAssyComponent tmpSubAssyComponent = (BOMSubAssyComponent)(vecAllSubAssy.elementAt(inx));
                    if (sortOrderStr.trim().equals(tmpSubAssyComponent.getSortOrderStr().trim()))
                        subAssyVec.addElement(tmpSubAssyComponent);
                }

                if (subAssyVec != null && subAssyVec.size() > 0)
                    component.setSubAssyComponent(subAssyVec);

               vecResult.addElement(component);
            }

            Vector coworkerVec = new Vector();
            String resultItemCode = "";
            String checkOutItemCode = "";
            coworkerVec = KETBomHelper.service.getCheckOuter(itemVec);

Kogger.debug(getClass(), "===>> coworkerVec : " + coworkerVec.toString());
Kogger.debug(getClass(), "===>> vecResult : " + vecResult.size());

            if(coworkerVec != null && coworkerVec.size() > 0) {
                for(int i=0; i<vecResult.size(); i++) {
                    resultItemCode = vecResult.elementAt(i) == null ? "" : vecResult.elementAt(i).toString().trim();

Kogger.debug(getClass(), "===>> resultItemCode : " + resultItemCode);

                    for(int j=0; j<coworkerVec.size(); j++) {
                        checkOutItemCode = coworkerVec.elementAt(j) == null ? "" : coworkerVec.elementAt(j).toString().trim().substring(3, coworkerVec.elementAt(j).toString().trim().indexOf("|"));

Kogger.debug(getClass(), "===>> checkOutItemCode : " + checkOutItemCode);

                        if(resultItemCode.equals(checkOutItemCode)) {
                            BOMAssyComponent cmp = (BOMAssyComponent)vecResult.elementAt(i);
                            cmp.setCheckOutStr(coworkerVec.elementAt(j).toString().substring(coworkerVec.elementAt(j).toString().indexOf("|")+1));
                            vecResult.set(i, cmp);
                            break;
                        }
                    }
                }
            }
        } catch (Exception ee) {
            Kogger.error(getClass(), ee);
        } finally {
            try    {
                if (rsComponent != null) { rsComponent.close(); rsComponent.close();}
                if (rsDesignator != null)  { rsDesignator.close(); rsDesignator.close();}

                if (stmtComponent != null) { stmtComponent.close(); stmtComponent.close();}
                if (stmtDesignator != null)  { stmtDesignator.close(); stmtDesignator.close();}
                if (stmtSubstitute != null)   { stmtSubstitute.close(); stmtSubstitute.close();}
            } catch(Exception e)     {
                Kogger.error(getClass(), e);
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null){
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return vecResult;
    }


    public Vector getBOMEcoOpen(String ecoHeaderNumber, String ecoItemCode)
    {
        Vector vecResult = new Vector();
        Vector vecAllDesignator = new Vector();
        Vector vecAllSubAssy = new Vector();

Kogger.debug(getClass(), "----->> registry.getString(plm) : " + registry.getString("plm"));
//Kogger.debug(getClass(), "@@@@@@@@@@@ ecoHeaderNumber : " + ecoHeaderNumber);
//Kogger.debug(getClass(), "@@@@@@@@@@@ ecoItemCode : " + ecoItemCode);
        try
        {
            StringBuffer strSql = new StringBuffer();
            String query = "";
            KETBOMHeaderQueryBean bean = new KETBOMHeaderQueryBean();

            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmtComponent = conn.createStatement();

            strSql.append(" SELECT                                                                            \n")
                    .append("        parentItemCode                                                            \n")
                    .append(" ,    childItemCode                                                            \n")
                    .append(" ,    sequenceNumber                                                        \n")
                    .append(" ,    bomLevel                                                                    \n")
                    .append(" ,    itemSeq                                                                    \n")
                    .append(" ,    beforeBoxQuantity                                                        \n")
                    .append(" ,    afterBoxQuantity                                                            \n")
                    .append(" ,    beforeQuantity                                                            \n")
                    .append(" ,    afterQuantity                                                                \n")
                    .append(" ,    beforeUnit                                                                \n")
                    .append(" ,    afterUnit                                                                    \n")
                    .append(" ,    beforeMaterial                                                            \n")
                    .append(" ,    afterMaterial                                                                \n")
                    .append(" ,    beforeHardnessFrom                                                    \n")
                    .append(" ,    afterHardnessFrom                                                        \n")
                    .append(" ,    beforeHardnessTo                                                        \n")
                    .append(" ,    afterHardnessTo                                                            \n")
                    .append(" ,    beforeStartDate                                                            \n")
                    .append(" ,    afterStartDate                                                            \n")
                    .append(" ,    beforeDesignDate                                                        \n")
                    .append(" ,    afterDesignDate                                                            \n")
                    .append(" ,    beforeEndDate                                                            \n")
                    .append(" ,    afterEndDate                                                                \n")
                    .append(" ,    ecoCode                                                                    \n")
                    .append("  FROM ketbomecocomponent                                            \n")
                    .append("    WHERE ecoHeaderNumber = '" + ecoHeaderNumber + "'            \n")
                    .append("    AND    ecoItemCode = '" + ecoItemCode + "'                            \n")
                    .append("    ORDER BY                                                                    \n")
                    .append("        sequenceNumber                                                        \n");
//Kogger.debug(getClass(), "@@@@@@@@@@ SQL  1 : " + strSql);
            rsComponent = stmtComponent.executeQuery(strSql.toString());
//Kogger.debug(getClass(), "@@@@@@@@@@ rsComponent : " + rsComponent);
            strSql.delete(0, strSql.length());

            stmtSubstitute = conn.createStatement();

            strSql.append(" SELECT                                                                            \n")
                    .append("        parentItemCode                                                            \n")
                    .append(" ,    childItemCode                                                            \n")
                    .append(" ,    substituteItemCode                                                        \n")
                    .append(" ,    beforeQuantity                                                            \n")
                    .append(" ,    afterQuantity                                                                \n")
                    .append(" ,    beforeUnit                                                                \n")
                    .append(" ,    afterUnit                                                                    \n")
                    .append(" ,    beforeStartDate                                                            \n")
                    .append(" ,    afterStartDate                                                            \n")
                    .append(" ,    beforeEndDate                                                            \n")
                    .append(" ,    afterEndDate                                                                \n")
                    .append(" ,    sequenceNumber                                                        \n")
                    .append(" ,    ecoCode                                                                    \n")
                    .append("  FROM ketbomecosubstitute                                                \n")
                    .append("    WHERE ecoHeaderNumber = '" + ecoHeaderNumber + "'            \n")
                    .append("    AND    ecoItemCode = '" + ecoItemCode + "'                            \n")
                    .append("    ORDER BY                                                                    \n")
                    .append("        sequenceNumber                                                        \n");
//Kogger.debug(getClass(), "@@@@@@@@@@ SQL  2 : " + strSql);
            rsSubstitute = stmtSubstitute.executeQuery(strSql.toString());
//Kogger.debug(getClass(), "@@@@@@@@@@ rsSubstitute : " + rsSubstitute);
            strSql.delete(0, strSql.length());

            vecAllSubAssy.removeAllElements();

            // Substitute Item 의 Item 정보 쿼리를 위한 Vector
            BOMSubAssyComponent subComponent = null;

            while(rsSubstitute.next()) {
                subComponent = new BOMSubAssyComponent();

                subComponent.setParentItemCodeStr(rsSubstitute.getString("parentItemCode") == null ? "" : rsSubstitute.getString("parentItemCode").toString().trim());
                subComponent.setChildItemCodeStr(rsSubstitute.getString("childItemCode") == null ? "" : rsSubstitute.getString("childItemCode").toString().trim());
                String subItemCodeStr = rsSubstitute.getString("substituteItemCode") == null ? "" : rsSubstitute.getString("substituteItemCode");
                subComponent.setSubstituteItemCodeStr(subItemCodeStr);
                String subAssyBeforeQty = (rsSubstitute.getString("beforeQuantity") == null ? "1.00000" : rsSubstitute.getString("beforeQuantity"));
                if (!Utility.isDouble(subAssyBeforeQty))
                    subAssyBeforeQty = "0";
                subComponent.setBeforeQuantityDbl(new Double(subAssyBeforeQty));
                String subAssyAfterQty = (rsSubstitute.getString("afterQuantity") == null ? "1.00000" : rsSubstitute.getString("afterQuantity"));
                if (!Utility.isDouble(subAssyAfterQty))
                    subAssyAfterQty = "0";
                subComponent.setQuantityDbl(new Double(subAssyAfterQty));

                // 추가 컬럼
                subComponent.setBeforeUnitStr(rsSubstitute.getString("beforeUnit") == null ? "" : rsSubstitute.getString("beforeUnit"));
                subComponent.setUitStr(rsSubstitute.getString("afterUnit") == null ? "" : rsSubstitute.getString("afterUnit"));

                subComponent.setBeforeStartDate(rsSubstitute.getString("beforeStartDate") == null ? "" : rsSubstitute.getString("beforeStartDate"));
                subComponent.setStartDate(rsSubstitute.getString("afterStartDate") == null ? "" : rsSubstitute.getString("afterStartDate"));
                subComponent.setBeforeEndDate(rsSubstitute.getString("beforeEndDate") == null ? "" : rsSubstitute.getString("beforeEndDate"));
                subComponent.setEndDate(rsSubstitute.getString("afterEndDate") == null ? "" : rsSubstitute.getString("afterEndDate"));
                subComponent.setEcoCodeStr(rsSubstitute.getString("ecoCode") == null ? "" : rsSubstitute.getString("ecoCode"));
                subComponent.setSortOrderStr(rsSubstitute.getString("sequenceNumber") == null ? "" : rsSubstitute.getString("sequenceNumber"));

                try    {
                    stmtItemSubstitute = conn.createStatement();
                    query = " SELECT                                                                                    \n";
                    query += "        m.name descr                                                                    \n    ";
                    query += " ,    i.statestate status                                                                    \n    ";
                    query += "  FROM wtpartmaster m, wtpart i                                                    \n    ";
                    query += "    WHERE m.wtpartnumber = '" + subItemCodeStr + "'                            \n    ";
                    query += "    AND m.ida2a2 = i.ida3masterreference                                            \n    ";
                    query += "    ORDER BY i.versionida2versioninfo, i.iterationida2iterationinfo DESC        ";

                    rsItemSubstitute = stmtItemSubstitute.executeQuery(query);

                    if(rsItemSubstitute.next()) {
                        subComponent.setDescStr(rsItemSubstitute.getString("descr") == null ? "" : rsItemSubstitute.getString("descr"));
                        subComponent.setStatusStr(rsItemSubstitute.getString("status") == null ? "" : rsItemSubstitute.getString("status"));
                        subComponent.setStatusKrStr(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/);        // PLM에서 부품의 상태는 모두 승인완료임
                    }
                } catch(Exception ex){
                    Kogger.error(getClass(), ex);
                } finally {
                    if (rsItemSubstitute != null)    { rsItemSubstitute.close(); rsItemSubstitute.close();}
                    if (stmtItemSubstitute != null)     { stmtItemSubstitute.close(); stmtItemSubstitute.close();}
                }
                vecAllSubAssy.addElement(subComponent);
            }

            int dataCount = 0;
            BOMAssyComponent component = null;
            Vector itemVec = new Vector();

            boolean  isRemove = false;
            String strEcoCode = "";

            while(rsComponent.next()) {
                strEcoCode = rsComponent.getString("ecoCode") == null ? "" : rsComponent.getString("ecoCode").trim();
                isRemove = strEcoCode.equals("Remove");

//Kogger.debug(getClass(), "@@@@ strEcoCode : " + strEcoCode);
//Kogger.debug(getClass(), "@@@@ isRemove : " + isRemove);

                if ( ! isRemove ) {

                    dataCount++;
                    String itemCodeStr = rsComponent.getString("childItemCode") == null ? "" : rsComponent.getString("childItemCode");
                    String sortOrderStr = rsComponent.getString("sequenceNumber") == null ? "" : rsComponent.getString("sequenceNumber");

                    Vector subAssyVec = new Vector();

                    component = new BOMAssyComponent(itemCodeStr.trim());
                    component.setSeqInt(new Integer(dataCount));
                    component.setLevelInt(new Integer(rsComponent.getString("bomLevel") == null ? "1" : rsComponent.getString("bomLevel")));
                    component.setItemSeqInt(new Integer(rsComponent.getString("itemSeq") == null ? "10" : rsComponent.getString("itemSeq")));

                    // 기준수량
                    String beforeBoxQty = rsComponent.getString("beforeBoxQuantity") == null ? "1.0" : rsComponent.getString("beforeBoxQuantity");
                    if (beforeBoxQty.startsWith("."))
                        beforeBoxQty = "0" + beforeBoxQty;
                    component.setBeforeBoxQuantityDbl(new Double(beforeBoxQty));
                    String afterBoxqty = rsComponent.getString("afterBoxQuantity") == null ? "1.0" : rsComponent.getString("afterBoxQuantity");
                    if (afterBoxqty.startsWith("."))
                        afterBoxqty = "0" + afterBoxqty;
                    component.setBoxQuantityDbl(new Double(afterBoxqty));

                    // 소요수량
                    String beforeQty = rsComponent.getString("beforeQuantity") == null ? "1.000" : rsComponent.getString("beforeQuantity");
                    if (beforeQty.startsWith("."))
                        beforeQty = "0" + beforeQty;
                    component.setBeforeQuantityDbl(new Double(beforeQty));
                    String qty = rsComponent.getString("afterQuantity") == null ? "1.000" : rsComponent.getString("afterQuantity");
                    if (qty.startsWith("."))
                        qty = "0" + qty;
                    component.setQuantityDbl(new Double(qty));

                    // 추가 컬럼
                    component.setBeforeUnitStr(rsComponent.getString("beforeUnit") == null ? "" : rsComponent.getString("beforeUnit"));
                    component.setUitStr(rsComponent.getString("afterUnit") == null ? "" : rsComponent.getString("afterUnit"));
                    component.setBeforeMaterialStr(rsComponent.getString("beforeMaterial") == null ? "" : rsComponent.getString("beforeMaterial"));
                    component.setMaterialStr(rsComponent.getString("afterMaterial") == null ? "" : rsComponent.getString("afterMaterial"));
                    component.setBeforeHardnessFrom(rsComponent.getString("beforeHardnessFrom") == null ? "" : rsComponent.getString("beforeHardnessFrom"));
                    component.setHardnessFrom(rsComponent.getString("afterHardnessFrom") == null ? "" : rsComponent.getString("afterHardnessFrom"));
                    component.setBeforeHardnessTo(rsComponent.getString("beforeHardnessTo") == null ? "" : rsComponent.getString("beforeHardnessTo"));
                    component.setHardnessTo(rsComponent.getString("afterHardnessTo") == null ? "" : rsComponent.getString("afterHardnessTo"));
                    component.setBeforeDesignDate(rsComponent.getString("beforeDesignDate") == null ? "" : rsComponent.getString("beforeDesignDate"));
                    component.setDesignDate(rsComponent.getString("afterDesignDate") == null ? "" : rsComponent.getString("afterDesignDate"));

                    component.setBeforeStartDate(rsComponent.getString("beforeStartDate") == null ? "" : rsComponent.getString("beforeStartDate"));
                    component.setStartDate(rsComponent.getString("afterStartDate") == null ? "" : rsComponent.getString("afterStartDate"));
                    component.setBeforeEndDate(rsComponent.getString("beforeEndDate") == null ? "" : rsComponent.getString("beforeEndDate"));
                    component.setEndDate(rsComponent.getString("afterEndDate") == null ? "" : rsComponent.getString("afterEndDate"));

                    component.setEcoCodeStr(rsComponent.getString("ecoCode") == null ? "" : rsComponent.getString("ecoCode"));
                    component.setParentItemCodeStr(rsComponent.getString("parentItemCode") == null ? "" : rsComponent.getString("parentItemCode").toString().trim());
                    component.setSortOrderStr(sortOrderStr);

                    // BOM 리스트를 Component 에 담을 때 Item 속성도 쿼리해서 Component 에 담아준다.
                    try    {
                        stmtItemComponent = conn.createStatement();
                        query = "  SELECT  m.name descr                                                                                                \n    ";
                        query += "         ,    i.versionida2versioninfo ver                                                                                \n    ";
                        query += "         ,    i.iterationida2iterationinfo iter                                                                                \n    ";
                        query += "         ,    i.statestate status                                                                                            \n    ";
                        query += "         ,    m.defaultunit unit                                                                                            \n    ";
                        query += "         , i."  + PartSpecEnum.SpPartType.getColumnName() + " partType                                                                                                \n    ";
                        query += "         , i."  + PartSpecEnum.SpIsDeleted.getColumnName() + " isDeleted                                                                                            \n    ";
                        query += " FROM   wtpartmaster m                                                                                                \n    ";
                        query += "          , wtpart i                                                                                                        \n    ";
                        query += "          , ( SELECT  MAX(p2.versionsortida2versioninfo) objver, MAX(p2.ida2a2) objid                        \n    ";
                        query += "               FROM  WTPart p2, WTPartMaster m2                                                                \n    ";
                        query += "              WHERE  m2.wtpartnumber =  '" + itemCodeStr + "'                                                \n    ";
                        query += "                AND   p2.ida3masterreference = m2.ida2a2                                                        \n    ";
                        query += "                AND   p2.statecheckoutinfo <> 'wrk'                                                                \n    ";
                        query += "                AND   p2.latestiterationinfo = 1                                                                        \n    ";
                        query += "             GROUP BY m2.wtpartnumber ) x                                                                        \n    ";
                        query += "WHERE  i.ida3masterreference = m.ida2a2                                                                            \n    ";
                        query += "  AND   i.ida2a2 = x.objid                                                                                            \n    ";
                        query += "ORDER BY m.wtpartnumber                                                                                            \n";

                        rsItemComponent = stmtItemComponent.executeQuery(query);

                        itemVec.addElement(itemCodeStr);

                        if(rsItemComponent.next()) {
                            component.setDescStr(rsItemComponent.getString("descr") == null ? "" : rsItemComponent.getString("descr"));
                            component.setVersionStr((rsItemComponent.getString("ver") == null ? "" : rsItemComponent.getString("ver")) + "." + (rsItemComponent.getString("iter") == null ? "" : rsItemComponent.getString("iter")));
                            component.setStatusStr(rsItemComponent.getString("status") == null ? "" : rsItemComponent.getString("status"));
                            component.setStatusKrStr(KETMessageService.service.getString("e3ps.bom.bom_editor_message", "00251")/*승인완료*/);        // PLM에서 부품의 상태는 모두 승인완료임
                            component.setIsDeleted(rsItemComponent.getString("isDeleted") == null ? "" : rsItemComponent.getString("isDeleted"));
                            // Added by MJOH, 2011-04-07
                            component.setIBAPartType(rsItemComponent.getString("partType") == null ? "" : rsItemComponent.getString("partType"));
                        }
                    }
                    catch(Exception ex)
                    {
                        Kogger.error(getClass(), ex);
                    }
                    finally
                    {
                        if (rsItemComponent != null)
                        { rsItemComponent.close(); rsItemComponent.close();}

                        if (stmtItemComponent != null)
                        { stmtItemComponent.close(); stmtItemComponent.close();}
                    }

                    subAssyVec.removeAllElements();

                    for (int inx = 0; inx < vecAllSubAssy.size(); inx++) {
                        BOMSubAssyComponent tmpSubAssyComponent = (BOMSubAssyComponent)(vecAllSubAssy.elementAt(inx));
                        if (sortOrderStr.trim().equals(tmpSubAssyComponent.getSortOrderStr().trim()) && (!Utility.checkNVL(tmpSubAssyComponent.getEcoCodeStr()).equals("Remove"))) {
                            subAssyVec.addElement(tmpSubAssyComponent);
                        }
                    }

                    if (subAssyVec != null && subAssyVec.size() > 0) {
                        component.setSubAssyComponent(subAssyVec);
                    }
                    vecResult.addElement(component);
                }
            }

            Vector coworkerVec = new Vector();
            String resultItemCode = "";
            String checkOutItemCode = "";
            coworkerVec = KETBomHelper.service.getCheckOuter(itemVec);

Kogger.debug(getClass(), "===>> coworkerVec : " + coworkerVec.toString());
Kogger.debug(getClass(), "===>> vecResult : " + vecResult.size());

            if(coworkerVec != null && coworkerVec.size() > 0) {
                for(int i=0; i<vecResult.size(); i++) {
                    resultItemCode = vecResult.elementAt(i) == null ? "" : vecResult.elementAt(i).toString().trim();

Kogger.debug(getClass(), "===>> resultItemCode : " + resultItemCode);

                    for(int j=0; j<coworkerVec.size(); j++) {
                        checkOutItemCode = coworkerVec.elementAt(j) == null ? "" : coworkerVec.elementAt(j).toString().trim().substring(3, coworkerVec.elementAt(j).toString().trim().indexOf("|"));

Kogger.debug(getClass(), "===>> checkOutItemCode : " + checkOutItemCode);

                        if(resultItemCode.equals(checkOutItemCode)) {
                            BOMAssyComponent cmp = (BOMAssyComponent)vecResult.elementAt(i);
                            cmp.setCheckOutStr(coworkerVec.elementAt(j).toString().substring(coworkerVec.elementAt(j).toString().indexOf("|")+1));
                            vecResult.set(i, cmp);
                            break;
                        }
                    }
                }
            }
        } catch (Exception ee) {
            Kogger.error(getClass(), ee);
        } finally {
            try     {
                if (rsComponent != null)    { rsComponent.close(); rsComponent.close();}
                if (rsDesignator != null)    { rsDesignator.close(); rsDesignator.close();}
                if (rsSubstitute != null)        { rsSubstitute.close(); rsSubstitute.close();}

                if (stmtComponent != null)    { stmtComponent.close(); stmtComponent.close();}
                if (stmtDesignator != null)        { stmtDesignator.close(); stmtDesignator.close();}
                if (stmtSubstitute != null)         { stmtSubstitute.close(); stmtSubstitute.close();}
            } catch(Exception e)     {
                Kogger.error(getClass(), e);
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null) {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return vecResult;
    }


    // BOM Object Remove...
    public void removeBOM(String bomOid, String itemCode) throws Exception
    {
        String SQL = "";
        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL =  "DELETE FROM                                    ";
            SQL +=     "ketbomcomponent                            ";
            SQL += "WHERE                                                ";
            SQL +=     "newbomcode = '" + itemCode + "'        ";
            stmt.executeUpdate(SQL);

            SQL =  "DELETE FROM                                    ";
            SQL +=     "ketbomsubstitute                            ";
            SQL += "WHERE                                                ";
            SQL +=     "newbomcode = '" + itemCode + "'        ";
            stmt.executeUpdate(SQL);

            SQL =  "DELETE FROM                                    ";
            SQL +=     "ketbomcoworker                            ";
            SQL += "WHERE                                                ";
            SQL +=     "newbomcode = '" + itemCode + "'        ";
            stmt.executeUpdate(SQL);

            try {
                KETBomHelper.service.removeBom(bomOid);
                conn.commit();
            } catch (Exception e) {
                conn.rollback();
                Kogger.error(getClass(), e);
                throw e;
            } finally {
                try {
                    stmt.close();
                } catch(Exception e) {
                    MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                    mbox.setModal(true);
                    mbox.setVisible(true);
                } finally {
                    if(res != null) {
                        res.freeConnection(registry.getString("plm"), conn);
                    }
                }
            }
        } catch(Exception e) {
            Kogger.error(getClass(), e);
            throw e;
        }
    }

    // Coworker 의 이름(ID) 를 가져온다.
    public Vector getCoworkerData(String itemCode)
    {
        String SQL = "";
        Vector coworkerVec = new Vector();
        coworkerVec.removeAllElements();

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL =  " SELECT                                        ";
            SQL += " coworkername, coworkeremail        ";
            SQL += " FROM                                            ";
            SQL += " ketbomcoworker                            ";
            SQL += " WHERE                                            ";
            SQL += " newbomcode = '" + itemCode + "'    ";

            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                coworkerVec.addElement(rs.getString("coworkername").trim() + "(" + rs.getString("coworkeremail").trim() + ")");
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                stmt.close();
            }
            catch(Exception e)
            {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                if(res != null)
                {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return coworkerVec;
    }

    // BOM ECO 의 Coworker 이름(ID) 를 가져온다.
    public Vector getBOMEcoCoworkerData(String ecoHeaderNumber)
    {
        String SQL = "";
        Vector coworkerVec = new Vector();
        coworkerVec.removeAllElements();

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL =  " SELECT                                                            ";
            SQL += " coworkername, coworkeremail                            ";
            SQL += " FROM                                                                ";
            SQL += " ketbomecocoworker                                            ";
            SQL += " WHERE                                                                ";
            SQL += " ecoHeaderNumber = '" + ecoHeaderNumber + "'        ";

            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                coworkerVec.addElement(rs.getString("coworkername").trim() + "(" + rs.getString("coworkeremail").trim() + ")");
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                stmt.close();
            }
            catch(Exception e)
            {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                if(res != null)
                {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return coworkerVec;
    }

    // Coworker 의 이름(ID), Coworker 의 Status(Endworking 상태..) 를 가져온다.
    public Vector getCoworkerInfo(String itemCode)
    {
        String SQL = "";
        Vector coworkerVec = new Vector();

        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL =  " SELECT                                                        \n";
            SQL += " coworkername, coworkerid, endworkingflag         \n";
            SQL += " FROM                                                        \n    ";
            SQL += " ketbomcoworker                                        \n    ";
            SQL += " WHERE                                                    \n    ";
            SQL += " newbomcode = '" + itemCode + "'                    ";

            rs = stmt.executeQuery(SQL);

            while(rs.next()) {
                JCheckBox check = new JCheckBox(rs.getString("coworkername").trim() + "(" + rs.getString("coworkerid").trim() + ")", (rs.getString("endworkingflag") != null && rs.getString("endworkingflag").trim().equals("4")) ? true : false);
                coworkerVec.addElement(check);
            }
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try    {
                if(rs != null)
                    rs.close();
                stmt.close();
            } catch(Exception e)    {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null) {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return coworkerVec;
    }

    // Coworker 의 이름(ID), Coworker 의 Status(Endworking 상태..) 를 가져온다.
    public Vector getBOMEcoCoworkerInfo(String ecoItemCode)
    {
        String SQL = "";
        Vector coworkerVec = new Vector();

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL =  " SELECT      coworkername, coworkerid, endworkingflag        \n";
            SQL += " FROM      ketbomecocoworker                                    \n";
            SQL += "WHERE  ecoItemCode = '" + ecoItemCode + "'                \n";

            rs = stmt.executeQuery(SQL);

Kogger.debug(getClass(), "--->> getBOMEcoCoworkerInfo SQL : " + SQL);

            while(rs.next())
            {
                JCheckBox check = new JCheckBox(rs.getString("coworkername").trim() + "(" + rs.getString("coworkerid").trim() + ")", (rs.getString("endworkingflag") != null && rs.getString("endworkingflag").trim().equals("4")) ? true : false);
                coworkerVec.addElement(check);
            }
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try {
                if(rs != null)
                    rs.close();
                stmt.close();
            } catch(Exception e) {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null) {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return coworkerVec;
    }

    // Coworker 의 Status(Endworking 상태..) 를 가져온다.
    public String getMyStatus(String model, String userId)
    {
        String status = "";
        String SQL = "";

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL = " Select endworkingflag                                    \n";
            SQL += " From ketbomcoworker                                    \n";
            SQL += " Where newbomcode = '" + model.trim() + "'        \n";
            SQL += " and coworkerid = '" + userId.trim() + "'            \n";

            rs = stmt.executeQuery(SQL);

            while(rs.next())
            {
                status = rs.getString("endworkingflag").trim();
            }

            if((status.equals("") || status==null))
            {
                status = "2";  // Check-In
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                stmt.close();
            }
            catch(Exception e)
            {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                if(res != null)
                {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return status;
    }

    // BOMEco 의 Coworker Status(Endworking 상태..) 를 가져온다.
    public String getBOMEcoMyStatus(String ecoHeaderNumber, String userId)
    {
        String status = "";
        String SQL = "";

Kogger.debug(getClass(), "----->> ecoHeaderNumber : " + ecoHeaderNumber);
Kogger.debug(getClass(), "----->> userId : " + userId);

        try
        {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL = " Select endworkingflag                                                        \n";
            SQL += " From ketbomecocoworker                                                    \n";
            SQL += " Where ecoHeaderNumber = '" + ecoHeaderNumber.trim() + "'        \n";
            SQL += " and coworkerid = '" + userId.trim() + "'                                \n";

            rs = stmt.executeQuery(SQL);

Kogger.debug(getClass(), "SQL : " + SQL);

            while(rs.next())
            {
                status = rs.getString("endworkingflag").trim();
            }

            if((status.equals("") || status==null))
            {
                status = "2";  // Check-In
            }
        }
        catch(Exception e)
        {
            Kogger.error(getClass(), e);
        }
        finally
        {
            try
            {
                if(rs != null)
                    rs.close();
                stmt.close();
            }
            catch(Exception e)
            {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            }
            finally
            {
                if(res != null)
                {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return status;
    }

    // Added by MJOH, 2007-04-19
    public void removeOrderBOMECO(String ecoHeaderNumber, String ecoItemCode) throws Exception
    {
        String SQL = "";
        String ecoNumber = ecoHeaderNumber.substring( 0, ecoHeaderNumber.indexOf("-") );
        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));
            conn.setAutoCommit(false);

            stmt = conn.createStatement();

            SQL =  " DELETE FROM    ketbomecocomponent                                \n";
            SQL += "WHERE EcoHeaderNumber = '" + ecoHeaderNumber + "'        \n";
            SQL += "  AND  EcoItemCode = '" + ecoItemCode + "'                        \n";
            stmt.executeUpdate(SQL);

            SQL =  "DELETE FROM    ketbomecotempcomponent                            \n";
            SQL += "WHERE EcoHeaderNumber = '" + ecoHeaderNumber + "'        \n";
            SQL += "  AND  EcoItemCode = '" + ecoItemCode + "'                        \n";
            stmt.executeUpdate(SQL);

            SQL =  "DELETE FROM     ketbomecocoworker                                \n";
            SQL += "WHERE EcoHeaderNumber = '" + ecoHeaderNumber + "'        \n";
            SQL += "  AND  EcoItemCode = '" + ecoItemCode + "'                        \n";
            stmt.executeUpdate(SQL);

            conn.commit();
        } catch(Exception e) {
            conn.rollback();
            Kogger.error(getClass(), e);
            throw e;
        } finally {
            try    {
                stmt.close();
            } catch(Exception e)    {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null){
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
    }

    // 로그인한 사용자가 작성자이거나, 공동작업자에 포함된 신규 BOM 정보를 가져온다.
    public Vector getMyBOMList(String strLoginUserId, String strUserOid) {
        String SQL = "";
        Vector bomCodeVec = new Vector();

        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            SQL =   "SELECT  distinct head.newbomcode                                                                 \n";
            SQL += " FROM  ketbomheader head                                                                        \n";
            SQL += "WHERE  head.newbomcode IN ( SELECT  distinct co.newbomcode                            \n";
            SQL += "                                         FROM  ketbomcoworker co                                \n";
            SQL += "                                        WHERE  co.coworkerid = '" + strLoginUserId + "' )    \n";
Kogger.debug(getClass(), ">>>> SQL [getMyBOMList] : " +  SQL);

            rs = stmt.executeQuery(SQL);

            while(rs.next()) {
                bomCodeVec.addElement(rs.getString("newbomcode"));
            }
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try    {
                if(rs != null)
                    rs.close();
                stmt.close();
            } catch(Exception e)    {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null)
                {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return bomCodeVec;
    }

    // 로그인한 사용자가 공동작업자에 포함된(ECO 활동수행자임) 변경 BOM 정보를 가져온다.
    public ArrayList<Hashtable> getMyBOMEcoList(String strLoginUserId, String strUserOid) {
        String SQL = "";
        ArrayList<Hashtable> rtnArray = new ArrayList<Hashtable>();

        try {
            res = DBConnectionManager.getInstance();
            conn = res.getConnection(registry.getString("plm"));

            stmt = conn.createStatement();

            // ECO 상태가 활동수행, 재작업이면서 활동수행자로 등록된 BomEcoHeader 의 ecoItemCode와 ecoHeaderNo를 가져온다
            SQL =   " SELECT    h.ecoitemcode    , h.ecoheadernumber                                                \n";
            SQL += "  FROM    ketbomecoheader h                                                                    \n";
            SQL += "            , ketprodchangeorder c                                                                    \n";
            SQL += " WHERE    h.ecoheadernumber = c.ecoid                                                        \n";
            SQL += "    AND    h.ATTRIBUTE1 = 'Y'                                                                     \n";
            SQL += "    AND  ( c.statestate = 'EXCUTEACTIVITY'                                                         \n";
            SQL += "      OR    c.statestate = 'REWORK' )                                                             \n";
            SQL += "  AND      h.ecoitemcode IN ( SELECT  distinct co.ecoitemcode                                 \n";
            SQL += "                                        FROM  ketbomecocoworker co                            \n";
            SQL += "                                       WHERE  co.coworkerid = '" + strLoginUserId + "' )        \n";
            SQL += "  UNION                                                                                                \n";
            SQL += " SELECT    h.ecoitemcode    , h.ecoheadernumber                                                \n";
            SQL += "  FROM    ketbomecoheader h                                                                    \n";
            SQL += "            , ketmoldchangeorder c                                                                \n";
            SQL += " WHERE    h.ecoheadernumber = c.ecoid                                                        \n";
            SQL += "    AND    h.ATTRIBUTE1 = 'Y'                                                                     \n";
            SQL += "    AND  ( c.statestate = 'EXCUTEACTIVITY'                                                         \n";
            SQL += "      OR    c.statestate = 'REWORK' )                                                             \n";
            SQL += "  AND      h.ecoitemcode IN ( SELECT  distinct co.ecoitemcode                                 \n";
            SQL += "                                        FROM  ketbomecocoworker co                            \n";
            SQL += "                                       WHERE  co.coworkerid = '" + strLoginUserId + "' )        \n";

Kogger.debug(getClass(), ">>>> SQL [getMyBOMEcoList] : " + SQL);

            rs = stmt.executeQuery(SQL);
            while(rs.next()) {
                Hashtable bomCodeVec = new Hashtable();
                bomCodeVec.put("ecoitemcode", rs.getString("ecoitemcode"));
                bomCodeVec.put("ecoheadernumber", rs.getString("ecoheadernumber"));

                rtnArray.add(bomCodeVec);
            }
        } catch(Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            try    {
                if(rs != null)
                    rs.close();
                stmt.close();
            } catch(Exception e)    {
                MessageBox mbox = new MessageBox(messageRegistry.getString("dbCloseError"), "Error", 0);
                mbox.setModal(true);
                mbox.setVisible(true);
            } finally {
                if(res != null)
                {
                    res.freeConnection(registry.getString("plm"), conn);
                }
            }
        }
        return rtnArray;
    }
}
