import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import java.lang.Math;

/**
 * Abstract class representing the powerups
 */
public abstract class Powerup extends Sprite {
    private static final float Y_SPEED = 0.1f;
    private static final int POWERUP_TIME = 5000;

    /**
     * Constructor: create the power up
     * @param img : String - the path at which the image is located
     * @param x : Float - the x coord where the powerup should be created
     * @param y : Float - the y coord where the powerup should be created
     * @throws SlickException
     */
    public Powerup(String img, float x, float y) throws SlickException{
        // call the Sprite constructor
        super(img,x,y);
        System.out.println("CREATED POWER-UP");
    }

    /**
     * Overridden Sprite  update method
     * @param input
     * @param delta
     * @throws SlickException
     */
    public void update(Input input, int delta) throws SlickException{

        // make sure the update the Sprite constructor before we update everything else
        super.update(input,delta);

        // set the Y location
        setY(getY()+ Y_SPEED*delta);

        if(getY() > App.SCREEN_HEIGHT){

            // once it goes off the screen we should mark it for removal;
            setExistState(false);
        }
    }

    /**
     * Activate the powerup
     * @param player : The player that the powerup acts on
     */
    public abstract void activate(Player player);

    /**
     * Get how long the powerup should last for
     * @return : Integer -- representing the time which the powerup is active for
     */
    public int getPowerupTime(){
        return POWERUP_TIME;
    }

}