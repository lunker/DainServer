package dk.server;

import org.vertx.java.core.AsyncResult;
import org.vertx.java.core.AsyncResultHandler;
import org.vertx.java.core.eventbus.EventBus;
import org.vertx.java.core.http.HttpClientResponse;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

public class DBVerticle extends Verticle {

	private JsonObject mongoDBConfig; 
	EventBus bus; 
	
	
	public void initialize(){
		mongoDBConfig = new JsonObject();
		mongoDBConfig.putString("address","database.my");
		mongoDBConfig.putString("host", "localhost");
		mongoDBConfig.putNumber("port", 27017);
		mongoDBConfig.putString("db_name","coffee");
		mongoDBConfig.putString("username", "superapp_dk");
		mongoDBConfig.putString("password", "794686h");
	}
	
	@Override
	public void start(){
		
		
		initialize();
		bus = vertx.eventBus();
		container.deployModule("io.vertx~mod-mongo-persistor~2.1.0", mongoDBConfig, 1, new AsyncResultHandler<String>() {

			@Override
			public void handle(AsyncResult<String> reply) {
				// TODO Auto-generated method stub
				if(reply.succeeded())
					System.out.println("deploy success");
				else
					System.out.println("deploy fail");
				
			}
		});

		System.out.println("dbverticle success");
	}// end method
	
	
	
	
	
}
