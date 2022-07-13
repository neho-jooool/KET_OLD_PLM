package e3ps.part.dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;

import wt.access.SecurityLabels;
import e3ps.common.query.LoggableStatement;
import e3ps.common.util.PlmDBUtil;
import e3ps.part.entity.KETNewPartList;

public class KETNewPartListDao {

    private Connection conn;

    public KETNewPartListDao(Connection conn){
        this.conn = conn;
    }

    public Hashtable getKETNewPartListDao(Hashtable arg)throws Exception{

        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        Hashtable<String, KETNewPartList> hash = new Hashtable<String, KETNewPartList>();

        sb = new StringBuffer();
        sb.append( "SELECT A.* 	                      				 									\n" );
        sb.append( "FROM ( 	                      						 								\n" );
        sb.append( "			SELECT 	B.*,	                      				 						\n" );
        sb.append( "		   			 	ROWNUM AS RNUM 	                      				 	\n" );
        sb.append( "			FROM( 		                      				 							\n" );
        sb.append( "						SELECT 	A.*,	                      				 			\n" );
        sb.append( "			   						A.CLASSNAMEA2A2 || ':' || A.IDA2A2 AS oid 	\n" );
        sb.append( "						FROM KETNewPartList A	                      				\n" );
        sb.append( "						WHERE	                      				 					\n" );
        sb.append( "			   					A.IDA3A5 =  ?                    				 		\n" );
        sb.append( "						ORDER BY A.PARTNUMBER DESC, A.REGDATE DESC, A.CREATESTAMPA2 DESC   \n" );
        sb.append( "					) B										 								\n" );
        sb.append( "			) A															 					\n" );
        sb.append( "	WHERE 								 												\n" );
        sb.append( "	  		A.RNUM BETWEEN ? AND ?	 											\n" );
        try{
            pstmt = new LoggableStatement( conn, sb.toString() );
            pstmt.setString( pstmtCnt++, (String)arg.get("spartListTypeCode2") ); // 구분
            pstmt.setString( pstmtCnt++, (String)arg.get("startRow") );
            pstmt.setString( pstmtCnt++, (String)arg.get("endRow") );
            rs = pstmt.executeQuery();

               //PersistenceHelper.manager.navigate(partListTypeCode2, "newpartlist", KETNewPartListTypeLink.class);
            int c = 0;
            while ( rs.next() ) {
                KETNewPartList ketNewPartList = new KETNewPartList();
                ketNewPartList.setPartNumber(rs.getString("PARTNUMBER"));
                ketNewPartList.setPartName( rs.getString("PARTNAME"));
                ketNewPartList.setRawMaterial( rs.getString("RAWMATERIAL"));
                ketNewPartList.setCustomer( rs.getString("CUSTOMER"));
                ketNewPartList.setModifier( rs.getString("MODIFIER"));
                ketNewPartList.setModDate(rs.getDate("MODDATE"));
                ketNewPartList.setRegDate(rs.getDate("REGDATE"));
                ketNewPartList.setRegister(rs.getString("REGISTER"));
                ketNewPartList.setDescription(rs.getString("DESCRIPTION"));
                ketNewPartList.setDel(rs.getBoolean("DEL"));
                ketNewPartList.setSecurityLabels(SecurityLabels.newSecurityLabels(rs.getString("oid")));
                
                hash.put(c+"", ketNewPartList);
                c =c+1;
                ketNewPartList = null;
           }
        }catch(Exception e){
            throw e;
        }finally{
            sb.delete( 0 , sb.length() );

            if( rs != null )
            {
                rs.close();
            }

            if( pstmt != null )
            {
                pstmt.close();
            }

            PlmDBUtil.close( conn );
        }

        return hash;
    }

