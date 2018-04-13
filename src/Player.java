import org.lwjgl.Sys;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
// Implementation of the player class as an extension of the sprite class
public class Player extends Sprite{

    //constants
    private static final float MOVE_RATE = 0.5f;
    private static final int MAX_NUM_LASER = 4096;

    // laser data
    private Laser[] laserArr;
    private String laserImgSrc = "res/shot.png";
    private int numLaserFired;
    private static int minLaserIndexOnScreen;
    // Constructor
    public Player(String imageSrc,float x,float y) throws SlickException{
        // update the player
        super(imageSrc,x,y);
        // create the array of lasers
        laserArr = new Laser[256];
        numLaserFired = 0;
        minLaserIndexOnScreen = 0;

    }

    // update method
    public void update(Input input ,int delta) throws SlickException{
        // only update the player if it exists
        if(!getExistState()){
            return;
        }
        super.update(input,delta);

        // move according to key presses
        if(input.isKeyDown(Input.KEY_UP)){
            // move up -- decrease Y
            setY(getY()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_DOWN)){
            // move down -- increase Y
            setY(getY()+MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_LEFT)){
            // move left -- decrease X
            setX(getX()-MOVE_RATE*delta);
        }
        if(input.isKeyDown(Input.KEY_RIGHT)){
            // move right -- increase X
            setX(getX()+MOVE_RATE*delta);
        }

        // laser fired if space bar is hit
        if(input.isKeyPressed(Input.KEY_SPACE)){
            // create a new laser at the middle of the current location of the player
            laserArr[numLaserFired] = new Laser(laserImgSrc,getX()+getWidth()/2,
                    getY()+getHeight()/2);

            // edit the location of the laser to be correctly centered according to the middle of the laser sprite
            float currLaserPosX = laserArr[numLaserFired].getX();
            float currLaserPosY = laserArr[numLaserFired].getY();
            float offsetX = laserArr[numLaserFired].getWidth()/2;
            float offsetY = laserArr[numLaserFired].getHeight()/2;
            // reset the laser position
            laserArr[numLaserFired].setX(currLaserPosX - offsetX);
            laserArr[numLaserFired].setY(currLaserPosY - offsetY);

            // increment the number of laser shots fired  -- wrap it to the max laser shots to overwrite the
            // old laser shots if it comes to this point;
            numLaserFired = (numLaserFired+1)%MAX_NUM_LASER;
            //minLaserIndexOnScreen = (minLaserIndexOnScreen+1)%MAX_NUM_LASER;
        }

        // update the laser locations
        for(int i = 0; i < numLaserFired; i++){
            // skip over the laser shots that do not exist
            if(!laserArr[i].getExistState()){
                continue;
            }
            // if the laser exists then we can update it
            laserArr[i].update(input,delta);
            // if the laser does not exist we can get rid of it.
        }
    }

    //override the sprite render -- need to render all the lasers that the player has fired
    public void render(){
        super.render();

        // need to render each of the laser shots on the screen
        for(int i = 0;i < numLaserFired; i++){
            if(laserArr[i].getExistState()) {
                laserArr[i].render();
            }
        }
    }

    // getter and setter methods
    public int getNumLasersFired(){
        return numLaserFired;
    }

    public Laser[] getLasersArr(){
        return laserArr;
    }

    // override setY() -- handles the top of the screen
    public void setY(float y){
        // the player cannot move past the top of the screen;
        if(y>=0){
            super.setY(y);
        }
    }

    public static void setMinLaserIndexOnScreen(int index){
        minLaserIndexOnScreen = index%MAX_NUM_LASER;
    }
    public static int getMinLaserIndexOnScreen(){
        //System.out.println(minLaserIndexOnScreen);
        return minLaserIndexOnScreen;

    }
}
