package driver;


import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import static com.mongodb.client.model.Filters.*;

import org.bson.Document;
import org.bson.conversions.Bson;

// alínea b)

public class Indices {
    
    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");


        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); 


        System.out.println("=== COM INDICES (Localidade, Gastronomia e Nome) ===");
        
        try {
            collection.createIndex(Indexes.ascending("localidade"));
        } catch (Exception e) {
            System.err.println("Erro ao criar um indice ascendente para localidade: " + e);
        }

        try {
            collection.createIndex(Indexes.ascending("gastronomia"));
        } catch (Exception e) {
            System.err.println("Erro ao criar um indice ascendente para gastronomia: " + e);
        }

        try {
            collection.createIndex(Indexes.text(("nome")));
        } catch (Exception e) {
            System.err.println("Erro ao criar um indice de texto para nome: " + e);
        }

        Bson filter = Filters.and(Filters.eq(gt("grades.score", 70)));

        long startTime = System.currentTimeMillis();
        for (Document document : collection.find(filter)) {
            System.out.println(document.toJson());
        }
        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Pesquisa com indices: " + estimatedTime + " ms.");

        System.out.println("=== SEM INDICES ===");

        filter = Filters.and(Filters.eq(gt("grades.score", 70)));

        startTime = System.currentTimeMillis();
        for (Document document : collection.find(filter)) {
            System.out.println(document.toJson());
        }
        estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("Pesquisa sem indices: " + estimatedTime + " ms.");


        mongoClient.close();
    }

}