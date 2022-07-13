/**
 * 프로젝트 : 한국단자 PLM 구축 프로젝트
 * 파일명 : ProjectReportDao.java
 * 작성자 : 엄태훈
 * 작성일자 : 2011. 5. 3.
 */
package e3ps.project.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import e3ps.common.query.LoggableStatement;
import e3ps.common.util.PlmDBUtil;
import e3ps.common.util.StringUtil;
import ext.ket.shared.log.Kogger;

/**
 * 클래스명 : TaskReportDao
 * 설명 : 프로젝트 리포트 조회
 * 작성자 : 엄태훈
 * 작성일자 : 2011. 06. 21.
 */
public class TaskReportDao {

    private Connection conn;

    public TaskReportDao(Connection conn){
        this.conn = conn;
    }

    public TaskReportDao(){
        super();
    }

    /**
     * 함수명 : ViewProductProjectList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 21.
     */
    public ArrayList ViewProductProjectList ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> productProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> productProject = null;
        String dept = "";
        String dept1 = "";
        String dept2 = "";
        String dept3 = "";
        String taskUser = "";
        String year = "";
        String checkDept = "";
        String pTaskName = "";
        int years = 0;

        checkDept = (String)hash.get("checkDept");
        dept = (String)hash.get("dept") ;
        dept1 = (String)hash.get("dept1");
        dept2 = (String)hash.get("dept2");
        dept3 = (String)hash.get("dept3");
        taskUser = (String)hash.get("creator");
        taskUser = taskUser.substring(taskUser.indexOf(":") + 1 );
        pTaskName = (String)hash.get("pTaskName");
        year = (String)hash.get("year");

//		Kogger.debug(getClass(),  ">>>>>> dept           :::::::  " +  dept);
//		Kogger.debug(getClass(),  ">>>>>> dept 1        :::::::  " +  dept1);
//		Kogger.debug(getClass(),  ">>>>>> dept 2        :::::::  " +  dept2);
//		Kogger.debug(getClass(),  ">>>>>>  taskUser   :::::::  " +  taskUser);

