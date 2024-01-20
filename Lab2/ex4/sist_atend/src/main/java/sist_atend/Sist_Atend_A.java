package sist_atend;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Sist_Atend_A {
    private static final int LimiteMaxPedidos = 5;
    private static final int TimeSlot = 1 ;

    private final MongoClient mongoClient;
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;

    public Sist_Atend_A(String databaseName) {
        mongoClient = MongoClients.create();
        database = mongoClient.getDatabase(databaseName);
        collection = database.getCollection("product_requests");
    }

    public void requestProduct(String username, String product, int limit, int timeSlotMinutes) {
        Date currentTime = new Date();
        Date timeAgo = new Date(currentTime.getTime() - TimeUnit.MINUTES.toMillis(timeSlotMinutes));

        long PedidosCount = collection.countDocuments(
                new Document("username", username)
                        .append("product", product)
                        .append("timestamp", new Document("$gt", timeAgo))
        );

        if (PedidosCount >= limit) {
            System.out.println("Limite excedido. Pedido não atendido para " + username + " - " + product);
        } else {
            Document newRequest = new Document()
                    .append("username", username)
                    .append("product", product)
                    .append("timestamp", currentTime);
            collection.insertOne(newRequest);
            System.out.println("Pedido atendido para " + username + " - " + product);
        }
    }

    public void printUserProductReport(String username, String product) {
        long count = collection.countDocuments(new Document("username", username).append("product", product));
        System.out.println("Relatório para " + username + " - " + product + ": " + count + " pedidos.");
    }

    public static void main(String[] args) {
        Sist_Atend_A sistema = new Sist_Atend_A("CBD_Sist_Atend_A");

        String username1 = "user1";
        String product1 = "product1";

        String username2 = "user2";
        String product2 = "product2";

        System.out.println("\nLimite máximo de " + LimiteMaxPedidos + " produtos por utilizador por " + TimeSlot + " minutos:\n");

        long startTime = System.currentTimeMillis();

        sistema.requestProduct(username1, product1, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username2, product2, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username1, product1, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username2, product2, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username1, product1, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username2, product2, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username1, product1, LimiteMaxPedidos, TimeSlot);
        sistema.requestProduct(username2, product2, LimiteMaxPedidos, TimeSlot);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("\nTempo total de execução: " + executionTime + " ms");

        System.out.println();

        sistema.printUserProductReport(username1, product1);
        sistema.printUserProductReport(username2, product2);
    }
}
