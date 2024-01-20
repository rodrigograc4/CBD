package driver;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

public class App {
    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("cbd");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE);

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            showMenu();
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    insertDocument(collection);
                    break;
                case 2:
                    updateDocument(collection);
                    break;
                case 3:
                    findDocument(collection);
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }

        mongoClient.close();
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("==== MENU ====");
        System.out.println("1. Inserir dados");
        System.out.println("2. Atualizar dados");
        System.out.println("3. Pesquisar dados");
        System.out.println("9. Sair");
        System.out.println("==============");
        System.out.print("Escolha uma opção: ");
    }

    private static void insertDocument(MongoCollection<Document> collection) {
        System.out.println("Inserindo um novo documento...");

        Document main = new Document("address", new Document("building", "14")
                                                .append("coord", Arrays.asList(-72.889877,"22.842317"))
                                                .append("rua", "Venda Nova")
                                                .append("zipcode", "3605"))
        .append("localidade", "Tomar")
        .append("gastronomia", "Portuguese")
        .append("nome", "O Fernando")
        .append("restaurant_id", "714420")
        .append("grades", new Document()
                            .append("date","23012001")
                            .append("grade","A")
                            .append("score","69"));

        collection.insertOne(main);
    }

    private static void updateDocument(MongoCollection<Document> collection) {
        System.out.println("Atualizando um documento...");

        collection.updateOne(eq("nome", "O Fernando"),Updates.set("address.zipcode","3696"));
    }
    
    private static void findDocument(MongoCollection<Document> collection) {
        System.out.println("Pesquisando documentos por localidade...");

        String parameter = "localidade";
        String value = "Tomar";

        Bson filter = Filters.eq(parameter, value);

        for (Document document : collection.find(filter)) {
            System.out.println(document.toJson());
        }
    }
}