    public int getPartNumberSize(String partNumber) throws Exception {
        int ire = 0;
        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;

        sb = new StringBuffer();
        sb.append( "SELECT A.*	                      				\n" );
        sb.append( "FROM KETNewPartList A 						\n" );
        sb.append( "WHERE											\n" );
        sb.append( "	 		A.PARTNUMBER = ?					\n" );
        try{
            pstmt = new LoggableStatement( conn, sb.toString() );
            pstmt.setString( pstmtCnt++, partNumber ); // part number

            rs = pstmt.executeQuery();
            while ( rs.next() ) {
                ire =ire+1;
            }
        }catch(Exception e){
            throw e;
        }finally{
            sb.delete( 0 , sb.length() );

            if( rs != null )
            {
                rs.close();
            }

            if( pstmt != null )
            {
                pstmt.close();
            }

            PlmDBUtil.close( conn );
        }
        return ire;
    }

public Hashtable getKETNewPartListPopDao(Hashtable arg) throws Exception{

        LoggableStatement pstmt = null;
        int pstmtCnt = 1;
        StringBuffer sb = null;
        ResultSet rs = null;
        Hashtable<String, KETNewPartList> hash = new Hashtable<String, KETNewPartList>();

        sb = new StringBuffer();
        sb.append( "SELECT A.*	                      																													\n" );
        sb.append( "FROM( 	                      				 																										\n" );
        sb.append( "			SELECT A.*, ROWNUM RNUM 	                      				 																	\n" );
        sb.append( "			FROM( 	                      				 																								\n" );
        sb.append( "					SELECT  	A.* 	                      				 																				\n" );
        sb.append( "								, A.CLASSNAMEA2A2 || ':' || A.IDA2A2 AS oid 	                      				 								\n" );
        sb.append( "					FROM KETNewPartList A 										 																	\n" );
        sb.append( "					WHERE															 																	\n" );
        sb.append( "	 		 				A.IDA3A5 = ?								 																				\n" );
        sb.append( "					AND	A.PARTNUMBER NOT IN(SELECT DOCUMENTNUMBER FROM EPMDocumentMaster)							\n" );
        sb.append( "					AND	A.DEL = 0								 																					\n" );
        sb.append( "					ORDER BY A.PARTNUMBER DESC, A.CREATESTAMPA2 DESC 																							\n" );
        sb.append( "					) A  																																	\n" );
        sb.append( "			) A																																				\n" );
        sb.append( "	WHERE  																																				\n" );
        sb.append( "			A.RNUM BETWEEN ? AND ?																												\n" );
        try{
            pstmt = new LoggableStatement( conn, sb.toString() );
            pstmt.setString( pstmtCnt++, (String)arg.get("spartListTypeCode2") ); // 구분
            pstmt.setString( pstmtCnt++, (String)arg.get("startRow") );
            pstmt.setString( pstmtCnt++, (String)arg.get("endRow") );
            rs = pstmt.executeQuery();
               //PersistenceHelper.manager.navigate(partListTypeCode2, "newpartlist", KETNewPartListTypeLink.class);
            int c = 0;
            while ( rs.next() ) {
               KETNewPartList ketNewPartList = new KETNewPartList();
               ketNewPartList.setPartNumber(rs.getString("PARTNUMBER"));
               ketNewPartList.setPartName( rs.getString("PARTNAME"));
               ketNewPartList.setRawMaterial( rs.getString("RAWMATERIAL"));
               ketNewPartList.setCustomer( rs.getString("CUSTOMER"));
               ketNewPartList.setModifier( rs.getString("MODIFIER"));
               ketNewPartList.setModDate(rs.getDate("MODDATE"));
               ketNewPartList.setRegDate(rs.getDate("REGDATE"));
               ketNewPartList.setRegister(rs.getString("REGISTER"));
               ketNewPartList.setDescription(rs.getString("DESCRIPTION"));
               ketNewPartList.setDel(rs.getBoolean("DEL"));
               ketNewPartList.setSecurityLabels(SecurityLabels.newSecurityLabels(rs.getString("oid")));

               hash.put(c+"", ketNewPartList);
               c =c+1;
               ketNewPartList = null;
            }
        }catch(Exception e){
            throw e;
        }finally{
            sb.delete( 0 , sb.length() );

            if( rs != null )
            {
                rs.close();
            }

            if( pstmt != null )
            {
                pstmt.close();
            }

            PlmDBUtil.close( conn );
        }

        return hash;
    }
}
