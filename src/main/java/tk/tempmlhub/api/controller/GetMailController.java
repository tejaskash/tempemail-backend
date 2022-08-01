package tk.tempmlhub.api.controller;

import tk.tempmlhub.api.adapter.GetMailAdapter;
import tk.tempmlhub.api.models.GetMailResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class GetMailController {

    @Resource
    GetMailAdapter getMailAdapter;

    @GetMapping(value = "/mail/{s3ObjectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<GetMailResponse> getMail(@PathVariable String s3ObjectId) {
        log.info("Retrieving s3ObjectId :"+ s3ObjectId);
        try {

            GetMailResponse getMailResponse = getMailAdapter.getProcessedEmail(s3ObjectId);
            log.info("Retrieved Mail For : " + s3ObjectId);
            return ResponseEntity
                    .ok(getMailResponse);

        } catch (MessagingException e) {
            log.error("Exception processing getProcessedEmail for : " + s3ObjectId, e);
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
        catch (IOException e) {
            log.error("Exception processing getProcessedEmail for : " + s3ObjectId, e);
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }

    }
}
