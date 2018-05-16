import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


/**
 * Implements the laser class -- should be created every time the space bar is hit
 */
public abstract class Laser extends Sprite{

    /**
     * Constructor: create the laser object at a specified location on the screen
     * @param imageSrc
     * @param x : Float - the x coord where the laser should be created
     * @param y : Float - the y coord where the laser should be created
     * @throws SlickException
     */
    public Laser(String imageSrc, float x, float y) throws SlickException{
        super(imageSrc,x,y);

        // edit the location of the laser to be correctly centered according to the middle of the laser sprite
        // calculate the offsets to center the object correctly
        float offsetX = this.getWidth()/2;
        float offsetY = this.getHeight()/2;

        // reset the laser position to the middle of the object that created it
        setX(getX() - offsetX);
        setY(getY() - offsetY);
    }

    /**
     * Update the layer attributes
     * @param input : keyboard input
     * @param delta : Integer -- time in ms since the last screen update
     * @throws SlickException
     */
    public void update(Input input,int delta) throws SlickException{
        // only update if it exists
        if(!getExistState()){
            //do no thing
            return;
        }
        super.update(input,delta);

        if(getY() > App.SCREEN_HEIGHT){
            setExistState(false);
        }
    }
}
