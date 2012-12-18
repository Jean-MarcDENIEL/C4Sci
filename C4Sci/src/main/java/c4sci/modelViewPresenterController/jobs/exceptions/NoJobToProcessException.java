package c4sci.modelViewPresenterController.jobs.exceptions;

public class NoJobToProcessException extends Exception {

	private static final long serialVersionUID = -3854456807456386294L;

	public NoJobToProcessException(String err_msg, Throwable exc_cause){
		super(err_msg, exc_cause);
	}
}