        try {

            //Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( " WITH 																																														\n");
            sb.append( " NUMBERCODE_with as ( SELECT ida2a2, name FROM NUMBERCODE )																								\n");
            sb.append( " SELECT 'P' pjtType, n.name as name, DECODE(ts.taskstate, 5, '완료', '진행') as taskstate, count(ts.ida3b4) as count, pr.statestate												\n");
            sb.append( "         FROM PRODUCTPROJECT pr, 																																														\n");
            sb.append( "                    E3PSTASK ts, 																																																	\n");
            sb.append( "                    NUMBERCODE_with n, 																																																\n");
            sb.append( "                    TASKMEMBERLINK tml, 																																														\n");
            sb.append( "                    PROJECTMEMBERLINK pml, 																																												\n");
            sb.append( "                    WTUSER wu, 																																																		\n");
            sb.append( "                    PEOPLE pe, 																																																		\n");
            sb.append( "                    DEPARTMENT dp, 																																																\n");
            sb.append( "                    EXTENDSCHEDULEDATA ex 																																												\n");
            sb.append( "     WHERE ts.ida3b4 = pr.ida2a2 																																															\n");
            sb.append( "		    AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )					\n");
            sb.append( "		    AND pr.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM PRODUCTPROJECT GROUP BY ida3b8 )																					\n");
            sb.append( "          AND pr.ida3d9 = n.ida2a2 																																																\n");
            sb.append( "          AND ts.ida3a4 = ex.ida2a2 																																															\n");
            sb.append( "          AND ts.ida2a2 = tml.ida3a5(+) 																																														\n");
            sb.append( "          AND tml.ida3b5 = pml.ida2a2(+) 																																														\n");
            sb.append( "          AND pml.ida3b5 = wu.ida2a2(+) 																																														\n");
            sb.append( "          AND wu.ida2a2 = pe.ida3b4(+)  																																														\n");
            sb.append( "		    AND pe.ida3c4 = dp.ida2a2(+) 																																														\n");
            if(pTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+pTaskName+"%' 																																														\n");
            }else{}
            if(checkDept.length() > 0){
                if("C".equals(checkDept) ){
                    sb.append( "		    AND pr.teamtype like '자동차%' 																																													\n");
                }else if("E".equals(checkDept) ){
                    sb.append( "		    AND pr.teamtype like '전자%' 																																													\n");
                }else{}
            }else{}
            sb.append( "		    AND nvl(tml.taskmembertype,  0) in (0, 1)																																										\n");
            sb.append("	  		AND pr.statestate not in ( 'STOPED','WITHDRAWN' ) 																																						\n");
            // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            // 사용자 조회
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																														\n");
            }else{	}
            // 년도 조회
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                                	 \n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																								             \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                        	 \n");
            }


            sb.append( "     GROUP BY n.name, DECODE(ts.taskstate, 5, '완료', '진행'), pr.statestate																																\n");
            sb.append( "UNION 																																																									\n");
            sb.append( " SELECT 'D' pjtType, n.name as name, t.taskstatestate as taskstate, count(t.taskstatestate) as count, t.statestate																		\n");
            sb.append( "         FROM (SELECT pr.ida3d9, ts.taskname, DECODE(ts.taskstate, 0, '지연') as taskstatestate, pr.statestate					\n");
            sb.append( "			  		 FROM PRODUCTPROJECT pr, 																																												\n");
            sb.append( "                     	  E3PSTASK ts, 																																																\n");
            sb.append( "                          TASKMEMBERLINK tml, 																																												\n");
            sb.append( "                          PROJECTMEMBERLINK pml, 																																										\n");
            sb.append( "                          WTUSER wu, 																																																\n");
            sb.append( "                          PEOPLE pe, 																																																\n");
            sb.append( "                          DEPARTMENT dp, 																																														\n");
            sb.append( "                    	  EXTENDSCHEDULEDATA ex 																																											\n");
            sb.append( "     			WHERE ts.ida3b4 = pr.ida2a2 																																													\n");
            sb.append( "		    		AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )			\n");
            sb.append( "		            AND pr.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM PRODUCTPROJECT GROUP BY ida3b8 )																			\n");
            sb.append( "				    AND ts.ida3a4 = ex.ida2a2 																																													\n");
            sb.append( "                  AND ts.ida2a2 = tml.ida3a5(+) 																																												\n");
            sb.append( "                  AND tml.ida3b5 = pml.ida2a2(+) 																																												\n");
            sb.append( "                  AND pml.ida3b5 = wu.ida2a2(+) 																																												\n");
            sb.append( "                  AND wu.ida2a2 = pe.ida3b4(+)  																																												\n");
            sb.append( "		          	AND pe.ida3c4 = dp.ida2a2(+) 																																												\n");
            sb.append("	  				AND pr.statestate not in ( 'STOPED','WITHDRAWN' ) 																																				\n");
            sb.append( "		            AND nvl(tml.taskmembertype,  0) in (0, 1)																																								\n");
            if(pTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+pTaskName+"%' 																																														\n");
            }else{}
            if(checkDept.length() > 0){
                if("C".equals(checkDept) ){
                    sb.append( "		    AND pr.teamtype like '자동차%' 																																													\n");
                }else if("E".equals(checkDept) ){
                    sb.append( "		    AND pr.teamtype like '전자%' 																																													\n");
                }else{}
            }else{}
            sb.append( "				  	AND ts.taskstate != 5  																																															\n");
            sb.append( "				  	AND pr.ida3d9 > 0  																																																\n");
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																														\n");
            }else{	}
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                                	 \n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																								             \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                        	 \n");
            }else{   }
            sb.append( "		AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD')															\n");
            sb.append( "				   ) t 																																																							\n");
            sb.append( "             ,NUMBERCODE_with n  																																																	\n");
            sb.append( "    WHERE t.ida3d9 = n.ida2a2  																																																\n");
            sb.append( "     GROUP BY n.name, t.taskstatestate, t.statestate 																																								\n");
            sb.append( "UNION 																																																									\n");
            sb.append( " SELECT 'R' pjtType, n.name as name, DECODE(ts.taskstate, 5, '완료', '진행') as taskstate, count(ts.ida3b4) as count,  rv.statestate												\n");
            sb.append( "         FROM REVIEWPROJECT rv, 																																															\n");
            sb.append( "                    E3PSTASK ts, 																																																	\n");
            sb.append( "                    NUMBERCODE_with n, 																																																\n");
            sb.append( "                    TASKMEMBERLINK tml, 																																														\n");
            sb.append( "                    PROJECTMEMBERLINK pml, 																																												\n");
            sb.append( "                    WTUSER wu, 																																																		\n");
            sb.append( "                    PEOPLE pe, 																																																		\n");
            sb.append( "                    DEPARTMENT dp, 																																																\n");
            sb.append( "                    EXTENDSCHEDULEDATA ex 																																												\n");
            sb.append( "     WHERE ts.ida3b4 = rv.ida2a2 																																																\n");
            sb.append( "		    AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )					\n");
            sb.append( "		    AND rv.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM REVIEWPROJECT GROUP BY ida3b8 )																						\n");
            sb.append( "          AND rv.ida3d9 = n.ida2a2 																																																\n");
            sb.append( "          AND ts.ida3a4 = ex.ida2a2 																																															\n");
            sb.append( "          AND ts.ida2a2 = tml.ida3a5(+) 																																														\n");
            sb.append( "          AND tml.ida3b5 = pml.ida2a2(+) 																																														\n");
            sb.append( "          AND pml.ida3b5 = wu.ida2a2(+) 																																														\n");
            sb.append( "          AND wu.ida2a2 = pe.ida3b4(+)  																																														\n");
            sb.append( "		  	AND pe.ida3c4 = dp.ida2a2(+) 																																														\n");
            if(pTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+pTaskName+"%' 																																														\n");
            }else{}
            if(checkDept.length() > 0){
                if("C".equals(checkDept) ){
                    sb.append( "		    AND rv.attr1 like '자동차%' 																																														\n");
                }else if("E".equals(checkDept) ){
                    sb.append( "		    AND rv.attr1 like '전자%' 																																														\n");
                }else{}
            }else{}
            sb.append( "		    AND nvl(tml.taskmembertype,  0) in (0, 1)																																										\n");
            sb.append("	  		AND rv.statestate not in ( 'STOPED','WITHDRAWN' ) 																																						\n");
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																														\n");
            }else{	}
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                      																                                                	\n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																								            \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                        	\n");
            }else{   }
            sb.append( "     GROUP BY n.name, DECODE(ts.taskstate, 5, '완료', '진행'), rv.statestate																																\n");
            sb.append( "UNION 																																																									\n");
            sb.append( " SELECT 'J' pjtType, n.name as name, t.taskstatestate as taskstate, count(t.taskstatestate) as count, t.statestate																			\n");
            sb.append( " 		FROM (SELECT rv.ida3d9, ts.taskname, DECODE(ts.taskstate, 0 , '지연') as taskstatestate, rv.statestate						\n");
            sb.append( "			  		 FROM REVIEWPROJECT rv, 																																												\n");
            sb.append( "                     	  E3PSTASK ts, 																																																\n");
            sb.append( "                          TASKMEMBERLINK tml, 																																												\n");
            sb.append( "                          PROJECTMEMBERLINK pml, 																																										\n");
            sb.append( "                          WTUSER wu, 																																																\n");
            sb.append( "                          PEOPLE pe, 																																																\n");
            sb.append( "                          DEPARTMENT dp, 																																														\n");
            sb.append( "                    	  EXTENDSCHEDULEDATA ex 																																											\n");
            sb.append( "     			WHERE ts.ida3b4 = rv.ida2a2 																																													\n");
            sb.append( "		    		AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )			\n");
            sb.append( "		    		AND rv.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM REVIEWPROJECT GROUP BY ida3b8 )																				\n");
            sb.append( "				 	AND ts.ida3a4 = ex.ida2a2 																																													\n");
            sb.append( "                  AND ts.ida2a2 = tml.ida3a5(+) 																																												\n");
            sb.append( "                  AND tml.ida3b5 = pml.ida2a2(+) 																																												\n");
            sb.append( "                  AND pml.ida3b5 = wu.ida2a2(+) 																																												\n");
            sb.append( "                  AND wu.ida2a2 = pe.ida3b4(+) 																																												\n");
            sb.append( "		          	AND pe.ida3c4 = dp.ida2a2(+)																																													\n");
            sb.append("	  				AND rv.statestate not in ( 'STOPED','WITHDRAWN' ) 																																				\n");
            sb.append( "		    	    AND nvl(tml.taskmembertype,  0) in (0, 1)																																								\n");
            if(pTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+pTaskName+"%' 																																														\n");
            }else{}
            if(checkDept.length() > 0){
                if("C".equals(checkDept) ){
                    sb.append( "		    AND rv.attr1 like '자동차%' 																																														\n");
                }else if("E".equals(checkDept) ){
                    sb.append( "		    AND rv.attr1 like '전자%' 																																														\n");
                }else{}
            }else{}
            sb.append( "				  	AND ts.taskstate != 5  																																															\n");
            sb.append( "				  	AND rv.ida3a9 > 0  																																																\n");
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                                	 \n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																								             \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                        	 \n");
            }else{   }
            sb.append( "		AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD'	)														\n");
            sb.append( "				   ) t 																																																							\n");
            sb.append( "             ,NUMBERCODE_with n  																																																	\n");
            sb.append( "    WHERE t.ida3d9 = n.ida2a2  																																																\n");
            sb.append( "     GROUP BY n.name, t.taskstatestate, t.statestate																																									\n");

            pstmt = new LoggableStatement( conn, sb.toString() );
            //Kogger.debug(getClass(), "---------------------- hooni 쿼리 \n"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                productProject = new Hashtable<String, String>();
                productProject.put("pjtType",   		StringUtil.checkNull(rs.getString(1))); // 개발, 검토 구분
                productProject.put("name",      		StringUtil.checkNull(rs.getString(2))); // 개발유형
                productProject.put("taskstate", 		StringUtil.checkNull(rs.getString(3))); // 진행상태
                productProject.put("count",     		StringUtil.checkNull(rs.getString(4))); // 건수
                productProject.put("statestate",     	StringUtil.checkNull(rs.getString(5))); // 프로젝트 진행 상태

                productProjectList.add(productProject);
            }

            //Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return productProjectList;
    }

    /**
     * 함수명 : ViewMoldProjectList
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 04.
     */
    public ArrayList ViewMoldProjectList ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> productProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> productProject = null;
        String dept = "";
        String dept1 = "";
        String dept2 = "";
        String dept3 = "";
        String taskUser = "";
        String year = "";
        String mTaskName = "";
        int years = 0;

        dept = (String)hash.get("dept") ;
        dept1 = (String)hash.get("dept1");
        dept2 = (String)hash.get("dept2");
        dept3 = (String)hash.get("dept3");
        taskUser = (String)hash.get("creator");
        taskUser = taskUser.substring(taskUser.indexOf(":") + 1 );
        year = (String)hash.get("year");
        mTaskName = (String)hash.get("mTaskName");

