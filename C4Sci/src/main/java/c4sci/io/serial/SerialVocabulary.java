package c4sci.io.serial;

/**
 * This class implements the commands that can be accepted by and which return can be decoded from a {@link SerialDevice}
 * @author jeanmarc.deniel
 *
 */
public interface SerialVocabulary{

	/**
	 * 
	 * @return The sequence label of the command
	 */
	 String getLabel();
	/**
	 * 
	 * @return The decoder that can be used to decode this order's result and update a {@link SerialDevice}.<br>
	 * <i>null</i> if there is nothing to decode. 
	 */
	SerialStateDecoder getResultDecoder();
	/**
	 * @param serial_device The {@link SerialDevice} whose state should be inspected to construct the method result
	 * @return The string making the parameters of the order, defined from the parameter (it seems necessary to use downcast here).
	 */
	String getCommandParameters(SerialDevice serial_device);
	
	/**
	 * 
	 * @param serial_device The device to take into account.
	 * @return The string corresponding to a state inquiry.
	 */
	 String getCommandState(SerialDevice serial_device);
}
