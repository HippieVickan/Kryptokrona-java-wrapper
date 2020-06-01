package wallet_api;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import json.API;
import json.Request;
import shared.Node;

/* FUNCTIONS:
 * WALLET
 * closeWallet
 * createWallet
 * openWallet
 * importByKey
 * importByMnemonic
 * importViewOnly
 * 
 * Addresses
 * getAddresses
 * createNewAddress
 * deleteSubWallet
 * getPrimaryAddress
 * importSubWallet
 * importSubWalletView
 * createIntegratedAddress
 * 
 * KEYS
 * getKeys
 * getKeyPairAddress
 * getMnemonic
 * 
 * NODE
 * getNode
 * setNode
 * 
 */


public class WalletDaemon {
	public API api;
	
	public String DAEMON_HOST;
	public int DAEMON_PORT;
	public String API_KEY;
	
	public boolean IS_OPEN;
	public String ACTIVE;
	
	
	public WalletDaemon(String ip,String host,int port,String key) {
		api=new API(ip);
		this.DAEMON_HOST=host;
		this.DAEMON_PORT=port;
		this.API_KEY=key;
	}
	public WalletDaemon(API api,String host,int port,String key) {
		this.api=api;
		this.DAEMON_HOST=host;
		this.DAEMON_PORT=port;
		this.API_KEY=key;
	}
	
	public void initialiseDaemon() {
		
	}
	
