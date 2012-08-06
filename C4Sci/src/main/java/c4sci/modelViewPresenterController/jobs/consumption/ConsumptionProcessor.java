package c4sci.modelViewPresenterController.jobs.consumption;

import c4sci.modelViewPresenterController.jobs.Command;

public interface ConsumptionProcessor<C extends Command> {
	void consumeJob(C job_to_process);
}
