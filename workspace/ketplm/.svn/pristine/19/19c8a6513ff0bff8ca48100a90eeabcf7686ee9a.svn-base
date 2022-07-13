package e3ps.cost.control;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;

import e3ps.cost.util.DBUtil;
import e3ps.cost.util.MSDBUtil;
import e3ps.cost.dao.CostAuthDao;

public class CostAuthCtl {
    /**
     * 함수명 : epUserList
     * 설명 : EP 계정 호출
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 03.
     */

    public ArrayList epUserList(){
        //connection
        Connection conn = null;

        ArrayList epList = new ArrayList();
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            epList = authDao.getEPUser();

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return epList;
    }

    /**
     * 함수명 : searchEPUserList
     * 설명 : EP 검색 계정 호출
     * @param String user
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 07.
     */

    public ArrayList searchEPUserList(String userName){
        try {
            userName = java.net.URLDecoder.decode(userName, "utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        //connection
        Connection conn = null;

        ArrayList searchEpList = new ArrayList();
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            searchEpList = authDao.getSearchEPUser(userName);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return searchEpList;
    }

    /**
     * 함수명 : modifyAuthList
     * 설명 : 개발원가 수정 사용자 조회
     * @param
     * @return
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 03.
     */

    public ArrayList modifyAuthList(String empno){
        //connection
        Connection conn = null;

        ArrayList epList = new ArrayList();
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            epList = authDao.getModifyAuthUser(empno);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return epList;
    }

    /**
     * 함수명 : modifyAuth
     * 설명 : 권한 수정
     * @param String account, String auth, String group
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */

    public int modifyAuth(String account, String auth, String group){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            complet = authDao.setModifyAuth(account, auth, group);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : searchEPuser
     * 설명 : EP 사용자 조회
     * @param String account
     * @return ArrayList
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */

    public ArrayList searchEPuser(String account){
        //connection
        Connection conn = null;

        ArrayList epList = new ArrayList();
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            epList = authDao.getSearchEPuser(account);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return epList;
    }

    /**
     * 함수명 : searchEPuser
     * 설명 : auth 사용자 조회
     * @param String account
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */

    public int searchAuthUser(String account){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            complet = authDao.getSearchAuthUser(account);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : insertAuth
     * 설명 : 권한 등록
     * @param String account, String name, String auth, String group
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */

    public int insertAuth(String account, String name, String auth, String group){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            complet = authDao.setInsertAuth(account, name, auth, group);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return complet;
    }

    /**
     * 함수명 : deleteAuth
     * 설명 : 권한 삭제
     * @param String account
     * @return int
     * @throws Exception
     * @throws
     * 작성자 : 엄태훈
     * 작성일자 : 2012. 06. 14.
     */

    public int deleteAuth(String account){
        //connection
        Connection conn = null;

        int complet = 0;
        try{
            // connection creation
            conn = MSDBUtil.getConnection();
            CostAuthDao authDao = new CostAuthDao(conn);

            // result
            complet = authDao.setDeleteAuth(account);

        }catch(Exception e){
            e.printStackTrace();
//		}finally{
//			DBUtil.close(conn);
        }

        return complet;
    }
}