package dk.server.services;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class TestDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		DatabaseConnector connector = new DatabaseConnector();
		
		connector.connect();
		
		/*
		FindIterable<Document> users = connector.getMyCollection("user").find();
		MongoCursor<Document> cursor =   users.iterator();
		
		while(cursor.hasNext()){
			System.out.println(cursor.next().get("_id"));
			
			Document d = cursor.next();
			if( d.get("_id").equals("54dac9cc355e1d0b76a609fb")){
				
				
				d.put("_id", "12341312313");
				
				
			}
			
		}


		*/
		
		
		FindIterable<Document> result = connector.getMyCollection("user").find(new Document("graded",""));
		connector.getMyCollection("user").
		
//		System.out.println(result.first().toString());
		
	}

}
