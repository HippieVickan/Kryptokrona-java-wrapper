package wallet_api;

import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import json.API;
import json.Request;

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
	
	public void createNewAdress() throws IOException, InterruptedException, WalletException {

		Request req=api.noParameterWalletRequest("/addresses/create",  getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
	}
	public void importWalletKeys(String fileName, String password,int height,String pView,String pSpend) throws IOException, InterruptedException, WalletException  {
		
		JsonObjectBuilder params=getGenericParameters();
		params.add("filename", fileName);
		params.add("password", password);
		params.add("scanHeight", height);
		params.add("privateViewKey", pView);
		params.add("privateSpendKey", pSpend);
		
		Request req=api.walletRequest("/wallet/import/key", params.build(), getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return;
		else throw e;
		
	}
	
	
	
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
	
	
	public String getKeys() throws IOException, InterruptedException, WalletException {
		
		Request req=api.noParameterWalletRequest("/keys",  getGenericHeaders().build(), "GET");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return req.RESULT.getString("privateViewKey");
		else throw e;
	}
	public KeyPair getKeyPairAdress(String adress) throws WalletException, IOException, InterruptedException {
		Request req=api.noParameterWalletRequest("/keys/"+adress,  getGenericHeaders().build(), "POST");
		WalletException e= handleStandardErrorCodes(req.RESPONSE_CODE);
		
		if(e==null) return new KeyPair(req.RESULT.getString("privateSpendKey"),req.RESULT.getString("publicSpendKey"));
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
		public String PUB_KEY;
		public String PRIV_KEY;
		
		public KeyPair(String s,String v) {
			PUB_KEY=s;
			PRIV_KEY=v;
		}
	}
}
