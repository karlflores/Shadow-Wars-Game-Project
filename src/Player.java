import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
// Implementation of the player class as an extension of the sprite class
public class Player extends Sprite{

    //constants
    private static final float MOVE_RATE = 0.5f;
    private static final int MAX_NUM_LASER = 4096;

    // laser data

    private static final String LASER_IMG_SRC = "res/shot.png";
    private int numLaserFired;
    private static int minLaserIndexOnScreen;
    // Constructor
    public Player(String imageSrc,float x,float y) throws SlickException{
        // update the player
        super(imageSrc,x,y);
    }

    // update method
    public void update(Input input ,int delta) throws SlickException{
        // only update the player if it exists
        if(!getExistState()){
            return;
        }
        super.update(input,delta);

        // move according to key presses
        if(input.isKeyDown(Input.KEY_UP)){
            // move up -- decrease Y
            setY(getY()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            // move down -- increase Y
            setY(getY()+MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            // move left -- decrease X
            setX(getX()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            // move right -- increase X
            setX(getX()+MOVE_RATE*delta);
        }

        // laser fired if space bar is hit
        if(input.isKeyPressed(Input.KEY_SPACE)){
            // create a new laser at the middle of the current location of the player
            Laser tempLaser = new Laser(LASER_IMG_SRC,getX()+getWidth()/2,
                    getY()+getHeight()/2);

            // edit the location of the laser to be correctly centered according to the middle of the laser sprite
            float offsetX = tempLaser.getWidth()/2;
            float offsetY = tempLaser.getHeight()/2;
            // reset the laser position
            tempLaser.setX(tempLaser.getX() - offsetX);
            tempLaser.setY(tempLaser.getY() - offsetY);

            // add the laser to the world
            World.getWorld().addSprite(tempLaser);
        }

    }

    //override the sprite render -- need to render all the lasers that the player has fired
    public void render(){
        super.render();
    }

    // override setY() -- handles the top of the screen
    public void setY(float y){
        // the player cannot move past the top of the screen;
        if(y>=0){
            super.setY(y);
        }
    }

}
