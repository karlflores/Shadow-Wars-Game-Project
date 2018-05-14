import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import java.lang.Math;

public abstract class Powerup extends Sprite {
    private static final float Y_SPEED = 0.1f;
    private static final int POWERUP_TIME = 5000;

    public Powerup(String img, float x, float y) throws SlickException{
        // call the Sprite constructor
        super(img,x,y);
        System.out.println("CREATED POWER-UP");
    }

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

    public abstract void activate(Player player);

    public int getPowerupTime(){
        return POWERUP_TIME;
    }

}