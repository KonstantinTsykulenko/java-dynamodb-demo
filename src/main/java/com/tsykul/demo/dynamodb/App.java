package com.tsykul.demo.dynamodb;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBStreamsClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.util.StringUtils;

@EnableDynamoDBRepositories("com.tsykul.demo.dynamodb.repository")
@SpringBootApplication
@EnableScheduling
public class App
{
    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    public static void main(String... args)
    {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public AWSCredentials awsCredentials()
    {
        return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
    }

    @Bean
    public AmazonDynamoDB amazonDynamoDB(AWSCredentials awsCredentials)
    {
        AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(awsCredentials);
        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint))
        {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }
        return amazonDynamoDB;
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDBClient amazonDynamoDBClient)
    {
        return new DynamoDBMapper(amazonDynamoDBClient);
    }

    @Bean
    public AmazonDynamoDBStreamsClient amazonDynamoDBStreamsAsyncClient(AWSCredentials awsCredentials)
    {
        return new AmazonDynamoDBStreamsClient(awsCredentials);
    }

    @Bean
    public DynamoDB dynamoDB(AmazonDynamoDB amazonDynamoDB)
    {
        return new DynamoDB(amazonDynamoDB);
    }
}
