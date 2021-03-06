package c4sci.modelViewPresenterController;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.JobProcessorFactory;
import c4sci.modelViewPresenterController.jobs.RequestResultInterface;

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
public abstract class MvpcLayer <C_Reactive extends Command, C_Feedback extends Command>{
	
	private RequestResultInterface<C_Reactive>	reactiveInterface;
	private RequestResultInterface<C_Feedback>	feedbackInterface;
	
	public MvpcLayer(){
		reactiveInterface 	= new RequestResultInterface<C_Reactive>();
		feedbackInterface	= new RequestResultInterface<C_Feedback>();
	}
	
	public RequestResultInterface<C_Reactive>	getReactiveInterface(){
		return reactiveInterface;
	}
	
	public RequestResultInterface<C_Feedback>	getFeedbackInterface(){
		return feedbackInterface;
	}
	
	/**
	 * 
	 * This method gives the {@link JobProcessorFactory} containing the layer strategies in order to treat messages in the <b>reactive-to-feedback</b> direction. 
	 * 
	 * @return The {@link JobProcessorFactory} that is able to create the {@link JobProcessor} instances to treat {@link Command Commands} coming from the reactive interface and send {@link Command commands} to the feedback interface.
	 */
	public abstract JobProcessorFactory<C_Reactive, C_Feedback> getReactiveJobProcessorFactory();

	/**
	 * This method gives the {@link JobProcessorFactory} containing the layer strategies in order to treat messages in the <b>feedback-to-reactive</b> direction. 
	 * 
	 * @return The {@link JobProcessorFactory} that is able to create the {@link JobProcessor} instances to treat {@link Command Commands} coming from the feedback interface and send {@link Command commands} to the reactive interface.
	 */
	public abstract JobProcessorFactory<C_Feedback, C_Reactive> getFeedbackJobProcessorFactory();
	
}
