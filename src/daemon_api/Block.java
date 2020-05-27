package daemon_api;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class Block extends BlockHeader {
	
	public int ALREADY_GENERATED_COINS;
	public int ALREADY_GENERATED_TRANSACTIONS;
	public int BASE_REWARD;
	public int EFFECTIVE_SIZE_MEDIAN;
	public double PENALTY;
	public int SIZE_MEDIAN;
	public int TOTAL_FEE_AMOUNT;
	
	public TransactionBase[] TRANSACTIONS;
	
	public int TX_SIZE_CUMULATIVE;
	
	
	
	public Block(JsonObject input) {
		
		super(input);
		JsonArray transactions= input.getJsonArray("transactions");
		TRANSACTIONS=new TransactionBase[transactions.size()];
		for(int i=0; i<=TRANSACTIONS.length-1; i++)  {
			TRANSACTIONS[i]=new TransactionBase(transactions.getJsonObject(i));
		}
		
		ALREADY_GENERATED_COINS=(int)Double.parseDouble(input.getString("alreadyGeneratedCoins"));
		this.ALREADY_GENERATED_TRANSACTIONS=input.getInt("alreadyGeneratedTransactions");
		this.BASE_REWARD=input.getInt("baseReward");
		this.EFFECTIVE_SIZE_MEDIAN=input.getInt("effectiveSizeMedian");
		this.PENALTY=input.getInt("penalty");
		this.SIZE_MEDIAN=input.getInt("sizeMedian");
		this.TOTAL_FEE_AMOUNT=input.getInt("totalFeeAmount");
		this.TX_SIZE_CUMULATIVE=input.getInt("transactionsCumulativeSize");
	}

}
