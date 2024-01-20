package sist_atend;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBPerformanceTest {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("performance_test");
        MongoCollection<Document> collection = database.getCollection("data");

        int numberOfOperations = 10000; // Número de operações para testar

        // Medir desempenho no MongoDB
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfOperations; i++) {
            Document document = new Document("key", "key" + i).append("value", "value" + i);
            collection.insertOne(document);
            Document result = collection.find(new Document("key", "key" + i)).first();
            collection.deleteOne(result);
            

        }
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Desempenho do MongoDB: " + executionTime + " ms para " + numberOfOperations + " operações.");

        mongoClient.close();
    }
}
