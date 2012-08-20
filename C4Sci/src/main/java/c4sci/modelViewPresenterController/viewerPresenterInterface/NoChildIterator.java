package c4sci.modelViewPresenterController.viewerPresenterInterface;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class is dedicated to the Components that do not accept child components. 
 * @author jeanmarc.deniel
 *
 */
public class NoChildIterator implements Iterator<Component> {

	public NoChildIterator() {}

	/**
	 * the iterator does give access to any Component
	 */
	public boolean hasNext() {
		return false;
	}
	/**
	 * No access.
	 */
	//CHECKSTYLE:OFF
	public Component next() {
		throw new NoSuchElementException();
	}
	/**
	 * No possible removal.
	 */
	public void remove() {
		throw new UnsupportedOperationException();
	}
	//CHECKSTYLE:ON
}
