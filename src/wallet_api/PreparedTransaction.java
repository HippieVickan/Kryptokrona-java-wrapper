package wallet_api;

public class PreparedTransaction {
	public String DESTINATION;
	public int AMOUNT;
	public String PAYMENT_ID;
	
	public PreparedTransaction(String des, int amnt,String id) {
		DESTINATION=des;
		AMOUNT=amnt;
		PAYMENT_ID=id;
	}
	
}
