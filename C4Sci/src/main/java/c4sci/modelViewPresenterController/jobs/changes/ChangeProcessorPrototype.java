package c4sci.modelViewPresenterController.jobs.changes;

import java.util.List;

import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This class defines Runnable that are able to clone themselves and be run.<br>
 * <br>
 * <b>Pattern :</b> This class instantiates the <b>Prototype</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 */
public abstract class ChangeProcessorPrototype<C_request extends Command, C_result extends Command> {

	private C_request changeCommand; 
	
	public ChangeProcessorPrototype(C_request change_command){
		setChangeCommand(change_command);
	}
	/**
	 * Creates a {@link ChangeProcessorPrototype} of the same type of "this".<br>
	 * <b>Pattern :</b> This method instantiates the <b>Prototype</b> GoF pattern.
	 * @param change_command The {@link Command} the return value should work on.
	 * @return <i>null</i> if cannot create the clone adapted to the parameter.
	 */	
	abstract public ChangeProcessorPrototype<C_request, C_result> getClone(C_request change_command);

	public final C_request getChangeCommand() {
		return changeCommand;
	}

	public final void setChangeCommand(C_request changeCommand) {
		this.changeCommand = changeCommand;
	}
	/**
	 * The method to call in order to get the changes performed on the {@link Command}.
	 */
	public abstract List<C_result> processCommand();

}
