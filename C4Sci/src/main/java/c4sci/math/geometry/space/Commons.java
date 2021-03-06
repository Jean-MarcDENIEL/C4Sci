package c4sci.math.geometry.space;

public final class Commons {
	public static final int	NB_COOR = 4;
	
	public enum CoorName {
		X(0), Y(1), Z(2), W(3);
		static final CoorName XYZ_TAB[] = {X, Y, Z};

		CoorName (int val_coor){
			coorIndex = val_coor;
		}
		public int getCoorValue(){
			return coorIndex;
		}
		private int coorIndex;
	};
	
	private Commons(){}
}
