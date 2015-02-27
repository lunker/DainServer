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

	
	/*
	 * success!!!!!
	 */
	public boolean addUser(JsonObject user, String collectionName){	
		

		/*
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
		*/
		
		/*
		 * 
									user.putString("id", id);
									user.putString("email", email);
									user.putString("password", password);
									user.putString("gender", gender);
									user.putString("age", age);
		 */
		
		database.createCollection(user.getString("id"));
		getMyCollection(user.getString("id")).insertOne(new Document("email", user.getString("email")).
				append("password", user.getString("password")).append("gender", user.getString("gender")).append("age", user.getString("age")));
		
//		database.createCollection("r"+user.getString("id"));
//		database.createCollection("g"+user.getString("id"));
		
		
		
		return true;
		
		
		
	}// end signup
	
	
	/*
	 * 
	 * success!!!!
	 * 
	 * 완벽하진않
	 */
	
	
	public Document getUser(String userId){
		
		Document user = getMyCollection(userId).find().first();
		
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
	
	
	/*
	 * success!!!
	 */
	public boolean addReview(String coffeeId,String userId, String content, String grade, String date){
		
		String reviewCollection = "r_" + coffeeId;
		Document document  = new Document();
		/*
		document.append("userid", review.getString("userid")).
				append("content", review.getString("content")).
				append("date",  review.getString("date")).
				append("photo","");
		*/
		
		// UPDATE THE REVIEW
		  Document target = getMyCollection(userId).find(new Document("coffeeid", coffeeId)).first();
		  
		  if( target != null  ){
				
			  //update user collection
				getMyCollection(userId).updateOne(new Document("coffeeid", coffeeId),
						new Document("grade", grade).append("date", date).append("content", content));
				// update review collection
				getMyCollection(reviewCollection).updateOne(new Document("userid", userId), new Document("content", content).append("grade", grade).append("date", date));
				
			}
			else{
				// ADD THE REVIEW
				getMyCollection(userId).insertOne(new Document("coffeeid", coffeeId).append("content", content)
						.append("grade", grade).append("date", date));
				// add review collection
				getMyCollection(reviewCollection).insertOne(new Document("userid", userId).
						append("content", content).append("grade", grade).append("date", date));
				
			}

		  return true;
	}// end method
	
	
	/*
	 * sucess!!!!
	 * get the all review at Coffee item 
	 */
	public void getReview(String coffeeId){
		
		FindIterable<Document> reviewIter = getMyCollection("r_"+coffeeId).find();
		MongoCursor<Document> cursor = reviewIter.iterator();
		StringBuilder builder = new StringBuilder();
		
		
		while(cursor.hasNext()){
			
			builder.append(cursor.next().toString());
		}
		System.out.println(builder.toString());
		
	}
	
	/*
	 * successs!!
	 */
	
	public synchronized boolean addGrade(String userId, String coffeeId, String grade){
		
		
		String gradeCollection = "g_"+ coffeeId ; 
		Document gradeField = new Document();
		Document countField = new Document();
		
		countField.put("totalcount", 1);
		gradeField.put("totalgrade", Double.parseDouble(grade));
		System.out.println("grade ? : "+ Double.parseDouble(grade));
		
		
		/*
		 * add grade history to user collection
		 */
		 
		if(getMyCollection(userId).find(new Document("coffeeid", coffeeId))!= null ){
			// UPDATE THE COFFEE GRADE
			getMyCollection(userId).updateOne(new Document("coffeeid", coffeeId), new Document("grade", grade));
			getMyCollection(gradeCollection).updateOne(new Document("userid", userId), new Document("grade", grade));
			return true;
			
		}
		else{
			// ADD THE GRADE TO NEW ITEM
			getMyCollection(userId).insertOne(new Document("coffeeid", coffeeId).append("grade", grade)
					.append("content", "").append("date", ""));
			getMyCollection(gradeCollection).insertOne(new Document("userid", userId).append("grade", grade));
			return true;
		}
		
		/*
		 * update the grade to coffee collection.
		 */
		
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



























