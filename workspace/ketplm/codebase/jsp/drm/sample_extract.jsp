<%@page import="com.fasoo.fcwpkg.packager.WorkPackager"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"%>
<%@ page import="java.io.*" %> 
<%@ page import="java.util.*" %> 




<%
//		GetFileType() 함수의 리턴 값 설명
//
//	20  : 파일을 찾을 수 없습니다. 
//	21  : 파일 사이즈가 0 입니다.
//	22  : 파일을 읽을 수 없습니다.
//	29  : 암호화 파일이 아닌 일반파일입니다.
//	26  : FSD Type의 암호화 파일입니다.
//	105 : Wrapsody Type의 암호화 파일입니다.
//	101 : MarkAny Type의 암호화 파일입니다.
//	104 : INCAPS(삼성 PC DRM) Type의 암호화 파일입니다.
//	103 : FSN Type의 암호화 파일입니다.
//
 


String drm_fsdinit_path = "D:\\fasoo\\key\\fsdinit";	// fsdinit 폴더 FullPath 설정 

String domain_id = "";
String ACLIDValue = "";
String SecurityCodeValue = "";
String CPIDValue = "";
Hashtable GetHeaderstr = null;	

String ORGFileDir = "D:\\fasoo\\sample\\org\\";			// 복호화 된 문서 FullPath
String DRMFileDir = "D:\\fasoo\\sample\\DRM\\";			// 복호화 대상 문서 FullPath
String Filename = "test.dwg";							// 파일명

String sErrMessage = "";
int error_num = 0;
String error_str = "";
int Error_Check = 0;
String Error_Message = "";
	
try {
	 
	int iBret = 0;
	WorkPackager oWorkPackager = new WorkPackager();
		
	iBret = oWorkPackager.GetFileType(DRMFileDir+Filename );		// 파일 타입 체크

	if (iBret == 26 || iBret == 103) {			// 암호화 문서일 경우 복호화
 
	System.out.println("File type ? " + iBret);
 
	boolean bRet = false;
	
		GetHeaderstr = oWorkPackager.GetFileHeaderW(DRMFileDir+Filename);
		
		for(Enumeration e = GetHeaderstr.keys(); e.hasMoreElements(); )
			{
				String msgName = (String)e.nextElement();
				String msgValue = (String)GetHeaderstr.get(msgName);
				System.out.println(msgName + "=" + msgValue);
			}

        int i = 0;
        SecurityCodeValue = (String)GetHeaderstr.get("misc2-info");
        System.out.println("SecurityCode:"+SecurityCodeValue);			// 등급코드
		
		CPIDValue = (String)GetHeaderstr.get("CPID");
        System.out.println("CPIDValue:"+CPIDValue);
        domain_id = CPIDValue;
        
		System.out.println("File type ? " + iBret);
	 	    
			 
	 		bRet = oWorkPackager.DoExtract(drm_fsdinit_path, domain_id, DRMFileDir+Filename, ORGFileDir+Filename);
		
				error_num = oWorkPackager.getLastErrorNum();
				error_str = oWorkPackager.getLastErrorStr();
		 	  
				if (error_num!=0){
					System.out.println("error_num = ? " + error_num);
					System.out.println("error_str = ?[ " + error_str+" ]");
		
					Error_Check = 1;
					Error_Message = "DRM_PKGING_ERROR";
				}
			 	else
				{
			 		System.out.println("FSD 문서 복호화");
			 		System.out.println("DoExtract Success!!! ");
			
				}
						
			
	} else {
	 			 Error_Check = 1;
				 Error_Message = "NOT Support File";
	} 
		

}catch( Exception e ){
 
	System.out.println("Error_Check num = :"+error_num);
	System.out.println("error_str = :"+error_str);
	System.out.println("Error_Message = :"+Error_Message);
 
}finally{
}
	 
 
%>
