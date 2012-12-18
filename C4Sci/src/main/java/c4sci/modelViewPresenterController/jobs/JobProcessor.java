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
	
}
