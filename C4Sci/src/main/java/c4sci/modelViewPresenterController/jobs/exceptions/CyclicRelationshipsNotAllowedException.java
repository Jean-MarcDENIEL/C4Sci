package c4sci.modelViewPresenterController.jobs.exceptions;

public class CyclicRelationshipsNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7771444710523536717L;

	public CyclicRelationshipsNotAllowedException() {
	}

	public CyclicRelationshipsNotAllowedException(String message_) {
		super(message_);
	}

	public CyclicRelationshipsNotAllowedException(Throwable cause_) {
		super(cause_);
	}

	public CyclicRelationshipsNotAllowedException(String message_,
			Throwable cause_) {
		super(message_, cause_);
	}

	public CyclicRelationshipsNotAllowedException(String message_,
			Throwable cause_, boolean enable_suppression,
			boolean writable_stack_trace) {
		super(message_, cause_, enable_suppression, writable_stack_trace);
	}

}
