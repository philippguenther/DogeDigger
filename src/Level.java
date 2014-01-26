import java.util.ArrayList;

import org.lwjgl.input.Mouse;


public class Level {
	private ArrayList<Box> boxes = new ArrayList<Box>();
	private Box lastClick;
	
	public void addBox(Box _b) {
		this.boxes.add(_b);
	}
	
	public Box getBoxByPos(Point pos) {
		Box r = null;
		for (Box b : this.boxes) {
			if (b.pos.equals(pos)) {
				r = b;
				break;
			}
		}
		return r;
	}
	
	public void input() {
		while(Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				int x = (int) (Mouse.getX() / Config.getBoxSize());
				int y = (int) ((Config.getWindowHeight() - Mouse.getEventY()) / Config.getBoxSize());
				Box b = this.getBoxByPos(new Point(x, y));
				
				if (b != null) {
					if (this.lastClick == null) {
						b.clicked = true;
						this.lastClick = b;
					} else if (this.lastClick.isNear(b)) {
						this.lastClick.swap(b);
						this.lastClick.clicked = false;
						this.lastClick = null;
					}
				} else {
					System.out.println("Box null");
				}
			}
		}
	}
	
	public void tick(int delta) {
		for (Box b : this.boxes) {
			b.tick(delta);
		}
	}
	
	public void render() {
		for (Box b : this.boxes) {
			b.render();
		}
	}
}