//		Kogger.debug(getClass(),  ">>>>>> dept           :::::::  " +  dept);
//		Kogger.debug(getClass(),  ">>>>>> dept 1        :::::::  " +  dept1);
//		Kogger.debug(getClass(),  ">>>>>> dept 2        :::::::  " +  dept2);
//		Kogger.debug(getClass(),  ">>>>>>  taskUser   :::::::  " +  taskUser);

        try {

            //Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append( "SELECT 'M' pjtType , mi.itemType, DECODE(ts.taskstate, '5', '완료', '진행') as taskstate, mi.making, n.name, count(mi.itemtype) as count, mp.statestate									\n");
            sb.append( "	  FROM MOLDPROJECT mp,  																																																				\n");
            sb.append( "	  	   E3PSTASK ts,  																																																								\n");
            sb.append( "         PRODUCTPROJECT pr,  																																																				\n");
            sb.append( "	  	   NUMBERCODE n,																																																							\n");
            sb.append( "	  	   MOLDITEMINFO mi, 																																																						\n");
            sb.append( "	  	   EXTENDSCHEDULEDATA ex, 																																																		\n");
            sb.append( "		   TASKMEMBERLINK tml, 																																																				\n");
            sb.append( "         PROJECTMEMBERLINK pml,  																																																		\n");
            sb.append( "		   WTUSER wu, 																																																								\n");
            sb.append( "		   PEOPLE pe,  																																																									\n");
            sb.append( "		   DEPARTMENT dp  																																																							\n");
            sb.append( "    WHERE ts.ida3b4 = mp.ida2a2 																																																			\n");
            sb.append( "		AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )										\n");
            sb.append( "      AND mp.ida2a2 in (SELECT max(ida2a2) as ida2a2 FROM MOLDPROJECT GROUP BY ida3b8 ) 																											\n");
            sb.append( "      AND mi.ida2a2 = mp.ida3a10 																																																				\n");
            sb.append( "      and mi.ida3a3 = pr.ida2a2 																																																					\n");
            sb.append( "      AND mi.ida3c3 = n.ida2a2 																																																					\n");
            sb.append( "      AND ts.ida3a4 = ex.ida2a2 																																																				\n");
            sb.append( "      AND ts.ida2a2 = tml.ida3a5(+) 																																																			\n");
            sb.append( "	    AND tml.ida3b5 = pml.ida2a2(+)  																																																		\n");
            sb.append( "	    AND pml.ida3b5 = wu.ida2a2(+) 																																																			\n");
            sb.append( "	    AND wu.ida2a2 = pe.ida3b4(+)  																																																			\n");
            sb.append( "		AND pe.ida3c4 = dp.ida2a2(+)																																																				\n");
            sb.append( "	    AND nvl(tml.taskmembertype,  0) in (0, 1)																																															\n");
            sb.append("	    AND mp.statestate not in ( 'STOPED','WITHDRAWN' ) 																																											\n");
            if(mTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+mTaskName+"%' 																																														\n");
            }else{}
            // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            // 사용자 조회
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																																		\n");
            }else{	}
            // 년도 조회
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                           					                     	\n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																								 				            \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                              					          	\n");
            }else{   }
            sb.append( "  GROUP BY mi.itemtype, DECODE(ts.taskstate, '5', '완료', '진행'), mi.making, n.name, mp.statestate																											\n");
            sb.append( "UNION 																																																													\n");
            sb.append( "SELECT 'O' pjtType, mi.itemType, DECODE(ts.taskstate, 0, '지연') as taskstatestate, mi.making, n.name, count(mi.itemtype) as count 	\n");
            sb.append( "	  , mp.statestate  																																																									\n");
            sb.append( "	  FROM MOLDPROJECT mp,  																																																				\n");
            sb.append( "	  	   E3PSTASK ts, 																																																								\n");
            sb.append( "         PRODUCTPROJECT pr,  																																																				\n");
            sb.append( "	  	   NUMBERCODE n, 																																																							\n");
            sb.append( "	  	   MOLDITEMINFO mi, 																																																						\n");
            sb.append( "	  	   EXTENDSCHEDULEDATA ex, 																																																		\n");
            sb.append( "		   TASKMEMBERLINK tml,  																																																				\n");
            sb.append( "         PROJECTMEMBERLINK pml,  																																																		\n");
            sb.append( "		   WTUSER wu, 																																																								\n");
            sb.append( "		   PEOPLE pe,  																																																									\n");
            sb.append( "		   DEPARTMENT dp  																																																							\n");
            sb.append( "    WHERE ts.ida3b4 = mp.ida2a2 																																																			\n");
            sb.append( "		AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )										\n");
            sb.append( "      AND mp.ida2a2 in (SELECT max(ida2a2) as ida2a2 FROM MOLDPROJECT GROUP BY ida3b8 ) 																											\n");
            sb.append( "      AND mi.ida2a2 = mp.ida3a10 																																																				\n");
            sb.append( "      and mi.ida3a3 = pr.ida2a2 																																																					\n");
            sb.append( "      AND mi.ida3c3 = n.ida2a2 																																																					\n");
            sb.append( "      AND ts.ida3a4 = ex.ida2a2 																																																				\n");
            sb.append( "      AND ts.ida2a2 = tml.ida3a5(+) 																																																			\n");
            sb.append( "	    AND tml.ida3b5 = pml.ida2a2(+)  																																																		\n");
            sb.append( "	    AND pml.ida3b5 = wu.ida2a2(+) 																																																			\n");
            sb.append( "	    AND wu.ida2a2 = pe.ida3b4(+)  																																																			\n");
            sb.append( "	    AND pe.ida3c4 = dp.ida2a2(+)																																																				\n");
            sb.append( "	    AND ts.taskstate = 0										 																																													\n");
            sb.append( "	    AND nvl(tml.taskmembertype,  0) in (0, 1)																																															\n");
            sb.append("	    AND mp.statestate not in ( 'STOPED','WITHDRAWN' ) 																																											\n");
            sb.append("       AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD')																\n");
            if(mTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+mTaskName+"%' 																																														\n");
            }else{}
            // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            // 사용자 조회
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																																		\n");
            }else{	}
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                            				    	\n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																								  					        \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                        	 				\n");
                sb.append( "	 AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD' )															\n");
            }else{   }
            sb.append( "    GROUP BY mi.itemtype, DECODE(ts.taskstate, 0, '지연'), mi.making, n.name, mp.statestate															\n");
