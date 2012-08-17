package c4sci.data;

import java.util.Date;

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
	
	private static long	currentTimeStamp = new Date().getTime();
	private static long	currentCountStamp = 0;
	private static synchronized 	long createCountStamp(){
		return currentCountStamp ++;
	}
	
	/**
	 * Creates a new Identity with unique time stamp and count stamp. 
	 */
	public DataIdentity() {
		timeStamp = currentTimeStamp;
		countStamp = createCountStamp();
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}
	/**
	 * <b>Warning : </b> This method can break the DataIdentity uniqueness assumption.
	 * @param time_stamp the Identity timestamp.
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

}
