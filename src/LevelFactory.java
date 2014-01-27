import org.jbox2d.common.Color3f;
import org.jbox2d.common.Vec2;


public class LevelFactory {
	
	public static Level getRandomLevel() {
		Level lvl = new Level( new Doge(new Vec2(3f, 1f), new GraphicQuad(new Color3f(1f, 0f, 0f))));
		return lvl;
	}
}
