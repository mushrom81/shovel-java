class Rotation {
	public static final int X_PLUS = 0;
	public static final int Y_PLUS = 1;
	public static final int X_MINUS = 2;
	public static final int Y_MINUS = 3;
	public static final int Z_PLUS = 4;
	public static final int Z_MINUS = 5;

	public static int rotate(int r1, int r2) {
		if (r1 < 4 && r2 < 4) {
			r1 += r2;
			return Rotation.fix(r1);
		} else if (r1 >= 4 && r2 >= 4) {
			if (r1 == r2) {
				r1 = X_MINUS;
			} else {
				r1 = X_PLUS;
			}
			return r1;
		} else {
			if (r1 >= 4) {
				return r1;
			} else {
				return r2;
			}
		}
	}

	public static int fix(int r) {
		r = r % 4;
		if (r < 0) r += 4;
		return r;
	}
}
