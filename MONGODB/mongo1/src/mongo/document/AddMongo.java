package mongo.document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
// db.javastuff.find()

public class AddMongo {

	private final static String HOST = "localhost";
	private final static int PORT = 27017;

	public static void main(String args[]) {
		try {
			// Connect to mongodb server on localhost
			MongoClient mongoClient = new MongoClient(HOST, PORT);
			DB db = mongoClient.getDB("sampledb");
			DBCollection coll = db.getCollection("javastuff");
			DBObject doc = new BasicDBObject("name", "owen").append("age", 47).append("email", "owen@mail.com")
					.append("phone", "111-222-333");
			coll.insert(doc);

			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {

		}

	}
}
