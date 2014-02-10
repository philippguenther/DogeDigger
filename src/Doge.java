import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Doge {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	private int deltaMove = Config.delayMove;
	private int deltaDig = Config.delayDig;
	
	public Doge (Level _level, Vec2f _position) {
		this.level = _level;
		this.position = _position;
		Vec2f[] v = new Vec2f[4];
		v[0] = new Vec2f(0f, 0f);
		v[1] = new Vec2f(1f, 0f);
		v[2] = new Vec2f(1f, 1f);
		v[3] = new Vec2f(0f, 1f);
		this.graphics.add(new GraphicPolygon(v, new Color4f(1f, 0f, 0f)));
	}
	
	public void tick (int delta) {
		Box bottom = this.level.get((int)this.position.x + "|" + ((int)this.position.y + 1));
		if (bottom == null) {
			this.position.x += this.level.getGravity().x * 0.1;
			this.position.y += this.level.getGravity().y * 0.1;
		}
		
		deltaMove += delta;
		if (this.deltaMove > Config.delayMove) {
			if (
				Keyboard.isKeyDown(Config.keyLeft) && 
				this.level.get(((int)this.position.x - 1) + "|" + (int)this.position.y) == null
			) {
				this.position.x -= 1;
				this.deltaMove = 0;
			} else if (
					Keyboard.isKeyDown(Config.keyRight) && 
					this.level.get(((int)this.position.x + 1) + "|" + (int)this.position.y) == null
			) {
				this.position.x += 1;
				this.deltaMove = 0;
			}
		}
		deltaDig += delta;
		if (this.deltaDig > Config.delayDig && Keyboard.isKeyDown(Config.keyDig)) {
			Box bot = this.level.get((int)this.position.x + "|" + ((int)this.position.y + 1));
			if (bot != null) {
				System.out.println("dig");
				bot.destroy();
				this.position.y += 1;
				this.deltaDig = 0;
			}
		}
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
		GL11.glTranslatef(this.position.x, this.position.y, 0f);
		for (Graphic g : this.graphics) {
			g.render(delta);
		}
		GL11.glPopMatrix();
	}
}
