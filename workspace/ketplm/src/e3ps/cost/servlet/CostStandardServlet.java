package e3ps.cost.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import e3ps.cost.dao.CostStandDao;
import e3ps.cost.util.DBUtil;
import e3ps.cost.util.StringUtil;
import xlib.cmc.*;

public class CostStandardServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        String wise_gb = StringUtil.getParameter(req, "wise_gb");

        GridData gdReq = null;
        GridData gdRes = null;

        // Encode Type을 UTF-8로 변환한다.
        req.setCharacterEncoding("utf-8");
        res.setContentType("text/html; charset=utf-8");

        PrintWriter out = res.getWriter();

        try {

            // WISEGRID_DATA 라는 Param으로 WiseGrid의 전문이 올라온다.
            String rawData = req.getParameter("WISEGRID_DATA");

            // 올라온 전문을 Parsing하여 자료구조 형태로 반환해준다.
            gdReq = OperateGridData.parse(rawData);

            // 전달받은 파라미터값을 가져온다.
            String mode = gdReq.getParam("mode");

            if (mode.equals("search_Reer")){
                gdRes = ReerSelectQry(gdReq);
            }else if (mode.equals("save_Reer")){
                gdRes = ReerSave(gdReq);
            }else if (mode.equals("search_Cutt")){
                gdRes = CuttSelectQry(gdReq);
            }else if (mode.equals("save_Cutt")){
                gdRes = CuttSave(gdReq);
            }else if (mode.equals("search_Plating")){
                gdRes = PlatingSelectQry(gdReq);
            }else if (mode.equals("save_Plating")){
                gdRes = PlatingSave(gdReq);
            }else if (mode.equals("search_Proces")){
                gdRes = ProcessingSelectQry(gdReq);
            }else if (mode.equals("save_Proces")){
                gdRes = ProcessingSave(gdReq);
            }else if (mode.equals("search_Pmaker")){
                gdRes = PmakerSelectQry(gdReq);
            }else if (mode.equals("save_Pmaker")){
                gdRes = PmakerSave(gdReq);
            }else if (mode.equals("search_Mmaker")){
                gdRes = MmakerSelectQry(gdReq);
            }else if (mode.equals("save_Mmaker")){
                gdRes = MmakerSave(gdReq);
            }else if (mode.equals("search_LME")){
                gdRes = LMESelectQry(gdReq);
            }else if (mode.equals("save_LME")){
                gdRes = LMESave(gdReq);
            }else if (mode.equals("search_Dis")){
                gdRes = DisSelectQry(gdReq);
            }else if (mode.equals("save_Dis")){
                gdRes = DisSave(gdReq);
            }


        } catch (Exception e) {
            gdRes = new GridData();
            gdRes.setMessage("Error: " + e.getMessage());
            gdRes.setStatus("false");
            e.printStackTrace();
        }

        finally
        {
            try
            {
                // WiseGrid에 전달할 데이터가 담긴 GridData 객체와 미리 생성해 놓은 PrintWriter 객체를 넘겨
                // 데이터를 Parsing 한 후 전송합니다.
                OperateGridData.write(gdRes, out);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    /*환율DB조회*/
    public GridData ReerSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            // 조회조건으로 사용할 Param 값들을 가져옵니다.
            String cmd = gdReq.getParam("cmd");

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList = codeDao.getReerList();
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                SearchMap			= (Hashtable)SearchList.get(i);
                String PK_RE		= (String)SearchMap.get("PK_RE");
                String REER_date	= (String)SearchMap.get("REER_date");
                String USD_rate   	= (String)SearchMap.get("USD_rate");
                String YEN_rate	 	= (String)SearchMap.get("YEN_rate");
                String EURO_rate 	= (String)SearchMap.get("EURO_rate");
                String CNY_rate 	= (String)SearchMap.get("CNY_rate");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("PK_RE").addValue(PK_RE, "");
                gdRes.getHeader("REER_date").addValue(REER_date, " ");
                gdRes.getHeader("USD_rate").addValue(USD_rate, " ");
                gdRes.getHeader("YEN_rate").addValue(YEN_rate, " ");
                gdRes.getHeader("EURO_rate").addValue(EURO_rate, " ");
                gdRes.getHeader("CNY_rate").addValue(CNY_rate, " ");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.

        gdRes.addParam("mode", "search");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");

        return gdRes;
    }

    /* 환율DB저장 */
    public GridData ReerSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("PK_RE", gdReq.getHeader("PK_RE").getValue(i) );
                    createData.put("REER_date", gdReq.getHeader("REER_date").getValue(i));
                    createData.put("USD_rate", gdReq.getHeader("USD_rate").getValue(i));
                    createData.put("YEN_rate", gdReq.getHeader("YEN_rate").getValue(i));
                    createData.put("EURO_rate", gdReq.getHeader("EURO_rate").getValue(i));
                    createData.put("CNY_rate", gdReq.getHeader("CNY_rate").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.ReerCreate(createDataList);
                    createDataList.clear();
                } else if (crud.equals("U")) {
                    updateData.put("PK_RE", gdReq.getHeader("PK_RE").getValue(i) );
                    updateData.put("REER_date", gdReq.getHeader("REER_date").getValue(i) );
                    updateData.put("USD_rate", gdReq.getHeader("USD_rate").getValue(i));
                    updateData.put("YEN_rate", gdReq.getHeader("YEN_rate").getValue(i));
                    updateData.put("EURO_rate", gdReq.getHeader("EURO_rate").getValue(i));
                    updateData.put("CNY_rate", gdReq.getHeader("CNY_rate").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.ReerUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("PK_RE", gdReq.getHeader("PK_RE").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.ReerDelete(deleteDataList);
                    deleteData.clear();
                }
            }
            if(conn!=null) {conn.close();}
            /*
             * 생성된 3개의 자료구조를 DataBase에 넘겨 처리한다.
             */


            /* 화면에 전달할  파라미터를 설정한다.
             * 메세지를 셋팅한다.
             * Status를 설정한다
             */

            gdRes.addParam("mode", "SaveReer");
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*절단비DB조회 */
    public GridData CuttSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            // 조회조건으로 사용할 Param 값들을 가져옵니다.
            String cmd = gdReq.getParam("cmd");

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList = codeDao.getCuttingList();
            Hashtable SearchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                SearchMap = (Hashtable)SearchList.get(i);
                String PK_CUT = (String)SearchMap.get("PK_CUT");

                String MET_TYPE   = (String)SearchMap.get("MET_TYPE");
                String MET_W      = (String)SearchMap.get("MET_W");
                String C_SIZE_T_1 = (String)SearchMap.get("C_SIZE_T_1");
                String C_SIZE_T_2 = (String)SearchMap.get("C_SIZE_T_2");
                String C_SIZE_T_3 = (String)SearchMap.get("C_SIZE_T_3");
                String C_SIZE_T_4 = (String)SearchMap.get("C_SIZE_T_4");
                String C_SIZE_T_5 = (String)SearchMap.get("C_SIZE_T_5");
                String C_SIZE_T_6 = (String)SearchMap.get("C_SIZE_T_6");
                String C_SIZE_T_7 = (String)SearchMap.get("C_SIZE_T_7");
                String C_SIZE_T_8 = (String)SearchMap.get("C_SIZE_T_8");
                String C_SIZE_T_9 = (String)SearchMap.get("C_SIZE_T_9");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("pk_cut").addValue(PK_CUT, "");
                gdRes.getHeader("met_type").addValue(MET_TYPE, " ");
                gdRes.getHeader("met_w").addValue(MET_W, " ");
                gdRes.getHeader("c_size_t_1").addValue(C_SIZE_T_1, " ");
                gdRes.getHeader("c_size_t_2").addValue(C_SIZE_T_2, " ");
                gdRes.getHeader("c_size_t_3").addValue(C_SIZE_T_3, " ");
                gdRes.getHeader("c_size_t_4").addValue(C_SIZE_T_4, " ");
                gdRes.getHeader("c_size_t_5").addValue(C_SIZE_T_5, " ");
                gdRes.getHeader("c_size_t_6").addValue(C_SIZE_T_6, " ");
                gdRes.getHeader("c_size_t_7").addValue(C_SIZE_T_7, " ");
                gdRes.getHeader("c_size_t_8").addValue(C_SIZE_T_8, " ");
                gdRes.getHeader("c_size_t_9").addValue(C_SIZE_T_9, " ");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.

        gdRes.addParam("mode", "search_Cutt");
        String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
        gdRes.setMessage(msg);
        gdRes.setStatus("true");

        return gdRes;
    }

    /* 절단비DB저장 */
    public GridData CuttSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("met_type", gdReq.getHeader("met_type").getValue(i) );
                    createData.put("met_w", gdReq.getHeader("met_w").getValue(i));
                    createData.put("c_size_t_1", gdReq.getHeader("c_size_t_1").getValue(i));
                    createData.put("c_size_t_2", gdReq.getHeader("c_size_t_2").getValue(i));
                    createData.put("c_size_t_3", gdReq.getHeader("c_size_t_3").getValue(i));
                    createData.put("c_size_t_4", gdReq.getHeader("c_size_t_4").getValue(i));
                    createData.put("c_size_t_5", gdReq.getHeader("c_size_t_5").getValue(i));
                    createData.put("c_size_t_6", gdReq.getHeader("c_size_t_6").getValue(i));
                    createData.put("c_size_t_7", gdReq.getHeader("c_size_t_7").getValue(i));
                    createData.put("c_size_t_8", gdReq.getHeader("c_size_t_8").getValue(i));
                    createData.put("c_size_t_9", gdReq.getHeader("c_size_t_9").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.CuttingCreate(createDataList);
                    createDataList.clear();

                } else if (crud.equals("U")) {
                    updateData.put("pk_cut", gdReq.getHeader("pk_cut").getValue(i) );
                    updateData.put("met_type", gdReq.getHeader("met_type").getValue(i) );
                    updateData.put("met_w", gdReq.getHeader("met_w").getValue(i));
                    updateData.put("c_size_t_1", gdReq.getHeader("c_size_t_1").getValue(i));
                    updateData.put("c_size_t_2", gdReq.getHeader("c_size_t_2").getValue(i));
                    updateData.put("c_size_t_3", gdReq.getHeader("c_size_t_3").getValue(i));
                    updateData.put("c_size_t_4", gdReq.getHeader("c_size_t_4").getValue(i));
                    updateData.put("c_size_t_5", gdReq.getHeader("c_size_t_5").getValue(i));
                    updateData.put("c_size_t_6", gdReq.getHeader("c_size_t_6").getValue(i));
                    updateData.put("c_size_t_7", gdReq.getHeader("c_size_t_7").getValue(i));
                    updateData.put("c_size_t_8", gdReq.getHeader("c_size_t_8").getValue(i));
                    updateData.put("c_size_t_9", gdReq.getHeader("c_size_t_9").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.CuttingUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("pk_cut", gdReq.getHeader("pk_cut").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.CuttingDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "save_Cutt");
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*도금비 조회*/
    public GridData PlatingSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList  = codeDao.getPlatingList();
            Hashtable searchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                searchMap = (Hashtable)SearchList.get(i);
                String PK_PLC = (String)searchMap.get("PK_PLC");

                String MET_TYPE   = (String)searchMap.get("MET_TYPE");

                String P_SIZE_T_1 = (String)searchMap.get("P_SIZE_T_1");
                String P_SIZE_T_2 = (String)searchMap.get("P_SIZE_T_2");
                String P_SIZE_T_3 = (String)searchMap.get("P_SIZE_T_3");
                String P_SIZE_T_4 = (String)searchMap.get("P_SIZE_T_4");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("PK_PLC").addValue(PK_PLC, "");
                gdRes.getHeader("MET_TYPE").addValue(MET_TYPE, " ");
                gdRes.getHeader("P_SIZE_T_1").addValue(P_SIZE_T_1, " ");
                gdRes.getHeader("P_SIZE_T_2").addValue(P_SIZE_T_2, " ");
                gdRes.getHeader("P_SIZE_T_3").addValue(P_SIZE_T_3, " ");
                gdRes.getHeader("P_SIZE_T_4").addValue(P_SIZE_T_4, " ");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "Search_Plating");
        String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
        gdRes.setMessage(msg);
        gdRes.setStatus("true");

        return gdRes;
    }


    /* 도금비저장 */
    public GridData PlatingSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("PK_PLC", gdReq.getHeader("PK_PLC").getValue(i) );
                    createData.put("MET_TYPE", gdReq.getHeader("MET_TYPE").getValue(i));
                    createData.put("P_SIZE_T_1", gdReq.getHeader("P_SIZE_T_1").getValue(i));
                    createData.put("P_SIZE_T_2", gdReq.getHeader("P_SIZE_T_2").getValue(i));
                    createData.put("P_SIZE_T_3", gdReq.getHeader("P_SIZE_T_3").getValue(i));
                    createData.put("P_SIZE_T_4", gdReq.getHeader("P_SIZE_T_4").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.platingCreate(createDataList);
                    createDataList.clear();

                } else if (crud.equals("U")) {
                    updateData.put("PK_PLC", gdReq.getHeader("PK_PLC").getValue(i) );
                    updateData.put("MET_TYPE", gdReq.getHeader("MET_TYPE").getValue(i));
                    updateData.put("P_SIZE_T_1", gdReq.getHeader("P_SIZE_T_1").getValue(i));
                    updateData.put("P_SIZE_T_2", gdReq.getHeader("P_SIZE_T_2").getValue(i));
                    updateData.put("P_SIZE_T_3", gdReq.getHeader("P_SIZE_T_3").getValue(i));
                    updateData.put("P_SIZE_T_4", gdReq.getHeader("P_SIZE_T_4").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.PlatingUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("PK_PLC", gdReq.getHeader("PK_PLC").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.PlatingDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "Save_Plating");
            String msg = new String("성공적으로 작업하였습니다.".getBytes("utf-8"), "8859_1");
            gdRes.setMessage(msg);
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*가공비조회*/
    public GridData ProcessingSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList  = codeDao.getProcessingList();
            Hashtable searchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                searchMap = (Hashtable)SearchList.get(i);
                String PK_PC = (String)searchMap.get("PK_PC");
                String PC_COST_TYPE = (String)searchMap.get("PC_COST_TYPE");
                String MET_TYPE = (String)searchMap.get("MET_TYPE");
                String PRO_COST = (String)searchMap.get("PRO_COST");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("PK_PC").addValue(PK_PC, "");
                gdRes.getHeader("PC_COST_TYPE").addSelectedHiddenValue(PC_COST_TYPE);
                gdRes.getHeader("MET_TYPE").addValue(MET_TYPE, "");
                gdRes.getHeader("PRO_COST").addValue(PRO_COST, "");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search_Proces");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");

        return gdRes;
    }


    /* 가공비저장 */
    public GridData ProcessingSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("PK_PC", gdReq.getHeader("pk_pc").getValue(i) );
                    createData.put("pc_cost_type", gdReq.getHeader("pc_cost_type").getValue(i));
                    createData.put("met_type", gdReq.getHeader("met_type").getValue(i));
                    createData.put("pro_cost", gdReq.getHeader("pro_cost").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.processingCreate(createDataList);
                    createDataList.clear();
                } else if (crud.equals("U")) {
                    updateData.put("PK_PC", gdReq.getHeader("pk_pc").getValue(i) );
                    updateData.put("pc_cost_type", gdReq.getHeader("pc_cost_type").getValue(i));
                    updateData.put("met_type", gdReq.getHeader("met_type").getValue(i));
                    updateData.put("pro_cost", gdReq.getHeader("pro_cost").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.ProcessingUpdate(updateDataList);
                    updateDataList.clear();
                } else if (crud.equals("D")) {
                    deleteData.put("pk_pc", gdReq.getHeader("pk_pc").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.ProcessingDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "save_Proces");
            gdRes.setMessage("성공적으로 작업하였습니다.");
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*비철원재료정보조회*/
    public GridData PmakerSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList  = codeDao.getPmakerList();
            Hashtable searchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                searchMap = (Hashtable)SearchList.get(i);
                String PK_PR = (String)searchMap.get("PK_PR");
                String MK_CODE = (String)searchMap.get("MK_CODE");
                String MAKER_NAME = (String)searchMap.get("MAKER_NAME");
                String MET_TYPE = (String)searchMap.get("MET_TYPE");
                String GRADE_NAME = (String)searchMap.get("GRADE_NAME");
                String S_GRAVITY = (String)searchMap.get("S_GRAVITY");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("PK_PR").addValue(PK_PR, "");
                gdRes.getHeader("MK_CODE").addValue(MK_CODE, "");
                gdRes.getHeader("MAKER_NAME").addValue(MAKER_NAME, "");
                gdRes.getHeader("MET_TYPE").addValue(MET_TYPE, "");
                gdRes.getHeader("GRADE_NAME").addValue(GRADE_NAME, "");
                gdRes.getHeader("S_GRAVITY").addValue(S_GRAVITY, "");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search_Pmaker");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");

        return gdRes;
    }


    /* 비철원재료정보저장 */
    public GridData PmakerSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("PK_PR", gdReq.getHeader("PK_PR").getValue(i) );
                    createData.put("MK_CODE", gdReq.getHeader("MK_CODE").getValue(i));
                    createData.put("MAKER_NAME", gdReq.getHeader("MAKER_NAME").getValue(i));
                    createData.put("MET_TYPE", gdReq.getHeader("MET_TYPE").getValue(i));
                    createData.put("GRADE_NAME", gdReq.getHeader("GRADE_NAME").getValue(i));
                    createData.put("S_GRAVITY", gdReq.getHeader("S_GRAVITY").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.PmakerCreate(createDataList);
                    createDataList.clear();
                } else if (crud.equals("U")) {
                    updateData.put("PK_PR", gdReq.getHeader("PK_PR").getValue(i) );
                    updateData.put("MK_CODE", gdReq.getHeader("MK_CODE").getValue(i));
                    updateData.put("MAKER_NAME", gdReq.getHeader("MAKER_NAME").getValue(i));
                    updateData.put("MET_TYPE", gdReq.getHeader("MET_TYPE").getValue(i));
                    updateData.put("GRADE_NAME", gdReq.getHeader("GRADE_NAME").getValue(i));
                    updateData.put("S_GRAVITY", gdReq.getHeader("S_GRAVITY").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.PmakerUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("PK_PR", gdReq.getHeader("PK_PR").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.PmakerDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "save_Pmaker");
            gdRes.setMessage("성공적으로 작업하였습니다.");
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*수지원재료정보조회*/
    public GridData MmakerSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList  = codeDao.getMmakerList();
            Hashtable searchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                searchMap = (Hashtable)SearchList.get(i);
                String PK_MK = (String)searchMap.get("PK_MK");
                String MK_CODE = (String)searchMap.get("MK_CODE");
                String MAKER_NAME = (String)searchMap.get("MAKER_NAME");
                String MATERIAL_NAME = (String)searchMap.get("MATERIAL_NAME");
                String GRADE_NAME = (String)searchMap.get("GRADE_NAME");
                String GRADE_COLOR = (String)searchMap.get("GRADE_COLOR");
                String GRADE_COST = (String)searchMap.get("GRADE_COST");
                String SU_STAN_DAY = (String)searchMap.get("SU_STAN_DAY");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("PK_MK").addValue(PK_MK, "");
                gdRes.getHeader("MK_CODE").addValue(MK_CODE, "");
                gdRes.getHeader("MAKER_NAME").addValue(MAKER_NAME, "");
                gdRes.getHeader("MATERIAL_NAME").addValue(MATERIAL_NAME, "");
                gdRes.getHeader("GRADE_NAME").addValue(GRADE_NAME, "");
                gdRes.getHeader("GRADE_COLOR").addValue(GRADE_COLOR, "");
                gdRes.getHeader("GRADE_COST").addValue(GRADE_COST, "");
                gdRes.getHeader("SU_STAN_DAY").addValue(SU_STAN_DAY, "");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search_Mmaker");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");

        return gdRes;
    }


    /* 수지원재료정보저장 */
    public GridData MmakerSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("PK_MK", gdReq.getHeader("PK_MK").getValue(i) );
                    createData.put("MK_CODE", gdReq.getHeader("MK_CODE").getValue(i));
                    createData.put("MAKER_NAME", gdReq.getHeader("MAKER_NAME").getValue(i));
                    createData.put("MATERIAL_NAME", gdReq.getHeader("MATERIAL_NAME").getValue(i));
                    createData.put("GRADE_NAME", gdReq.getHeader("GRADE_NAME").getValue(i));
                    createData.put("GRADE_COLOR", gdReq.getHeader("GRADE_COLOR").getValue(i));
                    createData.put("GRADE_COST", gdReq.getHeader("GRADE_COST").getValue(i));
                    createData.put("SU_STAN_DAY", gdReq.getHeader("SU_STAN_DAY").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.MmakerCreate(createDataList);
                    createDataList.clear();
                } else if (crud.equals("U")) {
                    updateData.put("PK_MK", gdReq.getHeader("PK_MK").getValue(i) );
                    updateData.put("MK_CODE", gdReq.getHeader("MK_CODE").getValue(i));
                    updateData.put("MAKER_NAME", gdReq.getHeader("MAKER_NAME").getValue(i));
                    updateData.put("MATERIAL_NAME", gdReq.getHeader("MATERIAL_NAME").getValue(i));
                    updateData.put("GRADE_NAME", gdReq.getHeader("GRADE_NAME").getValue(i));
                    updateData.put("GRADE_COLOR", gdReq.getHeader("GRADE_COLOR").getValue(i));
                    updateData.put("GRADE_COST", gdReq.getHeader("GRADE_COST").getValue(i));
                    updateData.put("SU_STAN_DAY", gdReq.getHeader("SU_STAN_DAY").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.MmakerUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("PK_MK", gdReq.getHeader("PK_MK").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.MmakerDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "save_Mmaker");
            gdRes.setMessage("성공적으로 작업하였습니다.");
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*LME정보조회*/
    public GridData LMESelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList  = codeDao.getLMEList();
            Hashtable searchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                searchMap = (Hashtable)SearchList.get(i);
                String pk_LME  = (String)searchMap.get("pk_LME");
                String in_date = (String)searchMap.get("in_date");
                String USD_rate= (String)searchMap.get("USD_rate");
                String YEN_rate= (String)searchMap.get("YEN_rate");
                String Lme_cu  = (String)searchMap.get("Lme_cu");
                String Lme_zn  = (String)searchMap.get("Lme_zn");
                String Lme_sn  = (String)searchMap.get("Lme_sn");
                String Lme_ni  = (String)searchMap.get("Lme_ni");
                String Lme_type= (String)searchMap.get("Lme_type");


                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                gdRes.getHeader("pk_LME").addValue(pk_LME  , "");
                gdRes.getHeader("in_date").addValue(in_date , "");
                gdRes.getHeader("USD_rate").addValue(USD_rate, "");
                gdRes.getHeader("YEN_rate").addValue(YEN_rate, "");
                gdRes.getHeader("Lme_cu").addValue(Lme_cu  , "");
                gdRes.getHeader("Lme_zn").addValue(Lme_zn  , "");
                gdRes.getHeader("Lme_sn").addValue(Lme_sn  , "");
                gdRes.getHeader("Lme_ni").addValue(Lme_ni  , "");
                gdRes.getHeader("Lme_type").addValue(Lme_type, "");


            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search_LME");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");

        return gdRes;
    }


    /* LME정보저장 */
    public GridData LMESave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("pk_LME", gdReq.getHeader("pk_LME").getValue(i));
                    createData.put("in_date", gdReq.getHeader("in_date").getValue(i));
                    createData.put("USD_rate", gdReq.getHeader("USD_rate").getValue(i));
                    createData.put("YEN_rate", gdReq.getHeader("YEN_rate").getValue(i));
                    createData.put("Lme_cu", gdReq.getHeader("Lme_cu").getValue(i));
                    createData.put("Lme_zn", gdReq.getHeader("Lme_zn").getValue(i));
                    createData.put("Lme_sn", gdReq.getHeader("Lme_sn").getValue(i));
                    createData.put("Lme_ni", gdReq.getHeader("Lme_ni").getValue(i));
                    createData.put("Lme_type", gdReq.getHeader("Lme_type").getValue(i));
                    createDataList.add(createData);
                    cnt = codeDao.LMECreate(createDataList);
                    createDataList.clear();
                } else if (crud.equals("U")) {
                    updateData.put("pk_LME", gdReq.getHeader("pk_LME").getValue(i) );
                    updateData.put("in_date", gdReq.getHeader("in_date").getValue(i));
                    updateData.put("USD_rate", gdReq.getHeader("USD_rate").getValue(i));
                    updateData.put("YEN_rate", gdReq.getHeader("YEN_rate").getValue(i));
                    updateData.put("Lme_cu", gdReq.getHeader("Lme_cu").getValue(i));
                    updateData.put("Lme_zn", gdReq.getHeader("Lme_zn").getValue(i));
                    updateData.put("Lme_sn", gdReq.getHeader("Lme_sn").getValue(i));
                    updateData.put("Lme_ni", gdReq.getHeader("Lme_ni").getValue(i));
                    updateData.put("Lme_type", gdReq.getHeader("Lme_type").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.LMEUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("pk_LME", gdReq.getHeader("pk_LME").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.LMEDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "save_LME");
            gdRes.setMessage("성공적으로 작업하였습니다.");
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }

    /*Dis정보조회*/
    public GridData DisSelectQry(GridData gdReq) throws Exception
    {
        Connection conn = null;
        // 전송할 데이터를 담을 빈 GridData를 생성합니다.
        GridData gdRes = null;
        int rowCount = 0;

        try
        {
            // WiseGrid에서 전달된 헤더정보와 ComboList, ImageList를 복사해 GridData를 생성합니다.
            // cloneResponseGridData 메서드를 사용하면 헤더를 일일이 생성하지 않고 손쉽게 만들 수 있습니다.
            gdRes = OperateGridData.cloneResponseGridData(gdReq);

            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);
            // result
            ArrayList SearchList  = codeDao.getDisList();
            Hashtable searchMap = null;
            // GridData에 데이터를 셋팅합니다.
            for(int i = 0; i < SearchList.size() ; i++)
            {
                // t_text, t_number, t_date 에는 addValue(String value, String hiddenValue) 메서드를 사용합니다.
                // t_combo 에는 addSelectedHiddenValue(String value) 또는 addSelectedIndex(int comboIndex) 메서드를 사용합니다.
                // t_imagetext 에는 addValue(String value, String hiddenValue, int imageIndex) 메서드를 사용합니다.

                searchMap = (Hashtable)SearchList.get(i);
                String pk_dis     = (String)searchMap.get("pk_dis");
                String distri_type= (String)searchMap.get("distri_type");
                String store      = (String)searchMap.get("store");
                String dest       = (String)searchMap.get("dest");
                String dest_1     = (String)searchMap.get("dest_1");
                String distri_cost= (String)searchMap.get("distri_cost");

                gdRes.getHeader("SELECTED").addValue ("0", "");
                gdRes.getHeader("CRUD").addValue("", "");
                   gdRes.getHeader("pk_dis").addValue(pk_dis  , "");
                   gdRes.getHeader("distri_type").addValue(distri_type , "");
                   gdRes.getHeader("store").addValue(store, "");
                   gdRes.getHeader("dest").addValue(dest, "");
                   gdRes.getHeader("dest_1").addValue(dest_1  , "");
                   gdRes.getHeader("distri_cost").addValue(distri_cost  , "");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }

        // 메세지와 상태값을 셋팅합니다.
        gdRes.addParam("mode", "search_Dis");
        gdRes.setMessage("성공적으로 작업하였습니다.");
        gdRes.setStatus("true");

        return gdRes;
    }

    /* Dis정보저장 */
    public GridData DisSave(GridData gdReq) throws Exception {

        GridData gdRes = new GridData();

        int rowCount = 0;

        try {
            //화면에서 전달받은 "CRUD"의 row 수를 가져온다.
            rowCount = gdReq.getHeader("CRUD").getRowCount();
            ArrayList createDataList = new ArrayList(rowCount);
            ArrayList updateDataList = new ArrayList(rowCount);
            ArrayList deleteDataList = new ArrayList(rowCount);

            Hashtable<String, String> createData = null;
            createData = new Hashtable<String, String>();

            Hashtable<String, String> updateData = null;
            updateData = new Hashtable<String, String>();

            Hashtable<String, String> deleteData = null;
            deleteData = new Hashtable<String, String>();

            int cnt = 0;
            Connection conn = null;
            conn = DBUtil.getConnection();
            CostStandDao codeDao = new CostStandDao(conn);

            //데이터 셋팅
            for (int i = 0; i < rowCount; i++) {

                //화면에서 전달받은 "CRUD"의 HiddenValue를 가져온다.
                String crud = gdReq.getHeader("CRUD").getHiddenValue(i);

                if (crud.equals("C")) {
                    createData.put("pk_dis",gdReq.getHeader("pk_dis").getValue(i) );
                    createData.put("distri_type",gdReq.getHeader("distri_type").getValue(i));
                    createData.put("store",gdReq.getHeader("store").getValue(i));
                    createData.put("dest",gdReq.getHeader("dest").getValue(i));
                    createData.put("dest_1",gdReq.getHeader("dest_1").getValue(i));
                    createData.put("distri_cost",gdReq.getHeader("distri_cost").getValue(i));

                    createDataList.add(createData);
                    cnt = codeDao.DisCreate(createDataList);
                    createDataList.clear();
                } else if (crud.equals("U")) {
                    updateData.put("pk_dis",gdReq.getHeader("pk_dis").getValue(i) );
                    updateData.put("distri_type",gdReq.getHeader("distri_type").getValue(i));
                    updateData.put("store",gdReq.getHeader("store").getValue(i));
                    updateData.put("dest",gdReq.getHeader("dest").getValue(i));
                    updateData.put("dest_1",gdReq.getHeader("dest_1").getValue(i));
                    updateData.put("distri_cost",gdReq.getHeader("distri_cost").getValue(i));
                    updateDataList.add(updateData);
                    cnt = codeDao.DisUpdate(updateDataList);
                    updateDataList.clear();

                } else if (crud.equals("D")) {
                    deleteData.put("pk_dis", gdReq.getHeader("pk_dis").getValue(i));
                    deleteDataList.add(deleteData);
                    cnt = codeDao.DisDelete(deleteDataList);
                    deleteDataList.clear();
                }
            }
            if(conn!=null) {conn.close();}

            gdRes.addParam("mode", "save_Dis");
            gdRes.setMessage("성공적으로 작업하였습니다.");
            gdRes.setStatus("true");

        } catch (Exception e) {
            throw e;
        }

        return gdRes;
    }
}
