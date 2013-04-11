package c4sci.io.serial;

import java.io.IOException;
import java.io.InputStream;
import java.util.TooManyListenersException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * This class implements a device that can be accessed through a serial port. 
 * @author jeanmarc.deniel
 *
 */
@SuppressWarnings("restriction")
public abstract class SerialDevice {

	private String				serialPortName;
	private SerialPort			serialPort;
	private int					delayBetweenSendsMilliSec;
	private String				commandStringEnd;
	private char				commandResultEnd;

	private StringBuffer		controllerReturnToDecode;
	private SerialStateDecoder	stateDecoderToUse;

	/**
	 * This class gets controller returns from the RS232 interface.
	 * It updates the outer class's inner state by :
	 * <ol>
	 * <li> calling its {@link #setControllerReturnToDecode()} </li>
	 * <li> invoking its {@link getStateDecoderToUse()} result</li>
	 * <ol>
	 * @author jeanmarc.deniel
	 *
	 */
	private class SerialDecoder implements SerialPortEventListener {
		private InputStream			decodedStream;

		public SerialDecoder(InputStream decoded_stream){
			decodedStream = decoded_stream;
		}

		public void serialEvent( SerialPortEvent serial_event) {
			getControllerReturnToDecode().setLength(0);
			try{
				int _read_data = decodedStream.read();
				// first receives the message from the controller
				//
				while ((_read_data> -1)&& (_read_data != getCommandResultEnd())){
					getControllerReturnToDecode().append((char)_read_data);
					_read_data = decodedStream.read();
				}
				// if the message is a command result then it is decoded
				//
				if (isACommandResult(getControllerReturnToDecode().toString())){
					SerialStateDecoder _state_decoder = getStateDecoderToUse();
					if (_state_decoder != null){
						_state_decoder.decodeState(getControllerReturnToDecode().toString(), SerialDevice.this);
					}
				}
			}
			catch(IOException _e){
				_e.printStackTrace();
			}
			catch (SerialStateParsingException _e) {
				_e.printStackTrace();
			}
		}
	}


	public SerialDevice(String serial_port_name, String command_string_end, char command_result_end, int delay_between_sends_millisec, int timeout_millisec){
		setSerialPortName(serial_port_name);
		setSerialPort(null);
		setControllerReturnToDecode(new StringBuffer());
		setDelayBetweenSendsMilliSec(delay_between_sends_millisec);
		setCommandStringEnd(command_string_end);
		setCommandResultEnd(command_result_end);
		
		try {
			CommPortIdentifier _com_id = CommPortIdentifier.getPortIdentifier(getSerialPortName());
			CommPort _com_port = _com_id.open("SerialDevice", timeout_millisec);

			if (_com_port instanceof SerialPort){
				serialPort = (SerialPort)_com_port;
			}
		}
		catch (NoSuchPortException _e) {
			_e.printStackTrace();
		}
		catch(Exception _ee){
			_ee.printStackTrace();
		}
		serialPort.notifyOnDataAvailable(true);
		try {
			serialPort.addEventListener(new SerialDecoder(serialPort.getInputStream()));
		} catch (TooManyListenersException _e) {
			_e.printStackTrace();
		} catch (IOException _e) {
			_e.printStackTrace();
		}
	}

	/**
	 * Tests whether a character chain can be considered as a command result
	 * @param serial_return A string coming from the serial device
	 * @return true if the parameter is a command result, false otherwise
	 */
	public abstract boolean isACommandResult(String serial_return);
	
	/**
	 * Translates a {@link String} to an integer array. Appends "\r\n" in order to make the controller take into account this order.
	 * @param order_str An order in READI language.
	 * @return The corresponding integer array to send through the serial port.
	 */
	private int[] translateToPort(String order_str){
		int[] _res = new int[order_str.length()+getCommandStringEnd().length()];
		int _i=0;
		for (; _i<order_str.length(); _i++){
			_res[_i] = (int) order_str.charAt(_i);
		}
		for (int _j=0; _j<getCommandStringEnd().length(); _j++){
			_res[_i++] = getCommandStringEnd().charAt(_j);
		}
		//_res[_i++] = '\r';
		//_res[_i++] = '\n';
		return _res;
	}
	
	/**
	 * Sends an order through the serial port and prepare 
	 * the return signal decoding.
	 * 
	 * @param order_to_send
	 */
	public void sendOrderAndSetDecoder(SerialVocabulary order_to_send){
		setStateDecoderToUse(order_to_send.getResultDecoder());

		// replaces getcommandSequence
		String _order_str = order_to_send.getCommandState(this) + order_to_send.getCommandParameters(this);
		int[] _order_int_array = translateToPort(_order_str);
		for (int _msg_data : _order_int_array){
			try {
				serialPort.getOutputStream().write(_msg_data);
			} catch (IOException _e) {
				_e.printStackTrace();
			}
		}
		try {
			Thread.sleep(getDelayBetweenSendsMilliSec());
		} catch (InterruptedException _e) {
			_e.printStackTrace();
		}
	}
	
	
	

	// -----------------------------------------------------------------
	// 				ACCESSORS 
	// -----------------------------------------------------------------
	
	public final synchronized StringBuffer getControllerReturnToDecode() {
		return controllerReturnToDecode;
	}

	private synchronized void setControllerReturnToDecode(StringBuffer controller_return_to_decode) {
		this.controllerReturnToDecode = controller_return_to_decode;
	};	
	
	public final synchronized SerialStateDecoder getStateDecoderToUse() {
		return stateDecoderToUse;
	}

	public final synchronized void setStateDecoderToUse(SerialStateDecoder state_decoder_to_use) {
		this.stateDecoderToUse = state_decoder_to_use;
	}

	public synchronized String getSerialPortName() {
		return serialPortName;
	}

	private synchronized void setSerialPortName(String serial_port_name) {
		this.serialPortName = serial_port_name;
	}

	public SerialPort getSerialPort() {
		return serialPort;
	}

	private void setSerialPort(SerialPort serial_port) {
		this.serialPort = serial_port;
	}

	private int getDelayBetweenSendsMilliSec() {
		return delayBetweenSendsMilliSec;
	}

	private void setDelayBetweenSendsMilliSec(int delay_between_sends_milliSec) {
		this.delayBetweenSendsMilliSec = delay_between_sends_milliSec;
	}

	public String getCommandStringEnd() {
		return commandStringEnd;
	}

	private void setCommandStringEnd(String command_string_end) {
		this.commandStringEnd = command_string_end;
	}

	public char getCommandResultEnd() {
		return commandResultEnd;
	}

	private void setCommandResultEnd(char command_result_end) {
		this.commandResultEnd = command_result_end;
	} 
}