	private JsonObjectBuilder getGenericHeaders() {
		JsonObjectBuilder headers=Json.createObjectBuilder();
		headers.add("accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("X-API-KEY", API_KEY);
		return headers;
	}
	
	public JsonObjectBuilder getGenericParameters() {
		JsonObjectBuilder params=Json.createObjectBuilder();
		params.add("daemonHost", DAEMON_HOST);
		params.add("daemonPort", DAEMON_PORT);
		return params;
	}
	
	//WALLET
	
	public void openWallet(String fileName,String password) throws IOException, InterruptedException, WalletException {
		JsonObjectBuilder params=getGenericParameters();
		params.add("filename", fileName);
		params.add("password", password);
		
		Request req=api.walletRequest("/wallet/open", params.build(), getGenericHeaders().build(), "POST");
		IS_OPEN=true;
		ACTIVE=fileName;
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		if(e==null) return;
		else throw e;
	}
	public void createWallet(String fileName,String password) throws WalletException, IOException, InterruptedException {
		JsonObjectBuilder params=getGenericParameters();
		params.add("filename", fileName);
		params.add("password", password);
		Request req=api.walletRequest("/wallet/create", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
	}
	public void closeWallet() throws IOException, InterruptedException, WalletException {
		
		Request req=api.noParameterWalletRequest("/wallet",  getGenericHeaders().build(), "DELETE");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
	}

	public void importByKey(String fileName,String password,int scanHeight,String privateViewKey,String privateSpendKey) throws WalletException, IOException, InterruptedException {
		
		JsonObjectBuilder params=getGenericParameters();
		params.add("filename", fileName);
		params.add("password", password);
		params.add("scanHeight", scanHeight);
		params.add("privateViewKey", privateViewKey);
		params.add("privateSpendKey", privateSpendKey);
		
		Request req=api.walletRequest("/wallet/import/key", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
		
	}
	public void importByMnemonic(String fileName,String password,int scanHeight,String mnemonic) throws WalletException, IOException, InterruptedException {
		
		JsonObjectBuilder params=getGenericParameters();
		params.add("filename", fileName);
		params.add("password", password);
		params.add("scanHeight", scanHeight);
		params.add("mnemonicSeed", mnemonic);
		
		Request req=api.walletRequest("/wallet/import/seed", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
		
	}
	public void importViewOnly(String fileName,String password,int scanHeight,String viewKey,String adress) throws WalletException, IOException, InterruptedException {
		
		JsonObjectBuilder params=getGenericParameters();
		params.add("filename", fileName);
		params.add("password", password);
		params.add("scanHeight", scanHeight);
		params.add("privateViewKey", viewKey);
		params.add("adress", adress);
		
		Request req=api.walletRequest("/wallet/import/view", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
		
	}
	

	
	
	
	
	
	
	//ADDRESSES
	public String[] getAdresses() throws WalletException, IOException, InterruptedException {
		
		Request req=api.noParameterWalletRequest("/addresses",  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		JsonObject resp=req.RESULT;
		String[] toReturn=new String[resp.getJsonArray("addresses").size()];
		System.out.println(resp);
		for(int i=0; i<=toReturn.length-1; i++) {
			toReturn[i]=resp.getJsonArray("addresses").getString(i);
		}
		
		
		if(e==null) return toReturn;
		else throw e;
	}
	public void deleteSubWallet(String address) throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/addresses/"+address,  getGenericHeaders().build(), "DELETE");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
	}
	public String getPrimaryAddress() throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/addresses/primary",  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null)  return req.RESULT.getString("address");
		else throw e;
	}
	public KeyPair createAddress() throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/addresses/create",  getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		if(e==null)  return new KeyPair(req.RESULT.getString("publicSpendKey"),req.RESULT.getString("privateSpendKey"),req.RESULT.getString("address"));
		else throw e;
	}
	public String importSubwallet(int height,String privKey) throws WalletException, IOException, InterruptedException {
		JsonObjectBuilder params=Json.createObjectBuilder();
		params.add("scanHeight", height);
		params.add("privateSpendKey", privKey);
		Request req=api.walletRequest("/wallet/import", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return req.RESULT.getString("address");
		else throw e;
	}
	public String importSubwalletView(int height,String pubKey) throws WalletException, IOException, InterruptedException {
		JsonObjectBuilder params=Json.createObjectBuilder();
		params.add("scanHeight", height);
		params.add("publicSpendKey", pubKey);
		Request req=api.walletRequest("/wallet/import/view", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return req.RESULT.getString("address");
		else throw e;
	}
	public String createIntegratedAddress(String addr,String ID) throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/addresses/"+addr+"/"+ID,  getGenericHeaders().remove("accept").build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return req.RESULT.getString("integratedAddress");
		else throw e;
	}
	
	//KEYS
	
	public String getKeys() throws IOException, InterruptedException, WalletException {
		JsonObjectBuilder jsb=Json.createObjectBuilder();
		jsb.add("accept", "application/json");
		jsb.add("X-API-KEY", this.API_KEY);
		JsonObject js=jsb.build();
		System.out.println("Json Object to server: "+js);
		Request req=api.noParameterWalletRequest("/keys",  js, "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return req.RESULT.getString("privateViewKey");
		else throw e;
	}
	public KeyPair getKeyPairAdress(String address) throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/keys/"+address,  getGenericHeaders().remove("accept").build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return new KeyPair(req.RESULT.getString("privateSpendKey"),req.RESULT.getString("publicSpendKey"),address);
		else throw e;
	}
	public String getMnemonic(String address) throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/keys/mnemonic"+address,  getGenericHeaders().remove("accept").build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return req.RESULT.getString("mnemonicSeed");
		else throw e;
	}
	
	//NODE
	
	public Node getNode() throws WalletException, IOException, InterruptedException  {
		Request req=api.noParameterWalletRequest("/node",  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null)  return new Node(req.RESULT);
		else throw e;
	}
	public void setNode(String host,int port) throws IOException, InterruptedException, WalletException {
		JsonObjectBuilder parameters=Json.createObjectBuilder();
		
		parameters.add("daemonHost", host);
		parameters.add("daemonPort", port);
		
		Request req=api.walletRequest("/node", parameters.build(), getGenericHeaders().build(), "PUT");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			DAEMON_HOST=host;
			DAEMON_PORT=port;
		}
		else throw e;
	}
	
	//TRANSACTIONS
	public WalletTransactionData[] getTransactions() throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions",  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	public WalletTransactionData[] getTransactions(int start) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/"+start,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	public WalletTransactionData[] getTransactions(int start,int end) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/"+start+"/"+end,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	
	
	
	
	
	public WalletTransactionData[] getTransactions(String address) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/address/"+address,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	public WalletTransactionData[] getTransactions(String address,int start) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/address/"+address+"/"+start,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	public WalletTransactionData[] getTransactions(String address,int start,int end) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/address/"+address+"/"+start+"/"+end,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	
	
	
	
	
	
	public WalletTransactionData getTransactionByHash(String hash) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/hash/"+hash,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return new WalletTransactionData(req.RESULT.getJsonObject("transactions"));
		else throw e;
	}
	public WalletTransactionData[] getUnconfirmedTransactions() throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/unconfirmed",  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) {
			JsonArray txs=req.RESULT.getJsonArray("transactions");
			WalletTransactionData[] toReturn=new WalletTransactionData[txs.size()];		
			for(int i=0; i<=txs.size()-1; i++) {
				toReturn[i]=new WalletTransactionData(txs.getJsonObject(i));		
			}
			return toReturn;
			
		}
		else throw e;
	}
	public WalletTransactionData getUnconfirmedTransactionByHash(String hash) throws IOException, InterruptedException, WalletException {
		Request req=api.noParameterWalletRequest("/transactions/unconfirmed/"+hash,  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return new WalletTransactionData(req.RESULT.getJsonObject("transactions"));
		else throw e;
	}
	
	public TransactionStatus sendTransaction(PreparedTransaction p) throws IOException, InterruptedException, WalletException {
		JsonObjectBuilder params=getGenericParameters();
		params.add("destination", p.DESTINATION);
		params.add("amount", p.AMOUNT);
		params.add("paymentID", p.PAYMENT_ID);

		Request req=api.walletRequest("/transactions/send/basic", params.build(), getGenericHeaders().build(), "POST");
		
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		if(e==null) return new TransactionStatus(req.RESULT);
		else throw e;
		
	}
	
	
	public WalletException handleStandardErrorCodes(int code) {
		switch(code) {
		
		case 200:
		break;
		
		case 400:
			return new ProcessingException("Daemon could not parse or process request, syntax error");
		case 401: 
			return new InvalidApiKeyException("API Key is invalid");
		case 403:
			return new WalletAlreadyOpenException("Wallet: "+ACTIVE+" is already open in daemon",ACTIVE);
		case 500:
			return new ProcessingException("Error while processing request, internal 500 error");
			
		
		}
		return null;
	}
	
	public class KeyPair {
		public String ADDRESS;
		public String PUB_KEY;
		public String PRIV_KEY;
		
		public KeyPair(String s,String v,String a) {
			PUB_KEY=s;
			PRIV_KEY=v;
			ADDRESS=a;
		}
	}
}
