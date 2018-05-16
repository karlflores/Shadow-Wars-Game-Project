import org.newdawn.slick.SlickException;

public class shotSpeed extends Powerup {

    private static final String IMG_SRC = "res/shotspeed-powerup.png";

    /**
     * Constructor: create the power up
     * @param img : String - the path at which the image is located
     * @param x : Float - the x coord where the powerup should be created
     * @param y : Float - the y coord where the powerup should be created
     * @throws SlickException
     */
    public shotSpeed(float x, float y) throws SlickException{
        super(IMG_SRC,x,y);
    }

    /**
     * Activate the powerup -- this should be called when the player catches the powerup
     * @param player : The player that the powerup acts on
     */
    public void activate(Player player){
        // add the powerup timer to the current shootrate timer
        player.setShootRateTimer(player.getShootRateTimer() + getPowerupTime());
    }
}
