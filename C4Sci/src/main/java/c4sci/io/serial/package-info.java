/**
* This package provides a simple way to interact with serial ports. <br>
* The way to proceed is the following :
* <ol>
*
* 	<li> create classes implementing the {@link c4sci.io.serial.SerialStateDecoder} : 
* 		<ul>
* 			<li>one class for each kind of return message to decode</li>
* 			<li>insert side-effects through the {@link c4sci.io.serial.SerialStateDecoder#decodeState(String, SerialDevice)} implementation (there it is necessary to use some downcast)</li>
* 		</ul>
* 	</li>
* 	<li>create a serial order enumeration that implements the {@link c4sci.io.serial.SerialVocabulary} :
* 		<ul>
* 			<li>each enumerated corresponds to a command whose name is given to its constructor</li>
* 			<li>if a command implies decoding a serial return code, 
* 				<ol>
* 					<li>it should be done with an instance of the {@link c4sci.io.serial.SerialStateDecoder} previously defined implementation or an anonymous one.</li>
* 					<li>it can get informations from the {@link c4sci.io.serial.SerialDevice} passed as argument to its {@link c4sci.io.serial.SerialVocabulary#getCommandParameters(SerialDevice)} method  </li>
* 				<ol>
* 		</ul>
* 	</li>
* 	<li> create a CC class :
* 		<ul>
* 			<li>make this class extend the  {@link c4sci.io.serial.SerialDevice} class </li>
* 			<li>make this class implement an interface with convenient methods </li>
* 			<li>when implementing serial communicating methods use the {@link c4sci.io.serial.SerialDevice#sendOrderAndSetDecoder(SerialVocabulary)} methods with the previously defined enumerated orders
* 		</ul>
* </ol>
*/
package c4sci.io.serial;