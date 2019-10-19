package admin.service.mongo.db;

import admin.service.models.DateTime;
import admin.service.models.Fare;
import admin.service.serializer.JsonSerializer;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.bulk.DeleteRequest;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class Database {

    private static final String DATABASE_URL = "mongodb+srv://MihaiMotpan:proepproject@proepproject-l768t.mongodb.net/test?retryWrites=true";
    private static final String MAIN_DATABASE = "ProEPProject";
    private static MongoClient mongoClient;
    private static MongoDatabase db;

    public Database() {
        mongoClient = new MongoClient(new MongoClientURI(DATABASE_URL));
        db = mongoClient.getDatabase(MAIN_DATABASE);
    }

    public void insertDocumentInCollection(MongoCollection<Document> collection, String json) {
        collection.insertOne(new Document(JsonSerializer.jsonToMap(json)));
    }

    public String getDocumentFromCollection(MongoCollection<Document> collection, Map<String,Object> json) {
        FindIterable<Document> documents = collection.find(new BasicDBObject(json));
        Document result = documents.first();
        if (result == null || result.isEmpty()) throw new DatabaseException("No document was retrieved!");
        return JsonSerializer.mapToJson(result);
    }

    public String getDocumentsFromCollection(MongoCollection<Document> collection, Map<String,Object> json) {
        List<Fare> documents = new ArrayList<>();
        FindIterable<Document> iterDoc = collection.find(new BasicDBObject(json));
        if (!iterDoc.iterator().hasNext()) throw new DatabaseException("No documents found!");
        for (Document document : iterDoc) {
            documents.add(JsonSerializer.toFare(document.toJson()));
        }
        return JsonSerializer.arrayListToJson(documents);
    }

    public String getDocumentsBasedOnDate(DateTime dateTime) {
        MongoCollection<Document> collection = getFaresCollection();
        List<Fare> documents = new ArrayList<>();
        BasicDBObject dbObject = new BasicDBObject();
        dbObject.append("day",dateTime.getDay());
        dbObject.append("month",dateTime.getMonth());
        dbObject.append("year",dateTime.getYear());
        FindIterable<Document> iterDoc = collection.find(new BasicDBObject("dateTime",dbObject));
        if (!iterDoc.iterator().hasNext()) throw new DatabaseException("No docs found!");
        for (Document document : iterDoc) {
            documents.add(JsonSerializer.toFare(document.toJson()));
        }
        return JsonSerializer.arrayListToJson(documents);
    }

    public void deleteDocumentFromCollection(MongoCollection<Document> collection, Map<String,Object> json) {
        DeleteResult result = collection.deleteOne(new Document(json));
        if (result.getDeletedCount() < 1)
            throw new DatabaseException("Deletion was not successful!");
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
