package sist_atend;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sist_Atend_B {
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private static final int LimiteMaxQuant = 10;
    private static final int TimeSlot = 1;

    public Sist_Atend_B(String databaseName) {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection("product_quantity_requests");
    }

    public void requestProduct(String username, String product, int quantity) {
        Date currentTime = new Date();
        Date timeAgo = new Date(currentTime.getTime() - TimeUnit.MINUTES.toMillis(TimeSlot));

        int totalQuantity = 0;
        FindIterable<Document> documents = collection.find(
                new Document("username", username)
                        .append("product", product) 
                        .append("timestamp", new Document("$gt", timeAgo))
        );
        for (Document doc : documents) {
            totalQuantity += doc.getInteger("quantity");
        }

        if (totalQuantity + quantity > LimiteMaxQuant) {
            System.out.println("Limite excedido. Pedido não atendido para " + username + " - " + product);
        } else {
            Document newRequest = new Document()
                    .append("username", username)
                    .append("product", product)
                    .append("quantity", quantity)
                    .append("timestamp", currentTime);
            collection.insertOne(newRequest);
            System.out.println("Pedido atendido para " + username + " - " + product + " (Quantidade: " + quantity + ")");
        }
    }

    public void printUserQuantReport(String username, int quantity1) {
        long count = collection.countDocuments(new Document("username", username).append("quantity", quantity1));
        System.out.println("Relatório para " + username + " - " + quantity1 + " unidades: " + count + " pedidos.");
    }

    public static void main(String[] args) {
        Sist_Atend_B sistema = new Sist_Atend_B("CBD_Sist_Atend_B");

        System.out.println("\nLimite máximo de " + LimiteMaxQuant + " unidades de produtos por utilizador por " + TimeSlot + " minutos:\n");

        long startTime = System.currentTimeMillis();

        String username1 = "user1";
        String product1 = "product1";
        int quantity1 = 4;

        String username2 = "user2";
        String product2 = "product2";
        int quantity2 = 6;

        sistema.requestProduct(username1, product1, quantity1);
        sistema.requestProduct(username2, product2, quantity2);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("\nTempo total de execução: " + executionTime + " ms");
        
        System.out.println();

        sistema.printUserQuantReport(username1, quantity1);
        sistema.printUserQuantReport(username2, quantity2);
    }
}
