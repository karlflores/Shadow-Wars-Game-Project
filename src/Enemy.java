import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
// Implements the enemy class as an extension of the sprite abstract class
// This currently does nothing but allows for extension
public abstract class Enemy extends Sprite {
    private final int delay;
    private final float moveRate;
    private final int score;
    private final static float INIT_YPOS = -64f;

    //constructor class
    public Enemy(String imageSrc, float x,float moveRate, int score,int delay) throws SlickException{
        super(imageSrc,x,INIT_YPOS);
        this.score = score;
        this.moveRate = moveRate;
        this.delay = delay;

    }

    public void update(Input input, int delta) throws SlickException{
        super.update(input, delta);

        // if the enemy goes off the screen -- then it does not exist anymore
        if(getY() > App.SCREEN_HEIGHT ){
            setExistState(false);
        }

    }

    // override setY() -- ensure that the enemy is located within the bounds of the board

    public int getScore() {
        return score;
    }
    public int getDelay(){
        return delay;
    }
}

