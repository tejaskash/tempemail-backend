package tk.tempmlhub.api.controller;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMappingException;
import tk.tempmlhub.api.adapter.NewMailAdapter;
import tk.tempmlhub.api.models.NewEmailAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController()
@RequestMapping("/api")
@Slf4j
public class NewEmailAddressController {

    @Resource
    private NewMailAdapter newMailAdapter;

    @GetMapping("/newmail")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<NewEmailAddress> getNewEmailAddress() {
        NewEmailAddress newEmailAddress;
        log.info("Creating new email address");
        try {
            newEmailAddress = newMailAdapter.getNewEmailAddress();
            return ResponseEntity.ok(newEmailAddress);
        } catch (DynamoDBMappingException exception) {
            log.error("Error Generating new email address", exception);
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
