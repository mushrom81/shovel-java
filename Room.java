class Room {
	private int[][][] terrain;
	public Orientation[] doors;

	Room(int dimX, int dimY, int dimZ, Orientation[] doors) {
		this.terrain = new int[dimX][dimY][dimZ];
		this.doors = doors;
	}

	public int get(int x, int y, int z) {
		try {
			// return 0;
			return this.terrain[x][y][z];
		} catch (Exception e) {
			return -1;
		}
	}

	public void set(int x, int y, int z, int value) {
		this.terrain[x][y][z] = value;
	}
}
