package mongo.document;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
// db.javastuff.find()

public class UpdateMongo {

	private final static String HOST = "localhost";
	private final static int PORT = 27017;

	public static void main(String args[]) {
		try {
			// Connect to mongodb server on localhost
			MongoClient mongoClient = new MongoClient(HOST, PORT);
			DB db = mongoClient.getDB("sampledb");
			DBCollection coll = db.getCollection("javastuff");
			
			
			DBObject query = new BasicDBObject().append("name", "owen");
			BasicDBObject newDocument = new BasicDBObject();
			// following line is not updated but remove all column except age
		//	newDocument.put("age", 23);
			newDocument.append("$set", new BasicDBObject().append("age",    23));

			coll.update(query, newDocument);
			// remove the 
			//coll.remove(query);

			 
			
			/*
			 * 
			 * delete all records whose age ranges from 0 to 49:
				BasicDBObject deleteQuery = new BasicDBObject();
				 List<Integer> list = new ArrayList<Integer>();
				for (int i=0;i<50;i++) 
				list.add(i);
					deleteQuery.put("age", new BasicDBObject("$in", list)); 
					coll.remove(deleteQuery); 
			 */

			mongoClient.close();
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {

		}

	}
}
