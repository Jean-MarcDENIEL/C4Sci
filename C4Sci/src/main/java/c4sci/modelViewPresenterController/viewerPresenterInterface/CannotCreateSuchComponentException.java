package c4sci.modelViewPresenterController.viewerPresenterInterface;

public class CannotCreateSuchComponentException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6993894139203060715L;

	public CannotCreateSuchComponentException() {
	}

	public CannotCreateSuchComponentException(String message_) {
		super(message_);
	}

	public CannotCreateSuchComponentException(Throwable cause_) {
		super(cause_);
	}

	public CannotCreateSuchComponentException(String message_, Throwable cause_) {
		super(message_, cause_);
	}

	public CannotCreateSuchComponentException(String message_, Throwable cause_,
			boolean enable_suppression, boolean writable_stack_trace) {
		super(message_, cause_, enable_suppression, writable_stack_trace);
	}

}
