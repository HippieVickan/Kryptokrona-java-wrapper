package main;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import daemon_api.BlockMetaData;
import daemon_api.BlockNotAcceptedException;
import daemon_api.Daemon;
import json.API;
import wallet_api.PreparedTransaction;
import wallet_api.WalletAlreadyOpenException;
import wallet_api.WalletDaemon;
import wallet_api.WalletException;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException, BlockNotAcceptedException, WalletException {
		//time to write a proper fucking unit test. In the main class.
		//SEKReTEqgDdEqq4479pbMNX3cfkXwPYEwR5hZkzM1BTy2nGjBzoQpaDHscwAfqe2U2HsQtJPP7tNb8UXQL8AFxxjFteFfe9XCyn
		WalletDaemon d=new WalletDaemon("127.0.0.1","127.0.0.1",11898,"pass");
		//main addr: SEKReTLxUi3Ake3gdSgTBnFeyzNrydbkTcRTjcru6kwcNRgt2LyR9UUELCe7rUkAnDj4Jhujqrg9k9negP86PjRsc1PbshUR2mT
	
		d.closeWallet();
		

		
		SecureRandom rand=new SecureRandom();
		String walletFileName="testwallet.wallet";
		d.openWallet(walletFileName, "mysupersecretpassword");
		System.out.println(d.getAdresses()[0]);
		double sync=0.0;
		System.out.println(d.getSyncPercentage());
		while((sync=d.getSyncPercentage())<1) {
			System.out.println("Wallet sync: "+sync);
			Thread.sleep((long)Math.ceil(100000*(1-sync)));
		}
		Thread.sleep(1000);
		System.out.println(d.getTransactions()[0].AMOUNT);
		System.out.println(d.getBalance());
		PreparedTransaction p=new PreparedTransaction("SEKReTLxUi3Ake3gdSgTBnFeyzNrydbkTcRTjcru6kwcNRgt2LyR9UUELCe7rUkAnDj4Jhujqrg9k9negP86PjRsc1PbshUR2mT",100,new String(rand.generateSeed(32),"IBM01140"));
		d.sendTransaction(p);
	}
	
}
