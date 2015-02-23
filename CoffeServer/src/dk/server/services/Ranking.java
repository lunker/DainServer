package dk.server.services;

public class Ranking {

	
	
	public void ordering(){
		
	}
	
	public void getRank(){
		
	}
	
	
	public void start(){
		// start the ranking 
		RankThread rt = new RankThread();
		rt.start();
	}
	
	private class RankThread extends Thread{
		
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
		
		}
		
	}
	
}
