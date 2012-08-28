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
	
	public String toString(){
		return "" + getX() + " " + getY();
	}
	
	public static final PlaneVector parseVector(String str_to_parse) throws NumberFormatException{
		if (str_to_parse == null)
			throw new NumberFormatException(null);
		String[] _substrings = str_to_parse.split(" ");
		PlaneVector _res = new PlaneVector();
		if (_substrings.length == 2){
			_res.setX(Float.parseFloat(_substrings[0]));
			_res.setY(Float.parseFloat(_substrings[1]));
		}
		else {
			throw new NumberFormatException(str_to_parse);
		}
		return _res;
	}
	
	public final float getX() {
		return xCoor;
	}
	public final void setX(float x_coor) {
		this.xCoor = x_coor;
	}
	public final float getY() {
		return yCoor;
	}
	public final void setY(float y_coor) {
		this.yCoor = y_coor;
	}
	public final float dotOp(PlaneVector other_v){
		return getX()*other_v.getX() + getY()*other_v.getY();
	}
	/**
	 * @return this - other_v
	 */
	public final PlaneVector minusOp(PlaneVector other_v){
		return new PlaneVector(getX()-other_v.getX(), getY()-other_v.getY());
	}
	/**
	 * @return this + other_v
	 */
	public final PlaneVector plusOp(PlaneVector other_v){
		return new PlaneVector(getX()+other_v.getX(), getY() + other_v.getY());
	}
	public final boolean isEqualOp(PlaneVector other_v){
		return Floatings.isEqual(getX(), other_v.getX()) && Floatings.isEqual(getY(), other_v.getY());
	}
	public final PlaneVector equalOp(PlaneVector other_v){
		setX(other_v.getX());
		setY(other_v.getY());
		return this;
	}
}
