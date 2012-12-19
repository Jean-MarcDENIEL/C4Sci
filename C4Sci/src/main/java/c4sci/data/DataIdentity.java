package c4sci.data;

import java.util.Date;

import c4sci.data.exceptions.DataValueParsingException;

/**
 * This class is used to identify and retrieve HierarchicalData through persistent values.<br>
 * Uniqueness of identity values is achieved through two values :
 * <ul>
 * <li>a time stamp</li>
 * <li>a counter value that is unique for a given time stamp</li>
 * </ul>
 * 
 * @author jeanmarc.deniel
 *
 */
public class DataIdentity {

	private long	timeStamp;
	private long	countStamp;

	private static long	CURRENT_TIME_STAMP	= new Date().getTime();
	private static long	CURRENT_COUNT_STAMP = 0;
	private static synchronized 	long createCountStamp(){
		return CURRENT_COUNT_STAMP ++;
	}

	/**
	 * Creates a new Identity with unique time stamp and count stamp. 
	 */
	public DataIdentity() {
		timeStamp = CURRENT_TIME_STAMP;
		countStamp = createCountStamp();
	}

	public long getTimeStamp(){
		return timeStamp;
	}
	/**
	 * <b>Warning : </b> This method can break the DataIdentity uniqueness assumption.
	 * @param time_stamp the Identity time stamp.
	 */
	public void setTimeStamp(long time_stamp){
		timeStamp = time_stamp;
	}
	public long getCountStamp(){
		return countStamp;
	}
	/**
	 * <b>Warning : </b> This method can break the DataIdentity uniqueness assumption.
	 * @param count_stamp the Identity count stamp
	 */
	public void setCountStamp(long count_stamp){
		countStamp = count_stamp;
	}

	/**
	 * @return true if obj_ if of DataIdentity type and the time stamp and the count stamp are identical. 
	 */
	//CHECKSTYLE:OFF
	public boolean equals(Object obj_){
		//CHECKSTYLE:ON
		try{
			if (obj_ == null){
				return false;
			}
			DataIdentity _obj_id = (DataIdentity) obj_;
			return (_obj_id.timeStamp == timeStamp) && (_obj_id.countStamp == countStamp);
		}
		catch(ClassCastException _e){
			return false;
		}
	}

	public int hashCode(){
		return (int)(timeStamp+countStamp);
	}

	public String toString(){
		return ""+Long.toString(getTimeStamp())+" "+Long.toString(getCountStamp());
	}
	/**
	 * Converts a string to retrieve the identity values.
	 * @param parsed_str the string containing the {@link DataIdentity} representation that is normally given by the {@link #toString()} method. 
	 * @throws DataValueParsingException in the case the parameter cannot be successfully parsed.
	 */
	public void parseFromString(String parsed_str) throws DataValueParsingException{
		if (parsed_str == null){
			throw new DataValueParsingException("long long", "null", "null expression to parse", null);
		}
		String[] _substrings = parsed_str.split(" ");
		if (_substrings.length != 2){
			throw new DataValueParsingException("long long", parsed_str, "should be composed of two longs", null);
		}
		try{
			setTimeStamp(Long.parseLong(_substrings[0]));
			setCountStamp(Long.parseLong(_substrings[1]));
		}
		catch(NumberFormatException _e){
			throw new DataValueParsingException("two longs", parsed_str, "should be composed of two longs", _e);
		}
	}
}
