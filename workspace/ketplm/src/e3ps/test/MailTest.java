package e3ps.test;

import e3ps.common.mail.SendMail;
import ext.ket.shared.log.Kogger;

public class MailTest {

	/*
	  @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sendMail();
	}

	public static void sendMail(){


		try{
			SendMail mail = new SendMail() ;

			   mail.setFromMailAddress("slgmoney@ket.com","Administrator");

			   //mail.setToMailAddress("uts121@naver.com", "uts121");
			   mail.setToMailAddress("neho@ket.com", "neho");
			   //mail.setCcMailAddress("eee@fff.com", "CC로받는사람");
			   //mail.setBccMailAddress("ggg@hhh.com", "BCC로받는사람");

			    mail.setSubject(" MAIL 제목을 넣으면 됩니다.");

			    String message = " Text 메일 메시지 내용 " ;
			    String htmlMessage = "<html><font color='red'> HTML 메일 메시지 내용</font></html>" ;
			    String[] fileNames = { "c:/attachFile1.zip","c:/attachFile2.txt"   } ;

			    mail.setHtmlAndFile(htmlMessage,fileNames);
			    //mail.setHtml(htmlMessage);
			    //mail.setText(message);

			     mail.send();  // 메일 전송
		}catch(Exception e){
		    Kogger.error(MailTest.class, e);
		}

	}

}
