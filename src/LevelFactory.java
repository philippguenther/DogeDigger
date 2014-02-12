import java.util.Random;


public class LevelFactory {
	
	public static void random (Level lvl, long seed) {
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
	
	public static void full (Level lvl) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		for (int x = 0; x < Config.levelMaxX; x++) {
			for (int y = 2; y < Config.levelMaxY; y++) {
				lvl.put(new EntityBox(lvl, new Vec2f(x, y), 0));
			}
		}
	}
	
	public static void test (Level lvl) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		for (int i = 0; i < Config.gameBoxesX; i++) {
			lvl.put(new EntityStatic(new Vec2f(i, 9)));
			lvl.put(new EntityBox(lvl, new Vec2f(i, 8), 0));
			lvl.put(new EntityBox(lvl, new Vec2f(i, 7), 0));
			lvl.put(new EntityBox(lvl, new Vec2f(i, 6), 0));
		}

		lvl.put(new EntityBox(lvl, new Vec2f(0, 5), 0));

		lvl.put(new EntityBox(lvl, new Vec2f(1, 5), 0));
		lvl.put(new EntityBox(lvl, new Vec2f(1, 4), 0));

		lvl.put(new EntityBox(lvl, new Vec2f(4, 5), 0));

		lvl.put(new EntityBox(lvl, new Vec2f(5, 5), 0));
		lvl.put(new EntityBox(lvl, new Vec2f(5, 4), 0));

		lvl.put(new EntityBox(lvl, new Vec2f(6, 5), 0));
	}
}
