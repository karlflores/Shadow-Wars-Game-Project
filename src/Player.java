import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;

public class Player extends Sprite{

    //instance constants
    private float MOVE_RATE = 0.5f;
    private int MAX_NUM_LASER = 256;
    private String laserImgSrc = "res/shot.png";
    // instance variables
    private Laser[] laserArr;
    private int numLaserFired;

    public Player(String imageSrc,float x,float y) throws SlickException {
        super(imageSrc,x,y);

        this.laserArr = new Laser[256];
        this.numLaserFired = 0;

    }
    public void update(Input input ,int delta) throws SlickException{
        super.update(input,delta);

        // move according to key presses
        if(input.isKeyDown(Input.KEY_UP)){
            // move up -- decrease Y
            this.setY(this.getY()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            // move down -- increase Y
            this.setY(this.getY()+MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            // move left -- decrease X
            this.setX(this.getX()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            // move right -- increase X
            this.setX(this.getX()+MOVE_RATE*delta);
        }

        // laser fired if space bar is hit
        if(input.isKeyPressed(Input.KEY_SPACE)){
            // create a new laser at the middle of the current location of the player
            this.laserArr[numLaserFired] = new Laser(this.laserImgSrc,this.getX()+this.getWidth()/2-8,
                    this.getY()+this.getHeight()/2-8);
`
            //increment the number of laser shots fired ;
            numLaserFired = (numLaserFired+1)%MAX_NUM_LASER;
        }

        // update the laser locations
        for(int i = 0; i < numLaserFired; i++){

            // if the laser exists then we can update it
            laserArr[i].update(input,delta);
            // if the laser does not exist we can get rid of it.
        }
        System.out.println(numLaserFired);
        //System.out.println("wtf");
        // render the laser
    }
    //override the sprite render -- need to render all the lasers that the player has fired
    public void render(){
        super.render();

        // need to render each of the laser shots on the screen
        for(int i = 0;i < numLaserFired; i++){
            if(laserArr[i].getRenderState()) {
                laserArr[i].render();
            }
        }
    }

    public int getNumLasersFired(){
        return this.numLaserFired;
    }
    public Laser[] getLasersArr(){
        return this.laserArr;
    }

}
