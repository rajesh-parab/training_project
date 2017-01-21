package mongo.connect;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class HelloMongo {

	private final static String HOST = "localhost";
	private final static int PORT = 27017;

	public static void main(String args[]) {
		try {
			// Connect to mongodb server on localhost
			MongoClient mongoClient = new MongoClient(HOST, PORT);
			DB db = mongoClient.getDB("test");
		 	db.getCollectionNames();
		 
			System.out.println("Successfully connected to          MongoDB");
			
			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}finally{
			
		}

	}
}
