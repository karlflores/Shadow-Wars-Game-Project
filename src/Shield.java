import org.newdawn.slick.SlickException;

/**
 * Class representing the shield powerup
 */
public class Shield extends Powerup {
    private static final String IMG_SRC = "res/shield-powerup.png";

    /**
     * Constrctpr : create the shield powerup at a given location
     * @param x : Float -- x coord that it should be created at
     * @param y : Float -- y coord that it should be created at
     * @throws SlickException
     */
    public Shield(float x, float y) throws SlickException{
        super(IMG_SRC,x,y);
    }

    /**
     * Activate the powerup on a specified player
     * @param player : The player that the powerup acts on
     */
    public void activate(Player player){
        player.setImmunityTimer(player.getImmunityTimer()+getPowerupTime());
    }

}
