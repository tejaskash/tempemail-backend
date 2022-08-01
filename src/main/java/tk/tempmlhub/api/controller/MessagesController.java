package tk.tempmlhub.api.controller;

import tk.tempmlhub.api.adapter.PollMessageAdapter;
import tk.tempmlhub.api.models.PollMessageRequest;
import tk.tempmlhub.api.models.PollMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Slf4j
public class MessagesController {

    @Resource
    PollMessageAdapter pollMessageAdapter;

    @PostMapping(value = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<PollMessageResponse> pollForMessages(@RequestBody PollMessageRequest pollMessageRequest) {
        log.info("Polling messages for: " + pollMessageRequest.getEmailId());
        try {
            PollMessageResponse pollMessageResponse = pollMessageAdapter
                    .getMessageByEmailAddresses(pollMessageRequest);
            log.info("Retrieved messages for: " + pollMessageRequest.getEmailId());
            return ResponseEntity
                    .ok(pollMessageResponse);
        } catch (Exception e) {
            log.error("Error polling messages for " + pollMessageRequest.getEmailId(), e);
            return ResponseEntity
                    .internalServerError()
                    .body(null);
        }
    }
}
