<%@page import="com.fasoo.fcwpkg.packager.*"%>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.IOException" %>


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
 

String drm_fsdinit_path = "D:\\fasoo\\key\\fsdinit";	// fsdinit 폴더 FullPath 설정 

String domain_id = "0100000000001454";					// 한국단자공업 고유 코드

String ORGFileDir = "D:\\fasoo\\sample\\org\\";			// 암호화 대상 문서 FullPath
String DRMFileDir = "D:\\fasoo\\sample\\DRM\\";			// 암호화 된 문서 FullPath

String Filename = "test.dwg";							// 파일명

String SecurityCode = "08f7268981f048e692edb1fd838065fe";		// 등급코드(일반)
// 각 등급에 맞게 SecurityCode 변경 필요
// a8453c90cd6344629cbfa0d0ded19be4		1급비밀
// 8486b2085ff34f0fa51098d2944b7d5c		2급비밀
// 3e266ff174164b7683540762bdef4d81		인비등급
// 08f7268981f048e692edb1fd838065fe		일반 등급
// bde693e21c0d47d1b76b037f29ccc3d7		대외비 등급

int iErrCheck = 0; 
String sErrMessage = "";

try {
 
		int iBret = 0;
		WorkPackager oWorkPackager = new WorkPackager();
 		try{
 			iBret = oWorkPackager.GetFileType(	ORGFileDir+Filename );	// 파일 타입 체크	
 		}catch(Exception e){
 			e.printStackTrace();
 		}
		

		if (iBret == 29) {		// 일반 문서일 경우 암호화

			boolean nret = false;
			 
		
	
			nret = oWorkPackager.IsSupportFile( drm_fsdinit_path,
					                          domain_id, 
					                          ORGFileDir+Filename);	// 지원 확장자 구분 
			System.out.println("nret"+nret);
			if (nret){
				
					oWorkPackager.setOverWriteFlag(false);		// OverWrite  true/false

					boolean bret = oWorkPackager.DoPackagingFsn2( drm_fsdinit_path,
								  domain_id,
								  ORGFileDir+Filename,
								  DRMFileDir+Filename,		
								  Filename,
								  "admin", 												// 소유자 ID > 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
								  "Administrator", 										// 소유자 이름 > 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
								  "admin","Administrator","COMPANY","VMWARE",			// 소유자 ID, 이름, 부서코드, 부서명 > 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)	
								  "admin","Administrator","COMPANY","VMWARE",			// 업무시스템명에 맞게 변경 필요 (ex. GROUPWARE)
								  SecurityCode
							  );
					if (!bret) {
						if( oWorkPackager.getLastErrorNum() == 11 ){
					 					
						}else{
							System.out.println("암호화 중 오류입니다.");
							System.out.println(" 오류 정보..");
							System.out.println("    ["+ oWorkPackager.getLastErrorNum()+"] "+oWorkPackager.getLastErrorStr());
							iErrCheck = 1;
							sErrMessage = oWorkPackager.getLastErrorStr();
						}

					}else{
						System.out.println("암호화성공 Packaged FileName : "+oWorkPackager.getContainerFileName()+"");

					}	

				} else {
					System.out.println(" 오류 정보..");
					System.out.println("    ["+ oWorkPackager.getLastErrorNum()+"] "+oWorkPackager.getLastErrorStr());
				}
			}else{		// 지원하지 않는 확장자 파일일 경우

				if (oWorkPackager.getLastErrorNum()==0) {
					 
				} else {
					System.out.println(" 오류 정보..");
					System.out.println("    ["+ oWorkPackager.getLastErrorNum()+"] "+oWorkPackager.getLastErrorStr());
				}

			}
 
 
		if ( iErrCheck == 0 ) {
			 
		}else{
			System.out.println("Download Action Error [message]: "+sErrMessage);
		}
		 
	 


}catch( Exception e ){
	System.out.println("<script>");
	System.out.println("alert('시스템에 문제가 발생 했습니다. 관리자에게 문의 해주십시요.');");
	System.out.println("</script>");
}finally{
 
	 
 
}
%>	
