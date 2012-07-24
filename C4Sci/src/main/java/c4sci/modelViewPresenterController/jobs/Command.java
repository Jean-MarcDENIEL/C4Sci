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
	private Command		previousCommand;
	private Command		nextCommand;
	private boolean		alreadyProcessed;
	Command(){
		previousCommand 	= null;
		nextCommand			= null;
		alreadyProcessed	= false;
	}
	/**
	 * Method to call to do the process.
	 */
	public synchronized void doProcess(){
		if (!alreadyProcessed){
			processJob();
			alreadyProcessed = true;
		}
	}
	/**
	 * Method to call to undo the process.
	 */
	public synchronized void undoProcess(){
		if (isUndoable() && alreadyProcessed){
			unprocessJob();
			alreadyProcessed = false;
		}
	}
	/**
	 * 
	 * @return true is the command can be undone.
	 */
	protected abstract boolean	isUndoable();
	/**
	 * This method describes the job to do. This method should be called anywhere else than in doProcess().
	 */
	protected abstract void 		processJob();
	/**
	 * This method describes how to undo the job. This method should not be called anywhere else than in undoProcess().
	 */
	abstract void 		unprocessJob();
	
}
