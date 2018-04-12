import org.lwjgl.Sys;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import utilities.BoundingBox;

public abstract class Sprite {

    // sprite data
	private float x;
	private float y;
	private Image image;
	private BoundingBox bb;

	/*
	* a sprite exists if it has not been killed and therefore should be rendered to the screen and
	* its parameters updated
    */
    private boolean exists;

	public Sprite(String imageSrc, float x, float y) throws SlickException {
		// Why would the constructor need a path to an image, and a coordinate?
		this.x = x;
		this.y = y;
		image = new Image(imageSrc);
		//set the bounding box of the image for collision detection
        //System.out.println(this.image.getHeight());
		bb = new BoundingBox(this.image,x,y);

		// when a sprite is created it should be rendered to the screen
		exists = true;

	}
	
	public  void update(Input input, int delta) throws SlickException{
		// How can this one method deal with different types of sprites?
        // if the sprite exists update it
        if(!exists){
            return;
        }
        // set the bounding box of the image to update to the new location of the sprite and image
        bb.setX(x);
        bb.setY(y);
        // each of the sub classes will have an update method that overrides this method;

        /*
        For all updates if we want to make sure that it updates by x pixels per millisecond.
        Since delta is the number of ms since the last frame update therefore x*delta is the
        number pixels it should move on this update
         */
	}

	public void render() {
		// This should be pretty simple.
        // draw the image if it has not been destroyed.
        if(exists) {
            image.draw(x, y);
        }

	}
	
	public void contactSprite(Sprite other) {
		// Should be called when one sprite makes contact with another.
        // sets the exist state of both sprites
        other.exists = false;
        this.exists = false;
	}

    //tests whether a collision as occurred
    public boolean makesContact(Sprite other){
        //use the bounding boxes created when instantiating the sprite to check for collisions
        if(bb.intersects(other.bb)){
            return true;
        }else{
            //System.out.println("check intersection - false");
            return false;
        }
    }

    // setters and getters
    // location setters

    // within the width of the screen
	public void setX(float x){
	    if(x >= 0 && x <= App.SCREEN_WIDTH-image.getWidth()){
	        this.x = x;
        }
    }

    // set y -- all sprites cannot move past the bottom of the screen
    public void setY(float y){
	    if(y<=App.SCREEN_HEIGHT-getHeight()){
	        this.y = y;
        }
    }
    // location getters
    public float getX(){
	    return x;
    }
    public float getY(){
	    return y;
    }

    // does this sprite exist on the screen -- used to handle updates to the object
    public boolean getExistState(){
        return this.exists;
    }
    public void setExistState(boolean value){
        exists = value;
    }
    // gets the height and width of the image
    public int getHeight(){
	    return image.getHeight();
    }
    public int getWidth(){
	    return image.getWidth();
    }
}

