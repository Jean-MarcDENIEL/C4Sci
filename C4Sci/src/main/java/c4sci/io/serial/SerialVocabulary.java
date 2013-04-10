package c4sci.io.serial;

/**
 * this class implements the commands that can be accepted by and which return can be decoded from a serial device
 * @author jeanmarc.deniel
 *
 */
public abstract class SerialVocabulary <S extends SerialDevice>{
	private String 				commandLabel;
	private SerialStateDecoder	resultDecoder;

	public SerialVocabulary(String cmd_label){
		commandLabel 	= 	cmd_label;
		resultDecoder	=	null;
	}
	/**
	 * 
	 * @return The sequence label of the command
	 */
	public String getLabel(){
		return commandLabel;
	}
	/**
	 * 
	 * @return The decoder that can be used to decode this order's result and update a {@link MotionEngine}.<br>
	 * <i>null</i> if there is nothing to decode. 
	 */
	public SerialStateDecoder getResultDecoder() {
		return resultDecoder;
	}
	/**
	 * @return The string making the parameters of the order.
	 */
	public abstract String getCommandParameters(S serial_device);
	
	/**
	 * 
	 * @param motion_engine The {@link MotionEngine} to take into account.
	 * @return The string corresponding to a state inquiry.
	 */
	public abstract String getCommandState(S serial_device);
	
	/**
	 * 
	 * @param serial_device the {@link SerialDevice} to take into account.
	 * @return The complete "set" sequence.
	 */
	public String getCommandSequence(S serial_device){
		return getCommandState(serial_device)+getCommandParameters(serial_device);
	}
}
