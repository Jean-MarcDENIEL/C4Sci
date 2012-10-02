package c4sci.modelViewPresenterController.jobs.changes;

import java.util.ArrayList;
import java.util.List;

import c4sci.modelViewPresenterController.jobs.Command;
import c4sci.modelViewPresenterController.jobs.JobProcessor;

public class ChangeJobProcessor<C_request extends Command, C_result extends Command> extends JobProcessor<C_request, C_result> {

	private ChangeProcessorFactory<C_request, C_result> processorFactory;
	
	public ChangeJobProcessor(ChangeProcessorFactory<C_request, C_result> processor_factory){
		processorFactory = processor_factory;
	}
	
	public List<C_result> processJob(C_request processing_cmd) {
		try {
			ChangeProcessor<C_request, C_result> _processor = processorFactory.createChangePerformer(processing_cmd);
			return _processor.processCommand();
		} catch (CannotPerformSuchChangeException _e) {
			return new ArrayList<C_result>();
		}
	}

}
