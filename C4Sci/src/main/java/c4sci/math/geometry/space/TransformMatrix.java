package c4sci.math.geometry.space;

/**
 * 3D space transform matrix.
 * Transform data are represented in row-major order.
 * 
 * </br>
 * <b>Warning : </b>all transform applied vectors should have their W scaling factor set to 1.0 in order to avoid scaling unwanted effects.
 * @author jeanmarc.deniel
 *
 */
public final class TransformMatrix {

	/**
	 * 1st coor = row
	 * 2nd coor = column
	 */
	private float valueTab[][];
	
	public void setValue(int row_val, int col_val, float new_val){
		valueTab[row_val][col_val] = new_val;
	}
	public float getValue(int row_val, int col_val){
		return valueTab[row_val][col_val];
	}
	
	/*********** CONSTRUCTORS ***************/
	private TransformMatrix(){
		valueTab = new float[Commons.NB_COOR][Commons.NB_COOR];
		for (int _i = 0; _i<Commons.NB_COOR; _i++){
			for (int _j=0; _j<Commons.NB_COOR; _j++){
				setValue(_i, _j, 0.0f);
			}
		}
	}
	
	/*********** FACTORY METHODS ************/
	/**
	 * Creates the 1.0 diag matrix.
	 */
	public static TransformMatrix createIdentityMatrix(){
		TransformMatrix _res = new TransformMatrix();
		for (int _i=0; _i<Commons.NB_COOR; _i++){
			_res.setValue(_i, _i, 1.0f);
		}
		return _res;
	}
	public static TransformMatrix createScaleMatrix(float x_scale, float y_scale, float z_scale, float w_scale){
		TransformMatrix _res = new TransformMatrix();
		int _x = Commons.CoorName.X.getCoorValue();
		int _y = Commons.CoorName.Y.getCoorValue();
		int _z = Commons.CoorName.Z.getCoorValue();
		int _w = Commons.CoorName.W.getCoorValue();
		_res.setValue(_x, _x, x_scale);
		_res.setValue(_y, _y, y_scale);
		_res.setValue(_z, _z, z_scale);
		_res.setValue(_w, _w, w_scale);
		return _res;
	}
	/**
	 * Creates a transform matrix that rotates around a given vector.<br>
	 * The rotation is clockwise around the vector. <br>For example : an PI/2 rotation around [Ox) <br>
	 * <ul>
	 * <li> [Oy)gives [Oz) <br>
	 * <li> [Oz)gives -[Ox) <br>
	 * </ul>
	 * @param rot_angle_rad the clockwise rotation angle, in radian.
	 * @param rot_axe the rotary axe.
	 * @return a transform matrix that rotates around a given vector.
	 */
	public static TransformMatrix createRotationMatrix(float rot_angle_rad, final SpaceVector rot_axe){
		TransformMatrix _res = new TransformMatrix();
		int _x = Commons.CoorName.X.getCoorValue();
		int _y = Commons.CoorName.Y.getCoorValue();
		int _z = Commons.CoorName.Z.getCoorValue();
		int _w = Commons.CoorName.W.getCoorValue();
		
		float _cos_t = (float)Math.cos(rot_angle_rad);
		float _sin_t = (float)Math.sin(rot_angle_rad);
		float _ux2 = (float) Math.pow(rot_axe.getX(), 2.0);
		float _uy2 = (float)Math.pow(rot_axe.getY(), 2.0);
		float _uz2 = (float)Math.pow(rot_axe.getZ(), 2.0);
		float _ux_uy = rot_axe.getX() * rot_axe.getY();
		float _ux_uz = rot_axe.getX() * rot_axe.getZ();
		float _uy_uz = rot_axe.getY() * rot_axe.getZ();
		
		_res.setValue(_x,_x, _ux2 + (1.0f-_ux2)*_cos_t);
		_res.setValue(_x,_y, _ux_uy*(1.0f-_cos_t) - rot_axe.getZ()*_sin_t);
		_res.setValue(_x,_z, _ux_uz*(1.0f-_cos_t) + rot_axe.getY()*_sin_t);
		_res.setValue(_x,_w, 0.0f);

		_res.setValue(_y,_x, _ux_uy*(1.0f-_cos_t) + rot_axe.getZ()*_sin_t);
		_res.setValue(_y,_y, _uy2 + (1.0f-_uy2)*_cos_t);
		_res.setValue(_y,_z, _uy_uz*(1.0f-_cos_t) - rot_axe.getX()*_sin_t);
		_res.setValue(_y,_w, 0.0f);

		_res.setValue(_z,_x, _ux_uz*(1.0f-_cos_t) - rot_axe.getY()*_sin_t);
		_res.setValue(_z,_y, _uy_uz*(1.0f-_cos_t) + rot_axe.getX()*_sin_t);
		_res.setValue(_z,_z, _uz2 + (1.0f-_uz2)*_cos_t);
		_res.setValue(_z,_w, 0.0f);

		_res.setValue(_w,_x, 0.0f);
		_res.setValue(_w,_y, 0.0f);
		_res.setValue(_w,_z, 0.0f);
		_res.setValue(_w,_w, 1.0f);
		return _res;
	}
	/**
	 * Creates a translation matrix.
	 * The [W,W] scale is set to the arg W scale value.<br>
	 * <b>warning </b>the vector scaling value is applied twice : once for translation values and once as an overall scaling value.
	 * @see c4sci.math.geometry.space.SpaceVector#getW() getW() vectors' scaling factor
	 * @param trans_vec the translation and scaling value.
	 * @return a translation matrix.
	 */
	public static TransformMatrix createTranslationAndScalingMatrix(final SpaceVector trans_vec){
		TransformMatrix _res = createIdentityMatrix();
		for (int _i=0; _i<Commons.NB_COOR; _i++){
			_res.setValue(_i, Commons.CoorName.W.getCoorValue(), trans_vec.getCoor(_i));
		}
		return _res;
	}
	
	/*********** OPERATORS ******************/
	/**
	 * Transforms a vector by a matrix.<br>
	 * The 4th matrix column usually represents the translation vector. <br>
	 * <b>Warning: </b>if the vector W value is not set to 1.0 all translation will be applied this scaling factor.
	 * @param other_vec
	 * @return the matrix that is the vector multiplied by "this" matrix
	 */
	public SpaceVector opMul(SpaceVector other_vec){
		SpaceVector _res = new SpaceVector();
		for (int _row = 0; _row<Commons.NB_COOR; _row++){
			float _tmp = 0.0f;
			for (int _col=0; _col<Commons.NB_COOR; _col++){
				_tmp += other_vec.getCoor(_col)*getValue(_row, _col);
			}
			_res.setCoor(_row, _tmp);
		}
		return _res;
	}
	
	public TransformMatrix opMul(TransformMatrix other_mat){
		TransformMatrix _res = new TransformMatrix();
		for (int _row=0; _row<Commons.NB_COOR; _row++){
			for (int _col_row_2 = 0; _col_row_2<Commons.NB_COOR; _col_row_2++){
				float _tmp = 0.0f;
				for (int _col_2=0; _col_2<Commons.NB_COOR; _col_2++){
					_tmp += getValue(_row, _col_row_2) * other_mat.getValue(_col_row_2, _col_2);
				}
				_res.setValue(_row, _col_row_2, _tmp);
			}
		}
		return _res;
	}
}
