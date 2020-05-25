package daemon_api;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class BlockMetaData {
	public int SIZE;
	public int DIFFICULTY;
	public String HASH;
	public long TIMESTAMP;
	public int TX;
	
	
	public BlockMetaData(JsonObject input,boolean parse) {
		if(parse)parseIsolatedChain(input);
	}
	
	

	public void parseIsolatedChain(JsonObject input) {
		parseUniversal(input);
		this.SIZE=input.getInt("cumul_size");
		this.TX=input.getInt("tx_count");
	}
	
	public void parseUniversal(JsonObject input) {
		DIFFICULTY=input.getInt("difficulty");
		HASH=input.getString("hash");
		TIMESTAMP=input.getInt("timestamp");	
	}

}
