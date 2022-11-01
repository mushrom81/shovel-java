import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;

class Orientation {
	private Room room;
	public int x, y, z, r;

	Orientation(Room room, int x, int y, int z, int r) {
		this.room = room;
		this.x = x;
		this.y = y;
		this.z = z;
		this.r = r;
	}

	public int get() {
		try {
			return this.room.get(this.x, this.y, this.z);
		} catch (Exception e) {
			return -1;
		}
	}

	public void set(int value) {
		this.room.set(this.x, this.y, this.z, value);
	}

	public boolean equals(Orientation compare) {
		return (this.room == compare.room) && (this.x == compare.x) && (this.y == compare.y) && (this.z == compare.z) && (this.r == compare.r);
	}

	public Orientation copy() {
		return new Orientation(this.room, this.x, this.y, this.z, this.r);
	}

	public Orientation rotate(int rot) {
		Orientation rotated = this.copy();
		switch (rot) {
			case Rotation.X_PLUS:
				break;
			case Rotation.Y_PLUS:
				rotated.x ^= rotated.y; rotated.y ^= rotated.x; rotated.x ^= rotated.y;
				rotated.x = -rotated.x;
				break;
			case Rotation.X_MINUS:
				rotated.x = -rotated.x;
				rotated.y = -rotated.y;
				break;
			case Rotation.Y_MINUS:
				rotated.x ^= rotated.y; rotated.y ^= rotated.x; rotated.x ^= rotated.y;
				rotated.y = -rotated.y;
				break;
			case Rotation.Z_PLUS:
				rotated.x ^= rotated.z; rotated.z ^= rotated.x; rotated.x ^= rotated.z;
				rotated.x = -rotated.x;
				break;
			case Rotation.Z_MINUS:
				rotated.x ^= rotated.z; rotated.z ^= rotated.x; rotated.x ^= rotated.z;
				rotated.z = -rotated.z;
				break;
		}
		rotated.r = Rotation.rotate(rotated.r, rot);
		return rotated;
	}

	private Orientation add(Orientation relative) {
		Orientation added = this.copy();
		relative = relative.rotate(this.r);
		added.x += relative.x;
		added.y += relative.y;
		added.z += relative.z;
		added.r = relative.r;
		return added;
	}

	public Orientation step(int... dirs) {
		if (dirs.length == 0) {
			return this.copy();
		} else if (dirs.length == 1) {
			return this.unitStep(dirs[0]);
		} else {
			Orientation stepped = new Orientation(null, 0, 0, 0, Rotation.X_PLUS);
			for (int i = 0; i < dirs.length; i++) {
				int[] cutDirs = new int[dirs.length - 1];
				int index = 0;
				for(int j = 0; j < dirs.length; j++) {
					if (j != i) {
						cutDirs[index++] = dirs[j];
					}
				}
				Orientation testPos = this.unitStep(dirs[i]);
				testPos = testPos.step(cutDirs);
				if (testPos.get() != -1) {
					if (stepped.get() != -1 && !stepped.equals(testPos)) {
						return new Orientation(null, 0, 0, 0, Rotation.X_PLUS);
					} else {
						stepped = testPos;
					}
				}
			}
			return stepped;
		}
	}

	public Orientation unitStep(int dir) {	
		Orientation step = new Orientation(null, 1, 0, 0, Rotation.X_PLUS).rotate(dir);
		step.r = Rotation.X_PLUS;
		Orientation stepped = this.add(step);
		if (stepped.get() == -1) {
			for (int i = 0; i < this.room.doors.length; i++) {
				Orientation testPos = this.room.doors[i].add(stepped);
				if (testPos.get() != -1) {
					if (stepped.get() != -1 && !stepped.equals(testPos)) {
						return new Orientation(null, 0, 0, 0, Rotation.X_PLUS);
					} else {
						stepped = testPos;
					}
				}
			}
		}
		return stepped;
	}
}
