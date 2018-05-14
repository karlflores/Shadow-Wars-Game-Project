import org.newdawn.slick.SlickException;

public class Shield extends Powerup {
    private static final String IMG_SRC = "res/shield-powerup.png";

    public Shield(float x, float y) throws SlickException{
        super(IMG_SRC,x,y);
    }

    public void activate(Player player){
        player.setImmunityTimer(getPowerupTime());
    }

}
