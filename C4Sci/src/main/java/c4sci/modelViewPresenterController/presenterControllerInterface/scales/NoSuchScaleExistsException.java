package c4sci.modelViewPresenterController.presenterControllerInterface.scales;

public class NoSuchScaleExistsException extends Exception{

	public NoSuchScaleExistsException() {
		super();
	}

	public NoSuchScaleExistsException(String message_, Throwable cause_,
			boolean enable_suppression, boolean writable_stack_trace) {
		super(message_, cause_, enable_suppression, writable_stack_trace);
	}

	public NoSuchScaleExistsException(String message_, Throwable cause_) {
		super(message_, cause_);
	}

	public NoSuchScaleExistsException(String message_) {
		super(message_);
	}

	public NoSuchScaleExistsException(Throwable cause_) {
		super(cause_);
	}

	private static final long serialVersionUID = 5233375344647683986L;


}
