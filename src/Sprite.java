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

    private int timeExist;

    /**
     * Constructor: create the sprite obeject at the specified location
     * @param imgsrc : String - the path at which the image is located
     * @param x : Float - the x coord where the powerup should be created
     * @param y : Float - the y coord where the powerup should be created
     * @throws SlickException
     */
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

		// timer
        timeExist = 0;

	}

    /**
     * Update the parameters of the sprite -- this is called when we want to update the screen state
     * @param input : keyboard input
     * @param delta : Integer -- time in ms since the last screen update
     * @throws SlickException
     */
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

        // update the timer -- delta is the number of ms since the last update
        timeExist+=delta;
	}

    /**
     * Render the sprite to the screen
     */
	public void render() {
		// This should be pretty simple.
        // draw the image if it has not been destroyed.
        if(exists) {
            image.draw(x, y);
        }

	}

    /**
     * Method called when this sprite is in contact with another sprite
     * @param other : Sprite -- the other sprite
     */
	public void contactSprite(Sprite other) {
		// Should be called when one sprite makes contact with another.
        // sets the exist state of both sprites
        other.exists = false;
        this.exists = false;
	}

    /**
     * Method that tests whether a collision as occurred
     * @param other : Sprite -- the other sprite that this sprite might be colliding with
     * @return : Boolean -- True if a collision has occurred, False if a collision has not occurred
     */
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

    /**
     * Set the x location of this sprite
     * @param x : Float -- the x coord to set to
     */
	public void setX(float x){
	    if(x >= 0 && x <= App.SCREEN_WIDTH-image.getWidth()){
	        this.x = x;
        }
    }


    /**
     * Set the y location of this sprite -- note that we cannot set y to be off the screen
     * @param y : Float -- the y coord to set to
     */
    public void setY(float y){
	    this.y = y;
    }

    /**
     * X location getters
     * @return : Float -- the x coord of the sprite
     */
    public float getX(){
	    return x;
    }

    /**
     * Y location getters
     * @return : Float -- the x coord of the sprite
     */
    public float getY(){
	    return y;
    }
    /**
     * Get if this sprite exists on the screen or not
     * @return : Boolean
     */
    public boolean getExistState(){
        return this.exists;
    }
    /**
     * Set if this sprite exists on the screen or not
     * @param value : Boolean
     */
    public void setExistState(boolean value){
        exists = value;
    }


    /**
     * Image height getter
     * @return : Integer -- the height of the sprite image
     */
    public int getHeight(){
	    return image.getHeight();
    }

    /**
     * Image width getter
     * @return : Integer -- the width of the sprite image
     */
    public int getWidth(){
	    return image.getWidth();
    }

    /**
     * Get the exist state of the sprite
     * @return Boolean
     */
    public int getTimeExists(){
	    return timeExist;
    }

}

