import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
public class Sprite {

	private float x;
	private float y;
	private Image image;
	public Sprite(String imageSrc, float x, float y) throws SlickException {
		// Why would the constructor need a path to an image, and a coordinate?
		this.x = x;
		this.y = y;
		this.image = new Image(imageSrc);
	}
	
	public void update(Input input, int delta) {
		// How can this one method deal with different types of sprites?
	}
	
	public void render() {
		// This should be pretty simple.
	}
	
	public void contactSprite(Sprite other) {
		// Should be called when one sprite makes contact with another. 
	}
}
