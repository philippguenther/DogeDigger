import java.util.Random;


public class LevelFactory {
	
	public static void random (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.levelMaxX; x++) {
			for (int y = 0; y < Config.levelMaxY; y++) {
				if (rand.nextBoolean()) {
					lvl.put(new EntityBox(lvl, new Vec2f(x, y)));
				}
			}
		}
	}
	
	public static void full (Level lvl) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		for (int x = 0; x < Config.levelMaxX; x++) {
			for (int y = 2; y < Config.levelMaxY; y++) {
				lvl.put(new EntityBox(lvl, new Vec2f(x, y)));
			}
		}
	}
	
	public static void test (Level lvl) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		for (int i = 0; i < Config.gameBoxesX; i++) {
			lvl.put(new EntityStatic(new Vec2f(i, 9)));
			lvl.put(new EntityBox(lvl, new Vec2f(i, 8)));
			lvl.put(new EntityBox(lvl, new Vec2f(i, 7)));
			lvl.put(new EntityBox(lvl, new Vec2f(i, 6)));
		}

		lvl.put(new EntityBox(lvl, new Vec2f(0, 5)));

		lvl.put(new EntityBox(lvl, new Vec2f(1, 5)));
		lvl.put(new EntityBox(lvl, new Vec2f(1, 4)));

		lvl.put(new EntityBox(lvl, new Vec2f(4, 5)));

		lvl.put(new EntityBox(lvl, new Vec2f(5, 5)));
		lvl.put(new EntityBox(lvl, new Vec2f(5, 4)));

		lvl.put(new EntityBox(lvl, new Vec2f(6, 5)));
	}
}
