import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import java.lang.Math;

public class sineEnemy extends Enemy {

    private static final String ENEMY_IMG_PATH = "res/sine-enemy.png";
    private static final float MOVE_RATE = 0.15f;
    private static final int SCORE = 100;
    private static final int AMPLITUDE = 96;
    private static final int PERIOD = 1500;
    private float xPos;
    public sineEnemy(float x,int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,SCORE,delay);

        // the average xPos of the enemy (pivot)
        xPos = getX();
    }

    public void update(Input input, int delta) throws  SlickException{
        super.update(input,delta);
        // System.out.println(offset());
        if(getTimeExists() > getDelay()) {
            setX(xPos + offset());
            setY(getY()+MOVE_RATE*delta);
        }
        // update the x location of the image
    }

    // calculate the offset of the xPos for the sine enemy
    private float offset(){
        float inner = (float)(2*Math.PI)/PERIOD;
        inner*=World.getWorld().getTime()-getDelay();

        return (float)(AMPLITUDE*Math.sin(inner));
    }

}
