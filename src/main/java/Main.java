import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class Main {

    public static void main(String[] args){

        //simpleMessage();

        simpleFileAttachment();
    }

    public static void simpleMessage(){
        String to = "";                                                 // sender email
        String from = "";                                               // receiver email
        String host = "smtp.mail.ru";                                   // mail server host
        String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.port", 465);
        props.put("mail.debug", "true");
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.mail.ru");
        props.put("mail.user", from);
        props.put("mail.password", password);


        // Получение объекта Session по умолчанию
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication  getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                from, password);
                    }
                });

        try {
            // Создание объекта MimeMessage по умолчанию
            MimeMessage message = new MimeMessage(session);

            // Установить От: поле заголовка
            message.setFrom(new InternetAddress(from));

            // Установить Кому: поле заголовка
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            /*  Несколько адресатов:
            Address[] cc = new Address[] {
                    new InternetAddress(to),
                    new InternetAddress(to),
                    new InternetAddress(to)};
            message.addRecipients(Message.RecipientType.CC, cc);
            */

            // Установить тему: поле заголовка
            message.setSubject("Это тема письма!");

            // Теперь установите фактическое сообщение
            message.setText("Это актуальное сообщение");

            // Отправить сообщение
            Transport.send(message);
            System.out.println("Сообщение успешно отправлено....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }

    public static void simpleFileAttachment(){

        String to = "";
        String from = "";
        String host = "smtp.mail.ru";
        String password = "";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.port", 465);
        props.put("mail.debug", "true");
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.mail.ru");
        props.put("mail.user", from);
        props.put("mail.password", password);


        // Получение объекта Session по умолчанию
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication  getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                from, password);
                    }
                });

        try {

            // Создание объекта MimeMessage по умолчанию
            MimeMessage message = new MimeMessage(session);

            // Установить От: поле заголовка
            message.setFrom(new InternetAddress(from));

            // Установить Кому: поле заголовка
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            Multipart multipart = new MimeMultipart("related");
            // Create the message part
            BodyPart messageBodyPart;
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(""); // msgbody contains the contents of the html file
            messageBodyPart.setHeader("Content-Type", "text/html");
            multipart.addBodyPart(messageBodyPart);

            //add file attachments
            DataSource source;
            File file = new File("D:/Develop/Java/MailRuJava/Mail.ru_Java/sifu.jpg");
            if(file.exists()){
                // add attachment
                messageBodyPart = new MimeBodyPart();
                source = new FileDataSource(file);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(file.getName());
                messageBodyPart.setHeader("Content-ID", "<BarcodeImage>");
                messageBodyPart.setDisposition("inline");
                multipart.addBodyPart(messageBodyPart);
            }

            // Put parts in message
            message.setContent(multipart);

            // Отправить сообщение
            Transport.send(message);
            System.out.println("Сообщение успешно отправлено....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
