package utils;

import com.reporter.model.OrderedItems;
import com.reporter.model.Users;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {
	public static void sendEmail(OrderedItems itemDetails, Users user) throws AddressException, MessagingException, IOException {
		try {
		List<String> attachments = Sharepoint.sharepoint(user, itemDetails.getAttachments());

		
		Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "smtp.gmail.com");
		   props.put("mail.smtp.port", "587");
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("no-reply@derevasystems.com", "G0d$mack1");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("jrogers@derevasystems.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(itemDetails.getEmail()));
		   msg.setSubject("Subject Results");
		   msg.setContent("Tutorials point email", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent(itemDetails.getMessage(), "text/html");
		   
		   
		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		   
		   for (int i=0; i<attachments.size(); i++) 
		   {
			   MimeBodyPart attachPart = new MimeBodyPart();
			   attachPart.attachFile(attachments.get(i));
			   multipart.addBodyPart(attachPart);
			   msg.setContent(multipart);
		   }
		   
		   
		   Transport.send(msg);
	    } catch (Exception ex) {
	    System.out.println("Error reading content!!");
	    ex.printStackTrace();
	    }
		   return;
		}

}
