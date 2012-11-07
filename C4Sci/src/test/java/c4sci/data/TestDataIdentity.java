package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestDataIdentity {

	@Test
	public void testEqualsObject() {
		DataIdentity _id_1 = new DataIdentity();
		
		DataIdentity _id_2 = new DataIdentity();
		
		DataIdentity _id_3 = new DataIdentity();
		_id_3.setCountStamp(_id_1.getCountStamp());
		_id_3.setTimeStamp(_id_1.getTimeStamp());
		
		assertFalse(_id_1.equals(new Integer(20)));
		assertTrue(_id_1.equals(_id_1));
		assertFalse(_id_1.equals(_id_2));
		assertTrue(_id_1.equals(_id_3));
		
		assertTrue(_id_1.hashCode() == _id_1.hashCode());
		assertFalse(_id_1.hashCode() == _id_2.hashCode());
		assertTrue(_id_1.hashCode() == _id_3.hashCode());
				
	}

}
