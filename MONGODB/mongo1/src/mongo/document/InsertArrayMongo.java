package mongo.document;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
// db.javastuff.find({"name":"john"}).pretty() 

public class InsertArrayMongo {

	private final static String HOST = "localhost";
	private final static int PORT = 27017;

	public static void main(String args[]) {
		try {
			// Connect to mongodb server on localhost
			MongoClient mongoClient = new MongoClient(HOST, PORT);
			DB db = mongoClient.getDB("sampledb");
			DBCollection coll = db.getCollection("javastuff");
			List<DBObject> kids = new ArrayList<>();
			kids.add(new BasicDBObject("name", "mike"));
			kids.add(new BasicDBObject("name", "faye"));
			DBObject doc = new BasicDBObject("name", "john").append("age", 35).append("kids", kids).append("info",
					new BasicDBObject("email", "john@mail.com").append("phone", "876-134-667"));
			coll.insert(doc);

			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {

		}

	}
}
