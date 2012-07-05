package c4sci.math.geometry.space;

import c4sci.math.algebra.Floatings;

/**
 * 4D space vector (X,Y,Z,W) adapted to matrix transforms.
 * Scale effect : 	x = X/W
 * 					y = Y/W
 * 					z = Z/W
 * W is a scale factor and should never be 0.0.
 *
 * Comparisons use Floatings thresholds.
 * 
 * @author jeanmarc.deniel
 *
 */
public class SpaceVector {

	private static final int	NB_COOR = 4;
	private float coorTab[];
	/**
	 * Creates a (0,0,0,1) vector
	 */
	public SpaceVector(){
		coorTab= new float[NB_COOR];
		setX(0.0f);
		setY(0.0f);
		setZ(0.0f);
		setW(1.0f);
	}
	/**
	 * Creates a (val_x,val_y,val_z,1) vector
	 */
	public SpaceVector(float val_x, float val_y, float val_z){
		coorTab = new float[NB_COOR];
		setW(1.0f);
		setX(val_x);
		setY(val_y);
		setZ(val_z);
	}
	public SpaceVector(float val_x, float val_y, float val_z, float val_w){
		coorTab = new float[NB_COOR];
		setW(1.0f);
		setX(val_x);
		setY(val_y);
		setZ(val_z);
		setW(val_w);
	}
	public SpaceVector(SpaceVector other_vec){
		System.arraycopy(other_vec.coorTab, 0, coorTab, 0, NB_COOR);
	}
	public enum CoorName {
		X(0), Y(1), Z(2), W(3);
		static final CoorName XYZ_TAB[] = {X, Y, Z};
		CoorName (){
			coorIndex = 0;
		}
		CoorName (int val_coor){
			coorIndex = val_coor;
		}
		public final int getCoorValue(){
			return coorIndex;
		}
		private int coorIndex;
	};

	/**************** GET/SET METHODS *****************************/
	/**
	 * 
	 * @param x_val : X <- x_val/W
	 */
	public	final void setX(float x_val){
		setCoor(CoorName.X, x_val);
	}
	/**
	 * 	
	 * @param y_val : Y <- y_val/W
	 */
	public final void setY(float y_val){
		setCoor(CoorName.Y, y_val);
	}
	/**
	 * 
	 * @param z_val : Z <- z_val/W
	 */
	public final void setZ(float z_val){
		setCoor(CoorName.Z, z_val);
	}
	public final void setW(float w_val){
		setCoor(CoorName.W, w_val);
	}
	/**
	 * 
	 * @param val_coor : if different from W, corresponding coordinate <- val_coor/W 
	 */
	public final void setCoor(CoorName n_coor, float val_coor){
		setCoor(n_coor.getCoorValue(), val_coor);
	}
	/**
	 * 
	 * @param n_coor	0=X, 1=Y, 2=Z, 3=W
	 * @param val_coor : if different from W, corresponding coordinate <- val_coor/W 
	 */
	public final void setCoor(int n_coor, float val_coor){
		if (n_coor == CoorName.W.getCoorValue()){
			coorTab[n_coor] = val_coor;
		}
		else{
			coorTab[n_coor] = val_coor/getW();
		}
	}
	/**
	 * 
	 * @return X/W
	 */
	public final float getX(){
		return getCoor(CoorName.X);
	}
	/**
	 * 
	 * @return Y/W
	 */
	public final float getY(){
		return getCoor(CoorName.Y);
	}
	/**
	 * 	
	 * @return Z/W
	 */
	public final float getZ(){
		return getCoor(CoorName.Z);
	}
	public final float getW(){
		return getCoor(CoorName.W);
	}
	/**
	 * 	
	 * @return if different from W, corresponding coordinate / W
	 */
	public final float getCoor(CoorName n_coor){
		return getCoor(n_coor.getCoorValue());
	}
	/**
	 * 
	 * @param n_coor	0=X, 1=Y, 2=Z, 3=W
	 * @return if different from W, corresponding coordinate / W
	 */
	public final float getCoor(int n_coor){
		float _mult = (n_coor == CoorName.W.getCoorValue())? 1.0f : coorTab[CoorName.W.getCoorValue()];
		return coorTab[n_coor] * _mult;
	}

