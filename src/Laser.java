import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
public class Laser extends Sprite{
    //private String imageSrc = "ref/shot";
    private boolean laserFired;
    private boolean laserExists;
    private float EPS = 5.0f;
    private float LASER_SPEED = 3.0f;

    //constructor class
    public Laser(String imageSrc, float x,float y) throws SlickException{
        super(imageSrc,x,y);
        this.laserFired = true;
        this.laserExists = true;
    }

    public void update(Input input,int delta) throws SlickException{
        super.update(input,delta);

        if(!this.laserExists){
            //do no thing
            return;
        }
        //update the y positions of the laser shot if it exists
        if(this.laserFired) {
            this.setY(this.getY() - LASER_SPEED * delta);
        }

        // if the laser has hit the top of the screen -- set the render state of the laser to false
        if(this.getY() < EPS){
            //
            this.setRenderState(false);
            this.laserExists = false;
        }

    }
    public boolean getLaserExists(){
        return this.laserExists;
    }
    public void setLaserExists(boolean value){
        this.laserExists = value;
    }
}
