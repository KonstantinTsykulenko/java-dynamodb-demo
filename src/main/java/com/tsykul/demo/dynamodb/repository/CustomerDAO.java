package com.tsykul.demo.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.ScanSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.tsykul.demo.dynamodb.entity.Customer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDAO
{
    @Autowired
    private DynamoDB       client;
    @Autowired
    private AmazonDynamoDB dynamoDB;
    @Autowired
    private DynamoDBMapper mapper;

    public List<Customer> findByName(String name)
    {
        Map<String, AttributeValue> lastKeyEvaluated;
        do
        {
            Map<String, AttributeValue> values = new HashMap<>();
            values.put(":val", new AttributeValue().withS(name));
            ScanRequest scanRequest = new ScanRequest()
                    .withLimit(100)
                    .withTableName("customers")
                    .withExpressionAttributeValues(values)
                    .withFilterExpression("firstName = :val")
                    .withProjectionExpression("id");

            ScanResult scanResult = dynamoDB.scan(scanRequest);

            scanResult.getItems().stream().forEach(System.out::println);

            lastKeyEvaluated = scanResult.getLastEvaluatedKey();
        }
        while (null != lastKeyEvaluated);

        return null;
    }

    public List<Customer> findPremium()
    {
        QuerySpec spec = new QuerySpec();

        spec
                .withKeyConditionExpression("premium = :val")
                .withValueMap(new ValueMap().withNumber(":val", 1));

        ItemCollection<QueryOutcome> customers = client.getTable("customers").getIndex("premiumIndex").query(spec);

        customers.forEach(System.out::println);

        return null;
    }

    public Customer saveWithMapper(Customer customer)
    {
        mapper.save(customer);
        return customer;
    }

    public Customer saveWithItemApi(Customer customer)
    {
        customer.setId(UUID.randomUUID().toString());
        client.getTable("customers").putItem(
                new Item().withPrimaryKey("id", customer.getId())
                          .with("firstName", customer.getFirstName())
                          .with("lastName", customer.getLastName()));
        return customer;
    }
}
