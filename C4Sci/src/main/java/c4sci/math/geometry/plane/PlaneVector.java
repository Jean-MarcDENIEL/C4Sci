package c4sci.math.geometry.plane;

import c4sci.math.algebra.Floatings;

/**
 * 2D vectors and points.
 * 
 * @author jeanmarc.deniel
 *
 */
public class PlaneVector {
	private float xCoor;
	private float yCoor;
	
	public PlaneVector(){
		setX(.0f);
		setY(.0f);
	}
	public PlaneVector(float x_coor, float y_coor){
		setX(x_coor);
		setY(y_coor);
	}
	public PlaneVector(PlaneVector other_v){
		setX(other_v.getX());
		setY(other_v.getY());
	}
	
	public float getX() {
		return xCoor;
	}
	public void setX(float xCoor) {
		this.xCoor = xCoor;
	}
	public float getY() {
		return yCoor;
	}
	public void setY(float yCoor) {
		this.yCoor = yCoor;
	}
	public float dotOp(PlaneVector other_v){
		return getX()*other_v.getX() + getY()*other_v.getY();
	}
	/**
	 * @return this - other_v
	 */
	public PlaneVector minusOp(PlaneVector other_v){
		return new PlaneVector(getX()-other_v.getX(), getY()-other_v.getY());
	}
	/**
	 * @return this + other_v
	 */
	public PlaneVector plusOp(PlaneVector other_v){
		return new PlaneVector(getX()+other_v.getX(), getY() + other_v.getY());
	}
	public boolean isEqualOp(PlaneVector other_v){
		return Floatings.isEqual(getX(), other_v.getX()) && Floatings.isEqual(getY(), other_v.getY());
	}
	public PlaneVector equalOp(PlaneVector other_v){
		setX(other_v.getX());
		setY(other_v.getY());
		return this;
	}
}
