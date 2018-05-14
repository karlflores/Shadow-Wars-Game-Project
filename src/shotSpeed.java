import org.newdawn.slick.SlickException;

public class shotSpeed extends Powerup {

    private static final String IMG_SRC = "res/shotspeed-powerup.png";

    public shotSpeed(float x, float y) throws SlickException{
        super(IMG_SRC,x,y);
    }

    // activate method call -- this should be called when the player catches the powerup
    public void activate(Player player){
        player.setShootRateTimer(getPowerupTime());
    }
}
