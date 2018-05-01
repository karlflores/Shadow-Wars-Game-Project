import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

// Implements the laser class -- should be created every time the space bar is hit
public abstract class Laser extends Sprite{

    //constructor class
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
    
    public void update(Input input,int delta) throws SlickException{
        // only update if it exists
        if(!getExistState()){
            //do no thing
            return;
        }
        super.update(input,delta);
    }
}
