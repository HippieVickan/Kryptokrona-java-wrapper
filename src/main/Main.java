package main;

import java.io.IOException;
import java.io.StringReader;
import java.util.Random;

import daemon_api.BlockMetaData;
import daemon_api.BlockNotAcceptedException;
import daemon_api.Daemon;
import json.API;
import wallet_api.WalletAlreadyOpenException;
import wallet_api.WalletDaemon;
import wallet_api.WalletException;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException, BlockNotAcceptedException, WalletException {
		//time to write a proper fucking unit test. In the main class.
		
		WalletDaemon d=new WalletDaemon("127.0.0.1","127.0.0.1",11898,"pass");

	
		
		

		
		long l=new Random().nextLong();

		String walletFileName="testwallet"+l+".wallet";
		d.createWallet(walletFileName, "mysupersecretpassword");
		String pView=d.getKeys();
		String initialAdress=d.getAdresses()[0];
		
		
		
		
		
		System.out.println(initialAdress.length());
	
		String pSpend=d.getKeyPairAdress(initialAdress).PRIV_KEY;
		d.closeWallet();
		d.importByKey("imported"+walletFileName, "myothersupersecretpassword", 30000, pView, pSpend);
	}
	
}
