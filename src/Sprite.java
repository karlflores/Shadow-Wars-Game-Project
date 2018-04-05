import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public class Sprite {

    private float EPS = (float)1E-5;
    private float SCREEN_HEIGHT = 768;
    private float SCREEN_WIDTH = 1024;

	private float x;
	private float y;
	private Image image;
	private BoundingBox bb;
	private boolean hasCollisionOccurred = false;

	public Sprite(String imageSrc, float x, float y) throws SlickException {
		// Why would the constructor need a path to an image, and a coordinate?
		this.x = x;
		this.y = y;
		this.image = new Image(imageSrc);

		//set the bounding box of the image for collision detection
        System.out.println(this.image.getHeight());
		this.bb = new BoundingBox(x,y,this.image.getWidth(),this.image.getHeight());

	}
	
	public void update(Input input, int delta) throws SlickException{
		// How can this one method deal with different types of sprites?

        // check if a collision has occurred
        if(hasCollisionOccurred == true) {
            //destroy the image --
            image.destroy();
        }
        // each of the sub classes will have an update method that overrides this method;

	}

	public void render() {
		// This should be pretty simple.
        // draw the image if it has not been destroyed.
        if(!image.isDestroyed()) {
            image.draw(x, y);
        }
	}
	
	public void contactSprite(Sprite other) {
		// Should be called when one sprite makes contact with another.
        this.hasCollisionOccurred = true;
	}

	public void setX(float x){
	    if(x >= 0 && x <= SCREEN_WIDTH-this.image.getWidth()){
	        this.x = x;
        }
    }

    // setters and getters
    public void setY(float y){
	    if(y >= 0 && y<=SCREEN_HEIGHT-this.image.getHeight()){
	        this.y = y;
        }
    }
    public float getX(){
	    return this.x;
    }
    public float getY(){
	    return this.y;
    }

    public void destroyImage() throws SlickException{
	    image.destroy();
    }
    public int getHeight(){
	    return image.getHeight();
    }
    public int getWidth(){
	    return image.getWidth();
    }

    //tests whether a collision as occurred
    public boolean makesContact(Sprite other){
        //use the bounding boxes created when instantiating the sprite to check for collisions
        if(this.bb.intersects(other.bb)){
            return true;
        }else{
            return false;
        }
    }
}

