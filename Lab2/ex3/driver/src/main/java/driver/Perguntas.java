package driver;


import java.util.ArrayList;
import java.util.Arrays;
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
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.group;

import org.bson.Document;

// alínea c)

public class Perguntas {

    public static void main(String[] args) {

        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        //prevenir logs do mongo na consola
        Logger mongoLogger = Logger.getLogger( "org.mongodb.driver" );
        mongoLogger.setLevel(Level.SEVERE); 


        System.out.println("6. Liste todos os restaurantes que tenham pelo menos um score superior a 85.");

        try {
            FindIterable<Document> query = collection.find(gt("grades.score", 85));
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("12. Liste o restaurant_id, o nome, a localidade e a gastronomia dos restaurantes localizados em Staten Island, Queens, ou Brooklyn.");

        try {
            FindIterable<Document> query = collection.find(in("localidade", "Staten Island", "Queens", "Brooklyn"))
            .projection(Projections.fields(Projections.include("restaurant_id"), Projections.include("nome"), Projections.include("gastronomia")));

            for (Document document : query) {
                System.out.println(document.toJson());
            }   
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("16. Liste o restaurant_id, o nome, o endereço (address) e as coordenadas geográficas (coord) dos restaurantes onde o 2º elemento da matriz de coordenadas tem um valor superior a 42 e inferior ou igual a 52.");

        try {
            FindIterable<Document> query = collection.find(and(gt("address.coord.1",42),lte("address.coord.1",52))).projection(Projections.fields(Projections.include("restaurant_id"), Projections.include("nome"), Projections.include("address")));
            for (Document document : query) {
                System.out.println(document.toJson());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println("22. Conte o total de restaurantes existentes em cada localidade.");

        try {
            AggregateIterable<Document> aggregation = collection.aggregate(
                Arrays.asList(
                    group("$borough", Accumulators.sum("total", 1))
                )
            );

            for (Document document : aggregation) {
                int totalRestaurants = document.getInteger("total");
                System.out.println("Total de restaurantes: " + totalRestaurants);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();


        System.out.println("24. Apresente o número de gastronomias diferentes na rua 'Fifth Avenue'.");

        try {
            DistinctIterable<String> query = collection.distinct("gastronomia", eq("address.rua", "Fifth Avenue"), String.class);
            ArrayList<Object> gastronomias = query.into(new ArrayList<>());
            int numGastronomias = gastronomias.size();
            System.out.println("Número de gastronomias diferentes na rua 'Fifth Avenue': " + numGastronomias);
        } catch (Exception e){
            e.printStackTrace();
        }

        mongoClient.close();

    }
    
}