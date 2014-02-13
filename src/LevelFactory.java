import java.util.Random;


public class LevelFactory {
	
	public static void randomCheese (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.gameBoxesX; x++) {
			for (int y = 0; y < Config.levelMaxY; y++) {
				if (rand.nextBoolean()) {
					EntityBoxType type;
					
					switch (rand.nextInt() % 4) {
					case 0:
						type = EntityBoxType.RED;
						break;
					case 1:
						type = EntityBoxType.GREEN;
						break;
					case 2:
						type = EntityBoxType.BLUE;
						break;
					default:
						type = EntityBoxType.YELLOW;
					}
					
					EntityBox box = new EntityBox(lvl, new Vec2f(x, y), type);
					lvl.put(box);
				}
			}
		}
	}
	
	public static void randomFull (Level lvl, long seed) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 0));
		
		Random rand = new Random(seed);
		for (int x = 0; x < Config.gameBoxesX; x++) {
			for (int y = 2; y < Config.levelMaxY; y++) {
				EntityBoxType type;
				
				switch (rand.nextInt() % 4) {
				case 0:
					type = EntityBoxType.RED;
					break;
				case 1:
					type = EntityBoxType.GREEN;
					break;
				case 2:
					type = EntityBoxType.BLUE;
					break;
				default:
					type = EntityBoxType.YELLOW;
				}
				
				EntityBox box = new EntityBox(lvl, new Vec2f(x, y), type);
				lvl.put(box);
			}
		}
	}
}
