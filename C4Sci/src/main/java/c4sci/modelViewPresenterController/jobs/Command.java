package c4sci.modelViewPresenterController.jobs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * This class encapsulates jobs to do and processing mechanisms :<br>
 * <ul>
 * <li>Commands can be chained in a chronological order one-to-many relationship to previous and followings Commands.</li>
 * <li>Commands can have child Commands (and recursively) : i.e Commands that need to be processed in order to get the parent Command fully processed.</li>
 * <li>Commands have a notification mechanism associated with reflex methods that are called each time a child Command notifies them.</li>
 * <li>Commands have ID that identifies their "type".</li>
 * <li>Commands have Priority, Cost in order to schedule them according to various strategies.</li>
 * </ul>
 * <br>
 * <b>Commands scheduling :</b><br>
 * Command scheduling can use chronological order and childhood relationships at the same time. <br>
 * e.g. : How to make an A Command launch three parallel B C D computations then get their result processed in a E Command ?
 * <ol>
 * <li> A command is created and queued</li>
 * <li> A command is chosen to be processed</li>
 * <li> A command creates B, C, D and E Commands</li>
 * <li> A is declared as <i>parent</i> Command of B, C, D but not as <i> previous</i> Command of them</li>
 * <li> A is declared as <i>previous</i> Command of E but not as <i>parent</i> of E</li>
 * <li> B, C, D and E are queued to be processed</li>
 * <li> At first, only B, C and D can be chosen because their {@link Command#hasUnprocessedAncestor()} returns false whereas E returns true.</li>
 * <li> B, C, D are processed and their {@link #hasBeenProcessed()} method returns true</li>
 * <li> Then A {@link #hasBeenProcessed()} method returns true.</li>
 * <li> Then E {@link #hasUnprocessedAncestor()} method returns false : E can be chosen to be processed.</li> 
 * </ol>
 * <br>
 * <b> Warning : </b> It is the responsibility of the API user to avoid cycles through the simultaneous use of previous/following and parent/child relationships : <br>
 * a child command of an "x" Command should not be a following command of this "x" command at the same time.<br> 
 * <br>
 * <b>Reflexes :</b><br>
 * A Command is notified each time a child Command has been processed.<br>
 * In this case, all the {@link CommandReflex} added through the {@link #addReflexOnChildNotification(CommandReflex)} method <br>
 * have their {@link CommandReflex#doReflex(Command)} method called.<br>
 * This can be useful when results from child Commands need to be reused. In this case, the {@link #doProcess()} method must be overridden<br>
 * and called in {@link CommandReflex#doReflex(Command)} methods.<br>
 * <br>
 * <b>Pattern : </b>This class instantiates the <b>Command</b> GoF pattern.
 * <b>Pattern : </b>Reflex methods use the <b>Strategy</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 */
public abstract class Command {
	// chronological order of Commands : one-to-many relationship
	private Command				ancestorCommand;
	private List<Command>		descendantCommands;
	// the parent Command : the Command that needs other sub-commands to be processed
	private Command				parentCommand;
	// the child Commands : Commands that need to be processed in order to get "this" fully processed.
	// in order to get the actual command entirely processed.
	private List<Command>		childCommandsList;
	
	// true if "this" command has been processed
	// does not mean that child command have been processed
	private AtomicBoolean		alreadyProcessed;
	private int 				commandPriority;
	private int					commandCost;
	private long				commandID;
	// post processing on notification from child commands
	private List<CommandReflex>		childNotificationReflexList;
	
	private static long	flagCount = 0;
	/**
	 * 
	 * @return a positive or null flag. Each successive call to this method will increment the result.
	 */
	public static synchronized long createNewFlag(){
		return flagCount++;
	}
	
	
	public interface CommandReflex{
		void doReflex(Command processed_command);
	};

	@SuppressWarnings("unused")
	private Command(){}

	/**
	 * Creates a command without any ancestor nor descendant, and with a new flag.<br>
	 * Cost and priority and flag are set to 0.
	 * @param parent_command the command depending on "this"or null if there is no parent command. <br>
	 * parent_command will be added "this" child and vice-versa
	 */
 	Command(Command parent_command){
		ancestorCommand 	= null;
		descendantCommands	= new ArrayList<Command>();

		setParentCommand(parent_command);
		if (parent_command != null){
			parent_command.addChildCommand(this);
		}
		childCommandsList	= new ArrayList<Command>();
		
		alreadyProcessed	= new AtomicBoolean(false);
		
		commandPriority		= 0;
		commandCost			= 0;
		
		commandID			= 0;
		
		//jobProcessorRequestMap	= new ConcurrentHashMap<JobProcessor<Command,Command>, Command>();
		//jobProcessorResultMap	= new ConcurrentHashMap<JobProcessor<Command,Command>, Command>();
		
		childNotificationReflexList	= new ArrayList<Command.CommandReflex>();
	}
 	/**
 	 * This method has no side-effect on the passed argument.<br>
 	 * 
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
 	 * 	<li>an ancestor</li>
 	 *  <li> 
 	 * </ul>at the same time.
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
 		modified_command.addFollowingCommands(getFollowingCommands());
 		
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
 	 * @return the type of the command.
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
	public final synchronized List<Command> getFollowingCommands(){
		return descendantCommands;
	}
	public final synchronized void setPreviousCommand(final Command anc_command){
		ancestorCommand = anc_command;
	}
	public final synchronized void addFollowingCommand(final Command desc_command){
		descendantCommands.add(desc_command);
	}
	public final synchronized void addFollowingCommands(final List<Command> desc_commands){
		descendantCommands.addAll(desc_commands);
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
	 * 
	 * @return true if all the child Commands have been processed.
	 */
	public final synchronized boolean hasAllChildrenProcessed(){
		for (Iterator<Command> _it = getChildCommandsIterator(); _it.hasNext(); ){
			if (!_it.next().hasBeenProcessed()){
				return false;
			}
		}
		return true;
	}
	/**
	 * This method indicates if the current Command and all its child Commands have been processed.<br>
	 * To work properly, this method needs the {link {@link #doProcess()} method to be called when the Command  is used.
	 * @return true if and only if the Command and all its sub-commands have been processed. False otherwise.
	 */
	public final synchronized boolean hasBeenProcessed(){
		if (!alreadyProcessed.get()){
			return false;
		}
		else{
			return hasAllChildrenProcessed();
		}
	}
	/**
	 * 
	 * @return true if an (even indirect) ancestor {@link Command#hasBeenProcessed()} method returns false.
	 */
	public final synchronized boolean hasUnprocessedAncestor(){
		for (Command _ancestor = getPreviousCommand(); _ancestor != null; _ancestor = _ancestor.getPreviousCommand()){
			if (!_ancestor.hasBeenProcessed()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method should always be called when working with a Command object.<br>
	 * This method can be overridden to achieve special behavior. e.g waiting for child Command to be processed in order to set {@link #alreadyProcessed} to true.<br>
	 * In this case, the super.{@link #doProcess()} method can be called in {@link CommandReflex#doReflex(Command)} methods of sub classes {@link CommandReflex reflexes}.<br>
	 * <br>
	 * The basic behavior of this method is : 
	 * <ol>
	 * <li> modifies the internal {@link #alreadyProcessed} state in order for the {@link #hasBeenProcessed()} method to work properly</li>
	 * <li> notify the parent Command that its been processed (but there could be child Commands still unprocessed) by calling {@link #notifyOnProcessed()}.
	 * </ol>
	 */
	public void doProcess(){
		alreadyProcessed.set(true);
		notifyOnProcessed();
	}
	
	/**
	 * Gets the result of the job processing, as computed by the {@link #doProcess()} method.
	 * @param job_proc The job processor whose result is wanted.
	 * @return The result or null in the following cases :
	 * <ul>
	 * <li>{@link #doProcess()} has not been yet called</li>
	 * <li> {@link job_proc.processJob()) returned null</li>
	 * <li> or job_proc has not been added as a JobProcessor to the Command through the 
	 */
	/*
	public final Command getProcessResult(final JobProcessor<Command, Command> job_proc){
		return jobProcessorResultMap.get(job_proc);
	}*/
	
	/**
	 * Adds a work to be processed when {@link #doProcess()} is called.
	 * @param job_proc A JobProcessor whose {@link JobProcessor#processJob(Command)} will be called.
	 * @param job_request The request passed to job_proc {@link JobProcessor#processJob(Command)} method.
	 */
	/*
	public final synchronized void addProcess(final JobProcessor<Command, Command> job_proc, Command job_request){
		jobProcessorRequestMap.put(job_proc, job_request);
	}*/

	/**
	 * Method to call when "this" or one of its child Command has been processed.
	 */
	private void notifyOnProcessed(){
		for (Iterator<CommandReflex> _it=childNotificationReflexList.iterator(); _it.hasNext();){
			_it.next().doReflex(this);
		}
		if (parentCommand != null){
			parentCommand.notifyOnProcessed();
		}
	}
	/**
	 * Adds a routine triggered on each notification received from a child Command.
	 * @param reflex_
	 */
	public final void addReflexOnChildNotification(CommandReflex reflex_){
		childNotificationReflexList.add(reflex_);
	}
	
}
