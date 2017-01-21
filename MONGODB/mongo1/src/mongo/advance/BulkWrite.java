package mongo.advance;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
 

public class BulkWrite {

	public static void main(String[] args) {
		
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		try{
			DB db = mongoClient.getDB("sampledb");
			DBCollection coll = db.getCollection("pojo");
			
		BulkWriteOperation builder = coll.initializeOrderedBulkOperation();
		builder.insert(new BasicDBObject("item", "A1"));
		builder.insert(new BasicDBObject("item", "A2"));
		builder.insert(new BasicDBObject("item", "A3"));
		builder.find(new BasicDBObject("item", "A1"))
				.updateOne(new BasicDBObject("$set", new BasicDBObject("A1", "AX")));
		BulkWriteResult result = builder.execute();
		System.out.println("Bulk Completed: Inserted documents " + result.getInsertedCount());
		System.out.println("Bulk Completed: Modified documents " + result.getModifiedCount());
		}finally{
			mongoClient.close();
		}

	}

}
