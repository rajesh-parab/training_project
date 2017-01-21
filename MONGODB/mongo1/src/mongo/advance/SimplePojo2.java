package mongo.advance;

import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class SimplePojo2 extends BasicDBObject  {

	 

	public static void main(String args[]) {
		
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		try{
		DB db = mongoClient.getDB("sampledb");
		DBCollection coll = db.getCollection("pojo");
		SimplePojo2 obj = new SimplePojo2();
		obj.put("user", "user3");
		obj.put("message", "message");
		obj.put("date", new Date());
		coll.insert(obj);
		mongoClient.close();
		/*coll.setObjectClass(SimplePojo.class);
		SimplePojo tw = (SimplePojo)coll.findOne();
		System.out.println(tw.get("user")); */
		}finally{
			mongoClient.close();
		}

	}
}
