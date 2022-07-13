package e3ps.cost.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

import e3ps.groupware.company.People;
import e3ps.groupware.company.PeopleHelper;

public class MailUtil {
    public boolean sendMail(String from,String email_to,String subject,String msg){
        boolean ok = false;
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.ket.com");
        Session sess  = Session.getDefaultInstance(properties, null);
        Message message = new MimeMessage(sess);
        try {
            System.out.println("여기오나???");
            System.out.println("from==>"+from);
            message.setFrom(new InternetAddress(from));   // 보내는 사람  주소

            /*여러명에게 보내기*/
            String[] parmeter = email_to.split(";");
            People people = null;
            boolean sendOk = true;
            String unableMail = "";
            InternetAddress[] address = new InternetAddress[parmeter.length];
            for(int i=0; i<parmeter.length; i++){
            	people = PeopleHelper.manager.getPeople(parmeter[i]);
            	if((people!=null && !people.isIsDisable()) || "neho".equals(parmeter[i])){
            		address[i] = new InternetAddress(parmeter[i]+"@ket.com");
            		System.out.println("to===>"+parmeter[i]);
            	}else{
            		sendOk = false;
            		unableMail = parmeter[i];
            	}
            }
            if(sendOk){
	            message.setRecipients(Message.RecipientType.TO, address); // 수령인
	
	            //한명에게 보내기
	            //message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email_to, false));
	
	
	
	            message.setSubject(subject);                  // 제목
	            message.setSentDate(new Date());              // 보낸 날짜
	            //message.setText(msg); // 글을 문자만 보낼 경우
	            // 글을 HTML 형식으로 보낼 경우
	            message.setContent(msg, "text/html; charset=EUC-KR");
	            message.setHeader("Content-Transfer-Encoding", "quoted-printable");
	
	            Transport.send(message);
	            System.out.println("E-mail successfully sent!");
            }else{
            	System.out.println("퇴사자 E-mail 발송안함 => "+unableMail+"@ket.com");
            }
            ok = true;
        } catch (AddressException e) {
            e.printStackTrace();
            System.out.println("AddressException : " + e);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("MessagingException : " + e);
        }

        return ok;
    }
}



