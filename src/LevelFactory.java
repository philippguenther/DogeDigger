import java.util.Random;


public class LevelFactory {
	
	public static Level getRandomLevel() {
		Level lvl = new Level();
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 6; y++) {
				Random rand = new Random();
				Color col = new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
				lvl.addBox(new Box(x, y, col));
			}
		}
		return lvl;
	}
}
