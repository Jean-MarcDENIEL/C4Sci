package c4sci.modelViewPresenterController.jobs.changes;

import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This class defines Runnable that are able to clone themselves and be run.<br>
 * <br>
 * <b>Pattern :</b> This class instantiates the <b>Prototype</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 */
public abstract class ChangeRunnablePrototype<C extends Command> implements Runnable {

	private C changeCommand; 
	
	public ChangeRunnablePrototype(C change_command){
		setChangeCommand(change_command);
	}
	/**
	 * Creates a {@link ChangeRunnablePrototype} of the same type of "this".<br>
	 * <b>Pattern :</b> This method instantiates the <b>Prototype</b> GoF pattern.
	 * @param change_command The {@link Command} the return value should work on.
	 * @return <i>null</i> if cannot create the clone adapted to the parameter.
	 */	
	abstract public ChangeRunnablePrototype<C> getClone(C change_command);

	public final C getChangeCommand() {
		return changeCommand;
	}

	public final void setChangeCommand(C changeCommand) {
		this.changeCommand = changeCommand;
	}
	/**
	 * The method to call in order to get the changes performed on the {@link Command}.
	 */
	//CHECKSTYLE:OFF
	public abstract void run();
	//CHECKSTYLE:ON

}
