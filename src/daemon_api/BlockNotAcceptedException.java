package daemon_api;

public class BlockNotAcceptedException extends Exception {

	private static final long serialVersionUID = 9036460693054590831L;
	
	BlockNotAcceptedException(String s) {
		super(s);
	}
}
