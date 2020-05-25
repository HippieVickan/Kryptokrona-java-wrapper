package wallet_api;

public class WalletAlreadyOpenException extends WalletException {
	public String ACTIVE;
	public WalletAlreadyOpenException(String message,String active) {
		super(message);
		ACTIVE=active;
	}
	
}
