package wallet_api;

public class WrongPasswordException extends WalletException {
	public WrongPasswordException(String message) {
		super(message);
	}
}
