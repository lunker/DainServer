package dk.server;

import java.io.UnsupportedEncodingException;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.Handler;
import org.vertx.java.core.MultiMap;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonArray;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.core.net.NetServer;
import org.vertx.java.core.net.NetSocket;
import org.vertx.java.core.streams.Pump;
import org.vertx.java.platform.Verticle;

import dk.server.services.DatabaseConnector;
import dk.server.services.Ranking;

public class MainServer extends Verticle {

	final int PORT = 983;
	final String IP = "14.49.38.96";
	final String DB = "database.my";
	private EventBus eventBus ;
	
	private DatabaseConnector connector = null;
	
	@Override
	public void start() {
		// TODO Auto-generated method stub
		connector = new DatabaseConnector();
		connector.connect();
		
		
		
		eventBus = vertx.eventBus();
		HttpServer server = vertx.createHttpServer();
		server.requestHandler(new Handler<HttpServerRequest>() {

			@Override
			public void handle(HttpServerRequest param) {
				// TODO Auto-generated method stub
				param.expectMultiPart(true);
				final HttpServerRequest request = param;
				final String action = request.headers().get("action");

				System.out.println("afasdasf");
				//GET
				if(request.method().equals("GET")){
					System.out.println("GET");
					
					// get the ranking
					if(action.equals("ranking")){
						System.out.println("[GET] get the ranking request");
					
						
						
						
					
					}// end ranking
				}
				else if(action.equals("review")){
					System.out.println("[GET] get the review request");
				}
				
				//POST
				else{
					request.endHandler(new Handler<Void>() {
						
						@Override
						public void handle(Void arg0) {
							// TODO Auto-generated method stub
							System.out.println("[POST] add grade");
							// post the grade 
							if(action.equals("grade")){
								
								System.out.println("[POST] add grade");
								
								MultiMap requestMap = request.formAttributes();
								
								// brand - string
								// coffeename - string
								// grade - double

//								double grade = Double.parseDouble(request.formAttributes().get("grade"));
								
								
								String brand = requestMap.get("brand");
								String grade = requestMap.get("grade");
								String coffeeName = requestMap.get("coffeename");
								/*
								try {
									coffeeName = new String(requestMap.get("coffeename").getBytes(), "UTF-16");
									
									System.out.println("[POST GRADE] get brand , coffeename, grade : " + brand + "," + coffeeName + "," + grade);
									
									if(connector.addGrade(brand, coffeeName, grade))
										System.out.println("[POST GRADE] SUCCESS");
									else
										System.out.println("[POST GRADE] FAIL" );
									
									
								} catch (UnsupportedEncodingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								*/
								System.out.println("[POST GRADE] get brand , coffeename, grade : " + brand + "," + coffeeName + "," + grade);
								
								if(connector.addGrade(brand, coffeeName, grade))
									System.out.println("[POST GRADE] SUCCESS");
								else
									System.out.println("[POST GRADE] FAIL" );
								
								
								
								
							}//END IF
							/*
							 * not yet
							 */
							else if(action.equals("registerreview")){
								
								System.out.println("[POST] registerreview");
								MultiMap requestMap = request.formAttributes();
								String userId = requestMap.get("userid");
								String brand = requestMap.get("brand");
								String coffeeName = requestMap.get("coffeename");
//								String productId = requestMap.get("productId");
								String content = requestMap.get("content");
								String date = requestMap.get("date");
								
							}
							//sign up
							else if(action.equals("signup")){
								/*
								 * 
								 *  - userId : integer(key)
								 - name
								 - email
								 - password
								 - gender
								 - age
								 - reviews : array
								 - graded items : array
 
								 */
								System.out.println("[POST] signup");
								
								MultiMap requestMap = request.formAttributes();
								String email = requestMap.get("email");
								String password = requestMap.get("password");
								String gender = requestMap.get("gender");
								String age = requestMap.get("age");
								
								JsonObject user = new JsonObject();
								user.putString("email", email);
								user.putString("password", password);
								user.putString("gender", gender);
								user.putString("age", age);
								
								
								if(connector.addUser(user, "user")){
									System.out.println("signup complete");
								}
								else
									System.out.println("signup fail");
								
								request.response().setChunked(true);
								request.response().write("ack");
							}// end else if 
							
						}
					}); // haaaaa
					
				}// end POST
				
			}
		});// end request handler.
		

		server.listen(PORT, IP,new Handler<AsyncResult<HttpServer>>() {

					@Override
					public void handle(AsyncResult<HttpServer> result) {
						// TODO Auto-generated method stub

						System.out.println("server waiting . . . " + result.succeeded());
					}
		});
	}// end start
	
	
	public String getBrandName(int brand){
		String name="";
		
		switch(brand){
			case 0 : name = "starbucks"; break;
			case 1 : name = "cafebene"; break;
			case 2 :	break;
		}
		
		return name;
	}// end method

	
	
}





























