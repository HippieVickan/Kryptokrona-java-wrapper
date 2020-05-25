package daemon_api;

import javax.json.JsonObject;

public class TransactionBase {
	public double AMOUNT_OUT;
	public double FEE;
	public String HASH;
	public int SIZE;
	
	
	public TransactionBase(JsonObject init) {
		System.out.println(init);
		parseJson(init);
	}
	public void parseJson(JsonObject in) {
		this.AMOUNT_OUT=in.getInt("amount_out");
		this.FEE=in.getInt("fee");
		this.HASH=in.getString("hash");
		this.SIZE=in.getInt("size");
		
	}
	
}
