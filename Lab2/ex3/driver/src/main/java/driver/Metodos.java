package driver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.group;

import org.bson.Document;

public class Metodos {

    static MongoCollection<Document> collection;

    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        collection = database.getCollection("restaurants");

        // Prevenir logs do MongoDB na consola
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        System.out.println("Número de localidades distintas: " + countLocalidades());

        System.out.println();

        System.out.println("Número de restaurantes por localidade:");
        Map<String, Integer> restByLocalidade = countRestByLocalidade();
        for (Map.Entry<String, Integer> entry : restByLocalidade.entrySet()) {
            System.out.println(" -> " + entry.getKey() + " - " + entry.getValue());
        }

        System.out.println();

        System.out.println("Nomes de restaurantes contendo 'Park' no nome:");
        List<String> restaurantNames = getRestWithNameContaining("Park");
        for (String nome : restaurantNames) {
            System.out.println(" -> " + nome);
        }

        mongoClient.close();
    }

    public static int countLocalidades() {
        try {
            DistinctIterable<String> query = collection.distinct("localidade", String.class);
            int count = 0;
            for (String localidade : query) {
                count++;
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }    

    public static Map<String, Integer> countRestByLocalidade() {
        Map<String, Integer> restLocalidade = new HashMap<>();
        try {
            AggregateIterable<Document> query = collection.aggregate(Arrays.asList(group("$localidade", Accumulators.sum("count", 1))));
            for (Document document : query) {
                restLocalidade.put(document.getString("_id"), document.getInteger("count"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restLocalidade;
    }

    public static List<String> getRestWithNameContaining(String name) {
        List<String> restaurantNames = new ArrayList<>();
        try {
            FindIterable<Document> query = collection.find(regex("nome", ".*" + name + ".*"));
            for (Document document : query) {
                restaurantNames.add(document.getString("nome"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurantNames;
    }
}
