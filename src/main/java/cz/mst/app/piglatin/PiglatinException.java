package cz.mst.app.piglatin;

@SuppressWarnings("serial")
public class PiglatinException extends RuntimeException {

	public PiglatinException(String message) {
		super(message);
	}

	public PiglatinException(Throwable cause) {
		super(cause);
	}
	
}
