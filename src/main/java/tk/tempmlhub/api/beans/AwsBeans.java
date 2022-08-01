package tk.tempmlhub.api.beans;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.tempmlhub.api.constants.Common;

@Configuration
public class AwsBeans {

    @Bean(name = "s3ClientProvider")
    public AmazonS3 getS3Client(@Autowired ProfileCredentialsProvider profileCredentialsProvider) {
        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(profileCredentialsProvider)
                .build();
    }

    @Bean(name = "ddbMapperProvider")
    public DynamoDBMapper getDynamoDBMapper(@Autowired AmazonDynamoDB amazonDynamoDBClient) {
        return new DynamoDBMapper(amazonDynamoDBClient);
    }

    @Bean(name = "ddbClientProvider")
    public AmazonDynamoDB getDynamoDBClient(@Autowired ProfileCredentialsProvider profileCredentialsProvider) {
        return AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.US_WEST_2)
                .withCredentials(profileCredentialsProvider)
                .build();
    }

    @Bean(name = "profileCredentialsProvider")
    public ProfileCredentialsProvider getProfileCredentials() {
        return new ProfileCredentialsProvider(Common.AWS_CREDENTIALS_PROFILE);
    }
}
