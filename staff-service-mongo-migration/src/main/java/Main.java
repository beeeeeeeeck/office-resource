import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import core.framework.mongo.MongoMigration;
import org.bson.Document;

/**
 * @author beckl
 */
public class Main {
    public static void main(String[] args) {
        MongoMigration migration = new MongoMigration("sys.properties", "sys.mongo.adminURI");
        migration.migrate(mongo -> mongo.runCommand(new Document().append("setParameter", 1).append("notablescan", 1)));

        migration = new MongoMigration("sys.properties");
        migration.migrate(mongo -> {
            mongo.createIndex("job_summaries", Indexes.ascending("staff_id", "title"), new IndexOptions().unique(true));
            mongo.createIndex("job_summaries", Indexes.ascending("staff_id"));
        });
    }
}