	public final float getNorm(){
		return (float) Math.sqrt(getNorm2());
	}
	/**
	 * @return square power of norm.
	 */
	public final float getNorm2(){
		return (float)(Math.pow(getX(), 2.0) + Math.pow(getY(), 2.0) + Math.pow(getZ(), 2.0));
	}

	/**
	 * Sets norm to 1.0
	 */
	public final void normalizeVector(){
		setW(getW()/ getNorm());
	}

	/**
	 * Computes X Y Z so that W is set to 1.0
	 */
	public final void normalizeW(){
		float _tab_val[] = new float[NB_COOR-1];
		int _i = 0;
		for (CoorName _coor : CoorName.XYZ_TAB){
			_tab_val[_i++] = getCoor(_coor);
		}
		setW(1.0f);
		_i = 0;
		for (CoorName _coor : CoorName.XYZ_TAB){
			setCoor(_coor, _tab_val[_i++]);
		}
	}

	/**************** PRODUCTS METHODS *********************/
	public final float dotProduct(final SpaceVector other_vec){
		float _res = 0.0f;
		for (CoorName _coor : CoorName.XYZ_TAB) {
			_res += getCoor(_coor)*other_vec.getCoor(_coor);
		}
		return _res;
	}

	public final SpaceVector crossProduct(final SpaceVector other_vec){
		return new SpaceVector(	  getY() * other_vec.getZ() - getZ()*other_vec.getY(),
				-(getX() * other_vec.getZ() - getZ() * other_vec.getX()),
				getX() * other_vec.getY() - getY() * other_vec.getX());
	}

	/**************** MIN MAX METHODS **********************/
	public final SpaceVector minVector(final SpaceVector other_vec){
		SpaceVector _res = new SpaceVector();
		for (CoorName _coor : CoorName.XYZ_TAB){
			_res.setCoor(_coor, Math.min(getCoor(_coor), other_vec.getCoor(_coor)));
		}
		return _res;
	}
	public final SpaceVector maxVector(final SpaceVector other_vec){
		SpaceVector _res = new SpaceVector();
		for (CoorName _coor : CoorName.XYZ_TAB){
			_res.setCoor(_coor, Math.max(getCoor(_coor), other_vec.getCoor(_coor)));
		}
		return _res;
	}

	/***************** OPERATOR METHODS ********************/
	
	/***************** AFFECTATION OPERATOR METHODS ********/
	public final SpaceVector opEquals(final SpaceVector other_vec){
		setW(1.0f);
		for (CoorName _coor : CoorName.XYZ_TAB) {
			setCoor(_coor, other_vec.getCoor(_coor));
		}
		return new SpaceVector(this);
	}

	/*public final SpaceVector opMinusEquals(final SpaceVector other_vec){

	}*/

	/***************** COMPARISON OPERATORS ****************/
	public final boolean isEqualTo(final SpaceVector other_vec){
		return
				Floatings.isEqual(getX(), other_vec.getX()) &&
				Floatings.isEqual(getY(), other_vec.getY()) &&
				Floatings.isEqual(getZ(), other_vec.getZ());
	}
	/**
	 * Tests for "absolute less than" on X and Y and Y
	 * @param other_vec
	 * @return
	 */
	public final boolean isLessThan(final SpaceVector other_vec){
		return 
				Floatings.isLess(getX(), other_vec.getX()) &&
				Floatings.isLess(getY(), other_vec.getY()) &&
				Floatings.isLess(getZ(), other_vec.getZ());
	}

	public final boolean isLessOrEqualTo(final SpaceVector other_vec){
		return
				Floatings.isLessEqual(getX(), other_vec.getX()) &&
				Floatings.isLessEqual(getY(), other_vec.getY()) &&
				Floatings.isLessEqual(getZ(), other_vec.getZ());
	}

	public final boolean isGreaterThan(final SpaceVector other_vec){
		return 
				Floatings.isGreater(getX(), other_vec.getX()) &&
				Floatings.isGreater(getY(), other_vec.getY()) &&
				Floatings.isGreater(getZ(), other_vec.getZ());
	}

	public final boolean isGreaterOrEqualTo(final SpaceVector other_vec){
		return 
				Floatings.isGreaterEqual(getX(), other_vec.getX()) &&
				Floatings.isGreaterEqual(getY(), other_vec.getY()) &&
				Floatings.isGreaterEqual(getZ(), other_vec.getZ());
	}
}
