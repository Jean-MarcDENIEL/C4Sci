package c4sci.modelViewPresenterController.presenter.constraintPresenter;

public class CyclicGraphNotAllowedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2942131737227796687L;

	private int	referenceComponent;
	private int constrainedComponent;

	@SuppressWarnings("unused")
	private CyclicGraphNotAllowedException() {
	}

	public CyclicGraphNotAllowedException(int ref_comp, int constr_comp){
		setReferenceComponent(ref_comp);
		setConstrainedComponent(constr_comp);
	}

	public CyclicGraphNotAllowedException(int ref_comp, int constr_comp, String message_e) {
		super(message_e);
		setReferenceComponent(ref_comp);
		setConstrainedComponent(constr_comp);
	}
	public CyclicGraphNotAllowedException(int ref_comp, int constr_comp, Throwable cause_e) {
		super(cause_e);
		setReferenceComponent(ref_comp);
		setConstrainedComponent(constr_comp);
	}
	public CyclicGraphNotAllowedException(int ref_comp, int constr_comp, String message_e, Throwable cause_e) {
		super(message_e, cause_e);
		setReferenceComponent(ref_comp);
		setConstrainedComponent(constr_comp);
	}
	public CyclicGraphNotAllowedException(int ref_comp, int constr_comp, String message_e, Throwable cause_e,
			boolean enable_suppression, boolean writable_stack_trace) {
		super(message_e, cause_e, enable_suppression, writable_stack_trace);
		setReferenceComponent(ref_comp);
		setConstrainedComponent(constr_comp);
	}
/*
	@SuppressWarnings("unused")
	private CyclicGraphNotAllowedException(String message_) {
		super(message_);
	}

	@SuppressWarnings("unused")
	private CyclicGraphNotAllowedException(Throwable cause_) {
		super(cause_);
	}

	@SuppressWarnings("unused")
	private CyclicGraphNotAllowedException(String message_, Throwable cause_) {
		super(message_, cause_);
	}

	@SuppressWarnings("unused")
	private CyclicGraphNotAllowedException(String message_, Throwable cause_,
			boolean enable_suppression, boolean writable_stack_trace) {
		super(message_, cause_, enable_suppression, writable_stack_trace);
	}
*/
	public final int getReferenceComponent() {
		return referenceComponent;
	}

	public final void setReferenceComponent(int reference_component) {
		this.referenceComponent = reference_component;
	}

	public final int getConstrainedComponent() {
		return constrainedComponent;
	}

	public final void setConstrainedComponent(int constrained_component) {
		this.constrainedComponent = constrained_component;
	}

}
