package c4sci.data;

import c4sci.data.exceptions.CannotInstantiateDataException;

/**
 * This interface represents data that are able to create an instance of their class.
 * @author jeanmarc.deniel
 *
 */
public interface PrototypeData {
	/**
	 * 
	 * @return An uninitialized instance of the same class as "this".
	 */
	PrototypeData	newInstance() throws CannotInstantiateDataException;
}
