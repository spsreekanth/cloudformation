AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard() 
.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
.withRegion(Regions.US_WEST_2).build(); 
DynamoDB dynamoDB = new DynamoDB(client); 

Table table = dynamoDB.getTable("Reply"); 

QuerySpec spec = new QuerySpec() 
    .withKeyConditionExpression("Id = :v_id") 
    .withValueMap(new ValueMap() 
        .withString(":v_id", "Amazon DynamoDB#DynamoDB Thread 1"));

ItemCollection<QueryOutcome> items = table.query(spec);



Map<String, AttributeValue> expressionAttributeValues = 
    new HashMap<String, AttributeValue>();
expressionAttributeValues.put(":val", new AttributeValue().withN("0")); 
        
ScanRequest scanRequest = new ScanRequest()
    .withTableName("ProductCatalog")
    .withFilterExpression("Price < :val")
    .withProjectionExpression("Id")
    .withExpressionAttributeValues(expressionAttributeValues);


ScanResult result = client.scan(scanRequest);
for (Map<String, AttributeValue> item : result.getItems()) {
    printItem(item);
}