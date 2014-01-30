import org.jbox2d.common.Vec2;


public class LevelFactory {
	
	public static Level randomLevel(Level lvl) {
		lvl.setDoge(new EntityDoge(new Vec2(3.5f, 1.5f)));
		lvl.setDepth(10f);
		
		for (float i = 0.5f; i < 7; i++) {
			lvl.addEntity(new EntityBox(new Vec2(i, 9.5f)));
			lvl.addEntity(new EntityBox(new Vec2(i, 8.5f)));
			lvl.addEntity(new EntityBox(new Vec2(i, 7.5f)));
			lvl.addEntity(new EntityBox(new Vec2(i, 6.5f)));
		}
		
		lvl.addEntity(new EntityBox(new Vec2(0.5f, 5.5f)));
		
		lvl.addEntity(new EntityBox(new Vec2(1.5f, 5.5f)));
		lvl.addEntity(new EntityBox(new Vec2(1.5f, 4.5f)));
		
		lvl.addEntity(new EntityBox(new Vec2(4.5f, 5.5f)));
		
		lvl.addEntity(new EntityBox(new Vec2(5.5f, 5.5f)));
		lvl.addEntity(new EntityBox(new Vec2(5.5f, 4.5f)));
		
		lvl.addEntity(new EntityBox(new Vec2(6.5f, 5.5f)));
		
		return lvl;
	}
}
