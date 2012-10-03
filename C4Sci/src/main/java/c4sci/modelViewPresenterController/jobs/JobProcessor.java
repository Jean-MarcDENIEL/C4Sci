package c4sci.modelViewPresenterController.jobs;

import java.util.List;


/**
 * This class implements a job treatment on a Command.<br>
 * <br>
 * <b>Pattern : </b> This class implements the <b>Strategy</b> GoF pattern.
 * @author jeanmarc.deniel
 *
 */
public abstract class JobProcessor<C_request extends Command, C_result extends Command> {
	/**
	 * This method implements the treatment on the command parameter.
	 * @param processing_cmd The request Command
	 * @return null if no result to treat afterward, or a list of child Commands of processing_cmd
	 */
	public abstract List<C_result> processJob(C_request processing_cmd);
	
	/**
	 * <b>Pattern :</b> this method instantiates the <b>Prototype</b> GoF pattern.
	 * @return an instance of the same type of "this", or <i>null</i> if cannot perform such an operation.
	 */
	@SuppressWarnings("unchecked")
	public JobProcessor<C_request, C_result> getClone(){
		try {
			return getClass().newInstance();
		} catch (InstantiationException _e) {
			return null;
		} catch (IllegalAccessException _e) {
			return null;
		}
	}
}
