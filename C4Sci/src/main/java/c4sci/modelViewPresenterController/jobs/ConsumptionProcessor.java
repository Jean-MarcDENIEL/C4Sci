package c4sci.modelViewPresenterController.jobs;

public interface ConsumptionProcessor<C extends Command> {
	void consumeJob(C job_to_process);
}
