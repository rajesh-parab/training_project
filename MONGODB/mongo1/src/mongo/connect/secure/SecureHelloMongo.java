package mongo.connect.secure;

import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class SecureHelloMongo {
	public static void main(String args[]) {
		try {
			MongoCredential credential = MongoCredential.createCredential("administrator", "admin",
					"mypassword".toCharArray());
			MongoClient mongoClient = new MongoClient(new ServerAddress("localhost"), Arrays.asList(credential));
			DB db = mongoClient.getDB("test");
			// this is real test of authorisation
			db.getCollectionNames();
			System.out.println("Successfully connected to secure          database");
			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		}
	}
}
