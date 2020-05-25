package daemon_api;

import javax.json.JsonObject;

public class BlockHeader extends BlockMetaData {
	
	public int DEPTH;
	public int HEIGHT;
	public int MAJOR_VERSION;
	public int MINER_VERSION;
	public int REWARD;
	
	public long NONCE;
	
	public String PREVIOUS_HASH;
	
	public boolean ORPHAN;
	
	public BlockHeader(JsonObject input) {
		super(input,false);
		this.parse(input);

	}
	public void parse(JsonObject input)  {
		
		boolean isBlock=false;
		System.out.println(input);
		try {
			SIZE=input.getInt("block_size");
		}
		catch(NullPointerException e) {
			try {
				SIZE=input.getInt("blockSize");
				isBlock=true;
			}
			catch(NullPointerException e1) {
				throw e;
			}
		}
		DEPTH=input.getInt("depth");
		DIFFICULTY=input.getInt("difficulty");
		HASH=input.getString("hash");
		HEIGHT=input.getInt("height");
		MAJOR_VERSION=input.getInt("major_version");
		MINER_VERSION=input.getInt("minor_version");
		NONCE=input.getInt("nonce");
		if(!isBlock)TX=input.getInt("num_txes");
		else TX=input.getInt("transactionsCumulativeSize");
		ORPHAN=input.getBoolean("orphan_status");
		PREVIOUS_HASH=input.getString("prev_hash");
		REWARD=input.getInt("reward");
		TIMESTAMP=input.getInt("timestamp");
	}
	
}