Kogger.debug(getClass(), sb.toString());
            pstmt = new LoggableStatement( conn, sb.toString() );
            //Kogger.debug(getClass(), "----------------------쿼리 \n"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                productProject = new Hashtable<String, String>();
                productProject.put("pjtType",     	StringUtil.checkNull(rs.getString(1))); // 개발, 검토 구분
                productProject.put("itemType",   	StringUtil.checkNull(rs.getString(2))); // 제품프로젝트의 금형아이템 타입
                productProject.put("taskstate",  	StringUtil.checkNull(rs.getString(3))); // 진행상태
                productProject.put("making",     	StringUtil.checkNull(rs.getString(4))); // 제품프로젝트의 금형아이템 making
                productProject.put("name",			StringUtil.checkNull(rs.getString(5))); // 금형프로젝트의 코드 네임
                productProject.put("count",      	StringUtil.checkNull(rs.getString(6))); // 건수

                productProjectList.add(productProject);
            }

            //Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return productProjectList;
    }

    /**
     * 함수명 : ViewReportList2
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 21.
     */
    public ArrayList ViewReportList2 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> productProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> productTask = null;

        String cmd = (String)hash.get("cmd");
        String devType = (String)hash.get("devType");
        String devName = "";
        String statestate = (String)hash.get("statestate");
        String year = (String)hash.get("year");
        String checkDept = (String)hash.get("checkDept");
        String pTaskName = (String)hash.get("pTaskName");
        int years = 0;

        String dept = (String)hash.get("dept") ;
        String dept1 = (String)hash.get("dept1");
        String dept2 = (String)hash.get("dept2");
        String dept3 = (String)hash.get("dept3");
        Kogger.debug(getClass(), "뎁트3===>"+dept3);
        String taskUser = (String)hash.get("creator");
        taskUser = taskUser.substring(taskUser.indexOf(":") + 1 );

        String sort = (String)hash.get("sort");

        if( devType.equals("DEV001") ){
            devName = "전략개발";
        }else if( devType.equals("DEV002") ){
            devName = "수주개발";
        }else if( devType.equals("DEV003") ){
            devName = "연구개발";
        }else if(  devType.equals("DEV004") ) {
            devName = "추가금형";
        }else{
            devName = "";
        }

        int devState = 0;
        if( statestate.equals("C") ){
            devState = 5; // 완료
        }else if( statestate.equals("P") || statestate.equals("D") ){
            devState = 0; // 진행
        }

        try {

            sb = new StringBuffer();
            sb.append("with \n");
            if(devType.length() > 0 ){
                sb.append("NUMBERCODE_with as (SELECT name, ida2a2 FROM NUMBERCODE WHERE name like '"+devName+"')  \n");
            }else{
                sb.append("NUMBERCODE_with as (SELECT name, ida2a2 FROM NUMBERCODE )  \n");
            }

            sb.append("\n");
            sb.append(" SELECT n.name, ep.pjtno,  																																																\n");
            sb.append("            ep.pjtname, ts.taskstate, ts.taskname, ts.ida2a2, 																																									\n");
            sb.append("            dp.name as departName, pe.name as userName, 																																					\n");
            sb.append("            ex.planenddate, DECODE(ts.taskstate, '5', ex.execenddate, '') as execenddate, 																										\n");
            sb.append("            ts.classnamea2a2||':'||ts.ida2a2 as pjtLink, ex.execstartdate, 																																	\n");
            sb.append("            ts.taskdesc, ts.taskcompletion, 																										                                                                    \n");
            sb.append("            decode((select count(*) from PROJECTISSUE pi where pi.ida3b4 = ts.ida2a2),0,null,'등록') as issue_yn																        \n");
            sb.append("     FROM 					 																																																	\n");
            if( cmd.equals("reportList2")  ||  cmd.equals("reportList2Excel") ){
                sb.append("     	 PRODUCTPROJECT pr, 																																														\n");
            }else{
                sb.append("     	 REVIEWPROJECT pr,	 																																														\n");
            }
            sb.append(" 		 E3PSPROJECTMASTER ep,  																																													\n");
            sb.append("    		 E3PSTASK ts, 																																																		\n");
            sb.append(" 		 TASKMEMBERLINK tml, 																																															\n");
            sb.append(" 		 PROJECTMEMBERLINK pml, 																																													\n");
            sb.append(" 		 WTUSER wu, 																																																			\n");
            sb.append(" 		 PEOPLE pe, 																																																			\n");
            sb.append(" 		 DEPARTMENT dp,  																																																	\n");
            sb.append(" 		 NUMBERCODE_with n, 																																															\n");
            sb.append(" 		 EXTENDSCHEDULEDATA ex 																																													\n");
            sb.append("   WHERE ts.ida3b4 = pr.ida2a2 																																														\n");
            sb.append( "		    AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )			\n");
            if( cmd.equals("reportList2") ||  cmd.equals("reportList2Excel")){
                sb.append( "		    AND pr.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM PRODUCTPROJECT GROUP BY ida3b8 )																		\n");
            }else{
                sb.append( "		    AND pr.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM REVIEWPROJECT GROUP BY ida3b8 )																			\n");
            }
            sb.append("   	AND pr.ida3b8 = ep.ida2a2 																																														\n");
            sb.append("   	AND pr.ida3d9 = n.ida2a2 																																															\n");
            sb.append(" 	    AND ts.ida3a4 = ex.ida2a2 																																														\n");
            sb.append(" 	    AND ts.ida2a2 = tml.ida3a5(+) 																																													\n");
            sb.append(" 	    AND tml.ida3b5 = pml.ida2a2(+) 																																													\n");
            sb.append(" 	    AND pml.ida3b5 = wu.ida2a2(+) 																																													\n");
            sb.append(" 	    AND wu.ida2a2 = pe.ida3b4(+) 																																													\n");
            sb.append(" 	    AND pe.ida3c4 = dp.ida2a2(+) 																																													\n");
            sb.append("	    AND nvl(tml.taskmembertype,  0) in (0, 1)																																									\n");
            sb.append("	    AND pr.statestate not in ( 'STOPED','WITHDRAWN' ) 																																					\n");
            if(pTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+pTaskName+"%' 																																						\n");
            }else{}
            if(checkDept.length() > 0){
                if( cmd.equals("reportList2")  ||  cmd.equals("reportList2Excel") ) {
                    if("C".equals(checkDept) ){
                        sb.append( "		    AND pr.teamtype like '자동차%' 																																									\n");
                    }else if("E".equals(checkDept) ){
                        sb.append( "		    AND pr.teamtype like '전자%' 																																										\n");
                    }else{}
                }else{
                    if("C".equals(checkDept) ){
                        sb.append( "		    AND pr.attr1 like '자동차%' 																																											\n");
                    }else if("E".equals(checkDept) ){
                        sb.append( "		    AND pr.attr1 like '전자%' 																																											\n");
                    }else{}
                }

            }else{}
            // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            // 사용자 조회
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																												\n");
            }else{	}
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                            \n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																						            \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                    \n");
            }else{   }
            if(statestate.length() > 0.){
                sb.append(" 	AND ts.taskstate = "+devState+"																																												\n");
                if( statestate.equals("P") ){
                }else if( statestate.equals("D") ){
                    sb.append("     AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD')	 																																											\n");
                    sb.append("	  AND pr.statestate not in ( 'PMOINWORK', 'DEVASSIGN' )																																		\n");
                }else{}
            }else{}
            if( statestate.length() > 0 ){
                if( "P".equals(statestate) ){
                    sb.append(" UNION 							  																																														\n");
                    sb.append(" SELECT n.name, ep.pjtno,  																																														\n");
                    sb.append("            ep.pjtname, ts.taskstate, ts.taskname, ts.ida2a2, 																																							\n");
                    sb.append("            dp.name as departName, pe.name as userName, 																																			\n");
                    sb.append("            ex.planenddate, DECODE(ts.taskstate, '5', ex.execenddate, '') as execenddate, 																								\n");
                    sb.append("            ts.classnamea2a2||':'||ts.ida2a2 as pjtLink, ex.execstartdate, 																															\n");
                    sb.append("            ts.taskdesc, ts.taskcompletion, 																										                                                            \n");
                    sb.append("            decode((select count(*) from PROJECTISSUE pi where pi.ida3b4 = ts.ida2a2),0,null,'등록') as issue_yn														        \n");
                    sb.append("     FROM 					 																																															\n");
                    if( cmd.equals("reportList2")  ||  cmd.equals("reportList2Excel") ){
                        sb.append("     	 PRODUCTPROJECT pr, 																																												\n");
                    }else{
                        sb.append("     	 REVIEWPROJECT pr,	 																																												\n");
                    }
                    sb.append(" 		 E3PSPROJECTMASTER ep,  																																											\n");
                    sb.append("    		 E3PSTASK ts, 																																																\n");
                    sb.append(" 		 TASKMEMBERLINK tml, 																																													\n");
                    sb.append(" 		 PROJECTMEMBERLINK pml, 																																											\n");
                    sb.append(" 		 WTUSER wu, 																																																	\n");
                    sb.append(" 		 PEOPLE pe, 																																																	\n");
                    sb.append(" 		 DEPARTMENT dp,  																																															\n");
                    sb.append(" 		 NUMBERCODE_with n, 																																													\n");
                    sb.append(" 		 EXTENDSCHEDULEDATA ex 																																											\n");
                    sb.append("   WHERE ts.ida3b4 = pr.ida2a2 																																												\n");
                    sb.append( "		    AND ts.ida2a2 not in ( SELECT ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  )	\n");
                    if( cmd.equals("reportList2") ||  cmd.equals("reportList2Excel")){
                        sb.append( "		    AND pr.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM PRODUCTPROJECT GROUP BY ida3b8 )																\n");
                    }else{
                        sb.append( "		    AND pr.ida2a2 in ( SELECT max(ida2a2) as ida2a2 FROM REVIEWPROJECT GROUP BY ida3b8 )																	\n");
                    }
                    sb.append("   	AND pr.ida3b8 = ep.ida2a2 																																												\n");
                    sb.append("   	AND pr.ida3d9 = n.ida2a2 																																													\n");
                    sb.append(" 	    AND ts.ida3a4 = ex.ida2a2 																																												\n");
                    sb.append(" 	    AND ts.ida2a2 = tml.ida3a5(+) 																																											\n");
                    sb.append(" 	    AND tml.ida3b5 = pml.ida2a2(+) 																																											\n");
                    sb.append(" 	    AND pml.ida3b5 = wu.ida2a2(+) 																																											\n");
                    sb.append(" 	    AND wu.ida2a2 = pe.ida3b4(+) 																																											\n");
                    sb.append(" 	    AND pe.ida3c4 = dp.ida2a2(+) 																																											\n");
                    sb.append("	    AND nvl(tml.taskmembertype,  0) in (0, 1)																																							\n");
                    sb.append("	    AND pr.statestate not in ( 'STOPED','WITHDRAWN' ) 																																			\n");
                    if(pTaskName.length() > 0){
                        sb.append( "		    AND ts.taskname like '%"+pTaskName+"%' 																																				\n");
                    }else{}
                    if(checkDept.length() > 0){
                        if("C".equals(checkDept) ){
                            sb.append( "		    AND pr.teamtype like '자동차%' 																																								\n");
                        }else if("E".equals(checkDept) ){
                            sb.append( "		    AND pr.teamtype like '전자%' 																																									\n");
                        }else{}
                    }else{}
                    // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
                    if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                        if( isParentCnt(dept3) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                        }
                    } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                        if( isParentCnt(dept2) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                        }
                    } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                        if( isParentCnt(dept1) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                        }
                    } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                        if( isParentCnt(dept) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                        }
                    }else{	}
                    // 사용자 조회
                    if( taskUser.length() > 0 ){
                        sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																										\n");
                    }else{	}
                    if (year.length() > 0){
                        years =  Integer.parseInt((String)hash.get("year"))+1;
                        sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                    \n");
                        sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																						    \n");
                        sb.append( "         OR ex.execenddate is null)                                                                																                                            \n");
                    }else{   }
                    if(statestate.length() > 0.){
                        sb.append(" 	AND ts.taskstate = "+devState+"																																										\n");
                        sb.append("     AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD')	 																																											\n");
                        sb.append("	  AND pr.statestate in ( 'PMOINWORK', 'DEVASSIGN' )																																		\n");
                        }else{}
                    }else{}
                }else{}

            if ( sort.length() > 0 ){
                sb.append("  ORDER BY "+sort+"  																																																	\n");
            }else{
                sb.append("  ORDER BY 2  																																																			\n");
            }

            pstmt = new LoggableStatement( conn, sb.toString() );
            //Kogger.debug(getClass(), "----------------------쿼리 \n"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                productTask = new Hashtable<String, String>();

                productTask.put("name",        			StringUtil.checkNull(rs.getString(1)) ); // 개발유형
                productTask.put("pjtno",       			StringUtil.checkNull(rs.getString(2)) ); // 프로젝트 no
                productTask.put("pjtname",     		StringUtil.checkNull(rs.getString(3)) ); // 프로젝트명
                productTask.put("taskstate",   		StringUtil.checkNull(rs.getString(4)) ); // task 상태
                productTask.put("taskname",    		StringUtil.checkNull(rs.getString(5)) ); // task 명
                productTask.put("taskoid", 		   		StringUtil.checkNull(rs.getString(6)) ); // taskoid
                productTask.put("departName",  		StringUtil.checkNull(rs.getString(7)) ); // 주관부서
                productTask.put("userName",    		StringUtil.checkNull(rs.getString(8)) ); // 담당자
                productTask.put("planenddate", 		StringUtil.checkNull(rs.getString(9)) ); // task 계획완료일
                productTask.put("execenddate", 		StringUtil.checkNull(rs.getString(10))); // task 실제완료일
                productTask.put("pjtLink",    			StringUtil.checkNull(rs.getString(11)) ); // 프로젝트링크 ida2a2
                productTask.put("execstartdate",   	StringUtil.checkNull(rs.getString(12)) ); // task 실제시작일
                productTask.put("taskdesc",   	    StringUtil.checkNull(rs.getString(13)) ); // task 설명
                productTask.put("taskcompletion",  StringUtil.checkNull(rs.getString(14)) ); // task 완료율
                productTask.put("issue_yn",   	        StringUtil.checkNull(rs.getString(15)) ); // issue 등록여부


                productProjectList.add(productTask);
            }

            //Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return productProjectList;
    }

    /**
     * 함수명 : ViewReportList4
     * 설명 :
     * @param hash
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 07. 05.
     */
    public ArrayList ViewReportList4 ( Hashtable hash ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> moldProjectList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> moldProject = null;

        String cmd = (String)hash.get("cmd");
        String devType = (String)hash.get("devType");
        String devName = "";
        String statestate = (String)hash.get("statestate");
        String year = (String)hash.get("year");
        int years = 0;
        String mTaskName = (String)hash.get("mTaskName");


        String itemType = (String)hash.get("itemType");
        String making = (String)hash.get("making");
        String moldType1 = (String)hash.get("moldType1");
        String moldType2 = (String)hash.get("moldType2");

        String dept = (String)hash.get("dept") ;
        String dept1 = (String)hash.get("dept1");
        String dept2 = (String)hash.get("dept2");
        String dept3 = (String)hash.get("dept3");
        String taskUser = (String)hash.get("creator");
        taskUser = taskUser.substring(taskUser.indexOf(":") + 1 );

        String sort = (String)hash.get("sort");
        try {
            sb = new StringBuffer();
            sb.append( " with  																																																									\n");
            sb.append( " NUMBERCODE_with as ( SELECT name, code, ida2a2 FROM NUMBERCODE 																														\n");
            if( moldType1.length() > 0 && moldType2.length() > 0 ){
                sb.append( "	     WHERE code in ('" + moldType1 + "','" + moldType2 + "')																																				\n");
            }else if( moldType1.length() > 0 && moldType2.length() == 0 ){
                sb.append( "	     WHERE code = '" + moldType1 + "'																																											\n");
            }else{}
            sb.append( " )   \n");
            sb.append( " SELECT n.name, ep.pjtno, ep.pjtname, ts.taskstate, ts.taskname, dp.name as departName, pe.name as userName,  ts.ida2a2, 																\n");
            sb.append("                ex.planenddate, DECODE(ts.taskstate, '5', ex.execenddate, '') as execenddate, ts.classnamea2a2||':'||ts.ida2a2 as pjtLink, mi.itemtype	,						\n");
            sb.append("                ts.taskdesc, ts.taskcompletion, 																										                                                                    \n");
            sb.append("                decode((select count(*) from PROJECTISSUE pi where pi.ida3b4 = ts.ida2a2),0,null,'등록') as issue_yn																        \n");
            sb.append( " 	  FROM MOLDPROJECT mp, 																																															\n");
            sb.append( " 	  	   E3PSTASK ts, 																																																			\n");
            sb.append( " 	  	   E3PSPROJECTMASTER ep, 																																														\n");
            sb.append( "         PRODUCTPROJECT pr, 																																																\n");
            sb.append( " 	  	   NUMBERCODE_with n, 																																																\n");
            sb.append( " 	  	   MOLDITEMINFO mi, 																																																	\n");
            sb.append( " 	  	   EXTENDSCHEDULEDATA ex, 																																													\n");
            sb.append( " 		   TASKMEMBERLINK tml, 																																															\n");
            sb.append( "         PROJECTMEMBERLINK pml, 																																														\n");
            sb.append( " 		   WTUSER wu,							 																																													\n");
            sb.append( " 		   PEOPLE pe, 																																																				\n");
            sb.append( " 		   DEPARTMENT dp 																																																		\n");
            sb.append( "     WHERE ts.ida3b4 = mp.ida2a2 																																														\n");
            sb.append( "       AND ts.ida2a2 not in ( SELECT  ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  ) 				\n");
            sb.append( "       AND mp.ida2a2 in (SELECT max(ida2a2) as ida2a2 FROM MOLDPROJECT GROUP BY ida3b8 ) 																						\n");
            sb.append( "       AND mp.ida3b8 = ep.ida2a2 																																															\n");
            sb.append( "       AND mi.ida2a2 = mp.ida3a10 																																														\n");
            sb.append( "       AND mi.ida3a3 = pr.ida2a2	 																																														\n");
            sb.append( "       AND mi.ida3c3 = n.ida2a2 																																															\n");
            sb.append( "       AND ts.ida3a4 = ex.ida2a2 																																															\n");
            sb.append( "       AND ts.ida2a2 = tml.ida3a5(+) 																																														\n");
            sb.append( "	     AND tml.ida3b5 = pml.ida2a2(+) 																																													\n");
            sb.append( "	     AND pml.ida3b5 = wu.ida2a2(+) 																																													\n");
            sb.append( "	     AND wu.ida2a2 = pe.ida3b4(+) 																																														\n");
            sb.append( "	     AND pe.ida3c4 = dp.ida2a2(+) 																																														\n");
            sb.append( "	     AND nvl(tml.taskmembertype,  0) in (0, 1)																																										\n");
            if(mTaskName.length() > 0){
                sb.append( "		    AND ts.taskname like '%"+mTaskName+"%' 																																							\n");
            }else{}
            // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
            if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                if( isParentCnt(dept3) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                }
            } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                if( isParentCnt(dept2) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                }
            } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept1) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                }
            } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                if( isParentCnt(dept) > 0 ) {
                    sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                }else{
                    sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                }
            }else{	}
            // 사용자 조회
            if( taskUser.length() > 0 ){
                sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																													\n");
            }else{	}
            // 년도 조회
            if (year.length() > 0){
                years =  Integer.parseInt((String)hash.get("year"))+1;
                sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                                \n");
                sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																							            \n");
                sb.append( "         OR ex.execenddate is null)                                                                																                                                        \n");
            }else{   }
            if(statestate.length() > 0){
                if( "C".equals(statestate) ){
                    sb.append( "	     AND ts.taskstate = 5 																																															\n");
                    sb.append("	    AND mp.statestate not in ( 'STOPED','WITHDRAWN' )																																				\n");
                }else {
                    if( "P".equals(statestate) ){
                        sb.append( "	     AND ts.taskstate = 0 																																														\n");
                        sb.append("	    AND mp.statestate not in ( 'STOPED','WITHDRAWN' )																																			\n");
                    }else if( "D".equals(statestate) ) {
                        sb.append( "	     AND ts.taskstate = 0 																																														\n");
                        sb.append("       AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD')																																											\n");
                        sb.append("	    AND mp.statestate not in ( 'STOPED','WITHDRAWN', 'PMOINWORK', 'DEVASSIGN' )																							\n");
                    }else{}
                }
            }else{}

            if(itemType.length() > 0){
                sb.append( "	     AND mi.itemtype = '"+ itemType +"' 																																											\n");
            }else{}

            if( making.length() > 0 ){
                sb.append( "	     AND mi.making = '"+ making +"' 																																												\n");
            }else{}

            if(statestate.length() > 0){
                if("P".equals(statestate)){
                    sb.append( " UNION 																																								 																\n");
                    sb.append( " SELECT n.name, ep.pjtno, ep.pjtname, ts.taskstate, ts.taskname, dp.name as departName, pe.name as userName,  ts.ida2a2, 																\n");
                    sb.append("                ex.planenddate, DECODE(ts.taskstate, '5', ex.execenddate, '') as execenddate, ts.classnamea2a2||':'||ts.ida2a2 as pjtLink, mi.itemtype	,						\n");
                    sb.append("                ts.taskdesc, ts.taskcompletion, 																										                                                                    \n");
                    sb.append("                decode((select count(*) from PROJECTISSUE pi where pi.ida3b4 = ts.ida2a2),0,null,'등록') as issue_yn																        \n");
                    sb.append( " 	  FROM MOLDPROJECT mp, 																																															\n");
                    sb.append( " 	  	   E3PSTASK ts, 																																																			\n");
                    sb.append( " 	  	   E3PSPROJECTMASTER ep, 																																														\n");
                    sb.append( "         PRODUCTPROJECT pr, 																																																\n");
                    sb.append( " 	  	   NUMBERCODE_with n, 																																																\n");
                    sb.append( " 	  	   MOLDITEMINFO mi, 																																																	\n");
                    sb.append( " 	  	   EXTENDSCHEDULEDATA ex, 																																													\n");
                    sb.append( " 		   TASKMEMBERLINK tml, 																																															\n");
                    sb.append( "         PROJECTMEMBERLINK pml, 																																														\n");
                    sb.append( " 		   WTUSER wu,							 																																													\n");
                    sb.append( " 		   PEOPLE pe, 																																																				\n");
                    sb.append( " 		   DEPARTMENT dp 																																																		\n");
                    sb.append( "     WHERE ts.ida3b4 = mp.ida2a2 																																														\n");
                    sb.append( "       AND ts.ida2a2 not in ( SELECT  ida3parentreference FROM E3PSTASK WHERE ida2a2 != ida3parentreference GROUP BY ida3parentreference  ) 				\n");
                    sb.append( "       AND mp.ida2a2 in (SELECT max(ida2a2) as ida2a2 FROM MOLDPROJECT GROUP BY ida3b8 ) 																						\n");
                    sb.append( "       AND mp.ida3b8 = ep.ida2a2 																																															\n");
                    sb.append( "       AND mi.ida2a2 = mp.ida3a10 																																														\n");
                    sb.append( "       AND mi.ida3a3 = pr.ida2a2	 																																														\n");
                    sb.append( "       AND mi.ida3c3 = n.ida2a2 																																															\n");
                    sb.append( "       AND ts.ida3a4 = ex.ida2a2 																																															\n");
                    sb.append( "       AND ts.ida2a2 = tml.ida3a5(+) 																																														\n");
                    sb.append( "	     AND tml.ida3b5 = pml.ida2a2(+) 																																													\n");
                    sb.append( "	     AND pml.ida3b5 = wu.ida2a2(+) 																																													\n");
                    sb.append( "	     AND wu.ida2a2 = pe.ida3b4(+) 																																														\n");
                    sb.append( "	     AND pe.ida3c4 = dp.ida2a2(+) 																																														\n");
                    sb.append( "		 AND nvl(tml.taskmembertype,  0) in (0, 1)																																										\n");
                    if(mTaskName.length() > 0){
                        sb.append( "		    AND ts.taskname like '%"+mTaskName+"%' 																																							\n");
                    }else{}
                    // 부서 코드로 구분 하위 뎁스의 deptCode값이 있을때 최하위 뎁스의 deptCode를 Count하여 deptCode의 Count가 0보다 크다면 부모키 조회 작다면 본인 코드 조회
                    if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() > 0 ){
                        if( isParentCnt(dept3) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept3+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept3+"' 										\n");
                        }
                    } else if(  dept.length() > 0 && dept1.length() > 0 && dept2.length() > 0 && dept3.length() <= 0 ){
                        if( isParentCnt(dept2) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept2+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept2+"' 										\n");
                        }
                    } else if ( dept.length() > 0 && dept1.length() > 0 && dept2.length() <= 0  ){
                        if( isParentCnt(dept1) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept1+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept1+"' 																																															\n");
                        }
                    } else if ( dept.length() > 0 && dept1.length() <= 0 && dept2.length() <= 0  ){
                        if( isParentCnt(dept) > 0 ) {
                            sb.append( "		AND dp.ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+dept+"'  ) 																				\n");
                        }else{
                            sb.append( "		AND dp.code = '"+dept+"' 																																															\n");
                        }
                    }else{	}
                    // 사용자 조회
                    if( taskUser.length() > 0 ){
                        sb.append( "		AND wu.ida2a2 = "+taskUser+" 																																													\n");
                    }else{	}
                    // 년도 조회
                    if (year.length() > 0){
                        years =  Integer.parseInt((String)hash.get("year"))+1;
                        sb.append( "     AND ex.planstartdate < '" + years +"0101'                                                       																                                                \n");
                        sb.append( "     AND (ex.execenddate between '" + year +"0101' and sysdate                                  																							            \n");
                        sb.append( "         OR ex.execenddate is null)                                                                																                                                        \n");
                    }else{   }
                    sb.append( "	     AND ts.taskstate = 0 																																																	\n");
                    sb.append("        AND to_date(to_char(ex.planenddate, 'YYYYMMDD'), 'YYYYMMDD') < to_date(to_char(sysdate, 'YYYYMMDD'), 'YYYYMMDD')																																														\n");
                    sb.append("	     AND mp.statestate not in ( 'STOPED','WITHDRAWN' )																																						\n");
                    sb.append("	     AND mp.statestate in ( 'PMOINWORK', 'DEVASSIGN' )																																					\n");

                    if(itemType.length() > 0){
                        sb.append( "	     AND mi.itemtype = '"+ itemType +"' 																																											\n");
                    }else{}

                    if( making.length() > 0 ){
                        sb.append( "	     AND mi.making = '"+ making +"' 																																												\n");
                    }else{}
                }
            }else{  }

            if ( sort.length() > 0 ){
                sb.append("  ORDER BY "+sort+"  ");
            }else{
                sb.append("  ORDER BY 2  ");
            }

