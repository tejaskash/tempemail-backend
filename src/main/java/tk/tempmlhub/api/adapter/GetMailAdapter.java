package tk.tempmlhub.api.adapter;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import tk.tempmlhub.api.models.GetMailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static tk.tempmlhub.api.constants.Common.EMAIL_S3_BUCKET;

import javax.annotation.Resource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GetMailAdapter {

    @Resource(name = "s3ClientProvider")
    private AmazonS3 amazonS3;

    public GetMailResponse getProcessedEmail(String s3ObjectId) throws MessagingException, IOException {

            S3Object emailFile = amazonS3.getObject(buildGetObjectRequest(s3ObjectId));
            MimeMessage mimeMessage = new MimeMessage(Session.getDefaultInstance(new Properties()),
                    emailFile.getObjectContent());

            String subject = mimeMessage.getSubject();
            List<String> senders = getAddresses(Arrays.asList(mimeMessage.getFrom()));
            List<String> recipients = getAddresses(Arrays.asList(mimeMessage.getAllRecipients()));
            Date date = mimeMessage.getSentDate();

            MimeMultipart mm =(MimeMultipart) mimeMessage.getContent();
            int ct = mm.getCount();

            String textContent = "";
            String htmlContent = "";

            for(int i=0;i<ct;i++) {
                BodyPart bp = mm.getBodyPart(i);
                if (bp.isMimeType("text/plain"))
                    textContent = textContent + "\n" + bp.getContent();
                if (bp.isMimeType("text/html"))
                    htmlContent = htmlContent + "\n" + bp.getContent();
            }

            return GetMailResponse
                    .builder()
                    .date(date)
                    .subject(subject)
                    .htmlContent(htmlContent)
                    .textContent(textContent)
                    .senders(senders)
                    .recipients(recipients)
                    .build();
    }

    private List<String> getAddresses(List<Address> addressList) {
        return addressList.stream()
                .parallel()
                .map(address -> {
                    InternetAddress internetAddress = (InternetAddress) address;
                    return internetAddress.getAddress();
                })
                .collect(Collectors.toList());
    }

    private GetObjectRequest buildGetObjectRequest(String s3ObjectId) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(EMAIL_S3_BUCKET, s3ObjectId);
        return getObjectRequest;
    }
}
