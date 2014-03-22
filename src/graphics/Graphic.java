package graphics;
public interface Graphic {
	public void render(int delta);
	
	public void flipX();
	public void unflipX();
	
	public boolean disposable();
	public void reset();
	
	public Graphic clone();
	
	public void destroy();
}