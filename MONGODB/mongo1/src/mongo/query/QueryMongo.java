package mongo.query;

import java.util.List;

import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

public class QueryMongo {
	private final static String HOST = "localhost";
	private final static int PORT = 27017;

	MongoClient mongoClient;
	DB db;
	DBCollection coll;

	@Before
	public void connect() {
		mongoClient = new MongoClient(HOST, PORT);
		 db = mongoClient.getDB("sampledb");
		   coll  = db.getCollection("javastuff");
	}

	@Test
	public void queryAll() {
		DBCursor cursor = coll.find();
		try {
			while (cursor.hasNext()) {
				DBObject object = cursor.next();
				System.out.println(object);
			}
		} finally {
			cursor.close();
		}
	}

	@Test
	public void firstDocument() {
		DBObject myDoc = coll.findOne();
		System.out.println(myDoc);
	}

	@Test
	public void countRows() {

		System.out.println(coll.getCount());
	}

	@Test
	public void countRowsUsingCursor() {

		DBCursor cursor = coll.find();
		System.out.println(cursor.count());
	}

	@Test
	public void query() {

		DBObject query = new BasicDBObject("name", "owen");
		List<DBObject> list = coll.find(query).toArray();
		for (DBObject db : list)
			System.out.println(db);
	}
	@Test
	public void queryExplainPlan() {

		DBObject query = new BasicDBObject("name", "owen");
		DBObject d = coll.find(query).explain();
		
			System.out.println(d);
	}
	@Test
	public void queryExplainPlanWithIndex() {
		BasicDBObject nameIndex=new BasicDBObject("name",1);
		coll.createIndex(nameIndex);

		DBObject query = new BasicDBObject("name", "owen");
		DBObject d = coll.find(query).explain();
		coll.dropIndex(nameIndex);
			System.out.println(d);
	}
	@Test
	public void skip() {

		DBObject query = new BasicDBObject("age", 47); // "47" not worked
		List<DBObject> list = coll.find(query).toArray();
		for (DBObject db : list)
			System.out.println(db);
		System.out.println("#######################");
		list = coll.find(query).skip(1).toArray();
		for (DBObject db : list)
			System.out.println(db);
	}

	@Test
	public void limit() {

		DBObject query = new BasicDBObject("age", 47);
		List<DBObject> list = coll.find(query).toArray();
		for (DBObject db : list)
			System.out.println(db);
		System.out.println("#######################");
		list = coll.find(query).limit(2).toArray();
		for (DBObject db : list)
			System.out.println(db);
	}

	@Test
	public void complexQuery() {

		DBObject query = new BasicDBObject("name", new BasicDBObject("$ne", "rajesh")).append("age",
				new BasicDBObject("$gt", 10));

		List<DBObject> list = coll.find(query).toArray();
		for (DBObject db : list)
			System.out.println(db);
	}

	@Test
	public void jsonList() {
		
 MongoCollection<Document> coll2 = (MongoCollection<Document>)coll;
 
		MongoCursor<Document> cursor = coll2.find().iterator();
		try {
			while (cursor.hasNext()) {
				Document doc = cursor.next();
				System.out.println(doc.toJson());
			}
		} finally

		{
			cursor.close();
		}

	}

	@After
	public void close() {
		mongoClient.close();
	}

}
