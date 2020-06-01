package wallet_api;

import javax.json.JsonObject;

public class TransactionStatus {
	public String HASH;
	public int FEE;
	public boolean RELAYED;
	
	public TransactionStatus(JsonObject in) {
		HASH=in.getString("transactionHash");
		FEE=in.getInt("fee");
		RELAYED=in.getBoolean("relayedToNetwork");
	}
}
