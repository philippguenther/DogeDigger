import java.util.Random;


public class LevelFactory {
	
	public static void randomCheese (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2i(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.gameBoxesX; x++) {
			for (int y = 0; y < Config.levelMaxY; y++) {
				if (rand.nextBoolean()) {
					Entity ent;
					
					switch (rand.nextInt(5)) {
					case 0:
						ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.RED);
						break;
					case 1:
						ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.GREEN);
						break;
					case 2:
						ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.BLUE);
						break;
					case 3:
						ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.YELLOW);
						break;
					default:
						ent = new EntityStatic(lvl, new Vec2i(x, y));
					}
					
					lvl.put(ent);
				}
			}
		}
	}
	
	public static void randomFull (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2i(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.gameBoxesX; x++) {
			for (int y = 2; y < Config.levelMaxY; y++) {
				Entity ent;
				
				switch (rand.nextInt(4)) {
				case 0:
					ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.RED);
					break;
				case 1:
					ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.GREEN);
					break;
				case 2:
					ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.BLUE);
					break;
				case 3:
					ent = new EntityBox(lvl, new Vec2i(x, y), EntityBoxType.YELLOW);
					break;
				default:
					ent = new EntityStatic(lvl, new Vec2i(x, y));
				}
				
				lvl.put(ent);
			}
		}
	}
}
