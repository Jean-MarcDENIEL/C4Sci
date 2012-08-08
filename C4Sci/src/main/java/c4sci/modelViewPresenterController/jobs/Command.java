package c4sci.modelViewPresenterController.jobs;

/**
 * This class encapsulates jobs to do.<br>
 * Commands can be chained to previous and followings.
 * <br><br>
 * 
 * 
 * <b>Pattern : </b>This class instantiates the <b>Command</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 */
public abstract class Command {
	private Command		ancestorCommand;
	private Command		descendantCommand;
	private boolean		alreadyProcessed;
	private int 		commandPriority;
	private int			commandCost;
	private long		commandFlag;
	
	private static long	flagCount = 0;
	/**
	 * 
	 * @return a positive or null flag. Each successive call to this method will increment the result.
	 */
	public static synchronized long createNewFlag(){
		return flagCount++;
	}
	
	/**
	 * Creates a command without any ancestor nor descendant, and with a new flag.<br>
	 * Cost and priority and flag are set to 0.
	 */
 	Command(){
		ancestorCommand 	= null;
		descendantCommand	= null;
		alreadyProcessed	= false;
		commandPriority		= 0;
		commandCost			= 0;
		commandFlag			= 0;
	}

 	/**
 	 * This method copies internal state into the passed parameter.<br>
 	 * This method should be overridden and called recursively by super.modifyAsClone(modified_command) in subclasses.
 	 * @param modified_command the Command that will be modified
 	 */
 	void modifyAsClone(Command modified_command){
 		modified_command.setPreviousCommand(getPreviousCommand());
 		modified_command.setFollowingCommand(getFollowingCommand());
 		modified_command.alreadyProcessed = hasBeenProcessed();
 		modified_command.setPriority(getPriority());
 		modified_command.setCost(getCost());
 		modified_command.setFlag(getFlag());
 	}
 	public final synchronized void setFlag(long flag_val){
 		commandFlag = flag_val;
 	}
 	public final synchronized long getFlag(){
 		return commandFlag;
 	}
 	
	/**
	 * Method to call to do the process.
	 */
	/*public final synchronized void doProcess(){
		if (!alreadyProcessed){
			processJob();
			alreadyProcessed = true;
		}
	}*/
 	
	/**
	 * Method to call to undo the process.
	 */
	/*
	public final synchronized void undoProcess(){

		if (isUndoable() && alreadyProcessed){
			unprocessJob();
			alreadyProcessed = false;
		}
	}
	 */
 	
 	/**
	 * 
	 * @return null if there is no previous Command.
	 */
	public final synchronized Command getPreviousCommand(){
		return ancestorCommand;
	}
	/**
	 * 
	 * @return null if there is no Command following this.
	 */
	public final synchronized Command getFollowingCommand(){
		return descendantCommand;
	}
	public final synchronized void setPreviousCommand(final Command anc_command){
		ancestorCommand = anc_command;
	}
	public final synchronized void setFollowingCommand(final Command desc_command){
		descendantCommand = desc_command;
	}
	/**
	 * 	
	 * @return A subjective positive or 0 value
	 */
	public final synchronized int getPriority(){
		return commandPriority;
	}
	/**
	 * 
	 * @param priority_value A subjective positive or 0 value. Negative values will be set to 0.
	 */
	public final synchronized void setPriority(int priority_value){
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
	public final synchronized int getCost(){
		return commandCost;
	}
	/**
	 * 
	 * @param cost_value A subjective positive or 0 value. Negative values will be set to 0.
	 */
	public final synchronized void setCost(int cost_value){
		if (cost_value >0){
			commandCost = cost_value;
		}
		else{
			commandCost = 0;
		}
	}
	public final synchronized boolean hasBeenProcessed(){
		return alreadyProcessed;
	}
	public final boolean hasUnprocessedAncestor(){
		for (Command _ancestor = getPreviousCommand(); _ancestor != null; _ancestor = _ancestor.getPreviousCommand()){
			if (!_ancestor.hasBeenProcessed()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return true is the command can be undone.
	 */
	protected abstract boolean	isUndoable();
	/**
	 * This method describes how to do the job. This method should be called nowhere else than in doProcess().
	 */
	/*
	protected abstract void 	processJob();
	*/
	/**
	 * This method describes how to undo the job. This method should be called nowhere else than in undoProcess().
	 */
	/*
	abstract void 		unprocessJob();
	*/

	
}
