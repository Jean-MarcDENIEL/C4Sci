package c4sci.io;

public class UncoherentIOStateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 293448207360573795L;

	public UncoherentIOStateException(){
		super();
	}
	public UncoherentIOStateException(String file_name, String error_message){
		super(file_name + " : " + error_message);
	}
	public UncoherentIOStateException(String file_name, String error_message, Throwable cause_){
		super(file_name + " : " + error_message, cause_);
	}
	
}
