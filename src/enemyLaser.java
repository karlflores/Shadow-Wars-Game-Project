import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

// Implements the laser class -- should be created every time the space bar is hit
public class enemyLaser extends Laser{
    // laser constants
    private static final float DESTROY_POSITION = App.SCREEN_HEIGHT + 10f;
    private static final float LASER_SPEED = 0.7f;

    // laser data
    private static final String LASER_IMG_SRC = "res/enemy-shot.png";

    //constructor class
    public enemyLaser(float x, float y) throws SlickException{
        super(LASER_IMG_SRC,x,y);
    }
    
    public void update(Input input,int delta) throws SlickException{
        super.update(input,delta);
        //update the y positions of the laser shot if it exists
        setY(getY() + LASER_SPEED * delta);

        // if the laser has hit the top of the screen -- set the render state of the laser to false
        if(getY() > DESTROY_POSITION){
            setExistState(false);
        }
    }
}
