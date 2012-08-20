package c4sci.modelViewPresenterController.viewerPresenterInterface;

public class CannotPerformSuchChangeException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7869974729600046577L;

	public CannotPerformSuchChangeException() {
	}

	public CannotPerformSuchChangeException(String message_) {
		super(message_);
	}

	public CannotPerformSuchChangeException(Throwable cause_) {
		super(cause_);
	}

	public CannotPerformSuchChangeException(String message_, Throwable cause_) {
		super(message_, cause_);
	}

	public CannotPerformSuchChangeException(String message_, Throwable cause_,
			boolean enable_suppression_, boolean writable_stack_trace_) {
		super(message_, cause_, enable_suppression_, writable_stack_trace_);
	}

}
