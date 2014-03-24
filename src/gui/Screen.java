package gui;

import org.lwjgl.input.Keyboard;


public class Screen {
	private Button[] buttons;
	private int active;
	
	public Screen(Button[] _buttons) {
		this.buttons = _buttons;
	}
	
	public String poll() {
		Keyboard.next();
		switch (Keyboard.getEventKey()) {
		case Keyboard.KEY_RETURN:
			if (this.buttons[this.active] != null)
				return this.buttons[this.active].getDesc();
			break;
		case Keyboard.KEY_UP:
			if (this.active > 0)
				this.active--;
			break;
		case Keyboard.KEY_DOWN:
			if (this.active < this.buttons.length - 1)
				this.active++;
			break;
		}
		return null;
	}
	
	public void tick(int delta) {
		for (Button b : this.buttons)
			b.tick(delta);
		this.buttons[this.active].tickActive(delta);
	}
	
	public void render() {
		for (Button b : this.buttons)
			b.render();
		this.buttons[this.active].renderActive();
	}
}
