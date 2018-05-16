import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

/**
 * Implements the enemy class as an extension of the sprite abstract class
 */
public abstract class Enemy extends Sprite {
    private final int delay;
    private final int score;
    private final static float INIT_YPOS = -64f;

    /**
     * Constructor: creates an enemy
     * @param imageSrc : String representing the path of the image source
     * @param x : Float representing the x coordinate of the Enemy to be placed on the screen
     * @param score: Integer representing the score of the enemy given to the player when it is destroyed
     * @param delay: Integer representing the time of which the Enemy does nothing
     * @throws SlickException
     */
    public Enemy(String imageSrc, float x, int score,int delay) throws SlickException{
        super(imageSrc,x,INIT_YPOS);

        // offset the position to being the top-left corner
        setX(getX()-getWidth()/2);
        setY(getY()-getHeight()/2);

        this.score = score;
        this.delay = delay;

    }

    /**
     * Override the Sprite update call
     * @param input
     * @param delta
     * @throws SlickException
     */
    public void update(Input input, int delta) throws SlickException{
        super.update(input, delta);

        // if the enemy goes off the screen -- then it does not exist anymore
        if(getY() > App.SCREEN_HEIGHT ){
            setExistState(false);
        }

    }

    /**
     * GET the score of the enemy
     * @return
     */
    public int getScore() {
        return score;
    }

    /**
     * Get the delay of the enemy
     * @return : Integer representing the time which the enemy does nothing
     */
    public int getDelay(){
        return delay;
    }
}

