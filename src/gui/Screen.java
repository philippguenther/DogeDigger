package gui;

import org.lwjgl.input.Keyboard;


public class Screen {
	private Button[] buttons;
	private int active;
	
	private int delta;
	
	public Screen(Button[] _buttons) {
		this.buttons = _buttons;
		this.delta = 0;
	}
	
	public String poll(int delta) {
		this.delta += delta;
		Keyboard.next();
		int key = Keyboard.getEventKey();
		if (this.delta > 100 && Keyboard.getEventKeyState() && !Keyboard.isRepeatEvent()) {
			switch (key) {
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
			this.delta = delta;
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
