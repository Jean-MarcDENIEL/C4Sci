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
	
	public SpaceVector(){
		coorTab= new float[NB_COOR];
		setX(0.0f);
		setY(0.0f);
		setZ(0.0f);
		setW(1.0f);
	}
	public SpaceVector(float val_x, float val_y, float val_z){
		coorTab = new float[NB_COOR];
		setW(1.0f);
		setX(val_x);
		setY(val_y);
		setZ(val_z);
	}
	public SpaceVector(float val_x, float val_y, float val_z, float val_w){
		coorTab = new float[4];
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

	public	final void setX(float x_val){
		coorTab[CoorName.X.getCoorValue()] = x_val/getW();
	}
	public final void setY(float y_val){
		coorTab[CoorName.Y.getCoorValue()] = y_val/getW();
	}
	public final void setZ(float z_val){
		coorTab[CoorName.Z.getCoorValue()] = z_val/getW();
	}
	public final void setW(float w_val){
		coorTab[CoorName.W.getCoorValue()] = w_val;
	}

	public final void setCoor(CoorName n_coor, float val_coor){
		if (n_coor == CoorName.W){
			setW(val_coor);
		}
		else{
			coorTab[n_coor.getCoorValue()] = val_coor/getW();
		}
	}
	
	public final float getX(){
		return coorTab[CoorName.X.getCoorValue()] * getW();
	}
	public final float getY(){
		return coorTab[CoorName.Y.getCoorValue()] * getW();
	}
	public final float getZ(){
		return coorTab[CoorName.Z.getCoorValue()] * getW();
	}
	public final float getW(){
		return coorTab[CoorName.W.getCoorValue()];
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
}
