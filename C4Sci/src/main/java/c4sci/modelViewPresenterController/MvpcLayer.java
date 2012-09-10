package c4sci.modelViewPresenterController;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.consumption.JobConsumerThread;

/**
 * MVPC Layers offer services that are necessary to initialize the application.<br>
 * <br>
 * MVPC Layers are bound to one or two {@link RequestResultInterface interfaces} furnishing {@link Command commands} to process.<br>
 * MVPC Layers are shared by two groups of {@link JobConsumerThread job consuming threads} :
 * <ul>
 * <li> "reactive" threads : they process {@link Command commands} that are user interaction requests.</li>
 * <li> "feedback" threads : they process {@link Command commands} that are the result of previously processed "user side" requests.</li>  
 * </ul>
 * The overall sequence is this :<br>
 * <img src="doc-files/4 layers jobs treatment sequence.jpg" width="100%">
 * @author jeanmarc.deniel
 *
 */
public interface MvpcLayer <C_Reactive extends Command, C_Feedback extends Command>{
	/**
	 * This method programs a {@link JobConsumerThread} in order to properly process {@link Command Commands} with different {@link Command#getCommandID()} values incoming from the "reactive side" {@link RequestResultInterface}.<br>
	 * To achieve this goal, the argument thread's {@link JobConsumerThread#associateFlagToProcessor(long, c4sci.modelViewPresenterController.jobs.JobProcessor) associateFlagToProcessor(...)} method is called for the various {@link Command#getCommandID()} values.<br>
	 * <br>
	 * <b>Pattern : </b> This method relies on the <b>Strategy</b> pattern implemented by the {@link JobConsumerThread} class.   
	 * @param consumer_thread The thread to program.
	 */
	void uploadReactiveThreadStrategy(JobConsumerThread<C_Reactive, C_Feedback> consumer_thread);
	/**
	 * This method programs a {@link JobConsumerThread} in order to properly process {@link Command Commands} with different {@link Command#getCommandID()} values incoming from the "feedback side" {@link RequestResultInterface}.<br>
	 * To achieve this goal, the argument thread's {@link JobConsumerThread#associateFlagToProcessor(long, c4sci.modelViewPresenterController.jobs.JobProcessor) associateFlagToProcessor(...)} method is called for the various {@link Command#getCommandID()} values.<br>
	 * <br>
	 * <b>Pattern : </b> This method relies on the <b>Strategy</b> pattern implemented by the {@link JobConsumerThread} class.   
	 * @param consumer_thread The thread to program.
	 */
	void uploadFeedbackThreadStrategy(JobConsumerThread<C_Reactive, C_Feedback> consumer_thread);
}
