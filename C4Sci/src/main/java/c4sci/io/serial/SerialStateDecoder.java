package c4sci.io.serial;

/**
 * Decoder of a string value  returned through a serial port.<br>
 * @author jeanmarc.deniel
 *
 */
public interface SerialStateDecoder{
	/**
	 * Decodes a string returned by a controller.<br>
	 * The string begins by '*' and end with '\n'.
	 * @param state_string The return value to decode
	 * @param motion_engine The motion engine data to update
	 * @throws SerialStateParsingException
	 */
	void decodeState(String state_string, SerialDevice serial_device) throws SerialStateParsingException;
}
