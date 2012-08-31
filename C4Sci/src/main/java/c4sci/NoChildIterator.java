package c4sci;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * This class is dedicated to the structures that do not accept children. 
 * @author jeanmarc.deniel
 * @param <E>
 *
 */
public class NoChildIterator<E> implements Iterator<E>{

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
	public E next() {
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
