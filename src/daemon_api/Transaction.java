package daemon_api;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class Transaction extends TransactionBase {
	public Block BLOCK;
	
	public String EXTRA;
	public int UNLOCK_TIME;
	public int VERSION;
	
	public int MIXIN;
	public String PAYMENT_ID;
	
	public SubTransactionIn[] VIN;
	public SubTransactionOut[] VOUT;
	
	public Transaction(JsonObject in) {
		super(in.getJsonObject("txDetails"));
		
		
		
		
		JsonObject tx=in.getJsonObject("tx");
		this.EXTRA=tx.getString("extra");
		this.UNLOCK_TIME=tx.getInt("unlock_time");
		this.VERSION=tx.getInt("version");
		
		JsonArray vin=tx.getJsonArray("vin");
		JsonArray vout=tx.getJsonArray("vout");
		VIN=new SubTransactionIn[vin.size()];
		VOUT=new SubTransactionOut[vout.size()];
		
		
		for(int i=0; i<=vin.size()-1; i++) VIN[i]=new SubTransactionIn(vin.getJsonObject(i));
		for(int i=0; i<=vout.size()-1; i++) VOUT[i]=new SubTransactionOut(vout.getJsonObject(i));
		
		
	}
	class SubTransactionOut {
		public int AMOUNT;
		public String KEY;
		public String TYPE;
		
		SubTransactionOut(JsonObject in) {
			
			this.AMOUNT=in.getInt("amount");
			this.KEY=in.getJsonObject("target").getJsonObject("data").getString("key");
			this.TYPE=in.getJsonObject("target").getString("type");
		}
		
	}
	class SubTransactionIn {
		public String TYPE;
		public int HEIGHT;
		
		SubTransactionIn(JsonObject in) {
			this.TYPE=in.getString("type");
			this.HEIGHT=in.getJsonObject("value").getInt("height");
		}
	}
}
