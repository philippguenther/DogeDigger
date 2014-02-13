import java.util.Random;


public class LevelFactory {
	
	public static void randomCheese (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.levelMaxX; x++) {
			for (int y = 0; y < Config.levelMaxY; y++) {
				if (rand.nextBoolean()) {
					EntityBox box = new EntityBox(lvl, new Vec2f(x, y), rand.nextInt());
					lvl.put(box);
				}
			}
		}
	}
	
	public static void randomFull (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.levelMaxX; x++) {
			for (int y = 2; y < Config.levelMaxY; y++) {
				lvl.put(new EntityBox(lvl, new Vec2f(x, y), rand.nextInt()));
			}
		}
	}
}
