import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import java.lang.Math;

/**
 * Class representing the sine Enemy
 */
public class sineEnemy extends Enemy {

    private static final String ENEMY_IMG_PATH = "res/sine-enemy.png";
    private static final float MOVE_RATE = 0.15f;
    private static final int SCORE = 100;
    private static final int AMPLITUDE = 96;
    private static final int PERIOD = 1500;
    private float xPos;

    /**
     * Update method
     * @param input : keyboard input
     * @param delta : Integer -- time in ms since the last screen update
     * @throws SlickException
     */
    public sineEnemy(float x,int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,SCORE,delay);

        // the average xPos of the enemy (pivot)
        xPos = getX();
    }

    /**
     * Update method
     * @param input : keyboard input
     * @param delta : Integer -- time in ms since the last screen update
     * @throws SlickException
     */
    public void update(Input input, int delta) throws  SlickException{
        super.update(input,delta);
        // System.out.println(offset());
        if(getTimeExists() > getDelay()) {
            // calculate the offset and add it to the current x position
            setX(xPos + offset());
            // update the y position
            setY(getY() + MOVE_RATE * delta);
        }
    }

    /**
     * calculate the offset of the xPos for the sine enemy
     * @return : Float -- the offset of the enemy
     */
    private float offset(){
        float inner = (float)(2*Math.PI)/PERIOD;
        // get how long the game has been running for and calculate the offset
        inner*=World.getWorld().getTime()-getDelay();

        return (float)(AMPLITUDE*Math.sin(inner));
    }

}
