package dk.server.services;

import java.util.ArrayList;

public class Ranking {

	
	private ArrayList<String> totalRanking = null;
	private ArrayList<String> americanoRanking = null;
//	private ArrayList<String> 
	
	
	public void updateRanking(){
		
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
