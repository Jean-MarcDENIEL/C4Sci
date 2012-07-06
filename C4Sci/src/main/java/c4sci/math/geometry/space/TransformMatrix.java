package c4sci.math.geometry.space;

/**
 * 3D space transform matrix.
 * Transform data are represented in row-major order.
 * @author jeanmarc.deniel
 *
 */
public class TransformMatrix {

	final static int NB_COOR = 4;
	private float valueTab[][];
	
	/*********** CONSTRUCTORS ***************/
	private TransformMatrix(){
		valueTab = new float[NB_COOR][NB_COOR];
		for (int _i = 0; _i<NB_COOR; _i++){
			for (int _j=0; _j<NB_COOR; _j++){
				valueTab[_i][_j] = 0.0f;
			}
		}
	}
	
	/*********** FACTORY METHODS ************/
	public static final TransformMatrix createIdentityMatrix(){
		TransformMatrix _res = new TransformMatrix();
		for (int _i=0; _i<NB_COOR; _i++)
			_res.valueTab[_i][_i] = 1.0f;
		return _res;
	}
	
	/*********** OPERATORS ******************/
	/*public static final SpaceVector opMul(SpaceVector _vec){
		
	}*/
}
