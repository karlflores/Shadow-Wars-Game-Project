import org.newdawn.slick.Input;

public class Sprite {
	public Sprite(String imageSrc, float x, float y) {
		// Why would the constructor need a path to an image, and a coordinate?
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
