import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
public class Laser extends Sprite{
    // laser constants
    private float DESTROY_POSITION = 2.0f;
    private float LASER_SPEED = 3.0f;

    //constructor class
    public Laser(String imageSrc, float x,float y) throws SlickException{
        super(imageSrc,x,y);
    }


    public void update(Input input,int delta) throws SlickException{
        // only update if it exists
        if(!this.getExistState()){
            //do no thing
            return;
        }
        super.update(input,delta);
        //update the y positions of the laser shot if it exists

        this.setY(this.getY() - LASER_SPEED * delta);


        // if the laser has hit the top of the screen -- set the render state of the laser to false
        if(this.getY() < this.DESTROY_POSITION){
            //
            this.setExistState(false);
        }
    }
}
