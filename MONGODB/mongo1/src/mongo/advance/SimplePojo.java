package mongo.advance;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.bson.BSONObject;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class SimplePojo implements DBObject {

	private Map<String, Object> data;
	private boolean partial;

	public SimplePojo() {
		data = new HashMap<>();
		partial = false;
	}

	@Override
	public Object put(String key, Object value) {
		return data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(BSONObject o) {
		data.putAll(o.toMap());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void putAll(Map m) {
		data.putAll(m);
	}

	@Override
	public Object get(String arg0) {
		return data.get(arg0);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map toMap() {
		return data;
	}

	@Override
	public Object removeField(String key) {
		return data.remove(key);
	}

	@Override
	public boolean containsKey(String key) {
		return data.containsKey(key);
	}

	@Override
	public boolean containsField(String key) {
		return data.containsKey(key);
	}

	@Override
	public Set<String> keySet() {
		return data.keySet();
	}

	@Override
	public void markAsPartialObject() {
		partial = true;
	}

	@Override
	public boolean isPartialObject() {
		return partial;
	}

	public static void main(String args[]) {
		
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		try{
		DB db = mongoClient.getDB("sampledb");
		DBCollection coll = db.getCollection("pojo");
		SimplePojo obj = new SimplePojo();
		obj.put("user", "user1");
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
