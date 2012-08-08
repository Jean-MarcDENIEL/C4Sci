package c4sci.modelViewPresenterController.jobs.consumption;

import c4sci.modelViewPresenterController.jobs.Command;

/**
 * This class implements a job treatment on a Command
 * @author jeanmarc.deniel
 *
 */
public abstract class JobProcessor<C_request extends Command, C_result extends Command> {
	/**
	 * This method implements the treatment on the command parameter.
	 * @param processing_cmd
	 * @return null if no result to treat afterward 
	 */
	public abstract C_result processJob(C_request processing_cmd);
}
