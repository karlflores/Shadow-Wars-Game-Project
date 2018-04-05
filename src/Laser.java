import org.newdawn.slick.SlickException;
import java.lang.Math;
public class Laser extends Sprite{
    //private String imageSrc = "ref/shot";
    private boolean laserFired;
    private float EPS = (float)1E-3;
    private float LASER_SPEED = (float)2;
    //constructor class
    public Laser(String imageSrc, float x,float y) throws SlickException{
        super(imageSrc,x,y);
        this.laserFired = true;
    }

    public void update(float delta) throws SlickException{
        //update the y positions of the laser shot if it exists
        if(this.laserFired) {
            this.setY(this.getY() - LASER_SPEED * delta);
        }

        // if the laser has hit the top of the screen -- destroy the image
        if((this.getY()-this.getHeight()/2) < EPS){
            this.destroyImage();
        }
    }
}
