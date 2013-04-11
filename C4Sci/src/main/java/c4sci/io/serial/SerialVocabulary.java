package c4sci.io.serial;

/**
 * This class implements the commands that can be accepted by and which return can be decoded from a serial device
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
	 * @return The decoder that can be used to decode this order's result and update a {@link MotionEngine}.<br>
	 * <i>null</i> if there is nothing to decode. 
	 */
	SerialStateDecoder getResultDecoder();
	/**
	 * @return The string making the parameters of the order.
	 */
	String getCommandParameters(SerialDevice serial_device);
	
	/**
	 * 
	 * @param motion_engine The {@link MotionEngine} to take into account.
	 * @return The string corresponding to a state inquiry.
	 */
	 String getCommandState(SerialDevice serial_device);
}
