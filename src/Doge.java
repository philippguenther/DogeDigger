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
		this.graphics.add(GraphicFactory.newDogeGraphic());
	}
	
	public void tick (int delta) {
		
		//FALLING
		Entity bot = this.level.get(new Vec2f(this.position.x, this.position.y + 1f));
		if (bot == null) {
			float dy = this.level.getGravity() * 0.005f * delta;
			this.position.y += dy;
			return;
		} else {
			this.position.y = bot.getPosition().y - 1f;
		}
		
		//MOVING
		deltaMove += delta;
		if (this.deltaMove > Config.delayMove) {
			if (Keyboard.isKeyDown(Config.keyLeft)) {
				// left
				Entity left = this.level.get(new Vec2f(this.position.x - 1, this.position.y));
				if (left == null) {
					this.position.x -= 1;
					this.deltaMove = 0;
					return;
				} else {
					Entity up = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity leftup = this.level.get(new Vec2f(this.position.x - 1, this.position.y - 1));
						if (leftup == null) {
							this.position.x -= 1f;
							this.position.y -= 1f;
							this.deltaMove = 0;
						}
					}
				}
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				// right
				Entity right = this.level.get(new Vec2f(this.position.x + 1, Math.round(this.position.y)));
				if (right == null) {
					this.position.x += 1f;
					this.deltaMove = 0;
					return;
				} else {
					Entity up = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity rightup = this.level.get(new Vec2f(this.position.x + 1, this.position.y - 1));
						if (rightup == null) {
							this.position.x += 1f;
							this.position.y -= 1f;
							this.deltaMove = 0;
						}
					}
				}
			}
		}
		
		// DIGGING
		deltaDig += delta;
		if (this.deltaDig > Config.delayDig && Keyboard.isKeyDown(Config.keyDig)) {
			if (Keyboard.isKeyDown(Config.keyLeft)) {
				// left
				Entity left = this.level.get(new Vec2f(this.position.x - 1, this.position.y));
				if (left != null) {
					left.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
				}
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				// right
				Entity right = this.level.get(new Vec2f(this.position.x + 1, this.position.y));
				if (right != null) {
					right.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
				}
			} else {
				// else bottom
				Entity bottom = this.level.get(new Vec2f(this.position.x, this.position.y + 1));
				if (bottom != null) {
					bottom.destroy();
					this.position.y += 1;
					this.deltaDig = 0;
				}
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
