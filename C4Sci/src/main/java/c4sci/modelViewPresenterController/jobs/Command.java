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
	private int 		commandPriority;
	private int			commandCost;
	
	
	Command(){
		ancestorCommand 	= null;
		descendantCommand	= null;
		alreadyProcessed	= false;
		commandPriority		= 0;
		commandCost			= 0;
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
	public final Command getPreviousCommand(){
		return ancestorCommand;
	}
	/**
	 * 
	 * @return null if there is no Command following this.
	 */
	public final Command getFollowingCommand(){
		return descendantCommand;
	}
	public final void setPreviousCommand(final Command anc_command){
		ancestorCommand = anc_command;
	}
	public final void setFollowingCommand(final Command desc_command){
		descendantCommand = desc_command;
	}
	/**
	 * 	
	 * @return A subjective positive or 0 value
	 */
	public final int getPriority(){
		return commandPriority;
	}
	/**
	 * 
	 * @param priority_value A subjective positive or 0 value. Negative values will be set to 0.
	 */
	public final void setPriority(int priority_value){
		if (priority_value>0){
			commandPriority = priority_value;
		}
		else{
			commandPriority = 0;
		}
	}
	/**
	 * 	
	 * @return A subjective positive or 0 value
	 */
	public final int getCost(){
		return commandCost;
	}
	/**
	 * 
	 * @param cost_value A subjective positive or 0 value. Negative values will be set to 0.
	 */
	public final void setCost(int cost_value){
		if (cost_value >0){
			commandCost = cost_value;
		}
		else{
			commandCost = 0;
		}
	}
	
	
	
	/**
	 * 
	 * @return true is the command can be undone.
	 */
	protected abstract boolean	isUndoable();
	/**
	 * This method describes how to do the job. This method should be called nowhere else than in doProcess().
	 */
	protected abstract void 	processJob();
	/**
	 * This method describes how to undo the job. This method should be called nowhere else than in undoProcess().
	 */
	abstract void 		unprocessJob();

	
}
