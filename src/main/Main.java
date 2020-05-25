package main;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;

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
	
		d.createWallet("testwallet.wallet", "mysupersecretpassword");
		d.closeWallet();
		d.openWallet("testwallet.wallet", "mysupersecretpassword");

	}
	
}
