<%@page import="com.fasoo.fcwpkg.packager.*"%>
<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.io.FileNotFoundException" %>
<%@ page import="java.io.IOException" %>


<%

//		GetFileType() �Լ��� ���� �� ����
//
//	20  : ������ ã�� �� �����ϴ�. 
//	21  : ���� ����� 0 �Դϴ�.
//	22  : ������ ���� �� �����ϴ�.
//	29  : ��ȣȭ ������ �ƴ� �Ϲ������Դϴ�.
//	26  : FSD Type�� ��ȣȭ �����Դϴ�.
//	105 : Wrapsody Type�� ��ȣȭ �����Դϴ�.
//	101 : MarkAny Type�� ��ȣȭ �����Դϴ�.
//	104 : INCAPS(�Ｚ PC DRM) Type�� ��ȣȭ �����Դϴ�.
//	103 : FSN Type�� ��ȣȭ �����Դϴ�.
 

String drm_fsdinit_path = "D:\\fasoo\\key\\fsdinit";	// fsdinit ���� FullPath ���� 

String domain_id = "0100000000001454";					// �ѱ����ڰ��� ���� �ڵ�

String ORGFileDir = "D:\\fasoo\\sample\\org\\";			// ��ȣȭ ��� ���� FullPath
String DRMFileDir = "D:\\fasoo\\sample\\DRM\\";			// ��ȣȭ �� ���� FullPath

String Filename = "test.dwg";							// ���ϸ�

String SecurityCode = "08f7268981f048e692edb1fd838065fe";		// ����ڵ�(�Ϲ�)
// �� ��޿� �°� SecurityCode ���� �ʿ�
// a8453c90cd6344629cbfa0d0ded19be4		1�޺��
// 8486b2085ff34f0fa51098d2944b7d5c		2�޺��
// 3e266ff174164b7683540762bdef4d81		�κ���
// 08f7268981f048e692edb1fd838065fe		�Ϲ� ���
// bde693e21c0d47d1b76b037f29ccc3d7		��ܺ� ���

int iErrCheck = 0; 
String sErrMessage = "";

try {
 
		int iBret = 0;
		WorkPackager oWorkPackager = new WorkPackager();
 		try{
 			iBret = oWorkPackager.GetFileType(	ORGFileDir+Filename );	// ���� Ÿ�� üũ	
 		}catch(Exception e){
 			e.printStackTrace();
 		}
		

		if (iBret == 29) {		// �Ϲ� ������ ��� ��ȣȭ

			boolean nret = false;
			 
		
	
			nret = oWorkPackager.IsSupportFile( drm_fsdinit_path,
					                          domain_id, 
					                          ORGFileDir+Filename);	// ���� Ȯ���� ���� 
			System.out.println("nret"+nret);
			if (nret){
				
					oWorkPackager.setOverWriteFlag(false);		// OverWrite  true/false

					boolean bret = oWorkPackager.DoPackagingFsn2( drm_fsdinit_path,
								  domain_id,
								  ORGFileDir+Filename,
								  DRMFileDir+Filename,		
								  Filename,
								  "admin", 												// ������ ID > �����ý��۸� �°� ���� �ʿ� (ex. GROUPWARE)
								  "Administrator", 										// ������ �̸� > �����ý��۸� �°� ���� �ʿ� (ex. GROUPWARE)
								  "admin","Administrator","COMPANY","VMWARE",			// ������ ID, �̸�, �μ��ڵ�, �μ��� > �����ý��۸� �°� ���� �ʿ� (ex. GROUPWARE)	
								  "admin","Administrator","COMPANY","VMWARE",			// �����ý��۸� �°� ���� �ʿ� (ex. GROUPWARE)
								  SecurityCode
							  );
					if (!bret) {
						if( oWorkPackager.getLastErrorNum() == 11 ){
					 					
						}else{
							System.out.println("��ȣȭ �� �����Դϴ�.");
							System.out.println(" ���� ����..");
							System.out.println("    ["+ oWorkPackager.getLastErrorNum()+"] "+oWorkPackager.getLastErrorStr());
							iErrCheck = 1;
							sErrMessage = oWorkPackager.getLastErrorStr();
						}

					}else{
						System.out.println("��ȣȭ���� Packaged FileName : "+oWorkPackager.getContainerFileName()+"");

					}	

				} else {
					System.out.println(" ���� ����..");
					System.out.println("    ["+ oWorkPackager.getLastErrorNum()+"] "+oWorkPackager.getLastErrorStr());
				}
			}else{		// �������� �ʴ� Ȯ���� ������ ���

				if (oWorkPackager.getLastErrorNum()==0) {
					 
				} else {
					System.out.println(" ���� ����..");
					System.out.println("    ["+ oWorkPackager.getLastErrorNum()+"] "+oWorkPackager.getLastErrorStr());
				}

			}
 
 
		if ( iErrCheck == 0 ) {
			 
		}else{
			System.out.println("Download Action Error [message]: "+sErrMessage);
		}
		 
	 


}catch( Exception e ){
	System.out.println("<script>");
	System.out.println("alert('�ý��ۿ� ������ �߻� �߽��ϴ�. �����ڿ��� ���� ���ֽʽÿ�.');");
	System.out.println("</script>");
}finally{
 
	 
 
}
%>	
