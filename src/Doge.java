import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class Doge {
	private Level level;
	
	private Vec2i position;
	private Vec2f offset = Vec2f.nil();
	private Graphic[] graphics = new Graphic[4];
	private Mover mover;
	
	private DogeState state = DogeState.SITTING;
	private boolean stateChanged = false;
	private boolean intense = false;
	
	private int deltaDig = Config.dogeDelayDig;
	private int moveDirection = 2;
	
	private Graphic graphicLeft;
	private Graphic graphicRight;
	private Graphic graphicSitting;
	private Graphic graphicDiggingDown;
	private Graphic graphicDiggingLeft;
	private Graphic graphicDiggingRigth;
	private Graphic graphicDead;
	
	
	public Doge (Level _level, Vec2i _position) {
		this.level = _level;
		this.position = _position;
		
		this.graphicLeft = GraphicFactory.newDogeWalking();
		this.graphicRight = GraphicFactory.newDogeWalking();
		this.graphicRight.flip();
		this.graphicSitting = GraphicFactory.newDogeSitting();
		this.graphicDiggingDown = GraphicFactory.newDogeDiggingDown();
		this.graphicDiggingLeft = GraphicFactory.newDogeDiggingSide();
		this.graphicDiggingRigth = GraphicFactory.newDogeDiggingSide();
		this.graphicDiggingRigth.flip();

		this.graphicDead = GraphicFactory.newDogeDead();
				
		this.graphics[0] = this.graphicDiggingDown;
	}
	
	public Vec2i getPosition() {
		return this.position;
	}
	
	public Vec2f getOffset() {
		return this.offset;
	}
	
	public void die() {
		this.state = DogeState.DEAD;
		System.out.println("DEATH!!!");
	}
	
	public void tick (int delta) {
		
		// graphics
		boolean disposable = true;
		if (this.graphics[0] instanceof GraphicAnimation) {
			disposable = !((GraphicAnimation) this.graphics[0]).disposable();
		}
		
		if (this.stateChanged && disposable) {
			switch (this.state) {
			case RIGHT:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicRight;
				break;
			case LEFT:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicLeft;
				break;
			case DEAD:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicDead;
				break;
			case DIGGINGDOWN:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicDiggingDown;
				break;
			case DIGGINGLEFT:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicDiggingLeft;
				break;
			case DIGGINGRIGTH:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicDiggingRigth;
				break;	
			default:
				this.graphicRight.reset();
				this.graphics[0] = this.graphicSitting;
			}
			this.stateChanged = false;
		}
		
		// mover
		if (this.mover != null) {
			if (this.mover.disposable())
				this.mover = null;
			else
				this.offset.add(this.mover.getVecDelta(delta));				
		} else {
			
			if (this.offset.x > 0.99) {
				this.position.x += 1;
				this.offset.x -= 1;
			} else if (this.offset.x < -0.99) {
				this.position.x -= 1;
				this.offset.x += 1;
			}
			if (this.offset.y > 0.99) {
				this.position.y += 1;
				this.offset.y -= 1;
			} else if (this.offset.y < -0.99) {
				this.position.y -= 1;
				this.offset.y += 1;
			}
		
			// die
			if (this.level.get(this.position) != null)
				this.die();
			
			// [intensifies]
			if (!this.intense && this.position.y > Config.levelMaxY - 10) {
				this.intense = true;
				this.graphicLeft = GraphicFactory.newDogeIntense();
				this.graphicRight = GraphicFactory.newDogeIntense();
				this.graphicRight.flip();
				this.graphicSitting = GraphicFactory.newDogeIntense();
			}
			
			// activate
			ArrayList<Entity> list = this.level.getEntitiesInRadius(this.position, 1);
			for (Entity e : list) {
				e.activate();
			}
			
			// check to fall
			Entity bot = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
			if (bot == null) {
				this.mover = new MoverLinear(new Vec2f(0f, 1f), Math.round(100 * (1 / this.level.getGravity())) );
				return;
			}
			
			// move
			if (Keyboard.isKeyDown(Config.keyUp)) {
				// top
				this.moveDirection = 0;
				this.state = DogeState.SITTING;
				this.stateChanged = true;
				return;
				
			} else if (Keyboard.isKeyDown(Config.keyRight)) {
				// right
				this.moveDirection = 1;
				this.state = DogeState.RIGHT;
				this.stateChanged = true;
				
				Entity right = this.level.get(new Vec2i(this.position.x + 1, Math.round(this.position.y)));
				if (right == null) {
					this.mover = new MoverLinear(new Vec2f(1f, 0f), 100);
					this.state = DogeState.RIGHT;
				} else {
					Entity up = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity rightup = this.level.get(new Vec2i(this.position.x + 1, this.position.y - 1));
						if (rightup == null) {
							this.mover = new MoverLinear(new Vec2f(1f, -1f), 200);
							return;
						}
					}
				}
				
			} else if (Keyboard.isKeyDown(Config.keyDown)) {
				// down
				this.moveDirection = 2;
				this.state = DogeState.SITTING;
				this.stateChanged = true;
				return;
				
			} else if (Keyboard.isKeyDown(Config.keyLeft)) {
				// left
				this.moveDirection = 3;
				this.state = DogeState.LEFT;
				this.stateChanged = true;
				
				Entity left = this.level.get(new Vec2i(this.position.x - 1, this.position.y));
				if (left == null) {
					this.mover = new MoverLinear(new Vec2f(-1f, 0f), 100);
				} else {
					Entity up = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
					if (up == null) {
						Entity leftup = this.level.get(new Vec2i(this.position.x - 1, this.position.y - 1));
						if (leftup == null) {
							this.mover = new MoverLinear(new Vec2f(-1f, -1f), 200);
							return;
						}
					}
				}
			} else {
				this.state = DogeState.SITTING;
				this.stateChanged = true;
			}
		}
		
		// dig
		deltaDig += delta;
		if (this.deltaDig > Config.dogeDelayDig && Keyboard.isKeyDown(Config.keyDig)) {
			if (this.moveDirection == 0) {
				// up
				Entity top = this.level.get(new Vec2i(this.position.x, this.position.y - 1));
				if (top != null) {
					top.destroy();
					this.deltaDig = delta;
					return;
				}
			} else if (this.moveDirection == 1) {
				// right
				Entity right = this.level.get(new Vec2i(this.position.x + 1, this.position.y));
				this.state = DogeState.DIGGINGRIGTH;
				this.stateChanged = true;
				if (right != null) {
					right.destroy();
					this.deltaDig = delta;
					return;
				}
				
			} else if (this.moveDirection == 2) {
				// down
				this.state = DogeState.DIGGINGDOWN;
				this.stateChanged = true;
				Entity bottom = this.level.get(new Vec2i(this.position.x, this.position.y + 1));
				if (bottom != null) {
					bottom.destroy();
					this.deltaDig = delta;
					return;
				}
				
			} else if (this.moveDirection == 3) {
				// left
				Entity left = this.level.get(new Vec2i(this.position.x - 1, this.position.y));
				this.state = DogeState.DIGGINGLEFT;
				this.stateChanged = true;
				if (left != null) {
					left.destroy();
					this.deltaDig = delta;
					return;
				}
			}
		}
	}
	
	public void render (int delta) {
		GL11.glPushMatrix();
			GL11.glTranslatef(this.position.x + this.offset.x, this.position.y + this.offset.y, 0f);
			for (Graphic g : this.graphics) {
				if (g != null)
					g.render(delta);
			}
		GL11.glPopMatrix();
	}
}

enum DogeState {
	SITTING,
	RIGHT,
	LEFT,
	DIGGINGDOWN,
	DIGGINGRIGTH,
	DIGGINGLEFT,
	DEAD
}
