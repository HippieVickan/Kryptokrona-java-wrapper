package daemon_api;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import json.API;

public class Daemon {	
	public API api;

	public Daemon(String ip) {
		api=new API(ip);
	}
	public Daemon(API api) {
		this.api=api;
	}
	
	
	public int getBlockCount() throws IOException, InterruptedException {
		return api.noParameterRequest("getblockcount").getJsonObject("result").getInt("count");
	}
	public String getBlockHash(int height) throws IOException, InterruptedException {
		return api.parameterRequest("on_getblockhash", "["+height+"]").getString("result");
	}
	public BlockTemplate getBlockTemplate(int size,String adress) throws IOException, InterruptedException {
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		parameters.add("reserve_size", size);
		parameters.add("wallet_address", adress);
		JsonObject result=api.parameterRequest("getblocktemplate", parameters.build().toString());
		return new BlockTemplate(result.getJsonObject("result"));
	}
	public boolean submitBlock(String blob) throws IOException, InterruptedException, BlockNotAcceptedException {
		JsonObject response=api.parameterRequest("submitblock", "[\""+blob+"\"]");
		if(response.containsKey("error") ) throw new BlockNotAcceptedException("Node "+api.IP+" did not accept blob: "+blob);
		if(response.getJsonObject("result").getString("status").equals("OK")) return true;
		return false;
	}
	public BlockHeader getLastBlockHeader() throws IOException, InterruptedException {
		return new BlockHeader(api.noParameterRequest("getlastblockheader").getJsonObject("result").getJsonObject("block_header"));
	}
	public BlockHeader getBlockHeaderByHash(String hash) throws IOException, InterruptedException {
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		parameters.add("hash", hash);
		return new BlockHeader(api.parameterRequest("getblockheaderbyhash", parameters.build().toString()));
	}
	public BlockHeader getBlockHeaderByHeight(int height) throws IOException, InterruptedException {
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		parameters.add("height", height);
		return new BlockHeader(api.parameterRequest("getblockheaderbyhash", parameters.build().toString()));
	}
	public String getCurrencyID() throws IOException, InterruptedException {
		return api.noParameterRequest("getcurrencyid").getJsonObject("result").getString("currency_id_blob");
	}
	public BlockMetaData[] getBlocks(int height) throws IOException, InterruptedException {
		BlockMetaData[] toReturn=new BlockMetaData[Math.min(height, 30)+1];
		
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		parameters.add("height", height);
		JsonObject response=api.parameterRequest("f_blocks_list_json", parameters.build().toString());
		
		JsonArray data=response.getJsonObject("result").getJsonArray("blocks");
		for(int i=0; i<=data.size()-1; i++) {
			
			toReturn[i]=new BlockMetaData(data.getJsonObject(i),true);
		}
		return toReturn;
	}
	
	
	public Block getBlock(String hash) throws IOException, InterruptedException {
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		parameters.add("hash", hash);
		JsonObject response=api.parameterRequest("f_block_json", parameters.build().toString());
		return new Block(response.getJsonObject("result").getJsonObject("block"));
	}
	
	public Transaction getTransaction(String hash) throws IOException, InterruptedException {
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		parameters.add("hash", hash);
		JsonObject response=api.parameterRequest("f_transaction_json", parameters.build().toString());
		//System.out.println(response.getJsonObject("result").getJsonObject("txDetails"));
		return new Transaction(response.getJsonObject("result"));
	}
	public TransactionBase[] getTransactionPool() throws IOException, InterruptedException {
		JsonObject response=api.noParameterRequest("f_on_transactions_pool_json");
		JsonArray transactions=response.getJsonObject("result").getJsonArray("transactions");
		TransactionBase[] toReturn=new TransactionBase[transactions.size()];
		
		for(int i=0; i<=toReturn.length-1; i++) {
			toReturn[i]=new TransactionBase(transactions.getJsonObject(i));
		}
		return toReturn;
	}
	
}
