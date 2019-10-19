package admin.service.mongo.db;

import com.mongodb.MongoException;

public class DatabaseException extends MongoException {
    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(int code, String msg) {
        super(code, msg);
    }

    public DatabaseException(String msg, Throwable t) {
        super(msg, t);
    }

    public DatabaseException(int code, String msg, Throwable t) {
        super(code, msg, t);
    }
}
