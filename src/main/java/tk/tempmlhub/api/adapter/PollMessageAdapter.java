package tk.tempmlhub.api.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import tk.tempmlhub.api.models.EmailAddressItem;
import tk.tempmlhub.api.models.PollMessageRequest;
import tk.tempmlhub.api.models.PollMessageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class PollMessageAdapter {

    @Resource(name = "ddbMapperProvider")
    private DynamoDBMapper dynamoDBMapper;

    public PollMessageResponse getMessageByEmailAddresses(PollMessageRequest pollMessageRequest) {
        EmailAddressItem item = dynamoDBMapper.load(EmailAddressItem.class, pollMessageRequest.getEmailId());
        List<String> messageItemList = item.getEmailsS3ObjectKeys();
        return PollMessageResponse.builder()
                .emailID(pollMessageRequest.getEmailId())
                .messageItems(messageItemList)
                .build();
    }

}
