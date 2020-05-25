package wallet_api;

public class InvalidApiKeyException  extends WalletException {
	public InvalidApiKeyException(String message) {
		super(message);
	}
}