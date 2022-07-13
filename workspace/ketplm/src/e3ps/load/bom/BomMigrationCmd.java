package e3ps.load.bom;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import e3ps.common.util.DateUtil;
import e3ps.sap.ErpBomMigInfoInterface;
import ext.ket.shared.log.Kogger;

public class BomMigrationCmd {

	public static String strDirPath = "D:\\ptc\\Windchill\\logs\\bom";

	 public static void main(String args[]) throws SQLException, IOException {
		 
    	if( args.length != 2 )
        {
            // 사용법 설명
            Kogger.debug(BomMigrationCmd.class, ">>> Usage  : windchill e3ps.load.bom.BomMigrationCmd userID passwd");
            return;
        }
		 
    	// 사용자 인증 
		wt.method.RemoteMethodServer.getDefault().setUserName(args[0]);
		wt.method.RemoteMethodServer.getDefault().setPassword(args[1]);

		DataInputStream dis = new DataInputStream(System.in);
		String itemtype = "";
		String itemname = "";
		String f_Date = "";
		String t_Date = "";
		
		FileWriter fw= null;
    	String currentDate = DateUtil.getCurrentDateString( new SimpleDateFormat("yyyy-MM-dd"));
    	String currentTime = DateUtil.getCurrentDateString( new SimpleDateFormat("HH:mm:ss"));
    	String strFileName = "BomMigrationLog_" + currentDate + "_" + currentTime.replace(":", "") + ".log";

    	File dir = new File(strDirPath);
    	if (!dir.exists()) {		// 로그 저장 폴더가 존재하지 않는 경우 생성함
    		dir.mkdir();
    	}

    	Kogger.debug(BomMigrationCmd.class, "@@@@@@@@@ strFilePath : " + strDirPath + "\\" + strFileName);
    	File file = new File(strDirPath + "\\" + strFileName);
    	if (!file.exists()) {		// 로그 저장 파일이 존재하지 않는 경우 생성함
    		file.createNewFile();
    	}
		
		try
		{
			// 로그 남길 파일 생성
    		fw = new FileWriter(file, true);
            fw.write(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::\n" );
    		
			do{
			  System.out.print("부품타입을 입력하세요 (제품:1,금형:2) : ");
			  itemtype = String.valueOf(dis.readLine());
			  
			  // 로그파일 저장
			  fw.write("부품타입을 입력하세요 (제품:1,금형:2) : " );
			  fw.write( itemtype + "\n" );
			 }while(reSult(itemtype));
			 
			  System.out.print("부품명을 입력하세요 : ");
			  itemname = String.valueOf(dis.readLine());
			  
			  // 로그파일 저장
			  fw.write("부품명을 입력하세요 : " );
			  fw.write( itemname + "\n" );

			  System.out.print("날짜(From)를 입력하세요 (yyyy-mm-dd) : ");
			  f_Date = String.valueOf(dis.readLine());
			  
			  // 로그파일 저장
			  fw.write("날짜(From)를 입력하세요 (yyyy-mm-dd) : " );
			  fw.write( f_Date + "\n" );

			  System.out.print("날짜(To)를 입력하세요 (yyyy-mm-dd) : ");
			  t_Date = String.valueOf(dis.readLine());
			  
			  // 로그파일 저장
			  fw.write("날짜(To)를 입력하세요 (yyyy-mm-dd) : " );
			  fw.write( t_Date + "\n" );			  
			  
		}
		catch (Exception e)
		{
			Kogger.debug(BomMigrationCmd.class, "Error : "+e);
		}

			//Kogger.debug(getClass(), "@@@ Result----------------------------------------");
			//Kogger.debug(getClass(), "부품타입 : "+itemtype);
			//Kogger.debug(getClass(), "부품명 : "+itemname);
			//Kogger.debug(getClass(), "날짜From : "+f_Date);
			//Kogger.debug(getClass(), "날짜To : "+t_Date);
		
		fw.flush();		
		
		ErpBomMigInfoInterface em = new ErpBomMigInfoInterface();	
		em.getMigResult( itemtype, itemname, f_Date, t_Date, strFileName);
	 }

	public static boolean reSult(String itemtype)
	{
		boolean rs = true;
		 if( !(itemtype.equals("1") || itemtype.equals("2")) )
		 {
			Kogger.debug(BomMigrationCmd.class, "숫자 1 혹은 2 만 입력할 수 있습니다.");
		 }else{
			rs = false;
		 }
		 return rs;
	}
}
