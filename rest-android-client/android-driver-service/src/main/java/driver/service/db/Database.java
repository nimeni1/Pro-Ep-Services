package driver.service.db;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import driver.service.models.Fare;
import driver.service.serializer.JsonSerializer;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Database {

    private static final String DATABASE_URL = "mongodb+srv://MihaiMotpan:proepproject@proepproject-l768t.mongodb.net/test?retryWrites=true";
    private static final String MAIN_DATABASE = "ProEPProject";
    private MongoClient mongoClient;
    private MongoDatabase db;

    public Database() {
        mongoClient = new MongoClient(new MongoClientURI(DATABASE_URL));
        db = mongoClient.getDatabase(MAIN_DATABASE);
    }

    public void insertDocumentInCollection(MongoCollection<Document> collection, String json) {
        collection.insertOne(new Document(JsonSerializer.jsonToMap(json)));
    }

    public String findDocument(MongoCollection<Document> collection, Map<String,Object> json) {
        String result;
        FindIterable<Document> documents = collection.find(new BasicDBObject(json));
        Document document = documents.first();
        if (document != null) {
            result = document.toJson();
        } else {
            throw new MongoException("No document found!");
        }
        return result;
    }

    public String getDocumentFromCollection(MongoCollection<Document> collection, Map<String,Object> json) {
        FindIterable<Document> documents = collection.find(new BasicDBObject(json));
        Document result = documents.first();
        return JsonSerializer.mapToJson(result);
    }

    public String getDocumentsFromCollection(MongoCollection<Document> collection, Map<String,Object> json) {
        List<Fare> documents = new ArrayList<>();
        FindIterable<Document> iterDoc = collection.find(new BasicDBObject(json));
        for (Document document : iterDoc) {
            documents.add(JsonSerializer.toFare(document.toJson()));
        }
        return JsonSerializer.arrayListToJson(documents);
    }

    public void deleteDocumentFromCollection(MongoCollection<Document> collection, Map<String,Object> json) {
        collection.deleteOne(new Document(json));
    }

    public void updateDocumentInCollection(MongoCollection<Document> collection, Map<String,Object> oldObject, Map<String,Object> newObject) {
        String key = oldObject.keySet().iterator().next();
        Bson bson = Filters.eq(key,oldObject.get(key));
        for (String field: newObject.keySet()) {
            Bson newBson = Updates.set(field,newObject.get(field));
            collection.findOneAndUpdate(bson,newBson);
        }
    }

    public MongoCollection<Document> getCarsCollection() {
        return db.getCollection("cars");
    }

    public MongoCollection<Document> getClientsCollection() {
        return db.getCollection("clients");
    }

    public MongoCollection<Document> getDriverCollection() {
        return db.getCollection("drivers");
    }

    public MongoCollection<Document> getFaresCollection() {
        return db.getCollection("fares");
    }

    public MongoCollection<Document> getRatingsCollection() {
        return db.getCollection("ratings");
    }

    public void closeConnection() {
        mongoClient.close();
    }
}
