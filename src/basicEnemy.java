import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Class representing the basic enemy
 */
public class basicEnemy extends Enemy{
    private static final String ENEMY_IMG_PATH = "res/basic-enemy.png";
    private static final float MOVE_RATE = 0.2f;
    private static final int SCORE = 50;

    /**
     * Constructor: create the basic enemy at the required x position
     * @param x : Float -- x location to create the enemy at
     * @param delay : Float -- how long the enemy should do nothing
     * @throws SlickException
     */
    public basicEnemy(float x,int delay) throws SlickException{
        super(ENEMY_IMG_PATH,x,SCORE,delay);
    }

    /**
     * Update method
     * @param input
     * @param delta
     * @throws SlickException
     */
    public void update(Input input, int delta) throws SlickException{
        super.update(input, delta);
        // set the Y location of the Enemy
        if(getTimeExists() > getDelay()){
            setY(getY()+MOVE_RATE*delta);
        }
    }


}
