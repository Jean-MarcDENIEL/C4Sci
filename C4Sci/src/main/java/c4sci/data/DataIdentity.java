package c4sci.data;

import java.util.Date;

/**
 * This class is used to identify and retrieve HierarchicalData through persistent values.<br>
 * Coherence is achieved through two values :
 * <lo>
 * <li>a time stamp</li>
 * <li>a counter value that is unique for a given time stamp</li>
 * </lo>
 * 
 * @author jeanmarc.deniel
 *
 */
public class DataIdentity {

	private long	timeStamp;
	private long	countStamp;
	
	static private	long	currentTimeStamp = new Date().getTime();
	static private  long	currentCountStamp = 0;
	static private synchronized 	long createCountStamp(){
		return currentCountStamp ++;
	}
	
	public DataIdentity() {
		timeStamp = currentTimeStamp;
		countStamp = createCountStamp();
	}
	
	public long getTimeStamp(){
		return timeStamp;
	}
	public void setTimeStamp(long time_stamp){
		timeStamp = time_stamp;
	}
	public long getCountStamp(){
		return countStamp;
	}
	public void setCountStamp(long count_stamp){
		countStamp = count_stamp;
	}
	
	public boolean equals(Object obj_){
		try{
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
