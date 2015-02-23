package dk.server.services;

import org.bson.Document;
import org.vertx.java.core.json.JsonObject;

import com.mongodb.BasicDBObject;
import com.mongodb.BulkUpdateRequestBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class DatabaseConnector {

	
	private final String DATABASE = "coffee";
	private final int PORT = 27017;
	private final String HOST = "localhost";
	
	public volatile MongoClient mongoClient = null;
	private MongoDatabase database = null;
	
	

	public MongoDatabase getDatabase() {
		return database;
	}

	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}
	
	public void connect(){

		MongoDatabase tmp = null;
		
		mongoClient = new MongoClient( HOST , PORT);
		tmp = mongoClient.getDatabase(DATABASE);
		if(tmp == null){
			System.out.println("fail to connect MongoDB");
		}
		else{
			System.out.println("connect the DB");
			setDatabase(tmp);
		}

	}// end method

	public MongoCollection<Document> getMyCollection(String collectionName){
		
		MongoCursor<String> cursor = database.listCollectionNames().iterator();
		while(cursor.hasNext()){
			if(cursor.next().equals(collectionName)){
				System.out.println("find collection ( " + collectionName + ")");
				return database.getCollection(collectionName);
			}
		}

		System.out.println("fail to find collection");
			
		return null;
	}

	public boolean addUser(JsonObject user, String collectionName){	
		
		/*
		 * 
	 - userId : integer(key)
	 - name
	 - email
	 - password
	 - gender
	 - age
	 - reviews : array
	 - graded items : array
		 */
		Document document = new Document();
		
		document.append("email","test!!").append("password", "test!!");
		
		try{
			getMyCollection(collectionName).insertOne(document);
			return true;
		}
		catch(MongoException e){
			e.printStackTrace();
			return false;
		}
	}// end signup
	
	public Document getUser(String userId){
		
		Document user = getMyCollection("user").find(new Document("userid", userId)).first();
		
		if(user == null){
			System.out.println("fail to find user");
		}
		else{
			System.out.println("find user");
		}
		
		return user;
		
	}
	
	/*
	 * not implement
	 */
	public boolean ranking(String criteria, String brand){
		
		return true;
	}
	
	public boolean addReview(JsonObject review, String brand, String coffeeName){
		
		/*
		 * 
		 * 
 - reviewId(key)
 - userId
 - productId
 - content
 - date
 - photo
		 */
		
		Document document  = new Document();
		document.append("userid", review.getString("userid")).
				append("content", review.getString("content")).
				append("date",  review.getString("date")).
				append("photo","");
		
		
		try{
			database.getCollection(brand + coffeeName).insertOne(document);
			return true;
		}
		catch(MongoException e){
			e.printStackTrace();
			return false;
		}
		
	}// end method
	
	
	/*
	 * not yet
	 */
	public void getReview(String brand, String coffeeName){
		
		FindIterable<Document> reviewIterate = getMyCollection("brand"+"coffeeName").find();
		
	}
	
	/*
	 * okay,
	 * encoding
	 * testing
	 * 
	 */
	
	public boolean addGrade(String brand, String coffeeName, String grade){
		
		
		
		//
		
		
		
		
		Document gradeField = new Document();
		Document countField = new Document();
		
		countField.put("totalcount", 1);
		gradeField.put("totalgrade", Double.parseDouble(grade));
		System.out.println("grade ? : "+ Double.parseDouble(grade));
		
		if( (getMyCollection(brand).findOneAndUpdate(new Document("name", coffeeName), 
										new Document("$inc", gradeField) )!= null  && 
										(getMyCollection(brand).findOneAndUpdate(new Document("name", coffeeName), new Document("$inc", countField)))!= null  )){
			return true;
		}
		return false;
		
	}
	
	/*
	 * not implement
	 */
	public double getGrade(String brand, String coffeeName){
		
		Document coffee = getMyCollection(brand).find(new Document("name", coffeeName)).first();
		
		Double result = coffee.getDouble("totalgrade");
		int count = coffee.getInteger("totalcount", 1);
		
		return result/count ;
	}
	
	
	/*
	public String getReviewCollectionName(String brand, String coffeeName){
		
	}
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
}



























