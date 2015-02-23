package dk.server.services;

public class TestDB {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		
		
		DatabaseConnector connector = new DatabaseConnector();
		
		connector.connect();
		connector.getMyCollection("asdf");
	}

}
