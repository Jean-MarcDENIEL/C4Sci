package c4sci.data;

import static org.junit.Assert.*;

import org.junit.Test;

import c4sci.data.exceptions.DataValueParsingException;

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

	@Test
	public void testParsingAndtString(){
		DataIdentity _id_1 = new DataIdentity();
		DataIdentity _id_2 = new DataIdentity();
		assertFalse(_id_2.equals(_id_1));
		try{
			_id_2.parseFromString(_id_1.toString());
		}
		catch(DataValueParsingException _e){
			fail("should not throw here");
		}
		assertTrue(_id_2.equals(_id_1));
		assertTrue(_id_1.equals(_id_2));

		try {
			_id_2.parseFromString("21221 2545");
		} catch (DataValueParsingException e) {
			fail("should not throw here");
		}
		assertFalse(_id_2.equals(_id_1));

		for (int _i=0; _i<5; _i++){
			try {
				switch(_i){
				case 0:
					_id_2.parseFromString("kj 120");
					break;
				case 1:
					_id_2.parseFromString("120");
					break;
				case 2:
					_id_2.parseFromString(null);
					break;
				case 3:
					_id_2.parseFromString("120.0 240");
					break;
				case 4:
					_id_2.parseFromString("120 240.0");
				}
				fail("should have thrown here");
			} catch (DataValueParsingException e) {
				assertTrue(true);
			}
		}
	}
}

