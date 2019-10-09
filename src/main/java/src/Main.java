package src;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Arrays;

import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

public class Main {

    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);

        MongoDatabase mongoDatabase = mongoClient.getDatabase("vehicels"); //db
        MongoCollection mongoCollection = mongoDatabase.getCollection("cars"); // collection - tabela

//        save(mongoCollection);
//        read(mongoCollection);
//        readByParam(mongoCollection, "Mark", "Ford");
//        readByParamMany(mongoCollection, "Mark", "Ford");
//        delete(mongoCollection, "Mark", "Ford");
//        update(mongoCollection);

        MongoCollection mongoCollectionBike = mongoDatabase.getCollection("bike"); // collection - tabela

        saveBike(mongoCollectionBike);
    }

    private static void saveBike(MongoCollection mongoCollectionBike) {

        Document document = new Document();
        document.put("name", "Paweł");
        document.put("owner", new Person("Pawel", "Bajda"));

        Document documentPerson = new Document();
        documentPerson.put("Name", "Paweł");
        documentPerson.put("Surname", "Bajda");

        document.put("owner", documentPerson);

        mongoCollectionBike.insertOne(document);
    }

    private static void update(MongoCollection mongoCollection) {

        Bson eq = Filters.eq("Mark", "Ford");
        Bson newDocument = combine(set("Model", "Fiesta"), set("Color", "Black"));

        mongoCollection.updateOne(eq, newDocument);
    }

    private static void delete(MongoCollection mongoCollection, String param, String value) {
        Document document = new Document();
        document.put(param, value);
        mongoCollection.deleteMany(document);
    }

    private static void readByParamMany(MongoCollection mongoCollection, String param, String value) {
        Document document = new Document();
        document.put(param, value);
        MongoCursor iterator = mongoCollection.find(document).iterator();
        while (iterator.hasNext()){
           Document next = (Document) iterator.next();
            System.out.println(next.toJson());
        }
    }

    private static void readByParam(MongoCollection mongoCollection, String param, Object value) {
        Document document = new Document();
        document.put(param, value);
        Document first = (Document) mongoCollection.find(document).first();
        System.out.println(first.toJson());

    }

    private static void read(MongoCollection mongoCollection) {

        Document first = (Document) mongoCollection.find().first();
        System.out.println(first.toJson());
    }

    private static void save(MongoCollection mongoCollection) {

//        Document document1 = new Document();
//        document1.put("Mark", "Fiat");
//        document1.put("Model", "126p");

        Document document2 = new Document();
        document2.put("Mark", "Ford");
        document2.put("Model", "Focus");

//        Document document3 = new Document();
//        document3.put("Mark", "Audi");
//        document3.put("Model", "A5");
//        document3.put("Color", Arrays.asList("Red", "Black", "Grey"));

        mongoCollection.insertMany(Arrays.asList(document2));
    }
}
