package mx.gob.segob.nsjp.web.email.action;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import mx.gob.segob.nsjp.web.email.form.EmailForm;

import org.apache.log4j.Logger;


public class EnviarEmail{
	
	/** Log de clase */
	private static final Logger log = Logger
			.getLogger(EnviarEmail.class);
	

	public void enviarCorreoAvisoConclusionAudiendicia(EmailForm form){
		   
		   try{
			   
			   final String user = form.getUser();
			   final String pass = form.getPass();
	
	    	// Propiedades de la conexión
	          Properties props = new Properties();
	          props.put("mail.smtp.starttls.enable", form.getStarttls());
	          props.setProperty("mail.smtp.port", form.getPort());
	          props.setProperty("mail.smtp.auth", form.getAuth());
	          props.put("mail.transport.protocol", form.getProtocol());
	          
	          // Preparamos la sesion
	          Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pass);
				}
			  });
	
	          // Construimos el mensaje
	          MimeMessage message = new MimeMessage(session);
	          message.setFrom(new InternetAddress(form.getUser()));
	          
	          //Agregamos los destinatarios a los que se les enviara el mensaje	          
	          for(String email : form.getTo()){
	        	  message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
	          }
	          
	          //Agregamos el asunto del mensaje
	          message.setSubject(form.getSubject());
	          //Agregamos en cuerpo del mensaje
	          MimeBodyPart messageBodyPart = new MimeBodyPart();
	          messageBodyPart.setContent(form.getContext(),"text/html"); 
	          Multipart multipart = new MimeMultipart();
	          multipart.addBodyPart(messageBodyPart);
	          messageBodyPart = new MimeBodyPart();
	          message.setContent(multipart); 
	
	          // Enviamos el msj
	          Transport t = session.getTransport();
	          t.connect(form.getHost(), user, pass);
	          t.sendMessage(message, message.getAllRecipients());
	
	          // Cierre.
	          t.close();
	      
	
	         log.info("Envio de notificacion exitoso... ");
	         
	      }catch (MessagingException me) {
	         log.error("Error al tratar de enviar el correo " , me);
	      }catch (Exception e) {
	    	  log.error("Error al tratar de enviar el correo " , e);
	      }
	  
      
   }
   
  
   
}

 