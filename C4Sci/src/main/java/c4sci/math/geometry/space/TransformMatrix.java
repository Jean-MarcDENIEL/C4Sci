package c4sci.math.geometry.space;

/**
 * 3D space transform matrix.
 * Transform data are represented in row-major order.
 * @author jeanmarc.deniel
 *
 */
public class TransformMatrix {

	final static int NB_COOR = 4;
	/**
	 * 1st coor = row
	 * 2nd coor = column
	 */
	private float valueTab[][];
	
	public void setValue(int _row, int _col, float _val){
		valueTab[_row][_col] = _val;
	}
	public float getValue(int _row, int _col){
		return valueTab[_row][_col];
	}
	
	/*********** CONSTRUCTORS ***************/
	private TransformMatrix(){
		valueTab = new float[NB_COOR][NB_COOR];
		for (int _i = 0; _i<NB_COOR; _i++){
			for (int _j=0; _j<NB_COOR; _j++){
				setValue(_i, _j, 0.0f);
			}
		}
	}
	
	/*********** FACTORY METHODS ************/
	public static final TransformMatrix createIdentityMatrix(){
		TransformMatrix _res = new TransformMatrix();
		for (int _i=0; _i<NB_COOR; _i++)
			_res.setValue(_i, _i, 1.0f);
		return _res;
	}
	public static final TransformMatrix createScaleMatrix(float x_scale, float y_scale, float z_scale, float w_scale){
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
	
	/*********** OPERATORS ******************/
	public final SpaceVector opMul(SpaceVector _vec){
		SpaceVector _res = new SpaceVector();
		for (int _row = 0; _row<Commons.NB_COOR; _row++){
			float _tmp = 0.0f;
			for (int _col=0; _col<Commons.NB_COOR; _col++){
				_tmp += _vec.getCoor(_row)*getValue(_row, _col);
			}
			_res.setCoor(_row, _tmp);
		}
		return _res;
	}
	
	public final TransformMatrix opMul(TransformMatrix _mat){
		TransformMatrix _res = new TransformMatrix();
		for (int _row=0; _row<Commons.NB_COOR; _row++){
			for (int _col_row_2 = 0; _col_row_2<Commons.NB_COOR; _col_row_2++){
				float _tmp = 0.0f;
				for (int _col_2=0; _col_2<Commons.NB_COOR; _col_2++){
					_tmp += getValue(_row, _col_row_2) * _mat.getValue(_col_row_2, _col_2);
				}
				_res.setValue(_row, _col_row_2, _tmp);
			}
		}
		return _res;
	}
}
