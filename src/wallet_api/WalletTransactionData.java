package wallet_api;

import javax.json.JsonObject;

public class WalletTransactionData {
	public int BLOCK_HEIGHT;
	public double FEE;
	public String HASH;
	public boolean COINBASE;
	public String PAYMENT_ID;
	public long TIMESTAMP;
	public long UNLOCK_TIME;
	public String RECIEVER;
	public int AMOUNT;
	
	
	public WalletTransactionData(JsonObject in) {
		BLOCK_HEIGHT=in.getInt("blockHeight");
		FEE=Double.parseDouble(in.get("fee").toString());
		HASH=in.getString("hash");
		COINBASE=in.getBoolean("isCoinBaseTransaction");
		PAYMENT_ID=in.getString("paymentID");
		TIMESTAMP=in.getInt("timestamp");
		UNLOCK_TIME=in.getInt("unlockTime");
		RECIEVER=in.getJsonObject("transfers").getString("address");
		AMOUNT=in.getJsonObject("transfers").getInt("amount");
	}
	
}
