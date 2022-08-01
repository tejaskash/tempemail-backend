package tk.tempmlhub.api.adapter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import tk.tempmlhub.api.models.EmailAddressItem;
import tk.tempmlhub.api.models.NewEmailAddress;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import static tk.tempmlhub.api.constants.Common.EMAIL_ROOT_DOMAIN;
import static tk.tempmlhub.api.constants.Common.AT;
import static tk.tempmlhub.api.constants.Common.EXPIRATION_TIME_IN_DAYS;

import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

@Service
@Slf4j
public class NewMailAdapter {

    @Resource(name = "faker")
    private List<Faker> fakers;

    @Resource(name = "ddbMapperProvider")
    private DynamoDBMapper dynamoDBMapper;

    public NewEmailAddress getNewEmailAddress() {
        Faker faker = getRandomFaker();
        String username = normaliseUsername(faker.name().username()) + generateRandomDigits();
        String fullEmailAddress = username + AT + EMAIL_ROOT_DOMAIN;
        LocalDateTime expires = LocalDateTime
                .now()
                .plusDays(EXPIRATION_TIME_IN_DAYS);
        createDynamoDBRecord(fullEmailAddress, expires);
        return NewEmailAddress.builder()
                .emailId(username)
                .fullEmailAddress(fullEmailAddress)
                .expires(expires)
                .build();
    }

    private Faker getRandomFaker() {
        return new Faker().options().nextElement(fakers);
    }

    private String normaliseUsername(final String username) {
        return Normalizer.normalize(username, Normalizer.Form.NFD);
    }

    private String generateRandomDigits() {
        Random rand = new Random();
        return String.format("%04d", rand.nextInt(10000));
    }

    private void createDynamoDBRecord(final String username, final LocalDateTime expires) {
        EmailAddressItem newItem = new EmailAddressItem();
        newItem.setUsername(username);
        newItem.setExpires(Date.from(expires.toInstant(ZoneOffset.UTC)));
        newItem.setIsValid(Boolean.TRUE);
        newItem.setEmailsS3ObjectKeys(Collections.emptyList());
        dynamoDBMapper.save(newItem);
    }

}
