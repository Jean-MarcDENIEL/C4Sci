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
	
	abstract public ChangeRunnablePrototype<C> getClone(C change_command);

	public final C getChangeCommand() {
		return changeCommand;
	}

	public final void setChangeCommand(C changeCommand) {
		this.changeCommand = changeCommand;
	}
	
	public abstract void run();

}
