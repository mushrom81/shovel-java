import java.io.Console;

class Game {
	public static char getInput(Console console) {
		try {
			String input = console.readLine("\n? ");
			if (input.length() != 1) {
				return Game.getInput(console);
			} else {
				return input.charAt(0);
			}
		} catch (Exception e) {
			return Game.getInput(console);
		}
	}

	public static void main(String[] args) {
		Room room1 = new Room(3, 3, 3, null);
		Room room2 = new Room(4, 4, 4, null);
		Orientation[] doors1 = {new Orientation(room2, 0, -3, -1, Rotation.X_PLUS)};
		Orientation[] doors2 = {new Orientation(room1, 	0, 3, 1, Rotation.X_PLUS)};
		room1.doors = doors1;
		room2.doors = doors2;

		Orientation player = new Orientation(room1, 0, 0, 0, Rotation.X_PLUS);
		
		
		Console console = System.console();
		char input = '4';
		while (input != 'q') {
			int[] screenInts = {
				player.step(Rotation.X_PLUS, Rotation.Y_PLUS, Rotation.Z_PLUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Z_PLUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Y_MINUS, Rotation.Z_PLUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Y_PLUS).get(),
				player.step(Rotation.X_PLUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Y_MINUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Y_PLUS, Rotation.Z_MINUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Z_MINUS).get(),
				player.step(Rotation.X_PLUS, Rotation.Y_MINUS, Rotation.Z_MINUS).get()
			};

			char[] screen = new char[9];
			
			for (int i = 0; i < 9; i++) {
				char display = ' ';
				switch (screenInts[i]) {
					case -1: display = '#'; break;
					case 0: display = ' '; break;
					case 1: display = 'I'; break;
				}
				screen[i] = display;
			}

			System.out.print("\033[H\033[2J");
			System.out.printf("(%d, %d, %d, %d)\n", player.x, player.y, player.z, player.r);
			System.out.printf("%c%c%c\n%c%c%c\n%c%c%c", screen[0], screen[1], screen[2], screen[3], screen[4], screen[5], screen[6], screen[7], screen[8]);
			
			input = Game.getInput(console);

			Orientation newPlayer = player;
			switch (input) {
				case 'w': newPlayer = player.step(Rotation.X_PLUS); break;
				case 's': newPlayer = player.step(Rotation.X_MINUS); break;
				case 'e': newPlayer = player.step(Rotation.Z_PLUS); break;
				case 'f': newPlayer = player.step(Rotation.Z_MINUS); break;
				case 'a': newPlayer.r = Rotation.rotate(player.r, Rotation.Y_PLUS); break;
				case 'd': newPlayer.r = Rotation.rotate(player.r, Rotation.Y_MINUS); break;
			}
			if (!newPlayer.equals(player) && newPlayer.get() != -1) {
				player = newPlayer;
			}
		}
	}
}


