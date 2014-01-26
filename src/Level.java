import java.util.ArrayList;

import org.lwjgl.input.Mouse;


public class Level {
	private ArrayList<Box> boxes = new ArrayList<Box>();
	private Box lastClick;
	
	public void addBox(Box _b) {
		this.boxes.add(_b);
	}
	
	public Box getBoxByPos(float x, float y) {
		Box r = null;
		for (Box b : this.boxes) {
			if (Math.abs(b.x - x) < 0.1f && Math.abs(b.y - y) < 0.1f) {
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
				Box b = this.getBoxByPos(x, y);
				
				if (b != null) {
					if (this.lastClick == null) {
						b.clicked = true;
						this.lastClick = b;
					} else if (this.lastClick.areSwapable(b)) {
						this.lastClick.swapPos(b);
						this.lastClick.clicked = false;
						this.lastClick = null;
					}
				} else {
					System.out.println("Box null");
				}
			}
		}
	}
	
	public void render(int _delta) {
		for (Box b : this.boxes) {
			b.render(_delta);
		}
	}
}
