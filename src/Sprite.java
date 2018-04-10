import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public class Sprite {

    private float EPS = 1E-5f;
    private float SCREEN_HEIGHT = 768f;
    private float SCREEN_WIDTH = 1024f;

	private float x;
	private float y;
	private Image image;
	private BoundingBox bb;
	private boolean renderImage;

	public Sprite(String imageSrc, float x, float y) throws SlickException {
		// Why would the constructor need a path to an image, and a coordinate?
		this.x = x;
		this.y = y;
		this.image = new Image(imageSrc);
		//set the bounding box of the image for collision detection
        //System.out.println(this.image.getHeight());
		this.bb = new BoundingBox(this.image,x,y);

		// when a sprite is created it should be rendered to the screen
		this.renderImage = true;

	}
	
	public void update(Input input, int delta) throws SlickException{
		// How can this one method deal with different types of sprites?
        // set the bounding box of the image to update to the new location of the sprite and image
        System.out.format("%f %f\n",this.x,this.y);
        this.bb.setX(this.x);
        this.bb.setY(this.y);
        // each of the sub classes will have an update method that overrides this method;
	}

	public void render() {
		// This should be pretty simple.
        // draw the image if it has not been destroyed.
        if(this.renderImage) {
            image.draw(x, y);
        }

	}
	
	public void contactSprite(Sprite other) {
		// Should be called when one sprite makes contact with another.
        other.setRenderState(false);
        this.setRenderState(false);
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
    public void setRenderState(boolean value){
	    this.renderImage = value;
    }

    public boolean getRenderState() {
        return this.renderImage;
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
            System.out.println("THIS HAS MADE CONTACT");
            return true;
        }else{
            //System.out.println("check intersection - false");
            return false;
        }
    }

    public BoundingBox getBB(){
	    return this.bb;
    }
}