Kogger.debug(getClass(), sb.toString());

            pstmt = new LoggableStatement( conn, sb.toString() );
            //Kogger.debug(getClass(), "----------------------쿼리 \n"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                moldProject = new Hashtable<String, String>();
                moldProject.put("name",        			StringUtil.checkNull(rs.getString(1)) ); // 개발유형
                moldProject.put("pjtno",       			StringUtil.checkNull(rs.getString(2)) ); // 프로젝트 no
                moldProject.put("pjtname",     			StringUtil.checkNull(rs.getString(3)) ); // 프로젝트명
                moldProject.put("taskstate",   			StringUtil.checkNull(rs.getString(4)) ); // task 상태
                moldProject.put("taskname",    		StringUtil.checkNull(rs.getString(5)) ); // task 명
                moldProject.put("departName",  		StringUtil.checkNull(rs.getString(6)) ); // 주관부서
                moldProject.put("userName",    		StringUtil.checkNull(rs.getString(7)) ); // 담당자
                moldProject.put("taskOid",    		   StringUtil.checkNull(rs.getString(8)) ); // taskOid
                moldProject.put("planenddate", 		StringUtil.checkNull(rs.getString(9)) ); // task 계획완료일
                moldProject.put("execenddate", 		StringUtil.checkNull(rs.getString(10))); // task 실제완료일
                moldProject.put("pjtLink",    			StringUtil.checkNull(rs.getString(11)) ); // 프로젝트링크 ida2a2
                moldProject.put("itemtype",    			StringUtil.checkNull(rs.getString(12)) ); // 금형구분
                moldProject.put("taskdesc",    	    StringUtil.checkNull(rs.getString(13)) ); // task 설명
                moldProject.put("taskcompletion",  StringUtil.checkNull(rs.getString(14)) ); // task 완료율
                moldProject.put("issue_yn",    	    StringUtil.checkNull(rs.getString(15)) ); // issue등록여부
                moldProjectList.add(moldProject);
            }

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return moldProjectList;
    }

    /**
     * 함수명 : getDeptLevel1
     * 설명 :
     * @throws Exception
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 28.
     */

    public ArrayList getDeptLevel1 ( String deptCole ) throws Exception{
        LoggableStatement pstmt = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        ArrayList<Hashtable<String, String>> taskDeptList = new ArrayList<Hashtable<String, String>>();
        Hashtable<String, String> taskDept = null;

        try {

            //Kogger.debug(getClass(), "----------------------쿼리실행시작");

            sb = new StringBuffer();
            sb.append(" SELECT code, name, ida3parentreference, sort, ida2a2 																					\n");
            sb.append("     FROM DEPARTMENT 																																\n");
            sb.append("   WHERE ida3parentreference = ( SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+deptCole+"'  ) 		\n");
            //sb.append("     AND enabled = 1 AND code not in ('10005','10007','10080') ORDER BY name		  													\n");
            /*전자 사업부도 포함될수있도록 변경 2013.11.11 윤도혁 대리 요청 황정태 수정*/
            sb.append("     AND enabled = 1 AND code not in ('10005','10007') ORDER BY name		  													\n");

            pstmt = new LoggableStatement( conn, sb.toString() );
            //Kogger.debug(getClass(), "----------------------쿼리"+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                taskDept = new Hashtable<String, String>();

                taskDept.put("code",        						StringUtil.checkNull(rs.getString(1)) ); // 부서코드
                taskDept.put("name",       						StringUtil.checkNull(rs.getString(2)) ); // 부서명
                taskDept.put("ida3parentreference",     	StringUtil.checkNull(rs.getString(3)) ); // 부모 ida2a2
                taskDept.put("sort",     							StringUtil.checkNull(rs.getString(4)) ); // 부서구분코드
                taskDept.put("ida2a2",   							StringUtil.checkNull(rs.getString(5)) ); // 부서 ida2a2

                taskDeptList.add(taskDept);
            }

            //Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
        }

        return taskDeptList;
    }

    /**
     * 함수명 : isParentCnt
     * 설명 :
     * @param hash
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 30.
     */
    public int isParentCnt( String deptCode ) throws Exception{
        LoggableStatement pstmt = null;
        Connection conn = null;
        StringBuffer sb = null;
        ResultSet rs = null;
        int listCnt = 0;

        try {

            //Kogger.debug(getClass(), "----------------------쿼리실행시작");

            conn = PlmDBUtil.getConnection();

            sb = new StringBuffer();
            sb.append("  SELECT count(*)  FROM DEPARTMENT	WHERE ida3parentreference = (SELECT ida2a2 FROM DEPARTMENT WHERE code = '"+deptCode+"' )\n");

            pstmt = new LoggableStatement( conn, sb.toString() );
            //Kogger.debug(getClass(), "----------------------쿼리 \n "+pstmt.getQueryString());
            rs = pstmt.executeQuery();

            while (rs.next()){
                listCnt = rs.getInt(1);
            }

            //Kogger.debug(getClass(), "----------------------쿼리실행종료");

        } catch (SQLException se) {
            Kogger.error(getClass(), se);
        } catch (Exception e) {
            Kogger.error(getClass(), e);
        } finally {
            sb.delete( 0 , sb.length() );
            statementRsClose(rs, pstmt);
            PlmDBUtil.close(conn);
        }

        return listCnt;
    }

    /**
     * 함수명 : statementRsClose
     * 설명 :
     * @throws Exception
     * 작성자 : 엄태훈
     * 작성일자 : 2011. 06. 21.
     */
    public void statementRsClose(ResultSet rs, LoggableStatement pstmt) throws Exception
    {
        if( rs != null )
            rs.close();

        if( pstmt != null )
            pstmt.close();
    }
}
