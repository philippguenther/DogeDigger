
public class LevelFactory {
	
	public static void random (Level lvl) {
		lvl.doge = new Doge(lvl, new Vec2f(3, 2));
		
		for (int i = 0; i < 7; i++) {
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
