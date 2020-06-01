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
		//SEKReTEqgDdEqq4479pbMNX3cfkXwPYEwR5hZkzM1BTy2nGjBzoQpaDHscwAfqe2U2HsQtJPP7tNb8UXQL8AFxxjFteFfe9XCyn
		WalletDaemon d=new WalletDaemon("127.0.0.1","127.0.0.1",11898,"pass");

	
		
		

		
		long l=new Random().nextLong();

		String walletFileName="testwallet.wallet";
		d.createWallet(walletFileName, "mysupersecretpassword");
		System.out.println(d.getAdresses()[0]);
		
	}
	
}
