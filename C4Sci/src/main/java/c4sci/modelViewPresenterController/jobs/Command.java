package c4sci.modelViewPresenterController.jobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

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
	// chronological order of Commands
	private Command		ancestorCommand;
	private Command		descendantCommand;
	// the parent Command : the Command that needs 
	// other sub-commands to be processed
	private Command		parentCommand;
	// the child Commands : Commands that need to be processed 
	// in order to get the actual command entirely processed.
	private List<Command>	childCommandsList;
	
	// true if "this" command has been processed
	// does not mean that child command have been processed
	private AtomicBoolean		alreadyProcessed;
	private int 		commandPriority;
	private int			commandCost;
	private long		commandID;
	
	// JobProcessor / Result / Request
	// Results and requests commands can be null
	private Map<JobProcessor<Command, Command>, Command>	jobProcessorRequestMap;
	private Map<JobProcessor<Command, Command>, Command>	jobProcessorResultMap;
	
	private static long	flagCount = 0;
	/**
	 * 
	 * @return a positive or null flag. Each successive call to this method will increment the result.
	 */
	public static synchronized long createNewFlag(){
		return flagCount++;
	}
	
	@SuppressWarnings("unused")
	private Command(){}
	
	/**
	 * Creates a command without any ancestor nor descendant, and with a new flag.<br>
	 * Cost and priority and flag are set to 0.
	 * @param parent_command the command depending on "this"or null if there is no parent command.
	 */
 	Command(Command parent_command){
		ancestorCommand 	= null;
		descendantCommand	= null;
		
		setParentCommand(parent_command);
		childCommandsList	= new ArrayList<Command>();
		
		alreadyProcessed	= new AtomicBoolean(false);
		
		commandPriority		= 0;
		commandCost			= 0;
		
		commandID			= 0;
		
		jobProcessorRequestMap	= new ConcurrentHashMap<JobProcessor<Command,Command>, Command>();
		jobProcessorResultMap	= new ConcurrentHashMap<JobProcessor<Command,Command>, Command>();
	}
 	/**
 	 * This method has no side-effect on the passed argument
 	 * @param parent_command the Command whose "this" is a sub-command
 	 */
 	public final void setParentCommand(final Command parent_command){
 		parentCommand = parent_command;
 	}
 	/**
 	 * 
 	 * @return the parent Command or null if there's no
 	 */
 	public final Command getParentCommand(){
 		return parentCommand;
 	}
 	/**
 	 * This method has no side-effect on the passed argument.
 	 * @param child_command
 	 */
 	public void addChildCommand(final Command child_command){
 		childCommandsList.add(child_command);
 	}
 	public Iterator<Command> getChildCommandsIterator(){
 		return childCommandsList.iterator();
 	}

 	/**
 	 * This method copies internal state into the passed parameter.<br>
 	 * This method should be overridden and called recursively by super.modifyAsClone(modified_command) in subclasses.
 	 * @param modified_command the Command that will be modified
 	 */
 	void modifyAsClone(Command modified_command){
 		modified_command.setPreviousCommand(getPreviousCommand());
 		modified_command.setFollowingCommand(getFollowingCommand());
 		
 		modified_command.setParentCommand(getParentCommand());

 		modified_command.childCommandsList.addAll(childCommandsList);
 		
 		modified_command.alreadyProcessed.set(hasBeenProcessed());
 		
 		modified_command.setPriority(getPriority());
 		modified_command.setCost(getCost());
 		
 		modified_command.setCommandID(getCommandID());
 	}
 	/**
 	 * Sets the command type identifier 
 	 * @param flag_val
 	 */
 	public final synchronized void setCommandID(long flag_val){
 		commandID = flag_val;
 	}
 	/**
 	 * Identifies the type of command.
 	 * @return
 	 */
 	public final synchronized long getCommandID(){
 		return commandID;
 	}
 	
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
	/**
	 * This method indicates if the current Command and all its child Commands have been processed.<br>
	 * To work properly, this method needs the {link {@link #doProcess()} method to be called when the Command  is used.
	 * @return true if and only if the Command and all its sub-commands have been processed. False otherwise.
	 */
	public final boolean hasBeenProcessed(){
		if (!alreadyProcessed.get()){
			return false;
		}
		else{
			for (Iterator<Command> _it = getChildCommandsIterator(); _it.hasNext(); ){
				if (!_it.next().hasBeenProcessed()){
					return false;
				}
			}
			return alreadyProcessed.get();
		}
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
	 * This method should always be called when working with a Command object.<br>
	 * <ol>
	 * <li> this method computes results of the {link {@link JobProcessor#processJob(Command)} method on all added JobProcessors</li>
	 * <li> this method modifies the internal state in order for the {link{@link #hasBeenProcessed()} method to work properly
	 * </ol>
	 */
	public final void doProcess(){
		for (Iterator<JobProcessor<Command, Command>> _it=jobProcessorRequestMap.keySet().iterator(); _it.hasNext();){
			JobProcessor<Command, Command> _job_proc = _it.next();
			jobProcessorResultMap.put(_job_proc, _job_proc.processJob(jobProcessorRequestMap.get(_job_proc)));
		}
		alreadyProcessed.set(true);
	}
	
	/**
	 * 
	 * @return true is the command can be undone.
	 */
	public abstract boolean	isUndoable();

	
}
