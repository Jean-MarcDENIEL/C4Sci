package c4sci.math.geometry.space;

/**
 * 4D space vector (X,Y,Z,W) adapted to matrix transforms.
 * W is a scale factor and should never be 0.0.
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

	public enum CoorName {
		X(0), Y(1), Z(2), W(3);
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
	public	final void setX(float x_val){
		setCoor(CoorName.X, x_val);
	}
	public final void setY(float y_val){
		setCoor(CoorName.Y, y_val);
	}
	public final void setZ(float z_val){
		setCoor(CoorName.Z, z_val);
	}
	public final void setW(float w_val){
		setCoor(CoorName.W, w_val);
	}

	public final void setCoor(CoorName n_coor, float val_coor){
		setCoor(n_coor.getCoorValue(), val_coor);
	}
	/**
	 * 
	 * @param n_coor	0=X, 1=Y, 2=Z, 3=W
	 */
	public final void setCoor(int n_coor, float val_coor){
		if (n_coor == CoorName.W.getCoorValue()){
			coorTab[n_coor] = val_coor;
		}
		else{
			coorTab[n_coor] = val_coor/getW();
		}
	}
	
	public final float getX(){
		return getCoor(CoorName.X);
	}
	public final float getY(){
		return getCoor(CoorName.Y);
	}
	public final float getZ(){
		return getCoor(CoorName.Z);
	}
	public final float getW(){
		return getCoor(CoorName.W);
	}
	public final float getCoor(CoorName n_coor){
		return getCoor(n_coor.getCoorValue());
	}
	/**
	 * 
	 * @param n_coor	0=X, 1=Y, 2=Z, 3=W
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
	
	/**************** PRODUCTS METHODS *********************/
	public final float dotProduct(final SpaceVector other_vec){
		return getX()*other_vec.getX() + getY()*other_vec.getY() + getZ()*other_vec.getZ();
	}
	
	public final SpaceVector crossProduct(final SpaceVector other_vec){
		return new SpaceVector(	  getY() * other_vec.getZ() - getZ()*other_vec.getY(),
								-(getX() * other_vec.getZ() - getZ() * other_vec.getX()),
								  getX() * other_vec.getY() - getY() * other_vec.getX());
	}
	
	/**************** MIN MAX METHODS **********************/
	
	
}
