package c4sci.modelViewPresenterController.jobs;

/**
 * This class encapsulates jobs to do.<br>
 * Commands can be chained to previous and followings.<br><br>
 * 
 * <b>Pattern : </b>This class instantiates the Command GoF pattern.
 * @author jeanmarc.deniel
 *
 */
public abstract class Command {
	private Command		ancestorCommand;
	private Command		descendantCommand;
	private boolean		alreadyProcessed;
	Command(){
		ancestorCommand 	= null;
		descendantCommand			= null;
		alreadyProcessed	= false;
	}
	/**
	 * Method to call to do the process.
	 */
	public final synchronized void doProcess(){
		if (!alreadyProcessed){
			processJob();
			alreadyProcessed = true;
		}
	}
	/**
	 * Method to call to undo the process.
	 */
	public final synchronized void undoProcess(){
		if (isUndoable() && alreadyProcessed){
			unprocessJob();
			alreadyProcessed = false;
		}
	}
	/**
	 * 
	 * @return null if there is no previous Command.
	 */
	public final Command getAncestor(){
		return ancestorCommand;
	}
	/**
	 * 
	 * @return null if there is no Command following this.
	 */
	public final Command getDescendant(){
		return descendantCommand;
	}
	public final void setAncestor(final Command anc_command){
		ancestorCommand = anc_command;
	}
	public final void setDescendant(final Command desc_command){
		descendantCommand = desc_command;
	}
	
	/**
	 * 
	 * @return true is the command can be undone.
	 */
	protected abstract boolean	isUndoable();
	/**
	 * This method describes the job to do. This method should be called nowhere else than in doProcess().
	 */
	protected abstract void 	processJob();
	/**
	 * This method describes how to undo the job. This method should be called nowhere else than in undoProcess().
	 */
	abstract void 		unprocessJob();
	
}
