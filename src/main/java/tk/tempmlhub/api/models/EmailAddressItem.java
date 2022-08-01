package tk.tempmlhub.api.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@DynamoDBTable(tableName = "email-address-items")
public class EmailAddressItem {

    @DynamoDBHashKey(attributeName = "username")
    private String username;

    @DynamoDBAttribute(attributeName = "emailsS3ObjectKeys")
    private List<String> emailsS3ObjectKeys;

    @DynamoDBAttribute(attributeName = "expires")
    private Date expires;

    @DynamoDBAttribute(attributeName = "isValid")
    private Boolean isValid;
}
