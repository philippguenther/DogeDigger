import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Doge {
	private Level level;
	
	private Vec2f position;
	private ArrayList<Graphic> graphics = new ArrayList<Graphic>();
	
	private int deltaMove = Config.delayMove;
	private int deltaDig = Config.delayDig;
	private int moveDirection = 0;
	
	public Doge (Level _level, Vec2f _position) {
		this.level = _level;
		this.position = _position;
		this.graphics.add(GraphicFactory.newDogeGraphic());
	}
	
	public void tick (int delta) {
		
		//FALLING
		Entity bot = this.level.get(new Vec2f(this.position.x, this.position.y + 0.5f));
		if (bot == null) {
			this.position.y += this.level.getGravity() * 0.005f * delta;
		} else {
			this.position.y = bot.getPosition().y -1f;
		}
		
		//MOVING
		deltaMove += delta;
		if (this.deltaMove > Config.delayMove) {
			if (Keyboard.isKeyDown(Config.keyUp)) {
				// top
				this.moveDirection = 0;
				
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				// right
				this.moveDirection = 1;
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
				
			} else if (Keyboard.isKeyDown(Config.keyDown)) {
				// down
				this.moveDirection = 2;
				
			} else if (Keyboard.isKeyDown(Config.keyLeft)) {
				// left
				this.moveDirection = 3;
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
			}
		}
		
		// DIGGING
		deltaDig += delta;
		if (this.deltaDig > Config.delayDig && Keyboard.isKeyDown(Config.keyDig)) {
			if (this.moveDirection == 0) {
				// up
				Entity top = this.level.get(new Vec2f(this.position.x, this.position.y - 1));
				if (top != null) {
					top.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
				}
			} else if (this.moveDirection == 1) {
				// right
				Entity right = this.level.get(new Vec2f(this.position.x + 1, this.position.y));
				if (right != null) {
					right.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
				}
				
			} else if (this.moveDirection == 2) {
				// down
				Entity bottom = this.level.get(new Vec2f(this.position.x, this.position.y + 1));
				if (bottom != null) {
					bottom.destroy();
					this.position.y += 1;
					this.deltaDig = 0;
				}
				
			} else if (this.moveDirection == 3) {
				// left
				Entity left = this.level.get(new Vec2f(this.position.x - 1, this.position.y));
				if (left != null) {
					left.destroy();
					this.deltaDig = 0;
					this.deltaMove = 0;
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
