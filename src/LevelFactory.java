import java.util.Random;


public class LevelFactory {
	
	public static Level getRandomLevel() {
		Level lvl = new Level();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 6; y++) {
				int rand = new Random().nextInt(6);
				Color col;
				switch(rand) {
					case 0:
						col = Color.red();
						break;
					case 1:
						col = Color.brightRed();
						break;
					case 2:
						col = Color.green();
						break;
					case 3:
						col = Color.brightGreen();
						break;
					case 4:
						col = Color.blue();
						break;
					default:
						col = Color.brightBlue();
						break;
				}
				lvl.addBox(new Box(x, y, col));
			}
		}
		return lvl;
	}
}
